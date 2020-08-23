package princeton.algo.symbolTable;

/**
 * This class represent key value pairs.
 * It is used for iterate through key-value pairs.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class Pair<K, V> {
    protected final K key;
    protected V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
