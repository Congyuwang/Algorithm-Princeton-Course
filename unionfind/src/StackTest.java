import princeton.algorithm.stack.ArrayStackOfStrings;
import princeton.algorithm.stack.LinkedStackOfStrings;
import princeton.algorithm.stack.ArrayStack;
import princeton.algorithm.stack.LinkedStack;
import java.util.Scanner;

public class StackTest {
    public static void main(String[] args) {
        ArrayStackOfStrings arrayStackOfStrings = new ArrayStackOfStrings();
        LinkedStackOfStrings linkedStackOfStrings = new LinkedStackOfStrings();
        ArrayStack<Integer> arrayStack = new ArrayStack<>();
        LinkedStack<Integer> linkedStack = new LinkedStack<>();
        Scanner scanner = new Scanner(System.in);
        // End input with EOF!
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (item.equals("-")) {
                try {
                    System.out.printf("arrayStack: %s\n", arrayStackOfStrings.pop());
                    System.out.printf("linkedStack: %s\n", linkedStackOfStrings.pop());
                    System.out.printf("arrayStack: %d\n", arrayStack.pop());
                    System.out.printf("linkedStack: %d\n", linkedStack.pop());
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
                }
            } else {
                arrayStackOfStrings.push(item);
                linkedStackOfStrings.push(item);
                try {
                    int intItem = Integer.parseInt(item);
                    arrayStack.push(intItem);
                    linkedStack.push(intItem);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        scanner.close();
    }
}
