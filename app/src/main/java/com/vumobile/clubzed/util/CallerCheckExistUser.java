package com.vumobile.clubzed.util;

import android.app.AlertDialog;
import android.util.Log;

import com.vumobile.clubzed.Soap.CallSoap;

public class CallerCheckExistUser extends Thread {
	
	public AlertDialog ad;
	public AlertDialog ad1;
	public CallSoap cs1;
	public String a,b;

	public void run()
	{
		try
		{
            Log.d("Tracker", "This is This is caller check ExistUser");
			cs1=new CallSoap();	
	        Log.i("CHGECK_NO","" + a);
			String resp1=cs1.Call(a); 	//Checking user is new or old
			Download_Class.rslt=resp1;
			Subscriptio_Class.rslt=resp1;
			
		}catch(Exception ex)
		{
			String errMsg = "Error";
			Download_Class.rslt=errMsg.toString();
			Subscriptio_Class.rslt=errMsg.toString();
			//ClubzApp.rslt=ex.toString();	
		}
    }
}
