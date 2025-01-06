// TestSorting.java
// Tests and times the following sorting methods on sorted and unsorted arrays
// of various sizes:
//      InsertionSort.insertionSort(int[] list) -- Insertion sort code from the Liang textbook
//      HeapSort.heapSort(E[] list) -- Heap sort code from the Liang textbook.  Uses a Heap object.
//      InPlaceIntHeapSort.heapSort(int[] list) -- An in-place version of heap sort.
//
// The timings and test results are written both to the console and to a file.
//
// by Jeff Ward

import java.util.Random;
import java.io.*;

public class TestSorting {
	private static PrintWriter outFile;
	
	public static void main(String[] args) throws IOException {
		outFile = new PrintWriter("Timings.txt");
		
		int[] sizes	= { 100000, 200000, 400000 }; // Array sizes
		
		for (int n: sizes) {
			int[] orderedArray = generateOrdered(n);
			int[] randomArray = generateRandom(n);
			int[] copy;
			
			// Insertion sort tests
			testInsertionSort(orderedArray, true);
			copy = copyArray(randomArray);
			testInsertionSort(copy, false);
			
			// Object-based heap sort tests
			testObjectBasedHeapSort(orderedArray, true);
			copy = copyArray(randomArray);
			testObjectBasedHeapSort(copy, false);
			
			// In place heap sort tests
			testInPlaceHeapSort(orderedArray, true);
			copy = copyArray(randomArray);
			testInPlaceHeapSort(copy, false);
		}
		
		output("Done!");
		outFile.close();
	}
	
	static void testInsertionSort(int[] array, boolean ordered) {
		long startTime = System.currentTimeMillis();
		InsertionSort.insertionSort(array);		
		long endTime = System.currentTimeMillis();
		long timeToSolve = endTime - startTime; // Time in milliseconds
		output("Insertion sort "
						+ "runtime on "
						+ (ordered ? "ordered" : "random")
						+ " array of length "
						+ array.length + ":  "
						+ timeToSolve + " milliseconds");
						
		if (checkSorted(array)) {
			output("Insertion sort verified.");
			output();
		}
		else {
			output("Insertion sort failed.");
			System.exit(1);
		}
	}
	
	static void testInPlaceHeapSort(int[] array, boolean ordered) {
		long startTime = System.currentTimeMillis();	
		InPlaceIntHeapSort.heapSort(array);		
		long endTime = System.currentTimeMillis();
		long timeToSolve = endTime - startTime; // Time in milliseconds
		output("In place heap sort "
						+ "runtime on "
						+ (ordered ? "ordered" : "random")
						+ " array of length "
						+ array.length + ":  "
						+ timeToSolve + " milliseconds");
		
		if (checkSorted(array)) {
			output("In place heap sort verified.");
			output();
		}
		else {
			output("In place heap sort failed.");
			System.exit(1);
		}
	}
	
	static void testObjectBasedHeapSort(int[] array, boolean ordered) {
		Integer[] intObjects = new Integer[array.length];
		for (int i = 0; i < array.length; i++)
			intObjects[i] = array[i];
		long startTime = System.currentTimeMillis();
		HeapSort.heapSort(intObjects);		
		long endTime = System.currentTimeMillis();
		long timeToSolve = endTime - startTime; // Time in milliseconds
		output("Object-based heap sort "
						+ "runtime on "
						+ (ordered ? "ordered" : "random")
						+ " array of length "
						+ array.length + ":  "
						+ timeToSolve + " milliseconds");
		
		for (int i = 0; i < array.length; i++)
			array[i] = intObjects[i];
		if (checkSorted(array)) {
			output("Object-based heap sort verified.");
			output();
		}
		else {
			output("Object-based heap sort failed.");
			System.exit(1);
		}
	}
	
	// Generate an array of n random integers.
	static int[] generateRandom(int n) {
		Random generator = new Random();
		int[] array = new int[n];
				
		for (int i = 0; i < n; i++)
			array[i] = generator.nextInt();
		
		return array;
	}
	
	// Generate an array consisting of the integers 0, 1, 2, ..., n-1.
	static int[] generateOrdered(int n) {
		int[] array = new int[n];
				
		for (int i = 0; i < n; i++) {
			array[i] = i;	
		}
		
		return array;
	}
		
	static int[] copyArray(int[] array) {
		int n = array.length;
		int[] returnVal = new int[n];
		
		for (int i = 0; i < n; i++) {
			returnVal[i] = array[i];
		}
		
		return returnVal;
	}
	
	static boolean checkSorted(int[] array) {
		int n = array.length;
		for (int i = 0; i <= n - 2; i++)
			if (array[i] > array[i + 1])
				return false;
		
		return true;
	}
	
	static void displayArray(int[] array) {
		for (int val: array)
			System.out.print(" " + val);
		System.out.println();
	}
	
	static void output(String s) {
		System.out.println(s);
		outFile.println(s);	
	}
	
	static void output() {
		output("");	
	}
}
