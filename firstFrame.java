//import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner; //for graph read

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.*;

public class firstFrame {
	
	public static interSteps testTheSteps = new interSteps();
	public JFrame frame;
	static JMenu mnVertex;
	static JMenuItem mntmVertMove;
	static JMenuItem mntmVertRemove;
	static JMenuItem mntmEdgeAdd ;
	static JMenuItem mntmEdgeRemove;
	static JMenuItem mntmExample;
	static JMenuItem mntmFile;
	static JMenuItem dijkstra;
	static JMenuItem mntmBackground;
	static JMenuItem mntmReset;
	
	static JMenu mnEdge;
	static JMenu mnAlgorithm;
	static JMenu mnLoad;
	static JMenu mnSettings;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					firstFrame window = new firstFrame();
					window.frame.setVisible(true);
					
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public firstFrame()
	{
		initialize();
	}
	
	private static DrawPanel drawPanel;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Graph-Toolbox v 0.5");
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBackground(new Color(184, 134, 11));
		//frame.setBounds(50, 50, 1300, 800);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		drawPanel = new DrawPanel();
		drawPanel.setForeground(Color.BLACK);
		drawPanel.lblMouseCoords.setText("COORD");
		
		
		// new size
		Toolkit tk = Toolkit.getDefaultToolkit();
		int frameWidth = ((int) tk.getScreenSize().getWidth());
		int frameHeight = ((int) tk.getScreenSize().getHeight());
		frame.setSize(frameWidth, frameHeight);
		drawPanel.setBounds(0, 40, frameWidth, frameHeight-40);
		
		frame.getContentPane().add(drawPanel);
		//create menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 426, 39);
		frame.getContentPane().add(menuBar);
		
		
		mnVertex = new JMenu("Vertex");
		menuBar.add(mnVertex);
		mnEdge = new JMenu("Edge");
		menuBar.add(mnEdge);
		mnAlgorithm = new JMenu("Algorithm");
		menuBar.add(mnAlgorithm);
		mnLoad = new JMenu("Load");
		menuBar.add(mnLoad);
		mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		
		
