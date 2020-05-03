package tests;

import princeton.algo.sort.*;
import princeton.algo.sort.hybrid.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import org.apache.commons.math3.distribution.TDistribution;
import edu.princeton.cs.algs4.Stopwatch;

class SortTest {
    public static void main(String[] args) {
        int LENGTH = 10_000;
        int BIG_LENGTH = 100_000;
        Random random = new Random();
        Double[] test2 = new Double[BIG_LENGTH];
        Double[] test3 = new Double[LENGTH];
        for (int i = 0; i < BIG_LENGTH; i++) {
            test2[i] = random.nextDouble() + i * 0.2;
        }
        for (int i = 0; i < LENGTH; i++) {
            test3[i] = random.nextDouble() - i * 0.2;
        }

        // test1: Selection sort
        System.out.println("\nTest1 (random order):");
        randomDoubleTest("selection", LENGTH, 100);
        randomDoubleTest("reference_selection", LENGTH, 100);
        randomDoubleTest("insertion", LENGTH, 100);
        randomDoubleTest("reference_insertion", LENGTH, 100);
        randomDoubleTest("shell", LENGTH, 100);
        randomDoubleTest("reference_shell", LENGTH, 100);
        randomDoubleTest("merge", LENGTH, 100);
        randomDoubleTest("reference_merge", LENGTH, 100);
        randomDoubleTest("mergeBU", LENGTH, 100);
        randomDoubleTest("reference_mergeBU", LENGTH, 100);
        randomDoubleTest("timSort", LENGTH, 100);
        randomDoubleTest("wikiSortWithBuffer", LENGTH, 100);
        randomDoubleTest("grailSortWithoutBuffer", LENGTH, 100);
        randomDoubleTest("grailSortWithBuffer", LENGTH, 100);
        randomDoubleTest("grailSortWithDynBuffer", LENGTH, 100);

        // test2: Insertion sort (partially sorted)
        System.out.println("\nTest2 (partially sorted):");
        test("selection", test2, 100);
        test("reference_selection", test2, 100);
        test("insertion", test2);
        test("reference_insertion", test2);
        test("shell", test2, 100);
        test("reference_shell", test2, 100);
        test("merge", test2, 100);
        test("reference_merge", test2, 100);
        test("mergeBU", test2, 100);
        test("reference_mergeBU", test2, 100);
        test("timSort", test2, 100);
        test("wikiSortWithBuffer", test2, 100);
        test("grailSortWithoutBuffer", test2, 100);
        test("grailSortWithBuffer", test2, 100);
        test("grailSortWithDynBuffer", test2, 100);

        // test3: Reverse Order
        System.out.println("\nTest3 (reverse order):");
        test("selection", test3, 100);
        test("reference_selection", test3, 100);
        test("insertion", test3);
        test("reference_insertion", test3);
        test("shell", test3, 100);
        test("reference_shell", test3, 100);
        test("merge", test3, 100);
        test("reference_merge", test3, 100);
        test("mergeBU", test3, 100);
        test("reference_mergeBU", test3, 100);
        test("timSort", test3, 100);
        test("wikiSortWithBuffer", test3, 100);
        test("grailSortWithoutBuffer", test3, 100);
        test("grailSortWithBuffer", test3, 100);
        test("grailSortWithDynBuffer", test3, 100);

        // test4: Sort Strings
        System.out.println("\nTest4 (sort strings):");
        randomStringTest("selection", 20, LENGTH, 100);
        randomStringTest("reference_selection", 20, LENGTH, 100);
        randomStringTest("insertion", 20, LENGTH, 100);
        randomStringTest("reference_insertion", 20, LENGTH, 100);
        randomStringTest("shell", 20, LENGTH, 100);
        randomStringTest("reference_shell", 20, LENGTH, 100);
        randomStringTest("merge", 20, LENGTH, 100);
        randomStringTest("reference_merge", 20, LENGTH, 100);
        randomStringTest("mergeBU", 20, LENGTH, 100);
        randomStringTest("reference_mergeBU", 20, LENGTH, 100);
        randomStringTest("timSort", 20, LENGTH, 100);
        randomStringTest("wikiSortWithBuffer", 20, LENGTH, 100);
        randomStringTest("grailSortWithoutBuffer", 20, LENGTH, 100);
        randomStringTest("grailSortWithBuffer", 20, LENGTH, 100);
        randomStringTest("grailSortWithDynBuffer", 20, LENGTH, 100);

        // test5: Sort lots of Strings
        System.out.println("\nTest5 (sort lots of Strings):");
        randomStringTest("shell", 20, BIG_LENGTH, 100);
        randomStringTest("reference_shell", 20, BIG_LENGTH, 100);
        randomStringTest("merge", 20, BIG_LENGTH, 100);
        randomStringTest("reference_merge", 20, BIG_LENGTH, 100);
        randomStringTest("mergeBU", 20, BIG_LENGTH, 100);
        randomStringTest("reference_mergeBU", 20, BIG_LENGTH, 100);
        randomStringTest("timSort", 20, BIG_LENGTH, 100);
        randomStringTest("wikiSortWithBuffer", 20, BIG_LENGTH, 100);
        randomStringTest("grailSortWithoutBuffer", 20, BIG_LENGTH, 100);
        randomStringTest("grailSortWithBuffer", 20, BIG_LENGTH, 100);
        randomStringTest("grailSortWithDynBuffer", 20, BIG_LENGTH, 100);
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
            case "reference_selection":
                edu.princeton.cs.algs4.Selection.sort(testCopy);
                break;
            case "reference_insertion":
                edu.princeton.cs.algs4.Insertion.sort(testCopy);
                break;
            case "reference_shell":
                edu.princeton.cs.algs4.Shell.sort(testCopy);
                break;
            case "reference_merge":
                edu.princeton.cs.algs4.Merge.sort(testCopy);
                break;
            case "reference_mergeBU":
                edu.princeton.cs.algs4.MergeBU.sort(testCopy);
                break;
            case "timSort":
                Arrays.sort(testCopy);
                break;
            case "wikiSortWithBuffer":
                Wiki.sort(testCopy);
                break;
            case "grailSortWithoutBuffer":
                Grail.sortWithoutBuffer(testCopy);
                break;
            case "grailSortWithBuffer":
                Grail.sortWithBuffer(testCopy);
                break;
            case "grailSortWithDynBuffer":
                Grail.sortWithDynBuffer(testCopy);
                break;
            default:
                break;
        }
        Double time = timer.elapsedTime();
        if(! Util.isSorted(testCopy)) {
            System.out.println("No! No! No! Not Sorted!");
            throw new InternalError("UnSorted");
        }
        if (ifPrint) {
            System.out.printf("%20s sort: ", algorithm);
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
        double upper;
        double lower;
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
        System.out.printf("%25s sort: ", algorithm);
        System.out.printf("mean elapsed time = %.5f,", mean);
        System.out.printf(" 95%% CI = [%.5f, %.5f]\n", lower, upper);
    }

