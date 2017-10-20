package com.vumobile.clubzed.adapter;

/**
 * Created by toukirul on 18/1/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.app.CustomVolleyRequest;
import com.vumobile.clubzed.model.SearchClass;
import com.vumobile.clubzed.util.Download_Class;
import com.vumobile.clubzed.util.Subscriptio_Class;

import java.util.List;

/**
 * Created by toukir on 1/14/17.
 */

//---------------this adapter shows only image and title-------------------//
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    public static int dimWidth, dimHeight;
    public static String model;
    public static String manufacture;
    public static String brand;

    Download_Class download_class;
    String contentCode,catgoryCode,content_type,contentName,physical_name,zid,content_image;
    ImageLoader imageLoader;
    private Context mContext;
    private List<SearchClass> searchClassList;
    Subscriptio_Class subscriptio_class;



    public SearchAdapter(Context context, List<SearchClass> stickerHomeList){
        this.mContext = context;
        this.searchClassList = stickerHomeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_grid,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final SearchClass primaryClass = searchClassList.get(position);
        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();




        holder.videoImageView.setImageUrl(primaryClass.getImageUrl(),imageLoader);
        holder.videoTitle.setText(primaryClass.getPhysicalName().replace("_"," "));

        holder.btnDownLoadFromSearcList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download_Class.applicationContext=mContext;
                download_class=new Download_Class();

                String contentType = primaryClass.getType();
                Toast.makeText(mContext,contentType,Toast.LENGTH_LONG).show();
                Download_Class.contentCode=primaryClass.getContent_code();
                Download_Class.categoryCode=primaryClass.getCategory_code();
                Download_Class.sContentType=primaryClass.getType();
                Download_Class.contentName=primaryClass.getContent_title();
                Download_Class.sPhysicalFileName=primaryClass.getPhysicalName();
                Download_Class.ZedID=primaryClass.getZid();
                Download_Class.contentImg=primaryClass.getImageUrl();
                Download_Class.manufacture=manufacture;
                Download_Class.model=model;
                Download_Class.dimHeight=dimHeight;
                Download_Class.dimWidth=dimWidth;
                download_class.detectMSISDN();

            }
        });
    }

    @Override
    public int getItemCount() {
        return searchClassList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        NetworkImageView videoImageView;
        TextView videoTitle;
        Button btnDownLoadFromSearcList;

        public MyViewHolder(View itemView) {
            super(itemView);
//            videoImageView = (NetworkImageView) itemView.findViewById(R.id.img_items_search);
//            videoTitle = (TextView) itemView.findViewById(R.id.txtSearchItem);
//            btnDownLoadFromSearcList = (Button) itemView.findViewById(R.id.btnDownLoadFromSearcList);
        }
    }

    public void clearData() {
        int size = this.searchClassList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.searchClassList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
}
