package princeton.algorithm.stack;

import java.util.NoSuchElementException;

public interface Stack<Item> extends Iterable<Item> {

    boolean isEmpty();

    void push(Item item) throws IllegalArgumentException;

    Item pop() throws NoSuchElementException;
}
