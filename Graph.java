import java.util.*; // for sets

/**
 * Class for the graph used by our developed software.
 * @author Bruckmann C., Wagner R.
 *
 */
public class Graph implements GraphInterface
{
	int vertexlabel = 0;
	// initialize the set for the edges and vertices for the graph
	public Hashtable<Integer,Vertex> vertexSet;
	public Set<Edge> edgeSet;
	
	public Hashtable<Integer, List<Integer>> outEdges;
	public Hashtable<Integer, List<Integer>> inEdges;
	
	public Graph() 
	{
		
		this.vertexlabel = 0;
		
		this.vertexSet = new Hashtable<Integer,Vertex>();
		this.edgeSet = new HashSet<Edge>();
		this.outEdges = new Hashtable<Integer, List<Integer>>();
		this.inEdges = new Hashtable<Integer, List<Integer>>();
	}
	
	/**
	 * 
	 */
	public boolean addVertex(Vertex v)
	{
		
			vertexSet.put(vertexlabel, v);
			vertexlabel = vertexlabel +1;
			return true;
		
		
	}
	
	
	
	/**
	 * 
	 */
	public boolean addEdge(int start, int end, double weight)
	{
		
		Edge edgeToAdd = new Edge(start,end,weight);
		
		// first check whether this edge already exists
		Iterator<Edge> iterator = edgeSet.iterator();
		//boolean edgeNotFound = true;
		
		while(iterator.hasNext())
		{
			Edge curEdge = iterator.next();
			if(curEdge.start == start && curEdge.end == end)
			{
				//edgeNotFound = false;
				return false;
			}
			
		}
		
		
		//check whether the vertices exist
		if(vertexSet.containsKey(start) && vertexSet.containsKey(end))

		{
			
			
			// both vertices exist, an edge can be constructed
			edgeSet.add(edgeToAdd);
			
			
			// add the edge from start to end to outEdges of start
			List<Integer> curList = outEdges.get(start);
			if(curList == null) {
				List<Integer> curList1 = new ArrayList<Integer>();
				curList1.add(end);
				outEdges.put(start, curList1);
			}
			else {
				curList.add(end);
				outEdges.put(start, curList);
			}
			
			
		
			// add the edge from start to end to inEdges of end
			curList = inEdges.get(end);
			
			if(curList == null) {
				List<Integer> curList1 = new ArrayList<Integer>();
				curList1.add(start);
				inEdges.put(end, curList1);
			}
			else {
				curList.add(start);
				inEdges.put(end, curList);
			}
			return true;
		}
		else
		{
			return false;
		}
		
		
		
		
		

	}
	
	
	
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
	
	public boolean removeEdge(int start, int end)
	{
		// first check whether this edge exists
				Iterator<Edge> iterator = edgeSet.iterator();
				boolean removed = false;
				
				{
					while(iterator.hasNext())
					{
						Edge curEdge = iterator.next();
						
						
							if(curEdge.start == start && curEdge.end == end)
							{
								edgeSet.remove(curEdge);
								// remove outgoing and incoming edges
								List<Integer> curList = outEdges.get(start);
								int indexOfEnd = curList.indexOf(end);
								curList.remove(indexOfEnd);
								outEdges.put(start, curList);
								
								List<Integer> curList2 = inEdges.get(end);
								if(curList2 == null)
								{
									// do nothing if
								}
								else
								{
									int indexOfStart = curList2.indexOf(start);
									curList2.remove(indexOfStart);
									inEdges.put(end, curList2);
									
									removed = true;
									return true;
								}
								
							}
					
						}
					return removed;
				}
				
				
					
	}
}