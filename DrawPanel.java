//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.scene.image.Image;

import javax.swing.JOptionPane;

public class DrawPanel extends JPanel 
{
	public static ArrayList<Circle> circles;
	public static Hashtable<Integer, List<Line>> lines;
	// for removing one special circle
	public static Circle blueCircle;
	public static Circle edgestart;
	public static Circle edgeend;
	public static Hashtable<Integer, List<Line>> blueLines;
	
	//private final int WIDTH = 800;
	//private final int HEIGHT = 1200;
	static boolean loadedFile = false;
	public static boolean clickedRemoveVertex = false;
	public static boolean clickedMoveVertex = false;
	public static boolean marked = false;
	public static boolean moved = true;
	public boolean addOrRemoveEdgeClicked = false;
	public static boolean addEdgeClicked = false;
	public static boolean startgiven = false;
	Color col = Color.BLACK;
	public String background = ".\\img\\backgroundImage.jpg";
	
	public static Graph graph = new Graph();
	public static int circleindex = 0;
	
	int numberOfVertices;
	int numberOfEdges;
	public static int currrentNumberOfEdges;
	public static boolean couldNotReadAnEdge = false;
	public static boolean duplicateEdge = false;
	public static boolean missingNV = false;
	public static boolean missingNE = false; 
	int start;
	int end;
	
