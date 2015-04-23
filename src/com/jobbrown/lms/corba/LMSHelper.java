package com.jobbrown.lms.corba;


/**
* com/jobbrown/lms/corba/LMSHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lms.idl
* Thursday, April 23, 2015 4:35:21 PM BST
*/

abstract public class LMSHelper
{
  private static String  _id = "IDL:LMS/LMS:1.0";

  public static void insert (org.omg.CORBA.Any a, com.jobbrown.lms.corba.LMS that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.jobbrown.lms.corba.LMS extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (com.jobbrown.lms.corba.LMSHelper.id (), "LMS");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.jobbrown.lms.corba.LMS read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_LMSStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.jobbrown.lms.corba.LMS value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static com.jobbrown.lms.corba.LMS narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.jobbrown.lms.corba.LMS)
      return (com.jobbrown.lms.corba.LMS)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.jobbrown.lms.corba._LMSStub stub = new com.jobbrown.lms.corba._LMSStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static com.jobbrown.lms.corba.LMS unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.jobbrown.lms.corba.LMS)
      return (com.jobbrown.lms.corba.LMS)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.jobbrown.lms.corba._LMSStub stub = new com.jobbrown.lms.corba._LMSStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
