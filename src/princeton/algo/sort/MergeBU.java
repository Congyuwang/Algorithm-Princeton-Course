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

    /**
     * Bottom up merge sort sort a {@code int} array. Insertion sort when length is
     * shorter than 8.
     *
     * @param a the {@code int} array to be sorted
     */
    public static void sort(int[] a) {
        int length = a.length;
        int[] b = new int[length];

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            int[] temp = a;
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
     * Bottom up merge sort sort a {@code double} array. Insertion sort when length
     * is shorter than 8.
     *
     * @param a the {@code double} array to be sorted
     */
    public static void sort(double[] a) {
        int length = a.length;
        double[] b = new double[length];

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            double[] temp = a;
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
     * Bottom up merge sort sort a {@code float} array. Insertion sort when length
     * is shorter than 8.
     *
     * @param a the {@code float} array to be sorted
     */
    public static void sort(float[] a) {
        int length = a.length;
        float[] b = new float[length];

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            float[] temp = a;
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
     * Bottom up merge sort sort a {@code long} array. Insertion sort when length is
     * shorter than 8.
     *
     * @param a the {@code long} array to be sorted
     */
    public static void sort(long[] a) {
        int length = a.length;
        long[] b = new long[length];

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            long[] temp = a;
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
     * Bottom up merge sort sort a {@code short} array. Insertion sort when length
     * is shorter than 8.
     *
     * @param a the {@code short} array to be sorted
     */
    public static void sort(short[] a) {
        int length = a.length;
        short[] b = new short[length];

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            short[] temp = a;
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
     * Bottom up merge sort sort a {@code char} array. Insertion sort when length is
     * shorter than 8.
     *
     * @param a the {@code char} array to be sorted
     */
    public static void sort(char[] a) {
        int length = a.length;
        char[] b = new char[length];

        for (int lo = 0; lo < length; lo += CUTOFF) {
            Insertion.sort(a, lo, Math.min(length, lo + CUTOFF));
        }

        int round = 0;
        for (int size = CUTOFF; size < length; size <<= 1) {
            char[] temp = a;
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

    private static void mergeTo(int[] src, int[] dest, int size) {
        int length = src.length;
        int lo;
        int twiceSize = size * 2;
        for (lo = 0; lo < length - size; lo += twiceSize) {
            int mid = lo + size;
            int hi = Math.min(length, lo + twiceSize);
            if (src[mid] < src[mid - 1]) {
                Merge.merge(src, dest, lo, mid, hi);
            } else {
                System.arraycopy(src, lo, dest, lo, hi - lo);
            }
        }
        if (lo < length) {
            System.arraycopy(src, lo, dest, lo, length - lo);
        }
    }

    private static void mergeTo(short[] src, short[] dest, int size) {
        int length = src.length;
        int lo;
        int twiceSize = size * 2;
        for (lo = 0; lo < length - size; lo += twiceSize) {
            int mid = lo + size;
            int hi = Math.min(length, lo + twiceSize);
            if (src[mid] < src[mid - 1]) {
                Merge.merge(src, dest, lo, mid, hi);
            } else {
                System.arraycopy(src, lo, dest, lo, hi - lo);
            }
        }
        if (lo < length) {
            System.arraycopy(src, lo, dest, lo, length - lo);
        }
    }

    private static void mergeTo(long[] src, long[] dest, int size) {
        int length = src.length;
        int lo;
        int twiceSize = size * 2;
        for (lo = 0; lo < length - size; lo += twiceSize) {
            int mid = lo + size;
            int hi = Math.min(length, lo + twiceSize);
            if (src[mid] < src[mid - 1]) {
                Merge.merge(src, dest, lo, mid, hi);
            } else {
                System.arraycopy(src, lo, dest, lo, hi - lo);
            }
        }
        if (lo < length) {
            System.arraycopy(src, lo, dest, lo, length - lo);
        }
    }

    private static void mergeTo(double[] src, double[] dest, int size) {
        int length = src.length;
        int lo;
        int twiceSize = size * 2;
        for (lo = 0; lo < length - size; lo += twiceSize) {
            int mid = lo + size;
            int hi = Math.min(length, lo + twiceSize);
            if (src[mid] < src[mid - 1]) {
                Merge.merge(src, dest, lo, mid, hi);
            } else {
                System.arraycopy(src, lo, dest, lo, hi - lo);
            }
        }
        if (lo < length) {
            System.arraycopy(src, lo, dest, lo, length - lo);
        }
    }

    private static void mergeTo(float[] src, float[] dest, int size) {
        int length = src.length;
        int lo;
        int twiceSize = size * 2;
        for (lo = 0; lo < length - size; lo += twiceSize) {
            int mid = lo + size;
            int hi = Math.min(length, lo + twiceSize);
            if (src[mid] < src[mid - 1]) {
                Merge.merge(src, dest, lo, mid, hi);
            } else {
                System.arraycopy(src, lo, dest, lo, hi - lo);
            }
        }
        if (lo < length) {
            System.arraycopy(src, lo, dest, lo, length - lo);
        }
    }

    private static void mergeTo(char[] src, char[] dest, int size) {
        int length = src.length;
        int lo;
        int twiceSize = size * 2;
        for (lo = 0; lo < length - size; lo += twiceSize) {
            int mid = lo + size;
            int hi = Math.min(length, lo + twiceSize);
            if (src[mid] < src[mid - 1]) {
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
