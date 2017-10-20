package com.vumobile.clubzed.GameRelated;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.util.Download_Class;
import com.vumobile.clubzed.util.HttpRequest;
import com.vumobile.clubzed.R;


import java.util.List;

public class GameListCustomAdapter extends BaseAdapter {

	List<GameListModel> pictureList;
	private Activity activity;
	public String string = "http://www.vumobile.biz/clubz_android/clubz_version.txt";
	public  String UpdateString;
	public static String updateReasonString="";
	private LayoutInflater inflater;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	ImageButton gameDownloadImageButton;
	Download_Class download_class=new Download_Class();
	MainActivity mainActivity=new MainActivity();
	public static  Activity mygameActivity;

	public GameListCustomAdapter(List<GameListModel> pictureList, Activity activity,
								 LayoutInflater inflater, ImageLoader imageLoader) {
		super();
		this.pictureList = pictureList;
		this.activity = activity;
		this.inflater = inflater;
		this.imageLoader = imageLoader;
	}

	public GameListCustomAdapter(List<GameListModel> pictureList, Activity activity) {
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
			convertView = inflater.inflate(R.layout.game_list_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.stickerThumbnail);
		NetworkImageView bannerImage = (NetworkImageView) convertView.findViewById(R.id.game_sticker_banner);


		TextView tvContentTitle = (TextView) convertView.findViewById(R.id.tvContentTitle);

		GameListModel model = pictureList.get(position);

		//

		//For Games
		if (model.getsContentType().equals("JG")) {

			if (model.getContent_img().contains("GamePreview")) {


				thumbNail.setImageUrl(model.getContent_img().replace(" ", "%20"), imageLoader);
				bannerImage.setImageUrl(model.getbanner_image().replace(" ", "%20"), imageLoader);
			} else {
				thumbNail.setImageUrl("http://wap.shabox.mobi/CMS/GamePreview/" + model.getContent_img().replace(" ", "%20"), imageLoader);
				bannerImage.setImageUrl("http://wap.shabox.mobi/CMS/GamePreview/" + model.getbanner_image().replace(" ", "%20"), imageLoader);


			}

		}
		gameDownloadImageButton= (ImageButton) convertView.findViewById(R.id.gameDownloadButton);

		gameDownloadImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

					CheckUpdate(string);

					Download_Class.contentCode=pictureList.get(position).getContent_code();
					Download_Class.categoryCode=pictureList.get(position).getCategoryCode();
					Download_Class.sContentType=pictureList.get(position).getsContentType();
					Download_Class.contentName=pictureList.get(position).getContent_name();
					Download_Class.sPhysicalFileName=pictureList.get(position).getsPhysicalFileName();
					Download_Class.ZedID=pictureList.get(position).getZedID();
					Download_Class.contentImg=pictureList.get(position).getZedID();
					Log.d("gamecontentCode",pictureList.get(position).getContent_code());
					Log.d("gamecategoryCode",pictureList.get(position).getCategoryCode());
					Log.d("gamecontentType",pictureList.get(position).getsContentType());
					Log.d("gamecontentName",pictureList.get(position).getContent_name());
					Log.d("gamePhysicalfileName",pictureList.get(position).getsPhysicalFileName());
					Log.d("gameZedID",pictureList.get(position).getZedID());
					Log.d("gamecontentImg",pictureList.get(position).getContent_img());

					download_class.detectMSISDN();
				//}

			}
		});



	return convertView;
	}



	//----------Function for Update dialog----------------
	private void CheckUpdate(final String url_string) {
		// TODO Auto-generated method stub

		// if (!SharedPreferencesHelper.isOnline(this))
		// AlertMessage.showMessage(this, "No Internet Connection");

		// busyNow = new BusyDialog(context, true);
		// busyNow.show();

		final Thread d = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					UpdateString = HttpRequest.GetText(HttpRequest
							.getInputStreamForGetRequest(url_string));
					String info_string = "http://www.vumobile.biz/clubz_android/clubz_version_reason.txt";
					updateReasonString = HttpRequest.GetText(HttpRequest
							.getInputStreamForGetRequest(info_string));

					Log.i("UpdateString", UpdateString);

				} catch (final Exception e) {

					e.printStackTrace();
				}

				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// if (busyNow != null) {
						// busyNow.dismis();
						// }
						/* Check Update Version */

						try {
							PackageInfo pinfo;
							pinfo = activity.getPackageManager().getPackageInfo(
									activity.getPackageName(), 0);
							String versionName = pinfo.versionName;
							if (!versionName.equalsIgnoreCase(UpdateString
									.toString().trim())) {

								Update();

							}

						} catch (PackageManager.NameNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						/* Check Update Version */

					}

				});

			}
		});

		d.start();
	}

	public void Update(){
		{

			final Dialog updateDialog = new Dialog(activity,android.R.style.Theme_Light);
			updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			updateDialog.setContentView(R.layout.update_dialog_activity);


			TextView update_text = (TextView)updateDialog.findViewById(R.id.update_text);

			Button update_app =(Button)updateDialog.findViewById(R.id.update_app);
			ImageView img = (ImageView)updateDialog.findViewById(R.id.imageView1);

			update_text.setText(updateReasonString);

			update_app.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					updateDialog.dismiss();
					// showUpdateDialog=false;


					/**
					 * if this button is clicked, close current
					 * activity
					 */
					final String appPackageName = activity.getPackageName();
					try {
						activity.startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("market://details?id="
										+ appPackageName)));
					} catch (android.content.ActivityNotFoundException anfe) {
						activity.startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://play.google.com/store/apps/details?id="
										+ appPackageName)));
					}
				}
			});

			img.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					updateDialog.dismiss();
				}
			});

			updateDialog.show();

		}
	}
}
