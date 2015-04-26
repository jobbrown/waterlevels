package com.jobbrown.lms.corba;

/**
* com/jobbrown/lms/corba/SensorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Sunday, April 26, 2015 4:50:15 PM BST
*/

public final class SensorHolder implements org.omg.CORBA.portable.Streamable
{
  public com.jobbrown.lms.corba.Sensor value = null;

  public SensorHolder ()
  {
  }

  public SensorHolder (com.jobbrown.lms.corba.Sensor initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.jobbrown.lms.corba.SensorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.jobbrown.lms.corba.SensorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.jobbrown.lms.corba.SensorHelper.type ();
  }

}
