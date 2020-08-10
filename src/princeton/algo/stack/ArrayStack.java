package princeton.algo.stack;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import princeton.algo.sort.Shuffle;

/**
 * The ArrayStack class implements an iterable LIFO stack data structure using Array
 * which features constant amortized time for each operations.
 *
 * @param <Item> The type of objects in the queue
 */
public class ArrayStack<Item> implements Stack<Item> {

    // n represent the next item
    private int N = 0;
    @SuppressWarnings("unchecked")
    private Item[] s = (Item[]) new Object[1];
    private int size = 0;

    @Override
    public void push(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        if (N == s.length) {
            resize(2 * s.length);
        }
        s[N++] = item;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        Item item = s[--N];

        // Avoids Loitering
        s[N] = null;

        // Avoids thrashing problem
        if (N == s.length >>> 2) {
            resize(s.length >>> 1);
        }
        size--;
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            return null;
        }
        return s[N - 1];
    }

    public void shuffle() {
        Shuffle.shuffle(s, 0, size);
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) Array.newInstance(s.getClass().getComponentType(), capacity);
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
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return s[--i];
        }
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * the {@code toArray()} method overrides the default method in Stack, using
     * System.arraycopy which is faster than the iterator.
     *
     * @return a shallow copy of the items
     */
    public Object[] toArray() {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) Array.newInstance(s.getClass().getComponentType(), this.size());
        System.arraycopy(s, 0, copy, 0, size);
        return copy;
    }
}
