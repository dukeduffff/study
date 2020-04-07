package com.qunar.study.interviewee;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by dujian on 2020/03/30
 */
public class Yuan {
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
        Deque<Integer> function = function(new int[]{2, 3, 5, 5, 5, 3, 4, 6, 6, 5, 4});
        for (Integer integer : function) {
            System.out.print(integer+" ");
        }
    }

    public static Deque<Integer> function(int[] arr) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(arr[0]);
        boolean isRepeat = false;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == deque.peekLast()) {
                isRepeat = true;
            } else {
                if (isRepeat) {
                    deque.removeLast();
                    isRepeat = false;
                }
                if (arr[i] == deque.peekLast()) {
                    isRepeat = true;
                    continue;
                }
                deque.addLast(arr[i]);
            }
        }
        return deque;
    }
}
