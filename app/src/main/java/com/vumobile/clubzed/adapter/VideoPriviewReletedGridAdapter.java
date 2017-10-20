package com.vumobile.clubzed.adapter;

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

import java.util.ArrayList;

/**
 * Created by toukirul on 13/2/2017.
 */

public class VideoPriviewReletedGridAdapter extends BaseAdapter {

    //Imageloader to load images
    private ImageLoader imageLoader;
    ImageView networkImageView;

    //Context
    TextView textView,txtLike,txtViews;
    private Context context;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> likes;
    private ArrayList<String> views;


    public VideoPriviewReletedGridAdapter(Context context, ArrayList<String> images, ArrayList<String> names,ArrayList<String> likes,ArrayList<String> views) {
        //Getting all the values
        this.context = context;
        this.images = images;
        this.names = names;
        this.likes = likes;
        this.views = views;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_related_video, null);
        } else {
            grid = convertView;
        }
        txtLike = (TextView) grid.findViewById(R.id.txtRelatedItemLikes);
        txtViews = (TextView) grid.findViewById(R.id.txtRelatedItemViews);
        textView = (TextView) grid.findViewById(R.id.txtRelatedTitles);
        networkImageView = (ImageView) grid.findViewById(R.id.img_items_related);
        //Initializing ImageLoader
//            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
//            imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
//            //Setting the image url to load
//            networkImageView.setImageUrl(images.get(position),imageLoader);

        // override is used for scalling the image
        Glide.with(context).load(images.get(position)).override(100, 100).into(networkImageView);
        textView.setText(names.get(position).replace("_", " "));
        txtViews.setText(views.get(position));
        txtLike.setText(likes.get(position));

        return grid;

    }
}
