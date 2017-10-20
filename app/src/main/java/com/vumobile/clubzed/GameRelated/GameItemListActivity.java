package com.vumobile.clubzed.GameRelated;

import android.app.ActionBar;
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

public class GameItemListActivity extends TabActivity implements ViewPager.OnPageChangeListener {

    public TabHost host;
    private ViewPager pager;
    GestureDetectorCompat gestureDetectorCompat;
    private GestureDetector gestureScanner;
    public int currentTabposition;
    public TabHost.TabSpec spec;
    TabWidget tabWidget;
    public ActionBar ab;
    public HorizontalScrollView horizontalScrollView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        host = (TabHost) findViewById(android.R.id.tabhost);
        horizontalScrollView= (HorizontalScrollView) findViewById(R.id.horizontalScrollView);

        //ab=getSupportActionBar();


        tabWidget= (TabWidget) findViewById(android.R.id.tabs);
        host=getTabHost();

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());


       // pager = (ViewPager) findViewById(R.id.pager);
        Download_Class.applicationContext=GameItemListActivity.this;
        host.setup();

        spec = host.newTabSpec("tab1");
        spec.setContent(new Intent(this, Action_Activity.class));
        spec.setIndicator("Action");
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setContent(new Intent(this, Arcade_Activity.class));
        spec.setIndicator("Arcade");
        host.addTab(spec);

        spec = host.newTabSpec("tab3");
        spec.setContent(new Intent(this, Sport_Activity.class));
        spec.setIndicator("Sports");
        host.addTab(spec);

        spec = host.newTabSpec("tab4");
        spec.setContent(new Intent(this, Cards_Activity.class));
        spec.setIndicator("Top Games");
        host.addTab(spec);

        spec = host.newTabSpec("tab5");
        spec.setContent(new Intent(this, Racing_Activity.class));
        spec.setIndicator("Racing");
        host.addTab(spec);

        spec = host.newTabSpec("tab6");
        spec.setContent(new Intent(this, GameOfTheWeek.class));
        spec.setIndicator("Game Of\nThe Week");
        host.addTab(spec);

        spec = host.newTabSpec("tab7");
        spec.setContent(new Intent(this, Puzzle_Activity.class));
        spec.setIndicator("Puzzle");
        host.addTab(spec);





    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
                    if (currentTabposition!=6) {
                        centerTabItem(currentTabposition + 1);
                    }
                    //getSupportActionBar().setSelectedNavigationItem(position);;


                }
            }
            if (event1.getX() < event2.getX()) {

                float a = event2.getX() - event1.getX();


                if (a >= 300) {
                    Log.d("MyGestureListener", "left to right");
                    host.setCurrentTab(currentTabposition - 1);

                    if (currentTabposition!=0) {
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



    /*public ProgressDialog pDialog;
    private List<GameListModel> pictureList = new ArrayList<GameListModel>();
    private ListView listView,listView2,listView3;
    private GameListCustomAdapter adapter;
    public static String URL = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=Action";

    NetworkImageView nIVHeader;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    RelativeLayout rlMain;

    TextView tvCatName;
    private int preLast;
    public  int feed;
    public int howmanyTime=0;
    public  int increment=0;

    public static int clearArrayListornot=0;

    public static String contentCode, categoryCode, contentName, sContentType, sPhysicalFileName, ZedID, contentImg;
    public Button gameoftheweek,action,arcade,brainNpuzzle,sports,cards,racing;
    public ImageView gameoftheweekImageView,actionImageView,arcadeImageView,brainNpuzzleImageView,sportsImageView,cardsImageVeiw,racingImgageView;
    public static  int swapIndicator=0;
    public static String model;
    public static String manufacture;
    public static String brand;
    public static int dimWidth,dimHeight;


    TabHost host;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d("Tracker", "This is AllItemLIstActivity Activity");

        //----------------Init text and image view-------------
        //initGametextandimageview();
        Download_Class.applicationContext=GameItemListActivity.this;



        rlMain = (RelativeLayout) findViewById(R.id.rlMain);

        nIVHeader = (NetworkImageView) findViewById(R.id.gamenIVHeader);

        *//**//*action.performLongClick();*//**//*

        adapter = new GameListCustomAdapter(pictureList, this);



        host= (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.linearLayout);
        spec.setIndicator("Tab One");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.linearLayout2);
        spec.setIndicator("Tab Two");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.linearLayout3);
        spec.setIndicator("Tab Three");
        host.addTab(spec);




        host.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonArrayRequest mJsonarray = jsonCall();
                Log.d("tab1","Tab1 clicked");
                URL = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=gameofweek";
                AppController.getInstance().addToRequestQueue(mJsonarray);//(mainJsonArray);
                listView = (ListView) findViewById(R.id.gameListView);
                listView.setAdapter(adapter);
                host.setCurrentTab(0);
            }
        });
        host.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonArrayRequest mJsonarray = jsonCall();
                Log.d("tab1","Tab2 clicked");

                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=Action";
                AppController.getInstance().addToRequestQueue(mJsonarray);//(mainJsonArray);
                listView2 = (ListView) findViewById(R.id.listView2);
                listView2.setAdapter(adapter);
                host.setCurrentTab(0);
            }
        });
        host.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonArrayRequest mJsonarray = jsonCall();
                Log.d("tab1","Tab3 clicked");
                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=Arcade";
                AppController.getInstance().addToRequestQueue(mJsonarray);//(mainJsonArray);
                listView3 = (ListView) findViewById(R.id.listView3);
                listView3.setAdapter(adapter);
                host.setCurrentTab(0);
            }
        });



        //listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
       *//**//* pDialog.setMessage("Loading...");
        pDialog.show();*//**//*

//        action.performClick();


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






        nIVHeader.setImageUrl("http://202.164.213.242/CMS/UIHeader/czapp/D480x800/clubz.jpg", imageLoader);//("http://202.164.213.242/CMS/UIHeader/D480x800/CZ_header.png", imageLoader);
        // ==============Start for ClubZ Apps Google Analytics For Each Activity
      *//**//*  // =================/
        try {
            Tracker t = ((AppController) GameItemListActivity.this.getApplication()).getTracker(TrackerName.APP_TRACKER);
            // Set screen name.
            // Where path is a String representing the screen name.
            t.setScreenName("ClubZ List Activity");
            Log.i("ClubZ Analytics True", "True");
            // Send a screen view.
            t.send(new HitBuilders.ScreenViewBuilder().build());
        } catch (Exception ex) {
            // TODO: handle exception
            Log.i("ClubZ Analytics Error", ex.getMessage());
        }*//**//*
        // ==============End for ClubZ Apps Google Analytics For Each Activity
        // =================/
      *//**//*  listView.setOnItemClickListener(new OnItemClickListener() {

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

                Intent downloadIntent = new Intent(getApplicationContext(), MainActivity.class);

                downloadIntent.putExtra("contentCode", contentCode);
                downloadIntent.putExtra("categoryCode", categoryCode);
                downloadIntent.putExtra("contentName", contentName);
                downloadIntent.putExtra("sContentType", sContentType);
                downloadIntent.putExtra("sPhysicalFileName", sPhysicalFileName);
                downloadIntent.putExtra("contentImg", contentImg);
                downloadIntent.putExtra("ZedID", ZedID);

                startActivity(downloadIntent);

            }
        });*//**//*
        //================Scroll view listener=================


        *//**//*listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                        clearArrayListornot=1;

                        JsonArrayRequest mJsonarray = jsonCall();
                        pDialog = new ProgressDialog(GameItemListActivity.this);
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
        });*//**//*
        if(feed==0) {
            JsonArrayRequest mJsonarray = jsonCall();
            AppController.getInstance().addToRequestQueue(mJsonarray);//(mainJsonArray);
            pDialog.setMessage("Loading...");
            pDialog.show();
            howmanyTime++;
            feed=1;
        }




        //-------------------Code for Swiping function---------------

        *//**//*listView.setOnTouchListener(new SwipeListener(this) {

            public void onSwipeRight() {
                Log.d("swip", "Right");
                switch (swapIndicator) {

                    case 0:
                        arcade.performClick();
                        break;
                    case 1:
                        sports.performClick();
                        break;
                    case 2:
                        cards.performClick();
                        break;
                    case 3:
                        racing.performClick();
                        break;
                    case 4:
                        gameoftheweek.performClick();
                        break;
                    case 5:
                        brainNpuzzle.performClick();
                        break;
                    case 6:
                        break;

                }

            }

            public void onSwipeLeft() {
                switch (swapIndicator) {

                    case 0:
                        break;
                    case 1:
                        action.performClick();
                        break;
                    case 2:
                        arcade.performClick();
                        break;
                    case 3:
                        sports.performClick();
                        break;
                    case 4:
                        cards.performClick();
                        break;
                    case 5:
                        racing.performClick();
                        break;
                    case 6:
                        gameoftheweek.performClick();
                        break;


                }

            }

        });*//**//*



    }


    public JsonArrayRequest jsonCall(){

        if (clearArrayListornot==0) {
            pictureList.clear();
        } else {
        }


        JsonArrayRequest mainJsonArray = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {




                    @Override
                    public void onResponse(JSONArray result) {

                        int howmanyContentTofetch=howmanyTime*10;

                        Log.d("howmanytimes",String.valueOf(howmanyContentTofetch));
                        // TODO Auto-generated method stub

                        Log.d("length", String.valueOf(result.length()));
                        hidePDialog();

                        if (howmanyContentTofetch<result.length() && result.length()>10) {
                            for (int i = increment*10; i < howmanyContentTofetch; i++) {    //result.length()
                                try {
                                    JSONObject obj = result.getJSONObject(i);
                                    GameListModel picture = new GameListModel();
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
                                    picture.setBanner_image(obj.getString("bigimage"));


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
                        else {
                            for (int i = 0; i < result.length(); i++) {    //result.length()
                                try {
                                    JSONObject obj = result.getJSONObject(i);
                                    GameListModel picture = new GameListModel();
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
                                    picture.setBanner_image(obj.getString("bigimage"));

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


    *//**//*public void initGametextandimageview(){

         gameoftheweek= (Button) findViewById(R.id.game_gameOfTheWeek);
         action= (Button) findViewById(R.id.game_action);
         arcade= (Button) findViewById(R.id.game_arcade);
         brainNpuzzle= (Button) findViewById(R.id.game_bnp);
         sports= (Button) findViewById(R.id.game_sports);
        cards= (Button) findViewById(R.id.game_cards);
        racing=(Button) findViewById(R.id.game_racing);

        gameoftheweekImageView= (ImageView) findViewById(R.id.game_gameoftheweekImage);
        actionImageView= (ImageView) findViewById(R.id.game_action_imageview);
        arcadeImageView= (ImageView) findViewById(R.id.game_arcade_imageiview);
        brainNpuzzleImageView= (ImageView) findViewById(R.id.game_bnp_imageview);
        sportsImageView= (ImageView) findViewById(R.id.game_sport_imageView);
        cardsImageVeiw=(ImageView) findViewById(R.id.game_cards_imageView);
        racingImgageView=(ImageView) findViewById(R.id.game_racing_imageView);

        gameoftheweek.setOnClickListener(this);
        action.setOnClickListener(this);
        arcade.setOnClickListener(this);
        brainNpuzzle.setOnClickListener(this);
        sports.setOnClickListener(this);
        cards.setOnClickListener(this);
        racing.setOnClickListener(this);
    }*//**//*

*//**//*//**//*//*--------------On click events-----------------
    @Override
    public void onClick(View v) {
        JsonArrayRequest mJsonarray;


        switch (v.getId()){

            case R.id.game_gameOfTheWeek:
                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=gameofweek";
                clearArrayListornot=0;
                gameoftheweekImageView.setBackgroundColor(getResources().getColor(R.color.white));
                actionImageView.setBackgroundColor(getResources().getColor(R.color.black));
                arcadeImageView.setBackgroundColor(getResources().getColor(R.color.black));
                brainNpuzzleImageView.setBackgroundColor(getResources().getColor(R.color.black));
                sportsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                cardsImageVeiw.setBackgroundColor(getResources().getColor(R.color.black));
                racingImgageView.setBackgroundColor(getResources().getColor(R.color.black));
                pDialog = new ProgressDialog(GameItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                gameoftheweek.setBackgroundColor(getResources().getColor(R.color.black));
                action.setBackgroundColor(getResources().getColor(R.color.grey));
                arcade.setBackgroundColor(getResources().getColor(R.color.grey));
                brainNpuzzle.setBackgroundColor(getResources().getColor(R.color.grey));
                sports.setBackgroundColor(getResources().getColor(R.color.grey));
                cards.setBackgroundColor(getResources().getColor(R.color.grey));
                racing.setBackgroundColor(getResources().getColor(R.color.grey));
                swapIndicator=5;


                break;
            case R.id.game_action:
                clearArrayListornot=0;
                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=Action";
                gameoftheweekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                actionImageView.setBackgroundColor(getResources().getColor(R.color.white));
                arcadeImageView.setBackgroundColor(getResources().getColor(R.color.black));
                brainNpuzzleImageView.setBackgroundColor(getResources().getColor(R.color.black));
                sportsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                cardsImageVeiw.setBackgroundColor(getResources().getColor(R.color.black));
                racingImgageView.setBackgroundColor(getResources().getColor(R.color.black));
                pDialog = new ProgressDialog(GameItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                gameoftheweek.setBackgroundColor(getResources().getColor(R.color.grey));
                action.setBackgroundColor(getResources().getColor(R.color.black));
                arcade.setBackgroundColor(getResources().getColor(R.color.grey));
                brainNpuzzle.setBackgroundColor(getResources().getColor(R.color.grey));
                sports.setBackgroundColor(getResources().getColor(R.color.grey));
                cards.setBackgroundColor(getResources().getColor(R.color.grey));
                racing.setBackgroundColor(getResources().getColor(R.color.grey));
                cards.setBackgroundColor(getResources().getColor(R.color.grey));
                racing.setBackgroundColor(getResources().getColor(R.color.grey));
                swapIndicator=0;
                break;
            case R.id.game_arcade:
                clearArrayListornot=0;
                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=Arcade";
                gameoftheweekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                actionImageView.setBackgroundColor(getResources().getColor(R.color.black));
                arcadeImageView.setBackgroundColor(getResources().getColor(R.color.white));
                brainNpuzzleImageView.setBackgroundColor(getResources().getColor(R.color.black));
                sportsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                cardsImageVeiw.setBackgroundColor(getResources().getColor(R.color.black));
                racingImgageView.setBackgroundColor(getResources().getColor(R.color.black));
                pDialog = new ProgressDialog(GameItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                gameoftheweek.setBackgroundColor(getResources().getColor(R.color.grey));
                action.setBackgroundColor(getResources().getColor(R.color.grey));
                arcade.setBackgroundColor(getResources().getColor(R.color.black));
                brainNpuzzle.setBackgroundColor(getResources().getColor(R.color.grey));
                sports.setBackgroundColor(getResources().getColor(R.color.grey));
                cards.setBackgroundColor(getResources().getColor(R.color.grey));
                racing.setBackgroundColor(getResources().getColor(R.color.grey));
                swapIndicator=1;
                break;
            case R.id.game_bnp:
                clearArrayListornot=0;
                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BNP";
                gameoftheweekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                actionImageView.setBackgroundColor(getResources().getColor(R.color.black));
                arcadeImageView.setBackgroundColor(getResources().getColor(R.color.black));
                brainNpuzzleImageView.setBackgroundColor(getResources().getColor(R.color.white));
                sportsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                cardsImageVeiw.setBackgroundColor(getResources().getColor(R.color.black));
                racingImgageView.setBackgroundColor(getResources().getColor(R.color.black));

                pDialog = new ProgressDialog(GameItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                gameoftheweek.setBackgroundColor(getResources().getColor(R.color.grey));
                action.setBackgroundColor(getResources().getColor(R.color.grey));
                arcade.setBackgroundColor(getResources().getColor(R.color.grey));
                brainNpuzzle.setBackgroundColor(getResources().getColor(R.color.black));
                sports.setBackgroundColor(getResources().getColor(R.color.grey));
                cards.setBackgroundColor(getResources().getColor(R.color.grey));
                racing.setBackgroundColor(getResources().getColor(R.color.grey));
                swapIndicator=6;
                break;
            case R.id.game_sports:
                clearArrayListornot=0;
                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BNP";
                gameoftheweekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                actionImageView.setBackgroundColor(getResources().getColor(R.color.black));
                arcadeImageView.setBackgroundColor(getResources().getColor(R.color.black));
                brainNpuzzleImageView.setBackgroundColor(getResources().getColor(R.color.black));
                sportsImageView.setBackgroundColor(getResources().getColor(R.color.white));
                cardsImageVeiw.setBackgroundColor(getResources().getColor(R.color.black));
                racingImgageView.setBackgroundColor(getResources().getColor(R.color.black));
                pDialog = new ProgressDialog(GameItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);

                gameoftheweek.setBackgroundColor(getResources().getColor(R.color.grey));
                action.setBackgroundColor(getResources().getColor(R.color.grey));
                arcade.setBackgroundColor(getResources().getColor(R.color.grey));
                brainNpuzzle.setBackgroundColor(getResources().getColor(R.color.grey));
                sports.setBackgroundColor(getResources().getColor(R.color.black));
                cards.setBackgroundColor(getResources().getColor(R.color.grey));
                racing.setBackgroundColor(getResources().getColor(R.color.grey));
                swapIndicator=2;
                break;

            case R.id.game_cards:
                clearArrayListornot=0;
                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=Topgame";
                gameoftheweekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                actionImageView.setBackgroundColor(getResources().getColor(R.color.black));
                arcadeImageView.setBackgroundColor(getResources().getColor(R.color.black));
                brainNpuzzleImageView.setBackgroundColor(getResources().getColor(R.color.black));
                sportsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                cardsImageVeiw.setBackgroundColor(getResources().getColor(R.color.white));
                racingImgageView.setBackgroundColor(getResources().getColor(R.color.black));
                pDialog = new ProgressDialog(GameItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                gameoftheweek.setBackgroundColor(getResources().getColor(R.color.grey));
                action.setBackgroundColor(getResources().getColor(R.color.grey));
                arcade.setBackgroundColor(getResources().getColor(R.color.grey));
                brainNpuzzle.setBackgroundColor(getResources().getColor(R.color.grey));
                sports.setBackgroundColor(getResources().getColor(R.color.grey));
                cards.setBackgroundColor(getResources().getColor(R.color.black));
                racing.setBackgroundColor(getResources().getColor(R.color.grey));
                swapIndicator=3;


                break;

            case R.id.game_racing:
                clearArrayListornot=0;
                URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=Racing";
                gameoftheweekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                actionImageView.setBackgroundColor(getResources().getColor(R.color.black));
                arcadeImageView.setBackgroundColor(getResources().getColor(R.color.black));
                brainNpuzzleImageView.setBackgroundColor(getResources().getColor(R.color.black));
                sportsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                cardsImageVeiw.setBackgroundColor(getResources().getColor(R.color.black));
                racingImgageView.setBackgroundColor(getResources().getColor(R.color.white));
                pDialog = new ProgressDialog(GameItemListActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();
                mJsonarray = jsonCall();
                AppController.getInstance().addToRequestQueue(mJsonarray);
                gameoftheweek.setBackgroundColor(getResources().getColor(R.color.grey));
                action.setBackgroundColor(getResources().getColor(R.color.grey));
                arcade.setBackgroundColor(getResources().getColor(R.color.grey));
                brainNpuzzle.setBackgroundColor(getResources().getColor(R.color.grey));
                sports.setBackgroundColor(getResources().getColor(R.color.grey));
                cards.setBackgroundColor(getResources().getColor(R.color.grey));
                racing.setBackgroundColor(getResources().getColor(R.color.black));
                swapIndicator=4;


                break;

        }

    }*/

