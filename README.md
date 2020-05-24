# Algorithm-Princeton-Assignments

This repository contains my solutions to princeton's algorithm open course taught by Robert Sedgewick and Kevin Wayne.

The source file folder is src.
The project is structured according to the topics of the lectures.

```{}
/---
    \
    |-assignments/:           shell scripts for assignment submission
    |
    |-data/:                  testing data for algorithms
    |
    |-lib/:                   external libraries
    |
    |-src:--                  source folder (package root)
            |
            |-/:                      default package (assignments)
            |
            |-interview/:             interview questions (e.g., Three Sum)
            |
            |-tests/:                 test sorting, shuffling and others
            |
            |-princeton/algo/:--      algorithms (have more optimization and varieties than algs4.jar)
                               |
                               |-unionfind/:   unionFind algorithms
                               |
                               |-queue/:       Queue interface and various implementations
                               |
                               |-stack/:       Stack interface and various implementations
                               |
                               |-sort:--       sorting algorithms
                               |      |
                               |      |-/:           sorting and shuffling
                               |      |
                               |      |-hybrid:      two hybrid sorting from git (made static methods)
                               |                     grailSort and wikiSort
                               |
                               |-binaryHeap:   Heap sort and PriorityQueue
```

## Sort package

This package includes sorting algorithms as well as shuffling.

Some of their properties:

| sorting algorithm           | stability  | memory usage | time usage         |
| --------------------------- | ---------- | ------------ | ----------         |
| Selection Sort              | not stable | in-place     | O(N^2)             |
| Insertion Sort              |     stable | in-place     | O(N^2)             |
| Shell Sort (Sedgewick 1985) | not stable | in-place     | O(N^(4/3))         |
| Merge Sort                  |     stable | linear extra (in-place for Queue/Stack) | O(N log N)         |
| Bottom Up Merge Sort        |     stable | linear extra | O(N log N)         |
| Quick Sort                  | not stable | in-place     | N log N on average |
| Heap Sort                   | not stable | in-place     | O(N log N)         |
| Grail Sort                  |     stable | in-place     | O(N log N)         |
| Wiki Sort                   |     stable | in-place     | O(N log N)         |

The implementation of them support different types of data structure:

| sorting algorithm           | Supported Data Type                           |
| --------------------------- | --------------------------------------------- |
| Selection Sort              | Comparable array, Comparator, Primitive array |
| Insertion Sort              | Comparable array, Comparator, Primitive array |
| Shell Sort (Sedgewick 1985) | Comparable array, Comparator, Primitive array |
| Merge Sort                  | Comparable array, Comparator, Primitive array, Queue (comparable / comparator), Stack (comparable / comparator) |
| Bottom Up Merge Sort        | Comparable array, Comparator, Primitive array |
| Quick Sort                  | Comparable array, Comparator, Primitive array |
| Heap Sort                   | Comparable array, Comparator, Primitive array |
| Grail Sort                  | Comparable array, Comparator                  |
| Wiki Sort                   | Comparable array, Comparator                  |

Note: because _heap sort_ uses _binary heap_ data structure, it is placed in **binaryHeap** package.

# Queue package

The Queue package includes Queue interface and its implementations, and a StreamChooseK algorithm.

- **Queue**: Queue interface: main methods include dequeue(), enqueue(), shuffle(), peek(), isEmpty(), and size().
- **LinkedQueue**: Queue implementation using linkedList, which also implements an in-place O(n log(n)) shuffling algorithm for linkedList.
- **Deque**: except for the basic Queue interface, it also implements addFirst(), addLast(), removeFirst(), removeLast() methods. It also implements the Stack interface.
- **ArrayQueue**: Queue implementation using array, which also implements an in-place O(n) time shuffling algorithm.
- **RandomizedQueue**: dequeue() and peek() returns random elements each time called (in constant time). The iterator uses ~n extra memory; each iterator is independently shuffled.
- **TwoStackQueue**: Queue implementation using two stack as inner data structure. Methods enqueue() and dequeue() uses constant amortized time.
- **StreamChooseK**: StreamChooseK uniformly chooses K item from an infinite input stream using constant time for each update, and a fixed K extra memory.

