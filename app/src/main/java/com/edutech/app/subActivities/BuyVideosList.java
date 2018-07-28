package com.edutech.app.subActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.adapter.PaidVideosListAdapter;
import com.edutech.app.others.StorageClass;
import com.edutech.app.prototype.VideosListPrototype;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;

public class BuyVideosList extends AppCompatActivity {
    public ProgressDialog progressDialog;
    public ListView listView;
    public PaidVideosListAdapter paidVideosListAdapter;
    public List<String> list;
    public String remoteFileLocation;
    public String remoteFilePurchaseLocation;

    public String demoStr;

    public String getDemoStr() {
        return demoStr;
    }

    public void setDemoStr(String demoStr) {
        this.demoStr = demoStr;
    }

    public String getRemoteFilePurchaseLocation() {
        return remoteFilePurchaseLocation;
    }

    public void setRemoteFilePurchaseLocation(String remoteFilePurchaseLocation) {
        this.remoteFilePurchaseLocation = remoteFilePurchaseLocation;
    }

    public String getRemoteFileLocation() {
        return remoteFileLocation;
    }

    public void setRemoteFileLocation(String remoteFileLocation) {
        this.remoteFileLocation = remoteFileLocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final String path = getIntent().getStringExtra("path");
        setContentView(R.layout.activity_buy_videos_list);
        list = new ArrayList<>();
        list.clear();
        list.add(path);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.listview_buyvideoslist);
        paidVideosListAdapter = new PaidVideosListAdapter(getApplicationContext(), path);
        listView.setAdapter(paidVideosListAdapter);
       // Toast.makeText(getApplicationContext(), "" + path, Toast.LENGTH_SHORT).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final VideosListPrototype videosListPrototype = (VideosListPrototype) parent.getItemAtPosition(position);
                if (oneVideoRemaining(path)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BuyVideosList.this);

                    builder.setTitle("" + videosListPrototype.getTitle());
                    builder.setMessage("This is the first demo video...activate it to view further");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            // now play video on clicking ok button
                            progressDialog = new ProgressDialog(BuyVideosList.this);
                            progressDialog.setMessage("" + videosListPrototype.getTitle());
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            FileWriter fileWriter = null;
                            try {
                                fileWriter = new FileWriter(getRemoteFileLocation(), true);
                                BufferedWriter out = new BufferedWriter(fileWriter);
                                out.write("yes");
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            new DecryptVideo().execute(videosListPrototype.getVideo_url(), videosListPrototype.getTitle());

                        }
                    });
                    builder.show();

                } else {
                    // now check whether user has bought the package or not
                    if (haveCorrectKey(path)) {
                        progressDialog = new ProgressDialog(BuyVideosList.this);
                        progressDialog.setMessage("" + videosListPrototype.getTitle());
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        new DecryptVideo().execute(videosListPrototype.getVideo_url(), videosListPrototype.getTitle());
                    } else {
                        Intent intent = new Intent(BuyVideosList.this, PurchaseActivity.class);
                        intent.putExtra("path", path);
                        startActivity(intent);
                    }


                }

            }
        });


    }

    public void isSubscribedPackage(String videoPath) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public class DecryptVideo extends AsyncTask<String, String, Void> {
        String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String videoTitle;

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoLink) {
            this.videoTitle = videoLink;
        }

        @Override
        protected Void doInBackground(String... params) {

            File encryptVideoFile = new File(params[0]);
            setLink(StorageClass.decryptVideo(encryptVideoFile));
            setVideoTitle(params[1]);
            return null;
        }

        @Override
        public void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            progressDialog.dismiss();
            Intent intent = new Intent(getApplicationContext(), VideoView.class);
            intent.putExtra("url", getLink());
            intent.putExtra("thumb", "");
            intent.putExtra("title", getVideoTitle());
            startActivity(intent);

        }
    }

    public boolean oneVideoRemaining(String path) {
        boolean bool = false;
        // now check the file where exists
        File fileID = new File(path + "/data/id.dll");
        String idPackage;
        try {
            idPackage = StorageClass.readFile(fileID.getAbsolutePath());
        } catch (IOException e) {
            idPackage = "";
            e.printStackTrace();
        }
        File remoteFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/system_media_os");
        if (!remoteFolder.exists()) {
            remoteFolder.mkdirs();
        }
        File remotePackFile = new File(remoteFolder, "ed" + idPackage + ".dll");
        setRemoteFileLocation(remotePackFile.getAbsolutePath());      //ecxeption may occur here
        if (remotePackFile.exists()) {
            bool = false;
        } else {
            bool = true;
            try {
                remotePackFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bool;

    }


    @Override
    public int checkCallingOrSelfPermission(String permission) {
        return super.checkCallingOrSelfPermission(permission);
    }

    public boolean haveCorrectKey(String packagePath) {
        boolean bool = false;
        File fileID = new File(packagePath + "/data/id.dll");
        String idPackage;
        try {
            idPackage = StorageClass.readFile(fileID.getAbsolutePath());
        } catch (IOException e) {
            idPackage = "";
            e.printStackTrace();
        }

        File remoteLocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/system_data_os");
        if (!remoteLocation.exists()) {
            remoteLocation.mkdirs();
        }
        File secretKeyFile = new File(remoteLocation, "system" + idPackage + ".dll");
        setRemoteFilePurchaseLocation(secretKeyFile.getAbsolutePath());
        if (!secretKeyFile.exists()) {
            bool = false;
        } else {
            /*try {
                String keyInFile = StorageClass.readFile(secretKeyFile.getAbsolutePath());
                if (keyInFile.length() == 10) {
                    // now get the last 5 digits of imei
                    int i = 0;
                    String last5Imei = "";
                    while (i < 5) {
                        last5Imei = getImei().charAt(getImei().length() - i - 1) + last5Imei;
                        i++;
                    }

                    // now get the string of characters at even places in the keyObatained in the file
                    String evenPlacesObtainedString = "";
                    for (int j = 1; j < keyInFile.length(); j = j + 2) {
                        evenPlacesObtainedString = evenPlacesObtainedString + keyInFile.charAt(j);
                    }

                    if (last5Imei.equals(evenPlacesObtainedString)) {
                        bool = true;
                    } else {
                        bool = false;
                    }
                } else {
                    bool = false;
                }
            } catch (IOException e) {
                bool = false;
                e.printStackTrace();
            }*/
            bool = true;
        }
        return bool;
    }

    public String getImei() {
        final TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
