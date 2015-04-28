package com.jobbrown.common.waterlevels;

/**
* com/jobbrown/common/waterlevels/SensorHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from waterlevels.idl
* Tuesday, April 28, 2015 7:35:32 PM BST
*/

public final class SensorHolder implements org.omg.CORBA.portable.Streamable
{
  public com.jobbrown.common.waterlevels.Sensor value = null;

  public SensorHolder ()
  {
  }

  public SensorHolder (com.jobbrown.common.waterlevels.Sensor initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.jobbrown.common.waterlevels.SensorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.jobbrown.common.waterlevels.SensorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.jobbrown.common.waterlevels.SensorHelper.type ();
  }

}
