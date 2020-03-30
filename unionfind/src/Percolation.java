import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {

    private final int dimensionN;
    private final boolean[][] isOpenTable;
    private final boolean[] isRootFull;
    private final boolean[] isRootEmpty;
    private boolean isPercolated;
    private final WeightedQuickUnionUF uf;
    private int openSite;

    // creates n-by-n grid, with all sites initially blocked
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

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (rowColException(row, col)) {
            throw new IllegalArgumentException("row and col numbers should be between 1 to n!");
        }
        if (!isOpen(row, col)) {
            openSite++;
            isOpenTable[row - 1][col - 1] = true;

            // top case: mark the root of a top open site as full
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

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (rowColException(row, col)) {
            throw new IllegalArgumentException("row and col numbers should be between 1 to n!");
        }
        return isOpenTable[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (rowColException(row, col)) {
            throw new IllegalArgumentException("row and col numbers should be between 1 to n!");
        }
        int root = uf.find(rowColToN(row, col));
        return isRootFull[root];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }

    // test client (optional)
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
