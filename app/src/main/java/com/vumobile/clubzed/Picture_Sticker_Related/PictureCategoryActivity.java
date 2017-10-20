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
import com.vumobile.clubzed.adapter.PictureBangladeshiCeleAdapter;
import com.vumobile.clubzed.adapter.PictureBollyCeleAdapter;
import com.vumobile.clubzed.adapter.PictureCoolAdapter;
import com.vumobile.clubzed.adapter.PictureHollywoodCeleAdapter;
import com.vumobile.clubzed.adapter.PictureLoveAdapter;
import com.vumobile.clubzed.adapter.PictureSpecialEventAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.PictureBangladeshiCele;
import com.vumobile.clubzed.model.PictureBollywoodCeleClass;
import com.vumobile.clubzed.model.PictureCoolClass;
import com.vumobile.clubzed.model.PictureHollywoodCelebratyClass;
import com.vumobile.clubzed.model.PictureLoveClass;
import com.vumobile.clubzed.model.PictureSpecialEventClass;
import com.vumobile.clubzed.util.FAQActivity;
import com.vumobile.clubzed.util.HelpActivity;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PictureCategoryActivity extends ActionBarActivity implements View.OnClickListener{

    ProgressDialog loading;

    Toolbar toolbar;
    Button btnBack;
    Intent intent;
    Subscriptio_Class subscriptio_class;
    TextView txtBollyCele, txtHollycele, txtBangladeshCele, txtSpecialEvent, txtCool, txtLove;
    PictureLoveClass pictureLoveClass;
    PictureCoolClass pictureCoolClass;
    PictureSpecialEventClass pictureSpecialEventClass;
    PictureBangladeshiCele pictureBangladeshiCele;
    PictureHollywoodCelebratyClass picHolyClass;
    PictureBollywoodCeleClass pictureBollywoodCeleClass;
    List<PictureLoveClass> pictureLoveClassList = new ArrayList<PictureLoveClass>();
    List<PictureCoolClass> pictureCoolClassList = new ArrayList<PictureCoolClass>();
    List<PictureSpecialEventClass> specialEventList = new ArrayList<PictureSpecialEventClass>();
    List<PictureBangladeshiCele> bdCeleList = new ArrayList<PictureBangladeshiCele>();
    List<PictureHollywoodCelebratyClass> holyCeleList = new ArrayList<PictureHollywoodCelebratyClass>();
    List<PictureBollywoodCeleClass> pictureBollywoodCeleClassList = new ArrayList<PictureBollywoodCeleClass>();
    RecyclerView recyclerBollyCele,recyclerHolyCele,recyclerbdCle,recyclerSpecialEvnt,recyclerPictureCool,recyclerPicLove;
    RecyclerView.Adapter adapterBollyCele,adapterHolyCele,adapterBdCele,adapterSpclEvent,adapterPicturecool,adapterPicLove;

    ImageView btnHelp, btnHome, btnMydownload,btnBuddy,btnBdTube,btnBanglaBeats,btnAmarSticker,btnRateMe,btnHelpSame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_category);

        initUI();


//        //init bollywood celebraty picture recycler
        initPicBollyCele();
//        parseBollyCele(Config.URL_PICTURE_BOLLY_CELE);
//
//        //init hollywood celebraty recycler
        initPicHolyCele();
//        parseHollyCeleData(Config.URL_HOLLYWOOD_CELE);
//
//        //init bangladeshi celebraty recycler
        initPicBdCeleRecycler();
//        parseBdCeleData(Config.URL_PICTURE_BD_CELEBRATY);
//
//        //init special event recycler
        initSpecialEventRecycler();
//        parseSpecialEventData(Config.URL_PICTURE_SPECIAL_EVENT);
//
//        //init picture cool recycler
        initPictureCoolRecycler();
//        parsePictureCool(Config.URL_PICTURE_COOL);
//
//        //init picture love recycler
        initPictureLove();
