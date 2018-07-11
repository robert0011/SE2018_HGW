import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import junit.framework.TestCase;

public class GraphTester extends TestCase {
	
	public void testEmptyConstructor() {
		 Graph g = new Graph();
		 assertNotNull("Graph should != null after call of constructor", g);
		 /*assertEquals("Size on vertexSet should return 0", 0, g.vertexSet.size());*/
		 assertEquals("Size of outEdges should return 0", 0, g.outEdges.size());
		 assertEquals("Size of inEdges should return 0", 0, g.outEdges.size());
	 }
	
	public void testAddVertex() {
		Graph g = new Graph();
		assertFalse("Graph g should not contain vertices.", g.vertexSet.containsKey(0));
		Vertex v1 = new Vertex(10,1, 2);
		
		Vertex v2 = new Vertex(10,1, 2);
				
		boolean success = g.addVertex(v1);
		
		assertTrue("Graph.addVertex(v) did not return true when adding a new unique vertex.",
				success);
		assertEquals("Graph.addVertex(v) did not update the size of its vertex set.", 
				g.vertexSet.size(), 1);
		
		success = g.addVertex(v2);
		assertEquals("Vertex v1 should have index 0.",v1.getIndex(),0);
		assertEquals("Vertex v2 should have index 1.",v2.getIndex(),1);

		
		Vertex v4 = new Vertex(10,7,8);
		g.addVertex(v4);
		assertEquals("Graph should contain 3 elements after addiing v4",g.vertexSet.size(),3);
		
		Vertex v3 = null;
		
		try{
			g.addVertex(v3);
			assertTrue("Graph.addVertex did not throw NullPointer but added v3", false);
		}catch (Exception e){
			assertTrue("Exception was thrown", true);
		}
		
	
	}
	
	public void testAddEdge() {
		Graph g = new Graph();
		Vertex v0 = new Vertex(10,1, 2);
		g.addVertex(v0);
		Vertex v1 = new Vertex(10,3,4);
		g.addVertex(v1);
		
		
		
		boolean success = g.addEdge(v0, v1, 1);
		assertTrue("edge should be added", success);
				assertEquals(g.outEdges.size(),1);

		Vertex v2 = new Vertex(10,5,8);
		boolean success2 = g.addEdge(v1, v2, 1);
		
		assertFalse("vertex v2 does not exist in the graph, edge should not be added", success2);
		
		success = g.addEdge(v0, v1, 1);
		assertFalse("This edge already exists and should therefore not be added.",success);
		
	}
	
	public void testEdgeSets() {
		Graph g = new Graph();
		Vertex v0 = new Vertex(10,1, 2);
		g.addVertex(v0);
		Vertex v1 = new Vertex(10,3,4);
		g.addVertex(v1);
		Vertex v2 = new Vertex(10,4,5);
		g.addVertex(v2);
		g.addEdge(v0, v1, 1);
		g.addEdge(v1, v2, 1);
		g.addEdge(v0, v2, 1);
		
		// check out going edges
		Hashtable<Integer, List<Edge>> test1 = g.outEdges;
		List<Edge> testList = test1.get(0);
		Edge a = testList.get(0);
		// the first vertex that can be reached from vertex with label 0 is the vertex with label 1
		assertEquals(a.getEnd().getIndex(),1);
		a= testList.get(1);
		assertEquals(a.getEnd().getIndex(),2);
		testList = test1.get(1);
		a= testList.get(0);
		assertEquals(a.getEnd().getIndex(),2);
		
		// check incoming edges
		test1 = g.inEdges;
		// incoming edges for vertex with label 1
		testList = test1.get(1);
		a = testList.get(0);
		// the first vertex with an edge to vertex with label 1 is the vertex with label 0
		assertEquals(a.getStart().getIndex(),0);
		
		testList = test1.get(2);
		a = testList.get(0);
		// the first vertex with an edge to vertex with label 2 is the vertex with label 0
		assertEquals(a.getStart().getIndex(),1);
		a = testList.get(1);
		assertEquals(a.getStart().getIndex(),0);
		
		
	}
	
