package princeton.algo.sort;

import java.lang.reflect.Array;

public class Merge {

    private static final int CUTOFF = 7;

    private Merge() {};

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        System.arraycopy(a, 0, aux, 0, a.length);
        sort(a, aux, 0, a.length);
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, T[] aux, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(aux, a, lo, mid);
        sort(aux, a, mid, hi);
        if (!Util.less(aux[mid], aux[mid - 1])) {
            return;
        }
        merge(a, aux, lo, mid, hi);
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
        assert Util.isSorted(a, lo, mid);
        assert Util.isSorted(a, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                aux[k] = a[j++];
            } else if (j == hi) {
                aux[k] = a[i++];
            } else if (Util.less(a[i], a[j])) {
                aux[k] = a[i++];
            } else {
                aux[k] = a[j++];
            }
        }
        assert Util.isSorted(a, lo, hi);
    }
}
