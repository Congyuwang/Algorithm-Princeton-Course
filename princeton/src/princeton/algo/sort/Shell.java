package princeton.algo.sort;

/**
 * This is based on Sedgewick 1985. The algorithm has worst time O(N^(4/3))
 */
public class Shell {
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int k = 0;
        while (h(k) < a.length) {
            k++;
        }
        while (k > 0) {
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

    // Sedgewick 1985 increment
    private static int h(int k) {
        if (k % 2 == 0) {
            return 9 * (- (1 << (k / 2)) + (1 << k)) + 1;
        }
        return - 6 * (1 << ((k + 1) / 2)) + 8 * (1 << k) + 1;
    }
}
