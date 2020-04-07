package com.qunar.study.leetcode;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;

import java.util.*;

/**
 * Created by dujian on 2020/03/21
 */
public class Array {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1, middle = 0;
        while (left <= right) {

        }
        return -1;
    }

    private int findRotationPoint(int[] nums) {
        int left = 0, right = nums.length - 1, middle = 0;
        while (left <= right) {
            middle = (left + right) / 2;
            if (nums[middle] > nums[middle + 1]) {
                return middle;
            } else if (nums[middle] < nums[middle - 1]) {
                return middle - 1;
            }
            if (nums[middle] > nums[0]) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }

    public int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        Arrays.sort(intervals, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            } else {
                return o1[1] - o2[1];
            }
        });
        for (int i = 0; i < intervals.length; i++) {
            int i1 = intervals[i][0];
            int i2 = intervals[i][1];
            if (list.size() == 0) {
                list.add(new int[]{i1, i2});
                continue;
            }
            int[] last = list.get(list.size() - 1);
            if (last[1] >= intervals[i][0]) {
                last[1] = i2;
            } else {
                list.add(new int[]{i1, i2});
            }
        }
        return list.toArray(new int[list.size()][2]);
    }

    //查找第K大的数
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> o2-o1);
        for (int num : nums) {
            heap.add(num);
        }
        int i = 1;
        while (!heap.isEmpty()) {
            Integer num = heap.remove();
            if (k == i++) {
                return num;
            }
        }
        return -1;
    }

    //给你一个有序整数数组，数组中的数可以是正数、负数、零，请实现一个函数，这个函数返回一个整数：返回这个数组所有数的平方值中有多少种不同的取值
    public int sqrtDiff(int[] array) {
        int left = 0;
        int right = array.length - 1;
        int num = 0;
        while (left <= right) {
            int i = Math.abs(array[left]);
            int j = Math.abs(array[right]);
            if (i == j) {
                num++;
                while (left <= right && Math.abs(array[left]) == i) {
                    left++;
                }
                while (left <= right && Math.abs(array[right]) == j) {
                    right--;
                }
            } else if (i < j) {
                num++;
                while (left <= right && Math.abs(array[right]) == j) {
                    right--;
                }
            } else {
                num++;
                while (left <= right && Math.abs(array[left]) == i) {
                    left++;
                }
            }
        }
        return num;
    }

    public int diffNum(int[] array, int k) {
        int slow = 0;
        int fast = 0;
        int num = 0;
        while (fast < array.length && slow<=fast) {
            if (fast == slow) {
                fast++;
            }
            if (fast >= array.length) {
                break;
            }
            int i = array[fast];
            int j = array[slow];
            while (fast < array.length - 1 && array[fast + 1] == i) {
                fast++;
            }
            while (slow < fast - 1 && array[slow + 1] == j) {
                slow++;
            }
            int diff = i - j;
            if (diff < k) {
                fast++;
            } else if (diff > k) {
                slow++;
            } else {
                num++;
                fast++;
                slow++;
            }
        }
        return num;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Deque<Integer> deque = new ArrayDeque<>();
        List<List<Integer>> result = new ArrayList<>();
        if (candidates.length == 0) {
            return result;
        }
        sum(candidates,0, target, deque, result, 0);
        return result;
    }

    private void sum(int[] candidates, int from, int target, Deque<Integer> deque, List<List<Integer>> result, int sum) {
        if (sum == target) {
            result.add(new ArrayList<>(deque));
            return;
        }
        for (int i = from; i < candidates.length; i++) {
            int candidate = candidates[i];
            if (target - sum < candidate) {
                continue;
            }
            deque.addLast(candidate);
            sum(candidates, i, target, deque, result, sum + candidate);
            deque.removeLast();
        }
    }

    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean[][] access = new boolean[board.length][board[0].length];
                    if (exist(board, access, i, j, word, 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean exist(char[][] board, boolean[][] access, int x, int y, String word, int index) {
        access[x][y] = true;
        if (index == word.length()) {
            return true;
        }
        if (x > 0 && !access[x - 1][y] && board[x - 1][y] == word.charAt(index)) {
            if (exist(board, access, x - 1, y, word, index + 1)) {
                return true;
            }
        }
        if (x < board.length - 1 && !access[x + 1][y] && board[x + 1][y] == word.charAt(index)) {
            if (exist(board, access, x + 1, y, word, index + 1)) {
                return true;
            }
        }
        if (y > 0 && !access[x][y - 1] && board[x][y - 1] == word.charAt(index)) {
            if (exist(board, access, x, y - 1, word, index + 1)) {
                return true;
            }
        }
        if (y < board[0].length - 1 && !access[x][y + 1] && board[x][y + 1] == word.charAt(index)) {
            if (exist(board, access, x, y + 1, word, index + 1)) {
                return true;
            }
        }
        return false;
    }

    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0 && nums[i] < nums.length && nums[i] != i + 1) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return nums.length;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public int threeSumClosest(int[] nums, int target) {
        int cloest = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right] + nums[i];
                cloest = findLarger(sum, cloest, target);
                right--;
            }
        }
        return target;
    }

    private int findLarger(int first, int second, int target) {
        if (Math.abs(target - first) > Math.abs(target - second)) {
            return second;
        }
        return first;
    }

    public int largestRectangleArea(int[] heights) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(-1);
        int largest = 0;
        for (int i = 0; i < heights.length; i++) {
            if (deque.peekLast() == -1 || heights[i] >= heights[deque.peekLast()]) {
                deque.addLast(i);
            } else {//将栈顶弹出
                while (deque.peekLast() != -1 && heights[i] < heights[deque.peekLast()]) {
                    Integer location = deque.removeLast();
                    int area = (i - deque.peekLast() - 1) * heights[location];
                    largest = Math.max(largest, area);
                }
                deque.addLast(i);
            }
        }
        int maxLocation = deque.peekLast();
        while (deque.size() > 1) {
            Integer location = deque.removeLast();
            largest = Math.max(largest, heights[location] * (maxLocation - deque.peekLast()));
        }
        return largest;
    }

    @Test
    public void test() {
        System.out.println(findRotationPoint(new int[]{5,1,2,3,4}));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(sqrtDiff(new int[]{-1, 0, 1, 1}));
        System.out.println(diffNum(new int[]{1, 2, 3, 3, 5}, 4));
        combinationSum(new int[]{2, 3, 6, 7}, 7);
        exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCCED");
        firstMissingPositive(new int[]{3, 5, -1, 1});
        threeSumClosest(new int[]{1,2,4,8,16,32,64,128}, 82);

        largestRectangleArea(new int[]{5,4,1,2});
    }
}
