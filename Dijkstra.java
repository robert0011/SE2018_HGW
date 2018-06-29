import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Dijkstra
{
	public static boolean finished = false;
	public static int start;
	public static List<Line> startEdges = new ArrayList<Line>();
	public static Dijkstravertex curVertex;
	private static int end;
	public static Hashtable<Integer, Dijkstravertex> unvisitedHash;
	public static List<Dijkstravertex> visited;
	public int steps = 0;
	public Circle startCircle;
	private Queue<Dijkstravertex> unvisited = new PriorityQueue<>(distanceComparator);
	
	public Dijkstra(int s, int e,ArrayList<Circle> c, Hashtable<Integer, List<Line>> l)
	{
		start = s;
		end = e;

		unvisitedHash = new Hashtable<Integer, Dijkstravertex>();
		visited = new ArrayList<Dijkstravertex>();
		
		// add all circles but the start circle to the queue
		Dijkstravertex vToAdd;
		for(Circle c1 : c)
		{
			if(c1.getIndex() != s)
			{
				vToAdd = new Dijkstravertex(c1);
				
				List<Line> edgesToAdd = new ArrayList<Line>();
				if(l.containsKey(c1.getIndex()))
				{
					// this might exist but be empty
					if(l.get(c1.getIndex()).size() != 0)
					{
						System.out.println("adding edge(s) to circle "+c1.getIndex());
						edgesToAdd = l.get(c1.getIndex());
						vToAdd.setEdges(edgesToAdd);
					}
				}
				unvisited.add(vToAdd);
				unvisitedHash.put(c1.getIndex(), vToAdd);
				
			}
			else
			{
				startCircle = c1;
				if(l.containsKey(s))
				{
					System.out.println("adding edge(s) to startvertex "+c1.getIndex());
					startEdges = l.get(s);
				}
				else 
				{
					startEdges = new ArrayList<Line>();
				}

			}
		}
		
		
	}
	
	public Queue<Dijkstravertex> getQueue()
	{
		return unvisited;
	}
	
	
	
	public static Comparator<Dijkstravertex> distanceComparator = new Comparator<Dijkstravertex>(){
			
			@Override
			public int compare(Dijkstravertex v1, Dijkstravertex v2) {
				return v1.getDistance()-v2.getDistance();
	        }
		};
		
	public static Comparator<Dijkstravertex> indexComparator = new Comparator<Dijkstravertex>(){
				
				@Override
				public int compare(Dijkstravertex v1, Dijkstravertex v2) {
					return v1.getCircle().getIndex()-v2.getCircle().getIndex();
		        }
			};
		

	
	
	public void performAStep()
	{
		if(steps != 0)
		{
			// set the color of the current vertex and its edges of the step before to gray
			curVertex.getCircle().setColor(Color.GRAY);
			if(curVertex.getEdges() != null && curVertex.getEdges().size() != 0)
			{
				for(int i = 0; i<curVertex.getEdges().size(); i=i+1)
				{
					curVertex.getEdges().get(i).setColor(Color.GRAY);
				}
			}
			curVertex.setVisited(true);
			unvisitedHash.remove(curVertex.getCircle().getIndex());
			
			curVertex = unvisited.poll();
			
			
			
		}
		else // steps = 0
		{
			startCircle.setColor(Color.GREEN);
			Dijkstravertex start = new Dijkstravertex(startCircle);
			start.setDistance(0);
			start.setVisited(true);
			start.setEdges(startEdges);
			visited.add(start);
			curVertex = start;
			System.out.println("start vertex: "+curVertex.getCircle().getIndex()+", number of edges: "+curVertex.getEdges().size());
			if(this.start == -1)
			{
				System.out.println("start was -1.");
				curVertex = null;
			}
		}
		
		
		
		if(curVertex == null || curVertex.getDistance() == (int) Double.POSITIVE_INFINITY || curVertex.getCircle().getIndex() == end)
		{
			System.out.println("reached the end!");
			finished = true;
			if(curVertex != null && curVertex.getCircle().getIndex() == end)
			{
				curVertex.getCircle().setColor(Color.GRAY);
				visited.add(curVertex);
			}
			
		}
		else
		{
			System.out.println("current vertex: "+curVertex.getCircle().getIndex());
			curVertex.getCircle().setColor(Color.GREEN);
			visited.add(curVertex);
			// for the current vertex consider all of its unvisited neighbors
						// and recalculate their distance to the start-vertex
					List<Line> edgesToCheck = curVertex.getEdges();
					if(edgesToCheck == null | edgesToCheck.size() == 0)
					{
						System.out.println("no edges to check.");
						// the current vertex has no more edges, do nothing
					}
					else
					{
						System.out.println("current vertex: "+curVertex.getCircle().getIndex());
						System.out.println("number of edges to check: "+edgesToCheck.size());
						for(int i = 0; i<=edgesToCheck.size()-1; i=i+1)
						{
							if(edgesToCheck.size() != 0)
							{
								System.out.println("checking edges");
								Line curLine = edgesToCheck.get(i);
								int possibleDist = curLine.getWeight()+curVertex.getDistance();
								int destination = curLine.getC2().getIndex();
								if(unvisitedHash.containsKey(destination))
								{
									// not sure if this works
									curVertex.getEdges().get(i).setColor(Color.GREEN);
									Dijkstravertex tmp = unvisitedHash.get(destination);
									int currentBestDist = tmp.getDistance();
									if(possibleDist < currentBestDist)
									{
										unvisitedHash.remove(destination);
										unvisited.remove(tmp);
										tmp.setDistance(possibleDist);
										/*if(steps == 0)
										{
											tmp.setPrecursor(start);
										}
										else
										{
											tmp.setPrecursor(curVertex.getCircle().getIndex());
											
										}*/
										tmp.setPrecursor(curVertex);
										//tmp.setPrecursor(curVertex.getCircle().getIndex());
										unvisitedHash.put(destination, tmp);
										unvisited.add(tmp);
										System.out.println("updated distance of circle "+tmp.getCircle().getIndex()+" to "+tmp.getDistance());
										
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
		List<Line>grayEdges;
		// all visited vertices and their edges need to be set black again
		for(Dijkstravertex d : visited)
		{
			d.getCircle().setColor(Color.BLACK);
			grayEdges = d.getEdges();
			if(grayEdges != null && grayEdges.size() != 0)
			{
				for(Line l : grayEdges)
				{
					l.setColor(Color.BLACK);
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
		if(curVertex.getCircle().getIndex() == end)
		{
			path.add(curVertex);
			
			while(curIndex.getCircle().getIndex() != startCircle.getIndex())
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
				System.out.println("Vertex "+d.getCircle().getIndex()+" has precursor "+d.getPrecursor()+".");
			}
		}
		return path;
		
	}
	
	
}