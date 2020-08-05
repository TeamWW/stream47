package com.lucifiere.funtion;

/**
 * 函数映射
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public abstract class BiFunction<T, U, R> {

    /**
     * 将此函数应用于给定参数
     *
     * @param t the function argument
     * @param u the function argument
     * @return the function result
     */
    public abstract R apply(T t, U u);

}
