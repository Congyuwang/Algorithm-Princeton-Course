package princeton.algorithm.queue;

import java.util.NoSuchElementException;

public class LinkedQueueOfStrings {

    private Node first = null;
    private Node last = null;

    private class Node {
        String item;
        Node next;
    }

    // remove the first item
    public String dequeue() throws Exception {
        if (isEmpty()) {
            throw new NoSuchElementException("QueueUnderFlow!");
        }
        String item = first.item;
        first = first.next;
        return item;
    }

    // insert after the last item
    public void enqueue(String item) {
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
    }

    public boolean isEmpty() {
        return first == null;
    }
}
