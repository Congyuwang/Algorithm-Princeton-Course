package princeton.algo.sort;

import java.util.Random;

/**
 * The Shuffle class shuffles any array shorter than MAX.INTEGER uniformly
 * in linear time.
 */
public class Shuffle {

    private Shuffle() {}

    public static void shuffle(Object[] a) {
        Random random = new Random();
        int size = a.length;
        for (int i = 1; i < size; i++) {
            int j = random.nextInt(i + 1);
            Object temp = a[j];
            a[j] = a[i];
            a[i] = temp;
        }
    }
}
