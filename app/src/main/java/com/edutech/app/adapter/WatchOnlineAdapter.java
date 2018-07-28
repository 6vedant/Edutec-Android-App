package com.edutech.app.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edutech.app.R;
import com.edutech.app.activities.WatchOnline;
import com.edutech.app.prototype.WatchOnlinePrototype;
import com.edutech.app.subActivities.VideoView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vedant on 1/10/2018.
 */

public class WatchOnlineAdapter extends RecyclerView.Adapter<WatchOnlineAdapter.HolderView> {

    public Context context;
    public ArrayList<WatchOnlinePrototype> watchOnlinePrototypes;

    public WatchOnlineAdapter(Context context, ArrayList<WatchOnlinePrototype> watchOnlinePrototypes) {
        this.context = context;
        this.watchOnlinePrototypes = watchOnlinePrototypes;

    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.downloads_list_item, parent, false);

        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(HolderView holder, final int position) {


        Glide.with(context).load(Uri.parse(watchOnlinePrototypes.get(position).getThumb_url())).thumbnail(0.5f).into(holder.thumbnail);
        holder.title.setText(watchOnlinePrototypes.get(position).getVideo_title());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // play the video here
                Intent intent = new Intent(context, VideoView.class);
                intent.putExtra("url", watchOnlinePrototypes.get(position).getVideo_url());
                intent.putExtra("thumb", watchOnlinePrototypes.get(position).getThumb_url());
                intent.putExtra("title", watchOnlinePrototypes.get(position).getVideo_title());
                context.startActivity(intent);
            }
        });

        holder.image_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here download the video
                DownloadFile downloadFile = new DownloadFile();
                downloadFile.execute(watchOnlinePrototypes.get(position).getVideo_url(), watchOnlinePrototypes.get(position).getVideo_title() + ".mp4");
            }
        });
    }

    @Override
    public int getItemCount() {
        return watchOnlinePrototypes.size();
    }

    class HolderView extends RecyclerView.ViewHolder {

        TextView title;
        ImageView thumbnail;
        ImageView image_download;


        public HolderView(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_freevideos);
            thumbnail = (ImageView) itemView.findViewById(R.id.imageview_freevideos);
            image_download = (ImageView) itemView.findViewById(R.id.downloadbutton_dowlnoads);


        }
    }


    class DownloadFile extends AsyncTask<String, String, Void> {
        private static final int MEGABYTE = 1024 * 1024;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setMessage("Downloading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            Toast.makeText(context, "you can check your downloaded video in file manager in edutec folder !!!!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Edutec");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                int totalSize = urlConnection.getContentLength();
                long total = 0;
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    total += bufferLength;
                    fileOutputStream.write(buffer, 0, bufferLength);
                    publishProgress("" + (int) ((total * 100) / totalSize));
                }
                fileOutputStream.close();
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
