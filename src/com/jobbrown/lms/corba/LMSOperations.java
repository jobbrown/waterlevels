package com.jobbrown.lms.corba;


/**
* com/jobbrown/lms/corba/LMSOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lms.idl
* Thursday, April 23, 2015 4:35:21 PM BST
*/

public interface LMSOperations 
{
  void acceptReading (int reading);
  void raiseAlarm (String zone, String raisingSensorName);
  String getSensorName (int ID);
  String getSensorZone (int ID);
  int getSensorAlarmLevel (int ID);
  boolean getSensorActive (int ID);
} // interface LMSOperations
