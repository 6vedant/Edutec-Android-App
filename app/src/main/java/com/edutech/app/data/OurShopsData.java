package com.edutech.app.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.edutech.app.activities.OurShops;
import com.edutech.app.adapter.OurShopsAdapter;
import com.edutech.app.prototype.BuyVideosPrototype;
import com.edutech.app.prototype.HorizontalImagesShopPrototype;
import com.edutech.app.prototype.OurShopsPrototype;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by vedant on 1/17/2018.
 */

public class OurShopsData {
    public Context context;
    public ArrayList<OurShopsPrototype> ourShopsPrototypes = new ArrayList<>();

    public RecyclerView recyclerView;

    public OurShopsData(final Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        ourShopsPrototypes.clear();

        FirebaseDatabase.getInstance().getReference().child("our_shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(context, "Unable to load data...please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUpdates(DataSnapshot dataSnapshot) {
        ourShopsPrototypes.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            OurShopsPrototype ourShopsPrototype = new OurShopsPrototype();
            ourShopsPrototype.setShop_address(ds.child("address").getValue().toString().trim());
            ourShopsPrototype.setShop_name(ds.child("name").getValue().toString().trim());
            ourShopsPrototype.setShop_phone(ds.child("phone").getValue().toString().trim());
            ourShopsPrototype.setShop_gpsx(ds.child("lat").getValue().toString().trim());
            ourShopsPrototype.setShop_gpsy(ds.child("long").getValue().toString().trim());
            ourShopsPrototype.setKey(ds.getKey());

            /*DataSnapshot ds1  = ds.child("images");
            for(DataSnapshot ds2  : ds1.getChildren()){
                ArrayList<HorizontalImagesShopPrototype> horizontalImagesShopPrototypes = new ArrayList<>();
                horizontalImagesShopPrototypes.add(new HorizontalImagesShopPrototype(ds2.getValue().toString().trim()));
                try{

                }
            }*/


            ourShopsPrototypes.add(ourShopsPrototype);
        }

        if (ourShopsPrototypes.size() > 0) {
          //  Toast.makeText(context,""+ourShopsPrototypes.size(),Toast.LENGTH_SHORT).show();
            OurShopsAdapter ourShopsAdapter = new OurShopsAdapter(context, ourShopsPrototypes);
            recyclerView.setAdapter(ourShopsAdapter);
        } else {
            Toast.makeText(context, "No data available at the moment", Toast.LENGTH_SHORT).show();
        }
    }
}
