package princeton.algo.sort;

import java.lang.reflect.Array;

public class MergeBU {

    private static final int CUTOFF = 7;

    private MergeBU() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int length = a.length;
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) Array.newInstance(a.getClass().getComponentType(), length);
        System.arraycopy(a, 0, aux, 0, length);

        int round = 0;
        for (int temp = CUTOFF; temp < length; temp <<= 1) {
            round++;
        }
        round %= 2;

        if (round == 0) {
            for (int lo = 0; lo < length; lo += CUTOFF) {
                Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
            }
        } else {
            for (int lo = 0; lo < length; lo += CUTOFF) {
                Insertion.sort(aux, lo, Math.min(length, lo + CUTOFF));
            }
        }

        for (int size = CUTOFF; size < length; size <<= 1) {
            int twiceSize = size * 2;
            if (round == 0) {
                int lo;
                for (lo = 0; lo < length - size; lo += twiceSize) {
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
                round = 1;
            } else {
                int lo;
                for (lo = 0; lo < length - size; lo += twiceSize) {
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
                round = 0;
            }
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
