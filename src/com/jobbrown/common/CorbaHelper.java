package com.jobbrown.common;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.jobbrown.lms.corba.LMSHelper;

public class CorbaHelper 
{
	public static NamingContextExt getNamingService(String[] args)
	{
		try {
			ORB orb = ORB.init(args, null);
		
			// Get a reference to the Naming service
		    org.omg.CORBA.Object nameServiceObj;
			
			nameServiceObj = orb.resolve_initial_references ("NameService");
	
		    if (nameServiceObj == null) {
		    	System.out.println("nameServiceObj = null");
		    	return null;
		    }
		    
		    // Use NamingContextExt instead of NamingContext. This is 
		    // part of the Interoperable naming Service.  
		    NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
		    if (nameService == null) {
		    	System.out.println("nameService = null");
		    	return null;
		    }
		    
		    return nameService;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
}
