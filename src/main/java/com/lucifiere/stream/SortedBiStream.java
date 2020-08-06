package com.lucifiere.stream;

import com.google.common.base.Optional;

import java.util.Comparator;
import java.util.Map;

/**
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public interface SortedBiStream<K, V> extends BiStream<K, V> {

    /**
     * 排序，如果可以的话
     *
     * @return 结果流
     */
    <C extends Comparable<C>> SortedBiStream<C, V> sorted();

    /**
     * 按照键排序
     *
     * @param comparator 排序器
     * @return 结果流
     */
    SortedBiStream<K, V> sorted(Comparator<? super K> comparator);

    /**
     * 截取
     *
     * @param maxSize 位数
     * @return 结果流
     */
    SortedBiStream<K, V> limit(int maxSize);

    /**
     * 游标前移
     *
     * @param n 位数
     * @return 结果流
     */
    SortedBiStream<K, V> skip(int n);

    /**
     * 找最小
     *
     * @param comparator 比较器
     * @return 最小值
     */
    Optional<Map.Entry<K, V>> min(Comparator<? super K> comparator);

    /**
     * 找最小
     *
     * @param comparator 比较器
     * @return 最大值
     */
    Optional<Map.Entry<K, V>> max(Comparator<? super K> comparator);


}
