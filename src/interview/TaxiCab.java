package interview;

import java.util.Arrays;
import java.util.Scanner;
import princeton.algo.sort.Quick;
import princeton.algo.binaryHeap.PriorityQueue;
import princeton.algo.queue.ArrayQueue;

/**
 * Finds ramanujan's taxiCab numbers in O(n^2 log(n)) time and O(n) space with a
 * priorityQueue.
 */
public class TaxiCab {

    private static class TwoInts implements Comparable<TwoInts> {
        final int a;
        final int b;
        final int quadSum;
        TwoInts(int a, int b) {
            this.a = a;
            this.b = b;
            quadSum = a * a * a + b * b * b;
        }

        @Override
        public int compareTo(TwoInts o) {
            return quadSum - o.quadSum;
        }
    }

    public static class TaxiCabNumber {
        final int a;
        final int b;
        final int c;
        final int d;
        final int quadSum;

        TaxiCabNumber(int a, int b, int c, int d, int quadSum) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.quadSum = a * a * a + b * b * b;
        }
    }

    /**
     * taxiCab finds all taxiCab numbers with a, b, c, d smaller or equal to n,
     * using O(n^2) space and O(n^2 log n) time.
     *
     * @param n tha n mentioned above.
     * @return the taxiCabs
     */
    public static Object[] taxiCab(int n) {
        if (n < 12) System.exit(0);
        ArrayQueue<TaxiCabNumber> result = new ArrayQueue<>();
        TwoInts[] twoIntsArray = new TwoInts[n * (n - 1) / 2];
        int count = 0;
        for (int a = 1; a < n; a++) {
            for (int b = a + 1; b <= n; b++) {
                twoIntsArray[count++] = new TwoInts(a, b);
            }
        }
        Quick.sort(twoIntsArray);
        TwoInts mem = null;
        for (TwoInts t : twoIntsArray) {
            if (mem != null && mem.quadSum == t.quadSum) {
                result.enqueue(new TaxiCabNumber(t.a, t.b, mem.a, mem.b, t.quadSum));
            }
            mem = t;
        }
        return result.toArray();
    }

    /**
     * taxiCab finds all taxiCab numbers with a, b, c, d smaller or equal to n,
     * using O(n) space and O(n^2 log n) time.
     *
     * @param n tha n mentioned above.
     * @return the taxiCabs
     */
    public static Object[] taxiCabCompact(int n) {
        if (n < 12) System.exit(0);
        ArrayQueue<TaxiCabNumber> result = new ArrayQueue<>();
        PriorityQueue<TwoInts> intHeap = new PriorityQueue<>(n - 1, (o1, o2) -> o2.quadSum - o1.quadSum);
        for (int b = 2; b <= n; b++) {
            intHeap.add(new TwoInts(1, b));
        }
        TwoInts mem = null;
        while(!intHeap.isEmpty()) {
            TwoInts newInt = intHeap.pop();
            if (mem != null && newInt.compareTo(mem) == 0) {
                result.enqueue(new TaxiCabNumber(newInt.a, newInt.b, mem.a, mem.b, newInt.quadSum));
            }
            if (newInt.b > newInt.a + 1) {
                intHeap.add(new TwoInts(newInt.a + 1, newInt.b));
            }
            mem = newInt;
        }
        return result.toArray();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.close();
        final String format = "%4d ^ 3  + %4d ^ 3   = %4d ^ 3  + %4d ^ 3     = %10d\n";
        Object[] result;

        result = taxiCab(n);
        System.out.printf("taxiCab >>> %d\n", result.length);
        Arrays.stream(result).map(t -> (TaxiCabNumber) t).forEach(t -> System.out.printf(format, t.a, t.b, t.c, t.d, t.quadSum));

        result = taxiCabCompact(n);
        System.out.printf("taxiCabCompact >>> %d\n", result.length);
        Arrays.stream(result).map(t -> (TaxiCabNumber) t).forEach(t -> System.out.printf(format, t.a, t.b, t.c, t.d, t.quadSum));
    }
}
