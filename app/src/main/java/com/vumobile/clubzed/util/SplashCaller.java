package com.vumobile.clubzed.util;
import android.util.Log;

import com.vumobile.clubzed.GCMIntentService;
import com.vumobile.clubzed.Notification.NetworkedService;
import com.vumobile.clubzed.Soap.CallSoap;

public class SplashCaller extends Thread
{

	//public AlertDialog ad;
	public CallSoap cs;
	public int a,b;

	public void run()
	{
		try
		{
			Log.d("Tracker", "This is SplashCaller");
			cs = new CallSoap();
			//For geting Mobile Number or MSISDN Number
			String resp = cs.Call();
			SplashActivity.resultMno_splash = resp;
			PushActivity.resultMno=resp;
			NetworkedService.resultMno = resp;
			Log.i("MNO","" + resp);
			GCMIntentService.resultMno=resp;
		}catch(Exception ex)
		{
			String errMsg = "Error";
			//ClubzAppExtend.rsltNumber=errMsg.toString();
			NetworkedService.resultMno = errMsg.toString();
		}


	}
}