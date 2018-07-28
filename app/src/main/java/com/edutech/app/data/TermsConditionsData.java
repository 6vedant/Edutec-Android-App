package com.edutech.app.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.adapter.TermsConditionsRecyclerViewAdapter;
import com.edutech.app.prototype.TermsConditonsPrototype;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by vedant on 1/15/2018.
 */

public class TermsConditionsData {
    public Context context;
    public ArrayList<TermsConditonsPrototype> termsConditonsPrototypes = new ArrayList<>();
    public RecyclerView recyclerView;
    public RelativeLayout layout;

    public TermsConditionsData(final Context context, RecyclerView recyclerView, final RelativeLayout layout){
        this.context =context;
        this.recyclerView = recyclerView;
        this.layout = layout;

        FirebaseDatabase.getInstance().getReference().child("terms_conditions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                layout.setBackgroundResource(R.drawable.oh_sucks_no_internet_connection);
                Toast.makeText(context,"Unable to load data",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUpdates(DataSnapshot dataSnapshot){
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            termsConditonsPrototypes.add(new TermsConditonsPrototype(ds.getValue().toString().trim()));
        }

        if(termsConditonsPrototypes.size()>0){
            TermsConditionsRecyclerViewAdapter termsConditionsRecyclerViewAdapter = new TermsConditionsRecyclerViewAdapter(context,termsConditonsPrototypes);
            recyclerView.setAdapter(termsConditionsRecyclerViewAdapter);

        }else{


        }
    }
}
