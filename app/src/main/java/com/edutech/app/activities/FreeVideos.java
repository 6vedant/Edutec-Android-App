package com.edutech.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.adapter.VideosAdapter;
import com.edutech.app.data.FreeVideosRecycerViewData;
import com.edutech.app.playerLib.GiraffePlayer;
import com.edutech.app.playerLib.GiraffePlayerActivity;
import com.edutech.app.prototype.VideosListPrototype;
import com.edutech.app.subActivities.BuyVideosList;
import com.edutech.app.subActivities.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class FreeVideos extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_free_videos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Quick Demo");

        recyclerView = (RecyclerView)findViewById(R.id.recycerview_freevideos);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        relativeLayout = (RelativeLayout)findViewById(R.id.relativelayout_freevideos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        try{
            FreeVideosRecycerViewData freeVideosRecycerViewData = new FreeVideosRecycerViewData(FreeVideos.this,progressBar,recyclerView,relativeLayout);

        }catch (Exception e){
            Toast.makeText(FreeVideos.this,""+e,Toast.LENGTH_SHORT).show();
        }



    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
