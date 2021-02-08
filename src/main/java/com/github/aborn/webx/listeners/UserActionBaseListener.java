package com.github.aborn.webx.listeners;


import com.intellij.openapi.diagnostic.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author aborn
 * @date 2021/02/08 3:36 PM
 */
public class UserActionBaseListener {

    protected static final Logger LOG = Logger.getInstance(UserActionBaseListener.class);

    protected void info(String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info(simpleDateFormat.format(new Date()) + " : " + message);
    }
}
