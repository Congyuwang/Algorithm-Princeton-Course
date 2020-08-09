package assignments.eightPuzzle;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * Solve eightPuzzle problem using the A* search algorithm! The boards are
 * usually small. So the operation time of Boards class is not of a big concern,
 * <em>unless it is used by the priority Queue for comparison</em>. However, the
 * critical values are already cached for faster comparison. The biggest
 * performance bottleneck of A* search algorithm is the memory it uses. It has
 * to store all the search nodes it generated.
 */
public class Solver {

    private static final Comparator<Node> MANHATTAN_PRIORITY = (o1, o2) -> o1.steps + o1.manhattan - o2.steps - o2.manhattan;

    private final boolean isSolvable;
    private final int moves;
    private Node lastNode;

    /**
     * Solve eightPuzzle problem using the A* search algorithm! the main algorithm
     * is in the constructor. Solve in turn the twin board and this board. Exactly
     * one of them will be solvable.
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial board cannot be null");
        }
        Board twin = initial.twin();
        MinPQ<Node> searchQueue = new MinPQ<>(MANHATTAN_PRIORITY);
        MinPQ<Node> searchQueueTwin = new MinPQ<>(MANHATTAN_PRIORITY);

        // A* algorithm main loop
        searchQueue.insert(new Node(initial, null, initial.manhattan(), 0));
        searchQueueTwin.insert(new Node(twin, null, twin.manhattan(), 0));
        while (true) {

            // dequeue the smallest node
            Node thisNode = searchQueue.delMin();

            // if thisNode reaches the goal, the puzzle is solved
            if (thisNode.b.isGoal()) {
                this.lastNode = thisNode;
                this.moves = thisNode.steps;
                this.isSolvable = true;
                // lastNode is reversed to root if solution found
                lastNode = reverse(lastNode);
                return;
            }

            // add neighbors to the priorityQueue
            if (thisNode.previousNode == null) {
                for (Board b : thisNode.b.neighbors()) {
                    searchQueue.insert(new Node(b, thisNode, b.manhattan(), thisNode.steps + 1));
                }
            } else {
                for (Board b : thisNode.b.neighbors()) {
                    if (!thisNode.previousNode.b.equals(b)) {
                        searchQueue.insert(new Node(b, thisNode, b.manhattan(), thisNode.steps + 1));
                    }
                }
            }

            // dequeue the smallest node
            Node thisNodeTwin = searchQueueTwin.delMin();

            // if twin is solvable, then this board is unsolvable
            if (thisNodeTwin.b.isGoal()) {
                this.lastNode = null;
                this.moves = -1;
                this.isSolvable = false;
                return;
            }

            // add neighbors to the priorityQueue of Twin
            if (thisNodeTwin.previousNode == null) {
                for (Board b : thisNodeTwin.b.neighbors()) {
                    searchQueueTwin.insert(new Node(b, thisNodeTwin, b.manhattan(), thisNodeTwin.steps + 1));
                }
            } else {
                for (Board b : thisNodeTwin.b.neighbors()) {
                    if (!thisNodeTwin.previousNode.b.equals(b)) {
                        searchQueueTwin.insert(new Node(b, thisNodeTwin, b.manhattan(), thisNodeTwin.steps + 1));
                    }
                }
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
            return () -> new Iterator<>() {
                Node current = new Node(null, lastNode, 0, 0);

                @Override
                public boolean hasNext() {
                    return current.previousNode != null;
                }

                @Override
                public Board next() {
                    current = current.previousNode;
                    if (current == null) {
                        throw new NoSuchElementException("iterator overrun");
                    }
                    return current.b;
                }
            };
        }
        return null;
    }

    /**
     * Reverse the order of the solution from (last -> root) to (root -> last).
     * Called <em>only if the solution is found</em>.
     *
     * @param lastNode provide the lastNode pointer
     * @return return the root node.
     */
    private static Node reverse(Node lastNode) {
        assert lastNode != null;
        Node progress = lastNode;
        Node memory = lastNode.previousNode;
        lastNode.previousNode = null;
        while (memory != null) {
            // save the next next node
            Node temp = memory.previousNode;
            // reverse the reference
            memory.previousNode = progress;
            // move on to the next step
            progress = memory;
            memory = temp;
        }
        assert progress.steps == 0;
        return progress;
    }

    private static final class Node {
        public final Board b;
        public final int manhattan;
        public final int steps;
        public Node previousNode;

        Node(Board b, Node previousNode, int manhattan, int steps) {
            this.b = b;
            this.previousNode = previousNode;
            this.manhattan = manhattan;
            this.steps = steps;
        }
    }

    /**
     * test client
     */
    public static void main(String[] args) {

        // create initial board from file
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
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
