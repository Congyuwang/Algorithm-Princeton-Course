package princeton.algo.sort;

import java.lang.reflect.Array;

public class MergeBU {

    private static final int CUTOFF = 7;

    private MergeBU() {};

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int length = a.length;
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) Array.newInstance(a.getClass().getComponentType(), length);
        System.arraycopy(a, 0, aux, 0, length);

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(aux, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size = size << 1) {
            int lo;
            if (round % 2 == 0) {
                for (lo = 0; lo < length - size; lo += 2 * size) {
                    int mid = lo + size;
                    int hi = Math.min(length, lo + size * 2);
                    if (Util.less(aux[mid], aux[mid - 1])) {
                        merge(a, aux, lo, mid, hi);
                    } else {
                        System.arraycopy(aux, lo, a, lo, hi - lo);
                    }
                }
                if (lo < length) {
                    System.arraycopy(aux, lo, a, lo, length - lo);
                }
            } else {
                for (lo = 0; lo < length - size; lo += 2 * size) {
                    int mid = lo + size;
                    int hi = Math.min(length, lo + size * 2);
                    if (Util.less(a[mid], a[mid - 1])) {
                        merge(aux, a, lo, mid, hi);
                    } else {
                        System.arraycopy(a, lo, aux, lo, hi - lo);
                    }
                }
                if (lo < length) {
                    System.arraycopy(a, lo, aux, lo, length - lo);
                }
            }
            round++;
        }
        if (round % 2 == 0) {
            System.arraycopy(aux, 0, a, 0, length);
        }
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
        assert Util.isSorted(a, lo, mid);
        assert Util.isSorted(a, mid, hi);
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