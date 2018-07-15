import java.util.ArrayList;
import java.util.List;

class Dijkstravertex
{
	private Vertex v;
	Dijkstravertex precursor;
	private boolean visited;
	private List<Edge> edges;
	
	
	public Dijkstravertex(Vertex v)
	{
		this.v = v;
		visited = false;
		edges = new ArrayList<Edge>();
	}
	
	public void setPrecursor(Dijkstravertex p)
	{
		precursor = p;
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