package com.qunar.study;

import com.qunar.study.algorithm.StringUtil;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testString()
    {
        int index = StringUtil.indexBF("baddef", "def");
        System.out.println(index);
    }
}
