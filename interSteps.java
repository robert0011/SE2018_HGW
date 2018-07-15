import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JOptionPane;

class interSteps
{
	private static String algorithm;
	private DrawPanel d;
	
	// for the Dijkstra
	public static int start = -1;
	public static int end;
	Dijkstra testDijkstra;
	private boolean reachedEnd = false;
	
	
	
	public interSteps(DrawPanel d, String alg, int s, int e)
	{
		this.d = d;
		algorithm = alg;
		start = s;
		end = e;
		if(alg.equals("Dijkstra"))
		{
			testDijkstra = new Dijkstra(s, e, d.graph);
		}
		
		reachedEnd = false;
	}
	
	public interSteps()
	{
		//start = -1;
	}
	
	public String getAlgorithm()
	{
		return algorithm;
	}
	
	public void stepwise(String a)
	{
		if(testDijkstra.steps == 0)
		{
			testDijkstra.setFinished(false);
		}
		
		if(this.getAlgorithm().equals("Dijkstra"))
		{
			algDijkstra(start, end);
		}
	}
	
	public void algDijkstra(int start, int end)
	{
		testDijkstra.performAStep();
		d.repaint();
		if(testDijkstra.reachedEnd())
		{
			reachedEnd = true;
		}
	
	}
	
	public void skipDijkstraToEnd()
	{
		while(!reachedEnd)
		{
			stepwise("Dijkstra");
		}
		showPath();
	}
	
	
	
	public void recolor()
	{
		testDijkstra.recolor();
		d.repaint();
	}
	
	public void setFinished(boolean b)
	{
		this.reachedEnd = b;
	}
	
	public boolean isFinished()
	{
		return reachedEnd;
	}
	
	public void setStart(int s)
	{
		start =s;
	}
	
	public void setEnd(int e)
	{
		end = e;
	}
	
	public void destruct()
	{
		algorithm = new String();
		start = -1;
		end = -1;
		testDijkstra.unvisitedHash = new Hashtable<Integer, Dijkstravertex>();;
	  	testDijkstra.visited = new ArrayList<Dijkstravertex>();
		reachedEnd = false;
		testDijkstra.steps = 0;
		testDijkstra.startVertex = null;
		testDijkstra.curVertex = null;
	}
	
	public void showPath()
	{
		ArrayList<Dijkstravertex> path = testDijkstra.getPath();
		Dijkstravertex tmp = null;
		Edge tmp2;
		for(int i = 0; i<path.size(); i = i+1)
		{
			//if last vertex = end, then there is no edge to color
			if(i != 0)
			{
				List<Edge> search = path.get(i).getEdges();
				for(int j = 0; j<search.size(); j = j+1)
				{
					tmp2 = search.get(j);
					int c2 = tmp2.getEnd().getIndex();
					if(c2 == tmp.getVertex().getIndex())
					{
						tmp2.setColor(Color.BLUE);
					}
				}
			}
			tmp = path.get(i);
			tmp.getVertex().setColor(Color.BLUE);
			
		}
		if(path.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "There is no path that connects the vertices you entered.");
		}
		//show the blue path
		d.repaint();
	}
}