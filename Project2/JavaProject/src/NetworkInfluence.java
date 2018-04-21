import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Anthony House, Ben Holmes
 */


public class NetworkInfluence
{
    private static final int INF = Integer.MAX_VALUE;
    private int numVertices;
    public HashMap<String, GraphVertex> graphVertexHashMap;
    private HashMap<String, Float> influences;
    static boolean approximate;
    static float ratio = 0.5f;


    public NetworkInfluence(String graphData) throws IOException {
        graphVertexHashMap = new HashMap<>();
        approximate = false;
        ratio = 0.5f;
        influences = new HashMap<>();

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
            numVertices = Integer.parseInt(line.trim());
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


            graphVertexHashMap.put(graphVertex1.getVertexName(), graphVertex1);
            graphVertexHashMap.put(graphVertex2.getVertexName(), graphVertex2);
        }

        bufferedReader.close();

        if(graphVertexHashMap.size() != numVertices) {
            System.out.println("WARNING! The number of vertices at the beginning of the file in Network Influence does not match the input of " + numVertices);
        }
    }

//    O(1)
    public int outDegree(String v)
    {
        GraphVertex graphVertex = graphVertexHashMap.get(v);

        if(graphVertex == null) {
            System.out.println("Vertex does not exist.");
            return -1;
        }

        return graphVertex.GetOutDegreeSize();
    }

//    O(1)
    public int inDegree(String v) {
        GraphVertex graphVertex = graphVertexHashMap.get(v);

        if(graphVertex == null) {
            System.out.println("Vertex does not exist.");
            return -1;
        }

        return graphVertex.GetInDegreeSize();
    }

//    O(n) + Collections.sort()
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

//    O(n + e) + backtrackPath()
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

//    O(shortestPath())
    public int distance(String u, String v)
    {
        if(u.equals(v))
            return 0;

        ArrayList<String> val = shortestPath(u, v);

        return val.size() == 0 ? -1 : val.size() -1;
//        return val.size();
    }

//    O(n * shortestPath())
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


    //Gets min distance from a node to all other nodes, and stores it in nodeDistances   Key = node, value = min distance
    //Also stores amount of nodes at each distance in distances Key = distance, value = amount of nodes

//    O(n + e) = O(nodes + edges)   it's a BFS
    private void getMinDistances(String u, HashMap<Integer, Integer> distances, HashMap<String, Integer> nodeDistances){
        if(!graphVertexHashMap.containsKey(u)) return;

        Queue<String> queue = new LinkedList<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        queue.add(u);
        visited.put(u, true);
        int dist = 0;

        distances.put(dist, 1);
        nodeDistances.put(u, 0);

        while(!queue.isEmpty()){
            String node = queue.poll();
            dist += 1;
            boolean found = false;
            for(String edge : graphVertexHashMap.get(node).getOutDegrees().keySet()){
                if(!visited.containsKey(edge)){
                    found = true;
                    visited.put(edge, true);
                    queue.add(edge);
                    if(distances.get(dist) == null){
                        distances.put(dist, 1);
                    }
                    else{
                        distances.put(dist, distances.get(dist) + 1);
                    }
                    if(nodeDistances.containsKey(edge)){
                        if(dist < nodeDistances.get(edge)){
                            nodeDistances.put(edge, dist);
                        }
                    }else{
                        nodeDistances.put(edge, dist);
                    }
                }
            }
            if(!found) dist -=1;
        }

    }

    //Computes influence of a node, given its distances from all other nodes
//    O(n)
    private float getTotal(HashMap<Integer, Integer> distances){
        float result = 0.0f;
        Iterator it = distances.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            result += (1/(Math.pow(2, (Integer)pair.getKey()))) * (Integer)pair.getValue();
        }
        return result;
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

