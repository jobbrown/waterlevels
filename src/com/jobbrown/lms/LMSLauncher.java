package com.jobbrown.lms;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.common.waterlevels.*;

public class LMSLauncher {
	private static ORB orb = null;
	private static RMC rmc = null;
	
	// Launch options
	private static String orbInitialPort = null;
	private static String location = null;
	
	public static void main(String[] args)
	{
		// create Options object
		Options options = new Options();
		
		// Add the options this class requires
		options.addOption("ORBInitialPort", true, "The initial port that ORB should use");
		options.addOption("location", true, "The string name of this location name");
		
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
			System.out.println("Error launcher: location flag is required");
		} else {
			// Initialize the ORB
			orb = ORB.init(args, null);
			
			// Set the options
			LMSLauncher.location = cmd.getOptionValue("location");
			LMSLauncher.orbInitialPort = cmd.getOptionValue("ORBInitialPort");
			
			// Run launch
			launch();
		}
	}
	
	public static void launch()
	{
		// Create RMC
		LMS lms = new LMS(getRMC(), LMSLauncher.location);
		
		// Register on NS
		try {
			// get reference to rootpoa & activate the POAManager
		    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		    rootpoa.the_POAManager().activate();
		    
		    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(lms);
		    com.jobbrown.common.waterlevels.LMS cref = LMSHelper.narrow(ref);
		    
		    NamingContextExt nameService = CorbaHelper.getNamingService(orb);
			
		    // Bind this object to the naming service
		    String name = LMSLauncher.location;
		    NameComponent[] bindName = nameService.to_name(name);
		    nameService.rebind(bindName, (Object) cref);
		    
		    //  wait for invocations from clients
		    lms.log("Launched LMS. Waiting ...");
		    
		    
			// orb.run();
		} catch (Exception e) {
			System.err.println("Caught error trying to launch RMC");
			e.printStackTrace();
		}
	    
		// Register with RMC
		lms.registerWithRMC();
		
		// This will need commenting out when we add the GUI
		orb.run();
	}
	
	public static RMC getRMC()
	{
		NamingContextExt namingService = CorbaHelper.getNamingService(LMSLauncher.orb);
		RMC rmc = null;
		
		// Get a reference to the LMS
	    try {
			rmc = RMCHelper.narrow(namingService.resolve_str("rmc"));
		} catch (NotFound | CannotProceed | InvalidName e) {
			System.out.print("Start RMC before starting any LMS instances");
			System.exit(1);
		}
	    
	    return rmc;
	}
}
