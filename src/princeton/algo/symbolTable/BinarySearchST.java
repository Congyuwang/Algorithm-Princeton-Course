package princeton.algo.symbolTable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code BinarySearchST} class implements a symbol table using
 * binary search for {@code get()} method.
 *
 * @param <K> the key type, must be comparable to itself
 * @param <V> the value type
 */
public class BinarySearchST<K extends Comparable<? super K>, V> implements OrderedSymbolTable<K, V> {

    private static final int INIT_SIZE = 2;
    private K[] keys;
    private V[] values;
    private K cacheKey;
    private int cacheRank;
    private int size;

    /**
     * Create an empty symbol table
     */
    public BinarySearchST() {
        this(INIT_SIZE);
    }

    /**
     * Create an empty symbol table with specified initial capacity
     *
     * @param capacity the initial capacity of the symbol table
     */
    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        keys = (K[]) new Comparable[capacity];
        values = (V[]) new Object[capacity];
    }

    @Override
    public int rank(K key) {
        if (key == null) {
            throw new NullPointerException("null key");
        }
        if (isEmpty()) {
            return 0;
        }
        if (key.equals(cacheKey)) {
            return cacheRank;
        }
        cacheKey = key;
        int lo = 0;
        int hi = size - 1;
        int mid;
        while (lo <= hi) {
            mid = (lo + hi) >>> 1;
            if (key.compareTo(keys[mid]) > 0) {
                lo = mid + 1;
            } else if (key.compareTo(keys[mid]) < 0) {
                hi = mid - 1;
            } else {
                cacheRank = mid;
                return mid;
            }
        }
        /*
         * If it falls out of the loop, the key is not found. If during the loop, key >
         * keys[hi], then ultimately lo = hi + 1. If during the loop, key < keys[lo],
         * then ultimately hi = lo - 1. In both cases, keys[hi] < key < keys[lo] (or lo
         * = keys.length). We always have lo = number of keys less than key.
         */
        cacheRank = lo;
        return lo;
    }

    @Override
    public void put(K key, V value) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("null key");
        }
        if (value == null) {
            throw new NullPointerException("null value unsupported");
        }
        int i = rank(key);
        if (i < size && key.compareTo(keys[i]) == 0) {
            values[i] = value;
        } else {
            if (size == keys.length) {
                resize(keys.length << 1);
            }
            System.arraycopy(keys, i, keys, i + 1, size - i);
            System.arraycopy(values, i, values, i + 1, size - i);
            keys[i] = key;
            values[i] = value;
            cacheKey = key;
            cacheRank = i;
            size++;
        }
    }

    @Override
    public V get(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("null key");
        }
        int i = rank(key);
        if (i < size && key.compareTo(keys[i]) == 0) {
            return values[i];
        }
        return null;
    }

    @Override
    public void delete(K key) throws NullPointerException {
        if (isEmpty()) {
            return;
        }
        int i = rank(key);
        if (i < size && key.equals(keys[i])) {
            System.arraycopy(keys, i + 1, keys, i, size - i - 1);
            System.arraycopy(values, i + 1, values, i, size - i - 1);
            size--;
        }
        if (size < (keys.length >>> 2)) {
            resize(keys.length >>> 1);
        }
        cacheKey = null;
        cacheRank = -1;
    }

    @Override
    public K floor(K key) {
        if (key == null) {
            throw new NullPointerException("null key");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("empty table");
        }
        int i = rank(key);
        if (key.equals(keys[i])) {
            return keys[i];
        }
        if (i > 0) {
            return keys[i - 1];
        }
        throw new NoSuchElementException("no element smaller than key");
    }

    @Override
    public K ceiling(K key) {
        if (key == null) {
            throw new NullPointerException("null key");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("empty table");
        }
        int i = rank(key);
        if (i == size) {
            throw new NoSuchElementException("no element greater than key");
        }
        return keys[i];
    }

    @Override
    public K select(int rank) {
        if (rank < 0 || rank >= size) {
            if (isEmpty()) {
                throw new NoSuchElementException("empty table");
            }
            throw new IllegalArgumentException("rank out of bound");
        }
        cacheKey = keys[rank];
        cacheRank = rank;
        return cacheKey;
    }

    @Override
    public Iterable<K> keys() {
        return new ArrayIterable<>(Arrays.copyOfRange(keys, 0, size));
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        if (lo == null) {
            throw new NullPointerException("key lo is null");
        }
        if (hi == null) {
            throw new NullPointerException("key hi is null");
        }
        if (lo.compareTo(hi) > 0) {
            return new EmptyArrayIterator<>();
        }
        final int rank_lo = rank(lo);
        final int rank_hi;
        if (contains(hi)) {
            rank_hi = rank(hi) + 1;
        } else {
            rank_hi = rank(hi);
        }
        return new ArrayIterable<>(Arrays.copyOfRange(keys, rank_lo, rank_hi));
    }

    /**
     * Return an iterable of pairs from lo to hi
     *
     * @param lo the lower bound, inclusive
     * @param hi the upper bound, inclusive
     * @return the iterator iterating key-value pairs from lo to hi
     * @throws NullPointerException if any key is null
     */
    @Override
    public Iterable<Pair<K, V>> pairs(K lo, K hi) throws NullPointerException {
        if (lo == null) {
            throw new NullPointerException("key lo is null");
        }
        if (hi == null) {
            throw new NullPointerException("key hi is null");
        }
        if (lo.compareTo(hi) > 0) {
            return new EmptyArrayIterator<>();
        }
        final int rank_lo = rank(lo);
        final int rank_hi;
        if (contains(hi)) {
            rank_hi = rank(hi) + 1;
        } else {
            rank_hi = rank(hi);
        }
        return new PairsIterable<>(Arrays.copyOfRange(keys, rank_lo, rank_hi),
                Arrays.copyOfRange(values, rank_lo, rank_hi));
    }

    @Override
    public Iterable<Pair<K, V>> pairs() {
        return new PairsIterable<>(Arrays.copyOfRange(keys, 0, size),
                Arrays.copyOfRange(values, 0, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public K min() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty table");
        }
        cacheKey = keys[0];
        cacheRank = 0;
        return cacheKey;
    }

    @Override
    public K max() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty table");
        }
        cacheKey = keys[size - 1];
        cacheRank = size - 1;
        return cacheKey;
    }

    private void resize(int newSize) {

        @SuppressWarnings("unchecked")
        K[] keysCopy = (K[]) Array.newInstance(keys.getClass().getComponentType(), newSize);
        System.arraycopy(keys, 0, keysCopy, 0, size);
        keys = keysCopy;

        @SuppressWarnings("unchecked")
        V[] valuesCopy = (V[]) Array.newInstance(values.getClass().getComponentType(), newSize);
        System.arraycopy(values, 0, valuesCopy, 0, size);
        values = valuesCopy;

    }

    private static class EmptyArrayIterator<T> implements Iterable<T> {
        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public T next() {
                    throw new NoSuchElementException();
                }
            };
        }
    }

    private static class ArrayIterable<T> implements Iterable<T> {
        private final T[] keyArray;

        ArrayIterable(T[] keyArray) {
            this.keyArray = keyArray;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {
                int pointer = 0;

                @Override
                public boolean hasNext() {
                    return pointer < keyArray.length;
                }

                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return keyArray[pointer++];
                }
            };
        }
    }

    private static class PairsIterable<K, V> implements Iterable<Pair<K, V>> {
        private final K[] keyArray;
        private final V[] valueArray;

        PairsIterable(K[] keyArray, V[] valueArray) {
            this.keyArray = keyArray;
            this.valueArray = valueArray;
        }

        @Override
        public Iterator<Pair<K, V>> iterator() {
            return new Iterator<>() {
                int pointer = 0;

                @Override
                public boolean hasNext() {
                    return pointer < keyArray.length;
                }

                @Override
                public Pair<K, V> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return new Pair<>(keyArray[pointer++], valueArray[pointer++]);
                }
            };
        }
    }
}
