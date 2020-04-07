import princeton.algorithm.queue.LinkedQueueOfStrings;
import princeton.algorithm.queue.ArrayQueueOfStrings;
import java.util.Scanner;

public class QueueTest {
    public static void main(String[] args) {
        LinkedQueueOfStrings linkedStack  = new LinkedQueueOfStrings();
        ArrayQueueOfStrings arrayStack = new ArrayQueueOfStrings();
        Scanner scanner = new Scanner(System.in);
        // End input with EOF!
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (item.equals("-")) {
                try {
                    System.out.printf("arrayStack: %s\n", arrayStack.dequeue());
                    System.out.printf("linkedStack: %s\n", linkedStack.dequeue());
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
            } else {
                arrayStack.enqueue(item);
                linkedStack.enqueue(item);
            }
        }
    }
}
