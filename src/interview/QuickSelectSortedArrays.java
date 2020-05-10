package interview;

import java.util.Comparator;

import princeton.algo.sort.Util;

/**
 * Find the nth smallest element from two <b>sorted</b> array. <em>n start from
 * 0.</em> 0 means the smallest.
 */
public class QuickSelectSortedArrays {
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

    public static void main(String[] args) {
        System.out.println("Primitive:");
        double[] t1 = { 2.5, 9.0 };
        double[] t2 = { 0.2, 1.0, 2.0, 3.0, 5.0};
        for (int i = 0; i < 7; i++) {
            System.out.println(select(t1, t2, i));
        }
        System.out.println("Comparable:");
        Double[] dt1 = { 0.2, 1.0, 2.0, 3.0, 5.0 };
        Double[] dt2 = { 2.5, 9.0 };
        for (int i = 0; i < 7; i++) {
            System.out.println(select(dt1, dt2, i));
        }
    }
}
