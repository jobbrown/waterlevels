package com.jobbrown.rmc.partials;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jobbrown.common.waterlevels.LMS;
import com.jobbrown.common.waterlevels.Sensor;
import javax.swing.JScrollPane;

public class ViewLMSPanel extends JPanel {

	private JLabel lmsName;
	private JPanel tableHolder;
	private LMS model;
	
	public ViewLMSPanel()
	{
		// Absolute layout, it's easiest
		setLayout(null);
		
		// LMS Label
		JLabel lblViewingLms = new JLabel("Viewing LMS: ");
		lblViewingLms.setBounds(16, 16, 97, 16);
		add(lblViewingLms);
		
		// Name of the LMS
		lmsName = new JLabel("");
		lmsName.setBounds(125, 16, 61, 16);
		add(lmsName);
		
		// Table holder
		JPanel tableHolder = new JPanel();
		tableHolder.setBounds(256, 180, 328, -163);
		add(tableHolder);
		
		JPanel sensorOptionsPAnel = new JPanel();
		sensorOptionsPAnel.setBounds(256, 205, 328, 89);
		add(sensorOptionsPAnel);
		
		JScrollPane lmsLogs = new JScrollPane();
		lmsLogs.setBounds(26, 44, 210, 236);
		add(lmsLogs);
		
	}
	
	public void createSensorTable()
	{
		Object[][] tableData = getSensorTableData();
		
		tableHolder.removeAll();
		
		if(tableData.length > 0) {
			// Make the table
			JTable table = new JTable(tableData, getSensorTableColumns()) {
				public boolean isCellEditable(int x, int y) {
					return false;
				}
			};
			
			// Add a selection listener (to show logs), options to edit settings etc
			table.getSelectionModel().addListSelectionListener( new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					System.out.println("Table changed");
					System.out.println(e.getSource());
				}
				
			});
			
			
		} else {
			// Probably should have some feedback here
			tableHolder.add(new JLabel("There are no sensors registered"));
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
	
	public void setup() 
	{
		// Set up the main label
		this.lmsName.setText(this.model.getLocation());
		
		// Set up the table of sensors
		this.createSensorTable();
	}

	public void loadLMS(LMS lms)
	{
		this.model = lms;
		this.setup();
	}
}
