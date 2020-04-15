package princeton.algorithm.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Deque class is an iterable linked list data structure that
 * allows addFirst, addLast, removeFirst, and removeLast methods.
 */
public class Deque<Item> implements Iterable<Item> {

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
            throw new NoSuchElementException("StackUnderFlow!");
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
            Item currentItem = current.item;
            current = current.next;
            return currentItem;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.printf("isEmpty = %b\n", deque.isEmpty());
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);
        System.out.printf("isEmpty = %b\n", deque.isEmpty());
        System.out.printf("Size = %d\n", deque.size());
        deque.forEach(i -> System.out.printf("%d ", i));
        System.out.println();
        System.out.printf("remove First = %d\n", deque.removeFirst());
        System.out.printf("remove Last = %d\n", deque.removeLast());
        deque.forEach(i -> System.out.printf("%d ", i));
        System.out.println();
        System.out.printf("isEmpty = %b\n", deque.isEmpty());
        System.out.printf("Size = %d\n", deque.size());
    }
}
