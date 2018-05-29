import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

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

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

public class firstFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					firstFrame window = new firstFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public firstFrame() {
		initialize();
	}
	
	private static DrawPanel drawPanel;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Graph-Toolbox");
		frame.getContentPane().setBackground(new Color(205, 133, 63));
		frame.setBackground(new Color(184, 134, 11));
		frame.setBounds(50, 50, 1200, 730);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblOtherLable = new JLabel("other lable");
		lblOtherLable.setBounds(350, 67, 85, 23);
		frame.getContentPane().add(lblOtherLable);
		
		JLabel lblBackgroundlable = new JLabel("background_lable");
		lblBackgroundlable.setIcon(new ImageIcon("C:\\Users\\user\\eclipse-workspace\\proj2805\\backgroundImage.jpg"));
		lblBackgroundlable.setBounds(0, 40, 1282, 650);
		//frame.getContentPane().add(lblBackgroundlable);
		
		drawPanel = new DrawPanel();
		drawPanel.setForeground(Color.CYAN);
		drawPanel.lblMouseCoords.setText("COORD");
		drawPanel.setLocation(0, 40);
		drawPanel.setSize(1400, 650);
		//drawPanel.add(lblBackgroundlable);
		frame.getContentPane().add(drawPanel);
		//drawPanel.setOpaque(false);
	}
}
