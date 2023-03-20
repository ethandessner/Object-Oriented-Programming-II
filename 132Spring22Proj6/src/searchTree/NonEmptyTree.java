package searchTree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 *  
 */
 public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {
	
	private Tree<K,V> left, right;
	private K key;
	private V value;
	private K maxKey;
	private K minKey;
	
	/*
	 * Basic constructor for NonEmptyTree
	 */
	public NonEmptyTree(K key, V value, Tree<K,V> left, Tree<K,V> right) { 
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}
	/*
	 * Value searchValue is set to null. The parameter key is compared to this key, if they are the same then
	 * searchValue is set equal to the value of the key. If not, then searchValue recursively called to the left side
	 * of the tree, and if not again, then it is called recursively to the right side.
	 */
	public V search(K key) {
		V searchValue = null;
		if(this.key.compareTo(key) == 0) {
			searchValue = this.value;
			return searchValue;
		}else if(this.key.compareTo(key) < 0){
			searchValue = right.search(key);
		}else {
			searchValue = left.search(key);
		}
		return searchValue;
	}
	/*
	 * A NonEmptyTree is inserted into the current tree. If the tree is empty, then a tree with the key and value
	 * parameters are added. If the tree already has that value, then it is simply updated. If it is greater, then the
	 * right side is called recursively, if not, then the left side is called recursively.
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		int comparison = this.key.compareTo(key);
		if(comparison == 0) {
			this.value = value;
			return this;
		}else if(comparison < 0) {
			right = right.insert(key, value);
			return this;
		}else {
			left = left.insert(key, value);
			return this;
		}
	}
	
	/*
	 * The parameter key is compared to the current key. If they are equal, then addkey is set to the maximum value
	 * on the left. If there is nothing on the left, then addKey is set to the value on the right. addValue is set
	 * equal to value of addKey. The current key and value are then set equal to addKey and addValue. The value on the
	 * left is then deleted since it has been overwritten. If the value of the key is less than 0, then the right side
	 * is called recursively. If the two prior conditions are false, then the left side is called recursively.
	 */
	public Tree<K, V> delete(K key) {
		int comparison = this.key.compareTo(key);
		if(comparison == 0) {
			K addKey;
			try {
				addKey = this.left.max();
			}catch(TreeIsEmptyException e) {
				return this.right;
			}
			V addValue = this.search(addKey);
			this.key = addKey;
			this.value = addValue;
			left = this.left.delete(addKey);
			return this;
		}else if(comparison < 0) {
			right = this.right.delete(key);
			return this;
		}else {
			left = this.left.delete(key);
			return this;
		}
	}
	
	/*
	 * The values for the right branches are called recursively until the maximum value is found.
	 */
	public K max() {
		try {
			maxKey = right.max();
		}catch(TreeIsEmptyException e) { //this is to account for if the tree is empty
			return key;
		}
		return maxKey;
	}

	/*
	 * The values for the left branches are called recursively until the minimum value is found.
	 */
	public K min() {
		try {
			minKey = left.min();
		}catch(TreeIsEmptyException e) { //this is to account for if the tree is empty
			return key;
		}
		return minKey;
	}
	
	/*
	 * The total amount of keys in the left and right side are counted and then returned.
	 */
	public int size() {
		return left.size() + right.size() + 1;
	}
	
	/*
	 * Takes in a collection and adds it to the tree.
	 */
	public void addKeysToCollection(Collection<K> c) {
		left.addKeysToCollection(c);
		c.add(this.key);
		right.addKeysToCollection(c);
	}
	
	/*
	 * The values between fromKey to toKey are returned.
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		if(this.key.compareTo(fromKey) < 0) {
			return this.right.subTree(fromKey, toKey);
		}else if(this.key.compareTo(toKey) > 0) {
			return this.left.subTree(fromKey, toKey);
		}else {
			return new NonEmptyTree<K,V>(this.key, this.value, 
					this.left.subTree(fromKey, toKey), this.right.subTree(fromKey, toKey));
		}
	}
}