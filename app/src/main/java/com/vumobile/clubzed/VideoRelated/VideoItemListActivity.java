package com.vumobile.clubzed.VideoRelated;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.vumobile.clubzed.util.Download_Class;
import com.vumobile.clubzed.R;

public class VideoItemListActivity extends TabActivity {

    private TabHost host;
    private ViewPager pager;
    GestureDetectorCompat gestureDetectorCompat;
    public int currentTabposition;
    public HorizontalScrollView horizontalScrollView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        host = (TabHost) findViewById(android.R.id.tabhost);
        horizontalScrollView= (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
        // pager = (ViewPager) findViewById(R.id.pager);
        Download_Class.applicationContext = VideoItemListActivity.this;


        host.setup();
        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setContent(new Intent(this, Video_BanglaTopHits.class));
        spec.setIndicator(" Bangla \nTop Hits");
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setContent(new Intent(this, Video_BollyTopHits.class));
        spec.setIndicator(" Bollywood\n Top Hits");
        host.addTab(spec);

        spec = host.newTabSpec("tab3");
        spec.setContent(new Intent(this, Video_EnglishTopHits.class));
        spec.setIndicator(" English \nTop Hits");
        host.addTab(spec);

        spec = host.newTabSpec("tab4");
        spec.setContent(new Intent(this, Video_OfTheWeek.class));
        spec.setIndicator("Video of\nThe Week");
        host.addTab(spec);

        spec = host.newTabSpec("tab5");
        spec.setContent(new Intent(this, Video_FullVideo.class));
        spec.setIndicator("Full Video");
        host.addTab(spec);

        spec = host.newTabSpec("tab6");
        spec.setContent(new Intent(this, Video_Movie_Clips.class));
        spec.setIndicator("Movie Clips");
        host.addTab(spec);

        spec = host.newTabSpec("tab7");
        spec.setContent(new Intent(this, Video_Movie_Trailer.class));
        spec.setIndicator(" Movie\nTrailer");
        host.addTab(spec);

        spec = host.newTabSpec("tab8");
        spec.setContent(new Intent(this, Video_Movie_Review.class));
        spec.setIndicator(" Movie\nReview");
        host.addTab(spec);

        spec = host.newTabSpec("tab9");
        spec.setContent(new Intent(this, Video_CelebrityInterview.class));
        spec.setIndicator(" Celebrity\nInterview");
        host.addTab(spec);

        spec = host.newTabSpec("tab10");
        spec.setContent(new Intent(this, BollywoodGossip.class));
        spec.setIndicator("Hollywood\n Gossip");
        host.addTab(spec);

        // pager.setAdapter(new MyPagerAdapter(this));
       /* pager.setOnPageChangeListener(this);
        host.setOnTabChangedListener(this);*/
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureDetectorCompat.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            currentTabposition= getTabHost().getCurrentTab();


            if (event2.getX() < event1.getX()) {

                float a = event2.getX() - event1.getX();
                //     Log.d("MyGestureListener Left To right", String.valueOf(a));
                if (a <= -250) {
                    Log.d("MyGestureListener", " right to left");
                    host.setCurrentTab(currentTabposition + 1);
                    if (currentTabposition!=9) {
                        centerTabItem(currentTabposition + 1);
                    }

                }
            }
            if (event1.getX() < event2.getX()) {

                float a = event2.getX() - event1.getX();


                if (a >= 300) {

                    Log.d("MyGestureListener", "left to right");
                    host.setCurrentTab(currentTabposition - 1);
                    if (currentTabposition != 0) {
                        centerTabItem(currentTabposition - 1);
                    }

                }

            }

            return true;
        }
    }


    public void centerTabItem(int position) {
        host.setCurrentTab(position);
        final TabWidget tabWidget = host.getTabWidget();
        final int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        final int leftX = tabWidget.getChildAt(position).getLeft();
        int newX = 0;

        newX = leftX + (tabWidget.getChildAt(position).getWidth() / 2) - (screenWidth / 2);
        if (newX < 0) {
            newX = 0;
        }
        horizontalScrollView.scrollTo(newX, 0);
    }

}















