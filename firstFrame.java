//import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner; //for graph read

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class firstFrame {
	
	public static interSteps testTheSteps = new interSteps();
	public JFrame frame;
	static JMenu mnVertex;
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
		JMenuItem mntmVertMove = new JMenuItem("move");
		mnVertex.add(mntmVertMove);
		mntmVertMove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(drawPanel.graph.vertexSet.size() > 0)
				{
					drawPanel.clickedMoveVertex = true;
				}		
			}
		});	
		JMenuItem mntmVertRemove = new JMenuItem("remove");
		mnVertex.add(mntmVertRemove);
		mntmVertRemove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent removeVertexAction) 
			{
				drawPanel.clickedRemoveVertex = true;
				drawPanel.col = Color.CYAN;
				drawPanel.repaint();
				drawPanel.removeVertex();		
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
		JMenuItem mntmEdgeAdd = new JMenuItem("add");
		mnEdge.add(mntmEdgeAdd);
		mntmEdgeAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent addEdgeAction) 
			{
				drawPanel.addOrRemoveEdgeClicked = true;
				drawPanel.addEdgeClicked = true;
			}
		});
		JMenuItem mntmEdgeRemove = new JMenuItem("remove");
		mnEdge.add(mntmEdgeRemove);
		mntmEdgeRemove.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent removeEdgeAction) 
			{
				drawPanel.addOrRemoveEdgeClicked = true;
			}
		});
		
		//load menu items
		JMenuItem mntmExample = new JMenuItem("example");
		mnLoad.add(mntmExample);
		mntmExample.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent loadingAction) 
			{
				drawPanel.reset();
				drawPanel.loadFile(".\\EXgraph.txt");
				drawPanel.repaint();
			}
		});
		JMenuItem mntmFile = new JMenuItem("file");
		mnLoad.add(mntmFile);
		mntmFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent loadingAction) 
			{
				drawPanel.reset();
				OeffnenDialogClass odc = new OeffnenDialogClass();
		        String file = odc.oeffnen();
		        System.out.println(file);
		        drawPanel.loadFile(file);
		        drawPanel.repaint();
			}
		});
		
		//algorithm menu items
		JMenuItem Dijkstra = new JMenuItem("Dijkstra");
		mnAlgorithm.add(Dijkstra);
		Dijkstra.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent Dijkstra) 
			{
				mnVertex.setEnabled(false);
				mnEdge.setEnabled(false);
				mnAlgorithm.setEnabled(false);
				createDijkstraFrame();
			}
		});
		
		
		//settings menu items
		JMenuItem mntmReset = new JMenuItem("reset");
		mnSettings.add(mntmReset);
		mntmReset.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent resetAction) 
			{
				drawPanel.reset();
			}
		});
		JMenuItem mntmBackground = new JMenuItem("change background");
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
		frame.setBounds(350, 200, 225, 80);
		//frame.setSize(200, 80);
		frame.getContentPane().setLayout(new FlowLayout());
		
		JTextField txtC1 = new JTextField(1);
		JTextField txtC2 = new JTextField(1);
		
		JButton btnNext = new JButton("next step");
		btnNext.setVisible(true);
		btnNext.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent next) 
			{
				txtC1.setEnabled(false);
				txtC2.setEnabled(false);
				
				if(testTheSteps == null || testTheSteps.start == -1)
				{
					System.out.println("testTheSteps = null");
					System.out.println("");
					testTheSteps = new interSteps(drawPanel, "Dijkstra", Integer.parseInt(txtC1.getText()), Integer.parseInt(txtC2.getText()));
				}
				
				testTheSteps.stepwise("Dijkstra");
				if(testTheSteps.isFinished())
				{
					btnNext.setEnabled(false);
					testTheSteps.showPath();
				}
				
			}
		});
		
		frame.addWindowListener(new WindowAdapter() 
		{
			  public void windowClosing(WindowEvent we) 
			  {
				  	enable();
				  	ArrayList<Dijkstravertex> p = testTheSteps.testDijkstra.getPath();
				  	if(p != null)
				  	{
				  		createPathFrame();
				  	}
				  	testTheSteps.recolor();
				  	testTheSteps.destruct();
				  	frame.dispose();
					
			  }
		});
		frame.getContentPane().add(btnNext);
		
		frame.getContentPane().add(txtC1);
		frame.getContentPane().add(txtC2);
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
		if(p != null)
		{
			for(Dijkstravertex i : p)
			{
				System.out.print(i.getVertex().getIndex() + ", ");
			}
		}
		System.out.println("");
	}
}
