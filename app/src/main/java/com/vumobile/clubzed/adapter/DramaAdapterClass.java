package com.vumobile.clubzed.adapter;

/**
 * Created by toukirul on 18/1/2017.
 */

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
import com.vumobile.clubzed.model.DramaClipsHome;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class DramaAdapterClass extends RecyclerView.Adapter<DramaAdapterClass.MyViewHolder> {

    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context mContext;
    private List<DramaClipsHome> dramaClipsHomeList;

    AQuery aq;

    public DramaAdapterClass(Context context, List<DramaClipsHome> dramaHomeList){
        this.mContext = context;
        this.dramaClipsHomeList = dramaHomeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_picasso,parent,false);
        aq = new AQuery(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DramaClipsHome primaryClass = dramaClipsHomeList.get(position);
        //imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
        holder.txtLikes.setText(primaryClass.getTotal_like());
        holder.txtViews.setText(primaryClass.getTotal_views());

        //aq.id(holder.videoImageView).image(primaryClass.getContent_image());
       Glide.with(mContext).load(primaryClass.getContent_image()).override(100,100).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getContent_image(),imageLoader);
        holder.videoTitle.setText(primaryClass.getContent_name().replace("_"," "));
       // aq.id(holder.videoTitle).text(primaryClass.getContent_name());
    }

    @Override
    public int getItemCount() {
        return dramaClipsHomeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle,txtLikes, txtViews;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtLikes = (TextView) itemView.findViewById(R.id.txtLike);
            txtViews = (TextView) itemView.findViewById(R.id.txtView);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_itemsss);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
        }
    }
}
