package com.vumobile.clubzed.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownload {
	  String sdCard;
	    
	    ImageView Downloaded_image;
	    private final String imageInSD = "/sdcard/er.PNG";
	    Bitmap bitmap=null;
        InputStream in=null;
	    
	    public void saveAdimage(String Url) {
	    	
	    	
//	    	        String url = "http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg";
	    	        new DownloadFileAsync().execute(Url);
	    	    

		
	    }
	    
	    public Bitmap getImageFromUrl(String url,BitmapFactory.Options options) {
	       
	        try {
	            in=getHttpConnection(url);
	            bitmap=BitmapFactory.decodeStream(in,null,options);
	           // in.close();
	        } catch(IOException e) {
	            e.printStackTrace();
	        }
	        return bitmap;
	    }
	    
	    public InputStream getHttpConnection(String surl) throws IOException {
	        InputStream inputStream =null;
	        URL url=new URL(surl);
	        URLConnection conn=url.openConnection();
	        try {
	            HttpURLConnection httpConn=(HttpURLConnection)conn;
	            httpConn.setRequestMethod("GET");
	            httpConn.connect();
	            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK) {
	                inputStream=httpConn.getInputStream();
	            }
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        }
	        return inputStream;
	    }
	    
	    
	    class DownloadFileAsync extends AsyncTask<String, String, String> {
	    	   
	    	@Override
	    	protected void onPreExecute() {
	    		super.onPreExecute();
	    		//showDialog(DIALOG_DOWNLOAD_PROGRESS);
	    	}

	    	@Override
	    	protected String doInBackground(String... aurl) {
	    		int count;

	    	try {

	    	URL url = new URL(aurl[0]);
	    	URLConnection conexion = url.openConnection();
	    	conexion.connect();

	    	int lenghtOfFile = conexion.getContentLength();
	    	Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

	    	InputStream input = new BufferedInputStream(url.openStream());
	    	OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/adplay.PNG");

	    	byte data[] = new byte[1024];

	    	long total = 0;

	    		while ((count = input.read(data)) != -1) {
	    			total += count;
	    			publishProgress(""+(int)((total*100)/lenghtOfFile));
	    			output.write(data, 0, count);
	    		}

	    		output.flush();
	    		output.close();
	    		input.close();
	    	} catch (Exception e) {}
	    	return null;

	    	}
	    	protected void onProgressUpdate(String... progress) {
	    		 Log.d("ANDRO_ASYNC",progress[0]);
	    		// mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	    	}

	    	@Override
	    	protected void onPostExecute(String unused) {
	    		//dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
	    	}
	    }
}
