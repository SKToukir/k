package com.vumobile.clubzed.Picture_Sticker_Related;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.vumobile.clubzed.AppConstant;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.RelatedStickerAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.GameActionClass;
import com.vumobile.clubzed.model.GameAracadeClass;
import com.vumobile.clubzed.model.GameClass;
import com.vumobile.clubzed.model.GamePuzzleClass;
import com.vumobile.clubzed.model.GameRacingClass;
import com.vumobile.clubzed.model.GameSportsClass;
import com.vumobile.clubzed.model.PictureBangladeshiCele;
import com.vumobile.clubzed.model.PictureBollywoodCeleClass;
import com.vumobile.clubzed.model.PictureClass;
import com.vumobile.clubzed.model.PictureCoolClass;
import com.vumobile.clubzed.model.PictureHollywoodCelebratyClass;
import com.vumobile.clubzed.model.PictureLoveClass;
import com.vumobile.clubzed.model.PictureSpecialEventClass;
import com.vumobile.clubzed.model.StickerRelatedClass;
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

public class PictureDetailsActivity extends ActionBarActivity {

    int num = 6;

    private PullToRefreshScrollView mPullRefreshScrollView;
    ScrollView picLayoutScroll;

    GameClass gameClass;
    GameActionClass gameActionClass;
    GameSportsClass gameSportsClass;
    GamePuzzleClass gamePuzzleClass;
    GameRacingClass gameRacingClass;
    GameAracadeClass gameAracadeClass ;
    PictureLoveClass pictureLoveClass;
    PictureCoolClass pictureCoolClass;
    PictureSpecialEventClass pictureSpecialEventClass;
    PictureBangladeshiCele pictureBangladeshiCele;
    PictureHollywoodCelebratyClass hollywoodCelebratyClass;
    PictureBollywoodCeleClass pictureBollywoodCeleClass;
    ProgressDialog loading;
    StickerRelatedClass relatedClass;
    List<GameActionClass> gameActionClassList = new ArrayList<GameActionClass>();
    List<GameSportsClass> gameSportsClassList = new ArrayList<GameSportsClass>();
    List<GamePuzzleClass> gamePuzzleClassList = new ArrayList<GamePuzzleClass>();
    List<GameRacingClass> gameRacingClassList = new ArrayList<GameRacingClass>();
    List<GameAracadeClass> gameAracadeClassList = new ArrayList<GameAracadeClass>();
    List<GameClass> gameClassList = new ArrayList<GameClass>();
    List<PictureLoveClass> pictureLoveClassList = new ArrayList<PictureLoveClass>();
    List<PictureCoolClass> pictureCoolClassList = new ArrayList<PictureCoolClass>();
    List<PictureSpecialEventClass> EventClassList = new ArrayList<PictureSpecialEventClass>();
    List<PictureBangladeshiCele> pictureBangladeshiCeleList = new ArrayList<PictureBangladeshiCele>();
    List<PictureHollywoodCelebratyClass> pictureHollywoodCelebratyClassList = new ArrayList<PictureHollywoodCelebratyClass>();
    List<PictureBollywoodCeleClass> pictureBollywoodCeleClassList = new ArrayList<PictureBollywoodCeleClass>();
    List<StickerRelatedClass> stickerRelatedClassList = new ArrayList<StickerRelatedClass>();
    PictureClass pictureClass;
    List<PictureClass> pictureList = new ArrayList<PictureClass>();

    RecyclerView recyclerView;
    RelatedStickerAdapter adapter;

    Button btnLoadMorePic;
    String strs;
    private static final int ANIM_DURATION = 600;
    private TextView titleTextView,txtPicLikeCount;
    private NetworkImageView imageView;
    private ImageView grid_item_image_pic;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;

    private RelativeLayout frameLayout;
    private ColorDrawable colorDrawable;

    public ProgressDialog pDialog, mPDialog;

    Toolbar toolbar;
    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;
    TextView downloadTextView, picTitle, txtTotalDownload;
    public static String model;
    public static String manufacture;
    public static String brand;
    ImageButton shareImgeButton;
    ImageView ratingImageButton;
    Button btnBack;
    MainActivity mainActivity;
    public static String contentCode = "", categoryCode = "", contentName = "", sContentType = "",
            sPhysicalFileName = "", ZedID = "", contentImg = "", image = "", PIC_TYPE = "", related_pic = "",likes = "", total_downloads = "";
    public static int dimWidth, dimHeight;
    ImageLoader imageLoader;
    public String string = "http://www.vumobile.biz/clubz_android/clubz_version.txt";
    public String UpdateString;
    public static String updateReasonString = "";
    public static boolean downloadORsubscription;
    public static String rsltNumber = "";
    public static int doAction = 0;
    public PHPRequest phpRequest = new PHPRequest();
    Subscriptio_Class subscriptio_class;
    public String pushResponseUrl = "http://www.vumobile.biz/gcm_server_php/push_response.php";
    private ImageView imagHome;

