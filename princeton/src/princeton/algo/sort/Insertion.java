package princeton.algo.sort;

public class Insertion {
    public static <T extends Comparable<T>> void sort(T[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1])) {
                    exch(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    private static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (less(a[i + 1], a[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Double[] test = new Double[100];
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 100; i++) {
            test[i] = random.nextDouble();
        }
        for (double i : test) {
            System.out.println(i);
        }
        System.out.printf("is sorted: %b\n", isSorted(test));
        Selection.sort(test);
        for (double i : test) {
            System.out.println(i);
        }
        System.out.printf("is sorted: %b\n", isSorted(test));
    }
}
