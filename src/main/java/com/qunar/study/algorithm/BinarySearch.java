package com.qunar.study.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dujian on 2020/01/08
 * 二叉查找树，左子树值小，右子树值大
 */
public class BinarySearch {
    private Node root;//根节点

    @Test
    public void test() {
        BinarySearch binarySearch = new BinarySearch();
        binarySearch.insert(2);
        binarySearch.insert(1);
        binarySearch.insert(3);
        binarySearch.delete(2);
        binarySearch.printTree();
        binarySearch.find(3).printNode();
    }

    public Node find(int data) {
        Node r = root;
        while (r != null) {
            if (data < r.data) {
                r = r.left;
            } else if (data > r.data) {
                r = r.right;
            } else {
                return r;
            }
        }
        return null;
    }

    public void delete(int data) {
        if (root == null) {
            return;
        }
        Node r = root;//要删除的节点
        Node pr = null;//要删除的节点的父节点
        //1.定位Node
        while (r != null && r.data != data) {
            pr = r;
            if (data < r.data) {
                r = r.left;
            } else {
                r = r.right;
            }
        }
        if (r == null) {
            return;//没有找到该节点
        }
        //2.要删除的节点有两个子节点
        if (r.left != null && r.right != null) {
            //找到最小的节点
            Node dpr = r;//最小节点的父节点
            Node dr = r.left;
            while (dr.left != null) {
                dpr = dr;
                dr = dr.left;
            }
            r.data = dr.data;
            pr = dpr;
            r = dr;//改成删除这个节点
        }
        //3.只有一个节点或者就是叶子节点
        Node child;
        if (r.left != null) {
            child = r.left;
        } else if (r.right != null) {
            child = r.right;
        } else {
            child = null;//叶子节点
        }
        if (pr == null) {
            root = child;
        } else if (pr.left == r) {
            pr.left = child;
        } else if (pr.right == r) {
            pr.right = child;
        }
    }

    /**
     * 不支持插入相同元素
     * @param data
     * @return
     */
    public boolean insert(int data) {
        if (root == null) {
            root = new Node(data);
            return true;
        }
        Node r = root;
        while (r != null) {
            if (data < r.data) {
                if (r.left == null) {
                    r.left = new Node(data);
                    return true;
                }
                r = r.left;
            } else if (data > r.data) {
                if (r.right == null) {
                    r.right = new Node(data);
                    return true;
                }
                r = r.right;
            } else {
                break;
            }
        }
        return false;
    }

    public void printTree() {
        root.printNode();
    }

    public static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
        }

        public void printNode() {
            int maxLevel = maxLevel(this);
            printNodeInternal(Collections.singletonList(this), 1, maxLevel);
        }

        private void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || isAllElementsNull(nodes))
                return;

            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            printWhitespaces(firstSpaces);

            List<Node> newNodes = new ArrayList<Node>();
            for (Node node : nodes) {
                if (node != null) {
                    System.out.print(node.data);
                    newNodes.add(node.left);
                    newNodes.add(node.right);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                printWhitespaces(betweenSpaces);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).left != null)
                        System.out.print("/");
                    else
                        printWhitespaces(1);

                    printWhitespaces(i + i - 1);

                    if (nodes.get(j).right != null)
                        System.out.print("\\");
                    else
                        printWhitespaces(1);

                    printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println("");
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                System.out.print(" ");
        }

        private int maxLevel(Node node) {
            if (node == null)
                return 0;

            return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
        }

        private boolean isAllElementsNull(List<Node> list) {
            for (Node object : list) {
                if (object != null)
                    return false;
            }

            return true;
        }
    }
}
