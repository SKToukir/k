package com.vumobile.clubzed.SongRelated;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

public class MusicOfTheWeek  extends Activity {
    ListView actionListView;
    NetworkImageView actionPageHeader;
    public  String URL =  "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BFT&subcat=Music";
    public static int increment,howmanyTime,clearArrayListornot;
    ArrayList<SongListModel> pictureList=new ArrayList<SongListModel>();
    SongListCustomAdapter adapter;
    Subscriptio_Class subscriptio_class;
    ProgressDialog pDialog;
    public  int feed=0;
    private int preLast;
    public static String contentCode, categoryCode, contentName, sContentType, sPhysicalFileName, ZedID, contentImg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_of_the_week);
        //VideoListCustomAdapter.songListAdapterContext=Video_BanglaTopHits.this;
        actionListView= (ListView) findViewById(R.id.actionListView);
        pDialog=new ProgressDialog(this);
        actionPageHeader = (NetworkImageView) findViewById(R.id.actionPageHeader);
        Subscriptio_Class.applicationContext=MusicOfTheWeek.this;
        subscriptio_class=new Subscriptio_Class();
        Subscriptio_Class.type="song";
        adapter = new SongListCustomAdapter(pictureList,MusicOfTheWeek.this);

        actionListView.setAdapter(adapter);


        //================Scroll view listener=================




        JsonArrayRequest mJsonarray = jsonCall();
        AppController.getInstance().addToRequestQueue(mJsonarray);//(mainJsonArray);
        pDialog.setMessage("Loading...");
        pDialog.show();
        howmanyTime++;
        feed=1;



        actionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                PlaySongActivity.contentCode = pictureList.get(position).getContent_code();
                PlaySongActivity.categoryCode = pictureList.get(position).getCategoryCode();
                PlaySongActivity.contentName = pictureList.get(position).getContent_name();
                PlaySongActivity.sContentType = pictureList.get(position).getsContentType();
                PlaySongActivity.sPhysicalFileName = pictureList.get(position).getsPhysicalFileName();
                PlaySongActivity.contentImg = pictureList.get(position).getContent_img();
                PlaySongActivity.ZedID = pictureList.get(position).getZedID();
                adapter.contentCode=contentCode;


                subscriptio_class.detectMSISDN();
/*
                contentCode = pictureList.get(position).getContent_code();
                categoryCode = pictureList.get(position).getCategoryCode();
                contentName = pictureList.get(position).getContent_name();
                sContentType = pictureList.get(position).getsContentType();
                sPhysicalFileName = pictureList.get(position).getsPhysicalFileName();
                contentImg = pictureList.get(position).getContent_img();
                ZedID = pictureList.get(position).getZedID();

                Intent downloadIntent = new Intent(getApplicationContext(), PlaySongActivity.class);//need to change

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

                        Log.d("length",String.valueOf(result.length()));
                        hidePDialog();

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
                                pictureList.add(picture);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data

                        adapter.notifyDataSetChanged();


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