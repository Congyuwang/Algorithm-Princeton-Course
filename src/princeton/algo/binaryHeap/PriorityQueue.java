package princeton.algo.binaryHeap;

import java.util.Iterator;
import java.util.NoSuchElementException;
import princeton.algo.queue.Queue;
import princeton.algo.sort.Util;
import princeton.algo.stack.Stack;

public class PriorityQueue<T> implements Queue<T>, Stack<T> {

    private T[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        heap = (T[]) new Object[1];
        size = 0;
    }

    public PriorityQueue(T[] array) {
        heap = array.clone();
        size = array.length;
        for (T t : array) {
            if (t != null) {
                throw new IllegalArgumentException("null element in array");
            }
        }
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(Queue<T> q) {
        heap = (T[]) new Object[q.size()];
        size = q.size();
        for (T t : q) {
            assert t != null;
            add(t);
        }
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(Stack<T> s) {
        heap = (T[]) new Object[s.size()];
        size = s.size();
        for (T t : s) {
            assert t != null;
            add(t);
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
        heap[0] = heap[size - 1];
        heap[--size] = null;
        moveDown(0);
        if (size == heap.length >>> 2) {
            resize(heap.length >> 1);
        }
        return temp;
    }

    private void moveUp(int k) {
        while (k > 0) {
            @SuppressWarnings("unchecked")
            Comparable<? super T> child = (Comparable<? super T>) heap[k];
            int parent = parent(k);
            if (child.compareTo(heap[parent]) > 0) {
                Util.exch(heap, k, parent);
                k = parent;
            } else {
                break;
            }
        }
    }

    private void moveDown(int k) {
        while (k <= parent(size - 1)) {
            @SuppressWarnings("unchecked")
            Comparable<? super T> parent = (Comparable<? super T>) heap[k];
            int leftChild = leftChild(k);
            assert leftChild < size;
            int rightChild = leftChild + 1;
            if (parent.compareTo(heap[leftChild]) < 0) {
                if (rightChild >= size) {
                    Util.exch(heap, k, leftChild);
                    k = leftChild;
                    continue;
                }
                @SuppressWarnings("unchecked")
                Comparable<? super T> right = (Comparable<? super T>) heap[rightChild];
                if (right.compareTo(heap[leftChild]) > 0) {
                    Util.exch(heap, k, rightChild);
                    k = rightChild;
                    continue;
                }
                Util.exch(heap, k, leftChild);
                k = leftChild;
            } else if (rightChild < size && parent.compareTo(heap[rightChild]) < 0) {
                Util.exch(heap, k, rightChild);
                k = rightChild;
            } else {
                break;
            }
        }
    }

    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] copy = (T[]) new Object[size];
        System.arraycopy(heap, 0, copy, 0, size);
        return copy;
    }

    private final int parent(int k) {
        return ((k + 1) >>> 1) - 1;
    }

    private final int leftChild(int k) {
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
}
