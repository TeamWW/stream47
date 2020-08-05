package com.lucifiere.stream;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lucifiere.funtion.BiConsumer;
import com.lucifiere.funtion.BiFunction;
import com.lucifiere.funtion.Function;
import com.lucifiere.funtion.Predicate;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author created by XD.Wang
 * Date 2020/8/5.
 */
public class MapStream<K, V> implements SortedBiStream<K, V> {

    /**
     * 内部映射
     */
    private final Map<K, V> innerMap;

    /**
     * 游标
     */
    private final int cursor;

    /**
     * 上限
     */
    private final int limit;

    public MapStream(Map<K, V> innerMap) {
        this.innerMap = innerMap;
        this.cursor = 0;
        this.limit = innerMap.size() - 1;
    }

    public MapStream(Map<K, V> innerMap, int cursor) {
        this.innerMap = innerMap;
        this.cursor = cursor;
        this.limit = innerMap.size() - 1;
    }

    public MapStream(Map<K, V> innerMap, int cursor, int limit) {
        this.innerMap = innerMap;
        this.cursor = cursor;
        this.limit = limit;
    }

    public static <K, V> BiStream<K, V> ofBiStream(Map<K, V> innerMap) {
        return new MapStream<>(Optional.fromNullable(innerMap).or(Maps.<K, V>newConcurrentMap()));
    }

    private static <K extends Comparable<K>, V> SortedBiStream<K, V> ofSortedStream(Map<K, V> innerMap) {
        if (innerMap == null || innerMap.size() == 0) {
            return new MapStream<>(new TreeMap<K, V>());
        }
        Map<K, V> newMap = new TreeMap<>(innerMap);
        return new MapStream<>(newMap);
    }

    private static <K, V> SortedBiStream<K, V> ofSortedStream(Map<K, V> innerMap, Comparator<? super K> comparator) {
        return new MapStream<>(toSortMap(innerMap, comparator));
    }

    private static <K, V> TreeMap<K, V> toSortMap(Map<K, V> innerMap, Comparator<? super K> comparator) {
        if (innerMap == null || innerMap.size() == 0) {
            return new TreeMap<>(comparator);
        }
        if (innerMap instanceof TreeMap) {
            return (TreeMap<K, V>) innerMap;
        }
        TreeMap<K, V> map = new TreeMap<>(comparator);
        map.putAll(innerMap);
        return map;
    }

    @Override
    public <C extends Comparable<C>> SortedBiStream<C, V> sorted() {
        return null;
    }

    @Override
    public SortedBiStream<K, V> sorted(Comparator<? super K> comparator) {
        return ofSortedStream(innerMap, comparator);
    }

    @Override
    public SortedBiStream<K, V> limit(int maxSize) {
        return new MapStream<>(innerMap, 0, maxSize);
    }

    @Override
    public SortedBiStream<K, V> skip(int n) {
        return new MapStream<>(innerMap, cursor + n);
    }

    @Override
    public Optional<Map.Entry<K, V>> min(Comparator<? super K> comparator) {
        TreeMap<K, V> map = toSortMap(innerMap, comparator);
        return Optional.fromNullable(map.firstEntry());
    }

    @Override
    public Optional<Map.Entry<K, V>> max(Comparator<? super K> comparator) {
        TreeMap<K, V> map = toSortMap(innerMap, comparator);
        return Optional.fromNullable(map.lastEntry());
    }

    @Override
    public Optional<Map.Entry<K, V>> findAny() {
        return null;
    }

    @Override
    public BiStream<K, V> filter(BiFunction<K, V, Boolean> predicate) {
        Map<K, V> mapAfterTransfer = new HashMap<>(innerMap.keySet().size());
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            if (predicate.apply(entry.getKey(), entry.getValue())) {
                mapAfterTransfer.put(entry.getKey(), entry.getValue());
            }
        }
        return ofBiStream(mapAfterTransfer);
    }

    @Override
    public BiStream<K, V> filterKey(Predicate<? super K> keyPredicate) {
        Map<K, V> mapAfterTransfer = new HashMap<>(innerMap.keySet().size());
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            if (keyPredicate.test(entry.getKey())) {
                mapAfterTransfer.put(entry.getKey(), entry.getValue());
            }
        }
        return ofBiStream(mapAfterTransfer);
    }

    @Override
    public BiStream<K, V> filterVal(Predicate<? super V> valPredicate) {
        Map<K, V> mapAfterTransfer = new HashMap<>(innerMap.keySet().size());
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            if (valPredicate.test(entry.getValue())) {
                mapAfterTransfer.put(entry.getKey(), entry.getValue());
            }
        }
        return ofBiStream(mapAfterTransfer);
    }

    @Override
    public BiStream<K, V> compute(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public BiStream<K, V> computeIfAbsent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public BiStream<K, V> computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Preconditions.checkNotNull(remappingFunction);
        Map<K, V> mapAfterTransfer = new HashMap<>(innerMap.keySet().size());
        V oldValue;
        if ((oldValue = innerMap.get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                mapAfterTransfer.put(key, newValue);
            } else {
                innerMap.remove(key);
            }
        }
        return ofBiStream(innerMap);
    }

    @Override
    public <NK, NV> BiStream<NK, NV> map(Function<? super K, ? extends NK> keyMapper, Function<? super V, ? extends NV> valMapper) {
        Map<NK, NV> mapAfterTransfer = new HashMap<>(innerMap.keySet().size());
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            NK newKey = keyMapper.apply(entry.getKey());
            NV newVal = valMapper.apply(entry.getValue());
            mapAfterTransfer.put(newKey, newVal);
        }
        return ofBiStream(mapAfterTransfer);
    }

    @Override
    public <NK> BiStream<NK, V> mapKey(Function<? super K, ? extends NK> keyMapper) {
        Map<NK, V> mapAfterTransfer = new HashMap<>(innerMap.keySet().size());
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            NK newKey = keyMapper.apply(entry.getKey());
            V newVal = entry.getValue();
            mapAfterTransfer.put(newKey, newVal);
        }
        return ofBiStream(mapAfterTransfer);
    }

    @Override
    public <NV> BiStream<K, NV> mapVal(Function<? super V, ? extends NV> valMapper) {
        Map<K, NV> mapAfterTransfer = new HashMap<>(innerMap.keySet().size());
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            K newKey = entry.getKey();
            NV newVal = valMapper.apply(entry.getValue());
            mapAfterTransfer.put(newKey, newVal);
        }
        return ofBiStream(mapAfterTransfer);
    }

    @Override
    public <NV, NK> BiStream<NK, NV> flatMap(Function<? super K, ? extends NK> keyMapper, Function<? super V, ? extends NV> valMapper) {
        return null;
    }

    @Override
    public BiStream<K, V> distinct() {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean anyMatch(BiFunction<? super K, ? super V, Boolean> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(BiFunction<? super K, ? super V, Boolean> predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(BiFunction<? super K, ? super V, Boolean> predicate) {
        return false;
    }

    @Override
    public Map<K, V> toMap() {
        return innerMap;
    }

}
