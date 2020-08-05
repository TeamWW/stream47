package com.lucifiere.stream;

import com.google.common.base.Optional;
import com.lucifiere.funtion.BiConsumer;
import com.lucifiere.funtion.BiFunction;
import com.lucifiere.funtion.Function;
import com.lucifiere.funtion.Predicate;

import java.util.Map;

/**
 * 非延迟计算串行流
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public interface BiStream<K, V> {

    /**
     * 过滤
     *
     * @param predicate 谓词逻辑
     * @return 结果流
     */
    BiStream<K, V> filter(BiFunction<K, V, Boolean> predicate);

    /**
     * 过滤键
     *
     * @param keyPredicate key的谓词逻辑
     * @return 结果流
     */
    BiStream<K, V> filterKey(Predicate<? super K> keyPredicate);

    /**
     * 过滤值
     *
     * @param valPredicate val的谓词逻辑
     * @return 结果流
     */
    BiStream<K, V> filterVal(Predicate<? super V> valPredicate);

    /**
     * 计算值然后补充或替换
     *
     * @param k                 键
     * @param remappingFunction 生成函数
     * @return 结果流
     */
    BiStream<K, V> compute(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction);

    /**
     * 缺失即计算值
     *
     * @param k                 键
     * @param remappingFunction 生成函数
     * @return 结果流
     */
    BiStream<K, V> computeIfAbsent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction);

    /**
     * 出现即替代值
     *
     * @param k                 键
     * @param remappingFunction 生成函数
     * @return 结果流
     */
    BiStream<K, V> computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction);

    /**
     * 映射
     *
     * @param keyMapper 键的映射逻辑
     * @param valMapper 值的映射逻辑
     * @return 结果流
     */
    <NK, NV> BiStream<NK, NV> map(Function<? super K, ? extends NK> keyMapper, Function<? super V, ? extends NV> valMapper);

    /**
     * 映射键
     *
     * @param keyMapper 键的映射逻辑
     * @return 结果流
     */
    <NK> BiStream<NK, V> mapKey(Function<? super K, ? extends NK> keyMapper);

    /**
     * 映射值
     *
     * @param valMapper 值的映射逻辑
     * @return 结果流
     */
    <NV> BiStream<K, NV> mapVal(Function<? super V, ? extends NV> valMapper);

    /**
     * 扁平化
     *
     * @param keyMapper 键的映射逻辑
     * @param valMapper 值的映射逻辑
     * @return 结果流
     */
    <NV, NK> BiStream<NK, NV> flatMap(Function<? super K, ? extends NK> keyMapper, Function<? super V, ? extends NV> valMapper);

    /**
     * 去重
     *
     * @return 结果流
     */
    BiStream<K, V> distinct();

    /**
     * 迭代
     *
     * @param action 迭代动作
     */
    void forEach(BiConsumer<? super K, ? super V> action);

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
    boolean anyMatch(BiFunction<? super K, ? super V, Boolean> predicate);

    /**
     * 合取运算
     *
     * @param predicate 谓词逻辑
     * @return 运算结果
     */
    boolean allMatch(BiFunction<? super K, ? super V, Boolean> predicate);

    /**
     * 取否运算
     *
     * @param predicate 谓词逻辑
     * @return 运算结果
     */
    boolean noneMatch(BiFunction<? super K, ? super V, Boolean> predicate);

    /**
     * 提取结果
     *
     * @return map
     */
    Map<K, V> toMap();

    /**
     * 获取第一个
     *
     * @return 运算结果
     */
    Optional<Map.Entry<K, V>> findAny();


}
