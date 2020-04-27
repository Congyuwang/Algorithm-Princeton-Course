package princeton.algo.sort;

import java.lang.reflect.Array;

public class Merge {

    private static final int CUTOFF = 7;

    private Merge() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        System.arraycopy(a, 0, aux, 0, a.length);
        sort(aux, a, 0, a.length);
    }

    private static <T extends Comparable<? super T>> void sort(T[] aux, T[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, aux, lo, mid); assert Util.isSorted(aux, lo, mid);
        sort(a, aux, mid, hi); assert Util.isSorted(aux, mid, hi);
        if (Util.less(aux[mid], aux[mid - 1])) {
            merge(aux, a, lo, mid, hi);
        } else {
            System.arraycopy(aux, lo, a, lo, hi - lo);
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
