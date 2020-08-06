package com.lucifiere.stream;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.lucifiere.funtion.*;

import java.util.Comparator;
import java.util.List;

/**
 * LIST流
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public class ListStream<T> implements Stream<T> {

    private final List<T> innerList;

    public ListStream(List<T> innerList) {
        this.innerList = innerList;
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        List<T> list = Lists.newArrayList();
        for (T t : innerList) {
            if (predicate.test(t)) {
                list.add(t);
            }
        }
        return Streams.of(list);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        List<R> rList = Lists.newArrayList();
        for (T t : innerList) {
            rList.add(mapper.apply(t));
        }
        return Streams.of(rList);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        
    }

    @Override
    public Stream<T> distinct() {
        return null;
    }

    @Override
    public Stream<T> sorted() {
        return null;
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return null;
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return null;
    }

    @Override
    public Stream<T> skip(long n) {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return null;
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return null;
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public Optional<T> findFirst() {
        return null;
    }

    @Override
    public Optional<T> findAny() {
        return null;
    }

    @Override
    public <R, A> R collect(Supplier<A> supplier) {
        return null;
    }
}
