import java.io.IOException;

public class NetworkInfluenceTester {

    public static void main(String[] args) throws Error, IOException {
        NetworkInfluence networkInfluence = new NetworkInfluence("NetworkInfluenceData.txt");

        String[] cities = {"Ames", "Minneapolis", "Chicago", "Omaha"};
        int[] inbound = {1, 1, 2, 1};
        int[] outbound = {2, 1, 1, 1};
        int[][] distance = {{0, 1, 2, 1}, {2, 0, 1, 3}, {1, 2, 0, 2}, {2, 3, 1, 0}}; // shortest path {Ames, Minneapolis, Chicago, Omaha}

        for(int i = 0; i < cities.length; i++) {
            if(networkInfluence.outDegree(cities[i]) != outbound[i])
                throw new Error(cities[i] + " out degree != " + outbound[i]);

            if(networkInfluence.inDegree(cities[i]) != inbound[i])
                throw new Error(cities[i] + " in degree != " + inbound[i]);

            for(int j = 0; j < cities.length; j++) {
                int outputDistance = networkInfluence.distance(cities[i], cities[j]);
                if(outputDistance != distance[i][j]) {
                    throw new Error("distance between " + cities[i] + " and " + cities[j] + " is incorrect. Expected: " + distance[i][j] + ". Actual: " + outputDistance);
                }
            }
        }

        System.out.println("All tests passed!");
    }

}
