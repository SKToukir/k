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
import com.vumobile.clubzed.model.FullVideoClass;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class FullVideoAdapter extends RecyclerView.Adapter<FullVideoAdapter.MyViewHolder> {

    AQuery aq;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context mContext;
    private List<FullVideoClass> fullVideoClassList;

    public FullVideoAdapter(Context context, List<FullVideoClass> fullVdoHomeList){
        this.mContext = context;
        this.fullVideoClassList = fullVdoHomeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_picasso,parent,false);
        //aq = new AQuery(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        FullVideoClass primaryClass = fullVideoClassList.get(position);
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
