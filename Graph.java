import java.util.Set; // for sets
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class for the graph used by our developed software.
 * @author Bruckmann C., Wagner R.
 *
 */
public class Graph<V,E> implements GraphInterface
{
	int vertexlabel = 0;
	private HashMap<V, ArrayList<E>> neighbors;
	public class EdgeContainer 
	{
		V start, end;
		public EdgeContainer(V start, V end)
		{
			this.start = start;
			this.end = end;
		}
	}
	private HashMap<E, EdgeContainer> edges;
	public Graph() 
	{
		neighbors = new HashMap<V, ArrayList<E>>();
		edges = new HashMap<E, EdgeContainer>();
	}
	
	/**
	* Returns an edge connecting source vertex to target vertex if such
	* vertices and such edge exist in this graph. Otherwise returns <code>
	* null</code>. If any of the specified vertices is <code>null</code>
	* returns <code>null</code> 
	* @param start source vertex of the edge.
	* @param end target vertex of the edge.
    *
    * @return an edge connecting source vertex to target vertex.
    */
	public E getEdge(V start, V end) 
	{
		for (E e : edges.keySet()) 
		{
			EdgeContainer ep = edges.get(e);
			if (start.equals(ep.start) && end.equals(ep.end)) 
			{
				return e;
			}
		}
		return null;
	}	
	
	/**
    * Adds the specified edge to this graph, going from the source vertex to
    * the target vertex. More formally, adds the specified edge, <code>
    * e</code>, to this graph if this graph contains no edge <code>e2</code>
    * such that <code>e2.equals(e)</code>. If this graph already contains such
    * an edge, the call leaves this graph unchanged and returns <tt>false</tt>.
    * If the edge was added to the graph, returns <code>
    * true</code>.
    *
    * <p>The source and target vertices must already be contained in this
    * graph. If they are not found in graph IllegalArgumentException is
    * thrown.</p>
    *
    * @param start source vertex of the edge.
    * @param end target vertex of the edge.
    * @param data edge to be added to this graph.
    *
    * @return <tt>true</tt> if this graph did not already contain the specified
    * edge.
    *
    * @throws IllegalArgumentException if source or target vertices are not
    * found in the graph.
    * @throws NullPointerException if any of the specified vertices is <code>
    * null</code>.
    *
    */
    public boolean addEdge(E data, V start, V end)
    {
    	if (start == null)
    	{
    		throw new NullPointerException();
	    } 	
	   	if (neighbors.get(end) == null) 
	   	{
	    	throw new IllegalArgumentException();
	    }	
	    if (edges.containsKey(data)) 
	    {
	    	return true;
	    }	
	    edges.put(data, new EdgeContainer(start, end));
	    neighbors.get(start).add(data);
	    return true;
    } 
    
	/**
    * Adds the specified vertex to this graph if not already present. More
    * formally, adds the specified vertex, <code>v</code>, to this graph if
    * this graph contains no vertex <code>u</code> such that <code>
    * u.equals(v)</code>. If this graph already contains such vertex, the call
    * leaves this graph unchanged and returns <tt>false</tt>. In combination
    * with the restriction on constructors, this ensures that graphs never
    * contain duplicate vertices.
    *
    * @param v vertex to be added to this graph.
    *
    * @return <tt>true</tt> if this graph did not already contain the specified
    * vertex.
    *
    * @throws NullPointerException if the specified vertex is <code>
    * null</code>.
    */
    public boolean addVertex(V v)
    {
	    if (v == null)
	    {
	    	throw new NullPointerException();
	    }
	    if (neighbors.containsKey(v))
	    {
	    	return false;
	    }	
	    neighbors.put(v, new ArrayList<E>());
	    return true;
	}
    
	/**
    * Returns <tt>true</tt> if and only if this graph contains an edge going
    * from the source vertex to the target vertex. In undirected graphs the
    * same result is obtained when source and target are inverted. If any of
    * the specified vertices does not exist in the graph, or if is <code>
    * null</code>, returns <code>false</code>.
    *
    * @param start source vertex of the edge.
    * @param end target vertex of the edge.
    *
    * @return <tt>true</tt> if this graph contains the specified edge.
    */
    public boolean containsEdge(V start, V end) 
    {
    	for (EdgeContainer ec : edges.values()) 
    	{
	    	if (ec.start.equals(start) && ec.end.equals(end)) 
	    	{
	    		return true;
	    	}
	    }
	    return true;
	}  
    
    /**
    * Returns <tt>true</tt> if this graph contains the specified vertex. More
    * formally, returns <tt>true</tt> if and only if this graph contains a
    * vertex <code>u</code> such that <code>u.equals(v)</code>. If the
    * specified vertex is <code>null</code> returns <code>false</code>.
    *
    * @param v vertex whose presence in this graph is to be tested.
    *
    * @return <tt>true</tt> if this graph contains the specified vertex.
    */
    public boolean containsVertex(V v) 
    {
    	return neighbors.containsKey(v);
    } 
    