/* extends Activity implements View.OnClickListener {
    public ProgressDialog pDialog;
    private List<VideoListModel> pictureList = new ArrayList<VideoListModel>();
    private ListView listView;
    private VideoListCustomAdapter adapter;
    public static String URL = null;
    NetworkImageView nIVHeader;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    RelativeLayout rlMain;
    private int preLast;
    public  int feed;
    public int howmanyTime=0;
    public  int increment=0;
    Button videoOfTheWeek,vid_banglaTopHits,vid_bollyWoodTophits,vid_englishTopHits,
            vid_fullVideo,vid_movie_clips,vid_movieReview,vid_movie_Trailer;
    ImageView videoOfTheWeekImageView,vid_banglaTopHitsImageView,vid_bollyWoodTophitsImageView,vid_englishTopHitsImageView,vid_fullVideoImageView,
            vid_movie_clipsImageView,vid_movieReviewImageView,vid_movie_TrailerImageView;
    public static String contentCode, categoryCode, contentName, sContentType, sPhysicalFileName, ZedID, contentImg;
    public static int swipeIndecator=0;
    public static String model;
    public static String manufacture;
    public static String brand;
    public static int dimWidth,dimHeight;
    public static boolean isOftheWeekOrnot=false;


    public static int clearArrayListornot=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Log.d("Tracker", "This is AllItemLIstActivity Activity");
        //String headerText = getIntent().getExtras().getString("headerText");
        videoTextandImageviewinit();//initialize text and image view

        listView = (ListView) findViewById(R.id.lvPicture);
        rlMain = (RelativeLayout) findViewById(R.id.rlMain);

        nIVHeader = (NetworkImageView) findViewById(R.id.nIVHeader);
        Download_Class.applicationContext=VideoItemListActivity.this;


        //Get the handset model and manufacturer

        vid_banglaTopHits.performClick();


        //---------------handset model dimension determine-----------
        DisplayMetrics dms = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dms);
        model = Build.MODEL;
        manufacture = Build.MANUFACTURER;
        brand = Build.BRAND; // like SEMC
        dimWidth = dms.widthPixels;
        dimHeight = dms.heightPixels;


        Download_Class.model=model;
        Download_Class.manufacture=manufacture;
        Download_Class.dimHeight=dimHeight;
        Download_Class.dimWidth=dimWidth;

//        tvCatName.setText(headerText);

        videoOfTheWeek.performLongClick();

        adapter = new VideoListCustomAdapter(pictureList, this);

        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
       *//* pDialog.setMessage("Loading...");
        pDialog.show();*//*



        //-------------------Swip listener--------------------
        listView.setOnTouchListener(new SwipeListener(this){


            public void onSwipeRight() {
                switch (swipeIndecator) {

                    case 0:
                        vid_bollyWoodTophits.performClick();
                        break;
                    case 1:
                        vid_englishTopHits.performClick();
                        break;
                    case 2:
                        videoOfTheWeek.performClick();
                        break;
                    case 3:
                        vid_fullVideo.performClick();
                        break;
                    case 4:
                        vid_movie_clips.performClick();
                        break;
                    case 5:
                        vid_movieReview.performClick();
                        break;
                    case 6:
                        vid_movie_Trailer.performClick();
                        break;
                    case 7:

                        break;

                }
            }
            public void onSwipeLeft() {
                switch (swipeIndecator) {

                    case 0:

                        break;
                    case 1:
                        vid_banglaTopHits.performClick();
                        break;
                    case 2:
                        vid_bollyWoodTophits.performClick();
                        break;
                    case 3:
                        vid_englishTopHits.performClick();
                        break;
                    case 4:
                        videoOfTheWeek.performClick();
                        break;
                    case 5:
                        vid_fullVideo.performClick();
                        break;
                    case 6:
                        vid_movie_clips.performClick();
                        break;
                    case 7:
                        vid_movieReview.performClick();
                        break;

                }
            }

        });


        nIVHeader.setImageUrl("http://202.164.213.242/CMS/UIHeader/czapp/D480x800/clubz.jpg", imageLoader);//("http://202.164.213.242/CMS/UIHeader/D480x800/CZ_header.png", imageLoader);
        // ==============Start for ClubZ Apps Google Analytics For Each Activity
        // =================/
       *//* try {
            Tracker t = ((AppController) VideoItemListActivity.this.getApplication()).getTracker(TrackerName.APP_TRACKER);
            // Set screen name.
            // Where path is a String representing the screen name.
            t.setScreenName("ClubZ List Activity");
            Log.i("ClubZ Analytics True", "True");
            // Send a screen view.
            t.send(new HitBuilders.ScreenViewBuilder().build());
        } catch (Exception ex) {
            // TODO: handle exception
            Log.i("ClubZ Analytics Error", ex.getMessage());
        }*//*
        // ==============End for ClubZ Apps Google Analytics For Each Activity
        // =================/
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                contentCode = pictureList.get(position).getContent_code();
                categoryCode = pictureList.get(position).getCategoryCode();
                contentName = pictureList.get(position).getContent_name();
                sContentType = pictureList.get(position).getsContentType();
                sPhysicalFileName = pictureList.get(position).getsPhysicalFileName();
                contentImg = pictureList.get(position).getContent_img();
                ZedID = pictureList.get(position).getZedID();

                Intent downloadIntent = new Intent(getApplicationContext(), VideoPreViewActivity.class);//need to change

                downloadIntent.putExtra("contentCode", contentCode);
                downloadIntent.putExtra("categoryCode", categoryCode);
                downloadIntent.putExtra("contentName", contentName);
                downloadIntent.putExtra("sContentType", sContentType);
                downloadIntent.putExtra("sPhysicalFileName", sPhysicalFileName);
                downloadIntent.putExtra("contentImg", contentImg);
                downloadIntent.putExtra("ZedID", ZedID);

                startActivity(downloadIntent);

            }
        });
        //================Scroll view listener=================


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("Scrolling", "Onscrollstatechanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {


                //Log.d("scrollingvisiblecount",String.valueOf(visibleItemCount));
                //Log.d("scrollingTotalcount",String.valueOf(totalItemCount));
                //if(visibleItemCount > 0 && SCROLL_STATE_IDLE)
                //Log.d("scrollingChildCount",String.valueOf(view.getChildCount()));
                final int lastItem = firstVisibleItem + visibleItemCount;
                // Log.d("scrollinglastCount",String.valueOf(lastItem));


                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) { //to avoid multiple calls for last item
                        Log.d("last", "Last");
                        clearArrayListornot = 1;

                        JsonArrayRequest mJsonarray = jsonCall();
                        pDialog = new ProgressDialog(VideoItemListActivity.this);
                        pDialog.setMessage("Loading...");
                        pDialog.show();
                        AppController.getInstance().addToRequestQueue(mJsonarray);
                        preLast = lastItem;


                    }
                }

                if (mLastFirstVisibleItem < firstVisibleItem) {

                    // Log.i("SCROLLING DOWN","TRUE");
                    //Log.d("scrollingvisiblecount",String.valueOf(mLastFirstVisibleItem));

                }
                if (mLastFirstVisibleItem > firstVisibleItem) {
                    // Log.i("SCROLLING UP","TRUE");
                }
                mLastFirstVisibleItem = firstVisibleItem;

            }
        });
        if(feed==0) {
            JsonArrayRequest mJsonarray = jsonCall();
            AppController.getInstance().addToRequestQueue(mJsonarray);//(mainJsonArray);
            pDialog.setMessage("Loading...");
            pDialog.show();
            howmanyTime++;
            feed=1;
        }


    }

    public JsonArrayRequest jsonCall(){

        if (clearArrayListornot==0) {
            pictureList.clear();
        }


        JsonArrayRequest mainJsonArray = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {




                    @Override
                    public void onResponse(JSONArray result) {

                        int howmanyContentTofetch=howmanyTime*10;

                        Log.d("howmanytimes",String.valueOf(howmanyContentTofetch));
                        // TODO Auto-generated method stub

                        Log.d("length",String.valueOf(result.length()));
                        hidePDialog();

                        if (howmanyContentTofetch<result.length() && result.length()>10) {
                            for (int i = increment*10; i < howmanyContentTofetch; i++) {    //result.length()
                                try {
                                    JSONObject obj = result.getJSONObject(i);
                                    VideoListModel picture = new VideoListModel();
                                    // Log.i(">>><<<",obj.toString());
                                    picture.setCategoryCode(obj
                                            .getString("CategoryCode"));
                                    picture.setContent_code(obj
                                            .getString("content_code"));
                                    picture.setContent_img(obj
                                            .getString("content_img"));
                                    picture.setContent_name(obj
                                            .getString("content_name"));
                                    picture.setsContentType(obj
                                            .getString("sContentType"));
                                    picture.setsPhysicalFileName(obj
                                            .getString("sPhysicalFileName"));
                                    picture.setZedID(obj.getString("ZedID"));


                                    pictureList.add(picture);

                                    Log.i(">>>Content Image<<<",
                                            picture.getContent_img());

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data

                            adapter.notifyDataSetChanged();
                        }
                        else if (result.length()<10) {
                            for (int i = 0; i < result.length(); i++) {    //result.length()
                                try {
                                    JSONObject obj = result.getJSONObject(i);
                                    VideoListModel picture = new VideoListModel();
                                    // Log.i(">>><<<",obj.toString());
                                    picture.setCategoryCode(obj
                                            .getString("CategoryCode"));
                                    picture.setContent_code(obj
                                            .getString("content_code"));
                                    picture.setContent_img(obj
                                            .getString("content_img"));
                                    picture.setContent_name(obj
                                            .getString("content_name"));
                                    picture.setsContentType(obj
                                            .getString("sContentType"));
                                    picture.setsPhysicalFileName(obj
                                            .getString("sPhysicalFileName"));
                                    picture.setZedID(obj.getString("ZedID"));

                                    pictureList.add(picture);

                                    Log.i(">>>Content Image<<<",
                                            picture.getContent_img());

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data

                            adapter.notifyDataSetChanged();
                        }

                        howmanyTime++;
                        increment++;
                        Log.d("howmanytimes",String.valueOf(howmanyTime));
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                hidePDialog();

                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
            }
        });
        // Adding request to request queue
        return mainJsonArray;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


   //-----------------initialize the image and text view----------------------
    public void videoTextandImageviewinit(){

        videoOfTheWeek= (Button) findViewById(R.id.video_videoOfTheWeek);
        vid_banglaTopHits= (Button) findViewById(R.id.video_b_top_hits);
        vid_bollyWoodTophits= (Button) findViewById(R.id.vid_bolly_tophits);
        vid_englishTopHits= (Button)findViewById(R.id.vid_eng_top_hits);
        vid_fullVideo= (Button) findViewById(R.id.vid_fullVideo);
        vid_movie_clips= (Button) findViewById(R.id.vid_movie_clips);
        vid_movieReview= (Button) findViewById(R.id.vid_movie_review);
        vid_movie_Trailer= (Button) findViewById(R.id.vid_movie_trailers);


        videoOfTheWeekImageView= (ImageView) findViewById(R.id.video_videooftheweekImage);
        vid_banglaTopHitsImageView= (ImageView) findViewById(R.id.vid_b_tophits_imageview);
        vid_bollyWoodTophitsImageView= (ImageView) findViewById(R.id.vid_bolly_tophits_imageiview);
        vid_englishTopHitsImageView= (ImageView) findViewById(R.id.vid_engtophits_imageview);
        vid_fullVideoImageView= (ImageView) findViewById(R.id.vid_full_video_imageView);
        vid_movie_clipsImageView= (ImageView) findViewById(R.id.vid_movie_clips_imageView);
        vid_movieReviewImageView= (ImageView) findViewById(R.id.vid_movie_review_imageView);
        vid_movie_TrailerImageView= (ImageView) findViewById(R.id.vid_movie_trailers_imageView);

        //Set on click to image views

        videoOfTheWeek.setOnClickListener(this);
        vid_banglaTopHits.setOnClickListener(this);
        vid_bollyWoodTophits.setOnClickListener(this);
        vid_englishTopHits.setOnClickListener(this);
        vid_fullVideo.setOnClickListener(this);
        vid_movie_clips.setOnClickListener(this);
        vid_movieReview.setOnClickListener(this);
        vid_movie_Trailer.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        JsonArrayRequest mJsonarray;

        switch (v.getId()) {

            case R.id.video_videoOfTheWeek:
                URL = "http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=video_4";

                pDialog = new ProgressDialog(VideoItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                isOftheWeekOrnot=true;
                clearArrayListornot=0;
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                videoOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.white));
                vid_banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_fullVideoImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_clipsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movieReviewImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_TrailerImageView.setBackgroundColor(getResources().getColor(R.color.black));

                videoOfTheWeek.setBackgroundColor(getResources().getColor(R.color.black));
                vid_banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_fullVideo.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_clips.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movieReview.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_Trailer.setBackgroundColor(getResources().getColor(R.color.grey));



                swipeIndecator=3;

                break;

            case R.id.video_b_top_hits:
                URL = "http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=video_2";

                pDialog = new ProgressDialog(VideoItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                hidePDialog();
                clearArrayListornot=0;
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                videoOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.white));
                vid_bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_fullVideoImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_clipsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movieReviewImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_TrailerImageView.setBackgroundColor(getResources().getColor(R.color.black));

                videoOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_banglaTopHits.setBackgroundColor(getResources().getColor(R.color.black));
                vid_bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_fullVideo.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_clips.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movieReview.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_Trailer.setBackgroundColor(getResources().getColor(R.color.grey));


                swipeIndecator=0;
                break;

            case R.id.vid_bolly_tophits:
                URL = "http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=video_1";
                pDialog = new ProgressDialog(VideoItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                videoOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.white));
                vid_englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_fullVideoImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_clipsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movieReviewImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_TrailerImageView.setBackgroundColor(getResources().getColor(R.color.black));

                videoOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.black));
                vid_englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_fullVideo.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_clips.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movieReview.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_Trailer.setBackgroundColor(getResources().getColor(R.color.grey));

                swipeIndecator=1;
                break;



            case R.id.vid_eng_top_hits:
                URL = "http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=vedio_english_tophits";
                pDialog = new ProgressDialog(VideoItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                videoOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.white));
                vid_fullVideoImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_clipsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movieReviewImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_TrailerImageView.setBackgroundColor(getResources().getColor(R.color.black));

                videoOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_englishTopHits.setBackgroundColor(getResources().getColor(R.color.black));
                vid_fullVideo.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_clips.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movieReview.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_Trailer.setBackgroundColor(getResources().getColor(R.color.grey));


                swipeIndecator=2;
                break;
            case R.id.vid_fullVideo:
                URL = "http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=video_5";
                pDialog = new ProgressDialog(VideoItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                videoOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_fullVideoImageView.setBackgroundColor(getResources().getColor(R.color.white));
                vid_movie_clipsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movieReviewImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_TrailerImageView.setBackgroundColor(getResources().getColor(R.color.black));

                videoOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_fullVideo.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_clips.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movieReview.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_Trailer.setBackgroundColor(getResources().getColor(R.color.grey));

                swipeIndecator=4;
                break;
            case R.id.vid_movie_clips:
                URL = "http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=vedio_movieclip";
                pDialog = new ProgressDialog(VideoItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                videoOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_fullVideoImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_clipsImageView.setBackgroundColor(getResources().getColor(R.color.white));
                vid_movieReviewImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_TrailerImageView.setBackgroundColor(getResources().getColor(R.color.black));

                videoOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_fullVideo.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_clips.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movieReview.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_Trailer.setBackgroundColor(getResources().getColor(R.color.grey));

                swipeIndecator=5;
                break;
            case R.id.vid_movie_review:
                URL = "http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=video_6";
                pDialog = new ProgressDialog(VideoItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                videoOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_fullVideoImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_clipsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movieReviewImageView.setBackgroundColor(getResources().getColor(R.color.white));
                vid_movie_TrailerImageView.setBackgroundColor(getResources().getColor(R.color.black));

                videoOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_fullVideo.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_clips.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movieReview.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_Trailer.setBackgroundColor(getResources().getColor(R.color.grey));

                swipeIndecator=6;
                break;
            case R.id.vid_movie_trailers:
                URL = "http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=video_8";
                pDialog = new ProgressDialog(VideoItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                videoOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.white));
                vid_banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_fullVideoImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_clipsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movieReviewImageView.setBackgroundColor(getResources().getColor(R.color.black));
                vid_movie_TrailerImageView.setBackgroundColor(getResources().getColor(R.color.white));

                videoOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_fullVideo.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_clips.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movieReview.setBackgroundColor(getResources().getColor(R.color.grey));
                vid_movie_Trailer.setBackgroundColor(getResources().getColor(R.color.black));

                swipeIndecator=7;
                break;

            default:
                break;
        }

    }
}
*/