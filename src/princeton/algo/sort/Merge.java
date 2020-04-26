package princeton.algo.sort;

import java.lang.reflect.Array;

public class Merge {

    private static final int CUTOFF = 7;

    private Merge() {};

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        sort(a, aux, 0, a.length);
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, T[] aux, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, aux, lo, mid);
        sort(a, aux, mid, hi);
        if (!Util.less(a[mid], a[mid - 1])) {
            return;
        }
        merge(a, aux, lo, mid, hi);
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
        assert Util.isSorted(a, lo, mid);
        assert Util.isSorted(a, mid, hi);
        System.arraycopy(a, lo, aux, lo, hi - lo);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                a[k] = aux[j++];
            } else if (j == hi) {
                a[k] = aux[i++];
            } else if (Util.less(aux[i], aux[j])) {
                a[k] = aux[i++];
            } else {
                a[k] = aux[j++];
            }
        }
        assert Util.isSorted(a, lo, hi);
    }
}
