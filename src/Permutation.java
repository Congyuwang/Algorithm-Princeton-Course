import edu.princeton.cs.algs4.StdIn;
import princeton.algo.queue.StreamChooseK;

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
        for (String s : streamChoose.getCollection()) {
            System.out.println(s);
        }
    }
}