//        parsePictureLove(Config.URL_PICTURE_LOVE);

        new BackgroundTask().execute();

        Subscriptio_Class.applicationContext = PictureCategoryActivity.this;
        subscriptio_class = new Subscriptio_Class();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureCategoryActivity.this.finish();
               // overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        recyclerBollyCele.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerBollyCele, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = pictureBollywoodCeleClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = pictureBollywoodCeleClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = pictureBollywoodCeleClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = pictureBollywoodCeleClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = pictureBollywoodCeleClassList.get(position).getPhysicalFilwName();
                PictureDetailsActivity.contentImg = pictureBollywoodCeleClassList.get(position).getImage_url();
                PictureDetailsActivity.ZedID = pictureBollywoodCeleClassList.get(position).getZeid();
                PictureDetailsActivity.image= pictureBollywoodCeleClassList.get(position).getImage_url();
                PictureDetailsActivity.likes = pictureBollywoodCeleClassList.get(position).getLikes();
                PictureDetailsActivity.total_downloads = pictureBollywoodCeleClassList.get(position).getDownloads();
                PictureDetailsActivity.related_pic = Config.URL_PICTURE_BOLLY_CELE;
                PictureDetailsActivity.PIC_TYPE = "bc";
                Subscriptio_Class.type="pic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerHolyCele.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerHolyCele, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = holyCeleList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = holyCeleList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = holyCeleList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = holyCeleList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = holyCeleList.get(position).getPhysicalFilwName();
                PictureDetailsActivity.contentImg = holyCeleList.get(position).getImage_url();
                PictureDetailsActivity.ZedID = holyCeleList.get(position).getZeid();
                PictureDetailsActivity.image= holyCeleList.get(position).getImage_url();
                PictureDetailsActivity.likes = holyCeleList.get(position).getLikes();
                PictureDetailsActivity.total_downloads = holyCeleList.get(position).getDownloads();
                PictureDetailsActivity.PIC_TYPE = "hc";
                PictureDetailsActivity.related_pic = Config.URL_HOLLYWOOD_CELE;
                Subscriptio_Class.type="pic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerbdCle.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerbdCle, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = bdCeleList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = bdCeleList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = bdCeleList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = bdCeleList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = bdCeleList.get(position).getPhysicalFilwName();
                PictureDetailsActivity.contentImg = bdCeleList.get(position).getImage_url();
                PictureDetailsActivity.ZedID = bdCeleList.get(position).getZeid();
                PictureDetailsActivity.image= bdCeleList.get(position).getImage_url();
                PictureDetailsActivity.likes = bdCeleList.get(position).getLikes();
                PictureDetailsActivity.total_downloads = bdCeleList.get(position).getDownloads();
                PictureDetailsActivity.PIC_TYPE = "bdc";
                PictureDetailsActivity.related_pic = Config.URL_PICTURE_BD_CELEBRATY;
                Subscriptio_Class.type="pic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerPictureCool.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerPictureCool, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = pictureCoolClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = pictureCoolClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = pictureCoolClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = pictureCoolClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = pictureCoolClassList.get(position).getPhysicalFilwName();
                PictureDetailsActivity.contentImg = pictureCoolClassList.get(position).getImage_url();
                PictureDetailsActivity.ZedID = pictureCoolClassList.get(position).getZeid();
                PictureDetailsActivity.image= pictureCoolClassList.get(position).getImage_url();
                PictureDetailsActivity.likes = pictureCoolClassList.get(position).getLikes();
                PictureDetailsActivity.total_downloads = pictureCoolClassList.get(position).getDownloads();
                PictureDetailsActivity.related_pic = Config.URL_PICTURE_COOL;
                PictureDetailsActivity.PIC_TYPE = "cool";
                Subscriptio_Class.type="pic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerPicLove.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerPicLove, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
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

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerSpecialEvnt.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerSpecialEvnt, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = specialEventList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = specialEventList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = specialEventList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = specialEventList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = specialEventList.get(position).getPhysicalFilwName();
                PictureDetailsActivity.contentImg = specialEventList.get(position).getImage_url();
                PictureDetailsActivity.ZedID = specialEventList.get(position).getZeid();
                PictureDetailsActivity.image= specialEventList.get(position).getImage_url();
                PictureDetailsActivity.likes = specialEventList.get(position).getLikes();
                PictureDetailsActivity.total_downloads = specialEventList.get(position).getDownloads();
                PictureDetailsActivity.related_pic = Config.URL_PICTURE_SPECIAL_EVENT;
                PictureDetailsActivity.PIC_TYPE = "event";
                Subscriptio_Class.type="pic";
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

        btnHome = (ImageView) findViewById(R.id.homePic);
        btnHelp = (ImageView) findViewById(R.id.helpPic);
        btnHelpSame = (ImageView) findViewById(R.id.helpSamePic);
        btnMydownload = (ImageView) findViewById(R.id.myDownloadsPic);

        btnBuddy = (ImageView) findViewById(R.id.buddyPic);
        btnBdTube = (ImageView) findViewById(R.id.bdtubePic);
        btnBanglaBeats = (ImageView) findViewById(R.id.banglabeatsPic);
        btnAmarSticker = (ImageView) findViewById(R.id.amarstPic);
        btnRateMe = (ImageView) findViewById(R.id.ratemePic);

        btnBuddy.setOnClickListener(this);
        btnBdTube.setOnClickListener(this);
        btnBanglaBeats.setOnClickListener(this);
        btnRateMe.setOnClickListener(this);
        btnAmarSticker.setOnClickListener(this);

        btnHome.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnHelpSame.setOnClickListener(this);
        btnMydownload.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_pic_cat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        txtBollyCele = (TextView) findViewById(R.id.picBollywoodCeleMore);
        txtHollycele = (TextView) findViewById(R.id.hollyCeleMore);
        txtBangladeshCele = (TextView) findViewById(R.id.banglaCeleMore);
        txtSpecialEvent = (TextView) findViewById(R.id.specialEventMore);
        txtCool = (TextView) findViewById(R.id.picCoolMore);
        txtLove = (TextView) findViewById(R.id.picLoveMore);

        txtBollyCele.setOnClickListener(this);
        txtHollycele.setOnClickListener(this);
        txtBangladeshCele.setOnClickListener(this);
        txtSpecialEvent.setOnClickListener(this);
        txtCool.setOnClickListener(this);
        txtLove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.picBollywoodCeleMore:
                intent = new Intent(PictureCategoryActivity.this,PicBollyCeleGridActivity.class);
                startActivity(intent);
                break;
            case R.id.hollyCeleMore:
                intent = new Intent(PictureCategoryActivity.this,PicHollyCeleGridActivity.class);
                startActivity(intent);
                break;
            case R.id.banglaCeleMore:
                intent = new Intent(PictureCategoryActivity.this,BDCeleGridActivity.class);
                startActivity(intent);
                break;
            case R.id.specialEventMore:
                intent = new Intent(PictureCategoryActivity.this,SpecialEventGridActivity.class);
                startActivity(intent);
                break;
            case R.id.picCoolMore:
                intent = new Intent(PictureCategoryActivity.this,CoolGridActivity.class);
                startActivity(intent);
                break;
            case R.id.picLoveMore:
                intent = new Intent(PictureCategoryActivity.this, LoveGridActivity.class);
                startActivity(intent);
                break;
            case R.id.homePic:
                intent = new Intent(PictureCategoryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.helpPic:
                intent = new Intent(PictureCategoryActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.helpSamePic:
                intent = new Intent(PictureCategoryActivity.this,FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.myDownloadsPic:
                intent = new Intent(PictureCategoryActivity.this, MyDownLoadsActivity.class);
                startActivity(intent);
                break;
            case R.id.buddyPic :
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy")));
                break;
            case R.id.bdtubePic:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube")));
                break;
            case R.id.banglabeatsPic:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en")));
                break;
            case R.id.amarstPic:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker")));
                break;
            case R.id.ratemePic:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en")));
                break;
        }

    }


    private void initPictureLove() {
        adapterPicLove = new PictureLoveAdapter(PictureCategoryActivity.this,pictureLoveClassList);

        recyclerPicLove = (RecyclerView) findViewById(R.id.recycler_view_picLove);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerPicLove.setLayoutManager(mLayoutManager);
        recyclerPicLove.setItemAnimator(new DefaultItemAnimator());
        recyclerPicLove.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initPictureCoolRecycler() {
        adapterPicturecool = new PictureCoolAdapter(PictureCategoryActivity.this,pictureCoolClassList);

        recyclerPictureCool = (RecyclerView) findViewById(R.id.recycler_view_picCool);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerPictureCool.setLayoutManager(mLayoutManager);
        recyclerPictureCool.setItemAnimator(new DefaultItemAnimator());
        recyclerPictureCool.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initPicBdCeleRecycler() {
        adapterBdCele = new PictureBangladeshiCeleAdapter(PictureCategoryActivity.this,bdCeleList);

        recyclerbdCle = (RecyclerView) findViewById(R.id.recycler_view_banglaCele);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerbdCle.setLayoutManager(mLayoutManager);
        recyclerbdCle.setItemAnimator(new DefaultItemAnimator());
        recyclerbdCle.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initPicHolyCele() {

        adapterHolyCele = new PictureHollywoodCeleAdapter(PictureCategoryActivity.this,holyCeleList);

        recyclerHolyCele = (RecyclerView) findViewById(R.id.recycler_view_hollyCele);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerHolyCele.setLayoutManager(mLayoutManager);
        recyclerHolyCele.setItemAnimator(new DefaultItemAnimator());
        recyclerHolyCele.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initPicBollyCele() {

        adapterBollyCele = new PictureBollyCeleAdapter(PictureCategoryActivity.this,pictureBollywoodCeleClassList);

        recyclerBollyCele = (RecyclerView) findViewById(R.id.recycler_view_picBollywoodCele);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerBollyCele.setLayoutManager(mLayoutManager);
        recyclerBollyCele.setItemAnimator(new DefaultItemAnimator());
        recyclerBollyCele.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initSpecialEventRecycler() {
        adapterSpclEvent = new PictureSpecialEventAdapter(PictureCategoryActivity.this,specialEventList);

        recyclerSpecialEvnt = (RecyclerView) findViewById(R.id.recycler_view_specialEvent);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerSpecialEvnt.setLayoutManager(mLayoutManager);
        recyclerSpecialEvnt.setItemAnimator(new DefaultItemAnimator());
        recyclerSpecialEvnt.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void parseBollyCele(String urlPictureBollyCele) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlPictureBollyCele, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    loading.dismiss();

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        pictureBollywoodCeleClass = new PictureBollywoodCeleClass();
                        pictureBollywoodCeleClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE_BOLLY_CELE));
                        pictureBollywoodCeleClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE_BOLLY_CELE));
                        pictureBollywoodCeleClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE_BOLLY_CELE));
                        pictureBollywoodCeleClass.setType(obj.getString(Config.TYPE_PICTURE_BOLLY_CELE));
                        pictureBollywoodCeleClass.setPhysicalFilwName(obj.getString(Config.PHYSICALFILENAME_PICTURE_BOLLY_CELE));
                        pictureBollywoodCeleClass.setZeid(obj.getString(Config.ZID_PICTURE_BOLLY_CELE));
                        pictureBollywoodCeleClass.setPath(obj.getString(Config.PATH_PICTURE_BOLLY_CELE));
                        pictureBollywoodCeleClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        pictureBollywoodCeleClass.setImage_url(obj.getString(Config.IMAGE_URL_PICTURE_BOLLY_CELE));
                        pictureBollywoodCeleClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        pictureBollywoodCeleClassList.add(pictureBollywoodCeleClass);

                    }
                    recyclerBollyCele.setAdapter(adapterBollyCele);
                    adapterBollyCele.notifyDataSetChanged();

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
        RequestQueue requestQueue = Volley.newRequestQueue(PictureCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseHollyCeleData(String urlHollywoodCele) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlHollywoodCele, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);

                        Log.d("Obj BOLLY_CELE ",obj.getString(Config.CONTENT_CODE_PICTURE_HOLLY_CELE));


                        picHolyClass = new PictureHollywoodCelebratyClass();
                        picHolyClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE_HOLLY_CELE));
                        picHolyClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE_HOLLY_CELE));
                        picHolyClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE_HOLLY_CELE));
                        picHolyClass.setType(obj.getString(Config.TYPE_PICTURE_HOLLY_CELE));
                        picHolyClass.setPhysicalFilwName(obj.getString(Config.PHYSICALFILENAME_PICTURE_HOLLY_CELE));
                        picHolyClass.setZeid(obj.getString(Config.ZID_PICTURE_HOLLY_CELE));
                        picHolyClass.setPath(obj.getString(Config.PATH_PICTURE_HOLLY_CELE));
                        picHolyClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        picHolyClass.setImage_url(obj.getString(Config.IMAGE_URL_PICTURE_HOLLY_CELE));
                        picHolyClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        holyCeleList.add(picHolyClass);

                    }
                    recyclerHolyCele.setAdapter(adapterHolyCele);
                    adapterHolyCele.notifyDataSetChanged();

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
        RequestQueue requestQueue = Volley.newRequestQueue(PictureCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseBdCeleData(String urlPictureBdCelebraty) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlPictureBdCelebraty, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);


                        pictureBangladeshiCele = new PictureBangladeshiCele();
                        pictureBangladeshiCele.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE_BANGLADESHI_CELE));
                        pictureBangladeshiCele.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE_BANGLADESHI_CELE));
                        pictureBangladeshiCele.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE_BANGLADESHI_CELE));
                        pictureBangladeshiCele.setType(obj.getString(Config.TYPE_PICTURE_BANGLADESHI_CELE));
                        pictureBangladeshiCele.setPhysicalFilwName(obj.getString(Config.PHYSICALFILENAME_PICTURE_BANGLADESHI_CELE));
                        pictureBangladeshiCele.setZeid(obj.getString(Config.ZID_PICTURE_BANGLADESHI_CELE));
                        pictureBangladeshiCele.setPath(obj.getString(Config.PATH_PICTURE_BANGLADESHI_CELE));
                        pictureBangladeshiCele.setLikes(obj.getString(Config.TOTAL_LIKE));
                        pictureBangladeshiCele.setImage_url(obj.getString(Config.IMAGE_URL_PICTURE_BANGLADESHI_CELE));
                        pictureBangladeshiCele.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        bdCeleList.add(pictureBangladeshiCele);

                    }
                    recyclerbdCle.setAdapter(adapterBdCele);
                    adapterBdCele.notifyDataSetChanged();

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
        RequestQueue requestQueue = Volley.newRequestQueue(PictureCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseSpecialEventData(String urlPictureSpecialEvent) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlPictureSpecialEvent, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);


                        pictureSpecialEventClass = new PictureSpecialEventClass();
                        pictureSpecialEventClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE_SPECIAL_EVENT));
                        pictureSpecialEventClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE_SPECIAL_EVENT));
                        pictureSpecialEventClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE_SPECIAL_EVENT));
                        pictureSpecialEventClass.setType(obj.getString(Config.TYPE_PICTURE_SPECIAL_EVENT));
                        pictureSpecialEventClass.setPhysicalFilwName(obj.getString(Config.PHYSICALFILENAME_PICTURE_SPECIAL_EVENT));
                        pictureSpecialEventClass.setZeid(obj.getString(Config.ZID_PICTURE_SPECIAL_EVENT));
                        pictureSpecialEventClass.setPath(obj.getString(Config.PATH_PICTURE_SPECIAL_EVENT));
                        pictureSpecialEventClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        pictureSpecialEventClass.setImage_url(obj.getString(Config.IMAGE_URL_PICTURE_SPECIAL_EVENT));
                        pictureSpecialEventClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        specialEventList.add(pictureSpecialEventClass);

                    }
                    recyclerSpecialEvnt.setAdapter(adapterSpclEvent);
                    adapterSpclEvent.notifyDataSetChanged();

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
        RequestQueue requestQueue = Volley.newRequestQueue(PictureCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parsePictureCool(String imageUrlPictureCool) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, imageUrlPictureCool, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);


                        pictureCoolClass = new PictureCoolClass();
                        pictureCoolClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE_COOL));
                        pictureCoolClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE_COOL));
                        pictureCoolClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE_COOL));
                        pictureCoolClass.setType(obj.getString(Config.TYPE_PICTURE_COOL));
                        pictureCoolClass.setPhysicalFilwName(obj.getString(Config.PHYSICALFILENAME_PICTURE_COOL));
                        pictureCoolClass.setZeid(obj.getString(Config.ZID_PICTURE_COOL));
                        pictureCoolClass.setPath(obj.getString(Config.PATH_PICTURE_COOL));
                        pictureCoolClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        pictureCoolClass.setImage_url(obj.getString(Config.IMAGE_URL_PICTURE_COOL));
                        pictureCoolClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        pictureCoolClassList.add(pictureCoolClass);

                    }
                    recyclerPictureCool.setAdapter(adapterPicturecool);
                    adapterPicturecool.notifyDataSetChanged();

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
        RequestQueue requestQueue = Volley.newRequestQueue(PictureCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parsePictureLove(String urlPictureLove) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlPictureLove, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray array = jsonObject.getJSONArray("Table");
                    for (int i = 0; i<9; i++){


                        JSONObject obj = array.getJSONObject(i);


                        pictureLoveClass = new PictureLoveClass();
                        pictureLoveClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE_LOVE));
                        pictureLoveClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE_LOVE));
                        pictureLoveClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE_LOVE));
                        pictureLoveClass.setType(obj.getString(Config.TYPE_PICTURE_LOVE));
                        pictureLoveClass.setPhysicalFilwName(obj.getString(Config.PHYSICALFILENAME_PICTURE_LOVE));
                        pictureLoveClass.setZeid(obj.getString(Config.ZID_PICTURE_LOVE));
                        pictureLoveClass.setPath(obj.getString(Config.PATH_PICTURE_LOVE));
                        pictureLoveClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        pictureLoveClass.setImage_url(obj.getString(Config.IMAGE_URL_PICTURE_LOVE));
                        pictureLoveClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));

                        pictureLoveClassList.add(pictureLoveClass);

                    }
                    recyclerPicLove.setAdapter(adapterPicLove);
                    adapterPicLove.notifyDataSetChanged();

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
        RequestQueue requestQueue = Volley.newRequestQueue(PictureCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }



    class BackgroundTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            //init bollywood celebraty picture recycler
            //initPicBollyCele();
            parseBollyCele(Config.URL_PICTURE_BOLLY_CELE);

            //init hollywood celebraty recycler
            //initPicHolyCele();
            parseHollyCeleData(Config.URL_HOLLYWOOD_CELE);

            //init bangladeshi celebraty recycler
            //initPicBdCeleRecycler();
            parseBdCeleData(Config.URL_PICTURE_BD_CELEBRATY);

            //init special event recycler
            //initSpecialEventRecycler();
            parseSpecialEventData(Config.URL_PICTURE_SPECIAL_EVENT);

            //init picture cool recycler
            //initPictureCoolRecycler();
            parsePictureCool(Config.URL_PICTURE_COOL);

            //init picture love recycler
            //initPictureLove();
            parsePictureLove(Config.URL_PICTURE_LOVE);

            return null;
        }
    }

}
