package com.jobbrown.lms;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.jobbrown.lms.corba.Sensor;
import com.jobbrown.lms.corba.SensorHelper;

public class LMS extends LMSPOA 
{
	private HashMap<String, ArrayList<Sensor>> sensors;
	private HashMap<String, ArrayList<String>> sensorZones;
	private static String[] arg = null;
	
	public static void main(String[] args)
	{
		arg = args;
		try {
			// Initialize the ORB
		    ORB orb = ORB.init(args, null);
		    
		    // get reference to rootpoa & activate the POAManager
		    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		    rootpoa.the_POAManager().activate();
		    
		    LMS lms = new LMS();
		    
		    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(lms);
		    com.jobbrown.lms.corba.LMS cref = LMSHelper.narrow(ref);
		    
		    // Get a reference to the Naming service
		    org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
		    if (nameServiceObj == null) {
		    	System.out.println("nameServiceObj = null");
		    	return;
		    }
		    
		    // Use NamingContextExt which is part of the Interoperable
		    // Naming Service (INS) specification.
		    NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
		    if (nameService == null) {
				System.out.println("nameService = null");
				return;
		    }
		    
		    // Bind this object to the naming service
		    String name = "lms";
		    NameComponent[] lmsName = nameService.to_name(name);
		    nameService.rebind(lmsName, cref);
		    
		    System.out.println("Waiting .... ");
		    
		    //  wait for invocations from clients
		    orb.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public LMS()
	{
		sensorZones = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> sensors = new ArrayList<String>();
		sensors.add("sensor1");
		sensors.add("sensor2");
		
		sensorZones.put("zone1", sensors);
		
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
		
		for(String sensorName : sensorZones.get(zone) ) {
			Sensor sensor = getSensor(sensorName);
			
			if(sensor != null) {
				if(sensor.isActive()) {
					if(!sensor.isFlooding()) {
						floodConfirmed = false;
						break;
					}
				}
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
	 * Get a reference to a sensor
	 * @param name
	 * @return
	 */
	private Sensor getSensor(String name)
	{
		NamingContextExt namingService = CorbaHelper.getNamingService(arg);
		
		Sensor sensor = null;
		try {
			sensor = SensorHelper.narrow(namingService.resolve_str(name));
		} catch (Exception e) {
			System.out.println("Failed to load sensor " + name);
			e.printStackTrace();
		}
		
		return sensor;
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
}
