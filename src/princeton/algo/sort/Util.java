package princeton.algo.sort;

import java.util.Comparator;

/**
 * The sort.Util class provides tools for sorting algorithms
 * which deal with mutually comparable type T.
 */
public class Util {

    /**
     * Compare two elements v and w, and return {@code true} if v < w
     *
     * @param <T> a mutually comparable type T
     * @param v   the first argument
     * @param w   the second argument
     * @return boolean v < w
     */
    public static <T extends Comparable<? super T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    /**
     * Compare two elements v and w, and return {@code true} if v < w
     *
     * @param <T>        comparable by the comparator
     * @param v          the first argument
     * @param w          the second argument
     * @param comparator the comparator of type T
     * @return boolean v < w
     */
    public static <T> boolean less(T v, T w, Comparator<? super T> comparator) {
        return comparator.compare(v, w) < 0;
    }

    /**
     * Checks whether the array a is sorted in weakly ascending order
     *
     * @param <T> a mutually comparable type.
     * @param a   the array to be tested
     * @return {@code true} if no later element is smaller than previous one.
     */
    public static <T extends Comparable<? super T>> boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length);
    }

    /**
     * Checks whether the array a is sorted in weakly ascending order
     *
     * @param <T> comparable by the comparator
     * @param a   the array to be tested
     * @return {@code true} if no later element is smaller than previous one.
     */
    public static <T> boolean isSorted(T[] a, Comparator<? super T> comparator) {
        return isSorted(a, 0, a.length, comparator);
    }

    /**
     * Checks whether the array a is sorted in weakly ascending order
     *
     * @param <T> a mutually comparable type
     * @param a   the array to be tested
     * @param i0  the beginning point (included)
     * @param i1  the ending point (excluded)
     * @throws IllegalArgumentException if the index is out of range
     * @return {@code true} if no later element is smaller than previous one
     */
    public static <T extends Comparable<? super T>> boolean isSorted(T[] a, int i0, int i1) {
        if (i0 < 0 || i1 > a.length || i1 > i0) {
            throw new IllegalArgumentException("index out of range!");
        }
        for (int i = i0; i < i1 - 1; i++) {
            if (Util.less(a[i + 1], a[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the array a is sorted in weakly ascending order
     *
     * @param <T>        comparable by the comparator
     * @param a          the array to be tested
     * @param i0         the beginning point (included)
     * @param i1         the ending point (excluded)
     * @param comparator the comparator used
     * @throws IllegalArgumentException if the index is out of range
     * @return {@code true} if no later element is smaller than previous one
     */
    public static <T> boolean isSorted(T[] a, int i0, int i1, Comparator<? super T> comparator) {
        if (i0 < 0 || i1 > a.length || i1 > i0) {
            throw new IllegalArgumentException("index out of range!");
        }
        for (int i = i0; i < i1 - 1; i++) {
            if (Util.less(a[i + 1], a[i], comparator)) {
                return false;
            }
        }
        return true;
    }

    /**
     * exchange two elements of array T[] a.
     *
     * @param a  an array of T
     * @param i1 index 1
     * @param i2 index 2
     */
    public static <T> void exch(T[] a, int i1, int i2) {
        T swap = a[i1];
        a[i1] = a[i2];
        a[i2] = swap;
    }
}
