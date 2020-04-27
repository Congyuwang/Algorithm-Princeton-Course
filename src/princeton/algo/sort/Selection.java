package princeton.algo.sort;

/**
 * The selection sort algorithm sorts arrays with type T which is
 * <i>mutually</i> comparable.
 * It uses ~N^2 / 2 compares for randomly shuffled input.
 * Selection sort is not stable.
 */
public class Selection {

    private Selection() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = 1 + i; j < a.length; j++) {
                if (Util.less(a[j], a[min])) {
                    min = j;
                }
            }
            Util.exch(a, i, min);
        }
    }
}
