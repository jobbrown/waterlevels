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
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.jobbrown.lms.LMS;
import com.jobbrown.rmc.alerts.Alert;
import com.jobbrown.rmc.alerts.EmailAlert;
import com.jobbrown.rmc.alerts.MobileAlert;
import com.jobbrown.rmc.partials.ViewLMSPanel;
import com.jobbrown.common.waterlevels.*;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;

public class RMCGUI extends JFrame {

	public RMC model;
	
	private JLabel lblConnections;
	private JTextArea alarms;
	private JComboBox<String> comboBox;
	private ViewLMSPanel viewLMSPanel;
	private JTextField tfForename;
	private JTextField tfSurname;
	private JTextField tfMobile;
	private JTextField tfEmail;
	private JTabbedPane tabbedPane;
	private JComboBox<String> cbRegion;
	
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
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
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
		usersPanel.setLayout(null);
		
		JLabel lblRegisterNewUser = new JLabel("Register new User");
		lblRegisterNewUser.setBounds(6, 6, 160, 16);
		usersPanel.add(lblRegisterNewUser);
		
		JLabel lblForename = new JLabel("Forename");
		lblForename.setBounds(6, 53, 61, 16);
		usersPanel.add(lblForename);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(6, 90, 61, 16);
		usersPanel.add(lblSurname);
		
		JLabel lblAlertPreference = new JLabel("Alert Preference");
		lblAlertPreference.setBounds(6, 131, 128, 16);
		usersPanel.add(lblAlertPreference);
		
		tfForename = new JTextField();
		tfForename.setBounds(137, 47, 134, 28);
		usersPanel.add(tfForename);
		tfForename.setColumns(10);
		
		tfSurname = new JTextField();
		tfSurname.setBounds(137, 84, 134, 28);
		usersPanel.add(tfSurname);
		tfSurname.setColumns(10);
		
		final JComboBox cbAlertPreference = new JComboBox();
		cbAlertPreference.setModel(new DefaultComboBoxModel(new String[] {"Mobile", "Email"}));
		cbAlertPreference.setBounds(137, 127, 134, 27);
		usersPanel.add(cbAlertPreference);
		
		JLabel lblMobileNumber = new JLabel("Mobile Number");
		lblMobileNumber.setBounds(6, 179, 115, 16);
		usersPanel.add(lblMobileNumber);
		
		tfMobile = new JTextField();
		tfMobile.setBounds(137, 173, 134, 28);
		usersPanel.add(tfMobile);
		tfMobile.setColumns(10);
		
		JLabel lblEmailAddress = new JLabel("E-Mail Address");
		lblEmailAddress.setBounds(6, 231, 96, 16);
		usersPanel.add(lblEmailAddress);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(137, 225, 134, 28);
		usersPanel.add(tfEmail);
		tfEmail.setColumns(10);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(137, 335, 117, 29);
		usersPanel.add(btnAddUser);
		
		btnAddUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(validates()) 
				{
					Alert alert = null;
					String alertable = "";
					
					if(cbAlertPreference.getSelectedItem() == "Mobile") {
						alert = new MobileAlert();
						alertable = tfMobile.getText();
					} else {
						alert = new EmailAlert();
						alertable = tfEmail.getText();
					}
					
					// Add the user
					addUser(cbRegion.getSelectedItem().toString(), tfForename.getText(), tfSurname.getText(), alertable, alert);
				}
				
			}
			
			public boolean validates()
			{
				if(tfForename.getText().length() == 0) {
					JOptionPane.showMessageDialog(tabbedPane, "Forename is manditory");
					return false;
				}
				
				if(tfSurname.getText().length() == 0) {
					JOptionPane.showMessageDialog(tabbedPane, "Surname is manditory");
					return false;
				}
				
				if( cbAlertPreference.getSelectedItem() == "Mobile") {
					if(tfMobile.getText().length() == 0) {
						JOptionPane.showMessageDialog(tabbedPane, "Moible number is manditory");
						return false;
					}
					
					if(tfMobile.getText().length() != 11) {
						JOptionPane.showMessageDialog(tabbedPane, "Mobile should be 11 chars");
						return false;
					}
					
					// Lifted from : http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-a-numeric-type-in-java
					if( ! tfMobile.getText().matches("-?\\d+(\\.\\d+)?")) {
						JOptionPane.showMessageDialog(tabbedPane, "Mobile should be numbers only");
						return false;
					}
				} else {
					if(tfEmail.getText().length() == 0) {
						JOptionPane.showMessageDialog(tabbedPane, "Email number is manditory");
						return false;
					}
				}
				
				return true;
			}
		});
		
		JLabel lblRegion = new JLabel("Region");
		lblRegion.setBounds(6, 290, 61, 16);
		usersPanel.add(lblRegion);
		
		cbRegion = new JComboBox();
		cbRegion.setBounds(137, 286, 134, 27);
		usersPanel.add(cbRegion);
		
		Panel organizationsPanel = new Panel();
		tabbedPane.addTab("Manage Organizations", null, organizationsPanel, null);
		
		// Launch it
		setTitle("RMC");
		setVisible(true);
	}
	
	public void addUser(String lms, String forename, String surname, String alertable, Alert alert) {
		this.model.addUser(lms, forename, surname, alertable, alert);
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
		
		// Empty the dropdown
		
		cbRegion.removeAllItems();
		
		for(Entry<String, com.jobbrown.common.waterlevels.LMS> stationPair : this.model.stations.entrySet()) {
			cbRegion.addItem(stationPair.getValue().getLocation());
		}
		
		/**
		 * Update 'Manage Organizations' Tab
		 */
	}

	public void lmsChanged(String lmsName)
	{
		viewLMSPanel.loadLMS(this.model.stations.get(lmsName));
	}
}
