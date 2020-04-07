package com.qunar.study.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dujian on 2020/03/27
 */
public class Common {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> list = new ArrayList<>(n);
        begin(1, n, list);
        return list;
    }

    public void begin(int prefix, int n, List<Integer> list) {
        if (prefix > n) {
            return;
        }
        int up;
        if (prefix == 1) {
            up = prefix + 9;
        } else {
            up = prefix + 10;
        }
        while (prefix < up && prefix <= n) {
            list.add(prefix);
            begin(prefix * 10, n, list);
            prefix++;
        }
    }

    @Test
    public void test() {
        lexicalOrder(13);
    }
}
