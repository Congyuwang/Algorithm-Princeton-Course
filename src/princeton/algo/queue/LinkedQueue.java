package princeton.algo.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * The LinkedQueue class implements an iterable FIFO queue data structure
 * using LinkedList data structure, which features constant runtime
 * in the worst for each operations.
 *
 * @param <Item> The type of objects in the queue
 */
public class LinkedQueue<Item> implements Queue<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    // remove the first item
    @Override
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("QueueUnderFlow!");
        }
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    // insert after the last item
    @Override
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("null item not allowed!");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    /**
     * insert an item into the queue.
     * @param item the item to be inserted
     * @param i position parameter. 0 means add in front, size means add in the end.
     */
    public void insert(Item item, int i) {
        if (item == null) {
            throw new NullPointerException("null item not allowed!");
        }
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("index out of range!");
        }
        if (i == size) {
            enqueue(item);
            return;
        }
        if (i == 0) {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            size++;
            return;
        }
        Node prev = first;
        for (int k = 1; k < i; k++) {
            prev = prev.next;
        }
        Node node = new Node();
        node.item = item;
        node.next = prev.next;
        prev.next = node;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedQueueIterator();
    }

    private class LinkedQueueIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item currentItem = current.item;
            current = current.next;
            return currentItem;
        }
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Shuffle the LinkedQueue in N log N time with no extra memory.
     */
    public void shuffle() {
        LinkedQueue<Item> deque = new LinkedQueue<>();
        deque.first = first;
        deque.last = last;
        deque.size = size;
        Random random = new Random();
        LinkedQueue<Item> shuffled = shuffle(deque, random);
        first = shuffled.first;
        last = shuffled.last;
    }

    private LinkedQueue<Item> shuffle(LinkedQueue<Item> d, Random random) {
        int dSize = d.size;
        if (dSize == 1) {
            return d;
        }
        LinkedQueue<Item> d1 = new LinkedQueue<>();
        LinkedQueue<Item> d2 = new LinkedQueue<>();
        boolean alternate = true;
        for (int i = 0; i < dSize; i++) {
            if (alternate) {
                d1.enqueue(d.dequeue());
                alternate = false;
            } else {
                d2.enqueue(d.dequeue());
                alternate = true;
            }
        }
        return merge(shuffle(d1, random), shuffle(d2, random), random);
    }

    private LinkedQueue<Item> merge(LinkedQueue<Item> d1, LinkedQueue<Item> d2, Random random) {
        LinkedQueue<Item> deque = new LinkedQueue<>();
        while (d1.size() > 0 || d2.size() > 0) {
            double p = (double) d1.size() / (d1.size() + d2.size());
            if (p == 0) {
                deque.enqueue(d2.dequeue());
            } else if (p == 1 || random.nextDouble() < p) {
                deque.enqueue(d1.dequeue());
            } else {
                deque.enqueue(d2.dequeue());
            }
        }
        return deque;
    }
}
