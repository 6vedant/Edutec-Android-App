package com.edutech.app.data;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.edutech.app.others.StorageClass;
import com.edutech.app.prototype.Unable2FindPackagesPrototype;
import com.edutech.app.subActivities.Unable2FindPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vedant on 5/9/17.
 */

public class Unable2FindPackagesData {
    public static ProgressDialog progressDialog;
    public static ArrayList<Unable2FindPackagesPrototype> unable2FindPackagesPrototypes = new ArrayList<>();
    public static  List<String> packLocatioin = new ArrayList<>();

    public static List<String> sdLocation = new ArrayList<>();

    public static List<String> getSdLocation() {
        return sdLocation;
    }

    public static void setSdLocation(List<String> sdLocation) {
        Unable2FindPackagesData.sdLocation = sdLocation;
    }

    public static void setPackLocatioin(List<String> packLocatioin) {
        Unable2FindPackagesData.packLocatioin = packLocatioin;
    }

    public static List<String> getPackLocatioin() {
        return packLocatioin;
    }

    public static void loadData(Context context){
        unable2FindPackagesPrototypes.clear();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Packages...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        new SearchPackage().execute();

      //  Toast.makeText(context,""+getPackLocatioin().get(0),Toast.LENGTH_SHORT).show();
        if(StorageClass.sdPresent()){
        //    Toast.makeText(context,""+getSdLocation().get(0),Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"SD card is present",Toast.LENGTH_SHORT).show();
        }


    }


    public static class SearchPackage extends AsyncTask<String,String,Void>{

        @Override
        protected Void doInBackground(String... params) {
             List<String> list = new ArrayList<>();
             List<String> list1 = new ArrayList<>();
            list.clear();
            // now search the packages inside the internal storage

            File internalStorageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/");
            list = StorageClass.giveAllFolderPresent(internalStorageFile,"_edutec");
            if(StorageClass.sdPresent()){
                list1.clear();
                list1 = StorageClass.giveAllFolderPresent(new File(StorageClass.getSdCardPath()),"_edutec");
                setSdLocation(list1);
            }
            setPackLocatioin(list);
            return  null;
        }

        @Override
        public void onPostExecute(Void avoid){
            progressDialog.dismiss();
           // first add the packages in the prototype of arraylist in internal storage

            for(int i=0;i<getPackLocatioin().size();i++){
                 String title = "";
                try {
                    title = StorageClass.readFile(getPackLocatioin().get(i)+"/data/data.txt");
                } catch (IOException e) {
                    title = getPackLocatioin().get(i).replace(Environment.getExternalStorageDirectory().getAbsolutePath(),"");
                    e.printStackTrace();
                }
                unable2FindPackagesPrototypes.add(new Unable2FindPackagesPrototype(title,getPackLocatioin().get(i)));
            }
            // now add the sd located packages in the prototypes
            if(StorageClass.sdPresent()){
                for(int i=0;i<getSdLocation().size();i++){
                    String title = "";
                    try {
                        title = StorageClass.readFile(getSdLocation().get(i)+"/data/data.txt");
                    } catch (IOException e) {
                        title = getSdLocation().get(i).replace(StorageClass.getSdCardPath(),"");
                        e.printStackTrace();
                    }
                    unable2FindPackagesPrototypes.add(new Unable2FindPackagesPrototype(title,getSdLocation().get(i)));
                }
            }
        }
    }

}
