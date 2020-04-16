package princeton.algo.sort;

public class Insertion {
    public static <T extends Comparable<T>> void sort(T[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (Util.less(a[j], a[j - 1])) {
                    Util.exch(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }
}
