package com.vumobile.clubzed;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vumobile.clubzed.adapter.OperatorHistoryAdapter;
import com.vumobile.clubzed.api.Config;
import com.vumobile.clubzed.app.DividerItemDecoration;
import com.vumobile.clubzed.model.MyDownloadsClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyDownLoadsActivity extends ActionBarActivity {
    Dialog dialogMNO;
    String resultNumber;
    Button btnOk;
    String msisdn;
    EditText edtMno;
    public static String rsltNumber = "";
    RecyclerView recyclerView;
    OperatorHistoryAdapter adapter;
    List<MyDownloadsClass> myDownloadsClassList = new ArrayList<MyDownloadsClass>();
    MyDownloadsClass myDownloadsClass;
    private Toolbar toolbar;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_down_loads);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_my_down);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initRecycler();

       dialogMNO = new Dialog(MyDownLoadsActivity.this,
                R.style.MyDialog);
        dialogMNO.setContentView(R.layout.msisdn_form);
        dialogMNO.setCancelable(true);
        btnOk = (Button) dialogMNO.findViewById(R.id.btnOk);

        edtMno = (EditText) dialogMNO.findViewById(R.id.edtMno);

        msisdn = AppConstant.mno;
        Log.d("mobileNumber",msisdn);

        if (msisdn.contains("ERROR")){

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resultNumber = edtMno.getText().toString();

                    if (resultNumber.startsWith("01")) {
                        resultNumber = "88" + resultNumber;
                        dialogMNO.dismiss();
                        parseOpreatorsHistory(resultNumber);
                    } else if (resultNumber.equals("") || resultNumber.equals(" ")
                            || resultNumber.equals(null) || resultNumber.isEmpty()
                            || resultNumber.length() > 13 || resultNumber.length() < 11) {
                        Toast.makeText(getApplicationContext(),
                                "Please Enter Correct Mobile Number",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

            dialogMNO.show();

        }else {
            parseOpreatorsHistory(msisdn);
        }

    }

    private void initRecycler() {

        adapter = new OperatorHistoryAdapter(MyDownLoadsActivity.this,myDownloadsClassList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_myDownloads);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void parseOpreatorsHistory(String resultNumber) {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_OPERATOR_HISTORY+resultNumber, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    JSONArray array = jsonObject.getJSONArray("Table");

                    for (int i = 0; i < array.length(); i++){

                        JSONObject obj = array.getJSONObject(i);
                        myDownloadsClass = new MyDownloadsClass();
                        myDownloadsClass.setContent_title(obj.getString(Config.MY_DOWNLOADS_CONTENT_TITLE));
                        myDownloadsClass.setSource(obj.getString(Config.MY_DOWNLOADS_SOURCE));
                        myDownloadsClass.setAmount(obj.getString(Config.MY_DOWNLOADS_AMOUNT));
                        myDownloadsClass.setTime_stamp(obj.getString(Config.MY_DOWNLOADS_TIMESTAMP));

                        myDownloadsClassList.add(myDownloadsClass);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MyDownLoadsActivity.this);

        //Adding request to the queue
        requestQueue.add(objectRequest);
        //AppController.getInstance().addToRequestQueue(request);
    }
}