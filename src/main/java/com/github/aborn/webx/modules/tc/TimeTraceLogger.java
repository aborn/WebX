package com.github.aborn.webx.modules.tc;

import com.intellij.openapi.diagnostic.Logger;
import org.apache.log4j.Level;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author aborn
 * @date 2021/02/12 11:26 PM
 */
public class TimeTraceLogger {
    public TimeTraceLogger() {
    }

    protected static final Logger LOG = Logger.getInstance(TimeTraceLogger.class);

    public static void info(String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mes = simpleDateFormat.format(new Date()) + " : #WebX : " + message;
        LOG.info(message);
        System.out.println(mes);
    }

    public static void debug(String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mes = simpleDateFormat.format(new Date()) + " : #WebX : " + message;
        LOG.debug(message);
        System.out.println(mes);
    }

    public static void setLevel(Level level) {
        LOG.setLevel(level);
    }
}
