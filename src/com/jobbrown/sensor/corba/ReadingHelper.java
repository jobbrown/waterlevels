package com.jobbrown.sensor.corba;


/**
* com/jobbrown/sensor/corba/ReadingHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Monday, April 27, 2015 3:56:45 PM BST
*/

abstract public class ReadingHelper
{
  private static String  _id = "IDL:Sensor/Reading:1.0";

  public static void insert (org.omg.CORBA.Any a, com.jobbrown.sensor.corba.Reading that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.jobbrown.sensor.corba.Reading extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "date",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[1] = new org.omg.CORBA.StructMember (
            "waterLevel",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "alarmLevel",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.jobbrown.sensor.corba.ReadingHelper.id (), "Reading", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.jobbrown.sensor.corba.Reading read (org.omg.CORBA.portable.InputStream istream)
  {
    com.jobbrown.sensor.corba.Reading value = new com.jobbrown.sensor.corba.Reading ();
    value.date = istream.read_string ();
    value.waterLevel = istream.read_long ();
    value.alarmLevel = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.jobbrown.sensor.corba.Reading value)
  {
    ostream.write_string (value.date);
    ostream.write_long (value.waterLevel);
    ostream.write_long (value.alarmLevel);
  }

}
