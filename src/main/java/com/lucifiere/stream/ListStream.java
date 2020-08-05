package com.lucifiere.stream;

import com.google.common.base.Optional;
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

    public Stream<T> filter(Predicate<? super T> predicate) {
        return null;
    }

    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return null;
    }

    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return null;
    }

    public Stream<T> distinct() {
        return null;
    }

    public Stream<T> sorted() {
        return null;
    }

    public Stream<T> sorted(Comparator<? super T> comparator) {
        return null;
    }

    public Stream<T> limit(long maxSize) {
        return null;
    }

    public Stream<T> skip(long n) {
        return null;
    }

    public void forEach(Consumer<? super T> action) {

    }

    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return null;
    }

    public Optional<T> min(Comparator<? super T> comparator) {
        return null;
    }

    public Optional<T> max(Comparator<? super T> comparator) {
        return null;
    }

    public long count() {
        return 0;
    }

    public boolean anyMatch(Predicate<? super T> predicate) {
        return false;
    }

    public boolean allMatch(Predicate<? super T> predicate) {
        return false;
    }

    public boolean noneMatch(Predicate<? super T> predicate) {
        return false;
    }

    public Optional<T> findFirst() {
        return null;
    }

    public Optional<T> findAny() {
        return null;
    }

    public <R, A> R collect(Supplier<A> supplier) {
        return null;
    }

}
