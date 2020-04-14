package princeton.algorithm.stack;

import java.util.NoSuchElementException;

public interface Stack<Item> extends Iterable<Item> {

    boolean isEmpty();

    int size();

    void push(Item item) throws IllegalArgumentException;

    Item pop() throws NoSuchElementException;
}
