package com.edutech.app.data;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.activities.OurShops;
import com.edutech.app.adapter.BuyVideosRecycerviewAdapter;
import com.edutech.app.adapter.FreeVideosRecycerviewAdapter;
import com.edutech.app.others.StorageClass;
import com.edutech.app.prototype.BuyVideosPrototype;
import com.edutech.app.prototype.VideosListPrototype;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vedant on 2/6/2018.
 */

public class FreeVideosRecycerViewData {
    Context context;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;

    public ArrayList<VideosListPrototype> videosListPrototypes = new ArrayList<>();

    public FreeVideosRecycerViewData(Context context, ProgressBar progressBar, RecyclerView recyclerView, RelativeLayout relativeLayout) {
        this.context = context;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.relativeLayout = relativeLayout;

        SearchFile searchFile = new SearchFile();
        searchFile.execute("_edutec", null, null);


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
                List<String> list = getFiles(filename, file);
                if (StorageClass.sdCardLocation() != null) {
                    File file1 = new File(StorageClass.sdCardLocation());
                    if (file1 != null) {
                        List<String> list1 = getFiles(filename, file1);

                        for (int i = 0; i < list1.size(); i++) {
                            list.add(list1.get(i));
                        }
                    }

                }

                setTemp_path(list);
            } catch (Exception e) {
                //   Toast.makeText(context, "77 " + e, Toast.LENGTH_SHORT).show();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {

            videosListPrototypes.clear();
            progressBar.setVisibility(View.INVISIBLE);

            // here add all the videos in videoslistprototypes which is present

            if (getTemp_path().size() > 0) {

                for (int i = 0; i < getTemp_path().size(); i++) {

                    // now adding all the free videos from all the pacakges
                    try {
                        boolean bool = false;
                        // now check if the package ie- list.get(i) is activated or not
                        File idFile = new File(getTemp_path().get(i) + "/data/id.dll");
                        String id = StorageClass.readFile(idFile.getAbsolutePath());
                        String activationFileName = "system" + id + ".dll";
                        File activationFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/android/data/system_data_os/" + activationFileName);
                        if (activationFile.exists()) {
                            bool = true;
                        }


                        File videosFile = new File(getTemp_path().get(i) + "/free/video");
                        File thumbnailsFile = new File(getTemp_path().get(i) + "/free/thumbnail");
                        File dataFile = new File(getTemp_path().get(i) + "/free/data/data.txt");
                        List<String> titleVideos = new ArrayList<>();
                        titleVideos.clear();
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
                        int j = -1;
                        for (File f : videosFile.listFiles()) {              //this loop is for files in a package
                            j++;
                            videosListPrototypes.add(new VideosListPrototype(titleVideos.get(j), thumbnailsFile.getAbsolutePath() + "/" + f.getName().replace("mp4", "png"), f.getAbsolutePath(), bool));

                            bool = false;
                        }

                    } catch (Exception e) {
                        //  Toast.makeText(context, "138 : " + e, Toast.LENGTH_SHORT).show();
                    }

                }
            } else {
                // itdoesn't contain any free videos or pacakge
                // Toast.makeText(context, "No package found in storage...contact our vendors", Toast.LENGTH_SHORT).show();
                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
                dialogBuilder
                        .withTitle(null)                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage("Content Package Not Found")                     //.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)
                        .withIcon(context.getResources().getDrawable(R.drawable.logo))
                        .withDuration(700)                                          //def
                        .withEffect(Effectstype.Shake)                                         //def Effectstype.Slidetop
                        .withButton1Text("Content Vendors")                                      //def gone
                        .withButton2Text("Ok")                                  //def gone
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .setCustomView(R.layout.no_package_found, context)         //.setCustomView(View or ResId,context)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(context, OurShops.class);
                                context.startActivity(intent);
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                             dialogBuilder.dismiss();
                            }
                        })
                        .show();


            }


            //Toast.makeText(context, "157" + getTemp_path().size() + " videos: " + videosListPrototypes.size(), Toast.LENGTH_SHORT).show();
            FreeVideosRecycerviewAdapter freeVideosRecycerviewAdapter = new FreeVideosRecycerviewAdapter(context, progressBar, videosListPrototypes);
            recyclerView.setAdapter(freeVideosRecycerviewAdapter);
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
                //  Toast.makeText(context, "120 " + e, Toast.LENGTH_SHORT).show();
            }
            return list;
        }


    }
}
