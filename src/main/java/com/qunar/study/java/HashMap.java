package com.qunar.study.java;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dujian on 2020/03/14
 */
public class HashMap {
    @Test
    public void use() {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "a");
        Double d = 1.21;
        System.out.println(d.intValue());
    }
}
