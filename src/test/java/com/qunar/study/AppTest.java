package com.qunar.study;

import com.qunar.study.algorithm.*;
import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testString() {
        int index = StringUtil.indexBF("baddef", "def");
        System.out.println(index);
        index = StringUtil.indexRK("baddef", "def");
        System.out.println(index);
        index = StringUtil.indexBM("baddef", "def");
        System.out.println(index);
        index = StringUtil.indexBM("abcacabcbcbacabc", "cbacabc");
        System.out.println(index);
        index = StringUtil.indexKMP("abcacabcbcbacabc", "cbacabc");
        System.out.println(index);
    }

    @Test
    public void testTrie() {
        Trie trie = new Trie();
        trie.insert("abcd");
        trie.insert("bc");
        trie.insert("bcd");
        trie.insert("c");
        trie.buildFailedPointer();
        System.out.println(trie.find("s"));
        System.out.println(trie.shield("absdcabcd"));
    }

    @Test
    public void testBacktracking() {
        Backtracking backtracking = new Backtracking();
        int[] queen = new int[8];
        Arrays.fill(queen, -1);
        /*backtracking.eightQueens(queen, 0);*/
        backtracking.bag01(0, new int[]{1, 4, 5, 5}, 0, 7);
        System.out.println(backtracking.maxWeight);
        System.out.println(backtracking.shortestPath(new int[][]{{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}}, 0, 0, 0));;
    }

    @Test
    public void testGreedy() {
        Greedy greedy = new Greedy();
        System.out.println(greedy.removeKNum(4312, 4));
    }

    @Test
    public void testPattern() {
        Pattern pattern = new Pattern("ab*ef");
        System.out.println(pattern.match("abcddfssf"));
    }

    @Test
    public void testDynamic() {
        DynamicProgramming dynamicProgramming = new DynamicProgramming();
        System.out.println(dynamicProgramming.maxBag(new int[]{1, 4, 5, 5}, 7));
        System.out.println(dynamicProgramming.maxBagOp(new int[]{1, 4, 5, 5}, 7));
        System.out.println(dynamicProgramming.maxBagValue(new int[]{2, 2, 4, 6, 3}, new int[]{3, 4, 8, 9, 6}, 9));
        System.out.println(dynamicProgramming.shortestPath(new int[][]{{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}}));
        System.out.println(dynamicProgramming.minCoinChange(new int[]{1, 3, 5}, 9));
    }
}
