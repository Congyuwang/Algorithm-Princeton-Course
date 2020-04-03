package princeton.algorithm.stack;

/**
 * This is an implementation of array stack of strings
 */
public class ArrayStackOfStrings {

    // n represent the next item
    int N = 0;
    String[] s = new String[4];

    public void push(String item) {
        if (N == s.length) {
            resize(2 * s.length);
        }
        s[N++] = item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public String pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("StackUnderFlow!");
        }
        String item = s[--N];

        // Avoids Loitering
        s[N] = null;

        // Avoids thrashing problem
        if (N == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        System.arraycopy(s, 0, copy, 0, N);
        s = copy;
    }
}