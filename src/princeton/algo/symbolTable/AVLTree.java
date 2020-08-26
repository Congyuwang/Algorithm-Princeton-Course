package princeton.algo.symbolTable;

import princeton.algo.queue.ArrayQueue;

import java.util.NoSuchElementException;

/**
 * This class provides an implementation of AVL-tree that uses
 * mostly recursive methods.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class AVLTree<K extends Comparable<? super K>, V> implements OrderedSymbolTable<K, V> {

    private Node root;
    private Node cache;

    private class Node extends Pair<K, V>{
        private int height;
        private int count;
        private Node left;
        private Node right;

        public Node(K key, V value, int height, int count) {
            super(key, value);
            this.height = height;
            this.count = count;
        }
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
            cache = node;
            return node;
        }
        return max(node.right);
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
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.count = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
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
    }

    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.count = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
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
        Node result = floor(root, key);
        if (result == null) throw new NoSuchElementException("no floor key");
        cache = result;
        return result.key;
    }

    private Node floor(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp < 0) return floor(node.left, key);
        Node temp = floor(node.right, key);
        return temp == null ? node : temp;
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
        Node result = ceiling(root, key);
        if (result == null) throw new NoSuchElementException("no ceiling key");
        cache = result;
        return result.key;
    }

    private Node ceiling(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp > 0) return ceiling(node.right, key);
        Node temp = ceiling(node.left, key);
        return temp == null ? node : temp;
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
        if (isEmpty()) throw new NoSuchElementException("empty table");
        if (rank < 0 || rank >= size()) throw new IllegalArgumentException("rank out of range");
        cache = select(root, rank);
        return cache.key;
    }

    private Node select(Node node, int rank) {
        int leftSize = size(node.left);
        if (rank < leftSize) return select(node.left, rank);
        if (rank == leftSize) return node;
        return select(node.right, rank - leftSize - 1);
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
        if (cmp < 0) return rank(node.left, key);
        if (cmp == 0) return size(node.left);
        return rank(node.right, key) + 1 + size(node.left);
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

    /**
     * Return an iterable of pairs from lo to hi
     *
     * @param lo the lower bound, inclusive
     * @param hi the upper bound, inclusive
     * @return the iterator iterating key-value pairs from lo to hi
     * @throws NullPointerException if any key is null
     */
    @Override
    public Iterable<Pair<K, V>> pairs(K lo, K hi) throws NullPointerException {
        if (lo == null) throw new NullPointerException("key lo is null");
        if (hi == null) throw new NullPointerException("key hi is null");
        ArrayQueue<Pair<K, V>> queue = new ArrayQueue<>();
        inorderPair(root, lo, hi, queue);
        return queue;
    }

    private void inorderPair(Node node, K lo, K hi, ArrayQueue<Pair<K, V>> queue) {
        if (node == null) return;
        int cmpLo = node.key.compareTo(lo);
        int cmpHi = node.key.compareTo(hi);
        if (cmpLo > 0) inorderPair(node.left, lo, hi, queue);
        if (cmpLo >= 0 && cmpHi <= 0) queue.enqueue(node);
        if (cmpHi < 0) inorderPair(node.right, lo, hi, queue);
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
        if (cache != null && key.equals(cache.key)) {
            cache.value = value;
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            cache = new Node(key, value, 0, 1);
            return cache;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
            cache = node;
        } else if (cmp < 0) node.left = put(node.left, key, value);
        else node.right = put(node.right, key, value);
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node.count = size(node.left) + size(node.right) + 1;
        return balance(node);
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
            cache = null;
        }
    }

    private Node delete(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node minNode = min(node.right);
            minNode.right = deleteMin(node.right);
            minNode.left = node.left;
//            minNode.right = node.right;
            node = minNode;
        }
        node.count = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
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

    private int height(Node node) {
        if (node == null) return -1;
        return node.height;
    }

    private Node balance(Node node) {
        int balanceFactor = balanceFactor(node);
        if (balanceFactor > 1) {
            if (balanceFactor(node.left) < 0) node.left = rotateLeft(node.left);
            node = rotateRight(node);
        } else if (balanceFactor < -1) {
            if (balanceFactor(node.right) > 0) node.right = rotateRight(node.right);
            node = rotateLeft(node);
        }
        return node;
    }

    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node rotateRight(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.count = node.count;
        node.count = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));
        return temp;
    }

    private Node rotateLeft(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.count = node.count;
        node.count = 1 + size(node.left) + size(node.right);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));
        return temp;
    }

    /**
     * Return an iterable object of keys
     *
     * @return an iterable containing the keys of the SymbolTable
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
     * Return an iterable of key-value pairs ({@code Pair} object).
     *
     * @return an iterable of key-value {@code Pair}s.
     */
    @Override
    public Iterable<Pair<K, V>> pairs() {
        ArrayQueue<Pair<K, V>> queue = new ArrayQueue<>(size());
        inorderPairs(root, queue);
        return queue;
    }

    private void inorderPairs(Node node, ArrayQueue<Pair<K, V>> queue) {
        if (node == null) return;
        inorderPairs(node.left, queue);
        queue.enqueue(node);
        inorderPairs(node.right, queue);
    }

}
