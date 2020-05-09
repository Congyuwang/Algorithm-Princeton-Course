package princeton.algo.sort;

import java.util.Comparator;

/**
 * This shell sort algorithm is based on Sedgewick 1985 increment.
 * The algorithm has worst time O(N^(4/3)).
 * Shell sort is not stable.
 */
public class Shell {

    private Shell() {}

    /**
     * Shell sort a mutually comparable array
     *
     * @param a   the array to be sorted
     * @param <T> a mutually comparable type
     */
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k >= 0) {
            int gap = h(k--);
            // step-gap shell sort
            for (int i = gap; i < a.length; i++) {
                int j = i;
                while (j >= gap && Util.less(a[j], a[j - gap])) {
                    Util.exch(a, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    /**
     * Shell sort an array.
     *
     * @param a   the array to be sorted
     * @param c   the comparator of the array component type
     * @param <T> the type of which the comparator compares
     */
    public static <T> void sort(T[] a, Comparator<? super T> c) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k >= 0) {
            int gap = h(k--);
            // step-gap shell sort
            for (int i = gap; i < a.length; i++) {
                int j = i;
                while (j >= gap && Util.less(a[j], a[j - gap], c)) {
                    Util.exch(a, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    /**
     * Shell sort {@code int} array
     *
     * @param a the {@code int} array
     */
    public static void sort(int[] a) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k >= 0) {
            int gap = h(k--);
            // step-gap shell sort
            for (int i = gap; i < a.length; i++) {
                int j = i;
                while (j >= gap && a[j] < a[j - gap]) {
                    Util.exch(a, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    /**
     * Shell sort {@code short} array
     *
     * @param a the {@code short} array
     */
    public static void sort(short[] a) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k >= 0) {
            int gap = h(k--);
            // step-gap shell sort
            for (int i = gap; i < a.length; i++) {
                int j = i;
                while (j >= gap && a[j] < a[j - gap]) {
                    Util.exch(a, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    /**
     * Shell sort {@code long} array
     *
     * @param a the {@code long} array
     */
    public static void sort(long[] a) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k >= 0) {
            int gap = h(k--);
            // step-gap shell sort
            for (int i = gap; i < a.length; i++) {
                int j = i;
                while (j >= gap && a[j] < a[j - gap]) {
                    Util.exch(a, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    /**
     * Shell sort {@code double} array
     *
     * @param a the {@code double} array
     */
    public static void sort(double[] a) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k >= 0) {
            int gap = h(k--);
            // step-gap shell sort
            for (int i = gap; i < a.length; i++) {
                int j = i;
                while (j >= gap && a[j] < a[j - gap]) {
                    Util.exch(a, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    /**
     * Shell sort {@code float} array
     *
     * @param a the {@code float} array
     */
    public static void sort(float[] a) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k >= 0) {
            int gap = h(k--);
            // step-gap shell sort
            for (int i = gap; i < a.length; i++) {
                int j = i;
                while (j >= gap && a[j] < a[j - gap]) {
                    Util.exch(a, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    /**
     * Shell sort {@code char} array
     *
     * @param a the {@code char} array
     */
    public static void sort(char[] a) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k >= 0) {
            int gap = h(k--);
            // step-gap shell sort
            for (int i = gap; i < a.length; i++) {
                int j = i;
                while (j >= gap && a[j] < a[j - gap]) {
                    Util.exch(a, j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    // Sedgewick 1985 increment
    private static int h(int k) {
        if (k % 2 == 0) {
            return 9 * (- (1 << (k >>> 1)) + (1 << k)) + 1;
        }
        return - 6 * (1 << ((k + 1) >>> 1)) + 8 * (1 << k) + 1;
    }
}
