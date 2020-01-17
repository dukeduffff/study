package com.qunar.study.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by dujian on 2019/11/22
 * 归并排序和快速排序,O(nlogn)时间复杂度
 */
public class Sort {
    @Test
    public void test() {
        int[] array = {11, 8, 3, 9, 7, 1, 2, 5};
        /*mergeSort(array, 0, 7);*/
        System.out.println(Arrays.toString(array));
        /*quickSort(array, 0, 7);*/
        System.out.println(Arrays.toString(array));
//        System.out.println(findMaxN(array, 0, 7, 2));
        countingSort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 归并排序，主要是根据中间值将数组划分成两部分，针对两部分分别排序，
     * 这样就是合并有序数组的流程就可以了，整体的时间复杂度是O(nlogn)
     * 空间复杂度是O(n)
     *
     * @param arr
     * @param from
     * @param to
     */
    private void mergeSort(int[] arr, int from, int to) {
        if (from == to) {
            return;
        }
        int half = (from + to) / 2;
        mergeSort(arr, from, half);
        int from1 = half + 1;
        mergeSort(arr, from1, to);
        //做merge操作，将两部分的结果进行合并，实质是合并两个有序数据，方法和合并两个有序链表是相同的操作
        int[] tmp = new int[to - from + 1];
        int i = 0;
        int begin = from;
        while (from <= half && from1 <= to) {
            if (arr[from] <= arr[from1]) {
                tmp[i++] = arr[from++];
            } else {
                //这里可以统计逆序度 num += half - from + 1,from位置的元素比from1的元素都大,个数就是
                tmp[i++] = arr[from1++];
            }
        }
        //合并两个数组剩余的元素
        while (from <= half) {
            tmp[i++] = arr[from++];
        }
        while (from1 <= to) {
            tmp[i++] = arr[from1++];
        }
        for (i = 0; i < tmp.length; i++) {
            arr[begin++] = tmp[i];
        }
    }

    /**
     * 快速排序算法
     * @param arr
     * @param from 开始指针
     * @param to 结束指针
     */
    private void quickSort(int[] arr, int from, int to) {
        if (from >= to) {
            return;
        }
        int pivot = to;
        int head = from, tail = from;
        while (tail < to) {
            if (arr[tail] < arr[pivot]) {//如果尾部元素小于pivot，则需要进行交换
                int tmp = arr[tail];
                arr[tail] = arr[head];
                arr[head] = tmp;
                head++;
            }
            tail++;
        }
        int tmp = arr[pivot];
        arr[pivot] = arr[head];
        arr[head] = tmp;

        //进行分区处理
        quickSort(arr, from, head - 1);
        quickSort(arr, head + 1, to);
    }

    /**
     * 利用快速排序的思想，查找第N大的元素
     * @param arr
     * @param from
     * @param to
     * @param n
     * @return
     */
    private int findMaxN(int[] arr, int from, int to, int n) {
        int pivot = to;
        int head = from, tail = from;
        while (tail < to) {
            if (arr[tail] < arr[pivot]) {//如果尾部元素小于pivot，则需要进行交换
                int tmp = arr[tail];
                arr[tail] = arr[head];
                arr[head] = tmp;
                head++;
            }
            tail++;
        }
        int tmp = arr[pivot];
        arr[pivot] = arr[head];
        arr[head] = tmp;
        //head指向中间位置
        if (head == n - 1) {
            return arr[head];
        } else if (head > n - 1) {
            return findMaxN(arr, from, head - 1, n);
        } else {
            return findMaxN(arr, head + 1, to, n);
        }
    }

    /**
     * 计数排序
     * @param arr
     */
    private void countingSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        //查找当前最大的数值
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        //申请一个数组，从0到max
        int[] c = new int[max + 1];
        //计算每个数值的个数，计入数组c
        for (int num : arr) {
            c[num]++;
        }
        //做一次累加
        for (int i = 1; i < c.length; i++) {
            c[i] = c[i - 1] + c[i];
        }
        //临时数组，存放排好序的数据
        int[] result = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            int score = arr[i];
            int location = c[score]--;
            result[--location] = score;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = result[i];
        }
    }
}
