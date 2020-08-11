package tests;

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

    public final int DEFAULT_MIN_LENGTH = 4;

    public final SymbolTable<String, Integer> counter;

    private static final Pattern englishWords = Pattern.compile("[a-z]+");

    /**
     * Create a new frequency counter and do the calculator with MIN_LENGTH = 4
     *
     * @param inputFile enter an input file containing many english words
     * @param name      the name of the symbol table implementation to be tested
     * @throws FileNotFoundException if the file path is incorrect
     */
    public FrequencyCounter(String inputFile, String name) throws FileNotFoundException {
        counter = countWords(inputFile, name, DEFAULT_MIN_LENGTH);
    }

    /**
     * Create a new frequency counter and do the calculator with specified
     * MIN_LENGTH
     *
     * @param inputFile enter an input file containing many english words
     * @param name      the name of the symbol table implementation to be tested
     * @throws FileNotFoundException if the file path is incorrect
     */
    public FrequencyCounter(String inputFile, String name, int min_length) throws FileNotFoundException {
        counter = countWords(inputFile, name, min_length);
    }

    /**
     * Print the word with most occurrences and the number of occurrences
     */
    public void printMostFrequent() {
        String maxWord = null;
        Integer maxWordCount = null;
        for (String word : counter.keys()) {
            Integer count = counter.get(word);
            if (maxWordCount == null) {
                maxWordCount = count;
                continue;
            }
            if (count.compareTo(maxWordCount) > 0) {
                maxWordCount = count;
                maxWord = word;
            }
        }
        System.out.println(maxWord + " " + maxWordCount);
    }

    /**
     * Print all words counts in the table
     */
    public void printAllWords() {
        for (String word : counter.keys()) {
            System.out.println(word + " " + counter.get(word));
        }
    }

    /**
     * Remove all words records with words shorter than {@code limit} length
     *
     * @param limit the lower threshold for words to be kept
     */
    public void deleteWordsShorterThan(int limit) {
        for (String word : counter.keys()) {
            if (word.length() < limit) {
                counter.delete(word);
            }
        }
    }

    /**
     * Count the number of occurrences for each words
     *
     * @param inputFile enter an input file containing many english words
     * @param name      the name of the symbol table implementation to be tested
     * @throws FileNotFoundException if the file path is incorrect
     */
    public static SymbolTable<String, Integer> countWords(String inputFile, String name, int min_length)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputFile));
        SymbolTable<String, Integer> wordCounter = tableChooser(name);
        while(scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            // only check english words with no punctuation, etc.
            // this is a lazy solution
            if (!englishWords.matcher(word).matches() || word.length() < min_length) {
                continue;
            }
            assert wordCounter != null;
            if (wordCounter.contains(word)) {
                wordCounter.put(word, wordCounter.get(word) + 1);
            } else {
                wordCounter.put(word, 1);
            }
        }
        scanner.close();
        return wordCounter;
    }

    /**
     * Count the number of occurrences for each words, returning OrderedSymbolTable
     *
     * @param inputFile enter an input file containing many english words
     * @param name      the name of the symbol table implementation to be tested
     * @throws FileNotFoundException if the file path is incorrect
     */
    public static OrderedSymbolTable<String, Integer> countWordsOrdered(String inputFile, String name, int min_length)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputFile));
        OrderedSymbolTable<String, Integer> wordCounter = tableChooserOrdered(name);
        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            // only check english words with no punctuation, etc.
            // this is a lazy solution
            if (!englishWords.matcher(word).matches() || word.length() < min_length) {
                continue;
            }
            assert wordCounter != null;
            if (wordCounter.contains(word)) {
                wordCounter.put(word, wordCounter.get(word) + 1);
            } else {
                wordCounter.put(word, 1);
            }
        }
        scanner.close();
        return wordCounter;
    }

    /**
     * return a new table using required implementation
     *
     * @param name the name of the symbol table implementation to be tested
     * @return     a new and empty symbolTable with the named implementation
     */
    public static SymbolTable<String, Integer> tableChooser(String name) {
        return switch (name) {
            case "ss" -> new SequentialSearchST<>();
            case "bs" -> new BinarySearchST<>();
            case "bst" -> new BinarySearchTree<>();
            default -> null;
        };
    }

    /**
     * return a new table using required implementation
     *
     * @param name the name of the symbol table implementation to be tested
     * @return a new and empty symbolTable with the named implementation
     */
    public static OrderedSymbolTable<String, Integer> tableChooserOrdered(String name) {
        return switch (name) {
            case "bs" -> new BinarySearchST<>();
            case "bst" -> new BinarySearchTree<>();
            default -> null;
        };
    }

    public static void main(String[] args) {
        FrequencyCounter fc;
        System.out.println("Sequential Symbol Table");
        try {
            fc = new FrequencyCounter("data/shorterShakespeare.txt", "ss");
            fc.printMostFrequent();
            System.out.println(fc.counter.size());
            fc.deleteWordsShorterThan(10);
            System.out.println(fc.counter.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FrequencyCounter fc1;
        System.out.println("Binary Search Symbol Table");
        try {
            fc1 = new FrequencyCounter("data/shorterShakespeare.txt", "bs");
            fc1.printMostFrequent();
            System.out.println(fc1.counter.size());
            fc1.deleteWordsShorterThan(10);
            System.out.println(fc1.counter.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
