package com.github.aborn.webx.modules.tc;

import com.github.aborn.webx.modules.tc.transfer.DataSenderHelper;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author aborn
 * @date 2021/02/09 10:46 AM
 */
public class TimeTrace implements Disposable {
    /**
     * 默认为false，调试的时候使用true
     */
    public static Boolean DEBUG = false;

    public TimeTrace() {
        init();
    }

    private static DayBitSet currentDayBitSet = new DayBitSet();

    private static Boolean READY = false;

    /**
     * 定时上报任务
     */
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static ScheduledFuture<?> scheduledFixture;

    /**
     * 最小的频率：2分钟
     */
    public static final BigDecimal FREQUENCY = new BigDecimal(2 * 60);
    public static BigDecimal lastTime = new BigDecimal(0);

    /**
     * 每30秒执行一次
     */
    private final int queueTimeoutSeconds = 30;
    private static ConcurrentLinkedQueue<ActionPoint> actionQueues = new ConcurrentLinkedQueue<ActionPoint>();

    public void init() {
        if (!READY) {
            READY = true;
            setLoggingLevel();
            setupQueueProcessor();
            TimeTraceLogger.info("TimeTrace init finished.");
        }
    }

    /**
     * 当前这30作为coding time 来记录
     */
    public static void record() {
        TimeTrace timeTrace = ServiceManager.getService(TimeTrace.class);
        timeTrace.init();

        currentDayBitSet.clearIfNotToday();
        int slot = currentDayBitSet.setSlotByCurrentTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int index = hours * 60 * 2;

        // 打点记录一下
        TimeTraceLogger.info(", slot:" + slot + ", hour_slot:" + (slot - index));
    }

    public static void appendActionPoint(@NotNull final VirtualFile file, Project project, final boolean isWrite) {
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
        TimeTraceLogger.info("appendActionPoint.");
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

    /**
     * 无论IDEA是否打开，每30s上报一次。
     */
    private static void processActionsQueue() {
        if (TimeTrace.READY) {

            String message = DataSenderHelper.postData(currentDayBitSet);
            TimeTraceLogger.info("POST DATA:" + message);
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
        TimeTraceLogger.info("send message. size = " + extraHeartbeats.size());
    }

    public static BigDecimal getCurrentTimestamp() {
        return new BigDecimal(String.valueOf(System.currentTimeMillis() / 1000.0)).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    private static String getLanguage(final VirtualFile file) {
        FileType type = file.getFileType();
        return type.getName();
    }

    public static void setLoggingLevel() {
        if (TimeTrace.DEBUG) {
            TimeTraceLogger.setLevel(Level.DEBUG);
            TimeTraceLogger.debug("Logging level set to DEBUG");
        } else {
            TimeTraceLogger.setLevel(Level.INFO);
        }
    }

    @Override
    public void dispose() {
        try {
            scheduledFixture.cancel(true);
        } catch (Exception e) {
        }

        // make sure to send all heartbeats before exiting
        processActionsQueue();
    }
}
