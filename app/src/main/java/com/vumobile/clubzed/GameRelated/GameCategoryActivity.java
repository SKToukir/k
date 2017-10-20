package com.vumobile.clubzed.GameRelated;

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
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.adapter.GameActionAdapter;
import com.vumobile.clubzed.adapter.GameArcadeAdapter;
import com.vumobile.clubzed.adapter.GamePuzzleAdapter;
import com.vumobile.clubzed.adapter.GameRacingAdapter;
import com.vumobile.clubzed.adapter.GameSportsAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.GameActionClass;
import com.vumobile.clubzed.model.GameAracadeClass;
import com.vumobile.clubzed.model.GamePuzzleClass;
import com.vumobile.clubzed.model.GameRacingClass;
import com.vumobile.clubzed.model.GameSportsClass;
import com.vumobile.clubzed.util.FAQActivity;
import com.vumobile.clubzed.util.HelpActivity;
import com.vumobile.clubzed.util.Subscriptio_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameCategoryActivity extends ActionBarActivity implements View.OnClickListener{

    Toolbar toolbar;
    ProgressDialog loading;
    Button btnBack;
    Intent intent;
    Subscriptio_Class subscriptio_class;
    TextView txtActionGameMore,txtSportsGameMore,txtBrainPuzzleGameMore,txtRacingGameMore,txtArcadeGameMore;
    GameAracadeClass gameAracadeClass;
    GameRacingClass gameRacingClass;
    GamePuzzleClass gamePuzzleClass;
    GameSportsClass gameSportsClass;
    GameActionClass gameActionClass;
    List<GameAracadeClass> gameAracadeClassList = new ArrayList<GameAracadeClass>();
    List<GameRacingClass> gameRacingClassList = new ArrayList<GameRacingClass>();
    List<GamePuzzleClass> gamePuzzleClassList = new ArrayList<GamePuzzleClass>();
    List<GameSportsClass> gameSportsClassList = new ArrayList<GameSportsClass>();
    List<GameActionClass> gameActionClassList = new ArrayList<GameActionClass>();
    RecyclerView recyclerActionGame,recyclerGameSports,recyclerGamePuzzle,recyclerGameRacing,recyclerGameArcade;
    RecyclerView.Adapter adapterActionGame,adapterGameSports,adapterGamePuzzle,adapterGameRacing,adapterGameArcade;
    ImageView btnHelp, btnHome, btnMydownload,btnBuddy,btnBdTube,btnBanglaBeats,btnAmarSticker,btnRateMe,btnHelpSame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_category);

        initUI();

        //init game action recycler
        initRecyclerGame();
        parseGameActionData(Config.URL_GAME_ACTION);

        //init game sports recycler
        initGameSports();
        parseGameSports(Config.URL_GAME_SPORTS);

        //init game puzzle recycler
        initGamePuzzleRecycler();
        parseGamePuzzle(Config.URL_GAME_PUZZLE);

        //init game racing recycler
        initGameRacingRecycler();
        parseGameRacing(Config.URL_GAME_RACING);

        //init game arcade recycler
        initGameArcadeRecycler();
        parseGameArcadeData(Config.URL_GAME_ARCADE);

        Subscriptio_Class.applicationContext = GameCategoryActivity.this;
        subscriptio_class = new Subscriptio_Class();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameCategoryActivity.this.finish();
                //overridePendingTransition(R.anim.right_out,R.anim.right_in);
            }
        });

        recyclerActionGame.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerActionGame, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = gameActionClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = gameActionClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = gameActionClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = gameActionClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = gameActionClassList.get(position).getPhysicalFileName();
                PictureDetailsActivity.contentImg = gameActionClassList.get(position).getImageUrl();
                PictureDetailsActivity.ZedID = gameActionClassList.get(position).getZeid();
                PictureDetailsActivity.image = gameActionClassList.get(position).getImageUrl();
                PictureDetailsActivity.related_pic = Config.URL_GAME_ACTION;
                PictureDetailsActivity.PIC_TYPE = "action";
                PictureDetailsActivity.likes = gameActionClassList.get(position).getLikes();
                Subscriptio_Class.type = "game";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerGameSports.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerActionGame, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = gameSportsClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = gameSportsClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = gameSportsClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = gameSportsClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = gameSportsClassList.get(position).getPhysicalFileName();
                PictureDetailsActivity.contentImg = gameSportsClassList.get(position).getImageUrl();
                PictureDetailsActivity.ZedID = gameSportsClassList.get(position).getZeid();
                PictureDetailsActivity.image = gameSportsClassList.get(position).getImageUrl();
                PictureDetailsActivity.related_pic = Config.URL_GAME_SPORTS;
                PictureDetailsActivity.PIC_TYPE = "sports";
                PictureDetailsActivity.likes = gameSportsClassList.get(position).getLikes();
                Subscriptio_Class.type = "game";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerGamePuzzle.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerActionGame, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = gamePuzzleClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = gamePuzzleClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = gamePuzzleClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = gamePuzzleClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = gamePuzzleClassList.get(position).getPhysicalFileName();
                PictureDetailsActivity.contentImg = gamePuzzleClassList.get(position).getImageUrl();
                PictureDetailsActivity.ZedID = gamePuzzleClassList.get(position).getZeid();
                PictureDetailsActivity.image = gamePuzzleClassList.get(position).getImageUrl();
                PictureDetailsActivity.related_pic = Config.URL_GAME_PUZZLE;
                PictureDetailsActivity.PIC_TYPE = "puzzle";
                PictureDetailsActivity.likes = gamePuzzleClassList.get(position).getLikes();
                Subscriptio_Class.type = "game";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerGameRacing.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerActionGame, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = gameRacingClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = gameRacingClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = gameRacingClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = gameRacingClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = gameRacingClassList.get(position).getPhysicalFileName();
                PictureDetailsActivity.contentImg = gameRacingClassList.get(position).getImageUrl();
                PictureDetailsActivity.ZedID = gameRacingClassList.get(position).getZeid();
                PictureDetailsActivity.image = gameRacingClassList.get(position).getImageUrl();
                PictureDetailsActivity.related_pic = Config.URL_GAME_RACING;
                PictureDetailsActivity.PIC_TYPE = "racing";
                PictureDetailsActivity.likes = gameRacingClassList.get(position).getLikes();
                Subscriptio_Class.type = "game";
                subscriptio_class.detectMSISDN();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerGameArcade.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), recyclerActionGame, new MainActivity.RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PictureDetailsActivity.contentCode = gameAracadeClassList.get(position).getContent_code();
                PictureDetailsActivity.categoryCode = gameAracadeClassList.get(position).getCategory_code();
                PictureDetailsActivity.contentName = gameAracadeClassList.get(position).getContent_title();
                PictureDetailsActivity.sContentType = gameAracadeClassList.get(position).getType();
                PictureDetailsActivity.sPhysicalFileName = gameAracadeClassList.get(position).getPhysicalFileName();
                PictureDetailsActivity.contentImg = gameAracadeClassList.get(position).getImageUrl();
                PictureDetailsActivity.ZedID = gameAracadeClassList.get(position).getZeid();
                PictureDetailsActivity.image = gameAracadeClassList.get(position).getImageUrl();
                PictureDetailsActivity.related_pic = Config.URL_GAME_ARCADE;
                PictureDetailsActivity.PIC_TYPE = "arcade";
                PictureDetailsActivity.likes = gameAracadeClassList.get(position).getLikes();
                Subscriptio_Class.type = "game";
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

        toolbar = (Toolbar) findViewById(R.id.tool_bar_game_cat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        txtActionGameMore = (TextView) findViewById(R.id.actionGameMore);
        txtSportsGameMore = (TextView) findViewById(R.id.gameSportsMore);
        txtBrainPuzzleGameMore = (TextView) findViewById(R.id.gamePuzzleMore);
        txtRacingGameMore = (TextView) findViewById(R.id.gameRacingMore);
        txtArcadeGameMore = (TextView) findViewById(R.id.gameArcadeMore);

        btnHome = (ImageView) findViewById(R.id.homeGame);
        btnHelp = (ImageView) findViewById(R.id.helpGame);
        btnHelpSame = (ImageView) findViewById(R.id.helpSameGame);
        btnMydownload = (ImageView) findViewById(R.id.myDownloadsGame);

        btnBuddy = (ImageView) findViewById(R.id.buddyGame);
        btnBdTube = (ImageView) findViewById(R.id.bdtubeGame);
        btnBanglaBeats = (ImageView) findViewById(R.id.banglabeatsGame);
        btnAmarSticker = (ImageView) findViewById(R.id.amarStGame);
        btnRateMe = (ImageView) findViewById(R.id.ratemeGame);

        btnBuddy.setOnClickListener(this);
        btnBdTube.setOnClickListener(this);
        btnBanglaBeats.setOnClickListener(this);
        btnRateMe.setOnClickListener(this);
        btnAmarSticker.setOnClickListener(this);

        btnHome.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnHelpSame.setOnClickListener(this);
        btnMydownload.setOnClickListener(this);

        txtActionGameMore.setOnClickListener(this);
        txtSportsGameMore.setOnClickListener(this);
        txtBrainPuzzleGameMore.setOnClickListener(this);
        txtRacingGameMore.setOnClickListener(this);
        txtArcadeGameMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.actionGameMore:
                intent = new Intent(GameCategoryActivity.this,ActionGmaeGridActivity.class);
                startActivity(intent);
                break;
            case R.id.gameSportsMore:
                intent = new Intent(GameCategoryActivity.this,SportsGameGridActivity.class);
                startActivity(intent);
                break;
            case R.id.gamePuzzleMore:
                intent = new Intent(GameCategoryActivity.this, BrainPuzzleGridActivity.class);
                startActivity(intent);
                break;
            case R.id.gameRacingMore:
                intent = new Intent(GameCategoryActivity.this,RacingGameGridActivity.class);
                startActivity(intent);
                break;
            case R.id.gameArcadeMore:
                intent = new Intent(GameCategoryActivity.this,ArcadeGridActivity.class);
                startActivity(intent);
                break;
            case R.id.homeGame:
                intent = new Intent(GameCategoryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.helpGame:
                intent = new Intent(GameCategoryActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.helpSameGame:
                intent = new Intent(GameCategoryActivity.this,FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.myDownloadsGame:
                intent = new Intent(GameCategoryActivity.this, MyDownLoadsActivity.class);
                startActivity(intent);
                break;
            case R.id.buddyGame :
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy")));
                break;
            case R.id.bdtubeGame:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube")));
                break;
            case R.id.banglabeatsGame:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en")));
                break;
            case R.id.amarStGame:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker")));
                break;
            case R.id.ratemeGame:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en")));
                break;
        }

    }

    private void initGameSports() {
        adapterGameSports = new GameSportsAdapter(GameCategoryActivity.this,gameSportsClassList);
        recyclerGameSports = (RecyclerView) findViewById(R.id.recycler_view_game_sports);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerGameSports.setLayoutManager(mLayoutManager);
        recyclerGameSports.setItemAnimator(new DefaultItemAnimator());
        recyclerGameSports.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initRecyclerGame() {
        adapterActionGame = new GameActionAdapter(GameCategoryActivity.this,gameActionClassList);
        recyclerActionGame = (RecyclerView) findViewById(R.id.recycler_view_action_game);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerActionGame.setLayoutManager(mLayoutManager);
        recyclerActionGame.setItemAnimator(new DefaultItemAnimator());
        recyclerActionGame.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initGameRacingRecycler() {

        adapterGameRacing = new GameRacingAdapter(GameCategoryActivity.this,gameRacingClassList);
        recyclerGameRacing = (RecyclerView) findViewById(R.id.recycler_view_game_racing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerGameRacing.setLayoutManager(mLayoutManager);
        recyclerGameRacing.setItemAnimator(new DefaultItemAnimator());
        recyclerGameRacing.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void initGamePuzzleRecycler() {
        adapterGamePuzzle = new GamePuzzleAdapter(GameCategoryActivity.this,gamePuzzleClassList);
        recyclerGamePuzzle = (RecyclerView) findViewById(R.id.recycler_view_game_puzzle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerGamePuzzle.setLayoutManager(mLayoutManager);
        recyclerGamePuzzle.setItemAnimator(new DefaultItemAnimator());
        recyclerGamePuzzle.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }


    private void initGameArcadeRecycler() {
        adapterGameArcade = new GameArcadeAdapter(GameCategoryActivity.this,gameAracadeClassList);
        recyclerGameArcade = (RecyclerView) findViewById(R.id.recycler_view_game_arcade);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerGameArcade.setLayoutManager(mLayoutManager);
        recyclerGameArcade.setItemAnimator(new DefaultItemAnimator());
        recyclerGameArcade.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void parseGameActionData(String urlGameAction) {

        JsonArrayRequest request = new JsonArrayRequest(urlGameAction, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        gameActionClass = new GameActionClass();
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerActionGame.setAdapter(adapterActionGame);
                adapterActionGame.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GameCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseGameSports(String urlGameSports) {
        JsonArrayRequest request = new JsonArrayRequest(urlGameSports, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        gameSportsClass = new GameSportsClass();
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

                        gameSportsClassList.add(gameSportsClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerGameSports.setAdapter(adapterGameSports);
                adapterGameSports.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GameCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }


    private void parseGamePuzzle(String urlGamePuzzle) {

        JsonArrayRequest request = new JsonArrayRequest(urlGamePuzzle, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
        loading.dismiss();
                for (int i = 0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        gamePuzzleClass = new GamePuzzleClass();
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

                        gamePuzzleClassList.add(gamePuzzleClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerGamePuzzle.setAdapter(adapterGamePuzzle);
                adapterGamePuzzle.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GameCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseGameRacing(String urlGameRacing) {
        JsonArrayRequest request = new JsonArrayRequest(urlGameRacing, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        gameRacingClass = new GameRacingClass();
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

                        gameRacingClassList.add(gameRacingClass);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerGameRacing.setAdapter(adapterGameRacing);
                adapterGameRacing.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GameCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }

    private void parseGameArcadeData(String urlGameArcade) {
        JsonArrayRequest request = new JsonArrayRequest(urlGameArcade, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0; i<9; i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        gameAracadeClass = new GameAracadeClass();
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerGameArcade.setAdapter(adapterGameArcade);
                adapterGameArcade.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Connection Error!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GameCategoryActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(request);
        //AppController.getInstance().addToRequestQueue(request);
    }
}
