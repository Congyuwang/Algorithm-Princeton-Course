package princeton.algo.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * The LinkedStack class implements an iterable LIFO stack data structure
 * using LinkedList, which features constant time for the worst case.
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
    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    /**
     * insert an item into the stack.
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
        if (i == 0) {
            push(item);
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

    public void shuffle() {
        LinkedStack<Item> stack = new LinkedStack<>();
        stack.first = first;
        stack.size = size;
        Random random = new Random();
        LinkedStack<Item> shuffled = shuffle(stack, random);
        first = shuffled.first;
    }

    private LinkedStack<Item> shuffle(LinkedStack<Item> d, Random random) {
        int dSize = d.size;
        if (dSize == 1) {
            return d;
        }
        LinkedStack<Item> d1 = new LinkedStack<>();
        LinkedStack<Item> d2 = new LinkedStack<>();
        boolean alternate = true;
        for (int i = 0; i < dSize; i++) {
            if (alternate) {
                d1.push(d.pop());
                alternate = false;
            } else {
                d2.push(d.pop());
                alternate = true;
            }
        }
        return merge(shuffle(d1, random), shuffle(d2, random), random);
    }

    private LinkedStack<Item> merge(LinkedStack<Item> d1, LinkedStack<Item> d2, Random random) {
        LinkedStack<Item> stack = new LinkedStack<>();
        while (d1.size() > 0 || d2.size() > 0) {
            double p = (double) d1.size() / (d1.size() + d2.size());
            if (p == 0) {
                stack.push(d2.pop());
            } else if (p == 1 || random.nextDouble() < p) {
                stack.push(d1.pop());
            } else {
                stack.push(d2.pop());
            }
        }
        return stack;
    }
}
