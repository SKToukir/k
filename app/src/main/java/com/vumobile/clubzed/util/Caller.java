package com.vumobile.clubzed.util;


import android.app.AlertDialog;
import android.util.Log;

import com.vumobile.clubzed.GCMIntentService;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.MyDownLoadsActivity;
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.Soap.CallSoap;
import com.vumobile.clubzed.SongRelated.PlaySongActivity;
import com.vumobile.clubzed.VideoRelated.VideoPreViewActivity;

public class Caller extends Thread
{

	public AlertDialog ad;
	public CallSoap cs;
	public int a,b;

	public void run()
	{
		try
		{
			Log.d("Tracker", "This is Caller");
			cs=new CallSoap();
			String resp=cs.Call();
			Subscriptio_Class.rsltNumber=resp;
			Download_Class.rsltNumber=resp;
			MainActivity.rsltNumber = resp;
			PushActivity.resultMno=resp;
			PlaySongActivity.rsltNumber=resp;
			PictureDetailsActivity.rsltNumber=resp;
			VideoPreViewActivity.rsltNumber=resp;
			MyDownLoadsActivity.rsltNumber = resp;
			GCMIntentService.resultMno = resp;



		}catch(Exception ex)
		{
			String errMsg = "Error";
			MainActivity.rsltNumber=errMsg.toString();
			Download_Class.rsltNumber = errMsg.toString();
			PushActivity.resultMno=errMsg.toString();
			GCMIntentService.resultMno = errMsg.toString();
			MyDownLoadsActivity.rsltNumber = errMsg.toString();
		}


	}
}