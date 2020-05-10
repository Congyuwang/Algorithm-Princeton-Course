package interview;

import java.lang.reflect.Array;
import princeton.algo.sort.Util;

/**
 * Count the number of inversions of a comparable array using merge sort
 * algorithm. Count number of inversions in merge process.
 * When an element in the second half is pushed before the first half
 * is pushed. This means there are the same number of inversions as
 * the number of elements not yet pushed in the first half.
 */
public class CountInversion {
    private CountInversion() {}

    public static <T extends Comparable<? super T>> int sort(T[] a) {
        int[] count = {0};

        @SuppressWarnings("unchecked")
        T[] b = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length, count);

        return count[0];
    }

    private static <T extends Comparable<? super T>> void sort(T[] b, T[] a, int lo, int hi, int[] count) {
        if (hi <= lo + 1) {
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid, count);
        sort(a, b, mid, hi, count);
        if (Util.less(b[mid], b[mid - 1])) {
            merge(b, a, lo, mid, hi, count);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
    }

    public static <T extends Comparable<? super T>> void merge(T[] src, T[] dest, int lo, int mid, int hi, int[] count) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        int firstHalfCount = mid - lo;
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
                firstHalfCount--;
            } else if (Util.less(src[j], src[i])) {
                count[0] += firstHalfCount;
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
                firstHalfCount--;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] testArray = {1, 3, 9, 5, 4, 7, 6};
        System.out.println(sort(testArray));
    }

}
