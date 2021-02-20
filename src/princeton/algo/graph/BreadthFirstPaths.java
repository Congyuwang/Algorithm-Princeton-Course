package princeton.algo.graph;

import princeton.algo.queue.Deque;
import princeton.algo.stack.Stack;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Breadth first paths give the shortest paths
 */
public class BreadthFirstPaths {

    private final boolean[] marked;
    private final int[] edgeTo;
    private final int s;

    public BreadthFirstPaths(Graph G, int s) {
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int v) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(v);
        marked[v] = true;
        while (!queue.isEmpty()) {
            int r = queue.poll();
            for (int k : G.adj(r)) {
                if (!marked[k]) {
                    queue.add(k);
                    marked[k] = true;
                    edgeTo[k] = s;
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Deque<>();
        while (edgeTo[v] != s) {
            path.push(edgeTo[v]);
            v = edgeTo[v];
        }
        path.push(s);
        return path;
    }
}
