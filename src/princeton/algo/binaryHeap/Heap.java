package princeton.algo.binaryHeap;

public class Heap {
    private Heap() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        PriorityQueue<T> pQueue = new PriorityQueue<>(a);
        for (int n = a.length - 1; n > 1; n--) {
            pQueue.exch(0, n);
            pQueue.moveDown(0, n - 1);
        }
    }

}
