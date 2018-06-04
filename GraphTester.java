import java.util.Hashtable;
import java.util.List;

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
		System.out.println("number of edges: "+g.vertexSet.size());
		
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
}
	