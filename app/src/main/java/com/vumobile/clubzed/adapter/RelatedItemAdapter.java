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
import com.vumobile.clubzed.model.RelatedVideoClass;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class RelatedItemAdapter extends RecyclerView.Adapter<RelatedItemAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<RelatedVideoClass> relatedVideoClasses;

    public RelatedItemAdapter(Context context, List<RelatedVideoClass> stickerHomeList){
        this.mContext = context;
        this.relatedVideoClasses = stickerHomeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tests,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        RelatedVideoClass primaryClass = relatedVideoClasses.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();




        holder.videoImageView.setImageUrl(primaryClass.getURL_RELATED_VIDEO(),imageLoader);
        holder.videoTitle.setText(primaryClass.getVIDEO_TITLE().replace("_"," "));
        holder.txtViews.setText(primaryClass.getVIDEO_VIEWS());
        holder.txtlikes.setText(primaryClass.getVIDEO_LIKE());

    }


    @Override
    public int getItemCount() {
        return relatedVideoClasses.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        NetworkImageView videoImageView;
        TextView videoTitle,txtViews,txtlikes;

        public MyViewHolder(View itemView) {
            super(itemView);
            videoImageView = (NetworkImageView) itemView.findViewById(R.id.img_items_related);
            videoTitle = (TextView) itemView.findViewById(R.id.txtRelatedTitle);
            txtlikes = (TextView) itemView.findViewById(R.id.txtRelatedItemLikes);
            txtViews = (TextView) itemView.findViewById(R.id.txtRelatedItemViews);
        }
    }

    public void clearData() {
        int size = this.relatedVideoClasses.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.relatedVideoClasses.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
            notifyDataSetChanged();
        }
    }
}
