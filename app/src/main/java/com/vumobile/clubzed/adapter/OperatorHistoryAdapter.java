package com.vumobile.clubzed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.model.MyDownloadsClass;

import java.util.List;

/**
 * Created by toukirul on 23/2/2017.
 */

public class OperatorHistoryAdapter extends RecyclerView.Adapter<OperatorHistoryAdapter.MyViewHolder> {

    ImageLoader imageLoader;
    private Context mContext;
    private List<MyDownloadsClass> MyDownloadsClassList;

    public OperatorHistoryAdapter(Context context, List<MyDownloadsClass> banglaTopClasses){
        this.mContext = context;
        this.MyDownloadsClassList = banglaTopClasses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.operator_history_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MyDownloadsClass primaryClass = MyDownloadsClassList.get(position);

        holder.txtTitle.setText(primaryClass.getContent_title().replace("_"," "));
        if (primaryClass.getSource().contains("CZ_GGL_AND_APP")){
            holder.txtSource.setText((primaryClass.getSource().replace("_"," ").replace("CZ GGL AND ","")));
        }else {
            holder.txtSource.setText("Portal");
        }
//        holder.txtSource.setText((primaryClass.getSource().replace("_"," ").replace("CZ GGL AND ","")));
        holder.txtCharged.setText(primaryClass.getAmount());
        holder.txtDownloadAt.setText(primaryClass.getTime_stamp());

    }

    @Override
    public int getItemCount() {
        return MyDownloadsClassList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle,txtSource,txtCharged,txtDownloadAt;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtContentTitle);
            txtSource = (TextView) itemView.findViewById(R.id.txtSource);
            txtCharged = (TextView) itemView.findViewById(R.id.txtCharged);
            txtDownloadAt = (TextView) itemView.findViewById(R.id.txtDownLoadAt);
        }
    }
}