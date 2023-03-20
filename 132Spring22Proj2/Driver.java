import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Driver{

	public static void main(String[] args) {
		ArrayList<String> document = new ArrayList<String>();
		ArrayList<String> terms = new ArrayList<String>();
		//terms.add("1");
//		terms.add("B");
//		terms.add("C");
		terms.add("1");
		terms.add("2");
	//	terms.add("3");
		//terms.add("4");
		
		document = new ArrayList<String>
		//(Arrays.asList("0","1","0","2","0","3","0","3","2","0","1","0","4"));
		(Arrays.asList("0","0","1","0","2","1","0","0","0"));
//		document.add("1");
//		document.add("2");
//		document.add("3");
//		document.add("C");
//		document.add("A");
//		document.add("B");
//		document.add("F");
//		document.add("B");
		MinimumSnippet test = new MinimumSnippet(document, terms);
		//System.out.println(test);
	}

}
