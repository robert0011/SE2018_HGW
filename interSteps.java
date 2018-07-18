import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JOptionPane;

class interSteps
{
	/**
	 * <p>
	 * A String which defines which algorithm shall be executed.
	 * </p>
	 */
	private static String algorithm;
	
	/**
	 * <p>
	 * A drawpanel object on which the Dijkstra shall be executed.
	 * </p>
	 */
	private DrawPanel d;
	
	/**
	 * <p>
	 * Startvertexnumber for the executed algorithm..
	 * </p>
	 */
	public static int start = -1;
	
	/**
	 * <p>
	 * Endvertexnumber for the executed algorithm.
	 * </p>
	 */
	public static int end;
	
	/**
	 * <p>
	 * An object from the Dijkstra constructor from the Dijkstra class, used to display the stepwise Dijkstra algorithm.
	 * </p>
	 */
	Dijkstra testDijkstra;
	
	/**
	 * <p>
	 * Boolean which states whether the Dijkstra has reached the endvertex.<br>
	 * Is true if the end is reached.
	 * </p>
	 */
	private boolean reachedEnd = false;
	
	
	/**
	 * <p>
	 * Constructor for the interSteps object.
	 * </p>
	 * 
	 * @param d An object of the drawpanel type.
	 * @param alg String which contains the information, which algorithm shall be executed.
	 * @param s Startvertex for the algorithm.
	 * @param e Endvertex of the algorithm.
	 */
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
	
	/**
	 * <p>
	 * Getter method, which returns used algorithm as String.
	 * <p>
	 * 
	 * @return String with the name of the executed algorithm.
	 */
	public String getAlgorithm()
	{
		return algorithm;
	}
	
	/**
	 * <p>
	 * Function for the stepwise execution of the Dijkstraalgorithm.
	 * </p>
	 * 
	 * @param a If this String contains the value "Dijkstra", the Dijkstraalgorithm will be executed stepwiseley.
	 */
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
	
	/**
	 * <p>
	 * Function which actual execute the Dijkstra algorithm using an Dijkstra object.
	 * </p>
	 * 
	 * @param start Startvertex for the Dijkstra.
	 * @param end Endvertex for the Dijkstra.
	 */
	public void algDijkstra(int start, int end)
	{
		testDijkstra.performAStep();
		d.repaint();
		if(testDijkstra.reachedEnd())
		{
			reachedEnd = true;
		}
	
	}
	
	/**
	 * <p>
	 * Function to skip the stepwise representation of the Dijkstra to the final step after the first step was done. 
	 * </p>
	 */
	public void skipDijkstraToEnd()
	{
		while(!reachedEnd)
		{
			stepwise("Dijkstra");
		}
		showPath();
	}
	
	/**
	 * <p>
	 * Function for the coloring and recoloring of the paths during the Dijkstra algorithm
	 * </p>
	 */
	public void recolor()
	{
		testDijkstra.recolor();
		d.repaint();
	}
	
	/**
	 * <p>
	 * Setter method, to set the boolean which states if the Dijkstra is finished or not.
	 * </p>
	 * 
	 * @param b
	 */
	public void setFinished(boolean b)
	{
		this.reachedEnd = b;
	}
	
	/**
	 * <p>
	 * Getter method which returns the boolean which states if the Dijkstra is finished or not.
	 * </p>
	 * @return true if the Dijkstra is finished.
	 */
	public boolean isFinished()
	{
		return reachedEnd;
	}
	
	/**
	 * <p>
	 * Setter method which sets the startvertex to the given.
	 * </p>
	 * 
	 * @param s Index of the new startvertex.
	 */
	public void setStart(int s)
	{
		start =s;
	}
	
	/**
	 * <p>
	 * Setter method which sets the endvertex to the given.
	 * </p>
	 * 
	 * @param e Index of the new endvertex.
	 */
	public void setEnd(int e)
	{
		end = e;
	}
	
	/**
	 * <p>
	 * Function which resets the Dijkstra object for the later reuse.
	 * </p>
	 */
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
	
	/**
	 * <p>
	 * Function which shows the paths for the Dijkstra algorithm.
	 * </p>
	 */
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