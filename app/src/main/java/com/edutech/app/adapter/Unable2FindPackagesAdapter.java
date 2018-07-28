package com.edutech.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edutech.app.R;
import com.edutech.app.data.Unable2FindPackagesData;
import com.edutech.app.prototype.Unable2FindPackagesPrototype;

import static com.edutech.app.data.Unable2FindPackagesData.unable2FindPackagesPrototypes;

/**
 * Created by vedant on 5/9/17.
 */

public class Unable2FindPackagesAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    public Context context;
    public Unable2FindPackagesAdapter(Context c){
        layoutInflater = LayoutInflater.from(c);
        context = c;
        Unable2FindPackagesData.loadData(c);
    }
    @Override
    public int getCount() {
        return unable2FindPackagesPrototypes.size();
    }

    @Override
    public Object getItem(int position) {
        return unable2FindPackagesPrototypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout deviceLayout = (LinearLayout)layoutInflater.inflate(R.layout.listviewitem_buyvideospackagenames,parent,false);
      //  TextView tv1 = (TextView)deviceLayout.findViewById(R.id.buyvideoslistitem_packagename);


        Unable2FindPackagesPrototype unable2FindPackagesPrototype = unable2FindPackagesPrototypes.get(position);

      //  tv1.setText(unable2FindPackagesPrototype.getTitle());
        deviceLayout.setTag(position);


        return deviceLayout;

    }
}
