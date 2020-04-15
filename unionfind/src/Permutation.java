import princeton.algorithm.queue.RandomizedQueue;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        StreamChooseK<String> streamChoose;
        try {
            streamChoose = new StreamChooseK<>(Integer.parseInt(args[0]));
        } catch (NumberFormatException e) {
            return;
        }
        while (StdIn.hasNextLine()) {
            try {
                streamChoose.update(StdIn.readString());
            } catch (NoSuchElementException e) {
                break;
            }
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

    private int k;
    private int count;
    RandomizedQueue<Item> collection;

    StreamChooseK(int k) {
        this.k = k;
        collection = new RandomizedQueue<>();
    }

    /**
     * Ignore an update item with probability 1 / (k+1) results
     * after the collection is full results in a uniformly chosen
     * collection.
     * <p>
     * Each item, once admitted into RandomizedQueue, will have equal probabilities
     * of being retained in the final collection regardless of the total updating times
     * afterwards. So, to obtain uniform distribution, just enqueue an item and than randomly
     * dequeue one item.
     * </p>
     * @param item the added item for updating the collection
     */
    void update(Item item) {
        if (count++ < k) {
            collection.enqueue(item);
        } else {
            collection.enqueue(item);
            collection.dequeue();
        }
    }

    RandomizedQueue<Item> getCollection() {
        return collection;
    }
}
