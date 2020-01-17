package com.qunar.study.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by dujian on 2020/01/17
 * 字典树
 */
public class Trie {
    private TrieNode root = new TrieNode('/');
    private static final char baseChar = 'a';

    public void insert(String s) {
        if (s == null || s.isEmpty()) {
            return;
        }
        TrieNode node = root;
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            TrieNode[] childNode = node.childNode;
            int location = s.charAt(i) - baseChar;
            if (childNode[location] == null) {
                childNode[location] = new TrieNode(s.charAt(i));
            }
            length++;
            node = childNode[location];
        }
        node.end = true;
        node.length = length;//统计长度
    }

    public boolean find(String s) {
        TrieNode node = root;
        for (int i = 0; i < s.length(); i++) {
            TrieNode[] childNode = node.childNode;
            int location = s.charAt(i) - baseChar;
            if (childNode[location] == null) {
                return false;
            }
            node = childNode[location];
        }
        if (node.end) {
            return true;
        }
        return false;
    }

    public String shield(String str) {
        List<Integer> match = match(str);
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        for (int i = 0; i < match.size();) {
            int begin = match.get(i++);
            int end = match.get(i++);
            stringBuilder.append(str, index, begin);
            while (begin <= end) {
                stringBuilder.append("*");
                begin++;
            }
            index = end + 1;
        }
        stringBuilder.append(str, index, str.length());
        return stringBuilder.toString();
    }

    public List<Integer> match(String str) {
        List<Integer> index = new ArrayList<>();
        TrieNode node = root;
        for (int i = 0; i < str.length(); i++) {
            TrieNode childNode = node.childNode[str.charAt(i) - baseChar];
            if (childNode == null) {
                if (node != root) {
                    node = node.fail;
                }
            } else {
                if (childNode.end) {//找到
                    int pos = i - childNode.length + 1;
                    index.add(pos);
                    index.add(i);
                } else {
                    node = childNode;
                }
            }
        }
        return index;
    }

    /**
     * 创建ac自动机的fail指针
     */
    public void buildFailedPointer() {
        Queue<TrieNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TrieNode pNode = queue.poll();
            if (pNode == null) {
                continue;
            }
            for (int i = 0; i < 26; i++) {
                TrieNode cNode = pNode.childNode[i];
                if (cNode == null) {
                    continue;
                }
                if (pNode == root) {
                    cNode.fail = root;
                } else {
                    TrieNode pFail = pNode.fail;
                    while (pFail != null) {
                        TrieNode nextNode = pFail.childNode[cNode.data - baseChar];
                        if (nextNode != null) {
                            cNode.fail = nextNode;
                            break;
                        }
                        pFail = pFail.fail;
                    }
                    if (pFail == null) {
                        cNode.fail = root;
                    }
                }
                queue.offer(cNode);
            }
        }
    }

    private static class TrieNode{
        private char data;
        private boolean end;
        private TrieNode fail;//fail指针
        private int length = -1;//end是true,记录模式串的长度
        private TrieNode[] childNode = new TrieNode[26];

        public TrieNode(char data) {
            this.data = data;
        }
    }
}
