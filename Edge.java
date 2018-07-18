import java.awt.Color;
import java.awt.Graphics;

/**
 * <p>
 * Class for constructing the edges of our directed graph. <br>
 * Every edge consists of two vertices, the first vertex is the start vertex and determines from which vertex the edge starts and the second vertex end<br>
 * determines at which vertex this edge ends. <br>
 * Each edge can either be unweighted (standard constructor: (vertex start, vertex end)) or weighted (overloaded constructor: (vertex start, vertex end, int weight)).<br>
 * </p>
 * 
 * @author C. Bruckmann, R. Wagner
 *
 */
class Edge
{
	/**
	 * <p>
	 * Vertex used to determine the beginning/ending of an edge.
	 * </p>
	 */
	Vertex start, end;
	
	/**
	 * <p>
	 * An integer used for weighted edges to define the weight of a particular edge. <br>
	 * </p>
	 */
	private int weight;
	
	/**
	 * <p>
	 * The color of the drawn edge.<br>
	 * The default color is black (hexacode: #000000, decimal code: rgb(0,0,0))
	 * </p>
	 */
	private Color col = Color.BLACK;
	
	// standard constructor for unweighted edges
	/**
	 * <p>
	 * The standard constructor without any weight. <br>
	 * The constructor draws an edge, which starts at the given start vertex and ends at the given end vertex.
	 * </p>
	 * 
	 * @param start The vertex at which the edge begins.
	 * @param end The vertex where the edge ends.
	 */
	public Edge(Vertex start, Vertex end) 
	{
		this.start = start;
		this.end = end;
		weight = 1;
	
	}
	// overload constructor for weighted edges
	/**
	 * <p>
	 * The overloaded constructor used for the construction of weighted edges.<br>
	 * This constructor is used by the addition of an integer weight as third parameter.<br>
	 * The constructor draws an edge with weight weight, which starts at the given start vertex and ends at the given end vertex.
	 * </p>
	 * 
	 * @param start The vertex at which the edge begins.
	 * @param end The vertex where the edge ends.
	 * @param weight The weight of the constructed edge.
	 */
	public Edge(Vertex start, Vertex end, int weight) 
	{
		this.start = start;
		this.end = end;
		
		if(weight < 0)
		{
			this.weight = 0;
		}
		else
		{
			this.weight = weight;
		}
	}
	
	/**
	 * <p>
	 * Fuction for drawing the graphical object g.
	 * </p>
	 * 
	 * @param g Graphical component, which shall be drawn.
	 */
	public void draw(Graphics g)
	{
		if(start.getX() == 0 & start.getY() == 0 & end.getX() == 0 & end.getY() == 0)
		{
			
		}
		else
		{
			g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
		}
	}
	
	/**
	 * <p>
	 * Getter method, which returns the start of the particular edge.
	 * </p>
	 * @return The vertex at which the edge starts.
	 */
	public Vertex getStart()
	{
		return start;
	}
	
	/**
	 * <p>
	 * Setter method, which is used to set/change the start vertex of an edge.
	 * </p>
	 * @param v The desired new start vertex of the edge.
	 */
	public void setStart(Vertex v)
	{
		start = v;
	}
	
	/**
	 * <p>
	 * Getter method, which returns the actual end vertex of the an edge.
	 * </p>
	 * @return The vertex at which the edge ends.
	 */
	public Vertex getEnd()
	{
		return end;
	}
	
	/**
	 * <p>
	 * Setter method, which is used to set/change the end vertex of an edge.
	 * </p>
	 * @param v The desired new end vertex of the edge.
	 */
	public void setEnd(Vertex v)
	{
		end = v;
	}
	
	/**
	 * <p>
	 * Getter method, which returns the weight of a weighted edge.
	 * </p>
	 * @return The weight of the given edge.
	 */
	public int getWeight()
	{
		return weight;
	}
	
	/**
	 * <p>
	 * Setter method, which is used to set the color of an edge
	 * </p>
	 * @param c The desired color for the edge.
	 */
	public void setColor(Color c)
	{
		this.col = c;
	}
	
	/**
	 * <p>
	 * Getter method, which returns the color of an edge.
	 * </p>
	 * @return The color of the given edge.
	 */
	public Color getColor()
	{
		return col;
	}
	
	/**
	 * <p>
	 * Draw an arrow line between two vertices.
	 * </p>
	 * @param g The graphics component.
	 * @param d  The width of the arrow.
	 * @param h  The height of the arrow.
	 */
	
	// https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
	public void drawArrowLine(Graphics g, int d, int h)
	{
		int x1,x2,y1,y2;
		if(start.getX() <= end.getX())
		{	
			x1 = start.getX();
			y1 = start.getY();
			x2 = end.getX()-(end.getRadius()-2);
			y2 = end.getY();
		}
		else
		{
			x1 = start.getX();
			y1 = start.getY();
			x2 = end.getX()+(end.getRadius());
			y2 = end.getY();
		}
		
		
		int dx = x2 - x1, dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = D - d, xn = xm, ym = h, yn = -h, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};

	    g.drawLine(x1, y1, x2, y2);
	    g.fillPolygon(xpoints, ypoints, 3);
	    
	}
	
}