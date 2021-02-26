package com.github.aborn.webx.modules.tc.transfer;

import com.github.aborn.webx.modules.tc.DayBitSet;
import com.github.aborn.webx.modules.tc.TimeTraceLogger;
import com.github.aborn.webx.utils.DateBitSlotUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.BitSet;

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

        String str = postData(dayBitSet);
        System.out.println(str);
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

        UserActionEntity userActionEntity = new UserActionEntity();
        userActionEntity.setToken(ServerInfo.getConfigServerInfo().getToken());
        userActionEntity.setDayBitSetArray(dayBitSet.getDayBitSetByteArray());
        userActionEntity.setDay(dayBitSet.getDay());

        Date currentDate = new Date();

        if (lastPostDate != null
                && lastPostData.cardinality() == dayBitSet.countOfCodingSlot()
                && ((currentDate.getTime() - lastPostDate.getTime()) / 1000 < 5 * 60)) {   // 5分钟
            return "No need to post";
        }

        SenderResponse result = postDataJson(ServerInfo.getConfigServerInfo().getUrl(), userActionEntity);
        TimeTraceLogger.info("after postdata, status:" + result.getStatus());
        lastPostDate = currentDate;
        lastPostData.or(dayBitSet.getCodingBitSet());

        return result.getMessage();
    }

    private static SenderResponse postDataJson(String url, UserActionEntity userAction) {
        SenderResponse senderResponse = new SenderResponse("", true);

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(completed(url));
        String result = null;
        try {

            StringEntity httpEntity = new StringEntity(userAction.toJson());
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(httpEntity);

            response = httpClient.execute(httpPost);
            result = toString(response.getEntity());
            senderResponse.setStatus(true);
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

    @NotNull
    private static String toString(HttpEntity entity) {
        String result = null;
        try {
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (StringUtils.isBlank(result)) {
            return StringUtils.EMPTY;
        } else {
            return format(result);
        }
    }

    private static String format(String str) {
        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(str);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(parse);
        return json;
    }

    private static boolean dataSenderSwitch() {
        return true;
    }
}
