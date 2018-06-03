import java.awt.EventQueue;
//import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.Scanner; //for graph read

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class firstFrame {

	private JFrame frame;

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
		frame = new JFrame("Graph-Toolbox v 0.3");
		frame.getContentPane().setBackground(new Color(205, 133, 63));
		frame.setBackground(new Color(184, 134, 11));
		frame.setBounds(50, 50, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/*JLabel lblOtherLabel = new JLabel("other label");
		lblOtherLabel.setBounds(350, 67, 85, 23);
		frame.getContentPane().add(lblOtherLable);*/
		
		JLabel lblBackgroundlable = new JLabel("background_lable");
		lblBackgroundlable.setIcon(new ImageIcon("D:\\JAVA_Workspace\\SE2018_HGW\\backgroundImage.jpg"));
		lblBackgroundlable.setBounds(0, 40, 1282, 700);
		//frame.getContentPane().add(lblBackgroundlable);
		
		drawPanel = new DrawPanel();
		drawPanel.setForeground(Color.CYAN);
		//drawPanel.setBackground(Color.WHITE);
		drawPanel.lblMouseCoords.setText("COORD");
		drawPanel.setLocation(0, 40);
		drawPanel.setSize(1200, 800);
		//drawPanel.add(lblBackgroundlable);
		frame.getContentPane().add(drawPanel);
		
		JButton btnAddEdge = new JButton("add edge");
		btnAddEdge.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent addEdgeAction) 
			{
				createAddEdgeFrame();
			}
		});
		btnAddEdge.setBounds(9, 11, 114, 23);
		frame.getContentPane().add(btnAddEdge);
		
		JButton btnRemoveEdge = new JButton("remove edge");
		btnRemoveEdge.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent removeEdgeAction) 
			{
				createRemoveEdgeFrame();
			}
		});
		btnRemoveEdge.setBounds(133, 11, 109, 23);
		frame.getContentPane().add(btnRemoveEdge);
		
		JButton btnLoadGraph = new JButton("load graph");
		btnLoadGraph.setBounds(247, 11, 110, 23);
		frame.getContentPane().add(btnLoadGraph);
		
		JButton btnResetWindow = new JButton("reset window");
		btnResetWindow.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent resetAction) 
			{
				drawPanel.reset();
			}
		});
		btnResetWindow.setBounds(1061, 0, 104, 34);
		frame.getContentPane().add(btnResetWindow);
		//drawPanel.setOpaque(false);
	}
	
	public static void createAddEdgeFrame() 
	{
		JFrame frame = new JFrame();
		frame.setSize(200, 80);
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
		
		frame.getContentPane().add(txtC1);
		frame.getContentPane().add(txtC2);
		frame.getContentPane().add(btnCancel);
		frame.getContentPane().add(btnAddLine);
		frame.setVisible(true);
	}
	
	public static void createRemoveEdgeFrame() 
	{
		JFrame frame = new JFrame();
		frame.setSize(225, 80);
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
}
