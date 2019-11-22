package com.qunar.study.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by dujian on 2019/11/22
 * 队列相关代码
 */
public class Queue {

    @Test
    public void test() {
        QueueInterface<Integer> queue = new ArrayCycleQueue<>(5);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        System.out.println(queue.dequeue());
        queue.enqueue(6);
    }


    class ArrayCycleQueue<T> implements QueueInterface<T> {
        private T[] array;
        private int size;
        private int length;
        private int head;//所指是已经出队
        private int tail;//当前入队的

        public ArrayCycleQueue(int length) {
            array = (T[]) new Object[length];
            this.length = length;
            size = 0;
            head = -1;
            tail = -1;
        }

        @Override
        public boolean enqueue(T data) {
            if (size == length) {
                return false;
            }
            if (tail == length - 1) {
                tail = -1;
            }
            array[++tail] = data;
            size++;
            return true;
        }

        @Override
        public T dequeue() {
            if (size == 0) {
                return null;
            }
            if (head == length - 1) {
                head = -1;
            }
            head++;
            size--;
            return array[head];
        }

        public String toString() {
            return Arrays.toString(array);
        }
    }


    //接口，每个队列都要实现的出队和入队
    private interface QueueInterface<T> {
        boolean enqueue(T data);

        T dequeue();
    }
}
