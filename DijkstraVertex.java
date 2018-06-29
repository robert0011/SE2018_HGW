import java.util.ArrayList;
import java.util.List;

class Dijkstravertex
{
	private Circle v;
	Dijkstravertex precursor;
	private int distance;
	private boolean visited;
	private List<Line> edges;
	
	
	public Dijkstravertex(Circle v)
	{
		this.v = v;
		//precursor = -1;
		distance = (int) Double.POSITIVE_INFINITY;
		visited = false;
		edges = new ArrayList<Line>();
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
	
	public void setEdges(List<Line> edges)
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
	
	public Circle getCircle()
	{
		return v;
	}
	
	List<Line> getEdges()
	{
		return edges;
	}
}