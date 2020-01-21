package com.qunar.study.algorithm;

/**
 * Created by dujian on 2020/01/17
 * 回溯算法
 */
public class Backtracking {
    /**
     * 8皇后问题
     * @param queen 索引是行，存储的列中的值
     * @param row
     */
    public void eightQueens(int[] queen, int row) {
        if (row == queen.length) {
            printQueen(queen);
            return;
        }
        for (int column = 0; column < queen.length; column++) {//查找这一列的有效位置
            boolean status = true;
            for (int r = 0; r < row; r++) {//检查对应的行
                if (queen[r] == column) {
                    status = false;
                    break;
                }
                int leftUpColumn = column - (row - r), rightUpColumn = column + (row - r);
                if (leftUpColumn >= 0 && queen[r] == leftUpColumn) {
                    status = false;
                    break;
                }
                if (rightUpColumn < queen.length && queen[r] == rightUpColumn) {
                    status = false;
                    break;
                }
            }
            if (status) {
                queen[row] = column;
                eightQueens(queen, row + 1);
            }
        }
    }

    private void printQueen(int[] queen) {
        for (int i = 0; i < queen.length; i++) {
            for (int j = 0; j < queen.length; j++) {
                if (queen[i] != j) {
                    System.out.print("⭕️ ");
                } else {
                    System.out.print("❎ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 0-1背包问题,开始找
     */
    public int maxWeight = Integer.MIN_VALUE;

    public void bag01(int weight, int goods[], int index, int limit) {
        if (weight > limit) {
            return;
        }
        if (index == goods.length || weight == limit) {
            if (weight > maxWeight) {
                maxWeight = weight;
            }
            return;
        }
        bag01(weight, goods, index + 1, limit);//不装第index+1个
        if (weight + goods[index] < limit) {
            bag01(weight + goods[index], goods, index + 1, limit);
        }
    }
}
