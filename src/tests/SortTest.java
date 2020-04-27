package tests;

import princeton.algo.sort.*;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

class SortTest {
    public static void main(String[] args) {
        int LENGTH = 20000;
        int BIG_LENGTH = 1_000_000;
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
        test("selection", test1);
        test("insertion", test1);
        test("shell", test1);
        test("merge", test1);
        test("mergeBU", test1);

        // test2: Insertion sort (partially sorted)
        System.out.println("Test2 (partially sorted):");
        test("selection", test2);
        test("insertion", test2);
        test("shell", test2);
        test("merge", test2);
        test("mergeBU", test2);

        // test3: Sort Strings
        System.out.println("Test3 (sort strings):");
        test("selection", test3);
        test("insertion", test3);
        test("shell", test3);
        test("merge", test3);
        test("mergeBU", test3);

        // test3: Sort lots of Strings
        System.out.println("Test4 (sort lots of strings):");
        test("shell", test4);
        test("merge", test4);
        test("mergeBU", test4);
    }

    private static <T extends Comparable<? super T>> void test(String algorithm, T[] test) {
        T[] testCopy = test.clone();
        System.out.printf("%15s sort: ", algorithm);
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
        System.out.printf("elapsed time = %.4f", timer.elapsedTime());
        System.out.printf(", IsSorted: %b\n", Util.isSorted(testCopy));
    }
}
