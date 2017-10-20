package com.vumobile.clubzed.VideoRelated;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.MyDownLoadsActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.BMAdapter;
import com.vumobile.clubzed.adapter.BNAdapter;
import com.vumobile.clubzed.adapter.BanglaTAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.BanglaMusicVideo;
import com.vumobile.clubzed.model.BanglaNatok;
import com.vumobile.clubzed.model.BanglaTelefilm;
import com.vumobile.clubzed.util.FAQActivity;
import com.vumobile.clubzed.util.HelpActivity;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HDCategoryActivity extends ActionBarActivity implements View.OnClickListener{

    Intent intent;
    ProgressDialog loading;

    Subscriptio_Class subscriptio_class;

    BanglaMusicVideo banglaMusicVideo;
    BanglaNatok banglaNatok;
    BanglaTelefilm banglaTelefilm;
    List<BanglaMusicVideo> banglaMusicVideoList = new ArrayList<BanglaMusicVideo>();
    List<BanglaNatok> banglaNatokList = new ArrayList<BanglaNatok>();
    List<BanglaTelefilm> banglaTelefilmList = new ArrayList<BanglaTelefilm>();

    RecyclerView recycler_view_banglaMusicVideo, recycler_view_banglaNatok, recycler_view_banglaTelefilm;
    RecyclerView.Adapter adapterBM, adapterBN, adapterBT;

    TextView txtBMVMore, txtBNMore, txtBTMore;
    Toolbar toolbar;
    private ImageView btnHome, btnDownloadPage, btnHelp, btnHelpSame, btnBuddy, btnBdTube, btnBanglaBeats, btnAmarStckr, btnRateme;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdcategory);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_hd_cat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HDCategoryActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        initUI();

        initRecyclerBMV();
        parseBMV(Config.URL_BANGLA_MUSIC_VIDEO);

        initRecyclerBN();
        parseBN(Config.URL_BANGLA_NATOK);

        initRecyclerBT();
        parseBT(Config.URL_BANGLA_TELEFILM);


        Subscriptio_Class.applicationContext = HDCategoryActivity.this;
        subscriptio_class = new Subscriptio_Class();

        recycler_view_banglaMusicVideo.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recycler_view_banglaMusicVideo, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = true;
                VideoPreViewActivity.duration = banglaMusicVideoList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  banglaMusicVideoList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  banglaMusicVideoList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = banglaMusicVideoList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  banglaMusicVideoList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  banglaMusicVideoList.get(position).getPhysical_file_name();
                VideoPreViewActivity.contentImg =  banglaMusicVideoList.get(position).getContent_image();
                VideoPreViewActivity.ZedID =  banglaMusicVideoList.get(position).getZeid();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_MUSIC_VIDEO;
                VideoPreViewActivity.info = banglaMusicVideoList.get(position).getInfo();
                VideoPreViewActivity.total_views = banglaMusicVideoList.get(position).getTotalView();
                VideoPreViewActivity.total_like = banglaMusicVideoList.get(position).getTotalLike();
                VideoPreViewActivity.genre = banglaMusicVideoList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "bm";
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_banglaNatok.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recycler_view_banglaNatok, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = true;
                VideoPreViewActivity.duration = banglaNatokList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  banglaNatokList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  banglaNatokList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = banglaNatokList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  banglaNatokList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  banglaNatokList.get(position).getPhysical_file_name();
                VideoPreViewActivity.contentImg =  banglaNatokList.get(position).getContent_image();
                VideoPreViewActivity.ZedID =  banglaNatokList.get(position).getZeid();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_NATOK;
                VideoPreViewActivity.info = banglaNatokList.get(position).getInfo();
                VideoPreViewActivity.total_views = banglaNatokList.get(position).getTotalView();
                VideoPreViewActivity.total_like = banglaNatokList.get(position).getTotalLike();
                VideoPreViewActivity.genre = banglaNatokList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "bn";
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_banglaTelefilm.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recycler_view_banglaTelefilm, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = true;
                VideoPreViewActivity.duration = banglaTelefilmList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  banglaTelefilmList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  banglaTelefilmList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = banglaTelefilmList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  banglaTelefilmList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  banglaTelefilmList.get(position).getPhysical_file_name();
                VideoPreViewActivity.contentImg =  banglaTelefilmList.get(position).getContent_image();
                VideoPreViewActivity.ZedID =  banglaTelefilmList.get(position).getZeid();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_TELEFILM;
                VideoPreViewActivity.info = banglaTelefilmList.get(position).getInfo();
                VideoPreViewActivity.total_views = banglaTelefilmList.get(position).getTotalView();
                VideoPreViewActivity.total_like = banglaTelefilmList.get(position).getTotalLike();
                VideoPreViewActivity.genre = banglaTelefilmList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "bt";
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void parseBT(String urlBanglaTelefilm) {
        JsonArrayRequest request = new JsonArrayRequest(urlBanglaTelefilm, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("Responsee", jsonArray.toString());
                loading.dismiss();
                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        banglaTelefilm = new BanglaTelefilm();

                        banglaTelefilm.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
                        String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ","%20");
                        banglaTelefilm.setContent_image(Config.URL_SOURCE+imgUrl);
                        banglaTelefilm.setInfo(obj.getString(Config.INFO_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setDuration(obj.getString(Config.DURATION_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setGenre(obj.getString(Config.GENRE_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setTotalLike(obj.getString(Config.TOTAL_LIKE_BANGLA_MUSIC_VIDEO));
                        banglaTelefilm.setTotalView(obj.getString(Config.TOTAL_VIEWS_BANGLA_MUSIC_VIDEO));

                        banglaTelefilmList.add(banglaTelefilm);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recycler_view_banglaTelefilm.setAdapter(adapterBT);
                adapterBT.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(HDCategoryActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void initRecyclerBT() {
        adapterBT = new BanglaTAdapter(HDCategoryActivity.this, banglaTelefilmList);
        recycler_view_banglaTelefilm= (RecyclerView) findViewById(R.id.recycler_view_banglaTelefilm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_banglaTelefilm.setLayoutManager(mLayoutManager);
        recycler_view_banglaTelefilm.setItemAnimator(new DefaultItemAnimator());
        recycler_view_banglaTelefilm.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void parseBN(String urlBanglaNatok) {
        JsonArrayRequest request = new JsonArrayRequest(urlBanglaNatok, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("Responsee", jsonArray.toString());
                loading.dismiss();
                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        banglaNatok = new BanglaNatok();

                        banglaNatok.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
                        String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ","%20");
                        banglaNatok.setContent_image(Config.URL_SOURCE+imgUrl);
                        banglaNatok.setInfo(obj.getString(Config.INFO_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setDuration(obj.getString(Config.DURATION_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setGenre(obj.getString(Config.GENRE_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setTotalLike(obj.getString(Config.TOTAL_LIKE_BANGLA_MUSIC_VIDEO));
                        banglaNatok.setTotalView(obj.getString(Config.TOTAL_VIEWS_BANGLA_MUSIC_VIDEO));

                        banglaNatokList.add(banglaNatok);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recycler_view_banglaNatok.setAdapter(adapterBN);
                adapterBN.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(HDCategoryActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void initRecyclerBN() {
        adapterBN = new BNAdapter(HDCategoryActivity.this, banglaNatokList);
        recycler_view_banglaNatok = (RecyclerView) findViewById(R.id.recycler_view_banglaNatok);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_banglaNatok.setLayoutManager(mLayoutManager);
        recycler_view_banglaNatok.setItemAnimator(new DefaultItemAnimator());
        recycler_view_banglaNatok.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initRecyclerBMV() {
        loading = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);
        loading.setCancelable(true);


        adapterBM = new BMAdapter(HDCategoryActivity.this, banglaMusicVideoList);
        recycler_view_banglaMusicVideo = (RecyclerView) findViewById(R.id.recycler_view_banglaMusicVideo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_banglaMusicVideo.setLayoutManager(mLayoutManager);
        recycler_view_banglaMusicVideo.setItemAnimator(new DefaultItemAnimator());
        recycler_view_banglaMusicVideo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void parseBMV(String urlBanglaMusicVideo) {
        JsonArrayRequest request = new JsonArrayRequest(urlBanglaMusicVideo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("Responsee", jsonArray.toString());
                loading.dismiss();
                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        banglaMusicVideo = new BanglaMusicVideo();

                        banglaMusicVideo.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
                        String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ","%20");
                        banglaMusicVideo.setContent_image(Config.URL_SOURCE+imgUrl);
                        banglaMusicVideo.setInfo(obj.getString(Config.INFO_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setDuration(obj.getString(Config.DURATION_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setGenre(obj.getString(Config.GENRE_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setTotalLike(obj.getString(Config.TOTAL_LIKE_BANGLA_MUSIC_VIDEO));
                        banglaMusicVideo.setTotalView(obj.getString(Config.TOTAL_VIEWS_BANGLA_MUSIC_VIDEO));

                        banglaMusicVideoList.add(banglaMusicVideo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recycler_view_banglaMusicVideo.setAdapter(adapterBM);
                adapterBM.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(HDCategoryActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void initUI() {
        txtBMVMore = (TextView) findViewById(R.id.banglaMusicVideoMore);
        txtBNMore = (TextView) findViewById(R.id.banglaNatokMore);
        txtBTMore = (TextView) findViewById(R.id.banglaTelefilmMore);

        btnHelp = (ImageView) findViewById(R.id.btnHelpVdohd);
        btnHome = (ImageView) findViewById(R.id.btnHomeVdohd);
        btnDownloadPage = (ImageView) findViewById(R.id.btnDownloadHistoryPageVdohd);
        btnBuddy = (ImageView) findViewById(R.id.buddyVdohd);
        btnBdTube = (ImageView) findViewById(R.id.bdtubeVdohd);
        btnBanglaBeats = (ImageView) findViewById(R.id.banglabeatsVdohd);
        btnAmarStckr = (ImageView) findViewById(R.id.amrStckrVdohd);
        btnRateme = (ImageView) findViewById(R.id.ratemeVdohd);
        btnHelpSame = (ImageView) findViewById(R.id.btnHelpSameVdohd);

        btnHelp.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnDownloadPage.setOnClickListener(this);
        btnBuddy.setOnClickListener(this);
        btnBdTube.setOnClickListener(this);
        btnBanglaBeats.setOnClickListener(this);
        btnAmarStckr.setOnClickListener(this);
        btnRateme.setOnClickListener(this);
        btnHelpSame.setOnClickListener(this);

        txtBMVMore.setOnClickListener(this);
        txtBNMore.setOnClickListener(this);
        txtBTMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.banglaMusicVideoMore:
                intent = new Intent(HDCategoryActivity.this, BMGridActivity.class);
                startActivity(intent);
                break;
            case R.id.banglaNatokMore:
                intent = new Intent(HDCategoryActivity.this, BNGridActivity.class);
                startActivity(intent);
                break;
            case R.id.banglaTelefilmMore:
                intent = new Intent(HDCategoryActivity.this, BTGridActivity.class);
                startActivity(intent);
                break;

            case R.id.btnHelpVdohd :
                intent = new Intent(HDCategoryActivity.this, FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHomeVdohd :
                intent = new Intent(HDCategoryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnDownloadHistoryPageVdohd :
                intent = new Intent(HDCategoryActivity.this,MyDownLoadsActivity.class);
                startActivity(intent);
                break;

            case R.id.buddyVdohd :
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy")));
                break;
            case R.id.bdtubeVdohd:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube")));
                break;
            case R.id.banglabeatsVdohd:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en")));
                break;
            case R.id.amrStckrVdohd:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker")));
                break;
            case R.id.ratemeVdohd:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en")));
                break;

            case R.id.btnHelpSameVdohd:
                intent = new Intent(HDCategoryActivity.this,HelpActivity.class);
                startActivity(intent);
                break;

        }

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.RecyclerTouchListener.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.RecyclerTouchListener.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @SuppressWarnings("deprecation")
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public interface ClickListener {
            void onClick(View view, int position);

            void onLongClick(View view, int position);
        }
    }
}
