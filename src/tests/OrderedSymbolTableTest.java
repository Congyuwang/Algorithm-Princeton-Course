package tests;

import princeton.algo.symbolTable.OrderedSymbolTable;
import static tests.FrequencyCounter.*;

public class OrderedSymbolTableTest {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        OrderedSymbolTable<String, Integer> test = null;

        while (test == null) {
            try {
                System.out.println("choose test type: (read/readAll/input)");
                String type = scanner.next();
                System.out.println("enter table type:");
                String tableType = scanner.next();
                switch (type) {
                    case "read" -> test = countWordsOrdered("data/shorterShakespeare.txt", tableType, 4);
                    case "readAll" -> test = countWordsOrdered("data/shakespeare.txt", tableType, 4);
                    case "input" -> test = tableChooserOrdered(tableType);
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
                    case "show":
                        for (String word : test.keys()) {
                            System.out.println(word + " " + test.get(word));
                        }
                        break;
                    case "range":
                        String lower = scanner.next();
                        String upper = scanner.next();
                        for (String word : test.keys(lower, upper)) {
                            System.out.println(word + " " + test.get(word));
                        }
                        break;
                    case "min":
                        String minKey = test.min();
                        System.out.println(minKey + " " + test.get(minKey));
                        break;
                    case "max":
                        String maxKey = test.max();
                        System.out.println(maxKey + " " + test.get(maxKey));
                        break;
                    case "select":
                        String select = test.select(scanner.nextInt());
                        System.out.println(select + " " + test.get(select));
                        break;
                    case "rank":
                        int rank = test.rank(scanner.next());
                        System.out.println(rank + " " + test.select(rank));
                        break;
                    case "floor":
                        String k1 = scanner.next();
                        String floorKey = test.floor(k1);
                        System.out.println(floorKey + " " + test.get(floorKey));
                        break;
                    case "ceiling":
                        String k2 = scanner.next();
                        String ceilKey = test.ceiling(k2);
                        System.out.println(ceilKey + " " + test.get(ceilKey));
                        break;
                    case "delMin":
                        test.deleteMin();
                        break;
                    case "delMax":
                        test.deleteMax();
                        break;
                    case "size":
                        System.out.println(test.size());
                        break;
                    case "sizeOf":
                        String lo = scanner.next();
                        String hi = scanner.next();
                        System.out.println(test.size(lo, hi));
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
