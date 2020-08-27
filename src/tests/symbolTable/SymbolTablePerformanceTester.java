package tests.symbolTable;

import princeton.algo.queue.*;
import princeton.algo.queue.Queue;
import princeton.algo.sort.Quick;
import princeton.algo.sort.Shuffle;
import princeton.algo.symbolTable.SymbolTable;
import java.util.*;
import static java.lang.System.gc;

/**
 * The class provides performance tests for symbolTables.
 * Call {@link SymbolTablePerformanceTester#testPutDelete(int)} to test put and delete methods.
 * Call {@link SymbolTablePerformanceTester#testGet(int)} to test get methods.
 * Print the {@code SymbolTablePerformanceTester} object to show statistics.
 */
public class SymbolTablePerformanceTester {

    private final SymbolTable<Integer, Integer> symbolTable;
    private final Queue<Integer> queue;
    private final int initialSize;
    private final Random random = new Random();
    private final Map<String, Map<String, Object>> statistics;

    /**
     * Class {@code SymbolTablePerformanceTester} tests the performance of different symbol tables.
     *
     * @param tableType   type of table to be tested, see {@link TableChooser#symbolTable(String name)}
     * @param initialSize initial size should at least 100
     * @throws IllegalArgumentException when {@code initialSize} is smaller than 100,
     *                                  or if the {@code tableType} parameter is invalid.
     */
    public SymbolTablePerformanceTester(String tableType, int initialSize) {
        if (initialSize < 100) throw new IllegalArgumentException("initial size too small");
        this.symbolTable = TableChooser.symbolTable(tableType);
        if (this.symbolTable == null)
            throw new IllegalArgumentException(String.format("%s is not a valid table type", tableType));
        this.initialSize = initialSize;
        this.queue = new LinkedQueue<>();
        this.statistics = new TreeMap<>();
        System.out.printf("Initialization time: %f ms\n", fillInRandomInt() / 1000000.0);
    }

    /**
     * Call this to test {@code put} and {@code test} methods.
     * The two methods are called randomly with equal probability.
     *
     * @param times the total times of {@code put} and {@code test} to be called.
     */
    public void testPutDelete(int times) {

        if (times < 0) throw new IllegalArgumentException("negative times argument");

        ArrayList<Long> deleteRuntimes = new ArrayList<>();
        ArrayList<Long> putRuntimes = new ArrayList<>();

        for (int i = 0; i < times; i++) {
            boolean doPut = random.nextBoolean();
            if (doPut) {
                long time = insertNewInt(symbolTable, queue, random);
                putRuntimes.add(time);
            } else {
                if (queue.isEmpty()) continue;
                Integer deleteKey = queue.dequeue();

                long time = System.nanoTime();
                symbolTable.delete(deleteKey);
                deleteRuntimes.add(System.nanoTime() - time);
            }
        }
        writeResults("delete-test", deleteRuntimes);
        writeResults("put-test", putRuntimes);
    }

    /**
     * Test get method of the table
     *
     * @param times the number of times for testing {@code get()}
     */
    public void testGet(int times) {

        if (times < 0) throw new IllegalArgumentException("negative times argument");

        ArrayList<Long> elapsedTimes = new ArrayList<>();

        Shuffle.shuffle(queue);
        Iterator<Integer> itr = queue.iterator();
        for (int i = 0; i < times; i++) {
            if (itr.hasNext()) {
                Integer key = itr.next();

                long time = System.nanoTime();
                Integer k = symbolTable.get(key);
                elapsedTimes.add(System.nanoTime() - time);

                if (!key.equals(k)) throw new RuntimeException("get wrong result");
            } else {
                itr = queue.iterator();
                i--;
            }
        }
        writeResults("get-test", elapsedTimes);
    }

    /**
     * print this result to view the statistics.
     *
     * @return result string
     */
    @Override
    public String toString() {
        char[] horizontalChars = new char[25 + 8];
        Arrays.fill(horizontalChars, '-');
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Test Result Of: ")
                .append(symbolTable.getClass().toString()).append('\n')
                .append(horizontalChars).append('\n');
        for (Map.Entry<String, Map<String, Object>> e : statistics.entrySet()) {
            if (e.getValue() == null) continue;
            stringBuilder.append(">> test type: ")
                    .append(e.getValue().get("test type"))
                    .append('\n')
                    .append(printSingleResult(e.getValue()));
        }
        return stringBuilder.toString();
    }

