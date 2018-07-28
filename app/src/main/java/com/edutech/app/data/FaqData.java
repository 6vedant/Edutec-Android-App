package com.edutech.app.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.edutech.app.adapter.FaqAdapter;
import com.edutech.app.prototype.FaqPrototype;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by vedant on 1/18/2018.
 */

public class FaqData {
    public Context context;
    public ArrayList<FaqPrototype> faqPrototypes = new ArrayList<>();

    public RecyclerView recyclerView;

    public FaqData(final Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;

        FirebaseDatabase.getInstance().getReference().child("faqs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(context, "Unable  to load faqs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUpdates(DataSnapshot dataSnapshot) {

        faqPrototypes.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            FaqPrototype faqPrototype = new FaqPrototype();
            String question = ds.child("question").getValue().toString().trim();
            String answer = ds.child("answer").getValue().toString().trim();
            faqPrototype.setAnswer(answer);
            faqPrototype.setQuestion(question);

            faqPrototypes.add(faqPrototype);
        }

        if (faqPrototypes.size() > 0) {

            FaqAdapter faqAdapter = new FaqAdapter(context, faqPrototypes);
            recyclerView.setAdapter(faqAdapter);

        } else {
            Toast.makeText(context, "No data available for the faqs", Toast.LENGTH_SHORT).show();
        }
    }
}
