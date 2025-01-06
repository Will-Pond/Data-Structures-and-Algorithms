// MyQuadraticHashSet.java
// Implements a hash table of objects, using open addressing with quadratic probing.
// - Jeff Ward

public class MyQuadraticHashSet<E> implements MySet<E> {
	private int size = 0; // The number of elements in the set

	private Object[] table; // The table elements. Initially all null.

	private double maxLoadFactor; // size should not exceed (table.length *
									// maxLoadFactor)

	private int thresholdSize; // (int) (table.length * maxLoadFactor)
								// When size + numRemoved exceeds this value,
								// then resize and rehash.

	private int[] tableSizes; // A set of prime numbers that will be used as the
								// hash table sizes.

	private int nextTableSizeIndex = 0; // Index into tableSizes.

	/*
	 * REMOVED is stored in the hashTable in place of a removed element. This
	 * lets the quadratic probing process know that it may need to continue
	 * probing.
	 */
	private final static Object REMOVED = new Object();

	private int numRemoved = 0; // The number of times that REMOVED occurs in the table.

	private int probeIndex(int hashCode, long probeCount, int tableLength) {
		return (int) ((hashCode % tableLength + tableLength + probeCount * probeCount) % tableLength);
	}


	public MyQuadraticHashSet(double maxLoadFactor, int[] tableSizes) {
		//initialize values based off of given inputs
		this.maxLoadFactor = maxLoadFactor;
		this.tableSizes = tableSizes;
		this.table = new Object[tableSizes[nextTableSizeIndex]];
		this.thresholdSize = (int) (table.length * maxLoadFactor);
	}

	public void clear() {

		//reset hash set parameters back to empty
		this.size = 0;
		this.numRemoved = 0;
		this.table = new Object[tableSizes[nextTableSizeIndex]];
		this.thresholdSize = (int) (table.length * maxLoadFactor);
	}
	

	public boolean contains(E e) {

		//declare + initialize nessacary variables for traversal
		int hashCode = e.hashCode();
		long probeCount = 0;
		int index = probeIndex(hashCode, probeCount, table.length);

		//traverse table, return true if element is found
		while (table[index] != null) {
			if (table[index] != REMOVED && e.equals(table[index])) {
				return true;
			}
			probeCount++;
			index = probeIndex(hashCode, probeCount, table.length);
		}

		//element not found, default to false
		return false;

	}


	public boolean add(E e) {

		//check if size is within threshold
		if (this.size + this.numRemoved >= this.thresholdSize) {
			rehash();
		}

		//check to see if new entry already exists
		boolean alreadyExists = contains(e);
		if (alreadyExists) {
			return false;
		}

		//does not already exist, perform addition
		int hashCode = e.hashCode();
		long probeCount = 0;
		int index = probeIndex(hashCode, probeCount, this.table.length);

		//traverse until spot is found
		while (this.table[index] != null && this.table[index] != this.REMOVED) {
			probeCount++;
			index = probeIndex(hashCode, probeCount, this.table.length);
		}

		//spot found is a removed, decrement numRemoved
		if (this.table[index] == this.REMOVED) {
			this.numRemoved--;
		}

		//add e to spot found
		table[index] = e;
		size++;
		return true;
	}

	@SuppressWarnings("unchecked")
	private void rehash() {
		nextTableSizeIndex++;
		int newTableSize = tableSizes[nextTableSizeIndex];
		Object[] newTable = new Object[newTableSize];
		thresholdSize = (int) (newTableSize * maxLoadFactor);

		for (Object element : table) {
			if (element != null && element != REMOVED) {
				int hashCode = element.hashCode();
				long probeCount = 0;
				int index = probeIndex(hashCode, probeCount, newTableSize);

				while (newTable[index] != null) {
					probeCount++;
					index = probeIndex(hashCode, probeCount, newTableSize);
				}

				newTable[index] = element;
			}
		}

		table = newTable;
		numRemoved = 0;
	}

	public boolean remove(E e) {
		int hashCode = e.hashCode();
		long probeCount = 0;
		int index = probeIndex(hashCode, probeCount, this.table.length);

		//look for item to remove, set to REMOVED, not null, if found, per assignment instructions
		while (this.table[index] != null) {
			if (this.table[index] != this.REMOVED && e.equals(this.table[index])) {
				this.table[index] = this.REMOVED;
				this.numRemoved++;
				this.size--;
				return true;
			}
			probeCount++;
			index = probeIndex(hashCode, probeCount, this.table.length);
		}

		//element not found, default to false
		return false;

	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public java.util.Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}
}
