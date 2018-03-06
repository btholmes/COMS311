import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class main {

	public static HashTable hashTable; 
	
	public static void main(String[] args) throws Exception {
		String fileLocation = args[0]; // 1st arg
//		String fileLocation = args[0]; // or use this..

		System.out.println(fileLocation);

		if(fileLocation.isEmpty()) {
			throw new Exception("Enter a file location.");
		}

		String s1 = "";
		String s2 = "";

		long tStart;
		long tEnd;
		long tDelta;
		double elapsedSeconds;

		BruteForceSimilarity bruteForceSimilarity;
		HashStringSimilarity hashStringSimilarity;
		HashCodeSimilarity hashCodeSimilarity;

		String line = "";

		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation + "shak1"));
		while ((line = bufferedReader.readLine()) != null) {
			line = line.replaceAll(" ", "")
						.replaceAll("\t", "")
						.replaceAll("\\.", "")
						.replaceAll(",", "")
						.replaceAll(":", "")
						.replaceAll(";", "");

			line = line.toLowerCase();
			s1 += line;
		}

		bufferedReader.close();

		bufferedReader = new BufferedReader(new FileReader(fileLocation + "shak2"));
		while ((line = bufferedReader.readLine()) != null) {
			line = line.replaceAll(" ", "")
					.replaceAll("\t", "")
					.replaceAll("\\.", "")
					.replaceAll(",", "")
					.replaceAll(":", "")
					.replaceAll(";", "");

			line = line.toLowerCase();
			s2 += line;
		}
		bufferedReader.close();

		tStart = System.currentTimeMillis();
		bruteForceSimilarity = new BruteForceSimilarity(s1, s2, 8);
		System.out.println("Brute Force Similarity: " + bruteForceSimilarity.similarity());
		tEnd = System.currentTimeMillis();
		tDelta = tEnd - tStart;
		elapsedSeconds = tDelta / 1000.0;
		System.out.println("Elapsed seconds: " + elapsedSeconds);

		tStart = System.currentTimeMillis();
		hashCodeSimilarity = new HashCodeSimilarity(s1, s2, 8);
		System.out.println("Hash Code Similarity: " + hashCodeSimilarity.similarity());
		tEnd = System.currentTimeMillis();
		tDelta = tEnd - tStart;
		elapsedSeconds = tDelta / 1000.0;
		System.out.println("Elapsed seconds: " + elapsedSeconds);

		tStart = System.currentTimeMillis();
		hashStringSimilarity = new HashStringSimilarity(s1, s2, 8);
		System.out.println("Hash String Similarity: " + hashStringSimilarity.similarity());
		tEnd = System.currentTimeMillis();
		tDelta = tEnd - tStart;
		elapsedSeconds = tDelta / 1000.0;
		System.out.println("Elapsed seconds: " + elapsedSeconds);
	}

	public static void printHashTable() {
		ArrayList<LinkedList<Tuple>> table = hashTable.getTable(); 
		for(int i = 0; i < table.size(); i++) {
			System.out.print(i + "   ");
			LinkedList<Tuple> list = table.get(i); 
			if(list != null) {
				for(int j =0; j < list.size(); j++) {
					Tuple tuple = list.get(j); 
					System.out.print(tuple.getKey() + ", " + tuple.getValue() + " : ");
				
				}
			}else System.out.print("null");

			System.out.println();
		}
	
	}
	
}
