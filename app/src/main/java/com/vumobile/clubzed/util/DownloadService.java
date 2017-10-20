package com.vumobile.clubzed.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*
 * Download Service
 */
public class DownloadService extends Service {
	//	Bitmap bitmap;
	private NotificationManager manager;
	// private NotificationCompat.Builder builder;
	private String url;
	/* Message Code */
	public static final int MSG_UPDATE = 0;// Download Update
	public static final int MSG_SUCCESS = 1;// Download Success
	public static final int MSG_FAILED = 2;// Download failed
	Map<Integer, NotificationCompat.Builder> notifications;// Notification queue

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		super.onCreate();
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notifications = new HashMap<Integer, NotificationCompat.Builder>();
		Log.i("Service", "onCreate()");

//		bitmap = getBitmapFromURL(ContentDownloadActivity.url);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Service", "onDestroy()");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("Service", "onStartCommand()");
		url = intent.getStringExtra("url");
		Log.i("url", url + "--" + startId);
		addNotification(startId, getStr(url));
		/* Open Thread Download*/
		new DownloadThread(this, url, startId).start();
		return super.onStartCommand(intent, flags, startId);
	}

	private String getStr(String url) {
		return url.substring(url.lastIndexOf("/") + 1, url.length());
	}

	/* Display Download */
	private void addNotification(int id, String title) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setContentTitle(title);
		builder.setContentText("Downloading..." + "0%");
		builder.setSmallIcon(android.R.drawable.stat_sys_download);
//		builder.setLargeIcon(bitmap);
		/* Click on the message when you set the display interface*/
//		Intent nextIntent = new Intent(this, DownloadService.class);
//		TaskStackBuilder task = TaskStackBuilder.create(this);
//		task.addNextIntent(nextIntent);
//		PendingIntent pengdingIntent = task.getPendingIntent(0,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//		builder.setContentIntent(pengdingIntent);


		builder.setProgress(100, 0, false);
		builder.setAutoCancel(true);
		builder.setTicker("Downloading....");
		notifications.put(id, builder);
		manager.notify(id, builder.build());
	}

	// Updates download progress
	private void updateNotification(int id, int progress) {
		NotificationCompat.Builder notification = notifications.get(id);
		notification.setContentText("Downloading..." + progress + "%");
		notification.setProgress(100, progress, false);
		manager.notify(id, notification.build());
	}

	/*Message Processing*/
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case DownloadService.MSG_UPDATE: {
					updateNotification(msg.arg1, msg.arg2);
				}
				break;

				case DownloadService.MSG_SUCCESS: try{
					NotificationCompat.Builder notification = notifications
							.get(msg.arg1);
					notification.setTicker("Download completed!");
					notification.setContentText("Download completed!");
					Toast.makeText(getApplicationContext(),"Download completed!",Toast.LENGTH_LONG).show();
					notification.setSmallIcon(android.R.drawable.stat_sys_download_done);
					String fileStr  = DownloadThread.fileURL;
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
//				File file = new File(fileStr);
					File yourFile = new File(Environment.getExternalStorageDirectory(), fileStr);
					String extension = android.webkit.MimeTypeMap
							.getFileExtensionFromUrl(Uri.fromFile(yourFile).toString());
					String mimetype = android.webkit.MimeTypeMap.getSingleton()
							.getMimeTypeFromExtension(extension);// set your audio path
					intent.setDataAndType(Uri.fromFile(yourFile), mimetype);

					PendingIntent pIntent = PendingIntent.getActivity(
							DownloadService.this, 0, intent, 0);
					notification.setContentIntent(pIntent);

					manager.notify(msg.arg1, notification.build());
					notifications.remove(msg.arg1);
					if (notifications.isEmpty()) {// All downloaded, stop service
						stopSelf();
					}
				}catch(Exception e){
					Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
				}
				break;
				case DownloadService.MSG_FAILED: {
					NotificationCompat.Builder notification = notifications
							.get(msg.arg1);
					notification.setContentTitle("Download failed");
					notification.setContentText("Please check your network connection!");
					manager.notify(msg.arg1, notification.build());
				}
				break;
			}
		}

	};
	public Bitmap getBitmapFromURL(String strURL) {
		try {
			URL url = new URL(strURL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}