import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class main {

	public static HashTable hashTable; 
	
    public static String vectorString1 = "ABFHBFDFAB";
    public static String vectorString2 = "BEAAHHDCH";
    public static String similarityString1 = "aroseisaroseisarose";
    public static String similarityString2 = "aroseisaflowerwhichisarose";

    public static int vectorShingleLength = 1;
    public static int similarityShingleLength = 4;
	
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
//        	  	line = line.replaceAll("[^A-Za-z0-9]", ""); 
        	  	line.replaceAll(" ", "")
                .replaceAll("\t", "")
                .replaceAll("\\.", "")
                .replaceAll(",", "")
                .replaceAll(":", "")
                .replaceAll(";", "");
        	  	
        	  	
        	  	s1 += line; 
        	  	line = br.readLine(); 
          }  
          
          br = new BufferedReader(test2);
          line = br.readLine(); 
          while(line != null) {
        		line = line.toLowerCase(); 
//        	  	line = line.replaceAll("[^A-Za-z0-9]", ""); 
        		line.replaceAll(" ", "")
                .replaceAll("\t", "")
                .replaceAll("\\.", "")
                .replaceAll(",", "")
                .replaceAll(":", "")
                .replaceAll(";", "");
        	  	s2 += line; 
        	  	line = br.readLine(); 
          }
          
          br.close();    
          test1.close();   
          test2.close(); 
		
          
    	    Long time = System.currentTimeMillis(); 
//    		BruteForceSimilarity brute = new BruteForceSimilarity(s1, s2, 8); 
//    		System.out.println(brute.similarity());
//      	System.out.println("Total time was " + new Double((System.currentTimeMillis() - time)/1000.0));
		
        time = System.currentTimeMillis(); 
  		HashStringSimilarity stringSimilarity = new HashStringSimilarity(s1, s2, 8);
  		System.out.println(stringSimilarity.similarity());
  		System.out.println("Total time was " + new Double((System.currentTimeMillis() - time)/1000.0));
	

  	    time = System.currentTimeMillis(); 
		HashCodeSimilarity hashCodeSimilarity = new HashCodeSimilarity(s1, s2, 8); 
		System.out.println(hashCodeSimilarity.similarity());
 		System.out.println("Total time was " + new Double((System.currentTimeMillis() - time)/1000.0));
 		
		
	}
	
}
