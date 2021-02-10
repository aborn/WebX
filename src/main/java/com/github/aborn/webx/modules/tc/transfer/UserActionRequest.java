package com.github.aborn.webx.modules.tc.transfer;

import com.github.aborn.webx.modules.tc.DayBitSet;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author aborn
 * @date 2021/02/10 12:14 PM
 */
public class UserActionRequest implements Serializable {
    private String token;
    private DayBitSet dayBitSet;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DayBitSet getDayBitSet() {
        return dayBitSet;
    }

    public void setDayBitSet(DayBitSet dayBitSet) {
        this.dayBitSet = dayBitSet;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
