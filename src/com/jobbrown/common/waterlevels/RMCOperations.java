package com.jobbrown.common.waterlevels;


/**
* com/jobbrown/common/waterlevels/RMCOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from waterlevels.idl
* Tuesday, April 28, 2015 10:34:49 PM BST
*/

public interface RMCOperations 
{
  boolean registerLMS (String name);
  void raiseAlarm (int sensorID, String zone, String lms, com.jobbrown.common.waterlevels.Reading reading);
} // interface RMCOperations
