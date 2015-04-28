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
import javax.swing.JTextArea;

import com.jobbrown.lms.LMS;
import com.jobbrown.common.waterlevels.*;

public class RMCGUI extends JFrame {

	public RMC model;
	
	private JLabel lblConnections;
	private JTextArea alarms;
	
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
		
		alarms = new JTextArea();
		
		alarms.setColumns(70);
		alarms.setText("Alarms will be displayed here");
		alarms.setRows(18);
		
		logsPanel.add(alarms);
		
		Panel lmsPanel = new Panel();
		tabbedPane.addTab("View LMS", null, lmsPanel, null);
		
		JComboBox<LMS> comboBox = new JComboBox<LMS>();
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
		for(Alarm alarm : this.model.alarms) {
			
			int sensorID = alarm.sensorID;
			String sensorZone = alarm.zone;
			String lms = alarm.lms;
			
			com.jobbrown.common.waterlevels.LMS raisingLMS = this.model.stations.get(lms);
			
			Sensor raisingSensor = (Sensor) raisingLMS.findSensorByID(sensorID);
			
			String text = alarm.reading.date + ": " + raisingSensor.name() + " in zone " + sensorZone + "\n";
			
			alarms.setText(text + alarms.getText());
			//alarms.setText(alarm.reading.date + ": " + this.model.getSensorByID(alarm.sensorID). + "\n" + alarms.getText());
		}
		
		
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
