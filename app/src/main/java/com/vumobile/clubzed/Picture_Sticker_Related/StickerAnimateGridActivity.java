package com.vumobile.clubzed.Picture_Sticker_Related;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.AnimatedGridAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.model.StickerAnimated;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StickerAnimateGridActivity extends ActionBarActivity {
    private PullToRefreshGridView mPullRefreshGridView;
    Subscriptio_Class subscriptio_class;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_STICKER_ANIMATED;
    //Tag values to read from json
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";

    //GridView Object
    private GridView gridView;
    int n = 0;
    //ArrayList for Storing image urls and titles
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> likes;
    private ArrayList<String> downloads;
    List<StickerAnimated> stickerAnimatedList = new ArrayList<StickerAnimated>();
    StickerAnimated stickerAnimatedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_animate_grid);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_grid_stickerAnimate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewgrid_stickerAnimate);
        gridView = mPullRefreshGridView.getRefreshableView();

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                likes = new ArrayList<>();
                downloads = new ArrayList<String>();
                stickerAnimatedList = new ArrayList<StickerAnimated>();
                n+=10;
                getData(n);
            }

//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
//                //Toast.makeText(DramaGridActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
////                images = new ArrayList<>();
////                names = new ArrayList<>();
////                dramaClipsHomeList = new ArrayList<DramaClipsHome>();
////                n+=10;
////                getData(n);
//                mPullRefreshGridView.onRefreshComplete();
//            }

//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
//                //Toast.makeText(DramaGridActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
//                images = new ArrayList<>();
//                names = new ArrayList<>();
//                dramaClipsHomeList = new ArrayList<DramaClipsHome>();
//                n+=10;
//                getData(n);
//            }

        });


        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        downloads = new ArrayList<String>();

        Subscriptio_Class.applicationContext=StickerAnimateGridActivity.this;
        subscriptio_class=new Subscriptio_Class();
        //Calling the getData method
        getData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StickerAnimateGridActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StickerPreview.contentCode = stickerAnimatedList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerAnimatedList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerAnimatedList.get(position).getContent_title();
                StickerPreview.sContentType = stickerAnimatedList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerAnimatedList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerAnimatedList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerAnimatedList.get(position).getZeid();
                StickerPreview.image = stickerAnimatedList.get(position).getImageUrl();
                StickerPreview.STICKER_TYPE = "ast";
                StickerPreview.total_likes = stickerAnimatedList.get(position).getLikes();
                StickerPreview.related_pic_url = Config.URL_STICKER_ANIMATED;
                StickerPreview.total_downloads = stickerAnimatedList.get(position).getDownloads();
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }
        });
    }

    private void getData(final int num){
        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            loading.dismiss();
                            JSONArray array = jsonObject.getJSONArray("Table");
                            for (int i = 0; i<array.length(); i++){


                                JSONObject obj = array.getJSONObject(i);


                                stickerAnimatedClass = new StickerAnimated();

                                stickerAnimatedClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_ANIMATED));
                                stickerAnimatedClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_ANIMATED));
                                stickerAnimatedClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_ANIMATED));
                                stickerAnimatedClass.setType(obj.getString(Config.TYPE_STICKER_ANIMATED));
                                stickerAnimatedClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_ANIMATED));
                                stickerAnimatedClass.setZeid(obj.getString(Config.ZID_STICKER_ANIMATED));
                                stickerAnimatedClass.setPath(obj.getString(Config.PATH_STICKER_ANIMATED));
                                stickerAnimatedClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_ANIMATED).replace(" ","%20"));
                                stickerAnimatedClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_ANIMATED));
                                stickerAnimatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                                stickerAnimatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                                Log.d("URLA",stickerAnimatedClass.getImageUrl());
                                stickerAnimatedList.add(stickerAnimatedClass);
                                images.add(obj.getString(Config.IMAGE_URL_STICKER_ANIMATED).replace(" ","%20"));
                                names.add(obj.getString(Config.CONTENT_TITLE_STICKER_ANIMATED));
                                likes.add(obj.getString(Config.TOTAL_LIKE));
                                downloads.add(obj.getString(Config.TOTAL_DOWNLOADS));


                            }
                            //Creating GridViewAdapter Object
                            AnimatedGridAdapter gameGridAdapter = new AnimatedGridAdapter(StickerAnimateGridActivity.this,images,names,likes,downloads);

                            //Adding adapter to gridview
                            gridView.setAdapter(gameGridAdapter);
                            gameGridAdapter.notifyDataSetChanged();
                            gridView.smoothScrollToPosition(n);
                            mPullRefreshGridView.onRefreshComplete();

                        } catch (JSONException e) {
                            mPullRefreshGridView.onRefreshComplete();
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mPullRefreshGridView.onRefreshComplete();
                        Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(StickerAnimateGridActivity.this);
                requestQueue.add(request);
            }
        });
        thread.start();
    }


    //    private void showGrid(JSONArray jsonArray,int y){
//        //Looping through all the elements of json array
//        for (int i = 0; i < 10 + y; i++) {
//            GameActionClass gameActionClass = new GameActionClass();
//            //Creating a json object of the current index
//            JSONObject obj = null;
//            try {
//                //getting json object from current index
//                obj = jsonArray.getJSONObject(i);
//
//                //getting image url and title from json object
//                gameActionClass = new GameActionClass();
//                gameActionClass.setContent_code(obj.getString(Config.CONTENT_CODE_GAME_ACTION));
//                gameActionClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_GAME_ACTION));
//                gameActionClass.setContent_title(obj.getString(Config.CONTENT_TITLE_GAME_ACTION));
//                gameActionClass.setType(obj.getString(Config.TYPE_GAME_ACTION));
//                gameActionClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_GAME_ACTION));
//                gameActionClass.setZeid(obj.getString(Config.ZID_GAME_ACTION));
//                gameActionClass.setPath(obj.getString(Config.PATH_GAME_ACTION));
//                gameActionClass.setImageUrl(obj.getString(Config.IMAGE_URL_GAME_ACTION));
//                gameActionClass.setBigImageUrl(obj.getString(Config.BIG_IAMGE_GAME_ACTION));
//
//
////                fullVideoClass.setContent_image(Config.URL_SOURCE+imgUrl);
//                images.add(gameActionClass.getImageUrl());
//                names.add(gameActionClass.getContent_title());
//
//                gameActionClassList.add(gameActionClass);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        //Creating GridViewAdapter Object
//        GameGridAdapter gameGridAdapter = new GameGridAdapter(this,images,names);
//
//        //Adding adapter to gridview
//        gridView.setAdapter(gameGridAdapter);
//        gameGridAdapter.notifyDataSetChanged();
//    }
    public void btnLoadMoregrid_stickerAnimate(View view) {
        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        downloads = new ArrayList<>();
        stickerAnimatedList = new ArrayList<StickerAnimated>();
        n+=10;
        getData(n);


        // http://stackoverflow.com/questions/18268218/change-screen-orientation-programatically-using-a-button
    }

}
