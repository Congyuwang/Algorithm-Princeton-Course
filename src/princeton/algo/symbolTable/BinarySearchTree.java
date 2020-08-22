package princeton.algo.symbolTable;

import princeton.algo.queue.ArrayQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code BinarySearchTree} class implements and ordered symbol table using
 * binary search tree data structure. Most of the methods are implemented using
 * recursive functions, mainly for the advantage of being able to extend to more
 * advanced tree structure.
 *
 * @param <K> the key type, must be comparable to itself
 * @param <V> the value type
 */
public class BinarySearchTree<K extends Comparable<? super K>, V> implements OrderedSymbolTable<K, V> {

    private Node root;
    private Node cache;

    private class Node  {
        final K key;
        V value;
        Node left;
        Node right;
        int count;

        public Node(K key, V value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }
    }

    /**
     * Put a new key value pair into the Symbol table. This API does not allow
     * putting null as values.
     *
     * @param key   A comparable key
     * @param value A value stored
     * @throws NullPointerException if key or value is null
     */
    @Override
    public void put(K key, V value) throws NullPointerException {
        if (key == null) throw new NullPointerException("null key");
        if (value == null) throw new NullPointerException("null value unsupported");
        // cache is useful for operations like put(k, get(k) + 1)
        if (cache != null && key.equals(cache.key)) {
            cache.value = value;
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            cache = new Node(key, value, 1);
            return cache;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
            cache = node;
        }
        // use recursion to calculate the size of subtrees in the path
        node.count = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * Obtain the value for the provided key
     *
     * @param key the key to search for
     * @return returns null if value not found
     * @throws NullPointerException if key is null
     */
    @Override
    public V get(K key) throws NullPointerException {
        if (key == null) throw new NullPointerException("null key");
        if (cache != null && key.equals(cache.key)) return cache.value;
        Node getNode = get(root, key);
        if (getNode != null) cache = getNode;
        return getNode == null ? null : getNode.value;
    }

    private Node get(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return get(node.left, key);
        if (cmp > 0) return get(node.right, key);
        return node;
    }

    /**
     * Delete a key and its value
     *
     * @param key the key to delete
     * @throws NullPointerException if key is null
     */
    @Override
    public void delete(K key) throws NullPointerException {
        if (key == null) throw new NullPointerException("null key");
        root = delete(root, key);
        if (cache != null && key.equals(cache.key)) {
            // clear cache to ensure no link to deleted node
            cache = null;
        }
    }

    private Node delete(Node node, K key) {
        assert key != null;
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            node.right = delete(node.right, key);
        } else if (cmp < 0) {
            node.left = delete(node.left, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node temp = min(node.right);
            temp.right = deleteMin(node.right);
            temp.left = node.left;
            node = temp;
        }
        node.count = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * Delete the smallest key if the table is not empty
     *
     * @throws NoSuchElementException if the table is empty
     */
    @Override
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("empty table");
        root = deleteMin(root);
        // clear cache to ensure no link to deleted node
        cache = null;
    }

    private Node deleteMin(Node node) {
        assert node != null;
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.count = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * Delete the largest key if the table is not empty
     *
     * @throws NoSuchElementException if the table is empty
     */
    @Override
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("empty table");
        root = deleteMax(root);
        // clear cache to ensure no link to deleted node
        cache = null;
    }

    private Node deleteMax(Node node) {
        assert node != null;
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.count = size(node.right) + size(node.left) + 1;
        return node;
    }

    /**
     * Get the number of elements in the Symbol Table
     *
     * @return the number of elements in the table
     */
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.count;
    }

