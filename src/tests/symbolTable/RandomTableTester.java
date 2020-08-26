package tests.symbolTable;

import princeton.algo.symbolTable.OrderedSymbolTable;

import java.util.Random;

public class RandomTableTester {

    private static final Random random = new Random();

    public static boolean tester(OrderedSymbolTable<Integer, Integer> table1,
                                 OrderedSymbolTable<Integer, Integer> table2, int size) {
        return tester(table1, table2, size, false);
    }

    public static boolean tester(OrderedSymbolTable<Integer, Integer> table1,
                                 OrderedSymbolTable<Integer, Integer> table2, int size, boolean printAll) {
        if (size < 0) throw new IllegalArgumentException("size < 0");
        for (int i = 0; i < size; i++) {
            double randD = random.nextDouble();
            int rand = random.nextInt();
            try {
                if (randD < 0.48) {
                    if (printAll) System.out.println(">> put " + rand);
                    table1.put(rand, rand);
                    table2.put(rand, rand);
                } else if (randD < 0.65) {
                    if (printAll) System.out.println(">> delete Max");
                    table1.deleteMax();
                    table2.deleteMax();
                } else if (randD < 0.8) {
                    if (printAll) System.out.println(">> delete Min");
                    table1.deleteMin();
                    table2.deleteMin();
                } else {
                    if (printAll) System.out.println(">> delete floor of " + rand);
                    table1.delete(table1.floor(rand));
                    table2.delete(table2.floor(rand));
                }
            } catch (Exception ignored) {
            }
            if (printAll) {
                System.out.println("table 1: ");
                for (Integer j : table1.keys()) System.out.print(j + " ");
                System.out.println();
                System.out.println("table 2: ");
                for (Integer j : table2.keys()) System.out.print(j + " ");
                System.out.println();
            }
        }
        System.out.println("tb1 size: " + table1.size());
        System.out.println("tb2 size: " + table2.size());
        for (Integer i : table1.keys()) {
            if (!table2.contains(i)) return false;
        }
        for (Integer i : table2.keys()) {
            if (!table1.contains(i)) return false;
        }
        return true;
    }

    public static void main(String[] args) {

        OrderedSymbolTable<Integer, Integer> table1 = TableChooser.orderedSymbolTable("rbt");
        OrderedSymbolTable<Integer, Integer> table2 = TableChooser.orderedSymbolTable("bst");
        for (int i = 0; i < 5; i++) {
            System.out.println(tester(table1, table2, 1000000));
        }

        table1 = TableChooser.orderedSymbolTable("rbt");
        OrderedSymbolTable<Integer, Integer> table3 = TableChooser.orderedSymbolTable("avl");
        for (int i = 0; i < 5; i++) {
            System.out.println(tester(table1, table3, 1000000));
        }

        table2 = TableChooser.orderedSymbolTable("bst");
        table3 = TableChooser.orderedSymbolTable("avl");
        for (int i = 0; i < 5; i++) {
            System.out.println(tester(table2, table3, 1000000));
        }
    }
}