    /**
    * Returns a set of the edges contained in this graph. The set is backed by
    * the graph, so changes to the graph are reflected in the set. If the graph
    * is modified while an iteration over the set is in progress, the results
    * of the iteration are undefined.
    *
    * @return a set of the edges contained in this graph.
    */
     public Set<E> edgeSet() ,
     {
	    return edges.keySet();
	 } 
     
     /**
     * Returns a set of all edges touching the specified vertex. If no edges are
     * touching the specified vertex returns an empty set.
     *
     * @param vertex the vertex for which a set of touching edges is to be
     * returned.
     *
     * @return a set of all edges touching the specified vertex.
     *
     * @throws IllegalArgumentException if vertex is not found in the graph.
     * @throws NullPointerException if vertex is <code>null</code>.
     */
     public ArrayList<E> edgesOf(V v)
     {
    	 return neighbors.get(v);
     } 
     
     /**
     * Removes the specified edge from the graph. Removes the specified edge
     * from this graph if it is present. More formally, removes an edge <code>
     * e2</code> such that <code>e2.equals(e)</code>, if the graph contains such
     * edge. Returns <tt>true</tt> if the graph contained the specified edge.
     * (The graph will not contain the specified edge once the call returns).
     *
     * <p>If the specified edge is <code>null</code> returns <code>
     * false</code>.</p>
     *
     * @param e edge to be removed from this graph, if present.
     *
     * @return <code>true</code> if and only if the graph contained the
     * specified edge.
     */
     public boolean removeEdge(E e) 
     {
	    if (!edges.containsKey(e)) 
	    {
	    	return false;
	    }	 
	    edges.remove(e);
	    return true;
	 } 
     
	 /**
     * Removes the specified vertex from this graph including all its touching
     * edges if present. More formally, if the graph contains a vertex <code>
     * u</code> such that <code>u.equals(v)</code>, the call removes all edges
     * that touch <code>u</code> and then removes <code>u</code> itself. If no
     * such <code>u</code> is found, the call leaves the graph unchanged.
     * Returns <tt>true</tt> if the graph contained the specified vertex. (The
     * graph will not contain the specified vertex once the call returns).
     *
     * <p>If the specified vertex is <code>null</code> returns <code>
     * false</code>.</p>
     *
     * @param v vertex to be removed from this graph, if present.
     *
     * @return <code>true</code> if the graph contained the specified vertex;
     * <code>false</code> otherwise.
     */
     public boolean removeVertex(V v) 
     {
    	if (!neighbors.containsKey(v)) 
    	{
    		return false;
	    }
	    neighbors.remove(v);
	    	 ArrayList<E> edgesToRemove = new ArrayList<E>();
	    	 for (E e : edges.keySet()) {
	    		 EdgeContainer ec = edges.get(e);
	    		 if (ec.start.equals(v) || ec.end.equals(v)) {
	    			 edgesToRemove.add(e);
	    		 }
	    	 }
	    	 for (E e : edgesToRemove) {
	    		 edges.remove(e);
	    	 }
	    	 return true;
	     }
	     
	     /**
	      * Returns a set of the vertices contained in this graph. The set is backed
	      * by the graph, so changes to the graph are reflected in the set. If the
	      * graph is modified while an iteration over the set is in progress, the
	      * results of the iteration are undefined.
	      *
	      *
	      * @return a set view of the vertices contained in this graph.
	      */
	     public Set<V> vertexSet() {
	    	 return neighbors.keySet();
	     }
	     
	     /**
	      * Returns the source vertex of an edge. For an undirected graph, source and
	      * target are distinguishable designations (but without any mathematical
	      * meaning).
	      *
	      * @param e edge of interest
	      *
	      * @return source vertex
	      * 
	      * @throws IllegalArgumentException if edge is not found in the graph.
	      */
	     public V getEdgeSource(E e) {
	    	 if (!edges.containsKey(e)) {
	    		 throw new IllegalArgumentException();
	    	 }
	    	 return edges.get(e).start;
	     }
	     
	    /**
	    * Returns the target vertex of an edge. For an undirected graph, source and
	    * target are distinguishable designations (but without any mathematical
	    * meaning).
	    *
	    * @param e edge of interest
	    *
	    * @return target vertex
	    * 
	    * @throws IllegalArgumentException if edge is not found in the graph.
	    */
	    public V getEdgeTarget(E e) 
	    {
	    	 if (!edges.containsKey(e)) 
	    	 {
	    		 throw new IllegalArgumentException();
	    	 }
	    	 return edges.get(e).start;
	    }
}