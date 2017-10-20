package com.vumobile.clubzed.GameRelated;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by it-6 on 2/23/2016.
 */
public class Puzzle_Activity extends Activity {
    ListView actionListView;
    NetworkImageView actionPageHeader;
    String URL="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=BNP";
    public static int increment,howmanyTime,clearArrayListornot;
    ArrayList<GameListModel> pictureList=new ArrayList<GameListModel>();
    GameListCustomAdapter adapter;
    ProgressDialog pDialog;
    public  int feed=0;
    private int preLast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bnp_);
        actionListView= (ListView) findViewById(R.id.actionListView);
        pDialog=new ProgressDialog(this);
        actionPageHeader = (NetworkImageView) findViewById(R.id.actionPageHeader);

        adapter = new GameListCustomAdapter(pictureList,Puzzle_Activity.this);

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
                        pDialog = new ProgressDialog(Puzzle_Activity.this);
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

}
