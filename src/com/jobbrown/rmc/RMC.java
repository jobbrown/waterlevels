package com.jobbrown.rmc;

import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.common.waterlevels.*;

public class RMC extends RMCPOA {
	
	public HashMap<String, LMS> stations = new HashMap<String, LMS>();
	public ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	
	public RMCGUI gui = null;
	
	/**
	 * Updates the GUI with new values
	 */
	public void update()
	{
		this.gui.updateGUI();
	}

	@Override
	public void raiseAlarm(int sensorID, String zone, String location, Reading reading) {
		Alarm alarm = new Alarm();
		
		alarm.sensorID = sensorID;
		alarm.zone = zone;
		alarm.reading = reading;
		alarm.lms = location;
		
		this.alarms.add(alarm);
		
		System.out.println("An alarm was raised");
		
		this.update();
	}
	
	@Override
	public boolean registerLMS(String name) {
		if(stations.containsKey(name)) {
			System.out.println("A sensor failed to register with name \"" + name + "\" due to a name clash.");
			return false;
		}
		
		// Get instance of LMS 
		NamingContextExt namingService = CorbaHelper.getNamingService(this._orb());
		LMS lms = null;
		
		// Get a reference to the LMS
	    try {
			lms = LMSHelper.narrow(namingService.resolve_str(name));
		} catch (NotFound | CannotProceed | InvalidName e) {
			System.out.println("Failed to add LMS named: " + name);
		}
		
		// Add it to the Array
		stations.put(name, lms);
		
		// Give some feedback
		System.out.println(name + " has registered with the RMC. There are " + stations.size() + " LMS's connected");
		
		// Update everything
		this.update();
		
		return true;
	}
}

	
