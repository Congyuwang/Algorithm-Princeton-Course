package princeton.algo.graph;

import edu.princeton.cs.algs4.Bag;

public class Graph {
    private final int V;
    private final Bag<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        this.adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            this.adj[v] = new Bag<>();
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }
}
