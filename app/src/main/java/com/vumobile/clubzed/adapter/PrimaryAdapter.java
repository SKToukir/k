package com.vumobile.clubzed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.model.VideoHome;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class PrimaryAdapter extends RecyclerView.Adapter<PrimaryAdapter.MyViewHolder> {

    AQuery aq;

    //ImageLoader imageLoader =  AppController.getInstance().getImageLoader();;
    private Context mContext;
    private List<VideoHome> videoHomeList;

    public PrimaryAdapter(Context context, List<VideoHome> videoHomeList){
        this.mContext = context;
        this.videoHomeList = videoHomeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_picasso,parent,false);
        aq = new AQuery(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        VideoHome primaryClass = videoHomeList.get(position);
        //imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();

        holder.txtLikes.setText(primaryClass.getTotal_like());
        holder.txtViews.setText(primaryClass.getTotal_views());
       // aq.id(holder.videoImageView).image(primaryClass.getImageUrl(),true,true,0,AQuery.FADE_IN);

        Glide.with(mContext).load(primaryClass.getImageUrl()).override(100,100).into(holder.videoImageView);
       // holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);
        holder.videoTitle.setText(primaryClass.getContentTile().replace("_"," "));

    }

    @Override
    public int getItemCount() {
        return videoHomeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle,txtLikes, txtViews;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtLikes = (TextView) itemView.findViewById(R.id.txtLike);
            txtViews = (TextView) itemView.findViewById(R.id.txtView);
            //vedio_icon = (ImageView) itemView.findViewById(R.id.vedio_icon);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_itemsss);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
        }
    }
}
