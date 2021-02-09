package com.github.aborn.webx.modules.tc;

import java.math.BigDecimal;

/**
 * 用户交互点
 * @author aborn
 * @date 2021/02/09 10:43 AM
 */
public class ActionPoint {
    String entity;
    BigDecimal timestamp;
    Boolean isWrite;
    String project;
    String language;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public BigDecimal getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(BigDecimal timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getWrite() {
        return isWrite;
    }

    public void setWrite(Boolean write) {
        isWrite = write;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
