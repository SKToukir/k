package com.vumobile.clubzed.GameRelated;

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
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.GameGridAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.model.GameSportsClass;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SportsGameGridActivity extends ActionBarActivity {
    private PullToRefreshGridView mPullRefreshGridView;
    private Handler handler = new Handler();
    private JSONArray json_array;
    Subscriptio_Class subscriptio_class;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_GAME_SPORTS;
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

    List<GameSportsClass> gameSportsClassList = new ArrayList<GameSportsClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_game_grid);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_grid_sports);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewgrid_sports);
        gridView = mPullRefreshGridView.getRefreshableView();

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                likes = new ArrayList<>();
                downloads = new ArrayList<String>();
                gameSportsClassList = new ArrayList<GameSportsClass>();
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

        Subscriptio_Class.applicationContext=SportsGameGridActivity.this;
        subscriptio_class=new Subscriptio_Class();
        //Calling the getData method
        getData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SportsGameGridActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PictureDetailsActivity.contentCode = gameSportsClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = gameSportsClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = gameSportsClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = gameSportsClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = gameSportsClassList.get(position).getPhysicalFileName();
                PictureDetailsActivity.contentImg = gameSportsClassList.get(position).getImageUrl();
                PictureDetailsActivity.ZedID = gameSportsClassList.get(position).getZeid();
                PictureDetailsActivity.image = gameSportsClassList.get(position).getImageUrl();
                PictureDetailsActivity.likes = gameSportsClassList.get(position).getLikes();
                PictureDetailsActivity.PIC_TYPE = "sports";
                PictureDetailsActivity.related_pic = Config.URL_GAME_SPORTS;
                Subscriptio_Class.type = "game";
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
                RequestQueue requestQueue = Volley.newRequestQueue(SportsGameGridActivity.this);
                //Adding our request to the queue
                requestQueue.add(jsonArrayRequest);
            }
        });
        thread.start();
    }


    private void showGrid(JSONArray jsonArray,int y){
        //Looping through all the elements of json array
        for (int i = 0; i < 10 + y; i++) {
            GameSportsClass gameSportsClass = new GameSportsClass();
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
                gameSportsClass = new GameSportsClass();
                gameSportsClass.setContent_code(obj.getString(Config.CONTENT_CODE_GAME_SPORTS));
                gameSportsClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_GAME_SPORTS));
                gameSportsClass.setContent_title(obj.getString(Config.CONTENT_TITLE_GAME_SPORTS));
                gameSportsClass.setType(obj.getString(Config.TYPE_GAME_SPORTS));
                gameSportsClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_GAME_SPORTS));
                gameSportsClass.setZeid(obj.getString(Config.ZID_GAME_SPORTS));
                gameSportsClass.setPath(obj.getString(Config.PATH_GAME_SPORTS));
                gameSportsClass.setImageUrl(obj.getString(Config.IMAGE_URL_GAME_SPORTS));
                gameSportsClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                gameSportsClass.setBigImageUrl(obj.getString(Config.BIG_IAMGE_GAME_SPORTS));
                gameSportsClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));


//                fullVideoClass.setContent_image(Config.URL_SOURCE+imgUrl);
                images.add(gameSportsClass.getImageUrl());
                names.add(gameSportsClass.getContent_title());
                likes.add(obj.getString(Config.TOTAL_LIKE));
                downloads.add(obj.getString(Config.TOTAL_DOWNLOADS));

                gameSportsClassList.add(gameSportsClass);

            } catch (JSONException e) {
                mPullRefreshGridView.onRefreshComplete();
                e.printStackTrace();
            }
        }
        //Creating GridViewAdapter Object
        GameGridAdapter gameGridAdapter = new GameGridAdapter(this,images,names,likes,downloads);

        //Adding adapter to gridview
        gridView.setAdapter(gameGridAdapter);
        gameGridAdapter.notifyDataSetChanged();
        gridView.smoothScrollToPosition(n);
        mPullRefreshGridView.onRefreshComplete();
    }
    public void btnLoadMoregrid_sports(View view) {
        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        downloads = new ArrayList<>();
        gameSportsClassList = new ArrayList<GameSportsClass>();
        n+=10;
        getData(n);


        // http://stackoverflow.com/questions/18268218/change-screen-orientation-programatically-using-a-button
    }

}