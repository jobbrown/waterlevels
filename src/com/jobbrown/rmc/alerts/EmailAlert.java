package com.jobbrown.rmc.alerts;

public class EmailAlert implements Alert {

	@Override
	public void alert(String alertable, String message) {
		System.out.println("Sending \"" + message + "\" to " + alertable + " (SIMULATION)");
	}

	/**
	 * This should be replaced by ensuring the e-mail address is valid
	 */
	@Override
	public boolean validates(String alertable) {
		return true;
	}

}
