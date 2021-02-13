package assignments.helloWorld;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int count = 1;
        while (!StdIn.isEmpty()) {
            var s = StdIn.readString();
            if (champion == null) {
                champion = s;
            } else if (StdRandom.bernoulli(1.0 / ++count)) {
                champion = s;
            }
        }
        System.out.println(champion);
    }
}
