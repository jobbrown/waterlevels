package com.jobbrown.lms.corba;


/**
* com/jobbrown/lms/corba/ReadingLogHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Friday, April 24, 2015 2:03:54 PM BST
*/

public final class ReadingLogHolder implements org.omg.CORBA.portable.Streamable
{
  public com.jobbrown.lms.corba.Reading value[] = null;

  public ReadingLogHolder ()
  {
  }

  public ReadingLogHolder (com.jobbrown.lms.corba.Reading[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.jobbrown.lms.corba.ReadingLogHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.jobbrown.lms.corba.ReadingLogHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.jobbrown.lms.corba.ReadingLogHelper.type ();
  }

}