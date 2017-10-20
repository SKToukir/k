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
import com.vumobile.clubzed.model.BollywoodTopClass;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class BollywoodTopAdapter extends RecyclerView.Adapter<BollywoodTopAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<BollywoodTopClass> bollywoodTopClassList;

    public BollywoodTopAdapter(Context context, List<BollywoodTopClass> bollywoodTopClassLists){
        this.mContext = context;
        this.bollywoodTopClassList = bollywoodTopClassLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_picasso,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BollywoodTopClass primaryClass = bollywoodTopClassList.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
        holder.txtLikes.setText(primaryClass.getTotal_like());
        holder.txtViews.setText(primaryClass.getTotal_views());

        Glide.with(mContext).load(primaryClass.getContent_img()).override(100,100).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getContent_img(),imageLoader);
        holder.videoTitle.setText(primaryClass.getContent_name().replace("_"," "));

    }

    @Override
    public int getItemCount() {
        return bollywoodTopClassList.size();
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
