package com.github.aborn.webx.modules.tc;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;

/**
 * 将一天分为 24*60*2 = 2880 个slot，每个slot代表30S，slot=true表示编程时间
 * @author aborn
 * @date 2021/02/10 10:53 AM
 */
public class DayBitSet implements Serializable {

    BitSet codingBitSet = new BitSet(2880);

    /**
     *  一年中的天，格式为 yyyy-MM-dd
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
     * @param slot
     */
    public void set(int slot) {
        this.codingBitSet.set(slot);
    }

    public int countOfCodingSlot2() {
        int result = 0;
        for (int i = codingBitSet.nextSetBit(0); i >= 0; i = codingBitSet.nextSetBit(i+1)) {
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
     * @return
     */
    public int codingTimeSeconds() {
        return countOfCodingSlot() * 30;
    }

    /**
     * 这一天的写代码时间（单位分）
     * @return
     */
    public double codingTimeMinutes() {
        return codingTimeSeconds() / 60.0;
    }

    @Override
    public String toString() {
        return "Day:"+ day + ", Coding Time:" + codingTimeSeconds() + "s(" + codingTimeMinutes() + "m).";
    }

    public static void main(String[] args) {
        DayBitSet dayBitSet = new DayBitSet();
        dayBitSet.set(1);
        System.out.println(dayBitSet.toString());
    }
}
