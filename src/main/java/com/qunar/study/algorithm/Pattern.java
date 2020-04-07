package com.qunar.study.algorithm;

import org.junit.Test;

/**
 * Created by dujian on 2020/01/21
 * 正则表达式,只对*和?进行正则表达式的匹配
 * *匹配任意多个(大于等于0)字符
 * ?可以匹配0个或者多个字符
 */
public class Pattern {

    boolean matched = false;
    public boolean isMatch(String s, String p) {
        match(s, 0, p, 0);
        return matched;
    }

    public void match(String s, int sBegin, String p, int pBegin) {
        if (matched) {
            return;
        }
        if (p.length() == pBegin) {
            if (sBegin == s.length()) {
                matched = true;
            }
            return;
        }
        if (p.charAt(pBegin) == '?') {
            match(s, sBegin + 1, p, pBegin + 1);
        } else if (p.charAt(pBegin) == '*') {
            for (int i = 0; i <= s.length() - sBegin; i++) {
                match(s, sBegin + i, p, pBegin + 1);
            }
        } else if (sBegin < s.length() && s.charAt(sBegin) == p.charAt(pBegin)) {
            match(s, sBegin + 1, p, pBegin + 1);
        }
    }

    @Test
    public void test() {
        isMatch("aaabbbaabaaaaababaabaaabbabbbbbbbbaabababbabbbaaaaba", "a*******b");
    }
}
