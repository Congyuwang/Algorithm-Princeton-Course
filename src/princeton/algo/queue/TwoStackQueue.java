package princeton.algo.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import princeton.algo.sort.Shuffle;
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

    private LinkedStack<Item> receiveStack = new LinkedStack<>();
    private LinkedStack<Item> storeStack = new LinkedStack<>();
    private int size = 0;

    @Override
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("QueueUnderFlow!");
        }
        if (receiveStack.size() == storeStack.size()) {
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
            receiveStack.push(item);
        }
        if (receiveStack.size() == storeStack.size()) {
            pour();
        }
        size++;
    }

    /**
     * Each pour takes (2 * size of storeStack + size of tempStack) push operations.
     * In {@code dequeue} and {@code enqueue}, {@code pour} is invoked only when
     * the size of receiveStack equals to that of storeStack, which happens as frequently
     * as the size of the Queue doubles. The operation use constant extra memory.
     */
    private void pour() {
        LinkedStack<Item> tempStack = new LinkedStack<>();
        while (!storeStack.isEmpty()) {
            tempStack.push(storeStack.pop());
        }
        while (!receiveStack.isEmpty()) {
            storeStack.push(receiveStack.pop());
        }
        while (!tempStack.isEmpty()) {
            storeStack.push(tempStack.pop());
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

    @Override
    public void shuffle() {
        pour();
        Shuffle.shuffle(storeStack);
    }
}
