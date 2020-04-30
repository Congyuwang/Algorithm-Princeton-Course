package princeton.algo.sort;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * This bottom up merge sort algorithm uses Insertion sort of length 8
 * at first stage. It stores results in two alternating arrays a and b.
 * The algorithm is stable. It also checks whether merging is necessary.
 */
public class MergeBU {

    private static final int CUTOFF = 8;

    private MergeBU() {}

    /**
     * Bottom up merge sort sort a mutually comparable array. Insertion sort when
     * length is shorter than 8.
     *
     * @param a   the array to be sorted
     * @param <T> a mutually comparable type
     */
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int length = a.length;
        @SuppressWarnings("unchecked")
        T[] b = (T[]) Array.newInstance(a.getClass().getComponentType(), length);

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            T[] temp = a;
            a = b;
            b = temp;
            mergeTo(b, a, size);
            round++;
        }

        assert Util.isSorted(a);

        if (round % 2 == 1) {
            System.arraycopy(a, 0, b, 0, length);
        }
    }

    /**
     * Bottom up merge sort an array. Insertion sort when length is shorter than 8.
     *
     * @param a   the array to be sorted
     * @param c   the comparator of the array component type
     * @param <T> the type of which the comparator compares
     */
    public static <T> void sort(T[] a, Comparator<? super T> c) {
        int length = a.length;
        @SuppressWarnings("unchecked")
        T[] b = (T[]) Array.newInstance(a.getClass().getComponentType(), length);

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF), c);
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            T[] temp = a;
            a = b;
            b = temp;
            mergeTo(b, a, size, c);
            round++;
        }

        assert Util.isSorted(a, c);

        if (round % 2 == 1) {
            System.arraycopy(a, 0, b, 0, length);
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

    private static <T> void mergeTo(T[] src, T[] dest, int size, Comparator<? super T> c) {
        int length = src.length;
        int lo;
        int twiceSize = size * 2;
        for (lo = 0; lo < length - size; lo += twiceSize) {
            int mid = lo + size;
            int hi = Math.min(length, lo + twiceSize);
            if (Util.less(src[mid], src[mid - 1], c)) {
                Merge.merge(src, dest, lo, mid, hi, c);
            } else {
                System.arraycopy(src, lo, dest, lo, hi - lo);
            }
        }
        if (lo < length) {
            System.arraycopy(src, lo, dest, lo, length - lo);
        }
    }
}
