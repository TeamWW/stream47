package com.lucifiere.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map流，处理ENTRY成员
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public class MapStream<T extends Map.Entry<K, V>, K, V> extends ListStream<T> {

    private final Map<K, V> sourceMap;

    public MapStream(Map<K, V> sourceMap) {
        Set<T> s = sourceMap.entrySet();
        List<T> ll = Lists.newArrayList();
        for (Map.Entry<K, V> e : ll) {
            ll.add(e);
        }
        this.sourceMap = sourceMap;
    }

}
