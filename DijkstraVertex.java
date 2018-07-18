import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Class for the vertices which are used in the Dijkstra algorithm.
 * </p>
 * 
 * @author C. Bruckmann, R. Wagner
 */
class Dijkstravertex
{
	/**
	 * <p>
	 * A vertex.
	 * </p>
	 */
	private Vertex v;
	
	/**
	 * <p>
	 * Vertex which later contains the precursor (the vertex from the former step) for the calculation of the shortest path.
	 * </p>
	 */
	Dijkstravertex precursor;
	
	/**
	 * <p>
	 * Boolean which returns true if a vertex is already been visited.
	 * </p>
	 */
	private boolean visited;
	
	/**
	 * <p>
	 * A list that consists of edges.
	 * </p>
	 */
	private List<Edge> edges;
	
	/**
	 * <p>
	 * Constructor of a Dijkstravertex, which needs an vertex of the graph.
	 * </p>
	 */
	public Dijkstravertex(Vertex v)
	{
		this.v = v;
		visited = false;
		edges = new ArrayList<Edge>();
	}
	
	/**
	 * <p>
	 * Setter method to define the precursor of the actual Dijkstravertex.
	 * </p> 
	 */
	public void setPrecursor(Dijkstravertex p)
	{
		precursor = p;
	}
	
	/**
	 * <p>
	 * Setter method to change the vist status of the vertex.
	 * </p>
	 */
	public void setVisited(boolean v)
	{
		visited = v;
	}
	
	/**
	 * <p>
	 * Setter method to change the edges to the actual list of edges.
	 * </p> 
	 */
	public void setEdges(List<Edge> edges)
	{
		this.edges = edges;
	}
	
	/**
	 * <p>
	 * Method which returns the precursor as Dijkstravertex.
	 * </p>
	 */
	public Dijkstravertex getPrecursor()
	{
		return precursor;
	}
	
	/**
	 * <p>
	 * Method which returns a boolean containing the information whether a vertex is already visited or not.
	 * </p>
	 */
	public boolean visited()
	{
		return visited;
	}
	
	/**
	 * <p>
	 * Getter method which returns a vertex.
	 * </p>
	 */
	public Vertex getVertex()
	{
		return v;
	}
	
	/**
	 * <p>
	 * Getter method which returns the edges from the list of edges.
	 * </p>
	 */
	List<Edge> getEdges()
	{
		return edges;
	}
}