package princeton.algo.sort;

public class Selection {
    public static <T extends Comparable<T>> void sort(T[] a) {
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
