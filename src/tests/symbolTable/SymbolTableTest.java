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
                SymbolTable<String, Integer> table = TableChooser.symbolTable(tableType);
                switch (type) {
                    case "read" -> test = countWords("data/shorterShakespeare.txt", tableType, 4, table);
                    case "readAll" -> test = countWords("data/shakespeare.txt", tableType, 4, table);
                    case "input" -> test = table;
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
                    case "showK" -> {
                        int K = scanner.nextInt();
                        int count = 0;
                        for (String word : test.keys()) {
                            if (count > K) break;
                            System.out.println(word + " " + test.get(word));
                            count++;
                        }
                    }
                    case "size" -> System.out.println(test.size());
                    case "topK" -> printTopK(test, scanner.nextInt());
                    case "bottomK" -> printBottomK(test, scanner.nextInt());
                    case "filterLengthK" -> filterLength(scanner.nextInt(), test);
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
