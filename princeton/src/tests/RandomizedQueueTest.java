package tests;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import princeton.algo.queue.RandomizedQueue;

class RandomizedQueueTest {
    public static void main(String[] args) {
        int RANGE = 10;
        int ROUND = 10000;
        int[][] uniformChecker = new int[RANGE][RANGE];
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        // add integers
        for (int i = 0; i < RANGE; i++) {
            randomizedQueue.enqueue(i);
        }
        // count the appearance of number s at position pos
        for (int round = 0; round < ROUND; round++) {
            int pos = 0;
            for (int s : randomizedQueue) {
                uniformChecker[pos++][s]++;
            }
        }
        // print the result
        for (int[] a : uniformChecker) {
            for (int s : a) {
                System.out.printf("%6d ", s);
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
        System.out.printf("Chi5 = %f\n", squaredSum / expected);
        System.out.printf("p-value = %f\n", chiSquaredDistribution.cumulativeProbability(chiSquared));
    }
}
