import static org.junit.Assert.*;

import org.junit.Test;

public class PublicTests {

//	@Test
//	public void testSimpleAdd() {
//		MyHashSet<String> s = new MyHashSet<String>();
//		s.add("hello");
//		s.add("apple");
//		System.out.println(s.getCapacity());
//		System.out.println(s.size());
//		assertEquals(4, s.getCapacity());
//		assertEquals(2, s.size());
//	}
//	
//	@Test
//	public void testReHash() {
//		MyHashSet<String> s = new MyHashSet<String>();
//		for (int i = 0; i < 1000; i++) {
//			s.add("Entry " + i);
//		}
//		assertEquals(2048, s.getCapacity());
//		assertEquals(1000, s.size());
//	}
//
//	@Test
//	public void testNoDuplicates() {
//		MyHashSet<String> s = new MyHashSet<String>();
//		for (int i = 0; i < 10; i++) {
//			s.add("hello");
//			s.add("apple");
//			s.add("cat");
//			s.add("last");
//		}
//		System.out.println("Cap " + s.getCapacity());
//		System.out.println("Size " + s.size());
//		assertEquals(8, s.getCapacity());
//		assertEquals(4, s.size());
//	}
	@Test
	public void testRemove() {
		MyHashSet<String> s = new MyHashSet<String>();
		for (int i = 0; i < 10; i++) {
			s.add("hello");
			s.add("apple");
			s.add("cat");
			s.add("last");
		}
		s.remove("hello");
		assertFalse(s.contains("hello"));
		s.remove("apple");
		assertFalse(s.contains("apple"));
		s.remove("cat");
		assertFalse(s.contains("cat"));
		s.remove("last");
		assertFalse(s.contains("last"));
		
	}
}
