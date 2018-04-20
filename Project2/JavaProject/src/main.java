import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class main {
	
	public static void main(String[] args)  {
//		"Iowa State", "Cyclones"
		ArrayList<String> topics = new ArrayList<>(Arrays.asList(""));
//		String seed = "/wiki/Iowa_State_University";
//		String seed = "/wiki/Iowa_State_Cyclones";
//		String seed = "/wiki/Complexity_theory";
//		String seed = "/wiki/Iowa_State_University_College_of_Veterinary_Medicine";
		String seed = "/wiki/Computer_Science";

		Long time = System.currentTimeMillis();
		try {
			for(int i =1; i <= 10; i++){
				int size = i*100;
				WikiCrawler w = new WikiCrawler(seed, size, topics, "WikiCS.txt");
				w.crawl();
//				System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");

				String file = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/WikiCS.txt";
				NetworkInfluenceCopySoWeDontModifySamePage network = new NetworkInfluenceCopySoWeDontModifySamePage(file);
//				System.out.println("Ames : " + network.influence("Ames"));
//				System.out.println("Minneapolis : " + network.influence("Minneapolis"));
//				System.out.println("Kansas : " + network.influence("Kansas"));
//				System.out.println("Chicago : " + network.influence("Chicago"));
//
//				file = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/graph3";
//				network = new NetworkInfluenceCopySoWeDontModifySamePage(file);
//				time = System.currentTimeMillis();
//				System.out.println("A is : " + network.influence("A"));
//				System.out.println("B is : " + network.influence("B"));
//				System.out.println("C is : " + network.influence("C"));
//				System.out.println("D is : " + network.influence("D"));
//				System.out.println("E is : " + network.influence("E"));
//				System.out.println("F is : " + network.influence("F"));
//				System.out.println("G is : " + network.influence("G") + "\n");
//			System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");
//
//			time = System.currentTimeMillis();
//			System.out.println("A-A is : " + network.influence(new ArrayList<>(Arrays.asList("A", "A"))));
//			System.out.println("A-B is : " + network.influence(new ArrayList<>(Arrays.asList("A", "B"))));
//			System.out.println("A-C is : " + network.influence(new ArrayList<>(Arrays.asList("A", "C"))));
//			System.out.println("A-D is : " + network.influence(new ArrayList<>(Arrays.asList("A", "D"))));
//			System.out.println("A-E is : " + network.influence(new ArrayList<>(Arrays.asList("A", "E"))));
//			System.out.println("A-F is : " + network.influence(new ArrayList<>(Arrays.asList("A", "F"))));
//			System.out.println("A-G is : " + network.influence(new ArrayList<>(Arrays.asList("A", "G"))));
//			System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");

//				time = System.currentTimeMillis();
//				System.out.println(network.mostInfluentialDegree(5*i - 1));
//				System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");

//				time = System.currentTimeMillis();
//				System.out.println(network.mostInfluentialModular(5*i - 1));
//				System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");

				time = System.currentTimeMillis();
				System.out.println(network.mostInfluentialSubModular((size*9)/10));
				System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception");
		}

	}


}
