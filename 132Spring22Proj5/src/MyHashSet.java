import java.util.ArrayList;
import java.util.Iterator;

/** 
 * The MyHashSet API is similar to the Java Set interface. This
 * collection is backed by a hash table.
 */
public class MyHashSet<E> implements Iterable<E> {

	/** Unless otherwise specified, the table will start as
	 * an array (ArrayList) of this size.*/
	private final static int DEFAULT_INITIAL_CAPACITY = 4;

	/** When the ratio of size/capacity exceeds this
	 * value, the table will be expanded. */
	private final static double MAX_LOAD_FACTOR = 0.75;

	public ArrayList<Node<E>> hashTable;

	private int size;  // number of elements in the table

	public static class Node<T> {
		private T data;
		public Node<T> next; 

		private Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/**
	 * Initializes an empty table with the specified capacity.  
	 *
	 * @param initialCapacity initial capacity (length) of the 
	 * underlying table
	 */
	
	public MyHashSet(int initialCapacity) {
		hashTable = new ArrayList<>();
		for(int x = 0; x < initialCapacity; x++) {
			hashTable.add(null);
		}
	}

	/**
	 * Initializes an empty table of length equal to 
	 * DEFAULT_INITIAL_CAPACITY
	 */
	public MyHashSet() {
		hashTable = new ArrayList<>();
		for(int x = 0; x < DEFAULT_INITIAL_CAPACITY; x++) {
			hashTable.add(null);
		}
	}

	/**
	 * Returns the number of elements stored in the table.
	 * @return number of elements in the table
	 */
	public int size(){
		return size;
	}

	/**
	 * Returns the length of the table (the number of buckets).
	 * @return length of the table (capacity)
	 */
	public int getCapacity(){
		return hashTable.size();
	}

	/** 
	 * Looks for the specified element in the table.
	 * 
	 * @param element to be found
	 * @return true if the element is in the table, false otherwise
	 */
	public boolean contains(Object element) {
		int data = element.hashCode();
		Node<E> curr = hashTable.get(Math.abs(data % getCapacity())); //gets arraylist index
		while(curr != null) {
			if(curr.data.equals(element)) {
				return true;
			}else {
				curr = curr.next;
			}
		}
		return false;
	}

	/** Adds the specified element to the collection, if it is not
	 * already present.  If the element is already in the collection, 
	 * then this method does nothing.
	 * 
	 * @param element the element to be added to the collection
	 * 
	 * Element is added to the end of the linked list in a given index. If the load factor exceeds size/capacity, a hash
	 * table of twice the size of the previous table is created. The elements of the old hash table are filled into the
	 * larger hash table. The new hash table is aliased to the new hash table.
	 */
	
	public void add(E element) {
		int data = element.hashCode();
		int indexAddTo = Math.abs(data % getCapacity()); 
		if(contains(element) == false) {
			Node<E> addTo = new Node<E>(element);
			addTo.next = hashTable.get(indexAddTo);
			hashTable.set(indexAddTo, addTo);
			size++;
		}
		if(((double)size)/((double)getCapacity()) > MAX_LOAD_FACTOR) {
			ArrayList<Node<E>> newHash = new ArrayList<>();
			for(int x = 0; x < 2*getCapacity(); x++) {
				newHash.add(null);
			}
			Iterator<E> itr = iterator();
			while(itr.hasNext()) {
				Node<E> replace = new Node<E>(itr.next());
				int indexReplace = Math.abs(replace.data.hashCode() % (2*getCapacity()));
				replace.next = newHash.get(indexReplace);
				newHash.set(indexReplace, replace);
			}
			hashTable = newHash;
		}
	}

	/** Removes the specified element from the collection.  If the
	 * element is not present then this method should do nothing (and
	 * return false in this case).
	 *
	 * @param element the element to be removed
	 * @return true if an element was removed, false if no element 
	 * removed
	 * 
	 * Sets nodes prev and curr to the first element. If element is in the first node, then it is removed. If not,
	 * curr is incremented until it is equal to element. Element is then removed and the size is decremented.
	 * If the element is not in the hash table, then false is returned.
	 */
	
	public boolean remove(Object element) {
		if(contains(element)) {
			int indexElm = Math.abs(element.hashCode() % getCapacity());
			Node<E> prev = hashTable.get(indexElm);
			Node<E> curr = hashTable.get(indexElm);
			if(curr.data.equals(element)) {
				hashTable.set(indexElm, curr.next);
				size--;
				return true;
			}
			while(!(curr.data.equals(element))) {	
				prev = curr;
				curr = curr.next;
			}
			prev.next = curr.next;
			curr = null;
			size--;
			return true;
		}
		return false;
	}

	/** Returns an Iterator that can be used to iterate over
	 * all of the elements in the collection.
	 * 
	 * The order of the elements is unspecified.
	 */

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			int x = 0;
			int count = 0;
			Node<E> curr = hashTable.get(x);
			@Override
			public E next() {
				while(curr == null) {
					x++;
					curr = hashTable.get(x);
				}
				E data = curr.data;
				curr = curr.next;
				count++;
				return data;
			}
			@Override
			public boolean hasNext() {
				if(count < size) {
					return true;
				}
				return false;
			}
		};
	}

}
