package com.vumobile.clubzed.SongRelated;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.SongGridViewAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.model.BanglaSongClass;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BanglaTopSongGridActivity extends ActionBarActivity {
    private PullToRefreshGridView mPullRefreshGridView;
    private Handler handler = new Handler();
    private JSONArray json_array;
    Subscriptio_Class subscriptio_class;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_BANGLA_SONG;
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
    private ArrayList<String> views;

    List<BanglaSongClass> banglaSongClassList = new ArrayList<BanglaSongClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangla_top_song_grid);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_banglatopsong);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewDramabanglatopsong);
        gridView = mPullRefreshGridView.getRefreshableView();

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                likes = new ArrayList<>();
                views = new ArrayList<>();
                banglaSongClassList = new ArrayList<BanglaSongClass>();
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
        views = new ArrayList<>();

        Subscriptio_Class.applicationContext= BanglaTopSongGridActivity.this;
        subscriptio_class=new Subscriptio_Class();

        //Calling the getData method
        getData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BanglaTopSongGridActivity.this.finish();
               // overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlaySongActivity.contentCode = banglaSongClassList.get(position).getContentcode();
                PlaySongActivity.categoryCode = banglaSongClassList.get(position).getCategory_code();
                PlaySongActivity.contentName = banglaSongClassList.get(position).getContent_title();
                PlaySongActivity.sContentType = banglaSongClassList.get(position).getType();
                PlaySongActivity.sPhysicalFileName = banglaSongClassList.get(position).getPhysicalFileName();
                PlaySongActivity.contentImg = banglaSongClassList.get(position).getImageUrl();
                PlaySongActivity.ZedID = banglaSongClassList.get(position).getZeid();
                PlaySongActivity.likes = banglaSongClassList.get(position).getLikes();
                PlaySongActivity.views = banglaSongClassList.get(position).getViews();
                PlaySongActivity.related_song = Config.URL_BANGLA_SONG;
                PlaySongActivity.SONG_TYPE = "bangla";
                Subscriptio_Class.type = "song";
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

                //Creating a json array request to get the json from our api
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //Dismissing the progressdialog on response
                                loading.dismiss();

                                json_array = response;

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        //Displaying our grid
                                        showGrid(json_array,num);
                                    }
                                });
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mPullRefreshGridView.onRefreshComplete();
                                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                            }
                        }
                );

                //Creating a request queue
                RequestQueue requestQueue = Volley.newRequestQueue(BanglaTopSongGridActivity.this);
                //Adding our request to the queue
                requestQueue.add(jsonArrayRequest);
            }
        });
        thread.start();
    }


    private void showGrid(JSONArray jsonArray,int y){
        //Looping through all the elements of json array
        for (int i = 0; i < 10 + y; i++) {
            BanglaSongClass banglaSongClass = new BanglaSongClass();
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
//                fullVideoClass.setContent_image(Config.URL_SOURCE+imgUrl);
                String imgUrl;
                banglaSongClass.setContentcode(obj.getString(Config.CONTENT_CODE_BANGLA_SONG));
                banglaSongClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_SONG));
                banglaSongClass.setContent_title(obj.getString(Config.CONTENT_NAME_BANGLA_SONG));
                banglaSongClass.setType(obj.getString(Config.CONTENT_TYPE_BANGLA_SONG));
                banglaSongClass.setPhysicalFileName((obj.getString(Config.PHYSICALNAME_BANGLA_SONG)).replace(" ","%20"));
                banglaSongClass.setZeid(obj.getString(Config.ZEID_BANGLA_SONG));
                banglaSongClass.setPath((obj.getString(Config.PATH_BANGLA_SONG)).replace(" ","%20"));
                banglaSongClass.setImageUrl(obj.getString(Config.CONTENT_IMAGE_BANGLA_SONG.replace(" ","%20")));
                banglaSongClass.setCount(obj.getString(Config.COUNT_BANGLA_SONG));
                banglaSongClass.setRating(obj.getString(Config.RATING_BANGLA_SONG));
                banglaSongClass.setLiveData(obj.getString(Config.LIVE_DATA_BANGLA_SONG));
                banglaSongClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                banglaSongClass.setViews(obj.getString(Config.VIEWS));

                images.add(banglaSongClass.getImageUrl());
                names.add(banglaSongClass.getContent_title());

                likes.add(obj.getString(Config.TOTAL_LIKE));
                views.add(obj.getString(Config.VIEWS));

                banglaSongClassList.add(banglaSongClass);

            } catch (JSONException e) {
                mPullRefreshGridView.onRefreshComplete();
                e.printStackTrace();
            }
        }
        //Creating GridViewAdapter Object
        SongGridViewAdapter gridViewAdapter = new SongGridViewAdapter(this,images,names,likes,views);

        //Adding adapter to gridview
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.notifyDataSetChanged();
        gridView.smoothScrollToPosition(n);
        mPullRefreshGridView.onRefreshComplete();
    }

    public void btnLoadMorebanglatop(View view) {
        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        views = new ArrayList<>();
        banglaSongClassList = new ArrayList<BanglaSongClass>();
        n+=10;
        getData(n);


        // http://stackoverflow.com/questions/18268218/change-screen-orientation-programatically-using-a-button
    }
}