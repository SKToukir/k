package com.vumobile.clubzed.VideoRelated;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.util.Download_Class;
import com.vumobile.clubzed.R;


import java.util.List;

public class VideoListCustomAdapter extends BaseAdapter {

	List<VideoListModel> pictureList;
	private Activity activity;
	private LayoutInflater inflater;
	ImageButton videoDownloadButton;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	Download_Class download_class=new Download_Class();

	public VideoListCustomAdapter(List<VideoListModel> pictureList, Activity activity,
								  LayoutInflater inflater, ImageLoader imageLoader) {
		super();
		this.pictureList = pictureList;
		this.activity = activity;
		this.inflater = inflater;
		this.imageLoader = imageLoader;
	}

	public VideoListCustomAdapter(List<VideoListModel> pictureList, Activity activity) {
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
		try {
			if (convertView == null)
                convertView = inflater.inflate(R.layout.video_list_row, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.stickerThumbnail);
        
        
        
        TextView tvContentTitle = (TextView)convertView.findViewById(R.id.tvContentTitle);
		videoDownloadButton= (ImageButton) convertView.findViewById(R.id.videoDownloadButton);
        
        VideoListModel model = pictureList.get(position);

		thumbNail.setImageUrl("http://wap.shabox.mobi/CMS/GraphicsPreview/" + model.getContent_img().replace(" ", "%20"), imageLoader);
        String videoTitle=model.getContent_name().replace("_"," ");

//		if (videoTitle.length()>20){
//			videoTitle = videoTitle.substring(0, Math.min(videoTitle.length(), 20));
//			videoTitle=videoTitle+"....";
//		}


        tvContentTitle.setText(videoTitle);

		videoDownloadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				//============pass the content information to download class==============
				Download_Class.contentCode=pictureList.get(position).getContent_code();
				Download_Class.categoryCode=pictureList.get(position).getCategoryCode();
				Download_Class.sContentType=pictureList.get(position).getsContentType();
				Download_Class.contentName=pictureList.get(position).getContent_name();
				Download_Class.sPhysicalFileName=pictureList.get(position).getsPhysicalFileName();
				Download_Class.ZedID=pictureList.get(position).getZedID();
				Download_Class.contentImg=pictureList.get(position).getContent_img();

				download_class.detectMSISDN();


			}
		});
        
		return convertView;
	}

}
