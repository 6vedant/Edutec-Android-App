package com.edutech.app.data;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.activities.OurShops;
import com.edutech.app.adapter.BuyVideosRecycerviewAdapter;
import com.edutech.app.others.StorageClass;
import com.edutech.app.prototype.BuyVideosPrototype;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vedant on 2/5/2018.
 */

public class BuyVideosRecycerData {

    public Context context;
    public ProgressBar progressBar;
    public static ArrayList<BuyVideosPrototype> buyVideosPrototypes = new ArrayList<>();
    public RecyclerView recyclerView;

    public BuyVideosRecycerData(Context context, RecyclerView recyclerView, ProgressBar progressBar) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
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
                //Toast.makeText(context, "77 " + e, Toast.LENGTH_SHORT).show();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {

            try {
                buyVideosPrototypes.clear();
                progressBar.setVisibility(View.INVISIBLE);
                if (getTemp_path().size() > 0) {
                    //here the code begins
                    for (int i = 0; i < getTemp_path().size(); i++) {



                        File f2 = new File(getTemp_path().get(i));
                        boolean bool = false;
                        File dataFile = new File(f2.getAbsolutePath() + "/data/data.txt");
                        File idFile = new File(f2.getAbsolutePath() + "/data/id.dll");
                        try {
                            String id = StorageClass.readFile(idFile.getAbsolutePath());
                            String fileName = "system" + id + ".dll";
                            File activationFIle = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/system_data_os/" + fileName);
                            if (activationFIle.exists()) {
                                bool = true;
                            }
                        } catch (IOException e) {
                            bool = false;
                            e.printStackTrace();
                        }
                        String data;
                        try {
                            FileReader fileReader = new FileReader(dataFile);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            StringBuffer stringBuffer = new StringBuffer();
                            List<String> strList = new ArrayList<>();
                            strList.clear();
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuffer.append(line);
                                stringBuffer.append("\n");
                                strList.add(line);
                            }

                            //buyVideosPrototypes.add(new BuyVideosPrototype(data,f2.getAbsolutePath()));

                            buyVideosPrototypes.add(new BuyVideosPrototype(strList.get(0), strList.get(1), strList.get(2),
                                    strList.get(3), strList.get(4), strList.get(5), strList.get(6), strList.get(7), f2.getAbsolutePath(), bool));
                            fileReader.close();
                            bool = false;

                        } catch (Exception e) {
                            e.printStackTrace();
                            bool = false;
                            //  Toast.makeText(context, "Error at 141  " + context + e, Toast.LENGTH_SHORT).show();
                        }


                    }


                } else {
                    // the list is empty
                    //Toast.makeText(context, "No package found in storage...", Toast.LENGTH_SHORT).show();

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


                BuyVideosRecycerviewAdapter buyVideosRecycerviewAdapter = new BuyVideosRecycerviewAdapter(context, progressBar, buyVideosPrototypes);
                recyclerView.setAdapter(buyVideosRecycerviewAdapter);

            } catch (Exception e) {
                //Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(context, "120 " + e, Toast.LENGTH_SHORT).show();
            }
            return list;
        }


    }
}