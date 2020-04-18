package interview;

import java.util.Arrays;
import java.util.Random;

/**
 * Solve DutchFlag problem using at most n swaps and opens the bucket for at most n times.
 * <p>
 * The general process is to look at one bucket at each time, if the color is
 * firstColor, swap it to the <i>next</i> position to the last of firstColor
 * buckets. If the color is lastColor, swap it to the <i>previous</i> position
 * of the lastColor. If the color is middleColor, leaves it there.
 * But there are some details of implementation which guarantees that each bucket is only
 * looked at once, by memorizing some important color.
 * </p>
 */
public class DutchFlag {

    /**
     * Sort the buckets and return the number of swaps and openings used.
     * @param c the buckets
     * @return the number of swaps used, and the number of times opening the
     *         buckets.
     */
    public static int[] sortPebbles(PebbleColor[] c) {
        assert(c.length > 2);

        /**
         * Count the times of swapping and the number of times opening the buckets.
         * swapCount++ each time swap() is called.
         * openBucketCount++ each time c[] is used.
         */
        int swapCount = 0;
        int openBucketCount = 0;

        /**
         * Initialize the three pointers. p1 points to the first bucket, p points to the
         * current bucket, and p2 points to the last bucket.
         */
        int p1 = 0;
        int p = 1;
        int p2 = c.length - 1;

        /**
         * Initialize the three types of colors, and the current color.
         */
        final PebbleColor firstColor = c[p1];
        openBucketCount++;

        PebbleColor currentColor = c[p];
        openBucketCount++;

        PebbleColor lastColor = c[p2];
        openBucketCount++;

        PebbleColor middleColor = null;

        while (p < p2 - 1) {
            /**
             * If currentColor equals to firstColor, and if the current bucket c[p] is next
             * to the firstColor bucket c[p1], there is no need to swap; otherwise swap p
             * and p1 + 1, and increment p1. This maintains that the buckets on the left side
             * of c[p1] are all firstColor buckets. Moreover, the bucket next to p1 must be
             * middleColor if p > p1 + 1 since p is incremented without p1 being incremented
             * only if middleColor is encountered. Update the currentColor to
             * middleColor if swap happened.
             */
            if (currentColor.equals(firstColor)) {
                if (p == p1 + 1) {
                    p1++;
                    currentColor = c[++p];
                    openBucketCount++;
                } else {
                    swap(c, p, ++p1);
                    if (middleColor == null) {
                        /**
                         * This should not be reached since p > p1 + 1 only if middleColor
                         * is initialized.
                         */
                        throw new InternalError("null middle PebbleColor");
                    }
                    currentColor = middleColor;
                    swapCount++;
                }
            } else if (firstColor.equals(lastColor)) {
                /**
                 * This happens if the currentColor != firstColor == lastColor.
                 * if it happens, it must be that p = p1 + 1.
                 */
                swap(c, p, p2);
                swapCount++;
                PebbleColor temp = lastColor;
                lastColor = currentColor;
                currentColor = temp;
            } else if (currentColor.equals(lastColor)) {
                /**
                 * if currentColor == lastColor, swap it to p2 - 1, and decrement p2. After
                 * swapping, look at the current bucket and update currentColor.
                 */
                swap(c, p, --p2);
                swapCount++;
                currentColor = c[p];
                openBucketCount++;
            } else {
                /**
                 * If currentColor is neither first or last, record it as middleColor, and
                 * increment p. This is where middleColor is initialized.
                 */
                middleColor = currentColor;
                currentColor = c[++p];
                openBucketCount++;
            }
        }
        /**
         * When p = p2 - 1, it is still possible that c[p] == firstColor.
         */
        if (currentColor.equals(firstColor)) {
            if (p > p1 + 1) {
                swap(c, p, ++p1);
                swapCount++;
            }
        }

        // return the counting statistics.
        return new int[] {swapCount, openBucketCount};
    }

    public static boolean isSorted(PebbleColor[] a) {
        final PebbleColor color1 = a[0];
        PebbleColor color2 = null;
        PebbleColor color3 = null;
        int i = 1;
        for (; i < a.length - 1; i++) {
            if (!color1.equals(a[i])) {
                color2 = a[i];
                break;
            }
        }
        for(; i < a.length; i++) {
            if (!color2.equals(a[i])) {
                if (color1.equals(a[i])) {
                    return false;
                }
                color3 = a[i];
            }
        }
        for (; i < a.length - 1; i++) {
            if (!color3.equals(a[i])) {
                return false;
            }
        }
        return true;
    }

    private static <T> void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void test(int testLength, boolean printArray) {
        Random random = new Random();
        PebbleColor[] pebbles = new PebbleColor[testLength];
        for (int i = 0; i < testLength; i++) {
            int j = random.nextInt(3);
            switch (j) {
                case 0:
                    pebbles[i] = PebbleColor.R;
                    break;
                case 1:
                    pebbles[i] = PebbleColor.W;
                    break;
                case 2:
                    pebbles[i] = PebbleColor.B;
                    break;
                default:
                    break;
            }
        }
        if (printArray) {
            System.out.printf("Before sorting: %s\n", Arrays.toString(pebbles));
            System.out.printf(" Is sorted?              : %b\n", isSorted(pebbles));
        }
        int[] statistics = sortPebbles(pebbles);
        if (printArray) {
            System.out.printf(" After sorting: %s\n", Arrays.toString(pebbles));
        }
        System.out.printf(" Is sorted?              : %b\n", isSorted(pebbles));
        System.out.printf(" Number of swap          : %d\n", statistics[0]);
        System.out.printf(" Number of bucket opening: %d\n", statistics[1]);
    }

    public static void main(String[] args) {
        test(20, true);
        test(20, true);
        for (int i = 1; i < 20; i++) {
            test(1000, false);
        }
    }
}