    Download_Class download_Class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting details screen layout
        setContentView(R.layout.activity_details_view);


        imageLoader = AppController.getInstance().getImageLoader();

        mainActivity = new MainActivity();
        //----------Check update --------------
        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.picLayoutScroll);
        toolbar = (Toolbar) findViewById(R.id.tool_bar_pic);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);
        imagHome = (ImageView) toolbar.findViewById(R.id.btnHomeBar);

        imagHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // init related list recycler
        initRecycler();
        parseRelatedPic(related_pic);

        //---------------handset model dimension determine-----------
        DisplayMetrics dms = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dms);
        model = Build.MODEL;
        manufacture = Build.MANUFACTURER;
        brand = Build.BRAND; // like SEMC
        dimWidth = dms.widthPixels;
        dimHeight = dms.heightPixels;


        //Progress dialog
        pDialog = new ProgressDialog(getApplicationContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");

        Subscriptio_Class.applicationContext=PictureDetailsActivity.this;
        subscriptio_class=new Subscriptio_Class();
        //--------------Context------------
        Download_Class.applicationContext = PictureDetailsActivity.this;
        download_Class = new Download_Class();


        //----------Initialize the Text and button--------------
        downloadTextView = (TextView) findViewById(R.id.pic_detail_downloadText);
        shareImgeButton = (ImageButton) findViewById(R.id.pic_detail_shareButton);


        shareImgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "https://play.google.com/store/apps/details?id=com.vumobile.clubzed";

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ClubZ");

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

        //retrieves the thumbnail data

        try {


            String title = getIntent().getExtras().getString("title");
            image = getIntent().getExtras().getString("contentImg");
            contentCode = getIntent().getExtras().getString("contentCode");
            categoryCode = getIntent().getExtras().getString("categoryCode");
            contentName = getIntent().getExtras().getString("contentName")
                    .replace("_", " ");
            sContentType = getIntent().getExtras().getString("sContentType");
            sPhysicalFileName = getIntent().getExtras().getString("sPhysicalFileName");
            ZedID = getIntent().getExtras().getString("ZedID");
            contentImg = getIntent().getExtras().getString("contentImg");
            doAction = getIntent().getExtras().getInt("doAction");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("contentcodepicde", contentCode);
        Log.d("contentcodepicde", categoryCode);
        Log.d("contentcodepicde", contentName);
        Log.d("contentcodepicde", sContentType);
        Log.d("contentImage", contentImg);

        if (doAction == 2) {

            new SendLaunchPushResponse().execute();

        }


        //---------set the pic title--------------
        picTitle = (TextView) findViewById(R.id.picDetailsCategoryTitle);
        picTitle.setText(contentName.replace("_", " "));
        txtPicLikeCount = (TextView) findViewById(R.id.txtPicLikeCount);
        txtTotalDownload = (TextView) findViewById(R.id.txtTotalDownload);
        txtTotalDownload.setText(total_downloads);
//        picTitle.setSelected(true);

        txtPicLikeCount.setText(likes);


        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {


                pictureLoveClassList.clear();
                pictureCoolClassList.clear();
                EventClassList.clear();
                pictureBangladeshiCeleList.clear();
                pictureHollywoodCelebratyClassList.clear();
                pictureBollywoodCeleClassList.clear();
                stickerRelatedClassList.clear();
                pictureList.clear();
                gameClassList.clear();
                gameActionClassList.clear();
                gameSportsClassList.clear();
                gamePuzzleClassList.clear();
                gameRacingClassList.clear();
                gameAracadeClassList.clear();

                num+=5;

                parseRelatedPic(related_pic);
            }
        });
        picLayoutScroll = mPullRefreshScrollView.getRefreshableView();

//============pass the content information to download class==============
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


        //Set image url
        imageView = (NetworkImageView) findViewById(R.id.grid_item_image);
        grid_item_image_pic = (ImageView) findViewById(R.id.grid_item_image_pic);
       /* Picasso.with(this).load(image).into(imageView);*/

        //Set the background color to black
        frameLayout = (RelativeLayout) findViewById(R.id.main_background);
        colorDrawable = new ColorDrawable(Color.BLACK);

        Glide.with(this).load(image).into(grid_item_image_pic);
