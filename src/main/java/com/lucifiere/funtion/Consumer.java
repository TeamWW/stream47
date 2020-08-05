package com.lucifiere.funtion;

import com.google.common.base.Preconditions;

/**
 * 消费函数
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public abstract class Consumer<T> {

    /**
     * 对给定的参数执行此操作
     *
     * @param t the input argument
     */
    public abstract void accept(T t);

    /**
     * 返回一个组合的Consumer，它依次执行此操作，然后执行after操作
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    Consumer<T> andThen(final Consumer<? super T> after) {
        Preconditions.checkNotNull(after);
        final Consumer<T> self = this;
        return new Consumer<T>() {
            @Override
            public void accept(T t) {
                self.accept(t);
                after.accept(t);
            }
        };
    }

}
