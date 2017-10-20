package com.vumobile.clubzed.util;
import android.app.AlertDialog;
import android.util.Log;

import com.vumobile.clubzed.Soap.CallSoap;


public class CallRobi extends Thread
{

	public AlertDialog ad;
	public CallSoap cs;
	public String msisdn,query;
	public String chargeType;

	public void run()
	{
		try
		{
            Log.d("Tracker", "This is Caller Robi");
			cs=new CallSoap();	
			String resp=cs.Call_ROBI(msisdn,chargeType, query);
			Download_Class.rslt=resp;
			Subscriptio_Class.rslt=resp;
		}catch(Exception ex)
		{
			String errMsg = "Error";
			Download_Class.rslt=errMsg.toString();
			Subscriptio_Class.rslt=errMsg.toString();
		}

	
    }
}