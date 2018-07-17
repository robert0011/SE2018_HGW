/**
* Class that tests the class Graph
* @author Bruckmann C., Wagner R.
*
*/

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class GraphTester
{
	/**
	 * Returns true if all specified vertices (given in v) and no more vertices are contained in the graph.
	 * @param v
	 * @param graph
	 * @return
	 */
	public boolean testVertexSet(ArrayList<Integer>v, Graph graph)
	{
		boolean foundAll = true;
		for(int i = 0; i < v.size(); i = i+1)
		{
			int vertexToFind = v.get(i);
			if(!graph.vertexSet.containsKey(vertexToFind))
			{
				foundAll = false;
			}
		}
		boolean sizeOk = false;
		if(v.size() == graph.vertexSet.size())
		{
			sizeOk = true;
		}
		if(foundAll & sizeOk)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Returns true if the graph exactly contains a given number of edges.
	 * @param size
	 * @param d
	 * @return
	 */
	public boolean testEdgeSetSize(int size, Graph graph)
    {
    	int currentSum  = 0;
    	Enumeration<Integer> keys = graph.outEdges.keys();
    	while(keys.hasMoreElements())
    	{
    		Integer curKey = keys.nextElement();
    		List<Edge> list = graph.outEdges.get(curKey);
    		currentSum = currentSum+list.size();
    	}
    	
    	if(size == currentSum)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
	
	/**
	 * Returns true if all the edges (given in edges) with corresponding weights (given in weights) are contained in the graph.
	 * For simplicity every vertex is restricted to have one or no edge.
	 * @param d
	 * @param edges
	 * @param weights
	 * @return
	 * @throws NullPointerException
	 */
    public boolean testEdges(Graph graph,Hashtable<Integer,Integer> edges, Hashtable<Integer,Integer>weights) throws NullPointerException
    {
    	boolean foundAll = true;
		boolean found = false;
    	Enumeration<Integer> edgeKeys = edges.keys();
    	int weightIndex = 0;
    	while(edgeKeys.hasMoreElements())
    	{
    		found = false;
    		Integer curStart = edgeKeys.nextElement();
    		Integer curEnd = edges.get(curStart);
    		try
    		{
    			List<Edge> list = graph.outEdges.get(curStart);
    			for(int i = 0; i<list.size(); i = i+1)
    			{
    				if(list.get(i).getEnd().getIndex() == curEnd)
    				{
    					if(list.get(i).getWeight() == weights.get(curStart))
    					{
    						found = true;
    					}
    					
    				}
    			}
    		}
    		catch(NullPointerException e)
    		{
    			// if some list entry does not exist, an exception is thrown
    				// and should be re-thrown
    			throw e;
    		}
    		if(!found)
    		{
    			foundAll = false;
    		}
    		weightIndex = weightIndex+1;
    	}
    	return foundAll;
    }

	/**
	 * Tests that the Graph exists after the call of the constructor.
	 */
	@Test
	public void emptyConstructor()
	{
		Graph g = new Graph();
		assertNotNull("Graph should != null after call of constructor", g);
		assertEquals("Size of vertexSet should return 0", 0, g.vertexSet.size());
		assertEquals("Size of outEdges should return 0", 0, g.outEdges.size());
		assertEquals("Size of inEdges should return 0", 0, g.outEdges.size());

	}
	
	/**
	 * Verifies that added vertices are part of the vertexset and that vertices with value null are not added.
	 */
	@Test
	public void testAddVertex()
	{
		Graph g = new Graph();
		
		Vertex v0 = new Vertex(10,1,2);
		// v0 was not added to the graph yet, so it should not be existing in the graph
		assertFalse("v0 was not added yet.",g.vertexSet.containsKey(0));
		assertEquals("Size of vertexSet should return 0", 0, g.vertexSet.size());
		
		// add vertex v0 to the graph
		boolean success = g.addVertex(v0);
		assertTrue("v0 should be added to the graph.",success);
		assertTrue("v0 should be in the vertexset.",g.vertexSet.containsKey(0));
		assertEquals("Size of vertexSet should return 1", 1, g.vertexSet.size());
		
		// a vertex with value null should not be added to a graph
		Vertex v1 = null;
		success = g.addVertex(v1);
		assertFalse("A vertex with value null should not be added to a graph.", success);
	}
	

	/**
	 * Verifies that a non-existent vertex in an empty graph can not be removed.
	 */
	@Test
	public void testRemoveVertex1()
	{
		// the method remove(int remove) can not be called with null, so this case need not be tested
		
		// remove a non-existent vertex in an empty graph
		Graph g = new Graph();
		// vertex with index 0 does not exist
		boolean success = g.removeVertex(0);
		assertFalse("v0 does not exist in the graph, can not be removed.", success);
	}
	
	/**
	 * Verifies that a non-existent vertex in an non-empty graph can not be removed. 
	 */
	@Test
	public void testRemoveVertex2()
	{
		// remove a non-existent vertex in a non-empty graph
		Graph g = new Graph();
		Vertex v0 = new Vertex(10,1,2);
		g.addVertex(v0);
		// vertex with index 1 does not exist
		boolean success = g.removeVertex(1);
		assertFalse("v1 does not exist in the graph, can not be removed.",success);
	}
	
	/**
	 * Verifies that a existing vertex in a graph with one vertex can be removed as expected.
	 */
	@Test
	public void testRemoveVertex3()
	{
		// remove a existing vertex in a graph with one vertex
		Graph g = new Graph();
		Vertex v0 = new Vertex(10,1,2);
		g.addVertex(v0);
		boolean success = g.removeVertex(0);
		assertTrue("v0 exists in the graph, can be removed.",success);
	}
	
	
	/**
	 * Verifies that a existing vertex in a graph with more than one vertex can be removed as expected.
	 */
	@Test
	public void testRemoveVertex4()
	{
		// remove a existing vertex in a graph with more than one vertex
		Graph g = new Graph();
		Vertex v0 = new Vertex(10,1,2);
		g.addVertex(v0);
		Vertex v1 = new Vertex(10,2,3);
		g.addVertex(v1);
		
		// create an ArrayList with the vertices that should be in the graph
		ArrayList<Integer> vertices = new ArrayList<Integer>();
		vertices.add(0);
		vertices.add(1);
		
		// test if the expected vertices actually are in the graph
		boolean success = testVertexSet(vertices,g);
		assertTrue("Something is wrong with the vertexset.", success);
		
		// test if v0 can be removed
		success = g.removeVertex(0);
		assertTrue("v0 exists in the graph, can be removed.",success);
		
		// now the vertexset should only contain v1
		vertices = new ArrayList<Integer>();
		vertices.add(1);
		success = testVertexSet(vertices,g);
		assertTrue("Something is wrong with the vertexset.", success);
		

	}	
	
	/**
	 * Verifies that an edge that contains a vertex with value null can not be added.
	 */
	@Test
	public void testAddEdge1()
	{
		Graph graph = new Graph();
		Vertex v0 = null;
		Vertex v1 = null;
		Vertex v2 = new Vertex(10,1,2);
		Vertex v3 = new Vertex(10,2,3);
		
		// v0 and v1 are not really added
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		
		// try to add an edge between two vertices with value null
		boolean success = graph.addEdge(v0, v1, 1);
		assertFalse("It should not be possible to add an edge between two vertices with value null.", success);
		
		// try to add an edge with one vertex with value null
		success = graph.addEdge(v0, v2, 1);
		assertFalse("It should not be possible to add an edge with a vertex with value null.", success);
		
		success = graph.addEdge(v2, v0, 1);
		assertFalse("It should not be possible to add an edge with a vertex with value null.", success);
		
		// add an edge between two "normal" vertices
		success = graph.addEdge(v2, v3, 1);
		assertTrue("Edge v2,v3 should be added.",success);
		
	}
	
	/**
	 * Verifies that an edge that contains a vertex which is non-existent in the graph can not be added.
	 */
	@Test
	public void testAddEdge2()
	{
		Graph graph = new Graph();
		Vertex v0 = new Vertex(10,2,3);
		Vertex v1 = new Vertex(10,5,8);
		
		// add only v1
		graph.addVertex(v1);
		
		//try to add the edge v1,v0
		boolean success = graph.addEdge(v1, v0, 2);
		assertFalse("v0 does not exist in the graph, can not be part of an edge.",success);
		
	}
	
	/**
	 * Verifies that a correct edge is added.
	 */
	@Test
	public void testAddEdge3()
	{
		Graph graph = new Graph();
		Vertex v0 = new Vertex(10,2,3);
		Vertex v1 = new Vertex(10,5,8);
		Vertex v2 = new Vertex(10,2,3);
		Vertex v3 = new Vertex(10,5,8);
		Vertex v4 = new Vertex(10,2,3);
		Vertex v5 = new Vertex(10,5,8);
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		graph.addVertex(v4);
		graph.addVertex(v5);
		
		graph.addEdge(v0, v2, 1);
		graph.addEdge(v1, v5, 5);
		graph.addEdge(v3, v2, 3);
		graph.addEdge(v5, v1, 1);
		
		// check if everything was added correctly
			// check the size of the edgeset
		boolean success = testEdgeSetSize(4,graph);
		assertTrue("Not every edge was added (but should).",success);
		
			// check if all expected edges exist
		Hashtable<Integer, Integer> edges = new Hashtable<Integer, Integer>();
		edges.put(0, 2);
		edges.put(1, 5);
		edges.put(3, 2);
		edges.put(5, 1);
		Hashtable<Integer, Integer> weights = new Hashtable<Integer, Integer>();
		weights.put(0, 1);
		weights.put(1, 5);
		weights.put(3, 3);
		weights.put(5, 1);
		success = testEdges(graph,edges,weights);
		assertTrue("Something went wrong when adding the edges.", success);
	}
	
	/**
	 * Verifies that an edge with weight < 0 (but correct) is added but converted to weight 0
	 */
	@Test
	public void testAddEdge4()
	{
		Graph graph = new Graph();
		Vertex v0 = new Vertex(10,2,3);
		Vertex v1 = new Vertex(10,5,8);
		Vertex v2 = new Vertex(10,2,3);
		Vertex v3 = new Vertex(10,5,8);
		Vertex v4 = new Vertex(10,2,3);
		Vertex v5 = new Vertex(10,5,8);
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		graph.addVertex(v4);
		graph.addVertex(v5);
		
		// try to add an edge v0,v1 with weight 1
		boolean success = graph.addEdge(v0, v1, 1);
		assertTrue("An edge with weight 1 should be added.",success);
				
		// try to add an edge v2,v3 with weight -1
		success = graph.addEdge(v2, v3, -1);
		assertTrue("An edge with weight -1 should be added.",success);
		
		
		// try to add an edge v4,v5 with weight 0
		success = graph.addEdge(v4, v5, 0);
		assertTrue("An edge with weight 0 should be added.",success);
		
		// check if edge v0,v1 has weight1, edge v2,v3 has weight 0 and edge v4,v5  has weight 0
		Hashtable<Integer,Integer> edges = new Hashtable<Integer,Integer>();
		edges.put(0, 1);
		edges.put(2, 3);
		edges.put(4, 5);
		
		Hashtable<Integer,Integer> weights = new Hashtable<Integer,Integer>();
		weights.put(0, 1);
		weights.put(2, 0);
		weights.put(4, 0);
		
		success = testEdgeSetSize(3,graph);
		assertTrue("The graph should contain three edges.",success);
		
		success = testEdges(graph,edges,weights);
		assertTrue("Something went wrong with the weights.", success);
	
	}
	
	/**
	 * Verifies that a vertex can be part of more than one edge
	 */
	@Test
	public void testAddEdge5()
	{
		Graph graph = new Graph();
		Vertex v0 = new Vertex(10,2,3);
		Vertex v1 = new Vertex(10,5,8);
		Vertex v2 = new Vertex(10,2,3);
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		
		boolean success = graph.addEdge(v0, v1, 3);
		assertTrue("Edge v0,v1 should be added.", success);
		
		success = graph.addEdge(v0, v2, 5);
		assertTrue("Edge v0,v2 should be added.", success);
		
		success = graph.addEdge(v2, v0, 5);
		assertTrue("Edge v2,v0 should be added.", success);
	}
	
	/**
	 * Verifies that an edge which already exists can not be added
	 */
	@Test
	public void testAddEdge6()
	{
		Graph graph = new Graph();
		Vertex v0 = new Vertex(10,2,3);
		Vertex v1 = new Vertex(10,5,8);
		graph.addVertex(v0);
		graph.addVertex(v1);
		
		boolean success = graph.addEdge(v0, v1, 5);
		assertTrue("Edge should be added.", success);
		
		success = graph.addEdge(v0, v1, 5);
		assertFalse("Edge v0,v1 already exists, should not be added.", success);
	}
	
	/**
	 * Verifies that when trying to remove an edge with value null no exception is thrown
	 */
	@Test
	public void testRemoveEdge1()
	{
		Graph graph = new Graph();
		Vertex v0 = null;
		Vertex v1 = new Vertex(10,1,2);
		
		// edge v0,v1 would not be added, but we need to test what happens 
			//if we try to remove it
		try
		{
			boolean success = graph.removeEdge(v0, v1);
			assertFalse("Edge v0,v1 does not exist, should not be removed.",success);
		}
		catch(Exception e)
		{
			assertTrue("No exception should be thrown.",true);
		}
		
	}
	
	
	/**
	 * Verifies that a non-existing edge can not be removed in an empty graph.
	 */
	@Test
	public void testRemoveEdge2()
	{
		Graph graph = new Graph();
		Vertex v0 = new Vertex(10,2,3);
		Vertex v1 = new Vertex(10,5,8);
		
		boolean success = graph.removeEdge(v0, v1);
		assertFalse("A non-existing edge can not be removed.", success);
	}
	
	/**
	 * Verifies that a non-existing edge can not be removed in a non-empty graph.
	 */
	@Test
	public void testRemoveEdge3()
	{
		Graph graph = new Graph();
		Vertex v0 = new Vertex(10,2,3);
		Vertex v1 = new Vertex(10,5,8);
		Vertex v2 = new Vertex(10,7,9);
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addEdge(v0, v1, 5);
		
		// v0,v2 was not added, can not be removed
		boolean success = graph.removeEdge(v0, v2);
		assertFalse("A non-existing edge can not be removed.",success);
		
	}
	
	/**
	 * Verifies that an existing edge can be removed.
	 */
	@Test
	public void testRemoveEdge4()
	{
		Graph graph = new Graph();
		Vertex v0 = new Vertex(10,2,3);
		Vertex v1 = new Vertex(10,5,8);
		Vertex v2 = new Vertex(10,7,9);
		Vertex v3 = new Vertex(10,16,1);
		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		graph.addEdge(v0, v2, 1);
		graph.addEdge(v2, v0, 1);
		graph.addEdge(v1, v3, 4);
		
		// remove an edge
		graph.removeEdge(v0, v2);
		
		// check if the graph contains the expected vertices and edges
			// check the vertexset
		ArrayList<Integer> vertices = new ArrayList<Integer>();
		vertices.add(0);
		vertices.add(1);
		vertices.add(2);
		vertices.add(3);
		boolean success = testVertexSet(vertices,graph);
		assertTrue("Something went wrong with rhe vertices.",success);
		
			// check if the size of the edgeSet is as expected
		success = testEdgeSetSize(2,graph);
		assertTrue("The size of the edgeset is not as expected.", success);
		
			// check if all expected edges are contained
		Hashtable<Integer,Integer> edges = new Hashtable<Integer,Integer>();
		edges.put(2, 0);
		edges.put(1, 3);
		Hashtable<Integer,Integer> weights = new Hashtable<Integer,Integer>();
		weights.put(2, 1);
		weights.put(1, 4);
		success = testEdges(graph,edges,weights);
		assertTrue("Something went wrong with the edges.",success);
		
	}
	
}