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

@SuppressWarnings("serial")
public class SensorGUI extends JFrame {
	/**
	 * The Sensor model that this gui is representing
	 */
	public Sensor model;
	
	private JSlider slider;
	private JLabel lblSensor, lblSensorID;
	private Timer slideTimer;
	
	private static ORB orb = null;
	
	public static void main(final String args[]) 
	{
		System.out.print("This file should not be launched directly. Please use SensorLauncher.");
	}

	/**
	 * Create the panel.
	 */
	public SensorGUI() 
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
	        	model.waterLevel(slider.getValue());	
	        }
	    });
	    slideTimer.setRepeats(false);
	    
	    // Launch it
	    setSize(200,300);
	    setVisible(true);
	    
	}
	
	public void updateGUI()
	{
		slider.setValue(model.waterLevel());
		lblSensorID.setText(model.ID + "");
	}
}
