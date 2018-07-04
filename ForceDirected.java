import java.lang.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/* 
 * Algorithm to beautfy a given graph by using Eades method
 * based on spring systems and electrical forces
 */
class ForceDirected
{
	
	//constants 
	int CONSTANTONE = 2;
	int CONSTANTTWO = 1;
	int CONSTANTTHREE = 1;
	double CONSTANTFOUR = 0.1;
	int REPETITIONS = 100;
	
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
	int newXCoordForW;
	int newYCoordForW;
	Circle newV;
	Circle newW;
	Circle v;
	Circle w;
	
	public void forceDirected(ArrayList<Circle> circles, Hashtable<Integer, List<Line>> lines, ArrayList<Integer> edgelist)
	{
		for(int i = 1; i < REPETITIONS; i++)
		{
			// repeat for every non-isolated vertex
			for(int j = 0; j < circles.size(); j++)
			{
				v = circles.get(j);
				x1 = v.getX();
				y1 = v.getY();
				for(int k = 0; k < circles.size(); k++)
				{
					w = circles.get(k);
					x2 = w.getX();
					y2 = w.getY();
					
					term = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1); 
					distance = Math.sqrt(term);
					//if hash contains edge vw or wv then they are adjacent
					Line edgeCheckOne = new Line(v,w,1);
					Line edgeCheckTwo = new Line(w,v,1);
					
					if(lines.containsValue(edgeCheckOne) || lines.containsValue(edgeCheckTwo))
					{
						//calculate force between adjacent vertices
						springForce = CONSTANTONE * Math.log(distance/CONSTANTTWO);
						if(v.getX() > w.getX())
						{
							newXCoordForV = (int) (v.getX() - CONSTANTFOUR*(springForce));
							newXCoordForW = (int) (w.getX() + CONSTANTFOUR*(springForce));
						}
						else
						{
							newXCoordForV = (int) (v.getX() + CONSTANTFOUR*(springForce));
							newXCoordForW = (int) (w.getX() - CONSTANTFOUR*(springForce));
							
						}
						if(v.getY() > w.getY())
						{
							newYCoordForV = (int) (v.getY() + CONSTANTFOUR*(springForce));
							newYCoordForW = (int) (w.getY() - CONSTANTFOUR*(springForce));
						}
						else
						{
							newYCoordForV = (int) (v.getY() - CONSTANTFOUR*(springForce));
							newYCoordForW = (int) (w.getY() + CONSTANTFOUR*(springForce));
						}
						// check for x coords 
						if(newXCoordForV > 150 && newXCoordForV < 1900)
						{
							//check for y coords
							if(newYCoordForV > 50 && newYCoordForV < 950)
							{
								newV = new Circle(v.getRadius(),newXCoordForV, newYCoordForV, v.getIndex());
							}
							else
							{
								newV = new Circle(v.getRadius(),newXCoordForV, v.getY(), v.getIndex());
							}
						}
						else
						{
							if(newYCoordForV > 50 && newYCoordForV < 950)
							{
								newV = new Circle(v.getRadius(),v.getX(), newYCoordForV, v.getIndex());
							}
							else
							{
								newV = new Circle(v.getRadius(),v.getX(), v.getY(), v.getIndex());
							}
						}
						
						
						/*if(newXCoordForW > 150 && newXCoordForW < 1900)
						{
							if(newYCoordForW > 50 && newYCoordForW < 950)
							{
								newW = new Circle(w.getRadius(),newXCoordForW, newYCoordForW, w.getIndex());
							}
							else
							{
								newW = new Circle(w.getRadius(),newXCoordForW, w.getY(), w.getIndex());
							}
						}
						else
						{
							if(newYCoordForW > 50 && newYCoordForW < 950)
							{
								newW = new Circle(w.getRadius(),w.getX(), newYCoordForW, w.getIndex());
							}
							else
							{
								newW = new Circle(w.getRadius(),w.getX(), w.getY(), w.getIndex());
							}
						}
						*/
						
						//actually moves v and w to new force induced position
						circles.set(v.getIndex(), newV);
						//circles.set(w.getIndex(), newW);
					}
					//if hash do not contain the edge then they are non-adjacent
					else
					{
						//calculate repulsion of nonadjacent vertices
						if(distance <= 0)
						{
							repulsionOfNonadjacentVertices = 10;
						}
						else
						{
							repulsionOfNonadjacentVertices = CONSTANTTHREE/(distance*distance);
						}
						
						if(v.getX() >= w.getX())
						{
							newXCoordForV = (int) (v.getX() + (repulsionOfNonadjacentVertices));
							//newXCoordForW = (int) (w.getX() - (repulsionOfNonadjacentVertices));
						}
						else
						{
							newXCoordForV = (int) (v.getX() - (repulsionOfNonadjacentVertices));
							//newXCoordForW = (int) (w.getX() + (repulsionOfNonadjacentVertices));
							
						}
						if(v.getY() >= w.getY())
						{
							newYCoordForV = (int) (v.getY() + (repulsionOfNonadjacentVertices));
							//newYCoordForW = (int) (w.getY() - (repulsionOfNonadjacentVertices));
						}
						else
						{
							newYCoordForV = (int) (v.getY() - (repulsionOfNonadjacentVertices));
							//newYCoordForW = (int) (w.getY() + (repulsionOfNonadjacentVertices));
						}
						
						if(newXCoordForV >= 150 && newXCoordForV < 1900)
						{
							if(newYCoordForV > 150 && newYCoordForV < 1000)
							{
								newV = new Circle(v.getRadius(),newXCoordForV, newYCoordForV, v.getIndex());
							}
							else
							{
								newV = new Circle(v.getRadius(),newXCoordForV, v.getY(), v.getIndex());
							}
						}
						else
						{
							if(newYCoordForV > 150 && newYCoordForV < 1000)
							{
								newV = new Circle(v.getRadius(),v.getX(), newYCoordForV, v.getIndex());
							}
							else
							{
								newV = new Circle(v.getRadius(),v.getX(), v.getY(), v.getIndex());
							}
						}
						
						/*if(newXCoordForW >= 150 && newXCoordForW < 1900)
						{
							if(newYCoordForW > 150 && newYCoordForW < 1000)
							{
								newW = new Circle(w.getRadius(),newXCoordForW, newYCoordForW, w.getIndex());
							}
							else
							{
								newW = new Circle(w.getRadius(),newXCoordForW, w.getY(), w.getIndex());
							}
						}
						else
						{
							if(newYCoordForW > 150 && newYCoordForW < 1000)
							{
								newW = new Circle(w.getRadius(),w.getX(), newYCoordForW, w.getIndex());
							}
							else
							{
								newW = new Circle(w.getRadius(),w.getX(), w.getY(), w.getIndex());
							}
						}*/
						
						
						//actually moves v and w to new force induced position

						circles.set(v.getIndex(), newV);
						//circles.set(w.getIndex(), newW);
	
					}
				}
			}
		}
		lines.clear();
		for(int i= 0; i < edgelist.size(); i++)
		{	
			int startVertex = edgelist.get(i).intValue();
			i++;
			int endVertex = edgelist.get(i).intValue();
			i++;
			int edgeWeight = edgelist.get(i).intValue();
			

			if(lines.containsKey(startVertex))
			{
				List<Line> curList = lines.get(startVertex);
				// die circles sind nicht sortiert! ordne circleindex zu!
				Circle c1 = circles.get(startVertex);
				Circle c2 = circles.get(endVertex);
				Line lineToAdd = new Line(c1,c2,edgeWeight);
				if(curList == null) 
				{
					List<Line> curList1 = new ArrayList<Line>();
					curList1.add(lineToAdd);
					lines.put(startVertex, curList1);
				}
				else 
				{
					curList.add(lineToAdd);
					lines.put(startVertex, curList);
				}
			}
			
			else
			{
				List<Line> curList = new ArrayList<Line>();

				Circle c1 = circles.get(startVertex);
				Circle c2 = circles.get(endVertex);
				Line lineToAdd = new Line(c1,c2,edgeWeight);
				curList.add(lineToAdd);
				lines.put(startVertex, curList);		
			}			
			
		} 
	}
	
}