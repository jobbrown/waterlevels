package com.jobbrown.sensor.corba;


/**
* com/jobbrown/sensor/corba/SensorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Monday, April 27, 2015 3:56:45 PM BST
*/

public interface SensorOperations 
{
  String[] getLog ();
  com.jobbrown.sensor.corba.Reading currentReading ();
  boolean isFlooding ();
  void checkAlarmStatus ();
  int id ();
  void id (int newId);
  String name ();
  void name (String newName);
  String zone ();
  void zone (String newZone);
  int waterLevel ();
  void waterLevel (int newWaterLevel);
  int alarmLevel ();
  void alarmLevel (int newAlarmLevel);
  boolean active ();
  void active (boolean newActive);
  com.jobbrown.sensor.corba.Reading[] readings ();
  void readings (com.jobbrown.sensor.corba.Reading[] newReadings);
} // interface SensorOperations
