package com.jobbrown.lms.corba;


/**
* com/jobbrown/lms/corba/DateTime.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lms.idl
* Sunday, April 26, 2015 4:50:15 PM BST
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