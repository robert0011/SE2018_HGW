import java.awt.Color;
import java.awt.Graphics;

public class Circle 
{
	private int x, y, r, circleindex;
	
	public Circle(int r, int x, int y, int ci) 
	{
		this.x = x;
		this.y = y;
		this.r = r;
		this.circleindex = ci;
	}
	
	public int getX() 
	{
		return x;
	}
	
	public int getY() 
	{
		return y; 
	}
	
	public int getRadius() 
	{
		return r; 
	}
	
	public int getIndex()
	{
		return circleindex;
	}
	
	public void draw(Graphics g) 
	{
		//g.setColor(Color.BLACK); // color of the added vertices
		g.fillOval(x-r, y-r, r*2, r*2);
	}
}
