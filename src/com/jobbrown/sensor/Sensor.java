package com.jobbrown.sensor;

import java.util.ArrayList;
import java.util.Calendar;

import org.omg.CORBA.ORB;

import com.jobbrown.sensor.corba.DateTime;
import com.jobbrown.sensor.corba.LMS;
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
	
	// The readings of this sensor
	public Reading[] readings;
	
	// An instance of the ORB
	private static ORB orb = null;
	
	public Sensor(LMS lms, int ID, String name, String zone)
	{
		this.lms = lms;
		this.ID = ID;
		this.name = name;
		this.zone = zone;
		
		// I'm sorry - Couldn't find a way to implement ArrayLists so this will do
		this.readings = new Reading[100];
	}
	
	@Override
	public DateTime launched() {
		return null;
	}

	@Override
	public void launched(DateTime newLaunched) {
	}

	@Override
	public Reading[] readings() {
		return this.readings;
	}

	@Override
	public void readings(Reading[] newReadings) {
		this.readings = newReadings;
	}
	
	@Override
	public Reading currentReading() {
		return this.readings[this.readings.length - 1];
	}

	@Override
	public boolean isFlooding() 
	{
		return this.active && (this.waterLevel >= this.alarmLevel);
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
			this.lms.raiseAlarm(this.zone, this.ID);
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
		// Update the water level on the model
		this.waterLevel = newWaterLevel;	
		
		// Create the reading log
		Reading reading = new Reading();
		DateTime dt = new DateTime();
		
		Calendar cal = Calendar.getInstance();
		
		// Construct a Date Time
		dt.year = cal.get(Calendar.YEAR);
		dt.month = cal.get(Calendar.MONTH);
		dt.day = cal.get(Calendar.DATE);
		dt.hours = cal.get(Calendar.HOUR_OF_DAY);
		dt.minutes = cal.get(Calendar.MINUTE);
		
		// Add the time, water level and alarm level to the Reading
		reading.date = dt;
		reading.waterLevel = newWaterLevel;
		reading.alarmLevel = this.alarmLevel;
		
		// Add the reading to the log
		this.readings[this.readings.length] = reading;
		
		// A little feedback.
		System.out.println("Created a new reading with water level: " + newWaterLevel);
		
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

	public void registerWithLMS() 
	{
		// Register with the LMS
		if(this.lms.registerSensor(this.name)) {
			System.out.println("Registered with LMS");
			return;
		}
		
		System.out.println("Failed to register with LMS. Probable name clash.");
		System.exit(1);
		
	}
}
