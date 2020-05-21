package tests;

import princeton.algo.queue.LinkedQueue;
import princeton.algo.queue.Queue;
import princeton.algo.sort.*;
import princeton.algo.sort.hybrid.*;
import princeton.algo.binaryHeap.Heap;
import princeton.algo.stack.LinkedStack;
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
        double[] test1_1 = new double[BIG_LENGTH];
        double[] test1_2 = new double[BIG_LENGTH];
        Double[] test2 = new Double[LENGTH];
        Double[] test3 = new Double[LENGTH];
        Double[] equalKeys = new Double[LENGTH];
        for (int i = 0; i < BIG_LENGTH; i++) {
            test1_1[i] = random.nextDouble();
        }
        for (int i = 0; i < BIG_LENGTH; i++) {
            test1_2[i] = random.nextDouble() - i * 0.2;
        }
        for (int i = 0; i < LENGTH; i++) {
            test2[i] = random.nextDouble() + i * 0.2;
        }
        for (int i = 0; i < LENGTH; i++) {
            test3[i] = random.nextDouble() - i * 0.2;
        }
        for (int i = 0; i < LENGTH / 1000; i++) {
            double d = random.nextDouble();
            for (int j = 0; j < 1000; j++) {
                equalKeys[i * 1000 + j] = d;
            }
        }
        Shuffle.shuffle(equalKeys);
        Queue<String> queueTest = new LinkedQueue<>();
        LinkedStack<String> stackTest = new LinkedStack<>();
        for (int j = 0; j < BIG_LENGTH; j++) {
            char[] chars = new char[20];
            for (int k = 0; k < 20; k++) {
                chars[k] = (char) (random.nextInt('z' - 'a') + 'a');
            }
            queueTest.enqueue(String.valueOf(chars));
            stackTest.push(String.valueOf(chars));
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
        randomDoubleTest("quickSort", LENGTH, 100);
        randomDoubleTest("princetonQuickSort", LENGTH, 100);
        randomDoubleTest("princetonTPQuickSort", LENGTH, 100);
        randomDoubleTest("heapSort", LENGTH, 100);

        System.out.println("\nTest1.1 (random primitive BIG_LENGTH):");
        test("shell", test1_1, 100);
        test("merge", test1_1, 100);
        test("mergeBU", test1_1, 100);
        test("quickSort", test1_1, 100);
        test("reference_quickSort", test1_1, 100);
        System.out.println("The reference Quick Sort is very fast as it requires no shuffling!");

        System.out.println("\nTest1.2 (inverted primitive BIG_LENGTH):");
        test("shell", test1_2, 100);
        test("merge", test1_2, 100);
        test("mergeBU", test1_2, 100);
        test("quickSort", test1_2, 100);
        test("reference_quickSort", test1_2, 100);
        System.out.println("The reference Quick Sort is very fast as it requires no shuffling!");

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
        test("quickSort", test2, 100);
        test("princetonQuickSort", test2, 100);
        test("princetonTPQuickSort", test2, 100);
        test("heapSort", test2, 100);

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
        test("quickSort", test3, 100);
        test("princetonQuickSort", test3, 100);
        test("princetonTPQuickSort", test3, 100);
        test("heapSort", test3, 100);

        // test4: equal Keys
        System.out.println("\nTest4 (equal keys):");
        test("selection", equalKeys, 100);
        test("reference_selection", equalKeys, 100);
        test("insertion", equalKeys);
        test("reference_insertion", equalKeys);
        test("shell", equalKeys, 100);
        test("reference_shell", equalKeys, 100);
        test("merge", equalKeys, 100);
        test("reference_merge", equalKeys, 100);
        test("mergeBU", equalKeys, 100);
        test("reference_mergeBU", equalKeys, 100);
        test("timSort", equalKeys, 100);
        test("wikiSortWithBuffer", equalKeys, 100);
        test("grailSortWithoutBuffer", equalKeys, 100);
        test("grailSortWithBuffer", equalKeys, 100);
        test("grailSortWithDynBuffer", equalKeys, 100);
        test("quickSort", equalKeys, 100);
        test("princetonQuickSort", equalKeys, 100);
        test("princetonTPQuickSort", equalKeys, 100);
        test("heapSort", test3, 100);

        // test4: Sort Strings
        System.out.println("\nTest5 (sort strings):");
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
        randomStringTest("quickSort", 20, LENGTH, 100);
        randomStringTest("princetonQuickSort", 20, LENGTH, 100);
        randomStringTest("princetonTPQuickSort", 20, LENGTH, 100);
        randomStringTest("heapSort", 20, LENGTH, 100);

        // test5: Sort lots of Strings
        System.out.println("\nTest6 (sort lots of Strings):");
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
        randomStringTest("quickSort", 20, BIG_LENGTH, 100);
        randomStringTest("princetonQuickSort", 20, BIG_LENGTH, 100);
        randomStringTest("princetonTPQuickSort", 20, BIG_LENGTH, 100);
        randomStringTest("heapSort", 20, BIG_LENGTH, 100);

        // merge sort Queue / Stack
        System.out.println("\nTest (merge sort Queue):");
        test("merge", queueTest, 100);

        System.out.println("\nTest (merge sort Stack):");
        test("merge", stackTest, 100);

        System.out.println("\nTest Stability:");
        testStable("selection");
        testStable("reference_selection");
        testStable("insertion");
        testStable("reference_insertion");
        testStable("shell");
        testStable("reference_shell");
        testStable("merge");
        testStable("reference_merge");
        testStable("mergeBU");
        testStable("reference_mergeBU");
        testStable("timSort");
        testStable("wikiSortWithBuffer");
        testStable("grailSortWithoutBuffer");
        testStable("grailSortWithBuffer");
        testStable("grailSortWithDynBuffer");
        testStable("quickSort");
        testStable("princetonQuickSort");
        testStable("princetonTPQuickSort");
        testStable("heapSort");
    }

    private static <T extends Comparable<? super T>> Double test(String algorithm, T[] test) {
        return test(algorithm, test, true, true);
    }

    private static <T extends Comparable<? super T>> Double test(String algorithm, princeton.algo.queue.Queue<T> test, boolean ifPrint) {
        Stopwatch timer = new Stopwatch();
        Merge.sort(test);
        Double time = timer.elapsedTime();
        T mem = null;
        for (T t : test) {
            if (mem != null && Util.less(t, mem)) {
                System.out.println("No! No! No! Not Sorted!");
                throw new InternalError("UnSorted");
            }
            mem = t;
        }
        if (ifPrint) {
            System.out.printf("%25s sort: ", algorithm);
            System.out.printf("     elapsed time = %.5f", time);
        }
        return time;
    }

    private static Double test(String algorithm, double[] testCopy, boolean ifPrint) {
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
            case "quickSort":
                Quick.sort(testCopy);
                break;
            case "reference_quickSort":
                Arrays.sort(testCopy);
                break;
            default:
                break;
        }
        Double time = timer.elapsedTime();
        if (!Util.isSorted(testCopy)) {
            System.out.println("No! No! No! Not Sorted!");
            throw new InternalError("UnSorted");
        }
        if (ifPrint) {
            System.out.printf("%25s sort: ", algorithm);
            System.out.printf("     elapsed time = %.5f", time);
        }
        return time;
    }

    private static <T extends Comparable<? super T>> Double test(String algorithm, princeton.algo.stack.Stack<T> test, boolean ifPrint) {
        Stopwatch timer = new Stopwatch();
        Merge.sort(test);
        Double time = timer.elapsedTime();
        T mem = null;
        for (T t : test) {
            if (mem != null && Util.less(t, mem)) {
                System.out.println("No! No! No! Not Sorted!");
                throw new InternalError("UnSorted");
            }
            mem = t;
        }
        if (ifPrint) {
            System.out.printf("%25s sort: ", algorithm);
            System.out.printf("     elapsed time = %.5f", time);
        }
        return time;
    }

    private static <T extends Comparable<? super T>> Double test(String algorithm, T[] test, boolean ifPrint, boolean useCopy) {
        T[] testCopy;
        if (useCopy) {
            testCopy = test.clone();
        } else {
            testCopy = test;
        }
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
            case "reference_quickSort":
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
            case "quickSort":
                Quick.sort(testCopy);
                break;
            case "princetonQuickSort":
                edu.princeton.cs.algs4.Quick.sort(testCopy);
                break;
            case "princetonTPQuickSort":
                edu.princeton.cs.algs4.Quick3way.sort(testCopy);
                break;
            case "heapSort":
                Heap.sort(testCopy);
                break;
            default:
                break;
        }
        Double time = timer.elapsedTime();
        if(!Util.isSorted(testCopy)) {
            System.out.println("No! No! No! Not Sorted!");
            throw new InternalError("UnSorted");
        }
        if (ifPrint) {
            System.out.printf("%25s sort: ", algorithm);
            System.out.printf("     elapsed time = %.5f", time);
            System.out.printf(", IsSorted: %b\n", Util.isSorted(testCopy));
        }
        return time;
    }

    private static <T extends Comparable<? super T>> void test(String algorithm, Queue<T> test, int times) {
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

    private static <T extends Comparable<? super T>> void test(String algorithm, double[] test, int times) {
        TDistribution tDistribution = new TDistribution(times - 1);
        Stack<Double> time = new Stack<>();
        double mean = 0.0;
        double variance = 0.0;
        double upper;
        double lower;
        for (int i = 0; i < times; i++) {
            double[] testCopy = test.clone();
            Double t = test(algorithm, testCopy, false);
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

    private static <T extends Comparable<? super T>> void test(String algorithm, princeton.algo.stack.Stack<T> test, int times) {
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

    private static <T extends Comparable<? super T>> void test(String algorithm, T[] test, int times) {
        TDistribution tDistribution = new TDistribution(times - 1);
        Stack<Double> time = new Stack<>();
        double mean = 0.0;
        double variance = 0.0;
        double upper;
        double lower;
        for (int i = 0; i < times; i++) {
            Double t = test(algorithm, test, false, true);
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
            Double t = test(algorithm, test, false, true);
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
            Double t = test(algorithm, test, false, true);
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

    private static class StableData implements Comparable<StableData> {
        public final int key;
        public final double value;
        StableData(int key, double value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public int compareTo(StableData o) {
            return key - o.key;
        }
    }

    private static boolean isStable(StableData[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (Util.less(a[i + 1].value, a[i].value) && a[i + 1].key == a[i].key) {
                return false;
            }
        }
        return true;
    }

    private static void testStable(String algorithm) {
        final int LENGTH = 10_000;
        Random random = new Random();
        StableData[] dat = new StableData[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            dat[i] = new StableData(random.nextInt(LENGTH / 100), i);
        }
        test(algorithm, dat, false, false);
        System.out.printf("%25s sort stable?: %b\n", algorithm, isStable(dat));
    }
}
