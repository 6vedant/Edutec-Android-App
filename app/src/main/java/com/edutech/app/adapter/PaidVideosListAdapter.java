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
import com.edutech.app.data.PaidVideosListData;
import com.edutech.app.prototype.VideosListPrototype;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.edutech.app.data.PaidVideosListData.videosListPrototypes1;

/**
 * Created by vedant on 2/9/17.
 */

public class PaidVideosListAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater layoutInflater;
    public PaidVideosListAdapter(Context c, String path){
        layoutInflater = LayoutInflater.from(c);
        context = c;
        PaidVideosListData.loadData(path,c);
    }


    @Override
    public int getCount() {
        return videosListPrototypes1.size();
    }

    @Override
    public Object getItem(int position) {
        return videosListPrototypes1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout deviceLayout = (LinearLayout)layoutInflater.inflate(R.layout.listitem_freevideos,parent,false);
        TextView title = (TextView)deviceLayout.findViewById(R.id.title_freevideos);
        ImageView imageView = (ImageView)deviceLayout.findViewById(R.id.imageview_freevideos);
        LinearLayout titlePackageLayout = (LinearLayout)deviceLayout.findViewById(R.id.layout_title_freevideos);
        TextView titlePackage = (TextView)deviceLayout.findViewById(R.id.title_freevideos);
        VideosListPrototype videosListPrototype = videosListPrototypes1.get(position);

        title.setText(videosListPrototype.getTitle());
        Picasso.with(context).load(new File(videosListPrototype.getThumbnail_url())).into(imageView);
        deviceLayout.setTag(position);


        return deviceLayout;
    }


    public String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
