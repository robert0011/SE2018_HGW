	/**
	 * This documentation is the description of the structure of our implemented graph.
	 * 
	 */



public interface GraphInterface
{
	/**
	 * Function to add a vertex to the graph.
	 * @param v is the given vertex that should be add to the graph.
	 * @return true if the given vertex is successfully added to the graph.
	 */
	public boolean addVertex(Vertex v);	// vertex v returns, true if vertex is successfully added to graph
	
	/**
	 * Function to add a new edge to the graph.
	 * @param start vertex, where the new edge begins
	 * @param end vertex, where the new edge shall end 
	 * @param weight an double for the weight of this edge, for unweighted graphs is the default value = 0
	 * @return true if the given edge is successfully added to the graph
	 */
	public boolean addEdge(Vertex start, Vertex end, int weight); // start = startvertex, end = endvertex, weight for the edge (default = 0) returns true if edge is successfulyl added to graph 
	
	/**
	 * Function to delete a vertex from the graph, also removes all edges from the graph, which are adjacent to v.
	 * @param v a vertex, which shall be deleted from the graph (incl. all adjacent edges)
	 * @return true if the deletion was successful 
	 */
	public boolean removeVertex(int remove); // removes vertex s from graph
	
	/**
	 * 
	 * @param e the edge that shall been removed from the graph 
	 * @return true if the deletion of the given edge was successful
	 */
	public boolean removeEdge(Vertex start, Vertex end); // removes edge e from graph
}