    /**
     * Get the smallest key.
     *
     * @return the smallest key
     * @throws NoSuchElementException if the table is empty
     */
    @Override
    public K min() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException("empty table");
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) {
            // cache here can be useful for get(min()) or put(max(), x)
            cache = node;
            return node;
        }
        return min(node.left);
    }

    /**
     * Get the largest key.
     *
     * @return the largest key
     * @throws NoSuchElementException if the table is empty
     */
    @Override
    public K max() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException("empty table");
        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) {
            // cache here can be useful for get(max()) or put(max(), x)
            cache = node;
            return node;
        }
        return max(node.right);
    }

    /**
     * The largest key smaller than or equal to the given key
     *
     * @param key the given key
     * @return the floor key found
     * @throws NullPointerException   if the key is null
     * @throws NoSuchElementException if there is no such key, or the table is empty
     */
    @Override
    public K floor(K key) throws NullPointerException, NoSuchElementException {
        if (key == null) throw new NullPointerException("null key");
        if (isEmpty()) throw new NoSuchElementException("empty table");
        Node floorNode = floor(root, key);
        if (floorNode == null) throw new NoSuchElementException("no floor key");
        // cache here can be useful for get(floor()) or put(floor(), x)
        cache = floorNode;
        return floorNode.key;
    }

    private Node floor(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return floor(node.left, key);
        if (cmp == 0) return node;
        Node rightFloor = floor(node.right, key);
        if (rightFloor == null) return node;
        return rightFloor;
    }

    /**
     * The smallest key greater than or equal to the given key
     *
     * @param key the given key
     * @return the key found
     * @throws NullPointerException   if the key is null
     * @throws NoSuchElementException if there is no such key, or the table is empty
     */
    @Override
    public K ceiling(K key) throws NullPointerException, NoSuchElementException {
        if (key == null) throw new NullPointerException("null key");
        if (isEmpty()) throw new NoSuchElementException("empty table");
        Node ceilingNode = ceiling(root, key);
        if (ceilingNode == null) throw new NoSuchElementException("no ceiling key");
        // cache here can be useful for get(ceiling()) or put(ceiling(), x)
        cache = ceilingNode;
        return ceilingNode.key;
    }

    private Node ceiling(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp > 0) return ceiling(node.right, key);
        if (cmp == 0) return node;
        Node leftFloor = ceiling(node.left, key);
        if (leftFloor == null) return node;
        return leftFloor;
    }

    /**
     * Select the key ranking the number rank.
     *
     * @param rank the number of rank
     * @return the key of that rank
     * @throws IllegalArgumentException if the rank is out bound
     * @throws NoSuchElementException   if the table is empty
     */
    @Override
    public K select(int rank) throws IllegalArgumentException, NoSuchElementException {
        if (rank < 0 || rank >= size()) {
            if (isEmpty()) throw new NoSuchElementException("empty table");
            throw new IllegalArgumentException("rank out of bound");
        }
        Node selectNode = select(root, rank);
        // cache here can be useful for get(select(n)) or put(select(n), xx)
        cache = selectNode;
        return selectNode.key;
    }

    private Node select(Node node, int rank) {
        assert node != null;
        int leftSize = size(node.left);
        if (leftSize == rank) return node;
        if (leftSize < rank) return select(node.right, rank - leftSize - 1);
        return select(node.left, rank);
    }

    /**
     * The number of keys less than the given key
     *
     * @param key the given key
     * @return the number of keys less than {@code key}
     * @throws NullPointerException if key is null
     */
    @Override
    public int rank(K key) throws NullPointerException {
        if (key == null) throw new NullPointerException("null key");
        return rank(root, key);
    }

    private int rank(Node node, K key) {
        if (node == null) return 0;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return size(node.left);
        if (key.compareTo(node.key) < 0) return rank(node.left, key);
        return 1 + rank(node.right, key) + size(node.left);
    }

    /**
     * Return an iterable object of keys
     *
     * @return an iterator containing the keys of the SymbolTable
     */
    @Override
    public Iterable<K> keys() {
        ArrayQueue<K> queue = new ArrayQueue<>(size());
        inorder(root, queue);
        return queue;
    }

    private void inorder(Node node, ArrayQueue<K> queue) {
        if (node == null) return;
        inorder(node.left, queue);
        queue.enqueue(node.key);
        inorder(node.right, queue);
    }

    /**
     * {@code inPlaceKeys()} uses Morris Traversal, which
     * is generally half the speed of the recursive method
     * used by {@code keys()}. However, {@code inPlaceKeys()}
     * uses zero extra memory. Another drawback of this method
     * is that, the user <em>should not modify the tree during
     * iteration</em>; otherwise the iterator
     * <em>WILL CRASH</em>.
     * <p>
     *     Morris Traversal:
     * </p>
     * this is an alternative for the inorder() recursive way.
     * <p>
     *     In short, create links from the rightmost end of
     *     every left subtree to each mother. Use these links
     *     to return to mother node, when the left subtree is
     *     all read. Once returned, the previous return link
     *     indicates the pointer to read the mother node, and
     *     move to the right subtree, and after this, remove
     *     the link from the rightmost end to mother node.
     *     The same then continues for the right subtree.
     *     The whole process ends when the pointer arrives at
     *     a node which belongs to nobody's left subtree
     *     (i.e. the max node), which as no return, and moves
     *     right to null.
     * </p>
     *
     * @return an iterable of keys
     */
    public Iterable<K> inPlaceKeys() {
        return MorrisTraversal::new;
    }

    private class MorrisTraversal implements Iterator<K> {
        Node current = root;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException("called next() but no more element");
            }
            while (true) {
                if (current.left == null) {
                    K toReturn = current.key;
                    current = current.right;
                    return toReturn;
                } else {
                    // whenever there is left subtree, check whether
                    // return links has been created using while loop
                    Node temp = current.left;
                    while (temp.right != null && temp.right != current) {
                        temp = temp.right;
                    }
                    if (temp.right == null) {
                        // if the return link is not created, create link to return
                        // and moves left until the smallest element of this subtree
                        // (i.e. current.left == null), and then starts to move right
                        temp.right = current;
                        current = current.left;
                    } else {
                        // if the return link has been created
                        // remove the link, and continue to read right up
                        temp.right = null;
                        K toReturn = current.key;
                        current = current.right;
                        return toReturn;
                    }
                }
            }
        }
    }

    /**
     * Return an iterable from lo to hi
     *
     * @param lo the lower bound, inclusive
     * @param hi the upper bound, inclusive
     * @return the iterator iterating from lo to hi
     * @throws NullPointerException if any key is null
     */
    @Override
    public Iterable<K> keys(K lo, K hi) throws NullPointerException {
        if (lo == null) throw new NullPointerException("key lo is null");
        if (hi == null) throw new NullPointerException("key hi is null");
        ArrayQueue<K> queue = new ArrayQueue<>();
        inorder(root, lo, hi, queue);
        return queue;
    }

    private void inorder(Node node, K lo, K hi, ArrayQueue<K> queue) {
        if (node == null) return;
        int cmpLo = node.key.compareTo(lo);
        int cmpHi = node.key.compareTo(hi);
        if (cmpLo > 0) inorder(node.left, lo, hi, queue);
        if (cmpLo >= 0 && cmpHi <= 0) queue.enqueue(node.key);
        if (cmpHi < 0) inorder(node.right, lo, hi, queue);
    }
}
