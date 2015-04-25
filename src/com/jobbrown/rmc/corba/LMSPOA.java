package com.jobbrown.rmc.corba;


/**
* com/jobbrown/rmc/corba/LMSPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from lms.idl
* Saturday, April 25, 2015 9:11:47 PM BST
*/

public abstract class LMSPOA extends org.omg.PortableServer.Servant
 implements com.jobbrown.rmc.corba.LMSOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("acceptReading", new java.lang.Integer (0));
    _methods.put ("raiseAlarm", new java.lang.Integer (1));
    _methods.put ("getSensorName", new java.lang.Integer (2));
    _methods.put ("getSensorZone", new java.lang.Integer (3));
    _methods.put ("getSensorAlarmLevel", new java.lang.Integer (4));
    _methods.put ("getSensorActive", new java.lang.Integer (5));
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
       case 0:  // LMS/LMS/acceptReading
       {
         int reading = in.read_long ();
         this.acceptReading (reading);
         out = $rh.createReply();
         break;
       }

       case 1:  // LMS/LMS/raiseAlarm
       {
         String zone = in.read_string ();
         String raisingSensorName = in.read_string ();
         this.raiseAlarm (zone, raisingSensorName);
         out = $rh.createReply();
         break;
       }

       case 2:  // LMS/LMS/getSensorName
       {
         int ID = in.read_long ();
         String $result = null;
         $result = this.getSensorName (ID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // LMS/LMS/getSensorZone
       {
         int ID = in.read_long ();
         String $result = null;
         $result = this.getSensorZone (ID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // LMS/LMS/getSensorAlarmLevel
       {
         int ID = in.read_long ();
         int $result = (int)0;
         $result = this.getSensorAlarmLevel (ID);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 5:  // LMS/LMS/getSensorActive
       {
         int ID = in.read_long ();
         boolean $result = false;
         $result = this.getSensorActive (ID);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:LMS/LMS:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public LMS _this() 
  {
    return LMSHelper.narrow(
    super._this_object());
  }

  public LMS _this(org.omg.CORBA.ORB orb) 
  {
    return LMSHelper.narrow(
    super._this_object(orb));
  }


} // class LMSPOA