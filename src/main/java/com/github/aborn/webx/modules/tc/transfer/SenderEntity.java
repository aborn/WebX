package com.github.aborn.webx.modules.tc.transfer;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author aborn
 * @date 2021/03/20 6:06 AM
 */
public class SenderEntity implements Serializable {

    public String toJson() {
        return new Gson().toJson(this);
    }
}
