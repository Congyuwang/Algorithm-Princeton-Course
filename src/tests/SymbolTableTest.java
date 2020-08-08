package tests;

import princeton.algo.symbolTable.SequentialSearchST;
import princeton.algo.symbolTable.SymbolTable;

public class SymbolTableTest {
    public static void main(String[] args) {
        SymbolTable<String, Integer> test = new SequentialSearchST<>();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        loop: while (true) {
            System.out.println("enter command:");
            String command = scanner.next();
            switch (command) {
                case "show":
                    for (String word : test.keys()) {
                        System.out.println(word + " " + test.get(word));
                    }
                    break;
                case "size":
                    System.out.println(test.size());
                    break;
                case "put":
                    System.out.println("enter key:");
                    String key = scanner.next();
                    System.out.println("enter value:");
                    Integer value = scanner.nextInt();
                    test.put(key, value);
                    break;
                case "delete":
                    System.out.println("enter key:");
                    String deleteKey = scanner.next();
                    test.delete(deleteKey);
                    break;
                case "quit":
                    break loop;
            }
        }
        scanner.close();
    }
}
