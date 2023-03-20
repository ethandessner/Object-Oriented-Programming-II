package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import listClass.BasicLinkedList;

public class StudentTests {
	
	@Test
	public void test() {
		BasicLinkedList<Integer> listTest = new BasicLinkedList<Integer>();
		String correct = "";
		listTest.addToEnd(1);
		listTest.addToEnd(2);
		listTest.addToEnd(3);
		listTest.addToEnd(4);
		listTest.addToEnd(5);
		listTest.addToFront(8);
		for(Integer fill : listTest) {
			correct += fill;
		}
		assertEquals(correct, "812345");
		assertTrue(listTest.getFirst() == 8);
		assertTrue(listTest.getLast() == 5);
		listTest.retrieveFirstElement();
		String correctSmall = "";
		for(Integer fill : listTest) {
			correctSmall += fill;
		}
		assertEquals(correctSmall, "12345");
	}
	
	@Test
	public void test2() {
		BasicLinkedList<Integer> listTest = new BasicLinkedList<Integer>();
		String correct = "";
		listTest.addToEnd(1);
		listTest.addToEnd(2);
		listTest.addToEnd(3);
		listTest.addToEnd(4);
		listTest.addToEnd(5);
		listTest.removeAllInstances(1);
		for(Integer fill : listTest) {
			correct += fill;
		}
		assertEquals(correct, "2345");
	}
	
	@Test
	public void test3() {
		BasicLinkedList<Integer> listTest = new BasicLinkedList<Integer>();
		String correct = "";
		listTest.addToEnd(1);
		listTest.addToEnd(1);
		listTest.addToEnd(1);
		listTest.addToEnd(1);
		listTest.addToEnd(1);
		listTest.removeAllInstances(1);
		for(Integer fill : listTest) {
			correct += fill;
		}
		assertEquals(correct, null);
	}
	
	@Test
	public void test4() {
		BasicLinkedList<Integer> listTest = new BasicLinkedList<Integer>();
		assertEquals(listTest.retrieveFirstElement(), null);
	}
	
	@Test
	public void test5() {
		BasicLinkedList<Integer> listTest = new BasicLinkedList<Integer>();
		assertEquals(listTest.retrieveLastElement(), null);
	}
	

}
