package com.lucifiere.funtion;

import com.google.common.base.Preconditions;

import java.util.Objects;

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
    abstract R apply(T t);

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
    <V> Function<V, R> compose(final Function<V, T> before) {
        Preconditions.checkNotNull(before);
        final Function<T, R> self = this;
        return new Function<V, R>() {
            @Override
            R apply(V v) {
                T t = before.apply(v);
                return self.apply(t);
            }
        };
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     * @see #compose(Function)
     */
    <V> Function<R, V> andThen(Function<? super R, ? extends T> after) {
        Preconditions.checkNotNull(after);
        final Function<R, V> self = this;
        return new Function<V, R>() {
            @Override
            R apply(V v) {
                return before.apply(v);
            }
        };
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T> Function<T, T> identity() {
        return t -> t;
    }

}
