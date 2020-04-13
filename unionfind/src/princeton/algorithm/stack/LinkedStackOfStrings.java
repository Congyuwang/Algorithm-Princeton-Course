package princeton.algorithm.stack;

import java.util.NoSuchElementException;

/**
 * This is an implementation of linked stack of strings
 */
public class LinkedStackOfStrings {

    private Node first = null;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(String item) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    public String pop() throws Exception {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        String item = first.item;
        first = first.next;
        return item;
    }
}
