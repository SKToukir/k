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
 * Created by toukirul on 1/2/2017.
 */

public class GameGridAdapter extends BaseAdapter{

    private ImageLoader imageLoader;
    ImageView networkImageView;

    //Context
    TextView textView,txtLikes,txtViews;
    private Context context;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> likes;
    private ArrayList<String> downloads;


    public GameGridAdapter (Context context, ArrayList<String> images, ArrayList<String> names,ArrayList<String> likes, ArrayList<String> downloads){
        //Getting all the values
        this.context = context;
        this.images = images;
        this.names = names;
        this.likes = likes;
        this.downloads = downloads;
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
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_game_row, null);
        }else{
            grid = convertView;
        }
        // here txtViews means downloads
        txtViews = (TextView) grid.findViewById(R.id.txtViews);
        txtLikes = (TextView) grid.findViewById(R.id.txtLikes);
        textView = (TextView) grid.findViewById(R.id.grid_text);
        networkImageView = (ImageView)grid.findViewById(R.id.grid_image);
//        //Initializing ImageLoader
//        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
//        imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
//        //Setting the image url to load
//        networkImageView.setImageUrl(images.get(position),imageLoader);
        txtViews.setText(downloads.get(position));
        txtLikes.setText(likes.get(position));
        Glide.with(context).load(images.get(position)).into(networkImageView);
        textView.setText(names.get(position).replace("_"," "));

        return grid;

    }
}
