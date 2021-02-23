package com.github.aborn.webx.modules.tc;

import com.github.aborn.webx.utils.DateBitSlotUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;

/**
 * 将一天分为 24*60*2 = 2880 个slot，每个slot代表30S，slot=true表示编程时间
 *
 * @author aborn
 * @date 2021/02/10 10:53 AM
 */
public class DayBitSet implements Serializable {

    BitSet codingBitSet = new BitSet(DateBitSlotUtils.SLOT_SIZE);

    /**
     * 一年中的天，格式为 yyyy-MM-dd
     */
    String day;

    public DayBitSet() {
        this(new Date());
    }

    public DayBitSet(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        day = simpleDateFormat.format(date);
    }

    /**
     * slot range [0,2880-1]
     *
     * @param slot 要设置的slot
     */
    public void set(int slot) {
        this.codingBitSet.set(slot);
    }

    public boolean get(int slot) {
        return this.codingBitSet.get(slot);
    }

    public int setSlotByCurrentTime() {
        return this.setSlotByTime(new Date());
    }

    public int setSlotByTime(Date date) {
        int slotIndex = DateBitSlotUtils.getSlotIndex(date);
        this.set(slotIndex);
        return slotIndex;
    }

    public byte[] getDayBitSetByteArray() {
        return this.codingBitSet.toByteArray();
    }

    public int countOfCodingSlot() {
        return codingBitSet.cardinality();
    }

    /**
     * @return 这一天的编码时间 (单位S)
     */
    public int codingTimeSeconds() {
        return countOfCodingSlot() * 30;
    }

    /**
     * @return 这一天的写代码时间（单位分钟）
     */
    public double codingTimeMinutes() {
        return codingTimeSeconds() / 60.0;
    }

    @Override
    public String toString() {
        return "Day:" + day + ", Coding Time:" + codingTimeSeconds() + "s(" + codingTimeMinutes() + "m).";
    }

    public static void main(String[] args) {
        DayBitSet dayBitSet = new DayBitSet();
        dayBitSet.set(1);
        System.out.println(dayBitSet.toString());
    }

    public String getDay() {
        return day;
    }

    public boolean isToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date()).equals(day);
    }

    public void clearIfNotToday() {
        if (isToday()) {
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.day = simpleDateFormat.format(new Date());
        this.codingBitSet.clear();
    }

    public BitSet getCodingBitSet() {
        return codingBitSet;
    }

    public String getCurrentHourSlotInfo() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return getHourSlotInfo(calendar.get(Calendar.HOUR_OF_DAY));
    }

    /**
     * 获取小时内的slot打印信息
     *
     * @param hour [0~23]
     * @return 小时的slot标记信息
     */
    public String getHourSlotInfo(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar calendarSet = Calendar.getInstance();
        calendarSet.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), hour, 0, 0);
        int index = calendarSet.get(Calendar.HOUR_OF_DAY) * 60 * 2;
        StringBuilder result = new StringBuilder(hour + "");
        result.append("[").append(index).append("+]:");

        int slotHourIndex = 0;
        for (int i = index; i < (index + 60 * 2); i++) {
            if (get(i)) {
                result.append(slotHourIndex).append(", ");
            }
            slotHourIndex++;
        }

        return result.toString();
    }
}
