	/**
	 * <p>
	 * This interface for our graph class shall deliver a quick overview of the basic functions <br>
	 *  of our graph and their needed input parameter.
	 * </p>
	 */



public interface GraphInterface
{
	/**
	 * <p>
	 * Function to add a vertex to the graph.
	 * </p>
	 * 
	 * @param v is the given vertex that should be add to the graph.
	 * @return true if the given vertex is successfully added to the graph.
	 */
	public boolean addVertex(Vertex v);
	
	/**
	 * <p>
	 * Function to add a new edge to the graph.
	 * </p>
	 * 
	 * @param start vertex, where the new edge begins
	 * @param end vertex, where the new edge shall end 
	 * @param weight an double for the weight of this edge, for unweighted graphs is the default value = 0
	 * @return true if the given edge is successfully added to the graph
	 */
	public boolean addEdge(Vertex start, Vertex end, int weight);
	
	/**
	 * <p>
	 * Function to delete a vertex from the graph, also removes all edges from the graph, which are adjacent to v.
	 * </p>
	 * 
	 * @param v A vertex, which shall be deleted from the graph (incl. all adjacent edges)
	 * @return true if the deletion was successful 
	 */
	public boolean removeVertex(int remove);
	
	/**
	 * <p>
	 * Function for the removal of the edge (start, end) from the graph.
	 * </p>
	 * 
	 * @param e tThe edge that shall been removed from the graph.
	 * @return true if the deletion of the given edge was successful.
	 */
	public boolean removeEdge(Vertex start, Vertex end);
}