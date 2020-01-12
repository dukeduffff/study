package com.qunar.study.algorithm;

/**
 * Created by dujian on 2020/01/12
 * 搜索算法
 */
public class StringUtil {
    /**
     * Brute Force暴力算法
     *
     * @param main
     * @param mode
     * @return
     */
    public static int indexBF(String main, String mode) {
        if (main == null || main.length() == 0) {
            return -1;
        }
        if (mode == null || mode.length() == 0) {
            return -1;
        }
        for (int i = 0; i <= main.length() - mode.length(); i++) {
            for (int j = 0; j < mode.length(); j++) {
                if (main.charAt(i + j) != mode.charAt(j)) {
                    break;
                }
                if (j == mode.length() - 1) {//找到之后返回即可
                    return i;
                }
            }
        }
        return -1;
    }
}
