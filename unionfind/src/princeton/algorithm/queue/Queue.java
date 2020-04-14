package princeton.algorithm.queue;

import java.util.NoSuchElementException;

interface Queue<Item> extends Iterable<Item> {

    // remove the first item
    Item dequeue() throws NoSuchElementException;

    // insert after the last item
    void enqueue(Item item) throws IllegalArgumentException;

    boolean isEmpty();

}
