package searchTree;

import java.util.Collection;

/**
 * This class is used to represent the empty search tree: a search tree that
 * contains no entries.
 * 
 * This class is a singleton class: since all empty search trees are the same,
 * there is no need for multiple instances of this class. Instead, a single
 * instance of the class is created and made available through the static field
 * SINGLETON.
 * 
 * The constructor is private, preventing other code from mistakenly creating
 * additional instances of the class.
 *  
 */
 public class EmptyTree<K extends Comparable<K>,V> implements Tree<K,V> {
	/**
	 * This static field references the one and only instance of this class.
	 * We won't declare generic types for this one, so the same singleton
	 * can be used for any kind of EmptyTree.
	 */
	private static EmptyTree SINGLETON = new EmptyTree();

	public static  <K extends Comparable<K>, V> EmptyTree<K,V> getInstance() {
		return SINGLETON;
	}

	/**
	 * Constructor is private to enforce it being a singleton
	 *  
	 */
	private EmptyTree() {
		// No activity
	}
	
	/*
	 * null is returned since the tree is empty - there are no contents to be searched
	 */
	public V search(K key) {
		return null;
	}
	
	public NonEmptyTree<K, V> insert(K key, V value) {
		return new NonEmptyTree<K,V>(key, value, getInstance(), getInstance());
	}
	/*
	 * There are no contents in the tree to be deleted, so the current tree is returned.
	 */
	public Tree<K, V> delete(K key) {
		return this;
	}
	
	/*
	 * There are no contents in the tree so an TreeIsEmptyException is thrown
	 */
	public K max() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/*
	 * There are no contents in the tree so an TreeIsEmptyException is thrown
	 */
	public K min() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}
	
	/*
	 * There are no contents in the tree so the size of the tree is 0
	 */
	public int size() {
		return 0;
	}
	
	/*
	 * There is no action to be done here
	 */
	public void addKeysToCollection(Collection<K> c) {
		return;
	}
	
	/*
	 * The current tree is returned
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		return this;
	}
}