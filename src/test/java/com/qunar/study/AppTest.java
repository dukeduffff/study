package com.qunar.study;

import com.qunar.study.algorithm.Backtracking;
import com.qunar.study.algorithm.StringUtil;
import com.qunar.study.algorithm.Trie;
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
        System.out.println(trie.shield("absdcabcd"));;
    }

    @Test
    public void testBacktracking() {
        Backtracking backtracking = new Backtracking();
        int[] queen = new int[8];
        Arrays.fill(queen, -1);
        backtracking.eightQueens(queen, 0);
    }
}
