package princeton.algo.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import princeton.algo.sort.Shuffle;
import princeton.algo.sort.Util;

/**
 * The ArrayQueue class implements an iterable FIFO queue data structure
 * using Array data structure, which features constant amortized time
 * for each operation.
 *
 * @param <Item> The type of objects in the queue
 */
public class ArrayQueue<Item> implements Queue<Item> {
    // count represents the total number of items
    // head represents the position of the first item
    // tail represents the position of the next item to be filled
    private int count = 0;
    private int head = 0;
    private int tail = 0;

    // cannot implement generic array. Use cast.
    @SuppressWarnings("unchecked")
    private Item[] s = (Item[]) new Object[1];

    @Override
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        // reset tail pointer if tail exceeds index limit
        if (tail == s.length) {
            tail = 0;
        }
        s[tail++] = item;
        // resize array if the size limit is met
        if (++count == s.length) {
            resize(s.length << 1);
        }
    }

    @Override
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("QueueUnderFlow!");
        }
        if (head == s.length) {
            head = 0;
        }
        Item item = s[head];
        s[head++] = null;
        // resize the array if it is too empty
        if (--count == s.length >>> 2 && count > 0) {
            resize(s.length >>> 1);
        }
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            return null;
        }
        return s[head];
    }

    public void shuffle() {
        if (count <= 1) {
            return;
        }
        if (head < tail) {
            Shuffle.shuffle(s, head, tail);
        } else {
            Random random = new Random();
            int j = head + 1;
            for (int i = 1; i < count; i++) {
                if (j == s.length) {
                    j = 0;
                }
                if (j > head) {
                    Util.exch(s, j, head + random.nextInt(i + 1));
                } else {
                    int r1 = head + random.nextInt(s.length - head);
                    int r2 = random.nextInt(j + 1);
                    if (random.nextDouble() < (double) (j + 1) / (i + 1)) {
                        Util.exch(s, j, r2);
                    } else {
                        Util.exch(s, j, r1);
                    }
                }
                j++;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    private void resize(int capacity) {
        // cannot implement generic array. Use cast.
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[capacity];
        if (head >= tail) {
            System.arraycopy(s, head, copy, 0, s.length - head);
            System.arraycopy(s, 0, copy, s.length - head, tail);
        } else {
            System.arraycopy(s, head, copy, 0, count);
        }
        head = 0;
        tail = count;
        s = copy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<Item> {

        // i point to
        private int i = head;
        private int j = count;

        @Override
        public boolean hasNext() {
            return j > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            j--;
            if (i == s.length) {
                i = 0;
            }
            return s[i++];
        }
    }

    @Override
    public int size() {
        return count;
    }
}
