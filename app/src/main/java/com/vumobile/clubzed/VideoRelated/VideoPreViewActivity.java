package com.vumobile.clubzed.VideoRelated;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longtailvideo.jwplayer.JWPlayerFragment;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.configuration.Skin;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.vumobile.clubzed.AppConstant;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.RelatedItemAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.BanglaMusicVideo;
import com.vumobile.clubzed.model.BanglaNatok;
import com.vumobile.clubzed.model.BanglaTelefilm;
import com.vumobile.clubzed.model.BanglaTopClass;
import com.vumobile.clubzed.model.BollywoodTopClass;
import com.vumobile.clubzed.model.CelebratyNewsClass;
import com.vumobile.clubzed.model.DhaliwoodGossipClass;
import com.vumobile.clubzed.model.DramaClipsHome;
import com.vumobile.clubzed.model.EnglishTopClass;
import com.vumobile.clubzed.model.FullVideoClass;
import com.vumobile.clubzed.model.HDVideo;
import com.vumobile.clubzed.model.HollywoodGossipClass;
import com.vumobile.clubzed.model.LifeStyleClass;
import com.vumobile.clubzed.model.MovieClipsClass;
import com.vumobile.clubzed.model.MovieReviewClass;
import com.vumobile.clubzed.model.MovieTrailerClass;
import com.vumobile.clubzed.model.RelatedVideoClass;
import com.vumobile.clubzed.model.ShortFilm;
import com.vumobile.clubzed.model.VideoHome;
import com.vumobile.clubzed.util.Caller;
import com.vumobile.clubzed.util.Download_Class;
import com.vumobile.clubzed.util.HttpRequest;
import com.vumobile.clubzed.util.PHPRequest;
import com.vumobile.clubzed.util.RequiredUserInfo;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoPreViewActivity extends ActionBarActivity {
    private ProgressDialog loading;
    PullToRefreshScrollView mPullRefreshScrollView;
    ScrollView scrollVideoPreview;
    public static boolean isHd = false;
    int num = 6;
    PlayerConfig playerConfig;
    LifeStyleClass lifeStyleClass;
    ShortFilm shortFilm;
    RelatedVideoClass relatedVideoClass;
    RecyclerView recyclerView;
    RelatedItemAdapter adapterRelatedItem;
    HollywoodGossipClass hollywoodGossipClass;
    CelebratyNewsClass celebratyNewsClass;
    MovieTrailerClass movieTrailerClass;
    DhaliwoodGossipClass dhaliwoodGossipClass;
    MovieReviewClass movieReviewClass;
    MovieClipsClass movieClipsClass;
    BanglaTopClass banglaTopClass;
    EnglishTopClass englishTopClass;
    FullVideoClass fullVideoClass;
    DramaClipsHome dramaClipsHome;
    HDVideo hdVideo;
    BanglaMusicVideo banglaMusicVideo;
    BanglaNatok banglaNatok;
    BanglaTelefilm banglaTelefilm;
    BollywoodTopClass bollywoodTopClass;
    VideoHome videoHome;

    List<ShortFilm> shortFilmList = new ArrayList<ShortFilm>();
    List<BanglaMusicVideo> banglaMusicVideoList = new ArrayList<BanglaMusicVideo>();
    List<BanglaNatok> banglaNatokList = new ArrayList<BanglaNatok>();
    List<BanglaTelefilm> banglaTelefilmList = new ArrayList<BanglaTelefilm>();
    List<LifeStyleClass> lifeStyleClassList = new ArrayList<LifeStyleClass>();
    List<HDVideo> hdVideoList = new ArrayList<HDVideo>();
    List<HollywoodGossipClass> hollywoodGossipClassList = new ArrayList<HollywoodGossipClass>();
    List<CelebratyNewsClass> celebratyNewsClassList = new ArrayList<CelebratyNewsClass>();
    List<MovieReviewClass> movieReviewClassList = new ArrayList<MovieReviewClass>();
    List<MovieTrailerClass> movieTrailerClassList = new ArrayList<MovieTrailerClass>();
    List<MovieClipsClass> movieClipsClassList = new ArrayList<MovieClipsClass>();
    List<DhaliwoodGossipClass> dhaliwoodGossipClassList = new ArrayList<DhaliwoodGossipClass>();
    List<BanglaTopClass> banglaTopClassList = new ArrayList<BanglaTopClass>();
    List<EnglishTopClass> englishTopClassList = new ArrayList<EnglishTopClass>();
    List<BollywoodTopClass> bollywoodTopClassList = new ArrayList<BollywoodTopClass>();
    List<FullVideoClass> fullVideoClassList = new ArrayList<FullVideoClass>();
    List<DramaClipsHome> dramaClipsHomeList = new ArrayList<DramaClipsHome>();
    List<VideoHome> videoHomeList = new ArrayList<VideoHome>();
    List<RelatedVideoClass> relatedClassList = new ArrayList<RelatedVideoClass>();

    LinearLayout pic_detail_upperLayout;
    int n = 345;
    Button btnRotate, btnOrzinalSize, btnLike;
    String strs, totalDuration;
    public static String contentCode = "", categoryCode = "", contentName = "", sContentType = "",
            sPhysicalFileName = "", ZedID = "", contentImg = "", duration = "";
    public static int dimWidth, dimHeight;
    TextView video_preview_download_button, videotitle, txtTotalDuration, txtLikeCount, txtViews, txtViewsCount, txtGenre, txtInfo, durationtxt, genretxt, infotxt;
    Download_Class download_Class;
    public static String model;
    public static String manufacture;
    public static String brand;
    public static String info = "", total_like = "", total_views = "", genre = "";
    Uri video;
    public String string = "http://www.vumobile.biz/clubz_android/clubz_version.txt";
    public String UpdateString;
    public static String updateReasonString = "";
    public static String URL_RELATED_VIDEO = "", TYPE = "";
    ImageButton shareButton;
    RelativeLayout videoPreviewbottomLayout;
    ProgressDialog bufferingProgressDialog;
    JWPlayerView videoView;
    JWPlayerFragment fragment;
    JWPlayerView playerView;
    PlaylistItem videolay;
    Button btnLoadMoreData;
    String videoImage;
    public static String fullVideoUrl = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/";
    public static String audioURL = "http://wap.shabox.mobi/CMS/Content/Audio/FullTrack/MP3/";
    public static String videoURL = "http://wap.shabox.mobi/CMS/Content/Graphics/Video%20Clips/D480x320/";
    public static String rsltNumber = "";
    public static int doAction = 0;
    public PHPRequest phpRequest = new PHPRequest();
    public String pushResponseUrl = "http://www.vumobile.biz/gcm_server_php/push_response.php";

    Subscriptio_Class subscriptio_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_pre_view);

        Log.d("ContentImage", contentImg);
        videoImage = contentImg;
        Log.d("ContentImage", videoImage);

        initUI();
        //init video recycler
        initRecycler();
        new BackgroundTask().execute();
        //parseRelatedVideo(URL_RELATED_VIDEO,TYPE);

        videoView(Config.URL_VIEWS + contentCode);
        //likeCount(Config.URL_LIKES);
        //new BackTask().execute(URL_RELATED_VIDEO,TYPE)

//        btnRotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                videoView.pause();
//                btnRotate.setVisibility(View.GONE);
//                btnOrzinalSize.setVisibility(View.VISIBLE);
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//        });
//
//        btnOrzinalSize.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                videoView.pause();
//                btnOrzinalSize.setVisibility(View.GONE);
//                btnRotate.setVisibility(View.VISIBLE);
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//            }
//        });


        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {


                lifeStyleClassList.clear();
                hollywoodGossipClassList.clear();
                celebratyNewsClassList.clear();
                movieReviewClassList.clear();
                movieTrailerClassList.clear();
                movieClipsClassList.clear();
                dhaliwoodGossipClassList.clear();
                banglaTopClassList.clear();
                englishTopClassList.clear();
                bollywoodTopClassList.clear();
                fullVideoClassList.clear();
                dramaClipsHomeList.clear();
                videoHomeList.clear();
                relatedClassList.clear();
                hdVideoList.clear();
                banglaMusicVideoList.clear();
                banglaNatokList.clear();
                banglaTelefilmList.clear();
                shortFilmList.clear();

                num += 10;

                new BackgroundTask().execute();
            }
        });
        scrollVideoPreview = mPullRefreshScrollView.getRefreshableView();
