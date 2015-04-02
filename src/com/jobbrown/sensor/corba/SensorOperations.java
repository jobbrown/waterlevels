package com.jobbrown.sensor.corba;


/**
* com/jobbrown/sensor/corba/SensorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Thursday, April 2, 2015 3:22:40 PM BST
*/

public interface SensorOperations 
{
  boolean isFlooding ();
  boolean isActive ();
  int getWaterLevel ();
  int getAlarmLevel ();
  void setAlarmLevel (int waterLevel);
  void setActive (boolean active);
} // interface SensorOperations
