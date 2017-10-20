package com.vumobile.clubzed.SongRelated;

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

public class SongItemListActivity extends TabActivity {

    private TabHost host;
    private ViewPager pager;
    GestureDetectorCompat gestureDetectorCompat;
    public int currentTabposition;
    public HorizontalScrollView horizontalScrollView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        host = (TabHost) findViewById(android.R.id.tabhost);
        horizontalScrollView= (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

        // pager = (ViewPager) findViewById(R.id.pager);
        Download_Class.applicationContext=SongItemListActivity.this;


        host.setup();
        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setContent(new Intent(this, BanglaTopHits.class));
        spec.setIndicator(" Bangla\nTop Hits");
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setContent(new Intent(this, BollyWoodTopHits.class));
        spec.setIndicator("BollyWood\n Top Hits");
        host.addTab(spec);

        spec = host.newTabSpec("tab3");
        spec.setContent(new Intent(this, MusicOfTheWeek.class));
        spec.setIndicator("Music Of\nThe Week");
        host.addTab(spec);

        spec = host.newTabSpec("tab4");
        spec.setContent(new Intent(this, EnglishTopHits.class));
        spec.setIndicator(" English\nTop Hits");
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
                    if (currentTabposition!=3) {
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





/* Activity implements View.OnClickListener{
    public ProgressDialog pDialog;
    private List<SongListModel> pictureList = new ArrayList<SongListModel>();
    private ListView listView;
    private SongListCustomAdapter adapter;
    public static String  URL = null;
    NetworkImageView nIVHeader;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    RelativeLayout rlMain;
    private int preLast;
    public  int feed;
    public int howmanyTime=0;
    public  int increment=0;

    public static int clearArrayListornot=0;

    Button musicOfTheWeek,banglaTopHits,bollyWoodTophits,englishTopHits;
    ImageView musicOfTheWeekImageView,banglaTopHitsImageView,bollyWoodTophitsImageView,englishTopHitsImageView;
    public static int swapIndicator=0;

    public static String contentCode, categoryCode, contentName, sContentType, sPhysicalFileName, ZedID, contentImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Log.d("Tracker", "This is AllItemLIstActivity Activity");
//        String headerText = getIntent().getExtras().getString("headerText");


        songTextAndImageView();//init the image and text view
        SongListCustomAdapter.songListAdapterContext=SongItemListActivity.this;

        listView = (ListView) findViewById(R.id.song_listView);
        rlMain = (RelativeLayout) findViewById(R.id.songRelativelayout);

        nIVHeader = (NetworkImageView) findViewById(R.id.nIVHeader);
       // tvCatName.setText(headerText);


        adapter = new SongListCustomAdapter(pictureList, this);

         banglaTopHits.performClick();

        listView.setAdapter(adapter);



        nIVHeader.setImageUrl("http://202.164.213.242/CMS/UIHeader/czapp/D480x800/clubz.jpg", imageLoader);//("http://202.164.213.242/CMS/UIHeader/D480x800/CZ_header.png", imageLoader);
        // ==============Start for ClubZ Apps Google Analytics For Each Activity
        // =================/
       *//* try {
            Tracker t = ((AppController) SongItemListActivity.this.getApplication()).getTracker(TrackerName.APP_TRACKER);
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





        listView.setOnTouchListener(new SwipeListener(this) {

            public void onSwipeRight() {
                Log.d("swap", "right Swap");
                switch (swapIndicator) {

                    case 0:
                        bollyWoodTophits.performClick();
                        swapIndicator = 1;
                        break;
                    case 1:
                        musicOfTheWeek.performClick();
                        swapIndicator = 2;
                        break;
                    case 2:
                        englishTopHits.performClick();
                        swapIndicator = 3;
                        break;
                    case 3:

                        break;


                }
            }

            public void onSwipeLeft() {
                Log.d("swap", "Left Swap");
                switch (swapIndicator) {

                    case 0:
                        break;
                    case 1:
                        banglaTopHits.performClick();
                        swapIndicator = 0;
                        break;
                    case 2:
                        bollyWoodTophits.performClick();
                        swapIndicator = 1;
                        break;
                    case 3:
                        musicOfTheWeek.performClick();
                        swapIndicator = 2;
                        break;


                }

            }


        });


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("songOnitemClick", "clicked");

                contentCode = pictureList.get(position).getContent_code();
                categoryCode = pictureList.get(position).getCategoryCode();
                contentName = pictureList.get(position).getContent_name();
                sContentType = pictureList.get(position).getsContentType();
                sPhysicalFileName = pictureList.get(position).getsPhysicalFileName();
                contentImg = pictureList.get(position).getContent_img();
                ZedID = pictureList.get(position).getZedID();
                adapter.contentCode=contentCode;


                Intent downloadIntent = new Intent(getApplicationContext(), PlaySongActivity.class);

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
                        pDialog = new ProgressDialog(SongItemListActivity.this);
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

                        Log.d("length", String.valueOf(result.length()));


                        if (howmanyContentTofetch<result.length() && result.length()>10) {
                            for (int i = increment*10; i < howmanyContentTofetch; i++) {    //result.length()
                                try {
                                    JSONObject obj = result.getJSONObject(i);
                                    SongListModel picture = new SongListModel();
                                    // Log.i(">>><<<",obj.toString());
                                    picture.setCategoryCode(obj
                                            .getString("catgorycode"));
                                    picture.setContent_code(obj
                                            .getString("contentcode"));
                                    picture.setContent_img(obj
                                            .getString("imageUrl"));
                                    picture.setContent_name(obj
                                            .getString("ContentTile"));
                                    picture.setsContentType(obj
                                            .getString("Type"));
                                    picture.setsPhysicalFileName(obj
                                            .getString("physicalfilename"));
                                    picture.setZedID(obj.getString("zid"));
                                    picture.setDownloadCount(obj.getString("Count"));
                                    picture.setRating(obj.getString("rating"));

                                    pictureList.add(picture);

                                    Log.i(">>>Content Image<<<",picture.getContent_img());

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            hidePDialog();
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            for (int i = 0; i < result.length(); i++) {    //result.length()
                                try {
                                    JSONObject obj = result.getJSONObject(i);
                                    SongListModel picture = new SongListModel();
                                    // Log.i(">>><<<",obj.toString());
                                    picture.setCategoryCode(obj
                                            .getString("catgorycode"));
                                    picture.setContent_code(obj
                                            .getString("contentcode"));
                                    picture.setContent_img(obj
                                            .getString("imageUrl"));
                                    picture.setContent_name(obj
                                            .getString("ContentTile"));
                                    picture.setsContentType(obj
                                            .getString("Type"));
                                    picture.setsPhysicalFileName(obj
                                            .getString("physicalfilename"));
                                    picture.setZedID(obj.getString("zid"));
                                    picture.setDownloadCount(obj.getString("Count"));
                                    picture.setRating(obj.getString("rating"));

                                    Log.i(">>>Content Image<<<",
                                            picture.getContent_img());

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            hidePDialog();

                            adapter.notifyDataSetChanged();
                        }

                        howmanyTime++;
                        increment++;
                        Log.d("howmanytimes", String.valueOf(howmanyTime));
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


    //==================Function for Int the text and image view====================

    public void songTextAndImageView(){

        //Textview
        musicOfTheWeek= (Button) findViewById(R.id.song_musicOfTheWeek);
        banglaTopHits= (Button) findViewById(R.id.song_b_top_hits);
        bollyWoodTophits= (Button) findViewById(R.id.song_bolly_tophits);
        englishTopHits= (Button) findViewById(R.id.song_eng_top_hits);
        //ImageView
        musicOfTheWeekImageView= (ImageView) findViewById(R.id.song_musicOfTheWeekImage);
        banglaTopHitsImageView= (ImageView) findViewById(R.id.song_b_top_hits_imageview);
        bollyWoodTophitsImageView= (ImageView) findViewById(R.id.song_bolly_tophits_imageiview);
        englishTopHitsImageView= (ImageView) findViewById(R.id.song_engtophits_imageview);


        musicOfTheWeek.setOnClickListener(this);
        banglaTopHits.setOnClickListener(this);
        bollyWoodTophits.setOnClickListener(this);
        englishTopHits.setOnClickListener(this);



    }



    @Override
    public void onClick(View v) {

        JsonArrayRequest mJsonarray;
        switch (v.getId()){

            case R.id.song_musicOfTheWeek:
               URL = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BFT&subcat=Music";//"http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=music_4";
                pDialog = new ProgressDialog(SongItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;

                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                swapIndicator=2;

                musicOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.white));
                banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));

                musicOfTheWeek.setBackgroundColor(getResources().getColor(R.color.black));
                banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));


                break;
            case R.id.song_b_top_hits:
                URL = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BFT&subcat=Banglatophits";//"http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=music_1";
                pDialog = new ProgressDialog(SongItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;

                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                swapIndicator=0;
                musicOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.white));
                bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                musicOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                banglaTopHits.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));


                break;
            case R.id.song_bolly_tophits:
                URL = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BFT&subcat=bolytophits";//"http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=music_3";
                pDialog = new ProgressDialog(SongItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                clearArrayListornot=0;

                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                swapIndicator=1;
                musicOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.white));
                englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));

                musicOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.black));
                englishTopHits.setBackgroundColor(getResources().getColor(R.color.grey));

                break;

            case R.id.song_eng_top_hits:
                URL ="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BFT&subcat=engtophits";//"http://203.76.126.210/clubz_android/datafeed3.php?callback=&&cat=music_2 ";
                pDialog = new ProgressDialog(SongItemListActivity.this);
                pDialog.setMessage("Loading...");
                clearArrayListornot=0;

                pDialog.show();
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                swapIndicator=3;
                musicOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                banglaTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodTophitsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                englishTopHitsImageView.setBackgroundColor(getResources().getColor(R.color.white));

                musicOfTheWeek.setBackgroundColor(getResources().getColor(R.color.grey));
                banglaTopHits.setBackgroundColor(getResources().getColor(R.color.grey));
                bollyWoodTophits.setBackgroundColor(getResources().getColor(R.color.grey));
                englishTopHits.setBackgroundColor(getResources().getColor(R.color.black));

                break;
        }
    }*/





}
