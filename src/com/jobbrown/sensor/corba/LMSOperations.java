package com.jobbrown.sensor.corba;


/**
* com/jobbrown/sensor/corba/LMSOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lms.idl
* Sunday, April 26, 2015 4:50:15 PM BST
*/

public interface LMSOperations 
{
  boolean registerSensor (String name);
  void raiseAlarm (String zone, int raisingSensorID);
} // interface LMSOperations
