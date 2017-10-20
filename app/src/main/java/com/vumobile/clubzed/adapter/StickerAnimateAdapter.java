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

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.app.CustomVolleyRequest;
import com.vumobile.clubzed.model.StickerAnimated;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class StickerAnimateAdapter extends RecyclerView.Adapter<StickerAnimateAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<StickerAnimated> stickerAnimatedList;

    public StickerAnimateAdapter(Context context, List<StickerAnimated> List){
        this.mContext = context;
        this.stickerAnimatedList = List;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animate_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        StickerAnimated primaryClass = stickerAnimatedList.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();



        holder.txtLikes.setText(primaryClass.getLikes());
        holder.txtView.setText(primaryClass.getDownloads());
        //holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);
        Glide.with(mContext).load(primaryClass.getImageUrl()).into(holder.videoImageView);
        holder.videoTitle.setText(primaryClass.getContent_title().replace("_"," "));

    }

    @Override
    public int getItemCount() {
        return stickerAnimatedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle,txtLikes,txtView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtView = (TextView) itemView.findViewById(R.id.txtView);
            txtLikes = (TextView) itemView.findViewById(R.id.txtLikess);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_items);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
        }
    }
}
