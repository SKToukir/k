package com.vumobile.clubzed.Picture_Sticker_Related;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vumobile.clubzed.SwipeListener;
import com.vumobile.clubzed.util.Subscriptio_Class;
import com.vumobile.clubzed.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Sticker_funny extends ActionBarActivity {

    String TAG = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=BANGLA";
    private GridView mGridView;
    private ProgressBar mProgressBar;
    public Button bangla, cricketBisshoCup, eid, events, funny, islamic, love;
    public ImageView banglaImageView, crickeBisshoCupImageView, eidImageView, eventsImageView, funnyImageView, islamicImageView, loveImageView;
    private PictureGridViewAdapter mGridAdapter;
    private ArrayList<PictureGridItemModel> mGridData;
    public static int swipeIndecator = 0;
    public static boolean  onscroll=true;
    private static String FEED_URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stk&subcat=FUNNY";

    private int preLast;
    public int feed;
    public int howmanyTime = 0;
    public int increment = 0;
    public static int clearArrayListornot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic__bangla__celebrity);

        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        Subscriptio_Class.applicationContext=Sticker_funny.this;

        Subscriptio_Class.type="pic";

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new PictureGridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);



        //-------------------Code for Swiping function---------------

        mGridView.setOnTouchListener(new SwipeListener(this) {

            public void onSwipeRight() {
                Log.d("swip", "Right");

            }

            public void onSwipeLeft() {
                Log.d("swip", "Left");
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

                        if (onscroll=true) {
                            //Start download
                            new AsyncHttpTask().execute(FEED_URL);
                            mProgressBar.setVisibility(View.VISIBLE);
                            preLast = lastItem;
                        }


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
        if(mGridData==null){
            feed=0;
        }

        if (feed == 0) {
            new AsyncHttpTask().execute(FEED_URL);
            mProgressBar.setVisibility(View.VISIBLE);
            howmanyTime++;
            feed = 1;
        }

    }


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
                Toast.makeText(Sticker_funny.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
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

    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("Table");
            PictureGridItemModel item;
            int howmanyContentTofetch=howmanyTime*20;
            if (clearArrayListornot==0) {
                mGridData.clear();
            }
            if (howmanyContentTofetch<posts.length() && result.length()>20){


                for (int i = increment*20; i < howmanyContentTofetch; i++) {
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
                    Log.d("picGrid", post.optString("CategoryCode"));
                    onscroll=false;

                    mGridData.add(item);
                }


            }
            howmanyTime++;
            increment++;


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

