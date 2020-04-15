package princeton.queue;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The RandomizedQueue class implements an Iterable data structure that provides
 * uniformly random access to items. {@code enqueue}, {@code dequeue}, {@code sample}
 * all takes amortized constant time. The {@code Iterator} takes linear time
 * to initialize, and each instantiation provides statistically independent
 * shuffle order.
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Queue<Item> {

    @SuppressWarnings("unchecked")
    private Item[] s = (Item[]) new Object[1];
    private int[] vacancyStack = {0};
    private int firstVacancy = 0;
    private int size = 0;

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        if (firstVacancy < 0) {
            grow();
        }
        s[vacancyStack[firstVacancy--]] = item;
        size++;
    }

    /**
     * {@code dequeue()} returns a random item from the Queue and remove it.
     *
     * @return a random item from the Queue.
     */
    @Override
    public Item dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        if (firstVacancy == vacancyStack.length - 1) {
            compress();
        }
        while (true) {
            int i = StdRandom.uniform(s.length);
            Item item = s[i];
            if (item != null) {
                vacancyStack[++firstVacancy] = i;
                s[i] = null;
                size--;
                return item;
            }
        }
    }

    /**
     * {@code sample()} returns a random item from the Queue without
     * removing the item.
     *
     * @return a random item from the Queue
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        while (true) {
            int i = StdRandom.uniform(s.length);
            Item item = s[i];
            if (item != null) {
                return item;
            }
        }
    }

    private void grow() {
        int capacity = size << 1;
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[capacity];
        int[] copyStack = new int[capacity - (capacity >> 2)];
        System.arraycopy(s, 0, copy, 0, size);
        for (int i = size; i < capacity; i++) {
            copyStack[i - size] = i;
        }
        s = copy;
        vacancyStack = copyStack;
        firstVacancy = capacity - size - 1;
    }

    private void compress() {
        int capacity = s.length >> 1;
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[capacity];
        int[] copyStack = new int[capacity - (capacity >> 2)];
        int i = 0;
        for (Item item : s) {
            if (item != null) {
                copy[i++] = item;
            }
        }
        for (i = size; i < capacity; i++) {
            copyStack[i - size] = i;
        }
        s = copy;
        vacancyStack = copyStack;
        firstVacancy = capacity - size - 1;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] shuffledItem = randomizeCondense();
        int i = shuffledItem.length;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return shuffledItem[--i];
        }
    }

    private Item[] randomizeCondense() {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[size];
        int p = 0;
        for (Item item : s) {
            if (item != null) {
                copy[p++] = item;
            }
        }
        // shuffle
        for (int i = size - 1; i > 0; i--) {
            int j = StdRandom.uniform(i + 1);
            Item temp = copy[j];
            copy[j] = copy[i];
            copy[i] = temp;
        }
        return copy;
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue("a");
        randomizedQueue.enqueue("b");
        randomizedQueue.enqueue("c");
        randomizedQueue.enqueue("d");
        randomizedQueue.enqueue("e");
        for (String s : randomizedQueue) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : randomizedQueue) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(randomizedQueue.dequeue() + " ");
        }
    }
}
