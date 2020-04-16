package tests;

import princeton.algo.sort.Insertion;
import princeton.algo.sort.Selection;
import princeton.algo.sort.Util;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

class SortTest {
    public static void main(String[] args) {
        int LENGTH = 10000;
        Double[] test1 = new Double[LENGTH];
        Double[] test2 = new Double[LENGTH];
        Double[] test3 = new Double[LENGTH];
        Random random = new Random();
        for (int i = 0; i < LENGTH; i++) {
            test1[i] = random.nextDouble();
        }
        System.arraycopy(test1, 0, test2, 0, LENGTH);
        System.arraycopy(test1, 0, test3, 0, LENGTH);

        // test1: Selection sort
        System.out.println("Selection sort:");
        Stopwatch timer1 = new Stopwatch();
        Insertion.sort(test1);
        System.out.println("Is sorted?: " + Util.isSorted(test1));
        System.out.println("test1: elapsed time = " + timer1.elapsedTime());

        // test2: Insertion sort
        System.out.println("Insertion sort:");
        Stopwatch timer2 = new Stopwatch();
        Selection.sort(test2);
        System.out.println("Is sorted?: " + Util.isSorted(test2));
        System.out.println("test2: elapsed time = " + timer2.elapsedTime());
    }
}