//
//        if (image.contains(".gif")){
//            imageView.setVisibility(View.GONE);
//            grid_item_image_pic.setVisibility(View.VISIBLE);
//            Glide.with(this).load(image).into(grid_item_image_pic);
//        }else {
//            imageView.setImageUrl(image, imageLoader);
//        }
        Log.d("picdetailimageurl", contentImg);

        downloadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckUpdate(string);

               /* mPDialog = new ProgressDialog(PictureDetailsActivity.this);
                Log.d("downloadbutton", "download button pressed");
                // Showing progress dialog before making http request
                mPDialog.setMessage("Loading...");
                mPDialog.show();*/
//                strs = "http://wap.shabox.mobi/CMS/Content/Graphics/WallPaper/D480x800/"+sPhysicalFileName+".jpg";
//                Download_Class.testUrl = strs;
                download_Class.detectMSISDN();
            }
        });

        recyclerView.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerView, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (PIC_TYPE.equals("pic")){
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

                }else if (PIC_TYPE.equals("bc")){

                    PictureDetailsActivity.contentCode = pictureBollywoodCeleClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = pictureBollywoodCeleClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = pictureBollywoodCeleClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = pictureBollywoodCeleClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = pictureBollywoodCeleClassList.get(position).getPhysicalFilwName();
                    PictureDetailsActivity.contentImg = pictureBollywoodCeleClassList.get(position).getImage_url();
                    PictureDetailsActivity.ZedID = pictureBollywoodCeleClassList.get(position).getZeid();
                    PictureDetailsActivity.image= pictureBollywoodCeleClassList.get(position).getImage_url();
                    PictureDetailsActivity.likes = pictureBollywoodCeleClassList.get(position).getLikes();
                    PictureDetailsActivity.related_pic = Config.URL_PICTURE_BOLLY_CELE;
                    PictureDetailsActivity.total_downloads = pictureBollywoodCeleClassList.get(position).getDownloads();
                    PictureDetailsActivity.PIC_TYPE = "bc";
                    Subscriptio_Class.type="pic";
                    subscriptio_class.detectMSISDN();


                }else if (PIC_TYPE.equals("hc")){

                    PictureDetailsActivity.contentCode = pictureHollywoodCelebratyClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = pictureHollywoodCelebratyClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = pictureHollywoodCelebratyClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = pictureHollywoodCelebratyClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = pictureHollywoodCelebratyClassList.get(position).getPhysicalFilwName();
                    PictureDetailsActivity.contentImg = pictureHollywoodCelebratyClassList.get(position).getImage_url();
                    PictureDetailsActivity.ZedID = pictureHollywoodCelebratyClassList.get(position).getZeid();
                    PictureDetailsActivity.image= pictureHollywoodCelebratyClassList.get(position).getImage_url();
                    PictureDetailsActivity.likes = pictureHollywoodCelebratyClassList.get(position).getLikes();
                    PictureDetailsActivity.total_downloads = pictureHollywoodCelebratyClassList.get(position).getDownloads();
                    PictureDetailsActivity.PIC_TYPE = "hc";
                    PictureDetailsActivity.related_pic = Config.URL_HOLLYWOOD_CELE;
                    Subscriptio_Class.type="pic";
                    subscriptio_class.detectMSISDN();


                }else if (PIC_TYPE.equals("bdc")){

                    PictureDetailsActivity.contentCode = pictureBangladeshiCeleList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = pictureBangladeshiCeleList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = pictureBangladeshiCeleList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = pictureBangladeshiCeleList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = pictureBangladeshiCeleList.get(position).getPhysicalFilwName();
                    PictureDetailsActivity.contentImg = pictureBangladeshiCeleList.get(position).getImage_url();
                    PictureDetailsActivity.ZedID = pictureBangladeshiCeleList.get(position).getZeid();
                    PictureDetailsActivity.image= pictureBangladeshiCeleList.get(position).getImage_url();
                    PictureDetailsActivity.likes = pictureBangladeshiCeleList.get(position).getLikes();
                    PictureDetailsActivity.total_downloads = pictureBangladeshiCeleList.get(position).getDownloads();
                    PictureDetailsActivity.PIC_TYPE = "bdc";
                    PictureDetailsActivity.related_pic = Config.URL_PICTURE_BD_CELEBRATY;
                    Subscriptio_Class.type="pic";
                    subscriptio_class.detectMSISDN();


                }else if (PIC_TYPE.equals("event")){

                    PictureDetailsActivity.contentCode = EventClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = EventClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = EventClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = EventClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = EventClassList.get(position).getPhysicalFilwName();
                    PictureDetailsActivity.contentImg = EventClassList.get(position).getImage_url();
                    PictureDetailsActivity.ZedID = EventClassList.get(position).getZeid();
                    PictureDetailsActivity.image= EventClassList.get(position).getImage_url();
                    PictureDetailsActivity.likes = EventClassList.get(position).getLikes();
                    PictureDetailsActivity.total_downloads = EventClassList.get(position).getDownloads();
                    PictureDetailsActivity.related_pic = Config.URL_PICTURE_SPECIAL_EVENT;
                    PictureDetailsActivity.PIC_TYPE = "event";
                    Subscriptio_Class.type="pic";
                    subscriptio_class.detectMSISDN();


                }else if (PIC_TYPE.equals("cool")){

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


                }else if (PIC_TYPE.equals("love")){

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

                }else if (PIC_TYPE.equals("game")){

                    PictureDetailsActivity.contentCode = gameClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = gameClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = gameClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = gameClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = gameClassList.get(position).getPhysicalFileName();
                    PictureDetailsActivity.contentImg = gameClassList.get(position).getImageUrl();
                    PictureDetailsActivity.ZedID = gameClassList.get(position).getZid();
                    PictureDetailsActivity.image = gameClassList.get(position).getImageUrl();
                    PictureDetailsActivity.total_downloads = gameClassList.get(position).getDownload();
                    PictureDetailsActivity.related_pic = Config.URL_GAME;
                    PictureDetailsActivity.likes = gameClassList.get(position).getLikes();
                    PictureDetailsActivity.PIC_TYPE = "game";
                    Subscriptio_Class.type = "game";
                    subscriptio_class.detectMSISDN();


                }else if (PIC_TYPE.equals("action")){
                    PictureDetailsActivity.contentCode = gameActionClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = gameActionClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = gameActionClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = gameActionClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = gameActionClassList.get(position).getPhysicalFileName();
                    PictureDetailsActivity.contentImg = gameActionClassList.get(position).getImageUrl();
                    PictureDetailsActivity.ZedID = gameActionClassList.get(position).getZeid();
                    PictureDetailsActivity.image = gameActionClassList.get(position).getImageUrl();
                    PictureDetailsActivity.total_downloads = gameActionClassList.get(position).getDownloads();
                    PictureDetailsActivity.related_pic = Config.URL_GAME_ACTION;
                    PictureDetailsActivity.PIC_TYPE = "action";
                    PictureDetailsActivity.likes = gameActionClassList.get(position).getLikes();
                    Subscriptio_Class.type = "game";
                    subscriptio_class.detectMSISDN();

                }else if (PIC_TYPE.equals("sports")){
                    PictureDetailsActivity.contentCode = gameSportsClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = gameSportsClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = gameSportsClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = gameSportsClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = gameSportsClassList.get(position).getPhysicalFileName();
                    PictureDetailsActivity.contentImg = gameSportsClassList.get(position).getImageUrl();
                    PictureDetailsActivity.ZedID = gameSportsClassList.get(position).getZeid();
                    PictureDetailsActivity.image = gameSportsClassList.get(position).getImageUrl();
                    PictureDetailsActivity.total_downloads = gameSportsClassList.get(position).getDownloads();
                    PictureDetailsActivity.related_pic = Config.URL_GAME_SPORTS;
                    PictureDetailsActivity.PIC_TYPE = "sports";
                    PictureDetailsActivity.likes = gameSportsClassList.get(position).getLikes();
                    Subscriptio_Class.type = "game";
                    subscriptio_class.detectMSISDN();

                }else if (PIC_TYPE.equals("puzzle")){
                    PictureDetailsActivity.contentCode = gamePuzzleClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = gamePuzzleClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = gamePuzzleClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = gamePuzzleClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = gamePuzzleClassList.get(position).getPhysicalFileName();
                    PictureDetailsActivity.contentImg = gamePuzzleClassList.get(position).getImageUrl();
                    PictureDetailsActivity.ZedID = gamePuzzleClassList.get(position).getZeid();
                    PictureDetailsActivity.image = gamePuzzleClassList.get(position).getImageUrl();
                    PictureDetailsActivity.total_downloads = gamePuzzleClassList.get(position).getDownloads();
                    PictureDetailsActivity.related_pic = Config.URL_GAME_PUZZLE;
                    PictureDetailsActivity.PIC_TYPE = "puzzle";
                    PictureDetailsActivity.likes = gamePuzzleClassList.get(position).getLikes();
                    Subscriptio_Class.type = "game";
                    subscriptio_class.detectMSISDN();

                }else if (PIC_TYPE.equals("racing")){

                    PictureDetailsActivity.contentCode = gameRacingClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = gameRacingClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = gameRacingClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = gameRacingClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = gameRacingClassList.get(position).getPhysicalFileName();
                    PictureDetailsActivity.contentImg = gameRacingClassList.get(position).getImageUrl();
                    PictureDetailsActivity.ZedID = gameRacingClassList.get(position).getZeid();
                    PictureDetailsActivity.image = gameRacingClassList.get(position).getImageUrl();
                    PictureDetailsActivity.total_downloads = gameRacingClassList.get(position).getDownloads();
                    PictureDetailsActivity.related_pic = Config.URL_GAME_RACING;
                    PictureDetailsActivity.PIC_TYPE = "racing";
                    PictureDetailsActivity.likes = gameRacingClassList.get(position).getLikes();
                    Subscriptio_Class.type = "game";
                    subscriptio_class.detectMSISDN();

                }else if (PIC_TYPE.equals("arcade")){

                    PictureDetailsActivity.contentCode = gameAracadeClassList.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = gameAracadeClassList.get(position).getCategory_code();
                    PictureDetailsActivity.contentName = gameAracadeClassList.get(position).getContent_title();
                    PictureDetailsActivity.sContentType = gameAracadeClassList.get(position).getType();
                    PictureDetailsActivity.sPhysicalFileName = gameAracadeClassList.get(position).getPhysicalFileName();
                    PictureDetailsActivity.contentImg = gameAracadeClassList.get(position).getImageUrl();
                    PictureDetailsActivity.ZedID = gameAracadeClassList.get(position).getZeid();
                    PictureDetailsActivity.total_downloads = gameAracadeClassList.get(position).getDownloads();
                    PictureDetailsActivity.image = gameAracadeClassList.get(position).getImageUrl();
                    PictureDetailsActivity.related_pic = Config.URL_GAME_ARCADE;
                    PictureDetailsActivity.PIC_TYPE = "arcade";
                    PictureDetailsActivity.likes = gameAracadeClassList.get(position).getLikes();
                    Subscriptio_Class.type = "game";
                    subscriptio_class.detectMSISDN();

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    private void parseRelatedPic(String related_pic) {

        if (PIC_TYPE.equals("pic")){

            JsonArrayRequest request = new JsonArrayRequest(related_pic, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    loading.dismiss();
                    hideMoreButton(num,jsonArray);

                    for (int i = 0; i<=num; i++){
                        try {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            pictureClass = new PictureClass();
                            relatedClass = new StickerRelatedClass();
                            pictureClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE));
                            pictureClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE));
                            pictureClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE));
                            pictureClass.setType(obj.getString(Config.TYPE_PICTURE));
                            pictureClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_PICTURE));
                            pictureClass.setZID(obj.getString(Config.ZID_PICTURE));
                            pictureClass.setPath(obj.getString(Config.PATH_PICTURE));
                            pictureClass.setLikes(obj.getString(Config.TOTAL_LIKE_PIC));
                            pictureClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            String imgUrl = obj.getString(Config.IMAGE_URL_PICTURE).replace(" ","%20");
                            pictureClass.setImageUrl(imgUrl);
                            pictureList.add(pictureClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_PICTURE));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_PICTURE).replace(" ","%20"));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE_PIC));

                            stickerRelatedClassList.add(relatedClass);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);

        }else if (PIC_TYPE.equals("bc")){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, related_pic, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        loading.dismiss();



                        JSONArray array = jsonObject.getJSONArray("Table");

                        // hide load more button when no more data to load
                        hideMoreButton(num,array);

                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            relatedClass = new StickerRelatedClass();
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

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_PICTURE_BOLLY_CELE));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_PICTURE_BOLLY_CELE));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mPullRefreshScrollView.onRefreshComplete();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);
            requestQueue.add(request);
        }else if (PIC_TYPE.equals("hc")){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, related_pic, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        loading.dismiss();

                        JSONArray array = jsonObject.getJSONArray("Table");
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            Log.d("Obj BOLLY_CELE ",obj.getString(Config.CONTENT_CODE_PICTURE_HOLLY_CELE));


                            hollywoodCelebratyClass = new PictureHollywoodCelebratyClass();
                            relatedClass = new StickerRelatedClass();
                            hollywoodCelebratyClass.setContent_code(obj.getString(Config.CONTENT_CODE_PICTURE_HOLLY_CELE));
                            hollywoodCelebratyClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_PICTURE_HOLLY_CELE));
                            hollywoodCelebratyClass.setContent_title(obj.getString(Config.CONTENT_TITLE_PICTURE_HOLLY_CELE));
                            hollywoodCelebratyClass.setType(obj.getString(Config.TYPE_PICTURE_HOLLY_CELE));
                            hollywoodCelebratyClass.setPhysicalFilwName(obj.getString(Config.PHYSICALFILENAME_PICTURE_HOLLY_CELE));
                            hollywoodCelebratyClass.setZeid(obj.getString(Config.ZID_PICTURE_HOLLY_CELE));
                            hollywoodCelebratyClass.setPath(obj.getString(Config.PATH_PICTURE_HOLLY_CELE));
                            hollywoodCelebratyClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            hollywoodCelebratyClass.setImage_url(obj.getString(Config.IMAGE_URL_PICTURE_HOLLY_CELE));
                            hollywoodCelebratyClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            pictureHollywoodCelebratyClassList.add(hollywoodCelebratyClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_PICTURE_HOLLY_CELE));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_PICTURE_HOLLY_CELE));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mPullRefreshScrollView.onRefreshComplete();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);
            requestQueue.add(request);
        }else if (PIC_TYPE.equals("bdc")){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, related_pic, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {


                        JSONArray array = jsonObject.getJSONArray("Table");
                        loading.dismiss();
                        hideMoreButton(num,array);
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);


                            pictureBangladeshiCele = new PictureBangladeshiCele();
                            relatedClass = new StickerRelatedClass();
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
                            pictureBangladeshiCeleList.add(pictureBangladeshiCele);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_PICTURE_BANGLADESHI_CELE));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_PICTURE_BANGLADESHI_CELE));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mPullRefreshScrollView.onRefreshComplete();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);
            requestQueue.add(request);

        }else if (PIC_TYPE.equals("event")){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, related_pic, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");

                        loading.dismiss();
                        hideMoreButton(num,array);

                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);


                            pictureSpecialEventClass = new PictureSpecialEventClass();
                            relatedClass = new StickerRelatedClass();

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
                            EventClassList.add(pictureSpecialEventClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_PICTURE_SPECIAL_EVENT));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_PICTURE_SPECIAL_EVENT));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mPullRefreshScrollView.onRefreshComplete();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);
            requestQueue.add(request);

        }else if (PIC_TYPE.equals("cool")){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, related_pic, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        loading.dismiss();
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);


                            pictureCoolClass = new PictureCoolClass();
                            relatedClass = new StickerRelatedClass();

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

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_PICTURE_COOL));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_PICTURE_COOL));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mPullRefreshScrollView.onRefreshComplete();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);
            requestQueue.add(request);

        }else if (PIC_TYPE.equals("love")){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, related_pic, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");

                        loading.dismiss();
                        hideMoreButton(num,array);

                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);


                            pictureLoveClass = new PictureLoveClass();
                            relatedClass = new StickerRelatedClass();
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

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_PICTURE_LOVE));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_PICTURE_LOVE));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);


                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mPullRefreshScrollView.onRefreshComplete();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);
            requestQueue.add(request);

        }else if (PIC_TYPE.equals("game")){

            JsonArrayRequest request = new JsonArrayRequest(related_pic, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    loading.dismiss();
                    for (int i = 0; i<num; i++){
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            gameClass = new GameClass();
                            relatedClass = new StickerRelatedClass();
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
                            gameClassList.add(gameClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TIILE_GAME));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_GAME));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }else if (PIC_TYPE.equals("action")){

            JsonArrayRequest request = new JsonArrayRequest(related_pic, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    loading.dismiss();
                    for (int i = 0; i<num; i++){
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            gameActionClass = new GameActionClass();
                            relatedClass = new StickerRelatedClass();

                            gameActionClass.setContent_code(obj.getString(Config.CONTENT_CODE_GAME_ACTION));
                            gameActionClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_GAME_ACTION));
                            gameActionClass.setContent_title(obj.getString(Config.CONTENT_TITLE_GAME_ACTION));
                            gameActionClass.setType(obj.getString(Config.TYPE_GAME_ACTION));
                            gameActionClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_GAME_ACTION));
                            gameActionClass.setZeid(obj.getString(Config.ZID_GAME_ACTION));
                            gameActionClass.setPath(obj.getString(Config.PATH_GAME_ACTION));
                            gameActionClass.setImageUrl(obj.getString(Config.IMAGE_URL_GAME_ACTION));
                            gameActionClass.setBigImageUrl(obj.getString(Config.BIG_IAMGE_GAME_ACTION));
                            gameActionClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            gameActionClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            gameActionClassList.add(gameActionClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_GAME_ACTION));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_GAME_ACTION));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);

        }else if (PIC_TYPE.equals("sports")){
            JsonArrayRequest request = new JsonArrayRequest(related_pic, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    loading.dismiss();
                    for (int i = 0; i<num; i++){
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            gameSportsClass = new GameSportsClass();
                            relatedClass = new StickerRelatedClass();

                            gameSportsClass.setContent_code(obj.getString(Config.CONTENT_CODE_GAME_SPORTS));
                            gameSportsClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_GAME_SPORTS));
                            gameSportsClass.setContent_title(obj.getString(Config.CONTENT_TITLE_GAME_SPORTS));
                            gameSportsClass.setType(obj.getString(Config.TYPE_GAME_SPORTS));
                            gameSportsClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_GAME_SPORTS));
                            gameSportsClass.setZeid(obj.getString(Config.ZID_GAME_SPORTS));
                            gameSportsClass.setPath(obj.getString(Config.PATH_GAME_SPORTS));
                            gameSportsClass.setImageUrl(obj.getString(Config.IMAGE_URL_GAME_SPORTS));
                            gameSportsClass.setBigImageUrl(obj.getString(Config.BIG_IAMGE_GAME_SPORTS));
                            gameSportsClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            gameSportsClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            gameSportsClassList.add(gameSportsClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_GAME_SPORTS));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_GAME_SPORTS));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }else if (PIC_TYPE.equals("puzzle")){

            JsonArrayRequest request = new JsonArrayRequest(related_pic, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    loading.dismiss();
                    for (int i = 0; i<num; i++){
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            gamePuzzleClass = new GamePuzzleClass();
                            relatedClass = new StickerRelatedClass();
                            gamePuzzleClass.setContent_code(obj.getString(Config.CONTENT_CODE_GAME_PUZZLE));
                            gamePuzzleClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_GAME_PUZZLE));
                            gamePuzzleClass.setContent_title(obj.getString(Config.CONTENT_TITLE_GAME_PUZZLE));
                            gamePuzzleClass.setType(obj.getString(Config.TYPE_GAME_PUZZLE));
                            gamePuzzleClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_GAME_PUZZLE));
                            gamePuzzleClass.setZeid(obj.getString(Config.ZID_GAME_PUZZLE));
                            gamePuzzleClass.setPath(obj.getString(Config.PATH_GAME_PUZZLE));
                            gamePuzzleClass.setImageUrl(obj.getString(Config.IMAGE_URL_GAME_PUZZLE));
                            gamePuzzleClass.setBigImageUrl(obj.getString(Config.BIG_IAMGE_GAME_PUZZLE));
                            gamePuzzleClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            gamePuzzleClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            gamePuzzleClassList.add(gamePuzzleClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_GAME_PUZZLE));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_GAME_PUZZLE));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);

        }else if(PIC_TYPE.equals("racing")){

            JsonArrayRequest request = new JsonArrayRequest(related_pic, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    loading.dismiss();
                    for (int i = 0; i<num; i++){
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            gameRacingClass = new GameRacingClass();
                            relatedClass = new StickerRelatedClass();

                            gameRacingClass.setContent_code(obj.getString(Config.CONTENT_CODE_GAME_RACING));
                            gameRacingClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_GAME_RACING));
                            gameRacingClass.setContent_title(obj.getString(Config.CONTENT_TITLE_GAME_RACING));
                            gameRacingClass.setType(obj.getString(Config.TYPE_GAME_RACING));
                            gameRacingClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_GAME_RACING));
                            gameRacingClass.setZeid(obj.getString(Config.ZID_GAME_RACING));
                            gameRacingClass.setPath(obj.getString(Config.PATH_GAME_RACING));
                            gameRacingClass.setImageUrl(obj.getString(Config.IMAGE_URL_GAME_RACING));
                            gameRacingClass.setBigImageUrl(obj.getString(Config.BIG_IAMGE_GAME_RACING));
                            gameRacingClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            gameRacingClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            gameRacingClassList.add(gameRacingClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_GAME_RACING));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_GAME_RACING));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }else if (PIC_TYPE.equals("arcade")){

            JsonArrayRequest request = new JsonArrayRequest(related_pic, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    loading.dismiss();
                    for (int i = 0; i<num; i++){
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            gameAracadeClass = new GameAracadeClass();
                            relatedClass = new StickerRelatedClass();

                            gameAracadeClass.setContent_code(obj.getString(Config.CONTENT_CODE_GAME_ARCADE));
                            gameAracadeClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_GAME_ARCADE));
                            gameAracadeClass.setContent_title(obj.getString(Config.CONTENT_TITLE_GAME_ARCADE));
                            gameAracadeClass.setType(obj.getString(Config.TYPE_GAME_ARCADE));
                            gameAracadeClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_GAME_ARCADE));
                            gameAracadeClass.setZeid(obj.getString(Config.ZID_GAME_ARCADE));
                            gameAracadeClass.setPath(obj.getString(Config.PATH_GAME_ARCADE));
                            gameAracadeClass.setImageUrl(obj.getString(Config.IMAGE_URL_GAME_ARCADE));
                            gameAracadeClass.setBigImageUrl(obj.getString(Config.BIG_IAMGE_GAME_ARCADE));
                            gameAracadeClass.setLikes(obj.getString(Config.TOTAL_LIKE));
                            gameAracadeClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            gameAracadeClassList.add(gameAracadeClass);

                            relatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            relatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_GAME_ARCADE));
                            relatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_GAME_ARCADE));
                            relatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClassList.add(relatedClass);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mPullRefreshScrollView.onRefreshComplete();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }
    }


    private void initRecycler() {

        btnLoadMorePic = (Button) findViewById(R.id.btn_load_picture);

        loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);

        adapter = new RelatedStickerAdapter(PictureDetailsActivity.this,stickerRelatedClassList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_picture_related);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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


    public void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void hidemPDialog() {
        if (mPDialog != null) {
            mPDialog.dismiss();
            mPDialog = null;
        }
    }

    public void btnPicLike(View view) {

        likeCountPic(Config.URL_LIKES);

    }

    private void likeCountPic(String urlLikes) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlLikes+contentCode, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    JSONArray array = jsonObject.getJSONArray("Table");

                    for (int i = 0; i<array.length(); i++){

                        JSONObject obj = array.getJSONObject(i);

                        String totalLikes = obj.getString("LikeCount");
                        txtPicLikeCount.setText(totalLikes);
                        Log.d("LikeCount",totalLikes);
                    }

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
        RequestQueue requestQueue = Volley.newRequestQueue(PictureDetailsActivity.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }


    public class SendLaunchPushResponse extends AsyncTask<String, String, String> {
        RequiredUserInfo userinfo = new RequiredUserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(PictureDetailsActivity.this);
        String HS_MOD_ = userinfo.deviceModel(PictureDetailsActivity.this);
        String user_email = userinfo.userEmail(PictureDetailsActivity.this);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            PictureDetailsActivity.rsltNumber = "START";

            try {
                Thread.sleep(1000);

                Caller c = new Caller();
                // c.ad= c.ad;
                c.join();
                c.start();

                while (PictureDetailsActivity.rsltNumber == "START") {
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

    public void btn_load_picture(View view) {

        loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);

        pictureLoveClassList.clear();
        pictureCoolClassList.clear();
        EventClassList.clear();
        pictureBangladeshiCeleList.clear();
        pictureHollywoodCelebratyClassList.clear();
        pictureBollywoodCeleClassList.clear();
        stickerRelatedClassList.clear();
        pictureList.clear();
        gameClassList.clear();
        gameActionClassList.clear();
        gameSportsClassList.clear();
        gamePuzzleClassList.clear();
        gameRacingClassList.clear();
        gameAracadeClassList.clear();

        num+=5;

        parseRelatedPic(related_pic);
    }

    private void hideMoreButton(int num, JSONArray jsonArray) {

        if (num >= jsonArray.length()){

            btnLoadMorePic.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PictureDetailsActivity.this, MainActivity.class);
// set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
