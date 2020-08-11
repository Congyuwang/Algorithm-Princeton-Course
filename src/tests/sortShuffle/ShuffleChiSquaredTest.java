package tests.sortShuffle;

import java.util.Random;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import princeton.algo.queue.LinkedQueue;
import princeton.algo.stack.ArrayStack;
import princeton.algo.stack.LinkedStack;
import princeton.algo.queue.RandomizedQueue;
import princeton.algo.queue.ArrayQueue;
import princeton.algo.queue.Deque;
import princeton.algo.sort.Shuffle;

/**
 * This class {@code ShuffleChiSquaredTest} tests whether each shuffle
 * or random algorithms are evenly distributed after shuffling.
 * It generates ordered integers from 1 to {@code range - 1}, and shuffles them.
 * This process is repeated for {@code round} rounds.
 */
class ShuffleChiSquaredTest {
    public static void main(String[] args) {
        int RANGE = 10;
        int LARGE_RANGE = 1000;
        int ROUND = 10_000;
        test("randomizedQueue", RANGE, ROUND);
        test("deque", RANGE, ROUND);
        test("linkedQueue", RANGE, ROUND);
        test("linkedStack", RANGE, ROUND);
        test("shuffleQueue", RANGE, ROUND);
        test("shuffleStack", RANGE, ROUND);
        test("shuffleArrayStack", RANGE, ROUND);
        test("shuffleArrayQueue", RANGE, ROUND);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randomRange = 5000 + random.nextInt(5000);
            System.out.println(">>>>>>>>>> random range = " + randomRange);
            test("randomizedQueue", randomRange, 200, false);
            test("deque", randomRange, 200, false);
            test("linkedQueue", randomRange, 200, false);
            test("linkedStack", randomRange, 200, false);
            test("shuffleQueue", randomRange, 200, false);
            test("shuffleStack", randomRange, 200, false);
            test("shuffleArrayStack", randomRange, 200, false);
            test("shuffleArrayQueue", randomRange, 200, false);
        }
        test("randomizedQueue", LARGE_RANGE, ROUND, false);
        test("deque", LARGE_RANGE, ROUND, false);
        test("linkedQueue", LARGE_RANGE, ROUND, false);
        test("linkedStack", LARGE_RANGE, ROUND, false);
        test("shuffleQueue", LARGE_RANGE, ROUND, false);
        test("shuffleStack", LARGE_RANGE, ROUND, false);
        test("shuffleArrayStack", LARGE_RANGE, ROUND, false);
        test("shuffleArrayQueue", LARGE_RANGE, ROUND, false);
    }

    /**
     * It generates ordered integers from 1 to {@code range - 1}, and shuffles them.
     * This process is repeated for {@code round} rounds.
     * The test will print the chi-squared statistics and p-value.
     *
     * @param algorithm name of the algorithms to be tested, include randomizedQueue,
     *                  deque, linkedQueue, linkedStack, shuffleQueue, shuffleStack,
     *                  shuffleArrayStack, shuffleArrayQueue
     * @param range     generate integers from {@code 0} to {@code range - 1}
     * @param round     the number of rounds to run for statistics
     */
    public static void test(String algorithm, int range, int round) {
        test(algorithm, range, round, true);
    }

    /**
     * It generates ordered integers from 1 to {@code range - 1}, and shuffles them.
     * This process is repeated for {@code round} rounds.
     * The test will print the chi-squared statistics and p-value.
     * <p>
     *     If {@code ifPrint} is set to {@code true}, this test will print the
     *     statistic table for calculating the the chi-squared statistics.
     *     This is not recommended if {@code range} is large.
     * </p>
     *
     * @param algorithm name of the algorithms to be tested, include randomizedQueue,
     *                  deque, linkedQueue, linkedStack, shuffleQueue, shuffleStack,
     *                  shuffleArrayStack, shuffleArrayQueue
     * @param range     generate integers from {@code 0} to {@code range - 1}
     * @param round     the number of rounds to run for statistics
     * @param ifPrint   whether to print the detailed distribution table, if {@code range}
     *                  is big, do not set {@code ifPrint} to {@code true}
     */
    public static void test(String algorithm, int range, int round, boolean ifPrint) {
        final int[][] uniformChecker = new int[range][range];
        // count the appearance of number s at position pos
        switch (algorithm) {
            case "randomizedQueue":
                for (int r = 0; r < round; r++) {
                    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
                    // add integers
                    for (int i = 0; i < range; i++) {
                        randomizedQueue.enqueue(i);
                    }
                    int pos = 0;
                    for (int s : randomizedQueue) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            case "deque":
                for (int r = 0; r < round; r++) {
                    Deque<Integer> deque = new Deque<>();
                    // add integers
                    for (int i = 0; i < range; i++) {
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
                for (int r = 0; r < round; r++) {
                    LinkedQueue<Integer> linkedQueue = new LinkedQueue<>();
                    // add integers
                    for (int i = 0; i < range; i++) {
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
                for (int r = 0; r < round; r++) {
                    LinkedStack<Integer> linkedStack = new LinkedStack<>();
                    // add integers
                    for (int i = 0; i < range; i++) {
                        linkedStack.push(i);
                    }
                    int pos = 0;
                    linkedStack.shuffle();
                    for (int s : linkedStack) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            case "shuffleQueue":
                for (int r = 0; r < round; r++) {
                    LinkedQueue<Integer> linkedQueue = new LinkedQueue<>();
                    // add integers
                    for (int i = 0; i < range; i++) {
                        linkedQueue.enqueue(i);
                    }
                    Shuffle.shuffle(linkedQueue);
                    int pos = 0;
                    for (int s : linkedQueue) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            case "shuffleStack":
                for (int r = 0; r < round; r++) {
                    LinkedStack<Integer> linkedStack = new LinkedStack<>();
                    // add integers
                    for (int i = 0; i < range; i++) {
                        linkedStack.push(i);
                    }
                    Shuffle.shuffle(linkedStack);
                    int pos = 0;
                    for (int s : linkedStack) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            case "shuffleArrayQueue":
                for (int r = 0; r < round; r++) {
                    ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
                    // add integers
                    for (int i = range / 3 - range; i < range / 3; i++) {
                        arrayQueue.enqueue(i);
                    }
                    for (int i = range / 3 - range; i < 0; i++) {
                        arrayQueue.dequeue();
                    }
                    for (int i = range / 3; i < range; i++) {
                        arrayQueue.enqueue(i);
                    }
                    arrayQueue.shuffle();
                    int pos = 0;
                    for (int s : arrayQueue) {
                        uniformChecker[pos++][s]++;
                    }
                }
                break;
            case "shuffleArrayStack":
                for (int r = 0; r < round; r++) {
                    ArrayStack<Integer> arrayStack = new ArrayStack<>();
                    // add integers
                    for (int i = 0; i < range; i++) {
                        arrayStack.push(i);
                    }
                    arrayStack.shuffle();
                    int pos = 0;
                    for (int s : arrayStack) {
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
        double expected = (double) round / range;
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                squaredSum += Math.pow(uniformChecker[i][j] - expected, 2);
            }
        }
        int degreeOfFreedom = (range - 1) * (range - 1);
        double chiSquared = squaredSum / expected;
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(degreeOfFreedom);
        System.out.printf("Chi(%d) = %f\n", degreeOfFreedom, squaredSum / expected);
        System.out.printf("p-value = %f\n", 1 - chiSquaredDistribution.cumulativeProbability(chiSquared));
        System.out.println();
    }
}
