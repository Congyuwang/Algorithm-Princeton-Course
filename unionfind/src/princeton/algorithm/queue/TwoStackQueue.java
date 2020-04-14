package princeton.algorithm.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import princeton.algorithm.stack.LinkedStack;

public class TwoStackQueue<Item> implements Queue<Item> {

    private LinkedStack<Item> inStack = new LinkedStack<>();
    private LinkedStack<Item> storeStack = new LinkedStack<>();
    private int size = 0;

    @Override
    public Item dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("StackUnderFlow!");
        }
        if (inStack.size() == storeStack.size()) {
            pour();
        }
        size--;
        return storeStack.pop();
    }

    @Override
    public void enqueue(Item item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("null item not allowed!");
        }
        if (isEmpty()) {
            storeStack.push(item);
        } else {
            inStack.push(item);
        }
        if (inStack.size() == storeStack.size()) {
            pour();
        }
        size++;
    }

    /**
     * Each pour takes (2 * size of storeStack + size of tempStack) push operations.
     * In {@code dequeue} and {@code enqueue}, {@code pour} is invoked only when
     * the size of inStack equals to that of storeStack, which happens as frequently
     * as the size of the Queue doubles.
     */
    private void pour() {
        LinkedStack<Item> tempStack = new LinkedStack<>();
        for (Item item : storeStack) {
            tempStack.push(item);
        }
        storeStack = new LinkedStack<>();
        for (Item item : inStack) {
            storeStack.push(item);
        }
        inStack = new LinkedStack<>();
        for (Item item : tempStack) {
            storeStack.push(item);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<Item> iterator() {
        pour();
        return storeStack.iterator();
    }

    @Override
    public int size() {
        return size;
    }
}
