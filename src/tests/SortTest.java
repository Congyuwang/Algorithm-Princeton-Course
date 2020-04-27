package tests;

import princeton.algo.sort.*;
import java.util.Random;
import java.util.Stack;
import org.apache.commons.math3.distribution.TDistribution;

import edu.princeton.cs.algs4.Stopwatch;

class SortTest {
    public static void main(String[] args) {
        int LENGTH = 10_000;
        int BIG_LENGTH = 100_000;
        Random random = new Random();
        Double[] test1 = new Double[LENGTH];
        Double[] test2 = new Double[LENGTH];
        String[] test3 = new String[LENGTH];
        String[] test4 = new String[BIG_LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            test1[i] = random.nextDouble();
            test2[i] = random.nextDouble() + i * 0.2;
            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
            test3[i] = String.valueOf(bytes);
        }
        for (int i = 0; i < BIG_LENGTH; i++) {
            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
            test4[i] = String.valueOf(bytes);
        }

        // test1: Selection sort
        System.out.println("Test1 (random order):");
        randomDoubleTest("selection", LENGTH, 10);
        randomDoubleTest("insertion", LENGTH, 10);
        randomDoubleTest("shell", LENGTH, 10);
        randomDoubleTest("merge", LENGTH, 100);
        randomDoubleTest("mergeBU", LENGTH, 100);

        // test2: Insertion sort (partially sorted)
        System.out.println("Test2 (partially sorted):");
        test("selection", test2, 10);
        test("insertion", test2);
        test("shell", test2, 100);
        test("merge", test2, 100);
        test("mergeBU", test2, 100);

        // test3: Sort Strings
        System.out.println("Test3 (sort strings):");
        randomStringTest("selection", 20, LENGTH, 10);
        randomStringTest("insertion", 20, LENGTH, 10);
        randomStringTest("shell", 20, LENGTH, 10);
        randomStringTest("merge", 20, LENGTH, 100);
        randomStringTest("mergeBU", 20, LENGTH, 100);

        // test3: Sort lots of Strings
        System.out.println("Test4 (sort lots of Strings):");
        randomStringTest("shell", 20, BIG_LENGTH, 10);
        randomStringTest("merge", 20, BIG_LENGTH, 100);
        randomStringTest("mergeBU", 20, BIG_LENGTH, 100);
    }

    private static <T extends Comparable<? super T>> Double test(String algorithm, T[] test) {
        return test(algorithm, test, true);
    }

    private static <T extends Comparable<? super T>> Double test(String algorithm, T[] test, boolean ifPrint) {
        T[] testCopy = test.clone();
        Stopwatch timer = new Stopwatch();
        switch (algorithm) {
            case "selection":
                Selection.sort(testCopy);
                break;
            case "insertion":
                Insertion.sort(testCopy);
                break;
            case "shell":
                Shell.sort(testCopy);
                break;
            case "merge":
                Merge.sort(testCopy);
                break;
            case "mergeBU":
                MergeBU.sort(testCopy);
                break;
            default:
                break;
        }
        Double time = timer.elapsedTime();
        if (ifPrint) {
            System.out.printf("%15s sort: ", algorithm);
            System.out.printf("     elapsed time = %.5f", time);
            System.out.printf(", IsSorted: %b\n", Util.isSorted(testCopy));
        }
        return time;
    }

    private static <T extends Comparable<? super T>> void test(String algorithm, T[] test, int times) {
        TDistribution tDistribution = new TDistribution(times - 1);
        Stack<Double> time = new Stack<>();
        double mean = 0.0;
        double variance = 0.0;
        double upper = 0.0;
        double lower = 0.0;
        for (int i = 0; i < times; i++) {
            Double t = test(algorithm, test, false);
            mean += t / times;
            time.add(t);
        }
        for (double t : time) {
            variance += Math.pow(t - mean, 2) / (times - 1);
        }
        upper = mean + Math.sqrt(variance / (times - 1)) * tDistribution.inverseCumulativeProbability(0.975);
        lower = mean - Math.sqrt(variance / (times - 1)) * tDistribution.inverseCumulativeProbability(0.975);
        System.out.printf("%15s sort: ", algorithm);
        System.out.printf("mean elapsed time = %.5f,", mean);
        System.out.printf(" 95%% CI = [%.5f, %.5f]\n", lower, upper);
    }

    private static <T extends Comparable<? super T>> void randomDoubleTest(String algorithm, int arrayLength, int times) {
        Double[] test = new Double[arrayLength];
        Random random = new Random();
        TDistribution tDistribution = new TDistribution(times - 1);
        Stack<Double> time = new Stack<>();
        double mean = 0.0;
        double variance = 0.0;
        double upper = 0.0;
        double lower = 0.0;
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < arrayLength; j++) {
                test[j] = random.nextDouble();
            }
            Double t = test(algorithm, test, false);
            mean += t / times;
            time.add(t);
        }
        for (double t : time) {
            variance += Math.pow(t - mean, 2) / (times - 1);
        }
        upper = mean + Math.sqrt(variance / (times - 1)) * tDistribution.inverseCumulativeProbability(0.975);
        lower = mean - Math.sqrt(variance / (times - 1)) * tDistribution.inverseCumulativeProbability(0.975);
        System.out.printf("%15s sort: ", algorithm);
        System.out.printf("mean elapsed time = %.5f,", mean);
        System.out.printf(" 95%% CI = [%.5f, %.5f]\n", lower, upper);
    }

    private static <T extends Comparable<? super T>> void randomStringTest(String algorithm, int stringLength, int arrayLength, int times) {
        String[] test = new String[arrayLength];
        Random random = new Random();
        TDistribution tDistribution = new TDistribution(times - 1);
        Stack<Double> time = new Stack<>();
        double mean = 0.0;
        double variance = 0.0;
        double upper = 0.0;
        double lower = 0.0;
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < arrayLength; j++) {
                byte[] bytes = new byte[stringLength];
                random.nextBytes(bytes);
                test[j] = String.valueOf(bytes);
            }
            Double t = test(algorithm, test, false);
            mean += t / times;
            time.add(t);
        }
        for (double t : time) {
            variance += Math.pow(t - mean, 2) / (times - 1);
        }
        upper = mean + Math.sqrt(variance / (times - 1)) * tDistribution.inverseCumulativeProbability(0.975);
        lower = mean - Math.sqrt(variance / (times - 1)) * tDistribution.inverseCumulativeProbability(0.975);
        System.out.printf("%15s sort: ", algorithm);
        System.out.printf("mean elapsed time = %.5f,", mean);
        System.out.printf(" 95%% CI = [%.5f, %.5f]\n", lower, upper);
    }
}
