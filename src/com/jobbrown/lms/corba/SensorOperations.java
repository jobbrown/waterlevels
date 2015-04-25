package com.jobbrown.lms.corba;


/**
* com/jobbrown/lms/corba/SensorOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Saturday, April 25, 2015 9:11:47 PM BST
*/

public interface SensorOperations 
{
  boolean isFlooding ();
  boolean isActive ();
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
} // interface SensorOperations
