package com.jobbrown.lms.corba;


/**
* com/jobbrown/lms/corba/SensorPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from sensor.idl
* Thursday, April 23, 2015 4:35:21 PM BST
*/

public abstract class SensorPOA extends org.omg.PortableServer.Servant
 implements com.jobbrown.lms.corba.SensorOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("isFlooding", new java.lang.Integer (0));
    _methods.put ("isActive", new java.lang.Integer (1));
    _methods.put ("getWaterLevel", new java.lang.Integer (2));
    _methods.put ("getAlarmLevel", new java.lang.Integer (3));
    _methods.put ("setAlarmLevel", new java.lang.Integer (4));
    _methods.put ("setWaterLevel", new java.lang.Integer (5));
    _methods.put ("setActive", new java.lang.Integer (6));
    _methods.put ("checkAlarmStatus", new java.lang.Integer (7));
    _methods.put ("getLog", new java.lang.Integer (8));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Sensor/Sensor/isFlooding
       {
         boolean $result = false;
         $result = this.isFlooding ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // Sensor/Sensor/isActive
       {
         boolean $result = false;
         $result = this.isActive ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // Sensor/Sensor/getWaterLevel
       {
         int $result = (int)0;
         $result = this.getWaterLevel ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 3:  // Sensor/Sensor/getAlarmLevel
       {
         int $result = (int)0;
         $result = this.getAlarmLevel ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 4:  // Sensor/Sensor/setAlarmLevel
       {
         int alarmLevel = in.read_long ();
         this.setAlarmLevel (alarmLevel);
         out = $rh.createReply();
         break;
       }

       case 5:  // Sensor/Sensor/setWaterLevel
       {
         int waterLevel = in.read_long ();
         this.setWaterLevel (waterLevel);
         out = $rh.createReply();
         break;
       }

       case 6:  // Sensor/Sensor/setActive
       {
         boolean active = in.read_boolean ();
         this.setActive (active);
         out = $rh.createReply();
         break;
       }

       case 7:  // Sensor/Sensor/checkAlarmStatus
       {
         this.checkAlarmStatus ();
         out = $rh.createReply();
         break;
       }

       case 8:  // Sensor/Sensor/getLog
       {
         com.jobbrown.lms.corba.Reading $result[] = null;
         $result = this.getLog ();
         out = $rh.createReply();
         com.jobbrown.lms.corba.ReadingLogHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Sensor/Sensor:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Sensor _this() 
  {
    return SensorHelper.narrow(
    super._this_object());
  }

  public Sensor _this(org.omg.CORBA.ORB orb) 
  {
    return SensorHelper.narrow(
    super._this_object(orb));
  }


} // class SensorPOA
