/**
	 * A class for constructing vertices.
	 * @author Bruckmann C., Wagner R.
	 *
	 */
class Vertex 
{
	int x; // x-coordinate
	int y; // y-coordinate
	String color; // for later vertex coloring
	
	
	// constructor for uncolored vertices
	public Vertex(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	// overload constructor for colored graphs
	public Vertex(int x, int y, String color) 
	{
		this.x = x;
		this.y = y;
		if(color.isEmpty() || (!"WHITE".equals(color)  && !"BLACK".equals(color) && !"RED".equals(color) && !"BLUE".equals(color) && !"GREEN".equals(color) && !"GREY".equals(color)))
		{
			this.color = "BLACK";
		}
		else
		{
			this.color = color;
		}	
	}
}