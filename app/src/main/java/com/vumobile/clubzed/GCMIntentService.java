package com.vumobile.clubzed;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

import com.google.android.gcm.GCMBaseIntentService;
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.SongRelated.PlaySongActivity;
import com.vumobile.clubzed.VideoRelated.VideoPreViewActivity;
import com.vumobile.clubzed.util.PHPRequest;
import com.vumobile.clubzed.util.RequiredUserInfo;
import com.vumobile.clubzed.util.ServiceHandler;
import com.vumobile.clubzed.util.SplashCaller;
import com.vumobile.clubzed.util.Subscriptio_Class;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class GCMIntentService extends GCMBaseIntentService {

    private static NotificationManager mNotificationManager;
    public static boolean isNotificationAccessEnabled = false;
    public String image_title = "";
    public String ImageURL = "";
    JSONArray contacts = null;
    // JSON Node names
    private static final String TAG_CONTACTS = "Table";
    private static String url2 = "http://wap.shabox.mobi/GCMPanel/ClubzPush.aspx";//"http://wap.shabox.mobi/GCMPanel/Amar_stickerInPush.aspx";
    private static final String TAG = "GCMIntentService";
    public String message1="";
    public  String contentCode="",categoryCode="",sContentType="",sPhysicalFileName="",ZedID="",sample_url="";
    //ContentDownloadActivity contentDownloadActivity=new ContentDownloadActivity();
    Button closeButton;
    public static String resultMno=null;
    public String pushResponseUrl = "http://www.vumobile.biz/gcm_server_php/push_response.php";
    PHPRequest phpRequest=new PHPRequest();


    public GCMIntentService() {
        super(CommonUtilities.SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        // displayMessage(context, "Your device registred with GCM");
        Log.d("NAME", MainActivity.name);
        ServerUtilities.register(context, MainActivity.name, MainActivity.model, MainActivity.email, registrationId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        CommonUtilities.displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("price");

        AppConstant.pushMessage = message;

        new GetContacts().execute();
        new SendLaunchPushResponse().execute();

    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        CommonUtilities.displayMessage(context, message);
        // notifies user
//		generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        CommonUtilities.displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        CommonUtilities.displayMessage(context,
                getString(R.string.gcm_recoverable_error, errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    /*@SuppressWarnings("deprecation")
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.clubz;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        String title = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context, HomeActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // notification.sound = Uri.parse("android.resource://" +
        // context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);

    }*/

    private void setCustomViewNotification(Context context, String sms, String Image) {

        Bitmap remote_picture = null;

        try {

            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(Image).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //contentDownloadActivity.doAction=1;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        String strDate = sdf.format(c.getTime());

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Creates an explicit intent for an ResultActivity to receive.

        Intent resultIntent;
        resultIntent = new Intent(context,PictureDetailsActivity.class);
        if (sContentType.matches("WP")||sContentType.matches("ST")) {
            resultIntent = new Intent(context,PictureDetailsActivity.class);

        } else if(sContentType.matches("FV")||sContentType.matches("VD")) {
            resultIntent = new Intent(context,VideoPreViewActivity.class);

        }
        else if(sContentType.matches("JG")) {
            resultIntent = new Intent(context,PictureDetailsActivity.class);

        }
        else if(sContentType.matches("BFT")) {
            resultIntent = new Intent(context,PlaySongActivity.class);

        }

        resultIntent.putExtra("contentCode", contentCode);
        resultIntent.putExtra("categoryCode", categoryCode);
        resultIntent.putExtra("contentName", image_title);
        resultIntent.putExtra("sContentType", sContentType);
        resultIntent.putExtra("sPhysicalFileName", sPhysicalFileName);
        resultIntent.putExtra("contentImg", sample_url);
        resultIntent.putExtra("ZedID", ZedID);
        resultIntent.putExtra("doAction",2);


        Log.d("contentCode", contentCode);
        Log.d("categoryCode", categoryCode);
        Log.d("contentName", image_title);
        Log.d("sContentType", sContentType);
        Log.d("sPhysicalFileName", sPhysicalFileName);
        Log.d("contentImg", sample_url);
        Log.d("ZedID", ZedID);
     /*   DownloadTask downloadTask = new DownloadTask();

        *//** Starting the task created above *//*
        downloadTask.execute(Image);*/
        // This ensures that the back button follows the recommended convention for the back key.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);



        // Adds the Intent that starts the Activity to the top of the stack.
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create remote view and set bigContentView.
        RemoteViews expandedView = new RemoteViews(this.getPackageName(), R.layout.push_activity);

        Intent volume = new Intent(context, MainActivity.class);//NotifActivityHandler


        volume.putExtra("DO", "2");
        PendingIntent pVolume = PendingIntent.getActivity(context, 1, resultIntent, 0);
        //expandedView.setOnClickPendingIntent(R.id.MainlayoutCustom, pVolume);
        expandedView.setTextViewText(R.id.text_view, AppConstant.pushMessage);

        //expandedView.setTextViewText(R.id.notificationTime, strDate);

        try {
            expandedView.setImageViewBitmap(R.id.imageViewTest, remote_picture );

        }catch (Exception e){

            e.printStackTrace();
        }

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(remote_picture)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(sms)

                        //  .setDeleteIntent(pendintIntent)
                .build();

        notification.bigContentView = expandedView;

        notification.defaults |= Notification.DEFAULT_SOUND;
        mNotificationManager.notify(0, notification);
    }



    private int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.drawable.clubz_notification : R.drawable.clubz_notification;
    }

    @Override
    public IBinder onBind(Intent mIntent) {
        IBinder mIBinder = super.onBind(mIntent);
        isNotificationAccessEnabled = true;
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent mIntent) {
        boolean mOnUnbind = super.onUnbind(mIntent);
        isNotificationAccessEnabled = false;
        return mOnUnbind;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(
                    url2, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

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

                    Log.d("Response sample_url ", "> " + sample_url);
                   // image_title = c.getString("image_title");
                    Log.d("Response image_title ", "> " + image_title);


                    setCustomViewNotification(GCMIntentService.this, message1,sample_url);

                    URL url = null;


                    try {
                        url = new URL(sample_url);
                    } catch (MalformedURLException e) {
                        System.out.println("The URL is not valid.");
                        System.out.println(e.getMessage());
                    }

                    if (url != null) {
                        ImageURL=url.toString();
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


    public class SendLaunchPushResponse extends AsyncTask<String, String, String> {
        RequiredUserInfo userinfo = new RequiredUserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(GCMIntentService.this);
        String HS_MOD_ = userinfo.deviceModel(GCMIntentService.this);
        String user_email=userinfo.userEmail(GCMIntentService.this);


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

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
                if(resultMno.equalsIgnoreCase("ERROR"))
                {
                    resultMno="wifi";
                }

            } catch (Exception ex) {
                Log.i("EXCEPTION EXIST USER", "" + ex.toString());

            }

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub


            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("email",user_email));
            params.add(new BasicNameValuePair("action", "received"));
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
