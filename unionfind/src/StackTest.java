import princeton.algorithm.stack.ArrayStackOfStrings;
import princeton.algorithm.stack.LinkedStackOfStrings;
import java.util.Scanner;

public class StackTest {
    public static void main(String[] args) {
        ArrayStackOfStrings arrayStack = new ArrayStackOfStrings();
        LinkedStackOfStrings linkedStack = new LinkedStackOfStrings();
        Scanner scanner = new Scanner(System.in);
        // End input with EOF!
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (item.equals("-")) {
                System.out.printf("arrayStack: %s\n", arrayStack.pop());
                System.out.printf("linkedStack: %s\n", linkedStack.pop());
            } else {
                arrayStack.push(item);
                linkedStack.push(item);
            }
        }
    }
}
