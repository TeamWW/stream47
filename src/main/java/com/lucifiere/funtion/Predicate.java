package com.lucifiere.funtion;

import com.google.common.base.Preconditions;

/**
 * 谓词逻辑
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public abstract class Predicate<T> {

    /**
     * 根据给定参数评估此谓词
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    abstract boolean test(T t);

    Predicate<T> and(final Predicate<? super T> other) {
        Preconditions.checkNotNull(other);
        final Predicate<T> self = this;
        return new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return self.test(t) && other.test(t);
            }
        };
    }

    /**
     * 返回表示该谓词逻辑取反的谓词逻辑
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    Predicate<T> negate() {
        final Predicate<T> self = this;
        return new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return !self.test(t);
            }
        };
    }

    /**
     * 返回一个组合谓词，该谓词表示此谓词和另一个谓词的短路逻辑
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    Predicate<T> or(final Predicate<? super T> other) {
        Preconditions.checkNotNull(other);
        final Predicate<T> self = this;
        return new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return self.test(t) || other.test(t);
            }
        };
    }

}
