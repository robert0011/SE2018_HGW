import java.util.*; // for sets


import javafx.util.Pair;

/**
 * Class for the graph used by our developed software.
 * @author Bruckmann C., Wagner R.
 *
 */
public class Graph implements GraphInterface
{
	int vertexlabel = 0;
	// initialize the set for the edges and vertices for the graph
	public Set<Pair<Integer,Vertex>> vertexSet;
	public Set<Edge> edgeSet;
	
	/**
	 * 
	 */
	public boolean addVertex(Vertex v)
	{
		if(v == null)
		{
			throw new NullPointerException();
		}
		else
		{
			Vertex vertexToAdd = new Vertex(v.x,v.y);
			vertexSet.add(new Pair<Integer,Vertex>(vertexlabel, vertexToAdd));
			vertexlabel = vertexlabel++;
			return true;
		}
		
		
	}
	
	/**
	 * 
	 */
	public boolean addEdge(Vertex start, Vertex end, double weight)
	{
		return true;

	}
	
	public boolean removeVertex(int remove)
	{
		
		Iterator<Pair<Integer,Vertex>> iterator = vertexSet.iterator();
		boolean removed = false;
		
		while (iterator.hasNext()) {
	         Pair<Integer,Vertex> current = iterator.next();
	         int label = current.getKey();
	         if(label == remove)
	         {
	        	 vertexSet.remove(current);
	        	 removed = true;
	         }
	      }
		if(removed == true)
		{
			return true;
		}
		else
		{
			// hier noch Nachricht ausgeben
			return false;
		}
		
	}
	
	public boolean removeEdge(Edge e)
	{
		return true;
	}
}