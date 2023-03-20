package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import listClass.BasicLinkedList;

public class PublicTests {

	@Test
	public void test1() {
		
		BasicLinkedList<Integer> blist = new BasicLinkedList<>();
		
		blist.addToEnd(6);
		blist.addToEnd(4);
		String answer = "";
		for (Integer entry : blist) {
			answer += entry;
		}
		assertEquals(answer, "64");
	}
	@Test
	public void test2() {
		
		BasicLinkedList<String> blist = new BasicLinkedList<String>();
		
		blist.addToEnd("Zebra").addToEnd("Bear").addToEnd("Dove");
		String answer = "";
		for (String entry : blist) {
			answer += entry;
		}
		assertEquals(answer, "ZebraBearDove");
	}

}
