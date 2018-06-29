import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;

import junit.framework.TestCase;

public class DijkstraTester extends TestCase
{
	public void test()
	{
		Circle c0 = new Circle(10, 1, 2, 0);
		Circle c1 = new Circle(10, 2, 3, 1);
		Circle c2 = new Circle(10, 1, 2, 2);
		Circle c3 = new Circle(10, 1, 2, 3);
		Line l1 = new Line(c0,c1,1);
		Line l2 = new Line(c2,c3,5);
		ArrayList<Circle> circles = new ArrayList<Circle>();
		circles.add(c0);
		circles.add(c1);
		circles.add(c2);
		circles.add(c3);
		System.out.println("Size of circles: "+circles.size());
		Hashtable<Integer, List<Line>> lines = new Hashtable<Integer, List<Line>>();
		List<Line> curr = new ArrayList<Line>();
		curr.add(l1);
		lines.put(c0.getIndex(),curr);
		
		curr = new ArrayList<Line>();
		curr.add(l2);
		lines.put(c2.getIndex(),curr);
		
		Dijkstra testDijkstra = new Dijkstra(2, 3, circles, lines);
		System.out.println("start:  "+testDijkstra.start);
		
		
		Queue<Dijkstravertex> testQ = testDijkstra.getQueue();
		System.out.println("size of queue: "+testQ.size());
		Dijkstravertex d1 = new Dijkstravertex(c0);
		d1.setDistance(5);
		Dijkstravertex d2 = new Dijkstravertex(c1);
		d2.setDistance(1);
		testQ.add(d1);
		testQ.add(d2);
		System.out.println("size of queue: "+testQ.size());
		
		int queueSize = testQ.size();
		testDijkstra.performAStep();
		System.out.println("first call of performAStep(): ");
		for(int i=1; i<= queueSize; i=i+1)
		{
			System.out.println("size of the queue unvisited: "+testQ.size()+ ", i= "+i);
	
			Dijkstravertex currQelement = testQ.remove();
			System.out.println(currQelement.getDistance());
		}
		
		testDijkstra.performAStep();
		
	}
	
	public void test2()
	{
		System.out.println("");
		System.out.println("second test:");
		Circle c0 = new Circle(10, 1, 2, 0);
		Circle c1 = new Circle(10, 2, 3, 1);
		Circle c2 = new Circle(10, 1, 2, 2);
		Circle c3 = new Circle(10, 1, 2, 3);
		Circle c4 = new Circle(10, 1, 2, 4);
		Line l1 = new Line(c0,c1,1);
		Line l2 = new Line(c1,c2,5);
		Line l3 = new Line(c1,c3,3);
		Line l4 = new Line(c2,c4,1);
		Line l5 = new Line(c3,c4,6);
		ArrayList<Circle> circles = new ArrayList<Circle>();
		circles.add(c0);
		circles.add(c1);
		circles.add(c2);
		circles.add(c3);
		circles.add(c4);
		Hashtable<Integer, List<Line>> lines = new Hashtable<Integer, List<Line>>();
		
		// create List of Lines for Circle c0
		List<Line> tmp = new ArrayList<Line>();
		tmp.add(l1);
		lines.put(c0.getIndex(), tmp);
		
		// create List of Lines for Circle c1
		tmp = new ArrayList<Line>();
		tmp.add(l2);
		tmp.add(l3);
		lines.put(c1.getIndex(), tmp);
		
		// create List of Lines for Circle c2
		tmp = new ArrayList<Line>();
		tmp.add(l4);
		lines.put(c2.getIndex(), tmp);
		
		// create List of Lines for Circle c3
		tmp = new ArrayList<Line>();
		tmp.add(l5);
		lines.put(c3.getIndex(), tmp);
		
		// Circle c4 has no outgoing edges
		
		
		Dijkstra testDijkstra = new Dijkstra(0, 4, circles, lines);
		Queue<Dijkstravertex> testQ = testDijkstra.getQueue();
		System.out.println("before call of performAStep(): ");
		System.out.println("initial testDijkstra queue size: "+testDijkstra.getQueue().size());
		
		int queueSize = testQ.size();
		for(int i=1; i<= queueSize; i=i+1)
		{
			System.out.println("size of the queue unvisited: "+testQ.size()+ ", i= "+i);
	
			Dijkstravertex currQelement = testQ.remove();
			System.out.println("Circle: "+currQelement.getCircle().getIndex()+", distance: "+ currQelement.getDistance());
		}
		System.out.println("testDijkstra queue size: "+testDijkstra.getQueue().size());
		
		
		testDijkstra = new Dijkstra(0, 4, circles, lines);
		System.out.println("");
		System.out.println("first call of performAStep(): ");
		testDijkstra.performAStep();
		testQ = testDijkstra.getQueue();
		queueSize = testQ.size();
		for(int i=1; i<= queueSize; i=i+1)
		{
			System.out.println("size of the queue unvisited: "+testQ.size()+ ", i= "+i);
	
			Dijkstravertex currQelement = testQ.remove();
			System.out.println("Circle: "+currQelement.getCircle().getIndex()+", distance: "+ currQelement.getDistance());
		}
		
		testDijkstra = new Dijkstra(0, 4, circles, lines);
		System.out.println("");
		System.out.println("second call of performAStep(): ");
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testQ = testDijkstra.getQueue();
		queueSize = testQ.size();
		for(int i=1; i<= queueSize; i=i+1)
		{
			System.out.println("size of the queue unvisited: "+testQ.size()+ ", i= "+i);
	
			Dijkstravertex currQelement = testQ.remove();
			System.out.println("Circle: "+currQelement.getCircle().getIndex()+", distance: "+ currQelement.getDistance());
		}
		
		testDijkstra = new Dijkstra(0, 4, circles, lines);
		System.out.println("");
		System.out.println("third call of performAStep(): ");
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testQ = testDijkstra.getQueue();
		queueSize = testQ.size();
		for(int i=1; i<= queueSize; i=i+1)
		{
			System.out.println("size of the queue unvisited: "+testQ.size()+ ", i= "+i);
	
			Dijkstravertex currQelement = testQ.remove();
			System.out.println("Circle: "+currQelement.getCircle().getIndex()+", distance: "+ currQelement.getDistance());
		}
		
		testDijkstra = new Dijkstra(0, 4, circles, lines);
		System.out.println("");
		System.out.println("fourth call of performAStep(): ");
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testQ = testDijkstra.getQueue();
		queueSize = testQ.size();
		for(int i=1; i<= queueSize; i=i+1)
		{
			System.out.println("size of the queue unvisited: "+testQ.size()+ ", i= "+i);
	
			Dijkstravertex currQelement = testQ.remove();
			System.out.println("Circle: "+currQelement.getCircle().getIndex()+", distance: "+ currQelement.getDistance());
		}
		
		testDijkstra = new Dijkstra(0, 4, circles, lines);
		System.out.println("");
		System.out.println("fifth call of performAStep(): ");
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testDijkstra.performAStep();
		testQ = testDijkstra.getQueue();
		queueSize = testQ.size();
		for(int i=1; i<= queueSize; i=i+1)
		{
			System.out.println("size of the queue unvisited: "+testQ.size()+ ", i= "+i);
	
			Dijkstravertex currQelement = testQ.remove();
			System.out.println("Circle: "+currQelement.getCircle().getIndex()+", distance: "+ currQelement.getDistance());
		}
		
		
	}
}