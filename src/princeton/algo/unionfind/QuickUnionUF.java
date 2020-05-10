package princeton.algo.unionfind;

public class QuickUnionUF {
    private final int[] id;
    private final int[] sz;

    public QuickUnionUF(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    private int root(int q) {
        while (id[q] != q) {
            q = id[q];
        }
        return q;
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (sz[i] > sz[j]) {
            id[j] = i;
            sz[i] += sz[j];
        } else {
            id[i] = j;
            sz[j] += sz[i];
        }
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
}
