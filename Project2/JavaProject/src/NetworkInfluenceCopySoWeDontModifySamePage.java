import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Hugh Potter
 */


public class NetworkInfluenceCopySoWeDontModifySamePage
{
    private static final int INF = Integer.MAX_VALUE;
    private int numVertices;
    private HashMap<String, GraphVertex> graphVertexHashMap;

    public NetworkInfluenceCopySoWeDontModifySamePage(String graphData) throws IOException {
        graphVertexHashMap = new HashMap<>();
        File f = new File(graphData);
        if(!f.exists() || f.isDirectory()) {
            System.out.println("File specified for Network Influence does not exist. Returning.");
            return;
        }

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(graphData));
        String line = bufferedReader.readLine();

        if(line == null) {
            System.out.println("Error reading file in Network Influence. Returning.");
            return;
        }

        try {
            numVertices = Integer.parseInt(line);
        } catch (Exception e) {
            System.out.println("Error parsing the number of vertices in Network Influence. Returning.");
            return;
        }

        while((line = bufferedReader.readLine()) != null) {
            if(line.equals(""))
                continue;

            String[] vertices = line.split("\\s+");

            if(vertices.length != 2) {
                System.out.println("Improperly formatted line in Network Influence file. Expected only two names of vertices.");
                return;
            }

            GraphVertex graphVertex1 = graphVertexHashMap.containsKey(vertices[0]) ? graphVertexHashMap.get(vertices[0]) : new GraphVertex(vertices[0]);
            GraphVertex graphVertex2 = graphVertexHashMap.containsKey(vertices[1]) ? graphVertexHashMap.get(vertices[1]) : new GraphVertex(vertices[1]);

            graphVertex1.AddOutDegreeVertex(graphVertex2);
            graphVertex2.AddInDegreeVertex(graphVertex1);

            graphVertexHashMap.putIfAbsent(graphVertex1.getVertexName(), graphVertex1);
            graphVertexHashMap.putIfAbsent(graphVertex2.getVertexName(), graphVertex2);
        }

        bufferedReader.close();

        if(graphVertexHashMap.size() != numVertices) {
            System.out.println("WARNING! The number of vertices at the beginning of the file in Network Influence does not match the input of " + numVertices);
        }
    }

    public int outDegree(String v)
    {
        GraphVertex graphVertex = graphVertexHashMap.get(v);

        if(graphVertex == null) {
            System.out.println("Vertex does not exist.");
            return -1;
        }

        return graphVertex.GetOutDegreeSize();
    }

    // TODO remove or set to private.
    public int inDegree(String v) {
        GraphVertex graphVertex = graphVertexHashMap.get(v);

        if(graphVertex == null) {
            System.out.println("Vertex does not exist.");
            return -1;
        }

        return graphVertex.GetInDegreeSize();
    }

    private ArrayList<String> backTrackPath(HashMap<String, String> prev, String u, String v){
        ArrayList<String> result = new ArrayList<>();
        if(u.equals(v)){
            result.add(u);
            return result;
        }
        if(!graphVertexHashMap.containsKey(v) || !graphVertexHashMap.containsKey(u)) return result;
        if(prev.get(v) == null) return result;

        result.add(v);
        String item = prev.get(v);
        while(item != null ){
            result.add(item);
            item = prev.get(item);
            if(item != null && item.equals(u)){
                result.add(u);
                break;
            }

        }
//        System.out.println(result);
        Collections.reverse(result);

        return result;
    }

    public ArrayList<String> shortestPath(String u, String v)
    {
        ArrayList<String> path = new ArrayList<>();
        HashMap<String, Boolean> flag = new HashMap<>();
        HashMap<String, String> prev = new HashMap<>();

        Queue<GraphVertex> queue = new LinkedList<>();
        HashMap<String, Boolean> visited = new HashMap<>();

        GraphVertex src = graphVertexHashMap.get(u);
        flag.put(src.getVertexName(), true);
        queue.add(src);
        visited.put(src.getVertexName(), true);

        while(!queue.isEmpty()){
            GraphVertex node = queue.poll();

            HashMap<String, GraphVertex> edges = node.getOutDegrees();
            Iterator it = edges.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                if(flag.get(pair.getKey()) == null || flag.get(pair.getKey()) == false){
                    String vertex = (String)pair.getKey();
                    flag.put(vertex, true);
                    prev.put(vertex, node.getVertexName());
                    queue.add(edges.get(pair.getKey()));
                }
            }

        }
        path = backTrackPath(prev, u, v);
        return path;
    }

//    Shortest path between two nodes
    public int distance(String u, String v)
    {
        if(u.equals(v))
            return 0;

        ArrayList<String> val = shortestPath(u, v);

        return val.size() == 0 ? -1 : val.size() -1;
//        return val.size();
    }

//    Shortest path again
    public int distance(ArrayList<String> s, String v)
    {
        Integer shortestPath = null;

        for(String str : s) {
            int distance = distance(str, v);

            if(distance != -1 && shortestPath == null) {
                shortestPath = distance;
            }
            else if(distance != -1 && distance < shortestPath) {
                shortestPath = distance;
            }
        }

        return shortestPath == null ? -1 : shortestPath;
    }

    private int gety(String x, int i){
        int y = 0;
        if(i ==0) return 1;
        if(i == 1) return outDegree(x);
        else{
            ArrayList<String> allVertexes = new ArrayList<>(graphVertexHashMap.keySet());
            for(String vertex : allVertexes){
                if(distance(x, vertex) == i){
                    y+=1;
                }
            }
        }
        return y;
    }

    public float influence(String u)
    {
        // implementation
        float sum = 0;
        for(int i =0; i < numVertices; i++){
            int y = gety(u, i);
            sum += (1/(Math.pow(2,i)) * y);
        }
        return sum;
    }

    public float influence(ArrayList<String> s)
    {
        // implementation

        // replace this:
        return -1f;
    }

    public ArrayList<String> mostInfluentialDegree(int k)
    {
        // implementation

        // replace this:
        return null;
    }

    public ArrayList<String> mostInfluentialModular(int k)
    {
        // implementation

        // replace this:
        return null;
    }

    public ArrayList<String> mostInfluentialSubModular(int k)
    {
        // implementation

        // replace this:
        return null;
    }


    private void ResetBFSAttributes() {
        graphVertexHashMap.forEach((key, value) -> {
            value.setVisited(false);
            value.setParentBFS(null);
            value.setDistanceFromStart(INF);
        });
    }
}