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
		ArrayList<String> topics = new ArrayList<>(Arrays.asList("Iowa State", "Cyclones"));
		String seed = "/wiki/Iowa_State_University";
//		String seed = "/wiki/Iowa_State_Cyclones";
//		String seed = "/wiki/Complexity_theory";
//		String seed = "/wiki/Iowa_State_University_College_of_Veterinary_Medicine";
//		String seed = "/wiki/Texas_A&M";

//		Long time = System.currentTimeMillis();
//		WikiCrawler w = new WikiCrawler(seed, 100, topics, "WikiISU.txt");
//		try {
//			w.crawl();
//			System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Exception");
//		}

		try{
			NetworkInfluenceCopySoWeDontModifySamePage network = new NetworkInfluenceCopySoWeDontModifySamePage("/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/NetworkInfluenceData.txt");
//			ArrayList<String> path = network.shortestPath("Chicago", "sdfd");
//			System.out.println(path);
			System.out.println("\n" + network.influence("Ames"));


		}catch(Exception e){
			e.printStackTrace();
		}



	}


}
