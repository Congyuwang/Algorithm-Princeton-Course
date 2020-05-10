package princeton.algo.sort;

import java.util.Comparator;

/**
 * A two-pivot quick sort implementation. Always shuffle before sorting. The
 * shuffling cost guarantees quick sort performance. Use insertion for length
 * shorter than {@code CUTOFF = 10}. This gives better adaptivity and reduces
 * overhead. When sorting primitive types, use three-mean key.
 * <p>
 * The Quick class also include the quick select algorithm, which find the nth
 * (start from {@code 0} and ends at {@code length - 1}) smallest element of an
 * array in linear time.
 * </p>
 */
public class Quick {

    private static final int CUTOFF = 10;

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length, c);
    }

    public static void sort(int[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    public static void sort(float[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    public static void sort(char[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    public static void sort(long[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    public static void sort(short[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    public static void sort(double[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time.
     *
     * @param a the input array in linear time.
     * @param n the n th smallest to be found (starting from {@code 0} and end at
     *          {@code a.length - 1})
     */
    public static <T extends Comparable<? super T>> void select(T[] a, int n) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        select(a, 0, a.length, n);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time.
     *
     * @param a the input array in linear time.
     * @param n the n th smallest to be found (starting from 0 and end at
     *          {@code a.length - 1})
     */
    public static <T> void sort(T[] a, int n, Comparator<? super T> c) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        select(a, 0, a.length, n, c);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time.
     *
     * @param a the input array in linear time.
     * @param n the n th smallest to be found (starting from 0 and end at
     *          {@code a.length - 1})
     */
    public static int select(int[] a, int n) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        return select(a, 0, a.length, n);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time.
     *
     * @param a the input array in linear time.
     * @param n the n th smallest to be found (starting from 0 and end at
     *          {@code a.length - 1})
     */
    public static float select(float[] a, int n) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        return select(a, 0, a.length, n);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time.
     *
     * @param a the input array in linear time.
     * @param n the n th smallest to be found (starting from 0 and end at
     *          {@code a.length - 1})
     */
    public static char select(char[] a, int n) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        return select(a, 0, a.length, n);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time.
     *
     * @param a the input array in linear time.
     * @param n the n th smallest to be found (starting from 0 and end at
     *          {@code a.length - 1})
     */
    public static long select(long[] a, int n) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        return select(a, 0, a.length, n);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time.
     *
     * @param a the input array in linear time.
     * @param n the n th smallest to be found (starting from 0 and end at
     *          {@code a.length - 1})
     */
    public static short select(short[] a, int n) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        return select(a, 0, a.length, n);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time.
     *
     * @param a the input array in linear time.
     * @param n the n th smallest to be found (starting from 0 and end at
     *          {@code a.length - 1})
     */
    public static double select(double[] a, int n) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        return select(a, 0, a.length, n);
    }

    private static <T extends Comparable<? super T>> T select(T[] a, int lo, int hi, int n) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
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
        if (lo <= n && n < hi) {
            return a[n];
        } else if (n < lo) {
            return select(a, loMem, lo, n);
        } else {
            return select(a, hi, hiMem, n);
        }
    }

    private static <T> T select(T[] a, int lo, int hi, int n, Comparator<? super T> c) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
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
        if (lo <= n && n < hi) {
            return a[n];
        } else if (n < lo) {
            return select(a, loMem, lo, n, c);
        } else {
            return select(a, hi, hiMem, n, c);
        }
    }

    private static int select(int[] a, int lo, int hi, int n) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        int key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            int cmp = key - a[mid];
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        if (lo <= n && n < hi) {
            return a[n];
        } else if (n < lo) {
            return select(a, loMem, lo, n);
        } else {
            return select(a, hi, hiMem, n);
        }
    }

    private static float select(float[] a, int lo, int hi, int n) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        float key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            float cmp = key - a[mid];
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        if (lo <= n && n < hi) {
            return a[n];
        } else if (n < lo) {
            return select(a, loMem, lo, n);
        } else {
            return select(a, hi, hiMem, n);
        }
    }

    private static char select(char[] a, int lo, int hi, int n) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        int key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            int cmp = key - a[mid];
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        if (lo <= n && n < hi) {
            return a[n];
        } else if (n < lo) {
            return select(a, loMem, lo, n);
        } else {
            return select(a, hi, hiMem, n);
        }
    }

    private static long select(long[] a, int lo, int hi, int n) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        long key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            long cmp = key - a[mid];
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        if (lo <= n && n < hi) {
            return a[n];
        } else if (n < lo) {
            return select(a, loMem, lo, n);
        } else {
            return select(a, hi, hiMem, n);
        }
    }

    private static short select(short[] a, int lo, int hi, int n) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        short key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            int cmp = key - a[mid];
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        if (lo <= n && n < hi) {
            return a[n];
        } else if (n < lo) {
            return select(a, loMem, lo, n);
        } else {
            return select(a, hi, hiMem, n);
        }
    }

    private static double select(double[] a, int lo, int hi, int n) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        double key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            double cmp = key - a[mid];
            if (cmp > 0) {
                Util.exch(a, lo++, mid++);
            } else if (cmp < 0) {
                Util.exch(a, --hi, mid);
            } else {
                mid++;
            }
        }
        if (lo <= n && n < hi) {
            return a[n];
        } else if (n < lo) {
            return select(a, loMem, lo, n);
        } else {
            return select(a, hi, hiMem, n);
        }
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        int key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            int cmp = key - a[mid];
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
    }

    private static int medianOf3(int a1, int a2, int a3) {
        if (a1 > a2) {
            if (a2 > a3) {
                return a2;
            }
            if (a3 > a1) {
                return a1;
            }
            return a3;
        } else {
            if (a3 > a2) {
                return a2;
            }
            if (a1 > a3) {
                return a1;
            }
        }
        return a3;
    }

    private static void sort(float[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        float key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            float cmp = key - a[mid];
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
    }

    private static float medianOf3(float a1, float a2, float a3) {
        if (a1 > a2) {
            if (a2 > a3) {
                return a2;
            }
            if (a3 > a1) {
                return a1;
            }
            return a3;
        } else {
            if (a3 > a2) {
                return a2;
            }
            if (a1 > a3) {
                return a1;
            }
        }
        return a3;
    }

    private static void sort(char[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        char key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            int cmp = key - a[mid];
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
    }

    private static char medianOf3(char a1, char a2, char a3) {
        if (a1 > a2) {
            if (a2 > a3) {
                return a2;
            }
            if (a3 > a1) {
                return a1;
            }
            return a3;
        } else {
            if (a3 > a2) {
                return a2;
            }
            if (a1 > a3) {
                return a1;
            }
        }
        return a3;
    }

    private static void sort(long[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        long key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            long cmp = key - a[mid];
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
    }

    private static long medianOf3(long a1, long a2, long a3) {
        if (a1 > a2) {
            if (a2 > a3) {
                return a2;
            }
            if (a3 > a1) {
                return a1;
            }
            return a3;
        } else {
            if (a3 > a2) {
                return a2;
            }
            if (a1 > a3) {
                return a1;
            }
        }
        return a3;
    }

    private static void sort(short[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        short key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            int cmp = key - a[mid];
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
    }

    private static short medianOf3(short a1, short a2, short a3) {
        if (a1 > a2) {
            if (a2 > a3) {
                return a2;
            }
            if (a3 > a1) {
                return a1;
            }
            return a3;
        } else {
            if (a3 > a2) {
                return a2;
            }
            if (a1 > a3) {
                return a1;
            }
        }
        return a3;
    }

    private static void sort(double[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int loMem = lo;
        int hiMem = hi;
        int mid = lo;
        double key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
        while (mid < hi) {
            double cmp = key - a[mid];
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
    }

    private static double medianOf3(double a1, double a2, double a3) {
        if (a1 > a2) {
            if (a2 > a3) {
                return a2;
            }
            if (a3 > a1) {
                return a1;
            }
            return a3;
        } else {
            if (a3 > a2) {
                return a2;
            }
            if (a1 > a3) {
                return a1;
            }
        }
        return a3;
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
