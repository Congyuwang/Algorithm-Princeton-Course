package princeton.algo.sort;

import java.lang.reflect.Array;
import java.util.Comparator;

import princeton.algo.queue.LinkedQueue;
import princeton.algo.queue.Queue;
import princeton.algo.stack.LinkedStack;
import princeton.algo.stack.Stack;

/**
 * This merge sort algorithm uses recursive function.
 * The algorithm uses insertion sort for arrays with length shorter than 8.
 * It stores results in two alternating arrays a and b.
 * It also checks whether merging is necessary.
 */
public class Merge {

    private static final int CUTOFF = 8;

    private Merge() {}

    /**
     * Merge sort sort a mutually comparable array. Insertion sort when length is
     * shorter than 8.
     *
     * @param a   the array to be sorted
     * @param <T> a mutually comparable type
     */
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] b = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length);
    }

    /**
     * Merge sort an array. Insertion sort when length is shorter than 8.
     *
     * @param a   the array to be sorted
     * @param c   the comparator of the array component type
     * @param <T> the type of which the comparator compares
     */
    public static <T> void sort(T[] a, Comparator<? super T> c) {
        @SuppressWarnings("unchecked")
        T[] b = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length, c);
    }

    /**
     * Merge sort a queue.
     *
     * @param b   the Queue to be sorted
     * @param <T> the type of which the comparator compares
     */
    public static <T extends Comparable<? super T>> void sort(Queue<T> b) {
        assert b != null;
        int bSize = b.size();
        if (bSize <= 1) {
            return;
        }
        try {
            @SuppressWarnings("unchecked")
            Queue<T> b1 = (Queue<T>) b.getClass().getConstructor().newInstance();
            @SuppressWarnings("unchecked")
            Queue<T> b2 = (Queue<T>) b.getClass().getConstructor().newInstance();
            boolean alternate = true;
            for (int i = 0; i < bSize; i++) {
                if (alternate) {
                    b1.enqueue(b.dequeue());
                    alternate = false;
                } else {
                    b2.enqueue(b.dequeue());
                    alternate = true;
                }
            }
            sort(b1);
            sort(b2);
            T i1 = null;
            T i2 = null;
            while (true) {
                if (!b1.isEmpty() && i1 == null) {
                    i1 = b1.dequeue();
                }
                if (!b2.isEmpty() && i2 == null) {
                    i2 = b2.dequeue();
                }
                if (i1 == null && i2 == null) {
                    break;
                }
                if (i1 == null) {
                    b.enqueue(i2);
                    i2 = null;
                } else if (i2 == null) {
                    b.enqueue(i1);
                    i1 = null;
                } else if (Util.less(i1, i2)) {
                    b.enqueue(i1);
                    i1 = null;
                } else {
                    b.enqueue(i2);
                    i2 = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Merge sort a queue
     *
     * @param b   the Queue to be sorted
     * @param c   the comparator of the Queue component type
     * @param <T> the type of which the comparator compares
     */
    public static <T> void sort(Queue<T> b, Comparator<? super T> c) {
        assert b != null;
        int bSize = b.size();
        if (bSize <= 1) {
            return;
        }
        try {
            @SuppressWarnings("unchecked")
            Queue<T> b1 = (Queue<T>) b.getClass().getConstructor().newInstance();
            @SuppressWarnings("unchecked")
            Queue<T> b2 = (Queue<T>) b.getClass().getConstructor().newInstance();
            boolean alternate = true;
            for (int i = 0; i < bSize; i++) {
                if (alternate) {
                    b1.enqueue(b.dequeue());
                    alternate = false;
                } else {
                    b2.enqueue(b.dequeue());
                    alternate = true;
                }
            }
            sort(b1, c);
            sort(b2, c);
            T i1 = null;
            T i2 = null;
            while (true) {
                if (!b1.isEmpty() && i1 == null) {
                    i1 = b1.dequeue();
                }
                if (!b2.isEmpty() && i2 == null) {
                    i2 = b2.dequeue();
                }
                if (i1 == null && i2 == null) {
                    break;
                }
                if (i1 == null) {
                    b.enqueue(i2);
                    i2 = null;
                } else if (i2 == null) {
                    b.enqueue(i1);
                    i1 = null;
                } else if (Util.less(i1, i2, c)) {
                    b.enqueue(i1);
                    i1 = null;
                } else {
                    b.enqueue(i2);
                    i2 = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Merge sort a stack
     *
     * @param b   the Queue to be sorted
     * @param <T> the type of which the comparator compares
     */
    public static <T extends Comparable<? super T>> void sort(Stack<T> b) {
        LinkedQueue<T> linkedList = new LinkedQueue<>();
        LinkedStack<T> linkedStack = new LinkedStack<>();
        while (!b.isEmpty()) {
            linkedList.enqueue(b.pop());
        }
        sort(linkedList);
        while (!linkedList.isEmpty()) {
            linkedStack.push(linkedList.dequeue());
        }
        while (!linkedStack.isEmpty()) {
            b.push(linkedStack.pop());
        }
    }

    /**
     * Merge sort a stack so that the first element to be popped is
     * the smallest one.
     *
     * @param b   the Queue to be sorted
     * @param c   the comparator of the Queue component type
     * @param <T> the type of which the comparator compares
     */
    public static <T> void sort(Stack<T> b, Comparator<? super T> c) {
        LinkedQueue<T> linkedList = new LinkedQueue<>();
        LinkedStack<T> linkedStack = new LinkedStack<>();
        while (!b.isEmpty()) {
            linkedList.enqueue(b.pop());
        }
        sort(linkedList, c);
        while (!linkedList.isEmpty()) {
            linkedStack.push(linkedList.dequeue());
        }
        while (!linkedStack.isEmpty()) {
            b.push(linkedStack.pop());
        }
    }

    /**
     * Merge sort {@code int} array
     *
     * @param a the {@code int} array
     */
    public static void sort(int[] a) {
        int[] b = new int[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length);
    }

    /**
     * Merge sort {@code short} array
     *
     * @param a the {@code short} array
     */
    public static void sort(short[] a) {
        short[] b = new short[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length);
    }

    /**
     * Merge sort {@code long} array
     *
     * @param a the {@code long} array
     */
    public static void sort(long[] a) {
        long[] b = new long[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length);
    }

    /**
     * Merge sort {@code float} array
     *
     * @param a the {@code float} array
     */
    public static void sort(float[] a) {
        float[] b = new float[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length);
    }

    /**
     * Merge sort {@code double} array
     *
     * @param a the {@code double} array
     */
    public static void sort(double[] a) {
        double[] b = new double[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length);
    }

    /**
     * Merge sort {@code char} array
     *
     * @param a the {@code char} array
     */
    public static void sort(char[] a) {
        char[] b = new char[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b, a, 0, a.length);
    }


    private static <T extends Comparable<? super T>> void sort(T[] b, T[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid); assert Util.isSorted(b, lo, mid);
        sort(a, b, mid, hi); assert Util.isSorted(b, mid, hi);
        if (Util.less(b[mid], b[mid - 1])) {
            merge(b, a, lo, mid, hi);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi);
    }

    private static <T> void sort(T[] b, T[] a, int lo, int hi, Comparator<? super T> c) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi, c);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid, c);
        assert Util.isSorted(b, lo, mid, c);
        sort(a, b, mid, hi, c);
        assert Util.isSorted(b, mid, hi, c);
        if (Util.less(b[mid], b[mid - 1], c)) {
            merge(b, a, lo, mid, hi, c);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi, c);
    }

    private static void sort(int[] b, int[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid);
        assert Util.isSorted(b, lo, mid);
        sort(a, b, mid, hi);
        assert Util.isSorted(b, mid, hi);
        if (b[mid] < b[mid - 1]) {
            merge(b, a, lo, mid, hi);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi);
    }

    private static void sort(short[] b, short[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid);
        assert Util.isSorted(b, lo, mid);
        sort(a, b, mid, hi);
        assert Util.isSorted(b, mid, hi);
        if (b[mid] < b[mid - 1]) {
            merge(b, a, lo, mid, hi);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi);
    }

    private static void sort(long[] b, long[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid);
        assert Util.isSorted(b, lo, mid);
        sort(a, b, mid, hi);
        assert Util.isSorted(b, mid, hi);
        if (b[mid] < b[mid - 1]) {
            merge(b, a, lo, mid, hi);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi);
    }

    private static void sort(float[] b, float[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid);
        assert Util.isSorted(b, lo, mid);
        sort(a, b, mid, hi);
        assert Util.isSorted(b, mid, hi);
        if (b[mid] < b[mid - 1]) {
            merge(b, a, lo, mid, hi);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi);
    }

    private static void sort(double[] b, double[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid);
        assert Util.isSorted(b, lo, mid);
        sort(a, b, mid, hi);
        assert Util.isSorted(b, mid, hi);
        if (b[mid] < b[mid - 1]) {
            merge(b, a, lo, mid, hi);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi);
    }

    private static void sort(char[] b, char[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = (lo + hi) >>> 1;
        sort(a, b, lo, mid);
        assert Util.isSorted(b, lo, mid);
        sort(a, b, mid, hi);
        assert Util.isSorted(b, mid, hi);
        if (b[mid] < b[mid - 1]) {
            merge(b, a, lo, mid, hi);
        } else {
            System.arraycopy(b, lo, a, lo, hi - lo);
        }
        assert Util.isSorted(a, lo, hi);
    }

    static <T extends Comparable<? super T>> void merge(T[] src, T[] dest, int lo, int mid, int hi) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (Util.less(src[j], src[i])) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
        assert Util.isSorted(dest, lo, hi);
    }

    static <T> void merge(T[] src, T[] dest, int lo, int mid, int hi, Comparator<? super T> c) {
        assert Util.isSorted(src, lo, mid, c);
        assert Util.isSorted(src, mid, hi, c);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (Util.less(src[j], src[i], c)) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
        assert Util.isSorted(dest, lo, hi, c);
    }

    static void merge(int[] src, int[] dest, int lo, int mid, int hi) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (src[j] < src[i]) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
        assert Util.isSorted(dest, lo, hi);
    }

    static void merge(short[] src, short[] dest, int lo, int mid, int hi) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (src[j] < src[i]) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
        assert Util.isSorted(dest, lo, hi);
    }

    static void merge(long[] src, long[] dest, int lo, int mid, int hi) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (src[j] < src[i]) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
        assert Util.isSorted(dest, lo, hi);
    }

    static void merge(double[] src, double[] dest, int lo, int mid, int hi) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (src[j] < src[i]) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
        assert Util.isSorted(dest, lo, hi);
    }

    static void merge(float[] src, float[] dest, int lo, int mid, int hi) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (src[j] < src[i]) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
        assert Util.isSorted(dest, lo, hi);
    }

    static void merge(char[] src, char[] dest, int lo, int mid, int hi) {
        assert Util.isSorted(src, lo, mid);
        assert Util.isSorted(src, mid, hi);
        for (int k = lo, i = lo, j = mid; k < hi; k++) {
            if (i == mid) {
                dest[k] = src[j++];
            } else if (j == hi) {
                dest[k] = src[i++];
            } else if (src[j] < src[i]) {
                dest[k] = src[j++];
            } else {
                dest[k] = src[i++];
            }
        }
        assert Util.isSorted(dest, lo, hi);
    }
}
