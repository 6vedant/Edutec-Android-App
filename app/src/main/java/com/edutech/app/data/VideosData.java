package com.edutech.app.data;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;

import com.edutech.app.others.StorageClass;
import com.edutech.app.prototype.VideosListPrototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vedant on 1/9/17.
 */

public class VideosData {
    public static ArrayList<VideosListPrototype> videosListPrototypes = new ArrayList<>();

    public static void loadData(List<String> list) {

        videosListPrototypes.clear();
        for (int i = 0; i < list.size(); i++) {          // this for loop is for packages

            boolean bool = false;
            // now check if the package ie- list.get(i) is activated or not
            File idFile = new File(list.get(i) + "/data/id.dll");
            try {
                String id = StorageClass.readFile(idFile.getAbsolutePath());
                String activationFileName = "system" + id + ".dll";
                File activationFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/android/data/system_data_os/" + activationFileName);
                if (activationFile.exists()) {
                    bool = true;
                }
            } catch (Exception e) {
                bool = false;
                e.printStackTrace();
            }

            File videosFile = new File(list.get(i) + "/free/video");
            File thumbnailsFile = new File(list.get(i) + "/free/thumbnail");
            File dataFile = new File(list.get(i) + "/free/data/data.txt");
            List<String> titleVideos = new ArrayList<>();
            titleVideos.clear();
            try {
                FileInputStream fstream = new FileInputStream(dataFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                String strLine;

//Read File Line By Line
                while ((strLine = br.readLine()) != null) {
                    // Print the content on the console
                    titleVideos.add(strLine);
                }

//Close the input stream
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int j = -1;
            for (File f : videosFile.listFiles()) {              //this loop is for files in a package
                j++;
                videosListPrototypes.add(new VideosListPrototype(titleVideos.get(j), thumbnailsFile.getAbsolutePath() + "/" + f.getName().replace("mp4", "png"), f.getAbsolutePath(), bool));

                bool = false;
            }


        }

    }
}
