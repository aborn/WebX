package com.github.aborn.webx.modules.tc.transfer;

import com.github.aborn.webx.modules.tc.DayBitSet;
import com.github.aborn.webx.modules.tc.TimeTraceLogger;
import com.github.aborn.webx.utils.DateBitSlotUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Date;

/**
 * @author aborn
 * @date 2021/02/10 12:00 PM
 */
public class DataSenderHelper {
    public DataSenderHelper() {
    }

    private static final BitSet lastPostData = new BitSet(DateBitSlotUtils.SLOT_SIZE);
    private static Date lastPostDate = null;

    public static void main(String[] args) {
        testPost();
    }

    public static void testPost() {
        DayBitSet dayBitSet = new DayBitSet();
        dayBitSet.setSlotByCurrentTime();
        dayBitSet.set(1);

        //String str = postData(dayBitSet);
        //System.out.println(str);

        SenderResponse response = validate("webx","8ba394513f8420e");
        System.out.println(response);
    }

    public static String postData(DayBitSet dayBitSet) {
        if (!dataSenderSwitch()) {
            return "error, data sender switch off.";
        }

        if (dayBitSet.countOfCodingSlot() == 0) {
            return "No need to post, reason: data empty.";
        }

        if (!dayBitSet.isToday()) {
            return "No need to post, reason: is not today's data. " + dayBitSet.getDay();
        }

        ServerInfo serverInfo = ServerInfo.getConfigServerInfo();
        if (serverInfo == null) {
            return "User config not provided, no posting data.";
        }

        UserActionEntity userActionEntity = new UserActionEntity();
        userActionEntity.setToken(serverInfo.getToken());
        userActionEntity.setDayBitSetArray(dayBitSet.getDayBitSetByteArray());
        userActionEntity.setDay(dayBitSet.getDay());

        Date currentDate = new Date();

        // 5分钟内无需频繁上传
        if (lastPostDate != null
                && lastPostData.cardinality() == dayBitSet.countOfCodingSlot()
                && ((currentDate.getTime() - lastPostDate.getTime()) / 1000 < 5 * 60)) {
            return "No need to post, reason: time to short.";
        }

        SenderResponse result = postDataJson(serverInfo.getUrl(), userActionEntity);
        TimeTraceLogger.info("after postdata, status:" + result.getStatus());
        lastPostDate = currentDate;
        lastPostData.clear();
        lastPostData.or(dayBitSet.getCodingBitSet());

        return result.getMessage();
    }

    /**
     * 验证token和id是否合法
     * @param id 用户的github id
     * @param token 用户申请获得的token
     * @return
     */
    public static SenderResponse validate(String id, String token) {
        UserEntity userEntity = new UserEntity(token, id);
        return postDataJson(ServerInfo.VALIDATE_URL, userEntity);
    }

    private static SenderResponse postDataJson(String url, SenderEntity senderEntity) {
        SenderResponse senderResponse = new SenderResponse("", true);

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(completed(url));
        String result = null;
        try {

            StringEntity httpEntity = new StringEntity(senderEntity.toJson());
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(httpEntity);

            response = httpClient.execute(httpPost);
            BaseResponse baseResponse = toString(response.getEntity());
            if (baseResponse == null) {
                result = "Error in http request.";
            } else {
                senderResponse.setStatus(baseResponse.getCode() == 200);
                senderResponse.setMessage(baseResponse.getMsg());
            }
        } catch (IOException e) {
            result = "There was an error accessing to URL: " + url + "\n\n" + e.toString();
            senderResponse.setStatus(false);
        } finally {
            release(response, httpClient);
        }

        senderResponse.setMessage(result);
        return senderResponse;
    }

    private static String completed(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return url;
    }

    private static void release(CloseableHttpResponse response, CloseableHttpClient httpClient) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
    }

    private static BaseResponse toString(HttpEntity entity) {
        String result = null;
        try {
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (StringUtils.isBlank(result)) {
            return null;
        } else {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
            int code = jsonObject.get("code").getAsInt();
            boolean status = jsonObject.get("status").getAsBoolean();
            String msg = jsonObject.get("msg").getAsString();
            BaseResponse baseResponse = new BaseResponse(status, msg, code);
            baseResponse.setData(jsonObject.get("data").getAsString());
            return baseResponse;
        }
    }

    private static boolean dataSenderSwitch() {
        return true;
    }
}
