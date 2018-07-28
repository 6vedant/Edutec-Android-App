package com.edutech.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edutech.app.R;
import com.edutech.app.prototype.HorizontalImagesShopPrototype;
import com.edutech.app.subActivities.ShopsActivityDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vedant on 2/6/2018.
 */

public class HorizontalImagesAdapter extends RecyclerView.Adapter<HorizontalImagesAdapter.MyViewHolder> {

    Context context;
    public ArrayList<HorizontalImagesShopPrototype> horizontalImagesShopPrototypes;
   public String x,y,name,key,phone,address;

    public HorizontalImagesAdapter(Context context, ArrayList<HorizontalImagesShopPrototype> horizontalImagesShopPrototypes,String key,String x,String y,String phone,String name,String address) {
        this.context = context;
        this.horizontalImagesShopPrototypes = horizontalImagesShopPrototypes;
        this.x = x;
        this.y = y;
        this.name = name;
        this.key = key;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shops_item_horizontalimage, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShopsActivityDetails.class);
                intent.putExtra("lat", x);
                intent.putExtra("lon", y);
                intent.putExtra("name", name);
                intent.putExtra("key", key);
                intent.putExtra("phone", phone);
                intent.putExtra("address", address);
                context.startActivity(intent);
            }
        });

        if(horizontalImagesShopPrototypes.get(position).getUrl()!=null){

            Glide.with(context).load(Uri.parse(horizontalImagesShopPrototypes.get(position).getUrl())).thumbnail(0.2f).into(holder.imageView);

        }
    }

    @Override
    public int getItemCount() {
        return horizontalImagesShopPrototypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview_itemshopshorizontalrecyerview);
        }
    }
}
