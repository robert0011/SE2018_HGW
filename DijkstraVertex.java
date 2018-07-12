import java.util.ArrayList;
import java.util.List;

class Dijkstravertex
{
	private Vertex v;
	Dijkstravertex precursor;
	private int distance;
	private boolean visited;
	private List<Edge> edges;
	
	
	public Dijkstravertex(Vertex v)
	{
		this.v = v;
		//precursor = -1;
		distance = (int) Double.POSITIVE_INFINITY;
		visited = false;
		edges = new ArrayList<Edge>();
	}
	
	public void setPrecursor(Dijkstravertex p)
	{
		precursor = p;
	}
	
	public void setDistance(int d)
	{
		distance = d;
	}
	
	public void setVisited(boolean v)
	{
		visited = v;
	}
	
	public void setEdges(List<Edge> edges)
	{
		this.edges = edges;
	}
	
	public Dijkstravertex getPrecursor()
	{
		return precursor;
	}
	
	public int getDistance()
	{
		return distance;
	}
	
	public boolean visited()
	{
		return visited;
	}
	
	public Vertex getVertex()
	{
		return v;
	}
	
	List<Edge> getEdges()
	{
		return edges;
	}
}