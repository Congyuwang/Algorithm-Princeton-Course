package princeton.algorithm.queue;

import java.util.Iterator;

/**
 * The LinkedQueue class implements an iterable FIFO queue data structure.
 *
 * @param <Item> The type of objects in the queue
 */
public class LinkedQueue<Item> implements Iterable<Item> {

    Node first = null;
    Node last = null;

    private class Node {
        Item item;
        Node next;
    }

    // remove the first item
    public Item dequeue() throws Exception {
        if (isEmpty()) {
            throw new Exception("QueueUnderFlow!");
        }
        Item item = first.item;
        first = first.next;
        return item;
    }

    // insert after the last item
    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
    }

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
            Item currentItem = current.item;
            current = current.next;
            return currentItem;
        }
    }
}
