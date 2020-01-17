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
                    System.out.print("  ");
                } else {
                    System.out.print("Q ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
