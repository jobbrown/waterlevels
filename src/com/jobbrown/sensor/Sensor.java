package com.jobbrown.sensor;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.sensor.corba.LMS;
import com.jobbrown.sensor.corba.LMSHelper;
import com.jobbrown.sensor.corba.Reading;
import com.jobbrown.sensor.corba.SensorPOA;

public class Sensor extends SensorPOA 
{
	/**
	 * The ID number of the Sensor
	 */
	public int ID;
	
	/**
	 * This is the name the model will be bound as in the name service
	 */
	public String name;
	
	/**
	 * Zone
	 */
	public String zone;
	
	/**
	 * The level at which an alarm should be raised
	 */
	public int alarmLevel = 70;
	
	/**
	 * The water level
	 */
	public int waterLevel = 50;
	
	/**
	 * Whether or not this sensor is active
	 */
	public Boolean active = true;
	
	/**
	 * The GUI that this model is displayed on
	 */
	public SensorGUI gui;
	
	/**
	 * The log
	 */
	public Reading[] log;
	

	/**
	 * A reference to the LMS
	 */
	public LMS lms;
	
	public Sensor(String[] args)
	{
		// This is dreadful, I'm sorry.
		this.log = new Reading[1000];
		
		// Establish a conection to the LMS
		getLMSConnection(args);
		
		// Load the settings for this Sensor
		loadSettings(Integer.parseInt(args[3]));
	}

	@Override
	public boolean isFlooding() 
	{
		return this.active && (this.waterLevel >= this.alarmLevel);
	}

	@Override
	public boolean isActive() 
	{
		return this.active;
	}

	@Override
	public int getWaterLevel() 
	{
		return this.waterLevel;
	}

	@Override
	public int getAlarmLevel() 
	{
		return this.alarmLevel;
	}

	@Override
	public void setAlarmLevel(int alarmLevel) 
	{
		this.alarmLevel = alarmLevel;
		
		// Update the GUI
		this.update();
		
		// Check if the new condition raises an alarm
		this.checkAlarmStatus();
	}
	
	@Override
	public void setWaterLevel(int waterLevel) 
	{
		this.waterLevel = waterLevel;	
		
		// Create the reading log
		Reading reading = new Reading();
		
		reading.date = 0;
		reading.time = 0;
		reading.waterLevel = waterLevel;
		reading.alarmLevel = this.alarmLevel;
		
		this.log[this.log.length] = reading;
		
		// Update the GUI
		this.update();
		
		// Check if the new condition raises an alarm
		this.checkAlarmStatus();
	}

	@Override
	public void setActive(boolean active) 
	{
		this.active = active;
		
		// Update the GUI
		this.update();
		
		// Check if the new condition raises an alarm
		this.checkAlarmStatus();
	}
	
	/**
	 * Check if we need to raise an alarm
	 */
	@Override
	public void checkAlarmStatus()
	{
		if(isFlooding())
		{
			// Raise an alarm
			this.lms.raiseAlarm(this.zone, this.name);
			this.lms.acceptReading(9999);
		}
	}
	
	/**
	 * Updates the GUI with new values
	 */
	public void update()
	{
		this.gui.updateGUI();
	}
	
	/**
	 * Loads the settings from the LMS
	 * 
	 * @param ID
	 */
	private void loadSettings(int ID)
	{
		this.ID = ID;
		this.name = lms.getSensorName(ID);
		this.zone = lms.getSensorZone(ID);
		this.alarmLevel = lms.getSensorAlarmLevel(ID);
		this.active = lms.getSensorActive(ID);
	}
	
	/**
	 * Establishes a connection to the LMS through the naming service
	 * 
	 * @param args
	 */
	private void getLMSConnection(String[] args)
	{
		NamingContextExt namingService = CorbaHelper.getNamingService(args);
		
		// Get a reference to the LMS
	    try {
			lms = LMSHelper.narrow(namingService.resolve_str("lms"));
		} catch (NotFound | CannotProceed | InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Reading[] getLog() {
		return this.log;
	}
	

}
