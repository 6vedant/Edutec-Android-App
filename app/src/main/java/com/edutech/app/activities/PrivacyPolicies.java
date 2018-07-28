package com.edutech.app.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.others.StorageClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrivacyPolicies extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7, t8;
    ProgressBar progressBar;
    RelativeLayout relativeLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_privacy_policies);
        setTitle("Privacy Policies");

        if(!isNetworkAvailable(PrivacyPolicies.this)){
            relativeLayout =(RelativeLayout)findViewById(R.id.layout_privacypolicies);
            relativeLayout.setBackgroundResource(R.drawable.oh_sucks_no_internet_connection);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        FirebaseDatabase.getInstance().getReference().child("privacy_policies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              getUpdates(dataSnapshot);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    public void getUpdates(DataSnapshot dataSnapshot){
        String one  = dataSnapshot.child("one").getValue().toString().trim();
        String two = dataSnapshot.child("two").getValue().toString().trim();
        String three  = dataSnapshot.child("three").getValue().toString().trim();
        String four = dataSnapshot.child("four").getValue().toString().trim();
        String five = dataSnapshot.child("five").getValue().toString().trim();
        String six = dataSnapshot.child("six").getValue().toString().trim();
        String seven = dataSnapshot.child("seventh").getValue().toString().trim();
        String eigth = dataSnapshot.child("eigth").getValue().toString().trim();

        t1.setText(one);
        t2.setText(two);
        t3.setText(three);
        t4.setText(four);
        t5.setText(five);
        t6.setText(six);
        t7.setText(seven);
        t8.setText(eigth);


    }

    public void init(){
        t1 = (TextView)findViewById(R.id.first_para);
        t2 = (TextView)findViewById(R.id.second_para);
        t3 = (TextView)findViewById(R.id.third_para);
        t4 = (TextView)findViewById(R.id.fourth_para);
        t5 = (TextView)findViewById(R.id.fivth_para);
        t6 = (TextView)findViewById(R.id.sixth_para);
        t7 = (TextView)findViewById(R.id.seventh_para);
        t8 = (TextView)findViewById(R.id.eighth_para);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
