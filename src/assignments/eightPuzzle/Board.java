package assignments.eightPuzzle;

import java.util.ArrayDeque;
import java.util.Iterator;

public class Board {

    private final int[][] tiles;
    private final int n;
    private final int[] zero;
    private final int hammingDistance;
    private final int manhattanDistance;

    /**
     * Create a board, calculate its hammingDistance, manhattanDistance, find the
     * zero position. Finish all these ar construction to prevent recalculation. The
     * constructor takes O(n^2) time.
     */
    public Board(int[][] tiles) {
        if (tiles.length < 2 || tiles.length != tiles[0].length) {
            throw new IllegalArgumentException("illegal board shape");
        }
        this.n = tiles.length;
        this.tiles = makeClone(tiles, n);
        int hamming = -1;
        int pos = 1;
        int manhattan = 0;
        int[] zero = null;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != pos++) {
                    hamming++;
                    manhattan += manhattan(i, j, this.tiles[i][j], n);
                }
                if (zero == null && this.tiles[i][j] == 0) {
                    zero = new int[] {i, j};
                }
            }
        }
        this.zero = zero;
        this.hammingDistance = hamming;
        this.manhattanDistance = manhattan;
    }

    /**
     * Note that the private constructor does NOT make a clone of tiles
     */
    private Board(int[][] tiles, int hamming, int manhattan, int[] zero) {
        this.n = tiles.length;
        this.tiles = tiles;
        this.zero = zero;
        this.hammingDistance = hamming;
        this.manhattanDistance = manhattan;
    }

    // string representation of this board
    public String toString() {
        final int padding = 2 + (int) Math.max(Math.floor(Math.log10(n * n - 1)), 0);
        final String digitFormat = "%" + padding + "d";
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.valueOf(n));
        stringBuilder.append('\n');
        for (int[] row : tiles) {
            for (int i : row) {
                stringBuilder.append(String.format(digitFormat, i));
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    /**
     * return the size (height) of the board. Constant run time.
     *
     * @return the height of the square board
     */
    public final int dimension() {
        return n;
    }

    /**
     * calculate the hamming distance (the number of tiles out of place) between
     * this board and the goal board. Constant run time.
     *
     * @return the hamming distance
     */
    public final int hamming() {
        return hammingDistance;
    }

    /**
     * calculate the manhattan distance (the sum of manhattan distances of each tile
     * to its goal position) between this board and the goal board. Constant run
     * time.
     *
     * @return the manhattan distance
     */
    public final int manhattan() {
        return manhattanDistance;
    }

    /**
     * check whether this board is the goal board. Constant run time.
     */
    public final boolean isGoal() {
        return manhattanDistance == 0;
    }

    /**
     * check whether this board equals to another board. O(n^2) time.
     */
    @Override
    public boolean equals(Object y) {
        if (y == null || !(y instanceof Board)) {
            return false;
        }
        Board b = (Board) y;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != b.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * return a iterator giving all neighbors of a board.
     * O(n^2) time for cloning the board.
     */
    public Iterable<Board> neighbors() {

        final ArrayDeque<int[]> neighbors = new ArrayDeque<>(4);

        if (zero[0] > 0) {
            neighbors.add(new int[] { -1, 0 });
        }

        if (zero[1] > 0) {
            neighbors.add(new int[] { 0, -1 });
        }

        if (zero[0] < n - 1) {
            neighbors.add(new int[] { 1, 0 });
        }

        if (zero[1] < n - 1) {
            neighbors.add(new int[] { 0, 1 });
        }

        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {

                    @Override
                    public boolean hasNext() {
                        return !neighbors.isEmpty();
                    }

                    @Override
                    public Board next() {
                        int[] move = neighbors.poll();
                        int[][] tilesCopy = makeClone(tiles, n);
                        int oldVal = tilesCopy[move[0] + zero[0]][move[1] + zero[1]];

                        // exchange zero with another position
                        tilesCopy[zero[0]][zero[1]] = oldVal;
                        tilesCopy[move[0] + zero[0]][move[1] + zero[1]] = 0;

                        // update information
                        int oldManhattan = manhattan(move[0] + zero[0], move[1] + zero[1], oldVal, n);
                        int newManhattan = manhattan(zero[0], zero[1], oldVal, n);
                        int deltaHamming = (int) (Math.signum(newManhattan) - Math.signum(oldManhattan));
                        return new Board(tilesCopy, deltaHamming + hammingDistance,
                                newManhattan - oldManhattan + manhattanDistance,
                                new int[] { move[0] + zero[0], move[1] + zero[1] });
                    }
                };
            }
        };
    }

    private static final int manhattan(int row, int col, int val, int n) {
        if (val == 0) {
            // skip 0
            return 0;
        }
        return Math.abs((val - 1) / n - row) + Math.abs((val - 1) % n - col);
    }

    private static final int[][] makeClone(int[][] tiles, int n) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    /**
     * unit testing.
     *
     * @param args no args needed
     */
    public static void main(String[] args) {
        int[][] test = { { 3, 2, 1 }, { 4, 0, 8 }, { 5, 7, 6 } };
        Board board = new Board(test);
        for (Board b : board.neighbors()) {
            System.out.println(b);
        }
        System.out.println(new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }).manhattan()); // 0
        System.out.println(new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }).hamming()); // 0
        System.out.println(new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }).isGoal()); // true
        System.out.println(new Board(new int[][] { { 1, 3, 2 }, { 4, 5, 6 }, { 0, 8, 7 } }).manhattan()); // 4
        System.out.println(new Board(new int[][] { { 1, 3, 2 }, { 4, 5, 6 }, { 0, 8, 7 } }).hamming()); // 3
        for (Board b : new Board(new int[][] { { 1, 3, 2 }, { 4, 5, 6 }, { 0, 8, 7 } }).neighbors()) {
            System.out.print(b);
            System.out.println(b.manhattan());
        }
    }

}
