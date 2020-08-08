package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import princeton.algo.symbolTable.SequentialSearchST;
import princeton.algo.symbolTable.SymbolTable;

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
     * @throws FileNotFoundException
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
     * @throws FileNotFoundException
     */
    public FrequencyCounter(String inputFile, String name, int min_length) throws FileNotFoundException {
        counter = countWords(inputFile, name, min_length);
    }

    /**
     * Print the word with most occurences and the number of occurrences
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
     * Count the number of occurences for each words
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
    private static SymbolTable<String, Integer> tableChooser(String name) {
        switch (name) {
            case "sequential":
                return new SequentialSearchST<String, Integer>();
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        FrequencyCounter fc;
        try {
            fc = new FrequencyCounter("data/shorterShakespeare.txt", "sequential");
            fc.printMostFrequent();
            fc.deleteWordsShorterThan(10);
            fc.printAllWords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
