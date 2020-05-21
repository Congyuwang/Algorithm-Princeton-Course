package princeton.algo.binaryHeap;

import java.util.Comparator;
import princeton.algo.sort.Util;

public class Heap {
    private Heap() {}

    /**
     * heap sort an array with a comparator
     *
     * @param <T> type parameter
     * @param a   the array of type T
     */
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

    /**
     * heap sort an array with a comparator
     *
     * @param <T> type parameter
     * @param a   the array of type T
     * @param c   the comparator of type T
     */
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

    /**
     * heap sort int array
     *
     * @param a the int array
     */
    public static void sort(int[] a) {
        final int size = a.length;
        for (int j = PriorityQueue.parent(size - 1); j >= 0; j--) {
            moveDown(a, j, size - 1);
        }
        for (int n = a.length - 1; n > 0; n--) {
            Util.exch(a, 0, n);
            moveDown(a, 0, n - 1);
        }
    }

    /**
     * heap sort long array
     *
     * @param a the long array
     */
    public static void sort(long[] a) {
        final int size = a.length;
        for (int j = PriorityQueue.parent(size - 1); j >= 0; j--) {
            moveDown(a, j, size - 1);
        }
        for (int n = a.length - 1; n > 0; n--) {
            Util.exch(a, 0, n);
            moveDown(a, 0, n - 1);
        }
    }

    /**
     * heap sort short array
     *
     * @param a the short array
     */
    public static void sort(short[] a) {
        final int size = a.length;
        for (int j = PriorityQueue.parent(size - 1); j >= 0; j--) {
            moveDown(a, j, size - 1);
        }
        for (int n = a.length - 1; n > 0; n--) {
            Util.exch(a, 0, n);
            moveDown(a, 0, n - 1);
        }
    }

    /**
     * heap sort double array
     *
     * @param a the double array
     */
    public static void sort(double[] a) {
        final int size = a.length;
        for (int j = PriorityQueue.parent(size - 1); j >= 0; j--) {
            moveDown(a, j, size - 1);
        }
        for (int n = a.length - 1; n > 0; n--) {
            Util.exch(a, 0, n);
            moveDown(a, 0, n - 1);
        }
    }

    /**
     * heap sort float array
     *
     * @param a the float array
     */
    public static void sort(float[] a) {
        final int size = a.length;
        for (int j = PriorityQueue.parent(size - 1); j >= 0; j--) {
            moveDown(a, j, size - 1);
        }
        for (int n = a.length - 1; n > 0; n--) {
            Util.exch(a, 0, n);
            moveDown(a, 0, n - 1);
        }
    }

    /**
     * heap sort char array
     *
     * @param a the char array
     */
    public static void sort(char[] a) {
        final int size = a.length;
        for (int j = PriorityQueue.parent(size - 1); j >= 0; j--) {
            moveDown(a, j, size - 1);
        }
        for (int n = a.length - 1; n > 0; n--) {
            Util.exch(a, 0, n);
            moveDown(a, 0, n - 1);
        }
    }

    private static void moveDown(int[] heap, int k, int limit) {
            while (k <= PriorityQueue.parent(limit)) {
            int parent = heap[k];
            int leftChild = PriorityQueue.leftChild(k);
            int rightChild = leftChild + 1;
            if (parent - heap[leftChild] < 0) {
                if (rightChild > limit) {
                    Util.exch(heap, k, leftChild);
                    k = leftChild;
                    continue;
                }
                int right = heap[rightChild];
                if (right - heap[leftChild] > 0) {
                    Util.exch(heap, k, rightChild);
                    k = rightChild;
                    continue;
                }
                Util.exch(heap, k, leftChild);
                k = leftChild;
            } else if (rightChild <= limit && parent - heap[rightChild] < 0) {
                Util.exch(heap, k, rightChild);
                k = rightChild;
            } else {
                break;
            }
        }
    }

