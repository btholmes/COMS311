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
		String seed = "/wiki/Complexity_theory";
//		String seed = "/wiki/Iowa_State_University_College_of_Veterinary_Medicine";
		WikiCrawler w = new WikiCrawler(seed, 20, topics, "WikiISU.txt");
		try {
			w.crawl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception");
		}

	}


}
