package com.vumobile.clubzed.SongRelated;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.R;


import java.io.IOException;
import java.util.List;

public class SongListCustomAdapter extends BaseAdapter {

	List<SongListModel> pictureList;
	private Activity activity;
	private LayoutInflater inflater;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	ImageView playpausebutton;
	public static  Context songListAdapterContext;
	private boolean intialStage = true;
	private boolean playPause;
	 MediaPlayer mediaPlayer = new MediaPlayer();
	public String contentCode="";



	public SongListCustomAdapter(List<SongListModel> pictureList, Activity activity,
								 LayoutInflater inflater, ImageLoader imageLoader) {
		super();
		this.pictureList = pictureList;
		this.activity = activity;
		this.inflater = inflater;
		this.imageLoader = imageLoader;
	}

	public SongListCustomAdapter(List<SongListModel> pictureList, Activity activity) {
		super();
		this.pictureList = pictureList;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictureList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pictureList.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.song_list_row, null);
 
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.stickerThumbnail);



        TextView tvContentTitle = (TextView)convertView.findViewById(R.id.tvContentTitle);
		TextView downloadCount= (TextView) convertView.findViewById(R.id.download_count);
		playpausebutton= (ImageView) convertView.findViewById(R.id.song_list_play_button);
		final RatingBar songRatingBar= (RatingBar) convertView.findViewById(R.id.songViewratingBar);
        


        tvContentTitle.setText(pictureList.get(position).getContent_name().replace("_", " "));
		//Log.e("Song NAME", pictureList.get(position).getContent_name().replace("_", " "));
		downloadCount.setText(pictureList.get(position).getDownloadCount());

		final MediaPlayer mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		playpausebutton.setBackgroundResource(R.drawable.playbutton);
		songRatingBar.setRating(Float.valueOf(pictureList.get(position).getRating()));


		//Rating Button click listener

		songRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
										boolean fromUser) {

				songRatingBar.setRating(Float.valueOf(rating));
				WebView ratingwebview=new WebView(songListAdapterContext);
				contentCode=pictureList.get(position).getContent_code();

				String ratingurl="http://wap.shabox.mobi/GCMPanel/clubzrating.aspx?contentcode="+contentCode+"&rating="+String.valueOf(rating);
				Log.d("ratingurl",ratingurl);
				ratingwebview.loadUrl(ratingurl);
			}
		});


		/*playpausebutton.setOnClickListener(new View.OnClickListener() {



			@Override
			public void onClick(View v) {
				MediaPlayer mp = new MediaPlayer();

				try {

					mp.setDataSource("http://202.164.213.242/CMS/Content/Audio/FullTrack/MP3/"+pictureList.get(position).getsPhysicalFileName()+"mp3");
					mp.prepare();
					mp.start();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
*/


		return convertView;
	}



	class Player extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog progress;

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			Boolean prepared;
			try {

				mediaPlayer.setDataSource(params[0]);

				mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						intialStage = true;
						playPause=false;
						playpausebutton.setBackgroundResource(R.drawable.playbutton);
						mediaPlayer.stop();
						mediaPlayer.reset();
					}
				});
				mediaPlayer.prepare();
				prepared = true;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				Log.d("IllegarArgument", e.getMessage());
				prepared = false;
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				prepared = false;
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				prepared = false;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				prepared = false;
				e.printStackTrace();
			}
			return prepared;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progress.isShowing()) {
				progress.cancel();
			}
			Log.d("Prepared", "//" + result);
			mediaPlayer.start();

			intialStage = false;
		}

		public Player() {
			progress = new ProgressDialog(songListAdapterContext);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.progress.setMessage("Buffering...");
			this.progress.show();

		}
	}



}
