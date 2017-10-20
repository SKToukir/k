package com.vumobile.clubzed.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.vumobile.clubzed.AppConstant;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.R;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class SplashActivity extends Activity {

	private Context context;
	protected static final int TIMER_RUNTIME = 10; // in ms --> 10s
	protected boolean mbActive;
	AlarmManager am;
	public String response_result;
	public String text_response_result,popup_response_result;
	String video_rul = "",pop_up_url="";

	String Text_url = "";
	public static boolean flag = true;
	public static String resultMno_splash = "";
	int i = 3;
	Intent intentService;
	ImageDownload download = new ImageDownload();
	PendingIntent pendingIntent;
	ImageView splashImage;
	public NetworkChecker internetConnection = new NetworkChecker();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		context = this;
		Log.d("Tracker", "This is Splash Activity");

		if (!SharedPreferencesHelper.isOnline(this)) {
//			AlertMessage.showInternetMessage(this, "Attention!",
//					"To Use this Application Please Connect to internet");
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setIcon(R.drawable.wireless);

			alert.setTitle("Attention !");
			alert.setMessage("To Use This Application Please Connect To Internet");

			alert.setPositiveButton("Settings", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					Intent intent = new Intent(
							Settings.ACTION_WIRELESS_SETTINGS);
					startActivity(intent);
					finish();

				}
			});
			alert.setNegativeButton("Cancel", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					dialog.dismiss();
					finish();

				}
			});

			alert.show();

		} else {
			initUi();
			adPlayInitialization();

		}

	}

	private void initUi() {
		// TODO Auto-generated method stub
		splashImage = (ImageView) findViewById(R.id.splashImage);
		GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(splashImage);
		Glide.with(this).load(R.drawable.splash).into(imageViewTarget);
		final Thread timerThread = new Thread() {
			@Override
			public void run() {
				mbActive = true;
				try {
					int waited = 0;
					while (mbActive && (waited < TIMER_RUNTIME)) {
						sleep(2100);
						if (mbActive) {
							waited += 5;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					onContinue();
				}
			}
		};
		timerThread.start();
	}

	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}

	public void onContinue() {
		// perform any final actions here

		runOnUiThread(new Runnable() {

			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

	String results = "", adplayResult = "";
	String keword_response = "";

	@SuppressWarnings("unchecked")
	public void adPlayInitialization() {

		(new AsyncTask() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();

			}

			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub

				try {

					try {
						Thread.sleep(1000);
						SplashActivity.resultMno_splash = "START";
						SplashCaller c = new SplashCaller();
						// c.ad= c.ad;
						c.join();
						c.start();

						while (SplashActivity.resultMno_splash == "START") {
							try {
								Thread.sleep(100);
							} catch (Exception ex) {
							}
						}

						AppConstant.mno = resultMno_splash;
						Log.i("resultMno_splash", "" + resultMno_splash);
					} catch (Exception ex) {
						Log.i("EXCEPTION EXIST USER", "" + ex.toString());
						// rslt= "Exception";

					}

					DisplayMetrics dms_ = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(dms_);

					ServerSideActivity ssa_ = new ServerSideActivity();
					RequiredUserInfo userinfo = new RequiredUserInfo();
					String HS_MANUFAC_ = userinfo
							.deviceMANUFACTURER(SplashActivity.this);
					String HS_MOD_ = userinfo.deviceModel(SplashActivity.this);
					String HS_DIM_ = dms_.widthPixels + "x" + dms_.heightPixels;
					String IP_ = internetConnection.getLocalIpAddress();
					String MobileNumber = SplashActivity.resultMno_splash;
					if (MobileNumber.equalsIgnoreCase("ERROR")) {
						MobileNumber = "";
					} else {
						MobileNumber = SplashActivity.resultMno_splash;
						Log.d("MNumber",MobileNumber);
					}

					/**
					 * Hit this Url For Every First Time FOr Showing Ad play
					 */
					String StringAdplay = "http://wap.shabox.mobi/adplayapi/load.aspx?pid=ED23B813-DE21-4DD5-9B76-209F684746C6"
							+ "&hma="
							+ HS_MANUFAC_.replaceAll(" ", "%20")
							+ "&hmo="
							+ HS_MOD_.replaceAll(" ", "%20")
							+ "&hdim="
							+ HS_DIM_.replaceAll(" ", "%20")
							+ "&hos=ANDROID"
							+ "&ip="
							+ IP_.replaceAll(" ", "%20")
							+ "&mno="
							+ MobileNumber;
					Log.i("StringAdplay", StringAdplay);

					adplayResult = HttpRequest.GetText(HttpRequest
							.getInputStreamForGetRequest(StringAdplay));

					String string = adplayResult.toString().trim();
					if (string.toString().trim().equalsIgnoreCase("AdPLAY")) {

						video_rul = "http://wap.shabox.mobi/adplayapi/AdPlay.aspx?pos=55&pid=ED23B813-DE21-4DD5-9B76-209F684746C6"
								+ "&mno="
								+ MobileNumber
								+ "&hma="
								+ HS_MANUFAC_.replaceAll(" ", "%20")
								+ "&hmo="
								+ HS_MOD_.replaceAll(" ", "%20")
								+ "&hdim="
								+ HS_DIM_.replaceAll(" ", "%20")
								+ "&hos=ANDROID"
								+ "&ip="
								+ IP_.replaceAll(" ", "%20");

						Text_url = "http://wap.shabox.mobi/adplayapi/AdPlay.aspx?pos=7&pid=ED23B813-DE21-4DD5-9B76-209F684746C6"
								+ "&mno="
								+ MobileNumber
								+ "&hma="
								+ HS_MANUFAC_.replaceAll(" ", "%20")
								+ "&hmo="
								+ HS_MOD_.replaceAll(" ", "%20")
								+ "&hdim="
								+ HS_DIM_.replaceAll(" ", "%20")
								+ "&hos=ANDROID"
								+ "&ip="
								+ IP_.replaceAll(" ", "%20");


						pop_up_url="http://wap.shabox.mobi/adplayapi/AdPlay.aspx?pos=4&pid=ED23B813-DE21-4DD5-9B76-209F684746C6"
								+ "&mno="
								+ MobileNumber
								+ "&hma="
								+ HS_MANUFAC_.replaceAll(" ", "%20")
								+ "&hmo="
								+ HS_MOD_.replaceAll(" ", "%20")
								+ "&hdim="
								+ HS_DIM_.replaceAll(" ", "%20")
								+ "&hos=ANDROID"
								+ "&ip="
								+ IP_.replaceAll(" ", "%20");

						try {
							response_result = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(video_rul));
							final JSONObject json_object = new JSONObject(response_result);
							AppConstant.video_response = json_object.getString("link");
							popup_response_result = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(pop_up_url));
							final JSONObject pop_upjson_object = new JSONObject(popup_response_result);
							AppConstant.popup_response = pop_upjson_object.getString("link");
							Log.i("popup_response_result", popup_response_result);
							text_response_result = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(Text_url));

							/*if (AdplayVideoListParser.connect(getApplicationContext(), response_result));
							if (AdplayListParser.connect(getApplicationContext(), popup_response_result));
							if (AdplayTextListParser.connect(getApplicationContext(),text_response_result));
*/
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				Log.i("AppConstant.video_response", AppConstant.video_response);

				if (AppConstant.video_response.equalsIgnoreCase("NOK")) {


					if (AppConstant.popup_response.equalsIgnoreCase("NOK")) {

					} else {
						//final AdplayListModel model = AllAdPlayList
						//.getAdplayList(0);
						// ad_url = model.getImgsrc();
						//Log.i("popup IJmage url", model.getImgsrc());
						// saveAdimage(ad_url);
						//.saveAdimage(ad_url);
					}

				}else {
					//Intent intent = new Intent(SplashActivity.this,
					//AdplayVideoActivity.class);
					//startActivity(intent);
				}

				super.onPostExecute(result);

			}

		}).execute();

	}

}
