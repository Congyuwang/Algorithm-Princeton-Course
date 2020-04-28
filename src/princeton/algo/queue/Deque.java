package princeton.algo.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

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
        Node prev;
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
            oldFirst.prev = first;
        } else {
            last = first;
        }
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
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
        last.prev = oldLast;
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
            first.prev = null;
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
        last = last.prev;
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
        node.prev = prev;
        prev.next = node;
        node.next.prev = node;
        size++;
    }

    /**
     * shuffle linked list in N(logN) time.
     */
    public void shuffle() {
        Random random = new Random();
        for (int step = 1; step < size; step <<= 1) {
            int twiceStep = step << 1;
            Node current = first;
            Node lo = null;
            Node mid = null;
            Node hi = null;
            for (int p = 0; p < size; p++) {
                assert current != null;
                boolean execute = false;
                if (p % twiceStep == 0) {
                    lo = current;
                }
                if (p % twiceStep == step) {
                    mid = current;
                }
                if (p % twiceStep == twiceStep - 1) {
                    execute = true;
                    hi = current;
                }
                if (execute) {
                    assert lo != null && hi != null && mid != null;
                    if (random.nextInt(2) == 0) {
                        current = exch(lo, mid, hi);
                        execute = false;
                        continue;
                    }
                    execute = false;
                }
                current = current.next;
            }
            if (size % twiceStep != 0 && random.nextInt(2) == 0) {
                insertTail(lo, twiceStep * (size / twiceStep), random);
            }
        }
    }

    /**
     * exchange sub linked lists [lo - mid.prev] [mid - hi] to [mid - hi] [lo -
     * mid.prev]. The operation takes constant time
     * @return the next node of hi
     */
    private Node exch(Node lo, Node mid, Node hi) {
        assert mid.prev != null;
        Node next = hi.next;
        Node midPrev = mid.prev;
        mid.prev = lo.prev;
        if (mid.prev != null) {
            mid.prev.next = mid;
        } else {
            first = mid;
        }
        midPrev.next = hi.next;
        if (midPrev.next != null) {
            midPrev.next.prev = midPrev;
        } else {
            last = midPrev;
        }
        hi.next = lo;
        lo.prev = hi;
        return next;
    }

    /**
     * Randomly insert tail of shuffle into head, where tail is the link after Node
     * tail (included). Takes linear time in worst case.
     *
     * @param tail       the first Node of the tail link [tail - last]
     * @param headLength the length of [first - tail.prev]
     * @param random     the random generator
     */
    private void insertTail(Node tail, int headLength, Random random) {
        int randRange = headLength + 1;
        int position = random.nextInt(randRange);
        if (position == headLength) {
            return;
        }
        Node current = first;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        exch(current, tail, last);
    }
}
