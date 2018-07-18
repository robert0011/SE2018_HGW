import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
/* 
 * Algorithm to beautify the layout a given graph by using Eades method
 * based on spring systems and electrical forces and the repulsion calculation
 * of Fruchterman and Reingold
 */

/**
 *  <p>
 * An algorithm that calculates an more aesthetically pleasing layout for the loaded graph. <br>
 * This approach uses the springforcebased force directed layout algorithm by Eades. <br>
 * </p>
 * <p>
 * This algorithm relocates the vertices of the graph <br>
 * according to the physical idea of springs and electric charges.<br>
 * Nonadjacent vertices repel each other, like they have the same electric charge. <br>
 * The edge between two adjacent vertices operates like a spring, which attract the adjacent vertices. <br>
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
	 * The amount of iterations of the whole forceinduced relocation.
	 * </p>
	 */
	int REPETITIONS = 50;
	
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
	 * Variable for the value of the repulsion of two vertices. <br>
	 * The calculated force is based on the inverse square law. <br>
	 * It is calculated with following formula: <br>
	 *  repulsionOfTwoVertices = 1 / distance<sup>2</sup> <br>
	 * </p>
	 */
	double repulsionOfTwoVertices;
	
	/**
	 * <p>
	 * Variable used for the calculation of the repulsion force.
	 * </p>
	 */
	int newXCoordForV;
	
	/**
	 * <p>
	 * Variable for the calculation of the x-coordinate of the vertex newV.
	 * </p> 
	 */
	int newYCoordForV;
	
	/**
	 * <p>
	 *  Variable for the calculation of the y-coordinate of the vertex newV.
	 * </p>
	 */
	Vertex newV;
	
	/**
	 * <p>
	 * A vertex containing the new calculated coordinates for the later replacement of the "old" vertex v.
	 * </p>
	 */
	Vertex v;
	
	/**
	 * <p>
	 * Another vertex used for calculations.
	 * </p>
	 */
	
	int newXCoordForW;
	
	/**
	 * <p>
	 * Variable for the calculation of the x-coordinate of the vertex newW.
	 * </p> 
	 */
	int newYCoordForW;
	
	/**
	 * <p>
	 * Variable for the calculation of the y-coordinate of the vertex newW.
	 * </p>
	 */
	Vertex newW;
	
	/**
	 * <p>
	 * A vertex containing the new calculated coordinates for the later replacement of the "old" vertex w.
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
	 * <p>
	 * This constructor is needed for the force directed algorithm.
	 * </p>
	 * @param vertices A hashtable of the loaded graph consisting of Integer as keys and vertices as values.
	 * @param edgelist An arraylist consisting of the edges of the loaded graph.
	 */
	public ForceDirected(Hashtable<Integer, Vertex> vertices, ArrayList<Edge> edgelist)
	{
		// toolkit for getting the screensize for the calculation of the boundaries
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
						newYCoordForV = v.getY();
										
						for(Edge e : edgelist)
						{
							
							if(!adjacent)
							{
								// if edgelist contains edge vw or wv then v and w are adjacent
								if((e.getStart().getIndex() == j & e.getEnd().getIndex() == k) || (e.getStart().getIndex() == k & e.getEnd().getIndex() == j))
								{
									
									//calculate force between adjacent vertices
									springForce = CONSTANTONE * Math.log(distance/CONSTANTTWO);
									adjacent = true;
								}
							}
						}
						
						/* if two vertices are adjacent the spring between these vertices attract each other
						 * if two vertices are not adjacent then there is no attraction between them
						 */
						if(adjacent)
						{	
							/* if  v.getX() is bigger than w.getX() then we have to 
							 * reduce the x-coordinate of v to reduce the distance between v and w
							 * for w we have to increase the x-coordinate to reduce the distance between v and w
							 */
							if(v.getX() > w.getX())
							{
								newXCoordForV = (int) (v.getX() - (CONSTANTTHREE*springForce));
								newXCoordForW = (int) (w.getX() + (CONSTANTTHREE*springForce));
								
							}
							else
							{
								newXCoordForV = (int) (v.getX() + (CONSTANTTHREE*springForce));
								newXCoordForW = (int) (w.getX() - (CONSTANTTHREE*springForce));
							}
							
							/* if  v.getY() is bigger than w.getY() then we have to 
							 * reduce the y-coordinate of v to reduce the distance between v and w
							 * for w we have to increase the y-coordinate to reduce the distance between v and w
							 */
							if(v.getY() > w.getY())
							{
								newYCoordForV = (int) (v.getY() - (CONSTANTTHREE*springForce));
								newYCoordForW = (int) (w.getY() + (CONSTANTTHREE*springForce));
								
							}
							else
							{
								newYCoordForV = (int) (v.getY() + (CONSTANTTHREE*springForce));
								newYCoordForW = (int) (w.getY() - (CONSTANTTHREE*springForce));
							}
						}
							
						/* the vertices repel each other (this resembles the idea of electrical forces, where
						 * every vertex has the same charge)
						 */
						else
						{
							repulsionOfTwoVertices = 1/(distance*distance);
							
							/* if  v.getX() is bigger than w.getX() then we have to 
							 * increase the x-coordinate of v to rise the distance between v and w
							 */
							if(v.getX() >= w.getX())
							{
								newXCoordForV = (int) (newXCoordForV + (CONSTANTTHREE*repulsionOfTwoVertices));
								newXCoordForW = (int) (w.getY() - (CONSTANTTHREE*repulsionOfTwoVertices));
							}
							else
							{
								newXCoordForV = (int) (newXCoordForV - (CONSTANTTHREE*repulsionOfTwoVertices));	
								newXCoordForW = (int) (w.getY() + (CONSTANTTHREE*repulsionOfTwoVertices));
							}
							/* if v.getY() is bigger than w.getY() then we have to 
							 * increase the y-coordinate of v to rise the distance between v and w
							 */
							if(v.getY() >= w.getY())
							{
								newYCoordForV = (int) (newYCoordForV + (CONSTANTTHREE*repulsionOfTwoVertices));
								newYCoordForW = (int) (w.getY() - (CONSTANTTHREE*repulsionOfTwoVertices));
							}
							else
							{
								newYCoordForV = (int) (newYCoordForV - (CONSTANTTHREE*repulsionOfTwoVertices));
								newYCoordForW = (int) (w.getY() + (CONSTANTTHREE*repulsionOfTwoVertices));
							}
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
						
						// no check the coordinates for w
						if(newXCoordForW > 100 && newXCoordForW < (frameWidth-150))
						{
							if(newYCoordForW > 100 && newYCoordForW < (frameHeight-150))
							{
								newW = new Vertex(w.getRadius(),newXCoordForW, newYCoordForW);
							}
							else
							{
								newW = new Vertex(w.getRadius(),newXCoordForW, w.getY());
							}
						}
						else
						{
							if(newYCoordForW > 100 && newYCoordForW < (frameHeight-150))
							{
								newW = new Vertex(w.getRadius(),w.getX(), newYCoordForW);
							}
							else
							{
								newW = new Vertex(w.getRadius(),w.getX(), w.getY());
							}
						}
						
						//actual change the coordinates of v according to the calculations
						v.setX(newV.getX()); 
						v.setY(newV.getY());
						if(adjacent)
						{
							w.setX(newW.getX());
							w.setY(newW.getY());
						}
						
					}
				}
			}
		}
		
		// at this stadium the cliques are supposed to be separated but the vertices within each clique will be shifted to one point
		//separate overlapping vertices after the final step of the force directed algorithm
		Random rand2 = new Random();
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
							int randomX = rand2.nextInt(30)-15;
							v.setX(v.getX() - randomX); 
						}
						else
						{
							int randomX = rand2.nextInt(30)-15;
							v.setX(v.getX() + randomX);
						}
						if(v.getY() >= frameHeight/2)
						{
							int randomY = rand2.nextInt(30)-15;
							v.setY(v.getY() - randomY);
						}
						else
						{
							int randomY = rand2.nextInt(30)-15;
							v.setY(v.getY() + randomY);
						}
					}
				}
			}
		}
		
		for(int z = 0; z <= 10; z = z+1)
		{
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
						adjacent = false;
										
						for(Edge e : edgelist)
						{
							
							if(!adjacent)
							{
								// if edgelist contains edge vw or wv then v and w are adjacent
								if((e.getStart().getIndex() == j & e.getEnd().getIndex() == k) || (e.getStart().getIndex() == k & e.getEnd().getIndex() == j))
								{
									
									//calculate the negative force between adjacent vertices to repel them for a better representation
									springForce = -1 * CONSTANTONE * Math.log(distance/CONSTANTTWO);
									adjacent = true;
								}
							}
						}
						if(adjacent)
						{
							/* if  v.getX() is bigger than w.getX() then we have to 
							 * increase the x-coordinate of v to increase the distance between v and w
							 * for w we have to reduce the x-coordinate to increase the distance between v and w
							 */
							if(v.getX() > w.getX())
							{
								newXCoordForV = (int) (v.getX() - (CONSTANTTHREE*springForce));
								newXCoordForW = (int) (w.getX() + (CONSTANTTHREE*springForce));
								
							}
							else
							{
								newXCoordForV = (int) (v.getX() + (CONSTANTTHREE*springForce));
								newXCoordForW = (int) (w.getX() - (CONSTANTTHREE*springForce));
							}
							
							/* if  v.getY() is bigger than w.getY() then we have to 
							 * increase the y-coordinate of v to increase the distance between v and w
							 * for w we have to reduce the y-coordinate to increase the distance between v and w
							 */
							if(v.getY() > w.getY())
							{
								newYCoordForV = (int) (v.getY() - (CONSTANTTHREE*springForce));
								newYCoordForW = (int) (w.getY() + (CONSTANTTHREE*springForce));
								
							}
							else
							{
								newYCoordForV = (int) (v.getY() + (CONSTANTTHREE*springForce));
								newYCoordForW = (int) (w.getY() - (CONSTANTTHREE*springForce));
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
							
							// no check the coordinates for w
							if(newXCoordForW > 100 && newXCoordForW < (frameWidth-150))
							{
								if(newYCoordForW > 100 && newYCoordForW < (frameHeight-150))
								{
									newW = new Vertex(w.getRadius(),newXCoordForW, newYCoordForW);
								}
								else
								{
									newW = new Vertex(w.getRadius(),newXCoordForW, w.getY());
								}
							}
							else
							{
								if(newYCoordForW > 100 && newYCoordForW < (frameHeight-150))
								{
									newW = new Vertex(w.getRadius(),w.getX(), newYCoordForW);
								}
								else
								{
									newW = new Vertex(w.getRadius(),w.getX(), w.getY());
								}
							}
							
							//actual change the coordinates of v according to the calculations
							v.setX(newV.getX()); 
							v.setY(newV.getY());
							if(adjacent)
							{
								w.setX(newW.getX());
								w.setY(newW.getY());
							}
						}
					}	
				}
			}
		}
		
	}
	
	
}