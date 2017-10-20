package com.vumobile.clubzed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.Picture_Sticker_Related.StickerPreview;
import com.vumobile.clubzed.SongRelated.PlaySongActivity;
import com.vumobile.clubzed.VideoRelated.VideoPreViewActivity;
import com.vumobile.clubzed.adapter.SearchGridAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.model.SearchClass;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends ActionBarActivity {

    private PullToRefreshGridView mPullRefreshGridView;
    Toolbar toolbar;
    Button btnBack;
    //Web api url
    public static final String DATA_URL = Config.URL_BOLLYWOOD_TOP;
    //Tag values to read from json
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";
    //GridView Object
    private GridView gridViewSearch;
    int n = 0;
    //ArrayList for Storing image urls and titles
    private ArrayList<String> images;
    private ArrayList<String> names;
    String searchContent;
    SearchClass searchClass;
    Subscriptio_Class subscriptio_class;
    List<SearchClass> searchClassList = new ArrayList<SearchClass>();
    String final_search_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        String txt_search = intent.getStringExtra("search_grid");
        searchContent = txt_search.replaceAll(" ","");
        toolbar = (Toolbar) findViewById(R.id.tool_bar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.gridViewSearch);
        gridViewSearch = mPullRefreshGridView.getRefreshableView();

        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                images = new ArrayList<>();
                names = new ArrayList<>();
                searchClassList = new ArrayList<SearchClass>();
                n+=10;
                parseSearchData(n);
            }
        });



        images = new ArrayList<>();
        names = new ArrayList<>();

        Subscriptio_Class.applicationContext= SearchResultActivity.this;
        subscriptio_class=new Subscriptio_Class();

        final_search_url = Config.URL_SEARCH+searchContent;

        parseSearchData(n);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.this.finish();
                // overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        gridViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String type = searchClassList.get(position).getType();

                //Toast.makeText(getApplicationContext(),type,Toast.LENGTH_LONG).show();

                if (type.matches("VD") || type.matches("FV")){
                    VideoPreViewActivity.contentCode =  searchClassList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode =  searchClassList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = searchClassList.get(position).getPhysicalName();
                    VideoPreViewActivity.sContentType =  searchClassList.get(position).getType();
                    VideoPreViewActivity.sPhysicalFileName =  searchClassList.get(position).getPhysicalName();
                    VideoPreViewActivity.ZedID = searchClassList.get(position).getZid();
                    VideoPreViewActivity.contentImg =  searchClassList.get(position).getImageUrl();
                    VideoPreViewActivity.info = searchClassList.get(position).getInfo();
                    VideoPreViewActivity.genre = searchClassList.get(position).getGenre();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_FULL_VIDEO;
                    VideoPreViewActivity.TYPE = "fullVideo";
                    Subscriptio_Class.type="fullvideo";
                    subscriptio_class.detectMSISDN();

                }else if (type.matches("BFT")){
                    PlaySongActivity.contentCode = searchClassList.get(position).getContent_code();
                    PlaySongActivity.categoryCode = searchClassList.get(position).getCategory_code();
                    PlaySongActivity.contentName = searchClassList.get(position).getPhysicalName();
                    PlaySongActivity.sContentType = searchClassList.get(position).getType();
                    PlaySongActivity.sPhysicalFileName = searchClassList.get(position).getPhysicalName();
                    PlaySongActivity.contentImg = searchClassList.get(position).getImageUrl();
                    PlaySongActivity.ZedID = searchClassList.get(position).getZid();
                    PlaySongActivity.related_song = Config.URL_BANGLA_SONG;
                    PlaySongActivity.SONG_TYPE = "bangla";
                    Subscriptio_Class.type = "song";
                    subscriptio_class.detectMSISDN();

                }else if (type.matches("WP")||type.matches("JG")){
                    PictureDetailsActivity.contentCode = searchClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = searchClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = searchClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = searchClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = searchClassList.get(position).getPhysicalName();
                    PictureDetailsActivity.contentImg = searchClassList.get(position).getImageUrl();
                    PictureDetailsActivity.ZedID = searchClassList.get(position).getZid();
                    PictureDetailsActivity.image= searchClassList.get(position).getImageUrl();
                    PictureDetailsActivity.related_pic = Config.URL_PICTURE;
                    PictureDetailsActivity.PIC_TYPE = "pic";
                    Subscriptio_Class.type="pic";
                    subscriptio_class.detectMSISDN();

                }else if (type.matches("ST") || type.matches("AST") || type.matches("AN")){
                    StickerPreview.contentCode = searchClassList.get(position).getContent_code();
                    StickerPreview.categoryCode = searchClassList.get(position).getCategory_code();
                    StickerPreview.contentName = searchClassList.get(position).getContent_title();
                    StickerPreview.sContentType = searchClassList.get(position).getType();
                    StickerPreview.sPhysicalFileName = searchClassList.get(position).getPhysicalName();
                    StickerPreview.contentImg = searchClassList.get(position).getImageUrl();
                    StickerPreview.ZedID = searchClassList.get(position).getZid();
                    StickerPreview.image = searchClassList.get(position).getImageUrl();
                    StickerPreview.related_pic_url = Config.URL_STICKER;
                    StickerPreview.STICKER_TYPE = "st";
                    Subscriptio_Class.type = "mpic";
                    subscriptio_class.detectMSISDN();

                }

            }
        });
    }


    private void parseSearchData(final int num) {

        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, final_search_url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            JSONArray array = jsonObject.getJSONArray("Table");

                            loading.dismiss();


                            for (int i = 0; i < 8 + num; i++) {

                                JSONObject obj = null;
                                obj = array.getJSONObject(i);
                                searchClass = new SearchClass();
                                searchClass.setContent_code(obj.getString(Config.SEARCH_CONTENT_CODE));
                                searchClass.setCategory_code(obj.getString(Config.SEARCH_CATGORY_CODE));
                                searchClass.setContent_title(obj.getString(Config.SEARCH_CONTENT_TITLE));
                                searchClass.setType(obj.getString(Config.SEARCH_TYPE));
                                searchClass.setPhysicalName(obj.getString(Config.SEARCH_PHYSICAL_NAME));
                                searchClass.setZid(obj.getString(Config.SEARCH_ZID));
                                searchClass.setImageUrl(obj.getString(Config.SEARCH_IMAGE_URL).replaceAll(" ", "%20"));
                                searchClass.setInfo(obj.getString(Config.SEARCH_CONTENT_INFO));
                                searchClass.setDuration(obj.getString(Config.SEARCH_DURATION));
                                searchClass.setGenre(obj.getString(Config.SEARCH_GENRE));

                                names.add(obj.getString(Config.SEARCH_CONTENT_TITLE));
                                images.add(obj.getString(Config.SEARCH_IMAGE_URL).replaceAll(" ", "%20"));

                                searchClassList.add(searchClass);

                            }


                            //Creating GridViewAdapter Object
                            SearchGridAdapter gridViewAdapters = new SearchGridAdapter(getApplicationContext(),images,names);

                            //Adding adapter to gridview
                            gridViewSearch.setAdapter(gridViewAdapters);
                            gridViewAdapters.notifyDataSetChanged();
                            gridViewSearch.smoothScrollToPosition(n);
                            mPullRefreshGridView.onRefreshComplete();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(SearchResultActivity.this);

                //Adding request to the queue
                requestQueue.add(request);
                //AppController.getInstance().addToRequestQueue(request);
            }
        });
        thread.start();



    }

    public void btnLoadMoregrid_search(View view) {
        images = new ArrayList<>();
        names = new ArrayList<>();
        searchClassList = new ArrayList<SearchClass>();
        n+=10;
        parseSearchData(n);



        // http://stackoverflow.com/questions/18268218/change-screen-orientation-programatically-using-a-button
    }

//    private void initReycler() {
//        adapterSearch = new SearchAdapter(SearchResultActivity.this, searchClassList);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_search);
//        recyclerView.setNestedScrollingEnabled(false);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//    }

}
