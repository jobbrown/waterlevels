package com.jobbrown.lms.corba;


/**
* com/jobbrown/lms/corba/ReadingLogHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Friday, April 24, 2015 2:03:54 PM BST
*/

abstract public class ReadingLogHelper
{
  private static String  _id = "IDL:Sensor/ReadingLog:1.0";

  public static void insert (org.omg.CORBA.Any a, com.jobbrown.lms.corba.Reading[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.jobbrown.lms.corba.Reading[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = com.jobbrown.lms.corba.ReadingHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (com.jobbrown.lms.corba.ReadingLogHelper.id (), "ReadingLog", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.jobbrown.lms.corba.Reading[] read (org.omg.CORBA.portable.InputStream istream)
  {
    com.jobbrown.lms.corba.Reading value[] = null;
    int _len0 = istream.read_long ();
    value = new com.jobbrown.lms.corba.Reading[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = com.jobbrown.lms.corba.ReadingHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.jobbrown.lms.corba.Reading[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      com.jobbrown.lms.corba.ReadingHelper.write (ostream, value[_i0]);
  }

}
