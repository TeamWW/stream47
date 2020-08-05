package com.lucifiere.stream;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.lucifiere.funtion.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Map流，处理ENTRY成员
 *
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public class MapStream<K, V, T extends Map.Entry<K, V>> implements Stream<T> {

    private final Map<K, V> innerMap;

    public MapStream(Map<K, V> innerMap) {
        Preconditions.checkNotNull(innerMap);
        this.innerMap = innerMap;
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        Map<K, V> mapAfterTransfer = new HashMap<>(innerMap.size());
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            if (predicate.test(entry)) {
                mapAfterTransfer.put(entry.getKey(), entry.getValue());
            }
        }
        return Streams.of(mapAfterTransfer);
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
    public <R, A> R collect(Supplier<A> supplier) {
        return null;
    }
}
