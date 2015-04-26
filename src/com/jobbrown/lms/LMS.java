package com.jobbrown.lms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.lms.corba.LMSPOA;
import com.jobbrown.lms.corba.RMC;
import com.jobbrown.lms.corba.Sensor;
import com.jobbrown.lms.corba.SensorHelper;

public class LMS extends LMSPOA 
{
	// Reference to the RMC
	public RMC rmc = null;
	
	// The location name of this LMS
	private String location;
	
	// All of the sensors attached to this LMS
	private HashMap<String, ArrayList<Sensor>> sensors;
	
	// The orb
	private static ORB orb = null;

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
	}
	
	@Override
	public void acceptReading(int reading) {
		System.out.println("Reading received" + reading);
	}
	
	@Override
	public void raiseAlarm(String zone, String raisingSensorName)
	{
		System.out.println(raisingSensorName + " just raised an alarm for zone " + zone);
		
		if( ! sensors.containsKey(zone)) {
			System.out.println("Invalid zone passed. Ignoring alarm");
			return;
		}
		
		/**
		 * For an alarm to be raised, all active sensors should be alarmed
		 */
		boolean floodConfirmed = true;
		
		for(Sensor sensor : sensors.get(zone)) {
			if ( sensor.active() && ! sensor.isFlooding())
			{
				floodConfirmed = false;
				break;
			}
		}
		
		if(floodConfirmed) {
			System.out.println("That alarm is confirmed, sending it to RMC");
		} else {
			System.out.println("That alarm is not confirmed. Logging, but not raising an alert");
		}
		
		System.out.println("------");
	}
	
	
	/** 
	 * Loop through the sensors and find the sensor by it's ID number
	 * 
	 * @param ID
	 */
	public Sensor findSensorByID(int ID)
	{
		Iterator<Entry<String, ArrayList<Sensor>>> it = sensors.entrySet().iterator();
		
		while(it.hasNext()) 
		{
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
			Sensor sensor = (Sensor) pair.getValue();
			
			if(sensor.id() == ID)
			{
				return sensor;
			}
		}
		
		return null;
	}
	
	/**
	 * Register this LMS with the RMC so it knows of its existence
	 */
	public void registerWithRMC()
	{
		// Register with the RMC
		if(this.rmc.registerLMS(this.location)) {
			System.out.println("Registered with RMC");
			return;
		}
		
		System.out.println("Failed to register with RMC. Probable name clash.");
		System.exit(1);
	}

	@Override
	public boolean registerSensor(String name) {
		// Get an instance of the Sensor
		NamingContextExt namingService = CorbaHelper.getNamingService(this._orb());
		Sensor sensor = null;
		
		// Get a reference to the LMS
	    try {
	    	sensor = SensorHelper.narrow(namingService.resolve_str(name));
		} catch (NotFound | CannotProceed | InvalidName e) {
			System.out.println("Failed to add Sensor named: " + name);
			
			return false;
		}
	    
	    if(sensor != null) {
	    	// Lets check this sensors zone already exists
		    if( ! this.sensors.containsKey(sensor.zone())) {
		    	// It doesn't, lets add this sensor
		    	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		    	
		    	// Put the array into the sensors hashmap
		    	this.sensors.put(sensor.zone(), sensors);
		    }
		    
		    // Check if the sensor zone already contains this sensor
		    if( ! this.sensors.get(sensor.zone()).contains(sensor)) {
		    	// Add the new sensor to the correct zone
		    	this.sensors.get(sensor.zone()).add(sensor);
		    	
		    	// A little feedback
		    	System.out.println(name + " has registered with the LMS in zone " + sensor.zone() + ". There are " + this.sensors.get(sensor.zone()).size() + " Sensors in this Zone.");
		    	
		    	return true;
		    }
	    } else {
	    	System.out.println("Returned Sensor was null");
	    }
	    
		// At this stage, the sensor must exist already
		return false;
	}
}
