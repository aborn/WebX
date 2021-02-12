package com.github.aborn.webx.modules.tc;

import com.intellij.openapi.diagnostic.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: guobao.jiang
 * Email : guobao.jiang@dianping.com
 * Date  : 02-12-2021
 * Time  : 11:26 PM
 */
public class TimeTraceLogger {
    protected static final Logger LOG = Logger.getInstance(TimeTraceLogger.class);

    public static void info(String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mes = simpleDateFormat.format(new Date()) + " : " + message;
        LOG.info(mes);
        System.out.println(mes);
    }
}
