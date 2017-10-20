package com.vumobile.clubzed.SongRelated;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.MyDownLoadsActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.BanglaSongAdapter;
import com.vumobile.clubzed.adapter.EnglishSongAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.BanglaSongClass;
import com.vumobile.clubzed.model.EnglishSongClass;
import com.vumobile.clubzed.util.FAQActivity;
import com.vumobile.clubzed.util.HelpActivity;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongCategoryActivity extends ActionBarActivity implements View.OnClickListener{

    ProgressDialog loading;

    Toolbar toolbar;
    Button btnBack;
    TextView songBanglaTopMore,englishtopMore;
    EnglishSongClass englishSongClass;
    BanglaSongClass banglaSongClass;
    List<EnglishSongClass> englishSongClassList = new ArrayList<EnglishSongClass>();
    List<BanglaSongClass> banglaSongClassList = new ArrayList<BanglaSongClass>();
    RecyclerView recyclerviewBanglaSong, recyclerEnglishSong;
    RecyclerView.Adapter adapterBanglaSong, adapterEnglishSong;
    ImageView btnHelp, btnHome, btnMydownload,btnBuddy,btnBdTube,btnBanglaBeats,btnAmarSticker,btnRateMe,btnHelpSame;
    Subscriptio_Class subscriptio_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_category);

        initFooter();

        toolbar = (Toolbar) findViewById(R.id.tool_bar_song_cat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        englishtopMore = (TextView) findViewById(R.id.englishtopMoregrid);
        songBanglaTopMore = (TextView) findViewById(R.id.songBanglasTopMore);

        englishtopMore.setOnClickListener(this);
        songBanglaTopMore.setOnClickListener(this);

        loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);

        new BackGroundTask().execute();



        Subscriptio_Class.applicationContext = SongCategoryActivity.this;
        subscriptio_class = new Subscriptio_Class();

        initRecyclerBanglaSong();


        initEnglishRecycler();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongCategoryActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });


        recyclerviewBanglaSong.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerviewBanglaSong, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
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

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerEnglishSong.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerEnglishSong, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PlaySongActivity.contentCode = englishSongClassList.get(position).getContentcode();
                PlaySongActivity.categoryCode = englishSongClassList.get(position).getCategory_code();
                PlaySongActivity.contentName = englishSongClassList.get(position).getContent_title();
                PlaySongActivity.sContentType = englishSongClassList.get(position).getType();
                PlaySongActivity.sPhysicalFileName = englishSongClassList.get(position).getPhysicalFileName();
                PlaySongActivity.contentImg = englishSongClassList.get(position).getImageUrl();
                PlaySongActivity.ZedID = englishSongClassList.get(position).getZeid();
                PlaySongActivity.likes = englishSongClassList.get(position).getLikes();
                PlaySongActivity.views = englishSongClassList.get(position).getViews();
                PlaySongActivity.related_song = Config.URL_ENGLISH_SONG;
                PlaySongActivity.SONG_TYPE = "english";
                Subscriptio_Class.type = "song";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

    }

    private void initFooter() {

        btnHome = (ImageView) findViewById(R.id.homeSong);
        btnHelp = (ImageView) findViewById(R.id.helpSong);
        btnHelpSame = (ImageView) findViewById(R.id.helpSameSong);
        btnMydownload = (ImageView) findViewById(R.id.myDownloadsSong);

        btnBuddy = (ImageView) findViewById(R.id.buddySong);
        btnBdTube = (ImageView) findViewById(R.id.bdtubeSong);
        btnBanglaBeats = (ImageView) findViewById(R.id.banglabeatsSong);
        btnAmarSticker = (ImageView) findViewById(R.id.amarStSong);
        btnRateMe = (ImageView) findViewById(R.id.rateMeSong);

        btnBuddy.setOnClickListener(this);
        btnBdTube.setOnClickListener(this);
        btnBanglaBeats.setOnClickListener(this);
        btnRateMe.setOnClickListener(this);
        btnAmarSticker.setOnClickListener(this);

        btnHome.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnHelpSame.setOnClickListener(this);
        btnMydownload.setOnClickListener(this);

    }


    private void initEnglishRecycler() {
        adapterEnglishSong = new EnglishSongAdapter(SongCategoryActivity.this,englishSongClassList);
        recyclerEnglishSong = (RecyclerView) findViewById(R.id.recycler_view_eng_song);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerEnglishSong.setLayoutManager(layoutManager);
        recyclerEnglishSong.setItemAnimator(new DefaultItemAnimator());
        recyclerEnglishSong.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.HORIZONTAL));
    }

    private void initRecyclerBanglaSong() {
        adapterBanglaSong = new BanglaSongAdapter(SongCategoryActivity.this,banglaSongClassList);
        recyclerviewBanglaSong = (RecyclerView) findViewById(R.id.recycler_view_bangla_song);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerviewBanglaSong.setLayoutManager(layoutManager);
        recyclerviewBanglaSong.setItemAnimator(new DefaultItemAnimator());
        recyclerviewBanglaSong.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.HORIZONTAL));
    }


    private void parseDataBanglaSong(String urlBanglaSong) {

        JsonArrayRequest request = new JsonArrayRequest(urlBanglaSong, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                loading.dismiss();
                for (int i = 0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        banglaSongClass = new BanglaSongClass();

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

                        banglaSongClassList.add(banglaSongClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerviewBanglaSong.setAdapter(adapterBanglaSong);
                adapterBanglaSong.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SongCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseDataEngLishSong(String urlEnglishSong) {

        JsonArrayRequest request = new JsonArrayRequest(urlEnglishSong, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        englishSongClass = new EnglishSongClass();
                        englishSongClass.setContentcode(obj.getString(Config.CONTENT_TYPE_ENGLISH_SONG));
                        englishSongClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_ENGLISH_SONG));
                        englishSongClass.setContent_title(obj.getString(Config.CONTENT_NAME_ENGLISH_SONG));
                        englishSongClass.setType(obj.getString(Config.CONTENT_TYPE_ENGLISH_SONG));
                        englishSongClass.setPhysicalFileName(obj.getString(Config.PHYSICALNAME_ENGLISH_SONG));
                        englishSongClass.setZeid(obj.getString(Config.ZEID_ENGLISH_SONG));
                        englishSongClass.setPath(obj.getString(Config.PATH_ENGLISH_SONG));
                        englishSongClass.setImageUrl(obj.getString(Config.CONTENT_IMAGE_ENGLISH_SONG));
                        englishSongClass.setCount(obj.getString(Config.COUNT_ENGLISH_SONG));
                        englishSongClass.setRating(obj.getString(Config.RATING_ENGLISH_SONG));
                        englishSongClass.setLiveData(obj.getString(Config.LIVE_DATA_ENGLISH_SONG));
                        englishSongClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        englishSongClass.setViews(obj.getString(Config.VIEWS));

                        englishSongClassList.add(englishSongClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerEnglishSong.setAdapter(adapterEnglishSong);
                adapterEnglishSong.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SongCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.songBanglasTopMore:
                Intent intent = new Intent(SongCategoryActivity.this,BanglaTopSongGridActivity.class);
                startActivity(intent);
                break;
            case R.id.englishtopMoregrid:
                Intent intent1 = new Intent(SongCategoryActivity.this,EnglishTopSongGridActivity.class);
                startActivity(intent1);
                break;
            case R.id.homeSong:
                intent = new Intent(SongCategoryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.helpSong:
                intent = new Intent(SongCategoryActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.helpSameSong:
                intent = new Intent(SongCategoryActivity.this,FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.myDownloadsSong:
                intent = new Intent(SongCategoryActivity.this, MyDownLoadsActivity.class);
                startActivity(intent);
                break;
            case R.id.buddySong :
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy")));
                break;
            case R.id.bdtubeSong:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube")));
                break;
            case R.id.banglabeatsSong:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en")));
                break;
            case R.id.amarStSong:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker")));
                break;
            case R.id.rateMeSong:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en")));
                break;
        }
    }

    class BackGroundTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            parseDataBanglaSong(Config.URL_BANGLA_SONG);

            parseDataEngLishSong(Config.URL_ENGLISH_SONG);

            return null;
        }
    }
}
