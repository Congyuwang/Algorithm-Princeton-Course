import princeton.unionfind.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {

    /**
     * The {@code Percolation} class represent a percolation data type.
     * It supports the creation of a blocked percolation,
     * openings of percolation sites by <em>open</em>, checking the state of a site
     * (whether a site is open or closed) by <em>isOpen</em>, and also
     * checking whether a site is full or not (whether it is connected from the top)
     * by <em>isFull</em>.
     * The class also has method <em>numberOfOpenSites</em> which returns the
     * number of open sites, and <em>percolates</em> which returns
     * whether the percolation percolates or not.
     *
     * The class is based on the classic algorithm {@code WeightedQuickUnionUF}.
     */
    private final int dimensionN;
    private final boolean[][] isOpenTable;
    private final boolean[] isRootFull;
    private final boolean[] isRootEmpty;
    private boolean isPercolated;
    private final WeightedQuickUnionUF uf;
    private int openSite;

    /**
     * Creates n-by-n grid, with all sites initially blocked
     * @param n the size of the grid (n-by-n).
     */
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("N must be positive");
        }
        dimensionN = n;
        int dimensionNSquared = n * n;
        openSite = 0;
        isPercolated = false;
        isOpenTable = new boolean[n][n];
        isRootFull = new boolean[dimensionNSquared];
        isRootEmpty = new boolean[dimensionNSquared];
        uf = new WeightedQuickUnionUF(dimensionNSquared);
    }

    private boolean rowColException(int row, int col) {
        return row < 1 || row > dimensionN || col < 1 || col > dimensionN;
    }

    // use 1 to n index
    private int rowColToN(int row, int col) {
        return (row - 1) * dimensionN + (col - 1);
    }

    private void unionSites(WeightedQuickUnionUF ufs, int row1, int col1, int row2, int col2) {
        if (isOpen(row2, col2)) {
            int root1 = uf.find(rowColToN(row1, col1));
            int root2 = uf.find(rowColToN(row2, col2));
            boolean empty = isRootEmpty[root1] || isRootEmpty[root2];
            boolean full = isRootFull[root1] || isRootFull[root2];
            ufs.union(root1, root2);

            /*
             the percolation percolates either one of the two trees to be combined
             is full from above and either one of the two is empty from below.
            */
            if (full) {
                isRootFull[root1] = true;
                isRootFull[root2] = true;
            }
            if (empty) {
                isRootEmpty[root1] = true;
                isRootEmpty[root2] = true;
            }
            if (empty && full) isPercolated = true;
        }
    }

    /**
     * Open the site {@code (row, col)}.
     *
     * @param row the row number of the site to open (between {@code 1} and {@code n}).
     * @param col the column number of the site to open (between {@code 1} and {@code n}).
     */
    public void open(int row, int col) {
        if (rowColException(row, col)) {
            throw new IllegalArgumentException("row and col numbers should be between 1 to n!");
        }
        if (!isOpen(row, col)) {
            openSite++;
            isOpenTable[row - 1][col - 1] = true;

            // the percolation percolates if a root is both full from above and empty from below.
            if (dimensionN == 1) {
                isPercolated = true;
                isRootFull[0] = true;
            } else if (row == 1) {
                int root = uf.find(rowColToN(row, col));
                isRootFull[root] = true;
                if (isRootEmpty[root]) isPercolated = true;
            } else if (row == dimensionN) {
                int root = uf.find(rowColToN(row, col));
                isRootEmpty[root] = true;
                if (isRootFull[root]) isPercolated = true;
            }
            // middle cases
            if (row > 1) unionSites(uf, row, col, row - 1, col);
            if (col > 1) unionSites(uf, row, col, row, col - 1);
            if (row < dimensionN) unionSites(uf, row, col, row + 1, col);
            if (col < dimensionN) unionSites(uf, row, col, row, col + 1);
        }
    }

    /**
     * Returns if a certain site is already open
     *
     * @param row the row number of the site (between {@code 1} and {@code n}).
     * @param col the column number of the site (between {@code 1} and {@code n}).
     * @return if the site is open (boolean).
     */
    public boolean isOpen(int row, int col) {
        if (rowColException(row, col)) {
            throw new IllegalArgumentException("row and col numbers should be between 1 to n!");
        }
        return isOpenTable[row - 1][col - 1];
    }

    /**
     * Returns if the site is full from above
     *
     * @param row the row number of the site (between {@code 1} and {@code n}).
     * @param col the column number of the site (between {@code 1} and {@code n}).
     * @return if the site is full (connected from above).
     */
    public boolean isFull(int row, int col) {
        if (rowColException(row, col)) {
            throw new IllegalArgumentException("row and col numbers should be between 1 to n!");
        }
        int root = uf.find(rowColToN(row, col));
        return isRootFull[root];
    }

    /**
     * Returns the number of open sites in the percolation.
     *
     * @return the number of open sites.
     */
    public int numberOfOpenSites() {
        return openSite;
    }

    /**
     * Returns the current state of percolation.
     * @return whether the percolation percolates.
     */
    public boolean percolates() {
        return isPercolated;
    }

    /**
     * Takes firstly the size of the percolation,
     * and then the number of {@code open} operations to be conducted.
     * Then it takes in the position of sites to be opened one by one.
     * Afterwards, it prints the percolation as 0 and 1.
     *
     * The next step is <it>input check full</it>,
     * it takes the position of a site and checks whether it is full,
     * and it prints the <it>full states</it> of the percolation.
     *
     * The last two output are the number of open sites,
     * and the state of percolation.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int k = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < k; i++) {
            int a = StdIn.readInt();
            int b = StdIn.readInt();
            percolation.open(a, b);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (percolation.isOpenTable[i][j]) {
                    System.out.print(1 + "\t");
                } else {
                    System.out.print(0 + "\t");
                }
            }
            System.out.println();
        }
        System.out.println("Now, input check full");
        int a = StdIn.readInt();
        int b = StdIn.readInt();
        System.out.println(percolation.isFull(a, b));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (percolation.isFull(i+1, j+1)) {
                    System.out.print(1 + "\t");
                } else {
                    System.out.print(0 + "\t");
                }
            }
            System.out.println();
        }
        System.out.println("numberOfOpenSites:");
        System.out.println(percolation.numberOfOpenSites());
        System.out.println("If percolate:");
        System.out.println(percolation.percolates());
    }
}
