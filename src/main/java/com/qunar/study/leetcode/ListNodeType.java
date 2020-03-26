package com.qunar.study.leetcode;

import org.junit.Test;

/**
 * Created by dujian on 2020/03/21
 * 链表题目
 */
public class ListNodeType {
    private ListNode arrayToListNode(int[] array) {
        ListNode head = new ListNode(-1), tail = head;
        for (int i = 0; i < array.length; i++) {
            tail.next = new ListNode(array[i]);
            tail = tail.next;
        }
        return head.next;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }

        public String toString() {
            if (next == null) {
                return new StringBuilder("Node:").append(val).append("->").append("null").toString();
            }
            return new StringBuilder("Node:").append(val).append("->").append(next.toString()).toString();
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        int num = 0;
        ListNode resultHead = new ListNode(-1), resultTail = resultHead, stageHead = null, stageTail = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = null;
            if (num++ == 0) {
                stageHead = stageTail = head;
            } else {
                stageTail.next = head;
                stageTail = stageTail.next;
            }
            if (k == num) {//进行翻转
                reverse(stageHead);
                num = 0;
                resultTail.next = stageTail;
                resultTail = stageHead;
            } else if (next == null) {
                resultTail.next = stageHead;
                resultTail = stageTail;
            }
            //连接
            head = next;
        }
        return resultHead.next;
    }

    public void reverse(ListNode node) {
        ListNode prev = null, next = null;
        while (node != null) {
            next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
    }

    @Test
    public void test() {
        ListNode head = arrayToListNode(new int[]{1, 2, 3, 4, 5});
        ListNode listNode = reverseKGroup(head, 2);
        System.out.println(listNode);
    }
}
