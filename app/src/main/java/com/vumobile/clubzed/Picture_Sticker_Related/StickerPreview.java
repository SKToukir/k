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
import android.support.v7.widget.CardView;
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
import com.vumobile.clubzed.model.StickerAnimated;
import com.vumobile.clubzed.model.StickerBanglaClass;
import com.vumobile.clubzed.model.StickerClass;
import com.vumobile.clubzed.model.StickerCricBissoClass;
import com.vumobile.clubzed.model.StickerEidClass;
import com.vumobile.clubzed.model.StickerEventClass;
import com.vumobile.clubzed.model.StickerFunnyClass;
import com.vumobile.clubzed.model.StickerIslamicClass;
import com.vumobile.clubzed.model.StickerLoveClass;
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

public class StickerPreview extends ActionBarActivity {

    CardView cardImage,cardImagenormal;
    private PullToRefreshScrollView mPullRefreshScrollView;
    ScrollView stLayoutScroll;
    private Intent intent;
    StickerLoveClass stickerLoveClass;
    List<StickerLoveClass> stickerLoveClassList = new ArrayList<StickerLoveClass>();
    StickerIslamicClass stickerIslamicClass;
    List<StickerIslamicClass> stickerIslamicClassList = new ArrayList<StickerIslamicClass>();
    StickerFunnyClass stickerFunnyClass;
    List<StickerFunnyClass> stickerFunnyClassList = new ArrayList<StickerFunnyClass>();
    StickerEventClass stickerEventClass;
    List<StickerEventClass> stickerEventClassList = new ArrayList<StickerEventClass>();
    StickerEidClass eidClass;
    List<StickerEidClass> stickerEidClassList = new ArrayList<StickerEidClass>();
    StickerCricBissoClass stickerCricBissoClass;
    List<StickerCricBissoClass> stickerCricBissoClassList = new ArrayList<StickerCricBissoClass>();
    StickerAnimated stickerAnimatedClass;
    StickerBanglaClass stickerBanglaClass;
    List<StickerAnimated> stickerAnimatedList = new ArrayList<StickerAnimated>();
    List<StickerBanglaClass> stickerBanglaClassList = new ArrayList<StickerBanglaClass>();
    public static String STICKER_TYPE = "";
    int num = 6;
    StickerRelatedClass stickerRelatedClass;
    List<StickerRelatedClass> stickerRelatedClasses = new ArrayList<StickerRelatedClass>();
    RecyclerView recyclerView;
    StickerClass stickerClass;
    List<StickerClass> stickerClassList = new ArrayList<StickerClass>();
    RelatedStickerAdapter adapter;
    private static final int ANIM_DURATION = 600;
    private TextView titleTextView;
    private NetworkImageView imageView;
    ImageView grid_item_image_preview;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;

    public static String related_pic_url = "";
    private RelativeLayout frameLayout;
    private ColorDrawable colorDrawable;
    ProgressDialog loading;
    public ProgressDialog pDialog, mPDialog;

    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;
    TextView downloadTextView, picTitle;
    public static String model;
    public static String manufacture;
    public static String brand;
    ImageButton shareImgeButton;
    ImageView ratingImageButton;
    MainActivity mainActivity;
    public static String contentCode = "", categoryCode = "", contentName = "", sContentType = "",
            sPhysicalFileName = "", ZedID = "", contentImg = "", image = "",total_likes = "", total_downloads = "";
    public static int dimWidth, dimHeight;
    ImageLoader imageLoader;
    public String string = "http://www.vumobile.biz/clubz_android/clubz_version.txt";
    public String UpdateString;
    public static String updateReasonString = "";
    public static boolean downloadORsubscription;
    public static String rsltNumber = "";
    public static int doAction = 0;
    public PHPRequest phpRequest = new PHPRequest();
    public String pushResponseUrl = "http://www.vumobile.biz/gcm_server_php/push_response.php";
    Subscriptio_Class subscriptio_class;
    TextView txtPicLikeCount,txtPicDownloadCount;
    Download_Class download_Class;
    Toolbar toolbar;
    private Button btnBack,btn_load_sticker;
    private ImageView imagHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting details screen layout
        setContentView(R.layout.activity_main_activity_picture_preview);
        imageLoader = AppController.getInstance().getImageLoader();




