package com.github.aborn.webx.modules.tc.transfer;

import java.io.Serializable;

/**
 * @author aborn
 * @date 2021/02/10 12:14 PM
 */
public class UserActionEntity extends SenderEntity implements Serializable {
    private String token;
    private byte[] dayBitSetArray;
    // 具体的天，格式：yyyy-MM-dd
    private String day;

    public String getToken() {
        return token;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public byte[] getDayBitSetArray() {
        return dayBitSetArray;
    }

    public void setDayBitSetArray(byte[] dayBitSetArray) {
        this.dayBitSetArray = dayBitSetArray;
    }
}
