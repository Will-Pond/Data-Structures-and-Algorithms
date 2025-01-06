/**
 * Will Pond CSC 364
 * In this coding assignment I there are many methods I have solved for myDoubleLinkedList use the head node and using the My-list interface.
 *  Also, I had implements the Clonable method and the remove method. After solving the methods I have test the code in the TestMyDoublinkedList to get all the test pass.
 *  There 40 test that I passed.
 */

import java.util.*;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E> implements Cloneable {
	private Node<E> head = new Node<E>(null);

	/** Create a default list */
	public MyDoublyLinkedList() {
		head.next = head;
		head.previous = head;
	}

	private static class Node<E> {
		E element;
		Node<E> previous;
		Node<E> next;

		public Node(E element) {
			this.element = element;
		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder("[");

		Node<E> current = head.next;
		for (int i = 0; i < size; i++) {
			result.append(current.element);
			current = current.next;
			if (current != head) {
				result.append(", "); // Separate two elements with a comma
			}
		}
		result.append("]"); // Insert the closing ] in the string

		return result.toString();
	}

	private Node<E> getNode(int index) {
		Node<E> current = head;
		if (index < size / 2)
			for (int i = -1; i < index; i++)
				current = current.next;
		else
			for (int i = size; i > index; i--)
				current = current.previous;
		return current;
	}

	@Override
	public void add(int index, E e) {
		if (index < 0 || index > size)
		{
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		Node<E> prev = getNode(index - 1);
		Node<E> next = prev.next;
		Node<E> newNode = new Node<E>(e);
		prev.next = newNode;
		next.previous = newNode;
		newNode.previous = prev;
		newNode.next = next;
		size++;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		 head.next = head;
		 head.previous = head;
		 size = 0;
	}

	@Override
	public boolean contains(E o) {
		for (Node<E> current = head.next; current != head; current = current.next) {
			E e = current.element;
			if (o == null ? e == null : o.equals(e))
				return true;
		}
		return false;
	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			return getNode(index).element;
		}
	}

	@Override
	public int indexOf(E e) {
		// TODO Auto-generated method stub
		// Note: Make sure that you check the equality with == for null  objects and with the equals() for others
		if(size == 0)
		{
			return -1;
		}
		Node<E>current = head.next;
		if(e == null)
		{
			for (int i = 0; i < size; i++)
			{
				if (current.element == null)
				{
					return i;
				}
				else
				{
					current = current.next;
				}
			}
		}
		else
		{
			for(int i = 0; i < size; i++)
			{
				if(e.equals(current.element))
				{
					return i;
				}
				else
				{
					current = current.next;
				}
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(E e) {
		// TODO Auto-generated method stub
		if(size == 0)
		{
			return -1;
		}
		Node<E>current = head.previous;
		if(e == null)
		{
			for (int i = size - 1; i > 0; i--)
			{
				if (current.element == null)
				{
					return i;
				} else
				{
					current = current.previous;
				}
			}
		}
				else
				{
					for(int i = size-1; i > 0; i--)
					{
						if(e.equals(current.element))
						{
							return i;
						}
						else
						{
							current = current.previous;
						}
					}
				}
		return -1;
			}


	@Override
	public E remove(int index) {
		// TODO Auto-generated method stub
		if(index < 0 || index >= this.size)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			Node<E> outcome = getNode(index);
			E element = outcome.element;
			outcome.previous.next = outcome.next;
			outcome.next.previous = outcome.previous;
			size--;
			return element;
		}
	}

	@Override
	public Object set(int index, E e) {
		// TODO Auto-generated method stub
		if(index < 0 || index >= size)
		{
			throw new IndexOutOfBoundsException();
		}
		else {
			Node<E> update = getNode(index);
			E beforeElement = update.element;
			update.element = e;
			return beforeElement;
		}

	}

	@Override
	public E getFirst() {
		// TODO Auto-generated method stub
		return get(0);
	}

	@Override
	public E getLast() {
		// TODO Auto-generated method stub
		return get(size -1);
	}

	@Override
	public void addFirst(E e) {
		add(0, e);
	}

	@Override
	public void addLast(E e) {
		// TODO Auto-generated method stub
       add(size, e);
	}

	@Override
	public E removeFirst() {
		// TODO Auto-generated method stub
		if(this.size == 0)
		{
			throw new NoSuchElementException();
		}
		else
		{
			return remove(0);
		}
	}

	@Override
	public E removeLast() {
		// TODO Auto-generated method stub
		if(size == 0)
		{
			throw new NoSuchElementException();
		}
		else
		{
			return remove(this.size-1);
		}
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new MyDoublyLinkedListIterator(index);
	}

	private static enum ITERATOR_STATE {
		CANNOT_REMOVE, CAN_REMOVE_PREV, CAN_REMOVE_CURRENT
	};

	private class MyDoublyLinkedListIterator implements ListIterator<E> {
		private Node<E> current; // node that holds the next element in the
									// iteration
		private int nextIndex; // index of current
		ITERATOR_STATE iterState = ITERATOR_STATE.CANNOT_REMOVE;

		private MyDoublyLinkedListIterator(int index) {
			if (index < 0 || index > size)
				throw new IndexOutOfBoundsException("iterator index out of bounds");
			current = getNode(index);
			nextIndex = index;
		}

		@Override
		public void add(E arg0) {
			// TODO Auto-generated method stub
			MyDoublyLinkedList.this.add(nextIndex,arg0);
		}

		@Override
		public boolean hasNext() {
			return nextIndex < size;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return nextIndex > 0;
		}

		@Override
		public E next() {
			if (nextIndex >= size)
				throw new NoSuchElementException();
			E returnVal = current.element;
			current = current.next;
			nextIndex++;
			iterState = ITERATOR_STATE.CAN_REMOVE_PREV;
			return returnVal;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return nextIndex;
		}

		@Override
		public E previous() {
			// TODO Auto-generated method stub
			if (!(hasPrevious()))
				throw new NoSuchElementException();
			E returnVal = current.previous.element;
			current = current.previous;
			nextIndex--;
			iterState = ITERATOR_STATE.CAN_REMOVE_CURRENT;
			return returnVal;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return nextIndex-1;
		}

		@Override
		public void remove() {
			switch (iterState) {
			case CANNOT_REMOVE:
				throw new IllegalStateException();
			case CAN_REMOVE_PREV:
				MyDoublyLinkedList.this.remove(nextIndex-1);
				iterState = ITERATOR_STATE.CANNOT_REMOVE;
				break;
			case CAN_REMOVE_CURRENT:
				MyDoublyLinkedList.this.remove(nextIndex);
				iterState = ITERATOR_STATE.CANNOT_REMOVE;
				break;
			}
			nextIndex--;
		}

		@Override
		public void set(E arg0) {
			// TODO Auto-generated method stub
			if(current == null)
			{
				throw new IllegalStateException();
			}
			else
			{
				current.previous.element = arg0;
			}
		}
	}
	public Object clone(){
		int i = 0;
		try{
			ListIterator<E> iterator = listIterator();
			MyDoublyLinkedList clonedList = (MyDoublyLinkedList<E>)super.clone();
			clonedList.head = new Node<E>(head.element);
			clonedList.head.next = clonedList.head;
			clonedList.head.previous = clonedList.head;
			clonedList.size = 0;
			while(i < size){
				clonedList.add(iterator.next());
				i++;
			}
			return clonedList;
		}catch(CloneNotSupportedException e) {
			return null;
		}
	}
	public boolean equals(Object object) {
		MyDoublyLinkedList<E> other = (MyDoublyLinkedList<E>) object;
		if (this == other) {
			return true;
		} else if (!(other instanceof MyDoublyLinkedList)) {
			return false;
		} else if (other.size() != this.size()) {
			return false;
		} else {
			ListIterator<E> first = listIterator();
			ListIterator<E> second = other.listIterator();
			while (first.hasNext() && second.hasNext()) {
					if (!(second.next() == first.next())) {
						return false;
				}
			}
			return true;
		}
	}
}
