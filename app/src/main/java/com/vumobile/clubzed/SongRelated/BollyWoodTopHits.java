package com.vumobile.clubzed.SongRelated;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.util.Subscriptio_Class;
import com.vumobile.clubzed.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BollyWoodTopHits extends Activity {
    ListView actionListView;
    NetworkImageView actionPageHeader;
    public  String URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BFT&subcat=bolytophits";
    public static int increment,howmanyTime,clearArrayListornot;
    ArrayList<SongListModel> pictureList=new ArrayList<SongListModel>();
    SongListCustomAdapter adapter;
    ProgressDialog pDialog;
    public  int feed=0;
    Subscriptio_Class subscriptio_class;
    private int preLast;
    public TextView nocontent;
    public static String contentCode, categoryCode, contentName, sContentType, sPhysicalFileName, ZedID, contentImg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolly_wood_top_hits);
        SongListCustomAdapter.songListAdapterContext=BollyWoodTopHits.this;
        actionListView= (ListView) findViewById(R.id.actionListView);
        nocontent= (TextView) findViewById(R.id.notAvailable);
        Subscriptio_Class.applicationContext=BollyWoodTopHits.this;
        subscriptio_class=new Subscriptio_Class();
        Subscriptio_Class.type="song";


        pDialog=new ProgressDialog(this);
        actionPageHeader = (NetworkImageView) findViewById(R.id.actionPageHeader);

        adapter = new SongListCustomAdapter(pictureList,BollyWoodTopHits.this);

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
                        pDialog = new ProgressDialog(BollyWoodTopHits.this);
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
            pDialog.setCancelable(false);
            pDialog.show();
            howmanyTime++;
            feed=1;
        }


        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("songOnitemClick", "clicked");

                PlaySongActivity.contentCode = pictureList.get(position).getContent_code();
                PlaySongActivity.categoryCode = pictureList.get(position).getCategoryCode();
                PlaySongActivity.contentName = pictureList.get(position).getContent_name();
                PlaySongActivity.sContentType = pictureList.get(position).getsContentType();
                PlaySongActivity.sPhysicalFileName = pictureList.get(position).getsPhysicalFileName();
                PlaySongActivity.contentImg = pictureList.get(position).getContent_img();
                PlaySongActivity.ZedID = pictureList.get(position).getZedID();
                adapter.contentCode=contentCode;


                subscriptio_class.detectMSISDN();

               /* contentCode = pictureList.get(position).getContent_code();
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

                startActivity(downloadIntent);*/
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

                        Log.d("length", String.valueOf(result.length()));

                        if (result.length()==0){
                            hidePDialog();
                            nocontent.setVisibility(View.VISIBLE);
                        }
                        else if (howmanyContentTofetch<result.length() && result.length()>10) {
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

                /*Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();*/
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

}
