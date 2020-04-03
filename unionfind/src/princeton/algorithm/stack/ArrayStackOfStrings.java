package princeton.algorithm.stack;

import java.util.Arrays;

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

    public String pop() {
        if (isEmpty()) {
            return null;
        }
        String item = s[--N];
        s[N] = null;
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
