package com.qunar.study.algorithm;

import com.qunar.study.utils.MathUtil;

import java.util.Arrays;

/**
 * Created by dujian on 2020/01/21
 * 动态规划算法
 */
public class DynamicProgramming {
    /**
     * 0-1背包问题
     * @param goods 物品
     * @param weight
     * @return
     */
    public int maxBag(int[] goods, int weight) {
        boolean[][] state = new boolean[goods.length][weight + 1];//状态转移矩阵
        state[0][0] = true;
        if (goods[0] <= weight) {
            state[0][goods[0]] = true;
        }
        for (int i = 1; i < goods.length; i++) {
            //1.不把第i个商品放入背包
            for (int j = 0; j <= weight; j++) {//如果不放入第i个物品
                if (state[i - 1][j]) {
                    state[i][j] = true;
                }
            }
            for (int j = 0; j <= weight - goods[i]; j++) {
                if (state[i - 1][j]) {
                    state[i][j + goods[i]] = true;
                }
            }
        }
        for (int i = weight; i > 0; i--) {
            if (state[goods.length - 1][i]) {
                return i;
            }
        }
        return -1;
    }

    public int maxBagOp(int[] goods, int weight) {
        boolean[] state = new boolean[weight + 1];//状态转移矩阵
        state[0] = true;
        if (goods[0] <= weight) {
            state[goods[0]] = true;
        }
        for (int i = 1; i < goods.length; i++) {
            //之前的状态已经包括了不放i的情况，现在统计放i的情况
            for (int j = weight - goods[i]; j >= 0; j--) {
                if (state[j]) {
                    state[j + goods[i]] = true;
                }
            }
        }
        for (int i = weight; i >= 0; i--) {
            if (state[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 0-1背包问题升级版,最大价值
     * @param goodsWeight
     * @param goodsValue
     * @param maxWeight
     * @return
     */
    public int maxBagValue(int[] goodsWeight, int[] goodsValue, int maxWeight) {
        int[] state = new int[maxWeight + 1];//状态转移矩阵
        Arrays.fill(state, -1);
        state[0] = 0;
        if (goodsWeight[0] <= maxWeight) {
            state[goodsWeight[0]] = goodsValue[0];
        }
        for (int i = 1; i < goodsWeight.length; i++) {
            //之前的状态已经包括了不放i的情况，现在统计放i的情况
            for (int j = maxWeight - goodsWeight[i]; j >= 0; j--) {
                if (state[j] >= 0) {
                    int v = state[j] + goodsValue[i];
                    if (v > state[j + goodsWeight[i]]) {
                        state[j + goodsWeight[i]] = v;
                    }
                }
            }
        }
        for (int i = maxWeight; i >= 0; i--) {
            if (state[i] >= 0) {
                return state[i];
            }
        }
        return -1;
    }

    /**
     * 动态规则求解最短路径
     * @param array
     * @return
     */
    public int shortestPath(int[][] array) {
        int[] state = new int[array.length];
        state[0] = array[0][0];
        //初始化
        for (int i = 1; i < array.length; i++) {
            state[i] = state[i - 1] + array[0][i];
        }
        //动态规划
        for (int i = 1; i < array.length; i++) {
            state[0] = array[i][0] + state[0];//先计算第一个位置的值
            for (int j = 1; j < array.length; j++) {
                int up = array[i][j] + state[j];
                int left = array[i][j] + state[j - 1];
                state[j] = Math.min(up, left);
            }
        }
        return state[array.length - 1];
    }

    /**
     * 硬币找零问题,有几种硬币分成几个阶段
     * @param coin
     * @param money
     * @return
     */
    public int minCoinChange(int[] coin, int money) {
        int state[] = new int[money + 1];
        Arrays.fill(state, -1);
        for (int i = 0; i < coin.length; i++) {
            for (int j = money; j > 0; j--) {//从前向后搜索
                int change = j % coin[i];
                int num = j / coin[i];
                if (change == 0) {//如果可以除尽，就使用这种找零
                    state[j] = num;
                } else {
                    if (state[change] == -1) {//领个位置都是-1，表示无法找零
                        continue;
                    } else if (state[j] == -1) {//上一个状态无法找零
                        state[j] = state[change] + num;
                    } else {
                        state[j] = Math.min(state[j], state[change] + num);
                    }
                }
            }
        }
        return state[money];
    }

    /**
     * 求解最长递增子序列
     * @param array
     * @return
     */
    public int longestIncreasingSubSequence(int[] array) {
        int[] dp = new int[array.length];
        dp[0] = 1;
        for (int i = 1; i < array.length; i++) {
            int j = 0;
            int max = 0;
            for (; j < i; j++) {
                if (array[i] > array[j]) {
                    max = max > dp[j] ? max : dp[j];
                }
            }
            dp[j] = max + 1;
        }
        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            if (max < dp[i]) {
                max = dp[i];
            }
        }
        return max;
    }
}
