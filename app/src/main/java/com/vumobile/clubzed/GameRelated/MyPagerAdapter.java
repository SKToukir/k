package com.vumobile.clubzed.GameRelated;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by it-6 on 2/22/2016.
 */
public class MyPagerAdapter extends PagerAdapter {
    String[] player={"helsdfj","sdfsdjf","d;lsfjl;asjdf","lajdlfj"};
    private Context ctx;
    public MyPagerAdapter(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //TextView tView = new TextView(ctx);
        ListView actionListView=new ListView(ctx);

        ListAdapter adapter=new ArrayAdapter<String>(ctx,android.R.layout.simple_expandable_list_item_1,player);
        actionListView.setAdapter(adapter);
        position++;
        /*tView.setText("Page number: " + position);
        tView.setTextColor(Color.RED);
        tView.setTextSize(20);*/
        container.addView(actionListView);
        return actionListView;
    }
    @Override
    public int getCount() {
        return 7;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}