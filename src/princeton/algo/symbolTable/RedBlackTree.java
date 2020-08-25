package princeton.algo.symbolTable;

import princeton.algo.queue.ArrayQueue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code RedBlackTree} class is a classical implementation of 2-3 tree
 * invented by Robert Sedgewick that does all the operations in log time.
 * The implementation only needs little change from {@link BinarySearchTree}.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class RedBlackTree<K extends Comparable<? super K>, V> implements OrderedSymbolTable<K, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;
    private Node cache;

    private int modCount;

    private class Node extends Pair<K, V> {
        Node left;
        Node right;
        boolean color;
        int count;

        public Node(K key, V value, boolean color, int count) {
            super(key, value);
            this.color = color;
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
            cache = new Node(key, value, RED, 1);
            modCount++;
            return cache;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = put(node.left, key, value);
        else if (cmp > 0) node.right = put(node.right, key, value);
        else {
            node.value = value;
            cache = node;
        }

        /* Amazing Sedgewick Code */
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

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
     * Delete the smallest key if the table is not empty.
     * <p>
     *    The algorithm maintains the invariant that the current
     *    is always in a 3-node. Before moving to the next left node,
     *    if the next node is not in a 3-node, it tries to borrow
     *    from its right sibling when possible. When its right sibling
     *    is also a 2-node, it reverse-flip the color, by creating a
     *    4-node. Since the current node is always in a 3-node,
     *    and in the reverse-flipping case the current node color must
     *    be red (except possibly for root node), reverse-flipping does
     *    not change the black height of the tree.
     * </p>
     * @throws NoSuchElementException if the table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("the table is empty");
        modCount++;
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return null;
        if (!isRed(node.left) && !isRed(node.left.left)) node = borrowFromRightSibling(node);
        node.left = deleteMin(node.left);
        return balance(node);
    }

    /**
     * Delete the largest key if the table is not empty
     *
     * @throws NoSuchElementException if the table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("the table is empty");
        modCount++;
        root = deleteMax(root);
    }

    private Node deleteMax(Node node) {

        // This right rotation ensures that node.left is not RED.
        // Otherwise, the colorFlips in later parts erase node.left's color.
        if (isRed(node.left)) node = rotateRight(node);

        // the rest is symmetric to deleteMin
        if (node.right == null) return null;
        if (!isRed(node.right) && !isRed(node.right.left)) node = borrowFromLeftSibling(node);
        node.right = deleteMax(node.right);
        return balance(node);
    }

    private Node borrowFromRightSibling(Node node) {

        /* amazing Sedgewick code again */
        flipColorsReversed(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    private Node borrowFromLeftSibling(Node node) {

        /* amazing Sedgewick code again */
        flipColorsReversed(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    private Node balance(Node node) {
        if (isRed(node.right)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);
        node.count = size(node.left) + size(node.right) + 1;
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
        if (!contains(key)) return;
        root = delete(root, key);
    }

    private Node delete(Node node, K key) {
        if (node == null) return null;
        if (key.compareTo(node.key) < 0) {
            if (!isRed(node.left) && !isRed(node.left.left))
                node = borrowFromRightSibling(node);
            node.left = delete(node.left, key);
        } else {
            // choose cheap operation when possible
            if (isRed(node.left)) node = rotateRight(node);
            if (key.compareTo(node.key) == 0 && node.right == null) {
                modCount++;
                return null;
            }
            if (!isRed(node.right) && !isRed(node.right.left))
                node = borrowFromLeftSibling(node);
            if (key.compareTo(node.key) == 0) {
                modCount++;
                Node temp = min(node.right);
                temp.right = deleteMin(node.right);
                temp.left = node.left;
                temp.color = node.color;
                node = temp;
            }
            else node.right = delete(node.right, key);
        }
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

    /**
     * Return an iterable object of keys.
     * <p>
     * The current solution for iteration is one of the best for iterating trees.
     * This iterator allows for (non-concurrent) modification of tree structure.
     * It takes a 'snapshot' of the tree elements by enqueueing it into an Array.
     * The default implementation in java uses a {@code parent} pointer in nodes
     * for iteration, and also implements a fast-fail iterator. The memory cost
     * of the {@code ArrayQueue} used here equals to adding a parent pointer for
     * each node, but the extra memory cost here is temporary, and can be released
     * after the iteration is over.
     * </p>
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

    /* collections of basic operations */
    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    private Node rotateLeft(Node node) {
        Node newHead = node.right;
        assert newHead != null;
        node.right = newHead.left;
        newHead.left = node;
        newHead.color = node.color;
        node.color = RED;
        newHead.count = node.count;
        node.count = size(node.left) + size(node.right) + 1;
        return newHead;
    }

    private Node rotateRight(Node node) {
        Node newHead = node.left;
        assert newHead != null;
        node.left = newHead.right;
        newHead.right = node;
        newHead.color = node.color;
        node.color = RED;
        newHead.count = node.count;
        node.count = size(node.left) + size(node.right) + 1;
        return newHead;
    }

    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    private void flipColorsReversed(Node node) {
        node.color = BLACK;
        node.left.color = RED;
        node.right.color = RED;
    }

}
