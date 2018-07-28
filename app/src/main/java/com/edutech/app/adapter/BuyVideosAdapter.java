package com.edutech.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.data.BuyVideosListData;
import com.edutech.app.prototype.BuyVideosPrototype;
import com.edutech.app.prototype.NavigationItemPrototype;

import java.io.IOException;
import java.util.List;

import static com.edutech.app.data.BuyVideosListData.buyVideosPrototypes;
import static com.edutech.app.data.NavigationData.navigationItemPrototypes;

/**
 * Created by vedant on 2/9/17.
 */

public class BuyVideosAdapter extends BaseAdapter {

    public Context context;
    private LayoutInflater layoutInflater;
    public ProgressBar progressBar;

    public BuyVideosAdapter(Context c,ProgressBar progressBar) {

        try {
            context = c;
            this.progressBar = progressBar;
            layoutInflater = LayoutInflater.from(c);
            BuyVideosListData buyVideosListData = new BuyVideosListData(c, progressBar);

        }catch (Exception e){
            Toast.makeText(context,"44 "+e,Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    public int getCount() {
        return buyVideosPrototypes.size();
    }

    @Override
    public Object getItem(int position) {
        return buyVideosPrototypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout deviceLayout = (LinearLayout) layoutInflater.inflate(R.layout.listviewitem_buyvideospackagenames, parent, false);

        TextView cbse_tv = (TextView) deviceLayout.findViewById(R.id.cbse_textview);
        TextView classx_tv = (TextView) deviceLayout.findViewById(R.id.classx_textview);
        TextView subject_tv = (TextView) deviceLayout.findViewById(R.id.economics_textview);
        TextView packagetitle_tv = (TextView) deviceLayout.findViewById(R.id.packagetitle_textview);
        TextView aspercbseguidlines_tv = (TextView) deviceLayout.findViewById(R.id.aspercbseguidlines_textview);
        TextView totalvideos_tv = (TextView) deviceLayout.findViewById(R.id.totalvideos_textview);
        TextView totalchapters_tv = (TextView) deviceLayout.findViewById(R.id.totalchapters_textview);
        TextView totalhoursclock_tv = (TextView) deviceLayout.findViewById(R.id.totalhoursclock_textview);
        TextView isActivated = (TextView) deviceLayout.findViewById(R.id.isactiivated_text);


        BuyVideosPrototype buyVideosPrototype = buyVideosPrototypes.get(position);

        cbse_tv.setText(buyVideosPrototype.getCbse());
        classx_tv.setText(buyVideosPrototype.getClassX());
        subject_tv.setText(buyVideosPrototype.getSubject());
        packagetitle_tv.setText(buyVideosPrototype.getPackageName());
        aspercbseguidlines_tv.setText(buyVideosPrototype.getCbseGudilines());
        totalvideos_tv.setText(buyVideosPrototype.getNumVideos());
        totalchapters_tv.setText(buyVideosPrototype.getNumChapters());
        totalhoursclock_tv.setText(buyVideosPrototype.getTotalHours());


        if (buyVideosPrototype.isActivated()) {
            isActivated.setText("Activated");
            isActivated.setTextColor(Color.GREEN);
        }


        deviceLayout.setTag(position);


        return deviceLayout;

    }
}
