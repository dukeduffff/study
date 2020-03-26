package com.qunar.study.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by dujian on 2020/03/22
 */
public class Tree {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Set<TreeNode> accessNode = new HashSet<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);
        accessNode.add(root);
        while (!deque.isEmpty()) {
            TreeNode top = deque.peekLast();
            if (top.left != null && accessNode.add(top.left)) {
                deque.addLast(top.left);
                continue;
            }
            top = deque.removeLast();
            list.add(top.val);
            if (top.right != null && accessNode.add(top.right)) {
                deque.addLast(top.right);
            }
        }
        return list;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        Deque<TreeNode>[] deques = new ArrayDeque[2];
        deques[0] = new ArrayDeque<>();
        deques[1] = new ArrayDeque<>();
        List<List<Integer>> result = new ArrayList<>();
        int level = 1;
        deques[level % 2].addLast(root);
        while (!deques[0].isEmpty() || !deques[1].isEmpty()) {
            Deque<TreeNode> thisDeque = deques[level % 2];
            Deque<TreeNode> nextDeque = deques[(level + 1) % 2];
            TreeNode node;
            List<Integer> list = new ArrayList<>();
            while (!thisDeque.isEmpty()) {
                node = thisDeque.removeFirst();
                list.add(node.val);
                TreeNode left = node.left;
                TreeNode right = node.right;
                if (left != null) {
                    nextDeque.add(left);
                }
                if (right != null) {
                    nextDeque.add(right);
                }
            }
            level++;
            result.add(list);
        }
        return result;
    }

    @Test
    public void test() {
        TreeNode root = arrayToTreeNode(new Integer[]{3,9,20,null,null,15,7});
        List<Integer> list = inorderTraversal(root);
        for (Integer num : list) {
            System.out.println(num);
        }
        List<List<Integer>> list1 = levelOrder(root);
        for (List<Integer> integers : list1) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

    private static TreeNode arrayToTreeNode(Integer[] array) {
        int len = array.length;
        TreeNode[] nodeArray = new TreeNode[len];
        nodeArray[0] = new TreeNode(array[0]);
        for (int i = 0; i < len; i++) {
            TreeNode root = nodeArray[i];
            int left = i * 2 + 1;
            int right = i * 2 + 2;
            if (left < len && array[left] != null) {
                root.left = new TreeNode(array[left]);
                nodeArray[left] = root.left;
            }
            if (right < len && array[right] != null) {
                root.right = new TreeNode(array[right]);
                nodeArray[right] = root.right;
            }
        }
        return nodeArray[0];
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
