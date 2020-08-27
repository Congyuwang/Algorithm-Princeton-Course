package tests.symbolTable;
import princeton.algo.queue.*;
import princeton.algo.queue.Queue;
import princeton.algo.sort.Quick;
import princeton.algo.sort.Shuffle;
import princeton.algo.symbolTable.SymbolTable;

import java.util.*;

/**
 *
 */
public class SymbolTablePerformanceTester {

    private final SymbolTable<Integer, Integer> symbolTable;
    private final Queue<Integer> queue;
    private final int initialSize;
    private final Random random = new Random();

    /**
     * Class {@code SymbolTablePerformanceTester} ...
     *
     * @param tableType ...
     * @param initialSize ...
     * @throws IllegalArgumentException when ...
     */
    public SymbolTablePerformanceTester(String tableType, int initialSize) {
        if (initialSize < 100) throw new IllegalArgumentException("initial size too small");
        this.symbolTable = TableChooser.symbolTable(tableType);
        this.initialSize = initialSize;
        this.queue = new LinkedQueue<>();
        System.out.printf("Initialization time: %f ms\n", fillInRandomInts() / 1000.0);
    }

    public TreeMap<String, Map<String, ?>> testPutDelete(int times) {

        TreeMap<String, String> statistics = new TreeMap<>();
        TreeMap<String, ArrayList<Long>> runTimes = new TreeMap<>();
        TreeMap<String, Map<String, ?>> result = new TreeMap<>();
        result.put("statistics", statistics);
        result.put("run times", runTimes);

        // store each runtime in an array
        ArrayList<Long> deleteRuntimes = new ArrayList<>();
        ArrayList<Long> putRuntimes = new ArrayList<>();
        runTimes.put("delete", deleteRuntimes);
        runTimes.put("put", putRuntimes);

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
                time = System.nanoTime() - time;
                deleteRuntimes.add(time);
            }
        }

        int putCount = putRuntimes.size();
        long putTime = putRuntimes.stream().mapToLong(Long::valueOf).sum();
        int deleteCount = deleteRuntimes.size();
        long deleteTime = deleteRuntimes.stream().mapToLong(Long::valueOf).sum();

        statistics.put("table type", symbolTable.getClass().toGenericString());
        statistics.put("test type", "put-delete-test");

        statistics.put("put count", String.valueOf(putRuntimes.size()));
        statistics.put("put time average (ns)", String.valueOf(putTime / (double) putCount));
        statistics.put("put total time (ms)", String.valueOf(putTime / 1000.0));
        statistics.put("put time 25% (ns)", percentile(putRuntimes, putCount >>> 2));
        statistics.put("put time median (ns)", percentile(putRuntimes, putCount >>> 1));
        statistics.put("put time 75% (ns)", percentile(putRuntimes, (putCount >>> 2) * 3));

        statistics.put("delete count", String.valueOf(runTimes.get("delete").size()));
        statistics.put("delete total time (ms)", String.valueOf(deleteTime / 1000.0));
        statistics.put("delete time average (ns)", String.valueOf(deleteTime / (double) deleteCount));
        statistics.put("delete time 25% (ns)", percentile(deleteRuntimes, deleteCount >>> 2));
        statistics.put("delete time median (ns)", percentile(deleteRuntimes, deleteCount >>> 1));
        statistics.put("delete time 75% (ns)", percentile(deleteRuntimes, (deleteCount >>> 2) * 3));
        return result;
    }

    public TreeMap<String, Map<String, ?>> testGet(int times) {
        TreeMap<String, String> statistics = new TreeMap<>();
        TreeMap<String, ArrayList<Long>> runTimes = new TreeMap<>();
        TreeMap<String, Map<String, ?>> result = new TreeMap<>();
        result.put("run times", runTimes);
        result.put("statistics", statistics);
        ArrayList<Long> getRuntimes = new ArrayList<>();
        runTimes.put("get", getRuntimes);

        Shuffle.shuffle(queue);
        Iterator<Integer> itr = queue.iterator();
        for (int i = 0; i < times; i++) {
            if (itr.hasNext()) {
                Integer key = itr.next();
                long time = System.nanoTime();
                Integer k = symbolTable.get(key);
                getRuntimes.add(System.nanoTime() - time);
                if (!key.equals(k)) throw new RuntimeException("get wrong result");
            } else {
                itr = queue.iterator();
                i--;
            }
        }

        int getCount = getRuntimes.size();
        long getTime = getRuntimes.stream().mapToLong(Long::valueOf).sum();

        statistics.put("get count", String.valueOf(runTimes.get("delete").size()));
        statistics.put("get total time (ms)", String.valueOf(getTime / 1000.0));
        statistics.put("get time average (ns)", String.valueOf(getTime / (double) getCount));
        statistics.put("get time 25% (ns)", percentile(getRuntimes, getCount >>> 2));
        statistics.put("get time median (ns)", percentile(getRuntimes, getCount >>> 1));
        statistics.put("get time 75% (ns)", percentile(getRuntimes, (getCount >>> 2) * 3));
        return result;
    }

    /**
     * Initialize the table and queue by filling in new integers.
     *
     * @return total nanoseconds elapsed during initialization
     */
    private long fillInRandomInts() {
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

    private String percentile(ArrayList<Long> runTimes, int i) {
        return String.valueOf(Quick.select(runTimes.toArray(new Long[0]), i));
    }

    public static void main(String[] args) {
        SymbolTablePerformanceTester tester = new SymbolTablePerformanceTester("rbt", 100000);
        Map<String, Map<String, ?>> result = tester.testPutDelete(1000);
        for(Map.Entry<String, ?> e : result.get("statistics").entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue().toString());
        }
    }
}
