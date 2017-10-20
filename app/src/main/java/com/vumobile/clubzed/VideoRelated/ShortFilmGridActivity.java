package com.vumobile.clubzed.VideoRelated;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.vumobile.clubzed.adapter.FullVideoGridViewAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.model.ShortFilm;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShortFilmGridActivity extends ActionBarActivity {

    private Handler handler = new Handler();
    JSONArray json_array;
    private PullToRefreshGridView mPullRefreshGridView;
    Subscriptio_Class subscriptio_class;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_BANGLA_SHORT_FILM;
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
    List<ShortFilm> shortFilmList = new ArrayList<ShortFilm>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_film_grid);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_sfvdo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);
        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewsfvdo);
        gridView = mPullRefreshGridView.getRefreshableView();

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                likes = new ArrayList<>();
                views = new ArrayList<>();
                shortFilmList = new ArrayList<ShortFilm>();
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

        Subscriptio_Class.applicationContext=ShortFilmGridActivity.this;
        subscriptio_class=new Subscriptio_Class();
        //Calling the getData method
        getData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShortFilmGridActivity.this.finish();
                // overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        mPullRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.duration = shortFilmList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  shortFilmList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  shortFilmList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = shortFilmList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  shortFilmList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  shortFilmList.get(position).getPhysical_file_name();
                VideoPreViewActivity.contentImg =  shortFilmList.get(position).getContent_image();
                VideoPreViewActivity.ZedID =  shortFilmList.get(position).getZeid();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_SHORT_FILM;
                VideoPreViewActivity.info = shortFilmList.get(position).getInfo();
                VideoPreViewActivity.total_views = shortFilmList.get(position).getTotalView();
                VideoPreViewActivity.total_like = shortFilmList.get(position).getTotalLike();
                VideoPreViewActivity.genre = shortFilmList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "sf";
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }
        });
    }

    private void getData(final int n) {
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
                                Log.d("Responsee", json_array.toString());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        //Displaying our grid
                                        showGrid(json_array,n);

                                    }
                                });
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mPullRefreshGridView.onRefreshComplete();
                                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
                            }
                        }
                );

                //Creating a request queue
                RequestQueue requestQueue = Volley.newRequestQueue(ShortFilmGridActivity.this);
                //Adding our request to the queue
                requestQueue.add(jsonArrayRequest);
            }
        });
        thread.start();
    }

    private void showGrid(JSONArray json_array, int n) {
        //Looping through all the elements of json array
        for (int i = 0; i < 10 + n; i++) {
            ShortFilm shortFilm = new ShortFilm();
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = json_array.getJSONObject(i);

                //getting image url and title from json object
                String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ","%20");
//                fullVideoClass.setContent_image(Config.URL_SOURCE+imgUrl);
                images.add(Config.URL_SOURCE+imgUrl);
                names.add(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                likes.add(obj.getString(Config.TOTAL_LIKE));
                views.add(obj.getString(Config.VIEWS));

                shortFilm.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                shortFilm.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                shortFilm.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                shortFilm.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                shortFilm.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                shortFilm.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
//                String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ","%20");
                shortFilm.setContent_image(Config.URL_SOURCE+imgUrl);
                shortFilm.setInfo(obj.getString(Config.INFO_BANGLA_MUSIC_VIDEO));
                shortFilm.setDuration(obj.getString(Config.DURATION_BANGLA_MUSIC_VIDEO));
                shortFilm.setGenre(obj.getString(Config.GENRE_BANGLA_MUSIC_VIDEO));
                shortFilm.setTotalLike(obj.getString(Config.TOTAL_LIKE_BANGLA_MUSIC_VIDEO));
                shortFilm.setTotalView(obj.getString(Config.TOTAL_VIEWS_BANGLA_MUSIC_VIDEO));

                shortFilmList.add(shortFilm);

            } catch (JSONException e) {
                mPullRefreshGridView.onRefreshComplete();
                e.printStackTrace();
            }
        }
        //Creating GridViewAdapter Object
        FullVideoGridViewAdapter gridViewAdapter = new FullVideoGridViewAdapter(this,images,names,likes,views);

        //Adding adapter to gridview
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.notifyDataSetChanged();
        gridView.smoothScrollToPosition(n);
        mPullRefreshGridView.onRefreshComplete();
    }

    public void btnLoadMorehdvdo(View view) {
        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        views = new ArrayList<>();
        shortFilmList = new ArrayList<ShortFilm>();
        n+=10;
        getData(n);
    }
}