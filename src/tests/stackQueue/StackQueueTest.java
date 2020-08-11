package tests.stackQueue;

import princeton.algo.queue.*;
import princeton.algo.stack.*;
import princeton.algo.binaryHeap.*;
import java.util.Scanner;

/**
 * The {@code StackQueueTest} class tests all implementations of {@code Stack} and
 * {@code Queue}.
 * <p>
 *     Commands:
 *     <ul>
 *         <li>quit: exit the test</li>
 *         <li>-: dequeue or pop</li>
 *         <li>>>: print using forEach loop</li>
 *         <li>-s: shuffle</li>
 *         <li>others: enqueue the input string</li>
 *     </ul>
 * </p>
 */
class StackQueueTest {
    public static void main(String[] args) {
        Queue<String> linkedQueue = new LinkedQueue<>();
        Queue<String> arrayQueue = new ArrayQueue<>();
        Queue<String> twoStackQueue = new TwoStackQueue<>();
        Deque<String> deque = new Deque<>();
        Queue<String> randomizedQueue = new RandomizedQueue<>();
        Stack<String> linkedStack = new LinkedStack<>();
        Stack<String> arrayStack = new ArrayStack<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.nextLine();
            switch (item) {
                case "quit" -> System.exit(0);
                case "-" -> {
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
                        System.out.printf("linkedStack: %s\n", linkedStack.pop());
                    } catch (Exception e) {
                        System.out.printf("linkedStack Error: %s\n", e.getMessage());
                    }
                    try {
                        System.out.printf("arrayStack: %s\n", arrayStack.pop());
                    } catch (Exception e) {
                        System.out.printf("arrayStack Error: %s\n", e.getMessage());
                    }
                    try {
                        System.out.printf("priorityQueue: %s\n", priorityQueue.dequeue());
                    } catch (Exception e) {
                        System.out.printf("priorityQueue Error: %s\n", e.getMessage());
                    }
                }
                case ">>" -> {
                    System.out.println();
                    System.out.println("arrayQueue (FIFO):");
                    for (String i : arrayQueue) {
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                    System.out.println("twoStackQueue (FIFO):");
                    for (String i : twoStackQueue) {
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                    System.out.println("linkedQueue (FIFO):");
                    for (String i : linkedQueue) {
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                    System.out.println("deque (FIFO):");
                    for (String i : deque) {
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                    System.out.println("randomizedQueue:");
                    for (String i : randomizedQueue) {
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                    System.out.println("linkedStack (LIFO):");
                    for (String i : linkedStack) {
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                    System.out.println("arrayStack (LIFO):");
                    for (String i : arrayStack) {
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                    System.out.println("priorityQueue:");
                    for (String i : priorityQueue) {
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                }
                case "-s" -> {
                    linkedQueue.shuffle();
                    arrayQueue.shuffle();
                    twoStackQueue.shuffle();
                    deque.shuffle();
                    randomizedQueue.shuffle();
                    arrayStack.shuffle();
                    linkedStack.shuffle();
                    System.out.println("Shuffled!");
                }
                default -> {
                    linkedQueue.enqueue(item);
                    arrayQueue.enqueue(item);
                    twoStackQueue.enqueue(item);
                    deque.enqueue(item);
                    randomizedQueue.enqueue(item);
                    arrayStack.push(item);
                    linkedStack.push(item);
                    priorityQueue.enqueue(item);
                }
            }
        }
        scanner.close();
    }
}
