package com.github.aborn.webx.modules.tc.transfer;

import com.github.aborn.webx.modules.tc.DayBitSet;

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

    public static void main(String[] args) {
        UserActionEntity userActionEntity = new UserActionEntity();
        userActionEntity.setToken("123");
        DayBitSet dayBitSet = new DayBitSet();
        dayBitSet.set(1);
        userActionEntity.setDayBitSetArray(dayBitSet.getDayBitSetByteArray());
        userActionEntity.setDay("2021-08-11");
        String json = userActionEntity.toJson();
        System.out.println(json);
    }
}
