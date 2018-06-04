//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.scene.image.Image;

public class DrawPanel extends JPanel 
{
	private static ArrayList<Circle> circles;
	private static ArrayList<Line> lines;
	//private final int WIDTH = 800;
	//private final int HEIGHT = 1200;
	
	public static Graph graph = new Graph();
	int circleindex = 0;
	int lineindex1;
	int lineindex2;
	
	int numberOfVertices;
	int numberOfEdges;
	int start;
	int end;
	
	public int mouseX, mouseY; 
	public JLabel lblMouseCoords;
	
	public DrawPanel() 
	{
		// set maximum drawable vertices to 50
		circles = new ArrayList<Circle>(50);
		// maximum edges = 50 * 49 (since every vertex can only have 49 neighbors
		lines = new ArrayList<Line>(2450);
		
		// just doing some magic 
		Circle startcircle = new Circle(0,0,0);
		for(int i = 0; i < 2449; i ++ )
		{
			lines.add(i, new Line(startcircle,startcircle));
		}
		
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2, false));	
		this.addMouseMotionListener(new MouseMotionListener() 
		{

			@Override
			public void mouseDragged(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) 
			{
				mouseX = e.getX();
				mouseY = e.getY();
				
				lblMouseCoords.setText("coords: (" + mouseX + ", " + mouseY + ")");
				lblMouseCoords.repaint();
			}
			
		});
		
		lblMouseCoords = new JLabel("move mouse for coords");
		this.add(lblMouseCoords);
		
		
		
		this.addMouseListener(new MouseAdapter() 
		{
        //    private Color background;

            @Override
            public void mousePressed(MouseEvent e) 
            {
            	Circle actualCircle = new Circle(10, mouseX, mouseY);
            	//Vertex vertexToAdd = new Vertex(mouseX,mouseY);
            	//graph.addVertex(vertexToAdd);
            	// test for painting only unique circles
            	boolean circleTester = false;
            	for(Circle c : circles)
            	{
            		// checks whether the x coordinate is taken or not
            		if(mouseX <= c.getX()+20 & mouseX>=c.getX()-20 & mouseY <= c.getY()+20 & mouseY >=c.getY()-20)
            		{
            			circleTester = true;
            		}
            	}
            	if(circleTester == true)
            	{
            		
            	}
            	// draw only new circles
            	else
            	{
            		if(circleindex < 50)
            		{
                    	circles.add(circleindex,actualCircle);
                    	circleindex++;
                    	//add vertex(mouseX, mouseY) to vertexSet
                    	Vertex newVertex = new Vertex(mouseX, mouseY);
                    	graph.addVertex(newVertex);
                    	repaint();
            		}
            	}

            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
            	// do nothing
            }
        });
	}
	
	public void addEdge(int cid1, int cid2) 
	{
		if (cid1 < 0 || cid2 < 0 || cid1 >= circles.size() || cid2 >= circles.size())
		{
			throw new IllegalArgumentException("Ids must be valid");
		}
		lineindex1 = ((cid1*50) + cid2);
		lineindex2 = ((cid2*50) + cid1);

		lines.set(lineindex1, new Line(circles.get(cid1), circles.get(cid2)));
		lines.set(lineindex2, new Line(circles.get(cid1), circles.get(cid2)));
		repaint();
		
	}

	public void removeEdge(int cid1, int cid2)
	{
		
		if (cid1 < 0 || cid2 < 0 || cid1 >= circles.size() || cid2 >= circles.size()) 
		{
			throw new IllegalArgumentException("Ids must be valid");
		}
		
		lineindex1 = ((cid1*50) + cid2) ;
		lineindex2 = ((cid2*50) + cid1);
		
		lines.set(lineindex1, new Line( new Circle(0,0,0), new Circle(0,0,0)));
		lines.set(lineindex2, new Line( new Circle(0,0,0), new Circle(0,0,0)));
		repaint();
	}
		
	@Override
    protected void paintComponent (Graphics g)
	{
	
		super.paintComponent(g);
		
		BufferedImage image;
		try 
		{
			image = ImageIO.read(new File("D:\\\\JAVA_Workspace\\\\SE2018_HGW\\\\backgroundImage.jpg"));
			g.drawImage(image, 0, 0, null);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		//lblMouseCoords.setText("coords: (" + mouseX + ", " + mouseY + ")");
		for (Line l: lines) 
		{
			l.draw(g);
		}
		int i = 0;
		for (Circle c : circles) 
		{
			c.draw(g);
			g.setColor(Color.WHITE); // textcolor for vertex numbers
			g.drawString(String.valueOf(i), c.getX() - 5, c.getY() + 3);
			i += 1;
		}
	}
	
	public void reset()
	{
		lines.clear(); //removes all lines
		circles.clear(); // removes all circles
		circleindex = 0;
		Circle startcircle = new Circle(0,0,0);
		for(int i = 0; i < 2449; i ++ )
		{
			lines.add(i, new Line(startcircle,startcircle));
		}
		repaint();
	}
	
	//function to load a graph from a txt file 
	public static void loadGraph()
	{
		
		Scanner fileIn;
		try
		{
			fileIn = new Scanner(new File("EXgraph.txt"));
			//first line of the txt is the amount of vertices
			int numberOfVertices = fileIn.nextInt();
			//second integer is the amount of edges
			int numberOfEdges = fileIn.nextInt();
			circles = new ArrayList<Circle>(numberOfVertices);
			lines = new ArrayList<Line>(numberOfVertices*(numberOfVertices-1));
			

			Circle startcircle = new Circle(0,0,0);
			for(int i = 0; i < (numberOfVertices*(numberOfVertices-1)); i ++ )
			{
				lines.add(i, new Line(startcircle,startcircle));
			}
					
					
			for(int i = 0; i < numberOfVertices; i ++ )
			{
				//random value between 5 and 1195
				Random rand = new Random();
				int x = rand.nextInt(950)+50;
				circles.add(i, new Circle(10, x, ((1+i) *(800/(numberOfVertices+2)) )));
				graph.addVertex(new Vertex(x,i *(800/numberOfEdges)));
			}
			
			for(int i = 0; i < numberOfEdges; i ++ )
			{
				//create edge(fileIn.nextInt(), fileIn.nextInt())
				int start = fileIn.nextInt();
				int end = fileIn.nextInt();
				//graph.addEdge(start, end, 0);
				lines.set((start*numberOfVertices)+end, new Line(circles.get(start), circles.get(end)));
				lines.set((end*numberOfVertices)+start, new Line(circles.get(start), circles.get(end)));
			}
			
			//closes scanner
			fileIn.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addEdge2(int cid1, int cid2) 
	{
		if (cid1 < 0 || cid2 < 0 || cid1 >= circles.size() || cid2 >= circles.size())
		{
			throw new IllegalArgumentException("Ids must be valid");
		}
		lineindex1 = ((cid1*numberOfVertices) + cid2);
		lineindex2 = ((cid2*numberOfVertices) + cid1);

		lines.set(lineindex1, new Line(circles.get(cid1), circles.get(cid2)));
		lines.set(lineindex2, new Line(circles.get(cid1), circles.get(cid2)));
		repaint();
		
	}
	
	public void removeEdge2(int cid1, int cid2)
	{
		
		if (cid1 < 0 || cid2 < 0 || cid1 >= circles.size() || cid2 >= circles.size()) 
		{
			throw new IllegalArgumentException("Ids must be valid");
		}
		
		lineindex1 = ((cid1*numberOfVertices) + cid2);
		lineindex2 = ((cid2*numberOfVertices) + cid1);	
		
		lines.set(lineindex1, new Line( new Circle(0,0,0), new Circle(0,0,0)));
		lines.set(lineindex2, new Line( new Circle(0,0,0), new Circle(0,0,0)));
		repaint();
	}
}
