// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may only include libraries of the form java.*)

/**
* @author Hugh Potter
*/

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class NetworkInfluence
{
    private static final int INF = Integer.MAX_VALUE;
    private int numVertices;
    private HashMap<String, GraphVertex> graphVertexHashMap;

	public NetworkInfluence(String graphData) throws Exception {
		graphVertexHashMap = new HashMap<>();
	    File f = new File(graphData);
		if(!f.exists() || f.isDirectory())
			throw new Exception("File specified for Network Influence does not exist. Returning.");

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(graphData));
		String line = bufferedReader.readLine();

		if(line == null)
            throw new Exception("Error reading file in Network Influence. Returning.");

        try {
		    numVertices = Integer.parseInt(line.trim());
        } catch (Exception e) {
            throw new Exception("Error parsing the number of vertices in Network Influence. Returning.");
        }

		while((line = bufferedReader.readLine()) != null) {
            if(line.equals(""))
                continue;

		    String[] vertices = line.split("\\s+");

            if(vertices.length != 2)
                throw new Exception("Improperly formatted line in Network Influence file. Expected only two names of vertices.");

            GraphVertex graphVertex1 = graphVertexHashMap.containsKey(vertices[0]) ? graphVertexHashMap.get(vertices[0]) : new GraphVertex(vertices[0]);
            GraphVertex graphVertex2 = graphVertexHashMap.containsKey(vertices[1]) ? graphVertexHashMap.get(vertices[1]) : new GraphVertex(vertices[1]);

            graphVertex1.AddOutDegreeVertex(graphVertex2);
            graphVertex2.AddInDegreeVertex(graphVertex1);

            graphVertexHashMap.putIfAbsent(graphVertex1.getVertexName(), graphVertex1);
            graphVertexHashMap.putIfAbsent(graphVertex2.getVertexName(), graphVertex2);
        }

        bufferedReader.close();

		if(graphVertexHashMap.size() != numVertices)
            System.out.println("WARNING! The number of vertices at the beginning of the file in Network Influence does not match the input of " + numVertices);
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

	public ArrayList<String> shortestPath(String u, String v)
	{
	    if(u.equals(v)) {
	        ArrayList<String> ret = new ArrayList<>();
	        ret.add(u);
	        return ret;
        }

	    GraphVertex endVertex = graphVertexHashMap.get(v);

	    if(endVertex == null) {
	        return new ArrayList<>();
        }

		Stack<GraphVertex> stack = new Stack<>();
        ResetBFSAttributes();
        graphVertexHashMap.get(u).setDistanceFromStart(0);

        graphVertexHashMap.forEach((key,value) -> {
            if(!value.visited())
                TopologicalSort(value, stack);
        });

        while (!stack.empty())
        {
            GraphVertex graphVertex = stack.pop();

            if(graphVertex.getDistanceFromStart() != INF) {
                UpdateDistances(graphVertex);
            }
        }

        LinkedList<String> graphVertices = new LinkedList<>();
        GraphVertex parentNode = endVertex.getParentBFS();

        while(parentNode != null) {
            if(graphVertices.size() == 0) {
                graphVertices.addLast(endVertex.getVertexName());
            }
            graphVertices.addFirst(parentNode.getVertexName());
            parentNode = parentNode.getParentBFS();
        }

		return new ArrayList<>(graphVertices);
	}

	public int distance(String u, String v)
	{
		ArrayList<String> shortestPath = shortestPath(u, v);

		return shortestPath.size() == 0 ? -1 : (shortestPath.size() - 1);
	}

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

	public float influence(String u)
	{
		// implementation

		// replace this:
		return -1f;
	}

	public float influence(ArrayList<String> s)
	{
		// implementation

		// replace this:
		return -1f;
	}

	public ArrayList<String> mostInfluentialDegree(int k)
	{
	    // Output nodes with top k outdegrees.
	    graphVertexHashMap.forEach((key, value) -> {

        });

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

	private void UpdateDistances(GraphVertex graphVertex) {
        graphVertex.getOutDegrees().forEach((key, value) -> {
            if (value.getDistanceFromStart() > graphVertex.getDistanceFromStart() + 1) {
                value.setDistanceFromStart(graphVertex.getDistanceFromStart() + 1);
                value.setParentBFS(graphVertex);
                UpdateDistances(value);
            }
        });
    }

	private void TopologicalSort(GraphVertex graphVertex, Stack<GraphVertex> stack) {
        graphVertex.setVisited(true);

        graphVertex.getOutDegrees().forEach((key, value) -> {
            if(!value.visited()) {
                TopologicalSort(value, stack);
            }
        });

        stack.push(graphVertex);
    }

	private void ResetBFSAttributes() {
	    graphVertexHashMap.forEach((key, value) -> {
	        value.setVisited(false);
	        value.setParentBFS(null);
	        value.setDistanceFromStart(INF);
        });
    }
}