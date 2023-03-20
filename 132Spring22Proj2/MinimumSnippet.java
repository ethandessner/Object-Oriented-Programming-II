import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


public class MinimumSnippet {

	ArrayList<String> finalSnippet;
	int finalSnippetLength;
	Iterable <String> documentList;
	List<String> termList;
	ArrayList<String> docList;
	ArrayList<Integer> indicesOfTerms;
	int start;
	int end;

	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		ArrayList<Integer> termIndsFinal = new ArrayList<Integer>();
		documentList = document;
		termList = terms;
		ArrayList<String> snippet = new ArrayList<String>(); //holder for snippet
		ArrayList<Integer> termInds = new ArrayList<Integer>(terms.size()); //stores indices for snippet terms
		ArrayList<String> currentSnippet = new ArrayList<String>();
		docList = iterConvert(documentList);
		int currentSnippetLength;
		int minSnippetLength = docList.size() + 1;
		int firstTermIndex;
		String holder = "";
		int y = 0;
		int i = 0;
		ArrayList<Integer> allTermInds = new ArrayList<Integer>();
		for(int z = 0; z < docList.size(); z++) {
			if(terms.contains(docList.get(z))) {
				allTermInds.add(z);
			}
		}
		System.out.println(allTermInds);
		if(terms.size() == 0) { //checks to see if the terms list is empty
			throw new IllegalArgumentException("Terms is empty!");
		}else if(terms.size() == 1 && docList.containsAll(terms)){ 
			/*checks to see if there is only one term in the terms list -
			 *  snippet is created and mininmum length is created
			 */
			firstTermIndex = docList.indexOf(terms.get(0));
			snippet.add(docList.get(firstTermIndex));
			minSnippetLength = 1;
			System.out.println(snippet);
			indicesOfTerms = new ArrayList<Integer>();
			indicesOfTerms.add(firstTermIndex);
			finalSnippet = snippet;
			finalSnippetLength = 1;
			start = firstTermIndex;
			end = firstTermIndex;
			//executes if there is the terms list has more than 1 terms
		}else { 
			/*First for loop - go until you find a term in the document
			 * wrap second for loop in an if statement
			 * - if a term is found, enter this for loop and start creating a snippet
			 */
			for(String current : docList) {
				/*Second for loop - fill the next terms into the snippet array list until all terms are found
			  	- characters in terms will have their index value stored in another array - if a duplicate is found, 
			  	  start again at the second index of termInds
				- if a duplicate is found, start looping at the second term that was found 
				- EX: terms: ABC - AERBA - start at B
			 	*/
				for(int x = y; x < docList.size(); x++) {
					if(terms.contains(current)) {
						// first checks if all terms are in the snippet before adding anything more
						if(snippet.containsAll(terms)) {	
							currentSnippet = snippet;
							break;
						}
						holder = docList.get(x);
						//checks to see if the string being added is a term duplicate
						if(snippet.contains(holder) && terms.contains(holder)) { 
							holder = docList.get(allTermInds.get(i));
							System.out.println("HOLDER: " + holder);
							i++;
							x--;
							snippet.clear();
							termInds.clear();
						}else { /*if the current term is not a duplicate and is not in the snippet yet, 
							    then it can be added*/
							snippet.add(holder);
						}
					}
				}
				y++;

				if(snippet.containsAll(terms)) {
					currentSnippetLength = snippet.size();
					if(currentSnippetLength <= minSnippetLength) {
						minSnippetLength = currentSnippetLength;
						currentSnippet = new ArrayList<String>(snippet);
						termIndsFinal = new ArrayList<Integer>(termInds);
					}
					snippet.clear();
					holder = docList.get(allTermInds.get((allTermInds.size() - 1)));
					termInds.clear();
				}
			}
			//evaluating snippet size - this should execute once a new snippet has been found
			indicesOfTerms = termIndsFinal;
			finalSnippet = currentSnippet;
			finalSnippetLength = minSnippetLength;

			int indexWhere;
			String findIndices = "";
			String docListAsString = "";
			for(String docConvert : docList) {
				docListAsString += docConvert;
			}
			for(String convert : finalSnippet) {
				findIndices += convert;
			}
			indexWhere = docListAsString.indexOf(findIndices);
			ArrayList<Integer> actualIndices = new ArrayList<Integer>();
			for(int workPls = indexWhere; workPls < indexWhere + finalSnippet.size(); workPls++) {
				if(terms.contains(docList.get(workPls))) {
					System.out.println(workPls);
					actualIndices.add(workPls);
				}
			}
			indicesOfTerms = actualIndices;
			if(indicesOfTerms.size() != 0) {
				start = indicesOfTerms.get(0);
				end = indicesOfTerms.get(indicesOfTerms.size() - 1);
			}
		}
	}

	public ArrayList<String> iterConvert(Iterable<String> doc){
		ArrayList<String> ArrList = new ArrayList<String>();
		doc = documentList;
		for(String current : doc) {
			ArrList.add(current);
		}
		return ArrList;
	}

	public boolean foundAllTerms() {
		if(finalSnippet.containsAll(termList)) {
			return true;
		}
		return false;
	}

	public int getStartingPos() {
		if(foundAllTerms() == false) {
			throw new IllegalArgumentException("Snippet not found");
		}
		return start;

	}

	public int getEndingPos() {
		if(foundAllTerms() == false) {
			throw new IllegalArgumentException("Snippet not found");
		}
		return end;

	}

	public int getLength() {
		if(foundAllTerms() == false) {
			throw new IllegalArgumentException("Snippet not found");
		}
		return finalSnippetLength;

	}

	public int getPos(int index) {
		int x = 0;
		if(foundAllTerms() == false) {
			throw new IllegalArgumentException("Snippet not found");
		}
		for(int i = start; i <= end; i++) {
			if(termList.get(index).equals(docList.get(i))) {
				x = i;
			}
		}
		return x;
	}

}
