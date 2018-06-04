import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadPanel extends JPanel 
{
	private ArrayList<Circle> circles;
	private ArrayList<Line> lines;
	public Graph graph;
	int circleindex = 0;
	int lineindex;
	int numberOfVertices;
	int numberOfEdges;
	
	public LoadPanel() 
	{
		//function to load a graph from a txt file 
		public static final loadGraph()
		{
			Scanner fileIn;
			try
			{
				fileIn = new Scanner(new File("EXgraph"));
				//first line of the txt is the amount of vertices
				numberOfVertices = fileIn.nextInt();
				//second integer is the amount of edges
				numberOfEdges = fileIn.nextInt();
				
				//does only read if file has a following word
				if(fileIn.hasNext() == true)
				{
					//create edge(fileIn.nextInt(), fileIn.nextInt())
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
		
		circles = new ArrayList<Circle>(numberOfVertices);
		lines = new ArrayList<Line>(numberOfEdges);
		graph = new Graph();
	}
}