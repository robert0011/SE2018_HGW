import java.awt.Graphics;
import java.awt.Color;

public class Line 
{
	private Circle c1, c2;
	public Line(Circle c1, Circle c2) 
	{
		this.c1 = c1;
		this.c2 = c2;
	}
	
	public void draw(Graphics g)
	{
		if(c1.getX() == 0 & c1.getY() == 0 & c2.getX() == 0 & c2.getY() == 0)
		{
			
		}
		else
		{
			g.drawLine(c1.getX(), c1.getY(), c2.getX(), c2.getY());
		}
	}
	
	public Circle getC1()
	{
		return c1;
	}
	
	public Circle getC2()
	{
		return c2;
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
		if(c1.getX() <= c2.getX())
		{	
			x1 = c1.getX();
			y1 = c1.getY();
			x2 = c2.getX()-(c2.getRadius()-2);
			y2 = c2.getY();
		}
		else
		{
			x1 = c1.getX();
			y1 = c1.getY();
			x2 = c2.getX()+(c2.getRadius());
			y2 = c2.getY();
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
