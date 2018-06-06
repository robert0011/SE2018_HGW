import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import junit.framework.TestCase;

public class GraphTester extends TestCase {
	
	public void testEmptyConstructor() {
		 Graph g = new Graph();
		 assertNotNull("Graph should != null after call to constructor", g);
		 /*assertEquals("Size on vertexSet should return 0", 0, g.vertexSet.size());*/
		 assertEquals("Size of outEdges should return 0", 0, g.outEdges.size());
		 assertEquals("Size of inEdges should return 0", 0, g.outEdges.size());
	 }
	
	public void testAddVertex() {
		Graph g = new Graph();
		Vertex v1 = new Vertex(1, 2);
		Vertex v2 = new Vertex(1, 2);
		
		boolean success = g.addVertex(v1);
		
		assertTrue("Graph.addVertex(v) did not return true when adding a new unique vertex.",
				success);
		assertEquals("Graph.addVertex(v) did not update the size of its vertex set.", 
				g.vertexSet.size(), 1);
		
		success = g.addVertex(v2);
		
		Vertex v4 = new Vertex(7,8);
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
		Vertex v1 = new Vertex(1, 2);
		g.addVertex(v1);
		Vertex v2 = new Vertex(3,4);
		g.addVertex(v2);
		
		
		
		boolean success = g.addEdge(0, 1, 1);
		assertTrue("edge should be added", success);
		assertEquals(g.edgeSet.size(),1);
		
		success = g.addEdge(1, 2, 1);
		assertFalse("vertex 2 does not exist, edge should not be added", success);
		
		success = g.addEdge(0, 1, 1);
		assertFalse("This edge already exists and should therefore not be added.",success);
		
	}
	
	public void testEdgeSets() {
		Graph g = new Graph();
		Vertex v1 = new Vertex(1, 2);
		g.addVertex(v1);
		Vertex v2 = new Vertex(3,4);
		g.addVertex(v2);
		Vertex v3 = new Vertex(4,5);
		g.addVertex(v3);
		g.addEdge(0, 1, 1);
		g.addEdge(1, 2, 1);
		g.addEdge(0, 2, 1);
		
		// check out going edges
		Hashtable<Integer, List<Integer>> test1 = g.outEdges;
		List<Integer> testList = test1.get(0);
		int a = testList.get(0);
		// the first vertex that can be reached from vertex with label 0 is the vertex with label 1
		assertEquals(a,1);
		a= testList.get(1);
		assertEquals(a,2);
		testList = test1.get(1);
		a= testList.get(0);
		assertEquals(a,2);
		
		// check incoming edges
		test1 = g.inEdges;
		// incoming edges for vertex with label 1
		testList = test1.get(1);
		a = testList.get(0);
		// the first vertex with an edge to vertex with label 1 is the vertex with label 0
		assertEquals(a,0);
		
		testList = test1.get(2);
		a = testList.get(0);
		// the first vertex with an edge to vertex with label 2 is the vertex with label 0
		assertEquals(a,1);
		a = testList.get(1);
		assertEquals(a,0);
		
		
	}
	
	public void testRemoveEdge() {
		Graph g = new Graph();
		boolean success = g.removeEdge(0, 1);
		assertFalse("The edgeSet is empty.",success);
		
		Vertex v0 = new Vertex(1, 2);
		g.addVertex(v0);
		Vertex v1 = new Vertex(3,4);
		g.addVertex(v1);
		g.addEdge(0, 1, 1);
		
		success = g.removeEdge(0, 1);
		assertTrue(success);
		
		success= g.removeEdge(0, 1);
		assertFalse(success);
		
		Vertex v2 = new Vertex(1,0);
		g.addVertex(v2);
		Vertex v3 = new Vertex(2,0);
		g.addVertex(v3);
		g.addEdge(0, 1, 1);
		g.addEdge(0, 3, 1);
		g.addEdge(1, 2, 1);
		g.addEdge(3, 2, 1);
		
		
		// check out going edges:
			Hashtable<Integer, List<Integer>> test1 = g.outEdges;
			List<Integer> testList = test1.get(0);
			
			int a = testList.get(0);
			// the first vertex that can be reached from vertex with label 0 is the vertex with label 1
			assertEquals(a,1);
			a = testList.get(1);
			assertEquals(a,3);
			
			testList = test1.get(1);
			a = testList.get(0);
			assertEquals(a,2);
			
			testList = test1.get(3);
			a = testList.get(0);
			assertEquals(a,2);
			
		// check incoming edges:
			test1 = g.inEdges;
			testList = test1.get(1);
			a = testList.get(0);
			assertEquals(a,0);
			
			testList = test1.get(2);
			a = testList.get(0);
			assertEquals(a,1);
			a = testList.get(1);
			assertEquals(a,3);
			
			testList = test1.get(3);
			a = testList.get(0);
			assertEquals(a,0);
			
			g.removeEdge(1, 2);
			testList = test1.get(2);
			a = testList.size();
			assertEquals(a,1);
			
			g.removeEdge(0, 1);
			g.removeEdge(0, 3);
			g.removeEdge(3, 2);
			assertEquals("edgeSet shold be empty.",g.edgeSet.size(),0);
			
			// g.inEdges.size() is not 0 because the keys are now stored with empty lists
			List<Integer> test = g.inEdges.get(2);
			assertEquals("List of incoming edges to vertex with label 2 should be empty.",test.size(),0);
			
			
			
	}
	
	public void testRemoveVertex() {
		Graph g = new Graph();
		Vertex v0 = new Vertex(1,0);
		g.addVertex(v0);
		assertEquals("size should be 1.",g.vertexSet.size(),1);
		Vertex v1 = new Vertex(0,1);
		g.addVertex(v1);
		assertEquals("size should be 2.",g.vertexSet.size(),2);
		
		boolean success = g.removeVertex(2);
		assertEquals("size should still be 2.",g.vertexSet.size(),2);
		assertFalse("there is no vertex with label 2 in vertexSet, should not be removed.",success);
		
		
		Vertex v2 = new Vertex(3,4);
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
	