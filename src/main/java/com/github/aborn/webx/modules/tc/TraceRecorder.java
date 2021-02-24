package com.github.aborn.webx.modules.tc;

import com.github.aborn.webx.utils.DateBitSlotUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于追踪
 * @author aborn
 * @date 2021/02/23 11:09 AM
 */
public class TraceRecorder {
    public TraceRecorder() {}

    private static boolean opended = false;
    private static Date openTime = null;
    private static Date closeTime = null;

    public static int getOpenedSlot() {
        if (openTime == null) { return -1; }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDay = simpleDateFormat.format(new Date());

        // ignore if not today.
        if (!simpleDateFormat.format(openTime).equals(currentDay)) {
            return -1;
        }

        return DateBitSlotUtils.getSlotIndex(openTime);
    }

    public static boolean isOpended() {
        return opended;
    }

    public static void open() {
        if (opended) {
            if (openTime == null) {
                openTime = new Date();
            }
        } else {
            opended = true;
            // 最近的openTime
            openTime = new Date();
        }
    }

    public static void close() {
        if (opended) {
            opended = false;
            closeTime = new Date();
        } else {
            if (closeTime == null) {
                closeTime = new Date();
            }
        }
    }
}
