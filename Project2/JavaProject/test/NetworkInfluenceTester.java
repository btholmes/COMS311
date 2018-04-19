import java.io.*;
import java.util.ArrayList;

public class NetworkInfluenceTester {

    private static final String FILE_LOCATION = "./Project2/NetworkInfluenceData.txt";

    public static void main(String[] args) throws Exception {
        TestOriginalFile();
        TestHardFile();

        System.out.println("All tests passed!");
    }

    private static void TestHardFile() throws Exception {
        EmptyFile();
        WriteToFile("9");
        WriteToFile("1 2");
        WriteToFile("1 3");
        WriteToFile("1 4");
        WriteToFile("4 3");
        WriteToFile("2 7");
        WriteToFile("3 5");
        WriteToFile("7 6");
        WriteToFile("7 8");
        WriteToFile("8 7");
        WriteToFile("6 9", false);

        NetworkInfluence networkInfluence = new NetworkInfluence(FILE_LOCATION);

        int distance;

        distance = networkInfluence.distance("1", "9");
        if(distance != 4)
            throw new Exception("Shortest distance expected to be 4. Was: " + distance);

        distance = networkInfluence.distance("7", "8");
        if(distance != 1)
            throw new Exception("Shortest distance expected to be 1. Was: " + distance);

        distance = networkInfluence.distance("9", "6");
        if(distance != -1)
            throw new Exception("Shortest distance expected to be -1. Was: " + distance);

        distance = networkInfluence.distance("9", "9");
        if(distance != 0)
            throw new Exception("Shortest distance expected to be 0. Was: " + distance);

        ArrayList<String> arrayList = networkInfluence.shortestPath("1", "5");
        if(arrayList.size() != 3)
            throw new Exception("Size is expected to be 3. Was: " + arrayList.size());
        if(!arrayList.toString().equals("[1, 3, 5]"))
            throw new Exception("Expected shortest path to be [1, 3, 5]. Was: " + arrayList.toString());
    }

    private static void TestOriginalFile() throws Exception {
        EmptyFile();
        WriteToFile("4");
        WriteToFile("Ames  Minneapolis");
        WriteToFile("Minneapolis Chicago");
        WriteToFile("Chicago Ames");
        WriteToFile("Ames Omaha");
        WriteToFile("Omaha Chicago", false);

        NetworkInfluence networkInfluence = new NetworkInfluence(FILE_LOCATION);

        String[] cities = {"Ames", "Minneapolis", "Chicago", "Omaha"};
        int[] expectedInbound = {1, 1, 2, 1};
        int[] expectedOutbound = {2, 1, 1, 1};
        int[][] expectedDistance = {{0, 1, 2, 1}, {2, 0, 1, 3}, {1, 2, 0, 2}, {2, 3, 1, 0}}; // shortest path {Ames, Minneapolis, Chicago, Omaha}

        for(int i = 0; i < cities.length; i++) {
            if(networkInfluence.outDegree(cities[i]) != expectedOutbound[i])
                throw new Exception(cities[i] + " out degree != " + expectedOutbound[i] + ". Actual: " + networkInfluence.outDegree(cities[i]));

            if(networkInfluence.inDegree(cities[i]) != expectedInbound[i])
                throw new Exception(cities[i] + " in degree != " + expectedInbound[i] + ". Actual: " + networkInfluence.inDegree(cities[i]));

            for(int j = 0; j < cities.length; j++) {
                int outputDistance = networkInfluence.distance(cities[i], cities[j]);
                if(outputDistance != expectedDistance[i][j]) {
                    throw new Exception("distance between " + cities[i] + " and " + cities[j] + " is incorrect. Expected: " + expectedDistance[i][j] + ". Actual: " + outputDistance);
                }
            }
        }

        ArrayList<String> args = new ArrayList<>();
        int distance;

        args.clear();
        args.add("Ames");
        args.add("Minneapolis");
        args.add("Chicago");
        distance = networkInfluence.distance(args, "Omaha");
        if(distance != 1)
            throw new Exception("Shortest distance expected to be 1. Was: " + distance);

        args.clear();
        args.add("Omaha");
        args.add("Minneapolis");
        args.add("Chicago");
        distance = networkInfluence.distance(args, "Ames");
        if(distance != 1)
            throw new Exception("Shortest distance expected to be 1. Was: " + distance);

        args.clear();
        args.add("Ames");
        args.add("Omaha");
        args.add("Minneapolis");
        args.add("Chicago");
        distance = networkInfluence.distance(args, "Ames");
        if(distance != 0)
            throw new Exception("Shortest distance expected to be 0. Was: " + distance);

        ArrayList<String> arrayList = networkInfluence.shortestPath("Minneapolis", "Omaha");
        if(arrayList.size() != 4)
            throw new Exception("Size is expected to be 4. Was: " + arrayList.size());
        if(!arrayList.toString().equals("[Minneapolis, Chicago, Ames, Omaha]"))
            throw new Exception("Expected shortest path to be [Minneapolis, Chicago, Ames, Omaha]. Was: " + arrayList.toString());

        arrayList = networkInfluence.shortestPath("Minneapolis", "Minneapolis");
        if(arrayList.size() != 1)
            throw new Exception("Size is expected to be 1. Was: " + arrayList.size());
        if(!arrayList.toString().equals("[Minneapolis]"))
            throw new Exception("Expected shortest path to be [Minneapolis]. Was: " + arrayList.toString());
    }

    private static void EmptyFile() throws IOException {
        OutputStream os = new FileOutputStream(FILE_LOCATION, false);
        os.close();
    }

    private static void WriteToFile(String line) throws IOException {
        WriteToFile(line, true);
    }

    private static void WriteToFile(String line, Boolean newLine) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_LOCATION, true));
        writer.write(line);

        if(newLine)
            writer.write("\n");

        writer.close();
    }

}
