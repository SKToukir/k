package com.vumobile.clubzed.adapter;

/**
 * Created by toukirul on 18/1/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.app.CustomVolleyRequest;
import com.vumobile.clubzed.model.RelatedMusicClass;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class RelatedItemMusicAdapter extends RecyclerView.Adapter<RelatedItemMusicAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<RelatedMusicClass> relatedMusicClasses;

    public RelatedItemMusicAdapter(Context context, List<RelatedMusicClass> stickerHomeList){
        this.mContext = context;
        this.relatedMusicClasses = stickerHomeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_music_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        RelatedMusicClass primaryClass = relatedMusicClasses.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();




        holder.videoImageView.setImageUrl(primaryClass.getURL_RELATED_MUSIC(),imageLoader);
        holder.videoTitle.setText(primaryClass.getMUSIC_TITLE().replace("_"," "));
        holder.txtViews.setText(primaryClass.getMUSIC_VIEWS());
        holder.txtlikes.setText(primaryClass.getMUSIC_LIKE());

    }


    @Override
    public int getItemCount() {
        return relatedMusicClasses.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        NetworkImageView videoImageView;
        TextView videoTitle,txtViews,txtlikes;

        public MyViewHolder(View itemView) {
            super(itemView);
            videoImageView = (NetworkImageView) itemView.findViewById(R.id.img_items_related_music);
            videoTitle = (TextView) itemView.findViewById(R.id.txtRelatedTitleMusic);
            txtlikes = (TextView) itemView.findViewById(R.id.txtRelatedItemLikesMusic);
            txtViews = (TextView) itemView.findViewById(R.id.txtRelatedItemViewsMusic);
        }
    }

    public void clearData() {
        int size = this.relatedMusicClasses.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.relatedMusicClasses.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
