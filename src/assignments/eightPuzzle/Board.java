package assignments.eightPuzzle;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;

public class Board {

    private final short[][] tiles;
    private final short n;
    private final short[] zero;
    private final short hammingDistance;
    private final int manhattanDistance;
    private final short[] fromMove;

    /**
     * Create a board, calculate its hammingDistance, manhattanDistance, find the
     * zero position. Finish all these constructions to prevent recalculation. The
     * constructor takes O(n^2) time.
     */
    public Board(int[][] tiles) {
        if (tiles.length < 2 || tiles.length != tiles[0].length) {
            throw new IllegalArgumentException("illegal board shape");
        }
        this.n = (short) tiles.length;
        this.tiles = new short[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = (short) tiles[i][j];
            }
        }
        short hamming = -1;
        int pos = 1;
        int manhattan = 0;
        short[] zero = null;
        for (short i = 0; i < n; i++) {
            for (short j = 0; j < n; j++) {
                if (this.tiles[i][j] != pos++) {
                    hamming++;
                    manhattan += internalManhattan(i, j, this.tiles[i][j], n);
                }
                if (zero == null && this.tiles[i][j] == 0) {
                    zero = new short[] { i, j };
                }
            }
        }
        this.zero = zero;
        this.hammingDistance = hamming;
        this.manhattanDistance = manhattan;
        this.fromMove = null;
    }

    /**
     * Note that the private constructor does NOT make a clone of tiles
     */
    private Board(short[][] tiles, short hamming, int manhattan, short[] zero, short[] move) {
        this.n = (short) tiles.length;
        this.tiles = tiles;
        this.zero = zero;
        this.hammingDistance = hamming;
        this.manhattanDistance = manhattan;
        this.fromMove = move;
    }

    // string representation of this board
    public String toString() {
        final int padding = 2 + (int) Math.max(Math.floor(Math.log10(n * n - 1)), 0);
        final String digitFormat = "%" + padding + "d";
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.valueOf(n));
        stringBuilder.append('\n');
        for (short[] row : tiles) {
            for (short i : row) {
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
    public int dimension() {
        return n;
    }

    /**
     * calculate the hamming distance (the number of tiles out of place) between
     * this board and the goal board. Constant run time.
     *
     * @return the hamming distance
     */
    public int hamming() {
        return hammingDistance;
    }

    /**
     * calculate the manhattan distance (the sum of manhattan distances of each tile
     * to its goal position) between this board and the goal board. Constant run
     * time.
     *
     * @return the manhattan distance
     */
    public int manhattan() {
        return manhattanDistance;
    }

    /**
     * check whether this board is the goal board. Constant run time.
     */
    public boolean isGoal() {
        return manhattanDistance == 0;
    }

    /**
     * check whether this board equals to another board. O(n^2) time.
     */
    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
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
     * return a iterator giving all neighbors of a board. O(n^2) time for cloning
     * the board.
     */
    public Iterable<Board> neighbors() {

        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {

                final ArrayDeque<short[]> neighbors = new ArrayDeque<>(4);

                if (zero[0] > 0 && !Arrays.equals(new short[] { 1, 0 }, fromMove)) {
                    neighbors.add(new short[] { -1, 0 });
                }
                if (zero[1] > 0 && !Arrays.equals(new short[] { 0, 1 }, fromMove)) {
                    neighbors.add(new short[] { 0, -1 });
                }
                if (zero[0] < n - 1 && !Arrays.equals(new short[] { -1, 0 }, fromMove)) {
                    neighbors.add(new short[] { 1, 0 });
                }
                if (zero[1] < n - 1 && !Arrays.equals(new short[] { 0, -1 }, fromMove)) {
                    neighbors.add(new short[] { 0, 1 });
                }

                return new Iterator<Board>() {

                    @Override
                    public boolean hasNext() {
                        return !neighbors.isEmpty();
                    }

                    @Override
                    public Board next() {
                        short[] move = neighbors.poll();
                        short[][] tilesCopy = makeClone(tiles, n);
                        short oldVal = tilesCopy[move[0] + zero[0]][move[1] + zero[1]];

                        // exchange zero with another position
                        tilesCopy[zero[0]][zero[1]] = oldVal;
                        tilesCopy[move[0] + zero[0]][move[1] + zero[1]] = 0;

                        // update information
                        int oldManhattan = internalManhattan(move[0] + zero[0], move[1] + zero[1], oldVal, n);
                        int newManhattan = internalManhattan(zero[0], zero[1], oldVal, n);
                        short newHamming = (short) (Math.signum(newManhattan) - Math.signum(oldManhattan));
                        newHamming += hammingDistance;
                        return new Board(tilesCopy, newHamming, newManhattan - oldManhattan + manhattanDistance,
                                new short[] { (short) (move[0] + zero[0]), (short) (move[1] + zero[1]) }, move);
                    }
                };
            }
        };
    }

    /**
     * return the twin board from the twin equivalent class.
     */
    public Board twin() {
        short[][] twoTiles = new short[2][2];
        int pos = 0;
        short[][] tilesCopy = makeClone(tiles, n);
        loop1: for (short i = 0; i < n; i++) {
            for (short j = 0; j < n; j++) {
                if (i != zero[0] || j != zero[1]) {
                    twoTiles[pos++] = new short[] { i, j };
                }
                if (pos >= 2) {
                    break loop1;
                }
            }
        }

        short val0 = tiles[twoTiles[0][0]][twoTiles[0][1]];
        short val1 = tiles[twoTiles[1][0]][twoTiles[1][1]];

        // exchange values
        tilesCopy[twoTiles[0][0]][twoTiles[0][1]] = val1;
        tilesCopy[twoTiles[1][0]][twoTiles[1][1]] = val0;

        // update information
        int oldManhattan0 = internalManhattan(twoTiles[0][0], twoTiles[0][1], val0, n);
        int oldManhattan1 = internalManhattan(twoTiles[1][0], twoTiles[1][1], val1, n);
        int newManhattan0 = internalManhattan(twoTiles[0][0], twoTiles[0][1], val1, n);
        int newManhattan1 = internalManhattan(twoTiles[1][0], twoTiles[1][1], val0, n);
        short newHamming = (short) (Math.signum(newManhattan0) + Math.signum(newManhattan1) - Math.signum(oldManhattan0)
                - Math.signum(newManhattan1));
        newHamming += hammingDistance;
        return new Board(tilesCopy, newHamming,
                newManhattan0 + newManhattan1 - oldManhattan0 - oldManhattan1 + manhattanDistance, zero, null);
    }

    private static final int internalManhattan(int row, int col, int val, int n) {
        if (val == 0) {
            // skip 0
            return 0;
        }
        return Math.abs((val - 1) / n - row) + Math.abs((val - 1) % n - col);
    }

    private static final short[][] makeClone(short[][] tiles, short n) {
        short[][] copy = new short[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }
}
