package princeton.algo.sort;

import java.util.Comparator;

/**
 * A two-pivot quick sort implementation. Use insertion for length shorter than
 * {@code CUTOFF = 8}. This gives better adaptivity and reduces overhead.
 * When sorting primitive types, use three-mean key.
 */
public class Quick {

    private static final int CUTOFF = 32;

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length, c);
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length);
    }

    public static void sort(float[] a) {
        sort(a, 0, a.length);
    }

    public static void sort(char[] a) {
        sort(a, 0, a.length);
    }

    public static void sort(long[] a) {
        sort(a, 0, a.length);
    }

    public static void sort(short[] a) {
        sort(a, 0, a.length);
    }

    public static void sort(double[] a) {
        sort(a, 0, a.length);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo + 1 >= hi) {
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        int key = a[lo] + (a[hi - 1] - a[lo]) / 3 + (a[(lo + hi - 1) >>> 1] - a[lo]) / 3;
        while (mid < hi) {
            int cmp = key - a[mid];
            if (cmp > 0) {
                exch(a, lo++, mid++);
            } else if (cmp < 0) {
                exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo);
        sort(a, hi, hiMem);
    }

    private static void exch(int[] a, int i0, int i1) {
        int temp = a[i0];
        a[i0] = a[i1];
        a[i1] = temp;
    }

    private static void sort(float[] a, int lo, int hi) {
        if (lo + 1 >= hi) {
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        float key = (a[lo] + a[hi - 1] + a[(lo + hi - 1) >>> 1]) / 3;
        while (mid < hi) {
            float cmp = key - a[mid];
            if (cmp > 0) {
                exch(a, lo++, mid++);
            } else if (cmp < 0) {
                exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo);
        sort(a, hi, hiMem);
    }

    private static void exch(float[] a, int i0, int i1) {
        float temp = a[i0];
        a[i0] = a[i1];
        a[i1] = temp;
    }

    private static void sort(char[] a, int lo, int hi) {
        if (lo + 1 >= hi) {
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        int key = (a[lo] + a[hi - 1] + a[(lo + hi - 1) >>> 1]) / 3;
        while (mid < hi) {
            int cmp = key - a[mid];
            if (cmp > 0) {
                exch(a, lo++, mid++);
            } else if (cmp < 0) {
                exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo);
        sort(a, hi, hiMem);
    }

    private static void exch(char[] a, int i0, int i1) {
        char temp = a[i0];
        a[i0] = a[i1];
        a[i1] = temp;
    }

    private static void sort(long[] a, int lo, int hi) {
        if (lo + 1 >= hi) {
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        long key = a[lo] + (a[hi - 1] - a[lo]) / 3 + (a[(lo + hi - 1) >>> 1] - a[lo]) / 3;
        while (mid < hi) {
            long cmp = key - a[mid];
            if (cmp > 0) {
                exch(a, lo++, mid++);
            } else if (cmp < 0) {
                exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo);
        sort(a, hi, hiMem);
    }

    private static void exch(long[] a, int i0, int i1) {
        long temp = a[i0];
        a[i0] = a[i1];
        a[i1] = temp;
    }

    private static void sort(short[] a, int lo, int hi) {
        if (lo + 1 >= hi) {
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        int key = (a[lo] + a[hi - 1] + a[(lo + hi - 1) >>> 1]) / 3;
        while (mid < hi) {
            int cmp = key - a[mid];
            if (cmp > 0) {
                exch(a, lo++, mid++);
            } else if (cmp < 0) {
                exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo);
        sort(a, hi, hiMem);
    }

    private static void exch(short[] a, int i0, int i1) {
        short temp = a[i0];
        a[i0] = a[i1];
        a[i1] = temp;
    }

    private static void sort(double[] a, int lo, int hi) {
        if (lo + 1 >= hi) {
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        double key = a[lo] / 3 + a[hi - 1] / 3 + a[(lo + hi - 1) >>> 1] / 3;
        while (mid < hi) {
            double cmp = key - a[mid];
            if (cmp > 0) {
                exch(a, lo++, mid++);
            } else if (cmp < 0) {
                exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo);
        sort(a, hi, hiMem);
    }

    private static void exch(double[] a, int i0, int i1) {
        double temp = a[i0];
        a[i0] = a[i1];
        a[i1] = temp;
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        T key = a[lo];
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        while (mid < hi) {
            int cmp = key.compareTo(a[mid]);
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo);
        sort(a, hi, hiMem);
        assert Util.isSorted(a, loMem, lo);
        assert Util.isSorted(a, hi, hiMem);
    }

    private static <T> void sort(T[] a, int lo, int hi, Comparator<? super T> c) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi, c);
            return;
        }
        T key = a[lo];
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        while (mid < hi) {
            int cmp = c.compare(key, a[mid]);
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        sort(a, loMem, lo, c);
        sort(a, hi, hiMem, c);
        assert Util.isSorted(a, loMem, lo, c);
        assert Util.isSorted(a, hi, hiMem, c);
    }
}
