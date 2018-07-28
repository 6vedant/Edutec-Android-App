package com.edutech.app.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edutech.app.adapter.WatchOnlineAdapter;
import com.edutech.app.prototype.WatchOnlinePrototype;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by vedant on 1/10/2018.
 */

public class WatchOnlineData {

    public Context context;
    public ProgressDialog progressDialog;
    public ArrayList<WatchOnlinePrototype> watchOnlinePrototypes= new ArrayList<>();
    public RecyclerView recyclerView;

    public WatchOnlineData(final Context context, RecyclerView recyclerView){
        this.context =context;
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Videos...");

        progressDialog.show();

        FirebaseDatabase.getInstance().getReference().child("watch_online").child("packages").child("pa").child("videos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(context, "Failed to load data...try again", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void getUpdates(DataSnapshot dataSnapshot){
        watchOnlinePrototypes.clear();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            String thumb_url = ds.child("thumb_url").getValue().toString().trim();
            String video_title = ds.child("video_title").getValue().toString().trim();
            String video_url = ds.child("video_url").getValue().toString().trim();

            WatchOnlinePrototype watchOnlinePrototype = new WatchOnlinePrototype();
            watchOnlinePrototype.setThumb_url(thumb_url);
            watchOnlinePrototype.setVideo_title(video_title);
            watchOnlinePrototype.setVideo_url(video_url);


            watchOnlinePrototypes.add(watchOnlinePrototype);

        }

        if(watchOnlinePrototypes.size()>0){

            WatchOnlineAdapter watchOnlineAdapter = new WatchOnlineAdapter(context,watchOnlinePrototypes);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setHasFixedSize(true);

            recyclerView.setAdapter(watchOnlineAdapter);
        }else{
            Toast.makeText(context,"No data available at the moment...",Toast.LENGTH_SHORT).show();
        }
    }



}
