package com.edutech.app.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edutech.app.others.StorageClass;
import com.edutech.app.prototype.BuyVideosPrototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;

/**
 * Created by vedant on 2/9/17.
 */

public class BuyVideosListData {

    public ProgressBar progressBar;
    public Context context;

    public static ArrayList<BuyVideosPrototype> buyVideosPrototypes = new ArrayList<>();

    public BuyVideosListData(Context context, ProgressBar progressBar) {

        this.context = context;
        this.progressBar = progressBar;
        buyVideosPrototypes.clear();
        SearchFile searchFile = new SearchFile();
        searchFile.execute("_edutec",null,null);

    }


    class SearchFile extends AsyncTask<String, String, String> {

        public List<String> temp_path;

        public List<String> getTemp_path() {
            return temp_path;
        }

        public void setTemp_path(List<String> temp_path) {
            this.temp_path = temp_path;
        }

        @Override
        protected String doInBackground(String... strings) {


            try {
                String filename = strings[0];
                File file = Environment.getExternalStorageDirectory().getAbsoluteFile();
                File file1 = new File(StorageClass.getSdCardPath());
                List<String> list = getFiles(filename, file);
                List<String> list1 = getFiles(filename, file1);

                List<String> temp = new ArrayList<>(list);
                temp.addAll(list1);
                setTemp_path(temp);
            } catch (Exception e) {
                Toast.makeText(context, "77 " + e, Toast.LENGTH_SHORT).show();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {


            progressBar.setVisibility(View.INVISIBLE);
            if (getTemp_path().size() > 0) {
                      //here the code begins



            } else {
                // the list is empty
                Toast.makeText(context, "No package found in storage...", Toast.LENGTH_SHORT).show();
            }
        }


        public List<String> getFiles(String filename, File file) {

            List<String> list = new ArrayList<>();

            try {
                list.clear();
                for (File f : file.listFiles()) {
                    if (f.isDirectory()) {
                        if (f.getName().contains("" + filename)) {

                            list.add(f.getAbsolutePath());

                        } else {
                            List<String> list1 = new ArrayList<>();
                            list1.clear();
                            list1 = getFiles(filename, f);
                            if (list1.size() > 0) {
                                for (int i = 0; i < list1.size(); i++) {
                                    list.add(list1.get(i));
                                }
                            }
                        }
                    }


                }

            } catch (Exception e) {
                Toast.makeText(context, "120 " + e, Toast.LENGTH_SHORT).show();
            }
            return list;
        }


    }


}
