package com.edutech.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edutech.app.R;
import com.edutech.app.data.NavigationData;
import com.edutech.app.prototype.NavigationItemPrototype;

import static com.edutech.app.data.NavigationData.navigationItemPrototypes;

/**
 * Created by vedant on 31/8/17.
 */

public class NavigationItemAdapter extends BaseAdapter {
    public Context context;
    private LayoutInflater layoutInflater;
        public NavigationItemAdapter(Context c){
        layoutInflater = LayoutInflater.from(c);
        context = c;
        NavigationData.loadData();
    }
    @Override
    public int getCount() {
        return navigationItemPrototypes.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationItemPrototypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout deviceLayout = (LinearLayout)layoutInflater.inflate(R.layout.listitem_navigation,parent,false);
        TextView tv1 = (TextView)deviceLayout.findViewById(R.id.textview_navigation);
        ImageView nav_img = (ImageView)deviceLayout.findViewById(R.id.navimage_imageview);
        NavigationItemPrototype navigationListViewPrototype = navigationItemPrototypes.get(position);

        tv1.setText(navigationListViewPrototype.getTitle());
        nav_img.setImageResource(navigationListViewPrototype.getImage_id());
        deviceLayout.setTag(position);


        return deviceLayout;

    }
}
