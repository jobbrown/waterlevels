package com.jobbrown.lms.corba;

/**
* com/jobbrown/lms/corba/LMSHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lms.idl
* Thursday, April 23, 2015 4:35:21 PM BST
*/

public final class LMSHolder implements org.omg.CORBA.portable.Streamable
{
  public com.jobbrown.lms.corba.LMS value = null;

  public LMSHolder ()
  {
  }

  public LMSHolder (com.jobbrown.lms.corba.LMS initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.jobbrown.lms.corba.LMSHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.jobbrown.lms.corba.LMSHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.jobbrown.lms.corba.LMSHelper.type ();
  }

}
