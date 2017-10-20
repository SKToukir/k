package com.vumobile.clubzed.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.SongRelated.PlaySongActivity;
import com.vumobile.clubzed.VideoRelated.VideoPreViewActivity;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.util.PHPRequest;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    private static NotificationManager mNotificationManager;
    public static NotificationManager mManager;
    public static boolean isNotificationAccessEnabled = false;
    public static String image_title = "";
    public String ImageURL = "";
    JSONArray contacts = null;
    // JSON Node names
    private static final String TAG_CONTACTS = "Table";
    private static String url2 = "http://wap.shabox.mobi/GCMPanel/ClubzPush.aspx";//"http://wap.shabox.mobi/GCMPanel/Amar_stickerInPush.aspx";
    private static final String TAG = "GCMIntentService";
    public String message1="";
    public static String contentCode="";
    public static String categoryCode="";
    public static String sContentType="";
    public static String sPhysicalFileName="";
    public static String ZedID="";
    public static String sample_url="";
    //ContentDownloadActivity contentDownloadActivity=new ContentDownloadActivity();
    Button closeButton;
    public static String resultMno=null;
    public String pushResponseUrl = "http://www.vumobile.biz/gcm_server_php/push_response.php";
    PHPRequest phpRequest=new PHPRequest();
    public static int clickCount=0;
    public static int clickCount1=0;


    public static void setmNotificationManager(NotificationManager mNotificationManager) {
        Utils.mNotificationManager = mNotificationManager;
    }

    @SuppressWarnings("static-access")
    public static void setCustomViewNotification(Context context,String sample_url, String contentCode,String image_title,String categoryCode,String sContentType,String sPhysicalFileName,String ZedID,String sms, int i) {

        Bitmap remote_picture = null;

        try {

            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(sample_url).getContent());
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
            PictureDetailsActivity.PIC_TYPE = "pic";
            PictureDetailsActivity.related_pic = Config.URL_PICTURE;

        } else if(sContentType.matches("FV")||sContentType.matches("VD")) {
            resultIntent = new Intent(context,VideoPreViewActivity.class);
            VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_FULL_VIDEO;
            VideoPreViewActivity.TYPE = "fullVideo";
        }
        else if(sContentType.matches("JG")) {
            resultIntent = new Intent(context,PictureDetailsActivity.class);
            PictureDetailsActivity.related_pic = Config.URL_PICTURE;
            PictureDetailsActivity.PIC_TYPE = "pic";
        }
        else if(sContentType.matches("BFT")) {
            resultIntent = new Intent(context,PlaySongActivity.class);
            PlaySongActivity.related_song = Config.URL_BANGLA_SONG;
            PlaySongActivity.SONG_TYPE = "bangla";
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
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.push_activity);

        Intent volume = new Intent(context, MainActivity.class);//NotifActivityHandler


        volume.putExtra("DO", "2");
        PendingIntent pVolume = PendingIntent.getActivity(context, 1, resultIntent, 0);
        expandedView.setOnClickPendingIntent(R.id.MainlayoutCustom, pVolume);
        expandedView.setTextViewText(R.id.text_view, sms);

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



    private static int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.drawable.clubz_notification : R.drawable.clubz_notification;
    }

}
