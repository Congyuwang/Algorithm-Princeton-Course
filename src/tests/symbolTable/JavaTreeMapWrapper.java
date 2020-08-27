package tests.symbolTable;

import princeton.algo.symbolTable.Pair;
import princeton.algo.symbolTable.SymbolTable;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * This Class {@link JavaTreeMapWrapper} wraps the Java Native {@link java.util.TreeMap} Class;
 * @param <K> key type, requires to be comparable with itself
 * @param <V> value type
 */
public class JavaTreeMapWrapper<K extends Comparable<? super K>, V> implements SymbolTable<K, V> {

    private final TreeMap<K, V> tree = new TreeMap<>();

    /**
     * Put a new key value pair into the Symbol table. This API does not allow
     * putting null as values.
     *
     * @param key   A comparable key
     * @param value A value stored
     * @throws NullPointerException if key or value is null
     */
    @Override
    public void put(K key, V value) throws NullPointerException {
        tree.put(key, value);
    }

    /**
     * Obtain the value for the provided key
     *
     * @param key the key to search for
     * @return returns null if value not found
     * @throws NullPointerException if key is null
     */
    @Override
    public V get(K key) throws NullPointerException {
        return tree.get(key);
    }

    /**
     * Delete a key and its value
     *
     * @param key the key to delete
     * @throws NullPointerException if key is null
     */
    @Override
    public void delete(K key) throws NullPointerException {
        tree.remove(key);
    }

    /**
     * Check whether a key exists
     *
     * @param key the key to check
     * @return true if the key exists
     * @throws NullPointerException if key is null
     */
    @Override
    public boolean contains(K key) throws NullPointerException {
        return tree.containsKey(key);
    }

    /**
     * Check if the table is empty
     *
     * @return if the table is empty, return true
     */
    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    /**
     * Get the number of elements in the Symbol Table
     *
     * @return the number of elements in the table
     */
    @Override
    public int size() {
        return tree.size();
    }

    /**
     * Return an iterable object of keys
     *
     * @return an iterable containing the keys of the SymbolTable
     */
    @Override
    public Iterable<K> keys() {
        return tree.keySet();
    }

    /**
     * Return an iterable of key-value pairs ({@code Pair} object).
     *
     * @return an iterable of key-value {@code Pair}s.
     */
    @Override
    public Iterable<Pair<K, V>> pairs() {
        Iterator<Map.Entry<K, V>> entryIterator = tree.entrySet().iterator();
        return () -> new Iterator<>() {
            @Override
            public boolean hasNext() {
                return entryIterator.hasNext();
            }
            @Override
            public Pair<K, V> next() {
                Map.Entry<K, V> e = entryIterator.next();
                return new Pair<>(e.getKey(), e.getValue());
            }
        };
    }
}
