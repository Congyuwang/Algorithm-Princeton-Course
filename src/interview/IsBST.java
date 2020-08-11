package interview;

/**
 * This class contains a method to check whether a tree
 * is a binary search tree.
 */
public class IsBST<K extends Comparable<? super K>> {

    public boolean isBST(Node<K> root) {
        return minMaxKey(root).valid;
    }

    private class minMaxKey {
        final K min;
        final K max;
        final boolean valid;

        public minMaxKey(K min, K max, boolean valid) {
            this.min = min;
            this.max = max;
            this.valid = valid;
        }
    }

    private minMaxKey minMaxKey(Node<K> node) {
        if (node == null) {
            return new minMaxKey(null, null, true);
        }

        minMaxKey left = minMaxKey(node.left);
        if (!left.valid) return new minMaxKey(null, null, false);
        if (left.max != null && left.max.compareTo(node.key) >= 0) {
            return new minMaxKey(null, null, false);
        }

        minMaxKey right = minMaxKey(node.right);
        if (!right.valid) return new minMaxKey(null, null, false);
        if (right.min != null && right.min.compareTo(node.key) <= 0) {
            return new minMaxKey(null, null, false);
        }

        return new minMaxKey(takeK2(node.key, left.min), takeK2(node.key, right.max), true);
    }

    // return k2 if k2 is not null
    private K takeK2(K k1, K k2) {
        return k2 == null ? k1 : k2;
    }

    // test client
    public static void main(String[] args) {
        IsBST<Double> doubleBstChecker = new IsBST<>();

        // first test: num5 as root, not a BST
        Node<Double> num1 = new Node<>(1d);
        Node<Double> num2 = new Node<>(2d);
        Node<Double> num3 = new Node<>(3d);
        Node<Double> num4 = new Node<>(4d);
        Node<Double> num5 = new Node<>(5d);
        Node<Double> num7 = new Node<>(7d);
        num5.left = num4;
        num4.left = num2;
        num2.left = num1;
        num4.right = num3;
        num5.right = num7;
        System.out.println(doubleBstChecker.isBST(num5));

        // second test: num10 as root, is a BST
        num1 = new Node<>(1d);
        num2 = new Node<>(2d);
        num3 = new Node<>(3d);
        Node<Double> num10 = new Node<>(10d);
        Node<Double> num12 = new Node<>(12d);
        Node<Double> num13 = new Node<>(13d);
        Node<Double> num14 = new Node<>(14d);
        Node<Double> num15 = new Node<>(15d);
        Node<Double> num16 = new Node<>(16d);
        num10.left = num2;
        num10.right = num16;
        num2.left = num1;
        num2.right = num3;
        num16.left = num12;
        num12.right = num13;
        num13.right = num14;
        num14.right = num15;
        System.out.println(doubleBstChecker.isBST(num10));
    }
}

class Node<K> {
    K key;
    Node<K> left;
    Node<K> right;

    public Node(K key) {
        this.key = key;
    }
}
