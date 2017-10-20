package com.vumobile.clubzed.VideoRelated;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Video_CelebrityInterview  extends Activity {
    ListView actionListView;
    NetworkImageView actionPageHeader;
    public  String URL = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=video_Cat&subcat=CellInterview";
    public static int increment,howmanyTime,clearArrayListornot;
    ArrayList<VideoListModel> pictureList=new ArrayList<VideoListModel>();
    VideoListCustomAdapter adapter;
    Subscriptio_Class subscriptio_class;
    ProgressDialog pDialog;
    public  int feed=0;
    private int preLast;
    public static String contentCode, categoryCode, contentName, sContentType, sPhysicalFileName, ZedID, contentImg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__bangla_top_hits);
        //VideoListCustomAdapter.songListAdapterContext=Video_BanglaTopHits.this;
        actionListView= (ListView) findViewById(R.id.actionListView);
        pDialog=new ProgressDialog(this);
        actionPageHeader = (NetworkImageView) findViewById(R.id.actionPageHeader);
        Subscriptio_Class.applicationContext=Video_CelebrityInterview.this;
        subscriptio_class=new Subscriptio_Class();
        Subscriptio_Class.type="video";
        adapter = new VideoListCustomAdapter(pictureList,Video_CelebrityInterview.this);

        actionListView.setAdapter(adapter);


        //================Scroll view listener=================


        actionListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

                        JsonArrayRequest mJsonarray = jsonCall();
                        pDialog = new ProgressDialog(Video_CelebrityInterview.this);
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

        if (pictureList==null){
            feed=0;
        }
        if(feed==0) {
            JsonArrayRequest mJsonarray = jsonCall();
            AppController.getInstance().addToRequestQueue(mJsonarray);//(mainJsonArray);
            pDialog.setMessage("Loading...");
            pDialog.show();
            howmanyTime++;
            feed=1;
        }


        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                VideoPreViewActivity.contentCode = pictureList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = pictureList.get(position).getCategoryCode();
                VideoPreViewActivity.contentName = pictureList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = pictureList.get(position).getsContentType();
                VideoPreViewActivity.sPhysicalFileName = pictureList.get(position).getsPhysicalFileName();
                VideoPreViewActivity.contentImg = pictureList.get(position).getContent_img();
                VideoPreViewActivity.ZedID = pictureList.get(position).getZedID();
                subscriptio_class.detectMSISDN();



            }
        });
    }



    public JsonArrayRequest jsonCall(){

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



    /*public JsonArrayRequest jsonCall(){

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
    }*/

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

}