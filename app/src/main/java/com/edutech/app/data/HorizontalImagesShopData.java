package com.edutech.app.data;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.edutech.app.adapter.HorizontalImagesAdapter;
import com.edutech.app.prototype.HorizontalImagesShopPrototype;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by vedant on 2/6/2018.
 */

public class HorizontalImagesShopData {
    public RecyclerView recyclerView;
    public Context context;
    public ArrayList<HorizontalImagesShopPrototype> horizontalImagesShopPrototypes = new ArrayList<>();

    public HorizontalImagesShopData(Context context, RecyclerView recyclerView, final String node, final String x, final String y, final String phone, final String name, final String address) {
        this.context = context;
        this.recyclerView = recyclerView;


        FirebaseDatabase.getInstance().getReference().child("our_shops").child(node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                horizontalImagesShopPrototypes.clear();
                dataSnapshot = dataSnapshot.child("images");
                getUpdates(dataSnapshot,node,x,y,phone,name,address);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUpdates(DataSnapshot dataSnapshot,String node,String x,String y,String phone,String name,String address) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String im = ds.getValue().toString().trim();
            horizontalImagesShopPrototypes.add(new HorizontalImagesShopPrototype(im));
        }
        if (horizontalImagesShopPrototypes.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);

            HorizontalImagesAdapter horizontalImagesAdapter = new HorizontalImagesAdapter(context, horizontalImagesShopPrototypes,node,x,y,phone,name,address);

            recyclerView.setAdapter(horizontalImagesAdapter);
        } else {
            // no data or images found in the database for the shop
        }

    }

}
