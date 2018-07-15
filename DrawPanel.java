//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;
import javax.swing.JOptionPane;

public class DrawPanel extends JPanel 
{
	// for removing one special circle
	public static Vertex blueVertex;
	public static Vertex edgestart;
	public static Vertex edgeend;
	
	public static boolean menuDisabled = false;
	static boolean loadedFile = false;
	public static boolean clickedRemoveVertex = false;
	public static boolean clickedMoveVertex = false;
	public static boolean marked = false;
	public static boolean moved = true;
	public boolean addOrRemoveEdgeClicked = false;
	public static boolean addEdgeClicked = false;
	public static boolean startgiven = false;
	public static boolean showWeights = false;
	public static boolean showVertexWeights = false; //for Dijkstra
	Font vertexWFont = new Font ("Chalkboard", 1, 15);
	Color col = Color.BLACK;
	public String background = ".\\img\\backgroundImage.jpg";
	
	public static Graph graph = new Graph();
	//public static int circleindex = 0;
	
	int numberOfVertices;
	int numberOfEdges;
	public static int currrentNumberOfEdges;
	public static boolean couldNotReadAnEdge = false;
	public static boolean duplicateEdge = false;
	public static boolean missingNV = false;
	public static boolean missingNE = false; 
	int start;
	int end;
	double smallestDistance;
	
