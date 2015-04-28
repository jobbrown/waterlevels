package com.jobbrown.sensor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.omg.CORBA.ORB;

import com.jobbrown.common.waterlevels.*;

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
	public int waterLevel = 0;
	
	// Whether or not this sensor is active
	public Boolean active = true;
	
	// The GUI that this model is displayed on
	public SensorGUI gui;
	
	// The readings of this sensor
	public ArrayList<Reading> readings;
	
	// Log
	public ArrayList<String> log;
	
	public Sensor(LMS lms, int ID, String name, String zone)
	{
		this.lms = lms;
		this.ID = ID;
		this.name = name;
		this.zone = zone;
		
		// Couldn't find a way to implement ArrayLists so this will do
		this.readings = new ArrayList<Reading>();
		
		this.log = new ArrayList<String>();
	}
	
	@Override
	public Reading[] readings() {
		return (Reading[]) this.readings.toArray();
	}

	@Override
	public void readings(Reading[] newReadings) {
		this.readings = new ArrayList<Reading>(Arrays.asList(newReadings));
	}
	
	@Override
	public Reading currentReading() {
		return this.readings.get( this.readings.size() - 1 );
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
			this.lms.raiseAlarm(this.ID, this.zone);
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
		
		// Construct a Date Time
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		
		// Add the time, water level and alarm level to the Reading
		reading.date = dateFormat.format(date);
		reading.waterLevel = newWaterLevel;
		reading.alarmLevel = this.alarmLevel;
		
		// Add the reading to the log
		this.readings.add(reading);
		
		// A little feedback.
		this.log("Created a new reading with water level: " + newWaterLevel);
		
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
			this.log("Registered with LMS");
			return;
		}
		
		this.log("Failed to register with LMS. Probable name clash.");
		System.exit(1);
	}
	
	/**
	 * Get this sensors log
	 */
	@Override
	public String[] getLog() {
		return (String[]) this.log.toArray();
	}
	
	/**
	 * Add a string to the log for this LMS. Prefixes the 
	 * @param str
	 */
	public void log(String str)
	{
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		String loggable = dateFormat.format(date) + ": " + str;
		
		// Add it to the log
		this.log.add(loggable);
		
		// Print it out, for now.
		System.out.println(loggable); 
	}
}
