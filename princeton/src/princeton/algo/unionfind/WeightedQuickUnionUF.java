package princeton.algo.unionfind;
// This algorithm uses N + M lg* N (almost linear time)

public class WeightedQuickUnionUF {
    private int[] id;
    private int[] sz;

    public WeightedQuickUnionUF(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int find(int q) {
        while (id[q] != q) {
            // Path compression
            id[q] = id[id[q]];
            q = id[q];
        }
        return q;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
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

    public void printID() {
        for (int value : id) {
            System.out.print(value + "\t");
        }
        System.out.println();
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
