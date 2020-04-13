import princeton.algorithm.queue.LinkedQueueOfStrings;
import princeton.algorithm.queue.ArrayQueueOfStrings;
import princeton.algorithm.queue.LinkedQueue;
import princeton.algorithm.queue.ArrayQueue;
import java.util.Scanner;

public class QueueTest {
    public static void main(String[] args) {
        LinkedQueueOfStrings linkedStackOfString  = new LinkedQueueOfStrings();
        ArrayQueueOfStrings arrayStackOfString = new ArrayQueueOfStrings();
        LinkedQueue<Integer> linkedStack  = new LinkedQueue<>();
        ArrayQueue<Integer> arrayStack = new ArrayQueue<>();
        Scanner scanner = new Scanner(System.in);
        // End input with EOF!
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (item.equals("-")) {
                try {
                    System.out.printf("arrayStack: %s\n", arrayStackOfString.dequeue());
                    System.out.printf("linkedStack: %s\n", linkedStackOfString.dequeue());
                    System.out.printf("arrayStack: %d\n", arrayStack.dequeue());
                    System.out.printf("linkedStack: %d\n", linkedStack.dequeue());
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
            } else {
                arrayStackOfString.enqueue(item);
                linkedStackOfString.enqueue(item);
                try {
                    int intItem = Integer.parseInt(item);
                    arrayStack.enqueue(intItem);
                    linkedStack.enqueue(intItem);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        scanner.close();
    }
}
