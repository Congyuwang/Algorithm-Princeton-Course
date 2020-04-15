import princeton.algo.queue.RandomizedQueue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {
        StreamChooseK<String> streamChoose;
        try {
            streamChoose = new StreamChooseK<>(Integer.parseInt(args[0]));
        } catch (NumberFormatException e) {
            return;
        }
        while (!StdIn.isEmpty()) {
            streamChoose.update(StdIn.readString());
        }
        RandomizedQueue<String> collection = streamChoose.getCollection();
        for (String s : collection) {
            System.out.println(s);
        }
    }
}

/**
 * A memory efficient way to choose k items from a stream of inputs,
 * which features constant operation time and constant memory usage
 * proportional to k.
 *
 * @param <Item> item type parameter
 */
class StreamChooseK<Item> {

    private final int k;
    private int count;
    private double p = 1.0;
    private RandomizedQueue<Item> collection;

    StreamChooseK(int k) {
        this.k = k;
        collection = new RandomizedQueue<>();
    }

    /**
     * After the first k items, update the new item after with a diminishing probability.
     * The k + 1 item is updated with probability k / (1+k).
     * The k + n item is updated with probability P(n),
     * which is calculated by P(n) = k * P(n - 1) / (k + P(n - 1))
     * @param item the added item for updating the collection
     */
    void update(Item item) {
        if (count < k) {
            collection.enqueue(item);
        } else {
            p = k * p / (p + k);
            if (StdRandom.uniform() < p) {
                collection.dequeue();
                collection.enqueue(item);
            }
        }
        count++;
    }

    RandomizedQueue<Item> getCollection() {
        return collection;
    }
}
