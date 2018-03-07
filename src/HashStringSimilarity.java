import java.util.ArrayList;
import java.util.LinkedList;

// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**
* @author Ben Holmes, Anthony House
*/

public class HashStringSimilarity extends newClass
{

	private String s1; 
	private String s2; 
	private int length; 
	private int prime = 31; 
	
	private HashTable S; 
	private ArrayList<String> s1DistinctStrings; 
	
	private HashTable T; 
	private ArrayList<String> s2DistinctStrings; 
	
	private ArrayList<String> distinctStrings; 
	private HashTable U; 

	public HashStringSimilarity(String s1, String s2, int sLength)
	{

		this.s1 = s1; 
		this.s2 = s2; 
		this.length = sLength; 
		
		//1009 104729  10067
		int tableSize = 1009; 
		
		S = new HashTable(tableSize);
		s1DistinctStrings = new ArrayList<String>(); 
		T = new HashTable(tableSize); 
		s2DistinctStrings = new ArrayList<String>(); 
		
		distinctStrings = new ArrayList<String>(); 
		U = new HashTable(tableSize); 
		
		
		hashItOut(s1, true); 
		hashItOut(s2, false); 
		
	}

	public void addToS1(String string, int key) {
		Tuple tuple = new Tuple(key, string);  
		
		if(S.search(tuple) == 0) s1DistinctStrings.add(string); 
		
		S.add(tuple);
		
		if(U.search(tuple) == 0) {
			U.add(tuple);
			distinctStrings.add(string); 
		}
	}
	public void addToS2(String string, int key) {
		Tuple tuple = new Tuple(key, string); 
		
		if(T.search(tuple) == 0) s2DistinctStrings.add(string); 
		
		T.add(tuple); 
		
		if(U.search(tuple) == 0) {
			U.add(tuple);
			distinctStrings.add(string); 
		}
	}
	
	public int convertToInt(char ch) {
		return ch; 
	}
	
	public int computeHash(String string) {
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
	 * Function generates hashes for each shingle, and adds them to appropriate set, s1 or s2. 
	 * @param mainString
	 * @param s1
	 */
	public void hashItOut(String mainString, boolean s1) {
		if(mainString.length() < length) {
			return; 
		}
		if(mainString.length() == length) {
			if(s1) addToS1(mainString, computeHash(mainString)); 
			else addToS2(mainString, computeHash(mainString)); 
		}
		
//		String firstString = mainString.substring(0, length); 
//		int hash = computeHash(firstString); 
		
		String firstString;  
		int hash; 
		for(int i = 0; i <= mainString.length()-length; i++) {
			firstString = mainString.substring(i, i+ length); 
			hash = computeHash(firstString); 
			if(s1) addToS1(firstString, hash); 
			else addToS2(firstString, hash); 
			
//			if(s1) addToS1(firstString, hash); 
//			else addToS2(firstString, hash); 
//			firstString = mainString.substring(i+1, i+1+length); 
//			hash -= convertToInt(mainString.charAt(i)); 
//			hash = hash/prime; 
//			hash += mainString.charAt(i+length) * Math.pow(prime, length -1);
		}
	}

	@Override
	public float lengthOfS1()
	{
		float result = 0.0f; 
		if(s1.length() < length) return 0.0f; 
		
		for(String string : s1DistinctStrings) {
			Tuple tuple = new Tuple(string.hashCode(), string); 
			result += Math.pow(S.search(tuple), 2);  
		}
		
		return (float) Math.sqrt(result); 
	}

	@Override
	public float lengthOfS2()
	{
		float result = 0.0f; 
		if(s2.length() < length) return 0.0f; 
		
		for(String string : s2DistinctStrings) {
			Tuple tuple = new Tuple(string.hashCode(), string); 
			result += Math.pow(T.search(tuple), 2);  
		}
		return (float) Math.sqrt(result); 
	}

	@Override
	public float similarity()
	{
		float result = 0.0f; 
		long topSummation = 0; 
		
		for(String string : distinctStrings) {
			Tuple tuple = new Tuple(string.hashCode(), string); 
			topSummation += (S.search(tuple) * T.search(tuple)); 
		}
		
		float denominator = (this.lengthOfS1() * this.lengthOfS2()); 
		
		if(denominator == 0) return 0.0f; 

		result = topSummation/denominator; 
		return result; 
	}
}