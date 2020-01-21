package com.qunar.study.algorithm;

/**
 * 贪心算法
 * Created by dujian on 2020/01/20
 */
public class Greedy {
    /**
     * 移除num的k个数字，使得剩下的数字最小
     * 每次移除高位比较大的值
     * @param num 操作数
     * @param k k个数字
     * @return
     */
    public int removeKNum(int num, int k) {
        if (num == 0) {
            return 0;
        }
        if (k == 0) {
            return num;
        }
        int maxTen = 1;//第一遍循环,确定最大的10的倍数
        int length = 0;//数字长度
        int tmp = num;
        while (tmp != 0) {
            maxTen *= 10;
            length++;
            tmp /= 10;
        }
        if (length <= k) {
            return 0;
        }
        maxTen /= 10;
        for (int i = 0; i < k; i++) {//消灭掉k个数
            int tmpTen = maxTen;
            for (int j = 0; j < length; j++) {
                if (j == length - 1) {//删除最后一个元素即可
                    num = deleteOneNum(num, 0);
                    break;
                }
                int high = (num / tmpTen) % 10;
                int low = (num % tmpTen) / (tmpTen / 10);
                if (high > low) {
                    num = deleteOneNum(num, length - j - 1);
                    break;
                }
                tmpTen /= 10;
            }
            maxTen /= 10;//减小10
            length--;
        }
        return num;
    }

    /**
     * 删除某个位置的值
     * @param num
     * @param location 从后往前开始计算位置,从0开始
     * @return
     */
    public int deleteOneNum(int num, int location) {
        if (location < 0) {
            return -1;
        }
        int pow = (int) Math.pow(10, location);
        int high;//高位值
        int low;//地位值
        high = num / (pow * 10);
        low = num % pow;
        return high * pow + low;
    }
}
