package princeton.algo.sort;

import java.lang.reflect.Array;

/**
 * This merge sort algorithm uses recursive function.
 * The algorithm uses insertion sort for arrays with length shorter than 8.
 * It stores results in two alternating arrays a and b.
 */
public class Merge {

    private static final int CUTOFF = 8;

    private Merge() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] b = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length);
    }

    private static <T extends Comparable<? super T>> void sort(T[] b, T[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid); assert Util.isSorted(b, lo, mid);
        sort(a, b, mid, hi); assert Util.isSorted(b, mid, hi);
        if (Util.less(b[mid], b[mid - 1])) {
            merge(b, a, lo, mid, hi);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi);
    }

    public static <T extends Comparable<? super T>> void merge(T[] src, T[] dest, int lo, int mid, int hi) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (Util.less(src[i], src[j])) {
                dest[k] = src[i++];
            } else {
                dest[k] = src[j++];
            }
        }
        assert Util.isSorted(dest, lo, hi);
    }
}
