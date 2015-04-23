package com.jobbrown.lms.corba;


/**
* com/jobbrown/lms/corba/SensorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Thursday, April 23, 2015 4:35:21 PM BST
*/

abstract public class SensorHelper
{
  private static String  _id = "IDL:Sensor/Sensor:1.0";

  public static void insert (org.omg.CORBA.Any a, com.jobbrown.lms.corba.Sensor that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.jobbrown.lms.corba.Sensor extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (com.jobbrown.lms.corba.SensorHelper.id (), "Sensor");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.jobbrown.lms.corba.Sensor read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_SensorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.jobbrown.lms.corba.Sensor value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static com.jobbrown.lms.corba.Sensor narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.jobbrown.lms.corba.Sensor)
      return (com.jobbrown.lms.corba.Sensor)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.jobbrown.lms.corba._SensorStub stub = new com.jobbrown.lms.corba._SensorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static com.jobbrown.lms.corba.Sensor unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.jobbrown.lms.corba.Sensor)
      return (com.jobbrown.lms.corba.Sensor)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.jobbrown.lms.corba._SensorStub stub = new com.jobbrown.lms.corba._SensorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
