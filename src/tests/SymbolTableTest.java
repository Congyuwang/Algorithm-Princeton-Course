package tests;

import princeton.algo.symbolTable.SymbolTable;
import static tests.FrequencyCounter.*;

public class SymbolTableTest {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        SymbolTable<String, Integer> test = null;

        while (test == null) {
            try {
                System.out.println("choose test type: (read/input)");
                String type = scanner.next();
                System.out.println("enter table type:");
                String tableType = scanner.next();
                if (type.equals("read")) {
                    test = countWords("data/shorterShakespeare.txt", tableType, 5);
                } else if (type.equals("input")) {
                    test = tableChooser(tableType);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                    String key = scanner.next();
                    Integer value = scanner.nextInt();
                    test.put(key, value);
                    break;
                case "delete":
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
