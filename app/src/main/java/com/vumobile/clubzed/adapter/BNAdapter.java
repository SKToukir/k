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
import com.vumobile.clubzed.model.BanglaNatok;

import java.util.List;

/**
 * Created by toukirul on 26/9/2017.
 */

public class BNAdapter extends RecyclerView.Adapter<BNAdapter.MyViewHolder> {

    AQuery aq;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context mContext;
    private List<BanglaNatok> fullVideoClassList;

    public BNAdapter(Context context, List<BanglaNatok> fullVdoHomeList){
        this.mContext = context;
        this.fullVideoClassList = fullVdoHomeList;
    }

    @Override
    public BNAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hd,parent,false);
        //aq = new AQuery(view);
        return new BNAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BNAdapter.MyViewHolder holder, int position) {

        BanglaNatok primaryClass = fullVideoClassList.get(position);
        //imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
        //aq.id(holder.videoImageView).image(primaryClass.getContent_image(),true,true,0,AQuery.FADE_IN);
        //new ImageDownloaderTask(holder.videoImageView).execute(primaryClass.getContent_image());
        holder.txtLikes.setText(primaryClass.getTotalLike());
        holder.txtViews.setText(primaryClass.getTotalView());
        Glide.with(mContext).load(primaryClass.getContent_image()).override(100,100).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getContent_image(),imageLoader);
        holder.videoTitle.setText(primaryClass.getContent_name().replace("_"," "));
    }

    @Override
    public int getItemCount() {
        return fullVideoClassList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle, txtLikes, txtViews;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtLikes = (TextView) itemView.findViewById(R.id.txtLikess);
            txtViews = (TextView) itemView.findViewById(R.id.txtView);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_itemshd);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titleshd);
        }
    }
}
