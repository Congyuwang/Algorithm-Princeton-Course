package princeton.algorithm.queue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    @SuppressWarnings("unchecked")
    private Item[] s = (Item[]) new Object[1];
    private int[] vacancyStack = {0};
    private int firstVacancy = 0;
    private int size = 0;


    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
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

    // remove and return a random item
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

    // return a random item (but do not remove it)
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

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        int number = StdIn.readInt();
        StdIn.readLine();
        for (int i = 0; i < number; i++) {
            String input = StdIn.readLine();
            randomizedQueue.enqueue(input);
        }
        System.out.println();
        for (String s : randomizedQueue) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : randomizedQueue) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : randomizedQueue) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (int i = 0; i < number; i++) {
            System.out.print(randomizedQueue.dequeue() + " ");
        }
    }
}
