package com.vumobile.clubzed.VideoRelated;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.vumobile.clubzed.adapter.BanglaTopAdapter;
import com.vumobile.clubzed.adapter.BollywoodTopAdapter;
import com.vumobile.clubzed.adapter.CelebratyNewsAdapter;
import com.vumobile.clubzed.adapter.DhaliwoodGossipAdapter;
import com.vumobile.clubzed.adapter.EnglishTopAdapter;
import com.vumobile.clubzed.adapter.HollywoodGossipAdapter;
import com.vumobile.clubzed.adapter.LifeStyleAdapter;
import com.vumobile.clubzed.adapter.MovieClipsAdapter;
import com.vumobile.clubzed.adapter.MovieReviewAdapter;
import com.vumobile.clubzed.adapter.MovieTrailerAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.BanglaTopClass;
import com.vumobile.clubzed.model.BollywoodTopClass;
import com.vumobile.clubzed.model.CelebratyNewsClass;
import com.vumobile.clubzed.model.DhaliwoodGossipClass;
import com.vumobile.clubzed.model.EnglishTopClass;
import com.vumobile.clubzed.model.HollywoodGossipClass;
import com.vumobile.clubzed.model.LifeStyleClass;
import com.vumobile.clubzed.model.MovieClipsClass;
import com.vumobile.clubzed.model.MovieReviewClass;
import com.vumobile.clubzed.model.MovieTrailerClass;
import com.vumobile.clubzed.util.FAQActivity;
import com.vumobile.clubzed.util.HelpActivity;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoMoreActivity extends ActionBarActivity implements View.OnClickListener {

    Intent intent;
    ProgressDialog loading;

    Subscriptio_Class subscriptio_class;

    LifeStyleClass lifeStyleClass;
    DhaliwoodGossipClass dhaliwoodGossipClass;
    BanglaTopClass banglaTopClass;
    HollywoodGossipClass hollywoodGossipClass;
    CelebratyNewsClass celebratyNewsClass;
    MovieReviewClass movieReviewClass;
    MovieTrailerClass movieTrailerClass;
    MovieClipsClass movieClipsClass;
    EnglishTopClass englishTopClass;
    BollywoodTopClass bollywoodTopClass;

    List<LifeStyleClass> lifeStyleClassList = new ArrayList<>();
    List<DhaliwoodGossipClass> dhaliwoodGossipClassList = new ArrayList<DhaliwoodGossipClass>();
    List<BanglaTopClass> banglaTopClassList = new ArrayList<BanglaTopClass>();
    List<HollywoodGossipClass> hollywoodGossipClassList = new ArrayList<HollywoodGossipClass>();
    List<CelebratyNewsClass> celebratyNewsClassList = new ArrayList<CelebratyNewsClass>();
    List<MovieReviewClass> movieReviewClassList = new ArrayList<MovieReviewClass>();
    List<MovieTrailerClass> movieTrailerClassList = new ArrayList<MovieTrailerClass>();
    List<MovieClipsClass> movieClipsClassList = new ArrayList<MovieClipsClass>();
    List<EnglishTopClass> englishTopClassList = new ArrayList<EnglishTopClass>();
    List<BollywoodTopClass> bollywoodTopClassList = new ArrayList<BollywoodTopClass>();

    RecyclerView recyclerViewBolyTop, recyclerEnglishTop, recyclerMovieclips,
            recyclerMovieTrailer, recyclerViewMovieReview, recyclerCelebraty,
            recyclerHollywoodGossip, recyclerBanglaTop, recyclerDhaliwood, recyclerLifeStyle;
    RecyclerView.Adapter adapterBolyTop, adapterEnglishTop, adapterMoviewClips, adapterMovieTrailer, adapterMovieReview,
            adapterCelebraty, adapterHollywoodGossip, adapterBanglaTop, adapterDhalywood, adapterLifestyle;

    TextView bolyTopMore, englishTopMore, banglaMore, dhaliwoodMore, movieClipsMore, moviewTrailerMore,
            movieReviewMore, celebratyMore, hollywoodMore, LifeStyleMore;
    Toolbar toolbar;
    Button btnBack;
    ImageView btnHome, btnDownloadPage, btnHelp, btnHelpSame, btnBuddy, btnBdTube, btnBanglaBeats, btnAmarStckr, btnRateme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_more);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_vdo_more);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoMoreActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });
        //new BackGroundTask().execute();


        initUI();

        parseDataBollyTop(Config.URL_BOLLYWOOD_TOP);
        parseDataEngTop(Config.URL_ENGLISH_TOP);
        parseMovieTrailer(Config.URL_MOVIE_TRAILER);
        parseMovieClipsData(Config.URL_MOVIE_CLIPS);
        parseMovieReview(Config.URL_MOVIE_REVIEW);
        parseCelebratyData(Config.URL_CELEBRATY_NEWS);
        parseHollywoodGossipData(Config.URL_HOLLYWOOD_GOSSIP);
        parseBanglaTop(Config.URL_BANGLA_TOP);
        parseDhalywoodGossip(Config.URL_DHALLYWOOD_GOSSIP);
        parseLifeStyle(Config.URL_VIDEO_LIFE_STYLE);


        // bollywood top recycler init
        initRecyclerBolyTop();


        //english top init
        initRecyclerEnglishTop();


        //movie trailer recycler init
        initRecyclerMovieTrailer();


        //movie clips recycler init
        initMovieclipsRecycler();


        //movie review recycler init
        initRecyclerMovieReview();


        //celebraty news recycler init
        initCelebratyRecycler();


        //hollywood gossip recycler
        initHollywoodGossipRecycler();


        //bangla top recycler init
        initBanglaTopRecycler();


        //dhaliwood recycler init
        initDhalowoodRecycler();

        //Life style recycler init
        initLifeStyleRecycler();


        Subscriptio_Class.applicationContext = VideoMoreActivity.this;
        subscriptio_class = new Subscriptio_Class();

        recyclerLifeStyle.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerLifeStyle, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = lifeStyleClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = lifeStyleClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = lifeStyleClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = lifeStyleClassList.get(position).getType();
                VideoPreViewActivity.sPhysicalFileName = lifeStyleClassList.get(position).getPhysical_file_name();
                VideoPreViewActivity.contentImg = lifeStyleClassList.get(position).getContent_image();
                VideoPreViewActivity.ZedID = lifeStyleClassList.get(position).getZid();
                VideoPreViewActivity.info = lifeStyleClassList.get(position).getInfo();
                VideoPreViewActivity.genre = lifeStyleClassList.get(position).getGenre();
                VideoPreViewActivity.total_like = lifeStyleClassList.get(position).getTotal_like();
                VideoPreViewActivity.total_views = lifeStyleClassList.get(position).getTotal_view();
                VideoPreViewActivity.TYPE = "lifestyle";
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_VIDEO_LIFE_STYLE;
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerViewBolyTop.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerViewBolyTop, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = bollywoodTopClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = bollywoodTopClassList.get(position).getCategoryCode();
                VideoPreViewActivity.contentName = bollywoodTopClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = bollywoodTopClassList.get(position).getsContentType();
                VideoPreViewActivity.sPhysicalFileName = bollywoodTopClassList.get(position).getsPhysicalFileName();
                VideoPreViewActivity.contentImg = bollywoodTopClassList.get(position).getContent_img();
                VideoPreViewActivity.ZedID = bollywoodTopClassList.get(position).getZedID();
                VideoPreViewActivity.info = bollywoodTopClassList.get(position).getInfo();
                VideoPreViewActivity.genre = bollywoodTopClassList.get(position).getGenre();
                VideoPreViewActivity.total_like = bollywoodTopClassList.get(position).getTotal_like();
                VideoPreViewActivity.total_views = bollywoodTopClassList.get(position).getTotal_views();
                VideoPreViewActivity.TYPE = "bolytop";
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BOLLYWOOD_TOP;
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerEnglishTop.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerEnglishTop, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = englishTopClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = englishTopClassList.get(position).getCategoryCode();
                VideoPreViewActivity.contentName = englishTopClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = englishTopClassList.get(position).getsContentType();
                VideoPreViewActivity.sPhysicalFileName = englishTopClassList.get(position).getsPhysicalFileName();
                VideoPreViewActivity.contentImg = englishTopClassList.get(position).getContent_img();
                VideoPreViewActivity.ZedID = englishTopClassList.get(position).getZedID();
                VideoPreViewActivity.info = englishTopClassList.get(position).getInfo();
                VideoPreViewActivity.genre = englishTopClassList.get(position).getGenre();
                VideoPreViewActivity.total_like = englishTopClassList.get(position).getTotal_like();
                VideoPreViewActivity.total_views = englishTopClassList.get(position).getTotal_views();
                VideoPreViewActivity.TYPE = "englishtop";
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_ENGLISH_TOP;
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerMovieclips.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerMovieclips, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = movieClipsClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = movieClipsClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = movieClipsClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = movieClipsClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName = movieClipsClassList.get(position).getPhysical_name();
                VideoPreViewActivity.contentImg = movieClipsClassList.get(position).getContent_image();
                VideoPreViewActivity.ZedID = movieClipsClassList.get(position).getZeid();
                VideoPreViewActivity.info = movieClipsClassList.get(position).getInfo();
                VideoPreViewActivity.genre = movieClipsClassList.get(position).getGenre();
                VideoPreViewActivity.total_like = movieClipsClassList.get(position).getTotal_like();
                VideoPreViewActivity.total_views = movieClipsClassList.get(position).getTotal_views();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_MOVIE_CLIPS;
                VideoPreViewActivity.TYPE = "movieclips";
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerMovieTrailer.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerMovieTrailer, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = movieTrailerClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = movieTrailerClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = movieTrailerClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = movieTrailerClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName = movieTrailerClassList.get(position).getPhysical_name();
                VideoPreViewActivity.contentImg = movieTrailerClassList.get(position).getContent_image();
                VideoPreViewActivity.ZedID = movieTrailerClassList.get(position).getZeid();
                VideoPreViewActivity.info = movieTrailerClassList.get(position).getInfo();
                VideoPreViewActivity.total_like = movieClipsClassList.get(position).getTotal_like();
                VideoPreViewActivity.total_views = movieClipsClassList.get(position).getTotal_views();
                VideoPreViewActivity.genre = movieClipsClassList.get(position).getGenre();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_MOVIE_TRAILER;
                VideoPreViewActivity.TYPE = "movietrailer";
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerViewMovieReview.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerViewMovieReview, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = movieReviewClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = movieReviewClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = movieReviewClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = movieReviewClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName = movieReviewClassList.get(position).getPhysical_name();
                VideoPreViewActivity.contentImg = movieReviewClassList.get(position).getContent_image();
                VideoPreViewActivity.ZedID = movieReviewClassList.get(position).getZeid();
                VideoPreViewActivity.info = movieReviewClassList.get(position).getInfo();
                VideoPreViewActivity.total_views = movieReviewClassList.get(position).getTotal_views();
                VideoPreViewActivity.total_like = movieClipsClassList.get(position).getTotal_like();
                VideoPreViewActivity.genre = movieClipsClassList.get(position).getGenre();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_MOVIE_REVIEW;
                VideoPreViewActivity.TYPE = "moviereview";
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerCelebraty.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerCelebraty, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = celebratyNewsClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = celebratyNewsClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = celebratyNewsClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = celebratyNewsClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName = celebratyNewsClassList.get(position).getPhysical_name();
                VideoPreViewActivity.contentImg = celebratyNewsClassList.get(position).getContent_image();
                VideoPreViewActivity.info = celebratyNewsClassList.get(position).getInfo();
                VideoPreViewActivity.genre = celebratyNewsClassList.get(position).getGenre();
                VideoPreViewActivity.total_like = celebratyNewsClassList.get(position).getTotal_like();
                VideoPreViewActivity.total_views = celebratyNewsClassList.get(position).getTotal_views();
                VideoPreViewActivity.ZedID = celebratyNewsClassList.get(position).getZeid();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_CELEBRATY_NEWS;
                VideoPreViewActivity.TYPE = "celenews";
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerHollywoodGossip.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerHollywoodGossip, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = hollywoodGossipClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = hollywoodGossipClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = hollywoodGossipClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = hollywoodGossipClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName = hollywoodGossipClassList.get(position).getPhysical_name();
                VideoPreViewActivity.contentImg = hollywoodGossipClassList.get(position).getContent_image();
                VideoPreViewActivity.ZedID = hollywoodGossipClassList.get(position).getZeid();
                VideoPreViewActivity.info = hollywoodGossipClassList.get(position).getInfo();
                VideoPreViewActivity.genre = hollywoodGossipClassList.get(position).getGenre();
                VideoPreViewActivity.total_like = hollywoodGossipClassList.get(position).getTotal_likes();
                VideoPreViewActivity.total_views = hollywoodGossipClassList.get(position).getTotal_views();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_HOLLYWOOD_GOSSIP;
                VideoPreViewActivity.TYPE = "holygossip";
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerBanglaTop.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerBanglaTop, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = banglaTopClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = banglaTopClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = banglaTopClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = banglaTopClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName = banglaTopClassList.get(position).getPhysical_name();
                VideoPreViewActivity.contentImg = banglaTopClassList.get(position).getContent_image();
                VideoPreViewActivity.ZedID = banglaTopClassList.get(position).getZeid();
                VideoPreViewActivity.info = banglaTopClassList.get(position).getInfo();
                VideoPreViewActivity.total_like = banglaTopClassList.get(position).getTotalLike();
                VideoPreViewActivity.total_views = banglaTopClassList.get(position).getTotalView();
                VideoPreViewActivity.genre = banglaTopClassList.get(position).getGenre();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_TOP;
                VideoPreViewActivity.TYPE = "banglatop";
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerDhaliwood.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerDhaliwood, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.contentCode = dhaliwoodGossipClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode = dhaliwoodGossipClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = dhaliwoodGossipClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType = dhaliwoodGossipClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName = dhaliwoodGossipClassList.get(position).getPhysical_name();
                VideoPreViewActivity.contentImg = dhaliwoodGossipClassList.get(position).getContent_image();
                VideoPreViewActivity.ZedID = dhaliwoodGossipClassList.get(position).getZeid();
                VideoPreViewActivity.info = dhaliwoodGossipClassList.get(position).getInfo();
                VideoPreViewActivity.genre = dhaliwoodGossipClassList.get(position).getGenre();
                VideoPreViewActivity.total_like = dhaliwoodGossipClassList.get(position).getTotal_like();
                VideoPreViewActivity.total_views = dhaliwoodGossipClassList.get(position).getTotal_views();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_DHALLYWOOD_GOSSIP;
                VideoPreViewActivity.TYPE = "dhaligossip";
                Subscriptio_Class.type = "video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initLifeStyleRecycler() {

        adapterLifestyle = new LifeStyleAdapter(VideoMoreActivity.this, lifeStyleClassList);
        recyclerLifeStyle = (RecyclerView) findViewById(R.id.recycler_view_life_style);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerLifeStyle.setLayoutManager(mLayoutManager);
        recyclerLifeStyle.setItemAnimator(new DefaultItemAnimator());
        recyclerLifeStyle.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

    }


    private void initRecyclerEnglishTop() {
        adapterEnglishTop = new EnglishTopAdapter(VideoMoreActivity.this, englishTopClassList);
        recyclerEnglishTop = (RecyclerView) findViewById(R.id.recycler_view_eng_top);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerEnglishTop.setLayoutManager(mLayoutManager);
        recyclerEnglishTop.setItemAnimator(new DefaultItemAnimator());
        recyclerEnglishTop.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initRecyclerBolyTop() {

        loading = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);
        loading.setCancelable(true);


        adapterBolyTop = new BollywoodTopAdapter(VideoMoreActivity.this, bollywoodTopClassList);
        recyclerViewBolyTop = (RecyclerView) findViewById(R.id.recycler_view_boly_top);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBolyTop.setLayoutManager(mLayoutManager);
        recyclerViewBolyTop.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBolyTop.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initRecyclerMovieTrailer() {

        adapterMovieTrailer = new MovieTrailerAdapter(VideoMoreActivity.this, movieTrailerClassList);
        recyclerMovieTrailer = (RecyclerView) findViewById(R.id.recycler_view_movieTrailer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerMovieTrailer.setLayoutManager(mLayoutManager);
        recyclerMovieTrailer.setItemAnimator(new DefaultItemAnimator());
        recyclerMovieTrailer.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initMovieclipsRecycler() {
        adapterMoviewClips = new MovieClipsAdapter(VideoMoreActivity.this, movieClipsClassList);
        recyclerMovieclips = (RecyclerView) findViewById(R.id.recycler_view_movieclips);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerMovieclips.setLayoutManager(mLayoutManager);
        recyclerMovieclips.setItemAnimator(new DefaultItemAnimator());
        recyclerMovieclips.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initRecyclerMovieReview() {

        adapterMovieReview = new MovieReviewAdapter(VideoMoreActivity.this, movieReviewClassList);
        recyclerViewMovieReview = (RecyclerView) findViewById(R.id.recycler_view_movieReview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMovieReview.setLayoutManager(mLayoutManager);
        recyclerViewMovieReview.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMovieReview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initCelebratyRecycler() {
        adapterCelebraty = new CelebratyNewsAdapter(VideoMoreActivity.this, celebratyNewsClassList);
        recyclerCelebraty = (RecyclerView) findViewById(R.id.recycler_view_celebraty_news);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerCelebraty.setLayoutManager(mLayoutManager);
        recyclerCelebraty.setItemAnimator(new DefaultItemAnimator());
        recyclerCelebraty.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initHollywoodGossipRecycler() {

        adapterHollywoodGossip = new HollywoodGossipAdapter(VideoMoreActivity.this, hollywoodGossipClassList);
        recyclerHollywoodGossip = (RecyclerView) findViewById(R.id.recycler_view_hollywood_gossip);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerHollywoodGossip.setLayoutManager(mLayoutManager);
        recyclerHollywoodGossip.setItemAnimator(new DefaultItemAnimator());
        recyclerHollywoodGossip.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initBanglaTopRecycler() {
        adapterBanglaTop = new BanglaTopAdapter(VideoMoreActivity.this, banglaTopClassList);
        recyclerBanglaTop = (RecyclerView) findViewById(R.id.recycler_view_bangla_top);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerBanglaTop.setLayoutManager(mLayoutManager);
        recyclerBanglaTop.setItemAnimator(new DefaultItemAnimator());
        recyclerBanglaTop.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initDhalowoodRecycler() {

        adapterDhalywood = new DhaliwoodGossipAdapter(VideoMoreActivity.this, dhaliwoodGossipClassList);
        recyclerDhaliwood = (RecyclerView) findViewById(R.id.recycler_view_dhaliwood_top);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerDhaliwood.setLayoutManager(mLayoutManager);
        recyclerDhaliwood.setItemAnimator(new DefaultItemAnimator());
        recyclerDhaliwood.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void parseDataBollyTop(String urlBollywoodTop) {

        JsonArrayRequest request = new JsonArrayRequest(urlBollywoodTop, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {

                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        bollywoodTopClass = new BollywoodTopClass();
                        bollywoodTopClass.setContent_code(obj.getString(Config.CONTENT_CODE_BOLYTOP));
                        bollywoodTopClass.setCategoryCode(obj.getString(Config.CATEGORY_CODE_BOLYTOP));
                        bollywoodTopClass.setContent_name(obj.getString(Config.CONTENT_NAME_BOLYTOP));
                        bollywoodTopClass.setsContentType(obj.getString(Config.CONTENT_TYPE_BOLYTOP));
                        bollywoodTopClass.setsPhysicalFileName(obj.getString(Config.PHYSICALNAME_BOLYTOP));
                        bollywoodTopClass.setZedID(obj.getString(Config.ZEID_BOLYTOP));
                        bollywoodTopClass.setInfo(obj.getString(Config.INFO_FULL_BOLYTOP));
                        bollywoodTopClass.setGenre(obj.getString(Config.GENRE_FULL_BOLYTOP));
                        bollywoodTopClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_FULL_BOLYTOP));
                        bollywoodTopClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_FULL_BOLYTOP));
                        String imgUrl = obj.getString(Config.CONTENT_IMAGE_BOLYTOP).replace(" ", "%20");
                        bollywoodTopClass.setContent_img(Config.URL_SOURCE + imgUrl);

                        bollywoodTopClassList.add(bollywoodTopClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerViewBolyTop.setAdapter(adapterBolyTop);
                adapterBolyTop.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Connection error!", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseDataEngTop(String urlEnglishTop) {

        JsonArrayRequest request = new JsonArrayRequest(urlEnglishTop, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {

                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        englishTopClass = new EnglishTopClass();
                        englishTopClass.setContent_code(obj.getString(Config.CONTENT_CODE_ENGTOP));
                        englishTopClass.setCategoryCode(obj.getString(Config.CATEGORY_CODE_ENGTOP));
                        englishTopClass.setContent_name(obj.getString(Config.CONTENT_NAME_ENGTOP));
                        englishTopClass.setsContentType(obj.getString(Config.CONTENT_TYPE_ENGTOP));
                        englishTopClass.setsPhysicalFileName(obj.getString(Config.PHYSICALNAME_ENGTOP));
                        englishTopClass.setZedID(obj.getString(Config.ZEID_ENGTOP));
                        englishTopClass.setInfo(obj.getString(Config.INFO_FULL_ENGTOP));
                        englishTopClass.setGenre(obj.getString(Config.GENRE_FULL_ENGTOP));
                        englishTopClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_FULL_ENGTOP));
                        englishTopClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_FULL_ENGTOP));
                        String imgUrl = obj.getString(Config.CONTENT_IMAGE_ENGTOP).replace(" ", "%20");
                        englishTopClass.setContent_img(Config.URL_SOURCE + imgUrl);

                        englishTopClassList.add(englishTopClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerEnglishTop.setAdapter(adapterEnglishTop);
                adapterEnglishTop.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseMovieClipsData(String urlMovieClips) {

        JsonArrayRequest request = new JsonArrayRequest(urlMovieClips, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        movieClipsClass = new MovieClipsClass();
                        movieClipsClass.setContent_code(obj.getString(Config.CONTENT_CODE_MOVIE_CLIPS));
                        movieClipsClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_MOVIE_CLIPS));
                        movieClipsClass.setContent_name(obj.getString(Config.CONTENT_NAME_MOVIE_CLIPS));
                        movieClipsClass.setContent_type(obj.getString(Config.CONTENT_TYPE_MOVIE_CLIPS));
                        movieClipsClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_MOVIE_CLIPS));
                        movieClipsClass.setZeid(obj.getString(Config.ZEID_MOVIE_CLIPS));
                        movieClipsClass.setInfo(obj.getString(Config.INFO_MOVIE_CLOPS));
                        movieClipsClass.setGenre(obj.getString(Config.GENRE_MOVIE_CLOPS));
                        movieClipsClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_MOVIE_CLOPS));
                        movieClipsClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_MOVIE_CLOPS));
                        movieClipsClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_MOVIE_CLIPS)).replace(" ", "%20"));
                        movieClipsClassList.add(movieClipsClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerMovieclips.setAdapter(adapterMoviewClips);
                adapterMoviewClips.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseMovieTrailer(String urlMovieTrailer) {
        JsonArrayRequest request = new JsonArrayRequest(urlMovieTrailer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        movieTrailerClass = new MovieTrailerClass();
                        movieTrailerClass.setContent_code(obj.getString(Config.CONTENT_CODE_MOVIE_TRAILER));
                        movieTrailerClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_MOVIE_TRAILER));
                        movieTrailerClass.setContent_name(obj.getString(Config.CONTENT_NAME_MOVIE_TRAILER));
                        movieTrailerClass.setContent_type(obj.getString(Config.CONTENT_TYPE_MOVIE_TRAILER));
                        movieTrailerClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_MOVIE_TRAILER));
                        movieTrailerClass.setZeid(obj.getString(Config.ZEID_MOVIE_TRAILER));
                        movieTrailerClass.setInfo(obj.getString(Config.INFO_MOVIE_TRAILER));
                        movieTrailerClass.setGenre(obj.getString(Config.GENRE_MOVIE_TRAILER));
                        movieTrailerClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_MOVIE_TRAILER));
                        movieTrailerClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_MOVIE_TRAILER));
                        movieTrailerClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_MOVIE_TRAILER)).replace(" ", "%20"));
                        movieTrailerClassList.add(movieTrailerClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerMovieTrailer.setAdapter(adapterMovieTrailer);
                adapterMovieTrailer.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseMovieReview(String urlMovieReview) {

        JsonArrayRequest request = new JsonArrayRequest(urlMovieReview, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        movieReviewClass = new MovieReviewClass();
                        movieReviewClass.setContent_code(obj.getString(Config.CONTENT_CODE_MOVIE_RIVIEW));
                        movieReviewClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_MOVIE_RIVIEW));
                        movieReviewClass.setContent_name(obj.getString(Config.CONTENT_NAME_MOVIE_RIVIEW));
                        movieReviewClass.setContent_type(obj.getString(Config.CONTENT_TYPE_MOVIE_RIVIEW));
                        movieReviewClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_MOVIE_RIVIEW));
                        movieReviewClass.setZeid(obj.getString(Config.ZEID_MOVIE_RIVIEW));
                        movieReviewClass.setInfo(obj.getString(Config.INFO_MOVIE_RIVIEW));
                        movieReviewClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_MOVIE_RIVIEW));
                        movieReviewClass.setTotla_like(obj.getString(Config.TOTAL_LIKE_MOVIE_RIVIEW));
                        movieReviewClass.setGenre(obj.getString(Config.GENRE_MOVIE_RIVIEW));
                        movieReviewClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_MOVIE_RIVIEW)).replace(" ", "%20"));
                        movieReviewClassList.add(movieReviewClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerViewMovieReview.setAdapter(adapterMovieReview);
                adapterMovieReview.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseCelebratyData(String urlCelebratyNews) {

        JsonArrayRequest request = new JsonArrayRequest(urlCelebratyNews, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        celebratyNewsClass = new CelebratyNewsClass();
                        celebratyNewsClass.setContent_code(obj.getString(Config.CONTENT_CODE_CELEBRATY_NEWS));
                        celebratyNewsClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_CELEBRATY_NEWS));
                        celebratyNewsClass.setContent_name(obj.getString(Config.CONTENT_NAME_CELEBRATY_NEWS));
                        celebratyNewsClass.setContent_type(obj.getString(Config.CONTENT_TYPE_CELEBRATY_NEWS));
                        celebratyNewsClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_CELEBRATY_NEWS));
                        celebratyNewsClass.setInfo(obj.getString(Config.INFO_CELEBRATY_NEWS));
                        celebratyNewsClass.setGenre(obj.getString(Config.GENRE_CELEBRATY_NEWS));
                        celebratyNewsClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_CELEBRATY_NEWS));
                        celebratyNewsClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_CELEBRATY_NEWS));
                        celebratyNewsClass.setZeid(obj.getString(Config.ZEID_CELEBRATY_NEWS));
                        celebratyNewsClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_CELEBRATY_NEWS)).replace(" ", "%20"));
                        celebratyNewsClassList.add(celebratyNewsClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerCelebraty.setAdapter(adapterCelebraty);
                adapterCelebraty.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseHollywoodGossipData(String urlHollywoodGossip) {

        JsonArrayRequest request = new JsonArrayRequest(urlHollywoodGossip, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        hollywoodGossipClass = new HollywoodGossipClass();
                        hollywoodGossipClass.setContent_code(obj.getString(Config.CONTENT_CODE_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setContent_name(obj.getString(Config.CONTENT_NAME_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setContent_type(obj.getString(Config.CONTENT_TYPE_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setZeid(obj.getString(Config.ZEID_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setInfo(obj.getString(Config.INFO_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setGenre(obj.getString(Config.GENRE_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setTotal_likes(obj.getString(Config.TOTAL_LIKE_HOLLYWOOD_GOSSIP));
                        hollywoodGossipClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_HOLLYWOOD_GOSSIP)).replace(" ", "%20"));
                        hollywoodGossipClassList.add(hollywoodGossipClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerHollywoodGossip.setAdapter(adapterHollywoodGossip);
                adapterHollywoodGossip.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseBanglaTop(String urlBanglaTop) {

        JsonArrayRequest request = new JsonArrayRequest(urlBanglaTop, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                loading.dismiss();
                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        banglaTopClass = new BanglaTopClass();
                        banglaTopClass.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_TOP));
                        banglaTopClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_TOP));
                        banglaTopClass.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_TOP));
                        banglaTopClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_BANGLA_TOP));
                        banglaTopClass.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_TOP));
                        banglaTopClass.setZeid(obj.getString(Config.ZEID_BANGLA_TOP));
                        banglaTopClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_BANGLA_TOP)).replace(" ", "%20"));
                        banglaTopClass.setTime_stamp(obj.getString(Config.TIME_STAMP_BANGLA_TOP));
                        banglaTopClass.setInfo(obj.getString(Config.INFO_FULL_BANGLA_TOP));
                        banglaTopClass.setGenre(obj.getString(Config.GENRE_FULL_BANGLA_TOP));
                        banglaTopClass.setTotalView(obj.getString(Config.TOTAL_VIEWS_FULL_BANGLA_TOP));
                        banglaTopClass.setTotalLike(obj.getString(Config.TOTAL_LIKE_FULL_BANGLA_TOP));
                        banglaTopClass.setLive_data(obj.getString(Config.LIVE_DATA_BANGLA_TOP));
                        banglaTopClass.setRow_num(obj.getString(Config.ROWNUM_BANGLA_TOP));

                        banglaTopClassList.add(banglaTopClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerBanglaTop.setAdapter(adapterBanglaTop);
                adapterBanglaTop.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseDhalywoodGossip(String urlDhallywoodGossip) {

        JsonArrayRequest request = new JsonArrayRequest(urlDhallywoodGossip, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        dhaliwoodGossipClass = new DhaliwoodGossipClass();
                        dhaliwoodGossipClass.setContent_code(obj.getString(Config.CONTENT_CODE_DHALLYWOOD_GOSSIP));
                        dhaliwoodGossipClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_DHALLYWOOD_GOSSIP));
                        dhaliwoodGossipClass.setContent_name(obj.getString(Config.CONTENT_NAME_DHALLYWOOD_GOSSIP));
                        dhaliwoodGossipClass.setContent_type(obj.getString(Config.CONTENT_TYPE_DHALLYWOOD_GOSSIP));
                        dhaliwoodGossipClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_DHALLYWOOD_GOSSIP));
                        dhaliwoodGossipClass.setZeid(obj.getString(Config.ZEID_DHALLYWOOD_GOSSIP));
                        dhaliwoodGossipClass.setInfo(obj.getString(Config.INFO_FULL_DHALIWOOD));
                        dhaliwoodGossipClass.setGenre(obj.getString(Config.GENRE_FULL_DHALIWOOD));
                        dhaliwoodGossipClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_FULL_DHALIWOOD));
                        dhaliwoodGossipClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_FULL_DHALIWOOD));
                        dhaliwoodGossipClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_DHALLYWOOD_GOSSIP)).replace(" ", "%20"));
                        dhaliwoodGossipClassList.add(dhaliwoodGossipClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerDhaliwood.setAdapter(adapterDhalywood);
                adapterDhalywood.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }


    private void parseLifeStyle(String urlVideoLifeStyle) {

        JsonArrayRequest request = new JsonArrayRequest(urlVideoLifeStyle, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i < 9; i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        lifeStyleClass = new LifeStyleClass();
                        lifeStyleClass.setContent_code(obj.getString(Config.CONTENTCODE_LIFESTYLE));
                        lifeStyleClass.setCategory_code(obj.getString(Config.CATEGORYCODE_LIFESTYLE));
                        lifeStyleClass.setContent_name(obj.getString(Config.CONTENT_NAME_LIFESTYLE));
                        lifeStyleClass.setType(obj.getString(Config.CONTENT_TYPE_LIFESTYLE));
                        lifeStyleClass.setPhysical_file_name(obj.getString(Config.PHYSICALFILENAME_LIFESTYLE));
                        lifeStyleClass.setZid(obj.getString(Config.ZID_LIFESTYLE));
                        lifeStyleClass.setInfo(obj.getString(Config.INFO_LIFESTYLE));
                        lifeStyleClass.setGenre(obj.getString(Config.GENRE_LIFESTYLE));
                        lifeStyleClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_LIFESTYLE));
                        lifeStyleClass.setTotal_view(obj.getString(Config.TOTAL_VIEW_LIFESTYLE));
                        lifeStyleClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_LIFESTYLE)).replace(" ", "%20"));
                        lifeStyleClassList.add(lifeStyleClass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerLifeStyle.setAdapter(adapterLifestyle);
                adapterLifestyle.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(VideoMoreActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bolytopMore:
                intent = new Intent(VideoMoreActivity.this, BollytopGridActivity.class);
                startActivity(intent);
                break;
            case R.id.englishTopMore:
                intent = new Intent(VideoMoreActivity.this, EnglishTopGridActivity.class);
                startActivity(intent);
                break;
            case R.id.banglatopMore:
                intent = new Intent(VideoMoreActivity.this, BanglaTopGridActivity.class);
                startActivity(intent);
                break;
            case R.id.dhaliwoodMore:
                intent = new Intent(VideoMoreActivity.this, DhaliwoodGossipGridActivity.class);
                startActivity(intent);
                break;
            case R.id.movieclipsMore:
                intent = new Intent(VideoMoreActivity.this, MovieClipsGridActivity.class);
                startActivity(intent);
                break;
            case R.id.movieTrailerMore:
                intent = new Intent(VideoMoreActivity.this, MovieTrailerGridActivity.class);
                startActivity(intent);
                break;
            case R.id.movieReviewMore:
                intent = new Intent(VideoMoreActivity.this, MovieReviewGridActivity.class);
                startActivity(intent);
                break;
            case R.id.celebratyNewsMore:
                intent = new Intent(VideoMoreActivity.this, CelebratyNewsGridActivity.class);
                startActivity(intent);
                break;
            case R.id.hollywoodGossipMore:
                intent = new Intent(VideoMoreActivity.this, HollyGossipGridActivity.class);
                startActivity(intent);
                break;
            case R.id.LifeStyleMore:
                intent = new Intent(VideoMoreActivity.this, LifeStyleGridActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHomeVdo:
                intent = new Intent(VideoMoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnHelpVdo:
                intent = new Intent(VideoMoreActivity.this, FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHelpSameVdo:
                intent = new Intent(VideoMoreActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDownloadHistoryPageVdo:
                intent = new Intent(VideoMoreActivity.this, MyDownLoadsActivity.class);
                startActivity(intent);
                break;
            case R.id.buddyVdo:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy")));
                break;
            case R.id.bdtubeVdo:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube")));
                break;
            case R.id.banglabeatsVdo:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en")));
                break;
            case R.id.amrStckrVdo:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker")));
                break;
            case R.id.ratemeVdo:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en")));
                break;
        }
    }


    private void initUI() {
        btnHome = (ImageView) findViewById(R.id.btnHomeVdo);
        btnHelp = (ImageView) findViewById(R.id.btnHelpVdo);
        btnHelpSame = (ImageView) findViewById(R.id.btnHelpSameVdo);
        btnDownloadPage = (ImageView) findViewById(R.id.btnDownloadHistoryPageVdo);

        btnBuddy = (ImageView) findViewById(R.id.buddyVdo);
        btnBdTube = (ImageView) findViewById(R.id.bdtubeVdo);
        btnBanglaBeats = (ImageView) findViewById(R.id.banglabeatsVdo);
        btnAmarStckr = (ImageView) findViewById(R.id.amrStckrVdo);
        btnRateme = (ImageView) findViewById(R.id.ratemeVdo);

        btnBuddy.setOnClickListener(this);
        btnBdTube.setOnClickListener(this);
        btnBanglaBeats.setOnClickListener(this);
        btnRateme.setOnClickListener(this);
        btnAmarStckr.setOnClickListener(this);

        LifeStyleMore = (TextView) findViewById(R.id.LifeStyleMore);
        bolyTopMore = (TextView) findViewById(R.id.bolytopMore);
        englishTopMore = (TextView) findViewById(R.id.englishTopMore);
        banglaMore = (TextView) findViewById(R.id.banglatopMore);
        dhaliwoodMore = (TextView) findViewById(R.id.dhaliwoodMore);
        movieClipsMore = (TextView) findViewById(R.id.movieclipsMore);
        moviewTrailerMore = (TextView) findViewById(R.id.movieTrailerMore);
        movieReviewMore = (TextView) findViewById(R.id.movieReviewMore);
        celebratyMore = (TextView) findViewById(R.id.celebratyNewsMore);
        hollywoodMore = (TextView) findViewById(R.id.hollywoodGossipMore);


        btnHome.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnHelpSame.setOnClickListener(this);
        btnDownloadPage.setOnClickListener(this);

        LifeStyleMore.setOnClickListener(this);
        bolyTopMore.setOnClickListener(this);
        banglaMore.setOnClickListener(this);
        englishTopMore.setOnClickListener(this);
        dhaliwoodMore.setOnClickListener(this);
        dhaliwoodMore.setOnClickListener(this);
        movieClipsMore.setOnClickListener(this);
        moviewTrailerMore.setOnClickListener(this);
        movieReviewMore.setOnClickListener(this);
        celebratyMore.setOnClickListener(this);
        hollywoodMore.setOnClickListener(this);


    }

//    class BackGroundTask extends AsyncTask<Void,Void,Void>{
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            parseDataBollyTop(Config.URL_BOLLYWOOD_TOP);
//            parseDataEngTop(Config.URL_ENGLISH_TOP);
//            parseMovieTrailer(Config.URL_MOVIE_TRAILER);
//            parseMovieClipsData(Config.URL_MOVIE_CLIPS);
//            parseMovieReview(Config.URL_MOVIE_REVIEW);
//            parseCelebratyData(Config.URL_CELEBRATY_NEWS);
//            parseHollywoodGossipData(Config.URL_HOLLYWOOD_GOSSIP);
//            parseBanglaTop(Config.URL_BANGLA_TOP);
//            parseDhalywoodGossip(Config.URL_DHALLYWOOD_GOSSIP);
//
//            return null;
//        }


}

