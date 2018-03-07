import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class main {

	public static HashTable hashTable; 
	
	public static void printResults(newClass classType) {
		Long time = System.currentTimeMillis(); 
		System.out.println(classType.similarity());
		System.out.println("Total time was " + (System.currentTimeMillis() - time));
	}
	
	public static void main(String[] args) throws IOException {
		 FileReader test1 =new FileReader("/Users/benholmes/eclipse-workspace/COMS311P1/src/shak1.txt");    
		 FileReader test2 = new FileReader("/Users/benholmes/eclipse-workspace/COMS311P1/src/shak2.txt"); 
         BufferedReader br=new BufferedReader(test1);    
  
          int i;    
          String line; 
          String s1 = ""; 
          String s2 = ""; 
          line = br.readLine(); 
          while(line != null){  
        	  	line = line.toLowerCase(); 
        	  	line = line.replaceAll("[^A-Za-z0-9]", ""); 
//        	  	line.replaceAll(" ", "")
//                .replaceAll("\t", "")
//                .replaceAll("\\.", "")
//                .replaceAll(",", "")
//                .replaceAll(":", "")
//                .replaceAll(";", "");
        	  	
        	  	
        	  	s1 += line; 
        	  	line = br.readLine(); 
          }  
          
          br = new BufferedReader(test2);
          line = br.readLine(); 
          while(line != null) {
        		line = line.toLowerCase(); 
        	  	line = line.replaceAll("[^A-Za-z0-9]", ""); 
//        		line.replaceAll(" ", "")
//                .replaceAll("\t", "")
//                .replaceAll("\\.", "")
//                .replaceAll(",", "")
//                .replaceAll(":", "")
//                .replaceAll(";", "");
        	  	s2 += line; 
        	  	line = br.readLine(); 
          }
          
          br.close();    
          test1.close();   
          test2.close(); 
		
//         System.out.println("Read files");
//         s1 = "aroseisaroseisarose"; 
//         s2 = "aroseisaflowerwhichisarose"; 
		
		HashStringSimilarity stringSimilarity = new HashStringSimilarity(s1, s2, 8);
//		System.out.println(stringSimilarity.similarity());
		
		BruteForceSimilarity brute = new BruteForceSimilarity(s1, s2, 8); 
//		System.out.println();
//		System.out.println(brute.similarity());
		
		HashCodeSimilarity hashCodeSimilarity = new HashCodeSimilarity(s1, s2, 8); 
//		System.out.println(hashCodeSimilarity.similarity());
		
		printResults(brute); 
		printResults(stringSimilarity); 
		printResults(hashCodeSimilarity); 

//		hashCodeSimilarity.getS().printTable();
//		hashCodeSimilarity.getT().printTable(); 

		
//		System.out.println('a'-96);
//		LinkedList<Tuple> list = new LinkedList<Tuple>(); 
//		list.add(new Tuple(1, "what")); 
		
	
		
//		hashTable = new HashTable(10); 
//		
//		for(int i = 0; i < 10; i++) {
//			Tuple tuple = new Tuple(i, "what"); 
//			Tuple tuple2 = new Tuple(0, "what"); 
//			hashTable.add(tuple);
//			hashTable.add(tuple2);
//		}
//		hashTable.remove(new Tuple(0, "what"));
//		hashTable.remove(new Tuple(0, "what"));
//		hashTable.remove(new Tuple(0, "what"));
//		hashTable.remove(new Tuple(0, "what"));
//		
//		printHashTable(); 
//		System.out.println();
//		System.out.println(hashTable.loadFactor());
//		System.out.println(hashTable.maxLoad());
//		System.out.println(hashTable.averageLoad());
//		System.out.println(hashTable.search(1).toString());
		
		
//		System.out.println(hashTable.numElements());
//		System.out.println("abc".hashCode());
//		System.out.println("abc".hashCode());
//		System.out.println(Math.sqrt(0));
		
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
