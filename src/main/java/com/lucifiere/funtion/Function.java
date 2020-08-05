package com.lucifiere.funtion;

import com.google.common.base.Preconditions;

/**
 * 函数映射
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public abstract class Function<T, R> {

    /**
     * 将此函数应用于给定参数
     *
     * @param t the function argument
     * @return the function result
     */
    public abstract R apply(T t);

    /**
     * 返回一个组合函数，该函数首先将before函数应用于其输入，然后将该函数应用于结果。
     * 即是f(g(x))
     *
     * @param <V>    the type of input to the {@code before} function, and to the
     *               composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     * @see #andThen(Function)
     */
    <V> Function<V, R> compose(final Function<? super V, ? extends T> before) {
        Preconditions.checkNotNull(before);
        final Function<T, R> self = this;
        return new Function<V, R>() {
            @Override
            public R apply(V v) {
                T t = before.apply(v);
                return self.apply(t);
            }
        };
    }

    /**
     * 返回一个组合函数，该函数首先将此函数应用于输入，然后将after函数应用于结果
     *
     * @param <V>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     * @see #compose(Function)
     */
    <V> Function<T, V> andThen(final Function<? super R, ? extends V> after) {
        Preconditions.checkNotNull(after);
        final Function<T, R> self = this;
        return new Function<T, V>() {
            @Override
            public V apply(T v) {
                R t = self.apply(v);
                return after.apply(t);
            }
        };
    }

    /**
     * 返回始终返回其输入参数的函数
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T> Function<T, T> identity() {
        return new Function<T, T>() {
            @Override
            public T apply(T t) {
                return t;
            }
        };
    }

}
