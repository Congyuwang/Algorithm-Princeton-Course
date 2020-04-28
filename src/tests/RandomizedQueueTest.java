package tests;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import princeton.algo.queue.RandomizedQueue;
import princeton.algo.queue.Deque;

class RandomizedQueueTest {
    public static void main(String[] args) {
        int RANGE = 10;
        int ROUND = 10000;
        int[][] uniformChecker1 = new int[RANGE][RANGE];
        int[][] uniformChecker2 = new int[RANGE][RANGE];
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        Deque<Integer> deque = new Deque<>();
        // add integers
        for (int i = 0; i < RANGE; i++) {
            randomizedQueue.enqueue(i);
            deque.enqueue(i);
        }
        // count the appearance of number s at position pos
        for (int round = 0; round < ROUND; round++) {
            int pos = 0;
            for (int s : randomizedQueue) {
                uniformChecker1[pos++][s]++;
            }
            pos = 0;
            deque.shuffle();
            for (int s : deque) {
                uniformChecker2[pos++][s]++;
            }
        }
        // print the result
        System.out.println("randomizedQueue:");
        for (int[] a : uniformChecker1) {
            for (int s : a) {
                System.out.printf("%6d ", s);
            }
            System.out.println();
        }
        System.out.println("\ndeque:");
        for (int[] a : uniformChecker2) {
            for (int s : a) {
                System.out.printf("%6d ", s);
            }
            System.out.println();
        }
        // calculate the Chi-squared statistics
        double squaredSum1 = 0;
        double squaredSum2 = 0;
        double expected = (double) ROUND / RANGE;
        for (int i = 0; i < RANGE; i++) {
            for (int j = 0; j < RANGE; j++) {
                squaredSum1 += Math.pow(uniformChecker1[i][j] - expected, 2);
            }
        }
        for (int i = 0; i < RANGE; i++) {
            for (int j = 0; j < RANGE; j++) {
                squaredSum2 += Math.pow(uniformChecker2[i][j] - expected, 2);
            }
        }
        int degreeOfFreedom = (RANGE - 1) * (RANGE - 1);
        double chiSquared1 = squaredSum1 / expected;
        double chiSquared2 = squaredSum2 / expected;
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(degreeOfFreedom);
        System.out.println("\nrandomizedQueue:");
        System.out.printf("Chi(%d) = %f\n", degreeOfFreedom, squaredSum1 / expected);
        System.out.printf("p-value = %f\n", chiSquaredDistribution.cumulativeProbability(chiSquared1));
        System.out.println("\ndeque:");
        System.out.printf("Chi(%d) = %f\n", degreeOfFreedom, squaredSum2 / expected);
        System.out.printf("p-value = %f\n", chiSquaredDistribution.cumulativeProbability(chiSquared2));
    }
}
