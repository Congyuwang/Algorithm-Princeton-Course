package princeton.algo.sort;

public class Selection {
    public static <T extends Comparable<T>> void sort(T[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = 1 + i; j < a.length; j++) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            exch(a, i, min);
        }
    }

    private static <T extends Comparable<T>> boolean less(Comparable<T> v, T w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        Integer[] test = {3, 1, 5, 2, 6, 4, 8, 5, 9, 5, 0, 7};
        Selection.sort(test);
        for (int i : test) {
            System.out.println(i);
        }
    }
}
