package com.edutech.app.subActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import com.edutech.app.R;
import com.edutech.app.adapter.Unable2FindPackagesAdapter;

public class Unable2FindPackage extends AppCompatActivity {
    public ListView listView;
    public Unable2FindPackagesAdapter unable2FindPackagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_unable2_find_package);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        listView = (ListView)findViewById(R.id.listview_findallpackages);
        unable2FindPackagesAdapter = new Unable2FindPackagesAdapter(Unable2FindPackage.this);
        listView.setAdapter(unable2FindPackagesAdapter);

    }


    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

    }
}
