package com.github.aborn.webx.modules.tc.transfer;

import java.io.Serializable;

/**
 * @author aborn
 * @date 2021/03/20 6:38 AM
 */
public class BaseResponse implements Serializable {
    // 状态
    private boolean status;

    // 消息
    private String msg;

    // 状态码
    private int code;

    private String data;

    public BaseResponse(boolean status, String msg, int code) {
        this.status = status;
        this.msg = msg;
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
