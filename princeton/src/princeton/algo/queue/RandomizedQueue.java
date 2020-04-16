package princeton.algo.queue;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

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
        if (size == s.length) {
            grow(s.length << 1);
        }
        s[size++] = item;
    }

    /**
     * {@code dequeue()} returns a random item from the Queue and remove it.
     * After each removal, fill in the gap using the last item in the Queue.
     *
     * @return a random item from the Queue.
     */
    @Override
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        if (size == s.length >> 2) {
            grow(s.length >> 1);
        }
        int i = StdRandom.uniform(size);
        Item item = s[i];
        s[i] = s[--size];
        s[size] = null;
        return item;
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
        return s[StdRandom.uniform(size)];
    }

    private void grow(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(s, 0, copy, 0, size);
        s = copy;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] shuffledItem = randomizeCondense();
        int i = shuffledItem.length;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return shuffledItem[--i];
        }
    }

    private Item[] randomizeCondense() {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[size];
        System.arraycopy(s, 0, copy, 0, size);
        // shuffle
        for (int i = 1; i < size; i++) {
            int j = StdRandom.uniform(i + 1);
            Item temp = copy[j];
            copy[j] = copy[i];
            copy[i] = temp;
        }
        return copy;
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        int RANGE = 10;
        int ROUND = 10000;
        for (int i = 0; i < RANGE; i++) {
            randomizedQueue.enqueue(i);
        }
        int[][] uniformChecker = new int[RANGE][RANGE];
        for (int round = 0; round < ROUND; round++) {
            int pos = 0;
            for (int s : randomizedQueue) {
                uniformChecker[pos++][s]++;
            }
        }
        for (int[] a : uniformChecker) {
            for (int s : a) {
                System.out.printf("%6d ", s);
            }
            System.out.println();
        }
        double squaredSum = 0;
        double expected = (double) ROUND / RANGE;
        for (int i = 0; i < RANGE; i++) {
            for (int j = 0; j < RANGE; j++) {
                squaredSum += Math.pow(uniformChecker[i][j] - expected, 2);
            }
        }
        int degreeOfFreedom = (RANGE - 1) * (RANGE - 1);
        double chiSquared = squaredSum / expected;
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(degreeOfFreedom);
        System.out.printf("Chi5 = %f\n", squaredSum / expected);
        System.out.printf("p-value = %f\n", chiSquaredDistribution.cumulativeProbability(chiSquared));
    }
}
