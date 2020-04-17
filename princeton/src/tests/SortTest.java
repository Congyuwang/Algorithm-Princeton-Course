package tests;

import princeton.algo.sort.*;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

class SortTest {
    public static void main(String[] args) {
        int LENGTH = 20000;
        Random random = new Random();
        Double[] test1 = new Double[LENGTH];
        Double[] test2 = new Double[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            test1[i] = random.nextDouble();
            test2[i] = random.nextDouble() + i * 0.2;
        }

        // test1: Selection sort
        System.out.println("Test1 (random order):");
        test("selection", test1);
        test("insertion", test1);
        test("shell", test1);

        // test2: Insertion sort (partially sorted)
        System.out.println("Test2 (partially sorted):");
        test("selection", test2);
        test("insertion", test2);
        test("shell", test2);
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
            default:
                break;
        }
        assert(Util.isSorted(testCopy));
        System.out.printf("elapsed time = %.4f", timer.elapsedTime());
        System.out.println();
    }
}
