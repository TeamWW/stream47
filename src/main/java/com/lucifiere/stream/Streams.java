package com.lucifiere.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * 流的工具类
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
final public class Streams {

    public static <K, V> BiStream<K, V> of(Map<K, V> map) {
        return MapStream.ofBiStream(map);
    }

    public static <T> Stream<T> of(List<T> ll) {
        return new ListStream<>(ll);
    }

    @SafeVarargs
    public static <T> Stream<T> of(T... ll) {
        return of(Lists.newArrayList(ll));
    }

}
