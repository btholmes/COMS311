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

public class BruteForceSimilarity extends newClass
{
	// member fields and other member methods
	private String s1;
	private String s2;
	private int shingleLength;

	private static ArrayList<String> s1DistinctStrings = new ArrayList<>();
	private static ArrayList<String> s2DistinctStrings = new ArrayList<>();

	private static ArrayList<Integer> S = new ArrayList<>();
	private static ArrayList<Integer> T = new ArrayList<>();

	private static ArrayList<String> distinctStrings = new ArrayList<>();

	public BruteForceSimilarity(String s1, String s2, int sLength)
	{
		// implementation
		this.s1 = s1; 
		this.s2 = s2; 
		this.shingleLength = sLength;
		
		hashItOut(s1, true); 
		hashItOut(s2, false);  
			
	}
	
	public static int getS1Count(String val) {
		if(!s1DistinctStrings.contains(val)) return 0; 
		
		int index = s1DistinctStrings.indexOf(val); 		
		return S.get(index); 
	}
	
	public static void addToS1(String val) {
		if(!s1DistinctStrings.contains(val)) {
			s1DistinctStrings.add(val); 
			S.add(1); 
		}else {
			int index = s1DistinctStrings.indexOf(val); 
			S.set(index, S.get(index)+1); 
		}
		addToDistinctStrings(val); 
	}
	
	public static int getS2Count(String val) {
		if(!s2DistinctStrings.contains(val)) return 0; 
		
		int index = s2DistinctStrings.indexOf(val); 		
		return T.get(index); 
	}
	
	public static void addToS2(String val) {
		if(!s2DistinctStrings.contains(val)) {
			s2DistinctStrings.add(val); 
			T.add(1); 
		}else {
			int index = s2DistinctStrings.indexOf(val); 
			T.set(index, T.get(index)+1); 
		}	
		addToDistinctStrings(val); 
	}
	
	public static void addToDistinctStrings(String value) {
		if(!distinctStrings.contains(value)) distinctStrings.add(value); 
	}
	

	public void hashItOut(String mainString, boolean s1) {
		if(mainString.length() < shingleLength) {
			return; 
		}
		if(mainString.length() == shingleLength) {
			if(s1) addToS1(mainString); 
			else addToS2(mainString); 
		}
		
		String firstString;
		
		for(int i = 0; i <= mainString.length()- shingleLength; i++) {
			firstString = mainString.substring(i, i+ shingleLength);
			if(s1) {
				addToS1(firstString);
			}
			else {
				addToS2(firstString);
			}
		}
	}

	@Override
	public float lengthOfS1()
	{
		float result = 0.0f; 
		if(s1.length() < shingleLength) return 0.0f;
		
		for(String string : s1DistinctStrings) {
			result += Math.pow(getS1Count(string), 2);  
		}
		
		return (float) Math.sqrt(result); 
	}
	@Override
	public float lengthOfS2()
	{
		// implementation
		float result = 0.0f; 
		if(s2.length() < shingleLength) return 0.0f;
		
		for(String string : s2DistinctStrings) {
			result += Math.pow(getS2Count(string), 2);  
		}
//		System.out.println("Length of s2 before sqrt " + result);
		
		return (float) Math.sqrt(result); 
	}
	@Override
	public float similarity()
	{
		// implementation
		float result = 0.0f; 
		long topSummation = 0; 
		
		for(String string : distinctStrings) {
			topSummation += (getS1Count(string) * getS2Count(string)); 
		}
//		System.out.println("Top Summation is " + topSummation);
		
		float denominator = (this.lengthOfS1() * this.lengthOfS2()); 
//		System.out.println("Denominator is " + denominator);
		
		if(denominator == 0) return 0.0f; 

		result = topSummation/denominator; 
		return result; 
	}
}