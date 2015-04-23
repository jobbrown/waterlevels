package com.jobbrown.sensor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
import com.jobbrown.sensor.corba.LMS;
import com.jobbrown.sensor.corba.LMSHelper;
import com.jobbrown.sensor.corba.SensorHelper;

@SuppressWarnings("serial")
public class SensorGUI extends JFrame {
	/**
	 * The Sensor model that this gui is representing
	 */
	private Sensor model;
	
	private JSlider slider;
	private JLabel lblSensor, lblSensorID;
	private Timer slideTimer;
	
	
	
	public static void main(final String args[]) 
	{
		/**
		 * Register on the name service
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				// Make the GUI
				SensorGUI gui = new SensorGUI(args);
				
				// Make the model
				gui.loadModel(args);
				
				// Set any options
				gui.setSize(200,300);
				
				// Finally make it visible
				gui.setVisible(true);
			}
	    });
	}

	protected void loadModel(String[] args) {
		model = new Sensor(args);
		model.gui = this;
		
		registerWithNameService(args, model);
	}

	public static void registerWithNameService(String[] args, Sensor sensor)
	{
		try {
			
			// Initialize the ORB
		    ORB orb = ORB.init(args, null);
		    
		    // get reference to rootpoa & activate the POAManager
		    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		    rootpoa.the_POAManager().activate();
		    
		    org.omg.CORBA.Object ref = rootpoa.servant_to_reference(sensor);
		    com.jobbrown.sensor.corba.Sensor cref = SensorHelper.narrow(ref);
		    
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
		    String name = "sensor" + sensor.ID;
		    NameComponent[] bindName = nameService.to_name(name);
		    nameService.rebind(bindName, cref);
		    
		    //  wait for invocations from clients
		    //  orb.run();
			
		} catch (Exception e) {
			System.out.println("Caught exception trying to register Sensor with NS");
			e.printStackTrace();
		}
	}
	/**
	 * Create the panel.
	 */
	public SensorGUI(String[] args) 
	{		
		setResizable(true);
		getContentPane().setLayout(null);
		
		slider = new JSlider();
		
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setBounds(6, 44, 122, 234);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(25);
		
		
		// Creat the "Sensor ID" label
		lblSensor = new JLabel("Sensor ID: ");
		lblSensor.setBounds(6, 6, 77, 16);
		
		// Create the Sensor ID Label
		lblSensorID = new JLabel("");
		lblSensorID.setBounds(73, 6, 61, 16);
		
		
		// Add it all to the pane
		getContentPane().add(lblSensor);
		getContentPane().add(lblSensorID);
		getContentPane().add(slider);
		
		setTitle("Sensor");
		
		// Close listener
        addWindowListener(new java.awt.event.WindowAdapter () {
            public void windowClosing (java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });
        
        // Add a change listener for the water level
        slider.addChangeListener(new ChangeListener() {
        	
	        @Override
	        public void stateChanged(ChangeEvent arg0) {
	            slideTimer.restart();
	        }
	    });
        
	    slideTimer = new Timer(500, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	model.setWaterLevel(slider.getValue());	
	        }
	    });
	    slideTimer.setRepeats(false);

	}
	
	public void updateGUI()
	{
		slider.setValue(model.getWaterLevel());
		lblSensorID.setText(model.ID + "");
	}
}
