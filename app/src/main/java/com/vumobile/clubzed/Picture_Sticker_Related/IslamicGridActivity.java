package com.vumobile.clubzed.Picture_Sticker_Related;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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
import com.vumobile.clubzed.adapter.GameGridAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.model.StickerIslamicClass;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IslamicGridActivity extends ActionBarActivity {
    private PullToRefreshGridView mPullRefreshGridView;
    Subscriptio_Class subscriptio_class;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_STICKER_ISLAMIC;
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
    List<StickerIslamicClass> stickerIslamicClassList = new ArrayList<StickerIslamicClass>();
    StickerIslamicClass stickerIslamicClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islamic_grid);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_grid_stickerIslamic);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewgrid_stickerIslamic);
        gridView = mPullRefreshGridView.getRefreshableView();

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                likes = new ArrayList<>();
                downloads = new ArrayList<String>();
                stickerIslamicClassList = new ArrayList<StickerIslamicClass>();
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

        Subscriptio_Class.applicationContext=IslamicGridActivity.this;
        subscriptio_class=new Subscriptio_Class();
        //Calling the getData method
        getData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IslamicGridActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StickerPreview.contentCode = stickerIslamicClassList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerIslamicClassList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerIslamicClassList.get(position).getContent_title();
                StickerPreview.sContentType = stickerIslamicClassList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerIslamicClassList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerIslamicClassList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerIslamicClassList.get(position).getZeid();
                StickerPreview.image = stickerIslamicClassList.get(position).getImageUrl();
                StickerPreview.STICKER_TYPE = "ist";
                StickerPreview.total_likes = stickerIslamicClassList.get(position).getTotal_like();
                StickerPreview.related_pic_url = Config.URL_STICKER_ISLAMIC;
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
                            for (int i = 0; i<=9+num; i++){


                                JSONObject obj = array.getJSONObject(i);


                                stickerIslamicClass = new StickerIslamicClass();

                                stickerIslamicClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_ISLAMIC));
                                stickerIslamicClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_ISLAMIC));
                                stickerIslamicClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_ISLAMIC));
                                stickerIslamicClass.setType(obj.getString(Config.TYPE_STICKER_ISLAMIC));
                                stickerIslamicClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_ISLAMIC));
                                stickerIslamicClass.setZeid(obj.getString(Config.ZID_STICKER_ISLAMIC));
                                stickerIslamicClass.setPath(obj.getString(Config.PATH_STICKER_ISLAMIC));
                                stickerIslamicClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_ISLAMIC).replace(" ","%20"));
                                stickerIslamicClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_ISLAMIC));
                                stickerIslamicClass.setTotal_like(obj.getString(Config.TOTAL_LIKE));

                                stickerIslamicClassList.add(stickerIslamicClass);
                                images.add(stickerIslamicClass.getImageUrl());
                                names.add(stickerIslamicClass.getContent_title());
                                downloads.add(obj.getString(Config.TOTAL_DOWNLOADS));
                                likes.add(obj.getString(Config.TOTAL_LIKE));

                            }
                            //Creating GridViewAdapter Object
                            GameGridAdapter gameGridAdapter = new GameGridAdapter(IslamicGridActivity.this,images,names,likes,downloads);

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
                RequestQueue requestQueue = Volley.newRequestQueue(IslamicGridActivity.this);
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
    public void btnLoadMoregrid_stickerIslamic(View view) {
        mPullRefreshGridView.onRefreshComplete();
        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        downloads = new ArrayList<String>();
        stickerIslamicClassList = new ArrayList<StickerIslamicClass>();
        n+=10;
        getData(n);


        // http://stackoverflow.com/questions/18268218/change-screen-orientation-programatically-using-a-button
    }

}
