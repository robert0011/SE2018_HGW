import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Hashtable;
/* 
 * Algorithm to beautify the layout a given graph by using Eades method
 * based on spring systems and electrical forces and the repulsion calculation
 * of Fruchterman and Reingold
 */

/**
 *  <p>
 * An algorithm that calculates an more aesthetically pleasing layout for the loaded graph. <br>
 * This approach uses a combination of the springforcebased force directed layout algorithms <br>
 * by Eades and Fruchterman &amp; Reingold.
 * </p>
 * @author C. Bruckmann, R. Wagner
 */
class ForceDirected
{
	//constants 
	/**
	 * <p>
	 * Constant from the Eades approach is empirically determined as 2.
	 * </p>
	 */
	int CONSTANTONE = 2;
	/**
	 * <p>
	 * Constant from the Eades approach is empirically determined as 1.
	 * </p>
	 */
	int CONSTANTTWO = 1;
	/**
	 * <p>
	 * Constant from the Eades approach is empirically determined as 0.1.
	 * </p>
	 */
	double CONSTANTTHREE = 0.1;
	/**
	 * <p>
	 * The amount of iterations of the whole algorithmn.<br>
	 * An amount of 100 repetitions is recommended.
	 * </p>
	 */
	int REPETITIONS = 100;
	
	/**
	 * <p>
	 * The distance used in this algorithm is the euclidean distance. 
	 * </p>
	 */
	double distance;
	
	/**
	 * <p>
	 * This term is used in the calculation of the euclidean distance: <br>
	 * term = (x<sub>2</sub> - x<sub>1</sub>)<sup>2</sup> + (y<sub>2</sub> - y<sub>1</sub>)<sup>2</sup>
	 * </p>
	 */
	double term;
	
	/**
	 * <p>
	 * The x-coordinate of vertex v.
	 * </p>
	 */
	int x1;
	
	/**
	 * <p>
	 * The y-coordinate of vertex v.
	 * </p> 
	 */
	int y1;
	
	/**
	 * <p>
	 * The x-coordinate of vertex w.
	 * </p>
	 */
	int x2;
	
	/**
	 * <p>
	 * The y-coordinate of vertex w.
	 * </p> 
	 */
	int y2;
	
	/**
	 * <p>
	 * Variable for the value of affecting force on the vertices.<br>
	 * The formula is based of the Eades algorithm. <br>
	 * It is calculated like this: <br>
	 * springForce = CONSTANTONE * log(distance / CONSTANTTWO)
	 * </p>
	 */
	double springForce;
	
	/**
	 * <p>
	 * Variable for the value of affecting force on the vertices.<br>
	 * The formula is based on the Fruchterman &amp; Reingold algorithm. <br>
	 * It is calculated like this: <br>
	 * repulsionOfNonadjacentVertices = ((frameWidth*frameHeight)/#vertices) / distance
	 * </p>
	 */
	double repulsionOfNonadjacentVertices;
	
	/**
	 * <p>
	 * Variable used for the calculation of the x-coordinate of the vertex newV.
	 * </p>
	 */
	int newXCoordForV;
	
	/**
	 * <p>
	 * Variable for the calculation of the y-coordinate of the vertex newV.
	 * </p> 
	 */
	int newYCoordForV;
	
	/**
	 * <p>
	 * A vertex which conatins the new calculated coordinates for the adjustment of the vertices of the given vertex set.
	 * </p>
	 */
	Vertex newV;
	
	/**
	 * <p>
	 * A vertex used for calculations.
	 * </p>
	 */
	Vertex v;
	
	/**
	 * <p>
	 * Another vertex used for calculations.
	 * </p>
	 */
	Vertex w;
	
	/**
	 * <p>
	 *  A boolean for the adjacency test to determine whether only repulsion or repulsion &amp; attraction affect a vertex.
	 *  </p>
	 */
	boolean adjacent = false;
	
	/**
	 * 
	 * @param vertices A hashtable of the loaded graph consisting of Integer as keys and vertices as values.
	 * @param edgelist An arraylist consisting of the edges of the loaded graph.
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
						adjacent = false;
						newXCoordForV = v.getX();
										
						for(Edge e : edgelist)
						{
							
							// if edgelist contains edge vw or wv then v and w are adjacent
						
							if((e.getStart().getIndex() == j & e.getEnd().getIndex() == k) || (e.getStart().getIndex() == k & e.getEnd().getIndex() == j))
							{
								
								//calculate force between adjacent vertices
								springForce = CONSTANTONE * Math.log(distance/CONSTANTTWO);
								adjacent = true;
							}
						}
						if(adjacent)
						{	
							/* if  v.getX() is bigger than w.getX() then we have to 
							 * reduce the x-coordinate of v to reduce the distance between v and w
							 */
							if(v.getX() > w.getX())
							{
								newXCoordForV = (int) (v.getX() - (CONSTANTTHREE*springForce));
							}
							else
							{
								newXCoordForV = (int) (v.getX() + (CONSTANTTHREE*springForce));
							}
						}
							
						/* if edgelist do not contain the edge then are the two vertices non-adjacent and repel each other
						 * the calculation of the repulsion is based on the Fruchterman Reingold algortihm
						 */
						double areaCoeffizient = Math.sqrt((frameWidth * frameHeight) / vertices.size());
						repulsionOfNonadjacentVertices = areaCoeffizient*areaCoeffizient / distance;
						
						/* if  v.getX() is bigger than w.getX() then we have to 
						 * increase the x-coordinate of v to rise the distance between v and w
						 */
						if(v.getX() >= w.getX())
						{
							newXCoordForV = (int) (newXCoordForV + (CONSTANTTHREE*repulsionOfNonadjacentVertices));
						}
						else
						{
							newXCoordForV = (int) (newXCoordForV - (CONSTANTTHREE*repulsionOfNonadjacentVertices));		
						}
						/* if v.getY() is bigger than w.getY() then we have to 
						 * increase the y-coordinate of v to rise the distance between v and w
						 */
						if(v.getY() >= w.getY())
						{
							newYCoordForV = (int) (newYCoordForV + (CONSTANTTHREE*repulsionOfNonadjacentVertices));
						}
						else
						{
							newYCoordForV = (int) (newYCoordForV - (CONSTANTTHREE*repulsionOfNonadjacentVertices));
						}
						
						// check whether the new coordinates are in the proper draw area (between the given boundaries) or not
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
						
						//actual change the coordinates of v according to the calculations
						v.setX(newV.getX()); 
						v.setY(newV.getY());	
					}
				}
			}
		}
		
		//separate overlapping vertices after the final step of the force directed algoritm
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
					
					term = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1); 
					distance = Math.sqrt(term);
					
					// if two v and w are too close separate them according to their position on the drawPanel
					if(distance <= 25)
					{
						if(v.getX() >= frameWidth/2)
						{
							v.setX(v.getX() - 40); 
						}
						else
						{
							v.setX(v.getX() + 40);
						}
						if(v.getY() >= frameHeight/2)
						{
							v.setY(v.getY() - 40);
						}
						else
						{
							v.setY(v.getY() + 40);
						}
					}
				}
			}
		}	
	}
	
}