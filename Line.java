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
}
