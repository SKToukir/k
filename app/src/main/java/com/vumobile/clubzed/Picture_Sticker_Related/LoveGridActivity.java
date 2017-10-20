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
import com.vumobile.clubzed.model.PictureLoveClass;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoveGridActivity extends ActionBarActivity {
    private PullToRefreshGridView mPullRefreshGridView;
    Subscriptio_Class subscriptio_class;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_PICTURE_LOVE;
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
    List<PictureLoveClass> pictureLoveClassList = new ArrayList<PictureLoveClass>();

    PictureLoveClass pictureLoveClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_grid);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_grid_love);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);


        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewgrid_love);
        gridView = mPullRefreshGridView.getRefreshableView();

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                likes = new ArrayList<>();
                downloads = new ArrayList<String>();
                pictureLoveClassList = new ArrayList<PictureLoveClass>();
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

        Subscriptio_Class.applicationContext=LoveGridActivity.this;
        subscriptio_class=new Subscriptio_Class();
        //Calling the getData method
        getData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoveGridActivity.this.finish();
               // overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PictureDetailsActivity.contentCode = pictureLoveClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = pictureLoveClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = pictureLoveClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = pictureLoveClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = pictureLoveClassList.get(position).getPhysicalFilwName();
                PictureDetailsActivity.contentImg = pictureLoveClassList.get(position).getImage_url();
                PictureDetailsActivity.ZedID = pictureLoveClassList.get(position).getZeid();
                PictureDetailsActivity.image= pictureLoveClassList.get(position).getImage_url();
                PictureDetailsActivity.likes = pictureLoveClassList.get(position).getLikes();
                PictureDetailsActivity.total_downloads = pictureLoveClassList.get(position).getDownloads();
                PictureDetailsActivity.related_pic = Config.URL_PICTURE_LOVE;
                PictureDetailsActivity.PIC_TYPE = "love";
                Subscriptio_Class.type="pic";
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

                            JSONArray array = jsonObject.getJSONArray("Table");

                            loading.dismiss();

                            for (int i = 0; i<=9+num; i++){


                                JSONObject obj = array.getJSONObject(i);


                                pictureLoveClass = new PictureLoveClass();
                                pictureLoveClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE_LOVE));
                                pictureLoveClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE_LOVE));
                                pictureLoveClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE_LOVE));
                                pictureLoveClass.setType(obj.getString(Config.TYPE_PICTURE_LOVE));
                                pictureLoveClass.setPhysicalFilwName(obj.getString(Config.PHYSICALFILENAME_PICTURE_LOVE));
                                pictureLoveClass.setZeid(obj.getString(Config.ZID_PICTURE_LOVE));
                                pictureLoveClass.setPath(obj.getString(Config.PATH_PICTURE_LOVE));
                                pictureLoveClass.setImage_url(obj.getString(Config.IMAGE_URL_PICTURE_LOVE));
                                pictureLoveClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                                pictureLoveClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                                images.add(pictureLoveClass.getImage_url());
                                names.add(pictureLoveClass.getContent_title());
                                likes.add(obj.getString(Config.TOTAL_LIKE));
                                downloads.add(obj.getString(Config.TOTAL_DOWNLOADS));
                                pictureLoveClassList.add(pictureLoveClass);

                            }
                            //Creating GridViewAdapter Object
                            GameGridAdapter gameGridAdapter = new GameGridAdapter(LoveGridActivity.this,images,names,likes,downloads);

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
                RequestQueue requestQueue = Volley.newRequestQueue(LoveGridActivity.this);
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
    public void btnLoadMoregrid_love(View view) {

        images = new ArrayList<>();
        names = new ArrayList<>();
        likes = new ArrayList<>();
        downloads = new ArrayList<String>();
        pictureLoveClassList = new ArrayList<PictureLoveClass>();
        n+=10;
        getData(n);


        // http://stackoverflow.com/questions/18268218/change-screen-orientation-programatically-using-a-button
    }

}