	public int mouseX, mouseY; 
	public JLabel lblMouseCoords;
	
	
	public DrawPanel() 
	{
		
		blueVertex = new Vertex(0,0,0);
		
		this.setLayout(null);
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
            	
            	// neither add nor remove edge is clicked
            	if(! addOrRemoveEdgeClicked)
            	{
            		if(! clickedMoveVertex)
                	{
            			// there is no vertex marked since move vertex has not been clicked
                		marked = false;
                    	if(moved)
                    	{
                    		if(loadedFile == true)
                        	{
                    			// a graph is loaded and a vertex shall be removed
                        		if(clickedRemoveVertex)
                        		{
                        			if(graph != null && graph.vertexSet != null && !graph.vertexSet.isEmpty())
                        			{
                        				Enumeration<Vertex> existingVertices = graph.vertexSet.elements();
                        				if(existingVertices.hasMoreElements())
                        				{
                        					Vertex existingVertex = existingVertices.nextElement();
                        					smallestDistance = (existingVertex.getX() - mouseX)*(existingVertex.getX() - mouseX)+(existingVertex.getX()-mouseY)*(existingVertex.getX()-mouseY); 
                        					blueVertex = existingVertex;
                                			Enumeration<Vertex> v = graph.vertexSet.elements();
                                			while(v.hasMoreElements())
                                			{
                                				Vertex curVertex = v.nextElement();
                                				double getX = (double) curVertex.getX();
                                				double getY = (double) curVertex.getY();
                                				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
                                				if(tmp < smallestDistance)
                                				{
                                					smallestDistance = tmp;
                                					blueVertex = curVertex;
                                				}
                                			}
                                			
                                			
                                			// invoke function for actual deletion of the marked vertex and its edges
                                			createRemoveVertexFrame();
                                			// print remaining number of vertices
                                			System.out.println("size of vertex set after ieration: "+graph.vertexSet.size());
                                			// process of removing vertex is finished, reset boolean for further deltions
                                			clickedRemoveVertex = false;
                                			repaint();
                        				}
                        				
                        			}
                        			
                        		}
                        		else
                    			{
                    				// in this case the vertexSet is empty and the action does not make sense
                    				resetBooleans();
                    				JOptionPane.showMessageDialog(null, "This action does not make sense.");
                    			}
                        		
                        	}
                        	else
                        	{
                        		if(! clickedRemoveVertex)
                        		{
                        			Vertex newVertex = new Vertex(10, mouseX, mouseY);
                                	// test for painting only unique vertices
                                	boolean vertexTester = false;
                                	Enumeration<Vertex> v = graph.vertexSet.elements();
                                	while(v.hasMoreElements())
                                	{
                                		Vertex curVertex = v.nextElement();
                                		// checks whether the x coordinate is taken or not
                                		if(mouseX <= curVertex.getX()+25 & mouseX>=curVertex.getX()-25 & mouseY <= curVertex.getY()+25 & mouseY >=curVertex.getY()-25)
                                		{
                                			vertexTester = true;
                                		}
                                	}
                                	if(vertexTester == true)
                                	{
                                		
                                	}
                                	// draw only new circles
                                	else
                                	{
                                		graph.addVertex(newVertex);
                                    	repaint();
                                		
                                	}
                        		}
                        		// remove vertex is clicked and no graph is loaded
                        		else
                        		{
                        			if(graph != null && graph.vertexSet != null && !graph.vertexSet.isEmpty())
                        			{
                        				Enumeration<Vertex> existingVertices = graph.vertexSet.elements();
                        				if(existingVertices.hasMoreElements())
                        				{
                        					Vertex existingVertex = existingVertices.nextElement();
                        					smallestDistance = (existingVertex.getX() - mouseX)*(existingVertex.getX() - mouseX)+(existingVertex.getX()-mouseY)*(existingVertex.getX()-mouseY);
                                			blueVertex = existingVertex;
                                		
                                			Enumeration<Vertex> v = graph.vertexSet.elements();
                                			while(v.hasMoreElements())
                                			{
                                				Vertex curVertex = v.nextElement();
                                				double tmp = (curVertex.getX() - mouseX)*(curVertex.getX() - mouseX)+(curVertex.getY()-mouseY)*(curVertex.getY()-mouseY);
                                				if(tmp < smallestDistance)
                                				{
                                					smallestDistance = tmp;
                                					blueVertex = curVertex;	
                                				}
                                			}
                                			
                                			repaint();
                                			createRemoveVertexFrame();  
                        				}
                        				
                        			}
                        			else
                        			{
                        				// in this case the vertexSet is empty and the action does not make sense
                        				resetBooleans();
                        				JOptionPane.showMessageDialog(null, "This action does not make sense.");
                        			}
                        			    			
                        		}
                        	}
                    	}
                    	else
                    	{
                    		int x = e.getX();
                			int y = e.getY();
                			Vertex movedVertex = new Vertex(10,x,y);
                			movedVertex.setIndex(blueVertex.getIndex());
                			graph.vertexSet.put(blueVertex.getIndex(), movedVertex);
                			//move edges along the moved vertex
                			int tmp1 = blueVertex.getIndex();
                			List<Edge> edgesToMove = graph.outEdges.get(tmp1);
                			List<Edge> edgesToMove2 = graph.inEdges.get(tmp1);
                			if(edgesToMove != null)
                			{
                				int edges2Move = edgesToMove.size();
                    			for(int i=0; i < edges2Move; i++)
                        		{
                    			    Edge edge = edgesToMove.get(i);
                    				edge.setStart(movedVertex);
                    				
                    				
                        		}
                    			repaint();
                			}
                			
                			if(edgesToMove2 != null)
                			{
                				int edges2Move2 = edgesToMove2.size();
                    			
                    			for(int i=0; i < edges2Move2; i++)
                        		{
                    		        Edge edge = edgesToMove2.get(i);
                    				edge.setEnd(movedVertex);
                    				
                           		}
                    			repaint();
                			}
                			
                			//reset blue Vertex
                			blueVertex = new Vertex(0,0,0);
                			//make changes appearing on the GUI
                			repaint();
                    	}
                    	// the vertex is now moved
                    	moved = true;
                	}
            		
            		// move vertex is clicked
                	else 
                	{
                		//mark the vertex to move
                		if(!marked)
                		{
                			if(graph != null && graph.vertexSet != null && !graph.vertexSet.isEmpty())
                			{
                				Enumeration<Vertex> existingVertices = graph.vertexSet.elements();
                				if(existingVertices.hasMoreElements())
                				{
                					Vertex existingVertex = existingVertices.nextElement();
                					smallestDistance = (existingVertex.getX() - mouseX)*(existingVertex.getX() - mouseX)+(existingVertex.getX()-mouseY)*(existingVertex.getX()-mouseY);
                            		blueVertex = existingVertex;
                        			
                        			Enumeration<Vertex> v = graph.vertexSet.elements();
                        			while(v.hasMoreElements())
                        			{
                        				Vertex curVertex = v.nextElement();
                        				double getX = (double) curVertex.getX();
                        				double getY = (double) curVertex.getY();
                        				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
                        				if(tmp < smallestDistance)
                        				{
                        					smallestDistance = tmp;
                        					blueVertex = curVertex;
                        				}
                        			}
                        			// the vertex is now marked
                        			marked = true;
                        			// the vertex hat not been moved yet
                        			moved = false;
                				}
                				
                			}
                			else
                			{
                				// in this case the vertexSet is empty and the action does not make sense
                				resetBooleans();
                				JOptionPane.showMessageDialog(null, "This action does not make sense.");
                			}
                			
                		}
            			repaint();
            			col = Color.CYAN;
            			repaint();
            			col = Color.BLACK; 
            			clickedMoveVertex = false;
            			repaint();
            			resetBooleans();
                	}
            	} 
            	// either add vertex or remove edge is clicked
            	else
            	{
            		if(! startgiven)
            		{
            			if(graph != null && graph.vertexSet != null && !graph.vertexSet.isEmpty())
            			{
            				Enumeration<Vertex> existingVertices = graph.vertexSet.elements();
            				if(existingVertices.hasMoreElements())
            				{
            					Vertex existingVertex = existingVertices.nextElement();
                				smallestDistance = (existingVertex.getX() - mouseX)*(existingVertex.getX() - mouseX)+(existingVertex.getY()-mouseY)*(existingVertex.getY()-mouseY);
                    			edgestart = existingVertex;
                    			Enumeration<Vertex> v = graph.vertexSet.elements();
                    			
                    			while(v.hasMoreElements())
                    			{
                    				Vertex curVertex = v.nextElement();
                    				double getX = (double) curVertex.getX();
                    				double getY = (double) curVertex.getY();
                    				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
                    				if(tmp < smallestDistance)
                    				{
                    					smallestDistance = tmp;
                    					edgestart = curVertex;
                    				}
                    			}
                    			startgiven = true;
            				}
            				
            			}
            			else
            			{
            				// in this case the vertexSet is empty and the action does not make sense
            				resetBooleans();
            				JOptionPane.showMessageDialog(null, "This action does not make sense.");
            			}
            			
            		}
            		else
            		{
            			if(graph != null && graph.vertexSet != null && !graph.vertexSet.isEmpty())
            			{
            				Enumeration<Vertex> existingVertices = graph.vertexSet.elements();
            				if(existingVertices.hasMoreElements())
            				{
            					Vertex existingVertex = existingVertices.nextElement();
            					double smallestDistance= (existingVertex.getX() - mouseX)*(existingVertex.getX() - mouseX)+(existingVertex.getY()-mouseY)*(existingVertex.getY()-mouseY);
                    			edgeend = existingVertex;
                    			Enumeration<Vertex> v = graph.vertexSet.elements();
                    			while(v.hasMoreElements())
                    			{
                    				Vertex curVertex = v.nextElement();
                    				double getX = (double) curVertex.getX();
                    				double getY = (double) curVertex.getY();
                    				double tmp = (getX - mouseX)*(getX - mouseX)+(getY-mouseY)*(getY-mouseY);
                    				if(tmp < smallestDistance)
                    				{
                    					smallestDistance = tmp;
                    					edgeend = curVertex;
                    				}
                    			}
            				}
            				
            			}
            			else
            			{
            				// in this case the vertexSet is empty and the action does not make sense
            				resetBooleans();
            				JOptionPane.showMessageDialog(null, "This action does not make sense.");
            			}
                		
            			if(! addEdgeClicked )
            			{
            				graph.removeEdge(edgestart, edgeend);
            				// an edge was removed or added, now other actions can be performed
                			resetBooleans();
            			}
            			else
            			{
            				createAddEdgeFrame();
            				// an edge was removed or added, now other actions can be performed
                			resetBooleans();
            			}
            			repaint();
            			startgiven = false;
            			addOrRemoveEdgeClicked = false; 
            			
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
		resetBooleans();
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
    			// collect all edges to be removed
				Vertex tmp1 = blueVertex;
				
    			List<Edge> edgesToRemove = graph.outEdges.get(tmp1.getIndex());
    			// Zielknoten
    			List<Edge> edgesToRemove2 = graph.inEdges.get(tmp1.getIndex());
    			
    			if(edgesToRemove != null)
    			{
    				int edges2Remove = edgesToRemove.size();
        			for(int i=0; i < edges2Remove; i++)
            		{
        				graph.removeEdge(edgesToRemove.get(0).getStart(), edgesToRemove.get(0).getEnd());
            		}
    			}
    			
    			if(edgesToRemove2 != null)
    			{
    				int edges2Remove2 = edgesToRemove2.size();
        			for(int i=0; i < edges2Remove2; i++)
            		{
        				graph.removeEdge(edgesToRemove2.get(0).getStart(), edgesToRemove2.get(0).getEnd());
            		}
    			}

				graph.removeVertex(blueVertex.getIndex());
    			clickedRemoveVertex = false;
    			col = Color.CYAN;
    			repaint();
    			col = Color.BLACK;
    			blueVertex = new Vertex(0,0,0);
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
				blueVertex = new Vertex(0,0,0);
				repaint();
				clickedRemoveVertex = false;
				frame.dispose();
			}
		});
		
