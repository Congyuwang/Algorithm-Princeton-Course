package princeton.algorithm.queue;

public class LinkedQueue<Item> {

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
}
