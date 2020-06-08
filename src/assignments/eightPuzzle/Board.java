package assignments.eightPuzzle;

import java.util.Iterator;

public class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles.clone();
        this.n = tiles.length;
    }

    // string representation of this board
    public String toString() {
        final int padding = 2 + (int) Math.max(Math.floor(Math.log10(n * n)), 0);
        final String digitFormat = String.format("%%dd", padding);
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

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = -1;
        int pos = 1;
        for (int[] row : tiles) {
            for (int i : row) {
                if (i != pos++) {
                    count++;
                }
            }
        }
        return count;
    }

    private int manhattan(int row, int col, int val) {
        if (val == 0) {
            return 0;
        }
        return Math.abs((val - 1) / n - row) + Math.abs((val - 1) % n - col);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                manhattan += manhattan(i, j, tiles[i][j]);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
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

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {

			@Override
			public Iterator<Board> iterator() {
				return new Iterator<Board>() {
                    @Override
                    public boolean hasNext() {
                        // TODO Auto-generated method stub
                        return false;
                    }
                    @Override
                    public Board next() {
                        // TODO Auto-generated method stub
                        return null;
                    }
                };
			}
        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return new Board(new int[][]{{}});
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
