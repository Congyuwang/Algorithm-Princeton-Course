package assignments.eightPuzzle;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * Solve eightPuzzle problem using the A* search algorithm!
 */
public class Solver {

    private final boolean isSolvable;
    private final int moves;
    private Node lastNode;
    private static final Comparator<Node> manhattanPriority = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.steps + o1.b.manhattan() - o2.steps - o2.b.manhattan();
        }
    };

    /**
     * Solve eightPuzzle problem using the A* search algorithm! the main algorithm
     * is in the constructor.
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial board cannot be null");
        }
        Board twin = initial.twin();
        MinPQ<Node> searchQueue = new MinPQ<>(manhattanPriority);
        MinPQ<Node> searchQueueTwin = new MinPQ<>(manhattanPriority);
        searchQueue.insert(new Node(initial, null, 0));
        searchQueueTwin.insert(new Node(twin, null, 0));
        Node thisNode;
        Node thisNodeTwin;
        while (true) {

            // dequeue the smallest node
            thisNode = searchQueue.delMin();
            thisNodeTwin = searchQueueTwin.delMin();

            // check if thisNode is the goal
            if (thisNode.b.isGoal()) {
                this.lastNode = thisNode;
                this.moves = thisNode.steps;
                this.isSolvable = true;
                // lastNode is reversed to root if solution found
                lastNode = reverse(lastNode);
                return;
            }

            // if twin is solvable, then this board is unsolvable
            if (thisNodeTwin.b.isGoal()) {
                this.lastNode = null;
                this.moves = -1;
                this.isSolvable = false;
                return;
            }

            // add neighbors to the priorityQueue
            for (Board b : thisNode.b.neighbors()) {
                searchQueue.insert(new Node(b, thisNode, thisNode.steps + 1));
            }
            for (Board b : thisNodeTwin.b.neighbors()) {
                searchQueueTwin.insert(new Node(b, thisNodeTwin, thisNode.steps + 1));
            }
        }
    }

    /**
     * return a boolean indicating whether this board is solvable.
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * The minimum number of steps to solve the board.
     *
     * @return -1 if not solvable.
     */
    public int moves() {
        return moves;
    }

    /**
     * return the steps to solve the puzzle
     *
     * @return null if not solvable
     */
    public Iterable<Board> solution() {
        if (isSolvable) {
            return new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new Iterator<Board>() {

                        Node current = new Node(null, lastNode, 0);

                        @Override
                        public boolean hasNext() {
                            return current.previousNode != null;
                        }

                        @Override
                        public Board next() {
                            current = current.previousNode;
                            return current.b;
                        }

                    };
                }
            };
        }
        return null;
    }

    /**
     * Reverse the order of the solution from (last -> root) to (root -> last).
     * Called <em>only if the solution is found</em>.
     *
     * @param lastNode
     * @return return the root node.
     */
    private static final Node reverse(Node lastNode) {
        assert lastNode != null;
        Node progress = lastNode;
        Node memory = lastNode.previousNode;
        lastNode.previousNode = null;
        while (memory != null) {
            Node temp = memory.previousNode;
            memory.previousNode = progress;
            progress = memory;
            memory = temp;
        }
        assert progress.steps == 0;
        return progress;
    }

    private static final class Node {
        Node(Board b, Node previousNode, int steps) {
            this.b = b;
            this.previousNode = previousNode;
            this.steps = steps;
        }

        Board b;
        Node previousNode;
        int steps;
    }

    /**
     * test client
     */
    public static void main(String[] args) {

        // create initial board from file
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
        Board initial = new Board(tiles);

        System.out.println(initial);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