    private void writeResults(String testName, ArrayList<Long> elapsedTimes) {
        int getCount = elapsedTimes.size();
        long getTime = elapsedTimes.stream().mapToLong(Long::valueOf).sum();
        Map<String, Object> statisticsTable = new TreeMap<>();
        statisticsTable.put("test type", testName);
        statisticsTable.put("count", getCount);
        statisticsTable.put("total time (ms)", getTime / 1000000.0);
        statisticsTable.put("time average (ns)", getTime / (double) getCount);
        statisticsTable.put("time 25% (ns)", percentile(elapsedTimes, getCount >>> 2));
        statisticsTable.put("time median (ns)", percentile(elapsedTimes, getCount >>> 1));
        statisticsTable.put("time 75% (ns)", percentile(elapsedTimes, (getCount >>> 2) * 3));
        statistics.put(testName, statisticsTable);
    }

    private String printSingleResult(Map<String, Object> singleResult) {
        char[] horizontalChars = new char[25 + 8];
        Arrays.fill(horizontalChars, '-');
        return String.valueOf(horizontalChars) + '\n' +
                toStringSingleLine(singleResult, "count") +
                toStringSingleLine(singleResult, "total time (ms)") +
                toStringSingleLine(singleResult, "time average (ns)") +
                toStringSingleLine(singleResult, "time 25% (ns)") +
                toStringSingleLine(singleResult, "time median (ns)") +
                toStringSingleLine(singleResult, "time 75% (ns)") +
                String.valueOf(horizontalChars) + '\n';
    }

    private String toStringSingleLine(Map<String, Object> singleResult, String key) {
        return String.format("%20s: %.8s\n", key, singleResult.get(key));
    }

    /**
     * Initialize the table and queue by filling in new integers.
     *
     * @return total nanoseconds elapsed during initialization
     */
    private long fillInRandomInt() {
        long initializeTime = 0;
        for (int i = 0; i < initialSize; i++) {
            initializeTime += insertNewInt(symbolTable, queue, random);
        }
        return initializeTime;
    }

    /**
     * Put new integer into test table (and queue).
     *
     * @return time elapsed during {@code put} operation (nanoseconds)
     */
    private static long insertNewInt(SymbolTable<Integer, Integer> table, Queue<Integer> queue, Random random) {
        while (true) {
            int randomInt = random.nextInt();
            if (!table.contains(randomInt)) {
                long time = System.nanoTime();
                table.put(randomInt, randomInt);
                time = System.nanoTime() - time;
                queue.enqueue(randomInt);
                return time;
            }
        }
    }

    /**
     * Calculate percentile for a long array
     *
     * @param array the long array
     * @param i     the ith smallest value of the array
     * @return the ith smallest value of the array
     */
    private String percentile(ArrayList<Long> array, int i) {
        return String.valueOf(Quick.select(array.toArray(new Long[0]), i));
    }

    public static void main(String[] args) {

        SymbolTablePerformanceTester bstTester = new SymbolTablePerformanceTester("bst", 1_000_000);
        bstTester.testPutDelete(2_000_000);
        bstTester.testGet(1_000_000);
        System.out.println(bstTester);
        gc();

        SymbolTablePerformanceTester rbtTester = new SymbolTablePerformanceTester("rbt", 1_000_000);
        rbtTester.testPutDelete(2_000_000);
        rbtTester.testGet(1_000_000);
        System.out.println(rbtTester);
        gc();

        SymbolTablePerformanceTester avlTester = new SymbolTablePerformanceTester("avl", 1_000_000);
        avlTester.testPutDelete(2_000_000);
        avlTester.testGet(1_000_000);
        System.out.println(avlTester);
        gc();

        SymbolTablePerformanceTester javaTester = new SymbolTablePerformanceTester("java", 1_000_000);
        javaTester.testPutDelete(2_000_000);
        javaTester.testGet(1_000_000);
        System.out.println(javaTester);
        gc();
    }
}
