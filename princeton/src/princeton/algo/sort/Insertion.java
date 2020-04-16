package princeton.algo.sort;

/**
 * Insertion.sort sorts array of mutually comparable type T.
 * The algorithm takes ~N^2/2 in worst case, and ~N^2/4 on average.
 * Insertion.sort is linear time if array is partially sorted.
 */
public class Insertion {
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (Util.less(a[j], a[j - 1])) {
                    Util.exch(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }
}
