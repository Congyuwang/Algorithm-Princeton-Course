package princeton.algo.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Deque class is an iterable linked list data structure that
 * allows addFirst, addLast, removeFirst, and removeLast methods.
 */
public class Deque<Item> implements Queue<Item> {

    private Node first = null;
    private Node last = null;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    /**
     * Add an {@code item} in front of the linked list.
     * @param item the item to be added.
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        Node oldFirst = first;
        first = new Node();
        if (!isEmpty()) {
            oldFirst.previous = first;
        } else {
            last = first;
        }
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        Node oldLast = last;
        last = new Node();
        if (!isEmpty()) {
            oldLast.next = last;
        } else {
            first = last;
        }
        last.item = item;
        last.previous = oldLast;
        last.next = null;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("QueueUnderFlow!");
        }
        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.previous = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        Item item = last.item;
        last = last.previous;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new LinkedDequeIterator();
    }

    private class LinkedDequeIterator implements Iterator<Item> {

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
    public Item dequeue() {
        return removeFirst();
    }

    @Override
    public void enqueue(Item item) throws IllegalArgumentException {
        addLast(item);
    }

    /**
     * insert an item into the queue.
     *
     * @param item the item to be inserted
     * @param i    position parameter. 0 means add in front, size means add in the
     *             end.
     */
    public void insert(Item item, int i) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("index out of range!");
        }
        if (i == size) {
            addLast(item);
            return;
        }
        if (i == 0) {
            addFirst(item);
            return;
        }
        Node prev = first;
        for (int k = 1; k < i; k++) {
            prev = prev.next;
        }
        Node node = new Node();
        node.item = item;
        node.next = prev.next;
        node.previous = prev;
        prev.next = node;
        node.next.previous = node;
        size++;
    }
}
