package com.jobbrown.common.waterlevels;

/**
* com/jobbrown/common/waterlevels/AlarmHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from waterlevels.idl
* Tuesday, April 28, 2015 10:34:49 PM BST
*/

public final class AlarmHolder implements org.omg.CORBA.portable.Streamable
{
  public com.jobbrown.common.waterlevels.Alarm value = null;

  public AlarmHolder ()
  {
  }

  public AlarmHolder (com.jobbrown.common.waterlevels.Alarm initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.jobbrown.common.waterlevels.AlarmHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.jobbrown.common.waterlevels.AlarmHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.jobbrown.common.waterlevels.AlarmHelper.type ();
  }

}
