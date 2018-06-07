//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class DrawPanel extends JPanel 
{
	public static ArrayList<Circle> circles;
	public static ArrayList<Line> lines;
	// for removing one special circle
	public static Circle blueCircle;
	private static ArrayList<Line> blueLines;
	
	//private final int WIDTH = 800;
	//private final int HEIGHT = 1200;
	static boolean loadedFile = false;
	public static boolean clickedRemoveVertex = false;
	Color col = Color.BLACK;
	
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
		
		//blueCircle = new Circle(-1,0,0);
		blueCircle = new Circle(0,0,-1000000);
		blueLines = new ArrayList<Line>(2450);
		
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
				lblMouseCoords.setForeground(Color.cyan);
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
            	if(loadedFile == true)
            	{
            		// if a file was loaded, no more edges should be created
            		// else
            		if(clickedRemoveVertex)
            		{
            			double smallestDistance= (circles.get(1).getX() - mouseX)*(circles.get(1).getX() - mouseX)+(circles.get(1).getY()-mouseY)*(circles.get(1).getY()-mouseY);
            			blueCircle = circles.get(1);
            			for(Circle c: circles)
            			{
            				double getX = (double) c.getX();
            				double getY = (double) c.getY();
            				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
            			//	double dist = Math.sqrt(tmp);
            				if(tmp < smallestDistance)
            				{
            					smallestDistance = tmp;
            					blueCircle = c;
            					
            					
            				}
            			}
            			repaint();
            			createRemoveEdgeFrame();
            		}
            		
            	}
            	else
            	{
            		if(! clickedRemoveVertex)
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
            		
            		else
            		{
            			double smallestDistance= (circles.get(1).getX() - mouseX)*(circles.get(1).getX() - mouseX)+(circles.get(1).getY()-mouseY)*(circles.get(1).getY()-mouseY);
            			blueCircle = circles.get(1);
            			for(Circle c: circles)
            			{
            				double tmp = (c.getX() - mouseX)*(c.getX() - mouseX)+(c.getY()-mouseY)*(c.getY()-mouseY);
            			//	double dist = Math.sqrt(tmp);
            				if(tmp < smallestDistance)
            				{
            					smallestDistance = tmp;
            					blueCircle = c;
            					
            					
            				}
            			}
            			repaint();
            			createRemoveEdgeFrame(); 
            			
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
	
	
	public void createRemoveEdgeFrame()
	{
		JFrame frame = new JFrame();
		frame.setBounds(350, 200, 225, 80);
		frame.getContentPane().setLayout(new FlowLayout());
		
		JLabel txt = new JLabel();
		txt.setText("Would you like to remove the blue Vertex?");
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				circles.remove(blueCircle);
    			Vertex vertexToRemove = new Vertex(blueCircle.getX(), blueCircle.getY());
    			// search for the key of vertexToRemove
    			Enumeration<Integer> test = graph.vertexSet.keys();
    			while(test.hasMoreElements())
    			{
    				int v = test.nextElement();
    				Vertex curVertex = graph.vertexSet.get(v);
    				if(curVertex.x == vertexToRemove.x & curVertex.y == vertexToRemove.y)
    				{
    					graph.vertexSet.remove(v);
    				}
    				
    				
    			}
    			
    			
    			clickedRemoveVertex = false;
    			col = Color.CYAN;
    			repaint();
    			col = Color.BLACK;
    			blueCircle = new Circle(0,0,-1000000);
    			repaint();
				
				frame.dispose();
			}
		});
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				col = Color.BLACK;
				blueCircle = new Circle(0,0,-1000000);
				repaint();
				frame.dispose();
			}
		});
		frame.getContentPane().add(txt);
		frame.getContentPane().add(ok);
		frame.getContentPane().add(btnCancel);
		frame.setVisible(true);
		
	}
	
	
	public void addEdge(int cid1, int cid2) 
	{
		if (cid1 < 0 || cid2 < 0 || cid1 >= circles.size() || cid2 >= circles.size())
		{
			throw new IllegalArgumentException("Ids must be valid");
		}
		else
		{
			lineindex1 = ((cid1*50) + cid2);
			lineindex2 = ((cid2*50) + cid1);

			lines.set(lineindex1, new Line(circles.get(cid1), circles.get(cid2)));
			//lines.set(lineindex2, new Line(circles.get(cid1), circles.get(cid2)));
			graph.addEdge(cid1, cid2, 1);
			repaint();
		}
		
		
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
	
	public void removeVertex()
	{
		col = Color.GRAY;
		repaint();
		clickedRemoveVertex = true;
	}
		
	@Override
    protected void paintComponent (Graphics g)
	{
		g.setColor(col);
	
		super.paintComponent(g);
		
		BufferedImage image;
		try 
		{
			image = ImageIO.read(new File(".\\backgroundImage.jpg"));
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
			//l.draw(g);
			g.setColor(col);
			l.drawArrowLine(g, 8, 8);
		}
		int i = 0;
		for (Circle c : circles) 
		{
			c.draw(g);
			g.setColor(Color.WHITE); // textcolor for vertex numbers
			g.drawString(String.valueOf(i), c.getX() - 5, c.getY() + 3);
			i += 1;
			g.setColor(col);
		}
		
		if(blueCircle.getRadius() != -1)
		{
			g.setColor(Color.CYAN);
			g.fillOval(blueCircle.getX()-blueCircle.getRadius(), blueCircle.getY()-blueCircle.getRadius(), blueCircle.getRadius()*2, blueCircle.getRadius()*2);
			g.setColor(col);
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
		loadedFile = false;
		repaint();
	}
	
	/*
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
			loadedFile = true;
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}*/
	
	
	// CAREFUL
	public static void loadFile(String path)
	{
		boolean txt = path.endsWith(".txt");
		if(!txt) {
			JOptionPane.showMessageDialog(null, "Please choose a .txt file.");
		}
		else
		{
			Scanner fileIn;
			try
			{
				fileIn = new Scanner(new File(path));
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
					graph.addEdge(start, end, 0);
					lines.set((start*numberOfVertices)+end, new Line(circles.get(start), circles.get(end)));
					lines.set((end*numberOfVertices)+start, new Line(circles.get(start), circles.get(end)));
				}
				
				//closes scanner
				fileIn.close();
				loadedFile = true;
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		
	}


}
