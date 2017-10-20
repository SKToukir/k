package com.vumobile.clubzed.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.model.StickerRelatedClass;

import java.util.List;

/**
 * Created by toukirul on 15/2/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    ImageLoader imageLoader;
    private Context mContext;
    private List<StickerRelatedClass> stickerRelatedClasses;
    private static LayoutInflater inflater=null;

    public CustomListAdapter(Context context, List<StickerRelatedClass> stickerHomeList){
        this.mContext = context;
        this.stickerRelatedClasses = stickerHomeList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return stickerRelatedClasses.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder=new Holder();

        View v = convertView;

        if (convertView == null) {
            v = inflater.inflate(R.layout.related_sticker_row, null);
            ImageView videoImageView = (ImageView) v.findViewById(R.id.img_items_related_sticker);
            TextView videoTitle = (TextView) v.findViewById(R.id.txtRelatedTitleSticker);
            TextView txtlikes = (TextView) v.findViewById(R.id.txtRelatedItemLikesSticker);

            holder.videoTitle = videoTitle;
            holder.videoImageView = videoImageView;
            holder.txtlikes = txtlikes;

            v.setTag(holder);
        }else {
            holder = (Holder) v.getTag();
        }
        StickerRelatedClass primaryClass = stickerRelatedClasses.get(position);

        Glide.with(mContext).load(primaryClass.getPicImageUrl()).override(100,100).into(holder.videoImageView);
        //holder.videoImageView.setImageUrl(primaryClass.getPicImageUrl(),imageLoader);
        holder.videoTitle.setText(primaryClass.getPicTitle().replace("_"," "));
        holder.txtlikes.setText(primaryClass.getLikes());

        return v;
    }

    public class Holder
    {
        ImageView videoImageView;
        TextView videoTitle,txtViews,txtlikes;
    }
}
