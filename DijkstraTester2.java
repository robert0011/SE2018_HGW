import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class DijkstraTester2
{
	/**
	 * 
	 * @param a
	 * @param b
	 * @return Returns true if the indices of the vertices in a correspond to the integers in b
	 */
	public boolean identicalLists(ArrayList<Dijkstravertex> a, ArrayList<Integer> b)
	{
		boolean identical = true;
		for(int i = 0; i < a.size(); i = i+1)
		{
			try
			{
				if(a.get(i).getVertex().getIndex() != b.get(i))
				{
					identical = false;
				}
			}
			catch(NullPointerException e)
			{
				// a exception is thrown when the lists do not have the same length
					// or something is wrong with a Dijkstravertex
				return false;
			}
		}
		return identical;
	}

	/**
	 * Verifies that in an empty graph no paths are found and that no exception is thrown.
	 */
	@Test
	public void emptyGraph()
	{
		DrawPanel drawPanel = new DrawPanel();
		interSteps testInterSteps = new interSteps(drawPanel,"Dijkstra",1,7);
		
		try
		{
			ArrayList<Dijkstravertex> path = testInterSteps.skipDijkstraToEnd();
			assertEquals("The path should be empty.",path.size(),0);
		}
		catch(Exception e)
		{
			assertTrue("Exception was thrown when searching a path in empty graph.",false);
		}
		
	}

}