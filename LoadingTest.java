/**
* Class that tests the loading of files
* @author Bruckmann C., Wagner R.
*
*/
import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


public class LoadingTest {
	
	/**
	 * Returns true if the vertex set contains the keys from 0 to maxInt.
	 * @param d
	 * @param maxIndex
	 * @return
	 */
	public boolean testVertexSet(DrawPanel d,int maxIndex)
	{
		boolean success = true;
		// check if all vertices from 0 to maxIndex exist
		for(int i = 0; i<= maxIndex; i = i+1)
    	{
			if(!d.graph.vertexSet.containsKey(maxIndex))
			{
				success = false;
			}
    	}
		// the graph should not contain more vertices than maxIndex
		if(d.graph.vertexSet.containsKey(maxIndex+1))
		{
			success = false;
		}
		return success;
	}
	
	/**
	 * Returns true if the graph exactly contains a given number of edges.
	 * @param size
	 * @param d
	 * @return
	 */
	public boolean testEdgeSetSize(int size, DrawPanel d)
    {
    	int currentSum  = 0;
    	Enumeration<Integer> keys = d.graph.outEdges.keys();
    	while(keys.hasMoreElements())
    	{
    		Integer curKey = keys.nextElement();
    		List<Edge> list = d.graph.outEdges.get(curKey);
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
	 * @param d
	 * @param edges
	 * @param weights
	 * @return
	 * @throws NullPointerException
	 */
    public boolean testEdges(Graph graph,Hashtable<Integer,List<Integer>> edges, Hashtable<Integer,Integer> weights) throws NullPointerException
    {
    	boolean foundAll = true;
		boolean found = false;
		// go through the list of integers and find them in the actually edges
    	Enumeration<Integer> edgeKeys = edges.keys();
    	while(edgeKeys.hasMoreElements())
    	{
    		found = false;
    		Integer curStart = edgeKeys.nextElement();
    		try
    		{
    			List<Integer> elementsToFindInTheGraph = edges.get(curStart);
    			//List<Edge> list = graph.outEdges.get(curStart);
    			// list is a list of edges which needs to correspond to edges (a list of integers)
    			for(int i = 0; i < elementsToFindInTheGraph.size(); i = i+1)
    			{
    				int EndToFind = elementsToFindInTheGraph.get(i);
    				for(int j = 0; j < graph.outEdges.get(curStart).size(); j = j+1)
    				{
    					Edge potEdge = graph.outEdges.get(curStart).get(j);
    					int potEnd = potEdge.getEnd().getIndex();
    					if(EndToFind == potEnd)
        				{
        					// the weights have to be the same as well
        					if(potEdge.getWeight() == weights.get(curStart))
        					{
        						found = true;
        					}
        					
        				}
    				}
    				
    			}
    		}
    		catch(NullPointerException e)
    		{
    			// if some list entry does not exist, an exception is thrown
    				// and should be re-thrown
    			System.out.println("Edge finder throws exception.");
    			throw e;
    		}
    		if(!found)
    		{
    			foundAll = false;
    		}
    	}
    	return foundAll;
    }

    /**
     * Tests whether a FileNotFoundException is thrown when a non-existing file is selected.
     * A window should be shown that says that the file does not exist.
     * @throws FileNotFoundException
     */
    @Test(expected = FileNotFoundException.class)
    public void notExistingFile() throws FileNotFoundException {
    	DrawPanel drawPanel = new DrawPanel();
    	// here the exception should be thrown
    	boolean success = drawPanel.loadFile("abc.txt");
    	assertFalse("Non-existent file was selected but read in.",success);
    	assertTrue("No file was read in but the vertexSet is non-empty.",drawPanel.graph.vertexSet.isEmpty());
    }
    
    
    /**
     * Assures that a non- .txt file is not read in.
     * A window should appear that says that you did not choose a .txt file.
     * @throws FileNotFoundException
     */
    @Test
    public void nontxtFile()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	assertNotNull("No DrawPanel is constructed.", drawPanel);
    	boolean success = true;
		try {
			success = drawPanel.loadFile("EXGraph.docx");
		} catch (FileNotFoundException e) {
			// No exception should be thrown because a non- .txt file 
			// is not attempted to be read in
		assertTrue("No exception should be thrown.", false);
			
		}
    	assertFalse("Non- .txt file was selected but read in.",success);
    	assertTrue("No file was read in but the vertexSet is non-empty.",drawPanel.graph.vertexSet.isEmpty());
    }
    
    /**
     * Tests if a file with a duplicate edge is loaded correctly.
     * A window should appear that says that there is a duplicate edge in the file.
     */
    @Test
    public void duplicateEdge()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	boolean success = true;
    	try
    	{
			success = drawPanel.loadFile("EXgraph_duplicateEdge.txt");
		} 
    	catch (FileNotFoundException e)
    	{
    		// No exception should be thrown because the file exists
			assertTrue("No exception should be thrown.",true);
		}
    	// while reading in, a problem should be recognized
    	assertFalse("A problem should be recognized when reading in a duplicate edge.",success);
    	
    	// the content of the file should be read in correctly anyways
    		// (despite the duplicate edge)
    	assertFalse("The graph should be read in anyways.",drawPanel.graph.vertexSet.isEmpty());
    	
    	// the graph should contain the vertices 0 to 19
    	success = testVertexSet(drawPanel,19);
    	assertTrue("Incorrect number of vertices", success);
    	
    	// the graph should contain 3 edges (one of the 4 edges can not be read in)
    	success = testEdgeSetSize(3,drawPanel);
    	assertTrue("Incorrect number of edges.", success);
    	
    	// the file contains the following edges: (mapping startvertex to endvertex)
    	Hashtable<Integer,List<Integer>> edges = new Hashtable<Integer, List<Integer>>();
    	List<Integer> curEnds = new ArrayList<Integer>();
    	curEnds.add(5);
    	edges.put(14,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(3);
    	edges.put(2, curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(19);
    	edges.put(5, curEnds);
    	
    	// mapping start vertices to weights
    	Hashtable<Integer, Integer> weights = new Hashtable<Integer,Integer>();
    	weights.put(14,9);
    	weights.put(2,1);
    	weights.put(5,6);
    	try
    	{
    		success = testEdges(drawPanel.graph, edges,weights);
        	assertTrue("Not all edges could be found.", success);
    	}
    	catch(NullPointerException e)
    	{
    		assertTrue("Not all edges could be found, exception was thrown.", false);
    	}
    	
    }
    
    
    /**
     * Assures that the user gets informed when the read in number of edges does not correspond to the number given in the file.
     * A window should appear that says that the numbers are different.
     */
    @Test
    public void lessEdges()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	boolean success = true;
    	try
    	{
			success = drawPanel.loadFile("EXgraph_lessEdges.txt");
		} 
    	catch (FileNotFoundException e)
    	{
    		// No exception should be thrown because the file exists
			assertTrue("No exception should be thrown.",true);
		}
    	assertFalse("A problem should be recognized when reading in less edges than specified.",success);
    	
    	// check if the graph is read in correctly
    		// it should contain 19 vertices
    	assertTrue("Incorrect number of vertices", testVertexSet(drawPanel,19));
    		// it should contain 3 edges (not 6 as specified in the file).
    	assertTrue("Incorrect number of edges", testEdgeSetSize(3,drawPanel));
    		// it should contain the following edges with the following weights
    	Hashtable<Integer,List<Integer>> edges = new Hashtable<Integer, List<Integer>>();
    	List<Integer> curEnds = new ArrayList<Integer>();
    	curEnds.add(5);
    	edges.put(14,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(3);
    	edges.put(2, curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(19);
    	edges.put(5, curEnds);
    	
    	// mapping start vertices to weights
    	Hashtable<Integer, Integer> weights = new Hashtable<Integer,Integer>();
    	weights.put(14,9);
    	weights.put(2,1);
    	weights.put(5,6);
    	try
    	{
    		success = testEdges(drawPanel.graph, edges,weights);
        	assertTrue("Not all edges could be found.", success);
    	}
    	catch(NullPointerException e)
    	{
    		assertTrue("Not all edges could be found, exception was thrown.", false);
    	}
    	
    }
    
    @Test
    /**
     * Assures that a file that does not provide the number of vertices and edges is not read in.
     * A window should appear that the number of edges or vertices is missing.
     */
    public void missingHead()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	boolean success = true;
    	try
    	{
			success = drawPanel.loadFile("EXgraph_missingHead.txt");
		} 
    	catch (FileNotFoundException e)
    	{
    		// No exception should be thrown because the file exists
			assertTrue("No exception should be thrown.",true);
		}
    	assertFalse("A file with a incorrect head should not be read in.",success);
    	assertTrue("The vertexSet should be empty.",drawPanel.graph.vertexSet.isEmpty());
    }
    
    /**
     * Assures that the program does not stop when edges in the file contain letters.
     * The other edges should be read in anyways.
     * A window should appear that says that an edge could not be read in.
     */
    @Test
    public void fileWithLetters()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	boolean success = true;
    	try
    	{
			success = drawPanel.loadFile("EXgraphWithLetters.txt");
		} 
    	catch (FileNotFoundException e)
    	{
    		// No exception should be thrown because the file exists
			assertTrue("No exception should be thrown.",true);
		}
    	assertFalse("A problem should be recognized when reading in a file that contains letters.", success);
    	
    	// check if the rest of the graph is read in correctly
    	// it should contain 9 vertices
    	assertTrue("Incorrect number of vertices", testVertexSet(drawPanel,9));
    		// it should contain 5 edges (one of the 6 edges in the file contains a letter).
    	assertTrue("Incorrect number of edges", testEdgeSetSize(5,drawPanel));
    		// it should contain the following edges with the following weights
    	Hashtable<Integer,List<Integer>> edges = new Hashtable<Integer, List<Integer>>();
    	List<Integer> curEnds = new ArrayList<Integer>();
    	curEnds.add(2);
    	edges.put(1,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(4);
    	edges.put(2,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(7);
    	edges.put(5,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(7);
    	edges.put(6,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(6);
    	edges.put(4,curEnds);
    	
    	// mapping start vertices to weights
    	Hashtable<Integer, Integer> weights = new Hashtable<Integer,Integer>();
    	weights.put(1,1);
    	weights.put(2,1);
    	weights.put(5,1);
    	weights.put(6,1);
    	weights.put(4,1);
    	try
    	{
    		success = testEdges(drawPanel.graph, edges,weights);
        	assertTrue("Not all edges could be found.", success);
    	}
    	catch(NullPointerException e)
    	{
    		assertTrue("Not all edges could be found, exception was thrown.", false);
    	}
    	
    }
    
    /**
     * Assures that the program does not stop when edges in the file are unreadable.
     * The other edges should be read in anyways.
     * A window should appear that says that an edge could not be read in.
     */
    @Test
    public void unreadableEdge()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	boolean success = true;
    	try
    	{
			success = drawPanel.loadFile("EXgraph_unreadableEdge.txt");
		} 
    	catch (FileNotFoundException e)
    	{
    		// No exception should be thrown because the file exists
			assertTrue("No exception should be thrown.",true);
		}
    	assertFalse("A problem should be recognized when reading in a file that contains an unreadable edge.", success);
    	// check if the rest of the graph is read in correctly
    	// it should contain 19 vertices
    	assertTrue("Incorrect number of vertices", testVertexSet(drawPanel,19));
    		// it should contain 3 edges (the edge 15,16 in the file is unreadable).
    	assertTrue("Incorrect number of edges", testEdgeSetSize(3,drawPanel));
    		// it should contain the following edges with the following weights
    	Hashtable<Integer,List<Integer>> edges = new Hashtable<Integer, List<Integer>>();
    	List<Integer> curEnds = new ArrayList<Integer>();
    	curEnds.add(5);
    	edges.put(14,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(3);
    	edges.put(2,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(19);
    	edges.put(5,curEnds);
    	
    	// mapping start vertices to weights
    	Hashtable<Integer, Integer> weights = new Hashtable<Integer,Integer>();
    	weights.put(14,9);
    	weights.put(2,1);
    	weights.put(5,6);
    	try
    	{
    		success = testEdges(drawPanel.graph, edges,weights);
        	assertTrue("Not all edges could be found.", success);
    	}
    	catch(NullPointerException e)
    	{
    		assertTrue("Not all edges could be found, exception was thrown.", false);
    	}
    	
    }
    
    /**
     * Assures that a file that is in the correct format is loaded and that every allowed edge-format
     * can be read.
     */
    @Test
    public void readableGraph()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	boolean success = true;
    	try
    	{
    		// the file provides an edge every of the 4 types of edges
			success = drawPanel.loadFile("EXgraph_ok.txt");
		} 
    	catch (FileNotFoundException e)
    	{
    		// No exception should be thrown because the file exists
			assertTrue("No exception should be thrown.",true);
		}
    	assertTrue("The file should be read in without any problems.", success);
    	
    	// check if the graph is correct
    		// it should contain 19 vertices
    	assertTrue("Incorrect number of vertices", testVertexSet(drawPanel,19));
    		// it should contain 4 edges
    	assertTrue("Incorrect number of edges", testEdgeSetSize(4,drawPanel));
    		// it should contain the following edges with the following weights
    	Hashtable<Integer,List<Integer>> edges = new Hashtable<Integer, List<Integer>>();
    	List<Integer> curEnds = new ArrayList<Integer>();
    	curEnds.add(5);
    	edges.put(14,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(3);
    	edges.put(2,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(19);
    	edges.put(5,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(7);
    	edges.put(8, curEnds);
    	
    	// mapping start vertices to weights
    	Hashtable<Integer, Integer> weights = new Hashtable<Integer,Integer>();
    	weights.put(14,9);
    	weights.put(2,1);
    	weights.put(5,6);
    	weights.put(8,1);
    	try
    	{
    		success = testEdges(drawPanel.graph, edges,weights);
        	assertTrue("Not all edges could be found.", success);
    	}
    	catch(NullPointerException e)
    	{
    		assertTrue("Not all edges could be found, exception was thrown.", false);
    	}
    }
    
    /**
     * Assures that edges between vertices with indices that exceed the number of vertices given in the file are not added.
     * A window should appear which says that the number of edges given in the file does not match the number of edges read in.
     */
    
    @Test
    public void edgeWithNonExistingVertex()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	boolean success = true;
    	try
    	{
    		success = drawPanel.loadFile("nonExistingVertex.txt");
		} 
    	catch (FileNotFoundException e)
    	{
    		// No exception should be thrown because the file exists
			assertTrue("No exception should be thrown.",true);
		}
    	assertFalse("A Problem should be recognized when adding an edge with a vertex that is not given in the file.", success);
    	
    	// check if the rest of the graph is read in correctly
    		// it should contain 9 vertices
    	assertTrue("Incorrect number of vertices", testVertexSet(drawPanel,9));
    		// it should contain 6 edges (one edge in the file is incorrect).
    	assertTrue("Incorrect number of edges", testEdgeSetSize(6,drawPanel));
    		// it should contain the following edges with the following weights
    	Hashtable<Integer,List<Integer>> edges = new Hashtable<Integer, List<Integer>>();
    	List<Integer> curEnds = new ArrayList<Integer>();
    	curEnds.add(2);
    	edges.put(1,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(3);
    	edges.put(2,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(4);
    	edges.put(3,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(5);
    	edges.put(4,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(7);
    	edges.put(5,curEnds);
    	curEnds = new ArrayList<Integer>();
    	curEnds.add(7);
    	edges.put(6,curEnds);
    	
    	// mapping start vertices to weights
    	Hashtable<Integer, Integer> weights = new Hashtable<Integer,Integer>();
    	weights.put(1,1);
    	weights.put(2,1);
    	weights.put(3,1);
    	weights.put(4,1);
    	weights.put(5,1);
    	weights.put(6,1);
    	try
    	{
    		success = testEdges(drawPanel.graph, edges,weights);
        	assertTrue("Not all edges could be found.", success);
    	}
    	catch(NullPointerException e)
    	{
    		assertTrue("Not all edges could be found, exception was thrown.", false);
    	}
    	
    }
    
    /**
     * Assures that no edges with negativeWeights are read in.
     */
    @Test
    public void negativeWeights()
    {
    	DrawPanel drawPanel = new DrawPanel();
    	boolean success = true;
    	try
    	{
    		success = drawPanel.loadFile("EXgraph_negWeight.txt");
		} 
    	catch (FileNotFoundException e)
    	{
    		// No exception should be thrown because the file exists
			assertTrue("No exception should be thrown.",true);
		}
    	// edges with the sign "-" should not fit any edge-type
    	assertFalse("A Problem should be recognized when adding an edge with a negative weight.", success);
    	
    	// check if the rest of the graph is read in correctly
    		// it should contain 4 vertices
    	assertTrue("Incorrect number of vertices", testVertexSet(drawPanel,4));
    		// it should contain 1 edges (one edge in the file is incorrect).
    	assertTrue("Incorrect number of edges", testEdgeSetSize(1,drawPanel));
    		// it should contain the following edges with the following weights
    	Hashtable<Integer,List<Integer>> edges = new Hashtable<Integer, List<Integer>>();
    	List<Integer> curEnds = new ArrayList<Integer>();
    	curEnds.add(2);
    	edges.put(1,curEnds);
    	
    	// mapping start vertices to weights
    	Hashtable<Integer, Integer> weights = new Hashtable<Integer,Integer>();
    	weights.put(1,1);
    	
    	try
    	{
    		success = testEdges(drawPanel.graph, edges,weights);
        	assertTrue("Not all edges could be found.", success);
    	}
    	catch(NullPointerException e)
    	{
    		assertTrue("Not all edges could be found, exception was thrown.", false);
    	}
    	
    }

    
    
}