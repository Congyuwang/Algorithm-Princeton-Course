package princeton.algo.sort;

import java.util.Comparator;

/**
 * A two-pivot quick sort implementation. Use insertion for length shorter than
 * {@code CUTOFF = 8}. This gives better adaptivity and reduces overhead.
 */
public class Quick {

    private static final int CUTOFF = 32;

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length, c);
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        T key = a[lo];
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        while (mid < hi) {
            int cmp = key.compareTo(a[mid]);
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo);
        sort(a, hi, hiMem);
        assert Util.isSorted(a, loMem, lo);
        assert Util.isSorted(a, hi, hiMem);
    }

    private static <T> void sort(T[] a, int lo, int hi, Comparator<? super T> c) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi, c);
            return;
        }
        T key = a[lo];
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        while (mid < hi) {
            int cmp = c.compare(key, a[mid]);
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo, c);
        sort(a, hi, hiMem, c);
        assert Util.isSorted(a, loMem, lo, c);
        assert Util.isSorted(a, hi, hiMem, c);
    }
}
