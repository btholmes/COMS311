import java.util.ArrayList;

// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**
* @author Ben Holmes, Anthony House
*/

public class HashCodeSimilarity extends newClass
{
	private String s1; 
	private String s2; 
	private int length; 
	private int prime = 31; 
	
	private HashTable S; 
	private ArrayList<Integer> s1DistinctHashes; 
	
	private HashTable T; 
	private ArrayList<Integer> s2DistinctHashes; 
	
	private ArrayList<Integer> distinctHashes; 
	private HashTable U; 

	public HashCodeSimilarity(String s1, String s2, int sLength)
	{
		this.s1 = s1; 
		this.s2 = s2; 
		this.length = sLength; 
		//1009 104729  10067
		int tableSize = 1009; 
		
		S = new HashTable(tableSize);
		s1DistinctHashes = new ArrayList<Integer>(); 
		
		T = new HashTable(tableSize); 
		s2DistinctHashes = new ArrayList<Integer>(); 
		
		distinctHashes = new ArrayList<Integer>(); 
		U = new HashTable(tableSize); 
		
		hashItOut(s1, true); 
		hashItOut(s2, false); 
	}
	
	public HashTable getS() {
		return S; 
	}
	
	public HashTable getT() {
		return T; 
	}
	
	public void addToS1(String string, int hash) {
		Tuple tuple = new Tuple(hash, string);  
		
		if(S.search(tuple) == 0) s1DistinctHashes.add(hash); 
		
		S.add(tuple);
		
		if(U.search(tuple) == 0) {
			U.add(tuple);
			distinctHashes.add(hash); 
		}
		
	}
	
	public void addToS2(String string, int hash) {
		Tuple tuple = new Tuple(hash, string); 
		
		if(T.search(tuple) == 0) s2DistinctHashes.add(hash); 
		
		T.add(tuple); 
		
		if(U.search(tuple) == 0) {
			U.add(tuple);
			distinctHashes.add(hash); 
		}
	}
	
	public int convertToInt(char ch) {
		return ch; 
	}
	
	public long computeHash(String string) {
//		long result = 0; 
//		
//		for(int i = 0; i < length; i++) {
//			result += convertToInt(string.charAt(i)) * Math.pow(prime, i); 
//		}
//		
//		return result; 
		return string.hashCode(); 
	}
	
	/**
	 * Uses Roll-over hashing method
	 * @param mainString
	 * @param s1
	 */
	public void hashItOut(String mainString, boolean s1) {
		if(mainString.length() < length) {
			return; 
		}
		if(mainString.length() == length) {
			if(s1) addToS1(mainString, mainString.hashCode()); 
			else addToS2(mainString, mainString.hashCode()); 
		}
		
		String firstString = mainString.substring(0, length); 
	
		for(int i = 0; i <= mainString.length()-length; i++) {
			String string = mainString.substring(i, i + length); 
			int hash = string.hashCode(); 
			if(s1) {
				addToS1(string, hash); 
			}
			else addToS2(string, hash); 
//			firstString = mainString.substring(i+1, i+1+length); 
//			hash -= convertToInt(mainString.charAt(i)); 
//			hash = hash/prime; 
//			hash += mainString.charAt(i+length) * Math.pow(prime, length -1); 
		}
		
//		firstString = mainString.substring(mainString.length() - length, mainString.length()); 
//		hash = computeHash(firstString); 
//		if(s1) {
//			addToS1(firstString, hash); 
////			System.out.println(firstString);
//		}
//		else addToS2(firstString, hash); 
		
		
	}

	@Override
	public float lengthOfS1()
	{
		float result = 0.0f; 
		if(s1.length() < length) return 0.0f; 
		
		for(Integer hash : s1DistinctHashes) {
			ArrayList<Tuple> list = S.search(hash); 
			if(list.size() > 0) {
				result += Math.pow(list.get(0).occurrences, 2); 
			}
		}
		
		return (float) Math.sqrt(result); 
	}

	@Override
	public float lengthOfS2()
	{
		float result = 0.0f; 
		if(s2.length() < length) return 0.0f; 
		
		for(Integer hash : s2DistinctHashes) {
			ArrayList<Tuple> list = T.search(hash); 
			if(list.size() > 0) {
				result += Math.pow(list.get(0).occurrences, 2); 
			}
		}
		
		
		return (float) Math.sqrt(result); 
		
	}

	@Override
	public float similarity()
	{
		float result = 0.0f; 
		long topSummation = 0; 
		
		for(Integer hash : distinctHashes) {
			ArrayList<Tuple> list1 = S.search(hash); 
			ArrayList<Tuple> list2 = T.search(hash); 
			if(list1.size() > 0 && list2.size()>0) {
				if(list1.size() > 1 || list2.size() > 1) {
					System.out.println(list1.size() + "  " + list2.size());
				}
				Tuple tuple1 = list1.get(0); 
				Tuple tuple2 = list2.get(0); 
				topSummation += (tuple1.occurrences * tuple2.occurrences); 
			}
		
		}
		
		float denominator = (this.lengthOfS1() * this.lengthOfS2()); 
		
		if(denominator == 0) return 0.0f; 

		result = topSummation/denominator; 
		return result; 
	}
}