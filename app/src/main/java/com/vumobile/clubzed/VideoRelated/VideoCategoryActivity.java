package com.vumobile.clubzed.VideoRelated;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.vumobile.clubzed.R;

public class VideoCategoryActivity extends ActionBarActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_category);

//        toolbar = (Toolbar) findViewById(R.id.tool_bar_full_vdo);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");

//        Button btn = (Button) toolbar.findViewById(R.id.btn_back);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }
}
