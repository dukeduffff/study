package com.qunar.study.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dujian on 2020/03/22
 */
public class StringSubject {
    public List<String> restoreIpAddresses(String s) {
        List<String> ipList = new ArrayList<>();
        if (s.length() < 4 || s.length() > 12) {
            return ipList;
        }
        Deque<String> deque = new LinkedList<>();
        dfs(s, 0, 0, ipList, deque);
        return ipList;
    }

    public void dfs(String s, int begin, int count, List<String> ipList, Deque<String> deque) {
        if (begin == s.length()) {
            if (count == 4) {
                ipList.add(String.join(".", deque));
            }
            return;
        }
        //利用剩余的长度进行剪枝
        if ((s.length() - begin < 4 - count) || (s.length() - begin > 3 * (4 - count))) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            if (begin + i >= s.length()) {
                break;
            }
            if (legalIp(s, begin, begin + i)) {
                deque.addLast(s.substring(begin, begin + i + 1));
                dfs(s, begin + i + 1, count + 1, ipList, deque);
                deque.removeLast();
            }
        }
    }

    private boolean legalIp(String s, int left, int right) {
        int len = right - left + 1;
        if (len > 3) {
            return false;
        }
        if (len > 1 && s.charAt(left) == '0') {
            return false;
        }
        int res = 0;
        for (int i = left; i <= right; i++) {
            res = res * 10 + s.charAt(i) - '0';
            if (res > 255) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        List<String> strings = restoreIpAddresses("000256");
        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));
        }
    }
}
