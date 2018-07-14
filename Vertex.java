import java.awt.Color;
import java.awt.Graphics;

/**
	 * A class for constructing vertices.
	 * @author Bruckmann C., Wagner R.
	 *
	 */
class Vertex 
{
	
	String color; // for later vertex coloring
	private int x, y, r; // x- and y coordinate, radius, index of the vertex
	private int vertexindex = -1;
	private Color col = Color.BLACK;
	
	
	// constructor for uncolored vertices
	public Vertex(int r, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public int getX() 
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY() 
	{
		return y; 
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getRadius() 
	{
		return r; 
	}
	
	public void setIndex(int ind)
	{
		vertexindex = ind;
	}
	
	public int getIndex()
	{
		return vertexindex;
	}
	
	public Color getColor()
	{
		return col;
	}
	
	public void setColor(Color c)
	{
		this.col = c;
	}
	
	public void draw(Graphics g) 
	{
		//g.setColor(Color.BLACK); // color of the added vertices
		g.fillOval(x-r, y-r, r*2, r*2);
	}

}