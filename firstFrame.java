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

/**
 * The main method of the software.
 * 
 * @author C. Bruckmann, R. Wagner
 */
public class firstFrame 
{
	/**
	 * <p>
	 * interSteps object for the stepwise execution of the Dijkstra algorithm.
	 * </p>
	 */
	public static interSteps testTheSteps = new interSteps();
	
	/**
	 * <p>
	 * The frame that contains the GUI.
	 * </p>
	 */
	public JFrame frame;
	
	/**
	 * <p>
	 * The menubar object "Vertex".
	 * </p>
	 */
	static JMenu mnVertex;
	
	/**
	 * <p>
	 * The menuitem of "Vertex" which triggers the function to move vertices by clicking.
	 * </p>
	 */
	static JMenuItem mntmVertMove;
	
	/**
	 * <p>
	 * The menuitem of "Vertex" which triggers the function to remove vertices by clicking.
	 * </p>
	 */
	static JMenuItem mntmVertRemove;
	
	/**
	 * <p>
	 * The menuitem of "Edge" which triggers the function to add an edge between two clicked vertices.
	 * </p>
	 */
	static JMenuItem mntmEdgeAdd;
	
	/**
	 * <p>
	 * The menuitem of "Edge" which triggers the function to remove an edge between two clicked vertices.
	 * </p>
	 */
	static JMenuItem mntmEdgeRemove;
	
	/**
	 * <p>
	 * The menuitem of "Load" which triggers the function to load the basic example of a loaded graph from a .txt file.
	 * </p>
	 */
	static JMenuItem mntmExample;
	
	/**
	 * <p>
	 * The menuitem of "Load" which opens the window to open a .txt file from any local saved directory.
	 * </p>
	 */
	static JMenuItem mntmFile;
	
	/**
	 * <p>
	 * The menuitem of "Algorithm" which triggers the Dijkstraalgorithm.
	 * </p>
	 */
	static JMenuItem dijkstra;
	
	/**
	 * <p>
	 * The menuitem of "Settings" which triggers the function to change the backgroundpicture.
	 * </p>
	 */
	static JMenuItem mntmBackground;
	
	/**
	 * <p>
	 * The menuitem of "Settings" which triggers the reset function to clear the drawpanel.
	 * </p>
	 */
	static JMenuItem mntmReset;
	
	/**
	 * <p>
	 * The menubar object "Edge".
	 * </p>
	 */
	static JMenu mnEdge;
	
	/**
	 * <p>
	 * The menubar object "Algorithm".
	 * </p>
	 */
	static JMenu mnAlgorithm;
	
	/**
	 * <p>
	 * The menubar object "Load".
	 * </p>
	 */
	static JMenu mnLoad;
	
	/**
	 * <p>
	 * The menubar object "Settings".
	 * </p>
	 */
	static JMenu mnSettings;

	/**
	 * <p>
	 * Launch the application.
	 * </p>
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
	 * <p>
	 * Create the application.
	 * </p>
	 */
	public firstFrame()
	{
		initialize();
	}
	
	/**
	 * <p>
	 * Create the drawpanel.
	 * </p>
	 */
	private static DrawPanel drawPanel;

	/**
	 * <p>
	 * Initialize the contents of the frame.
	 * </p>
	 */
	private void initialize()
	{
		frame = new JFrame("Graph-Toolbox");
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBackground(new Color(184, 134, 11));
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
						drawPanel.menuDisabled = true;
					}
					else
					{
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
					drawPanel.menuDisabled = true;				
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please finish your action.");
				}
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
					drawPanel.menuDisabled = true;
				}
				else
				{
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
					drawPanel.marked = false;
					drawPanel.menuDisabled = true;
				}
				else
				{
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
				try 
				{
					drawPanel.loadFile(".\\EXgraph.txt");
				} 
				catch (FileNotFoundException e)
				{
					// problems with the file are handled in the class DrawPanel
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
		        try
		        {
					drawPanel.loadFile(file);
				} 
		        catch (FileNotFoundException e)
		        {
					// problems with the file are handled in the class DrawPanel
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
	
	/**
	 * <p>
	 * Function which enables the menubar after the completition of the Dijkstra.
	 * </p>
	 */
	public static void enable()
	{
		mnVertex.setEnabled(true);
		mnEdge.setEnabled(true);
		mnAlgorithm.setEnabled(true);
		mnLoad.setEnabled(true);
		mnSettings.setEnabled(true);	
		drawPanel.resetBooleans();
	}
	
	/**
	 * <p>
	 * Function which enables the menuitems of the menubar after the completition of the Dijkstra.
	 * </p>
	 */
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
	
	/*public static void createRemoveEdgeFrame() 
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
	}*/
	
	/**
	 * <p>
	 * Function which opens the window of the Dijkstra algorithm for the input of the start and end vertex.<br>
	 * Also displays the window for the "next step" or "skip to the end" options of the stepwise approach.
	 * </p>
	 */
	public static void createDijkstraFrame() 
	{
		JFrame frame = new JFrame();
		frame.setBounds(350, 250, 275, 150);
		frame.getContentPane().setLayout(null);

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
					if(field1.getValue() != null & field2.getValue() != null)
					{
							
						testTheSteps = new interSteps(drawPanel, "Dijkstra", (int) field1.getValue(), (int) field2.getValue());
						boolean success = testTheSteps.stepwise("Dijkstra");
						btnSkip.setEnabled(true);
						
						if(!success)
						{
							frame.dispose();
							testTheSteps.destruct();
							enable();
						}						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "You did not fill in the vertices.");
						frame.dispose();
						enable();
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
					  	drawPanel.showVertexWeights = false;
					  	testTheSteps.recolor();
					  	testTheSteps.destruct();
					  	testTheSteps = new interSteps();
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

	/**
	 * <p>
	 * Function to create and display the window for the backgroundpicture selection.
	 * </p>
	 */
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
}
