package com.jobbrown.sensor.corba;


/**
* com/jobbrown/sensor/corba/DateTime.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Monday, April 27, 2015 3:09:58 PM BST
*/

public final class DateTime implements org.omg.CORBA.portable.IDLEntity
{
  public int year = (int)0;
  public int month = (int)0;
  public int day = (int)0;
  public int hours = (int)0;
  public int minutes = (int)0;

  public DateTime ()
  {
  } // ctor

  public DateTime (int _year, int _month, int _day, int _hours, int _minutes)
  {
    year = _year;
    month = _month;
    day = _day;
    hours = _hours;
    minutes = _minutes;
  } // ctor

} // class DateTime
