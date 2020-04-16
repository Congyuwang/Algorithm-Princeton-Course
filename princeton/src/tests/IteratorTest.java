package tests;

import princeton.algo.queue.Queue;
import princeton.algo.queue.RandomizedQueue;
import princeton.algo.stack.Stack;
import princeton.algo.queue.ArrayQueue;
import princeton.algo.queue.LinkedQueue;
import princeton.algo.queue.TwoStackQueue;
import princeton.algo.stack.ArrayStack;
import princeton.algo.stack.LinkedStack;
import java.util.Scanner;

class IteratorTest {
    public static void main(String[] args) {
        Queue<Integer> linkedQueue  = new LinkedQueue<>();
        Queue<Integer> arrayQueue = new ArrayQueue<>();
        Queue<Integer> twoStackQueue = new TwoStackQueue<>();
        Queue<Integer> randomizedQueue = new RandomizedQueue<>();
        Stack<Integer> linkedStack = new LinkedStack<>();
        Stack<Integer> arrayStack = new ArrayStack<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (item.equals("-")) {
                try {
                    System.out.printf("linkedQueue: %s\n", linkedQueue.dequeue());
                    System.out.printf("arrayQueue: %s\n", arrayQueue.dequeue());
                    System.out.printf("twoStackQueue: %s\n", twoStackQueue.dequeue());
                    System.out.printf("randomizedQueue: %s\n", randomizedQueue.dequeue());
                    System.out.printf("linkedStack: %d\n", linkedStack.pop());
                    System.out.printf("arrayStack: %d\n", arrayStack.pop());
                } catch (Exception e) {
                    System.out.printf("Error: %s\n", e.getMessage());
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
