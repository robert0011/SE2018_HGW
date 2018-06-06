import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class GraphTester extends TestCase {
	
	public void testEmptyConstructor() {
		System.out.println("hi");
		 Graph g = new Graph();
		 System.out.println("hi");
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
		System.out.println("number of nodes: "+g.vertexSet.size());
		
		success = g.addVertex(v1);
		assertFalse("Graph.addVertex(v1) should return false since v1 was already added",
				success);
		assertEquals("Graph should contain 1 element (that was added twice)",g.vertexSet.size(),1);
		
		success = g.addVertex(v2);
		assertFalse("Graph.addVertex(v2) should return false because v2=v1",
				success);
		assertEquals("after trying to add v2 the size should still be 1", 
				g.vertexSet.size(), 1);
		
		Vertex v4 = new Vertex(7,8);
		g.addVertex(v4);
		assertEquals("Graph should contain 2 elements after addiing v4",g.vertexSet.size(),2);
		
		Vertex v3 = null;
		//System.out.println("Hi");
		
		try{
			boolean success2 =g.addVertex(v3);
			assertTrue("Graph.addVertex did not throw NullPointer but added v3", false);
		}catch (Exception e){
			assertTrue("Exception was thrown", true);
		}
		
	
	}
	
	public void testAddEdge() {
		Graph g = new Graph();
		Vertex v1 = new Vertex(1, 2);
		g.addVertex(v1);
		System.out.println("vertexlabel of v1+1: "+g.vertexlabel);
		Vertex v2 = new Vertex(3,4);
		g.addVertex(v2);
		System.out.println("vertexlabel of v2+1: "+g.vertexlabel);
		
		
		
		boolean success = g.addEdge(0, 1, 1);
		assertTrue("edge should be added", success);
		assertEquals(g.edgeSet.size(),1);
		
		success = g.addEdge(1, 2, 1);
		assertFalse("vertex 2 does not exist, edge should not be added", success);
		
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
		
		Set<Edge> test = g.edgeSet;
		System.out.println("testEdgeSets, größe edgeSet: "+g.edgeSet.size());
		
		// check out going edges
		Hashtable<Integer, List<Integer>> test1 = g.outEdges;
		List<Integer> testList = test1.get(0);
		System.out.println("size of testList1: "+testList.size());
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
		System.out.println("number of edges in edgeSet: "+g.edgeSet.size());
		
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
			
			
	}
	
	public void testRemoveVertex() {
		Graph g = new Graph();
		Vertex v0 = new Vertex(1,0);
		g.addVertex(v0);
		assertEquals("size should be 1.",g.vertexSet.size(),1);
		Vertex v1 = new Vertex(0,1);
		g.addVertex(v1);
		assertEquals("size should be 2.",g.vertexSet.size(),2);
		
		g.removeVertex(3);
		assertEquals("size should still be 2.",g.vertexSet.size(),2);
		g.removeVertex(0);
	}
}
	