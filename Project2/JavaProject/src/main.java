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
		ArrayList<String> topics = new ArrayList<>(Arrays.asList());
//		String seed = "/wiki/Iowa_State_University";
//		String seed = "/wiki/Iowa_State_Cyclones";
//		String seed = "/wiki/Complexity_theory";
//		String seed = "/wiki/Iowa_State_University_College_of_Veterinary_Medicine";
		String seed = "/wiki/Computer_Science";

//		Long time = System.currentTimeMillis();
//		WikiCrawler w = new WikiCrawler(seed, 100, topics, "WikiCS.txt");
//		try {
//			w.crawl();
//			System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Exception");
//		}

		try{
//			String file = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/graph1";
//			NetworkInfluenceCopySoWeDontModifySamePage network = new NetworkInfluenceCopySoWeDontModifySamePage(file);
//			ArrayList<String> path = network.shortestPath("Ames", "Ames");
//			System.out.println(path);

//			ArrayList<String> places = new ArrayList<>(Arrays.asList("Chicago", "Chicago"));
//			int distance = network.distance(places, "Minneapolis");
//			System.out.println(distance);




//			String file = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/graph3";
//			NetworkInfluenceCopySoWeDontModifySamePage network = new NetworkInfluenceCopySoWeDontModifySamePage(file);
////			ArrayList<String> set = new ArrayList<>(Arrays.asList("A", "C"));
////			System.out.println(network.influence(set));
////			System.out.println(network.distance(set, "B"));
//			System.out.println(network.mostInfluentialDegree(10));
//			System.out.println(network.mostInfluentialModular(10));

			String file = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/wikiCS.txt";
			NetworkInfluenceCopySoWeDontModifySamePage network = new NetworkInfluenceCopySoWeDontModifySamePage(file);
			System.out.println(network.mostInfluentialDegree(10));


		}catch(Exception e){
			e.printStackTrace();
		}



	}


}