//        shareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String shareBody = "https://play.google.com/store/apps/details?id=com.vumobile.clubzed";
//
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ClubZ");
//
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share via"));
//            }
//        });

        Subscriptio_Class.applicationContext = VideoPreViewActivity.this;
        subscriptio_class = new Subscriptio_Class();


        // for handling related list items click
        recyclerView.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerView, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                // Toast.makeText(getApplicationContext(),videoHomeList.get(position).getContentTile(),Toast.LENGTH_LONG).show();

                if (TYPE.equals("video")) {
                    VideoPreViewActivity.duration = videoHomeList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = videoHomeList.get(position).getContentcode();
                    VideoPreViewActivity.categoryCode = videoHomeList.get(position).getCategory();
                    VideoPreViewActivity.contentName = videoHomeList.get(position).getContentTile();
                    VideoPreViewActivity.sContentType = videoHomeList.get(position).getType();
                    VideoPreViewActivity.sPhysicalFileName = videoHomeList.get(position).getPhysicalfilename();
                    VideoPreViewActivity.contentImg = videoHomeList.get(position).getImageUrl();
                    VideoPreViewActivity.ZedID = videoHomeList.get(position).getZid();
                    VideoPreViewActivity.info = videoHomeList.get(position).getInfo();
                    VideoPreViewActivity.total_views = videoHomeList.get(position).getTotal_views();
                    VideoPreViewActivity.total_like = videoHomeList.get(position).getTotal_like();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_VIDEO_URL;
                    VideoPreViewActivity.TYPE = "video";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();

                } else if (TYPE.equals("drama")) {
                    VideoPreViewActivity.duration = dramaClipsHomeList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = dramaClipsHomeList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = dramaClipsHomeList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = dramaClipsHomeList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = dramaClipsHomeList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = dramaClipsHomeList.get(position).getPhysical_name();
                    VideoPreViewActivity.contentImg = dramaClipsHomeList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = dramaClipsHomeList.get(position).getZeid();
                    VideoPreViewActivity.info = dramaClipsHomeList.get(position).getInfo();
                    VideoPreViewActivity.total_like = dramaClipsHomeList.get(position).getTotal_like();
                    VideoPreViewActivity.total_views = dramaClipsHomeList.get(position).getTotal_views();
                    VideoPreViewActivity.genre = dramaClipsHomeList.get(position).getGenre();
                    VideoPreViewActivity.TYPE = "drama";
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_DRAMA;
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();

                } else if (TYPE.equals("fullVideo")) {
                    VideoPreViewActivity.duration = fullVideoClassList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = fullVideoClassList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = fullVideoClassList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = fullVideoClassList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = fullVideoClassList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = fullVideoClassList.get(position).getPhysical_file_name();
                    VideoPreViewActivity.ZedID = fullVideoClassList.get(position).getZeid();
                    VideoPreViewActivity.contentImg = fullVideoClassList.get(position).getContent_image();
                    VideoPreViewActivity.info = fullVideoClassList.get(position).getInfo();
                    VideoPreViewActivity.total_like = fullVideoClassList.get(position).getTotalLike();
                    VideoPreViewActivity.total_views = fullVideoClassList.get(position).getTotalView();
                    VideoPreViewActivity.genre = fullVideoClassList.get(position).getGenre();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_FULL_VIDEO;
                    VideoPreViewActivity.TYPE = "fullVideo";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();

                } else if (TYPE.equals("bolytop")) {
                    VideoPreViewActivity.duration = bollywoodTopClassList.get(position).getDuration();
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

                } else if (TYPE.equals("englishtop")) {
                    VideoPreViewActivity.duration = englishTopClassList.get(position).getDuration();
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

                } else if (TYPE.equals("banglatop")) {
                    VideoPreViewActivity.duration = banglaTopClassList.get(position).getDuration();
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

                } else if (TYPE.equals("dhaligossip")) {
                    VideoPreViewActivity.duration = dhaliwoodGossipClassList.get(position).getDuration();
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

                } else if (TYPE.equals("movieclips")) {
                    VideoPreViewActivity.duration = movieClipsClassList.get(position).getDuration();
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

                } else if (TYPE.equals("movietrailer")) {
                    VideoPreViewActivity.duration = movieTrailerClassList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = movieTrailerClassList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = movieTrailerClassList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = movieTrailerClassList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = movieTrailerClassList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = movieTrailerClassList.get(position).getPhysical_name();
                    VideoPreViewActivity.contentImg = movieTrailerClassList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = movieTrailerClassList.get(position).getZeid();
                    VideoPreViewActivity.info = movieTrailerClassList.get(position).getInfo();
                    VideoPreViewActivity.total_like = movieTrailerClassList.get(position).getTotal_like();
                    VideoPreViewActivity.total_views = movieTrailerClassList.get(position).getTotal_views();
                    VideoPreViewActivity.genre = movieTrailerClassList.get(position).getGenre();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_MOVIE_TRAILER;
                    VideoPreViewActivity.TYPE = "movietrailer";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();

                } else if (TYPE.equals("moviereview")) {
                    VideoPreViewActivity.duration = movieReviewClassList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = movieReviewClassList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = movieReviewClassList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = movieReviewClassList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = movieReviewClassList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = movieReviewClassList.get(position).getPhysical_name();
                    VideoPreViewActivity.contentImg = movieReviewClassList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = movieReviewClassList.get(position).getZeid();
                    VideoPreViewActivity.info = movieReviewClassList.get(position).getInfo();
                    VideoPreViewActivity.total_views = movieReviewClassList.get(position).getTotal_views();
                    VideoPreViewActivity.total_like = movieReviewClassList.get(position).getTotla_like();
                    VideoPreViewActivity.genre = movieReviewClassList.get(position).getGenre();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_MOVIE_REVIEW;
                    VideoPreViewActivity.TYPE = "moviereview";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();

                } else if (TYPE.equals("celenews")) {
                    VideoPreViewActivity.duration = celebratyNewsClassList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = celebratyNewsClassList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = celebratyNewsClassList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = celebratyNewsClassList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = celebratyNewsClassList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = celebratyNewsClassList.get(position).getPhysical_name();
                    VideoPreViewActivity.contentImg = celebratyNewsClassList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = celebratyNewsClassList.get(position).getZeid();
                    VideoPreViewActivity.info = celebratyNewsClassList.get(position).getInfo();
                    VideoPreViewActivity.genre = celebratyNewsClassList.get(position).getGenre();
                    VideoPreViewActivity.total_like = celebratyNewsClassList.get(position).getTotal_like();
                    VideoPreViewActivity.total_views = celebratyNewsClassList.get(position).getTotal_views();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_CELEBRATY_NEWS;
                    VideoPreViewActivity.TYPE = "celenews";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();

                } else if (TYPE.equals("holygossip")) {
                    VideoPreViewActivity.duration = hollywoodGossipClassList.get(position).getDuration();
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

                } else if (TYPE.equals("lifestyle")) {
                    VideoPreViewActivity.duration = lifeStyleClassList.get(position).getDuration();
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

                } else if (TYPE.equals("hd")) {
                    VideoPreViewActivity.duration = hdVideoList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = hdVideoList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = hdVideoList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = hdVideoList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = hdVideoList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = hdVideoList.get(position).getPhysical_file_name();
                    VideoPreViewActivity.contentImg = hdVideoList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = hdVideoList.get(position).getZeid();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_HD_VIDEO;
                    VideoPreViewActivity.info = hdVideoList.get(position).getInfo();
                    VideoPreViewActivity.total_views = hdVideoList.get(position).getTotalView();
                    VideoPreViewActivity.total_like = hdVideoList.get(position).getTotalLike();
                    VideoPreViewActivity.genre = hdVideoList.get(position).getGenre();
                    VideoPreViewActivity.TYPE = "hd";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();
                } else if (TYPE.equals("bm")) {
                    VideoPreViewActivity.duration = banglaMusicVideoList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = banglaMusicVideoList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = banglaMusicVideoList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = banglaMusicVideoList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = banglaMusicVideoList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = banglaMusicVideoList.get(position).getPhysical_file_name();
                    VideoPreViewActivity.contentImg = banglaMusicVideoList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = banglaMusicVideoList.get(position).getZeid();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_MUSIC_VIDEO;
                    VideoPreViewActivity.info = banglaMusicVideoList.get(position).getInfo();
                    VideoPreViewActivity.total_views = banglaMusicVideoList.get(position).getTotalView();
                    VideoPreViewActivity.total_like = banglaMusicVideoList.get(position).getTotalLike();
                    VideoPreViewActivity.genre = banglaMusicVideoList.get(position).getGenre();
                    VideoPreViewActivity.TYPE = "bm";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();
                } else if (TYPE.equals("bn")) {
                    VideoPreViewActivity.duration = banglaNatokList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = banglaNatokList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = banglaNatokList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = banglaNatokList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = banglaNatokList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = banglaNatokList.get(position).getPhysical_file_name();
                    VideoPreViewActivity.contentImg = banglaNatokList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = banglaNatokList.get(position).getZeid();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_NATOK;
                    VideoPreViewActivity.info = banglaNatokList.get(position).getInfo();
                    VideoPreViewActivity.total_views = banglaNatokList.get(position).getTotalView();
                    VideoPreViewActivity.total_like = banglaNatokList.get(position).getTotalLike();
                    VideoPreViewActivity.genre = banglaNatokList.get(position).getGenre();
                    VideoPreViewActivity.TYPE = "bn";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();
                } else if (TYPE.equals("bt")) {
                    VideoPreViewActivity.duration = banglaTelefilmList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = banglaTelefilmList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = banglaTelefilmList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = banglaTelefilmList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = banglaTelefilmList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = banglaTelefilmList.get(position).getPhysical_file_name();
                    VideoPreViewActivity.contentImg = banglaTelefilmList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = banglaTelefilmList.get(position).getZeid();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_TELEFILM;
                    VideoPreViewActivity.info = banglaTelefilmList.get(position).getInfo();
                    VideoPreViewActivity.total_views = banglaTelefilmList.get(position).getTotalView();
                    VideoPreViewActivity.total_like = banglaTelefilmList.get(position).getTotalLike();
                    VideoPreViewActivity.genre = banglaTelefilmList.get(position).getGenre();
                    VideoPreViewActivity.TYPE = "bt";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();
                } else if (TYPE.equals("sf")) {
                    VideoPreViewActivity.duration = shortFilmList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = shortFilmList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = shortFilmList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = shortFilmList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = shortFilmList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = shortFilmList.get(position).getPhysical_file_name();
                    VideoPreViewActivity.contentImg = shortFilmList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = shortFilmList.get(position).getZeid();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_SHORT_FILM;
                    VideoPreViewActivity.info = shortFilmList.get(position).getInfo();
                    VideoPreViewActivity.total_views = shortFilmList.get(position).getTotalView();
                    VideoPreViewActivity.total_like = shortFilmList.get(position).getTotalLike();
                    VideoPreViewActivity.genre = shortFilmList.get(position).getGenre();
                    VideoPreViewActivity.TYPE = "sf";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();
                } else {
                    // when notification arrived
                    VideoPreViewActivity.duration = hdVideoList.get(position).getDuration();
                    VideoPreViewActivity.contentCode = hdVideoList.get(position).getContent_code();
                    VideoPreViewActivity.categoryCode = hdVideoList.get(position).getCategory_code();
                    VideoPreViewActivity.contentName = hdVideoList.get(position).getContent_name();
                    VideoPreViewActivity.sContentType = hdVideoList.get(position).getContent_type();
                    VideoPreViewActivity.sPhysicalFileName = hdVideoList.get(position).getPhysical_file_name();
                    VideoPreViewActivity.contentImg = hdVideoList.get(position).getContent_image();
                    VideoPreViewActivity.ZedID = hdVideoList.get(position).getZeid();
                    VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_HD_VIDEO;
                    VideoPreViewActivity.info = hdVideoList.get(position).getInfo();
                    VideoPreViewActivity.total_views = hdVideoList.get(position).getTotalView();
                    VideoPreViewActivity.total_like = hdVideoList.get(position).getTotalLike();
                    VideoPreViewActivity.genre = hdVideoList.get(position).getGenre();
                    VideoPreViewActivity.TYPE = "hd";
                    Subscriptio_Class.type = "video";
                    subscriptio_class.detectMSISDN();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        //bufferingProgressDialog.show();


        //--------------Context------------
        Download_Class.applicationContext = VideoPreViewActivity.this;
        download_Class = new Download_Class();


        //---------------handset model dimension determine-----------
        DisplayMetrics dms = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dms);
        model = Build.MODEL;
        manufacture = Build.MANUFACTURER;
        brand = Build.BRAND; // like SEMC
        dimWidth = dms.widthPixels;
        dimHeight = dms.heightPixels;


        //----------Get the intent information--------------

        try {

            //Dialog
//            bufferingProgressDialog = ProgressDialog.show(VideoPreViewActivity.this,
//                    "Please wait", "Loading", true, true);


            contentCode = getIntent().getExtras().getString("contentCode");
            categoryCode = getIntent().getExtras().getString("categoryCode");
            contentName = getIntent().getExtras().getString("contentName");

            sContentType = getIntent().getExtras().getString("sContentType");
            sPhysicalFileName = getIntent().getExtras().getString(
                    "sPhysicalFileName").replace(" ", "_");
            ZedID = getIntent().getExtras().getString("ZedID");
            contentImg = getIntent().getExtras().getString("contentImg");
            doAction = getIntent().getExtras().getInt("doAction");

            String check = contentCode + "\n" + categoryCode + "\n" + contentName + "\n" + sContentType + "\n" + sPhysicalFileName + "\n" + ZedID + "\n" + contentImg + "\n" + doAction;
            Log.d("uu", check);
//            Toast.makeText(VideoPreViewActivity.this,"Im here"+
//                    check,Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doAction == 2) {

            // here hide all contents which has no data like related content, info, genre, likes
//            txtInfo.setVisibility(View.GONE);
//            txtGenre.setVisibility(View.GONE );
            new SendLaunchPushResponse().execute();

        }

        playerView = (JWPlayerView) findViewById(R.id.VideoView);
