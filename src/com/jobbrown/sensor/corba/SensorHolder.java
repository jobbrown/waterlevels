package com.jobbrown.sensor.corba;

/**
* com/jobbrown/sensor/corba/SensorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Saturday, April 25, 2015 9:11:46 PM BST
*/

public final class SensorHolder implements org.omg.CORBA.portable.Streamable
{
  public com.jobbrown.sensor.corba.Sensor value = null;

  public SensorHolder ()
  {
  }

  public SensorHolder (com.jobbrown.sensor.corba.Sensor initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.jobbrown.sensor.corba.SensorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.jobbrown.sensor.corba.SensorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.jobbrown.sensor.corba.SensorHelper.type ();
  }

}
