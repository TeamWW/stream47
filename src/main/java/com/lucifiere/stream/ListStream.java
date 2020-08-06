package com.lucifiere.stream;

import com.google.common.base.Optional;
import com.lucifiere.funtion.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
        return null;
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return null;
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return null;
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
    public List<T> toList() {
        return null;
    }

    @Override
    public List<T> toSet() {
        return null;
    }

    @Override
    public <K, V> Map<K, List<V>> singleGroupBy(Function<T, K> function) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> groupBy(Function<T, K> function) {
        return null;
    }

}
