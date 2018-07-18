import java.awt.Color;
import java.awt.Graphics;

/**
 * Class for constructing edges.
 * @author Bruckmann C., Wagner R.
 *
 */
class Edge
{
	Vertex start, end;
	private int weight;
	private Color col = Color.BLACK;
	// normal constructor for unweighted edges
	public Edge(Vertex start, Vertex end) 
	{
		this.start = start;
		this.end = end;
		weight = 1;
	
	}
	// overload constructor for weighted edges
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
	
	public Vertex getStart()
	{
		return start;
	}
	
	public void setStart(Vertex v)
	{
		start = v;
	}
	
	public Vertex getEnd()
	{
		return end;
	}
	
	public void setEnd(Vertex v)
	{
		end = v;
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public void setColor(Color c)
	{
		this.col = c;
	}
	
	public Color getColor()
	{
		return col;
	}
	
	/**
	 * Draw an arrow line between two points.
	 * @param g the graphics component.
	 * @param d  the width of the arrow.
	 * @param h  the height of the arrow.
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