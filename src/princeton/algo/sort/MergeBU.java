package princeton.algo.sort;

import java.lang.reflect.Array;

public class MergeBU {

    private MergeBU() {};

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        System.arraycopy(a, 0, aux, 0, a.length);
        int round = 0;
        for (int size = 1; size < a.length; size = size << 1) {
            for (int lo = 0; lo < a.length - size; lo += 2 * size) {
                if (round % 2 == 0) {
                    merge(a, aux, lo, lo + size, Math.min(a.length, lo + size * 2));
                } else {
                    merge(aux, a, lo, lo + size, Math.min(a.length, lo + size * 2));
                }
            }
            round++;
        }
        if (round % 2 == 0) {
            System.arraycopy(aux, 0, a, 0, a.length);
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
