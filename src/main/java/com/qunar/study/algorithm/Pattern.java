package com.qunar.study.algorithm;

/**
 * Created by dujian on 2020/01/21
 * 正则表达式,只对*和?进行正则表达式的匹配
 * *匹配任意多个(大于等于0)字符
 * ?可以匹配0个或者多个字符
 */
public class Pattern {
    private String pattern;

    public Pattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean match(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return pMatch(text, 0, 0);
    }

    /**
     * 正则表达式的匹配算法
     * @param text
     * @param i
     * @param j
     * @return
     */
    private boolean pMatch(String text, int i, int j) {
        if (i == text.length() && j == pattern.length()) {
            return true;
        } else if (i >= text.length() || j >= pattern.length()) {//只要有一个没有到头就返回false
            return false;
        }
        char p = pattern.charAt(j);
        char t = text.charAt(i);
        if (p == '*') {
            for (int k = 0; k < text.length(); k++) {//这里可以对循环范围进行优化
                if (pMatch(text, i + k, j + 1)) {
                    return true;
                }
            }
        } else if (p == '?') {
            if (pMatch(text, i, j + 1)) {//i位置不匹配?
                return true;
            }
            if (pMatch(text, i + 1, j + 1)) {//i位置匹配
                return true;
            }
        } else if (p == t) {//针对不是上面两个情况进行优化,也可以对范围进行优化
            if (pMatch(text, i + 1, j + 1)) {
                return true;
            }
        }
        return false;
    }
}
