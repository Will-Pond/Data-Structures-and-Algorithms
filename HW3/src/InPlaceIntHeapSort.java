public class InPlaceIntHeapSort {

    public static void heapSort(int[] array) {
        int n = array.length;

        // Part I: Turn the array into a max-heap
        for (int i = 1; i < n; i++) {
            siftUp(array, i);
        }

        // Part II: Repeatedly extract the max element at 0-th position from
        for (int i = n - 1; i >= 0; i--) {
            // Move the largest item from 0-th to the end i-th index
            swap(array, 0, i);
            // Note: The last index into the heap is now i - 1
            siftDown(array, 0, i);
        }
    }
    //Used to build the heap from the array
    private static void siftUp(int[] array, int i) {
        int parent = (i - 1) / 2;
        while (array[parent] < array[i] && i > 0) {
            swap(array, parent, i);
            i = parent;
            parent = (i - 1) / 2;
        }
    }

    //Recursively rebuilds the heap after removing the max (root)
    private static void siftDown(int[] array, int i, int heapSize) {
        int unsortedMaxIndex = i;
        int left  = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heapSize && array[left] > array[unsortedMaxIndex]) {
            unsortedMaxIndex = left;
        }

        if (right < heapSize && array[right] > array[unsortedMaxIndex]) {
            unsortedMaxIndex = right;
        }

        if (i != unsortedMaxIndex) {
            swap(array, i, unsortedMaxIndex);
            siftDown(array, unsortedMaxIndex, heapSize);
        }

    }
    //Swap utility function
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}