		//vertex menu items
		mntmVertMove = new JMenuItem("move");
		mnVertex.add(mntmVertMove);
		mntmVertMove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(drawPanel.graph.vertexSet.size() > 0)
				{
					if(!drawPanel.menuDisabled)
					{
						//enableItems(false);
						drawPanel.clickedMoveVertex = true;
						drawPanel.clickedRemoveVertex = false;
						drawPanel.addOrRemoveEdgeClicked = false;
						drawPanel.addEdgeClicked = false;
						drawPanel.marked = false;
						//drawPanel.blueVertex = new Vertex(0,0,0);
						drawPanel.menuDisabled = true;
					}
					else
					{
						System.out.println("menu disabled");
						JOptionPane.showMessageDialog(null, "Please finish your action.");
						
					}
					
				}		
			}
		});	
		mntmVertRemove = new JMenuItem("remove");
		mnVertex.add(mntmVertRemove);
		mntmVertRemove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent removeVertexAction) 
			{
				if(!drawPanel.menuDisabled)
				{
					//enableItems(false);
					drawPanel.clickedRemoveVertex = true;
					drawPanel.clickedMoveVertex = false;
					drawPanel.addOrRemoveEdgeClicked = false;
					drawPanel.addEdgeClicked = false;
					drawPanel.marked = false;
					drawPanel.removeVertex();
					drawPanel.menuDisabled = true;				}
				else
				{
					System.out.println("menu disabled");
					JOptionPane.showMessageDialog(null, "Please finish your action.");
				}
				
				
				//drawPanel.col = Color.CYAN;
				//drawPanel.repaint();
				
				
				
				//drawPanel.blueVertex = new Vertex(0,0,0);
			}
		});
		
		JCheckBox checkbox = new JCheckBox("show weights");
		checkbox.setSelected(false);
		mnEdge.add(checkbox);
		checkbox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JCheckBox cb = (JCheckBox) event.getSource();
		        if (cb.isSelected()) {
		            drawPanel.showWeights = true;
		            drawPanel.repaint();
		        } else {
		        	drawPanel.showWeights = false;
		        	drawPanel.repaint();
		        }
		    }
		});
		
		//edge menu items
		mntmEdgeAdd = new JMenuItem("add");
		mnEdge.add(mntmEdgeAdd);
		mntmEdgeAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent addEdgeAction) 
			{
				if(!drawPanel.menuDisabled)
				{
					drawPanel.addOrRemoveEdgeClicked = true;
					drawPanel.addEdgeClicked = true;
					drawPanel.startgiven = false;
					drawPanel.clickedMoveVertex = false;
					drawPanel.clickedRemoveVertex = false;
					drawPanel.marked = false;
					//drawPanel.blueVertex = new Vertex(0,0,0);
					drawPanel.menuDisabled = true;
				}
				else
				{
					System.out.println("menu disabled");
					JOptionPane.showMessageDialog(null, "Please finish your action.");
				}
				
			}
		});
		mntmEdgeRemove = new JMenuItem("remove");
		mnEdge.add(mntmEdgeRemove);
		mntmEdgeRemove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent removeEdgeAction) 
			{
				if(!drawPanel.menuDisabled)
				{
					drawPanel.addOrRemoveEdgeClicked = true;
					drawPanel.addEdgeClicked = false;
					drawPanel.startgiven = false;
					drawPanel.clickedMoveVertex = false;
					drawPanel.clickedRemoveVertex = false;
					//drawPanel.blueVertex = null;
					drawPanel.marked = false;
					//drawPanel.blueVertex = new Vertex(0,0,0);
					drawPanel.menuDisabled = true;
				}
				else
				{
					System.out.println("menu disabled");
					JOptionPane.showMessageDialog(null, "Please finish your action.");
				}
				
			}
		});
		
		//load menu items
		mntmExample = new JMenuItem("example");
		mnLoad.add(mntmExample);
		mntmExample.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent loadingAction) 
			{
				drawPanel.reset();
				try {
					drawPanel.loadFile(".\\EXgraph.txt");
				} catch (FileNotFoundException e) {
					// problems with the file are handled in the class DrawPanel
					System.out.println("EXgraph.txt could not be found.");
				}
				drawPanel.repaint();
			}
		});
		mntmFile = new JMenuItem("file");
		mnLoad.add(mntmFile);
		mntmFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent loadingAction) 
			{
				drawPanel.reset();
				OeffnenDialogClass odc = new OeffnenDialogClass();
		        String file = odc.oeffnen();
		        System.out.println(file);
		        try {
					drawPanel.loadFile(file);
				} catch (FileNotFoundException e) {
					// problems with the file are handled in the class DrawPanel
					System.out.println("Your file could not be found.");
				}
		        drawPanel.repaint();
			}
		});
		
		//algorithm menu items
		dijkstra = new JMenuItem("Dijkstra");
		mnAlgorithm.add(dijkstra);
		dijkstra.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent Dijkstra) 
			{
				mnVertex.setEnabled(false);
				mnEdge.setEnabled(false);
				mnAlgorithm.setEnabled(false);
				mnSettings.setEnabled(false);
				mnLoad.setEnabled(false);
				createDijkstraFrame();
			}
		});
		
		
		//settings menu items
		mntmReset = new JMenuItem("reset");
		mnSettings.add(mntmReset);
		mntmReset.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent resetAction) 
			{
				drawPanel.reset();
			}
		});
		mntmBackground = new JMenuItem("change background");
		mnSettings.add(mntmBackground);
		mntmBackground.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent backgroundAction) 
			{
				createBackgroundFrame();
			}
		});
	}
	
	public static void enable()
	{
		mnVertex.setEnabled(true);
		mnEdge.setEnabled(true);
		mnAlgorithm.setEnabled(true);
		mnLoad.setEnabled(true);
		mnSettings.setEnabled(true);
		
		drawPanel.resetBooleans();
		//drawPanel.menuDisabled = false;
	}
	
	public static void enableItems(boolean b)
	{
		mntmVertMove.setEnabled(b);
		mntmVertRemove.setEnabled(b);
		mntmEdgeAdd.setEnabled(b);
		mntmEdgeRemove.setEnabled(b);
		mntmExample.setEnabled(b);
		mntmFile.setEnabled(b);
		dijkstra.setEnabled(b);
		mntmBackground.setEnabled(b);
		mntmReset.setEnabled(b);
		
	}
	
	

	public static void createRemoveEdgeFrame() 
	{
		JFrame frame = new JFrame();
		frame.setBounds(350, 200, 225, 80);
		frame.getContentPane().setLayout(new FlowLayout());
		
		JTextField txtC1 = new JTextField(1);
		JTextField txtC2 = new JTextField(1);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				frame.dispose();
			}
		});
		
		JButton btnRemoveEdge = new JButton("remove");
		btnRemoveEdge.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int cid1 = Integer.parseInt(txtC1.getText());
				int cid2 = Integer.parseInt(txtC2.getText());
				drawPanel.removeEdge(cid1, cid2);
				frame.dispose();
			}
		});
		
		frame.getContentPane().add(txtC1);
		frame.getContentPane().add(txtC2);
		frame.getContentPane().add(btnCancel);
		frame.getContentPane().add(btnRemoveEdge);
		frame.setVisible(true);
	}
	
	public static void createDijkstraFrame() 
	{
		JFrame frame = new JFrame();
		frame.setBounds(350, 250, 275, 150);
		//frame.setSize(200, 80);
		frame.getContentPane().setLayout(null);
		
		/*JTextField txtC1 = new JTextField(1);
		JTextField txtC2 = new JTextField(1);*/
		JLabel txt = new JLabel();
		txt.setText("Please enter two vertices.");
		txt.setBounds(0, 0, 300, 20);
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum((int) Double.POSITIVE_INFINITY);
	    formatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
	    JFormattedTextField field1 = new JFormattedTextField(formatter);
	    JFormattedTextField field2 = new JFormattedTextField(formatter);
	    field1.setBounds(30, 30, 40, 25);
	    field2.setBounds(70, 30, 40, 25);
	    
	    JButton btnSkip = new JButton("skip to end");
		btnSkip.setBounds(110, 60, 100, 25);
		btnSkip.setEnabled(false);

		JButton btnNext = new JButton("next step");
		btnNext.setBounds(110, 30, 100, 25);
		btnNext.setVisible(true);
		btnNext.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent next) 
			{
				field1.setEnabled(false);
				field2.setEnabled(false);
				field1.setBackground(Color.LIGHT_GRAY);
				field2.setBackground(Color.LIGHT_GRAY);
				drawPanel.showVertexWeights = true;
				
				if(testTheSteps == null || testTheSteps.start == -1)
				{
					System.out.println("testTheSteps = null");
					System.out.println("");
					if(drawPanel.graph != null && drawPanel.graph.vertexSet != null)
					{
						if(field1.getValue() != null & field2.getValue() != null)
						{
							if(drawPanel.graph.vertexSet.containsKey(field1.getValue()) & drawPanel.graph.vertexSet.containsKey(field2.getValue()))
							{	
								testTheSteps = new interSteps(drawPanel, "Dijkstra", (int) field1.getValue(), (int) field2.getValue());
								testTheSteps.stepwise("Dijkstra");
								btnSkip.setEnabled(true);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "At least one of the vertices you entered does not exist in the graph.");
								frame.dispose();
								enable();
							}
							
						}
						else
						{
							JOptionPane.showMessageDialog(null, "You did not enter any vertex.");
							frame.dispose();
							enable();
						}
					}
					
				}
				else
				{
					testTheSteps.stepwise("Dijkstra");
				}
				
				
				if(testTheSteps.isFinished())
				{
					btnNext.setEnabled(false);
					btnSkip.setEnabled(false);
					testTheSteps.showPath();
				}
				
			}
		});
		
		btnSkip.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent skip) 
			{
				testTheSteps.skipDijkstraToEnd();
				btnNext.setEnabled(false);
				btnSkip.setEnabled(false);
			}
		});
		
		frame.addWindowListener(new WindowAdapter() 
		{
			  public void windowClosing(WindowEvent we) 
			  {
				  	enable();
				  	if(testTheSteps != null && testTheSteps.testDijkstra != null && testTheSteps.testDijkstra.curVertex != null)
				  	{
				  		ArrayList<Dijkstravertex> p = testTheSteps.testDijkstra.getPath();
					  	if(p != null)
					  	{
					  		createPathFrame();
					  	}
					  	drawPanel.showVertexWeights = false;
					  	testTheSteps.recolor();
					  	testTheSteps.destruct();
				  	}
				  	
				  	frame.dispose();
					
			  }
		});
		frame.getContentPane().add(btnNext);
		frame.getContentPane().add(btnSkip);
		frame.getContentPane().add(txt);
		frame.getContentPane().add(field1);
		frame.getContentPane().add(field2);
		frame.setVisible(true);
	}
	
	
	public static void createBackgroundFrame() 
	{
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setBounds(200, 200, 1000, 500);
		
		int x =(int) Math.floor(frame.getWidth()*0.5);
		int y =(int) Math.floor((frame.getHeight()-35)*0.5);
		
		JButton btnA = new JButton();
		btnA.setBounds(0, 0, x, y);
		BufferedImage image2;
		try
		{
		image2 = ImageIO.read(new File(".\\img\\flow.jpg"));
		BufferedImage rescaledImage = drawPanel.scale(image2, x, y);


		File fileImage2 = new File(".\\img\\flow_scaled.jpg");
		ImageIO.write(rescaledImage, "jpg", fileImage2);


		btnA.setIcon(new ImageIcon(".\\img\\flow_scaled.jpg"));
		btnA.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent a)
			{
				drawPanel.setBackground(".\\img\\flow.jpg");
				drawPanel.repaint();
				frame.dispose();
			}
		});
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton btnB = new JButton();
		btnB.setBounds(x, 0, x, y);
		BufferedImage image1;
		try
		{
		image1 = ImageIO.read(new File(".\\img\\clouds.jpg"));
		BufferedImage rescaledImage = drawPanel.scale(image1, x, y);


		File fileImage1 = new File(".\\img\\clouds_scaled.jpg");
		ImageIO.write(rescaledImage, "jpg", fileImage1);


		btnB.setIcon(new ImageIcon(".\\img\\clouds_scaled.jpg"));
		btnB.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent b)
			{
				drawPanel.setBackground(".\\img\\clouds.jpg");
				drawPanel.repaint();
				frame.dispose();
			}
		});
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton btnC = new JButton();
		btnC.setBounds(x, y, x, y);
		BufferedImage image3;
		try
		{
		image3 = ImageIO.read(new File(".\\img\\backgroundImage.jpg"));
		BufferedImage rescaledImage = drawPanel.scale(image3, x, y);


		File fileImage3 = new File(".\\img\\backgroundImage_scaled.jpg");
		ImageIO.write(rescaledImage, "jpg", fileImage3);


		btnC.setIcon(new ImageIcon(".\\img\\backgroundImage_scaled.jpg"));
		btnC.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent c)
			{
				drawPanel.setBackground(".\\img\\backgroundImage.jpg");
				drawPanel.repaint();
				frame.dispose();
			}
		});
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton btnD = new JButton();
		btnD.setBounds(0, y, x, y);
		BufferedImage image4;
		try
		{
		image4 = ImageIO.read(new File(".\\img\\raindrop.jpg"));
		BufferedImage rescaledImage = drawPanel.scale(image4, x, y);


		File fileImage4 = new File(".\\img\\raindrop_scaled.jpg");
		ImageIO.write(rescaledImage, "jpg", fileImage4);


		btnD.setIcon(new ImageIcon(".\\img\\raindrop_scaled.jpg"));
		btnD.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent d)
			{
				drawPanel.setBackground(".\\img\\raindrop.jpg");
				drawPanel.repaint();
				frame.dispose();
			}
		});
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.getContentPane().add(btnA);
		frame.getContentPane().add(btnB);
		frame.getContentPane().add(btnC);
		frame.getContentPane().add(btnD);
		frame.setVisible(true);		
	}
	
	public static void createPathFrame()
	{
		ArrayList<Dijkstravertex> p = testTheSteps.testDijkstra.getPath();
		System.out.println("");
		System.out.println("Path:");
		if(p != null && p.size()!=0)
		{
			for(Dijkstravertex i : p)
			{
				System.out.print(i.getVertex().getIndex() + ", ");
			}
		}
		System.out.println("");
	}
}
