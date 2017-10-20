package com.vumobile.clubzed.Picture_Sticker_Related;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.util.Subscriptio_Class;

import java.util.ArrayList;

public class PictureGridViewAdapter extends ArrayAdapter<PictureGridItemModel> {

    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<PictureGridItemModel> mGridData = new ArrayList<PictureGridItemModel>();
    Subscriptio_Class subscriptio_class=new Subscriptio_Class();

    public PictureGridViewAdapter(Context mContext, int layoutResourceId, ArrayList<PictureGridItemModel> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }


    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<PictureGridItemModel> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;



        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("ImagePosition",String.valueOf(mGridData.get(position).getTitle()));

                if ((mGridData.get(position).getsContentType()).matches("WP")) {
                    PictureDetailsActivity.contentCode = mGridData.get(position).getContent_code();
                    PictureDetailsActivity.categoryCode = mGridData.get(position).getCategoryCode();
                    PictureDetailsActivity.contentName = mGridData.get(position).getContent_name();
                    PictureDetailsActivity.sContentType = mGridData.get(position).getsContentType();
                    PictureDetailsActivity.sPhysicalFileName = mGridData.get(position).getsPhysicalFileName();
                    PictureDetailsActivity.contentImg = mGridData.get(position).getContent_img();
                    PictureDetailsActivity.ZedID = mGridData.get(position).getZedID();
                    PictureDetailsActivity.image=mGridData.get(position).getImage();
                    Log.d("contentImage", mGridData.get(position).getContent_img());
                    subscriptio_class.detectMSISDN();
                } else {

                    StickerPreview.contentCode = mGridData.get(position).getContent_code();
                    StickerPreview.categoryCode = mGridData.get(position).getCategoryCode();
                    StickerPreview.contentName = mGridData.get(position).getContent_name();
                    StickerPreview.sContentType = mGridData.get(position).getsContentType();
                    StickerPreview.sPhysicalFileName = mGridData.get(position).getsPhysicalFileName();
                    StickerPreview.contentImg = mGridData.get(position).getContent_img();
                    StickerPreview.ZedID = mGridData.get(position).getZedID();
                    StickerPreview.image=mGridData.get(position).getImage();
                    Log.d("contentImage", mGridData.get(position).getContent_img());
                    Subscriptio_Class.type="mpic";
                    subscriptio_class.detectMSISDN();
                }


                /*Intent pictureViewIntent=new Intent(mContext,PictureDetailsActivity.class);
                pictureViewIntent.
                        putExtra("title", mGridData.get(position).getTitle()).
                        putExtra("image", mGridData.get(position).getImage()).
                        putExtra("contentCode", mGridData.get(position).getContent_code()).
                        putExtra("categoryCode", mGridData.get(position).getCategoryCode()).
                        putExtra("contentName", mGridData.get(position).getContent_name()).
                        putExtra("sContentType", mGridData.get(position).getsContentType()).
                        putExtra("sPhysicalFileName", mGridData.get(position).getsPhysicalFileName()).
                        putExtra("contentImg", mGridData.get(position).getContent_img()).
                        putExtra("ZedID", mGridData.get(position).getZedID());
                mContext.startActivity(pictureViewIntent);*/

            }
        });
        PictureGridItemModel item = mGridData.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));
        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return row;

    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}