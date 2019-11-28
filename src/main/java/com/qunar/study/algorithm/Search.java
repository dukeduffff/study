package com.qunar.study.algorithm;

import org.junit.Test;

/**
 * Created by dujian on 2019/11/28
 * 查找算法类
 */
public class Search {
    @Test
    public void searchTest() {
        int[] arr = new int[]{1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        System.out.println(binarySearchFirstBiggerThan(arr, 2));
    }

    /**
     * 查找返回所在的位置
     *
     * @param arr
     * @param findNum
     * @return
     */
    public int binarySearch(int[] arr, int findNum) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) >> 1;
            if (arr[mid] > findNum) {
                high = mid - 1;
            } else if (arr[mid] < findNum) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public int binarySearchFirst(int[] arr, int findNum) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) >> 1;
            if (arr[mid] > findNum) {
                high = mid - 1;
            } else if (arr[mid] < findNum) {
                low = mid + 1;
            } else {
                if (mid == 0 || arr[mid - 1] != findNum) {
                    return mid;
                }
                high = mid - 1;
            }
        }
        return -1;
    }

    public int binarySearchFirstBiggerThan(int[] arr, int findNum) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] >= findNum) {
                if (mid == 0 || arr[mid - 1] < findNum) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }
}
