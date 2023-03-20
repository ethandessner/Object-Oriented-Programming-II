package listClass;

import java.util.LinkedList;
import java.util.Iterator;

/*
 * Creates properties for a linked list. Allows a linked list to add elements to the front and the back of the linked
 * list and can remove all of a certain instance from the list. There is a head reference and a tail reference that
 * allows the list to be modified easily.
 */
public class BasicLinkedList<T> implements Iterable<T> {
	private static class Node<T>{
		private T data;
		private Node<T> next;

		public Node(T element){
			data = element;
			next = null;
		}
	}

	private Node<T> head;
	private Node<T> tail;
	private int currentSize;
	
	/*
	 * Constructor for linked list. Sets head and tail node to null. Current size of the list
	 * is set to 0.
	 */
	public BasicLinkedList(){
		currentSize = 0;
		head = null;
		tail = null;
	}
	
	/*
	 * Returns the size of the linked list.
	 */
	public int getSize() {
		return currentSize;
	}
	
	/*
	 * Returns the first element of the linked list.
	 */
	public T getFirst() {
		if(head == null) {
			return null;
		}
		return head.data;
	}
	
	/*
	 * Returns the last element of the linked list.
	 */
	public T getLast() {
		if(head == null) {
			return null;
		}
		return tail.data;
	}
	
	/*
	 * Returns the data in a given index.
	 */
	public T get(int index) {
		if(index > currentSize || index < 0) {
			return null;
		}
		int current = 0;
		Node<T> place = head;
		while(current != index) {
			place = place.next;
			current++;
		}
		return place.data;
	}
	
	/*
	 * Returns the data of the first element and then reassigns the head to the node in front of it. The old head is 
	 * then removed from the linked list by the garbage collector.
	 */
	public T retrieveFirstElement() {
		if(head == null) {
			return null;
		}
		Node<T> place = head;
		head = head.next;
		currentSize--;
		return place.data;
	}
	
	/*
	 * Returns the data of the tail and then the tail is reassigned to the node before it. The old tail is then removed
	 * from the linked list by the garbage collector.
	 */
	public T retrieveLastElement() {
		if(head == null) {
			return null;
		}else if(head.next == null) {
			T data = head.data;
			head = null;
			currentSize--;
			return data;
		}else {
			T data = null;
			Node<T> curr = head;
			while(curr.next.next != null) {
				curr = curr.next;
			}
			data = tail.data;
			curr.next = null;
			tail = curr;
			currentSize--;
			return data;
		}
	}
	
	/*
	 * A node newNode is set to the end of the linked list by using tail.next = newNode. The tail is now newNode.
	 * If the linked list is empty, then both the tail and head are set to newNode.
	 */
	public BasicLinkedList<T> addToEnd(T data){
		currentSize++;
		Node<T> newNode = new Node<T>(data);
		if(head == null) {
			head = newNode;
			tail = newNode;
			return this;
		}
		tail.next = newNode;
		tail = newNode;
		return this;
	}
	
	/*
	 * A node newNode is added to the linked list at the front by being reassigned as the head. If the linked list is
	 * empty then newNode is set to the head and the tail is set to the head.
	 */
	public BasicLinkedList<T> addToFront(T data){ 
		currentSize++;
		Node<T> newNode = new Node<T>(data);
		if(head == null) {
			head = newNode;
			tail = head;
			return this;
		}
		newNode.next = head;
		head = newNode;
		return this;
	}
	
	/*
	 * Nodes prev and head are used to traverse the linked list. The entire linked list is traversed in a while loop and
	 * is done traversing once the curr node reaches the end and becomes null. First, the tail is checked to see if it 
	 * has the target data. If it does, the tail points to prev which is then set to null, eliminating the head. Curr is
	 * set back to the head. The head is then checked, and if the target data is at the head, the head is set to point
	 * to the node in front of it, and the old head is collected by the garbage collector. The nodes is the middle are
	 * then checked last. The prev node is set to the node in front of the curr node. The node in front of the curr node
	 * is then set to null, curr is set back to the start, and prev is set back to null. If the target data is not found
	 * in one of the nodes, then prev and curr are both incremented forward one node. The linked list is then returned.
	 */
	public BasicLinkedList<T> removeAllInstances(T targetData){
		Node<T> prev = null;
		Node<T> curr = head;
		while(curr != null) {
			if(head == null) {
				return this;
			}else if(curr.data.equals(targetData) && curr.next == null) {
				tail = prev;
				prev = null;
				curr = head;
				currentSize--;
			}else if(head.data.equals(targetData)) {
				curr = curr.next;
				head.next = null;
				head = curr;
				curr = head;
				currentSize--;
				if(currentSize == 0) {
					tail = head;
				}
			}else if(curr.data.equals(targetData) && curr.next != null) {
				prev.next = curr.next;
				curr.next = null;
				curr = head;
				prev = null;
				currentSize--;
			}else {
				prev = curr;
				curr = curr.next;
			}
		}
		return this;
	}
	/*
	 * Basic iterator method for the linked list. Has a next method and a hasNext method.
	 */
	@Override
	public Iterator<T> iterator(){
		return new Iterator<T>() {
			Node<T> curr = head;
			@Override
			public boolean hasNext() {
				return curr != null;
			}
			@Override
			public T next() {
				T data = curr.data;
				curr = curr.next;
				return data;
			}
		};
	}
	
}
