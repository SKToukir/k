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
import com.vumobile.clubzed.model.StickerRelatedClass;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class RelatedStickerAdapter extends RecyclerView.Adapter<RelatedStickerAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<StickerRelatedClass> stickerRelatedClasses;

    public RelatedStickerAdapter(Context context, List<StickerRelatedClass> stickerHomeList){
        this.mContext = context;
        this.stickerRelatedClasses = stickerHomeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_sticker_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        StickerRelatedClass primaryClass = stickerRelatedClasses.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();

        holder.txtRelatedItemDownloadsSticker.setText(primaryClass.getDownloads());

        Glide.with(mContext).load(primaryClass.getPicImageUrl()).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getPicImageUrl(),imageLoader);
        holder.videoTitle.setText(primaryClass.getPicTitle().replace("_"," "));
        holder.txtlikes.setText(primaryClass.getLikes());

        holder.setIsRecyclable(false);

    }


    @Override
    public int getItemCount() {
        return stickerRelatedClasses.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView videoImageView;
        TextView videoTitle,txtViews,txtlikes, txtRelatedItemDownloadsSticker;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtRelatedItemDownloadsSticker= (TextView) itemView.findViewById(R.id.txtRelatedItemDownloadsSticker);
            videoImageView = (ImageView) itemView.findViewById(R.id.img_items_related_sticker);
            videoTitle = (TextView) itemView.findViewById(R.id.txtRelatedTitleSticker);
            txtlikes = (TextView) itemView.findViewById(R.id.txtRelatedItemLikesSticker);

        }
    }

    public void clearData() {
        int size = this.stickerRelatedClasses.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.stickerRelatedClasses.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
