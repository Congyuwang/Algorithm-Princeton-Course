package princeton.algo.unionfind;
// This algorithm uses N + M lg N (almost linear time)

public class WeightedUF {
    private final int[] id;
    private final int[] sz;

    public WeightedUF(int N) {
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
        /*
         we keep records of the size of each root
         this reduces root depth to lg N.
        */
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
