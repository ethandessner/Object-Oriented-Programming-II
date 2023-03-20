import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

/**
 * <P>
 * The HeavyBag class implements a Set-like collection that allows duplicates (a
 * lot of them).
 * </P>
 * <P>
 * The HeavyBag class provides Bag semantics: it represents a collection with
 * duplicates. The "Heavy" part of the class name comes from the fact that the
 * class needs to efficiently handle the case where the bag contains 100,000,000
 * copies of a particular item (e.g., don't store 100,000,000 references to the
 * item).
 * </P>
 * <P>
 * In a Bag, removing an item removes a single instance of the item. For
 * example, a Bag b could contain additional instances of the String "a" even
 * after calling b.remove("a").
 * </P>
 * <P>
 * The iterator for a heavy bag must iterate over all instances, including
 * duplicates. In other words, if a bag contains 5 instances of the String "a",
 * an iterator will generate the String "a" 5 times.
 * </P>
 * <P>
 * In addition to the methods defined in the Collection interface, the HeavyBag
 * class supports several additional methods: uniqueElements, getCount, and
 * choose.
 * </P>
 * <P>
 * The class extends AbstractCollection in order to get implementations of
 * addAll, removeAll, retainAll and containsAll.  (We will not be over-riding those).
 * All other methods defined in
 * the Collection interface will be implemented here.
 * </P>
 */
public class HeavyBag<T> extends AbstractCollection<T> implements Serializable {

	/* Leave this here!  (We need it for our testing) */
	private static final long serialVersionUID = 1L;
	private int size = 0;
	private Map<T, Integer> heavyBag;
	/* Create whatever instance variables you think are good choices */

	/*
	 * Initialize a new, empty HeavyBag
	 */
	public HeavyBag() {
		heavyBag = new HashMap<T, Integer>();
	}

	/**
	 * Adds an instance of o to the Bag
	 * 
	 * @return always returns true, since added an element to a bag always
	 *         changes it
	 * 
	 */
	@Override
	public boolean add(T o) {
		Integer currSize = heavyBag.get(o);
		if(!heavyBag.containsKey(o)) {
			heavyBag.put(o, 1);
		}else {
			heavyBag.put(o, ++currSize);
		}
		size++;
		return true;
	}

	/**
	 * Adds multiple instances of o to the Bag.  If count is 
	 * less than 0 or count is greater than 1 billion, throws
	 * an IllegalArgumentException.
	 * 
	 * @param o the element to add
	 * @param count the number of instances of o to add
	 * @return true, since addMany always modifies
	 * the HeavyBag.
	 */
	public boolean addMany(T o, int count) {
		Integer currSize = heavyBag.get(o);
		if(!heavyBag.containsKey(o)) {
			heavyBag.put(o, count);

		}else {
			count += currSize;
			heavyBag.put(o, count);
		}
		size += count;
		return true;
	}

	/**
	 * Generate a String representation of the HeavyBag. This will be useful for
	 * your own debugging purposes, but will not be tested other than to ensure that
	 * it does return a String and that two different HeavyBags return two
	 * different Strings.
	 */
	@Override
	public String toString() {
		return heavyBag.keySet() + " ";
	}

	/**
	 * Tests if two HeavyBags are equal. Two HeavyBags are considered equal if they
	 * contain the same number of copies of the same elements.
	 * Comparing a HeavyBag to an instance of
	 * any other class should return false;
	 */
	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if(!(o instanceof HeavyBag)) {
			return false;
		}
		HeavyBag newBag = (HeavyBag) o;
		return this.hashCode() == newBag.hashCode() && this.size == newBag.size;
	}

	/**
	 * Return a hashCode that fulfills the requirements for hashCode (such as
	 * any two equal HeavyBags must have the same hashCode) as well as desired
	 * properties (two unequal HeavyBags will generally, but not always, have
	 * unequal hashCodes).
	 */
	@Override
	public int hashCode() {
		return heavyBag.hashCode();
	}

	/**
	 * <P>
	 * Returns an iterator over the elements in a heavy bag. Note that if a
	 * Heavy bag contains 3 a's, then the iterator must iterate over 3 a's
	 * individually.
	 * </P>
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Set<T> heavySet = uniqueElements();
			Iterator<T> heavyIt = heavySet.iterator();
			Integer elements = 0;
			T currElm = null;
			int curr = 1;
			int currSize = size;

			@Override
			public boolean hasNext() {
				return curr <= currSize;
			}

			@Override
			public T next() {
				if(hasNext()) {
					if(elements > 0) {
						--elements;
					}
					if(elements == 0) {
						currElm = heavyIt.next();
						elements = heavyBag.get(currElm);
					}
					curr++;
				}else {
					throw new NoSuchElementException("No elements of this type are left in the bag");
				}
				return currElm;
			}

			@Override
			public void remove() {

			}
		};
	}

	/**
	 * return a Set of the elements in the Bag (since the returned value is a
	 * set, it can contain no duplicates. It will contain one value for each 
	 * UNIQUE value in the Bag).
	 * 
	 * @return A set of elements in the Bag
	 */
	public Set<T> uniqueElements() {
		return heavyBag.keySet();
	}

	/**
	 * Return the number of instances of a particular object in the bag. Return
	 * 0 if it doesn't exist at all.
	 * 
	 * @param o
	 *            object of interest
	 * @return number of times that object occurs in the Bag
	 */
	public int getCount(Object o) {
		if(!heavyBag.containsKey(o)) {
			return 0;
		}
		return heavyBag.get(o);
	}

	/**
	 * Given a random number generator, randomly choose an element from the Bag
	 * according to the distribution of objects in the Bag (e.g., if a Bag
	 * contains 7 a's and 3 b's, then 70% of the time choose should return an a,
	 * and 30% of the time it should return a b.
	 * 
	 * This operation can take time proportional to the number of unique objects
	 * in the Bag, but no more.
	 * 
	 * This operation should not affect the Bag.
	 * 
	 * @param r
	 *            Random number generator
	 * @return randomly chosen element
	 */
	public T choose(Random r) {
		int count = 0;
		int index = r.nextInt(size());
		T chosen = null;
		for(T scan : this.heavyBag.keySet()) {
			count += heavyBag.get(scan);
			if(count > index) {
				chosen = scan;
				break;
			}
		}
		return chosen;
	}

	/**
	 * Returns true if the Bag contains one or more instances of o
	 */
	@Override
	public boolean contains(Object o) {
		boolean isInBag = false;
		if(!(heavyBag.containsKey(o))) {
			return false;
		}else if(heavyBag.containsKey(o)) {
			isInBag = true;
		}
		return isInBag;
	}


	/**
	 * Decrements the number of instances of o in the Bag.
	 * 
	 * @return return true if and only if at least one instance of o exists in
	 *         the Bag and was removed.
	 */
	@Override
	public boolean remove(Object o) {
		Integer keySize = heavyBag.get(o);
		if(heavyBag.containsKey(o) && heavyBag.get(o) > 0) {
			if(keySize >= 2) {
				heavyBag.put((T)o, --keySize);
				size--;
			}else {
				heavyBag.remove(o);
				size--;
			}
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Total number of instances of any object in the Bag (counting duplicates)
	 */
	@Override
	public int size() {
		return size;
	}
}