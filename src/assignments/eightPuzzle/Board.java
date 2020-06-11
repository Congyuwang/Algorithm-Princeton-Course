package assignments.eightPuzzle;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    private final short[][] tiles;

    /**
     * Create a board, compress integer to short. The constructor takes O(n^2) time.
     */
    public Board(int[][] tiles) {
        final int n = tiles.length;
        if (n < 2 || n != tiles[0].length) {
            throw new IllegalArgumentException("illegal board shape");
        }
        this.tiles = new short[n][n];
        for (short i = 0; i < n; i++) {
            for (short j = 0; j < n; j++) {
                this.tiles[i][j] = (short) tiles[i][j];
            }
        }
    }

    /**
     * Note that the private constructor does not make a deeper copy of tiles
     */
    private Board(short[][] tiles) {
        this.tiles = tiles.clone();
    }

    /**
     * return the size (height) of the board.
     *
     * @return the height of the square board
     */
    public int dimension() {
        return tiles.length;
    }

    /**
     * calculate the hamming distance (the number of tiles out of place) between
     * this board and the goal board. O(n^2) time.
     *
     * @return the hamming distance
     */
    public int hamming() {
        short hamming = -1;
        int pos = 1;
        for (short[] row : tiles) {
            for (short s : row) {
                if (s != pos) {
                    hamming++;
                }
                pos++;
            }
        }
        return hamming;
    }

    /**
     * calculate the manhattan distance (the sum of manhattan distances of each tile
     * to its goal position) between this board and the goal board. O(n^2) time.
     *
     * @return the manhattan distance
     */
    public int manhattan() {
        int manhattan = 0;
        final int n = tiles.length;
        for (short i = 0; i < n; i++) {
            for (short j = 0; j < n; j++) {
                manhattan += internalManhattan(i, j, this.tiles[i][j], n);
            }
        }
        return manhattan;
    }

    /**
     * check whether this board is the goal board. O(n^2) time.
     */
    public boolean isGoal() {
        int pos = 1;
        for (short[] row : tiles) {
            for (short s : row) {
                if (s != pos && s != 0) {
                    return false;
                }
                pos++;
            }
        }
        return true;
    }

    /**
     * check whether this board equals to another board. O(n^2) time.
     */
    @Override
    public boolean equals(Object y) {
        if (y == null || !(this.getClass() == y.getClass())) {
            return false;
        }
        final int n = tiles.length;
        final Board b = (Board) y;
        final int height = b.tiles.length;
        if (height != n || b.tiles[0].length != n) {
            return false;
        }
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
     * return a iterator giving all neighbors of a board. O(n^2) time.
     */
    public Iterable<Board> neighbors() {
        final int n = tiles.length;
        final short[] zero = findZero(tiles, n);
        final ArrayDeque<short[]> neighbors = new ArrayDeque<>(4);
        if (zero[0] > 0)
            neighbors.add(new short[] { -1, 0 });
        if (zero[1] > 0)
            neighbors.add(new short[] { 0, -1 });
        if (zero[0] < n - 1)
            neighbors.add(new short[] { 1, 0 });
        if (zero[1] < n - 1)
            neighbors.add(new short[] { 0, 1 });
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
                        if (neighbors.isEmpty()) throw new NoSuchElementException("iterator overrun");
                        short[] move = neighbors.poll();
                        short[][] tilesCopy = makeClone(tiles, n);
                        tilesCopy[zero[0]][zero[1]] = tilesCopy[move[0] + zero[0]][move[1] + zero[1]];
                        tilesCopy[move[0] + zero[0]][move[1] + zero[1]] = 0;
                        return new Board(tilesCopy);
                    }
                };
            }
        };
    }

    /**
     * return the twin board from the twin equivalent class.
     */
    public Board twin() {
        final int n = tiles.length;
        final short[][] twoTiles = new short[2][2];
        final short[] zero = findZero(tiles, n);
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

        // exchange values
        short val0 = tiles[twoTiles[0][0]][twoTiles[0][1]];
        tilesCopy[twoTiles[0][0]][twoTiles[0][1]] = tilesCopy[twoTiles[1][0]][twoTiles[1][1]];
        tilesCopy[twoTiles[1][0]][twoTiles[1][1]] = val0;

        return new Board(tilesCopy);
    }

    /**
     * return a string representation of the Board
     */
    @Override
    public String toString() {
        final int n = tiles.length;
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

    private static int internalManhattan(int row, int col, int val, int n) {
        if (val == 0) {
            // skip 0
            return 0;
        }
        return Math.abs((val - 1) / n - row) + Math.abs((val - 1) % n - col);
    }

    private static short[][] makeClone(short[][] tiles, int n) {
        short[][] copy = new short[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    private static short[] findZero(short[][] tiles, int n) {
        short[] temp = null;
        loop: for (short i = 0; i < n; i++) {
            for (short j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    temp = new short[] { i, j };
                    break loop;
                }
            }
        }
        return temp;
    }
}
