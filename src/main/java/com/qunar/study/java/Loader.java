package com.qunar.study.java;

import org.junit.Test;

/**
 * Created by dujian on 2020/03/08
 */
public class Loader {
    static class FinalTest {
        public static final int a = 2;

        static {
            System.out.println("final block");
        }
    }

    @Test
    public void test() {
        System.out.println(FinalTest.a);
    }
}