//        fragment = (JWPlayerFragment) getFragmentManager().findFragmentById(R.id.VideoView);
//        playerView = fragment.getPlayer();
//        playerView.animate();
        //videoView = (JWPlayerView) findViewById(R.id.VideoView);
//        final MediaController mediaController = new MediaController(this);
//        Log.d("physicalFileName",sPhysicalFileName);
//
//        mediaController.setAnchorView(videoView);
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                    @Override
//                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
//                            bufferingProgressDialog.show();
//                        }
//                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
//
//                            bufferingProgressDialog.dismiss();
//                        }
//
//                        return false;
//
//                    }
//                });
//                mediaController.show(0);
//            }
//        });


        videotitle.setText(contentName.replace("_", " "));
        videotitle.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_text));
// Set video link (mp4 format )

        if (sContentType.matches("FV")) {
            video = Uri.parse(fullVideoUrl + sPhysicalFileName.replace(" ", "_") + ".mp4");
            Log.d("linkf", fullVideoUrl + sPhysicalFileName.replace(" ", "_") + ".mp4");
            //Toast.makeText(getApplicationContext(),"1"+fullVideoUrl + sPhysicalFileName.replace(" ","_") + ".mp4",Toast.LENGTH_LONG).show();
            strs = fullVideoUrl + sPhysicalFileName.replace(" ", "_") + ".mp4";
            playVideo(strs);
            //Toast.makeText(getApplicationContext(),getDuration(strs),Toast.LENGTH_LONG).show();
        } else if (sContentType.matches("VD") || sContentType.matches("null")) {
            video = Uri.parse(videoURL + sPhysicalFileName + ".mp4");
            Log.d("linkf", videoURL + sPhysicalFileName + ".mp4");
//            Toast.makeText(getApplicationContext(),"2"+videoURL + sPhysicalFileName + ".mp4",Toast.LENGTH_LONG).show();
            strs = videoURL + sPhysicalFileName + ".mp4";
            //Toast.makeText(getApplicationContext(),getDuration(strs),Toast.LENGTH_LONG).show();
            playVideo(strs);
        } else {

            video = Uri.parse(audioURL + sPhysicalFileName + ".mp3");
            //Toast.makeText(getApplicationContext(),getDuration(audioURL+sPhysicalFileName+".mp3"),Toast.LENGTH_LONG).show();
            playVideo(strs);
        }

        // this used for hide top status bar
        getWindow().clearFlags(WindowManager
                .LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
//        params.width =  metrics.widthPixels;
//        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
//        videoView.setLayoutParams(params);
//        videoView.setMediaController(mediaController);
//        videoView.setVideoURI(video);
        //videoView.start();
//        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//            public void onPrepared(MediaPlayer mp) {
//                int duration = video.getDuration();
//                video.requestFocus();
//                video.start();
//                controller.show();
//
//            }
//        });
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                duration = videoView.getDuration();
//                videoView.requestFocus();
//                videoView.start();
//                totalDuration = getDuration(duration);
//                txtTotalDuration.setText(totalDuration);
//
//                if (videoView.isPlaying()){
//                    bufferingProgressDialog.dismiss();
//                }
//            }
//        });


        video_preview_download_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckUpdate(string);


                Download_Class.contentCode = contentCode;
                Download_Class.categoryCode = categoryCode;
                Download_Class.sContentType = sContentType;
                Download_Class.contentName = contentName;
                Download_Class.sPhysicalFileName = sPhysicalFileName;
                Download_Class.ZedID = ZedID;
                Download_Class.contentImg = contentImg;
                Download_Class.manufacture = manufacture;
                Download_Class.model = model;
                Download_Class.dimHeight = dimHeight;
                Download_Class.dimWidth = dimWidth;

                String str = contentCode + "\n" + categoryCode + "\n" + sContentType + "\n" + contentName + "\n" + sPhysicalFileName + "\n" + ZedID + "\n" + contentImg + "\n" + manufacture + "\n" + model + "\n" + dimHeight + "\n" + dimWidth;
                //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
                //Download_Class.testUrl = strs;
                download_Class.detectMSISDN();
            }
        });


    }

    private void playVideo(String strs) {
        if (isHd) {
//
//            List<PlaylistItem> playlist = new ArrayList<>();
//
//// Example 1: Create key-value map for the HTTP headers
//            Map<String, String> httpHeaders = new HashMap<>();
//
////            Map<String, String> httpHeadersSD = new HashMap<>();
////            httpHeaders.put("cookie", "XYZ123");
//
//            Map<String, String> httpHeadersHD = new HashMap<>();
//            httpHeaders.put("cookie", "QWERTY999");
//
//// Example 2: Create individual MediaSources
////            MediaSource mediaSD = new MediaSource.Builder()
////                    .file(strs)
////                    .httpHeaders(httpHeadersSD)
////                    .build();
//
//            MediaSource mediaHD = new MediaSource.Builder()
//                    .file(strs)
//                    .httpHeaders(httpHeadersHD)
//                    .build();
//
//            List<MediaSource> mediasources = new ArrayList<>();
////            mediasources.add(mediaSD);
//            mediasources.add(mediaHD);
//
//// Example 2: Add another PlaylistItem pointing to the second piece of content
//// This one will use multiple sources
//            playlist.add(new PlaylistItem.Builder()
//                    .sources(mediasources)
//                    .image(contentImg)
//                    .description("VU Mobile")
//                    .build());
//
//// Load the playlist into the player
//
//            playerView.play();
//            playerView.load(playlist);
// .skinName("test").skinUrl("http://vumobile.biz/adplay/test.css")
            // .stretching("exactfit")

            PlayerConfig playerConfig = new PlayerConfig.Builder()
                    .file(strs).logoFile("http://vumobile.biz/v/hddd.png")
                    .logoPosition(PlayerConfig.LOGO_POSITION_BOTTOM_RIGHT).logoHide(true)
                    .timeSliderAbove(true).image(videoImage)
                    .build();
            playerView = new JWPlayerView(VideoPreViewActivity.this, playerConfig);
            playerView.setBackgroundAudio(false);
            ViewGroup jwPlayerViewContainer = (ViewGroup) findViewById(R.id.containers);
            jwPlayerViewContainer.addView(playerView);
            playerView.setMute(false);


        } else {
            PlayerConfig playerConfig = new PlayerConfig.Builder()
                    .file(strs).controls(true).skin(Skin.SEVEN)
                    .timeSliderAbove(true).image(videoImage)
                    .build();
            playerView = new JWPlayerView(VideoPreViewActivity.this, playerConfig);
            ViewGroup jwPlayerViewContainer = (ViewGroup) findViewById(R.id.containers);
            jwPlayerViewContainer.addView(playerView);
            playerView.setMute(false);

        }
//        videolay = new PlaylistItem.Builder().file("http://vumobile.biz/v/a.mp4").title("Hello").image("https://www.w3schools.com/css/paris.jpg").build();
//        playerView.load(videolay);
////        int k = playerView.getCurrentQuality();
////        Log.d("QUALITY", String.valueOf(k));
////        playerView.setCurrentQuality(k);
//        playerView.play();
//        videolay = new PlaylistItem(strs);
//        playerView.setControls(true);
////        playerView.setSkin(Skin.FIVE);
//        int k = playerView.getCurrentQuality();
//        Log.d("QUALITY",String.valueOf(k));
//        playerView.setCurrentQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        playerView.load(videolay);
//        playerView.play();
    }

    // count video views
    private void videoView(String urlViews) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlViews, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    JSONArray array = jsonObject.getJSONArray("Table");

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject obj = array.getJSONObject(i);
                        String totalViews = obj.getString("ViewCount");
                        txtViewsCount.setText(totalViews);
                        Log.d("views", totalViews);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }


    private void initUI() {


        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.scrollVideoPreview);

        video_preview_download_button = (TextView) findViewById(R.id.video_preview_download_button);
        videotitle = (TextView) findViewById(R.id.video_preview_DetailsCategoryTitle);
        videoPreviewbottomLayout = (RelativeLayout) findViewById(R.id.video_preview_bottomlayout);
        pic_detail_upperLayout = (LinearLayout) findViewById(R.id.pic_detail_upperLayout);
