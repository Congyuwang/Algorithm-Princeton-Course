package princeton.algorithm.stack;

public class ArrayStack<Item> {

    // n represent the next item
    int N = 0;
    Item[] s = (Item[]) new Object[1];

    public void push(Item item) {
        if (N == s.length) {
            resize(2 * s.length);
        }
        s[N++] = item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Item pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("StackUnderFlow!");
        }
        Item item = s[--N];

        // Avoids Loitering
        s[N] = null;

        // Avoids thrashing problem
        if (N == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(s, 0, copy, 0, N);
        s = copy;
    }
}
