package tests.symbolTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import princeton.algo.symbolTable.*;

/**
 * The {@code FrequencyCounter} class tests various symbol table implementation
 * using frequencyCounter.
 */
public class FrequencyCounter {

    private static final Pattern englishWords = Pattern.compile("[a-z]+");

    /**
     * Print the K word with most occurrences and the number of occurrences
     */
    public static <T extends SymbolTable<String, Integer>> void printTopK(T table, int top) {
        T result = TableUtil.topNValue(table, top);
        for (Pair<String, Integer> pair : result.pairs()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    /**
     * Print the K word with fewest occurrences and the number of occurrences
     */
    public static <T extends SymbolTable<String, Integer>> void printBottomK(T table, int top) {
        T result = TableUtil.bottomNValue(table, top);
        for (Pair<String, Integer> pair : result.pairs()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    /**
     * Remove all words records with words shorter than {@code limit} length
     *
     * @param limit the lower threshold for words to be kept
     */
    public static <T extends SymbolTable<String, Integer>> void filterLength(int limit, T counter) {
        TableUtil.keyFilter(counter, i -> i.length() >= limit);
    }

    /**
     * Count the number of occurrences for each words
     *
     * @param inputFile enter an input file containing many english words
     * @param name      the name of the symbol table implementation to be tested
     * @throws FileNotFoundException if the file path is incorrect
     */
    public static <T extends SymbolTable<String, Integer>> T countWords(String inputFile, String name, int min_length, T table)
            throws FileNotFoundException {
        if (table == null) throw new NullPointerException("null table");
        Scanner scanner = new Scanner(new File(inputFile));
        while(scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            // only check english words with no punctuation, etc.
            // this is a lazy solution
            if (!englishWords.matcher(word).matches() || word.length() < min_length) continue;
            if (table.contains(word)) table.put(word, table.get(word) + 1);
            else table.put(word, 1);
        }
        scanner.close();
        return table;
    }
}
