package com.edutech.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edutech.app.R;
import com.edutech.app.data.VideosData;
import com.edutech.app.prototype.NavigationItemPrototype;
import com.edutech.app.prototype.VideosListPrototype;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.edutech.app.data.NavigationData.navigationItemPrototypes;
import static com.edutech.app.data.VideosData.videosListPrototypes;

/**
 * Created by vedant on 1/9/17.
 */

public class VideosAdapter extends BaseAdapter {
    public Context context;
    public int packPos;

    public int getPackPos() {
        return packPos;
    }

    public void setPackPos(int packPos) {
        this.packPos = packPos;
    }

    private LayoutInflater layoutInflater;
    public int startPackagePosition;

    public int getStartPackagePosition() {
        return startPackagePosition;
    }

    public void setStartPackagePosition(int startPackagePosition) {
        this.startPackagePosition = startPackagePosition;
    }

    public List<String> getPackaeList() {
        return packaeList;
    }

    public void setPackaeList(List<String> packaeList) {
        this.packaeList = packaeList;
    }

    public List<String> packaeList;

    public VideosAdapter(Context c, List<String> list) {
        layoutInflater = LayoutInflater.from(c);
        setPackaeList(list);
        context = c;
        setStartPackagePosition(0);
        setPackPos(0);
        VideosData.loadData(list);

    }

    @Override
    public int getCount() {
        return videosListPrototypes.size();
    }

    @Override
    public Object getItem(int position) {
        return videosListPrototypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout deviceLayout = (LinearLayout) layoutInflater.inflate(R.layout.listitem_freevideos, parent, false);
        TextView title = (TextView) deviceLayout.findViewById(R.id.title_freevideos);
        ImageView imageView = (ImageView) deviceLayout.findViewById(R.id.imageview_freevideos);
        LinearLayout titlePackageLayout = (LinearLayout) deviceLayout.findViewById(R.id.layout_title_freevideos);
        TextView titlePackage = (TextView) deviceLayout.findViewById(R.id.title_freevideos);

        if (position == 0) {
            setStartPackagePosition(0);
            setPackPos(0);
        }

        if (getStartPackagePosition() == position && getPackPos() < getPackaeList().size()) {

            titlePackageLayout.setVisibility(View.VISIBLE);
            File textFile = new File(getPackaeList().get(getPackPos()) + "/data/data.txt");
            File vidFile = new File(getPackaeList().get(getPackPos()) + "/free/video");
            if (textFile.exists()) {
                try {
                    titlePackage.setText(readFile(textFile.getAbsolutePath()).substring(0, 12) + "...");
                    int count = 0;
                    for (File f : vidFile.listFiles()) {
                        count++;
                    }
                    setPackPos(getPackPos() + 1);
                    setStartPackagePosition(getStartPackagePosition() + count);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        VideosListPrototype videosListPrototype = videosListPrototypes.get(position);

        title.setText(videosListPrototype.getTitle());
        Picasso.with(context).load(new File(videosListPrototype.getThumbnail_url())).fit().into(imageView);
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
