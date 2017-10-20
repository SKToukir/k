package com.vumobile.clubzed.adapter;

/**
 * Created by toukirul on 23/1/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.app.CustomVolleyRequest;
import com.vumobile.clubzed.model.EnglishSongClass;

import java.util.List;

/**
 * Created by toukirul on 23/1/2017.
 */

/**
 * Created by toukirul on 18/1/2017.
 */

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class EnglishSongAdapter extends RecyclerView.Adapter<EnglishSongAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<EnglishSongClass> englishSongList;

    public EnglishSongAdapter(Context context, List<EnglishSongClass> englishSongLists){
        this.mContext = context;
        this.englishSongList = englishSongLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_music_picasso,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        EnglishSongClass primaryClass = englishSongList.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();


        holder.txtLikes.setText(primaryClass.getLikes());
        holder.txtViews.setText(primaryClass.getViews());
        Glide.with(mContext).load(primaryClass.getImageUrl()).override(100,100).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);
        holder.videoTitle.setText(primaryClass.getContent_title().replace("_"," "));

    }

    @Override
    public int getItemCount() {
        return englishSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle,txtLikes, txtViews;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtLikes = (TextView) itemView.findViewById(R.id.txtMusicLikes);
            txtViews = (TextView) itemView.findViewById(R.id.txtMusicViews);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_items_music);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
        }
    }
}
