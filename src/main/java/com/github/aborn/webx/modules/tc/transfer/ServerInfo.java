package com.github.aborn.webx.modules.tc.transfer;

/**
 * @author aborn
 * @date 2021/02/10 2:41 PM
 */
public class ServerInfo {
    String url;
    String token;

    public ServerInfo(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public static ServerInfo DEFAULT = new ServerInfo("https://aborn.me/webx/postUserAction", "8ba394513f8420e");
    public static ServerInfo LOCAL = new ServerInfo("http://127.0.0.1:8080/webx/postUserAction", "8ba394513f8420e");

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public static ServerInfo getConfigServerInfo() {
        return LOCAL;   // 调试的时候使用
    }
}