    private static void randomDoubleTest(String algorithm, int arrayLength, int times) {
        Double[] test = new Double[arrayLength];
        Random random = new Random();
        TDistribution tDistribution = new TDistribution(times - 1);
        Stack<Double> time = new Stack<>();
        double mean = 0.0;
        double variance = 0.0;
        double upper;
        double lower;
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
        System.out.printf("%25s sort: ", algorithm);
        System.out.printf("mean elapsed time = %.5f,", mean);
        System.out.printf(" 95%% CI = [%.5f, %.5f]\n", lower, upper);
    }

    private static void randomStringTest(String algorithm, int stringLength, int arrayLength, int times) {
        String[] test = new String[arrayLength];
        Random random = new Random();
        TDistribution tDistribution = new TDistribution(times - 1);
        Stack<Double> time = new Stack<>();
        double mean = 0.0;
        double variance = 0.0;
        double upper;
        double lower;
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < arrayLength; j++) {
                char[] chars = new char[stringLength];
                for (int k = 0; k < stringLength; k++) {
                    chars[k] = (char) (random.nextInt('z' - 'a') + 'a');
                }
                test[j] = String.valueOf(chars);
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
        System.out.printf("%25s sort: ", algorithm);
        System.out.printf("mean elapsed time = %.5f,", mean);
        System.out.printf(" 95%% CI = [%.5f, %.5f]\n", lower, upper);
    }
}
