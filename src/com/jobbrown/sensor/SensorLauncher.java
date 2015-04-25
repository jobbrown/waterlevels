package com.jobbrown.sensor;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.lms.LMSLauncher;
import com.jobbrown.lms.corba.RMC;
import com.jobbrown.lms.corba.RMCHelper;
import com.jobbrown.sensor.corba.LMS;
import com.jobbrown.sensor.corba.LMSHelper;
import com.jobbrown.sensor.corba.SensorHelper;

public class SensorLauncher {
	private static ORB orb = null;
	
	// Setup values
	private static String orbInitialPort;
	private static String location;
	private static String zone;
	private static int id;
	
	public static void main(String[] args)
	{
		// create Options object
		Options options = new Options();
		
		// Add the options this class requires
		options.addOption("ORBInitialPort", true, "The initial port that ORB should use");
		options.addOption("location", true, "The string name of this location name");
		options.addOption("zone", true, "The string name of the zone this sensor should be placed in");
		options.addOption("id", true, "The ID number of this sensor");
		
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse( options, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(!cmd.hasOption("ORBInitialPort")) {
			System.out.println("Error launching: ORBInitialPort flag is required");
		} else if(!cmd.hasOption("location")) {
			System.out.println("Error launching: location flag is required");
		} else if(!cmd.hasOption("zone")) {
			System.out.println("Error launching: zone flag is required");
		} else if(!cmd.hasOption("id")) {
			System.out.println("Error launching: id flag is required");
		} else {
			// Initialize the ORB
			orb = ORB.init(args, null);
			
			// Set the options
			SensorLauncher.location = cmd.getOptionValue("location");
			SensorLauncher.orbInitialPort = cmd.getOptionValue("ORBInitialPort");
			SensorLauncher.zone = cmd.getOptionValue("zone");
			SensorLauncher.id = Integer.parseInt(cmd.getOptionValue("id"));
			
			// Run launch
			launch();
		}
	}
	
	public static void launch()
	{
		// Create the Sensor
		Sensor sensor = new Sensor(getLMS(), SensorLauncher.id);
		
		System.out.println("Creating the GUI");
		
		// Create the Sensor GUI
		SensorGUI sGUI = new SensorGUI();
		
		// Give the Sensor some settings
		sensor.gui = sGUI;
		
		// Register the Sensor on the NS
		try {
			// get reference to rootpoa & activate the POAManager
		    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		    rootpoa.the_POAManager().activate();
		    
		    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(sensor);
		    com.jobbrown.sensor.corba.Sensor cref = SensorHelper.narrow(ref);
		    
		    NamingContextExt nameService = CorbaHelper.getNamingService(SensorLauncher.orb);
			
		    // Bind this object to the naming service
		    NameComponent[] bindName = nameService.to_name("Sensor" + SensorLauncher.id);
		    nameService.rebind(bindName, cref);
		    
		    // Output a message
		    System.out.println("Registered on naming service with name: " + "Sensor" + SensorLauncher.id);
		} catch (Exception e) {
			System.err.println("Caught error trying to launch Sensor");
			e.printStackTrace();
		}

		// Register the Sensor with the LMS
		
		
	}
	
	public static LMS getLMS()
	{
		NamingContextExt namingService = CorbaHelper.getNamingService(SensorLauncher.orb);
		LMS lms = null;
		
		// Get a reference to the LMS
	    try {
			lms = LMSHelper.narrow(namingService.resolve_str(SensorLauncher.location));
		} catch (NotFound | CannotProceed | InvalidName e) {			
			System.out.println("Start an LMS with location: " + SensorLauncher.location + " before trying to link Sensors to it");
			System.exit(1);
		}
	    
	    return lms;
	}
}
