package com.jobbrown.lms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.common.waterlevels.*;

public class LMS extends LMSPOA 
{
	// Reference to the RMC
	public RMC rmc = null;
	
	// The location name of this LMS
	private String location;
	
	// All of the sensors attached to this LMS
	private HashMap<String, ArrayList<Sensor>> sensors;
	
	// This LMS's log
	private ArrayList<String> log; 

	/**
	 * LMS Constructor
	 * 
	 * @param rmc The RMC object
	 * @param location The string location name of this LMS
	 */
	public LMS(RMC rmc, String location)
	{
		this.rmc = rmc;
		this.location = location;
		this.sensors = new HashMap<String, ArrayList<Sensor>>();	
		this.log = new ArrayList<String>();
	
		this.log("Loaded LMS");
	}
	
	/**
	 * Register this LMS with the RMC so it knows of its existence
	 */
	public void registerWithRMC()
	{
		// Register with the RMC
		if(this.rmc.registerLMS(this.location)) {
			this.log("Registered with RMC");
			return;
		}
		
		this.log("Failed to register with RMC. Probable name clash.");
		System.exit(1);
	}
	
	/** 
	 * Loop through the zones and find the sensor by it's ID number.
	 * If it isnt found, null is returned 
	 * 
	 * @param ID the ID number of the sensor
	 */
	public Sensor findSensorByID(int ID)
	{
		Iterator<Entry<String, ArrayList<Sensor>>> it = sensors.entrySet().iterator();
		
		while(it.hasNext()) 
		{
			Map.Entry pair = (Map.Entry) it.next();
			ArrayList<Sensor> sensors = (ArrayList<Sensor>) pair.getValue();
			
			for(Sensor sensor : sensors) {
				if(sensor.id() == ID)
				{
					return sensor;
				}
			}
			
		}
		
		return null;
	}
	
	/**
	 * Allow a Sensor to raise an alarm with this LMS
	 * 
	 * @param String zone the zone that the sensor is in
	 * @param int raisingSensorID  the ID of the sensor
	 */
	@Override
	public void raiseAlarm(int raisingSensorID, String zone)
	{
		// Get the instance of the Sensor and the raising reading
		Sensor raisingSensor = findSensorByID(raisingSensorID);
		Reading raisingReading = raisingSensor.currentReading();
		
		// A little feedback
		this.log(raisingSensor.name() + " just raised an alarm for zone " + zone);
		
		if( ! sensors.containsKey(zone)) {
			this.log("Invalid zone given. Ignoring alarm.");
			return;
		}
		
		// For an alarm to be raised, all active sensors in the zone should be alarmed
		boolean floodConfirmed = true;
		
		for(Sensor sensor : sensors.get(zone)) {
			if ( sensor.active() && ! sensor.isFlooding())
			{
				floodConfirmed = false;
				break;
			}
		}
		
		if(floodConfirmed) {
			this.log("That alarm is confirmed, sending it to RMC");
			this.rmc.raiseAlarm(raisingSensor.id(), raisingSensor.zone(), this.location, raisingReading);
		} else {
			this.log("That alarm is not confirmed. Logging, but not raising an alert");
		}
	}
	
	/**
	 * This function allows a Sensor to register itself with this LMS. 
	 * 
	 * It will look for the Sensor on the naming service, get its zone
	 * and then add the sensor against the zone in the sensor hash map.
	 * 
	 * This action is logged.
	 * 
	 * @param name the name of the sensor on the naming service
	 */
	@Override
	public boolean registerSensor(String name) {
		// Get an instance of the Sensor
		NamingContextExt namingService = CorbaHelper.getNamingService(this._orb());
		Sensor sensor = null;
		
		// Get a reference to the sensor
	    try {
	    	sensor = SensorHelper.narrow(namingService.resolve_str(name));
		} catch (NotFound | CannotProceed | InvalidName e) {
			System.out.println("Failed to add Sensor named: " + name);
			
			return false;
		}
	    
	    if(sensor != null) {
	    	// Lets check this sensors zone already exists. If not, create it
		    if( ! this.sensors.containsKey(sensor.zone())) {
		    	// Create that zone
		    	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		    	
		    	// Put the array into the sensors hashmap
		    	this.sensors.put(sensor.zone(), sensors);
		    }
		    
		    // Check if the sensor zone already contains this sensor
		    if( ! this.sensors.get(sensor.zone()).contains(sensor)) {
		    	// Add the new sensor to the correct zone
		    	this.sensors.get(sensor.zone()).add(sensor);
		    	
		    	// A little feedback
		    	this.log(name + " has registered with the LMS in zone " + sensor.zone() + ". There are " + this.sensors.get(sensor.zone()).size() + " Sensors in this Zone.");
		    	
		    	// Return success
		    	return true;
		    }
	    } else {
	    	this.log("Returned sensor was null");
	    }
	    
		// At this stage, the sensor must exist already
		return false;
	}

	/**
	 * Get this sensors log
	 * 
	 * @return String[] array of all log entries
	 */
	@Override
	public String[] getLog() {
		String[] logs = new String[this.log.size()];
		return this.log.toArray(logs);
	}
	
	/**
	 * Add a string to the log for this LMS. 
	 * Prefixes the log message with the current timestamp
	 * 
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

	/**
	 * Return this LMS's location name. 
	 * This is provided at start up of LMSLauncher
	 * 
	 * @return String location the location name
	 */
	@Override
	public String getLocation() {
		return this.location;
	}

	/**
	 * Get an array of all the sensors in this LMS.
	 * 
	 * @return Sensor[] an array of sensors in this LMS
	 */
	@Override
	public Sensor[] getAllSensors() {	
		ArrayList<Sensor> sensorArray = new ArrayList<Sensor>();
		
		for(Entry<String, ArrayList<Sensor>> entry : this.sensors.entrySet()) {
			for(Sensor thisSensor : entry.getValue()) {
				sensorArray.add(thisSensor);
			}
		}
		
		// ArrayList.toArray() didn't want to play ball.
		Sensor[] sensorRealArray = new Sensor[sensorArray.size()];
		
		return sensorArray.toArray(sensorRealArray);
	}
	
}
