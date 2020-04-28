package tests;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import princeton.algo.queue.LinkedQueue;
import princeton.algo.stack.LinkedStack;
import princeton.algo.queue.RandomizedQueue;
import princeton.algo.queue.Deque;

class RandomizedQueueTest {
    public static void main(String[] args) {
        int RANGE = 10;
        int LARGE_RANGE = 1000;
        int ROUND = 10_000;
        test("randomizedQueue", RANGE, ROUND);
        test("deque", RANGE, ROUND);
        test("linkedQueue", RANGE, ROUND);
        test("linkedStack", RANGE, ROUND);
        test("randomizedQueue", LARGE_RANGE, ROUND, false);
        test("deque", LARGE_RANGE, ROUND, false);
        test("linkedQueue", LARGE_RANGE, ROUND, false);
        test("linkedStack", LARGE_RANGE, ROUND, false);
    }

    public static void test(String algorithm, int range, int round) {
        test(algorithm, range, round, true);
    }

    public static void test(String algorithm, int range, int round, boolean ifPrint) {
        final int RANGE = range;
        final int ROUND = round;
        final int[][] uniformChecker = new int[RANGE][RANGE];
        // count the appearance of number s at position pos
        switch (algorithm) {
            case "randomizedQueue":
                for (int r = 0; r < ROUND; r++) {
                    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
                    // add integers
                    for (int i = 0; i < RANGE; i++) {
                        randomizedQueue.enqueue(i);
                    }
                    int pos = 0;
                    for (int s : randomizedQueue) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            case "deque":
                for (int r = 0; r < ROUND; r++) {
                    Deque<Integer> deque = new Deque<>();
                    // add integers
                    for (int i = 0; i < RANGE; i++) {
                        deque.enqueue(i);
                    }
                    int pos = 0;
                    deque.shuffle();
                    for (int s : deque) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            case "linkedQueue":
                for (int r = 0; r < ROUND; r++) {
                    LinkedQueue<Integer> linkedQueue = new LinkedQueue<>();
                    // add integers
                    for (int i = 0; i < RANGE; i++) {
                        linkedQueue.enqueue(i);
                    }
                    int pos = 0;
                    linkedQueue.shuffle();
                    for (int s : linkedQueue) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            case "linkedStack":
                for (int r = 0; r < ROUND; r++) {
                    LinkedStack<Integer> linkedStack = new LinkedStack<>();
                    // add integers
                    for (int i = 0; i < RANGE; i++) {
                        linkedStack.push(i);
                    }
                    int pos = 0;
                    linkedStack.shuffle();
                    for (int s : linkedStack) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            default:
                break;
        }
        // print the result
        System.out.println(algorithm + ":");
        if (ifPrint) {
            for (int[] a : uniformChecker) {
                for (int s : a) {
                    System.out.printf("%6d ", s);
                }
                System.out.println();
            }
            System.out.println();
        }
        // calculate the Chi-squared statistics
        double squaredSum = 0;
        double expected = (double) ROUND / RANGE;
        for (int i = 0; i < RANGE; i++) {
            for (int j = 0; j < RANGE; j++) {
                squaredSum += Math.pow(uniformChecker[i][j] - expected, 2);
            }
        }
        int degreeOfFreedom = (RANGE - 1) * (RANGE - 1);
        double chiSquared = squaredSum / expected;
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(degreeOfFreedom);
        System.out.printf("Chi(%d) = %f\n", degreeOfFreedom, squaredSum / expected);
        System.out.printf("p-value = %f\n", 1 - chiSquaredDistribution.cumulativeProbability(chiSquared));
        System.out.println();
    }
}
