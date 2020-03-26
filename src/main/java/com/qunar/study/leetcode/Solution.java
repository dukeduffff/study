package com.qunar.study.leetcode;

import java.util.*;

/**
 * Created by dujian on 2020/02/08
 */
public class Solution {
    public int firstMissingPositive(int[] nums) {
        int result = 1;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == result) {
                while (set.contains(result++)) {

                }
            } else if (nums[i] > result) {
                set.add(nums[i]);
            }
        }
        return result;
    }

    public boolean isValid(String s) {
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                deque.addLast(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (deque.isEmpty()) {
                    return false;
                }
                char stack = deque.pollLast();
                if (stack == '(' && c == ')') {
                    continue;
                } else if (stack == '{' && c == '}') {
                    continue;
                } else if (stack == '[' && c == ']') {
                    continue;
                }
                return false;
            } else {
                return false;
            }
        }
        if (deque.isEmpty()) {
            return true;
        }
        return false;
    }

    public int longestValidParentheses(String s) {
        Deque<Character> deque = new LinkedList<>();
        int[] arr = new int[s.length()];
        Arrays.fill(arr, 0);
        int current = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                deque.addLast(c);
                current = 0;
            } else if (c == ')') {
                if (deque.isEmpty()) {
                    continue;
                }
                char stack = deque.pollLast();
                if (stack == '(' && c == ')') {
                    current++;
                    if (i - current * 2 >= 0) {
                        current += arr[i - current * 2];
                    }
                    arr[i] = current;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
        }
        return max * 2;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[nums.length - k + 1];
        for (int i = 0; i < k; i++) {
            pushElement(deque, nums, i, k);
        }
        result[0] = nums[deque.getFirst()];
        for (int i = k; i < nums.length; i++) {
            pushElement(deque, nums, i, k);
            result[i - k + 1] = nums[deque.getFirst()];
        }
        return result;
    }

    private void pushElement(Deque<Integer> deque, int[] nums, int location, int k) {
        if (deque.isEmpty()) {
            deque.addFirst(location);
            return;
        }
        int remove = location - k;
        while (!deque.isEmpty() && deque.getFirst() <= remove) {//删除不再范围的数据
            deque.pollFirst();
        }
        if (deque.isEmpty()) {
            deque.addFirst(location);
        } else if (nums[deque.getFirst()] < nums[location]) {//如果队列第一个小于当前数值
            deque.addFirst(location);
        } else {//否则添加到后面
            while (nums[deque.getLast()] < nums[location]) {
                deque.pollLast();
            }
            deque.addLast(location);
        }
    }

    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        long left = 0, right = x / 2;
        long mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            long stage = mid * mid;
            if (stage == x) {
                return (int) mid;
            } else if (stage < x) {
                left = mid + 1;
            } else {
                if ((mid - 1) * (mid - 1) < x) {
                    return (int) (mid - 1);
                } else {
                    right = mid - 1;
                }
            }
        }
        return (int) mid;
    }

    public static int stack(int[] height) {
        Deque<Integer> deque = new LinkedList<>();
        int i = 0, result = 0;
        while (i < height.length) {
            while (!deque.isEmpty() && height[i] > height[deque.getLast()]) {
                Integer cave = deque.removeLast();
                if (deque.isEmpty()) {
                    break;
                }
                Integer bound = deque.getLast();
                result += (Math.min(height[i], height[bound]) - height[cave]) * (i - bound - 1);
            }
            deque.addLast(i++);
        }
        return result;
    }

    public static String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        String reverseStr = new StringBuilder(s).reverse().toString();
        int[][] dp = new int[s.length()][s.length()];
        int maxLen = 0;
        int maxEnd = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == reverseStr.charAt(0)) {//先设置第一行的状态
                dp[0][i] = 1;
                maxEnd = i;
                maxLen = 1;
            }
            if (s.charAt(0) == reverseStr.charAt(i)) {
                dp[i][0] = 1;
                maxEnd = 0;
                maxLen = 1;
            }
        }
        for (int i = 1; i < s.length(); i++) {
            for (int j = 1; j < s.length(); j++) {
                if (reverseStr.charAt(i) == s.charAt(j)) {
                    int len = dp[i - 1][j - 1] + 1;
                    dp[i][j] = len;
                    if (len > maxLen) {
                        maxLen = len;
                        maxEnd = j;
                    }
                }
            }
        }
        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }

    public static void main(StringSubject[] args) {
        Solution solution = new Solution();
        Deque<StringSubject> deque = new LinkedList<>();
        solution.firstMissingPositive(new int[]{2, 1});
        System.out.println(solution.isValid("]"));
        System.out.println(solution.longestValidParentheses("()(())"));
        System.out.println(Arrays.toString(solution.maxSlidingWindow(new int[]{1, -1}, 1)));
        System.out.println(solution.mySqrt(4));
        stack(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        longestPalindrome("aacdefcaa");
    }
}
