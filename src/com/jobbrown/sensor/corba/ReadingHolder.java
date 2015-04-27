package com.jobbrown.sensor.corba;

/**
* com/jobbrown/sensor/corba/ReadingHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Monday, April 27, 2015 3:56:45 PM BST
*/

public final class ReadingHolder implements org.omg.CORBA.portable.Streamable
{
  public com.jobbrown.sensor.corba.Reading value = null;

  public ReadingHolder ()
  {
  }

  public ReadingHolder (com.jobbrown.sensor.corba.Reading initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.jobbrown.sensor.corba.ReadingHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.jobbrown.sensor.corba.ReadingHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.jobbrown.sensor.corba.ReadingHelper.type ();
  }

}
