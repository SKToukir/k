package com.vumobile.clubzed.SongRelated;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.vumobile.clubzed.AppConstant;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.VideoRelated.VideoPreViewActivity;
import com.vumobile.clubzed.adapter.RelatedItemMusicAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.BanglaSongClass;
import com.vumobile.clubzed.model.EnglishSongClass;
import com.vumobile.clubzed.model.MusicClass;
import com.vumobile.clubzed.model.RelatedMusicClass;
import com.vumobile.clubzed.util.Caller;
import com.vumobile.clubzed.util.Download_Class;
import com.vumobile.clubzed.util.HttpRequest;
import com.vumobile.clubzed.util.PHPRequest;
import com.vumobile.clubzed.util.RequiredUserInfo;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaySongActivity extends ActionBarActivity {

    private PullToRefreshScrollView mPullRefreshScrollView;
    ScrollView scrollViewSong;
    ProgressDialog loading;
    int num = 9;
    RelatedItemMusicAdapter adapter;
    MusicClass musicClass;
    BanglaSongClass banglaSongClass;
    EnglishSongClass englishSongClass;
    List<BanglaSongClass> banglaSongClassList = new ArrayList<BanglaSongClass>();
    List<EnglishSongClass> englishSongClassList = new ArrayList<EnglishSongClass>();
    List<MusicClass> musicClassList = new ArrayList<MusicClass>();
    List<RelatedMusicClass> relatedMusicClasses = new ArrayList<RelatedMusicClass>();
    RelatedMusicClass relatedMusicClass;
    RecyclerView recyclerView;
    public static String contentCode = "", categoryCode = "", contentName = "", sContentType = "",
            sPhysicalFileName = "", ZedID = "", contentImg = "",likes = "",views = "",related_song = "",SONG_TYPE = "";
    public static int dimWidth, dimHeight;
    TextView song_preview_download_button, songTitle,progressindicator,txtViewsCount;
    Download_Class download_Class;
    public static String model;
    public static String manufacture;
    public static String brand;
    NetworkImageView playSongNetworkImageView;
    ImageLoader imageLoader;
    ImageButton shareButton, ratingButton,playPauseButton;
    ProgressDialog bufferingProgressDialog;
    MediaPlayer mediaPlayer=new MediaPlayer();
    public SeekBar seekBar;
    Uri songURL;
    public String string = "http://www.vumobile.biz/clubz_android/clubz_version.txt";
    public  String UpdateString;
    public static String updateReasonString="";
    public  int mediaPos,mediaMax;
    Handler handler=new Handler();
    public static String fullVideoUrl = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/";
    public static String audioURL = "http://wap.shabox.mobi/CMS/Content/Audio/FullTrack/MP3/";
    //public static String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/Video%20Clips/D800x600/";
    public static String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/Video%20Clips/D480x320/";
    public static String rsltNumber="";
    public static int doAction=0;
    public PHPRequest phpRequest=new PHPRequest();
    public String pushResponseUrl = "http://www.vumobile.biz/gcm_server_php/push_response.php";
    TextView txtlikeCountMusic;
    Button btnMore;
    Subscriptio_Class subscriptio_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);


        // count views
        viewsCount(Config.URL_VIEWS+contentCode);
        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.scrollViewSong);
        txtViewsCount = (TextView) findViewById(R.id.txtViewsCount);
        txtlikeCountMusic = (TextView) findViewById(R.id.txtlikeCountMusic);
        songTitle = (TextView) findViewById(R.id.songPreviewTitle);
        playSongNetworkImageView = (NetworkImageView) findViewById(R.id.songPreviewImage);
        song_preview_download_button = (TextView) findViewById(R.id.songDownloadTextView);
        //shareButton = (ImageButton) findViewById(R.id.songshareButton);
        imageLoader = AppController.getInstance().getImageLoader();
        //ratingButton = (ImageButton) findViewById(R.id.songratingButton);
        playPauseButton= (ImageButton) findViewById(R.id.playandPauseButton);
        bufferingProgressDialog = new ProgressDialog(PlaySongActivity.this);
        seekBar= (SeekBar) findViewById(R.id.seekBar);
        bufferingProgressDialog.setTitle("Buffering");
        bufferingProgressDialog.setMessage("Please wait");
        progressindicator= (TextView) findViewById(R.id.durationText);
        btnMore = (Button) findViewById(R.id.btnMoreMusic);

        txtlikeCountMusic.setText(likes);
        txtViewsCount.setText(views);

        initRelatedMusicRecycler();
        pareseRelatedMusic(related_song);

        Subscriptio_Class.applicationContext=PlaySongActivity.this;
        subscriptio_class=new Subscriptio_Class();

