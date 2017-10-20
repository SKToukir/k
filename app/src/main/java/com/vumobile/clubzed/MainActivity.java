package com.vumobile.clubzed;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gcm.GCMRegistrar;
import com.squareup.picasso.Picasso;
import com.vumobile.clubzed.GameRelated.GameCategoryActivity;
import com.vumobile.clubzed.GameRelated.GameListCustomAdapter;
import com.vumobile.clubzed.Notification.MyReceiver;
import com.vumobile.clubzed.Notification.NetworkedService;
import com.vumobile.clubzed.Picture_Sticker_Related.PictureCategoryActivity;
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.Picture_Sticker_Related.StickerCategoryActivity;
import com.vumobile.clubzed.Picture_Sticker_Related.StickerPreview;
import com.vumobile.clubzed.SongRelated.PlaySongActivity;
import com.vumobile.clubzed.SongRelated.SongCategoryActivity;
import com.vumobile.clubzed.VideoRelated.DramaGridActivity;
import com.vumobile.clubzed.VideoRelated.FullVideoGridviewActivity;
import com.vumobile.clubzed.VideoRelated.HDCategoryActivity;
import com.vumobile.clubzed.VideoRelated.ShortFilmGridActivity;
import com.vumobile.clubzed.VideoRelated.VideoMoreActivity;
import com.vumobile.clubzed.VideoRelated.VideoPreViewActivity;
import com.vumobile.clubzed.adapter.DramaAdapterClass;
import com.vumobile.clubzed.adapter.FullVideoAdapter;
import com.vumobile.clubzed.adapter.GamesAdapter;
import com.vumobile.clubzed.adapter.HDVideoAdapter;
import com.vumobile.clubzed.adapter.MusicAdapter;
import com.vumobile.clubzed.adapter.PictureAdapter;
import com.vumobile.clubzed.adapter.PrimaryAdapter;
import com.vumobile.clubzed.adapter.ShortFilmAdapter;
import com.vumobile.clubzed.adapter.StickerAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.CustomVolleyRequest;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.DramaClipsHome;
import com.vumobile.clubzed.model.FullVideoClass;
import com.vumobile.clubzed.model.GameClass;
import com.vumobile.clubzed.model.HDVideo;
import com.vumobile.clubzed.model.MusicClass;
import com.vumobile.clubzed.model.PictureClass;
import com.vumobile.clubzed.model.SharedPrefClass;
import com.vumobile.clubzed.model.ShortFilm;
import com.vumobile.clubzed.model.SliderClass;
import com.vumobile.clubzed.model.StickerClass;
import com.vumobile.clubzed.model.VideoHome;
import com.vumobile.clubzed.util.FAQActivity;
import com.vumobile.clubzed.util.HelpActivity;
import com.vumobile.clubzed.util.HttpRequest;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.vumobile.clubzed.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.vumobile.clubzed.CommonUtilities.EXTRA_MESSAGE;
import static com.vumobile.clubzed.CommonUtilities.SENDER_ID;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,View.OnClickListener,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;

    SliderClass sliderClass;
    List<SliderClass> sliderClasses = new ArrayList<SliderClass>();
    SharedPrefClass sharedPrefClass;
    Intent intent;
    ProgressDialog loading;
    ScrollView scrollView;
    StickerClass stickerClass;
    PictureClass pictureClass;
    GameClass gameClass;
    MusicClass musicClass;
    ShortFilm shortFilm;
    VideoHome videoHome;
    DramaClipsHome dramaClips;
    HDVideo hdVideo;
    FullVideoClass fullVideoClass;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    //    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    Context mContext;
    Picasso picasso;
    private SliderLayout mViewFlipper;
    List<ShortFilm> shortFilmList = new ArrayList<ShortFilm>();
    List<StickerClass> stickerList = new ArrayList<StickerClass>();
    List<PictureClass> pictureList = new ArrayList<PictureClass>();
    List<GameClass> gameList = new ArrayList<GameClass>();
    List<MusicClass> musicClassList = new ArrayList<MusicClass>();
    List<FullVideoClass> fullVideoClassList = new ArrayList<FullVideoClass>();
    List<VideoHome> videoHomeList = new ArrayList<VideoHome>();
    List<DramaClipsHome> dramaClipsHomeList = new ArrayList<DramaClipsHome>();
    List<HDVideo> hdVideoList = new ArrayList<HDVideo>();
    RecyclerView recyclerView,recyclerViewDrama,recyclerviewFullVideo,recyclerViewMusic,recyclerViewGame,
            recyclerViewPicture,recyclerViewSticker, recycler_view_hdvideo, recycler_view_SFvideo;
    RecyclerView.Adapter adapter,adapterDrama,recyclerFullVdoAdapter,musicAdapter,gameAdapter,
            pictureAdapter,stickerAdapter, hdVideoAdapter, adapterSf;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    NetworkImageView homePageHeaderImage, homePageBannerImage;

    public static String rsltNumber = "";
    public static boolean showUpdateDialog=false;

    EditText etSearch;
    TextView txtpictureMore, txtvideoMore, txtgameMore, txtmusicMore,txtstickerMore,txtDramaMore,txtFullVdoMore, txthdvideoMore, shortMore;
    NetworkImageView picture1, picture2, picture3, picture4, videoOne, videoTwo, videoThree, videoFour,
            gameOne, gameTwo, gameThree, gameFour, musicOne, musicTwo, musicThree, musicFour,stickerOne,
            stickerTwo,stickerThree,stickerFour;
    public final String gameUrl = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=games";
    public final String pictureUrl = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=picture";
    public final String videoUrl = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=video";
    public final String musicUrl = "http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=music";
    public final String stickerUrl="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=stkCatagory";
    public final String sliderurl="http://wap.shabox.mobi/GCMPanel/ClubzAPI.aspx?cat=Slider";

    RequestQueue homeActivityRequesQueue;
    public String imageUrl = "", portalCode = "", title = "";
    public  String[] urlString={"1","2","3","4"};
    ImageLoader imageLoader;
    public  String UpdateString;
    static final Integer ACCOUNTS = 0x6;
    public static String updateReasonString="";
    public static String contentCode, categoryCode, contentName, sContentType, sPhysicalFileName, ZedID, contentImg;
    public static String[] contentCodeArray=new String[24];
    public static String[] categoryCodeArray=new String[24];
    public static String[] contentNameArray=new String[24];
    public static String[] sContentTypeArray=new String[24];
    public static String[] sPhysicalFileNameArray=new String[24];
    public static String[] ZedIDArray=new String[24];
    public static String[] contentImgArray=new String[24];
    Button btnSearching;
    LinearLayout searchLayout;
    Subscriptio_Class subscriptio_class;
    public static int videoCounter=0,musicCounter=0,pictureCounter=0,gameCounter=0,stickerCounter=0;
    public  String slidercontcode,slidercategorycode,slidercontentName,slidercontentType,sliderPhysicalfilename,slidercontetnImage,sliderZid,
            SliderType="",sliderpath="";
    ImageView btnHelp, btnHome, btnMydownload,btnBuddy,btnBdTube,btnBanglaBeats,btnAmarSticker,btnRateMe,btnHelpSame;

    public String[] slidercontetnImagearray;

    String []imageArray={"http://wap.shabox.mobi/cztest/Faisal/any.jpg","http://wap.shabox.mobi/cztest/Faisal/people.jpg","http://wap.shabox.mobi/cztest/Faisal/nature.jpg"};
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    //---------need for GCM and Server registration----------
    public static int dimHeight,dimWidth,j=0,index=0,i=0;
    public static String model,manufacture,brand,name,email;
    public static String ipAddress;


    private PendingIntent pendingIntent;
    // label to display GCM messages
    AsyncTask<Void, Void, Void> mRegisterTask;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_main);

        //============Ask user for permission=========//
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;


        askUserRuntimePermission();

        sharedPrefClass = new SharedPrefClass();
        initSliderImage();
        initUI();// initialize the ui

        // video recycler
        initRecycler();
        // hd video
        initHdVideoRecycler();
        //drama recycler
        initDramaRecycler();
        //init full video recycler
        initFullVideoRecycler();
        //init music recycler
        initMusicRecycler();
        // init game recycler
        initGameRecycler();
        // init game recycler
        initPictureRecycler();
        // init sticker recycler
        initStickerRecyler();
        // init short film recycler
        initRecyclerShortFilm();

        //new BackGroundTask().execute();

        //parse video api
        parseDataVideo(Config.URL_VIDEO_URL);
        // parse short film
        parseShortFilm(Config.URL_BANGLA_SHORT_FILM);
        // parse hd video
        parseHDVideo(Config.URL_HD_VIDEO);
        //parse drama api
        parseDramaApi(Config.URL_DRAMA);
        //parse full video api
        parseFullVideo(Config.URL_FULL_VIDEO);
        //parse music api
        parseMusic(Config.URL_MUSIC);
        //parse game api
        parseGame(Config.URL_GAME);
        // parse picture api
        parsePicture(Config.URL_PICTURE);
        //parse sticker api
        parseSticker(Config.URL_STICKER);

        GameListCustomAdapter.mygameActivity=MainActivity.this;
        Subscriptio_Class.applicationContext=MainActivity.this;
        subscriptio_class=new Subscriptio_Class();
        //initPushNotification();
        //----------GCM & server Register-------------
        initDisplay();

        recycler_view_SFvideo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_SFvideo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.duration = shortFilmList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  shortFilmList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  shortFilmList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = shortFilmList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  shortFilmList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  shortFilmList.get(position).getPhysical_file_name();
                VideoPreViewActivity.contentImg =  shortFilmList.get(position).getContent_image();
                VideoPreViewActivity.ZedID =  shortFilmList.get(position).getZeid();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_BANGLA_SHORT_FILM;
                VideoPreViewActivity.info = shortFilmList.get(position).getInfo();
                VideoPreViewActivity.total_views = shortFilmList.get(position).getTotalView();
                VideoPreViewActivity.total_like = shortFilmList.get(position).getTotalLike();
                VideoPreViewActivity.genre = shortFilmList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "sf";
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recycler_view_hdvideo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_hdvideo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = true;
                VideoPreViewActivity.duration = hdVideoList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  hdVideoList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  hdVideoList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = hdVideoList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  hdVideoList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  hdVideoList.get(position).getPhysical_file_name();
                VideoPreViewActivity.contentImg =  hdVideoList.get(position).getContent_image();
                VideoPreViewActivity.ZedID =  hdVideoList.get(position).getZeid();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_HD_VIDEO;
                VideoPreViewActivity.info = hdVideoList.get(position).getInfo();
                VideoPreViewActivity.total_views = hdVideoList.get(position).getTotalView();
                VideoPreViewActivity.total_like = hdVideoList.get(position).getTotalLike();
                VideoPreViewActivity.genre = hdVideoList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "hd";
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.duration = videoHomeList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  videoHomeList.get(position).getContentcode();
                VideoPreViewActivity.categoryCode =  videoHomeList.get(position).getCategory();
                VideoPreViewActivity.contentName = videoHomeList.get(position).getContentTile();
                VideoPreViewActivity.sContentType =  videoHomeList.get(position).getType();
                VideoPreViewActivity.sPhysicalFileName =  videoHomeList.get(position).getPhysicalfilename();
                VideoPreViewActivity.contentImg =  videoHomeList.get(position).getImageUrl();
                VideoPreViewActivity.ZedID =  videoHomeList.get(position).getZid();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_VIDEO_URL;
                VideoPreViewActivity.info = videoHomeList.get(position).getInfo();
                VideoPreViewActivity.total_views = videoHomeList.get(position).getTotal_views();
                VideoPreViewActivity.total_like = videoHomeList.get(position).getTotal_like();
                VideoPreViewActivity.genre = videoHomeList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "video";
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerViewDrama.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewDrama, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.duration = dramaClipsHomeList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  dramaClipsHomeList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  dramaClipsHomeList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = dramaClipsHomeList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  dramaClipsHomeList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  dramaClipsHomeList.get(position).getPhysical_name();
                VideoPreViewActivity.contentImg =  dramaClipsHomeList.get(position).getContent_image();
                VideoPreViewActivity.ZedID =  dramaClipsHomeList.get(position).getZeid();
                VideoPreViewActivity.info = dramaClipsHomeList.get(position).getInfo();
                VideoPreViewActivity.total_like = dramaClipsHomeList.get(position).getTotal_like();
                VideoPreViewActivity.total_views = dramaClipsHomeList.get(position).getTotal_views();
                VideoPreViewActivity.genre = dramaClipsHomeList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "drama";
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_DRAMA;
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerviewFullVideo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerviewFullVideo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                VideoPreViewActivity.isHd = false;
                VideoPreViewActivity.duration = fullVideoClassList.get(position).getDuration();
                VideoPreViewActivity.contentCode =  fullVideoClassList.get(position).getContent_code();
                VideoPreViewActivity.categoryCode =  fullVideoClassList.get(position).getCategory_code();
                VideoPreViewActivity.contentName = fullVideoClassList.get(position).getContent_name();
                VideoPreViewActivity.sContentType =  fullVideoClassList.get(position).getContent_type();
                VideoPreViewActivity.sPhysicalFileName =  fullVideoClassList.get(position).getPhysical_file_name();
                VideoPreViewActivity.ZedID = fullVideoClassList.get(position).getZeid();
                VideoPreViewActivity.contentImg =  fullVideoClassList.get(position).getContent_image();
                VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_FULL_VIDEO;
                VideoPreViewActivity.info = fullVideoClassList.get(position).getInfo();
                VideoPreViewActivity.total_like = fullVideoClassList.get(position).getTotalLike();
                VideoPreViewActivity.total_views = fullVideoClassList.get(position).getTotalView();
                VideoPreViewActivity.genre = fullVideoClassList.get(position).getGenre();
                VideoPreViewActivity.TYPE = "fullVideo";
                Subscriptio_Class.type="video";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerViewMusic.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewMusic, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PlaySongActivity.contentCode = musicClassList.get(position).getContent_code();
                PlaySongActivity.categoryCode = musicClassList.get(position).getCategory_code();
                PlaySongActivity.contentName = musicClassList.get(position).getCintent_title();
                PlaySongActivity.sContentType = musicClassList.get(position).getType();
                PlaySongActivity.sPhysicalFileName = musicClassList.get(position).getPhysicalFileName();
                PlaySongActivity.contentImg = musicClassList.get(position).getImageUrl();
                PlaySongActivity.ZedID = musicClassList.get(position).getZID();
                PlaySongActivity.related_song = Config.URL_MUSIC;
                PlaySongActivity.likes = musicClassList.get(position).getLikes();
                PlaySongActivity.views = musicClassList.get(position).getViews();
                PlaySongActivity.SONG_TYPE = "music";
                Subscriptio_Class.type = "song";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerViewPicture.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewPicture, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                PictureDetailsActivity.contentCode = pictureList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = pictureList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = pictureList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = pictureList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = pictureList.get(position).getPhysicalFileName();
                PictureDetailsActivity.contentImg = pictureList.get(position).getImageUrl();
                PictureDetailsActivity.ZedID = pictureList.get(position).getZID();
                PictureDetailsActivity.image= pictureList.get(position).getImageUrl();
                PictureDetailsActivity.likes = pictureList.get(position).getLikes();
                PictureDetailsActivity.total_downloads = pictureList.get(position).getDownloads();
                PictureDetailsActivity.PIC_TYPE = "pic";
                PictureDetailsActivity.related_pic = Config.URL_PICTURE;
                Subscriptio_Class.type="pic";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerViewSticker.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewSticker, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                StickerPreview.contentCode = stickerList.get(position).getContent_code();
                StickerPreview.categoryCode = stickerList.get(position).getCategory_code();
                StickerPreview.contentName = stickerList.get(position).getContent_title();
                StickerPreview.sContentType = stickerList.get(position).getType();
                StickerPreview.sPhysicalFileName = stickerList.get(position).getPhysicalFileName();
                StickerPreview.contentImg = stickerList.get(position).getImageUrl();
                StickerPreview.ZedID = stickerList.get(position).getZID();
                StickerPreview.image = stickerList.get(position).getImageUrl();
                StickerPreview.total_likes = stickerList.get(position).getLikes();
                StickerPreview.total_downloads = stickerList.get(position).getDownloads();
                StickerPreview.STICKER_TYPE = "st";
                StickerPreview.related_pic_url = Config.URL_STICKER;
                Subscriptio_Class.type = "mpic";
                subscriptio_class.detectMSISDN();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerViewGame.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewGame, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                PictureDetailsActivity.contentCode = gameList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = gameList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = gameList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = gameList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = gameList.get(position).getPhysicalFileName();
                PictureDetailsActivity.contentImg = gameList.get(position).getImageUrl();
                PictureDetailsActivity.ZedID = gameList.get(position).getZid();
                PictureDetailsActivity.image = gameList.get(position).getImageUrl();
                PictureDetailsActivity.related_pic = Config.URL_GAME;
                PictureDetailsActivity.likes = gameList.get(position).getLikes();
                PictureDetailsActivity.total_downloads = gameList.get(position).getDownload();
                PictureDetailsActivity.PIC_TYPE = "game";
                Subscriptio_Class.type = "game";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));

        //Set the action bar image
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.bdtube_icon);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_nav);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.header));
        getSupportActionBar().setTitle("");


        //----------Check update --------------
        String string = "http://www.vumobile.biz/clubz_android/clubz_version.txt";
        CheckUpdate(string);



        //---Header & Banner image set-------------
        //imageLoader =AppController.getInstance().getImageLoader();
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
//        homePageHeaderImage = (NetworkImageView) findViewById(R.id.homePageHeaderImage);
//        homePageBannerImage = (NetworkImageView) findViewById(R.id.homePageBannerImage);

        //homePageHeaderImage .setImageUrl("http://202.164.213.242/CMS/UIHeader/czapp/D480x800/clubz.jpg",imageLoader);



        //-----------Set the category on home page---------------




        /*pictureOneTitle.setText(contentNameArray[8]);
        pictureTwoTitle.setText(contentNameArray[9]);
*/

        //----------Set the click listener for more TextView------------



        //Code for main page slider;



        try {

            Intent serviceIntent = new Intent(MainActivity.this, NetworkedService.class);
            startService(serviceIntent);
            Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 90 * 1000, pendingIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseShortFilm(String urlBanglaShortFilm) {
        JsonArrayRequest request = new JsonArrayRequest(urlBanglaShortFilm, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("Responsee", jsonArray.toString());
                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        shortFilm = new ShortFilm();

                        shortFilm.setContent_code(obj.getString(Config.CONTENT_CODE_BANGLA_MUSIC_VIDEO));
                        shortFilm.setCategory_code(obj.getString(Config.CATEGORY_CODE_BANGLA_MUSIC_VIDEO));
                        shortFilm.setContent_name(obj.getString(Config.CONTENT_NAME_BANGLA_MUSIC_VIDEO));
                        shortFilm.setContent_type(obj.getString(Config.CONTENT_TYPE_BANGLA_MUSIC_VIDEO));
                        shortFilm.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_BANGLA_MUSIC_VIDEO));
                        shortFilm.setZeid(obj.getString(Config.ZEID_BANGLA_MUSIC_VIDEO));
                        String imgUrl = obj.getString(Config.CONTENT_IMAGE_BANGLA_MUSIC_VIDEO).replace(" ","%20");
                        shortFilm.setContent_image(Config.URL_SOURCE+imgUrl);
                        shortFilm.setInfo(obj.getString(Config.INFO_HD_VIDEO));
                        shortFilm.setDuration(obj.getString(Config.DURATION_HD_VIDEO));
                        shortFilm.setGenre(obj.getString(Config.GENRE_HD_VIDEO));
                        shortFilm.setTotalLike(obj.getString(Config.TOTAL_LIKE_HD_VIDEO));
                        shortFilm.setTotalView(obj.getString(Config.TOTAL_VIEWS_HD_VIDEO));

                        shortFilmList.add(shortFilm);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recycler_view_SFvideo.setAdapter(adapterSf);
                adapterSf.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void initRecyclerShortFilm() {
        adapterSf = new ShortFilmAdapter(MainActivity.this,shortFilmList);
        recycler_view_SFvideo = (RecyclerView) findViewById(R.id.recycler_view_SFvideo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_SFvideo.setLayoutManager(mLayoutManager);
        recycler_view_SFvideo.setItemAnimator(new DefaultItemAnimator());
        recycler_view_SFvideo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void parseHDVideo(String urlHdVideo) {
        JsonArrayRequest request = new JsonArrayRequest(urlHdVideo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("Responsee", jsonArray.toString());
                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        hdVideo = new HDVideo();

                        hdVideo.setContent_code(obj.getString(Config.CONTENT_CODE_HD_VIDEO));
                        hdVideo.setCategory_code(obj.getString(Config.CATEGORY_CODE_HD_VIDEO));
                        hdVideo.setContent_name(obj.getString(Config.CONTENT_NAME_HD_VIDEO));
                        hdVideo.setContent_type(obj.getString(Config.CONTENT_TYPE_HD_VIDEO));
                        hdVideo.setPhysical_file_name(obj.getString(Config.PHYSICALNAME_HD_VIDEO));
                        hdVideo.setZeid(obj.getString(Config.ZEID_HD_VIDEO));
                        String imgUrl = obj.getString(Config.CONTENT_IMAGE_HD_VIDEO).replace(" ","%20");
                        hdVideo.setContent_image(imgUrl);
                        hdVideo.setInfo(obj.getString(Config.INFO_HD_VIDEO));
                        hdVideo.setDuration(obj.getString(Config.DURATION_HD_VIDEO));
                        hdVideo.setGenre(obj.getString(Config.GENRE_HD_VIDEO));
                        hdVideo.setTotalLike(obj.getString(Config.TOTAL_LIKE_HD_VIDEO));
                        hdVideo.setTotalView(obj.getString(Config.TOTAL_VIEWS_HD_VIDEO));

                        hdVideoList.add(hdVideo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recycler_view_hdvideo.setAdapter(hdVideoAdapter);
                hdVideoAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void initHdVideoRecycler() {
        hdVideoAdapter = new HDVideoAdapter(MainActivity.this,hdVideoList);
        recycler_view_hdvideo = (RecyclerView) findViewById(R.id.recycler_view_hdvideo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recycler_view_hdvideo.setLayoutManager(mLayoutManager);
        recycler_view_hdvideo.setItemAnimator(new DefaultItemAnimator());
        recycler_view_hdvideo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void askUserRuntimePermission() {

    }


    private void initSliderImage() {
        mContext = this;
        mViewFlipper = (SliderLayout) this.findViewById(R.id.view_flipper);

        JsonArrayRequest request = new JsonArrayRequest(Config.URL_SLIDER, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int j=0; j<=8; j++){
                    try {
                        sliderClass = new SliderClass();
                        JSONObject obj = response.getJSONObject(j);
                        String url = obj.getString(Config.SLIDER_IMAGE_URL).replaceAll(" ","%20");
                        sliderClass.setContent_code(obj.getString(Config.URL_SLIDER_content_code));
                        sliderClass.setCate_gory_code(obj.getString(Config.URL_SLIDER_category_code));
                        sliderClass.setContent_title(obj.getString(Config.URL_SLIDER_content_title));
                        sliderClass.setType(obj.getString(Config.URL_SLIDER_type));
                        sliderClass.setPhysical_file_name(obj.getString(Config.URL_SLIDER_physicalfilename));
                        sliderClass.setZid(obj.getString(Config.URL_SLIDER_zid));
                        sliderClass.setPath(obj.getString(Config.URL_SLIDER_path));
                        sliderClass.setImage_url(obj.getString(Config.SLIDER_IMAGE_URL).replaceAll(" ","%20"));
                        sliderClass.setTime_stamp(obj.getString(Config.URL_SLIDER_time_stamp));
                        sliderClass.setLive_date(obj.getString(Config.URL_SLIDER_live_date));
                        sliderClass.setExpire_date(obj.getString(Config.URL_SLIDER_live_date));
                        sliderClass.setInfo(obj.getString("info"));
                        sliderClass.setGenre(obj.getString("genre"));
                        sliderClass.setTotal_like(obj.getString("totalLike"));
                        sliderClass.setTotal_view(obj.getString("totalView"));


                        sliderClasses.add(sliderClass);
                        //String name = obj.getString("ContentTile");
                        setImageInFlipr(url,sliderClass.getContent_code(),
                                sliderClass.getCate_gory_code(),sliderClass.getContent_title(),
                                sliderClass.getType(),sliderClass.getPhysical_file_name(),
                                sliderClass.getZid(),sliderClass.getPath(),sliderClass.getImage_url(),sliderClass.getInfo(),
                                sliderClass.getGenre(),sliderClass.getTotal_like(),sliderClass.getTotal_view());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                mViewFlipper.setPresetTransformer(SliderLayout.Transformer.Accordion);
                mViewFlipper.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                //mViewFlipper.setCustomAnimation(new DescriptionAnimation());
                mViewFlipper.setDuration(3000);
                mViewFlipper.addOnPageChangeListener(MainActivity.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        //Adding request to the queue
        requestQueue.add(request);

    }
    private void setImageInFlipr(String imgUrl,String contentCode,String catGory,String content_title,String type,String physicalName,
                                 String ZID, String Path, String urlImage,String info, String genre, String total_like, String total_view) {

        TextSliderView textSliderView = new TextSliderView(this);
        // initialize a SliderLayout
        textSliderView
                .image(imgUrl)
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this);

        //add your extra information
        textSliderView.bundle(new Bundle());
        textSliderView.getBundle()
                .putString("content_code",contentCode);
        textSliderView.getBundle()
                .putString("catgory_code",catGory);
        textSliderView.getBundle()
                .putString("content_title",content_title);
        textSliderView.getBundle()
                .putString("type",type);
        textSliderView.getBundle()
                .putString("physical_name",physicalName);
        textSliderView.getBundle()
                .putString("zid",ZID);
        textSliderView.getBundle()
                .putString("path",Path);
        textSliderView.getBundle()
                .putString("url",urlImage);
        textSliderView.getBundle()
                .putString("info",info);
        textSliderView.getBundle()
                .putString("genre",genre);
        textSliderView.getBundle()
                .putString("like",total_like);
        textSliderView.getBundle()
                .putString("view",total_view);

        mViewFlipper.addSlider(textSliderView);
    }

    private void initRecycler() {
        adapter = new PrimaryAdapter(MainActivity.this,videoHomeList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_primary);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initDramaRecycler() {

        adapterDrama = new DramaAdapterClass(MainActivity.this,dramaClipsHomeList);
        recyclerViewDrama = (RecyclerView) findViewById(R.id.recycler_view_drama);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDrama.setLayoutManager(mLayoutManager);
        recyclerViewDrama.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDrama.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initFullVideoRecycler() {

        recyclerFullVdoAdapter = new FullVideoAdapter(MainActivity.this,fullVideoClassList);
        recyclerviewFullVideo = (RecyclerView) findViewById(R.id.recycler_view_fullVdo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerviewFullVideo.setLayoutManager(mLayoutManager);
        recyclerviewFullVideo.setItemAnimator(new DefaultItemAnimator());
        recyclerviewFullVideo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initMusicRecycler() {

        musicAdapter = new MusicAdapter(MainActivity.this,musicClassList);
        recyclerViewMusic = (RecyclerView) findViewById(R.id.recycler_view_music);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMusic.setLayoutManager(mLayoutManager);
        recyclerViewMusic.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMusic.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initGameRecycler() {
        gameAdapter = new GamesAdapter(MainActivity.this,gameList);
        recyclerViewGame = (RecyclerView) findViewById(R.id.recycler_view_game);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewGame.setLayoutManager(mLayoutManager);
        recyclerViewGame.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGame.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initPictureRecycler() {
        pictureAdapter = new PictureAdapter(MainActivity.this,pictureList);
        recyclerViewPicture = (RecyclerView) findViewById(R.id.recycler_view_picture);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPicture.setLayoutManager(mLayoutManager);
        recyclerViewPicture.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPicture.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initStickerRecyler() {
        stickerAdapter = new StickerAdapter(MainActivity.this,stickerList);
        recyclerViewSticker = (RecyclerView) findViewById(R.id.recycler_view_sticker);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSticker.setLayoutManager(mLayoutManager);
        recyclerViewSticker.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSticker.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }



    @Override
    public void onBackPressed(){

        this.finish();
        System.exit(0);
        super.onBackPressed();

    }


    //-----Checking either the app is going background or not--------------
    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    public void initDisplay() {

        DisplayMetrics dms = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dms);

        model = Build.MODEL;
        manufacture = Build.MANUFACTURER;
        brand = Build.BRAND; // like SEMC
        dimWidth = dms.widthPixels;
        dimHeight = dms.heightPixels;

        //Test add by Faisal



        Log.e("MODEL ", "" + model + manufacture + brand + " " + dimWidth + "x"
                + dimHeight);

        String possibleEmail = null;

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccountsByType(
                "com.google");
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;
                Log.i("MY_EMAIL_count", "" + possibleEmail);
            }
        }

        Log.i("MY_EMAIL", "" + possibleEmail);

        // //////////////////////////////////////
        // / Push Notification Method ////////////
        final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
                // Waking up mobile if it is sleeping
                WakeLocker.acquire(getApplicationContext());
                WakeLocker.release();
            }
        };
        // ///////////////////////// Start Push Notification
        // Intitialization///////////////////////




        try {
            MainActivity homeActivity=new MainActivity();

            // Getting handset brand name, email
            name = brand;
            email = possibleEmail;
            //email = "";
            // Make sure the device has the proper dependencies.
            GCMRegistrar.checkDevice(this);

            // Make sure the manifest was properly set - comment out this line
            // while developing the app, then uncomment it when it's ready.
            GCMRegistrar.checkManifest(this);



            registerReceiver(mHandleMessageReceiver, new IntentFilter(
                    DISPLAY_MESSAGE_ACTION));





            // Get GCM registration id
            final String regId = GCMRegistrar.getRegistrationId(this);
            Log.i("REG_ID", "" + regId);



               /* ServerUtilities.register(context, name, model,
                email, regId);*/
            // edited by Faisal
            // Check if regid already presents
            if (regId.equals("")) {
                // Registration is not present, register now with GCM
                GCMRegistrar.register(this, SENDER_ID);
            } else {
                // Device is already registered on GCM
                if (GCMRegistrar.isRegisteredOnServer(this)) {
                    // Skips registration.
                    // Toast.makeText(getApplicationContext(),
                    // "Already registered with GCM", Toast.LENGTH_LONG).show();
                } else {
                    // Try to register again, but not in the UI thread.
                    // It's also necessary to cancel the thread onDestroy(),
                    // hence the use of AsyncTask instead of a raw thread.
                    final Context context = this;
                    mRegisterTask = new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                            // Register on our server
                            // On server creates a new user
                            ServerUtilities.register(context, name, model,
                                    email, regId);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            mRegisterTask = null;
                            Log.i(">>>MARA KHA<<<", "PMK");
                        }

                    };
                    mRegisterTask.execute(null, null, null);
                }
            }

        } catch (Exception e) {
            Log.i("GET_XCEP", "" + e.getMessage());
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.videoMore:
                intent = new Intent(MainActivity.this, VideoMoreActivity.class);
                startActivity(intent);
                break;
            case R.id.fullVdoMore:
                intent = new Intent(MainActivity.this, FullVideoGridviewActivity.class);
                startActivity(intent);
                break;
            case R.id.dramaMore:
                intent = new Intent(MainActivity.this,DramaGridActivity.class);
                startActivity(intent);
                break;
            case R.id.musicMore:
                Intent intents = new Intent(MainActivity.this, SongCategoryActivity.class);
                startActivity(intents);
                break;
            case R.id.gameMore:
                intent = new Intent(MainActivity.this, GameCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.SFvideoMore:
                intent = new Intent(MainActivity.this, ShortFilmGridActivity.class);
                startActivity(intent);
                break;
            case R.id.pictureMore:
                intent = new Intent(MainActivity.this, PictureCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.hdvideoMore:
                Log.d("LLLLLLLLLLLLL","click");
                intent = new Intent(MainActivity.this, HDCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.stickerMore:
                Intent intent = new Intent(MainActivity.this, StickerCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHelp :
                intent = new Intent(MainActivity.this, FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHome :
                intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnMyDownloads :
                intent = new Intent(MainActivity.this,MyDownLoadsActivity.class);
                startActivity(intent);
                break;

            case R.id.btnBuddyLink :
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy")));
                break;
            case R.id.btnBdTubeLink:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube")));
                break;
            case R.id.btnBanglaBeatsLink:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en")));
                break;
            case R.id.btnAmarStickerLink:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker")));
                break;
            case R.id.btnRateMeLink:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en")));
                break;

            case R.id.btnHelpSame:
                intent = new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
                break;


        }
    }

    //------------------initialize the components-------------------
    public void initUI(){
        loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);
        scrollView = (ScrollView) findViewById(R.id.scrollViews);

        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        btnSearching = (Button) findViewById(R.id.btnStartSearching);
        etSearch = (EditText) findViewById(R.id.etSearch);
        //-------------Section More Init-------------------
        txtvideoMore = (TextView) findViewById(R.id.videoMore);
        txtDramaMore = (TextView) findViewById(R.id.dramaMore);
        txtFullVdoMore = (TextView) findViewById(R.id.fullVdoMore);
        txthdvideoMore = (TextView) findViewById(R.id.hdvideoMore);
        shortMore = (TextView) findViewById(R.id.SFvideoMore);
        txtmusicMore = (TextView) findViewById(R.id.musicMore);
        txtgameMore = (TextView) findViewById(R.id.gameMore);
        txtpictureMore = (TextView) findViewById(R.id.pictureMore);
        txtstickerMore = (TextView) findViewById(R.id.stickerMore);

        btnHelp = (ImageView) findViewById(R.id.btnHelp);
        btnHome = (ImageView) findViewById(R.id.btnHome);
        btnMydownload = (ImageView) findViewById(R.id.btnMyDownloads);
        btnBuddy = (ImageView) findViewById(R.id.btnBuddyLink);
        btnBdTube = (ImageView) findViewById(R.id.btnBdTubeLink);
        btnBanglaBeats = (ImageView) findViewById(R.id.btnBanglaBeatsLink);
        btnAmarSticker = (ImageView) findViewById(R.id.btnAmarStickerLink);
        btnRateMe = (ImageView) findViewById(R.id.btnRateMeLink);
        btnHelpSame = (ImageView) findViewById(R.id.btnHelpSame);

        btnHelpSame.setOnClickListener(this);

        btnBuddy.setOnClickListener(this);
        btnBdTube.setOnClickListener(this);
        btnBanglaBeats.setOnClickListener(this);
        btnRateMe.setOnClickListener(this);
        btnAmarSticker.setOnClickListener(this);

        btnHome.setOnClickListener(this);
        shortMore.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        txtvideoMore.setOnClickListener(this);
        txtDramaMore.setOnClickListener(this);
        txtFullVdoMore.setOnClickListener(this);
        txtmusicMore.setOnClickListener(this);
        txtmusicMore.setOnClickListener(this);
        txtgameMore.setOnClickListener(this);
        txtpictureMore.setOnClickListener(this);
        txtstickerMore.setOnClickListener(this);
        btnMydownload.setOnClickListener(this);
        txthdvideoMore.setOnClickListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Log.d("ContentCode",slider.getBundle().get("catgory_code").toString());

        String type = slider.getBundle().get("type").toString();

        VideoPreViewActivity.contentCode =  slider.getBundle().get("content_code").toString();
        VideoPreViewActivity.categoryCode =  slider.getBundle().get("catgory_code").toString();
        VideoPreViewActivity.contentName = slider.getBundle().get("content_title").toString();
        VideoPreViewActivity.sContentType =  slider.getBundle().get("type").toString();
        VideoPreViewActivity.sPhysicalFileName =  slider.getBundle().get("physical_name").toString();
        VideoPreViewActivity.ZedID = slider.getBundle().get("zid").toString();
        VideoPreViewActivity.contentImg =  slider.getBundle().get("url").toString();
        VideoPreViewActivity.URL_RELATED_VIDEO = Config.URL_FULL_VIDEO;
        VideoPreViewActivity.genre = slider.getBundle().getString("genre");
        VideoPreViewActivity.total_like = slider.getBundle().getString("like");
        VideoPreViewActivity.total_views = slider.getBundle().getString("view");
        VideoPreViewActivity.TYPE = "fullVideo";
        VideoPreViewActivity.info = slider.getBundle().getString("info");
        Subscriptio_Class.type="video";
        subscriptio_class.detectMSISDN();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

//        String title = sliderClasses.get(position).getContent_title();
//        Toast.makeText(getApplicationContext(),title,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
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

                            if (!versionName.isEmpty()) {

                                try {
                                    if (!versionName.equalsIgnoreCase(UpdateString
                                            .toString().trim())) {
                                        showUpdateDialog = true;
                                        Update();

                                    }
                                }catch (NullPointerException e){
                                    e.printStackTrace();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Can not fetch version", Toast.LENGTH_SHORT).show();
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


    public void Update(){


        final Dialog updateDialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);

        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        updateDialog.setContentView(R.layout.update_dialog_activity);
        TextView update_text = (TextView) updateDialog
                .findViewById(R.id.update_text);
        update_text.setText(updateReasonString);
        Button update_app = (Button) updateDialog.findViewById(R.id.update_app);
        ImageView img = (ImageView) updateDialog.findViewById(R.id.imageView1);
        Log.i("Tracker", " The Update dialog is called");

        update_app.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                updateDialog.dismiss();

                /**
                 * if this button is clicked, close current activity
                 */
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri
                            .parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + appPackageName)));
                }
                // Post the Update Log
                try {

                    String emailID=getMailId();
                    String updateLogString="http://203.76.126.210/shaboxbuddy/All_AppUpdateLog.php?Email="+emailID+
                            "&MNO="+AppConstant.mno+"&AppName=CZ&AppVersion="+UpdateString.toString().trim();
                    WebView updateWebView=new WebView(MainActivity.this);
                    updateWebView.loadUrl(updateLogString);
                } catch (Exception e) {
                    e.printStackTrace();
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


    public String getMailId() {
        String strGmail = null;
        try {
            Account[] accounts = AccountManager.get(this).getAccounts();
            Log.e("PIKLOG", "Size: " + accounts.length);
            for (Account account : accounts) {

                String possibleEmail = account.name;
                String type = account.type;

                if (type.equals("com.google")) {

                    strGmail = possibleEmail;
                    Log.e("PIKLOG", "Emails: " + strGmail);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strGmail;
    }


    /**
     * Push Notification Method
     */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            /**
             * Waking up mobile if it is sleeping
             */
            WakeLocker.acquire(getApplicationContext());
            WakeLocker.release();
        }
    };

    private void parseDramaApi(String url){

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        dramaClips = new DramaClipsHome();

                        dramaClips.setContent_code(obj.getString(Config.CONTENT_CODE_DRAMA));
                        dramaClips.setCategory_code(obj.getString(Config.CATEGORY_CODE_DRAMA));
                        dramaClips.setContent_name(obj.getString(Config.CONTENT_NAME_DRAMA));
                        dramaClips.setContent_type(obj.getString(Config.CONTENT_TYPE_DRAMA));
                        dramaClips.setPhysical_name(obj.getString(Config.PHYSICALFILENAME_DRAMA));
                        dramaClips.setDuration(obj.getString(Config.DURATION_FULL_VIDEO));
                        dramaClips.setZeid(obj.getString(Config.ZEID_DRAMA));
                        dramaClips.setInfo(obj.getString(Config.INFO_FULL_DRAMA));
                        dramaClips.setTotal_views(obj.getString(Config.TOTAL_VIEWS_FULL_DRAMA));
                        dramaClips.setTotal_like(obj.getString(Config.TOTAL_LIKE_FULL_DRAMA));
                        dramaClips.setGenre(obj.getString(Config.GENRE_FULL_DRAMA));
                        String imgUrl = obj.getString(Config.CONTENT_IMAGE_DRAMA).replace(" ","%20");
                        dramaClips.setContent_image(Config.URL_SOURCE+imgUrl);

                        dramaClipsHomeList.add(dramaClips);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclerViewDrama.setAdapter(adapterDrama);
                adapterDrama.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseDataVideo(String urlVideoUrl) {

        JsonArrayRequest request = new JsonArrayRequest(urlVideoUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //uncomment this for checking update or not

                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = response.getJSONObject(i);
                        videoHome = new VideoHome();
                        videoHome.setLiveDate(obj.getString(Config.LIVE_DATE));
                        videoHome.setContentcode(obj.getString(Config.CONTENT_CODE_VIDEO));
                        videoHome.setCategory(obj.getString(Config.CATEGORY_CODE));
                        videoHome.setContentTile(obj.getString(Config.CONTENT_TITLE));
                        videoHome.setType(obj.getString(Config.TYPE));
                        videoHome.setPhysicalfilename(obj.getString(Config.PHYSICAL_FILE_NAME_VIDEO));
                        videoHome.setZid(obj.getString(Config.ZID));
                        String imageUrl = obj.getString(Config.IMAGE_URL).replaceAll(" ","%20");
                        videoHome.setImageUrl(imageUrl);
                        videoHome.setDuration(obj.getString(Config.DURATION_VIDEO));
                        videoHome.setInfo(obj.getString(Config.INFO_VIDEO));
                        videoHome.setTotal_like(obj.getString(Config.TOTAL_LIKE_VIDEO));
                        videoHome.setTotal_views(obj.getString(Config.TOTAL_VIEWS_VIDEO));
                        videoHome.setGenre(obj.getString(Config.GENRE_VIDEO));
                        videoHomeList.add(videoHome);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseFullVideo(String urlFullVideo) {

        JsonArrayRequest request = new JsonArrayRequest(urlFullVideo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        fullVideoClass = new FullVideoClass();

                        fullVideoClass.setContent_code(obj.getString(Config.CONTENT_CODE_FULL_VIDEO));
                        fullVideoClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_FULL_VDO));
                        fullVideoClass.setContent_name(obj.getString(Config.CONTENT_NAME_FULL_VIDEO));
                        fullVideoClass.setContent_type(obj.getString(Config.CONTENT_TYPE_FULL_VIDEO));
                        fullVideoClass.setPhysical_file_name(obj.getString(Config.PHYSICAL_FILE_NAME_FULL_VIDEO));
                        fullVideoClass.setZeid(obj.getString(Config.ZED_ID_FULL_VIDEO));
                        String imgUrl = obj.getString(Config.URL_IMAGE_FULL_VDO).replace(" ","%20");
                        fullVideoClass.setContent_image(Config.URL_SOURCE+imgUrl);
//                        fullVideoClass.setTime_stamp(obj.getString(Config.TIME_STAMP_FULL_VDO));
//                        fullVideoClass.setLive_date(obj.getString(Config.LIVE_DATA_FULL_VDO));
//                        fullVideoClass.setRow_num(obj.getString(Config.ROWNUM_FULL_VDO));
                        fullVideoClass.setInfo(obj.getString(Config.INFO_FULL_VIDEO));
                        fullVideoClass.setDuration(obj.getString(Config.DURATION_FULL_VIDEO));
                        fullVideoClass.setGenre(obj.getString(Config.GENRE_FULL_VIDEO));
                        fullVideoClass.setTotalLike(obj.getString(Config.TOTAL_LIKE_FULL_VIDEO));
                        fullVideoClass.setTotalView(obj.getString(Config.TOTAL_VIEWS_FULL_VIDEO));

                        fullVideoClassList.add(fullVideoClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclerviewFullVideo.setAdapter(recyclerFullVdoAdapter);
                recyclerFullVdoAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void parseMusic(String urlMusic) {

        JsonArrayRequest request = new JsonArrayRequest(urlMusic, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {



                for (int i=0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        musicClass = new MusicClass();
                        musicClass.setContent_title(obj.getString(Config.CONTENT_TITLE_MUSIC));
                        musicClass.setLive_date(obj.getString(Config.LIVE_DATE_MUSIC));
                        musicClass.setContent_code(obj.getString(Config.CONTENT_CODE_MUSIC));
                        musicClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_MUSIC));
                        musicClass.setType(obj.getString(Config.TYPE_MUSIC));
                        musicClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_MUSIC));
                        musicClass.setZID(obj.getString(Config.ZID_MUSIC));
                        musicClass.setPath(obj.getString(Config.PATH_MUSIC));
                        musicClass.setImageUrl(obj.getString(Config.IMAGE_URL_MUSIC));
                        musicClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        musicClass.setViews(obj.getString(Config.VIEWS));
                        //musicClass.setDuration(obj.getString(Config.DURATION_FULL_VIDEO));

                        musicClassList.add(musicClass);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerViewMusic.setAdapter(musicAdapter);
                musicAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }


    private void parseGame(String urlGame) {

        JsonArrayRequest request = new JsonArrayRequest(urlGame, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        gameClass = new GameClass();
                        gameClass.setLive_date(obj.getString(Config.LIVEDATE_GAME));
                        gameClass.setContent_code(obj.getString(Config.CONTENT_CODE_GAME));
                        gameClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_GAME));
                        gameClass.setContent_title(obj.getString(Config.CONTENT_TIILE_GAME));
                        gameClass.setType(obj.getString(Config.TYPE_GAME));
                        gameClass.setPhysicalFileName(obj.getString(Config.PHYSICAL_FILE_GAME));
                        gameClass.setZid(obj.getString(Config.ZID_GAME));
                        gameClass.setPath(obj.getString(Config.PATH_GAME));
                        gameClass.setImageUrl(obj.getString(Config.IMAGE_URL_GAME));
                        gameClass.setBigImageUrl(obj.getString(Config.BIGIMAGE_GAME));
                        gameClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                        gameClass.setDownload(obj.getString(Config.TOTAL_DOWNLOADS));

                        gameList.add(gameClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerViewGame.setAdapter(gameAdapter);
                gameAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

    private void parsePicture(String urlPicture) {

        JsonArrayRequest request = new JsonArrayRequest(urlPicture, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                loading.dismiss();

                for (int i = 0; i<9; i++){
                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        pictureClass = new PictureClass();

                        pictureClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE));
                        pictureClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE));
                        pictureClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE));
                        pictureClass.setType(obj.getString(Config.TYPE_PICTURE));
                        pictureClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_PICTURE));
                        pictureClass.setZID(obj.getString(Config.ZID_PICTURE));
                        pictureClass.setPath(obj.getString(Config.PATH_PICTURE));
                        pictureClass.setLikes(obj.getString(Config.TOTAL_LIKE_PIC));
                        String imgUrl = obj.getString(Config.IMAGE_URL_PICTURE).replace(" ","%20");
                        pictureClass.setImageUrl(imgUrl);
                        pictureClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));


                        pictureList.add(pictureClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclerViewPicture.setAdapter(pictureAdapter);
                pictureAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);

    }

    private void parseSticker(String urlSticker) {

        JsonArrayRequest request = new JsonArrayRequest(urlSticker, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i<9; i++){


                    try {

                        JSONObject obj = jsonArray.getJSONObject(i);
                        stickerClass = new StickerClass();

                        stickerClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER));
                        stickerClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_STICKER));
                        stickerClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER));
                        stickerClass.setType(obj.getString(Config.TYPE_STICKER));
                        stickerClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER));
                        stickerClass.setZID(obj.getString(Config.ZID_STICKER));
                        stickerClass.setPath(obj.getString(Config.PATH_STICKER));
                        stickerClass.setLikes(obj.getString(Config.STICKER_LIKE));
                        stickerClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                        String imgUrl = obj.getString(Config.IMAGE_URL_STICKER).replace(" ","%20");
                        stickerClass.setImageUrl(imgUrl);


                        stickerList.add(stickerClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                recyclerViewSticker.setAdapter(stickerAdapter);
                stickerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection error!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
    }

//    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            try {
//                // right to left swipe
//                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
//                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
//                    mViewFlipper.showNext();
//                    return true;
//                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
//                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
//                    mViewFlipper.showPrevious();
//                    return true;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return false;
//        }
//    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnSearch:
                searchLayout.setVisibility(View.VISIBLE);
                scrollView.fullScroll(View.FOCUS_UP);
                item.setVisible(false);
                btnSearching.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setVisible(true);
                        final String searchContent = etSearch.getText().toString();
                        searchLayout.setVisibility(View.GONE);
                        hideKeyBoard();
                        etSearch.setText("");

                        if (searchContent.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Empty search box!",Toast.LENGTH_LONG).show();
                        }else {
                            Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
                            intent.putExtra("search_grid",searchContent);
                            startActivity(intent);
                        }
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    class BackGroundTask extends AsyncTask<Void,Void,Void>{
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            //parse video api
//            parseDataVideo(Config.URL_VIDEO_URL);
//            //parse drama api
//            parseDramaApi(Config.URL_DRAMA);
//            //parse full video api
//            parseFullVideo(Config.URL_FULL_VIDEO);
//            //parse music api
//            parseMusic(Config.URL_MUSIC);
//            //parse game api
//            parseGame(Config.URL_GAME);
//            // parse picture api
//            parsePicture(Config.URL_PICTURE);
//            //parse sticker api
//            parseSticker(Config.URL_STICKER);
//            return null;
//        }
    //}

    public void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mViewFlipper.stopAutoCycle();
        super.onStop();
    }
}
