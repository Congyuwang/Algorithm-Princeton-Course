package princeton.algorithm.queue;

import java.util.NoSuchElementException;

/**
 * The Queue interface is an iterable Generic class that implements the Queue
 * data structure, which generally follows the FIFO (first in first out) rule
 * (except for the {@code RandomizedQueue} class).
 * <p>
 * {@code Item dequeue()} retrieves one item from the queue and removes it.
 * {@code void enqueue()} pushes one item into the queue.
 * </p>
 *
 * @param <Item> The generic type parameter.
 */
public interface Queue<Item> extends Iterable<Item> {

    /**
     * returns an item from the Queue, and remove it.
     * @return an item in the Queue
     * @throws NoSuchElementException throws if the Queue is empty.
     */
    Item dequeue() throws NoSuchElementException;

    /**
     * push an item into the Queue
     * @param item the item to be pushed
     * @throws IllegalArgumentException throws if the item is null.
     */
    void enqueue(Item item) throws IllegalArgumentException;

    boolean isEmpty();

    int size();

}
