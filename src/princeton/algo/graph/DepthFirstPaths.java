package princeton.algo.graph;

import princeton.algo.queue.Deque;
import princeton.algo.stack.Stack;

public class DepthFirstPaths {

    private final boolean[] marked;
    private final int[] edgeTo;
    private final int s;

    public DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
                // edgeTo records how we got to a point
                edgeTo[w] = v;
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
