package princeton.algorithm.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The ArrayStack class implements an iterable LIFO stack data structure.
 *
 * @param <Item> The type of objects in the queue
 */
public class ArrayStack<Item> implements Iterable<Item> {

    // n represent the next item
    int N = 0;
    Item[] s = (Item[]) new Object[1];

    public void push(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        if (N == s.length) {
            resize(2 * s.length);
        }
        s[N++] = item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Item pop() throws Exception {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        Item item = s[--N];

        // Avoids Loitering
        s[N] = null;

        // Avoids thrashing problem
        if (N == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(s, 0, copy, 0, N);
        s = copy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<Item> {

        // i point to
        private int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return s[--i];
        }
    }
}
