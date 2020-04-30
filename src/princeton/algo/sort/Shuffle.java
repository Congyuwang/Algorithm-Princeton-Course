package princeton.algo.sort;

import java.util.Random;
import princeton.algo.queue.Queue;
import princeton.algo.stack.Stack;

/**
 * The Shuffle class shuffles any array shorter than MAX.INTEGER uniformly in
 * linear time, and shuffles stack and queue in N log(N).
 */
public class Shuffle {

    private Shuffle() {}

    /**
     * Uniformly shuffle any array in linear time.
     *
     * @param a the input array
     */
    public static void shuffle(Object[] a) {
        Random random = new Random();
        int size = a.length;
        for (int i = 1; i < size; i++) {
            int j = random.nextInt(i + 1);
            Object temp = a[j];
            a[j] = a[i];
            a[i] = temp;
        }
    }

    /**
     * Uniformly shuffle a Queue in N log N time.
     *
     * @param d    the Queue
     * @param <T>  the type of input queue (which extends {@code Queue} interface)
     * @param <T2> the component type of the input queue
     */
    public static <T extends Queue<T2>, T2> void shuffle(T d) {
        int size = d.size();
        Random random = new Random();
        T shuffled = shuffle(d, random);
        for (int i = 0; i < size; i++) {
            assert shuffled != null;
            d.enqueue(shuffled.dequeue());
        }
    }

    /**
     * Uniformly shuffle a Stack in N log N time.
     *
     * @param s    the stack
     * @param <T>  the type of input stack (which extends {@code Stack} interface)
     * @param <T2> the component type of the input stack
     */
    public static <T extends Stack<T2>, T2> void shuffle(T s) {
        int size = s.size();
        Random random = new Random();
        T shuffled = shuffle(s, random);
        for (int i = 0; i < size; i++) {
            assert shuffled != null;
            s.push(shuffled.pop());
        }
    }

    private static <T extends Queue<T2>, T2> T shuffle(T d, Random random) {
        assert d != null;
        int dSize = d.size();
        if (dSize == 1) {
            return d;
        }
        try {
            @SuppressWarnings("unchecked")
            T d1 = (T) d.getClass().getConstructor().newInstance();
            @SuppressWarnings("unchecked")
            T d2 = (T) d.getClass().getConstructor().newInstance();
            boolean alternate = true;
            for (int i = 0; i < dSize; i++) {
                if (alternate) {
                    d1.enqueue(d.dequeue());
                    alternate = false;
                } else {
                    d2.enqueue(d.dequeue());
                    alternate = true;
                }
            }
            return merge(shuffle(d1, random), shuffle(d2, random), random);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T extends Stack<T2>, T2> T shuffle(T s, Random random) {
        assert s != null;
        int dSize = s.size();
        if (dSize == 1) {
            return s;
        }
        try {
            @SuppressWarnings("unchecked")
            T s1 = (T) s.getClass().getConstructor().newInstance();
            @SuppressWarnings("unchecked")
            T s2 = (T) s.getClass().getConstructor().newInstance();
            boolean alternate = true;
            for (int i = 0; i < dSize; i++) {
                if (alternate) {
                    s1.push(s.pop());
                    alternate = false;
                } else {
                    s2.push(s.pop());
                    alternate = true;
                }
            }
            return merge(shuffle(s1, random), shuffle(s2, random), random);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T extends Stack<T2>, T2> T merge(T s1, T s2, Random random) {
        assert s1 != null;
        assert s2 != null;
        try {
            @SuppressWarnings("unchecked")
            T stack = (T) s1.getClass().getConstructor().newInstance();
            while (s1.size() > 0 || s2.size() > 0) {
                double p = (double) s1.size() / (s1.size() + s2.size());
                if (p == 0) {
                    stack.push(s2.pop());
                } else if (p == 1 || random.nextDouble() < p) {
                    stack.push(s1.pop());
                } else {
                    stack.push(s2.pop());
                }
            }
            return stack;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T extends Queue<T2>, T2> T merge(T d1, T d2, Random random) {
        assert d1 != null;
        assert d2 != null;
        try {
            @SuppressWarnings("unchecked")
            T queue = (T) d1.getClass().getConstructor().newInstance();
            while (d1.size() > 0 || d2.size() > 0) {
                double p = (double) d1.size() / (d1.size() + d2.size());
                if (p == 0) {
                    queue.enqueue(d2.dequeue());
                } else if (p == 1 || random.nextDouble() < p) {
                    queue.enqueue(d1.dequeue());
                } else {
                    queue.enqueue(d2.dequeue());
                }
            }
            return queue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
