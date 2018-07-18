import java.awt.Color;
import java.awt.Graphics;

	/**
	 * <p>
	 * Class for constructing the vertices of our directed graph. <br>
	 * Every vertex consists of three integers, the first is the radius of the vertex while drawn (10 pt as standard).<br>
	 * The second integer is the x-coordinate of the vertex and the third one is the y-coordinate of the vertex. <br>
	 * </p>
	 * 
	 * @author C. Bruckmann, R. Wagner
	 *
	 */
class Vertex 
{
	
	/** 
	 * <p>
	 * Variable for later vertex coloring.
	 * </p>
	 */
	String color;
	
	/**
	 * Integer for the properties of a vertex.
	 */
	private int x, y, r; // x- and y coordinate, radius, index of the vertex
	
	/**
	 * <p>
	 * The index of the vertex, which is later used as their vertexnumber-label
	 * </p> 
	 */
	private int vertexindex = -1;
	
	/**
	 * <p>
	 *  The distance of the vertex, which is later used in the Dijkstra algorithm.
	 * </p>
	 */
	private int vertexDistance = (int) Double.POSITIVE_INFINITY;
	
	/**
	 * <p>
	 * The color of the vertex.
	 * </p>
	 */
	private Color col = Color.BLACK;
	
	/**
	 * <p>
	 *  The standard constructor for the vertices of the graph.
	 * </p>
	 * 
	 * @param r Defines the radius of the vertex in pt if it is drawn.
	 * @param x Defines the x-coordinate of the vertex on the drawPanel.
	 * @param y Defines the y-coordinate of the vertex on the drawPanel.
	 */
	public Vertex(int r, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	/**
	 * <p>
	 * Getter method, which returns the x-coordinate of the vertex.
	 * </p>
	 * 
	 * @return The actual x-coordinate of the Vertex.
	 */
	public int getX() 
	{
		return x;
	}
	
	/**
	 * <p>
	 * Getter method, which returns the y-coordinate of the vertex.
	 * </p>
	 * 
	 * @return The actual y-coordinate of the Vertex.
	 */
	public int getY() 
	{
		return y; 
	}
	
	/**
	 * <p>
	 * Getter method, which returns the radius of the vertex.
	 * </p>
	 * 
	 * @return The actual radius of the Vertex.
	 */
	public int getRadius() 
	{
		return r; 
	}
	
	/**
	 * <p>
	 * Setter method, which is used to change the index of a vertex.
	 * </p>
	 * 
	 * @param ind The new index for the vertex.
	 */
	public void setIndex(int ind)
	{
		vertexindex = ind;
	}
	
	/**
	 * <p>
	 * Setter method, which is used to change the x-coordinate of a vertex.
	 * </p>
	 *  
	 * @param x The new x-coordinate for the vertex.
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * <p>
	 * Setter method, which is used to change the y-coordinate of a vertex.
	 * </p>
	 *  
	 * @param y The new y-coordinate for the vertex.
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * <p>
	 * Setter method, which is used to change the radius of a vertex.
	 * </p>
	 *  
	 * @param r The new radius for the vertex.
	 */
	public void setRadius(int r)
	{
		this.r = r;
	}
	
	/**
	 * <p>
	 * Getter method, which returns the actual distance of the vertex.
	 * </p>
	 *  
	 * @return
	 */
	public int getIndex()
	{
		return vertexindex;
	}
	
	/**
	 * <p>
	 * Getter method, which returns the actual color of the vertex.
	 * </p>
	 *  
	 * @return The color of the vertex.
	 */
	public Color getColor()
	{
		return col;
	}
	
	/**
	 * <p>
	 * Getter method, which returns the actual distance of the vertex, which is needed for the Dijkstra algorithm.
	 * </p>
	 * 
	 * @return The distance of 
	 */
	public int getDistance()
	{
		return vertexDistance;
	}
	
	/**
	 * <p>
	 * Setter method to change the actual vertex color.
	 * </p>
	 * 
	 * @param c The new color for the vertex.
	 */
	public void setColor(Color c)
	{
		this.col = c;
	}
	
	/**
	 * <p>
	 * Setter method to change the actual vertex distance.
	 * </p>
	 * 
	 * @param w The distance, which will be set as the new vertex distance.
	 */
	public void setDistance(int w)
	{
		vertexDistance = w;
	}
	
	/**
	 * <p>
	 * This function fills the drawn vertices with black (hexacode: #000000, decimal code: rgb(0,0,0)) color.
	 * </p>
	 * 
	 * @param g The vertex that shall be filled.
	 */
	public void draw(Graphics g) 
	{
		g.fillOval(x-r, y-r, r*2, r*2);
	}

}