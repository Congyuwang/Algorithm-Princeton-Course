package princeton.algo.symbolTable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code SequentialSearchST} class implements a SymbolTable
 * using sequential search. This is a very slow data structure.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class SequentialSearchST<K, V> implements SymbolTable<K, V> {

    private Node first;
    private int size = 0;
    private int memorySize = 0;
    private Node cacheNode;

    private final class Node {
        final K key;
        V value;
        final Node next;

        Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("null key");
        }
        if (value == null) {
            throw new NullPointerException("null value unsupported");
        }
        privatePut(key, value);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("null key");
        }
        if (cacheNode != null && key.equals(cacheNode.key)) {
            return cacheNode.value;
        }
        for (Node i = first; i != null; i = i.next) {
            if (key.equals(i.key)) {
                return i.value;
            }
        }
        return null;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new NullPointerException("null key");
        }
        privatePut(key, null);
        size--;
        if (size > 0 && memorySize / size >= 2) {
            shrink();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<K> keys() {
        return PrivateIterator::new;
    }

    private final class PrivateIterator implements Iterator<K> {
        int counter = size;
        Node current = first;

        @Override
        public boolean hasNext() {
            return counter > 0;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more keys in table");
            }
            Node temp;
            do {
                temp = current;
                current = current.next;
            } while (temp.value == null);
            counter--;
            return temp.key;
        }
    }

    private void privatePut(K key, V value) {
        // use cache Node
        if (cacheNode != null && key.equals(cacheNode.key)) {
            cacheNode.value = value;
            return;
        }
        for (Node i = first; i != null; i = i.next) {
            if (key.equals(i.key)) {
                if (i.value == null && value != null) {
                    size++;
                }
                i.value = value;
                cacheNode = i;
                return;
            }
        }
        if (value != null) {
            first = new Node(key, value, first);
            cacheNode = first;
            memorySize++;
            size++;
        }
    }

    private void shrink() {
        SequentialSearchST<K, V> tempTable = new SequentialSearchST<>();
        for (K key : keys()) {
            tempTable.put(key, get(key));
        }
        this.cacheNode = null;
        this.first = tempTable.first;
        this.memorySize = size;
    }
}
