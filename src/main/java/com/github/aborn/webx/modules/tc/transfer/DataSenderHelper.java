package com.github.aborn.webx.modules.tc.transfer;

import com.github.aborn.webx.modules.tc.DayBitSet;
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

/**
 * @author aborn
 * @date 2021/02/10 12:00 PM
 */
public class DataSenderHelper {

    public static void main(String[] args) {
        testPost();
    }

    public static void testPost() {
        String url = "http://127.0.0.1:8080/webx/live/postUserAction";
        String token = "8ba394513f8420e";
        DayBitSet dayBitSet = new DayBitSet();

        UserActionRequest userActionRequest = new UserActionRequest();
        userActionRequest.setToken(token);
        userActionRequest.setDayBitSet(dayBitSet);

        String str = postDataJson(url, userActionRequest.toJson());
        System.out.println(str);
    }

    public static String postDataJson(String url, String json) {

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postMethod = new HttpPost(completed(url));

        String result = null;
        try {
            StringEntity httpEntity = new StringEntity(json);

            httpEntity.setContentType("application/json");
            httpEntity.setContentEncoding("UTF-8");

            postMethod.addHeader("Content-type", "application/json; charset=utf-8");
            postMethod.setHeader("Accept", "application/json");
            // post body
            postMethod.setEntity(httpEntity);

            response = httpClient.execute(postMethod);
            result = toString(response.getEntity());
        } catch (IOException e) {
            result = "There was an error accessing to URL: " + url + "\n\n" + e.toString();
        } finally {
            release(response, httpClient);
        }

        return result;
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

    public static String format(String str) {
        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(str);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(parse);
        return json;
    }
}
