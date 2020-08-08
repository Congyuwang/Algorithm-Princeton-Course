package princeton.algo.symbolTable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SequentialSearchST<K, V> implements SymbolTable<K, V> {

    private Node first;
    private int size = 0;

    private final class Node {
        K key;
        V value;
        Node next;

        Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("null key unacceptable");
        }
        if (value == null) {
            throw new NullPointerException("null value unsupported");
        }
        privatePut(key, value);
    }

    private void privatePut(K key, V value) {
        for (Node i = first; i != null; i = i.next) {
            if (key.equals(i.key)) {
                if (i.value == null && value != null) {
                    size++;
                }
                i.value = value;
                return;
            }
        }
        if (value != null) {
            first = new Node(key, value, first);
            size++;
        }
    }

    @Override
    public V get(K key) {
        for (Node i = first; i != null; i = i.next) {
            if (key.equals(i.key)) {
                return i.value;
            }
        }
        return null;
    }

    @Override
    public void delete(K key) {
        privatePut(key, null);
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<K> keys() {
        return new Iterable<K>() {
            @Override
            public Iterator<K> iterator() {
                return new PrivateIterator();
            }
        };
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
}
