import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DijkstraTester
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
	 * Verifies that a path is found if there exists one.
	 */
	@Test
	public void pathExists()
	{
		DrawPanel drawPanel = new DrawPanel();
		try {
			drawPanel.loadFile("EXgraph.txt");
		} catch (FileNotFoundException e) {
			assertTrue("File was not found.",false);
		}
		interSteps testInterSteps = new interSteps(drawPanel,"Dijkstra",1,7);
		ArrayList<Dijkstravertex> path = testInterSteps.skipDijkstraToEnd();
		
		// the path should be (it is saved backwards) 7,5,2,1
		ArrayList<Integer> expectedPath = new ArrayList<Integer>();
		expectedPath.add(7);
		expectedPath.add(5);
		expectedPath.add(2);
		expectedPath.add(1);
		
		// check if the expected path was found
		boolean success = identicalLists(path,expectedPath);
		// expectedPath is the only possible path here
		assertTrue("The correct path was not found.", success);
		
	}
	
	
	/**
	 * Verifies that no path is found if one of the given vertices does not exist in the graph and that no exception is thrown.
	 */
	@Test
	public void incorrectVertices()
	{
		// please notice: since the constructor of interSteps expects the vertices to be given as integers, they can not be null
		DrawPanel drawPanel = new DrawPanel();
		try {
			drawPanel.loadFile("EXgraph.txt");
		} catch (FileNotFoundException e) {
			assertTrue("File was not found.",false);
		}
		try
		{
			// EXgraph.txt provides vertices 0 to 9, so there is no vertex 10
			interSteps testInterSteps = new interSteps(drawPanel,"Dijkstra",1,10);
			ArrayList<Dijkstravertex> path = testInterSteps.skipDijkstraToEnd();
			assertEquals("The path should be empty.",path.size(),0);
		}
		catch(Exception e)
		{
			assertTrue("Exception was thrown.",false);
		}
		
	}
	
	/**
	 * Verifies that the path from a vertex to itself is found and has weight 0
	 */
	@Test
	public void pathToItself()
	{
		DrawPanel drawPanel = new DrawPanel();
		try {
			drawPanel.loadFile("EXgraph.txt");
		} catch (FileNotFoundException e) {
			assertTrue("File was not found.",false);
		}
		
		// edge 1,1 does not exist, but the path from 1 to 1 should be found
		try
		{
			interSteps testInterSteps = new interSteps(drawPanel,"Dijkstra",1,1);
			ArrayList<Dijkstravertex> path = testInterSteps.skipDijkstraToEnd();
			assertEquals("The path should contain 1 vertex.",path.size(),1);
			// expected path: 1
			ArrayList<Integer> expectedPath = new ArrayList<Integer>();
			expectedPath.add(1);
			boolean success = identicalLists(path, expectedPath);
			assertTrue("The path computed is not the same as the expected path.", success);
			
			// the weight of the path should be 0
			assertEquals("The distance from a vertex to itself is not 0.",path.get(0).getVertex().getDistance(),0);
		}
		catch(Exception e)
		{
			assertTrue("Exception was thrown.",false);
		}
		
		// even if the edge 1,1 exists, the distance should still be 0
			// vertex 1 exists in the graph
		Vertex v1 = drawPanel.graph.vertexSet.get(1);
		drawPanel.graph.addEdge(v1, v1, 5);
		try
		{
			interSteps testInterSteps = new interSteps(drawPanel,"Dijkstra",1,1);
			ArrayList<Dijkstravertex> path = testInterSteps.skipDijkstraToEnd();
			assertEquals("The path should contain 1 vertex.",path.size(),1);
			// expected path: 1
			ArrayList<Integer> expectedPath = new ArrayList<Integer>();
			expectedPath.add(1);
			boolean success = identicalLists(path, expectedPath);
			assertTrue("The path computed is not the same as the expected path.", success);
			
			// the weight of the path should be 0
			assertEquals("The distance from a vertex to itself is not 0.",path.get(0).getVertex().getDistance(),0);
		}
		catch(Exception e)
		{
			assertTrue("Exception was thrown.",false);
		}
		
	}
	
	/**
	 * Verifies that no path is found when the start vertex is isolated and that no exception is thrown.
	 */
	@Test
	public void isolatedStart()
	{
		DrawPanel drawPanel = new DrawPanel();
		try {
			drawPanel.loadFile("EXgraph.txt");
		} catch (FileNotFoundException e) {
			assertTrue("File was not found.",false);
		}
		
		
		try
		{
			// Vertex 0 is isolated
			interSteps testInterSteps = new interSteps(drawPanel,"Dijkstra",0,1);
			ArrayList<Dijkstravertex> path = testInterSteps.skipDijkstraToEnd();
			assertEquals("There should be no path.",0,path.size());
		}
		catch(Exception e)
		{
			assertTrue("Exception was thrown.",false);
		}
	}
	
	/**
	 * Verifies that no path is found when the end vertex is isolated and that no exception is thrown.
	 */
	@Test
	public void isolatedEnd()
	{
		DrawPanel drawPanel = new DrawPanel();
		try {
			drawPanel.loadFile("EXgraph.txt");
		} catch (FileNotFoundException e) {
			assertTrue("File was not found.",false);
		}
		
		
		try
		{
			// Vertex 0 is isolated
			interSteps testInterSteps = new interSteps(drawPanel,"Dijkstra",1,0);
			ArrayList<Dijkstravertex> path = testInterSteps.skipDijkstraToEnd();
			assertEquals("There should be no path.",0,path.size());
		}
		catch(Exception e)
		{
			assertTrue("Exception was thrown.",false);
		}
	}
	
	/**
	 * Verifies that no path is found if there actually is no path while both vertices are not isolated. No exception should be thrown.
	 */
	@Test
	public void noPath()
	{
		DrawPanel drawPanel = new DrawPanel();
		try {
			drawPanel.loadFile("EXgraph.txt");
		} catch (FileNotFoundException e) {
			assertTrue("File was not found.",false);
		}
		
		try
		{
			// in EXgraph.txt there is no path between vertices 5 and 6
				// and they are both not isolated
			interSteps testInterSteps = new interSteps(drawPanel,"Dijkstra",5,6);
			ArrayList<Dijkstravertex> path = testInterSteps.skipDijkstraToEnd();
			assertEquals("There should be no path.",0,path.size());
		}
		catch(Exception e)
		{
			assertTrue("Exception was thrown.",false);
		}
	}
	
}