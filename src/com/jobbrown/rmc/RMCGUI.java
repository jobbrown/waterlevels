package com.jobbrown.rmc;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.jobbrown.lms.LMS;
import com.jobbrown.rmc.partials.ViewLMSPanel;
import com.jobbrown.common.waterlevels.*;
import javax.swing.JButton;

public class RMCGUI extends JFrame {

	public RMC model;
	
	private JLabel lblConnections;
	private JTextArea alarms;
	private JComboBox<String> comboBox;
	private ViewLMSPanel viewLMSPanel;
	
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
		setBounds(100, 100, 909, 610);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 903, 582);
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
		
		JButton btnRefresh = new JButton("Refresh");
		
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateGUI();
			}
			
		});
		logsPanel.add(btnRefresh);
		
		Panel lmsPanel = new Panel();
		tabbedPane.addTab("View LMS", null, lmsPanel, null);
		lmsPanel.setLayout(null);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(20, 6, 303, 27);
		
		comboBox.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lmsChanged((String) comboBox.getSelectedItem());
			}
			
		});
		
		lmsPanel.add(comboBox);
		
		viewLMSPanel = new ViewLMSPanel();
		viewLMSPanel.setBounds(30, 45, 846, 472);
		lmsPanel.add(viewLMSPanel);
		
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
		
		// Empty the Dropdown
		comboBox.removeAllItems();
		comboBox.addItem("");
		comboBox.setSelectedItem("");
		
		// Rebuild the array then add it
		for(Entry<String, com.jobbrown.common.waterlevels.LMS> stationPair : this.model.stations.entrySet()) {
			comboBox.addItem(stationPair.getValue().getLocation());
		}
		
		
		
		/**
		 * Update 'Manage Users' Tab
		 */
		
		/**
		 * Update 'Manage Organizations' Tab
		 */
	}

	public void lmsChanged(String lmsName)
	{
		viewLMSPanel.loadLMS(this.model.stations.get(lmsName));
	}
}
