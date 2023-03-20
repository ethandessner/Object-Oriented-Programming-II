

import org.junit.Test;

import java.util.Arrays;

import junit.framework.TestCase;


/** This class contains the public test cases for the  minimumSnippet project 
 * */
public class PublicTests  extends TestCase {
	
	static MinimumSnippet get(String [] doc, String [] terms) {
		return new MinimumSnippet(Arrays.asList(doc), Arrays.asList(terms));
	}
	
	
	/** Test the case where none of the terms are found */
	@Test
	public void testNotFoundAny() {
		MinimumSnippet m = get(new String[] {"1", "2", "3"}, new String[] {"x"} );
		assertFalse(m.foundAllTerms());
	}
	
	/** Test the case where only some of the terms are found */
	@Test
	public void testNotFoundAll() {
		MinimumSnippet m = get(new String[] {"1", "2", "3"}, new String[] {"1", "x"} );
		assertFalse(m.foundAllTerms());
	}
	
	/* Test finding "1" in the document "1", "2", "3" */
	@Test
	public void test1in123() {
		MinimumSnippet m = get(new String[] {"1", "2", "3"}, new String[] {"1"} );
		assertTrue(m.foundAllTerms());
		assertEquals(0, m.getStartingPos());
		assertEquals(0, m.getEndingPos());
		assertEquals(1, m.getLength());
		assertEquals(0, m.getPos(0));
	}
	
	/* Test finding "3" in the document "1", "2", "3" */
	@Test
	public void test3in123() {
		MinimumSnippet m = get(new String[] {"1", "2", "3"}, new String[] {"3"} );
		assertTrue(m.foundAllTerms());
		assertEquals(2, m.getStartingPos());
		assertEquals(2, m.getEndingPos());
		assertEquals(1, m.getLength());
		assertEquals(2, m.getPos(0));
	}
	
	/* Test finding "1" and "3" in the document "1", "2", "2", "3", "2", "1" */
	@Test
	public void test13in122321() {
		MinimumSnippet m = get(new String[] {"1", "2", "2", "3", "2", "1"}, new String[] {new String("1"), "3"} );
		assertTrue(m.foundAllTerms());
		assertEquals(3, m.getStartingPos());
		assertEquals(5, m.getEndingPos());
		assertEquals(3, m.getLength());
		assertEquals(5, m.getPos(0));
		assertEquals(3, m.getPos(1));
	}
	
	/* Test finding "1" and "3" in the document "1", "2", "2", "3", "2", "1" */
	@Test
	public void test31in122321() {
		MinimumSnippet m = get(new String[] {"1", "2", "2", "3", "2", "1"}, new String[] {"3", "1"} );
		assertTrue(m.foundAllTerms());
		assertEquals(3, m.getStartingPos());
		assertEquals(5, m.getEndingPos());
		assertEquals(3, m.getLength());
		assertEquals(3, m.getPos(0));
		assertEquals(5, m.getPos(1));
	}
	@Test
	public void test12in001021000() {
		MinimumSnippet m = get(new String[] {"0","0","1","0","2","1","0","0","0"}, new String[] {"1", "2"} );
		assertTrue(m.foundAllTerms());
		assertEquals(4, m.getStartingPos());
		assertEquals(5, m.getEndingPos());
		assertEquals(2, m.getLength());
		assertEquals(5, m.getPos(0));
		assertEquals(4, m.getPos(1));
	}
	
	@Test
	public void test12in00102000() {
		MinimumSnippet m = get(new String[] {"0","0","1","0","2","0","0","0"}, new String[] {"1", "2"} );
		assertTrue(m.foundAllTerms());
		assertEquals(2, m.getStartingPos());
		assertEquals(4, m.getEndingPos());
		assertEquals(3, m.getLength());
		assertEquals(2, m.getPos(0));
		assertEquals(4, m.getPos(1));
	}
	
	@Test
	public void test123in0102030320100() {
		MinimumSnippet m = get(new String[] {"0","1","0","2","0",new String("3"),"0","3", "2", "0", "1", "0", "0"}, new String[] {"1", "2", "3"} );
		assertTrue(m.foundAllTerms());
		assertEquals(7, m.getStartingPos());
		assertEquals(10, m.getEndingPos());
		assertEquals(4, m.getLength());
		assertEquals(10, m.getPos(0));
		assertEquals(8, m.getPos(1));
		assertEquals(7, m.getPos(2));
	}
	@Test
	public void test1234in40102030320100() {
		MinimumSnippet m = get(new String[] {"4","0","1","0","2","0","3","0","3", "2", "0", "1", "0", "4", "0"}, new String[] {"1", "2", "3", "4"} );
		assertTrue(m.foundAllTerms());
		assertEquals(8, m.getStartingPos());
		assertEquals(13, m.getEndingPos());
		assertEquals(6, m.getLength());
		assertEquals(11, m.getPos(0));
		assertEquals(9, m.getPos(1));
		assertEquals(8, m.getPos(2));
		assertEquals(13, m.getPos(3));
	}	
	@Test
	public void test1234in0102030320100() {
		MinimumSnippet m = get(new String[] {"0","1","0","2","0","3","0","3", "2", "0", "1", "0", "4", "0"}, new String[] {"1", "2", "3", "4"} );
		assertTrue(m.foundAllTerms());
		assertEquals(7, m.getStartingPos());
		assertEquals(12, m.getEndingPos());
		assertEquals(6, m.getLength());
		assertEquals(10, m.getPos(0));
		assertEquals(8, m.getPos(1));
		assertEquals(7, m.getPos(2));
		assertEquals(12, m.getPos(3));
	}	
}
