package princeton.algo.symbolTable;

import java.util.NoSuchElementException;

/**
 * The {@code OrderedSymbolTable} class implements a SymbolTable with comparable
 * keys and provides more API than the SymbolTable class. The table does not
 * allow null value.
 *
 * @param <K> the key type, must be comparable to itself
 * @param <V> the value type
 */
public interface OrderedSymbolTable<K extends Comparable<? super K>, V> extends SymbolTable<K, V> {

    /**
     * Get the smallest key.
     *
     * @return the smallest key
     * @throws NoSuchElementException if the table is empty
     */
    K min() throws NoSuchElementException;

    /**
     * Get the largest key.
     *
     * @return the largest key
     * @throws NoSuchElementException if the table is empty
     */
    K max() throws NoSuchElementException;

    /**
     * Delete the smallest key if the table is not empty
     *
     * @throws NoSuchElementException if the table is empty
     */
    default void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("the table is empty");
        }
        delete(min());
    }

    /**
     * Delete the largest key if the table is not empty
     *
     * @throws NoSuchElementException if the table is empty
     */
    default void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("the table is empty");
        }
        delete(max());
    }

    /**
     * Get the number of keys between two keys [lo, hi)
     *
     * @param lo the lower bound, inclusive
     * @param hi the upper bound, inclusive
     * @return the number of keys between lo and hi
     * @throws NullPointerException if any key is null
     */
    default int size(K lo, K hi) {
        if (lo == null) {
            throw new NullPointerException("key lo is null");
        }
        if (hi == null) {
            throw new NullPointerException("key hi is null");
        }
        if (hi.compareTo(lo) < 0) {
            return 0;
        }
        int exclusive = rank(hi) - rank(lo);
        if (contains(hi)) {
            return exclusive + 1;
        }
        return exclusive;
    }

    /**
     * The largest key smaller than or equal to the given key
     *
     * @param key the given key
     * @return the floor key found
     * @throws NullPointerException if the key is null
     * @throws NoSuchElementException if there is no such key, or the table is empty
     */
    K floor(K key) throws NullPointerException, NoSuchElementException;

    /**
     * The smallest key greater than or equal to the given key
     *
     * @param key the given key
     * @return the key found
     * @throws NullPointerException   if the key is null
     * @throws NoSuchElementException if there is no such key, or the table is empty
     */
    K ceiling(K key) throws NullPointerException, NoSuchElementException;

    /**
     * Select the key ranking the number rank.
     *
     * @param rank the number of rank
     * @return the key of that rank
     * @throws IllegalArgumentException if the rank is out bound
     * @throws NoSuchElementException if the table is empty
     */
    K select(int rank) throws IllegalArgumentException, NoSuchElementException;

    /**
     * The number of keys less than the given key
     *
     * @param key the given key
     * @return the number of keys less than {@code key}
     * @throws NullPointerException if key is null
     */
    int rank(K key) throws NullPointerException;

    /**
     * Return an iterable from lo to hi
     *
     * @param lo the lower bound, inclusive
     * @param hi the upper bound, inclusive
     * @return the iterator iterating from lo to hi
     * @throws NullPointerException if any key is null
     */
    Iterable<K> keys(K lo, K hi) throws NullPointerException;
}
