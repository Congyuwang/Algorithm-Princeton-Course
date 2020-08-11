package tests.symbolTable;

import princeton.algo.symbolTable.SymbolTable;
import static tests.symbolTable.FrequencyCounter.*;

/**
 * The class {@code SymbolTableTest} tests methods of a {@code SymbolTable}
 */
public class SymbolTableTest {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        SymbolTable<String, Integer> test = null;

        while (test == null) {
            try {
                System.out.println("choose test type: (read/readAll/input)");
                String type = scanner.next();
                System.out.println("enter table type:");
                String tableType = scanner.next();
                switch (type) {
                    case "read" -> test = countWords("data/shorterShakespeare.txt", tableType, 4);
                    case "readAll" -> test = countWords("data/shakespeare.txt", tableType, 4);
                    case "input" -> test = tableChooser(tableType);
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loop: while (true) {
            System.out.println("enter command:");
            String command = scanner.next();
            try {
                switch (command) {
                    case "show" -> {
                        for (String word : test.keys()) {
                            System.out.println(word + " " + test.get(word));
                        }
                    }
                    case "size" -> System.out.println(test.size());
                    case "put" -> {
                        String key = scanner.next();
                        Integer value = scanner.nextInt();
                        test.put(key, value);
                    }
                    case "delete" -> {
                        String deleteKey = scanner.next();
                        test.delete(deleteKey);
                    }
                    case "quit" -> {
                        break loop;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + command);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
