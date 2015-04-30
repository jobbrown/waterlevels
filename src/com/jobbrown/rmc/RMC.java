package com.jobbrown.rmc;

import java.util.ArrayList;
import java.util.HashMap;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.common.waterlevels.*;
import com.jobbrown.rmc.alerts.Alert;
import com.jobbrown.rmc.alerts.MobileAlert;

public class RMC extends RMCPOA {
	
	// A hashmap of LMS name (location) against an instance of that LMS
	public HashMap<String, LMS> stations = new HashMap<String, LMS>();
	
	// A hashmap of LMS name (location) against a list of users registered for alerts
	public HashMap<String, ArrayList<User>> users = new HashMap<String, ArrayList<User>>();
	
	// A list of raised alarms
	public ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	
	public RMCGUI gui = null;
	
	/**
	 * Updates the GUI with new values
	 */
	public void update()
	{
		this.gui.updateGUI();
	}
	
	public void addUser(String lms, String forename, String surname, String alertable, Alert alert)
	{
		// Check that an arraylist of users already exists for that LMS
		if( ! users.containsKey(lms)) {
			if ( stations.containsKey(lms)) {
				users.put(lms, new ArrayList<User>());
			} else {
				return;
			}	
		}
		
		// Create the user
		User user = new User(forename, surname, alertable, alert);
				
		// Add it to hashmap
		ArrayList<User> usrs = users.get(lms);
		usrs.add(user);
		
		users.put(lms, usrs);
		
		// A little feedback
		System.out.println("User has been added for notifications. " + users.get(lms).size() + " users are registered for notifications here.");
	}

	@Override
	public void raiseAlarm(int sensorID, String zone, String location, Reading reading) {
		Alarm alarm = new Alarm();
		
		alarm.sensorID = sensorID;
		alarm.zone = zone;
		alarm.reading = reading;
		alarm.lms = location;
		
		this.alarms.add(alarm);
		
		System.out.println("An alarm was raised .. Locating Users");
		
		/**
		 * If users have registered for alerts in this region. Register them.
		 */
		if(users.containsKey(location)) {
			ArrayList<User> usrs = users.get(location);
			
			System.out.println("There are " + usrs.size() + " users to inform.");
			
			for(User usr : usrs) {
				usr.alert("A flood warning has been generated in zone: " + zone);
			}
		} else {
			System.out.println("No users to notify");
		}
		
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

	
