package com.jobbrown.rmc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.jobbrown.common.CorbaHelper;
import com.jobbrown.rmc.corba.RMCHelper;

public class RMCLauncher {
	private static ORB orb = null;
	
	public static void main(String[] args)
	{
		// create Options object
		Options options = new Options();
		
		// Add the options this class requires
		options.addOption("ORBInitialPort", true, "The initial port that ORB should use");
		
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse( options, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(!cmd.hasOption("ORBInitialPort")) {
			System.out.println("Error launching: ORBInitialPort flag is required");
		} else {
			// Initialize the ORB
			orb = ORB.init(args, null);
			
			// Run launch
			launch();
		}
	}
	
	public static void launch()
	{
		// Create RMC
		RMC rmc = new RMC();
		
		// Create GUI
		RMCGUI rGUI = new RMCGUI();
		
		// Pass some references around
		rmc.gui = rGUI;
		rGUI.model = rmc;
		
		// Preload everything
		rGUI.updateGUI();
		
		// Register on NS
		try {
			// get reference to rootpoa & activate the POAManager
		    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		    rootpoa.the_POAManager().activate();
		    
		    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(rmc);
		    com.jobbrown.rmc.corba.RMC cref = RMCHelper.narrow(ref);
		    
		    NamingContextExt nameService = CorbaHelper.getNamingService(orb);
			
		    // Bind this object to the naming service
		    String name = "rmc";
		    NameComponent[] bindName = nameService.to_name(name);
		    nameService.rebind(bindName, cref);
		    
		    //  wait for invocations from clients
		    System.out.println("Launched RMC - Waiting ...");
		    
		    // This will need commenting out when we add the GUI
			//orb.run();
		} catch (Exception e) {
			System.err.println("Caught error trying to launch RMC");
			e.printStackTrace();
		}
	}
}
