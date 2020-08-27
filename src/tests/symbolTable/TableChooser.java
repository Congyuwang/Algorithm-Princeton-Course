package tests.symbolTable;

import princeton.algo.symbolTable.*;

public class TableChooser {
    /**
     * return a new table using required implementation
     *
     * @param name the name of the symbol table implementation to be tested
     * @return a new and empty symbolTable with the named implementation
     */
    public static <K extends Comparable<? super K>, V> SymbolTable<K, V> symbolTable(String name) {
        return switch (name) {
            case "ss" -> new SequentialSearchST<>();
            case "hs" -> null; // hashTable
            case "bs" -> new BinarySearchST<>();
            case "bst" -> new BinarySearchTree<>();
            case "rbt" -> new RedBlackTree<>();
            case "avl" -> new AVLTree<>();
            default -> null;
        };
    }

    /**
     * return a new table using required implementation
     *
     * @param name the name of the symbol table implementation to be tested
     * @return a new and empty symbolTable with the named implementation
     */
    public static <K extends Comparable<? super K>, V> OrderedSymbolTable<K, V> orderedSymbolTable(String name) {
        return switch (name) {
            case "bs" -> new BinarySearchST<>();
            case "bst" -> new BinarySearchTree<>();
            case "rbt" -> new RedBlackTree<>();
            case "avl" -> new AVLTree<>();
            default -> null;
        };
    }
}