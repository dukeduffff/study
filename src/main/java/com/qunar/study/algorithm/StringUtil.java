package com.qunar.study.algorithm;

import java.util.Arrays;

/**
 * Created by dujian on 2020/01/12
 * 搜索算法
 */
public class StringUtil {
    /**
     * Brute Force暴力算法
     *
     * @param main 主串
     * @param mode 模式串
     * @return 第一次出现的位置
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
     * 利用哈希值进行计算,RK算法
     * @param main 主串
     * @param mode 模式串
     * @return 第一次出现的位置
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

    /**
     * BM算法，坏字符和好后缀原则进行移动
     * 坏字符:最后一个字符
     * 好前缀原则:1.模式串中和好后缀匹配的领一个子串;2.模式串中和好后缀匹配的前缀子串
     * @param main 主串
     * @param mode 模式串
     * @return 第一次出现的位置
     */
    public static int indexBM(String main, String mode) {
        int[] bc = new int[256];
        int[] suffix = new int[mode.length()];
        boolean[] prefix = new boolean[mode.length()];
        generateBC(mode, bc);//生成各个字符最后出现的位置
        generateGS(mode, suffix, prefix);
        int mainLength = main.length();
        int modeLength = mode.length();
        int i = 0;//main上的索引

        while (i <= mainLength - modeLength) {
            int j;
            int slen = 0;//好后缀的长度
            for (j = modeLength - 1; j >= 0; j--) {
                if (mode.charAt(j) != main.charAt(i + j)) {//坏字符出现的位置
                    break;
                }
                slen++;
            }
            if (j < 0) {//已经找到
                return i;
            }
            int badCharMove = j - bc[main.charAt(i + j)];//移动步数
            int goodSuffixIndex = -1;
            if (slen > 0) {
                if (suffix[slen] != -1) {
                    goodSuffixIndex = j - suffix[slen] + 1;//当前j的位置指向不匹配字符
                } else {
                    for (int k = slen; k > 0; k--) {
                        if (prefix[k]) {
                            goodSuffixIndex = mode.length() - k;
                            break;
                        }
                    }
                }
            }
            i = i + Math.max(badCharMove, goodSuffixIndex);//坏字符移动的位置
        }
        return -1;
    }

    private static void generateBC(String mode, int[] bc) {//记录mode各个字符出现的位置
        Arrays.fill(bc, -1);
        for (int i = 0; i < mode.length(); i++) {//每个字符最后出现的位置
            bc[mode.charAt(i)] = i;
        }
    }

    private static void generateGS(String mode, int[] suffix, boolean[] prefix) {
        //初始化suffix和prefix数组
        Arrays.fill(suffix, -1);
        Arrays.fill(prefix, false);
        for (int i = 0; i < mode.length() - 1; i++) {//倒数第二个字符为止
            int j = i, k = 0;
            while (j >= 0 && mode.charAt(j) == mode.charAt(mode.length() - k - 1)) {
                k++;//统计的长度
                suffix[k] = j;
                j--;
            }
            if (j < 0) {
                prefix[k] = true;
            }
        }
    }
}