    private static void moveDown(long[] heap, int k, int limit) {
            while (k <= PriorityQueue.parent(limit)) {
            long parent = heap[k];
            int leftChild = PriorityQueue.leftChild(k);
            int rightChild = leftChild + 1;
            if (parent - heap[leftChild] < 0) {
                if (rightChild > limit) {
                    Util.exch(heap, k, leftChild);
                    k = leftChild;
                    continue;
                }
                long right = heap[rightChild];
                if (right - heap[leftChild] > 0) {
                    Util.exch(heap, k, rightChild);
                    k = rightChild;
                    continue;
                }
                Util.exch(heap, k, leftChild);
                k = leftChild;
            } else if (rightChild <= limit && parent - heap[rightChild] < 0) {
                Util.exch(heap, k, rightChild);
                k = rightChild;
            } else {
                break;
            }
        }
    }

    private static void moveDown(double[] heap, int k, int limit) {
            while (k <= PriorityQueue.parent(limit)) {
            double parent = heap[k];
            int leftChild = PriorityQueue.leftChild(k);
            int rightChild = leftChild + 1;
            if (parent - heap[leftChild] < 0) {
                if (rightChild > limit) {
                    Util.exch(heap, k, leftChild);
                    k = leftChild;
                    continue;
                }
                double right = heap[rightChild];
                if (right - heap[leftChild] > 0) {
                    Util.exch(heap, k, rightChild);
                    k = rightChild;
                    continue;
                }
                Util.exch(heap, k, leftChild);
                k = leftChild;
            } else if (rightChild <= limit && parent - heap[rightChild] < 0) {
                Util.exch(heap, k, rightChild);
                k = rightChild;
            } else {
                break;
            }
        }
    }

    private static void moveDown(float[] heap, int k, int limit) {
            while (k <= PriorityQueue.parent(limit)) {
            float parent = heap[k];
            int leftChild = PriorityQueue.leftChild(k);
            int rightChild = leftChild + 1;
            if (parent - heap[leftChild] < 0) {
                if (rightChild > limit) {
                    Util.exch(heap, k, leftChild);
                    k = leftChild;
                    continue;
                }
                float right = heap[rightChild];
                if (right - heap[leftChild] > 0) {
                    Util.exch(heap, k, rightChild);
                    k = rightChild;
                    continue;
                }
                Util.exch(heap, k, leftChild);
                k = leftChild;
            } else if (rightChild <= limit && parent - heap[rightChild] < 0) {
                Util.exch(heap, k, rightChild);
                k = rightChild;
            } else {
                break;
            }
        }
    }

    private static void moveDown(short[] heap, int k, int limit) {
            while (k <= PriorityQueue.parent(limit)) {
            short parent = heap[k];
            int leftChild = PriorityQueue.leftChild(k);
            int rightChild = leftChild + 1;
            if (parent - heap[leftChild] < 0) {
                if (rightChild > limit) {
                    Util.exch(heap, k, leftChild);
                    k = leftChild;
                    continue;
                }
                short right = heap[rightChild];
                if (right - heap[leftChild] > 0) {
                    Util.exch(heap, k, rightChild);
                    k = rightChild;
                    continue;
                }
                Util.exch(heap, k, leftChild);
                k = leftChild;
            } else if (rightChild <= limit && parent - heap[rightChild] < 0) {
                Util.exch(heap, k, rightChild);
                k = rightChild;
            } else {
                break;
            }
        }
    }

    private static void moveDown(char[] heap, int k, int limit) {
            while (k <= PriorityQueue.parent(limit)) {
            char parent = heap[k];
            int leftChild = PriorityQueue.leftChild(k);
            int rightChild = leftChild + 1;
            if (parent - heap[leftChild] < 0) {
                if (rightChild > limit) {
                    Util.exch(heap, k, leftChild);
                    k = leftChild;
                    continue;
                }
                char right = heap[rightChild];
                if (right - heap[leftChild] > 0) {
                    Util.exch(heap, k, rightChild);
                    k = rightChild;
                    continue;
                }
                Util.exch(heap, k, leftChild);
                k = leftChild;
            } else if (rightChild <= limit && parent - heap[rightChild] < 0) {
                Util.exch(heap, k, rightChild);
                k = rightChild;
            } else {
                break;
            }
        }
    }
}
