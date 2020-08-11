package tests.sortShuffle;

import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;
import princeton.algo.sort.Quick;

/**
 * Test QuickSelect algorithm runtime
 */
class QuickSelectTest implements RuntimeTest {

    private final int testLength;

    QuickSelectTest(int testLength) {
        this.testLength = testLength;
    }

    @Override
    public double run() {
        Random random = new Random();
        double[] a = new double[testLength];
        for (int i = 0; i < testLength; i++) {
            a[i] = random.nextDouble();
        }
        Stopwatch stopwatch = new Stopwatch();
        Quick.select(a, testLength / 10);
        return stopwatch.elapsedTime();
    }

    public static void main(String[] args) {
        int[] lengths = {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 256000, 512000, 1024000};
        final int REPEAT = 50;
        for (int l : lengths) {
            double mean = 0.0;
            for (int j = 0; j < REPEAT; j++) {
                QuickSelectTest quickSelectTest = new QuickSelectTest(l);
                mean += quickSelectTest.run() / REPEAT;
                Runtime.getRuntime().gc();
            }
            System.out.printf("%10d takes: %.8f \n", l, mean);
        }
    }
}
