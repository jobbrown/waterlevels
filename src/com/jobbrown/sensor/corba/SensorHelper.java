package com.jobbrown.sensor.corba;


/**
* com/jobbrown/sensor/corba/SensorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Thursday, April 2, 2015 3:22:40 PM BST
*/

abstract public class SensorHelper
{
  private static String  _id = "IDL:Sensor/Sensor:1.0";

  public static void insert (org.omg.CORBA.Any a, com.jobbrown.sensor.corba.Sensor that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.jobbrown.sensor.corba.Sensor extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (com.jobbrown.sensor.corba.SensorHelper.id (), "Sensor");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.jobbrown.sensor.corba.Sensor read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_SensorStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.jobbrown.sensor.corba.Sensor value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static com.jobbrown.sensor.corba.Sensor narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.jobbrown.sensor.corba.Sensor)
      return (com.jobbrown.sensor.corba.Sensor)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.jobbrown.sensor.corba._SensorStub stub = new com.jobbrown.sensor.corba._SensorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static com.jobbrown.sensor.corba.Sensor unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.jobbrown.sensor.corba.Sensor)
      return (com.jobbrown.sensor.corba.Sensor)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.jobbrown.sensor.corba._SensorStub stub = new com.jobbrown.sensor.corba._SensorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}