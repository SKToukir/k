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
import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.app.AppController;
import com.vumobile.clubzed.app.CustomVolleyRequest;
import com.vumobile.clubzed.model.StickerClass;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.MyViewHolder> {

    AQuery aq;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context mContext;
    private List<StickerClass> stickerHomeList;

    public StickerAdapter(Context context, List<StickerClass> stickerHomeList){
        this.mContext = context;
        this.stickerHomeList = stickerHomeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticker_row,parent,false);
        aq = new AQuery(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        StickerClass primaryClass = stickerHomeList.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();



        String img_url = primaryClass.getImageUrl();

        holder.txtLikess.setText(primaryClass.getLikes());
        holder.txtView.setText(primaryClass.getDownloads());

        if (img_url.contains(".gif")){
            holder.videoImageView.setVisibility(View.GONE);
            holder.imageView_item.setVisibility(View.VISIBLE);

            Glide.with(mContext).load(primaryClass.getImageUrl()).into(holder.imageView_item);
            //Picasso.with(mContext).load(img_url).into(holder.imageView_item);
            holder.videoTitle.setText(primaryClass.getContent_title().replace("_"," "));

        }else {
            holder.videoImageView.setVisibility(View.VISIBLE);
            holder.imageView_item.setVisibility(View.GONE);
            aq.id(holder.videoImageView).image(primaryClass.getImageUrl());
            //Picasso.with(mContext).load(primaryClass.getImageUrl()).into(holder.videoImageView);
            // holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);
            holder.videoTitle.setText(primaryClass.getContent_title().replace("_"," "));
        }

    }

    @Override
    public int getItemCount() {
        return stickerHomeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle,txtLikess,txtView;
        ImageView imageView_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtView = (TextView) itemView.findViewById(R.id.txtView);
            txtLikess = (TextView) itemView.findViewById(R.id.txtLikess);
            imageView_item = (ImageView) itemView.findViewById(R.id.imgView_itemss_glid);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_items_sticker_glid);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
        }
    }
}
