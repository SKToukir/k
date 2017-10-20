package com.vumobile.clubzed.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.vumobile.clubzed.AppConstant;
import com.vumobile.clubzed.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class PushActivity extends Activity {
	Context context;
	TextView push_text;
	public String pushResponseUrl = "http://www.vumobile.biz/gcm_server_php/push_response.php";
	public static String resultMno = null;
	PHPRequest phpRequest = new PHPRequest();
	Button closeButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.push_activity);
        Log.d("Tracker", "This is PushActivity");

		try {
			Thread.sleep(1000);
			resultMno = "START";
			SplashCaller cws = new SplashCaller();
			cws.join();
			cws.start();

			while (resultMno == "START") {
				try {
					Thread.sleep(100);
				} catch (Exception ex) {
				}
			}
			Log.i("MSISDN ", "" + resultMno);
			
		} catch (Exception ex) {
			Log.i("EXCEPTION EXIST USER", "" + ex.toString());

		}

        context = this;
        push_text=(TextView)findViewById(R.id.text_view);
        push_text.setText(AppConstant.pushMessage);
		closeButton= (Button) findViewById(R.id.notifiation_image);

		closeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new SendCancelPushResponse().execute();
				PushActivity.this.finish();
			}
		});

	}



	public void LaunchApp(View v) {
		new SendLaunchPushResponse().execute();
		/*if(ContentDownloadActivity.active_activity==true){
			PushActivity.this.finish();
		}else {
			Intent intent = new Intent(PushActivity.this, ContentDownloadActivity
                    .class);
			startActivity(intent);
			PushActivity.this.finish();
		}*/
		
	}
	private class SendCancelPushResponse extends AsyncTask<String, String, String> {
		TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
		
		RequiredUserInfo userinfo = new RequiredUserInfo();
		String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(PushActivity.this);
		String HS_MOD_ = userinfo.deviceModel(PushActivity.this);
		String user_email=userinfo.userEmail(PushActivity.this);
		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//Toast.makeText(getApplicationContext(), "resultMno", Toast.LENGTH_LONG).show();
			if(resultMno.equalsIgnoreCase("ERROR"))
			{
				resultMno="wifi";
			}
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email",user_email));
			params.add(new BasicNameValuePair("action", "cancel"));
			params.add(new BasicNameValuePair("handset_name", HS_MANUFAC_));
			params.add(new BasicNameValuePair("handset_model", HS_MOD_));			
			params.add(new BasicNameValuePair("msisdn",resultMno));
			// getting JSON Object
			// Note that create product url accepts POST method
			phpRequest.makeHttpRequest(pushResponseUrl, "POST", params);
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		}

	}
	
	public class SendLaunchPushResponse extends AsyncTask<String, String, String> {
		RequiredUserInfo userinfo = new RequiredUserInfo();
		String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(PushActivity.this);
		String HS_MOD_ = userinfo.deviceModel(PushActivity.this);
		String user_email=userinfo.userEmail(PushActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(resultMno.equalsIgnoreCase("ERROR"))
			{
				resultMno="wifi";
			}

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email",user_email));
			params.add(new BasicNameValuePair("action", "launch"));
			params.add(new BasicNameValuePair("handset_name", HS_MANUFAC_));
			params.add(new BasicNameValuePair("handset_model", HS_MOD_));
			params.add(new BasicNameValuePair("msisdn",resultMno));

			// getting JSON Object
			// Note that create product url accepts POST method
			phpRequest.makeHttpRequest(pushResponseUrl, "POST", params);
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		}

	}


}
