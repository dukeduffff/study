package com.qunar.study.interviewee;

import org.junit.Test;

/**
 * Created by dujian on 2020/03/28
 */
public class Solution {
    @Test
    public void test() {
        System.out.println(isMatched("a", "ab*", 0, 0));
    }

    public boolean isMatched(String str, String p, int i, int j) {
        if (p.length() == j) {
            return str.length() == i;
        }
        //只有上一个字符相等才进行判断
        boolean beginMatch = false;
        if (i < str.length() && j < p.length()) {
            beginMatch = str.charAt(i) == p.charAt(j) || p.charAt(j) == '.';
        }
        if (j < p.length() - 1 && p.charAt(j + 1) == '*') {
            boolean matched =  isMatched(str, p, i, j + 2);
            if (matched){
                return matched;
            }
            if (i == str.length() - 1 && j == p.length() - 2) {
                return beginMatch;
            } else {
                if (str.length() == i) {
                    return p.length() == j;
                }
                return isMatched(str, p, i + 1, j) && beginMatch;
            }
        } else {
            if (str.length() == i) {
                return p.length() == j;
            }
            return beginMatch && isMatched(str, p, i + 1, j + 1);
        }
    }
}
