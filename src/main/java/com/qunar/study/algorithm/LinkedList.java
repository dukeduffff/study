package com.qunar.study.algorithm;

import org.junit.Test;

/**
 * Created by dujian on 2019/11/20
 * 链表相关算法
 */
public class LinkedList {
    @Test
    public void main() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        Node head = generateNode(arr);
        //调用测试方法
        /*System.out.println(isPalindrome(head));*/
        /*printLinkedList(reverse(head));*/
        /*System.out.println(isLoop(head));*/
        /*int[] arr1 = new int[]{2, 4, 6, 8};
        Node head1 = generateNode(arr1);
        printLinkedList(mergeTwoOrderLinkedList(head, head1));*/
        Node result = lastNNode(head, 2);
        System.out.println(result);
        System.out.println(findMiddleNode(head).data);
    }

    /**
     * 根据数组生成一个链表
     *
     * @param arr
     * @return
     */
    private Node generateNode(int[] arr) {
        Node head = null;
        //组装一个链表
        for (int i = arr.length - 1; i >= 0; i--) {
            head = new Node(arr[i], head);
        }
        return head;
    }

    /**
     * 打印链表
     *
     * @param head
     */
    private void printLinkedList(Node head) {
        if (head == null) {
            System.out.println("空链表");
        }
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
    }

    class Node {
        public int data;
        public Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public String toString() {
            if (next == null) {
                return new StringBuilder("Node:").append(data).append("->").append("null").toString();
            }
            return new StringBuilder("Node:").append(data).append("->").append(next.toString()).toString();
        }
    }

    /**
     * 判断链表回文
     * 1.利用快慢指针找到中间值
     * 2.从满指针开始，将链表原地反转
     * 3.从链表的两端分别向中间遍历，查看是否是回文
     *
     * @param head
     * @return
     */
    private boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        //利用快慢指针快速找到中间值
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //原地反转链表,将slow指针后面的链表反转即可
        Node pointer = slow;
        Node prev = null;
        while (pointer != null) {
            Node tmp = pointer.next;
            pointer.next = prev;
            prev = pointer;
            pointer = tmp;
        }
        Node iteratorHead = head;
        Node iteratorPointer = prev;
        while (iteratorHead != null) {
            if (iteratorHead.data != iteratorPointer.data) {
                return false;
            }
            iteratorHead = iteratorHead.next;
            iteratorPointer = iteratorPointer.next;
        }
        return true;
    }

    /**
     * 链表原地反转
     *
     * @param head
     * @return
     */
    private Node reverse(Node head) {
        Node prev = null;
        Node node = head;
        while (node != null) {
            Node tmp = node.next;
            node.next = prev;
            prev = node;
            node = tmp;
        }
        return prev;
    }

    /**
     * 判断单链表是否有环
     *
     * @param head
     * @return
     */
    private boolean isLoop(Node head) {
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            if (fast.next == slow || fast.next.next == slow) {
                return true;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;
    }

    /**
     * 合并两个有序链表
     * @param first
     * @param second
     * @return
     */
    private Node mergeTwoOrderLinkedList(Node first, Node second) {
        Node head = null;
        Node tail = null;
        while (first != null && second != null) {
            Node tmp;
            int firstData = first.data;
            int secondData = second.data;
            if (firstData <= secondData) {
                tmp = first;
                first = first.next;
            } else {
                tmp = second;
                second = second.next;
            }
            if (head == null) {
                head = tail = tmp;
            } else {
                tail.next = tmp;
                tail = tail.next;
            }
        }
        while (first != null) {
            tail.next = first;
            tail = tail.next;
            first = first.next;
        }
        while (second != null) {
            tail.next = second;
            tail = tail.next;
            second = second.next;
        }
        return head;
    }

    /**
     * 查找链表倒数第n个节点
     * @param head
     * @param n
     * @return
     */
    public Node lastNNode(Node head, int n) {
        if (head == null) {
            return null;
        }
        if (n <= 0) {
            return null;
        }
        Node fast = head, slow = head;
        int distance = 0;
        while (fast != null) {
            if (distance != n) {
                fast = fast.next;
            } else {
                fast = fast.next;
                slow = slow.next;
            }
        }
        if (distance == n) {
            return slow;
        }
        return null;
    }

    public Node findMiddleNode(Node head) {
        if (head == null) {
            return null;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
