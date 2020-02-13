package com.qunar.study.algorithm;

/**
 * Created by dujian on 2020/02/08
 */
public class Recursion {
    public int fibonacci(int num) {
        if (num == 1 || num == 2) {
            return 1;
        }
        return fibonacci(num - 1) + fibonacci(num - 2);
    }
}
