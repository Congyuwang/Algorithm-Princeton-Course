package tests;

import princeton.algo.sort.Insertion;
import princeton.algo.sort.Selection;
import princeton.algo.sort.Util;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

class SortTest {
    public static void main(String[] args) {
        int LENGTH = 20000;
        Double[] test1 = new Double[LENGTH];
        Double[] test2 = new Double[LENGTH];
        Double[] test1copy1 = new Double[LENGTH];
        Double[] test2copy1 = new Double[LENGTH];
        Random random = new Random();
        for (int i = 0; i < LENGTH; i++) {
            test1[i] = random.nextDouble();
            test2[i] = random.nextDouble();
            test2[i] += i * 0.2;
        }
        System.arraycopy(test1, 0, test1copy1, 0, LENGTH);
        System.arraycopy(test2, 0, test2copy1, 0, LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            test1[i] = random.nextDouble();
        }

        // test1: Selection sort
        System.out.println("Test1: Selection sort:");
        Stopwatch timer1 = new Stopwatch();
        Selection.sort(test1);
        System.out.printf("elapsed time = %4f\n", timer1.elapsedTime());
        System.out.println("Is sorted?: " + Util.isSorted(test1));

        // test1: Insertion sort
        System.out.println("Test1: Insertion sort:");
        Stopwatch timer2 = new Stopwatch();
        Insertion.sort(test1copy1);
        System.out.printf("elapsed time = %4f\n", timer2.elapsedTime());
        System.out.println("Is sorted?: " + Util.isSorted(test2));

        // test2: Insertion sort (partially sorted)
        System.out.println("Test2 (partially sorted): Insertion sort:");
        Stopwatch timer3 = new Stopwatch();
        Selection.sort(test2);
        System.out.printf("elapsed time = %4f\n", timer3.elapsedTime());
        System.out.println("Is sorted?: " + Util.isSorted(test2));

        // test2: Selection sort (partially sorted)
        System.out.println("Test2 (partially sorted): Insertion sort:");
        Stopwatch timer4 = new Stopwatch();
        Insertion.sort(test2copy1);
        System.out.printf("elapsed time = %4f\n", timer4.elapsedTime());
        System.out.println("Is sorted?: " + Util.isSorted(test2));
    }
}
