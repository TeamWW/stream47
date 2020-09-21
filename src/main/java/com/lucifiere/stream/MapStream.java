package com.lucifiere.stream;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lucifiere.funtion.BiConsumer;
import com.lucifiere.funtion.BiFunction;
import com.lucifiere.funtion.Function;
import com.lucifiere.funtion.Predicate;

import java.util.*;

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

    private static class SortedMapView<K, V> extends TreeMap<K, V> {

        private final MapStream<K, V> stream;

        private final TreeMap<K, V> innerMap;

        private SortedMapView(MapStream<K, V> mapStream) {
            Preconditions.checkArgument(mapStream.innerMap instanceof TreeMap, new UnsupportedOperationException());
            this.stream = mapStream;
            this.innerMap = (TreeMap<K, V>) mapStream.innerMap;
        }

        @Override
        public int size() {
            return stream.limit - stream.cursor;
        }

        @Override
        public boolean containsKey(Object key) {
            return innerMap.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return innerMap.containsValue(value);
        }

        @Override
        public V get(Object key) {
            return innerMap.get(key);
        }

        @Override
        public Comparator<? super K> comparator() {
            return innerMap.comparator();
        }

        @Override
        public K firstKey() {
            return innerMap.firstKey();
        }

        @Override
        public K lastKey() {
            return innerMap.lastKey();
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            innerMap.putAll(map);
        }

        @Override
        public V put(K key, V value) {
            return innerMap.put(key, value);
        }

        @Override
        public V remove(Object key) {
            return innerMap.remove(key);
        }

        @Override
        public void clear() {
            innerMap.clear();
        }

        @Override
        public Object clone() {
            return innerMap.clone();
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            return innerMap.firstEntry();
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            return innerMap.lastEntry();
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            return innerMap.pollFirstEntry();
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            return innerMap.pollLastEntry();
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K key) {
            return innerMap.lowerEntry(key);
        }

        @Override
        public K lowerKey(K key) {
            return innerMap.lowerKey(key);
        }

        @Override
        public Map.Entry<K, V> floorEntry(K key) {
            return innerMap.floorEntry(key);
        }

        @Override
        public K floorKey(K key) {
            return innerMap.floorKey(key);
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K key) {
            return innerMap.ceilingEntry(key);
        }

        @Override
        public K ceilingKey(K key) {
            return innerMap.ceilingKey(key);
        }

        @Override
        public Map.Entry<K, V> higherEntry(K key) {
            return innerMap.higherEntry(key);
        }

        @Override
        public K higherKey(K key) {
            return innerMap.higherKey(key);
        }

        @Override
        public Set<K> keySet() {
            return innerMap.keySet();
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return innerMap.navigableKeySet();
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return innerMap.descendingKeySet();
        }

        @Override
        public Collection<V> values() {
            return innerMap.values();
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return innerMap.entrySet();
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return innerMap.descendingMap();
        }

        @Override
        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return innerMap.subMap(fromKey, fromInclusive, toKey, toInclusive);
        }

        @Override
        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            return innerMap.headMap(toKey, inclusive);
        }

        @Override
        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            return innerMap.tailMap(fromKey, inclusive);
        }

        @Override
        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return innerMap.subMap(fromKey, toKey);
        }

        @Override
        public SortedMap<K, V> headMap(K toKey) {
            return innerMap.headMap(toKey);
        }

        @Override
        public SortedMap<K, V> tailMap(K fromKey) {
            return innerMap.tailMap(fromKey);
        }

        @Override
        public boolean isEmpty() {
            return innerMap.isEmpty();
        }
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
    public Optional<V> compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Preconditions.checkNotNull(remappingFunction);
        V oldValue = innerMap.get(key);

        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            if (oldValue != null || innerMap.containsKey(key)) {
                innerMap.remove(key);
            }
            return Optional.absent();
        } else {
            innerMap.put(key, newValue);
            return Optional.of(newValue);
        }
    }

    @Override
    public Optional<V> computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        Preconditions.checkNotNull(mappingFunction);
        V v;
        if ((v = innerMap.get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                innerMap.put(key, newValue);
                return Optional.of(newValue);
            }
        }
        return Optional.fromNullable(v);
    }

    @Override
    public Optional<V> computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Preconditions.checkNotNull(remappingFunction);
        V oldValue;
        if ((oldValue = innerMap.get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                innerMap.put(key, newValue);
                return Optional.of(oldValue);
            } else {
                innerMap.remove(key);
                return Optional.absent();
            }
        }
        return Optional.absent();
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
    public <NV, NK> BiStream<NK, NV> flatMap(Function<? super K, List<? extends NK>> keyMapper, Function<? super V, List<? extends NV>> valMapper) {
        return null;
    }

    @Override
    public BiStream<K, V> distinct() {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Preconditions.checkNotNull(action);
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            action.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public long count() {
        return innerMap.size();
    }

    @Override
    public boolean anyMatch(BiFunction<? super K, ? super V, Boolean> predicate) {
        Preconditions.checkNotNull(predicate);
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            if (predicate.apply(entry.getKey(), entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(BiFunction<? super K, ? super V, Boolean> predicate) {
        Preconditions.checkNotNull(predicate);
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            if (!predicate.apply(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean noneMatch(BiFunction<? super K, ? super V, Boolean> predicate) {
        Preconditions.checkNotNull(predicate);
        for (Map.Entry<K, V> entry : innerMap.entrySet()) {
            if (predicate.apply(entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<K, V> toMap() {
        return innerMap;
    }

}
