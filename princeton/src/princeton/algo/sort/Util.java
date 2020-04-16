package princeton.algo.sort;

/**
 * The sort.Util class provides tools for sorting algorithms
 * which deal with mutually comparable type T.
 */
public class Util {

    /**
     * Compare two elements v and w, and return {@code true} if v < w.
     * @param <T> a mutually comparable type T
     * @param v the first argument
     * @param w the second argument
     * @return boolean v < w
     */
    public static <T extends Comparable<? super T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    /**
     * exchange two elements of array T[] a.
     * @param a an array of T
     * @param i1 index 1
     * @param i2 index 2
     */
    public static <T> void exch(T[] a, int i1, int i2) {
        T swap = a[i1];
        a[i1] = a[i2];
        a[i2] = swap;
    }

    /**
     * Checks whether the array a is sorted in weakly ascending order.
     * @param <T> a mutually comparable type.
     * @param a the array to be tested
     * @return {@code true} if no later element is smaller than previous one.
     */
    public static <T extends Comparable<? super T>> boolean isSorted(T[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (Util.less(a[i + 1], a[i])) {
                return false;
            }
        }
        return true;
    }
}
