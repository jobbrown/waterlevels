package com.jobbrown.sensor;

import org.omg.CORBA.ORB;
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
	//A reference to the LMS
	public LMS lms;
	
	// The ID number of the Sensor
	public int ID;
	
	// This is the name the model will be bound as in the name service
	public String name;
	
	// Zone
	public String zone;
	
	// The level at which an alarm should be raised
	public int alarmLevel = 70;
	
	// The current water level
	public int waterLevel = 50;
	
	// Whether or not this sensor is active
	public Boolean active = true;
	
	// The GUI that this model is displayed on
	public SensorGUI gui;
	
	// An instance of the ORB
	private static ORB orb = null;
	
	public Sensor(LMS lms, int ID)
	{
		// Set the LMS
		this.lms = lms;
		
		// Load the settings for this Sensor
		loadSettings(ID);
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
	 * Get the ID of this sensor
	 * 
	 * @return int The ID
	 */
	@Override
	public int id() {
		return this.ID;
	}

	/**
	 * Change the ID of this sensor
	 */
	@Override
	public void id(int newId) {
		this.ID = newId;
	}

	/**
	 * Get the name of this sensor
	 * 
	 * @return String name
	 */
	@Override
	public String name() {
		return this.name;
	}

	/**
	 * Change the name of this sensor (no effects on naming service)
	 */
	@Override
	public void name(String newName) {
		this.name = newName;
	}

	/**
	 * Get the zone this sensor is in
	 * 
	 * return String the zone
	 */
	@Override
	public String zone() {
		return this.zone;
	}

	/**
	 * Change the zone of this sensor
	 */
	@Override
	public void zone(String newZone) {
		this.zone = newZone;
	}

	/**
	 * Get the water level of this Sensor
	 * 
	 * @return int the current water level
	 */
	@Override
	public int waterLevel() {
		return this.waterLevel;
	}

	@Override
	public void waterLevel(int newWaterLevel) {
		this.waterLevel = newWaterLevel;	
		
		/*
		// Create the reading log
		Reading reading = new Reading();
		
		reading.date = 0;
		reading.time = 0;
		reading.waterLevel = waterLevel;
		reading.alarmLevel = this.alarmLevel;
		
		this.log[this.log.length] = reading;
		*/
		
		// Update the GUI, check if we're alarmed
		this.updateThenCheck();
	}

	/**
	 * Get the alarm level for this sensor
	 * 
	 * @return int the alarm level
	 */
	@Override
	public int alarmLevel() {
		return this.alarmLevel;
	}

	/**
	 * Change the alarm level for this sensor
	 */
	@Override
	public void alarmLevel(int newAlarmLevel) {
		this.alarmLevel = newAlarmLevel;
		
		// Update the GUI, then check if we're alarmed
		this.updateThenCheck();
	}

	/**
	 * Return whether this Sensor is active or not
	 * 
	 * @return boolean
	 */
	@Override
	public boolean active() {
		return this.active;
	}

	/**
	 * Set whether this Sensor is active or not.
	 */
	@Override
	public void active(boolean newActive) {
		this.active = newActive;
		
		// Update the GUI, Check if we're alarmed.
		this.updateThenCheck();
	}
	
	/**
	 * This will update the GUI, then check if we're currently in an alarmed stata
	 */
	private void updateThenCheck()
	{
		this.update();
		this.checkAlarmStatus();
	}
	

}
