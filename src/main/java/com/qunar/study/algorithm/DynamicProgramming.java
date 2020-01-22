package com.qunar.study.algorithm;

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
}
