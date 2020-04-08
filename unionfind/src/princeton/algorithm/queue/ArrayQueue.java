package princeton.algorithm.queue;

public class ArrayQueue<Item> {
    // count represents the total number of items
    // head represents the position of the first item
    // tail represents the position of the next item to be filled
    int count = 0;
    int head = 0;
    int tail = 0;

    // cannot implement generic array. Use cast.
    Item[] s = (Item[]) new Object[1];

    public void enqueue(Item item) {
        // reset tail pointer if tail exceeds index limit
        if (tail == s.length) {
            tail = 0;
        }
        s[tail++] = item;
        // resize array if the size limit is met
        if (++count == s.length) {
            resize(s.length * 2);
        }
    }

    public Item dequeue() throws Exception {
        if (isEmpty()) {
            throw new Exception("StackUnderFlow!");
        }
        if (head == s.length) {
            head = 0;
        }
        Item item = s[head];
        s[head++] = null;
        // resize the array if it is too empty
        if (--count == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    private void resize(int capacity) {
        // cannot implement generic array. Use cast.
        Item[] copy = (Item[]) new Object[capacity];
        if (head > tail) {
            System.arraycopy(s, 0, copy, 0, tail);
            System.arraycopy(s, head, copy, tail, s.length - head);
        } else {
            System.arraycopy(s, head, copy, 0, count);
        }
        head = 0;
        tail = count;
        s = copy;
    }
}
