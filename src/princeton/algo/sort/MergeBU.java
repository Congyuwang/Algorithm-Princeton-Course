package princeton.algo.sort;

import java.lang.reflect.Array;

public class MergeBU {

    private static final int CUTOFF = 7;

    private MergeBU() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int length = a.length;
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) Array.newInstance(a.getClass().getComponentType(), length);

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            T[] temp = a;
            a = aux;
            aux = temp;
            mergeTo(aux, a, size);
            round++;
        }

        assert Util.isSorted(a);

        if (round % 2 == 1) {
            System.arraycopy(a, 0, aux, 0, length);
        }
    }

    private static <T extends Comparable<? super T>> void mergeTo(T[] src, T[] dest, int size) {
        int length = src.length;
        int lo;
        int twiceSize = size * 2;
        for (lo = 0; lo < length - size; lo += twiceSize) {
            int mid = lo + size;
            int hi = Math.min(length, lo + twiceSize);
            if (Util.less(src[mid], src[mid - 1])) {
                Merge.merge(src, dest, lo, mid, hi);
            } else {
                System.arraycopy(src, lo, dest, lo, hi - lo);
            }
        }
        if (lo < length) {
            System.arraycopy(src, lo, dest, lo, length - lo);
        }
    }
}
