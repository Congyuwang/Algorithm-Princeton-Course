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
     * @throws NullPointerException if the parameter is null
     */
    void push(Item item) throws NullPointerException;

    /**
     * @exception NoSuchElementException if there is no more element.
     */
    Item pop();
}
