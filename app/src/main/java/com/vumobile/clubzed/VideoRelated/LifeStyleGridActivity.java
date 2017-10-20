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
import com.vumobile.clubzed.model.LifeStyleClass;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LifeStyleGridActivity extends ActionBarActivity {
    private PullToRefreshGridView mPullRefreshGridView;
    private Handler handler = new Handler();
    JSONArray json_array;
    Subscriptio_Class subscriptio_class;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_VIDEO_LIFE_STYLE;
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
    List<LifeStyleClass> lifeStyleClassList = new ArrayList<LifeStyleClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style_grid);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_lifestyle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewlifestyle);
        gridView = mPullRefreshGridView.getRefreshableView();


        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                likes = new ArrayList<>();
                views = new ArrayList<>();
                lifeStyleClassList = new ArrayList<LifeStyleClass>();
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

        Subscriptio_Class.applicationContext= LifeStyleGridActivity.this;
        subscriptio_class=new Subscriptio_Class();

        //Calling the getData method
        getData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LifeStyleGridActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode =  lifeStyleClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  lifeStyleClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = lifeStyleClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  lifeStyleClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  lifeStyleClassList.get(position).getPhysical_file_name();
                VideoPreViewActivity.ZedID = lifeStyleClassList.get(position).getZid();
                VideoPreViewActivity.contentImg =  lifeStyleClassList.get(position).getContent_image();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_VIDEO_LIFE_STYLE;
                VideoPreViewActivity.TYPE = "banglatop";
                VideoPreViewActivity.genre = lifeStyleClassList.get(position).getGenre();
                VideoPreViewActivity.total_views = lifeStyleClassList.get(position).getTotal_view();
                VideoPreViewActivity.total_like = lifeStyleClassList.get(position).getTotal_like();
                VideoPreViewActivity.info = lifeStyleClassList.get(position).getInfo();
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
                               Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
                           }
                       }
               );

               //Creating a request queue
               RequestQueue requestQueue = Volley.newRequestQueue(LifeStyleGridActivity.this);
               //Adding our request to the queue
               requestQueue.add(jsonArrayRequest);
           }
       });
        thread.start();
    }


    private void showGrid(JSONArray jsonArray,int y){
        //Looping through all the elements of json array
        for (int i = 0; i < 10 + y; i++) {
            LifeStyleClass lifeStyleClass = new LifeStyleClass();
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
//                fullVideoClass.setContent_image(Config.URL_SOURCE+imgUrl);
                String imgUrl;
                lifeStyleClass.setContent_code(obj.getString(Config.CONTENTCODE_LIFESTYLE));
                lifeStyleClass.setCategory_code(obj.getString(Config.CATEGORYCODE_LIFESTYLE));
                lifeStyleClass.setContent_name(obj.getString(Config.CONTENT_NAME_LIFESTYLE));
                lifeStyleClass.setContent_type(obj.getString(Config.CONTENT_TYPE_LIFESTYLE));
                lifeStyleClass.setPhysical_file_name(obj.getString(Config.PHYSICALFILENAME_LIFESTYLE));
                lifeStyleClass.setZid(obj.getString(Config.ZID_LIFESTYLE));
                imgUrl = obj.getString(Config.CONTENT_IMAGE_LIFESTYLE).replace(" ", "%20");
                lifeStyleClass.setContent_image(Config.URL_SOURCE + imgUrl);
                lifeStyleClass.setInfo(obj.getString(Config.INFO_LIFESTYLE));
                lifeStyleClass.setGenre(obj.getString(Config.GENRE_LIFESTYLE));
                lifeStyleClass.setTotal_view(obj.getString(Config.TOTAL_VIEW_LIFESTYLE));
                lifeStyleClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_LIFESTYLE));
                images.add(Config.URL_SOURCE + imgUrl);
                names.add(obj.getString(Config.CONTENT_NAME_LIFESTYLE));
                likes.add(obj.getString(Config.TOTAL_LIKE));
                views.add(obj.getString(Config.VIEWS));


                lifeStyleClassList.add(lifeStyleClass);

            } catch (JSONException e) {
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


    public void btnNextlifestyleGrid(View view) {
        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        views = new ArrayList<>();
        lifeStyleClassList = new ArrayList<LifeStyleClass>();
        n+=10;
        getData(n);

        // http://stackoverflow.com/questions/18268218/change-screen-orientation-programatically-using-a-button
    }
}