	public int mouseX, mouseY; 
	public JLabel lblMouseCoords;
	
	
	public DrawPanel() 
	{
		// set maximum drawable vertices to 300
		circles = new ArrayList<Circle>(300);
		lines = new Hashtable<Integer, List<Line>>();
		
		//blueCircle = new Circle(-1,0,0);
		blueCircle = new Circle(0,0,-1000000,-5);
		blueLines = new Hashtable<Integer, List<Line>>();
		
		
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
            	if(! addOrRemoveEdgeClicked)
            	{
            		if(! clickedMoveVertex)
                	{
                		marked = false;
                    	if(moved)
                    	{
                    		if(loadedFile == true)
                        	{
                        		// if a file was loaded, no more edges should be created
                        		// else
                        		if(clickedRemoveVertex)
                        		{
                        			double smallestDistance= (circles.get(0).getX() - mouseX)*(circles.get(0).getX() - mouseX)+(circles.get(0).getY()-mouseY)*(circles.get(0).getY()-mouseY);
                        			blueCircle = circles.get(0);
                        			for(Circle c: circles)
                        			{
                        				double getX = (double) c.getX();
                        				double getY = (double) c.getY();
                        				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
                        				if(tmp < smallestDistance)
                        				{
                        					smallestDistance = tmp;
                        					blueCircle = c;
                        				}
                        			}
                        			repaint();
                        			createRemoveVertexFrame();
                        		}        		
                        	}
                        	else
                        	{
                        		if(! clickedRemoveVertex)
                        		{
                        			Circle actualCircle = new Circle(10, mouseX, mouseY, circleindex);
                                	// test for painting only unique circles
                                	boolean circleTester = false;
                                	for(Circle c : circles)
                                	{
                                		// checks whether the x coordinate is taken or not
                                		if(mouseX <= c.getX()+25 & mouseX>=c.getX()-25 & mouseY <= c.getY()+25 & mouseY >=c.getY()-25)
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
                                		if(circleindex < 300)
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
                        			double smallestDistance= (circles.get(0).getX() - mouseX)*(circles.get(0).getX() - mouseX)+(circles.get(0).getY()-mouseY)*(circles.get(0).getY()-mouseY);
                        			blueCircle = circles.get(0);
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
                        			createRemoveVertexFrame();      			
                        		}
                        	}
                    	}
                    	else
                    	{
                    		int x = e.getX();
                			int y = e.getY();
                			Circle movedCircle = new Circle(10,x,y,blueCircle.getIndex());
                			circles.set(blueCircle.getIndex(), movedCircle);
                			//move edges along the moving vertex
                			int tmp1 = blueCircle.getIndex();
                			List<Integer> edgesToMove = graph.outEdges.get(tmp1);
                			List<Integer> edgesToMove2 = graph.inEdges.get(tmp1);
                			if(edgesToMove != null)
                			{
                				int edges2Move = edgesToMove.size();
                    			for(int i=0; i < edges2Move; i++)
                        		{
                    				int movingindex = edgesToMove.get(0);
                    				removeEdge(movedCircle.getIndex(),movingindex);
                    				repaint();
                    				//falsch
                    				addEdge(movedCircle.getIndex(),movingindex,1);
                    				repaint();
                        		}
                			}
                			
                			if(edgesToMove2 != null)
                			{
                				int edges2Move2 = edgesToMove2.size();
                    			
                    			for(int i=0; i < edges2Move2; i++)
                        		{
                    				//System.out.println(edges2Move2);
                    				int movingindex = edgesToMove2.get(0);
                    				removeEdge(movingindex, movedCircle.getIndex());
                    				repaint();	
                    				// falsch
                    				addEdge(movingindex, movedCircle.getIndex(),1);
                    				repaint();
                           		}
                			}
                			
                			//reset blue circle
                			blueCircle = new Circle(0,0,-1000000,-5);
                			//make changes appearing on the GUI
                			repaint();
                    	}
                    	moved = true;
                	}
                	else //block for actual moving of an vertex
                	{
                		//mark the vertex to move
                		clickedMoveVertex = false;
                		if(!marked)
                		{
                    		double smallestDistance= (circles.get(0).getX() - mouseX)*(circles.get(0).getX() - mouseX)+(circles.get(0).getY()-mouseY)*(circles.get(0).getY()-mouseY);
                			blueCircle = circles.get(0);
                			for(Circle c: circles)
                			{
                				double getX = (double) c.getX();
                				double getY = (double) c.getY();
                				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
                				if(tmp < smallestDistance)
                				{
                					smallestDistance = tmp;
                					blueCircle = c;
                				}
                			}
                			marked = true;
                			moved = false;
                		}
            			repaint();
            			col = Color.CYAN;
            			repaint();
            			col = Color.BLACK;     			
            			repaint();	
                	}
            	} 
            	else
            	{
            		if(! startgiven)
            		{
            			double smallestDistance= (circles.get(0).getX() - mouseX)*(circles.get(0).getX() - mouseX)+(circles.get(0).getY()-mouseY)*(circles.get(0).getY()-mouseY);
            			edgestart = circles.get(0);
            			for(Circle c: circles)
            			{
            				double getX = (double) c.getX();
            				double getY = (double) c.getY();
            				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
            				if(tmp < smallestDistance)
            				{
            					smallestDistance = tmp;
            					edgestart = c;
            				}
            			}
            			startgiven = true;
            		}
            		else
            		{
                		double smallestDistance= (circles.get(0).getX() - mouseX)*(circles.get(0).getX() - mouseX)+(circles.get(0).getY()-mouseY)*(circles.get(0).getY()-mouseY);
            			edgeend = circles.get(0);
            			for(Circle c: circles)
            			{
            				double getX = (double) c.getX();
            				double getY = (double) c.getY();
            				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
            				if(tmp < smallestDistance)
            				{
            					smallestDistance = tmp;
            					edgeend = c;
            				}
            			}
            			if(! addEdgeClicked )
            			{
            				removeEdge(edgestart.getIndex(), edgeend.getIndex());
            			}
            			else
            			{
            				addEdge(edgestart.getIndex(), edgeend.getIndex(), 1);
            				addEdgeClicked = false;
            			}
            			repaint();
            			addOrRemoveEdgeClicked = false;
            			startgiven = false;
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
	
	
	public void createRemoveVertexFrame()
	{
		JFrame frame = new JFrame();
		frame.setBounds(350, 200, 275, 95);
		frame.getContentPane().setLayout(new FlowLayout());
		
		JLabel txt = new JLabel();
		txt.setText("Would you like to remove the blue Vertex?");
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
    			
    			// collect all edges to be removed
				int tmp1 = blueCircle.getIndex();
				
				//Carmens method
    			List<Integer> edgesToRemove = graph.outEdges.get(tmp1);
    			// Zielknoten
    			List<Integer> edgesToRemove2 = graph.inEdges.get(tmp1);
    			
    			if(edgesToRemove != null)
    			{
    				int edges2Remove = edgesToRemove.size();
        			for(int i=0; i < edges2Remove; i++)
            		{
            				removeEdge(tmp1, edgesToRemove.get(0));
            				//System.out.println("removed");
            		}
    			}
    			
    			if(edgesToRemove2 != null)
    			{
    				int edges2Remove2 = edgesToRemove2.size();
        			System.out.println(edges2Remove2);
        			for(int i=0; i < edges2Remove2; i++)
            		{
            				removeEdge(edgesToRemove2.get(0), tmp1);
            				//System.out.println("removed2");
            		}
    			}

    			circles.set(blueCircle.getIndex(), new Circle(0,-5,-5,blueCircle.getIndex()));
				graph.removeVertex(blueCircle.getIndex());
    			
    			clickedRemoveVertex = false;
    			col = Color.CYAN;
    			repaint();
    			col = Color.BLACK;
    			blueCircle = new Circle(0,0,-1000000, -5);
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
				blueCircle = new Circle(0,0,-1000000, -5);
				repaint();
				frame.dispose();
			}
		});
		
		frame.addWindowListener(new WindowAdapter() 
		{
			  public void windowClosing(WindowEvent we) 
			  {
				  col = Color.BLACK;
					blueCircle = new Circle(0,0,-1000000, -5);
					repaint();
					frame.dispose();
			  }
		});
		
		frame.getContentPane().add(txt);
		frame.getContentPane().add(ok);
		frame.getContentPane().add(btnCancel);
		frame.setVisible(true);
		
	}
	
	
	// NEW
	public boolean addEdge(int cid1, int cid2, int weight) 
	{
		
			boolean success = graph.addEdge(cid1, cid2, weight);
			if(success)
			{
				if(lines.containsKey(cid1))
				{
					List<Line> curList = lines.get(cid1);
					// die circles sind nicht sortiert! ordne circleindex zu!
					Circle c1 = circles.get(cid1);
					Circle c2 = circles.get(cid2);
					Line lineToAdd = new Line(c1,c2);
					if(curList == null) 
					{
						List<Line> curList1 = new ArrayList<Line>();
						curList1.add(lineToAdd);
						lines.put(cid1, curList1);
					}
					else 
					{
						curList.add(lineToAdd);
						lines.put(cid1, curList);
					}
				}
				
				else
				{
					List<Line> curList = new ArrayList<Line>();
	
					Circle c1 = circles.get(cid1);
					Circle c2 = circles.get(cid2);
					Line lineToAdd = new Line(c1,c2);
					curList.add(lineToAdd);
					lines.put(cid1, curList);		
				}			
			}
			repaint();
			return success;
			
	}

	
	public void removeEdge(int cid1, int cid2)
	{
		
		boolean success = graph.removeEdge(cid1, cid2);
		if(success)
		{
			List<Line> curList = lines.get(cid1);
			// anscheinend Fehler, weil curList.indexOf(cid2) = -1, da cid2 nicht in curList
			// in curList gibt es eine Line mit c1.getIndex() = cid1 und c2.getIndex = cid2
			int indexToRemove= -5;
			Circle c1, c2;
			int circle1, circle2 = -2;
			//for( Line l : curList)
			for(int i=0; i<curList.size(); i=i+1)
			{
				Line tmp = curList.get(i);
				c1 = tmp.getC1();
				c2 = tmp.getC2();
				circle1 = c1.getIndex();
				circle2 = c2.getIndex();
				if(circle1 == cid1 & circle2==cid2)
				{
					indexToRemove = i;
				}
			}
			
			curList.remove(indexToRemove);
			lines.put(cid1, curList);
		}
		repaint();
	}
	
	
	
	public void removeVertex()
	{
		col = Color.GRAY;
		repaint();
		clickedRemoveVertex = true;
	}
	
	// https://stackoverflow.com/questions/9417356/bufferedimage-resize
	public static BufferedImage scale(BufferedImage src, int w, int h)
	{
	    BufferedImage img = 
	            new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    int x, y;
	    int ww = src.getWidth();
	    int hh = src.getHeight();
	    int[] ys = new int[h];
	    for (y = 0; y < h; y++)
	        ys[y] = y * hh / h;
	    for (x = 0; x < w; x++) {
	        int newX = x * ww / w;
	        for (y = 0; y < h; y++) {
	            int col = src.getRGB(newX, ys[y]);
	            img.setRGB(x, y, col);
	        }
	    }
	    return img;
	}
		
	@Override
    protected void paintComponent (Graphics g)
	{
		g.setColor(col);
	
		super.paintComponent(g);
		
		BufferedImage image;
		try 
		{
			image = ImageIO.read(new File(background));
			int height = this.getHeight();
			int width = this.getWidth();
			BufferedImage rescaledImage = scale(image, width, height);
			g.drawImage(rescaledImage, 0, 0, null);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		//lblMouseCoords.setText("coords: (" + mouseX + ", " + mouseY + ")");
		Enumeration<List<Line>> lineIterator = lines.elements();
		while(lineIterator.hasMoreElements())
		{
			List<Line> curList = lineIterator.nextElement();
			// hier geändert
			
			for(int i=0; i < curList.size(); i = i+1)
			{
				Line lineToDraw = curList.get(i);
				g.setColor(col);
				lineToDraw.drawArrowLine(g, 8, 8);
			}
		}
		
		//int i = 0;
		for (Circle c : circles) 
		{
			c.draw(g);
			g.setColor(Color.WHITE); // textcolor for vertex numbers
			g.drawString(String.valueOf(c.getIndex()), c.getX() - 5, c.getY() + 3);
			//g.drawString(String.valueOf(i), c.getX() - 5, c.getY() + 3);
			//i += 1;
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
		blueLines.clear();
		blueCircle = new Circle(0,0,-1000000,-5);
		loadedFile = false;
		graph = new Graph();
		numberOfEdges = 0;
		currrentNumberOfEdges = 0;
		missingNV = false;
		missingNE = false;
		
		repaint();
	}

	
	// CAREFUL
	public boolean loadFile(String path)
	{
		reset();
		boolean txt = path.endsWith(".txt");
		if(!txt) {
			JOptionPane.showMessageDialog(null, "Please choose a .txt file.");
			return true;
		}
		else
		{
			Scanner fileIn;
			try
			{
				int numberOfVertices = 0;
				numberOfEdges = 0;
				fileIn = new Scanner(new File(path));
				// first there needs to be given the number of vertices
				/*if(fileIn.hasNextInt())
				{
					numberOfVertices = fileIn.nextInt();
					//System.out.println("number of vertices: "+numberOfVertices);
				}
				else
				{
					missingNV = true;
					System.out.println("error");
				}
				// next information is the number of edges
				if(fileIn.hasNextInt())
				{
					numberOfEdges = fileIn.nextInt();
					//System.out.println("number of edges: "+numberOfEdges);
				}
				else
				{
					missingNE = true;
					System.out.println("error2");
				}*/
				String v = fileIn.nextLine();
				String c1 = "(\\d+)";
				Pattern c2 = Pattern.compile(c1);
				Matcher m1 = c2.matcher(v);
				boolean matchesV = m1.matches();
				if(matchesV)
				{
					String test = m1.group(0);
					numberOfVertices = Integer.parseInt(test);
				}
				else
				{
					missingNV = true;
					fileIn.close();
					createInputErrorFrame();
					return false;
				}
				
				String e = fileIn.nextLine();
				m1 = c2.matcher(e);
				boolean matchesE = m1.matches();
				if(matchesE)
				{
					String test = m1.group(0);
					numberOfEdges = Integer.parseInt(test);
				}
				else
				{
					missingNE = true;
					fileIn.close();
					createInputErrorFrame();
					return false;
				}
				
				
				
				//fileIn.nextLine();
				circles = new ArrayList<Circle>(numberOfVertices);
				lines = new Hashtable<Integer, List<Line>>();
						
						
				for(int i = 0; i < numberOfVertices; i ++ )
				{
					//random value between 5 and 1195
					Random rand = new Random();
					int x = rand.nextInt(950)+50;
					// CAREFUL
					double yCoord = (1+i) *(800/(numberOfVertices+2));
					Circle tmp = new Circle(10, x, (int) yCoord, circleindex);
					circleindex = circleindex+1;
					circles.add(i, tmp);
					//circles.add(i, new Circle(10, x, ((1+i) *(800/(numberOfVertices+2)))));
					graph.addVertex(new Vertex(x,i *(800/numberOfEdges)));
				}
				
				
				while(fileIn.hasNext())
				{
					
					String line = fileIn.nextLine();
					int firstVertex = -1;
					int secondVertex = -1;
					int weight = -1;
					//System.out.println(line);
					// for instance 2 3
					String patternType1 = "(.*?)(\\d+)\\s+(\\d+)";
					// for instance 2 3 5 with 5 as weight
					String patternType2 = "(.*?)(\\d+)\\s+(\\d+)+\\s+(\\d+)";
					// for instance (2,3) or (2, 3) or (2,  3) or (2 , 3), or ( 2 ,3) etc.
					String patternType3 = "(.*?)(\\s+)?(\\d+)(\\s+)?,(\\s+)?(\\d+)(\\s+)?\\)";
					// like patternType3 but with 3 numbers
					String patternType4 = "(.*?)(\\s+)?(\\d+)(\\s+)?,(\\s+)?(\\d+)(\\s+)?,(\\s+)?(\\d+)(\\s+)?\\)";
					
					
					Pattern pattern1 = Pattern.compile(patternType1);
					Pattern pattern2 = Pattern.compile(patternType2);
					Pattern pattern3 = Pattern.compile(patternType3);
					Pattern pattern4 = Pattern.compile(patternType4);

			        Matcher matcher1 = pattern1.matcher(line);
			        Matcher matcher2 = pattern2.matcher(line);
			        Matcher matcher3 = pattern3.matcher(line);
			        Matcher matcher4 = pattern4.matcher(line);
			        
			        boolean matchesType1 = matcher1.matches();
			        boolean matchesType2 = matcher2.matches();
			        boolean matchesType3 = matcher3.matches();
			        boolean matchesType4 = matcher4.matches();
			        
			        
			        if(matchesType1 & !matchesType2)
			        {
			        	String test = matcher1.group(2);
			        	String test2 = matcher1.group(3);
			        	firstVertex = Integer.parseInt(test);
			        	secondVertex = Integer.parseInt(test2);
			        	/*System.out.println("first vertex: "+Integer.parseInt(test));
			        	System.out.println("second vertex: "+Integer.parseInt(test2));*/
			        	
			        }
			        
			        if(matchesType2)
			        {
			        	String test = matcher2.group(2);
			        	String test2 = matcher2.group(3);
			        	String test3 = matcher2.group(4);
			        	firstVertex = Integer.parseInt(test);
			        	secondVertex = Integer.parseInt(test2);
			        	weight = Integer.parseInt(test3);
			        	
			        }
			        
			        if(matchesType3 & !matchesType4)
			        {
			        	String test = matcher3.group(3);
			        	String test2 = matcher3.group(6);
			        	firstVertex = Integer.parseInt(test);
			        	secondVertex = Integer.parseInt(test2);
			        	
			        }
			        
			        if(matchesType4)
			        {
			        	String test = matcher4.group(3);
			        	String test2 = matcher4.group(6);
			        	String test3 = matcher4.group(9);
			        	firstVertex = Integer.parseInt(test);
			        	secondVertex = Integer.parseInt(test2);
			        	weight = Integer.parseInt(test3);
			 
			        	
			        }
			        
			        if(matchesType3 || matchesType1)
			        {
			        	boolean success = false;
			        	if(weight == -1)
			        	{
			        		success = addEdge(firstVertex, secondVertex,1);
			        	}
			        	else
			        	{
			        		//System.out.println("WEIGHTED EDGE");
			        		success = addEdge(firstVertex, secondVertex, weight);
			        	}
			        	if(success)
			        	{
			        		currrentNumberOfEdges = currrentNumberOfEdges+1;
			        	}
			        	else
			        	{
			        		duplicateEdge = true;
			        	}
			        	
			        }
			        else
			        {
			        	couldNotReadAnEdge = true;
			        }
				}
				
				//closes scanner
				fileIn.close();
				loadedFile = true;
				
				if(numberOfEdges != currrentNumberOfEdges | couldNotReadAnEdge | missingNV | missingNE | duplicateEdge)
				{
					createInputErrorFrame();
					currrentNumberOfEdges = numberOfEdges;
					couldNotReadAnEdge = false;
					missingNV = false;
					missingNE = false;
					duplicateEdge = false;
					return true;
					
				}
				else
				{
					return true;
				}
				
				
					
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return true;
			}
		}	
	}


	public void createInputErrorFrame()
	{
		JFrame frame = new JFrame();
		frame.setBounds(350, 200, 600, 300);
		frame.getContentPane().setLayout(new FlowLayout());
		
		//String text = "<html>Please notice: <br> <br> </html>";
		JLabel text = new JLabel("<html>Please notice: <br> <br> </html>");
		text.setFont(text.getFont().deriveFont(Font.BOLD));
		frame.getContentPane().add(text);
		JLabel txt1 = new JLabel("<html>The number of edges given in line 2 is not equal to the number of actually read edges.<br>  </html>");
		JLabel txt2 = new JLabel("<html>Failed to read an edge.<br> </html>");
		JLabel txt3 = new JLabel("<html>The number of vertices is not given in line 1 or the number of edges is not given in line 2.<br> </html>");
		JLabel txt4 = new JLabel("<html>File contains a duplicate edge.<br> </html>");
		text.setBounds(0, 0, 600, 50);
		txt1.setBounds(0, 100, 600, 50);
		txt2.setBounds(0, 200, 600, 50);
		txt3.setBounds(0, 300, 600, 50);
		
		if(numberOfEdges != currrentNumberOfEdges)
		{
			frame.getContentPane().add(txt1);		}
		if(couldNotReadAnEdge)
		{
			frame.getContentPane().add(txt2);
		}
		if(missingNV | missingNE)
		{
			frame.getContentPane().add(txt3);
		}
		if(duplicateEdge)
		{
			frame.getContentPane().add(txt4);
		}
		
		
		frame.setVisible(true);
	}
	
	public void setBackground(String b)
	{
		this.background = b;
	}
	
	
}
