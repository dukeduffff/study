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

    /**
     * 利用哈希值进行计算
     * @param main
     * @param mode
     * @return
     */
    public static int indexRK(String main, String mode) {
        if (main == null || main.length() == 0) {
            return -1;
        }
        if (mode == null || mode.length() == 0) {
            return -1;
        }
        int modeHashCode = 0;
        //计算mode哈希值
        for (int i = 0; i < mode.length(); i++) {
            modeHashCode = modeHashCode * 26 + (mode.charAt(i) - 'a');//计算模式串的哈希值
        }
        int powH = pow(26, mode.length() - 1);
        int removeChar = 0;
        int prevHashCode = 0;//前半部分的哈希值
        for (int i = 0; i < main.length(); i++) {
            if (i < mode.length() - 1) {//只是单纯的计算
                prevHashCode = prevHashCode * 26 + (main.charAt(i) - 'a');
                continue;
            }
            prevHashCode = (prevHashCode - removeChar * powH) * 26 + (main.charAt(i) - 'a');
            int firstChar = i - mode.length() + 1;
            removeChar = main.charAt(firstChar) - 'a';
            if (prevHashCode == modeHashCode) {
                for (int j = 0; j < mode.length(); j++) {
                    if (mode.charAt(j) != main.charAt(firstChar + j)) {
                        break;
                    }
                    if (j == mode.length() - 1) {
                        return firstChar;
                    }
                }
            }
        }
        return -1;
    }

    private static int pow(int num, int count) {
        int result = 1;
        for (int i = 0; i < count; i++) {
            result *= num;
        }
        return result;
    }
}
