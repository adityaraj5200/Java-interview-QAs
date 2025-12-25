import java.util.*;

class MaxHeap {
    private List<Integer> heap;

    public MaxHeap() {
        heap = new ArrayList<>();
    }

    // Heapify up (after insertion)
    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0 && heap.get(parent) < heap.get(index)) {
            Collections.swap(heap, parent, index);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    // Heapify down (after removal)
    private void heapifyDown(int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;

        if (left < heap.size() && heap.get(left) > heap.get(largest))
            largest = left;
        if (right < heap.size() && heap.get(right) > heap.get(largest))
            largest = right;

        if (largest != index) {
            Collections.swap(heap, index, largest);
            heapifyDown(largest);
        }
    }

    // Insert a value into the heap
    public void push(int val) {
        heap.add(val);
        heapifyUp(heap.size() - 1);
    }

    // Remove and return the maximum value (root)
    public int pop() {
        if (heap.isEmpty())
            throw new NoSuchElementException("Heap is empty");

        int maxVal = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        if (!heap.isEmpty())
            heapifyDown(0);

        return maxVal;
    }

    // Get the maximum value without removal
    public int peek() {
        if (heap.isEmpty())
            throw new NoSuchElementException("Heap is empty");
        return heap.get(0);
    }

    // Check if the heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Print the heap (for debugging)
    public void print() {
        for (int num : heap) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

public class CustomMaxHeap {
    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap();

        // Insert elements
        maxHeap.push(30);
        maxHeap.push(20);
        maxHeap.push(15);
        maxHeap.push(50);
        maxHeap.push(10);
        maxHeap.push(5);

        System.out.print("Max-Heap: ");
        maxHeap.print();

        System.out.println("Max element: " + maxHeap.peek()); // 50

        // Extract max
        System.out.println("Extracted max: " + maxHeap.pop()); // 50
        System.out.print("Heap after extraction: ");
        maxHeap.print();
    }
}
