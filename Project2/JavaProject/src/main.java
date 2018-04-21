import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class main {
	
	public static void main(String[] args)  {
//		"Iowa State", "Cyclones"
		ArrayList<String> topics = new ArrayList<>(Arrays.asList(""));
		String seed = "/wiki/Computer_Science";

		Long time = System.currentTimeMillis();
		try {
//			for(int i =1; i <= 10; i++){
//				int size = i;
//				WikiCrawler w = new WikiCrawler(seed, 100, topics, "WikiCS.txt");
//				w.crawl();
//				System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");

//				String file = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/subTest.txt";
				String file = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/graph1";
//				String file = "WikiCS.txt";
				NetworkInfluence network = new NetworkInfluence(file);
//				System.out.println("Ames : " + network.influence("Ames"));
//				System.out.println("Minneapolis : " + network.influence("Minneapolis"));
//				System.out.println("Kansas : " + network.influence("Kansas"));
//				System.out.println("Chicago : " + network.influence("Chicago"));
//
//				file = "/Users/benholmes/Documents/GitHubRepos/COMS311/Project2/graph3";
//				network = new NetworkInfluence(file);
//				time = System.currentTimeMillis();
//				System.out.println("A is : " + network.influence("H"));
//				System.out.println("B is : " + network.influence("X"));
//				System.out.println("C is : " + network.influence("Y"));
//				System.out.println("D is : " + network.influence("Z"));
//				System.out.println("E is : " + network.influence("E"));
//				System.out.println("F is : " + network.influence("F"));
//				System.out.println("G is : " + network.influence("G") + "\n");
//			System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");
//
//			System.out.println(network.mostInfluentialSubModular(2));
//			time = System.currentTimeMillis();
//			System.out.println("A-X is : " + network.influence(new ArrayList<>(Arrays.asList("A", "A"))));
//			System.out.println("A-Y is : " + network.influence(new ArrayList<>(Arrays.asList("A", "B"))));
//			System.out.println("A-Z is : " + network.influence(new ArrayList<>(Arrays.asList("A", "C"))));
//			System.out.println("A-D is : " + network.influence(new ArrayList<>(Arrays.asList("A", "D"))));
//			System.out.println("A-E is : " + network.influence(new ArrayList<>(Arrays.asList("A", "E"))));
//			System.out.println("A-F is : " + network.influence(new ArrayList<>(Arrays.asList("A", "F"))));
//			System.out.println("A-X is : " + network.influence(new ArrayList<>(Arrays.asList("A", "G"))));
////			System.out.println((System.currentTimeMillis() - time)/1000 + "seconds");
//
				ArrayList<String> result = network.mostInfluentialDegree(6);
				System.out.println("Total Influence " + network.influence(result));
				int i = 0;
				for(String item : result){
//					System.out.println(i + " " + item + " " + network.influence(item));
					System.out.println(i + " " + item + " " + network.graphVertexHashMap.get(item).getOutDegrees().size());
					i++;
				}

				result = network.mostInfluentialModular(6);
				System.out.println("Total Influence " + network.influence(result));
				i= 0;
				for(String item : result){
					System.out.println(i + " " + item + " " + network.influence(item));
					i++;
				}

				result = network.mostInfluentialSubModular(6);
				System.out.println("Total Influence " + network.influence(result));
				i= 0;
				for(String item : result){
					System.out.println(i + " " + item + " " + network.influence(item));
					i++;
				}

//			int i = 0;
//			for(String vertex : network.graphVertexHashMap.keySet()){
//				System.out.println(i + " , " + vertex + " "  + network.influence(vertex));
//				i++;
//			}


//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception");
		}

	}


}
