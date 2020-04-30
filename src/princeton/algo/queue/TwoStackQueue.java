package princeton.algo.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import princeton.algo.stack.LinkedStack;

/**
 * The TwoStackQueue class implements the iterable Queue data structure using
 * two stacks.
 * <p>
 * The {@code private void pour()} method pours the storage stack to a temporary
 * stack, and then pours the input stack into the storage stack, and finally
 * pours the temporary stack back into the storage stack.
 * </p>
 * <p>
 * This operation is invoked only when the input stack has the same length as
 * the storage stack. As a result, the {@code dequeue()} and {@code enqueue()}
 * takes constant amortized time.
 * </p>
 *
 * @param <Item> the generic type argument.
 */
public class TwoStackQueue<Item> implements Queue<Item> {

    private LinkedStack<Item> inStack = new LinkedStack<>();
    private LinkedStack<Item> storeStack = new LinkedStack<>();
    private int size = 0;

    @Override
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("QueueUnderFlow!");
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
            throw new NullPointerException("null item not allowed!");
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
