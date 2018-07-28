package com.edutech.app.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.data.TermsConditionsData;
import com.edutech.app.others.StorageClass;

public class TermsConditions extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RelativeLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_terms_conditions);
        if (!isNetworkAvailable(TermsConditions.this)) {
            layout = (RelativeLayout) findViewById(R.id.relativelayout_termscondtions);
            layout.setBackgroundResource(R.drawable.oh_sucks_no_internet_connection);

        }
        setTitle("Terms & Conditions");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.termscondition_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            TermsConditionsData termsConditionsData = new TermsConditionsData(TermsConditions.this, recyclerView, layout);
            progressBar.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to load data", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);

        }


    }


    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
