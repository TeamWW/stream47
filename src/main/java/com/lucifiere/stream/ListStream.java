package com.lucifiere.stream;

import com.google.common.base.Optional;
import com.lucifiere.funtion.BinaryOperator;
import com.lucifiere.funtion.Consumer;
import com.lucifiere.funtion.Function;
import com.lucifiere.funtion.Predicate;

import java.util.*;

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
        List<T> list = new ArrayList<>();
        for (T t : innerList) {
            if (predicate.test(t)) {
                list.add(t);
            }
        }
        return Streams.of(list);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        List<R> rList = new ArrayList<>();
        for (T t : innerList) {
            rList.add(mapper.apply(t));
        }
        return Streams.of(rList);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, List<? extends R>> mapper) {
        List<R> rList = new ArrayList<>();
        for (T t : innerList) {
            rList.addAll(mapper.apply(t));
        }
        return Streams.of(rList);
    }

    @Override
    public Stream<T> distinct() {
        Set<T> set = new HashSet<>();
        for (T t : innerList) {
            set.add(t);
        }
        return Streams.of(new ArrayList<>(set));
    }

    @Override
    public Stream<T> sorted() {
        T[] array = (T[]) innerList.toArray();
        Arrays.sort(array);
        return Streams.of(Arrays.asList(array));
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        T[] array = (T[]) innerList.toArray();
        Arrays.sort(array, comparator);
        return Streams.of(Arrays.asList(array));
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
        for (T t : innerList) {
            action.accept(t);
        }
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
        return innerList.size();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        for (T t : innerList) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        for (T t : innerList) {
            if (!predicate.test(t)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        for (T t : innerList) {
            if (predicate.test(t)) {
                return false;
            }
        }
        return true;
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
        return innerList;
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
