package com.edutech.app.activities;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.adapter.BuyVideosAdapter;
import com.edutech.app.data.BuyVideosRecycerData;
import com.edutech.app.prototype.BuyVideosPrototype;
import com.edutech.app.subActivities.BuyVideosList;
import com.edutech.app.subActivities.Unable2FindPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuyVideos extends AppCompatActivity {
    public ListView listView;
    public BuyVideosAdapter buyVideosAdapter;
    public ProgressBar progressBar;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_buy_videos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        setTitle("Start Course");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_buyvideos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        try{
            BuyVideosRecycerData buyVideosRecycerData = new BuyVideosRecycerData(BuyVideos.this,recyclerView,progressBar);
        }catch (Exception e){
            Toast.makeText(BuyVideos.this,""+e,Toast.LENGTH_SHORT).show();
        }



        //click listener for listview items
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BuyVideosPrototype buyVideosPrototype = (BuyVideosPrototype) parent.getItemAtPosition(position);
                //  Toast.makeText(getApplicationContext(),""+position+buyVideosPrototype.getPackagePath(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BuyVideosList.class);
                intent.putExtra("title", buyVideosPrototype.getSubject());
                intent.putExtra("path", buyVideosPrototype.getPackagePath());
                startActivity(intent);
            }
        });*/



    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
