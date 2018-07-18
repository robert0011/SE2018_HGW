import java.util.*; // for sets

/**
 * <p>
 * The graph class delivers the basic structure for most functionalities of this toolbox.<br>
 * This graph is directed and consists of vertices and edges, the edges can either be weighted<br>
 * or  unweighted. 
 * </p>
 * @author C. Bruckmann, R. Wagner
 *
 */
public class Graph implements GraphInterface
{
	/**
	 * <p>
	 * An integer which contains the vertexnumber.
	 * </p>
	 */
	int vertexlabel = 0;

	/**
	 * <p>
	 * A hashtabele consisting of integers as keys and vertices as values.<br>
	 * The integer represents the 'vertex number' and the vertex is the corresponding vertex.
	 * </p>
	 */
	public Hashtable<Integer,Vertex> vertexSet;
	
	/**
	 * <p>
	 * A hashtabele consisting of integers as keys and a list of edges as value.<br>
	 * The integer represents the vertex and the list of edges represents the edges, <br>
	 * which have this particular vertex as their start vertex.
	 * </p>
	 */
	public Hashtable<Integer, List<Edge>> outEdges;
	
	/**
	 * <p>
	 * A hashtabele consisting of integers as keys and a list of edges as value.<br>
	 * The integer represents the vertex and the list of edges represents the edges, <br>
	 * which have this particular vertex as their end vertex.
	 * </p>
	 */
	public Hashtable<Integer, List<Edge>> inEdges;
	
	/**
	 * <p>
	 * Constructor for the graph object.
	 * </p>
	 */
	public Graph() 
	{
		
		this.vertexlabel = 0;
		this.vertexSet = new Hashtable<Integer,Vertex>();
		this.outEdges = new Hashtable<Integer, List<Edge>>();
		this.inEdges = new Hashtable<Integer, List<Edge>>();
	}
	
	/**
	 * <p>
	 * Function which adds new vertices to the graph. Returns true if the addition was successful.
	 * </p>
	 */
	public boolean addVertex(Vertex v)
	{
			//vertex is not null, so it is addable, return true
			if(v != null)
			{
				Vertex vertexToAdd = v;
				vertexToAdd.setIndex(vertexlabel);
				vertexSet.put(vertexlabel, vertexToAdd);
				vertexlabel = vertexlabel +1;
				return true;
			}
			// input is null, so no actual vertex to add, return false
			else
			{
				return false;
			}
			
	}
	
	
	
	/**
	 * <p>
	 * Function which adds new edges to the graph. Returns true if the addition was successful.
	 * </p> 
	 */
	public boolean addEdge(Vertex start, Vertex end, int weight)
	{
		// only add an edge if the start and end vertex are not null
		if(start != null & end != null)
		{
			Edge edgeToAdd = new Edge(start,end,weight);
			
			// first check whether this edge already exists
			boolean edgeExists = false;
			if(outEdges.containsKey(start.getIndex()))
			{
				List<Edge> curr = outEdges.get(start.getIndex());
				for(Edge e : curr)
				{
					if(start != null && end != null && e.getStart().getIndex()==start.getIndex() && e.getEnd().getIndex()==end.getIndex())
					{
						edgeExists = true;
					}
				}
				
			}
			
			
			//check whether the vertices exist
			if(!edgeExists && start != null && end != null && vertexSet.containsKey(start.getIndex()) && vertexSet.containsKey(end.getIndex()))
			{
				// add the edge from start to end to outEdges of start
				List<Edge> curList = outEdges.get(start.getIndex());
				if(curList == null) 
				{
					List<Edge> curList1 = new ArrayList<Edge>();
					curList1.add(edgeToAdd);
					outEdges.put(start.getIndex(), curList1);
				}
				else 
				{
					curList.add(edgeToAdd);
					outEdges.put(start.getIndex(), curList);
				}
				
				
			
				// add the edge from start to end to inEdges of end
				curList = inEdges.get(end.getIndex());
				
				if(curList == null) 
				{
					List<Edge> curList1 = new ArrayList<Edge>();
					curList1.add(edgeToAdd);
					inEdges.put(end.getIndex(), curList1);
				}
				else 
				{
					curList.add(edgeToAdd);
					inEdges.put(end.getIndex(), curList);
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		
		else
		{
			return false;
		}
	}
	
	
	/**
	 * <p>
	 * Function to delete a vertex from the graph, retruns true if the deletion was successful.
	 * </p>
	 */
	public boolean removeVertex(int remove) 
	{
		if(vertexSet.containsKey(remove))
		{
			vertexSet.remove(remove);
			return true;
		}
		else
		{
			return false;
		}		
	}
	
	/**
	 * <p>
	 * Function to delete an edge from the graph, retruns true if the deletion was successful.
	 * </p>
	 */
	public boolean removeEdge(Vertex start, Vertex end)
	{
		boolean edgeExists = false;
		// first check whether this edge exists
		if(start != null & end != null)
		{
			if(outEdges.containsKey(start.getIndex()))
			{
				List<Edge> curr = outEdges.get(start.getIndex());
				for(int i =0; i< curr.size(); i=i+1)
				{
					if(curr.get(i).getStart().getIndex() == start.getIndex() & curr.get(i).getEnd().getIndex()==end.getIndex())
					{
						curr.remove(i);
						edgeExists = true;
					}
				}
			}
		}
			
		// the edge must also be in inEdges
		if(edgeExists)
		{
			List<Edge>curr = inEdges.get(end.getIndex());
			for(int i =0; i< curr.size(); i=i+1)
			{
				if(curr.get(i).getStart().getIndex() == start.getIndex() && curr.get(i).getEnd().getIndex()==end.getIndex())
				{
					curr.remove(i);
				}
			}
			return true;
		}
		else
		{
			return false;
		}				
	}
}