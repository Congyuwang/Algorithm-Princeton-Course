package princeton.algorithm.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The LinkedStack class implements an iterable LIFO stack data structure.
 *
 * @param <Item> The type of objects in the stack
 */
public class LinkedStack<Item> implements Stack<Item> {
    private Node first = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void push(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    @Override
    public Item pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedStackIterator();
    }

    private class LinkedStackIterator implements Iterator<Item> {

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

    @Override
    public int size() {
        return size;
    }
}
