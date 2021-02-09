package com.github.aborn.webx.modules.tc;

import com.intellij.openapi.diagnostic.Logger;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author aborn
 * @date 2021/02/09 10:46 AM
 */
public class TimeTrace {
    protected static final Logger LOG = Logger.getInstance(TimeTrace.class);

    public static Boolean READY = false;
    // 定时上报任务
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> scheduledFixture;

    private final int queueTimeoutSeconds = 30;
    private static ConcurrentLinkedQueue<ActionPoint> heartbeatsQueue = new ConcurrentLinkedQueue<ActionPoint>();

    public void init() {
        setupQueueProcessor();
    }

    private void setupQueueProcessor() {
        final Runnable handler = new Runnable() {
            public void run() {
                processHeartbeatQueue();
            }
        };
        long delay = queueTimeoutSeconds;
        scheduledFixture = scheduler.scheduleAtFixedRate(handler, delay, delay, java.util.concurrent.TimeUnit.SECONDS);
    }

    private static void processHeartbeatQueue() {
        if (TimeTrace.READY) {

            // get single heartbeat from queue
            ActionPoint heartbeat = heartbeatsQueue.poll();
            if (heartbeat == null) {
                return;
            }

            // get all extra heartbeats from queue
            ArrayList<ActionPoint> actionPoints = new ArrayList<ActionPoint>();
            actionPoints.add(heartbeat);
            while (true) {
                ActionPoint h = heartbeatsQueue.poll();
                if (h == null)
                    break;
                actionPoints.add(h);
            }

            sendTraceActions(actionPoints);
        }
    }

    private static void sendTraceActions(final ArrayList<ActionPoint> extraHeartbeats) {
        // 处理发送消息
        LOG.info("send message.");
    }
}
