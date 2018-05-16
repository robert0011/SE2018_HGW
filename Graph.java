import java.util.Set; // for sets

import GraphInterface.Vertex;

/**
 * Class for the graph used by our developed software.
 * @author Bruckmann C., Wagner R.
 *
 */
public class Graph implements GraphInterface
{
	int vertexlabel = 0;
	
	// initialize the set for the edges and vertices for the graph
	public Set<Vertex> vertexSet();
	public Set<Edge> edgeSet();
	
	/**
	 * 
	 */
	public boolean addVertex(Vertex v)
	{
		Vertex vertrexToAdd = new Vertex(int MouseX,int MouseY, int color);
		vertexToAdd.label = vertexlabel +1;
		vertexlabel = vertexlabel++;
	}
	
	/**
	 * 
	 */
	public boolean addEdge(Vertex start, Vertex end, double weight)
	{

	}
	
	/**
	 * 
	 */
	public boolean removeVertex(Vertex v)
	{
		
	}
	
	/**
	 * 
	 */
	public boolean removeEdge(Edge e)
	{
		
	}
}