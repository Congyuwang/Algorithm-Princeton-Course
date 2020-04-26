package princeton.algo.sort;

/**
 * Insertion.sort sorts array of mutually comparable type T.
 * The algorithm takes ~N^2/2 in worst case, and ~N^2/4 on average.
 * Insertion.sort is linear time if array is partially sorted.
 */
public class Insertion {

    private Insertion() {};

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, 0, a.length);
    }

    public static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        if (lo < 0 || hi > a.length || lo >= hi) {
            throw new IllegalArgumentException("illegal index");
        }
        for (int i = lo; i < hi; i++) {
            for (int j = i; j > lo; j--) {
                if (Util.less(a[j], a[j - 1])) {
                    Util.exch(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }
}
