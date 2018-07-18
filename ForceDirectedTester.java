import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ForceDirectedTester
{
	public BigInteger binomial(final int N, final int K) {
	    BigInteger ret = BigInteger.ONE;
	    for (int k = 0; k < K; k++) {
	        ret = ret.multiply(BigInteger.valueOf(N-k))
	                 .divide(BigInteger.valueOf(k+1));
	    }
	    return ret;
	}

	public double distance(Vertex v, Vertex w)
	{
		return Math.sqrt((v.getX() - w.getX()) * (v.getX() - w.getX()) + (v.getY() - w.getY()) * (v.getY() - w.getY()) );
	}
	
	public double distanceList(List<Vertex> list)
	{
		double sumOfDistances = 0;
		for(int i = 0; i < list.size(); i = i+1)
		{
			for(int j = 0; j < list.size(); j = j+1)
			{
				if(i != j)
				{
					double curDist = distance(list.get(i), list.get(j));
					sumOfDistances = sumOfDistances + curDist;
				}
			}
			
		}
		return sumOfDistances/2;
	}
	
	public double distanceList2(List<Vertex> list1, List<Vertex> list2)
	{
		double sumOfDistances = 0;
		for(int i = 0; i < list1.size(); i = i+1)
		{
			for(int j = 0; j < list2.size(); j = j+1)
			{
				double curDist = distance(list1.get(i), list2.get(j));
				sumOfDistances = sumOfDistances + curDist;
			}
		}
		return sumOfDistances;
	}
	
	
	@Test
	public void test()
	{
		DrawPanel drawPanel = new DrawPanel();
		try {
			drawPanel.loadFile("twoCliques.txt");
		} catch (FileNotFoundException e) {
			assertTrue("File was not found.",false);
		}
		List<Vertex> clique1 = new ArrayList<Vertex>();
		List<Vertex> clique2 = new ArrayList<Vertex>();
		Vertex v0 = drawPanel.graph.vertexSet.get(0);
		Vertex v1 = drawPanel.graph.vertexSet.get(1);
		Vertex v2 = drawPanel.graph.vertexSet.get(2);
		Vertex v3 = drawPanel.graph.vertexSet.get(3);
		Vertex v4 = drawPanel.graph.vertexSet.get(4);
		Vertex v5 = drawPanel.graph.vertexSet.get(5);
		Vertex v6 = drawPanel.graph.vertexSet.get(6);
		clique1.add(v0);
		clique1.add(v1);
		clique1.add(v2);
		
		clique2.add(v3);
		clique2.add(v4);
		clique2.add(v5);
		clique2.add(v6);
		
		
		/*It is disireable that the average distance between vertices within the same clique is smaller 
		 * than the average distance between two vertices from different cliques.
		 */
		
		// sum of distances in clique1:
		double sum1 = distanceList(clique1);
		int pairs1 = 3;
		double sum2 = distanceList(clique2);
		int pairs2 = 6;
		
		double average1 = sum1/pairs1;
		double average2 = sum2/pairs2;
		
		int interCliquePairs = 12;
		double interCliqueSum = distanceList2(clique1, clique2);
		double interCliqueAverage = interCliqueSum/interCliquePairs;
		
		boolean success = false;
		if(average1 < interCliqueAverage && average2 < interCliqueAverage)
		{
			success = true;
		}
		
		assertTrue("ForceDirected failed.",success);
	}
	
	
	
}