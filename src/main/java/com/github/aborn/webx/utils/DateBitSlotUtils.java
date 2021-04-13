package com.github.aborn.webx.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author aborn
 * @date 2021/02/23 4:12 PM
 */
public class DateBitSlotUtils {
    private DateBitSlotUtils() {}

    public static final int SLOT_SIZE = 24 * 60 * 2;
    public static final int MAX_SLOT_INDEX = SLOT_SIZE - 1;

    public static int getSlotIndex(Date date) {
        if (date == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        return hours * 60 * 2 + minutes * 2 + (seconds / 30);
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date + ", slot=" + getSlotIndex(date));
    }
}
