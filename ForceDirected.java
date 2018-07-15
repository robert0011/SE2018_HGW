import java.awt.Toolkit;
import java.lang.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/* 
 * Algorithm to beautfy a given graph by using Eades method
 * based on spring systems and electrical forces
 */
/**
 * 
 * @author C. Bruckmann, R. Wagner
 *
 */
class ForceDirected
{
	
	//constants 
	int CONSTANTONE = 2;
	int CONSTANTTWO = 1;
	int CONSTANTTHREE = 1;
	double CONSTANTFOUR = 0.1;
	int REPETITIONS = 150;
	
	double distance;
	double term;
	int x1;
	int y1;
	int x2;
	int y2;
	double springForce;
	double repulsionOfNonadjacentVertices;
	int newXCoordForV;
	int newYCoordForV;
	Vertex newV;
	Vertex v;
	Vertex w;
	List<Edge> edgelist;
	
	
	/**
 	* 
 	* @param vertices vertexset from the drawn/loaded graph
 	* @param edges edgeset from the drawn/loaded graph
 	* @param edgelist
 	*/
	public ForceDirected(Hashtable<Integer, Vertex> vertices, ArrayList<Edge> edgelist)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		int frameWidth = ((int) tk.getScreenSize().getWidth());
		int frameHeight = ((int) tk.getScreenSize().getHeight());
		
		for(int i = 0; i < REPETITIONS; i++)
		{
			// repeat for every non-isolated vertex
			for(int j = 0; j < vertices.size()-1; j++)
			{
				v = vertices.get(j);
				x1 = v.getX();
				y1 = v.getY();
				for(int k = 0; k < vertices.size()-1; k++)
				{
					// do nothing for identical vertices
					if(k != j)
					{
						w = vertices.get(k);
						x2 = w.getX();
						y2 = w.getY();
						
						
						// calculation of the euclidean distance 
						term = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1); 
						distance = Math.sqrt(term);
										
						for(Edge e : edgelist)
						{
							
							// if edgelist contains edge vw or wv then v and w are adjacent
						
							if((e.getStart().getIndex() == j & e.getEnd().getIndex() == k) || (e.getStart().getIndex() == k & e.getEnd().getIndex() == j))
							{
								
								//calculate force between adjacent vertices
								springForce = CONSTANTONE * Math.log(distance/CONSTANTTWO);
								
								/* if the x-coordinate of v is on the right side of w 
								 * (v.getX() is bigger than w.getX()) so we have to 
								 * reduce the x-coordinate of v to reduce the distance between v and w
								 */
								if(v.getX() > w.getX())
								{
									newXCoordForV = (int) (v.getX() - (CONSTANTFOUR*springForce));
								}
								else
								{
									newXCoordForV = (int) (v.getX() + (CONSTANTFOUR*springForce));
								}
								
								/*
								 * 
								 */
								if(v.getY() > w.getY())
								{
									newYCoordForV = (int) (v.getY() + (CONSTANTFOUR*springForce));
								}
								else
								{
									newYCoordForV = (int) (v.getY() - (CONSTANTFOUR*springForce));
								}
								// check for x coords 
								if(newXCoordForV > 100 && newXCoordForV < (frameWidth-150))
								{
									//check for y coords
									if(newYCoordForV > 100 && newYCoordForV < (frameHeight-150))
									{
										newV = new Vertex(v.getRadius(),newXCoordForV, newYCoordForV);
									}
									else
									{
										newV = new Vertex(v.getRadius(),newXCoordForV, v.getY());
									}
								}
								else
								{
									if(newYCoordForV > 100 && newYCoordForV < (frameHeight-150))
									{
										newV = new Vertex(v.getRadius(),v.getX(), newYCoordForV);
									}
									else
									{
										newV = new Vertex(v.getRadius(),v.getX(), v.getY());
									}
								}	
							}
							

							/* if edgelist do not contain the edge then are the two vertices non-adjacent and repel each other
							 * 
							 */
							else
							{
								//calculate repulsion of non-adjacent vertices
								if(distance == 0)
								{
									// repel overlapping 
									repulsionOfNonadjacentVertices = 400;
								}
								else
								{
									repulsionOfNonadjacentVertices = CONSTANTTHREE/(distance*distance);
								}
								
								/* if the x-coordinate of v is on the right side of w 
								 * (v.getX() is bigger than w.getX()) so we have to 
								 * reduce the x-coordinate of v to reduce the distance between v and w
								 */
								if(v.getX() >= w.getX())
								{
									newXCoordForV = (int) (v.getX() + (CONSTANTFOUR*repulsionOfNonadjacentVertices));
								}
								else
								{
									newXCoordForV = (int) (v.getX() - (CONSTANTFOUR*repulsionOfNonadjacentVertices));			
								}
								if(v.getY() >= w.getY())
								{
									newYCoordForV = (int) (v.getY() + (CONSTANTFOUR*repulsionOfNonadjacentVertices));
								}
								else
								{
									newYCoordForV = (int) (v.getY() - (CONSTANTFOUR*repulsionOfNonadjacentVertices));
								}
								
								if(newXCoordForV > 100 && newXCoordForV < (frameWidth-150))
								{
									if(newYCoordForV > 100 && newYCoordForV < (frameHeight-150))
									{
										newV = new Vertex(v.getRadius(),newXCoordForV, newYCoordForV);
									}
									else
									{
										newV = new Vertex(v.getRadius(),newXCoordForV, v.getY());
									}
								}
								else
								{
									if(newYCoordForV > 100 && newYCoordForV < (frameHeight-150))
									{
										newV = new Vertex(v.getRadius(),v.getX(), newYCoordForV);
									}
									else
									{
										newV = new Vertex(v.getRadius(),v.getX(), v.getY());
									}
								}
							}		
							vertices.get(j).setX(newV.getX()); 
							vertices.get(j).setY(newV.getY());	
						}
					}
				}
			}
		}	
		//separate overlapping vertices after the final step of th
		for(int j = 0; j < vertices.size()-1; j++)
		{
			v = vertices.get(j);
			x1 = v.getX();
			y1 = v.getY();
			for(int k = 0; k < vertices.size()-1; k++)
			{
				// do nothing for identical vertices
				if(k != j)
				{
					w = vertices.get(k);
					x2 = w.getX();
					y2 = w.getY();
					
					if(v.getX() == w.getX() && v.getY() == w.getY())
					{
						vertices.get(j).setX(vertices.get(j).getX()+25); 
						vertices.get(j).setY(vertices.get(j).getY()+25);	
					}
				}
			}
		}	
	}
	
}