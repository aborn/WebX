package com.github.aborn.webx.modules.tc;

import com.github.aborn.webx.modules.tc.transfer.DataSenderHelper;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author aborn
 * @date 2021/02/09 10:46 AM
 */
public class TimeTrace implements Disposable {
    protected static final Logger LOG = Logger.getInstance(TimeTrace.class);

    public TimeTrace() {
        init();
    }

    private static DayBitSet currentDayBitSet = new DayBitSet();

    private static Boolean READY = false;
    // 定时上报任务
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> scheduledFixture;
    // 最小的频率
    public static final BigDecimal FREQUENCY = new BigDecimal(2 * 60);
    public static BigDecimal lastTime = new BigDecimal(0);

    // 每30秒执行一次
    private final int queueTimeoutSeconds = 30;
    private static ConcurrentLinkedQueue<ActionPoint> actionQueues = new ConcurrentLinkedQueue<ActionPoint>();

    public void init() {
        if (!READY) {
            READY = true;
            setupQueueProcessor();
            LOG.info("TimeTrace init finished.");
        }
    }

    /**
     * 当前这30作为coding time 来记录
     */
    public static void record() {
        if (!currentDayBitSet.isToday()) {
            currentDayBitSet = new DayBitSet();
        }
        currentDayBitSet.setSlotByCurrentTime();
    }

    public static void appendActionPoint(final VirtualFile file, Project project, final boolean isWrite) {
        TimeTrace service = ServiceManager.getService(TimeTrace.class);
        service.init();

        final BigDecimal currentTimestamp = getCurrentTimestamp();
        if (!isWrite && !enoughTimePassed(currentTimestamp)) {
            return;
        }

        final String language = getLanguage(file);
        ActionPoint actionPoint = new ActionPoint();
        final String projectName = project != null ? project.getName() : null;

        actionPoint.setEntity(file.getPath());
        actionPoint.setLanguage(language);
        actionPoint.setTimestamp(currentTimestamp);
        actionPoint.setWrite(isWrite);
        actionPoint.setProject(projectName);

        lastTime = currentTimestamp;
        LOG.info("appendActionPoint.");
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            actionQueues.add(actionPoint);
        });
    }

    public static boolean enoughTimePassed(BigDecimal currentTime) {
        return TimeTrace.lastTime.add(FREQUENCY).compareTo(currentTime) < 0;
    }

    private void setupQueueProcessor() {
        final Runnable handler = () -> processActionsQueue();
        long delay = queueTimeoutSeconds;
        scheduledFixture = scheduler.scheduleAtFixedRate(handler, delay, delay, java.util.concurrent.TimeUnit.SECONDS);
    }

    private static void processActionsQueue() {
        if (TimeTrace.READY) {

            ActionPoint actionPoint = actionQueues.poll();
            if (actionPoint == null) {
                return;
            }

            ArrayList<ActionPoint> actionPoints = new ArrayList<>();
            actionPoints.add(actionPoint);
            while (true) {
                ActionPoint h = actionQueues.poll();
                if (h == null) {
                    break;
                }
                actionPoints.add(h);
            }

            sendTraceActions(actionPoints);
        }
    }

    private static void sendTraceActions(final ArrayList<ActionPoint> extraHeartbeats) {
        // 处理发送消息
        LOG.info("send message. size = " + extraHeartbeats.size());
        DataSenderHelper.postData(currentDayBitSet);
    }

    public static BigDecimal getCurrentTimestamp() {
        return new BigDecimal(String.valueOf(System.currentTimeMillis() / 1000.0)).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    private static String getLanguage(final VirtualFile file) {
        FileType type = file.getFileType();
        return type.getName();
    }

    @Override
    public void dispose() {
        try {
            scheduledFixture.cancel(true);
        } catch (Exception e) { }

        // make sure to send all heartbeats before exiting
        processActionsQueue();
    }
}
