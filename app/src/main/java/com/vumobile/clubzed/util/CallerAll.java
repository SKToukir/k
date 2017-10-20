package com.vumobile.clubzed.util;
import android.app.AlertDialog;
import android.util.Log;

import com.vumobile.clubzed.GCMIntentService;
import com.vumobile.clubzed.Soap.CallSoap;


public class CallerAll extends Thread
{

	public AlertDialog ad;
	public CallSoap cs;
	public String a,b;
	public String pin;

	public void run()
	{
		try
		{
            Log.d("Tracker", "This is CallerAll");
			cs=new CallSoap();	
			String resp=cs.Call_WS(a, b,pin);
			Download_Class.rslt=resp;
			Subscriptio_Class.rslt=resp;
			PushActivity.resultMno=resp;
			Log.i("MNO","" + resp);
			GCMIntentService.resultMno=resp;
		}catch(Exception ex)
		{
			String errMsg = "Error";
			Download_Class.rslt=errMsg.toString();
			Subscriptio_Class.rslt=errMsg.toString();
			PushActivity.resultMno=errMsg.toString();;
			Log.i("MNO","" + errMsg.toString());
			GCMIntentService.resultMno=errMsg.toString();;
		}

	
    }
}