        mainActivity = new MainActivity();
        //----------Check update --------------
        initUI();
        initRecycler();
        parseRelatedSticker(related_pic_url);
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

        Subscriptio_Class.applicationContext=StickerPreview.this;
        subscriptio_class=new Subscriptio_Class();
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



        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {


                stickerLoveClassList.clear();
                stickerIslamicClassList.clear();
                stickerFunnyClassList.clear();
                stickerEventClassList.clear();
                stickerEidClassList.clear();
                stickerCricBissoClassList.clear();
                stickerAnimatedList.clear();
                stickerBanglaClassList.clear();
                stickerRelatedClasses.clear();
                stickerClassList.clear();
                stickerRelatedClasses.clear();
                //stickerRelatedClasses = new ArrayList<StickerRelatedClass>();
                num+=5;
                new BackgroundTask().execute();
            }
        });
        stLayoutScroll = mPullRefreshScrollView.getRefreshableView();


        //--------------Context------------
        Download_Class.applicationContext = StickerPreview.this;
        download_Class = new Download_Class();


        //----------Initialize the Text and button--------------
        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.stLayoutScroll);
        downloadTextView = (TextView) findViewById(R.id.pic_detail_downloadText);
        shareImgeButton = (ImageButton) findViewById(R.id.pic_detail_shareButton);
        txtPicLikeCount.setText(total_likes);
        txtPicDownloadCount.setText(total_downloads);
        recyclerView.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(StickerPreview.this, recyclerView, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (STICKER_TYPE.equals("st")){
                    StickerPreview.contentCode = stickerClassList.get(position).getContent_code();
                    StickerPreview.categoryCode = stickerClassList.get(position).getCategory_code();
                    StickerPreview.contentName = stickerClassList.get(position).getContent_title();
                    StickerPreview.sContentType = stickerClassList.get(position).getType();
                    StickerPreview.sPhysicalFileName = stickerClassList.get(position).getPhysicalFileName();
                    StickerPreview.contentImg = stickerClassList.get(position).getImageUrl();
                    StickerPreview.ZedID = stickerClassList.get(position).getZID();
                    StickerPreview.image = stickerClassList.get(position).getImageUrl();
                    StickerPreview.related_pic_url = Config.URL_STICKER;
                    StickerPreview.total_likes = stickerClassList.get(position).getLikes();
                    StickerPreview.total_downloads = stickerClassList.get(position).getDownloads();
                    Log.d("DOWNLOADS", stickerClassList.get(position).getDownloads());
                    StickerPreview.STICKER_TYPE = "st";
                    Subscriptio_Class.type = "mpic";
                    subscriptio_class.detectMSISDN();

                }else if (STICKER_TYPE.equals("bst")){
                    StickerPreview.contentCode = stickerBanglaClassList.get(position).getContent_code();
                    StickerPreview.categoryCode = stickerBanglaClassList.get(position).getCateGory_code();
                    StickerPreview.contentName = stickerBanglaClassList.get(position).getContent_title();
                    StickerPreview.sContentType = stickerBanglaClassList.get(position).getType();
                    StickerPreview.sPhysicalFileName = stickerBanglaClassList.get(position).getPhysicalFileName();
                    StickerPreview.contentImg = stickerBanglaClassList.get(position).getImageUrl();
                    StickerPreview.ZedID = stickerBanglaClassList.get(position).getZeid();
                    StickerPreview.image = stickerBanglaClassList.get(position).getImageUrl();
                    StickerPreview.total_downloads = stickerBanglaClassList.get(position).getDownloads();
                    StickerPreview.STICKER_TYPE = "bst";
                    StickerPreview.total_likes = stickerBanglaClassList.get(position).getLikes();
                    StickerPreview.related_pic_url = Config.URL_STICKER_BANGLA;
                    Subscriptio_Class.type = "mpic";
                    subscriptio_class.detectMSISDN();

                }else if (STICKER_TYPE.equals("ast")){
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

                }else if(STICKER_TYPE.equals("cbst")){
                    StickerPreview.contentCode = stickerCricBissoClassList.get(position).getContent_code();
                    StickerPreview.categoryCode = stickerCricBissoClassList.get(position).getCateGory_code();
                    StickerPreview.contentName = stickerCricBissoClassList.get(position).getContent_title();
                    StickerPreview.sContentType = stickerCricBissoClassList.get(position).getType();
                    StickerPreview.sPhysicalFileName = stickerCricBissoClassList.get(position).getPhysicalFileName();
                    StickerPreview.contentImg = stickerCricBissoClassList.get(position).getImageUrl();
                    StickerPreview.ZedID = stickerCricBissoClassList.get(position).getZeid();
                    StickerPreview.image = stickerCricBissoClassList.get(position).getImageUrl();
                    StickerPreview.total_downloads = stickerCricBissoClassList.get(position).getDownloads();
                    StickerPreview.STICKER_TYPE = "cbst";
                    StickerPreview.related_pic_url = Config.URL_STICKER_CRICKET;
                    StickerPreview.total_likes = stickerCricBissoClassList.get(position).getTotal_like();
                    Subscriptio_Class.type = "mpic";
                    subscriptio_class.detectMSISDN();

                }else if (STICKER_TYPE.equals("eidst")){
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

                }else if (STICKER_TYPE.equals("est")){
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

                }else if (STICKER_TYPE.equals("fst")){
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

                }else if (STICKER_TYPE.equals("ist")){
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

                }else if (STICKER_TYPE.equals("lst")){
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
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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

            loading = ProgressDialog.show(this, "Please wait...","Loading data...",false,false);
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

        //use for full screen layout
//        getWindow().clearFlags(WindowManager
//                .LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //---------set the pic title--------------
        picTitle = (TextView) findViewById(R.id.picDetailsCategoryTitle);
        picTitle.setText(contentName.replace("_", " "));

        //text move animation
      //  picTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_text));

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
        grid_item_image_preview = (ImageView) findViewById(R.id.grid_item_image_preview);
       /* Picasso.with(this).load(image).into(imageView);*/

        //Set the background color to black
        frameLayout = (RelativeLayout) findViewById(R.id.main_background);
        colorDrawable = new ColorDrawable(Color.BLACK);


        if (image.contains(".gif")){
            cardImage.setVisibility(View.VISIBLE);
            cardImagenormal.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            grid_item_image_preview.setVisibility(View.VISIBLE);
            Glide.with(this).load(image).into(grid_item_image_preview);
            loading.dismiss();
        }else {
            cardImage.setVisibility(View.GONE);
            imageView.setImageUrl(image, imageLoader);
            Log.d("picdetailimageurl", image);
            loading.dismiss();
        }
        downloadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckUpdate(string);

               /* mPDialog = new ProgressDialog(PictureDetailsActivity.this);
                Log.d("downloadbutton", "download button pressed");
                // Showing progress dialog before making http request
                mPDialog.setMessage("Loading...");
                mPDialog.show();*/
                download_Class.detectMSISDN();
            }
        });


    }

    private void initUI() {
        txtPicDownloadCount = (TextView) findViewById(R.id.txtPicDownloadCount);
        cardImage = (CardView) findViewById(R.id.cardImage);
        cardImagenormal = (CardView) findViewById(R.id.cardImagenormal);
        btn_load_sticker = (Button) findViewById(R.id.btn_load_sticker);
        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.stLayoutScroll);
        txtPicLikeCount = (TextView) findViewById(R.id.txtPicLikeCount);
    }

    private void initRecycler() {

        adapter = new RelatedStickerAdapter(StickerPreview.this,stickerRelatedClasses);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_sticker_related);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void parseRelatedSticker(String url) {

        //adapter = new RelatedStickerAdapter(StickerPreview.this,stickerRelatedClasses);
//        adapter.clearData();
//        adapter.notifyDataSetChanged();

        if (STICKER_TYPE.equals("st")) {



            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    Log.d("BalerDownload",jsonArray.toString());

                    loading.dismiss();
                    hideMoreButton(num,jsonArray);
                    for (int i = 0; i < num; i++) {

                        try {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            stickerClass = new StickerClass();
                            stickerRelatedClass = new StickerRelatedClass();

                            stickerClass.setContent_code(obj.getString(Config.CONTENT_CODE_STICKER));
                            stickerClass.setCategory_code(obj.getString(Config.CATEGORY_CODE_STICKER));
                            stickerClass.setContent_title(obj.getString(Config.CONTENT_TITLE_STICKER));
                            stickerClass.setType(obj.getString(Config.TYPE_STICKER));
                            stickerClass.setPhysicalFileName(obj.getString(Config.PHYSICALFILENAME_STICKER));
                            stickerClass.setZID(obj.getString(Config.ZID_STICKER));
                            stickerClass.setPath(obj.getString(Config.PATH_STICKER));
                            stickerClass.setLikes(obj.getString(Config.STICKER_LIKE));
                            String imgUrl = obj.getString(Config.IMAGE_URL_STICKER).replace(" ", "%20");
                            stickerClass.setImageUrl(imgUrl);
                            stickerClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));


                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER));
                            stickerRelatedClass.setPicImageUrl(imgUrl);
                            stickerRelatedClass.setLikes(obj.getString(Config.STICKER_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);

                            stickerClassList.add(stickerClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);

            //Adding request to the queue
            requestQueue.add(request);
            //AppController.getInstance().addToRequestQueue(request);
        }else if (STICKER_TYPE.equals("bst")){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        loading.dismiss();
                        hideMoreButton(num,array);
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            stickerBanglaClass = new StickerBanglaClass();
                            stickerRelatedClass = new StickerRelatedClass();

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

                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER));
                            stickerRelatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_STICKER_BANGLA).replace(" ","%20"));
                            stickerRelatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);

                            stickerBanglaClassList.add(stickerBanglaClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);
            requestQueue.add(request);

        }else if (STICKER_TYPE.equals("ast")){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        hideMoreButton(num,array);
                        loading.dismiss();
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            stickerAnimatedClass = new StickerAnimated();
                            stickerRelatedClass = new StickerRelatedClass();

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
                           // Log.d("URLA",stickerAnimatedClass.getImageUrl());

                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER_ANIMATED));
                            stickerRelatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_STICKER_ANIMATED).replace(" ","%20"));
                            stickerRelatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);
                            stickerAnimatedList.add(stickerAnimatedClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);
            requestQueue.add(request);

        }else if (STICKER_TYPE.equals("cbst")){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        hideMoreButton(num,array);
                        loading.dismiss();
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            stickerCricBissoClass = new StickerCricBissoClass();
                            stickerRelatedClass = new StickerRelatedClass();

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


                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER_CRIC_BISSO));
                            stickerRelatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_STICKER_CRIC_BISSO).replace(" ","%20"));
                            stickerRelatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);

                            stickerCricBissoClassList.add(stickerCricBissoClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);
            requestQueue.add(request);

        }else if (STICKER_TYPE.equals("eidst")){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        hideMoreButton(num,array);
                        loading.dismiss();
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            eidClass = new StickerEidClass();
                            stickerRelatedClass = new StickerRelatedClass();

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

                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER_EID));
                            stickerRelatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_STICKER_EID).replace(" ","%20"));
                            stickerRelatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);

                            stickerEidClassList.add(eidClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);
            requestQueue.add(request);

        }else if (STICKER_TYPE.equals("est")){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        hideMoreButton(num,array);
                        loading.dismiss();
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            stickerEventClass = new StickerEventClass();
                            stickerRelatedClass = new StickerRelatedClass();

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

                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER_EVENT));
                            stickerRelatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_STICKER_EVENT).replace(" ","%20"));
                            stickerRelatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);

                            stickerEventClassList.add(stickerEventClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);
            requestQueue.add(request);
        }else if (STICKER_TYPE.equals("fst")){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        hideMoreButton(num,array);
                        loading.dismiss();
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            stickerFunnyClass = new StickerFunnyClass();
                            stickerRelatedClass = new StickerRelatedClass();


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

                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER_FUNNY));
                            stickerRelatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_STICKER_FUNNY).replace(" ","%20"));
                            stickerRelatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);

                            stickerFunnyClassList.add(stickerFunnyClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);
            requestQueue.add(request);
        }else if (STICKER_TYPE.equals("ist")){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        hideMoreButton(num,array);
                        loading.dismiss();
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            stickerIslamicClass = new StickerIslamicClass();
                            stickerRelatedClass = new StickerRelatedClass();

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

                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER_ISLAMIC));
                            stickerRelatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_STICKER_ISLAMIC).replace(" ","%20"));
                            stickerRelatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);

                            stickerIslamicClassList.add(stickerIslamicClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);
            requestQueue.add(request);
        }else if (STICKER_TYPE.equals("lst")){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {

                        JSONArray array = jsonObject.getJSONArray("Table");
                        hideMoreButton(num,array);
                        loading.dismiss();
                        for (int i = 0; i<num; i++){


                            JSONObject obj = array.getJSONObject(i);

                            stickerLoveClass = new StickerLoveClass();
                            stickerRelatedClass = new StickerRelatedClass();

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

                            stickerRelatedClass.setDownloads(obj.getString(Config.TOTAL_DOWNLOADS));
                            stickerRelatedClass.setPicTitle(obj.getString(Config.CONTENT_TITLE_STICKER_LOVE));
                            stickerRelatedClass.setPicImageUrl(obj.getString(Config.IMAGE_URL_STICKER_LOVE).replace(" ","%20"));
                            stickerRelatedClass.setLikes(obj.getString(Config.TOTAL_LIKE));

                            stickerRelatedClasses.add(stickerRelatedClass);

                            stickerLoveClassList.add(stickerLoveClass);

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
                    Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);
            requestQueue.add(request);
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

    public void btn_load_sticker(View view) {

        loading = ProgressDialog.show(this, "Please wait...","Loading...",false,false);
        loading.setCancelable(true);

        stickerLoveClassList.clear();
        stickerIslamicClassList.clear();
        stickerFunnyClassList.clear();
        stickerEventClassList.clear();
        stickerEidClassList.clear();
        stickerCricBissoClassList.clear();
        stickerAnimatedList.clear();
        stickerBanglaClassList.clear();
        stickerRelatedClasses.clear();
        stickerClassList.clear();
        stickerRelatedClasses.clear();
        //stickerRelatedClasses = new ArrayList<StickerRelatedClass>();
        num+=5;
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            parseRelatedSticker(related_pic_url);

            return null;
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
        RequestQueue requestQueue = Volley.newRequestQueue(StickerPreview.this);

        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    public class SendLaunchPushResponse extends AsyncTask<String, String, String> {
        RequiredUserInfo userinfo = new RequiredUserInfo();
        String HS_MANUFAC_ = userinfo.deviceMANUFACTURER(StickerPreview.this);
        String HS_MOD_ = userinfo.deviceModel(StickerPreview.this);
        String user_email = userinfo.userEmail(StickerPreview.this);

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

    private void hideMoreButton(int num, JSONArray jsonArray) {

        if (num >= jsonArray.length()){

            mPullRefreshScrollView.onRefreshComplete();
            btn_load_sticker.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StickerPreview.this, MainActivity.class);
// set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
