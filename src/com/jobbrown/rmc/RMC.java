package com.jobbrown.rmc;

import java.util.ArrayList;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.rmc.corba.LMSHelper;
import com.jobbrown.rmc.corba.LMS;
import com.jobbrown.rmc.corba.RMCPOA;

public class RMC extends RMCPOA {
	
	private ArrayList<String> stationNames = new ArrayList<String>();
	private ArrayList<LMS> stations = new ArrayList<LMS>();
	
	@Override
	public void registerLMS(String name) {
		if(stationNames.contains(name)) {
			System.out.println("An LMS with that location name is already registered.");
			return;
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
	    stationNames.add(name);
		stations.add(lms);
		
		// Give some feedback
		System.out.println(name + " has registered with the RMC. There are " + stations.size() + " LMS's connected");
	}
}

	
