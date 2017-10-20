package com.vumobile.clubzed.Picture_Sticker_Related;

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

public class PictureGridViewActivity  extends TabActivity {

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
        Download_Class.applicationContext = PictureGridViewActivity.this;
        //MyPagerAdapter.activity=PictureGridViewActivity.this;

        host.setup();
        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setContent(new Intent(this, Pic_Bangla_Celebrity.class));
        spec.setIndicator("Bangladeshi\n   Celebrity");
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setContent(new Intent(this, Pic_Bolly_Celebrity.class));
        spec.setIndicator("Bollywood\n" + " Celebrity");
        host.addTab(spec);

        spec = host.newTabSpec("tab3");
        spec.setContent(new Intent(this, Pic_cool.class));
        spec.setIndicator("Cool");
        host.addTab(spec);

        spec = host.newTabSpec("tab4");
        spec.setContent(new Intent(this, Pic_Speacial_Events.class));
        spec.setIndicator("Speacial");
        host.addTab(spec);

       /* spec = host.newTabSpec("tab5");
        spec.setContent(new Intent(PictureGridViewActivity.this, Pic_oftheWeek.class));
        spec.setIndicator("   Picture\nOf The Week");
        host.addTab(spec);*/

        spec = host.newTabSpec("tab6");
        spec.setContent(new Intent(this, Pic_Hoolywood_Celebrity.class));
        spec.setIndicator("Hollywood\n" + " Celebrity");
        host.addTab(spec);

