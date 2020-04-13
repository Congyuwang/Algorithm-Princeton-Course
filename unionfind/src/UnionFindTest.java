import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import princeton.algorithm.unionfind.WeightedQuickUnionUF;

class UnionFindTest {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(new File("data/tinyUF.txt")));
        int objectNumber = scanner.nextInt();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(objectNumber);
        while(scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
            }
        }
        scanner.close();
        scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        int q = scanner.nextInt();
        System.out.print(uf.connected(p, q));
        scanner.close();
    }
}
