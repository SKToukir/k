package com.vumobile.clubzed.Picture_Sticker_Related;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.vumobile.clubzed.util.Download_Class;
import com.vumobile.clubzed.R;

public class StickerGridViewActivity extends TabActivity {

    private TabHost host;
    private ViewPager pager;
    GestureDetectorCompat gestureDetectorCompat;
    public int currentTabposition;
    public HorizontalScrollView horizontalScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
        horizontalScrollView= (HorizontalScrollView) findViewById(R.id.horizontalScrollView);


        host = (TabHost) findViewById(android.R.id.tabhost);
        // pager = (ViewPager) findViewById(R.id.pager);
        Download_Class.applicationContext = StickerGridViewActivity.this;
        //MyPagerAdapter.activity=PictureGridViewActivity.this;

        host.setup();
        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setContent(new Intent(this, Sticker_bangla.class));
        spec.setIndicator("Bangla");
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setContent(new Intent(this, Sticker_cricketbisshoCup.class));
        spec.setIndicator("   Cricket\nBissho Cup");
        host.addTab(spec);

        spec = host.newTabSpec("tab3");
        spec.setContent(new Intent(this, Sticker_Eid.class));
        spec.setIndicator("EID");
        host.addTab(spec);

        spec = host.newTabSpec("tab4");
        spec.setContent(new Intent(this, Sticker_Events.class));
        spec.setIndicator("Events");
        host.addTab(spec);

        spec = host.newTabSpec("tab5");
        spec.setContent(new Intent(this, Sticker_funny.class));
        spec.setIndicator("Funny");
        host.addTab(spec);

        spec = host.newTabSpec("tab6");
        spec.setContent(new Intent(this, Sticker_islamic.class));
        spec.setIndicator("Islamic");
        host.addTab(spec);

