package com.qunar.study.algorithm;

import com.qunar.study.utils.MathUtil;

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

    /**
     * KMP算法,好前缀移动,主要是
     * @param main 主串
     * @param mode 模式串
     * @return 字符串首次出现的位置
     */
    public static int indexKMP(String main, String mode) {
        int[] next = new int[mode.length()];
        generateNext(mode, next);
        int j = 0;
        for (int i = 0; i < main.length(); i++) {
            while (j > 0 && main.charAt(i) != mode.charAt(j)) {//移动j的过程
                j = next[j - 1] + 1;//这样移动节省了前面相同的比较次数
            }
            if (main.charAt(i) == mode.charAt(j)) {
                j++;
            }
            if (j == mode.length()) {
                return i - mode.length() + 1;
            }
        }
        return -1;
    }

    /**
     * 利用next之前生成的值进行判断
     * i之前的next数组都是求出来的,利用next[i]来求解next[i+1]的值
     * @param mode
     * @param next
     */
    private static void generateNext(String mode, int[] next) {
        Arrays.fill(next, -1);
        int k = -1;//每次循环的K值必然是和上次的k值相关，从上次的K值开始查找，直到找到一个
        for (int i = 1; i < mode.length(); i++) {
            while (k != -1 && mode.charAt(k + 1) != mode.charAt(i)) {
                k = next[k];
            }
            if (mode.charAt(k + 1) == mode.charAt(i)) {
                k++;
            }
            next[i] = k;
        }
    }

    /**
     * 莱温斯坦距离,支持增加,删除,替换操作,回溯算法
     * 该距离越小越能表示两个字符串相似度越高
     * 为什么可以支持替换操作:因为这是两者距离的计算
     * @param main
     * @param mode
     * @return
     */
    public static int lewinsteinDistanceBT(String main, String mode, int i, int j, int distance) {
        if (i == main.length() || j == mode.length()) {
            if (i < main.length()) {
                return distance + main.length() - i;
            }
            if (j < mode.length()) {
                return distance + mode.length() - j;
            }
            return distance;
        }
        int d = Integer.MAX_VALUE;//取最小值
        if (main.charAt(i) == mode.charAt(j)) {
            d = Math.min(lewinsteinDistanceBT(main, mode, i + 1, j + 1, distance), d);
        } else {
            //删除i位置或者在j位置前添加和i相同添加
            d = Math.min(lewinsteinDistanceBT(main, mode, i + 1, j, distance + 1), d);
            //和上线相反
            d = Math.min(lewinsteinDistanceBT(main, mode, i, j + 1, distance + 1), d);
            //
            d = Math.min(lewinsteinDistanceBT(main, mode, i + 1, j + 1, distance + 1), d);
        }
        return d;
    }

    /**
     * 莱温斯坦距离-动态规划解决方法
     *
     * @param main
     * @param mode
     * @return
     */
    public static int lewinsteinDistanceDp(String main, String mode) {
        int l1 = main.length(), l2 = mode.length();
        int[][] dp = new int[l1][l2];
        if (main.charAt(0) == mode.charAt(0)) {
            dp[0][0] = 0;
        } else {
            dp[0][0] = 1;
        }
        //生成第一行数据
        for (int i = 1; i < l2; i++) {
            if (main.charAt(0) == mode.charAt(i)) {
                dp[0][i] = dp[0][i - 1];
            } else {
                dp[0][i] = dp[0][i - 1] + 1;
            }
        }
        //生成第一列数据
        for (int i = 1; i < l1; i++) {
            if (main.charAt(i) == mode.charAt(0)) {
                dp[i][0] = dp[i - 1][0];
            } else {
                dp[i][0] = dp[i - 1][0] + 1;
            }
        }
        //生成一般矩阵
        for (int i = 1; i < l1; i++) {
            for (int j = 1; j < l2; j++) {
                if (main.charAt(i) == mode.charAt(j)) {
                    dp[i][j] = MathUtil.min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);
                } else {
                    dp[i][j] = MathUtil.min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[l1 - 1][l2 - 1];
    }

    /**
     * 最长公共子串
     * 只允许增加和删除操作,不支持替换
     *
     * @param main
     * @param mode
     * @return
     */
    public static int lcsDp(String main, String mode) {
        int m = main.length(), n = mode.length();
        int[][] dp = new int[m][n];
        if (main.charAt(0) == mode.charAt(0)) {
            dp[0][0] = 1;
        } else {
            dp[0][0] = 0;
        }
        //生成第一行数据
        for (int i = 1; i < n; i++) {
            if (main.charAt(0) == mode.charAt(i)) {
                dp[0][i] = 1;
            } else {
                dp[0][i] = dp[0][i - 1];
            }
        }
        //生成第一列数据
        for (int i = 1; i < m; i++) {
            if (main.charAt(i) == mode.charAt(0)) {
                dp[i][0] = 1;
            } else {
                dp[i][0] = dp[i - 1][0];
            }
        }
        //生成通用数据
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (main.charAt(i) == mode.charAt(j)) {
                    dp[i][j] = MathUtil.max(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1] + 1);
                } else {
                    dp[i][j] = MathUtil.max(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}
