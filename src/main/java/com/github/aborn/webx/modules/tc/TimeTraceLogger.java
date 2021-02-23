package com.github.aborn.webx.modules.tc;

import com.intellij.openapi.diagnostic.Logger;
import org.apache.log4j.Level;

/**
 * @author aborn
 * @date 2021/02/12 11:26 PM
 */
public class TimeTraceLogger {
    public TimeTraceLogger() {
    }

    protected static final Logger LOG = Logger.getInstance(TimeTraceLogger.class);

    public static void info(String message) {
        LOG.info(" : #WebX : " + message);
    }

    public static void setLevel(Level level) {
        LOG.setLevel(level);
    }
}
