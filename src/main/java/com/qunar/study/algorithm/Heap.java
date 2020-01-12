package com.qunar.study.algorithm;

import java.util.Arrays;

/**
 * Created by dujian on 2020/01/09
 * 堆排序，自上往下进行堆化
 * 左右叶子节点分别是2i+1和2i+2
 * 父节点是(i-1)/2
 * 最后一个非叶子节点是(lastIndex - 1) / 2，实际上就是最后一个节点的父节点，其父节点索引前面的节点均为非叶子节点
 *
 * 堆可以解决的问题：优先级队列(合并文件,高性能定时器)，求解Top K问题，求解中位数以及百分位数，
 */
public class Heap {
    protected int[] heap;
    private Heapify heapify;
    int size;

    public Heap(int length, boolean min) {
        this.heap = new int[length];
        heapify = min ? new MinHeap() : new MaxHeap();
        size = 0;
    }

    public Heap(int[] heap, boolean min) {
        this.heap = heap;
        heapify = min ? new MinHeap() : new MaxHeap();
        size = heap.length;
        buildHeap();
    }

    @Override
    public String toString() {
        return Arrays.toString(heap);
    }

    public boolean insert(int data) {
        if (size == heap.length) {
            return false;
        }
        heap[size] = data;
        heapify.insert(heap, size);
        size++;
        return true;
    }

    public boolean deleteFirst() {
        if (size == 0) {
            return false;
        }
        heapify.swap(heap, 0, --size);//同时降低size
        heapify.heapify(heap, 0, size);
        return true;
    }

    public int[] sort() {
        int[] result = Arrays.copyOf(heap, size);
        int lastIndex = size - 1;
        while (lastIndex > 0) {
            heapify.swap(result, 0, lastIndex--);
            heapify.heapify(result, 0, lastIndex);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] heapArr = new int[]{5, 1, 9, 2, 3, 7, 14, 13, 4};
        Heap heap = new Heap(heapArr, false);
        int[] sort = heap.sort();
        System.out.println(Arrays.toString(sort));
        heap.insert(5);
        heap.insert(1);
        heap.insert(9);
        heap.insert(2);
        heap.insert(3);
        heap.insert(7);
        heap.insert(14);
        heap.insert(13);
        heap.insert(4);
        heap.deleteFirst();
    }

    private void buildHeap() {
        int nonLeafIndex = (size - 1) / 2;//最后一个叶子节点
        while (nonLeafIndex >= 0) {
            heapify.heapify(heap, nonLeafIndex, size - 1);
            nonLeafIndex--;
        }
    }

    private interface Heapify {
        void insert(int[] heap, int index);
        void heapify(int[] heap, int beginIndex, int lastIndex);

        default void swap(int heap[], int i, int j) {
            int tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
        }
    }

    //小顶堆
    private static class MinHeap implements Heapify {
        @Override
        public void insert(int[] heap, int index) {
            while ((index - 1) / 2 >= 0 && heap[index] < heap[(index - 1) / 2]) {
                swap(heap, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        @Override
        public void heapify(int[] heap, int beginIndex, int lastIndex) {
            while (true) {
                int minPos = beginIndex;
                int leftChild = 2 * beginIndex + 1;
                int rightChild = 2 * beginIndex + 2;
                if (leftChild <= lastIndex && heap[leftChild] < heap[minPos]) {
                    minPos = leftChild;
                }
                if (rightChild <= lastIndex && heap[rightChild] < heap[minPos]) {
                    minPos = rightChild;
                }
                if (minPos == beginIndex) {
                    break;
                }
                swap(heap, minPos, beginIndex);
                beginIndex = minPos;
            }
        }
    }

    private static class MaxHeap implements Heapify {
        @Override
        public void insert(int[] heap, int index) {
            while ((index - 1) / 2 >= 0 && heap[index] > heap[(index - 1) / 2]) {
                swap(heap, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        @Override
        public void heapify(int[] heap, int beginIndex, int lastIndex) {
            while (true) {
                int maxPos = beginIndex;
                int leftChild = 2 * beginIndex + 1;
                int rightChild = 2 * beginIndex + 2;
                if (leftChild <= lastIndex && heap[leftChild] > heap[maxPos]) {
                    maxPos = leftChild;
                }
                if (rightChild <= lastIndex && heap[rightChild] > heap[maxPos]) {
                    maxPos = rightChild;
                }
                if (maxPos == beginIndex) {
                    break;
                }
                swap(heap, maxPos, beginIndex);
                beginIndex = maxPos;
            }
        }
    }
}