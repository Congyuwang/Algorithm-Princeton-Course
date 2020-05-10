package princeton.algo.unionfind;
// This algorithm uses N + M lg N

public class PathCompressionUF {
    private final int[] id;

    public PathCompressionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    private int root(int q) {
        while (id[q] != q) {
            // Path compression
            id[q] = id[id[q]];
            q = id[q];
        }
        return q;
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[j] = i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
}
