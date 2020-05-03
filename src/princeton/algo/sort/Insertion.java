package princeton.algo.sort;

import java.util.Comparator;

/**
 * Insertion.sort sorts array of mutually comparable type T. The algorithm takes
 * ~N^2/2 in worst case, and ~N^2/4 on average. Insertion.sort is linear time if
 * array is partially sorted. The algorithm is stable.
 */
public class Insertion {

    private Insertion() {}

    /**
     * Insertion sort a mutually comparable array
     *
     * @param a   the array to be sorted
     * @param <T> a mutually comparable type
     */
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, 0, a.length);
    }

    /**
     * Insertion sort an array.
     *
     * @param a   the array to be sorted
     * @param c   the comparator of the array component type
     * @param <T> the type of which the comparator compares
     */
    public static <T> void sort(T[] a, Comparator<? super T> c) {
        sort(a, 0, a.length, c);
    }

    static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        assert lo >= 0;
        assert hi <= a.length;
        assert lo < hi;
        for (int i = lo; i < hi; i++) {
            for (int j = i; j > lo && Util.less(a[j], a[j - 1]); j--) {
                Util.exch(a, j, j - 1);
            }
        }
    }

    static <T> void sort(T[] a, int lo, int hi, Comparator<? super T> c) {
        assert lo >= 0;
        assert hi <= a.length;
        assert lo < hi;
        for (int i = lo; i < hi; i++) {
            for (int j = i; j > lo && Util.less(a[j], a[j - 1], c); j--) {
                Util.exch(a, j, j - 1);
            }
        }
    }
}