		frame.addWindowListener(new WindowAdapter() 
		{
			  public void windowClosing(WindowEvent we) 
			  {
				  col = Color.BLACK;
					blueVertex = new Vertex(0,0,0);
					repaint();
					frame.dispose();
			  }
		});
		
		frame.getContentPane().add(txt);
		frame.getContentPane().add(ok);
		frame.getContentPane().add(btnCancel);
		frame.setVisible(true);	
	}
	
	public void createAddEdgeFrame()
	{
		JFrame frame = new JFrame("adding an edge");
		frame.getContentPane().setLayout(null);
		frame.setBounds(350, 250, 275, 120);
		
		
		JLabel txt = new JLabel();
		txt.setText("Please enter the weight of the new edge.");
		txt.setBounds(0, 0, 300, 20);
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum((int) Double.POSITIVE_INFINITY);
	    formatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
	    JFormattedTextField field = new JFormattedTextField(formatter);
	    //field.setValue(1);
	    field.setBounds(30, 30, 40, 25);

		
		JButton ok = new JButton("OK");
		ok.setBounds(70, 30, 60, 25);
		ok.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				boolean success = true;
				if(field.getValue() != null)
				{
					int weight = (int) field.getValue();
					success = graph.addEdge(edgestart, edgeend,weight);
					addEdgeClicked = false;
					repaint();
					frame.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You did not enter a weight.");
					
				}
				if(!success)
				{
					JOptionPane.showMessageDialog(null, "The edge could not be added. Probably it already exists.");
				}
				
    			
			}
		});
		frame.getContentPane().add(txt);
		frame.getContentPane().add(field);
		frame.getContentPane().add(ok);
		frame.setVisible(true);	

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
	    for (x = 0; x < w; x++) 
	    {
	        int newX = x * ww / w;
	        for (y = 0; y < h; y++) 
	        {
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
				
		// draw outEdges
		Enumeration<List<Edge>> edgeIterator = graph.outEdges.elements();
		Font currentFont = g.getFont();
		Font myFont = new Font ("Comic Sans MS", 1, 15);
		g.setFont(myFont);
		while(edgeIterator.hasMoreElements())
		{
			List<Edge> curList = edgeIterator.nextElement();
			for(int i=0; i < curList.size(); i = i+1)
			{
				Edge edgeToDraw = curList.get(i);
				//g.setColor(col);
				g.setColor(edgeToDraw.getColor());
				edgeToDraw.drawArrowLine(g, 8, 8);
				if(showWeights)
				{
					int x1 = edgeToDraw.getStart().getX();
					int y1 = edgeToDraw.getStart().getY();
					int x2 = edgeToDraw.getEnd().getX();
					int y2 = edgeToDraw.getEnd().getY();
					double point1 = (x1+x2)/2;
					double point2 = (y1+y2)/2;
					g.drawString(String.valueOf(edgeToDraw.getWeight()), (int)point1, (int)point2);
				}
			}
		}
		g.setFont(currentFont);
	
		Enumeration<Vertex> v = graph.vertexSet.elements();
		while(v.hasMoreElements()) 
		{
			Vertex curVertex = v.nextElement();
			g.setColor(curVertex.getColor());
			curVertex.draw(g);
			g.setColor(Color.WHITE); // textcolor for vertex numbers
			g.drawString(String.valueOf(curVertex.getIndex()), curVertex.getX() - 5, curVertex.getY() + 3);
			if(showVertexWeights)
			{
				if(curVertex.getDistance() != (int) Double.POSITIVE_INFINITY)
				{
					g.setFont(vertexWFont);
					g.drawString(String.valueOf(curVertex.getDistance()), curVertex.getX() +15, curVertex.getY() + 15);
					g.setFont(currentFont);
				}
				
			}
			g.setColor(col);
		}
		
		if(blueVertex.getRadius() != 0)
		{
			g.setColor(Color.CYAN);
			g.fillOval(blueVertex.getX()-blueVertex.getRadius(), blueVertex.getY()-blueVertex.getRadius(), blueVertex.getRadius()*2, blueVertex.getRadius()*2);
			g.setColor(col);
		}
	}
	
	public void reset()
	{
		// resets graph sets
		graph.vertexSet.clear();
		graph.inEdges.clear();
		graph.outEdges.clear();
		blueVertex = new Vertex(0,0,0);
		
		// resets booleans for all buttons 
		loadedFile = false;
		clickedRemoveVertex = false;
		clickedMoveVertex = false;
		marked = false;
		moved = true;
		addOrRemoveEdgeClicked = false;
		addEdgeClicked = false;
		startgiven = false;
		menuDisabled = false;
		
		// reset graph and parameter for loading a graph
		graph = new Graph();
		numberOfEdges = 0;
		currrentNumberOfEdges = 0;
		
		missingNV = false;
		missingNE = false;
		
		repaint();
	}
	
	public boolean removeEdge(int a, int b)
	{
		boolean removed = false;
		// find the edge in the graph that needs to be removed
		if(graph.vertexSet.containsKey(a) & graph.vertexSet.containsKey(b))
		{
			Vertex start = graph.vertexSet.get(a);
			Vertex end = graph.vertexSet.get(b);
			removed = graph.removeEdge(start, end);
		}
		return removed;
	}

	
	public boolean loadFile(String path) throws FileNotFoundException
	{
		reset();
		boolean txt = path.endsWith(".txt");
		if(!txt) {
			JOptionPane.showMessageDialog(null, "Please choose a .txt file.");
			return true;
		}
		else
		{
			File file = new File(path);
			try
			{
				
				int numberOfVertices = 0;
				numberOfEdges = 0;
				Scanner fileIn = new Scanner(file);
				ArrayList<Edge> edgelist = new ArrayList<Edge>();
				
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
				
						
				Toolkit tk = Toolkit.getDefaultToolkit();
				int frameWidth = ((int) tk.getScreenSize().getWidth());
				int frameHeight = ((int) tk.getScreenSize().getHeight());
				
				for(int i = 0; i < numberOfVertices; i ++ )
				{
					//random distribution of the loaded vertices
					Random rand = new Random();
					int xCoord = rand.nextInt(frameWidth/2)+frameWidth/4;
					int yCoord = rand.nextInt(frameHeight/2)+frameWidth/8;
					graph.addVertex(new Vertex(10, xCoord, yCoord));
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
			        		success = graph.addEdge(graph.vertexSet.get(firstVertex), graph.vertexSet.get(secondVertex),1);
			        		if(success)
			        		{
			        			edgelist.add(new Edge(graph.vertexSet.get(firstVertex), graph.vertexSet.get(secondVertex),1));
					        }
			        	}
			        	else
			        	{
			        		//System.out.println("WEIGHTED EDGE");
			        		success = graph.addEdge(graph.vertexSet.get(firstVertex), graph.vertexSet.get(secondVertex),weight);
			        		if(success)
			        		{
			        			edgelist.add(new Edge(graph.vertexSet.get(firstVertex), graph.vertexSet.get(secondVertex),weight));
					        }
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
				
				ForceDirected eades = new ForceDirected(graph.vertexSet, edgelist);
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
				System.out.println("no such file.");
				JOptionPane.showMessageDialog(null, "The file "+path+" does not exist.");
				throw e;
				
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
	
	public void resetBooleans()
	{
		// for removing one special circle
		//blueVertex;
		//edgestart;
		//edgeend;
		menuDisabled = false;
		//static boolean loadedFile = false;
		clickedRemoveVertex = false;
		clickedMoveVertex = false;
		//marked = false;
		//moved = true;
		addOrRemoveEdgeClicked = false;
		addEdgeClicked = false;
		startgiven = false;
		//public static boolean showWeights = false;
	}
	
	
}
