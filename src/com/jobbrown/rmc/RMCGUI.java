package com.jobbrown.rmc;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class RMCGUI extends JFrame {

	public RMC model;
	
	private JLabel lblConnections;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RMCGUI frame = new RMCGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RMCGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 909, 383);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 903, 355);
		getContentPane().add(tabbedPane);
		
		Panel systemPanel = new Panel();
		tabbedPane.addTab("System Status", null, systemPanel, null);
		
		lblConnections = new JLabel("There are currently 0 LMS's connected");
		systemPanel.add(lblConnections);
		
		Panel logsPanel = new Panel();
		tabbedPane.addTab("Recent Alarms", null, logsPanel, null);
		
		Panel lmsPanel = new Panel();
		tabbedPane.addTab("View LMS", null, lmsPanel, null);
		
		JComboBox comboBox = new JComboBox();
		lmsPanel.add(comboBox);
		
		Panel usersPanel = new Panel();
		tabbedPane.addTab("Manage Users", null, usersPanel, null);
		
		Panel organizationsPanel = new Panel();
		tabbedPane.addTab("Manage Organizations", null, organizationsPanel, null);
		
		// Launch it
		setTitle("RMC");
		setVisible(true);
	}
	
	public void updateGUI()
	{
		/**
		 * Update System Status Tab
		 */
		lblConnections.setText("There are currently " + this.model.stations.size() + " LMS's connected");
		
		/**
		 * Update Recent Alarms Tab
		 */
		
		/**
		 * Update 'View LMS' Tab
		 * 
		 */
		
		/**
		 * Update 'Manage Users' Tab
		 */
		
		/**
		 * Update 'Manage Organizations' Tab
		 */
	}

}
