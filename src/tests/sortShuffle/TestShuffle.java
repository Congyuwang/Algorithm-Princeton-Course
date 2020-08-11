package tests.sortShuffle;

import java.util.Arrays;

import edu.princeton.cs.algs4.Stopwatch;
import princeton.algo.queue.LinkedQueue;
import princeton.algo.sort.Shuffle;

/**
 * Test the run time of different shuffling algorithms (shuffling
 * for different data structure) at different length.
 */
class TestShuffleArray implements RuntimeTest {

    final int testLength;

    TestShuffleArray(int l) {
        testLength = l;
    }

    @Override
    public double run() {
        Integer[] ints = new Integer[testLength];
        for (int i = 0; i < testLength; i++) {
            ints[i] = i;
        }
        Stopwatch stopwatch = new Stopwatch();
        Shuffle.shuffle(ints);
        double t = stopwatch.elapsedTime();
        Runtime.getRuntime().gc();
        return t;
    }

}

class TestShuffleLink implements RuntimeTest {

    final int testLength;

    TestShuffleLink(int l) {
        testLength = l;
    }

    @Override
    public double run() {
        LinkedQueue<Integer> linkedQueue = new LinkedQueue<>();
        for (int i = 0; i < testLength; i++) {
            linkedQueue.enqueue(i);
        }
        Stopwatch stopwatch = new Stopwatch();
        Shuffle.shuffle(linkedQueue);
        double t = stopwatch.elapsedTime();
        Runtime.getRuntime().gc();
        return t;
    }
}

class TestShuffle {
    public static void main(String[] args) {
        int LENGTH = 37;
        int[] lengths = new int[LENGTH];
        lengths[0] = 2048;
        for (int i = 1; i < LENGTH; i++) {
            lengths[i] = (int) Math.floor(lengths[i - 1] * 1.2);
        }
        double[][] logTime = new double[2][lengths.length];
        int i = 0;
        for (int length : lengths) {
            RuntimeTest test1 = new TestShuffleArray(length);
            RuntimeTest test2 = new TestShuffleLink(length);
            System.out.println("length:" + length);
            logTime[0][i] = Math.log(test1.run(10));
            System.out.println(logTime[0][i]);
            logTime[1][i] = Math.log(test2.run(10));
            System.out.println(logTime[1][i]);
            System.out.println();
            i++;
        }
        System.out.println(Arrays.deepToString(logTime));
    }
}
