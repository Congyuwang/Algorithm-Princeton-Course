package princeton.algo.sort;

import java.util.Comparator;

/**
 * A two-pivot quick sort implementation. Always shuffle before sorting. The
 * shuffling cost guarantees quick sort performance. Use insertion for length
 * shorter than {@code CUTOFF = 10}. This gives better adaptivity and reduces
 * overhead. The quick sort uses median of 3 as key.
 * <p>
 * The Quick class also include the quick select algorithm, which find the nth
 * (start from {@code 0} and ends at {@code length - 1}) smallest element of an
 * array in linear time.
 * </p>
 * <p>
 * It also includes a select algorithm which finds the nth (start from {@code 0}
 * and ends at {@code length - 1}) smallest element from two
 * <em><b>sorted</b></em> arrays in linear time.
 * </p>
 */
public class Quick {

    private static final int CUTOFF = 10;

    /**
     * quick sort the {@code Comparable} array
     *
     * @param a the {@code Comparable} array
     */
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    /**
     * quick sort the array with a comparator
     *
     * @param a the array to be sorted
     */
    public static <T> void sort(T[] a, Comparator<? super T> c) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length, c);
    }

    /**
     * quick sort the {@code int} array
     *
     * @param a the {@code int} array
     */
    public static void sort(int[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    /**
     * quick sort the {@code float} array
     *
     * @param a the {@code float} array
     */
    public static void sort(float[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    /**
     * quick sort the {@code char} array
     *
     * @param a the {@code char} array
     */
    public static void sort(char[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    /**
     * quick sort the {@code long} array
     *
     * @param a the {@code long} array
     */
    public static void sort(long[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    /**
     * quick sort the {@code short} array
     *
     * @param a the {@code short} array
     */
    public static void sort(short[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    /**
     * quick sort the {@code double} array
     *
     * @param a the {@code double} array
     */
    public static void sort(double[] a) {
        Shuffle.shuffle(a);
        sort(a, 0, a.length);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time on average.
     *
     * @param a the input array.
     * @param n the n th smallest to be found (starting from {@code 0} and end at
     *          {@code a.length - 1})
     */
    public static <T extends Comparable<? super T>> T select(T[] a, int n) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        return select(a, 0, a.length, n);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time on average.
     *
     * @param a the input array.
     * @param n the n th smallest to be found (starting from 0 and end at
     *          {@code a.length - 1})
     */
    public static <T> T select(T[] a, int n, Comparator<? super T> c) {
        if (n >= a.length || n < 0) {
            throw new IllegalArgumentException("out of range");
        }
        Shuffle.shuffle(a);
        return select(a, 0, a.length, n, c);
    }

    /**
     * Find the nth (start from {@code 0} and ends at {@code length - 1}) smallest
     * element of an array in linear time on average.
     *
     * @param a the input array.
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
     * element of an array in linear time on average.
     *
     * @param a the input array.
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
     * element of an array in linear time on average.
     *
     * @param a the input array.
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
     * element of an array in linear time on average.
     *
     * @param a the input array.
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
     * element of an array in linear time on average.
     *
     * @param a the input array.
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
     * element of an array in linear time on average.
     *
     * @param a the input array.
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
        T key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
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

    private static <T extends Comparable<? super T>> T medianOf3(T a1, T a2, T a3) {
        if (Util.less(a2, a1)) {
            if (Util.less(a3, a2)) {
                return a2;
            }
            if (Util.less(a1, a3)) {
                return a1;
            }
            return a3;
        } else {
            if (Util.less(a2, a3)) {
                return a2;
            }
            if (Util.less(a3, a1)) {
                return a1;
            }
        }
        return a3;
    }

    private static <T> T select(T[] a, int lo, int hi, int n, Comparator<? super T> c) {
        assert lo <= n;
        assert n < hi;
        if (lo + 1 >= hi) {
            return a[lo];
        }
        T key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1], c);
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

    private static <T> T medianOf3(T a1, T a2, T a3, Comparator<? super T> c) {
        if (Util.less(a2, a1, c)) {
            if (Util.less(a3, a2, c)) {
                return a2;
            }
            if (Util.less(a1, a3, c)) {
                return a1;
            }
            return a3;
        } else {
            if (Util.less(a2, a3, c)) {
                return a2;
            }
            if (Util.less(a3, a1, c)) {
                return a1;
            }
        }
        return a3;
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
            return Math.min(a3, a1);
        } else {
            if (a3 > a2) {
                return a2;
            }
            return Math.max(a3, a1);
        }
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
            return Math.min(a3, a1);
        } else {
            if (a3 > a2) {
                return a2;
            }
            return Math.max(a3, a1);
        }
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
            return Math.min(a3, a1);
        } else {
            if (a3 > a2) {
                return a2;
            }
            return Math.max(a3, a1);
        }
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
            return Math.min(a3, a1);
        } else {
            if (a3 > a2) {
                return a2;
            }
            return Math.max(a3, a1);
        }
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        if (lo + CUTOFF >= hi) {
            Insertion.sort(a, lo, hi);
            return;
        }
        T key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1]);
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
        T key = medianOf3(a[lo], a[hi - 1], a[(lo + hi - 1) >>> 1], c);
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

    /**
     * select from two <b>sorted</b> arrays (in ascending orders) the nth smallest element
     * of the two arrays in logarithmic time.
     *
     * @param a the first sorted array
     * @param b the second sorted array
     * @param n the nth smallest (<em>starting from 0</em>)
     * @return the nth smallest element
     */
    public static double select(double[] a, double[] b, int n) {
        assert Util.isSorted(a);
        assert Util.isSorted(b);
        if (n < 0 || n > a.length + b.length - 1) {
            throw new IllegalArgumentException("Out of range");
        }
        if (a.length == 0 && b.length == 0) {
            throw new IllegalArgumentException("empty array");
        }
        if (a.length == 0 || (b.length > n && a[0] > b[n])) {
            return b[n];
        }
        if (b.length == 0 || (a.length > n && b[0] > a[n])) {
            return a[n];
        }
        if (n == 0) {
            return Math.min(a[0], b[0]);
        }
        final int aLimit = Math.min(a.length - 1, n - 1);
        final int bLimit = Math.min(b.length - 1, n - 1);
        final int range = aLimit + bLimit - (n - 1);
        int i = range >>> 1;
        int iA = aLimit - i;
        int iB = n - 1 - iA;
        int lo = 0;
        int hi = range;
        while (true) {
            if (iA == aLimit) {
                if (iB == bLimit) {
                    return Math.max(a[a.length - 1], b[b.length - 1]);
                } else {
                    if (b[iB + 1] >= a[iA]) {
                        return Math.max(a[iA], b[iB]);
                    }
                }
            } else if (iB == bLimit) {
                if (a[iA + 1] >= b[iB]) {
                    return Math.max(a[iA], b[iB]);
                }
            } else if (a[iA + 1] >= b[iB] && b[iB + 1] >= a[iA]) {
                return Math.max(a[iA], b[iB]);
            }
            if (iA != aLimit && a[iA + 1] < b[iB]) {
                hi = i;
            }
            if (iB != bLimit && b[iB + 1] < a[iA]) {
                lo = i;
                if (lo == hi - 1) {
                    lo++;
                }
            }
            i = (lo + hi) >>> 1;
            iA = aLimit - i;
            iB = n - 1 - iA;
        }
    }

    /**
     * select from two <b>sorted</b> arrays (in ascending orders) the nth smallest element
     * of the two arrays in logarithmic time.
     *
     * @param a the first sorted array
     * @param b the second sorted array
     * @param n the nth smallest (<em>starting from 0</em>)
     * @return the nth smallest element
     */
    public static float select(float[] a, float[] b, int n) {
        assert Util.isSorted(a);
        assert Util.isSorted(b);
        if (n < 0 || n > a.length + b.length - 1) {
            throw new IllegalArgumentException("Out of range");
        }
        if (a.length == 0 && b.length == 0) {
            throw new IllegalArgumentException("empty array");
        }
        if (a.length == 0 || (b.length > n && a[0] > b[n])) {
            return b[n];
        }
        if (b.length == 0 || (a.length > n && b[0] > a[n])) {
            return a[n];
        }
        if (n == 0) {
            return Math.min(a[0], b[0]);
        }
        final int aLimit = Math.min(a.length - 1, n - 1);
        final int bLimit = Math.min(b.length - 1, n - 1);
        final int range = aLimit + bLimit - (n - 1);
        int i = range >>> 1;
        int iA = aLimit - i;
        int iB = n - 1 - iA;
        int lo = 0;
        int hi = range;
        while (true) {
            if (iA == aLimit) {
                if (iB == bLimit) {
                    return Math.max(a[a.length - 1], b[b.length - 1]);
                } else {
                    if (b[iB + 1] >= a[iA]) {
                        return Math.max(a[iA], b[iB]);
                    }
                }
            } else if (iB == bLimit) {
                if (a[iA + 1] >= b[iB]) {
                    return Math.max(a[iA], b[iB]);
                }
            } else if (a[iA + 1] >= b[iB] && b[iB + 1] >= a[iA]) {
                return Math.max(a[iA], b[iB]);
            }
            if (iA != aLimit && a[iA + 1] < b[iB]) {
                hi = i;
            }
            if (iB != bLimit && b[iB + 1] < a[iA]) {
                lo = i;
                if (lo == hi - 1) {
                    lo++;
                }
            }
            i = (lo + hi) >>> 1;
            iA = aLimit - i;
            iB = n - 1 - iA;
        }
    }

    /**
     * select from two <b>sorted</b> arrays (in ascending orders) the nth smallest element
     * of the two arrays in logarithmic time.
     *
     * @param a the first sorted array
     * @param b the second sorted array
     * @param n the nth smallest (<em>starting from 0</em>)
     * @return the nth smallest element
     */
    public static int select(int[] a, int[] b, int n) {
        assert Util.isSorted(a);
        assert Util.isSorted(b);
        if (n < 0 || n > a.length + b.length - 1) {
            throw new IllegalArgumentException("Out of range");
        }
        if (a.length == 0 && b.length == 0) {
            throw new IllegalArgumentException("empty array");
        }
        if (a.length == 0 || (b.length > n && a[0] > b[n])) {
            return b[n];
        }
        if (b.length == 0 || (a.length > n && b[0] > a[n])) {
            return a[n];
        }
        if (n == 0) {
            return Math.min(a[0], b[0]);
        }
        final int aLimit = Math.min(a.length - 1, n - 1);
        final int bLimit = Math.min(b.length - 1, n - 1);
        final int range = aLimit + bLimit - (n - 1);
        int i = range >>> 1;
        int iA = aLimit - i;
        int iB = n - 1 - iA;
        int lo = 0;
        int hi = range;
        while (true) {
            if (iA == aLimit) {
                if (iB == bLimit) {
                    return Math.max(a[a.length - 1], b[b.length - 1]);
                } else {
                    if (b[iB + 1] >= a[iA]) {
                        return Math.max(a[iA], b[iB]);
                    }
                }
            } else if (iB == bLimit) {
                if (a[iA + 1] >= b[iB]) {
                    return Math.max(a[iA], b[iB]);
                }
            } else if (a[iA + 1] >= b[iB] && b[iB + 1] >= a[iA]) {
                return Math.max(a[iA], b[iB]);
            }
            if (iA != aLimit && a[iA + 1] < b[iB]) {
                hi = i;
            }
            if (iB != bLimit && b[iB + 1] < a[iA]) {
                lo = i;
                if (lo == hi - 1) {
                    lo++;
                }
            }
            i = (lo + hi) >>> 1;
            iA = aLimit - i;
            iB = n - 1 - iA;
        }
    }

    /**
     * select from two <b>sorted</b> arrays (in ascending orders) the nth smallest element
     * of the two arrays in logarithmic time.
     *
     * @param a the first sorted array
     * @param b the second sorted array
     * @param n the nth smallest (<em>starting from 0</em>)
     * @return the nth smallest element
     */
    public static short select(short[] a, short[] b, int n) {
        assert Util.isSorted(a);
        assert Util.isSorted(b);
        if (n < 0 || n > a.length + b.length - 1) {
            throw new IllegalArgumentException("Out of range");
        }
        if (a.length == 0 && b.length == 0) {
            throw new IllegalArgumentException("empty array");
        }
        if (a.length == 0 || (b.length > n && a[0] > b[n])) {
            return b[n];
        }
        if (b.length == 0 || (a.length > n && b[0] > a[n])) {
            return a[n];
        }
        if (n == 0) {
            return (short) Math.min(a[0], b[0]);
        }
        final int aLimit = Math.min(a.length - 1, n - 1);
        final int bLimit = Math.min(b.length - 1, n - 1);
        final int range = aLimit + bLimit - (n - 1);
        int i = range >>> 1;
        int iA = aLimit - i;
        int iB = n - 1 - iA;
        int lo = 0;
        int hi = range;
        while (true) {
            if (iA == aLimit) {
                if (iB == bLimit) {
                    return (short) Math.max(a[a.length - 1], b[b.length - 1]);
                } else {
                    if (b[iB + 1] >= a[iA]) {
                        return (short) Math.max(a[iA], b[iB]);
                    }
                }
            } else if (iB == bLimit) {
                if (a[iA + 1] >= b[iB]) {
                    return (short) Math.max(a[iA], b[iB]);
                }
            } else if (a[iA + 1] >= b[iB] && b[iB + 1] >= a[iA]) {
                return (short) Math.max(a[iA], b[iB]);
            }
            if (iA != aLimit && a[iA + 1] < b[iB]) {
                hi = i;
            }
            if (iB != bLimit && b[iB + 1] < a[iA]) {
                lo = i;
                if (lo == hi - 1) {
                    lo++;
                }
            }
            i = (lo + hi) >>> 1;
            iA = aLimit - i;
            iB = n - 1 - iA;
        }
    }

    /**
     * select from two <b>sorted</b> arrays (in ascending orders) the nth smallest element
     * of the two arrays in logarithmic time.
     *
     * @param a the first sorted array
     * @param b the second sorted array
     * @param n the nth smallest (<em>starting from 0</em>)
     * @return the nth smallest element
     */
    public static long select(long[] a, long[] b, int n) {
        assert Util.isSorted(a);
        assert Util.isSorted(b);
        if (n < 0 || n > a.length + b.length - 1) {
            throw new IllegalArgumentException("Out of range");
        }
        if (a.length == 0 && b.length == 0) {
            throw new IllegalArgumentException("empty array");
        }
        if (a.length == 0 || (b.length > n && a[0] > b[n])) {
            return b[n];
        }
        if (b.length == 0 || (a.length > n && b[0] > a[n])) {
            return a[n];
        }
        if (n == 0) {
            return Math.min(a[0], b[0]);
        }
        final int aLimit = Math.min(a.length - 1, n - 1);
        final int bLimit = Math.min(b.length - 1, n - 1);
        final int range = aLimit + bLimit - (n - 1);
        int i = range >>> 1;
        int iA = aLimit - i;
        int iB = n - 1 - iA;
        int lo = 0;
        int hi = range;
        while (true) {
            if (iA == aLimit) {
                if (iB == bLimit) {
                    return Math.max(a[a.length - 1], b[b.length - 1]);
                } else {
                    if (b[iB + 1] >= a[iA]) {
                        return Math.max(a[iA], b[iB]);
                    }
                }
            } else if (iB == bLimit) {
                if (a[iA + 1] >= b[iB]) {
                    return Math.max(a[iA], b[iB]);
                }
            } else if (a[iA + 1] >= b[iB] && b[iB + 1] >= a[iA]) {
                return Math.max(a[iA], b[iB]);
            }
            if (iA != aLimit && a[iA + 1] < b[iB]) {
                hi = i;
            }
            if (iB != bLimit && b[iB + 1] < a[iA]) {
                lo = i;
                if (lo == hi - 1) {
                    lo++;
                }
            }
            i = (lo + hi) >>> 1;
            iA = aLimit - i;
            iB = n - 1 - iA;
        }
    }

    /**
     * select from two <b>sorted</b> arrays (in ascending orders) the nth smallest element
     * of the two arrays in logarithmic time.
     *
     * @param a the first sorted array
     * @param b the second sorted array
     * @param n the nth smallest (<em>starting from 0</em>)
     * @return the nth smallest element
     */
    public static char select(char[] a, char[] b, int n) {
        assert Util.isSorted(a);
        assert Util.isSorted(b);
        if (n < 0 || n > a.length + b.length - 1) {
            throw new IllegalArgumentException("Out of range");
        }
        if (a.length == 0 && b.length == 0) {
            throw new IllegalArgumentException("empty array");
        }
        if (a.length == 0 || (b.length > n && a[0] > b[n])) {
            return b[n];
        }
        if (b.length == 0 || (a.length > n && b[0] > a[n])) {
            return a[n];
        }
        if (n == 0) {
            return (char) Math.min(a[0], b[0]);
        }
        final int aLimit = Math.min(a.length - 1, n - 1);
        final int bLimit = Math.min(b.length - 1, n - 1);
        final int range = aLimit + bLimit - (n - 1);
        int i = range >>> 1;
        int iA = aLimit - i;
        int iB = n - 1 - iA;
        int lo = 0;
        int hi = range;
        while (true) {
            if (iA == aLimit) {
                if (iB == bLimit) {
                    return (char) Math.max(a[a.length - 1], b[b.length - 1]);
                } else {
                    if (b[iB + 1] >= a[iA]) {
                        return (char) Math.max(a[iA], b[iB]);
                    }
                }
            } else if (iB == bLimit) {
                if (a[iA + 1] >= b[iB]) {
                    return (char) Math.max(a[iA], b[iB]);
                }
            } else if (a[iA + 1] >= b[iB] && b[iB + 1] >= a[iA]) {
                return (char) Math.max(a[iA], b[iB]);
            }
            if (iA != aLimit && a[iA + 1] < b[iB]) {
                hi = i;
            }
            if (iB != bLimit && b[iB + 1] < a[iA]) {
                lo = i;
                if (lo == hi - 1) {
                    lo++;
                }
            }
            i = (lo + hi) >>> 1;
            iA = aLimit - i;
            iB = n - 1 - iA;
        }
    }

    /**
     * select from two <b>sorted</b> arrays (in ascending orders) the nth smallest element
     * of the two arrays in logarithmic time.
     *
     * @param a the first sorted array
     * @param b the second sorted array
     * @param n the nth smallest (<em>starting from 0</em>)
     * @return the nth smallest element
     */
    public static <T extends Comparable<? super T>> T select(T[] a, T[] b, int n) {
        assert Util.isSorted(a);
        assert Util.isSorted(b);
        if (n < 0 || n > a.length + b.length - 1) {
            throw new IllegalArgumentException("Out of range");
        }
        if (a.length == 0 && b.length == 0) {
            throw new IllegalArgumentException("empty array");
        }
        if (a.length == 0 || (b.length > n && Util.less(b[n], a[0]))) {
            return b[n];
        }
        if (b.length == 0 || (a.length > n && Util.less(a[n], b[0]))) {
            return a[n];
        }
        if (n == 0) {
            return min(a[0], b[0]);
        }
        final int aLimit = Math.min(a.length - 1, n - 1);
        final int bLimit = Math.min(b.length - 1, n - 1);
        final int range = aLimit + bLimit - (n - 1);
        int i = range >>> 1;
        int iA = aLimit - i;
        int iB = n - 1 - iA;
        int lo = 0;
        int hi = range;
        while (true) {
            if (iA == aLimit) {
                if (iB == bLimit) {
                    return max(a[a.length - 1], b[b.length - 1]);
                } else {
                    if (!Util.less(b[iB + 1], a[iA])) {
                        return max(a[iA], b[iB]);
                    }
                }
            } else if (iB == bLimit) {
                if (!Util.less(a[iA + 1], b[iB])) {
                    return max(a[iA], b[iB]);
                }
            } else if (!Util.less(a[iA + 1], b[iB]) && !Util.less(b[iB + 1], a[iA])) {
                return max(a[iA], b[iB]);
            }
            if (iA != aLimit && Util.less(a[iA + 1], b[iB])) {
                hi = i;
            }
            if (iB != bLimit && Util.less(b[iB + 1], a[iA])) {
                lo = i;
                if (lo == hi - 1) {
                    lo++;
                }
            }
            i = (lo + hi) >>> 1;
            iA = aLimit - i;
            iB = n - 1 - iA;
        }
    }

    /**
     * select from two <b>sorted</b> arrays (in ascending orders) the nth smallest element
     * of the two arrays in logarithmic time.
     *
     * @param a the first sorted array
     * @param b the second sorted array
     * @param n the nth smallest (<em>starting from 0</em>)
     * @param c the comparator of type T
     * @return the nth smallest element
     */
    public static <T> T select(T[] a, T[] b, int n, Comparator<? super T> c) {
        assert Util.isSorted(a, c);
        assert Util.isSorted(b, c);
        if (n < 0 || n > a.length + b.length - 1) {
            throw new IllegalArgumentException("Out of range");
        }
        if (a.length == 0 && b.length == 0) {
            throw new IllegalArgumentException("empty array");
        }
        if (a.length == 0 || (b.length > n && Util.less(b[n], a[0], c))) {
            return b[n];
        }
        if (b.length == 0 || (a.length > n && Util.less(a[n], b[0], c))) {
            return a[n];
        }
        if (n == 0) {
            return min(a[0], b[0], c);
        }
        final int aLimit = Math.min(a.length - 1, n - 1);
        final int bLimit = Math.min(b.length - 1, n - 1);
        final int range = aLimit + bLimit - (n - 1);
        int i = range >>> 1;
        int iA = aLimit - i;
        int iB = n - 1 - iA;
        int lo = 0;
        int hi = range;
        while (true) {
            if (iA == aLimit) {
                if (iB == bLimit) {
                    return max(a[a.length - 1], b[b.length - 1], c);
                } else {
                    if (!Util.less(b[iB + 1], a[iA], c)) {
                        return max(a[iA], b[iB], c);
                    }
                }
            } else if (iB == bLimit) {
                if (!Util.less(a[iA + 1], b[iB], c)) {
                    return max(a[iA], b[iB], c);
                }
            } else if (!Util.less(a[iA + 1], b[iB], c) && !Util.less(b[iB + 1], a[iA], c)) {
                return max(a[iA], b[iB], c);
            }
            if (iA != aLimit && Util.less(a[iA + 1], b[iB], c)) {
                hi = i;
            }
            if (iB != bLimit && Util.less(b[iB + 1], a[iA], c)) {
                lo = i;
                if (lo == hi - 1) {
                    lo++;
                }
            }
            i = (lo + hi) >>> 1;
            iA = aLimit - i;
            iB = n - 1 - iA;
        }
    }

    private static <T extends Comparable<? super T>> T min(T v, T w) {
        return v.compareTo(w) <= 0 ? v : w;
    }

    private static <T extends Comparable<? super T>> T max(T v, T w) {
        return v.compareTo(w) >= 0 ? v : w;
    }

    private static <T> T min(T v, T w, Comparator<? super T> c) {
        return c.compare(v, w) <= 0 ? v : w;
    }

    private static <T> T max(T v, T w, Comparator<? super T> c) {
        return c.compare(v, w) >= 0 ? v : w;
    }
}
