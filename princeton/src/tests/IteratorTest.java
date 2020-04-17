package tests;

import princeton.algo.queue.*;
import princeton.algo.stack.*;
import java.util.Scanner;

class IteratorTest {
    public static void main(String[] args) {
        Queue<Integer> linkedQueue = new LinkedQueue<>();
        Queue<Integer> arrayQueue = new ArrayQueue<>();
        Queue<Integer> twoStackQueue = new TwoStackQueue<>();
        Queue<Integer> deque = new Deque<>();
        Queue<Integer> randomizedQueue = new RandomizedQueue<>();
        Stack<Integer> linkedStack = new LinkedStack<>();
        Stack<Integer> arrayStack = new ArrayStack<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (item.equals("-")) {
                try {
                    System.out.printf("linkedQueue: %s\n", linkedQueue.dequeue());
                } catch (Exception e) {
                    System.out.printf("linkedQueue Error: %s\n", e.getMessage());
                }
                try {
                    System.out.printf("arrayQueue: %s\n", arrayQueue.dequeue());
                } catch (Exception e) {
                        System.out.printf("arrayQueue Error: %s\n", e.getMessage());
                }
                try {
                    System.out.printf("twoStackQueue: %s\n", twoStackQueue.dequeue());
                } catch (Exception e) {
                        System.out.printf("twoStackQueue Error: %s\n", e.getMessage());
                }
                try {
                    System.out.printf("deque: %s\n", deque.dequeue());
                } catch (Exception e) {
                        System.out.printf("deque Error: %s\n", e.getMessage());
                }
                try {
                    System.out.printf("randomizedQueue: %s\n", randomizedQueue.dequeue());
                } catch (Exception e) {
                        System.out.printf("randomizedQueue Error: %s\n", e.getMessage());
                }
                try {
                    System.out.printf("linkedStack: %d\n", linkedStack.pop());
                } catch (Exception e) {
                        System.out.printf("linkedStack Error: %s\n", e.getMessage());
                }
                try {
                    System.out.printf("arrayStack: %d\n", arrayStack.pop());
                } catch (Exception e) {
                        System.out.printf("arrayStack Error: %s\n", e.getMessage());
                }
            } else if (item.equals(">>")) {
                System.out.println("linkedQueue (FIFO):");
                for (int i : linkedQueue) {
                    System.out.print(i + " ");
                }
                System.out.println();
                System.out.println("arrayQueue (FIFO):");
                for (int i : arrayQueue) {
                    System.out.print(i + " ");
                }
                System.out.println();
                System.out.println("twoStackQueue (FIFO):");
                for (int i : twoStackQueue) {
                    System.out.print(i + " ");
                }
                System.out.println();
                System.out.println("deque (FIFO):");
                for (int i : deque) {
                    System.out.print(i + " ");
                }
                System.out.println();
                System.out.println("randomizedQueue:");
                for (int i : randomizedQueue) {
                    System.out.print(i + " ");
                }
                System.out.println();
                System.out.println("linkedStack (LIFO):");
                for (int i : linkedStack) {
                    System.out.print(i + " ");
                }
                System.out.println();
                System.out.println("arrayStack (LIFO):");
                for (int i : arrayStack) {
                    System.out.print(i + " ");
                }
                System.out.println();
            } else {
                try {
                    int intItem = Integer.parseInt(item);
                    linkedQueue.enqueue(intItem);
                    arrayQueue.enqueue(intItem);
                    twoStackQueue.enqueue(intItem);
                    deque.enqueue(intItem);
                    randomizedQueue.enqueue(intItem);
                    arrayStack.push(intItem);
                    linkedStack.push(intItem);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        scanner.close();
    }
}
