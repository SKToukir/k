package com.vumobile.clubzed.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.vumobile.clubzed.util.PHPRequest;
import com.vumobile.clubzed.util.RequiredUserInfo;
import com.vumobile.clubzed.util.ServiceHandler;
import com.vumobile.clubzed.util.SplashCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IT-10 on 3/28/2016.
 */

public class NetworkedService extends Service {

    private static final String TAG = "HelloService";
    String contentCode,categoryCode,sContentType,sPhysicalFileName,ZedID,message;
    private boolean isRunning  = false;
    private static String url2 = "http://wap.shabox.mobi/GCMPanel/ClubzPush.aspx?email=";
    public String pushResponseUrl = "http://www.vumobile.biz/gcm_server_php/push_response.php";
    public static String resultMno = null;
    PHPRequest phpRequest = new PHPRequest();
    // JSON Node names
    private static final String TAG_CONTACTS = "Table";
    public String StickerTitle;

    public static String image_title = "";
    public static String sample_url = "";
    JSONArray contacts = null;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.i(TAG, "Service onStartCommand");

        if (intent == null) {
            cleanupAndStopServiceRightAway();
            return START_NOT_STICKY;
        }
        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.


                try {

                    Thread.sleep(6000);

                    try {
                        new GetContacts().execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {

                }

                //i = i+1;


            }
        }).start();

        return START_STICKY;

    }

    private void cleanupAndStopServiceRightAway() {
        // Add your code here to cleanup the service

        // Add your code to perform any recovery required
        // for recovering from your previous crash

        // Request to stop the service right away at the end
        stopSelf();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("Appps", "called receiver method");
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {


            RequiredUserInfo userinfo = new RequiredUserInfo();

            String user_email=userinfo.deviceID(NetworkedService.this);

            String urlpush=url2+ user_email;

            Log.d("push", urlpush);

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(
                    urlpush, ServiceHandler.GET);

            Log.d("Response:", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.d("Response object: ", "> " + jsonObj);
                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_CONTACTS);
                    Log.d("Response array: ", "> " + contacts);


                    // looping through All Contacts

                    JSONObject c = contacts.getJSONObject(0);
                    Log.d("Response next object: ", "> " + c);
                    sample_url = c.getString("ContentImage");
                    contentCode=c.getString("ContentCode");
                    image_title=c.getString("ContentName");
                    categoryCode=c.getString("CategoryCode");
                    sContentType=c.getString("ContentType");
                    sPhysicalFileName=c.getString("PfileName");
                    ZedID=c.getString("zedId");
                    message=c.getString("message");
                    Log.d("Response sample_url ", "> " + sample_url);
                    // image_title = c.getString("image_title");
                    Log.d("Response image_title ", "> " + image_title);


                    // setCustomViewNotification(GCMIntentService.this, message1,sample_url);
                    Utils.setCustomViewNotification(getApplicationContext(), sample_url,contentCode, image_title, categoryCode, sContentType,sPhysicalFileName,ZedID, message, 0);
                    // SendLaunchPushRes();

                    new SendLaunchPushResponse(getApplicationContext(), "received").execute();
                    URL url = null;


                    try {
                        url = new URL(sample_url);
                    } catch (MalformedURLException e) {
                        System.out.println("The URL is not valid.");
                        System.out.println(e.getMessage());
                    }

                    if (url != null) {
                        //ImageURL=url.toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // setCustomViewNotification(context, message1);
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d("Response onPostExecute ", "> " + "onPostExecute");
        }

    }
   /* private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
          //  AllNotificationList.removeNotificationList();
            String urlpush=url2+ SharedPreferencesHelper.getMailAddress(getApplicationContext());
            Log.d("Url Push",urlpush);
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlpush, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);


            if (jsonStr != null) {
                try {
                  //  AllNotificationList.removeNotificationList();
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.d("Response object: ", "> " + jsonObj);
                    // Getting JSON Array node

                    contacts = jsonObj.getJSONArray(TAG_CONTACTS);
                    Log.d("Response array: ", "> " + contacts);
                    // looping through All Contacts

                    for (int i=0; i<contacts.length();i++) {


                        JSONObject c = contacts.getJSONObject(i);
                        sample_url = c.getString("image_url");

                        if (sample_url != null) {

                            NotificationList notificationList = new NotificationList();
                            AllNotificationList  allNotificationList = new AllNotificationList();

                            Log.d("Response next object: ", "> " + c);
                            sample_url = c.getString("image_url");
                            StickerTitle = c.getString("image_title");
                            String Push_Message = c.getString("Push_Message");

                            Log.d("Response data ", "> " + image_title + " id" + i);
                            notificationList.setImage_title(StickerTitle);
                            notificationList.setImage_url(sample_url);
                            notificationList.setPush_Message(Push_Message);
                            allNotificationList.setNotificationList(notificationList);

                            Utils.setCustomViewNotification(getApplicationContext(), StickerTitle, Push_Message, sample_url, i);
                            // SendLaunchPushRes();

                            new SendLaunchPushResponse(getApplicationContext(), "received").execute();
                        }else{
                            Log.d("sample_url","sample_url");
                        }
                    }
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] ");
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

        }*/

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;
        sendBroadcast(new Intent("YouWillNeverKillMe"));
        Log.i(TAG, "Service onDestroy");
    }
    public class SendLaunchPushResponse extends AsyncTask<String, String, String> {

        Context context;
        String action;
        public SendLaunchPushResponse(Context context1,String action1){
            context=context1;
            action=action1;
        }

        RequiredUserInfo userinfo = new RequiredUserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(NetworkedService.this);
        String HS_MOD_ = userinfo.deviceModel(NetworkedService.this);
        String user_email=userinfo.userEmail(NetworkedService.this);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            // detect MSISDN when notification launched
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

                if(resultMno.equalsIgnoreCase("ERROR"))
                {
                    resultMno="wifi";
                }

                Log.i("MSISDNs", "" + resultMno);

            } catch (Exception ex) {
                Log.i("EXCEPTION EXIST USER", "" + ex.toString());

            }

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email",user_email));
            params.add(new BasicNameValuePair("action", action));
            params.add(new BasicNameValuePair("handset_name", HS_MANUFAC_));
            params.add(new BasicNameValuePair("handset_model", HS_MOD_));
            params.add(new BasicNameValuePair("msisdn",resultMno));

            // getting JSON Object
            // Note that create product url accepts POST method
            phpRequest.makeHttpRequest(pushResponseUrl, "POST", params);
            Log.d("Tariqulpush", pushResponseUrl+"params: "+ params);
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);

    }


    private void ensureServiceStaysRunning() {
        // KitKat appears to have (in some cases) forgotten how to honor START_STICKY
        // and if the service is killed, it doesn't restart.  On an emulator & AOSP device, it restarts...
        // on my CM device, it does not - WTF?  So, we'll make sure it gets back
        // up and running in a minimum of 20 minutes.  We reset our timer on a handler every
        // 2 minutes...but since the handler runs on uptime vs. the alarm which is on realtime,
        // it is entirely possible that the alarm doesn't get reset.  So - we make it a noop,
        // but this will still count against the app as a wakelock when it triggers.  Oh well,
        // it should never cause a device wakeup.  We're also at SDK 19 preferred, so the alarm
        // mgr set algorithm is better on memory consumption which is good.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            // A restart intent - this never changes...
            final int restartAlarmInterval = 20*1000;
            final int resetAlarmTimer = 2*1000;
            final Intent restartIntent = new Intent(this, NetworkedService.class);
            restartIntent.putExtra("ALARM_RESTART_SERVICE_DIED", true);
            final AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Handler restartServiceHandler = new Handler()
            {
                @Override
                public void handleMessage(Message msg) {
                    // Create a pending intent
                    PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, restartIntent, 0);
                    alarmMgr.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + restartAlarmInterval, pintent);
                    sendEmptyMessageDelayed(0, resetAlarmTimer);
                }
            };
            restartServiceHandler.sendEmptyMessageDelayed(0, 0);
        }
    }
}