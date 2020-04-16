package princeton.algo.sort;

import edu.princeton.cs.algs4.StdRandom;

public class Shuffle {
    public static void shuffle(Object[] a) {
        int size = a.length;
        for (int i = 1; i < size; i++) {
            int j = StdRandom.uniform(i + 1);
            Object temp = a[j];
            a[j] = a[i];
            a[i] = temp;
        }
    }
}
