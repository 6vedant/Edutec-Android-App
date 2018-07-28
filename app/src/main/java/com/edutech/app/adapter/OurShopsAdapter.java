package com.edutech.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.data.HorizontalImagesShopData;
import com.edutech.app.prototype.OurShopsPrototype;
import com.edutech.app.subActivities.ShopsActivityDetails;

import java.util.ArrayList;

/**
 * Created by vedant on 1/17/2018.
 */

public class OurShopsAdapter extends RecyclerView.Adapter<OurShopsAdapter.MyViewHolder> {

    Context context;
    public ArrayList<OurShopsPrototype> ourShopsPrototypes;

    public OurShopsAdapter(Context context, ArrayList<OurShopsPrototype> ourShopsPrototypes) {
        this.context = context;
        this.ourShopsPrototypes = ourShopsPrototypes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.item_ourshops, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.shop_name.setText(ourShopsPrototypes.get(position).getShop_name());
        holder.shop_address.setText(ourShopsPrototypes.get(position).getShop_address());

        holder.gps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here it is the gps button
                String lat = ourShopsPrototypes.get(position).getShop_gpsx();
                String lng = ourShopsPrototypes.get(position).getShop_gpsy();

                String strUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + ourShopsPrototypes.get(position).getShop_name() + "" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                context.startActivity(intent);
            }
        });
        holder.textView_call.setText("Call: " + ourShopsPrototypes.get(position).getShop_phone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the activity
                Intent intent = new Intent(context, ShopsActivityDetails.class);
                intent.putExtra("lat", ourShopsPrototypes.get(position).getShop_gpsx());
                intent.putExtra("lon", ourShopsPrototypes.get(position).getShop_gpsy());
                intent.putExtra("name", ourShopsPrototypes.get(position).getShop_name());
                intent.putExtra("key", ourShopsPrototypes.get(position).getKey());
                intent.putExtra("phone", ourShopsPrototypes.get(position).getShop_phone());
                intent.putExtra("address", ourShopsPrototypes.get(position).getShop_address());
                context.startActivity(intent);
            }
        });


        holder.recyclerView_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShopsActivityDetails.class);
                intent.putExtra("lat", ourShopsPrototypes.get(position).getShop_gpsx());
                intent.putExtra("lon", ourShopsPrototypes.get(position).getShop_gpsy());
                intent.putExtra("name", ourShopsPrototypes.get(position).getShop_name());
                intent.putExtra("key", ourShopsPrototypes.get(position).getKey());
                intent.putExtra("phone", ourShopsPrototypes.get(position).getShop_phone());
                intent.putExtra("address", ourShopsPrototypes.get(position).getShop_address());
                context.startActivity(intent);
            }
        });

        holder.call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here calling intent shouild be called
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ourShopsPrototypes.get(position).getShop_phone()));
                context.startActivity(callIntent);

            }
        });

        if (ourShopsPrototypes.get(position).getKey() != null) {
            // now load all the images files here

            try {
                HorizontalImagesShopData horizontalImagesShopData = new HorizontalImagesShopData(context, holder.recyclerView_images, ourShopsPrototypes.get(position).getKey(), ourShopsPrototypes.get(position).getShop_gpsx(), ourShopsPrototypes.get(position).getShop_gpsy(), ourShopsPrototypes.get(position).getShop_phone(), ourShopsPrototypes.get(position).getShop_name(), ourShopsPrototypes.get(position).getShop_address());

            } catch (Exception e) {
                // catch it

            }
        }

    }

    @Override
    public int getItemCount() {
        return ourShopsPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name, shop_address;
        TextView textView_call;
        LinearLayout call_button, gps_button;
        RecyclerView recyclerView_images;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView_call = (TextView) itemView.findViewById(R.id.call_textview);
            shop_address = (TextView) itemView.findViewById(R.id.shopaddress_ourshop);
            shop_name = (TextView) itemView.findViewById(R.id.shopname_ourshops);
            call_button = (LinearLayout) itemView.findViewById(R.id.callshop_shopitem);
            gps_button = (LinearLayout) itemView.findViewById(R.id.gps_button_ourshopsitem);
            recyclerView_images = (RecyclerView) itemView.findViewById(R.id.horizontal_recylerview_shops);

        }
    }
}
