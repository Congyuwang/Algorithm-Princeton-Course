package princeton.algo.binaryHeap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import princeton.algo.queue.Queue;
import princeton.algo.sort.Util;
import princeton.algo.stack.Stack;

public class PriorityQueue<T> implements Queue<T>, Stack<T> {

    private T[] heap;
    private int size;
    private final Comparator<T> comparator;

    /**
     * build a priority queue with natural ordering
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        comparator = null;
        heap = (T[]) new Object[1];
        size = 0;
    }

    /**
     * build a priority queue with a comparator
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue(Comparator<T> c) {
        comparator = c;
        heap = (T[]) new Object[1];
        size = 0;
    }

    /**
     * build a priority queue from an array with natural ordering
     */
    public PriorityQueue(T[] array) {
        comparator = null;
        heap = array.clone();
        size = array.length;
        for (int j = parent(size - 1); j >= 0; j--) {
            moveDown(j);
        }
    }

    /**
     * build a priority queue from an array with a comparator
     */
    public PriorityQueue(T[] array, Comparator<T> c) {
        comparator = c;
        heap = array.clone();
        size = array.length;
        for (int j = parent(size - 1); j >= 0; j--) {
            moveDown(j);
        }
    }

    /**
     * build a priority queue from a queue with natural ordering
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue(Queue<T> q) {
        comparator = null;
        heap = (T[]) new Object[q.size()];
        size = q.size();
        int pos = 0;
        for (T t : q) {
            assert t != null;
            heap[pos++] = t;
        }
        for (int j = parent(size - 1); j >= 0; j--) {
            moveDown(j);
        }
    }

    /**
     * build a priority queue from a queue with a comparator
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue(Queue<T> q, Comparator<T> c) {
        comparator = c;
        heap = (T[]) new Object[q.size()];
        size = q.size();
        int pos = 0;
        for (T t : q) {
            assert t != null;
            heap[pos++] = t;
        }
        for (int j = parent(size - 1); j >= 0; j--) {
            moveDown(j);
        }
    }

    /**
     * build a priority queue from a stack with natural ordering
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue(Stack<T> s) {
        comparator = null;
        heap = (T[]) new Object[s.size()];
        size = s.size();
        int pos = 0;
        for (T t : s) {
            assert t != null;
            heap[pos++] = t;
        }
        for (int j = parent(size - 1); j >= 0; j--) {
            moveDown(j);
        }
    }

    /**
     * build a priority queue from a stack with a comparator
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue(Stack<T> s, Comparator<T> c) {
        comparator = c;
        heap = (T[]) new Object[s.size()];
        size = s.size();
        int pos = 0;
        for (T t : s) {
            assert t != null;
            heap[pos++] = t;
        }
        for (int j = parent(size - 1); j >= 0; j--) {
            moveDown(j);
        }
    }

    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("null item encountered");
        }
        if (size == heap.length) {
            resize(size << 1);
        }
        heap[size] = item;
        moveUp(size++);
    }

    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("underflow!");
        }
        T temp = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;
        moveDown(0);
        if (size == heap.length >>> 2) {
            resize(heap.length >> 1);
        }
        return temp;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return heap[0];
    }

    private void moveUp(int k) {
        if (comparator == null) {
            while (k > 0) {
                @SuppressWarnings("unchecked")
                Comparable<? super T> child = (Comparable<? super T>) heap[k];
                int parent = parent(k);
                if (child.compareTo(heap[parent]) > 0) {
                    exch(k, parent);
                    k = parent;
                } else {
                    break;
                }
            }
        } else {
            while (k > 0) {
                T child = heap[k];
                int parent = parent(k);
                if (comparator.compare(child, heap[parent]) > 0) {
                    exch(k, parent);
                    k = parent;
                } else {
                    break;
                }
            }
        }
    }

    private void moveDown(int k) {
        moveDown(k, size - 1);
    }

    // package private
    void moveDown(int k, int limit) {
        if (comparator == null) {
            while (k <= parent(limit)) {
                @SuppressWarnings("unchecked")
                Comparable<? super T> parent = (Comparable<? super T>) heap[k];
                int leftChild = leftChild(k);
                int rightChild = leftChild + 1;
                if (parent.compareTo(heap[leftChild]) < 0) {
                    if (rightChild > limit) {
                        exch(k, leftChild);
                        k = leftChild;
                        continue;
                    }
                    @SuppressWarnings("unchecked")
                    Comparable<? super T> right = (Comparable<? super T>) heap[rightChild];
                    if (right.compareTo(heap[leftChild]) > 0) {
                        exch(k, rightChild);
                        k = rightChild;
                        continue;
                    }
                    exch(k, leftChild);
                    k = leftChild;
                } else if (rightChild <= limit && parent.compareTo(heap[rightChild]) < 0) {
                    exch(k, rightChild);
                    k = rightChild;
                } else {
                    break;
                }
            }
        } else {
            while (k <= parent(limit)) {
                T parent = heap[k];
                int leftChild = leftChild(k);
                int rightChild = leftChild + 1;
                if (comparator.compare(parent, heap[leftChild]) < 0) {
                    if (rightChild > limit) {
                        exch(k, leftChild);
                        k = leftChild;
                        continue;
                    }
                    T right = heap[rightChild];
                    if (comparator.compare(right, heap[leftChild]) > 0) {
                        exch(k, rightChild);
                        k = rightChild;
                        continue;
                    }
                    exch(k, leftChild);
                    k = leftChild;
                } else if (rightChild <= limit && comparator.compare(parent, heap[rightChild]) < 0) {
                    exch(k, rightChild);
                    k = rightChild;
                } else {
                    break;
                }
            }
        }
    }

    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] copy = (T[]) new Object[size];
        System.arraycopy(heap, 0, copy, 0, size);
        return copy;
    }

    private static final int parent(int k) {
        return ((k + 1) >>> 1) - 1;
    }

    private static final int leftChild(int k) {
        return ((k + 1) << 1) - 1;
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked")
        T[] copy = (T[]) new Object[capacity];
        System.arraycopy(heap, 0, copy, 0, size);
        heap = copy;
    }

    @Override
    public Iterator<T> iterator() {
        return new PriorityQueueIterator();
    }

    private class PriorityQueueIterator implements Iterator<T> {

        int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            return heap[current++];
        }

    }

    @Override
    public T dequeue() {
        return remove();
    }

    @Override
    public void enqueue(T item) throws IllegalArgumentException {
        add(item);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void push(T item) throws IllegalArgumentException {
        add(item);
    }

    @Override
    public T pop() {
        return remove();
    }

    public void shuffle() {
        throw new UnsupportedOperationException("cannot shuffle PriorityQueue");
    }

    // package private
    void exch(int i, int j) {
        assert i >= 0 && i < size;
        assert j >= 0 && j < size;
        Util.exch(heap, i, j);
    }
}
