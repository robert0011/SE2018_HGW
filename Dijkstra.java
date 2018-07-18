import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.JOptionPane;

/**
 * <p>
 * The Dijkstra algorithm calculates the shortest path between two vertices, if such a path exists.
 * </p>
 * 
 * @author C. Bruckmann, R. Wagner
 *
 */
class Dijkstra
{
	/**
	 * <p>
	 * Boolean for the state of completition of the algorithm.
	 * </p>
	 */
	public static boolean finished = false;
	
	/**
	 * <p>
	 * Integer that defines the startingpoint of this algorithm.
	 * </p>
	 */
	public static int start;
	
	/**
	 * <p>
	 * 
	 * </p>
	 */
	public static List<Edge> startEdges = new ArrayList<Edge>();
	
	/**
	 * <ü>
	 * An Dijkstravertex which contains the current vertex.
	 * </p>
	 */
	public static Dijkstravertex curVertex;
	
	/**
	 * <p>
	 * 
	 * </p>
	 */
	private static int end;
	
	/**
	 * <p>
	 * 
	 * </p>
	 */
	public static Hashtable<Integer, Dijkstravertex> unvisitedHash;
	
	/**
	 * <p>
	 * 
	 * </p>
	 */
	public static List<Dijkstravertex> visited;
	
	/**
	 * 
	 */
	public int steps = 0;
	
	/**
	 * <p>
	 * 
	 * </p>
	 */
	public Vertex startVertex;
	
	/**
	 * <p>
	 * 
	 * </p>
	 */
	private Queue<Dijkstravertex> unvisited = new PriorityQueue<>(distanceComparator);
	
	/**
	 * <p>
	 * 
	 * </p>
	 */
	public static boolean graphRead = true;
	
