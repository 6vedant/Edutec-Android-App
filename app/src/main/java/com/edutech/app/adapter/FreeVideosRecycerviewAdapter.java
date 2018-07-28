package com.edutech.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edutech.app.R;
import com.edutech.app.activities.BuyVideos;
import com.edutech.app.activities.FreeVideos;
import com.edutech.app.prototype.VideosListPrototype;
import com.edutech.app.subActivities.VideoView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by vedant on 2/6/2018.
 */

public class FreeVideosRecycerviewAdapter extends RecyclerView.Adapter<FreeVideosRecycerviewAdapter.MyViewHolder> {

    Context context;
    public ProgressBar progressBar;
    public ArrayList<VideosListPrototype> videosListPrototypes;

    public FreeVideosRecycerviewAdapter(Context context, ProgressBar progressBar, ArrayList<VideosListPrototype> videosListPrototypes) {
        this.context = context;
        this.progressBar = progressBar;
        this.videosListPrototypes = videosListPrototypes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_freevideos, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Glide.with(context).load(new File(videosListPrototypes.get(position).getThumbnail_url())).thumbnail(0.5f).into(holder.thumb);
        holder.title.setText(videosListPrototypes.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here open the video player
                final VideosListPrototype videosListPrototype = (VideosListPrototype) videosListPrototypes.get(position);

                if (!videosListPrototype.isActivated()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("" + videosListPrototype.getTitle());
                    builder.setMessage("Activate package to view further...");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            Intent intent = new Intent(context, VideoView.class);
                            intent.putExtra("url", videosListPrototype.getVideo_url());
                            intent.putExtra("thumb", videosListPrototype.getThumbnail_url());
                            intent.putExtra("title", videosListPrototype.getTitle());
                            context.startActivity(intent);

                        }
                    }).setNegativeButton("Activate ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            context.startActivity(new Intent(context, BuyVideos.class));
                        }
                    });
                    builder.show();

                } else {
                    Intent intent = new Intent(context, VideoView.class);
                    intent.putExtra("url", videosListPrototype.getVideo_url());
                    intent.putExtra("thumb", videosListPrototype.getThumbnail_url());
                    intent.putExtra("title", videosListPrototype.getTitle());
                    context.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return videosListPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView title;


        public MyViewHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.imageview_freevideos);
            title = (TextView) itemView.findViewById(R.id.title_freevideos);

        }
    }
}
