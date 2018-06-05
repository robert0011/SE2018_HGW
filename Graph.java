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
	
	public Hashtable<Integer, List<Integer>> outEdges;
	public Hashtable<Integer, List<Integer>> inEdges;
	
	public Graph() 
	{
		
		this.vertexlabel = 0;
		
		this.vertexSet = new HashSet<Pair<Integer,Vertex>>();
		this.edgeSet = new HashSet<Edge>();
		this.outEdges = new Hashtable<Integer, List<Integer>>();
		this.inEdges = new Hashtable<Integer, List<Integer>>();
	}
	
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
			
			// test whether the vertex is already in the set
			Iterator<Pair<Integer,Vertex>> pairIterator = vertexSet.iterator();
			boolean found = false;
			while(pairIterator.hasNext()) {
				Pair<Integer,Vertex> currPair = pairIterator.next();
				Vertex curVertex = currPair.getValue();
				int start = curVertex.x;
				int end = curVertex.y;
				if(v.x == start && v.y ==end)
				{
					found = true;
				}
				
			}
			
			if(found == true)
			{
				return false;
			}
			else
			{
				vertexSet.add(new Pair<Integer,Vertex>(vertexlabel, vertexToAdd));
				vertexlabel = vertexlabel+1;
				return true;
			}
			
		}
		
		
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
		boolean foundStart = false;
		boolean foundEnd = false;
		Iterator<Pair<Integer,Vertex>> iterator2 = vertexSet.iterator();
		while(iterator2.hasNext())
		{
			Pair<Integer,Vertex> current = iterator2.next();
			int curVertexLable = current.getKey();
			if(start == curVertexLable)
			{
				foundStart = true;
			}
			if(end == curVertexLable)
			{
				foundEnd = true;
			}
		}
		
		
		
		if(foundStart & foundEnd)
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
			List<Integer> curList2 = inEdges.get(end);
			
			if(curList2 == null) {
				List<Integer> curList1 = new ArrayList<Integer>();
				curList1.add(end);
				inEdges.put(end, curList1);
			}
			else {
				curList2.add(end);
				inEdges.put(end, curList2);
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
	
	public boolean removeEdge(int start, int end)
	{
		// first check whether this edge exists
				Iterator<Edge> iterator = edgeSet.iterator();
				boolean removed = false;
				
				/*if(iterator.next() == null)
				{
					// there is no edge to be deleted
					return false;
				}
				
				else
				{*/
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
								int indexOfStart = curList2.indexOf(start);
								curList2.remove(indexOfStart);
								inEdges.put(end, curList2);
								
								removed = true;
								return true;
							}
							
						}
					return removed;
				//}
				
				
					
	}
}