//        shareButton= (ImageButton) findViewById(R.id.vid_preview_shareButton);
        btnRotate = (Button) findViewById(R.id.btn_fullscreen);
        btnOrzinalSize = (Button) findViewById(R.id.btn_orginalScreen);
        txtTotalDuration = (TextView) findViewById(R.id.txtTotalDuration);
        btnLike = (Button) findViewById(R.id.btnLike);
        txtLikeCount = (TextView) findViewById(R.id.txtlikeCount);
        txtViews = (TextView) findViewById(R.id.txtViews);
        txtViewsCount = (TextView) findViewById(R.id.txtViewsCount);
        txtGenre = (TextView) findViewById(R.id.txtGenre);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        btnLoadMoreData = (Button) findViewById(R.id.btnLoadMore);
        durationtxt = (TextView) findViewById(R.id.Duration);
        genretxt = (TextView) findViewById(R.id.Genre);
        infotxt = (TextView) findViewById(R.id.Info);
        //txtViewsCount.setText(total_views);
        txtGenre.setText(genre);
        txtInfo.setText(info);
        txtLikeCount.setText(total_like);
        txtTotalDuration.setText(duration);
        Log.d("TOtalLike", total_like);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeCount(Config.URL_LIKES);
            }
        });


    }

    private void parseRelatedVideo(String urlRelatedVideo, String types) {
        String url = urlRelatedVideo;
        String type = types;


        if (TYPE.equals("video")) {

            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    hideDialog();

                    hideMoreButton(num, response);

                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = response.getJSONObject(i);
                            videoHome = new VideoHome();
                            relatedVideoClass = new RelatedVideoClass();
                            videoHome.setLiveDate(obj.getString(Config.LIVE_DATE));
                            videoHome.setContentcode(obj.getString(Config.CONTENT_CODE_VIDEO));
                            videoHome.setCategory(obj.getString(Config.CATEGORY_CODE));
                            videoHome.setContentTile(obj.getString(Config.CONTENT_TITLE));
                            videoHome.setType(obj.getString(Config.TYPE));
                            videoHome.setPhysicalfilename(obj.getString(Config.PHYSICAL_FILE_NAME_VIDEO));
                            videoHome.setZid(obj.getString(Config.ZID));
                            String imageUrl = obj.getString(Config.IMAGE_URL).replaceAll(" ", "%20");
                            videoHome.setImageUrl(imageUrl);
                            videoHome.setDuration(obj.getString(Config.DURATION_VIDEO));
                            videoHome.setInfo(obj.getString(Config.INFO_VIDEO));
                            videoHome.setTotal_like(obj.getString(Config.TOTAL_LIKE_VIDEO));
                            videoHome.setTotal_views(obj.getString(Config.TOTAL_VIEWS_VIDEO));
                            videoHomeList.add(videoHome);

                            relatedVideoClass.setVIDEO_LIKE(videoHome.getTotal_like());
                            relatedVideoClass.setVIDEO_VIEWS(videoHome.getTotal_views());
                            relatedVideoClass.setURL_RELATED_VIDEO(videoHome.getImageUrl());
                            relatedVideoClass.setVIDEO_TITLE(videoHome.getContentTile());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                    Log.d("error", error.getMessage());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);

        } else if (TYPE.equals("drama")) {

            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    hideMoreButton(num, response);

                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = response.getJSONObject(i);
                            dramaClipsHome = new DramaClipsHome();
                            relatedVideoClass = new RelatedVideoClass();
                            dramaClipsHome.setContent_code(obj.getString(Config.CONTENT_CODE_DRAMA));
                            dramaClipsHome.setCategory_code(obj.getString(Config.CATEGORY_CODE_DRAMA));
                            dramaClipsHome.setContent_name(obj.getString(Config.CONTENT_NAME_DRAMA));
                            dramaClipsHome.setContent_type(obj.getString(Config.CONTENT_TYPE_DRAMA));
                            dramaClipsHome.setPhysical_name(obj.getString(Config.PHYSICALFILENAME_DRAMA));
                            dramaClipsHome.setZeid(obj.getString(Config.ZEID_DRAMA));
                            dramaClipsHome.setInfo(obj.getString(Config.INFO_FULL_DRAMA));
                            dramaClipsHome.setTotal_views(obj.getString(Config.TOTAL_VIEWS_FULL_DRAMA));
                            dramaClipsHome.setTotal_like(obj.getString(Config.TOTAL_LIKE_FULL_DRAMA));
                            dramaClipsHome.setGenre(obj.getString(Config.GENRE_FULL_DRAMA));
                            String imageUrl = obj.getString(Config.CONTENT_IMAGE_DRAMA).replaceAll(" ", "%20");
                            dramaClipsHome.setContent_image(Config.URL_SOURCE + imageUrl);
                            dramaClipsHomeList.add(dramaClipsHome);

                            relatedVideoClass.setVIDEO_VIEWS(dramaClipsHome.getTotal_views());
                            relatedVideoClass.setVIDEO_LIKE(dramaClipsHome.getTotal_like());
                            relatedVideoClass.setURL_RELATED_VIDEO(dramaClipsHome.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(dramaClipsHome.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("fullVideo")) {

            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {


                    hideMoreButton(num, response);

                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = response.getJSONObject(i);
                            fullVideoClass = new FullVideoClass();
                            relatedVideoClass = new RelatedVideoClass();
                            fullVideoClass.setContent_code(obj.getString(Config.CONTENT_CODE_FULL_VIDEO));
                            fullVideoClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_FULL_VDO));
                            fullVideoClass.setContent_name(obj.getString(Config.CONTENT_NAME_FULL_VIDEO));
                            fullVideoClass.setContent_type(obj.getString(Config.CONTENT_TYPE_FULL_VIDEO));
                            fullVideoClass.setPhysical_file_name(obj.getString(Config.PHYSICAL_FILE_NAME_FULL_VIDEO));
                            fullVideoClass.setZeid(obj.getString(Config.ZED_ID_FULL_VIDEO));
                            String imgUrl = obj.getString(Config.URL_IMAGE_FULL_VDO).replace(" ", "%20");
                            fullVideoClass.setContent_image(Config.URL_SOURCE + imgUrl);
//                            fullVideoClass.setTime_stamp(obj.getString(Config.TIME_STAMP_FULL_VDO));
//                            fullVideoClass.setLive_date(obj.getString(Config.LIVE_DATA_FULL_VDO));
//                            fullVideoClass.setRow_num(obj.getString(Config.ROWNUM_FULL_VDO));
                            fullVideoClass.setInfo(obj.getString(Config.INFO_FULL_VIDEO));
                            fullVideoClass.setDuration(obj.getString(Config.DURATION_FULL_VIDEO));
                            fullVideoClass.setGenre(obj.getString(Config.GENRE_FULL_VIDEO));
                            fullVideoClass.setTotalLike(obj.getString(Config.TOTAL_LIKE_FULL_VIDEO));
                            fullVideoClass.setTotalView(obj.getString(Config.TOTAL_VIEWS_FULL_VIDEO));
                            fullVideoClassList.add(fullVideoClass);

                            relatedVideoClass.setVIDEO_VIEWS(fullVideoClass.getTotalView());
                            relatedVideoClass.setVIDEO_LIKE(fullVideoClass.getTotalLike());
                            relatedVideoClass.setURL_RELATED_VIDEO(fullVideoClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(fullVideoClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("bolytop")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    hideMoreButton(num, response);

                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = response.getJSONObject(i);
                            bollywoodTopClass = new BollywoodTopClass();
                            relatedVideoClass = new RelatedVideoClass();
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

                            relatedVideoClass.setVIDEO_LIKE(bollywoodTopClass.getTotal_like());
                            relatedVideoClass.setVIDEO_VIEWS(bollywoodTopClass.getTotal_views());
                            relatedVideoClass.setURL_RELATED_VIDEO(bollywoodTopClass.getContent_img());
                            relatedVideoClass.setVIDEO_TITLE(bollywoodTopClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("englishtop")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
                            englishTopClass = new EnglishTopClass();
                            englishTopClass.setDuration(obj.getString(Config.DURATION_FULL_VIDEO));
                            englishTopClass.setContent_code(obj.getString(Config.CONTENT_CODE_ENGTOP));
                            englishTopClass.setCategoryCode(obj.getString(Config.CATEGORY_CODE_ENGTOP));
                            englishTopClass.setContent_name(obj.getString(Config.CONTENT_NAME_ENGTOP));
                            englishTopClass.setsContentType(obj.getString(Config.CONTENT_TYPE_ENGTOP));
                            englishTopClass.setsPhysicalFileName(obj.getString(Config.PHYSICALNAME_ENGTOP));
                            englishTopClass.setZedID(obj.getString(Config.ZEID_ENGTOP));
                            String imgUrl = obj.getString(Config.CONTENT_IMAGE_ENGTOP).replace(" ", "%20");
                            englishTopClass.setContent_img(Config.URL_SOURCE + imgUrl);
                            englishTopClass.setInfo(obj.getString(Config.INFO_FULL_ENGTOP));
                            englishTopClass.setGenre(obj.getString(Config.GENRE_FULL_ENGTOP));
                            englishTopClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_FULL_ENGTOP));
                            englishTopClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_FULL_ENGTOP));
                            englishTopClassList.add(englishTopClass);

                            relatedVideoClass.setVIDEO_VIEWS(englishTopClass.getTotal_views());
                            relatedVideoClass.setVIDEO_LIKE(englishTopClass.getTotal_like());
                            relatedVideoClass.setURL_RELATED_VIDEO(englishTopClass.getContent_img());
                            relatedVideoClass.setVIDEO_TITLE(englishTopClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("banglatop")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
                            banglaTopClass = new BanglaTopClass();
                            banglaTopClass.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_TOP));
                            banglaTopClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_TOP));
                            banglaTopClass.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_TOP));
                            banglaTopClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_BANGLA_TOP));
                            banglaTopClass.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_TOP));
                            banglaTopClass.setZeid(obj.getString(Config.ZEID_BANGLA_TOP));
                            banglaTopClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_BANGLA_TOP)).replace(" ", "%20"));
                            banglaTopClass.setTime_stamp(obj.getString(Config.TIME_STAMP_BANGLA_TOP));
                            banglaTopClass.setLive_data(obj.getString(Config.LIVE_DATA_BANGLA_TOP));
                            banglaTopClass.setRow_num(obj.getString(Config.ROWNUM_BANGLA_TOP));
                            banglaTopClass.setInfo(obj.getString(Config.INFO_FULL_BANGLA_TOP));
                            banglaTopClass.setGenre(obj.getString(Config.GENRE_FULL_BANGLA_TOP));
                            banglaTopClass.setTotalView(obj.getString(Config.TOTAL_VIEWS_FULL_BANGLA_TOP));
                            banglaTopClass.setTotalLike(obj.getString(Config.TOTAL_LIKE_FULL_BANGLA_TOP));

                            banglaTopClassList.add(banglaTopClass);

                            relatedVideoClass.setVIDEO_LIKE(banglaTopClass.getTotalLike());
                            relatedVideoClass.setVIDEO_VIEWS(banglaTopClass.getTotalView());
                            relatedVideoClass.setURL_RELATED_VIDEO(banglaTopClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(banglaTopClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("dhaligossip")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
                            dhaliwoodGossipClass = new DhaliwoodGossipClass();
                            dhaliwoodGossipClass.setContent_code(obj.getString(Config.CONTENT_CODE_DHALLYWOOD_GOSSIP));
                            dhaliwoodGossipClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_DHALLYWOOD_GOSSIP));
                            dhaliwoodGossipClass.setContent_name(obj.getString(Config.CONTENT_NAME_DHALLYWOOD_GOSSIP));
                            dhaliwoodGossipClass.setContent_type(obj.getString(Config.CONTENT_TYPE_DHALLYWOOD_GOSSIP));
                            dhaliwoodGossipClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_DHALLYWOOD_GOSSIP));
                            dhaliwoodGossipClass.setZeid(obj.getString(Config.ZEID_DHALLYWOOD_GOSSIP));
                            dhaliwoodGossipClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_DHALLYWOOD_GOSSIP)).replace(" ", "%20"));
                            dhaliwoodGossipClass.setInfo(obj.getString(Config.INFO_FULL_DHALIWOOD));
                            dhaliwoodGossipClass.setGenre(obj.getString(Config.GENRE_FULL_DHALIWOOD));
                            dhaliwoodGossipClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_FULL_DHALIWOOD));
                            dhaliwoodGossipClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_FULL_DHALIWOOD));
                            dhaliwoodGossipClassList.add(dhaliwoodGossipClass);

                            relatedVideoClass.setVIDEO_VIEWS(dhaliwoodGossipClass.getTotal_views());
                            relatedVideoClass.setVIDEO_LIKE(dhaliwoodGossipClass.getTotal_like());
                            relatedVideoClass.setURL_RELATED_VIDEO(dhaliwoodGossipClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(dhaliwoodGossipClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("movieclips")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
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

                            relatedVideoClass.setVIDEO_LIKE(movieClipsClass.getTotal_like());
                            relatedVideoClass.setVIDEO_VIEWS(movieClipsClass.getTotal_views());
                            relatedVideoClass.setURL_RELATED_VIDEO(movieClipsClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(movieClipsClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("movietrailer")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
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

                            relatedVideoClass.setVIDEO_LIKE(movieTrailerClass.getTotal_like());
                            relatedVideoClass.setVIDEO_VIEWS(movieTrailerClass.getTotal_views());
                            relatedVideoClass.setURL_RELATED_VIDEO(movieTrailerClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(movieTrailerClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("moviereview")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
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

                            relatedVideoClass.setVIDEO_LIKE(movieReviewClass.getTotla_like());
                            relatedVideoClass.setVIDEO_VIEWS(movieReviewClass.getTotal_views());
                            relatedVideoClass.setURL_RELATED_VIDEO(movieReviewClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(movieReviewClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("celenews")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
                            celebratyNewsClass = new CelebratyNewsClass();
                            celebratyNewsClass.setContent_code(obj.getString(Config.CONTENT_CODE_CELEBRATY_NEWS));
                            celebratyNewsClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_CELEBRATY_NEWS));
                            celebratyNewsClass.setContent_name(obj.getString(Config.CONTENT_NAME_CELEBRATY_NEWS));
                            celebratyNewsClass.setContent_type(obj.getString(Config.CONTENT_TYPE_CELEBRATY_NEWS));
                            celebratyNewsClass.setPhysical_name(obj.getString(Config.PHYSICALNAME_CELEBRATY_NEWS));
                            celebratyNewsClass.setZeid(obj.getString(Config.ZEID_CELEBRATY_NEWS));
                            celebratyNewsClass.setInfo(obj.getString(Config.INFO_CELEBRATY_NEWS));
                            celebratyNewsClass.setGenre(obj.getString(Config.GENRE_CELEBRATY_NEWS));
                            celebratyNewsClass.setTotal_views(obj.getString(Config.TOTAL_VIEWS_CELEBRATY_NEWS));
                            celebratyNewsClass.setTotal_like(obj.getString(Config.TOTAL_LIKE_CELEBRATY_NEWS));
                            celebratyNewsClass.setContent_image((Config.URL_SOURCE + obj.getString(Config.CONTENT_IMAGE_CELEBRATY_NEWS)).replace(" ", "%20"));
                            celebratyNewsClassList.add(celebratyNewsClass);

                            relatedVideoClass.setVIDEO_VIEWS(celebratyNewsClass.getTotal_views());
                            relatedVideoClass.setVIDEO_LIKE(celebratyNewsClass.getTotal_like());
                            relatedVideoClass.setURL_RELATED_VIDEO(celebratyNewsClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(celebratyNewsClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("holygossip")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
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

                            relatedVideoClass.setVIDEO_LIKE(hollywoodGossipClass.getTotal_likes());
                            relatedVideoClass.setVIDEO_VIEWS(hollywoodGossipClass.getTotal_views());
                            relatedVideoClass.setURL_RELATED_VIDEO(hollywoodGossipClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(hollywoodGossipClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("lifestyle")) {

            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {

                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            relatedVideoClass = new RelatedVideoClass();
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

                            relatedVideoClass.setVIDEO_LIKE(lifeStyleClass.getTotal_like());
                            relatedVideoClass.setVIDEO_VIEWS(lifeStyleClass.getTotal_view());
                            relatedVideoClass.setURL_RELATED_VIDEO(lifeStyleClass.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(lifeStyleClass.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);

        } else if (TYPE.equals("hd")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.d("Responsee", jsonArray.toString());
                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            hdVideo = new HDVideo();
                            relatedVideoClass = new RelatedVideoClass();
                            hdVideo.setContent_code(obj.getString(Config.CONTENT_CODE_HD_VIDEO));
                            hdVideo.setCategory_code(obj.getString(Config.CATEGORY_CODE_HD_VIDEO));
                            hdVideo.setContent_name(obj.getString(Config.CONTENT_NAME_HD_VIDEO));
                            hdVideo.setContent_type(obj.getString(Config.CONTENT_TYPE_HD_VIDEO));
                            hdVideo.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_HD_VIDEO));
                            hdVideo.setZeid(obj.getString(Config.ZEID_HD_VIDEO));
                            String imgUrl = obj.getString(Config.CONTENT_IMAGE_HD_VIDEO).replace(" ", "%20");
                            hdVideo.setContent_image(imgUrl);
                            hdVideo.setInfo(obj.getString(Config.INFO_HD_VIDEO));
                            hdVideo.setDuration(obj.getString(Config.DURATION_HD_VIDEO));
                            hdVideo.setGenre(obj.getString(Config.GENRE_HD_VIDEO));
                            hdVideo.setTotalLike(obj.getString(Config.TOTAL_LIKE_HD_VIDEO));
                            hdVideo.setTotalView(obj.getString(Config.TOTAL_VIEWS_HD_VIDEO));
                            hdVideoList.add(hdVideo);

                            Log.d("LOOOOG", "like " + hdVideo.getTotalLike());
                            Log.d("LOOOOG", "view " + hdVideo.getTotalView());
                            Log.d("LOOOOG", "image " + hdVideo.getContent_image());
//
                            relatedVideoClass.setVIDEO_LIKE(hdVideo.getTotalLike());
                            relatedVideoClass.setVIDEO_VIEWS(hdVideo.getTotalView());
                            relatedVideoClass.setURL_RELATED_VIDEO(hdVideo.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(hdVideo.getContent_name());

                            relatedClassList.add(relatedVideoClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("bm")) {
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.d("Responsee", jsonArray.toString());
                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            banglaMusicVideo = new BanglaMusicVideo();
                            relatedVideoClass = new RelatedVideoClass();
                            banglaMusicVideo.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
                            String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ", "%20");
                            banglaMusicVideo.setContent_image(Config.URL_SOURCE + imgUrl);
                            banglaMusicVideo.setInfo(obj.getString(Config.INFO_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setDuration(obj.getString(Config.DURATION_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setGenre(obj.getString(Config.GENRE_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setTotalLike(obj.getString(Config.TOTAL_LIKE_BANGLA_MUSIC_VIDEO));
                            banglaMusicVideo.setTotalView(obj.getString(Config.TOTAL_VIEWS_BANGLA_MUSIC_VIDEO));

                            banglaMusicVideoList.add(banglaMusicVideo);

                            relatedVideoClass.setVIDEO_LIKE(banglaMusicVideo.getTotalLike());
                            relatedVideoClass.setVIDEO_VIEWS(banglaMusicVideo.getTotalView());
                            relatedVideoClass.setURL_RELATED_VIDEO(banglaMusicVideo.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(banglaMusicVideo.getContent_name());

                            relatedClassList.add(relatedVideoClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("bn")) {

            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.d("Responsee", jsonArray.toString());
                    hideMoreButton(num, jsonArray);
                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            banglaNatok = new BanglaNatok();
                            relatedVideoClass = new RelatedVideoClass();
                            banglaNatok.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
                            String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ", "%20");
                            banglaNatok.setContent_image(Config.URL_SOURCE + imgUrl);
                            banglaNatok.setInfo(obj.getString(Config.INFO_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setDuration(obj.getString(Config.DURATION_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setGenre(obj.getString(Config.GENRE_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setTotalLike(obj.getString(Config.TOTAL_LIKE_BANGLA_MUSIC_VIDEO));
                            banglaNatok.setTotalView(obj.getString(Config.TOTAL_VIEWS_BANGLA_MUSIC_VIDEO));

                            banglaNatokList.add(banglaNatok);

                            relatedVideoClass.setVIDEO_LIKE(banglaNatok.getTotalLike());
                            relatedVideoClass.setVIDEO_VIEWS(banglaNatok.getTotalView());
                            relatedVideoClass.setURL_RELATED_VIDEO(banglaNatok.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(banglaNatok.getContent_name());

                            relatedClassList.add(relatedVideoClass);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else if (TYPE.equals("bt")) {

            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.d("Responsee", jsonArray.toString());
                    hideMoreButton(num, jsonArray);
                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            banglaTelefilm = new BanglaTelefilm();
                            relatedVideoClass = new RelatedVideoClass();
                            banglaTelefilm.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
                            String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ", "%20");
                            banglaTelefilm.setContent_image(Config.URL_SOURCE + imgUrl);
                            banglaTelefilm.setInfo(obj.getString(Config.INFO_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setDuration(obj.getString(Config.DURATION_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setGenre(obj.getString(Config.GENRE_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setTotalLike(obj.getString(Config.TOTAL_LIKE_BANGLA_MUSIC_VIDEO));
                            banglaTelefilm.setTotalView(obj.getString(Config.TOTAL_VIEWS_BANGLA_MUSIC_VIDEO));

                            banglaTelefilmList.add(banglaTelefilm);

                            relatedVideoClass.setVIDEO_LIKE(banglaTelefilm.getTotalLike());
                            relatedVideoClass.setVIDEO_VIEWS(banglaTelefilm.getTotalView());
                            relatedVideoClass.setURL_RELATED_VIDEO(banglaTelefilm.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(banglaTelefilm.getContent_name());

                            relatedClassList.add(relatedVideoClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);

        } else if (TYPE.equals("sf")) {

            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.d("Responsee", jsonArray.toString());
                    hideMoreButton(num, jsonArray);
                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            shortFilm = new ShortFilm();
                            relatedVideoClass = new RelatedVideoClass();
                            shortFilm.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                            shortFilm.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                            shortFilm.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                            shortFilm.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                            shortFilm.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                            shortFilm.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
                            String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ", "%20");
                            shortFilm.setContent_image(Config.URL_SOURCE + imgUrl);
                            shortFilm.setInfo(obj.getString(Config.INFO_HD_VIDEO));
                            shortFilm.setDuration(obj.getString(Config.DURATION_HD_VIDEO));
                            shortFilm.setGenre(obj.getString(Config.GENRE_HD_VIDEO));
                            shortFilm.setTotalLike(obj.getString(Config.TOTAL_LIKE_HD_VIDEO));
                            shortFilm.setTotalView(obj.getString(Config.TOTAL_VIEWS_HD_VIDEO));

                            shortFilmList.add(shortFilm);

                            relatedVideoClass.setVIDEO_LIKE(shortFilm.getTotalLike());
                            relatedVideoClass.setVIDEO_VIEWS(shortFilm.getTotalView());
                            relatedVideoClass.setURL_RELATED_VIDEO(shortFilm.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(shortFilm.getContent_name());

                            relatedClassList.add(relatedVideoClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        } else {
            // this will fire when new notification arrived
            durationtxt.setVisibility(View.GONE);
            infotxt.setVisibility(View.GONE);
            genretxt.setVisibility(View.GONE);
            JsonArrayRequest request = new JsonArrayRequest(Config.URL_HD_VIDEO, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    Log.d("Responsee", jsonArray.toString());
                    hideMoreButton(num, jsonArray);

                    for (int i = 0; i < num; i++) {


                        try {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            hdVideo = new HDVideo();
                            relatedVideoClass = new RelatedVideoClass();
                            hdVideo.setContent_code(obj.getString(Config.CONTENT_CODE_HD_VIDEO));
                            hdVideo.setCategory_code(obj.getString(Config.CATEGORY_CODE_HD_VIDEO));
                            hdVideo.setContent_name(obj.getString(Config.CONTENT_NAME_HD_VIDEO));
                            hdVideo.setContent_type(obj.getString(Config.CONTENT_TYPE_HD_VIDEO));
                            hdVideo.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_HD_VIDEO));
                            hdVideo.setZeid(obj.getString(Config.ZEID_HD_VIDEO));
                            String imgUrl = obj.getString(Config.CONTENT_IMAGE_HD_VIDEO).replace(" ", "%20");
                            hdVideo.setContent_image(imgUrl);
                            hdVideo.setInfo(obj.getString(Config.INFO_HD_VIDEO));
                            hdVideo.setDuration(obj.getString(Config.DURATION_HD_VIDEO));
                            hdVideo.setGenre(obj.getString(Config.GENRE_HD_VIDEO));
                            hdVideo.setTotalLike(obj.getString(Config.TOTAL_LIKE_HD_VIDEO));
                            hdVideo.setTotalView(obj.getString(Config.TOTAL_VIEWS_HD_VIDEO));
                            hdVideoList.add(hdVideo);

                            Log.d("LOOOOG", "like " + hdVideo.getTotalLike());
                            Log.d("LOOOOG", "view " + hdVideo.getTotalView());
                            Log.d("LOOOOG", "image " + hdVideo.getContent_image());
//
                            relatedVideoClass.setVIDEO_LIKE(hdVideo.getTotalLike());
                            relatedVideoClass.setVIDEO_VIEWS(hdVideo.getTotalView());
                            relatedVideoClass.setURL_RELATED_VIDEO(hdVideo.getContent_image());
                            relatedVideoClass.setVIDEO_TITLE(hdVideo.getContent_name());

                            relatedClassList.add(relatedVideoClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapterRelatedItem);
                    adapterRelatedItem.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(), "Connection error!", Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }
    }

    private void hideMoreButton(int nums, JSONArray array) {

        if (nums > array.length()) {
            mPullRefreshScrollView.onRefreshComplete();
            btnLoadMoreData.setVisibility(View.GONE);
        }
    }

    private void initRecycler() {
        adapterRelatedItem = new RelatedItemAdapter(VideoPreViewActivity.this, relatedClassList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_relative_Videos);
        recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.scrollToPosition(adapterRelatedItem.getItemCount()-1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            //videoPreviewbottomLayout.setVisibility(View.GONE);
//            btnOrzinalSize.setVisibility(View.VISIBLE);
//            txtTotalDuration.setText(totalDuration);
//            DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
//            params.width =  metrics.widthPixels;
//            params.height = metrics.heightPixels;
//            videoView.setLayoutParams(params);
//            videoView.start();
//
////
////            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
////            params.setMargins(200, 0, 0, 0);
////            videoView.setLayoutParams(params);
//
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
////            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)videoView.getLayoutParams();
////            params.setMargins(0, 200, 0, 0);
////            videoView.setLayoutParams(params);
//            btnOrzinalSize.setVisibility(View.GONE);
//            videotitle.setText(contentName.replace("_", " "));
//            //text move animation
//            videotitle.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_text));
//            txtTotalDuration.setText(totalDuration);
//            DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
//            params.width =  metrics.widthPixels;
//            params.height = metrics.heightPixels/2;
//            params.leftMargin = 0;
//            videoView.setLayoutParams(params);
//            videoView.start();
//        }
    }

    //----------Function for Update dialog----------------
    private void CheckUpdate(final String url_string) {
        // TODO Auto-generated method stub

        // if (!SharedPreferencesHelper.isOnline(this))
        // AlertMessage.showMessage(this, "No Internet Connection");

        // busyNow = new BusyDialog(context, true);
        // busyNow.show();

        final Thread d = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    UpdateString = HttpRequest.GetText(HttpRequest
                            .getInputStreamForGetRequest(url_string));
                    String info_string = "http://www.vumobile.biz/clubz_android/clubz_version_reason.txt";
                    updateReasonString = HttpRequest.GetText(HttpRequest
                            .getInputStreamForGetRequest(info_string));

                    Log.i("UpdateString", UpdateString);

                } catch (final Exception e) {

                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // if (busyNow != null) {
                        // busyNow.dismis();
                        // }
                        /* Check Update Version */

                        try {
                            PackageInfo pinfo;
                            pinfo = getPackageManager().getPackageInfo(
                                    getPackageName(), 0);
                            String versionName = pinfo.versionName;
                            if (!versionName.equalsIgnoreCase(UpdateString
                                    .toString().trim())) {

                                Update();

                            }

                        } catch (PackageManager.NameNotFoundException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        /* Check Update Version */

                    }

                });

            }
        });

        d.start();
    }

    public void Update() {
        {

            final Dialog updateDialog = new Dialog(this, android.R.style.Theme_Light);
            updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            updateDialog.setContentView(R.layout.update_dialog_activity);


            TextView update_text = (TextView) updateDialog.findViewById(R.id.update_text);

            Button update_app = (Button) updateDialog.findViewById(R.id.update_app);
            ImageView img = (ImageView) updateDialog.findViewById(R.id.imageView1);

            update_text.setText(updateReasonString);

            update_app.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    updateDialog.dismiss();
                    // showUpdateDialog=false;


                    /**
                     * if this button is clicked, close current
                     * activity
                     */
                    final String appPackageName = getPackageName();
                    try {
                        startActivity(new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id="
                                        + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id="
                                        + appPackageName)));
                    }
                }
            });

            img.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    updateDialog.dismiss();
                }
            });

            updateDialog.show();

        }
    }

    private void likeCount(String urlLikes) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlLikes + contentCode, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    JSONArray array = jsonObject.getJSONArray("Table");

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject obj = array.getJSONObject(i);

                        String totalLikes = obj.getString("LikeCount");
                        txtLikeCount.setText(totalLikes);
                        Log.d("LikeCount", totalLikes);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Connection Error!", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(VideoPreViewActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }


    public class SendLaunchPushResponse extends AsyncTask<String, String, String> {
        RequiredUserInfo userinfo = new RequiredUserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(VideoPreViewActivity.this);
        String HS_MOD_ = userinfo.deviceModel(VideoPreViewActivity.this);
        String user_email = userinfo.userEmail(VideoPreViewActivity.this);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            VideoPreViewActivity.rsltNumber = "START";

            try {
                Thread.sleep(1000);

                Caller c = new Caller();
                // c.ad= c.ad;
                c.join();
                c.start();

                while (VideoPreViewActivity.rsltNumber == "START") {
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {
                    }
                }

                AppConstant.mno = rsltNumber;

            } catch (Exception ex) {

            }
            if (AppConstant.mno.equals("ERROR")) {
                rsltNumber = "wifi";
            } else {
                rsltNumber = AppConstant.mno;
            }

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", user_email));
            params.add(new BasicNameValuePair("action", "launch"));
            params.add(new BasicNameValuePair("handset_name", HS_MANUFAC_));
            params.add(new BasicNameValuePair("handset_model", HS_MOD_));
            params.add(new BasicNameValuePair("msisdn", rsltNumber));

            // getting JSON Object
            // Note that create product url accepts POST method
            phpRequest.makeHttpRequest(pushResponseUrl, "POST", params);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }


    }

    private String getDuration(int totlalDuration) {

        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(totlalDuration),
                TimeUnit.MILLISECONDS.toSeconds(totlalDuration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totlalDuration))
        );


    }

    class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            parseRelatedVideo(URL_RELATED_VIDEO, TYPE);

            return null;
        }
    }

    public void btnLoadMore(View view) {
//        adapterRelatedItem.clearData();
//        relatedClassList = new ArrayList<RelatedVideoClass>();
//        adapterRelatedItem = new RelatedItemAdapter(VideoPreViewActivity.this,relatedClassList);
//        adapterRelatedItem.notifyDataSetChanged();

        loading = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);
        loading.setCancelable(true);

        lifeStyleClassList.clear();
        hollywoodGossipClassList.clear();
        celebratyNewsClassList.clear();
        movieReviewClassList.clear();
        movieTrailerClassList.clear();
        movieClipsClassList.clear();
        dhaliwoodGossipClassList.clear();
        banglaTopClassList.clear();
        englishTopClassList.clear();
        bollywoodTopClassList.clear();
        fullVideoClassList.clear();
        dramaClipsHomeList.clear();
        videoHomeList.clear();
        relatedClassList.clear();
        hdVideoList.clear();
        banglaMusicVideoList.clear();
        banglaNatokList.clear();
        banglaTelefilmList.clear();
        shortFilmList.clear();

        num += 10;
//        hollywoodGossipClassList = new ArrayList<HollywoodGossipClass>();
//        celebratyNewsClassList = new ArrayList<CelebratyNewsClass>();
//        movieReviewClassList = new ArrayList<MovieReviewClass>();
//        movieTrailerClassList = new ArrayList<MovieTrailerClass>();
//        movieClipsClassList = new ArrayList<MovieClipsClass>();
//        dhaliwoodGossipClassList = new ArrayList<DhaliwoodGossipClass>();
//        banglaTopClassList = new ArrayList<BanglaTopClass>();
//        englishTopClassList = new ArrayList<EnglishTopClass>();
//        bollywoodTopClassList = new ArrayList<BollywoodTopClass>();
//        fullVideoClassList = new ArrayList<FullVideoClass>();
//        dramaClipsHomeList = new ArrayList<DramaClipsHome>();
//        videoHomeList = new ArrayList<VideoHome>();

        new BackgroundTask().execute();

    }

    public void hideDialog() {

        if (loading != null) {
            loading.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        playerView.stop();
        Intent i = new Intent(VideoPreViewActivity.this, MainActivity.class);
// set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerView.stop();
    }
}

