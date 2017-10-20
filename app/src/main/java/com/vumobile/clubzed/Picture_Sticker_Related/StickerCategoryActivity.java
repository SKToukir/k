package com.vumobile.clubzed.Picture_Sticker_Related;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.MyDownLoadsActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.StickerAnimateAdapter;
import com.vumobile.clubzed.adapter.StickerBanglaAdapter;
import com.vumobile.clubzed.adapter.StickerCricAdapter;
import com.vumobile.clubzed.adapter.StickerEidAdapter;
import com.vumobile.clubzed.adapter.StickerEventAdapter;
import com.vumobile.clubzed.adapter.StickerFunnyAdapter;
import com.vumobile.clubzed.adapter.StickerIslamicAdapter;
import com.vumobile.clubzed.adapter.StickerLoveAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.StickerAnimated;
import com.vumobile.clubzed.model.StickerBanglaClass;
import com.vumobile.clubzed.model.StickerCricBissoClass;
import com.vumobile.clubzed.model.StickerEidClass;
import com.vumobile.clubzed.model.StickerEventClass;
import com.vumobile.clubzed.model.StickerFunnyClass;
import com.vumobile.clubzed.model.StickerIslamicClass;
import com.vumobile.clubzed.model.StickerLoveClass;
import com.vumobile.clubzed.util.FAQActivity;
import com.vumobile.clubzed.util.HelpActivity;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StickerCategoryActivity extends ActionBarActivity implements View.OnClickListener{

    Intent intent;
    ProgressDialog loading;
    Toolbar toolbar;
    Button btnBack;
    Subscriptio_Class subscriptio_class;
    TextView txtBanglaMore,txtCricketBissoMore,txtEidMore,txtEventMore,txtFunnyMore,txtIslamicMore,txtLoveMore,txtAnimatedStickerMore;
    StickerAnimated stickerAnimatedClass;
    StickerLoveClass stickerLoveClass;
    StickerIslamicClass stickerIslamicClass;
    StickerFunnyClass stickerFunnyClass;
    StickerEventClass stickerEventClass;
    StickerEidClass eidClass;
    StickerCricBissoClass stickerCricBissoClass;
    StickerBanglaClass stickerBanglaClass;
    List<StickerAnimated> stickerAnimatedList = new ArrayList<StickerAnimated>();
    List<StickerLoveClass> stickerLoveClassList = new ArrayList<StickerLoveClass>();
    List<StickerIslamicClass> stickerIslamicClassList = new ArrayList<StickerIslamicClass>();
    List<StickerFunnyClass> stickerFunnyClassList = new ArrayList<StickerFunnyClass>();
    List<StickerEventClass> stickerEventClassList = new ArrayList<StickerEventClass>();
    List<StickerEidClass> stickerEidClassList = new ArrayList<StickerEidClass>();
    List<StickerCricBissoClass> stickerCricBissoClassList = new ArrayList<StickerCricBissoClass>();
    List<StickerBanglaClass> stickerBanglaClassList = new ArrayList<StickerBanglaClass>();

    RecyclerView recyclerStBangla,recyclerCricBisso,recyclerEid,recyclerEent,recyclerFunny,recyclerIslamic,recyclerLove,recyclerAnimate;
    RecyclerView.Adapter adapterStBangla, adapterCricBisso, adapterEid,adapterEvent,adapterFunny,adapterIslamic,adapterLove,adapterAnimate;
    ImageView btnHelp, btnHome, btnMydownload,btnBuddy,btnBdTube,btnBanglaBeats,btnAmarSticker,btnRateMe,btnHelpSame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_category);

        initUI();

        //new BackGroundTask().execute();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        parseBanglaSticker(Config.URL_STICKER_BANGLA);
        parseCricBisso(Config.URL_STICKER_CRICKET);
        parseEidSticker(Config.URL_STICKER_EID);
        parseEvent(Config.URL_STICKER_EVENT);
        parseFunny(Config.URL_STICKER_FUNNY);
        parseIslamic(Config.URL_STICKER_ISLAMIC);
        parseLove(Config.URL_STICKER_LOVE);
        parseAnimate(Config.URL_STICKER_ANIMATED);

        Subscriptio_Class.applicationContext = StickerCategoryActivity.this;
        subscriptio_class = new Subscriptio_Class();

        // init bangla sticker recycler
        initRecyclerBangla();

        //initsticker cric bisso recyc;er
        initCricBisso();

        //init sticker eid recycler
        initEidRecycler();

        //init sticker event recycler
        initEvent();

        //init sticker funny recycler
        initFunny();

        //init islamic recycler
        initIslamic();

        //init love recycler
        initLove();

        //init animate sticker recycler
        initAnimate();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StickerCategoryActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        recyclerStBangla.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerStBangla, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StickerPreview.contentCode = stickerBanglaClassList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerBanglaClassList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerBanglaClassList.get(position).getContent_title();
                StickerPreview.sContentType = stickerBanglaClassList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerBanglaClassList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerBanglaClassList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerBanglaClassList.get(position).getZeid();
                StickerPreview.image = stickerBanglaClassList.get(position).getImageUrl();
                StickerPreview.STICKER_TYPE = "bst";
                StickerPreview.total_likes = stickerBanglaClassList.get(position).getLikes();
                StickerPreview.total_downloads = stickerBanglaClassList.get(position).getDownloads();
                StickerPreview.related_pic_url = Config.URL_STICKER_BANGLA;
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerCricBisso.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerCricBisso, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StickerPreview.contentCode = stickerCricBissoClassList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerCricBissoClassList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerCricBissoClassList.get(position).getContent_title();
                StickerPreview.sContentType = stickerCricBissoClassList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerCricBissoClassList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerCricBissoClassList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerCricBissoClassList.get(position).getZeid();
                StickerPreview.image = stickerCricBissoClassList.get(position).getImageUrl();
                StickerPreview.STICKER_TYPE = "cbst";
                StickerPreview.related_pic_url = Config.URL_STICKER_CRICKET;
                StickerPreview.total_downloads = stickerCricBissoClassList.get(position).getDownloads();
                StickerPreview.total_likes = stickerCricBissoClassList.get(position).getTotal_like();
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerEid.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerEid, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StickerPreview.contentCode = stickerEidClassList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerEidClassList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerEidClassList.get(position).getContent_title();
                StickerPreview.sContentType = stickerEidClassList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerEidClassList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerEidClassList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerEidClassList.get(position).getZeid();
                StickerPreview.image = stickerEidClassList.get(position).getImageUrl();
                StickerPreview.total_downloads = stickerEidClassList.get(position).getDownloads();
                StickerPreview.STICKER_TYPE = "eidst";
                StickerPreview.total_likes = stickerEidClassList.get(position).getTotal_like();
                StickerPreview.related_pic_url = Config.URL_STICKER_EID;
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerEent.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerEent, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StickerPreview.contentCode = stickerEventClassList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerEventClassList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerEventClassList.get(position).getContent_title();
                StickerPreview.sContentType = stickerEventClassList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerEventClassList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerEventClassList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerEventClassList.get(position).getZeid();
                StickerPreview.image = stickerEventClassList.get(position).getImageUrl();
                StickerPreview.total_likes = stickerEventClassList.get(position).getLikes();
                StickerPreview.total_downloads = stickerEventClassList.get(position).getDownloads();
                StickerPreview.STICKER_TYPE = "est";
                StickerPreview.related_pic_url = Config.URL_STICKER_EVENT;
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerFunny.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerFunny, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StickerPreview.contentCode = stickerFunnyClassList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerFunnyClassList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerFunnyClassList.get(position).getContent_title();
                StickerPreview.sContentType = stickerFunnyClassList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerFunnyClassList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerFunnyClassList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerFunnyClassList.get(position).getZeid();
                StickerPreview.image = stickerFunnyClassList.get(position).getImageUrl();
                StickerPreview.total_downloads = stickerFunnyClassList.get(position).getDownloads();
                StickerPreview.STICKER_TYPE = "fst";
                StickerPreview.total_likes = stickerFunnyClassList.get(position).getTotal_like();
                StickerPreview.related_pic_url = Config.URL_STICKER_FUNNY;
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerIslamic.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerIslamic, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StickerPreview.contentCode = stickerIslamicClassList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerIslamicClassList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerIslamicClassList.get(position).getContent_title();
                StickerPreview.sContentType = stickerIslamicClassList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerIslamicClassList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerIslamicClassList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerIslamicClassList.get(position).getZeid();
                StickerPreview.image = stickerIslamicClassList.get(position).getImageUrl();
                StickerPreview.total_downloads = stickerIslamicClassList.get(position).getDownloads();
                StickerPreview.STICKER_TYPE = "ist";
                StickerPreview.total_likes = stickerIslamicClassList.get(position).getTotal_like();
                StickerPreview.related_pic_url = Config.URL_STICKER_ISLAMIC;
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerLove.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerLove, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StickerPreview.contentCode = stickerLoveClassList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerLoveClassList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerLoveClassList.get(position).getContent_title();
                StickerPreview.sContentType = stickerLoveClassList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerLoveClassList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerLoveClassList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerLoveClassList.get(position).getZeid();
                StickerPreview.image = stickerLoveClassList.get(position).getImageUrl();
                StickerPreview.total_downloads = stickerLoveClassList.get(position).getDownloads();
                StickerPreview.STICKER_TYPE = "lst";
                StickerPreview.total_likes = stickerLoveClassList.get(position).getTotal_like();
                StickerPreview.related_pic_url = Config.URL_STICKER_LOVE;
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerAnimate.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerAnimate, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StickerPreview.contentCode = stickerAnimatedList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerAnimatedList.get(position).getCateGory_code();
                StickerPreview.contentName = stickerAnimatedList.get(position).getContent_title();
                StickerPreview.sContentType = stickerAnimatedList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerAnimatedList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerAnimatedList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerAnimatedList.get(position).getZeid();
                StickerPreview.image = stickerAnimatedList.get(position).getImageUrl();
                StickerPreview.total_downloads = stickerAnimatedList.get(position).getDownloads();
                StickerPreview.STICKER_TYPE = "ast";
                StickerPreview.total_likes = stickerAnimatedList.get(position).getLikes();
                StickerPreview.related_pic_url = Config.URL_STICKER_ANIMATED;
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    private void initUI() {
        loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_sticker_cats);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        btnHome = (ImageView) findViewById(R.id.homeSt);
        btnHelp = (ImageView) findViewById(R.id.helpSt);
        btnHelpSame = (ImageView) findViewById(R.id.helpSameSt);
        btnMydownload = (ImageView) findViewById(R.id.myDownloadsSt);

        btnBuddy = (ImageView) findViewById(R.id.buddySt);
        btnBdTube = (ImageView) findViewById(R.id.bdtubeSt);
        btnBanglaBeats = (ImageView) findViewById(R.id.banglabeatsSt);
        btnAmarSticker = (ImageView) findViewById(R.id.amarStSt);
        btnRateMe = (ImageView) findViewById(R.id.ratemeSt);

        btnBuddy.setOnClickListener(this);
        btnBdTube.setOnClickListener(this);
        btnBanglaBeats.setOnClickListener(this);
        btnRateMe.setOnClickListener(this);
        btnAmarSticker.setOnClickListener(this);

        btnHome.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnHelpSame.setOnClickListener(this);
        btnMydownload.setOnClickListener(this);

        txtBanglaMore = (TextView) findViewById(R.id.stickerBanglaMore);
        txtCricketBissoMore = (TextView) findViewById(R.id.cricBissoMore);
        txtEidMore = (TextView) findViewById(R.id.eidStickerMore);
        txtEventMore = (TextView) findViewById(R.id.eventMore);
        txtFunnyMore = (TextView) findViewById(R.id.funnyMore);
        txtIslamicMore = (TextView) findViewById(R.id.islamicMore);
        txtLoveMore = (TextView) findViewById(R.id.loveMore);
        txtAnimatedStickerMore = (TextView) findViewById(R.id.animatedStickerMore);

        txtBanglaMore.setOnClickListener(this);
        txtCricketBissoMore.setOnClickListener(this);
        txtEidMore.setOnClickListener(this);
        txtEventMore.setOnClickListener(this);
        txtFunnyMore.setOnClickListener(this);
        txtIslamicMore.setOnClickListener(this);
        txtLoveMore.setOnClickListener(this);
        txtAnimatedStickerMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.animatedStickerMore:
                intent = new Intent(StickerCategoryActivity.this,StickerAnimateGridActivity.class);
                startActivity(intent);
                break;
            case R.id.stickerBanglaMore:
                intent = new Intent(StickerCategoryActivity.this,StickerBanglaGridActivity.class);
                startActivity(intent);
                break;
            case R.id.cricBissoMore:
                intent = new Intent(StickerCategoryActivity.this,StickerCricBissoGridActivity.class);
                startActivity(intent);
                break;
            case R.id.eidStickerMore:
                intent = new Intent(StickerCategoryActivity.this,StickerEidGridActivity.class);
                startActivity(intent);
                break;
            case R.id.eventMore:
                intent = new Intent(StickerCategoryActivity.this,EventGridActivity.class);
                startActivity(intent);
                break;
            case R.id.funnyMore:
                intent = new Intent(StickerCategoryActivity.this,FunnyGridActivity.class);
                startActivity(intent);
                break;
            case R.id.islamicMore:
                intent = new Intent(StickerCategoryActivity.this,IslamicGridActivity.class);
                startActivity(intent);
                break;
            case R.id.loveMore:
                intent = new Intent(StickerCategoryActivity.this,StickerLoveGridAcitvity.class);
                startActivity(intent);
                break;
            case R.id.homeSt:
                intent = new Intent(StickerCategoryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.helpSt:
                intent = new Intent(StickerCategoryActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.helpSameSt:
                intent = new Intent(StickerCategoryActivity.this,FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.myDownloadsSt:
                intent = new Intent(StickerCategoryActivity.this, MyDownLoadsActivity.class);
                startActivity(intent);
                break;
            case R.id.buddySt :
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy")));
                break;
            case R.id.bdtubeSt:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube")));
                break;
            case R.id.banglabeatsSt:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en")));
                break;
            case R.id.amarStSt:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker")));
                break;
            case R.id.ratemeSt:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en")));
                break;

        }
    }

    private void initAnimate() {
        adapterAnimate = new StickerAnimateAdapter(StickerCategoryActivity.this,stickerAnimatedList);
        recyclerAnimate = (RecyclerView) findViewById(R.id.recycler_view_animatedSticker);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerAnimate.setLayoutManager(mLayoutManager);
        recyclerAnimate.setItemAnimator(new DefaultItemAnimator());
        recyclerAnimate.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }
    private void initLove() {
        adapterLove = new StickerLoveAdapter(StickerCategoryActivity.this,stickerLoveClassList);
        recyclerLove = (RecyclerView) findViewById(R.id.recycler_view_love);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerLove.setLayoutManager(mLayoutManager);
        recyclerLove.setItemAnimator(new DefaultItemAnimator());
        recyclerLove.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initIslamic() {
        adapterIslamic = new StickerIslamicAdapter(StickerCategoryActivity.this,stickerIslamicClassList);
        recyclerIslamic = (RecyclerView) findViewById(R.id.recycler_view_islamic);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerIslamic.setLayoutManager(mLayoutManager);
        recyclerIslamic.setItemAnimator(new DefaultItemAnimator());
        recyclerIslamic.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initFunny() {
        adapterFunny = new StickerFunnyAdapter(StickerCategoryActivity.this,stickerFunnyClassList);
        recyclerFunny = (RecyclerView) findViewById(R.id.recycler_view_funny);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerFunny.setLayoutManager(mLayoutManager);
        recyclerFunny.setItemAnimator(new DefaultItemAnimator());
        recyclerFunny.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initEvent() {
        adapterEvent = new StickerEventAdapter(StickerCategoryActivity.this,stickerEventClassList);
        recyclerEent = (RecyclerView) findViewById(R.id.recycler_view_event);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerEent.setLayoutManager(mLayoutManager);
        recyclerEent.setItemAnimator(new DefaultItemAnimator());
        recyclerEent.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initEidRecycler() {
        adapterEid = new StickerEidAdapter(StickerCategoryActivity.this,stickerEidClassList);
        recyclerEid = (RecyclerView) findViewById(R.id.recycler_view_eidSticker);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerEid.setLayoutManager(mLayoutManager);
        recyclerEid.setItemAnimator(new DefaultItemAnimator());
        recyclerEid.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initCricBisso() {
        adapterCricBisso = new StickerCricAdapter(StickerCategoryActivity.this,stickerCricBissoClassList);
        recyclerCricBisso = (RecyclerView) findViewById(R.id.recycler_view_cricBisso);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerCricBisso.setLayoutManager(mLayoutManager);
        recyclerCricBisso.setItemAnimator(new DefaultItemAnimator());
        recyclerCricBisso.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initRecyclerBangla() {
        adapterStBangla = new StickerBanglaAdapter(StickerCategoryActivity.this,stickerBanglaClassList);
        recyclerStBangla = (RecyclerView) findViewById(R.id.recycler_view_stickerBangla);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerStBangla.setLayoutManager(mLayoutManager);
        recyclerStBangla.setItemAnimator(new DefaultItemAnimator());
        recyclerStBangla.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }



    class BackGroundTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

//            parseBanglaSticker(Config.URL_STICKER_BANGLA);
//            parseCricBisso(Config.URL_STICKER_CRICKET);
//            parseEidSticker(Config.URL_STICKER_EID);
//            parseEvent(Config.URL_STICKER_EVENT);
//            parseFunny(Config.URL_STICKER_FUNNY);
//            parseIslamic(Config.URL_STICKER_ISLAMIC);
//            parseLove(Config.URL_STICKER_LOVE);
//            parseAnimate(Config.URL_STICKER_ANIMATED);
            return null;
        }
    }

    private void parseAnimate(String urlStickerAnimated) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStickerAnimated, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        stickerAnimatedClass = new StickerAnimated();

                        stickerAnimatedClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_ANIMATED));
                        stickerAnimatedClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_ANIMATED));
                        stickerAnimatedClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_ANIMATED));
                        stickerAnimatedClass.setType(obj.getString(Config.TYPE_STICKER_ANIMATED));
                        stickerAnimatedClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_ANIMATED));
                        stickerAnimatedClass.setZeid(obj.getString(Config.ZID_STICKER_ANIMATED));
                        stickerAnimatedClass.setPath(obj.getString(Config.PATH_STICKER_ANIMATED));
                        stickerAnimatedClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_ANIMATED).replace(" ","%20"));
                        stickerAnimatedClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_ANIMATED));
                        stickerAnimatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        stickerAnimatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        Log.d("URLA",stickerAnimatedClass.getImageUrl());
                        stickerAnimatedList.add(stickerAnimatedClass);

                    }
                    recyclerAnimate.setAdapter(adapterAnimate);
                    adapterAnimate.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StickerCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseLove(String urlStickerLove) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStickerLove, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        stickerLoveClass = new StickerLoveClass();

                        stickerLoveClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_LOVE));
                        stickerLoveClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_LOVE));
                        stickerLoveClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_LOVE));
                        stickerLoveClass.setType(obj.getString(Config.TYPE_STICKER_LOVE));
                        stickerLoveClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_LOVE));
                        stickerLoveClass.setZeid(obj.getString(Config.ZID_STICKER_LOVE));
                        stickerLoveClass.setPath(obj.getString(Config.PATH_STICKER_LOVE));
                        stickerLoveClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_LOVE).replace(" ","%20"));
                        stickerLoveClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_LOVE));
                        stickerLoveClass.setTotal_like(obj.getString(Config.TOTAL_LIKE));
                        stickerLoveClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        stickerLoveClassList.add(stickerLoveClass);

                    }
                    recyclerLove.setAdapter(adapterLove);
                    adapterLove.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StickerCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseIslamic(String urlStickerIslamic) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStickerIslamic, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


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
                        stickerIslamicClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        stickerIslamicClassList.add(stickerIslamicClass);

                    }
                    recyclerIslamic.setAdapter(adapterIslamic);
                    adapterIslamic.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StickerCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseFunny(String urlStickerFunny) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStickerFunny, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        stickerFunnyClass = new StickerFunnyClass();

                        stickerFunnyClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_FUNNY));
                        stickerFunnyClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_FUNNY));
                        stickerFunnyClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_FUNNY));
                        stickerFunnyClass.setType(obj.getString(Config.TYPE_STICKER_FUNNY));
                        stickerFunnyClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_FUNNY));
                        stickerFunnyClass.setZeid(obj.getString(Config.ZID_STICKER_FUNNY));
                        stickerFunnyClass.setPath(obj.getString(Config.PATH_STICKER_FUNNY));
                        stickerFunnyClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_FUNNY).replace(" ","%20"));
                        stickerFunnyClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_FUNNY));
                        stickerFunnyClass.setTotal_like(obj.getString(Config.TOTAL_LIKE));
                        stickerFunnyClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        stickerFunnyClassList.add(stickerFunnyClass);

                    }
                    recyclerFunny.setAdapter(adapterFunny);
                    adapterFunny.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StickerCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseEvent(String urlStickerEvent) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStickerEvent, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        stickerEventClass = new StickerEventClass();

                        stickerEventClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_EVENT));
                        stickerEventClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_EVENT));
                        stickerEventClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_EVENT));
                        stickerEventClass.setType(obj.getString(Config.TYPE_STICKER_EVENT));
                        stickerEventClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_EVENT));
                        stickerEventClass.setZeid(obj.getString(Config.ZID_STICKER_EVENT));
                        stickerEventClass.setPath(obj.getString(Config.PATH_STICKER_EVENT));
                        stickerEventClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_EVENT).replace(" ","%20"));
                        stickerEventClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_EVENT));
                        stickerEventClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        stickerEventClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                        Log.d("GOOOOOOOOOOO",obj.getString(Config.TOTAL_DOWNLOADS));

                        stickerEventClassList.add(stickerEventClass);

                    }
                    recyclerEent.setAdapter(adapterEvent);
                    adapterEvent.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StickerCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseEidSticker(String urlStickerEid) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStickerEid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        eidClass = new StickerEidClass();

                        eidClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_EID));
                        eidClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_EID));
                        eidClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_EID));
                        eidClass.setType(obj.getString(Config.TYPE_STICKER_EID));
                        eidClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_EID));
                        eidClass.setZeid(obj.getString(Config.ZID_STICKER_EID));
                        eidClass.setPath(obj.getString(Config.PATH_STICKER_EID));
                        eidClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_EID).replace(" ","%20"));
                        eidClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_EID));
                        eidClass.setTotal_like(obj.getString(Config.TOTAL_LIKE));
                        eidClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        stickerEidClassList.add(eidClass);

                    }
                    recyclerEid.setAdapter(adapterEid);
                    adapterEid.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StickerCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseCricBisso(String urlStickerCricket) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStickerCricket, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        stickerCricBissoClass = new StickerCricBissoClass();

                        stickerCricBissoClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_CRIC_BISSO));
                        stickerCricBissoClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_CRIC_BISSO));
                        stickerCricBissoClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_CRIC_BISSO));
                        stickerCricBissoClass.setType(obj.getString(Config.TYPE_STICKER_CRIC_BISSO));
                        stickerCricBissoClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_CRIC_BISSO));
                        stickerCricBissoClass.setZeid(obj.getString(Config.ZID_STICKER_CRIC_BISSO));
                        stickerCricBissoClass.setPath(obj.getString(Config.PATH_STICKER_CRIC_BISSO));
                        stickerCricBissoClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_CRIC_BISSO).replace(" ","%20"));
                        stickerCricBissoClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_CRIC_BISSO));
                        stickerCricBissoClass.setTotal_like(obj.getString(Config.TOTAL_LIKE));
                        stickerCricBissoClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        stickerCricBissoClassList.add(stickerCricBissoClass);

                    }
                    recyclerCricBisso.setAdapter(adapterCricBisso);
                    adapterCricBisso.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StickerCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseBanglaSticker(String urlStickerBangla) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStickerBangla, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                loading.dismiss();
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        stickerBanglaClass = new StickerBanglaClass();

                        stickerBanglaClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER_BANGLA));
                        stickerBanglaClass.setCateGory_code(obj.getString(Config.CATEGORY_CODE_STICKER_BANGLA));
                        stickerBanglaClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER_BANGLA));
                        stickerBanglaClass.setType(obj.getString(Config.TYPE_STICKER_BANGLA));
                        stickerBanglaClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER_BANGLA));
                        stickerBanglaClass.setZeid(obj.getString(Config.ZID_STICKER_BANGLA));
                        stickerBanglaClass.setPath(obj.getString(Config.PATH_STICKER_BANGLA));
                        stickerBanglaClass.setImageUrl(obj.getString(Config.IMAGE_URL_STICKER_BANGLA).replace(" ","%20"));
                        stickerBanglaClass.setLive_date(obj.getString(Config.LIVE_DATE_STICKER_BANGLA));
                        stickerBanglaClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        stickerBanglaClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        stickerBanglaClassList.add(stickerBanglaClass);

                    }
                    recyclerStBangla.setAdapter(adapterStBangla);
                    adapterStBangla.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(StickerCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }
}
