package princeton.algo.symbolTable;

/**
 * The {@code OrderedSymbolTable} class implements a SymbolTable with comparable
 * keys and provides more API than the SymbolTable class.
 *
 * @param <K> the key type, must be comparable to itself
 * @param <V> the value type
 */
public interface OrderedSymbolTable<K extends Comparable<? super K>, V> extends SymbolTable<K, V> {

    /**
     * Get the smallest key.
     *
     * @return the smallest key, null if the table is empty
     */
    K min();

    /**
     * Get the largest key.
     *
     * @return the largest key, null if the table is empty
     */
    K max();

    /**
     * Delete the smallest key if the table is not empty
    */
    default void deleteMin() {
        delete(min());
    }

    /**
     * Delete the largest key if the table is not empty
     */
    default void deleteMax() {
        delete(max());
    }

    /**
     * Get the number of keys between two keys [lo, hi)
     *
     * @param lo the lower bound, inclusive
     * @param hi the upper bound, inclusive
     * @return the number of keys between lo and hi
     */
    default int size(K lo, K hi) {
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
     * @return the floor key found, null if no such key
     */
    K floor(K key);

    /**
     * The smallest key greater than or equal to the given key
     *
     * @param key the given key
     * @return the key found, null if no such key
     */
    K ceiling(K key);

    /**
     * Select the key ranking the number rank.
     *
     * @param rank the number of rank
     * @return the key of that rank
     */
    K select(int rank);

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
     * @param hi the upper bound, exclusive
     * @return the iterator iterating from lo to hi
     * @throws NullPointerException if any key is null
     */
    Iterable<K> keys(K lo, K hi) throws NullPointerException;
}
