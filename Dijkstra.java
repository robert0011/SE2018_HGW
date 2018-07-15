import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Dijkstra
{
	public static boolean finished = false;
	public static int start;
	public static List<Edge> startEdges = new ArrayList<Edge>();
	public static Dijkstravertex curVertex;
	private static int end;
	public static Hashtable<Integer, Dijkstravertex> unvisitedHash;
	public static List<Dijkstravertex> visited;
	public int steps = 0;
	public Vertex startVertex;
	private Queue<Dijkstravertex> unvisited = new PriorityQueue<>(distanceComparator);
	
	public Dijkstra(int s, int e, Graph g)
	{
		start = s;
		end = e;

		unvisitedHash = new Hashtable<Integer, Dijkstravertex>();
		visited = new ArrayList<Dijkstravertex>();
		
		// add all vertices but the start vertex to the queue
		Dijkstravertex vToAdd;
		Enumeration<Vertex> v = g.vertexSet.elements();
		while(v.hasMoreElements())
		{
			Vertex curVertex = v.nextElement();
			if(curVertex.getIndex() != s)
			{
				vToAdd = new Dijkstravertex(curVertex);
				
				if(g.outEdges.containsKey(curVertex.getIndex()))
				{
					List<Edge> edgesToAdd = g.outEdges.get(curVertex.getIndex());
					vToAdd.setEdges(edgesToAdd);
				}
				unvisited.add(vToAdd);
				unvisitedHash.put(curVertex.getIndex(), vToAdd);
				
				
			}
			else
			{
				startVertex = curVertex;
				// add outgoing edges to the start vertex
				if(g.outEdges.containsKey(s))
				{
					startEdges = g.outEdges.get(s);
					System.out.println("adding " +startEdges.size() + " edge(s) to startvertex "+curVertex.getIndex());
					
				}
				
				else 
				{
					startEdges = new ArrayList<Edge>();
				}

			}
		}
		if(g.vertexSet.containsKey(3))
		{
			System.out.println("vertex 3 is contained in the graph.");
		}
		if(g.outEdges.containsKey(2))
		{
			System.out.println("vertex "+g.outEdges.get(2).get(0).getEnd().getIndex()+" is connected to vertex 2.");
			System.out.println("edge 2 3 is contained.");
		}
		if(unvisitedHash.containsKey(3))
		{
			System.out.println("unvisitedHash contains vertex 3.");
		}
		
		
	}
	
	public Queue<Dijkstravertex> getQueue()
	{
		return unvisited;
	}
	
	
	
	public static Comparator<Dijkstravertex> distanceComparator = new Comparator<Dijkstravertex>(){
			
			@Override
			public int compare(Dijkstravertex v1, Dijkstravertex v2) {
				return v1.getVertex().getDistance()-v2.getVertex().getDistance();
	        }
		};
		
	public static Comparator<Dijkstravertex> indexComparator = new Comparator<Dijkstravertex>(){
				
				@Override
				public int compare(Dijkstravertex v1, Dijkstravertex v2) {
					return v1.getVertex().getIndex()-v2.getVertex().getIndex();
		        }
			};
		

	
	
	public void performAStep()
	{
		if(steps != 0)
		{
			// set the color of the current vertex and its edges of the step before to gray
			curVertex.getVertex().setColor(Color.GRAY);
			if(curVertex.getEdges() != null && curVertex.getEdges().size() != 0)
			{
				for(int i = 0; i<curVertex.getEdges().size(); i=i+1)
				{
					curVertex.getEdges().get(i).setColor(Color.GRAY);
				}
			}
			curVertex.setVisited(true);
			unvisitedHash.remove(curVertex.getVertex().getIndex());
			System.out.println("removing vertex "+curVertex.getVertex().getIndex()+" from unvisitedHash." );
			
			curVertex = unvisited.poll();
			
			
			
		}
		else // steps = 0
		{
			startVertex.setColor(Color.GREEN);
			Dijkstravertex start = new Dijkstravertex(startVertex);
			start.getVertex().setDistance(0);
			start.setVisited(true);
			start.setEdges(startEdges);
			visited.add(start);
			curVertex = start;
			System.out.println("start vertex: "+curVertex.getVertex().getIndex()+", number of edges: "+curVertex.getEdges().size());
			if(this.start == -1)
			{
				System.out.println("start was -1.");
				curVertex = null;
			}
		}
		
		
		
		if(curVertex == null || curVertex.getVertex().getDistance() == (int) Double.POSITIVE_INFINITY || curVertex.getVertex().getIndex() == end)
		{
			System.out.println("reached the end!");
			finished = true;
			if(curVertex != null && curVertex.getVertex().getIndex() == end)
			{
				curVertex.getVertex().setColor(Color.GRAY);
				visited.add(curVertex);
			}
			
		}
		else
		{
			System.out.println("current vertex: "+curVertex.getVertex().getIndex());
			curVertex.getVertex().setColor(Color.GREEN);
			visited.add(curVertex);
			// for the current vertex consider all of its unvisited neighbors
						// and recalculate their distance to the start-vertex
					List<Edge> edgesToCheck = curVertex.getEdges();
					if(edgesToCheck == null | edgesToCheck.size() == 0)
					{
						System.out.println("no edges to check.");
						// the current vertex has no more edges, do nothing
					}
					else
					{
						System.out.println("current vertex: "+curVertex.getVertex().getIndex());
						System.out.println("number of edges to check: "+edgesToCheck.size());
						for(int i = 0; i<=edgesToCheck.size()-1; i=i+1)
						{
							if(edgesToCheck.size() != 0)
							{
								System.out.println("checking edges");
								Edge curEdge = edgesToCheck.get(i);
								int possibleDist = curEdge.getWeight()+curVertex.getVertex().getDistance();
								int destination = curEdge.getEnd().getIndex();
								
								if(!unvisitedHash.containsKey(destination))
								{
									System.out.println("unvisitedHash does not contain vertex "+destination);
								}
								if(unvisitedHash.containsKey(destination))
								{
									System.out.println("vertex "+destination+" is still unvisited.");
									// not sure if this works
									curVertex.getEdges().get(i).setColor(Color.GREEN);
									Dijkstravertex tmp = unvisitedHash.get(destination);
									double currentBestDist = tmp.getVertex().getDistance();
									if(possibleDist < currentBestDist)
									{
										unvisitedHash.remove(destination);
										System.out.println("removing vertex "+destination+" from unvisitedHash.");
										unvisited.remove(tmp);
										tmp.getVertex().setDistance(possibleDist);
										tmp.setPrecursor(curVertex);
										unvisitedHash.put(destination, tmp);
										unvisited.add(tmp);
										System.out.println("updated distance of circle "+tmp.getVertex().getIndex()+" to "+tmp.getVertex().getDistance());
										
									}
								}
							}
							
						}
						
					}
					this.steps = this.steps+1;
		}
		
		
		
		
	}
	
	public void recolor()
	{
		List<Edge>grayEdges;
		// all visited vertices and their edges need to be set black again
		for(Dijkstravertex d : visited)
		{
			d.getVertex().setColor(Color.BLACK);
			d.getVertex().setDistance((int) Double.POSITIVE_INFINITY);
			grayEdges = d.getEdges();
			if(grayEdges != null && grayEdges.size() != 0)
			{
				for(Edge e : grayEdges)
				{
					e.setColor(Color.BLACK);
				}
			}
			this.steps = 0;
			
		}
		unvisitedHash = new Hashtable<Integer, Dijkstravertex>();
		visited = new ArrayList<Dijkstravertex>();
	}
	
	public boolean reachedEnd()
	{
		return finished;
	}
	
	public void setFinished(boolean b)
	{
		finished = b;
	}
	
	public ArrayList<Dijkstravertex> getPath()
	{
		// this should be end
		Dijkstravertex curIndex = curVertex;
		ArrayList<Dijkstravertex> path = new ArrayList<Dijkstravertex>();
		if(curVertex.getVertex().getIndex() == end)
		{
			path.add(curVertex);
			
			while(curIndex.getVertex().getIndex() != startVertex.getIndex())
			{
				curIndex = curIndex.getPrecursor();
				
				path.add(curIndex);
			}
			
			/*if(curIndex != start)
			{
				curIndex = curVertex.getPrecursor();
				path.add(curIndex);
			}*/
			
			// print visited
			System.out.println("");
			System.out.println("visited:");
			for(Dijkstravertex d : visited)
			{
				System.out.println("Vertex "+d.getVertex().getIndex()+" has precursor "+d.getPrecursor()+".");
			}
		}
		return path;
		
	}
	
	
}