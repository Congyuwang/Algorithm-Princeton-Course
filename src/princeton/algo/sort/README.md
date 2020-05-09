# Sorting algorithms

This package include sorting algorithms as well as shuffling.

Some of their properties are listed below:

| sorting algorithm           | stability  | memory usage | time usage         |
| --------------------------- | ---------- | ------------ | ----------         |
| Selection Sort              | not stable | in-place     | O(N^2)             |
| Insertion Sort              |     stable | in-place     | O(N^2)             |
| Shell Sort (Sedgewick 1985) | not stable | in-place     | O(N^(4/3))         |
| Merge Sort                  |     stable | linear extra (in-place for Queue/Stack) | O(N log N)         |
| Bottom Up Merge Sort        |     stable | linear extra | O(N log N)         |
| Quick Sort                  | not stable | in-place     | N log N on average |
| Grail Sort                  |     stable | in-place     | O(N log N)         |
| Wiki Sort                   |     stable | in-place     | O(N log N)         |

The implementation of them support different types of data structure:

| sorting algorithm           | Supported Data Type
| --------------------------- | ---------- |
| Selection Sort              | Comparable array, Comparator, Primitive array |
| Insertion Sort              | Comparable array, Comparator, Primitive array |
| Shell Sort (Sedgewick 1985) | Comparable array, Comparator, Primitive array |
| Merge Sort                  | Comparable array, Comparator, Primitive array, Queue (comparable / comparator), Stack (comparable / comparator) |
| Bottom Up Merge Sort        | Comparable array, Comparator, Primitive array |
| Quick Sort                  | Comparable array, Comparator, Primitive array |
| Grail Sort                  | Comparable array, Comparator |
| Wiki Sort                   | Comparable array, Comparator |
