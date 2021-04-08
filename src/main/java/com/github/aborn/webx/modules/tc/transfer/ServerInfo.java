package com.github.aborn.webx.modules.tc.transfer;

import com.github.aborn.webx.modules.tc.TimeTraceLogger;
import com.github.aborn.webx.utils.ConfigFile;
import org.apache.commons.lang3.StringUtils;

/**
 * @author aborn
 * @date 2021/02/10 2:41 PM
 */
public class ServerInfo {
    private static final String POST_URL = "https://aborn.me/webx/postUserAction";
    private static String TOKEN = null;
    public static final String VALIDATE_URL = "https://aborn.me/webx/user/postUserConfig";
    public static final String VALIDATE_URL_LOCAL = "http://127.0.0.1:8080/webx/user/postUserConfig";

    String url;
    String token;

    public ServerInfo(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public static ServerInfo DEFAULT = new ServerInfo(POST_URL, "8ba394513f8420e");
    public static ServerInfo LOCAL = new ServerInfo("http://127.0.0.1:8080/webx/postUserAction", "8ba394513f8420e");

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public static ServerInfo getConfigServerInfo() {
        // 调试的时候可使用LOCAL
        String token = TOKEN != null ? TOKEN : ConfigFile.get("settings", "token");
        TimeTraceLogger.info("current user token is:" + token);
        if (StringUtils.isNotBlank(token)) {
            return new ServerInfo(POST_URL, token.trim());
        } else {
            return null;
        }
    }

    public static void setToken(String token) {
        TOKEN = token;
    }
}
