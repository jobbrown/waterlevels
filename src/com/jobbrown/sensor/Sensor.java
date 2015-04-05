package com.jobbrown.sensor;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.sensor.corba.LMS;
import com.jobbrown.sensor.corba.LMSHelper;
import com.jobbrown.sensor.corba.SensorPOA;

public class Sensor extends SensorPOA 
{
	/**
	 * The ID number of the Sensor
	 */
	public int ID;
	
	/**
	 * The level at which an alarm should be raised
	 */
	public int alarmLevel;
	
	/**
	 * The water level
	 */
	public int waterLevel;
	
	/**
	 * Whether or not this sensor is active
	 */
	public Boolean active;
	
	/**
	 * The GUI that this model is displayed on
	 */
	public SensorGUI gui;

	
	public LMS lms;
	
	public Sensor(String[] args)
	{
		getLMSConnection(args);
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
		System.out.println("Loading settings of Sensor " + ID);
		
		this.ID = ID;
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
	
	private void registerOnNamingService()
	{
		
	}

}
