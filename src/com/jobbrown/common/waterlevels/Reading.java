package com.jobbrown.common.waterlevels;


/**
* com/jobbrown/common/waterlevels/Reading.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from waterlevels.idl
* Tuesday, April 28, 2015 7:35:32 PM BST
*/

public final class Reading implements org.omg.CORBA.portable.IDLEntity
{
  public String date = null;
  public int waterLevel = (int)0;
  public int alarmLevel = (int)0;

  public Reading ()
  {
  } // ctor

  public Reading (String _date, int _waterLevel, int _alarmLevel)
  {
    date = _date;
    waterLevel = _waterLevel;
    alarmLevel = _alarmLevel;
  } // ctor

} // class Reading