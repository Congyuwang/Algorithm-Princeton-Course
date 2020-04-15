package princeton.algo.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
            throw new IllegalArgumentException("null item not allowed!");
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
}
