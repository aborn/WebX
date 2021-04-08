package com.github.aborn.webx.modules.tc.transfer;

import java.io.Serializable;

/**
 * @author aborn
 * @date 2021/03/20 6:03 AM
 */
public class UserEntity extends SenderEntity implements Serializable {
    private String token;
    private String id;
    private Integer actiontype;

    public UserEntity(String token, String id) {
        this.token = token;
        this.id = id;
        // WebX Validate actiontype Code as 101
        this.actiontype = 101;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getActiontype() {
        return actiontype;
    }

    public void setActiontype(Integer actiontype) {
        this.actiontype = actiontype;
    }
}
