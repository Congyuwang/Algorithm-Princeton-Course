package tests.sortShuffle;

/**
 * An interface for testing algorithm efficiency
 */
public interface RuntimeTest {

    /**
     * run the test and return run time
     * @return run time in seconds
     */
    double run();

    default double run(int n) {
        double totalTime = 0.0;
        for (int i = 0; i < n; i++) {
            totalTime += run();
        }
        return totalTime;
    }
}
