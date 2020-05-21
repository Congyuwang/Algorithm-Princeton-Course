package princeton.algo.stack;

import java.util.NoSuchElementException;

/**
 * The Stack interface is an iterable Generic class that implements the Stack
 * data structure, which generally follows the FILO (first in last out) rule.
 * <p>
 * {@code Item pop()} retrieves one item from the stack and removes it from the
 * stack. {@code void push()} pushes one item into the stack.
 * </p>
 *
 * @param <Item> The generic type parameter.
 */
public interface Stack<Item> extends Iterable<Item> {

    boolean isEmpty();

    int size();

    /**
     * push an item into the stack
     * @param item the item to be pushed
     * @throws IllegalArgumentException if the parameter is null
     */
    void push(Item item) throws IllegalArgumentException;

    /**
     * @exception NoSuchElementException if there is no more element.
     */
    Item pop();

    /**
     * shuffle the stack. For array implementation, the operation takes linear time
     * and requires no extra memory. For linked-list implementation, the operation
     * takes log(n) time, and requires no extra memory.
     */
    void shuffle();

    /**
     * peek the first item to be popped, but do not remove.
     * @return {@code null} is empty
     */
    Item peek();
}