//    O(n) or Best Case O(1) if node u has been calculated before
    public float influence(String u)
    {
        if(influences.containsKey(u)) return influences.get(u);
        HashMap<Integer, Integer> distances = new HashMap<>();
        HashMap<String, Integer> nodeDistances = new HashMap<>();
        getMinDistances(u, distances, nodeDistances);

        if(distances.size() == 0) return 0.0f;

        float sum = getTotal(distances);
        influences.putIfAbsent(u, sum);
        return sum;

//        float sum = 0.0f;
//        for(int i =0; i < numVertices; i++){
//            int y = gety(u, i);
//            sum += (1/(Math.pow(2,i)) * y);
//        }
//
//        return sum;
    }

    private int gety(ArrayList<String> s, int i){
        int y = 0;
        if(i ==0) return s.size();
        else{
            ArrayList<String> allVertexes = new ArrayList<>(graphVertexHashMap.keySet());
            for(String vertex : allVertexes){
                if(distance(s, vertex) == i){
//                    System.out.println(vertex);
                    y+=1;
                }
            }
        }
        return y;
    }

    //Non multi set
//    O(s(n+e)) + O(n)+ O(n) to get total
    public float influence(ArrayList<String> s)
    {
        HashMap<Integer, Integer> distances = new HashMap<>();
        HashMap<String, Integer> nodeDistances = new HashMap<>();

        for(String node : s){
            if(!graphVertexHashMap.containsKey(node)) continue;
            //At the end nodeDistances will contain min distance from all nodes to all other nodes
            getMinDistances(node, distances, nodeDistances);
        }
        distances = new HashMap<>();
        Iterator it = nodeDistances.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            Integer distance = (Integer) entry.getValue();
            if(distances.containsKey(distance)){
                distances.put(distance, distances.get(distance)+1);
            }else{
                distances.put(distance, 1);
            }
        }
        return getTotal(distances);



//        float sum = 0.0f;
//        for(int i =0; i < numVertices; i++){
//            int y = gety(s, i);
////            System.out.println("i is " + i  + " and nodes at distance are " + y);
//            sum += (1/(Math.pow(2,i)) * y);
//        }
//        return sum;
    }

    //Takes O(n) + Collections.sort()
    public void getDegreeMaps(HashMap<Integer, ArrayList<String>> allValues, ArrayList<Integer> maxValues){
        Iterator it = graphVertexHashMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            String vertex = (String)pair.getKey();
            int outDegree = outDegree(vertex);
            if(allValues.get(outDegree) == null){
                ArrayList<String> list = new ArrayList<String>();
                list.add(vertex);
                allValues.put(outDegree, list);
            } else{
                allValues.get(outDegree).add(vertex);
            }
            maxValues.add(outDegree);
        }
        Collections.sort(maxValues);
    }

//    O(n)
    public ArrayList<String> mostInfluentialDegree(int k)
    {
        if(k == 0) return new ArrayList<>();
        if(k >= graphVertexHashMap.size()) return new ArrayList<>(graphVertexHashMap.keySet());

        HashMap<Integer, ArrayList<String>> allDegrees = new HashMap<>();
        ArrayList<Integer> maxDegrees = new ArrayList<>();
        getDegreeMaps(allDegrees, maxDegrees);

        ArrayList<String> result = new ArrayList<String>();
        int added = 0;
        int prev = Integer.MIN_VALUE;
        while(k > 0 && added < maxDegrees.size()){
            for(int i = maxDegrees.size()-1 ; i >= 0; i--){
                Integer value = maxDegrees.get(i);
                if(value == prev) continue;

                ArrayList<String> nodes = allDegrees.get(value);

                if(nodes.size() <= k && (added + nodes.size() < maxDegrees.size())) {
                    result.addAll(nodes);
                    k -= nodes.size();
                    added += nodes.size();
                } else{
                    while(k > 0 && added < maxDegrees.size()){
                        for(String node : nodes){
                            result.add(node);
                            k--;
                            added++;
                            if(k <= 0 || added >= maxDegrees.size()) break;
                        }
                        if(k <= 0 || added >= maxDegrees.size()) break;
                    }
                }
                if(k <= 0 || added >= maxDegrees.size()) break;
                prev = value;
            }
        }

        return result;
    }

    //Takes O(n) + O(n)
    public void getInfluentialModularMaps(HashMap<Float, ArrayList<String>> allValues, ArrayList<Float> maxValues){
        Iterator it = graphVertexHashMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            String vertex = (String)pair.getKey();
            float influence = influence(vertex);
            if(allValues.get(influence) == null){
                ArrayList<String> list = new ArrayList<>();
                list.add(vertex);
                allValues.put(influence, list);
            } else{
                allValues.get(influence).add(vertex);
            }
            maxValues.add(influence);
        }
        Collections.sort(maxValues);
    }

