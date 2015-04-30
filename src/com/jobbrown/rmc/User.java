package com.jobbrown.rmc;

import com.jobbrown.rmc.alerts.Alert;

public class User {
	public String forename;
	public String surname;
	public String alertable;
	public Alert alert;
	
	/**
	 * Just for good measure
	 */
	public User()
	{	
	}
	
	/**
	 * Full constructor
	 * @param forename
	 * @param surname
	 * @param alertable
	 * @param alert
	 */
	public User(String forename, String surname, String alertable, Alert alert) {
		this.forename = forename;
		this.surname = surname;
		this.alertable = alertable;
		this.alert = alert;
	}
	
	/**
	 * Send an alert to the user
	 * @param message The message to send
	 */
	public void alert(String message)
	{
		System.out.println("Alerting the user via: " + this.alert);
		
		this.alert.alert(this.alertable, message);
	}
}
