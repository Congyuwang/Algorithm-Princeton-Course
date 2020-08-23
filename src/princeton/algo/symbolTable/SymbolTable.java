package princeton.algo.symbolTable;

/**
 * This is an interface containing the APIs of a symbolTable
 *
 * @param <K> key type
 * @param <V> value type
 */
public interface SymbolTable<K, V> {

    /**
     * Put a new key value pair into the Symbol table. This API does not allow
     * putting null as values.
     *
     * @param key   A comparable key
     * @param value A value stored
     * @throws NullPointerException if key or value is null
     */
    void put(K key, V value) throws NullPointerException;

    /**
     * Obtain the value for the provided key
     *
     * @param key the key to search for
     * @return returns null if value not found
     * @throws NullPointerException if key is null
     */
    V get(K key) throws NullPointerException;

    /**
     * Delete a key and its value
     *
     * @param key the key to delete
     * @throws NullPointerException if key is null
     */
    void delete(K key) throws NullPointerException;

    /**
     * Check whether a key exists
     *
     * @param key the key to check
     * @return true if the key exists
     * @throws NullPointerException if key is null
     */
    default boolean contains(K key) throws NullPointerException {
        return get(key) != null;
    }

    /**
     * Check if the table is empty
     *
     * @return if the table is empty, return true
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Get the number of elements in the Symbol Table
     *
     * @return the number of elements in the table
     */
    int size();

    /**
     * Return an iterable object of keys
     *
     * @return an iterable containing the keys of the SymbolTable
     */
    Iterable<K> keys();

    /**
     * Return an iterable of key-value pairs ({@code Pair} object).
     *
     * @return an iterable of key-value {@code Pair}s.
     */
    Iterable<Pair<K, V>> pairs();
}
