package com.edutech.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edutech.app.R;
import com.edutech.app.prototype.BuyVideosPrototype;
import com.edutech.app.subActivities.BuyVideosList;

import java.util.ArrayList;

/**
 * Created by vedant on 2/5/2018.
 */

public class BuyVideosRecycerviewAdapter extends RecyclerView.Adapter<BuyVideosRecycerviewAdapter.MyViewHolder> {

    Context context;
    public ArrayList<BuyVideosPrototype> buyVideosPrototypes;
    ProgressBar progressBar;

    public BuyVideosRecycerviewAdapter(Context context, ProgressBar progressBar, ArrayList<BuyVideosPrototype> buyVideosPrototypes) {
        this.context = context;
        this.progressBar = progressBar;
        this.buyVideosPrototypes = buyVideosPrototypes;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.listviewitem_buyvideospackagenames, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        try {
            final BuyVideosPrototype buyVideosPrototype = buyVideosPrototypes.get(position);

            holder.cbse_tv.setText(buyVideosPrototype.getCbse());
            holder.classx_tv.setText(buyVideosPrototype.getClassX());
            holder.subject_tv.setText(buyVideosPrototype.getSubject());
            holder.packagetitle_tv.setText(buyVideosPrototype.getPackageName());
            holder.aspercbseguidlines_tv.setText(buyVideosPrototype.getCbseGudilines());
            holder.totalvideos_tv.setText(buyVideosPrototype.getNumVideos());
            holder.totalchapters_tv.setText(buyVideosPrototype.getNumChapters());
            holder.totalhoursclock_tv.setText(buyVideosPrototype.getTotalHours());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Toast.makeText(getApplicationContext(),""+position+buyVideosPrototype.getPackagePath(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, BuyVideosList.class);
                    intent.putExtra("title", buyVideosPrototype.getSubject());
                    intent.putExtra("path", buyVideosPrototype.getPackagePath());
                    context.startActivity(intent);

                }
            });
            if (buyVideosPrototype.isActivated()) {
                holder.isActivated.setText("Activated");
                holder.isActivated.setTextColor(Color.GREEN);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return buyVideosPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cbse_tv, classx_tv, subject_tv, packagetitle_tv, aspercbseguidlines_tv, totalvideos_tv, totalchapters_tv, totalhoursclock_tv, isActivated;

        public MyViewHolder(View itemView) {
            super(itemView);

            cbse_tv = (TextView) itemView.findViewById(R.id.cbse_textview);
            classx_tv = (TextView) itemView.findViewById(R.id.classx_textview);
            subject_tv = (TextView) itemView.findViewById(R.id.economics_textview);
            packagetitle_tv = (TextView) itemView.findViewById(R.id.packagetitle_textview);
            aspercbseguidlines_tv = (TextView) itemView.findViewById(R.id.aspercbseguidlines_textview);
            totalvideos_tv = (TextView) itemView.findViewById(R.id.totalvideos_textview);
            totalchapters_tv = (TextView) itemView.findViewById(R.id.totalchapters_textview);
            totalhoursclock_tv = (TextView) itemView.findViewById(R.id.totalhoursclock_textview);
            isActivated = (TextView) itemView.findViewById(R.id.isactiivated_text);


        }


    }
}
