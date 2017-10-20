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
import com.vumobile.clubzed.model.StickerEidClass;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class StickerEidAdapter extends RecyclerView.Adapter<StickerEidAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<StickerEidClass> stickerEidClassList;

    public StickerEidAdapter(Context context, List<StickerEidClass> List){
        this.mContext = context;
        this.stickerEidClassList = List;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_games_glide,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        StickerEidClass primaryClass = stickerEidClassList.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();

        holder.txtTotalDwn.setText(primaryClass.getDownloads());
        holder.txtLikes.setText(primaryClass.getTotal_like());
        Glide.with(mContext).load(primaryClass.getImageUrl()).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);
        holder.videoTitle.setText(primaryClass.getContent_title().replace("_"," "));

    }

    @Override
    public int getItemCount() {
        return stickerEidClassList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle,txtTotalDwn,txtLikes;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTotalDwn = (TextView) itemView.findViewById(R.id.txtTotalDwn);
            txtLikes = (TextView) itemView.findViewById(R.id.txtLikes);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_items_animate_glid);
            videoTitle = (TextView) itemView.findViewById(R.id.txt_item_titles);
        }
    }
}
