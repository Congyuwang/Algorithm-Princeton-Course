package assignments.eightPuzzle;

import java.util.Scanner;

import edu.princeton.cs.algs4.In;

/**
 * unit testing for Board.jva
 */
public class BoardTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        In in = new In(scanner.next());
        scanner.close();
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        System.out.println("isGoal:\n" + board.isGoal()); // true
        System.out.println("manhattan:\n" + board.manhattan()); // 4
        System.out.println("hamming:\n" + board.hamming()); // 3
        System.out.println("twin:\n" + board.twin());
        System.out.println("twin manhattan:\n" + board.twin().manhattan());
        System.out.println("twin hamming:\n" + board.twin().hamming());
        for (Board b : board.neighbors()) {
            System.out.print("neighbors:\n" + b);
            System.out.println("neighbors manhattan: " + b.manhattan());
            System.out.println("neighbors hamming: " + b.hamming());
            for (Board bb : b.neighbors()) {
                System.out.println("neighbors' neighbors :\n" + bb);
                System.out.println("neighbors' neighbors manhattan: " + bb.manhattan());
                System.out.println("neighbors' neighbors hamming: " + bb.hamming());
            }
        }
    }
}
