package princeton.algorithm.unionfind;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class solves three sum problem that involves unique elements.
 */
public class ThreeSum {

    private int count;
    private List<List<Integer>> solution;

    /**
     * Constructor: the constructor includes the main processing unit.
     *
     * @param ints The input array.
     */
    ThreeSum(int[] ints) {
        int[] inputArray = new int[ints.length];
        solution = new ArrayList<>();
        System.arraycopy(ints, 0, inputArray, 0, ints.length);
        quickSort(inputArray, 0, inputArray.length - 1);
        // process record current process of the outer loop
        int process = 0;
        int memory0 = 0;
        for (int value : inputArray) {
            // prevent duplicates: if value is the same, skip a loop
            if (process > 1 && value == memory0) {
                process++;
                continue;
            }
            memory0 = value;
            int key = -value;
            // lo starts from one value larger than the current process to prevent duplicates
            int lo = ++process;
            int hi = inputArray.length - 1;
            // This while loop search for key in a zig-zag fashion in linear time
            while (lo < hi) {
                int currentValue = inputArray[lo] + inputArray[hi];
                if (currentValue < key) {
                    lo++;
                } else if (currentValue > key) {
                    hi--;
                } else {
                    count++;
                    collectSolution(value, inputArray[lo], inputArray[hi]);
                    if (lo < inputArray.length - 1){
                        lo++;
                        // skip over this lo if it is the same as the previous one
                        while (lo < hi && inputArray[lo] == inputArray[lo - 1]) {
                            lo++;
                        }
                    } else {
                        hi--;
                        // skip over this hi if it is the same as the previous one
                        while (lo < hi && inputArray[hi] == inputArray[hi + 1]) {
                            hi--;
                        }
                    }
                }
            }
        }
    }

    private static void quickSort(int[] numbers, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int key = numbers[left];

        while (i < j) {
            while (i < j && key <= numbers[j]) {
                j--;
            }
            while (i < j && key >= numbers[i]) {
                i++;
            }
            int temp = numbers[j];
            numbers[j] = numbers[i];
            numbers[i] = temp;
        }
        int temp = numbers[left];
        numbers[left] = numbers[j];
        numbers[j] = temp;
        quickSort(numbers, left, i - 1);
        quickSort(numbers, i + 1, right);
    }

    public int getCount() {
        return count;
    }

    public void printSolutionSet() {
        for (int i = 0; i < count; i++) {
            System.out.printf("%d + %d + %d = 0\n", solution.get(i).get(0), solution.get(i).get(1), solution.get(i).get(2));
        }
    }

    public void collectSolution(int a, int b, int c) {
        List<Integer> temp = new ArrayList<>();
        temp.add(a);
        temp.add(b);
        temp.add(c);
        solution.add(temp);
    }

    public List<List<Integer>> getSolutionSet() {
        return solution;
    }

    // testing server
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int inputLength = scanner.nextInt();
        int[] inputs = new int[inputLength];
        for (int j = 0; j < inputLength; j++) {
            inputs[j] = scanner.nextInt();
        }
        ThreeSum threeSum = new ThreeSum(inputs);
        threeSum.printSolutionSet();
        System.out.println("Total Count: " + threeSum.getCount());
        System.out.println(Arrays.deepToString(threeSum.getSolutionSet().toArray()));
    }
}