//        shareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String shareBody = "https://play.google.com/store/apps/details?id=com.vumobile.clubzed";
//
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ClubZ");
//
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share via"));
//            }
//        });
//        ratingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WebView ratingwebview = new WebView(PlaySongActivity.this);
//                String ratingurl = "http://wap.shabox.mobi/GCMPanel/clubzrating.aspx?contentcode=" + contentCode + "&rating=5";
//                //ratingButton.setBackgroundColor(getResources().getColor(R.color.yello));
//                Log.d("ratingurl", ratingurl);
//                ratingwebview.loadUrl(ratingurl);
//            }
//        });


        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {


                relatedMusicClasses.clear();
                musicClassList.clear();
                banglaSongClassList.clear();
                englishSongClassList.clear();
                num+=5;
                pareseRelatedMusic(related_song);
            }
        });
        scrollViewSong = mPullRefreshScrollView.getRefreshableView();

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.img_btn_play));
                }
                else {
                    mediaPlayer.start();
                    playPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.img_btn_pause));
                }
            }
        });


        //--------------Context------------
        Download_Class.applicationContext = PlaySongActivity.this;
        download_Class = new Download_Class();


        //---------------handset model dimension determine-----------
        DisplayMetrics dms = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dms);
        model = Build.MODEL;
        manufacture = Build.MANUFACTURER;
        brand = Build.BRAND; // like SEMC
        dimWidth = dms.widthPixels;
        dimHeight = dms.heightPixels;


        //----------Get the intent information--------------

        try {
            contentCode = getIntent().getExtras().getString("contentCode");
            categoryCode = getIntent().getExtras().getString("categoryCode");
            contentName = getIntent().getExtras().getString("contentName");

            sContentType = getIntent().getExtras().getString("sContentType");
            sPhysicalFileName = getIntent().getExtras().getString(
                    "sPhysicalFileName");
            ZedID = getIntent().getExtras().getString("ZedID");
            contentImg = getIntent().getExtras().getString("contentImg");
            doAction=getIntent().getExtras().getInt("doAction");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doAction==2){

            new SendLaunchPushResponse().execute();

        }


        playSongNetworkImageView.setImageUrl(contentImg, imageLoader);
        songTitle.setText(contentName.replace("_", " "));
        songTitle.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_text));




        songURL = Uri.parse(audioURL + sPhysicalFileName.replace(" ", "_") + ".mp3");
        Log.d("songurl",String.valueOf(songURL));


        try {
            mediaPlayer.setDataSource(String.valueOf(songURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mediaPlayer.isPlaying()){

            mediaPos = mediaPlayer.getCurrentPosition();
            mediaMax = mediaPlayer.getDuration();

            seekBar.setMax(mediaMax); // Set the Maximum range of the
            seekBar.setProgress(mediaPos);// set current progress to song's

            handler.removeCallbacks(moveSeekBarThread);
            handler.postDelayed(moveSeekBarThread, 100);
        }


        recyclerView.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerView, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (SONG_TYPE.equals("music")){
                    PlaySongActivity.contentCode = musicClassList.get(position).getContent_code();
                    PlaySongActivity.categoryCode = musicClassList.get(position).getCategory_code();
                    PlaySongActivity.contentName = musicClassList.get(position).getCintent_title();
                    PlaySongActivity.sContentType = musicClassList.get(position).getType();
                    PlaySongActivity.sPhysicalFileName = musicClassList.get(position).getPhysicalFileName();
                    PlaySongActivity.contentImg = musicClassList.get(position).getImageUrl();
                    PlaySongActivity.ZedID = musicClassList.get(position).getZID();
                    PlaySongActivity.related_song = Config.URL_MUSIC;
                    PlaySongActivity.likes = musicClassList.get(position).getLikes();
                    PlaySongActivity.views = musicClassList.get(position).getViews();
                    PlaySongActivity.SONG_TYPE = "music";
                    Subscriptio_Class.type = "song";
                    subscriptio_class.detectMSISDN();
                    stopMusic();


                }else if (SONG_TYPE.equals("bangla")){

                    PlaySongActivity.contentCode = banglaSongClassList.get(position).getContentcode();
                    PlaySongActivity.categoryCode = banglaSongClassList.get(position).getCategory_code();
                    PlaySongActivity.contentName = banglaSongClassList.get(position).getContent_title();
                    PlaySongActivity.sContentType = banglaSongClassList.get(position).getType();
                    PlaySongActivity.sPhysicalFileName = banglaSongClassList.get(position).getPhysicalFileName();
                    PlaySongActivity.contentImg = banglaSongClassList.get(position).getImageUrl();
                    PlaySongActivity.ZedID = banglaSongClassList.get(position).getZeid();
                    PlaySongActivity.likes = banglaSongClassList.get(position).getLikes();
                    PlaySongActivity.views = banglaSongClassList.get(position).getViews();
                    PlaySongActivity.related_song = Config.URL_BANGLA_SONG;
                    PlaySongActivity.SONG_TYPE = "bangla";
                    Subscriptio_Class.type = "song";
                    subscriptio_class.detectMSISDN();
                    stopMusic();

                }else if (SONG_TYPE.equals("english")){

                    PlaySongActivity.contentCode = englishSongClassList.get(position).getContentcode();
                    PlaySongActivity.categoryCode = englishSongClassList.get(position).getCategory_code();
                    PlaySongActivity.contentName = englishSongClassList.get(position).getContent_title();
                    PlaySongActivity.sContentType = englishSongClassList.get(position).getType();
                    PlaySongActivity.sPhysicalFileName = englishSongClassList.get(position).getPhysicalFileName();
                    PlaySongActivity.contentImg = englishSongClassList.get(position).getImageUrl();
                    PlaySongActivity.ZedID = englishSongClassList.get(position).getZeid();
                    PlaySongActivity.likes = englishSongClassList.get(position).getLikes();
                    PlaySongActivity.views = englishSongClassList.get(position).getViews();
                    PlaySongActivity.related_song = Config.URL_ENGLISH_SONG;
                    PlaySongActivity.SONG_TYPE = "english";
                    Subscriptio_Class.type = "song";
                    subscriptio_class.detectMSISDN();
                    stopMusic();

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        song_preview_download_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CheckUpdate(string);

                Download_Class.contentCode = contentCode;
                Download_Class.categoryCode = categoryCode;
                Download_Class.sContentType = sContentType;
                Download_Class.contentName = contentName;
                Download_Class.sPhysicalFileName = sPhysicalFileName;
                Download_Class.ZedID = ZedID;
                Download_Class.contentImg = contentImg;
                Download_Class.manufacture = manufacture;
                Download_Class.model = model;
                Download_Class.dimHeight = dimHeight;
                Download_Class.dimWidth = dimWidth;

                download_Class.detectMSISDN();

            }
        });

    }

    // count video views
    private void viewsCount(String urlViews) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlViews, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    JSONArray array = jsonObject.getJSONArray("Table");

                    for (int i = 0; i<array.length(); i++){

                        JSONObject obj = array.getJSONObject(i);

                        String totalViews = obj.getString("ViewCount");
                        txtViewsCount.setText(totalViews);
                        Log.d("views",totalViews);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(PlaySongActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void initRelatedMusicRecycler() {

        loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);

        adapter = new RelatedItemMusicAdapter(PlaySongActivity.this,relatedMusicClasses);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_relative_music);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void pareseRelatedMusic(final String related_music) {

        if (SONG_TYPE.equals("music")) {

            JsonArrayRequest request = new JsonArrayRequest(related_music, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    loading.dismiss();
                    hideMoreButton(num,jsonArray);
                    for (int i = 0; i < num; i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            musicClass = new MusicClass();
                            relatedMusicClass = new RelatedMusicClass();
                            musicClass.setContent_title(obj.getString(Config.CONTENT_TITLE_MUSIC));
                            musicClass.setLive_date(obj.getString(Config.LIVE_DATE_MUSIC));
                            musicClass.setContent_code(obj.getString(Config.CONTENT_CODE_MUSIC));
                            musicClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_MUSIC));
                            musicClass.setType(obj.getString(Config.TYPE_MUSIC));
                            musicClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_MUSIC));
                            musicClass.setZID(obj.getString(Config.ZID_MUSIC));
                            musicClass.setPath(obj.getString(Config.PATH_MUSIC));
                            musicClass.setImageUrl(obj.getString(Config.IMAGE_URL_MUSIC));
                            musicClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            musicClass.setViews(obj.getString(Config.VIEWS));
                            musicClassList.add(musicClass);

                            relatedMusicClass.setMUSIC_TITLE(obj.getString(Config.CONTENT_TITLE_MUSIC));
                            relatedMusicClass.setURL_RELATED_MUSIC(obj.getString(Config.IMAGE_URL_MUSIC));
                            relatedMusicClass.setMUSIC_LIKE(obj.getString(Config.TOTAL_LIKE));
                            relatedMusicClass.setMUSIC_VIEWS(obj.getString(Config.VIEWS));



                            relatedMusicClasses.add(relatedMusicClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(PlaySongActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }else if (SONG_TYPE.equals("english")){
            JsonArrayRequest request = new JsonArrayRequest(related_music, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    loading.dismiss();
                    hideMoreButton(num,jsonArray);
                    for (int i = 0; i<num; i++){
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            englishSongClass = new EnglishSongClass();
                            relatedMusicClass = new RelatedMusicClass();

                            englishSongClass.setContentcode(obj.getString(Config.CONTENT_TYPE_ENGLISH_SONG));
                            englishSongClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_ENGLISH_SONG));
                            englishSongClass.setContent_title(obj.getString(Config.CONTENT_NAME_ENGLISH_SONG));
                            englishSongClass.setType(obj.getString(Config.CONTENT_TYPE_ENGLISH_SONG));
                            englishSongClass.setPhysicalFileName(obj.getString(Config.PHYSICALNAME_ENGLISH_SONG));
                            englishSongClass.setZeid(obj.getString(Config.ZEID_ENGLISH_SONG));
                            englishSongClass.setPath(obj.getString(Config.PATH_ENGLISH_SONG));
                            englishSongClass.setImageUrl(obj.getString(Config.CONTENT_IMAGE_ENGLISH_SONG));
                            englishSongClass.setCount(obj.getString(Config.COUNT_ENGLISH_SONG));
                            englishSongClass.setRating(obj.getString(Config.RATING_ENGLISH_SONG));
                            englishSongClass.setLiveData(obj.getString(Config.LIVE_DATA_ENGLISH_SONG));
                            englishSongClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            englishSongClass.setViews(obj.getString(Config.VIEWS));

                            englishSongClassList.add(englishSongClass);

                            relatedMusicClass.setMUSIC_TITLE(obj.getString(Config.CONTENT_NAME_ENGLISH_SONG));
                            relatedMusicClass.setURL_RELATED_MUSIC(obj.getString(Config.CONTENT_IMAGE_ENGLISH_SONG));
                            relatedMusicClass.setMUSIC_LIKE(obj.getString(Config.TOTAL_LIKE));
                            relatedMusicClass.setMUSIC_VIEWS(obj.getString(Config.VIEWS));



                            relatedMusicClasses.add(relatedMusicClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(PlaySongActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }else if (SONG_TYPE.equals("bangla")){
            JsonArrayRequest request = new JsonArrayRequest(related_music, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    loading.dismiss();
                    hideMoreButton(num,jsonArray);
                    for (int i = 0; i<num; i++){
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            banglaSongClass = new BanglaSongClass();
                            relatedMusicClass = new RelatedMusicClass();

                            banglaSongClass.setContentcode(obj.getString(Config.CONTENT_CODE_BANGLA_SONG));
                            banglaSongClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_SONG));
                            banglaSongClass.setContent_title(obj.getString(Config.CONTENT_NAME_BANGLA_SONG));
                            banglaSongClass.setType(obj.getString(Config.CONTENT_TYPE_BANGLA_SONG));
                            banglaSongClass.setPhysicalFileName((obj.getString(Config.PHYSICALNAME_BANGLA_SONG)).replace(" ","%20"));
                            banglaSongClass.setZeid(obj.getString(Config.ZEID_BANGLA_SONG));
                            banglaSongClass.setPath((obj.getString(Config.PATH_BANGLA_SONG)).replace(" ","%20"));
                            banglaSongClass.setImageUrl(obj.getString(Config.CONTENT_IMAGE_BANGLA_SONG.replace(" ","%20")));
                            banglaSongClass.setCount(obj.getString(Config.COUNT_BANGLA_SONG));
                            banglaSongClass.setRating(obj.getString(Config.RATING_BANGLA_SONG));
                            banglaSongClass.setLiveData(obj.getString(Config.LIVE_DATA_BANGLA_SONG));
                            banglaSongClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            banglaSongClass.setViews(obj.getString(Config.VIEWS));

                            banglaSongClassList.add(banglaSongClass);

                            relatedMusicClass.setMUSIC_TITLE(obj.getString(Config.CONTENT_NAME_BANGLA_SONG));
                            relatedMusicClass.setURL_RELATED_MUSIC(obj.getString(Config.CONTENT_IMAGE_BANGLA_SONG.replace(" ","%20")));
                            relatedMusicClass.setMUSIC_LIKE(obj.getString(Config.TOTAL_LIKE));
                            relatedMusicClass.setMUSIC_VIEWS(obj.getString(Config.VIEWS));



                            relatedMusicClasses.add(relatedMusicClass);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(PlaySongActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }
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

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // if (busyNow != null) {
                        // busyNow.dismis();
                        // }
						/* Check Update Version */

                        try {
                            PackageInfo pinfo;
                            pinfo = getPackageManager().getPackageInfo(
                                    getPackageName(), 0);
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

            final Dialog updateDialog = new Dialog(this,android.R.style.Theme_Light);
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
                    final String appPackageName = getPackageName();
                    try {
                        startActivity(new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id="
                                        + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(
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




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            songTitle.setText(contentName.replace("_", " "));


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            songTitle.setText(contentName.replace("_", " "));
        }
    }


    private Runnable moveSeekBarThread = new Runnable() {

        public void run() {


            int mediaPos_new = 0;
            int mediaMax_new = 0;
            try {
                mediaPos_new = mediaPlayer.getCurrentPosition();
                mediaMax_new = mediaPlayer.getDuration();
            } catch (Exception e) {
                e.printStackTrace();
            }


            int totaldurationMin=(mediaMax_new/1000)/60;
                int totaldurationSec=(mediaMax_new/1000)/60;

                int curminute=(mediaPos_new/1000)/60;
                int cursec=(mediaPos_new/1000)%60;
                seekBar.setMax(mediaMax_new);
                seekBar.setProgress(mediaPos_new);
                progressindicator.setText(String.valueOf(curminute)+":"+String.valueOf(cursec)+"/"+String.valueOf(totaldurationMin)+":"+String.valueOf(totaldurationSec));
                handler.postDelayed(this, 100); //Looping the thread after 0.1 second

                // seconds
            }

    };

    @Override
    public void onBackPressed() {
        mediaPlayer.reset();
        mediaPlayer.stop();
        mediaPlayer.release();

        Intent i = new Intent(PlaySongActivity.this, MainActivity.class);
// set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void btnLikeMusic(View view) {

        likeCountPic(Config.URL_LIKES);

    }

    private void likeCountPic(String urlLikes) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlLikes+contentCode, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    JSONArray array = jsonObject.getJSONArray("Table");

                    for (int i = 0; i<array.length(); i++){

                        JSONObject obj = array.getJSONObject(i);

                        String totalLikes = obj.getString("LikeCount");
                        txtlikeCountMusic.setText(totalLikes);
                        Log.d("LikeCount",totalLikes);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(PlaySongActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    // to load more item
    public void btnMoreMusic(View view) {

        relatedMusicClasses.clear();
        musicClassList.clear();
        banglaSongClassList.clear();
        englishSongClassList.clear();
        num+=5;
        pareseRelatedMusic(related_song);
    }

    public class SendLaunchPushResponse extends AsyncTask<String, String, String> {
        RequiredUserInfo userinfo = new RequiredUserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(PlaySongActivity.this);
        String HS_MOD_ = userinfo.deviceModel(PlaySongActivity.this);
        String user_email=userinfo.userEmail(PlaySongActivity.this);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            PlaySongActivity.rsltNumber = "START";

            try {
                Thread.sleep(1000);

                Caller c = new Caller();
                // c.ad= c.ad;
                c.join();
                c.start();

                while (PlaySongActivity.rsltNumber == "START") {
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {
                    }
                }

                AppConstant.mno = rsltNumber;

            } catch (Exception ex) {

            }
            if(AppConstant.mno.equals("ERROR"))
            {
                rsltNumber="wifi";
            }
            else {
                rsltNumber=AppConstant.mno;
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
            params.add(new BasicNameValuePair("msisdn",rsltNumber));

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

    private void hideMoreButton(int nums,JSONArray array) {

        if (nums > array.length()){
            Toast.makeText(getApplicationContext(),"No more data for loading",Toast.LENGTH_LONG).show();
            btnMore.setVisibility(View.GONE);
        }
    }

    public void stopMusic(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            playPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.img_btn_play));
        }
        else {
            mediaPlayer.start();
            playPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.img_btn_pause));
        }
    }

}

