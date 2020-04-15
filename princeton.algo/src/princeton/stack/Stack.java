package princeton.stack;

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

    void push(Item item) throws IllegalArgumentException;

    Item pop() throws NoSuchElementException;
}
