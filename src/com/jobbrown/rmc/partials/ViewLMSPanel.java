package com.jobbrown.rmc.partials;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jobbrown.common.waterlevels.LMS;
import com.jobbrown.common.waterlevels.Sensor;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.ScrollPane;
import javax.swing.JTextArea;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;

public class ViewLMSPanel extends JPanel {
	// The LMS which this view is showing
	private LMS model;
	
	// GUI properties
	private JLabel lmsName;
	private JTextArea lmsLogs;
	private JPanel sensorOptionsPanel;
	private JTextArea sensorLogs;
	private ScrollPane tableHolder;
	private JTable table;
	private Button updateAlarmLevelBtn;
	private JSlider newAlarmLevelSlider;
	
	
	public ViewLMSPanel()
	{
		// Absolute layout, it's easiest
		setLayout(null);
		
		// LMS Label
		JLabel lblViewingLms = new JLabel("Viewing LMS: ");
		lblViewingLms.setBounds(6, 6, 97, 16);
		add(lblViewingLms);
		
		// Name of the LMS
		lmsName = new JLabel("");
		lmsName.setBounds(115, 6, 236, 16);
		add(lmsName);
		
		sensorOptionsPanel = new JPanel();
		sensorOptionsPanel.setBounds(436, 199, 356, 89);
		add(sensorOptionsPanel);
		sensorOptionsPanel.setLayout(null);
		
		Button toggleActiveBtn = new Button("Toggle Sensor");
		toggleActiveBtn.setBounds(10, 10, 176, 29);
		sensorOptionsPanel.add(toggleActiveBtn);
		
		toggleActiveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleEnabled();
			}
			
		});
		
		updateAlarmLevelBtn = new Button("Update Alarm Level");
		updateAlarmLevelBtn.setBounds(192, 10, 154, 29);
		sensorOptionsPanel.add(updateAlarmLevelBtn);
		
		updateAlarmLevelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				updateAlarmLevel();
			}
			
		});
		
		newAlarmLevelSlider = new JSlider();
		newAlarmLevelSlider.setMajorTickSpacing(25);
		newAlarmLevelSlider.setMinorTickSpacing(5);
		newAlarmLevelSlider.setPaintTicks(true);
		newAlarmLevelSlider.setPaintLabels(true);
		newAlarmLevelSlider.setBounds(10, 45, 336, 43);
		sensorOptionsPanel.add(newAlarmLevelSlider);
		
		
		lmsLogs = new JTextArea();
		lmsLogs.setBounds(6, 34, 418, 355);
		add(lmsLogs);
		
		sensorLogs = new JTextArea();
		sensorLogs.setBounds(436, 294, 356, 95);
		add(sensorLogs);
		
		tableHolder = new ScrollPane();
		tableHolder.setBounds(436, 34, 356, 159);
		add(tableHolder);
		
	}
	
	public void createSensorTable()
	{
		Object[][] tableData = getSensorTableData();
		
		tableHolder.removeAll();
		
		if(tableData.length > 0) {
			System.out.println("Im in");
			
			// Make the table
			table = new JTable(tableData, getSensorTableColumns()) {
				public boolean isCellEditable(int x, int y) {
					return false;
				}
			};
					
			// Add a selection listener (to show logs), options to edit settings etc
			table.getSelectionModel().addListSelectionListener( new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					showSensorLogs();
				}
				
			});
			
			JScrollPane scrollPane = new JScrollPane(table);
			table.setFillsViewportHeight(true);
			
			tableHolder.add(scrollPane);
			
			
			
		} else {
			// Probably should have some feedback here
			tableHolder.add(new JLabel("There are no sensors registered"));
		}
	}
	
	private void updateAlarmLevel() {
		Sensor sensor = getSelectedSensor();
		
		if(sensor == null) {
			return;
		}
		
		sensor.alarmLevel(newAlarmLevelSlider.getValue());
	}
	
	private void toggleEnabled() {
		Sensor sensor = getSelectedSensor();
		
		if(sensor == null) {
			return;
		}
		
		if(sensor.active()) {
			sensor.active(false);
		} else {
			sensor.active(true);
		}
	}
	
	private Sensor getSelectedSensor()
	{
		if(this.model == null) {
			return null;
		}
		
		int i = table.getSelectedRow();
		int sensorID = (int) table.getValueAt(i, 0);
		
		return this.model.findSensorByID(sensorID);
	}
	
	private void showSensorLogs()
	{
		if(this.model == null) {
			return;
		}
		
		Sensor sensor = getSelectedSensor();
		
		if(sensor != null) {
			String[] logs = sensor.getLog();
			
			sensorLogs.setText("");
			
			
			for(String log : sensor.getLog()) {
				sensorLogs.setText(log + "\n" + sensorLogs.getText());
			}
			
		} else {
			System.out.println("sensor was null ..");
		}
	}
	
	private Object[][] getSensorTableData()
	{		
		// Get all the sensors we need to model
		Sensor[] sensors = this.model.getAllSensors();
		
		// Create the array of data that we're going to fill
		Object[][] tableData = new Object[sensors.length][5];
		
		int i = 0;
		for(Sensor sensor : sensors) {
			tableData[i][0] = sensor.id();
			tableData[i][1] = sensor.zone();
			tableData[i][2] = sensor.waterLevel() + "";
			tableData[i][3] = sensor.alarmLevel() + "";
			tableData[i][4] = String.valueOf(sensor.active());
			
			i++;
		}
		
		return tableData;
	}
	
	private String[] getSensorTableColumns()
	{
		String[] columns = { "ID", "Zone", "Water Level", "Alarm Level", "Active" };
		return columns;	
	}
	
	private void loadLMSLogs() 
	{
		lmsLogs.setText("");
		
		String[] logs = this.model.getLog();
		
		for(String log : logs) {
			lmsLogs.setText(log + "\n" + lmsLogs.getText());
		}
	}
	
	public void setup() 
	{
		if(this.model == null) {
			return;
		}
		
		// Set up the main label
		this.lmsName.setText(this.model.getLocation());
		
		// Load the LMS logs
		this.loadLMSLogs();
				
		// Set up the table of sensors
		this.createSensorTable();

	}

	public void loadLMS(LMS lms)
	{
		this.model = lms;
		this.setup();
	}
}
