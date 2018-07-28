package com.edutech.app.data;

import android.content.Context;
import android.widget.Toast;

import com.edutech.app.others.StorageClass;
import com.edutech.app.prototype.VideosListPrototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vedant on 2/9/17.
 */

public class PaidVideosListData {
    public static ArrayList<VideosListPrototype> videosListPrototypes1 = new ArrayList<>();

    public static void loadData(String path, Context context) {
        videosListPrototypes1.clear();


        File videosFile = new File(path + "/content/video");
        File thumbnailsFile = new File(path + "/content/thumbnail");
        File dataFile = new File(path + "/content/data/data.txt");
        List<String> titleVideos = new ArrayList<>();
        List<String> thumb_file = new ArrayList<>();
        List<String> videoPath = new ArrayList<>();
        videoPath.clear();
        thumb_file.clear();
        titleVideos.clear();

        int count = 0;
        for (File f1 : videosFile.listFiles()) {
            count++;

        }
        for (int i = 1; i <= count; i++) {
            thumb_file.add("");
            videoPath.add("");
        }

        for (File videoFile : videosFile.listFiles()) {
            String videoNameID = videoFile.getName().substring(0, 2);
            if (videoNameID.charAt(0) == '0') {
                videoNameID = videoNameID.substring(1);
            }
            int id = Integer.parseInt(videoNameID);
            if (id <= count) {
                videoPath.set(id - 1, videoFile.getAbsolutePath());
            }
        }

        for (File thumbFile : thumbnailsFile.listFiles()) {
            String thumbNameID = thumbFile.getName().substring(0, 2);
            if (thumbNameID.charAt(0) == '0') {
                thumbNameID = thumbNameID.substring(1);
            }
            int id = Integer.parseInt(thumbNameID);
            if (id <= count) {
                thumb_file.set(id - 1, thumbFile.getAbsolutePath());
            }
        }

        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(dataFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

//Read File Line By Line
        try {
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                titleVideos.add(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//Close the input stream
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // now adding all the list in the arraylist of the listview data
        for (int i = 0; i < videoPath.size(); i++) {
            videosListPrototypes1.add(new VideosListPrototype(titleVideos.get(i), thumb_file.get(i), videoPath.get(i), false));
        }
    }

}

