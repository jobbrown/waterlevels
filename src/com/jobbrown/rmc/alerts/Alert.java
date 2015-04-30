package com.jobbrown.rmc.alerts;

public interface Alert {
	public void alert(String alertable, String message);
	public boolean validates(String alertable);
}
