package com.jobbrown.lms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.lms.corba.LMSHelper;
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

	
	public LMS(RMC rmc, String location)
	{
		// Store the location
		this.location = location;
		
		// Store the RMC
		this.rmc = rmc;
	}
	
	@Override
	public void acceptReading(int reading) {
		System.out.println("Reading received" + reading);
	}
	
	@Override
	public void raiseAlarm(String zone, String raisingSensorName)
	{
		System.out.println(raisingSensorName + " just raised an alarm for zone " + zone);
		
		/**
		 * For an alarm to be raised, all active sensors should be alarmed
		 */
		boolean floodConfirmed = true;
		
		// Loop through each alarm, check its active, check its flooding
		
		if(floodConfirmed) {
			System.out.println("That alarm is confirmed, sending it to RMC");
		} else {
			System.out.println("That alarm is not confirmed. Logging, but not raising an alert");
		}
		
		System.out.println("------");
	}
	
	
	@Override
	public String getSensorName(int ID) {
		return "sensor" + ID;
	}

	@Override
	public String getSensorZone(int ID)
	{
		return "zone1";
	}

	@Override
	public int getSensorAlarmLevel(int ID)
	{
		return 70;
	}

	@Override
	public boolean getSensorActive(int ID) 
	{
		return true;
	}
	
	public void registerSensor(int ID, String zone)
	{
		
	}
	
	/** 
	 * Loop through the sensors and find the sensor by it's ID number
	 * 
	 * @param ID
	 */
	public Sensor findSensorByID(int ID)
	{
		Iterator it = sensors.entrySet().iterator();
		
		while(it.hasNext()) 
		{
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
	 * Register this LMS with the RMC so it knows of its existance
	 */
	public void registerWithRMC()
	{
		// Register with the RMC
		this.rmc.registerLMS(this.location);
		
		// A little feedback
		System.out.println("Registered with RMC");
	}
}
