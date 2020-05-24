# Queue package

The Queue package includes Queue interface and its implementations, and a StreamChooseK algorithm.

- **Queue**: Queue interface: main methods include dequeue(), enqueue(), shuffle(), peek(), isEmpty(), and size().
- **LinkedQueue**: Queue implementation using linkedList, which also implements an in-place O(n log(n)) shuffling algorithm for linkedList.
- **Deque**: except for the basic Queue interface, it also implements addFirst(), addLast(), removeFirst(), removeLast() methods. It also implements the Stack interface.
- **ArrayQueue**: Queue implementation using array, which also implements an in-place O(n) time shuffling algorithm.
- **RandomizedQueue**: dequeue() and peek() returns random elements each time called (in constant time). The iterator uses ~n extra memory; each iterator is independently shuffled.
- **TwoStackQueue**: Queue implementation using two stack as inner data structure. Methods enqueue() and dequeue() uses constant amortized time.
- **StreamChooseK**: StreamChooseK uniformly chooses K item from an infinite input stream using constant time for each update, and a fixed K extra memory.