        spec = host.newTabSpec("tab7");
        spec.setContent(new Intent(this, Pic_Love.class));
        spec.setIndicator("Love");
        host.addTab(spec);



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
                if (currentTabposition!=5) {
                    centerTabItem(currentTabposition + 1);
                }

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





/* ActionBarActivity  {
    private static final String TAG = PictureGridViewActivity.class.getSimpleName();

    private GridView mGridView;
    private ProgressBar mProgressBar;
    public Button pictureOfTheWeek,bangladeshiCelebrity,bollyWoodCelebrity,cool,hollywoodCelebrity,love,speacialEvent,sticker;
    public ImageView pictureOfTheWeekImageView,bangladeshiCelebrityImageView,bollyWoodCelebrityImageView,coolImageView,hollywoodCelebrityImageView,loveImageView,speacialEventImageView,stickerImageView;
    private PictureGridViewAdapter mGridAdapter;
    private ArrayList<PictureGridItemModel> mGridData;
    public static int swipeIndecator=0;

    private int preLast;
    public  int feed;
    public int howmanyTime=0;
    public  int increment=0;

    public static int clearArrayListornot=0;
    TabHost host;

    private static String FEED_URL = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=bangladeshiCelPic";//http://javatechig.com/?json=get_recent_posts&count=45";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        //initializetheTextandImageView();

        host= (TabHost) findViewById(R.id.pictureTab);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.linearLayout4);
        spec.setIndicator("Tab One");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.linearLayout5);
        spec.setIndicator("Tab Two");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.linearLayout6);
        spec.setIndicator("Tab Three");
        host.addTab(spec);




        host.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=holy";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                host.setCurrentTab(0);
            }
        });
        host.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=bangladeshiCelPic";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);

                host.setCurrentTab(0);
            }
        });
        host.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FEED_URL = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=boly";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);

                host.setCurrentTab(0);
            }
        });


        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new PictureGridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);







        //Grid view click event
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                PictureGridItemModel item = (PictureGridItemModel) parent.getItemAtPosition(position);

                Intent intent = new Intent(PictureGridViewActivity.this, PictureDetailsActivity.class);
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


        bangladeshiCelebrity.performClick();


      *//*  //-------------------Code for Swiping function---------------

        mGridView.setOnTouchListener(new SwipeListener(this) {

            public void onSwipeRight() {
                Log.d("swip", "Right");
                switch (swipeIndecator) {

                    case 0:
                        bollyWoodCelebrity.performClick();
                        break;
                    case 1:
                        cool.performClick();
                        break;
                    case 2:
                        pictureOfTheWeek.performClick();
                        break;
                    case 3:
                        hollywoodCelebrity.performClick();
                        break;
                    case 4:
                        love.performClick();
                        break;
                    case 5:
                        speacialEvent.performClick();
                        break;
                    case 6:
                        sticker.performClick();
                        break;
                    case 7:
                        pictureOfTheWeek.performLongClick();
                        break;

                }

            }

            public void onSwipeLeft() {
                switch (swipeIndecator) {

                    case 0:

                        break;
                    case 1:
                        bangladeshiCelebrity.performClick();
                        break;
                    case 2:
                        bollyWoodCelebrity.performClick();
                        break;
                    case 3:
                        cool.performClick();
                        break;
                    case 4:
                        pictureOfTheWeek.performClick();
                        break;
                    case 5:
                        hollywoodCelebrity.performClick();
                        break;
                    case 6:
                        love.performClick();
                        break;
                    case 7:
                        speacialEvent.performClick();
                        break;

                }

            }

        });*//*


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
        if(feed==0) {


            new AsyncHttpTask().execute(FEED_URL);
            mProgressBar.setVisibility(View.VISIBLE);;
            howmanyTime++;
            feed=1;
        }





    }

    //------------Click Events------------------
   *//* @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.pic_pictureOfTheWeek:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=picofweek";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);
                pictureOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.white));
                bangladeshiCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                coolImageView.setBackgroundColor(getResources().getColor(R.color.black));
                hollywoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));
                speacialEventImageView.setBackgroundColor(getResources().getColor(R.color.black));


                pictureOfTheWeek.setBackgroundColor(getResources().getColor((R.color.orange)));
                bangladeshiCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                bollyWoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                cool.setBackgroundColor(getResources().getColor((R.color.grey)));
                hollywoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                speacialEvent.setBackgroundColor(getResources().getColor((R.color.grey)));

                swipeIndecator=3;

                break;

            case R.id.pic_b_celebrity:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=bangladeshiCelPic";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);
                pictureOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bangladeshiCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.white));
                bollyWoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                coolImageView.setBackgroundColor(getResources().getColor(R.color.black));
                hollywoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));
                speacialEventImageView.setBackgroundColor(getResources().getColor(R.color.black));


                pictureOfTheWeek.setBackgroundColor(getResources().getColor((R.color.grey)));
                bangladeshiCelebrity.setBackgroundColor(getResources().getColor((R.color.orange)));
                bollyWoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                cool.setBackgroundColor(getResources().getColor((R.color.grey)));
                hollywoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                speacialEvent.setBackgroundColor(getResources().getColor((R.color.grey)));

                swipeIndecator=0;
                break;

            case R.id.pic_bolly_celebrity:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=boly";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);
                pictureOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bangladeshiCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.white));
                coolImageView.setBackgroundColor(getResources().getColor(R.color.black));
                hollywoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));
                speacialEventImageView.setBackgroundColor(getResources().getColor(R.color.black));


                pictureOfTheWeek.setBackgroundColor(getResources().getColor((R.color.grey)));
                bangladeshiCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                bollyWoodCelebrity.setBackgroundColor(getResources().getColor((R.color.orange)));
                cool.setBackgroundColor(getResources().getColor((R.color.grey)));
                hollywoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                speacialEvent.setBackgroundColor(getResources().getColor((R.color.grey)));

                swipeIndecator=1;
                break;



            case R.id.pic_cool:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=cool";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);
                pictureOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bangladeshiCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                coolImageView.setBackgroundColor(getResources().getColor(R.color.white));
                hollywoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));
                speacialEventImageView.setBackgroundColor(getResources().getColor(R.color.black));


                pictureOfTheWeek.setBackgroundColor(getResources().getColor((R.color.grey)));
                bangladeshiCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                bollyWoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                cool.setBackgroundColor(getResources().getColor((R.color.orange)));
                hollywoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                speacialEvent.setBackgroundColor(getResources().getColor((R.color.grey)));

                swipeIndecator=2;
                break;
            case R.id.pic_holly_celebrity:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=holy";
                clearArrayListornot = 0;
                 new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);
                pictureOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bangladeshiCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                coolImageView.setBackgroundColor(getResources().getColor(R.color.black));
                hollywoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.white));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));
                speacialEventImageView.setBackgroundColor(getResources().getColor(R.color.black));


                pictureOfTheWeek.setBackgroundColor(getResources().getColor((R.color.grey)));
                bangladeshiCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                bollyWoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                cool.setBackgroundColor(getResources().getColor((R.color.grey)));
                hollywoodCelebrity.setBackgroundColor(getResources().getColor((R.color.orange)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                speacialEvent.setBackgroundColor(getResources().getColor((R.color.grey)));

                swipeIndecator=4;
                break;
            case R.id.pic_love:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=love";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);
                pictureOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bangladeshiCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                coolImageView.setBackgroundColor(getResources().getColor(R.color.black));
                hollywoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.white));
                speacialEventImageView.setBackgroundColor(getResources().getColor(R.color.black));

                pictureOfTheWeek.setBackgroundColor(getResources().getColor((R.color.grey)));
                bangladeshiCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                bollyWoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                cool.setBackgroundColor(getResources().getColor((R.color.grey)));
                hollywoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.orange)));
                speacialEvent.setBackgroundColor(getResources().getColor((R.color.grey)));

                swipeIndecator=5;
                break;
            case R.id.pic_speacial_events:
                FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=specialevent";
                clearArrayListornot = 0;
                new AsyncHttpTask().execute(FEED_URL);
                mProgressBar.setVisibility(View.VISIBLE);
                pictureOfTheWeekImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bangladeshiCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                bollyWoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                coolImageView.setBackgroundColor(getResources().getColor(R.color.black));
                hollywoodCelebrityImageView.setBackgroundColor(getResources().getColor(R.color.black));
                loveImageView.setBackgroundColor(getResources().getColor(R.color.black));
                speacialEventImageView.setBackgroundColor(getResources().getColor(R.color.white));


                pictureOfTheWeek.setBackgroundColor(getResources().getColor((R.color.grey)));
                bangladeshiCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                bollyWoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                cool.setBackgroundColor(getResources().getColor((R.color.grey)));
                hollywoodCelebrity.setBackgroundColor(getResources().getColor((R.color.grey)));
                love.setBackgroundColor(getResources().getColor((R.color.grey)));
                speacialEvent.setBackgroundColor(getResources().getColor((R.color.orange)));

                swipeIndecator=6;
                break;


            default:
                break;
        }

    }

*//*
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
                Toast.makeText(PictureGridViewActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
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
   *//* public void initializetheTextandImageView(){

       //----------Init the textView------------------
        pictureOfTheWeek= (Button) findViewById(R.id.pic_pictureOfTheWeek);
        bangladeshiCelebrity= (Button) findViewById(R.id.pic_b_celebrity);
        bollyWoodCelebrity= (Button) findViewById(R.id.pic_bolly_celebrity);
        cool= (Button) findViewById(R.id.pic_cool);
        hollywoodCelebrity= (Button) findViewById(R.id.pic_holly_celebrity);
        love= (Button) findViewById(R.id.pic_love);
        speacialEvent= (Button) findViewById(R.id.pic_speacial_events);


        //-----------Init the imageView---------------
        pictureOfTheWeekImageView= (ImageView) findViewById(R.id.pic_picoftheweekImage);
        bangladeshiCelebrityImageView= (ImageView) findViewById(R.id.pic_b_celebrity_imageview);
        bollyWoodCelebrityImageView= (ImageView) findViewById(R.id.pic_bolly_celebrity_imageiview);
        coolImageView= (ImageView) findViewById(R.id.pic_cool_imageview);
        hollywoodCelebrityImageView= (ImageView) findViewById(R.id.pic_holly_celebrity_imageView);
        loveImageView= (ImageView) findViewById(R.id.pic_love_imageView);
        speacialEventImageView= (ImageView) findViewById(R.id.pic_speacial_event_imageView);
        stickerImageView= (ImageView) findViewById(R.id.pic_sticker_imageView);


        pictureOfTheWeek.setOnClickListener(this);
        bangladeshiCelebrity.setOnClickListener(this);
        bollyWoodCelebrity.setOnClickListener(this);
        cool.setOnClickListener(this);
        hollywoodCelebrity.setOnClickListener(this);
        love.setOnClickListener(this);
        speacialEvent.setOnClickListener(this);


    }
*//*
}*/