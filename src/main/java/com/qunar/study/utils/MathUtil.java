package com.qunar.study.utils;

/**
 * Created by dujian on 2020/02/02
 */
public class MathUtil {
    public static int min(int num1, int num2, int... array) {
        int min = Math.min(num1, num2);
        if (array == null || array.length == 0) {
            return min;
        }
        for (int i = 0; i < array.length; i++) {
            min = Math.min(min, array[i]);
        }
        return min;
    }

    public static int max(int num1, int num2, int... array) {
        int min = Math.max(num1, num2);
        if (array == null || array.length == 0) {
            return min;
        }
        for (int i = 0; i < array.length; i++) {
            min = Math.max(min, array[i]);
        }
        return min;
    }
}
