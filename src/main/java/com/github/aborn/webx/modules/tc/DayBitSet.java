package com.github.aborn.webx.modules.tc;

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
    public static final int SLOT_SIZE = 24 * 60 * 2;
    private static final int MAX_SLOT_INDEX = SLOT_SIZE - 1;

    BitSet codingBitSet = new BitSet(SLOT_SIZE);

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
     * @param slot
     */
    public void set(int slot) {
        this.codingBitSet.set(slot);
    }

    public int setSlotByCurrentTime() {
        return this.setSlotByTime(new Date());
    }

    public int setSlotByTime(Date date) {
        int slotIndex = getSlotIndex(date);
        this.set(slotIndex);
        return slotIndex;
    }

    public byte[] getDayBitSetByteArray() {
        return this.codingBitSet.toByteArray();
    }

    public int getSlotIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int index = hours * 60 * 2 + minutes * 2 + (seconds / 30);
//        if (index > MAX_SLOT_INDEX) {
//            throw new Exception("Out of range exception.");
//        }

        return index;
    }

    // test
    private int countOfCodingSlot2() {
        int result = 0;
        for (int i = codingBitSet.nextSetBit(0); i >= 0; i = codingBitSet.nextSetBit(i + 1)) {
            result++;
            if (i == Integer.MAX_VALUE) {
                break; // or (i+1) would overflow
            }
        }
        return result;
    }

    public int countOfCodingSlot() {
        return codingBitSet.cardinality();
    }

    /**
     * 这一天的编码时间 (单位S)
     *
     * @return
     */
    public int codingTimeSeconds() {
        return countOfCodingSlot() * 30;
    }

    /**
     * 这一天的写代码时间（单位分）
     *
     * @return
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
}