	/**
	 * <p>
	 * 
	 * </p>
	 */
	public static boolean startAndEndInG = false;
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param s Index of the startvertex
	 * @param e Index of the endvertex
	 * @param g Graph on which the Dijkstra shall be executed
	 */
	public Dijkstra(int s, int e, Graph g)
	{
		start = s;
		end = e;

		unvisitedHash = new Hashtable<Integer, Dijkstravertex>();
		visited = new ArrayList<Dijkstravertex>();
		
		// check that the graph and its vertexset are not null
		if(g == null | g.vertexSet == null)
		{
			graphRead = false;
		}
		else
		{
			// check if start and end are contained in the graph
			if(g.vertexSet.containsKey(s) & g.vertexSet.containsKey(e))
			{
				startAndEndInG = true;
			}
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
						//System.out.println("adding " +startEdges.size() + " edge(s) to startvertex "+curVertex.getIndex());
						
					}
					
					else 
					{
						startEdges = new ArrayList<Edge>();
					}

				}
			}
		}	
	}
	
	/**
	 * An error message is displayed if the function returns false.
	 * @return Returns true if the graph and the vertexset are not null and start and end are contained in the graph.
	 */
	public boolean checkParameters()
	{
		boolean ok = true;
		// if the graph was not read, the graph or its vertexset is null
		if(!graphRead)
		{
			ok = false;
			JOptionPane.showMessageDialog(null, "The graph is empty.");
			
		}
		if(!startAndEndInG)
		{
			ok = false;
			JOptionPane.showMessageDialog(null, "At least one of the vertices you entered does not exist in the graph.");
			
		}
		
		return ok;
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
		

	
	/**
	 * @return Returns true if the graph exists and contains start and end. So it returns checkParameters()
	 */
	public boolean performAStep()
	{
		if(checkParameters())
		{
			if(steps != 0)
			{
				// set the color of the current vertex and its edges of the step before to gray
				if(curVertex.getVertex().getDistance() != (int) Double.POSITIVE_INFINITY)
				{
					curVertex.getVertex().setColor(Color.GRAY);
				}
				
				if(curVertex.getEdges() != null && curVertex.getEdges().size() != 0 && curVertex.getVertex().getDistance() != (int) Double.POSITIVE_INFINITY)
				{
					for(int i = 0; i<curVertex.getEdges().size(); i=i+1)
					{
						curVertex.getEdges().get(i).setColor(Color.GRAY);
					}
				}
				curVertex.setVisited(true);
				unvisitedHash.remove(curVertex.getVertex().getIndex());
				//System.out.println("removing vertex "+curVertex.getVertex().getIndex()+" from unvisitedHash." );
				
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
				//System.out.println("start vertex: "+curVertex.getVertex().getIndex()+", number of edges: "+curVertex.getEdges().size());
				if(this.start == -1)
				{
					//System.out.println("start was -1.");
					curVertex = null;
				}
			}
			
			
			
			if(curVertex == null || curVertex.getVertex().getDistance() == (int) Double.POSITIVE_INFINITY || curVertex.getVertex().getIndex() == end)
			{
				/*System.out.println("reached the end 1!");
				if(curVertex == null)
				{
					System.out.println("curVertex is null!");
				}
				if(curVertex.getVertex().getDistance() == (int) Double.POSITIVE_INFINITY)
				{
					System.out.println("Distance is infinity!");
				}
				if(curVertex.getVertex().getIndex() == end)
				{
					System.out.println("Actually reached the end!");
				}*/
				finished = true;
				if(curVertex != null && curVertex.getVertex().getIndex() == end && curVertex.getVertex().getDistance() != (int) Double.POSITIVE_INFINITY)
				{
					curVertex.getVertex().setColor(Color.GRAY);
					if(curVertex.getVertex().getDistance() != (int) Double.POSITIVE_INFINITY)
					visited.add(curVertex);
				}
				
			}
			else
			{
				//System.out.println("current vertex: "+curVertex.getVertex().getIndex());
				curVertex.getVertex().setColor(Color.GREEN);
				visited.add(curVertex);
				// for the current vertex consider all of its unvisited neighbors
							// and recalculate their distance to the start-vertex
						List<Edge> edgesToCheck = curVertex.getEdges();
						if(edgesToCheck == null | edgesToCheck.size() == 0)
						{
							//System.out.println("no edges to check.");
							// the current vertex has no more edges, do nothing
						}
						else
						{
							//System.out.println("current vertex: "+curVertex.getVertex().getIndex());
							//System.out.println("number of edges to check: "+edgesToCheck.size());
							for(int i = 0; i<=edgesToCheck.size()-1; i=i+1)
							{
								if(edgesToCheck.size() != 0)
								{
									//System.out.println("checking edges");
									Edge curEdge = edgesToCheck.get(i);
									int possibleDist = curEdge.getWeight()+curVertex.getVertex().getDistance();
									int destination = curEdge.getEnd().getIndex();
									
									if(!unvisitedHash.containsKey(destination))
									{
										//System.out.println("unvisitedHash does not contain vertex "+destination);
									}
									if(unvisitedHash.containsKey(destination))
									{
										//System.out.println("vertex "+destination+" is still unvisited.");
										// not sure if this works
										curVertex.getEdges().get(i).setColor(Color.GREEN);
										Dijkstravertex tmp = unvisitedHash.get(destination);
										double currentBestDist = tmp.getVertex().getDistance();
										if(possibleDist < currentBestDist)
										{
											unvisitedHash.remove(destination);
											//System.out.println("removing vertex "+destination+" from unvisitedHash.");
											unvisited.remove(tmp);
											tmp.getVertex().setDistance(possibleDist);
											tmp.setPrecursor(curVertex);
											unvisitedHash.put(destination, tmp);
											unvisited.add(tmp);
											//System.out.println("updated distance of circle "+tmp.getVertex().getIndex()+" to "+tmp.getVertex().getDistance());
											
										}
									}
								}
								
							}
							
						}
						this.steps = this.steps+1;
			}
		
			// if the checkParameters() was ok, then there were no problems in the Dijkstra
			return true;
		}
		else
		{
			return false;
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
		if(curVertex.getVertex().getIndex() == end & curVertex.getVertex().getDistance() != (int) Double.POSITIVE_INFINITY)
		{
			path.add(curVertex);
			
			while(startVertex != null && curIndex.getVertex().getIndex() != startVertex.getIndex())
			{
				curIndex = curIndex.getPrecursor();
				
				path.add(curIndex);
			}
			
			
			
			// print visited
			/*System.out.println("");
			System.out.println("visited:");
			for(Dijkstravertex d : visited)
			{
				System.out.println("Vertex "+d.getVertex().getIndex()+" has precursor "+d.getPrecursor()+".");
			}*/
		}
		return path;
		
	}
	
	
}