	public void testRemoveEdge() {
		Graph g = new Graph();
		Vertex v0 = new Vertex(10,1,2);
		Vertex v1 = new Vertex(10,2,3);
		boolean success = g.removeEdge(v0, v1);
		assertFalse("The edgeSet is empty.",success);
		
		g.addVertex(v0);
		g.addVertex(v1);
		g.addEdge(v0, v1, 1);
		
		success = g.removeEdge(v0, v1);
		assertTrue(success);
		
		success= g.removeEdge(v0, v1);
		assertFalse(success);
		
		Vertex v2 = new Vertex(10,1,0);
		g.addVertex(v2);
		Vertex v3 = new Vertex(10,2,0);
		g.addVertex(v3);
		g.addEdge(v0, v1, 1);
		g.addEdge(v0, v3, 1);
		g.addEdge(v1, v2, 1);
		g.addEdge(v3, v2, 1);
		
		
		// check out going edges:
			Hashtable<Integer, List<Edge>> test1 = g.outEdges;
			List<Edge> testList = test1.get(0);
			
			Edge a = testList.get(0);
			// the first vertex that can be reached from vertex with label 0 is the vertex with label 1
			assertEquals(a.getEnd().getIndex(),1);
			a = testList.get(1);
			assertEquals(a.getEnd().getIndex(),3);
			
			testList = test1.get(1);
			a = testList.get(0);
			assertEquals(a.getEnd().getIndex(),2);
			
			testList = test1.get(3);
			a = testList.get(0);
			assertEquals(a.getEnd().getIndex(),2);
			
		// check incoming edges:
			test1 = g.inEdges;
			testList = test1.get(1);
			a = testList.get(0);
			assertEquals(a.getStart().getIndex(),0);
			
			testList = test1.get(2);
			a = testList.get(0);
			assertEquals(a.getStart().getIndex(),1);
			a = testList.get(1);
			assertEquals(a.getStart().getIndex(),3);
			
			testList = test1.get(3);
			a = testList.get(0);
			assertEquals(a.getStart().getIndex(),0);
			
			g.removeEdge(v1, v2);
			test1 = g.inEdges;
			testList = test1.get(2);
			int b = testList.size();
			assertEquals(b,1);
			
			g.removeEdge(v0, v1);
			g.removeEdge(v0, v3);
			g.removeEdge(v3, v2);

			Enumeration<List<Edge>> e = g.inEdges.elements();
			while(e.hasMoreElements())
			{
				assertEquals("All Lists in inEdges should be empty.",e.nextElement().size(),0);
			}
			
			e = g.outEdges.elements();
			while(e.hasMoreElements())
			{
				assertEquals("All Lists in outEdges should be empty.",e.nextElement().size(),0);
			}
			
			
			
			
			// g.inEdges.size() is not 0 because the keys are now stored with empty lists
			List<Edge> test = g.inEdges.get(2);
			assertEquals("List of incoming edges to vertex with label 2 should be empty.",test.size(),0);
			
			
			
	}
	
	public void testRemoveVertex()
	{
		Graph g = new Graph();
		Vertex v0 = new Vertex(10,1,0);
		g.addVertex(v0);
		assertEquals("size should be 1.",g.vertexSet.size(),1);
		Vertex v1 = new Vertex(10,0,1);
		g.addVertex(v1);
		assertEquals("size should be 2.",g.vertexSet.size(),2);
		
		boolean success = g.removeVertex(2);
		assertEquals("size should still be 2.",g.vertexSet.size(),2);
		assertFalse("there is no vertex with label 2 in vertexSet, should not be removed.",success);
		
		
		Vertex v2 = new Vertex(10,3,4);
		g.addVertex(v2);
		
		// enumerate the elements in vertexSet:
		Enumeration<Integer> test = g.vertexSet.keys();
		//System.out.println("Elements in vertexSet:");
		int assumedSize = 3;
		int realSize = 0;
		while(test.hasMoreElements())
		{
			// should contain 0, 1 and 2
			Integer v = test.nextElement();
			assertTrue(v == 0 | v==1 | v==2);
			realSize = realSize+1;
			//System.out.print(v+", ");
			
		}
		assertEquals(assumedSize,realSize);
		//System.out.println("");
		
		success = g.removeVertex(1);
		assertEquals("size of vertexSet should be two.",g.vertexSet.size(),2);
		assertTrue("vertex with label 1 was in vertexSet, should be removed.", success);
		
		test = g.vertexSet.keys();
		//System.out.println("Elements in vertexSet:");
		assumedSize = 2;
		realSize = 0;
		while(test.hasMoreElements())
		{
			Integer v = test.nextElement();
			assertTrue(v == 0 | v==2);
			realSize = realSize +1;
			//System.out.print(v+", ");
			
		}
		assertEquals("Something went wrong when removing vertices from vertexSet.",assumedSize,realSize);
		//System.out.println("");
		
		success = g.removeVertex(0);
		assertEquals("size of vertexSet should be zero.",g.vertexSet.size(),1);
		assertTrue("vertex with label 0 was in vertexSet, should be removed.", success);
		
		success = g.removeVertex(2);
		assertTrue("vertex with label 2 was in vertexSet, should be removed.",success);
		assertEquals("size should still be 0.",g.vertexSet.size(),0);
		
		success = g.removeVertex(2);
		assertFalse("vertexSet is empty, nothing to be removed.",success);
		
	}
}
	