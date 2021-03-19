package com.github.aborn.webx.modules.tc.transfer;

/**
 * @author aborn
 * @date 2021/02/15 10:40 PM
 */
public class SenderResponse {
    public SenderResponse() {}

    String message;
    Boolean status;
    int code;

    public SenderResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
