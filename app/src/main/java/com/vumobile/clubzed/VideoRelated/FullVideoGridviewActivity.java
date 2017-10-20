package com.vumobile.clubzed.VideoRelated;

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
import com.vumobile.clubzed.adapter.FullVideoGridViewAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.model.FullVideoClass;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FullVideoGridviewActivity extends ActionBarActivity {
    private Handler handler = new Handler();
    JSONArray json_array;
    private PullToRefreshGridView mPullRefreshGridView;
    Subscriptio_Class subscriptio_class;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_FULL_VIDEO;
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
    List<FullVideoClass> fullVideoClassList = new ArrayList<FullVideoClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_video_gridview);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_fullvdo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewfullvdo);
        gridView = mPullRefreshGridView.getRefreshableView();

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                likes = new ArrayList<>();
                views = new ArrayList<>();
                fullVideoClassList = new ArrayList<FullVideoClass>();
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

        Subscriptio_Class.applicationContext=FullVideoGridviewActivity.this;
        subscriptio_class=new Subscriptio_Class();
        //Calling the getData method
        getData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullVideoGridviewActivity.this.finish();
               // overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        mPullRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode =  fullVideoClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  fullVideoClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = fullVideoClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  fullVideoClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  fullVideoClassList.get(position).getPhysical_file_name();
                VideoPreViewActivity.ZedID = fullVideoClassList.get(position).getZeid();
                VideoPreViewActivity.contentImg =  fullVideoClassList.get(position).getContent_image();
                VideoPreViewActivity.info = fullVideoClassList.get(position).getInfo();
                VideoPreViewActivity.total_like = fullVideoClassList.get(position).getTotalLike();
                VideoPreViewActivity.total_views = fullVideoClassList.get(position).getTotalView();
                VideoPreViewActivity.genre = fullVideoClassList.get(position).getGenre();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_FULL_VIDEO;
                VideoPreViewActivity.TYPE = "fullVideo";
                Subscriptio_Class.type="video";
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
                                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
                            }
                        }
                );

                //Creating a request queue
                RequestQueue requestQueue = Volley.newRequestQueue(FullVideoGridviewActivity.this);
                //Adding our request to the queue
                requestQueue.add(jsonArrayRequest);
            }
        });
        thread.start();

    }


    private void showGrid(JSONArray jsonArray,int y){
        //Looping through all the elements of json array
        for (int i = 0; i < 10 + y; i++) {
            FullVideoClass fullVideoClass = new FullVideoClass();
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
                String imgUrl = obj.getString(Config.URL_IMAGE_FULL_VDO).replace(" ","%20");
//                fullVideoClass.setContent_image(Config.URL_SOURCE+imgUrl);
                images.add(Config.URL_SOURCE+imgUrl);
                names.add(obj.getString(Config.CONTENT_NAME_FULL_VIDEO));
                likes.add(obj.getString(Config.TOTAL_LIKE));
                views.add(obj.getString(Config.VIEWS));

                fullVideoClass.setContent_code(obj.getString(Config.CONTENT_CODE_FULL_VIDEO));
                fullVideoClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_FULL_VDO));
                fullVideoClass.setContent_name(obj.getString(Config.CONTENT_NAME_FULL_VIDEO));
                fullVideoClass.setContent_type(obj.getString(Config.CONTENT_TYPE_FULL_VIDEO));
                fullVideoClass.setPhysical_file_name(obj.getString(Config.PHYSICAL_FILE_NAME_FULL_VIDEO));
                fullVideoClass.setZeid(obj.getString(Config.ZED_ID_FULL_VIDEO));
                fullVideoClass.setContent_image(Config.URL_SOURCE+imgUrl);
//                fullVideoClass.setTime_stamp(obj.getString(Config.TIME_STAMP_FULL_VDO));
//                fullVideoClass.setLive_date(obj.getString(Config.LIVE_DATA_FULL_VDO));
//                fullVideoClass.setRow_num(obj.getString(Config.ROWNUM_FULL_VDO));
                fullVideoClass.setInfo(obj.getString(Config.INFO_FULL_VIDEO));
                fullVideoClass.setDuration(obj.getString(Config.DURATION_FULL_VIDEO));
                fullVideoClass.setGenre(obj.getString(Config.GENRE_FULL_VIDEO));
                fullVideoClass.setTotalLike(obj.getString(Config.TOTAL_LIKE_FULL_VIDEO));
                fullVideoClass.setTotalView(obj.getString(Config.TOTAL_VIEWS_FULL_VIDEO));

                fullVideoClassList.add(fullVideoClass);

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
    public void btnLoadMorefullvdo(View view) {
        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        views = new ArrayList<>();
        fullVideoClassList = new ArrayList<FullVideoClass>();
        n+=10;
        getData(n);


        // http://stackoverflow.com/questions/18268218/change-screen-orientation-programatically-using-a-button
    }

}
