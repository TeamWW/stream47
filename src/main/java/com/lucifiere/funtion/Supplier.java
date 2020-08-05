package com.lucifiere.funtion;

/**
 * 工厂函数
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public abstract class Supplier<T> {

    /**
     * 获取计算结果
     *
     * @return a result
     */
    abstract T get();

}
