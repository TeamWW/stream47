package com.lucifiere.stream;

import com.google.common.base.Optional;
import com.lucifiere.funtion.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 非延迟计算串行流
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public interface Stream<T> {

    /**
     * 过滤
     *
     * @param predicate 谓词逻辑
     * @return 结果流
     */
    Stream<T> filter(Predicate<? super T> predicate);

    /**
     * 映射
     *
     * @param mapper 映射逻辑
     * @return 结果流
     */
    <R> Stream<R> map(Function<? super T, ? extends R> mapper);

    /**
     * 扁平化
     *
     * @param mapper 映射逻辑
     * @param <R>    R
     * @return 结果流
     */
    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    /**
     * 去重
     *
     * @return 结果流
     */
    Stream<T> distinct();

    /**
     * 排序，如果可以的话
     *
     * @return 结果流
     */
    Stream<T> sorted();

    /**
     * 排序
     *
     * @param comparator 排序器
     * @return 结果流
     */
    Stream<T> sorted(Comparator<? super T> comparator);

    /**
     * 截取
     *
     * @param maxSize 位数
     * @return 结果流
     */
    Stream<T> limit(long maxSize);

    /**
     * 游标前移
     *
     * @param n 位数
     * @return 结果流
     */
    Stream<T> skip(long n);

    /**
     * 迭代
     *
     * @param action 迭代动作
     */
    void forEach(Consumer<? super T> action);

    /**
     * 规约
     *
     * @param identity    初始值
     * @param accumulator 规约逻辑
     * @return 规约结果
     */
    T reduce(T identity, BinaryOperator<T> accumulator);

    /**
     * 找最小
     *
     * @param comparator 比较器
     * @return 最小值
     */
    Optional<T> min(Comparator<? super T> comparator);

    /**
     * 找最小
     *
     * @param comparator 比较器
     * @return 最大值
     */
    Optional<T> max(Comparator<? super T> comparator);

    /**
     * 统计元素数目
     *
     * @return 数目
     */
    long count();

    /**
     * 析取运算
     *
     * @param predicate 谓词逻辑
     * @return 运算结果
     */
    boolean anyMatch(Predicate<? super T> predicate);

    /**
     * 合取运算
     *
     * @param predicate 谓词逻辑
     * @return 运算结果
     */
    boolean allMatch(Predicate<? super T> predicate);

    /**
     * 取否运算
     *
     * @param predicate 谓词逻辑
     * @return 运算结果
     */
    boolean noneMatch(Predicate<? super T> predicate);

    /**
     * 获取第一个
     *
     * @return 运算结果
     */
    Optional<T> findFirst();

    /**
     * 获取任意一个
     *
     * @return 运算结果
     */
    Optional<T> findAny();

    /**
     * 提取结果
     *
     * @return 结果
     */
    List<T> toList();

    /**
     * 提取结果
     *
     * @return 结果
     */
    List<T> toSet();

    /**
     * 一对一分组
     *
     * @param function 分组逻辑
     * @param <K>      K类型
     * @param <V>      V类型
     * @return K -> V
     */
    <K, V> Map<K, List<V>> singleGroupBy(Function<T, K> function);

    /**
     * 一对N分组
     *
     * @param function 分组逻辑
     * @param <K>      K类型
     * @param <V>      V类型
     * @return K -> V
     */
    <K, V> Map<K, V> groupBy(Function<T, K> function);

}
