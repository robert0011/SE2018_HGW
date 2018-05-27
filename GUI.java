import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.Scanner; //for graph read

public class GUI
{
	
	private static DrawPanel drawPanel;
	
	public static void initGUI() 
	{
		JFrame frame = new JFrame("Graph-Toolbox v0.1");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLayout(new BorderLayout(5, 5));
        
        drawPanel = new DrawPanel();
		frame.add(drawPanel);
		
		JPanel buttonPane = new JPanel();
		JButton button1 = new JButton("Add Edge");
		button1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				createAddEdgeFrame();			
			}
		});
		buttonPane.add(button1);
        frame.setVisible(true);
            
		JButton button2 = new JButton("Remove Edge");
		button2.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// here needs to be the remove edge function
			}
		});

		buttonPane.add(button2);
        frame.setVisible(true);
        
		JButton button3 = new JButton("save Graph");
		button3.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				saveGraph();
			}
		});
		
		buttonPane.add(button3);
        frame.setVisible(true);
        
		JButton button4 = new JButton("load Graph");
		button4.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				loadGraph();
			}
		});
		
		buttonPane.add(button4);
		frame.add(buttonPane, BorderLayout.NORTH);
        frame.setVisible(true);
	}

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable()
		{  //Note 1
            public void run() 
            {
                initGUI();
            }
        });
	}
	
	public static void createAddEdgeFrame() 
	{
		JFrame frame = new JFrame();
		frame.setSize(200, 80);
		frame.setLayout(new FlowLayout());
		
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
		
		JButton btnAddLine = new JButton("add");
		btnAddLine.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int cid1 = Integer.parseInt(txtC1.getText());
				int cid2 = Integer.parseInt(txtC2.getText());
				drawPanel.addEdge(cid1, cid2);
				frame.dispose();
			}
		});
		
		frame.add(txtC1);
		frame.add(txtC2);
		frame.add(btnCancel);
		frame.add(btnAddLine);
		frame.setVisible(true);
	}
	
	public static void createRemoveEdgeFrame() 
	{
		JFrame frame = new JFrame();
		frame.setSize(200, 80);
		frame.setLayout(new FlowLayout());
		
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
		
		frame.add(txtC1);
		frame.add(txtC2);
		frame.add(btnCancel);
		frame.add(btnRemoveEdge);
		frame.setVisible(true);
	}
	
	public static void createRemoveEdgeFrame()
	{
		
	}
	
	// function to save a graph in a txt file
	public static void saveGraph()
	{
		PrintWriter writer = new PrintWriter("graph.txt", "UTF-8");
		//save the number of vertices
		writer.println(vertexSet.size());
		//save the number of edges
		writer.println(edges.size());
		//save the edges (Format: start end)
		while(int i != (edges.size()+2))
		{
			writer.println("start end");
			i = i++;
		}
		writer.close();
	}
	
	//function to load a graph from a txt file 
	public static void loadGraph()
	{
		Scanner fileIn = new Scanner(new File(/*thePathToYourFile*/));
		
		//first line of the txt is the amount of vertices
		int numberOfVertices = fileIn.nextInt();
		//second integer is the amount of edges
		int numberOfEdges = fileIn.nextInt();
		
		//does only read if file has a following word
		if(fileIn.hasNext() == true)
		{
			//create edge(fileIn.nextInt(), fileIn.nextInt())
		}
		//closes scanner
		fileIn.close();
		
	}
}
