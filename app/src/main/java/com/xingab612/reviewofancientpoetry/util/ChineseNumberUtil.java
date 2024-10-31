package com.xingab612.reviewofancientpoetry.util;

import java.math.BigDecimal;

public class ChineseNumberUtil {
    private ChineseNumberUtil() {
    }

    private static final String[] NUMBERS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] TENS = {"", "十", "百", "千"};
    private static final String[] UNIT = {"", "万", "亿", "兆", "京", "垓", "秭", "穰", "沟", "涧", "正", "载", "极"};


    public static String convert(String number) {
        return convert(new BigDecimal(number));
    }
    public static String convert(BigDecimal number) {
        String s = number.toString();
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return convertInteger(split[0]);
        }
        return convertInteger(split[0]) + "点" + convertFloat(split[1]);
    }
    public static String convert(long number) {
        return convert(BigDecimal.valueOf(number));
    }
    public static String convert(double number) {
        return convert(BigDecimal.valueOf(number));
    }

    private static String convertInteger(String number) {
        if (number.equals("0")) {
            return NUMBERS[0];
        }

        StringBuilder result = new StringBuilder();

        String[] split = split(number);
        for (int i = split.length - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder();
            boolean needZero = false;
            for (int j = split[i].length() - 1,k = 0; j >= 0; j--, k++) {
                char digit = split[i].charAt(j);
                if (digit == '0') {
                    if (!needZero) continue;
                } else {
                    needZero = true;
                    sb.insert(0, TENS[k]);
                }
                sb.insert(0, NUMBERS[Integer.parseInt(String.valueOf(digit))]);
            }
            result.append(sb.toString().replace("一十", "十")).append(UNIT[i]);
        }

        return result.toString();
    }
    private static String convertFloat(String number) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            result.append(NUMBERS[Integer.parseInt(String.valueOf(number.charAt(i)))]);
        }
        return result.toString();
    }


    /**
     * 将字符串数字按四位一组进行分割，并返回分割后的字符串数组
     *
     * @param number 待分割的字符串数字
     * @return 分割后的字符串数组，每个元素最多包含4位数字
     * @throws IllegalArgumentException 如果输入的数字长度大于 UNIT.length * 4，则抛出此异常
     */
    private static String[] split(String number) {
        int length = number.length();
        if (length > UNIT.length * 4) { // 注意这里是 UNIT.length * 4，因为每4位数字对应一个单位
            throw new IllegalArgumentException("数字太大");
        }

        int arrLen = (length + 3) / 4; // 使用 (length + 3) / 4 确保能正确分配数组大小

        String[] result = new String[arrLen];
        for (int i = 0; i < arrLen; i++) {
            int start = length - 4 * (i + 1);
            int end = length - 4 * i;
            // 防止 start 变成负数
            if (start < 0) {
                start = 0;
            }
            result[i] = number.substring(start, end);
        }

        return result;
    }
}

