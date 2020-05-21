package princeton.algo.binaryHeap;

import java.util.Comparator;

import princeton.algo.sort.Util;

public class Heap {
    private Heap() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        final int size = a.length;
        for (int j = PriorityQueue.parent(size - 1); j >= 0; j--) {
            PriorityQueue.moveDown(a, j, size - 1, null);
        }
        for (int n = a.length - 1; n > 0; n--) {
            Util.exch(a, 0, n);
            PriorityQueue.moveDown(a, 0, n - 1, null);
        }
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        final int size = a.length;
        for (int j = PriorityQueue.parent(size - 1); j >= 0; j--) {
            PriorityQueue.moveDown(a, j, size - 1, c);
        }
        for (int n = a.length - 1; n > 0; n--) {
            Util.exch(a, 0, n);
            PriorityQueue.moveDown(a, 0, n - 1, c);
        }
    }
}