//    O(n)
    public ArrayList<String> mostInfluentialModular(int k)
    {
        if(k ==0) return new ArrayList<String>();
        if(k >= graphVertexHashMap.size()) return new ArrayList<String>(graphVertexHashMap.keySet());


        HashMap<Float, ArrayList<String>> allInfluences = new HashMap<>();
        ArrayList<Float> maxInfluences = new ArrayList<>();
        getInfluentialModularMaps(allInfluences, maxInfluences);


        ArrayList<String> result = new ArrayList<String>();
        int added = 0;
        Float prev = (float)Integer.MIN_VALUE;
        while(k > 0 && added < maxInfluences.size()){
            for(int i = maxInfluences.size()-1 ; i >= 0; i--){
                Float value = maxInfluences.get(i);
                if(value.equals(prev)) {
                    continue;
                }

                ArrayList<String> nodes = allInfluences.get(value);
                if(nodes.size() <= k && (added + nodes.size() < maxInfluences.size())) {
                    result.addAll(nodes);
                    k -= nodes.size();
                    added += nodes.size();
                } else{
                    while(k > 0 && added < maxInfluences.size()){
                        for(String node : nodes){
                            result.add(node);
                            k--;
                            added++;
                            if(k <= 0 || added >= maxInfluences.size()) break;
                        }
                        if(k <= 0 || added >= maxInfluences.size()) break;
                    }
                }
                if(k <= 0 || added >= maxInfluences.size()) break;
                prev = value;
            }
        }

        return result;
    }


//    O(   k *( n + ( s(n+e) + n + n) )   )  = O(k*(n^2)*(n+e))  approx.. O(kn^2)
    public ArrayList<String> mostInfluentialSubModular(int k)
    {
        if(k >= graphVertexHashMap.size()){
            return new ArrayList<String>(graphVertexHashMap.keySet());
        }
        if(k ==0) return new ArrayList<String>();
        double changling = (double)k/numVertices;

//        if(numVertices >= 900 && (numVertices - k <= 70 )
//                || numVertices >= 800 && (numVertices - k <= 56 )
//                || numVertices >= 700 && (numVertices - k <= 49 )
//                || numVertices >= 600 && (numVertices - k <= 40 )
//                || numVertices >= 500 && (numVertices - k <= 35 )
//                || numVertices >= 400 && (numVertices - k <= 28 )
//                || numVertices >= 300 && (numVertices - k <= 21 )
//                || numVertices >= 200 && (numVertices - k <= 14 )
//                ){
//            ArrayList<String> result = new ArrayList<>(graphVertexHashMap.keySet());
//            for(int i = graphVertexHashMap.size()-1; i >= (graphVertexHashMap.size() - (graphVertexHashMap.size() -k)) ; i--){
//                 result.remove(i);
//            }
//            return result;
//        }
//        else if(changling >= 0.80 && numVertices >= 200 ){
//            approximate = true;
//        }

        ArrayList<String> orderedResult = new ArrayList<>();
        HashMap<String, Float> S = new HashMap();
        float currentModular = Integer.MIN_VALUE;

        for(int i =0; i < k; i++){
            String maxString = "";
            float maxValue = (float)Integer.MIN_VALUE;

            Iterator it = graphVertexHashMap.entrySet().iterator();
            while(it.hasNext()){
                ArrayList<String> vCopy = new ArrayList<>(S.keySet());

                Map.Entry entry = (Map.Entry)it.next();
                String compareString = (String)entry.getKey();

                if(S.containsKey(compareString)) continue;
                else{
                    vCopy.add(compareString);
                    float compareValue = influence(vCopy);
                    float approximation = compareValue - currentModular;
                    if(approximate && S.size() > 0 && maxValue > 0 && approximation >= ratio){
                        maxValue = compareValue;
                        maxString = compareString;
                        break;
                    }else if(compareValue > maxValue){
                        maxValue = compareValue;
                        maxString = compareString;
                    }
                }
            }
            if(!S.containsKey(maxString)){
                S.put(maxString, maxValue);
                currentModular = influence(new ArrayList<>(S.keySet()));
                orderedResult.add(maxString);
            }

            if(S.size() >= k) break;
//            System.out.println(S.size());
        }
        return orderedResult;
    }

}