        spec = host.newTabSpec("tab7");
        spec.setContent(new Intent(this, Sticker_love.class));
        spec.setIndicator("Love");
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
                    if (currentTabposition!=6) {
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

















   /*     ActionBarActivity implements View.OnClickListener{

    String TAG="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=BANGLA";
    private GridView mGridView;
    private ProgressBar mProgressBar;
    public Button bangla, cricketBisshoCup, eid, events, funny, islamic, love;
    public ImageView banglaImageView, crickeBisshoCupImageView, eidImageView, eventsImageView, funnyImageView, islamicImageView, loveImageView;
    private PictureGridViewAdapter mGridAdapter;
    private ArrayList<PictureGridItemModel> mGridData;
    public static int swipeIndecator=0;

    private static String FEED_URL = "";//http://javatechig.com/?json=get_recent_posts&count=45";

    private int preLast;
    public  int feed;
    public int howmanyTime=0;
    public  int increment=0;
    public static int clearArrayListornot=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_grid_view);

        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        initializetheTextandImageView();




        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new PictureGridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);

        bangla.performClick();







        //Grid view click event
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                PictureGridItemModel item = (PictureGridItemModel) parent.getItemAtPosition(position);

                Intent intent = new Intent(StickerGridViewActivity.this, PictureDetailsActivity.class);
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);


                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);
                Log.d("screenLocation", String.valueOf(screenLocation));
                Log.d("screenLocation", String.valueOf(imageView.getY()));
                Log.d("screenPosition", String.valueOf(position));


                //Pass the image title and url to DetailsActivity
                intent.putExtra("left", screenLocation[0]).
                        putExtra("top", screenLocation[1]).
                        putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage()).
                        putExtra("contentCode", item.getContent_code()).
                        putExtra("categoryCode", item.getCategoryCode()).
                        putExtra("contentName", item.getContent_name()).
                        putExtra("sContentType", item.getsContentType()).
                        putExtra("sPhysicalFileName", item.getsPhysicalFileName()).
                        putExtra("contentImg", item.getContent_img()).
                        putExtra("ZedID", item.getZedID());

                startActivity(intent);
            }
        });





        //-------------------Code for Swiping function---------------

        mGridView.setOnTouchListener(new SwipeListener(this) {

            public void onSwipeRight() {
                Log.d("swip", "Right");
                switch (swipeIndecator) {

                    case 0:
                        cricketBisshoCup.performClick();
                        break;
                    case 1:
                        eid.performClick();
                        break;
                    case 2:
                        events.performClick();
                        break;
                    case 3:
                        funny.performClick();
                        break;
                    case 4:
                        islamic.performClick();
                        break;
                    case 5:
                        love.performClick();
                        break;
                    case 6:

                        break;

                }

            }

            public void onSwipeLeft() {
                switch (swipeIndecator) {

                    case 0:
                        break;
                    case 1:
                        bangla.performClick();
                        break;
                    case 2:
                        cricketBisshoCup.performClick();
                        break;
                    case 3:
                        eid.performClick();
                        break;

                    case 4:
                        events.performClick();
                        break;
                    case 5:
                        funny.performClick();
                        break;
                    case 6:
                        islamic.performClick();
                        break;

                }

            }

        });

        //================Scroll view listener=================


        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

                        //Start download
                        new AsyncHttpTask().execute(FEED_URL);
                        mProgressBar.setVisibility(View.VISIBLE);
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

        if (mGridData==null){
            feed=0;
        }
        if(feed==0) {
            new AsyncHttpTask().execute(FEED_URL);
            mProgressBar.setVisibility(View.VISIBLE);
            howmanyTime++;
            feed=1;
        }

    }

    //------------Click Events------------------
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.stc_bangle:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=BANGLA";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);

                banglaImageView.setBackgroundColor(getResources().getColor(R.color.white));
                crickeBisshoCupImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eidImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eventsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                funnyImageView.setBackgroundColor(getResources().getColor(R.color.black));
                islamicImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));


                bangla.setBackgroundColor(getResources().getColor((R.color.black)));
                cricketBisshoCup.setBackgroundColor(getResources().getColor((R.color.grey)));
                eid.setBackgroundColor(getResources().getColor((R.color.grey)));
                events.setBackgroundColor(getResources().getColor((R.color.grey)));
                funny.setBackgroundColor(getResources().getColor((R.color.grey)));
                islamic.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                swipeIndecator=0;

                break;

            case R.id.stc_crk_bisshocup:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=CBC";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);

                banglaImageView.setBackgroundColor(getResources().getColor(R.color.black));
                crickeBisshoCupImageView.setBackgroundColor(getResources().getColor(R.color.white));
                eidImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eventsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                funnyImageView.setBackgroundColor(getResources().getColor(R.color.black));
                islamicImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));


                bangla.setBackgroundColor(getResources().getColor((R.color.grey)));
                cricketBisshoCup.setBackgroundColor(getResources().getColor((R.color.black)));
                eid.setBackgroundColor(getResources().getColor((R.color.grey)));
                events.setBackgroundColor(getResources().getColor((R.color.grey)));
                funny.setBackgroundColor(getResources().getColor((R.color.grey)));
                islamic.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                swipeIndecator=1;
                break;

            case R.id.stk_eid:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=EID";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);

                banglaImageView.setBackgroundColor(getResources().getColor(R.color.black));
                crickeBisshoCupImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eidImageView.setBackgroundColor(getResources().getColor(R.color.white));
                eventsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                funnyImageView.setBackgroundColor(getResources().getColor(R.color.black));
                islamicImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));


                bangla.setBackgroundColor(getResources().getColor((R.color.grey)));
                cricketBisshoCup.setBackgroundColor(getResources().getColor((R.color.grey)));
                eid.setBackgroundColor(getResources().getColor((R.color.black)));
                events.setBackgroundColor(getResources().getColor((R.color.grey)));
                funny.setBackgroundColor(getResources().getColor((R.color.grey)));
                islamic.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                swipeIndecator=2;
                break;



            case R.id.stk_events:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=EVENTS";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);

                banglaImageView.setBackgroundColor(getResources().getColor(R.color.black));
                crickeBisshoCupImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eidImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eventsImageView.setBackgroundColor(getResources().getColor(R.color.white));
                funnyImageView.setBackgroundColor(getResources().getColor(R.color.black));
                islamicImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));


                bangla.setBackgroundColor(getResources().getColor((R.color.grey)));
                cricketBisshoCup.setBackgroundColor(getResources().getColor((R.color.grey)));
                eid.setBackgroundColor(getResources().getColor((R.color.grey)));
                events.setBackgroundColor(getResources().getColor((R.color.black)));
                funny.setBackgroundColor(getResources().getColor((R.color.grey)));
                islamic.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                swipeIndecator=3;
                break;
            case R.id.stc_funny:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=FUNNY";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);

                banglaImageView.setBackgroundColor(getResources().getColor(R.color.black));
                crickeBisshoCupImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eidImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eventsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                funnyImageView.setBackgroundColor(getResources().getColor(R.color.white));
                islamicImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));


                bangla.setBackgroundColor(getResources().getColor((R.color.grey)));
                cricketBisshoCup.setBackgroundColor(getResources().getColor((R.color.grey)));
                eid.setBackgroundColor(getResources().getColor((R.color.grey)));
                events.setBackgroundColor(getResources().getColor((R.color.grey)));
                funny.setBackgroundColor(getResources().getColor((R.color.black)));
                islamic.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                swipeIndecator=4;
                break;
            case R.id.stc_islamic:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=ISLAMIC";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);

                banglaImageView.setBackgroundColor(getResources().getColor(R.color.black));
                crickeBisshoCupImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eidImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eventsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                funnyImageView.setBackgroundColor(getResources().getColor(R.color.black));
                islamicImageView.setBackgroundColor(getResources().getColor(R.color.white));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));


                bangla.setBackgroundColor(getResources().getColor((R.color.grey)));
                cricketBisshoCup.setBackgroundColor(getResources().getColor((R.color.grey)));
                eid.setBackgroundColor(getResources().getColor((R.color.grey)));
                events.setBackgroundColor(getResources().getColor((R.color.grey)));
                funny.setBackgroundColor(getResources().getColor((R.color.grey)));
                islamic.setBackgroundColor(getResources().getColor((R.color.black)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                swipeIndecator=5;
                break;
            case R.id.stc_love:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=LOVE";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);

                banglaImageView.setBackgroundColor(getResources().getColor(R.color.black));
                crickeBisshoCupImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eidImageView.setBackgroundColor(getResources().getColor(R.color.black));
                eventsImageView.setBackgroundColor(getResources().getColor(R.color.black));
                funnyImageView.setBackgroundColor(getResources().getColor(R.color.black));
                islamicImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.white));


                bangla.setBackgroundColor(getResources().getColor((R.color.grey)));
                cricketBisshoCup.setBackgroundColor(getResources().getColor((R.color.grey)));
                eid.setBackgroundColor(getResources().getColor((R.color.grey)));
                events.setBackgroundColor(getResources().getColor((R.color.grey)));
                funny.setBackgroundColor(getResources().getColor((R.color.grey)));
                islamic.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.black)));
                swipeIndecator=6;
                break;

            default:
                break;
        }

    }


    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            Log.d("feedUrl",params[0]);
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
            } else {
                Toast.makeText(StickerGridViewActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            mProgressBar.setVisibility(View.GONE);
        }
    }


    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    *//**
     * Parsing the feed results and get the list
     *
     * @param result
     *//*
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("Table");
            PictureGridItemModel item;
            int howmanyContentTofetch=howmanyTime*10;
            if (clearArrayListornot==0) {
                mGridData.clear();
            }
            if (howmanyContentTofetch<posts.length() && result.length()>10){


                for (int i = increment*10; i < howmanyContentTofetch; i++) {
                    JSONObject post = posts.optJSONObject(i);
                    String title = post.optString("physicalfilename").replace("_"," ");
                    String imageUrl=post.optString("imageUrl");
                    item = new PictureGridItemModel();
                    item.setTitle(title);
                    item.setImage(imageUrl);
                    item.setCategoryCode(post.optString("catgorycode"));
                    item.setContent_code(post.optString("contentcode"));
                    item.setContent_img(post.optString("content_img"));
                    item.setContent_name(post.optString("ContentTile"));
                    item.setsContentType(post.optString("Type"));
                    item.setsPhysicalFileName(post.optString("physicalfilename"));
                    item.setZedID(post.optString("zid"));

                    Log.d("picGrid",post.optString("CategoryCode"));

               *//* JSONArray attachments = post.getJSONArray("attachments");
                if (null != attachments && attachments.length() > 0) {
                    JSONObject attachment = attachments.getJSONObject(0);
                    if (attachment != null)
                        item.setImage(attachment.getString("url"));
                }*//*

                    mGridData.add(item);
                }
            }

            else {

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);
                    String title = post.optString("physicalfilename").replace("_"," ");
                    String imageUrl=post.optString("imageUrl");
                    item = new PictureGridItemModel();
                    item.setTitle(title);
                    item.setImage(imageUrl);
                    item.setCategoryCode(post.optString("catgorycode"));
                    item.setContent_code(post.optString("contentcode"));
                    item.setContent_img(post.optString("content_img"));
                    item.setContent_name(post.optString("ContentTile"));
                    item.setsContentType(post.optString("Type"));
                    item.setsPhysicalFileName(post.optString("physicalfilename"));
                    item.setZedID(post.optString("zid"));

                    Log.d("picGrid",post.optString("CategoryCode"));

               *//* JSONArray attachments = post.getJSONArray("attachments");
                if (null != attachments && attachments.length() > 0) {
                    JSONObject attachment = attachments.getJSONObject(0);
                    if (attachment != null)
                        item.setImage(attachment.getString("url"));
                }*//*

                    mGridData.add(item);
                }


            }
            howmanyTime++;
            increment++;


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //------Method for Initializing the Text and Image view-------
    public void initializetheTextandImageView(){

        //----------Init the textView------------------

        bangla = (Button) findViewById(R.id.stc_bangle);
        cricketBisshoCup = (Button) findViewById(R.id.stc_crk_bisshocup);
        eid = (Button) findViewById(R.id.stk_eid);
        events = (Button) findViewById(R.id.stk_events);
        funny = (Button) findViewById(R.id.stc_funny);
        islamic = (Button) findViewById(R.id.stc_islamic);
        love = (Button) findViewById(R.id.stc_love);

        //-----------Init the imageView---------------

        banglaImageView = (ImageView) findViewById(R.id.stc_bangle_imageview);
        crickeBisshoCupImageView = (ImageView) findViewById(R.id.stc_crk_bisshocup_imageiview);
        eidImageView = (ImageView) findViewById(R.id.stk_eid_imageview);
        eventsImageView = (ImageView) findViewById(R.id.stk_events_imageview);
        funnyImageView = (ImageView) findViewById(R.id.stc_funny_imageView);
        islamicImageView = (ImageView) findViewById(R.id.stc_islamic_imageView);
        loveImageView = (ImageView) findViewById(R.id.stc_love_imageview);



        bangla.setOnClickListener(this);
        cricketBisshoCup.setOnClickListener(this);
        eid.setOnClickListener(this);
        events.setOnClickListener(this);
        funny.setOnClickListener(this);
        islamic.setOnClickListener(this);
        love.setOnClickListener(this);

    }

}
*/