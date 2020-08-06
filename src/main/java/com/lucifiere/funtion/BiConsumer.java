package com.lucifiere.funtion;

import com.google.common.base.Preconditions;

/**
 * 二元消费函数
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public abstract class BiConsumer<T, U> {

    /**
     * 对给定的参数执行此操作
     *
     * @param t the input argument
     * @param u the input argument
     */
    public abstract void accept(T t, U u);

    /**
     * 返回一个组合的Consumer，它依次执行此操作，然后执行after操作
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    BiConsumer<T, U> andThen(final BiConsumer<? super T, ? super U> after) {
        Preconditions.checkNotNull(after);
        final BiConsumer<T, U> self = this;
        return new BiConsumer<T, U>() {
            @Override
            public void accept(T t, U u) {
                self.accept(t, u);
                after.accept(t, u);
            }
        };
    }

}
