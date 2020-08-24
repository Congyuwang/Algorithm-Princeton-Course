package princeton.algo.symbolTable;

import princeton.algo.binaryHeap.PriorityQueue;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * This class contains utility tools for {@code SymbolTable} class
 * including filtering, computation and so on.
 */
public class TableUtil {

    private static final class valueComparator<K, V extends Comparable<? super V>>
            implements Comparator<Pair<K, V>> {
        @Override
        public int compare(Pair<K, V> o1, Pair<K, V> o2) {
            if (o1 == null || o2 == null) throw new NullPointerException("null Node");
            V v1 = o1.getValue();
            V v2 = o2.getValue();
            if (v1 == null || v2 == null) throw new NullPointerException("null Value for Node");
            return v1.compareTo(v2);
        }
    }

    private static final class valueComparatorReversed<K, V extends Comparable<? super V>>
            implements Comparator<Pair<K, V>> {
        @Override
        public int compare(Pair<K, V> o1, Pair<K, V> o2) {
            if (o1 == null || o2 == null) throw new NullPointerException("null Node");
            V v1 = o1.getValue();
            V v2 = o2.getValue();
            if (v1 == null || v2 == null) throw new NullPointerException("null Value for Node");
            return v2.compareTo(v1);
        }
    }

    /**
     * Return a new table containing K pairs with largest values.
     * The old table is not modified.
     *
     * @param table the table
     * @param topN  parameter topN (in 'topN' values to keep)
     * @param <T>   table type
     * @param <K>   key type
     * @param <V>   value type
     * @return a new table containing topN values, or fewer if the
     *         provided table is smaller than K.
     * @throws IllegalArgumentException if topN < 0
     * @throws InternalError if type {@code T} table cannot be instantiated
     */
    @SuppressWarnings("unchecked")
    public static <T extends SymbolTable<K, V>, K, V extends Comparable<? super V>> T topNValue(T table, int topN) {
        PriorityQueue<Pair<K, V>> PQ = new PriorityQueue<>(Math.max(1, topN), new valueComparatorReversed<>());
        if (topN < 0) throw new IllegalArgumentException("negative topN parameter");
        int firstK = 0;
        for (Pair<K, V> pair : table.pairs()) {
            PQ.enqueue(pair);
            if (firstK >= topN) PQ.remove();
            else firstK++;
        }
        try {
            SymbolTable<K, V> newTable = table.getClass().getConstructor().newInstance();
            for (Pair<K, V> p : PQ) newTable.put(p.getKey(), p.getValue());
            return (T) newTable;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InternalError("Cannot instantiate new table");
        }
    }

    /**
     * Return a new table containing K pairs with smallest values.
     * The old table is not modified.
     *
     * @param table the table
     * @param bottomN  parameter topK (in 'bottomN' values to keep)
     * @param <T>   table type
     * @param <K>   key type
     * @param <V>   value type
     * @return a new table containing topK values, or fewer if the
     *         provided table is smaller than K.
     * @throws IllegalArgumentException if topK < 0
     * @throws InternalError if type {@code T} table cannot be instantiated
     */
    @SuppressWarnings("unchecked")
    public static <T extends SymbolTable<K, V>, K, V extends Comparable<? super V>> T bottomNValue(T table, int bottomN) {
        PriorityQueue<Pair<K, V>> PQ = new PriorityQueue<>(Math.max(1, bottomN), new valueComparator<>());
        if (bottomN < 0) throw new IllegalArgumentException("negative topK parameter");
        int firstK = 0;
        for (Pair<K, V> pair : table.pairs()) {
            PQ.enqueue(pair);
            if (firstK >= bottomN) PQ.remove();
            else firstK++;
        }
        try {
            SymbolTable<K, V> newTable = table.getClass().getConstructor().newInstance();
            for (Pair<K, V> p : PQ) newTable.put(p.getKey(), p.getValue());
            return (T) newTable;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InternalError("Cannot instantiate new table");
        }
    }

    /**
     * Filter the table (i.e. remove all pairs whose key evaluates false for Predicate {@code p}).
     * The operation take n log(n) time with linear extra memory for iterator to store keys.
     *
     * @param table the table to be filtered
     * @param p     the condition for key to be kept
     * @param <T>   table type
     * @param <K>   key type
     * @param <V>   value type
     */
    public static <T extends SymbolTable<K, V>, K, V> void keyFilter(T table, Predicate<K> p) {
        for (K key : table.keys()) {
            if (!p.test(key)) {
                table.delete(key);
            }
        }
    }

    /**
     * Filter the table (i.e. remove all pairs whose value evaluates false for Predicate {@code p}).
     * The operation take n log(n) time with linear extra memory for iterator to store pairs.
     *
     * @param table the table to be filtered
     * @param p     the condition for key to be kept
     * @param <T>   table type
     * @param <K>   key type
     * @param <V>   value type
     */
    public static <T extends SymbolTable<K, V>, K, V> void valueFilter(T table, Predicate<V> p) {
        for (Pair<K, V> pair : table.pairs()) {
            if (!p.test(pair.getValue())) {
                table.delete(pair.getKey());
            }
        }
    }
}
