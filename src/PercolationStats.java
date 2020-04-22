import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private final int trials;
    private final double meanStored;
    private final double stdStored;
    private final double confidenceInterval;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException("N > 0; T > 1!");
        confidenceInterval = 1.96;
        this.trials = trials;
        int[] thresholds = new int[trials];
        int sampleLength = n * n;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            do {
                int value = StdRandom.uniform(sampleLength);
                percolation.open(value / n + 1, value % n + 1);
            } while (!percolation.percolates());
            thresholds[i] = percolation.numberOfOpenSites();
        }

        meanStored = StdStats.mean(thresholds) / sampleLength;
        stdStored = StdStats.stddev(thresholds) / sampleLength;
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanStored;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdStored;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return meanStored - stdStored * confidenceInterval / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return meanStored + stdStored * confidenceInterval / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int n = StdIn.readInt();
            int t = StdIn.readInt();
            Stopwatch stopwatch = new Stopwatch();
            PercolationStats percolationStats = new PercolationStats(n, t);
            System.out.println("mean" + "\t= " + percolationStats.mean() + "\t");
            System.out.println("stddev" + "\t= " + percolationStats.stddev() + "\t");
            System.out.println("95% confidence interval" + "\t= " + "[" +
                    percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]\t");
            System.out.println("Elapsed:" + stopwatch.elapsedTime());
        }
    }
}
