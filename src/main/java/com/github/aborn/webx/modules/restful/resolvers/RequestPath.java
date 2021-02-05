package com.github.aborn.webx.modules.restful.resolvers;

/**
 * @author aborn
 * @date 2021/02/05 11:28 AM
 */
public class RequestPath {

    String path;
    String method;

    public RequestPath(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
