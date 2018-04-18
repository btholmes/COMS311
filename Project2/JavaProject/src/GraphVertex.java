import java.util.HashMap;

public class GraphVertex {
    private String vertexName;
    private Boolean visited;
    private GraphVertex parentBFS;
    private HashMap<String, GraphVertex> outDegrees;
    private HashMap<String, GraphVertex> inDegrees;

    public GraphVertex(String vertexName) {
        this.vertexName = vertexName;
        this.outDegrees = new HashMap<>();
        this.inDegrees = new HashMap<>();
        this.visited = false;
        this.parentBFS = null;
    }

    public void AddOutDegreeVertex(GraphVertex graphVertex) {
        this.outDegrees.putIfAbsent(graphVertex.getVertexName(), graphVertex);
    }

    public HashMap<String, GraphVertex> getOutDegrees() {
        return outDegrees;
    }

    public int GetOutDegreeSize() {
        return this.outDegrees.size();
    }

    public void AddInDegreeVertex(GraphVertex graphVertex) {
        this.inDegrees.putIfAbsent(graphVertex.getVertexName(), graphVertex);
    }

    public int GetInDegreeSize() {
        return this.inDegrees.size();
    }

    public String getVertexName() {
        return vertexName;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public GraphVertex getParentBFS() {
        return parentBFS;
    }

    public void setParentBFS(GraphVertex parentBFS) {
        this.parentBFS = parentBFS;
    }
}
