package princeton.algo.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

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

    /**
     * insert an item into the queue.
     * @param item the item to be inserted
     * @param i position parameter. 0 means add in front, size means add in the end.
     */
    public void insert(Item item, int i) {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("index out of range!");
        }
        if (i == size) {
            enqueue(item);
            return;
        }
        if (i == 0) {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            size++;
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

    /**
     * shuffle linked list in N(logN) time.
     */
    public void shuffle() {
        Random random = new Random();
        for (int step = 1; step < size; step <<= 1) {
            int twiceStep = step << 1;
            Node current = first;
            Node loPrev = null;
            Node lo = null;
            Node midPrev = null;
            Node mid = null;
            Node hi = null;
            boolean updateLoPrev = false;
            for (int p = 0; p < size; p++) {
                assert current != null;
                boolean execute = false;
                if (updateLoPrev) {
                    loPrev = hi;
                    updateLoPrev = false;
                }
                if (p % twiceStep == 0) {
                    lo = current;
                }
                if (p % twiceStep == step - 1) {
                    midPrev = current;
                }
                if (p % twiceStep == step) {
                    mid = current;
                }
                if (p % twiceStep == twiceStep - 1) {
                    execute = true;
                    updateLoPrev = true;
                    hi = current;
                }
                if (execute) {
                    assert lo != null && hi != null && midPrev != null && mid != null;
                    if (random.nextInt(2) == 0) {
                        current = exch(loPrev, lo, midPrev, mid, hi);
                        loPrev = midPrev;
                        updateLoPrev = false;
                        execute = false;
                        continue;
                    }
                    execute = false;
                }
                current = current.next;
            }
            if (size % twiceStep != 0 && random.nextInt(2) == 0) {
                insertTail(loPrev, lo, twiceStep * (size / twiceStep), random);
            }
        }
    }

    /**
     * exchange sub linked lists [lo - midPrev] [mid - hi] to [mid - hi] [lo -
     * midPrev]. The operation takes constant time
     *
     * @return the next node of hi
     */
    private Node exch(Node loPrev, Node lo, Node midPrev, Node mid, Node hi) {
        Node next = hi.next;
        if (loPrev == null) {
            first = mid;
        } else {
            loPrev.next = mid;
        }
        if (hi.next == null) {
            last = midPrev;
        }
        midPrev.next = hi.next;
        hi.next = lo;
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
    private void insertTail(Node tailPrev, Node tail, int headLength, Random random) {
        int randRange = headLength + 1;
        int position = random.nextInt(randRange);
        if (position == headLength) {
            return;
        }
        Node currentPrev;
        Node current = first;
        if (position == 0) {
            currentPrev = null;
        } else {
            currentPrev = first;
            for (int i = 1; i < position; i++) {
                currentPrev = currentPrev.next;
            }
        }
        if (currentPrev == null) {
            current = first;
        } else {
            current = currentPrev.next;
        }
        exch(currentPrev, current, tailPrev, tail, last);
    }
}
