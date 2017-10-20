package com.vumobile.clubzed.adapter;

/**
 * Created by toukirul on 18/1/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.vumobile.clubzed.model.PictureClass;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyViewHolder> {

    AQuery aq;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context mContext;
    private List<PictureClass> pictureHomeList;

    public PictureAdapter(Context context, List<PictureClass> picList){
        this.mContext = context;
        this.pictureHomeList = picList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_games_glide,parent,false);
        aq = new AQuery(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PictureClass primaryClass = pictureHomeList.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();

        holder.txtLikes.setText(primaryClass.getLikes());
        holder.txtTotalDwn.setText(primaryClass.getDownloads());
        Log.d("Goooooooooooooooo",primaryClass.getDownloads());
        //aq.id(holder.videoImageView).image(primaryClass.getImageUrl(),true,true,0,AQuery.FADE_IN);
        Glide.with(mContext).load(primaryClass.getImageUrl()).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);
        holder.videoTitle.setText(primaryClass.getContent_title().replace("_"," "));

//        String img_url = primaryClass.getImageUrl();
//
//        if (img_url.contains(".gif")){
//            holder.videoImageView.setVisibility(View.GONE);
//            holder.img_items_animate.setVisibility(View.VISIBLE);
//            Glide.with(mContext).load(primaryClass.getImageUrl()).into(holder.img_items_animate);
//            holder.videoTitle.setText(primaryClass.getContent_title().replace("_"," "));
//        }else {
//            holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);
//            holder.videoTitle.setText(primaryClass.getContent_title().replace("_"," "));
//        }
    }

    @Override
    public int getItemCount() {
        return pictureHomeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle,txtTotalDwn, txtLikes;
        ImageView img_items_animate;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtLikes = (TextView) itemView.findViewById(R.id.txtLikes);
            txtTotalDwn = (TextView) itemView.findViewById(R.id.txtTotalDwn);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_items_animate_glid);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
            img_items_animate = (ImageView) itemView.findViewById(R.id.img_items_animate);
        }
    }
}
