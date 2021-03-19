package com.github.aborn.webx.modules.tc.transfer;

import java.io.Serializable;

/**
 * @author aborn
 * @date 2021/03/20 6:03 AM
 */
public class UserEntity extends SenderEntity implements Serializable {
    private String token;
    private String id;

    public UserEntity(String token, String id) {
        this.token = token;
        this.id = id;
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
}
