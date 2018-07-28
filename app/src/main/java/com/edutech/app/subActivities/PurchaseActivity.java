package com.edutech.app.subActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edutech.app.R;
import com.edutech.app.activities.OurShops;
import com.edutech.app.activities.PrivacyPolicies;
import com.edutech.app.others.CallbackDetails;
import com.edutech.app.others.KeyActivationDetails;
import com.edutech.app.others.StorageClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PurchaseActivity extends AppCompatActivity {
    public String path;
    public boolean ActivatedKey = false;

    public boolean isActivatedKey() {
        return ActivatedKey;
    }

    public void setActivatedKey(boolean activatedKey) {
        ActivatedKey = activatedKey;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    EditText keyEditext, phoneEdittext;
    public ProgressDialog progressDialog;
    public String master_key, master_package;

    public String getMaster_key() {
        return master_key;
    }

    public void setMaster_key(String master_key) {
        this.master_key = master_key;
    }

    public String getMaster_package() {
        return master_package;
    }

    public void setMaster_package(String master_package) {
        this.master_package = master_package;
    }


    public String keymail;

    public String getKeymail() {
        return keymail;
    }

    public void setKeymail(String keymail) {
        this.keymail = keymail;
    }

    TextView whatsapppaytmphone;
    EditText callbackphone;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String path = getIntent().getStringExtra("path");
        setPath(path);
        setTitle("Activate Package");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_purchase);


        if (!isNetworkAvailable(PurchaseActivity.this)) {
            relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
            relativeLayout.setBackgroundResource(R.drawable.oh_sucks_no_internet_connection);

            linearLayout = (LinearLayout)findViewById(R.id.linearlayour_purchaseactivity);
            linearLayout.setVisibility(View.GONE);

        }else{


        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressDialog = new ProgressDialog(PurchaseActivity.this);
        progressDialog.setMessage("Verifying the Key");
        progressDialog.setCanceledOnTouchOutside(false);

        keyEditext = (EditText) findViewById(R.id.edittext_key_entered);
        phoneEdittext = (EditText) findViewById(R.id.edittex_phoneemail_et);

        whatsapppaytmphone = (TextView) findViewById(R.id.paytmorwhatsappnumber);
        callbackphone = (EditText) findViewById(R.id.phone_callback);

        FirebaseDatabase.getInstance().getReference().child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String phone = dataSnapshot.child("paytm_phone").getValue().toString().trim();
                whatsapppaytmphone.setText(phone);
                setKeymail(dataSnapshot.child("key_mail").getValue().toString().trim());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                whatsapppaytmphone.setText("9958573530");
                setKeymail("contactedutec@gmail.com");

            }
        });

        findViewById(R.id.callbutton_callback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!callbackphone.getText().toString().trim().isEmpty()) {
                    // here save the number for call back with package id and date and imei

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(PurchaseActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(PurchaseActivity.this);
                    }
                    builder.setTitle("Callback ")
                            .setMessage("We will get back to you within an hour...")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                    //  Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    // callIntent.setData(Uri.parse("tel:9958573530"));
                                    //startActivity(callIntent);
                                    onBackPressed();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                    File fileID = new File(path + "/data/id.dll");
                    String idPackage;
                    try {
                        idPackage = StorageClass.readFile(fileID.getAbsolutePath());
                    } catch (IOException e) {
                        idPackage = "";
                        e.printStackTrace();
                    }


                    CallbackDetails callbackDetails = new CallbackDetails(callbackphone.getText().toString().trim(), StorageClass.getImei(PurchaseActivity.this), StorageClass.getDate(), "" + idPackage.charAt(0));
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference = databaseReference.child("callback_details");
                    databaseReference.push().setValue(callbackDetails);


                } else {
                    Toast.makeText(getApplicationContext(), "Enter valid phone number for getting a call back: ", Toast.LENGTH_SHORT);
                }
            }
        });

        // now fetch the package id and subject for sending the mail option to that
        findViewById(R.id.mailus_getkey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send the mail with all subject,package to sender
                File fileID = new File(path + "/data/id.dll");
                String idPackage;
                try {
                    idPackage = StorageClass.readFile(fileID.getAbsolutePath());
                } catch (IOException e) {
                    idPackage = "";
                    e.printStackTrace();
                }


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{getKeymail()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Provide me the key for package of " + idPackage + " soon");
                i.putExtra(Intent.EXTRA_TEXT, "Hi!\n Please provide me the key for package of" + idPackage);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    //  Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // fetch the value of master key and master package
        FirebaseDatabase.getInstance().getReference().child("master_key").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String master_key = dataSnapshot.child("key").getValue().toString().trim();
                String master_package = dataSnapshot.child("package").getValue().toString().trim();

                setMaster_key(master_key);
                setMaster_package(master_package);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



      /*  findViewById(R.id.onlinekey_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Contact this number on whatsapp", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(PurchaseActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(PurchaseActivity.this);
                }
                builder.setTitle("Contact Us")
                        .setMessage("Contact this whatsapp number for key: ")
                        .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:9958573530"));
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });
*/

        findViewById(R.id.submit_checkkey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = keyEditext.getText().toString().toLowerCase();

                if (!TextUtils.isEmpty(phoneEdittext.getText().toString().trim())) {

                    if (key.length() == 6 && (!TextUtils.isEmpty(phoneEdittext.getText().toString().trim()))) {
                        //  Toast.makeText(getApplicationContext(), "First level crossed", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference().child("keys").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                progressDialog.show();
                                String key = keyEditext.getText().toString();
                                File fileID = new File(path + "/data/id.dll");
                                String idPackage;
                                try {
                                    idPackage = StorageClass.readFile(fileID.getAbsolutePath());
                                } catch (IOException e) {
                                    idPackage = "";
                                    e.printStackTrace();
                                }

                                String char3place = "" + key.charAt(2) + "";
                                String pack1place = "" + idPackage.charAt(0);
                                DataSnapshot dataSnapshot1 = dataSnapshot.child("package");
                                if (dataSnapshot1.hasChild(char3place)) {
                                    DataSnapshot dataSnapshot2 = dataSnapshot1.child(char3place);
                                    if (dataSnapshot2.hasChild(key) && (char3place.equals(pack1place))) {
                                        if (!dataSnapshot2.child(key).getValue().toString().equals("no")) {
                                            setActivatedKey(true);
                                            postExecutePurchase(path);
                                            try {
                                                Toast.makeText(getApplicationContext(), "Success....You can view the videos of this package", Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Failure..Key is wrong or unauthorazied", Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }

                                            try {
                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("key_activation");
                                                databaseReference = databaseReference.child(phoneEdittext.getText().toString().trim());
                                                KeyActivationDetails keyActivationDetails = new KeyActivationDetails(keyEditext.getText().toString().trim(), phoneEdittext.getText().toString().trim(), StorageClass.getDate(), StorageClass.getImei(PurchaseActivity.this), pack1place);
                                                databaseReference.setValue(keyActivationDetails);
                                            } catch (Exception e) {
                                            }

                                            // progressDialog.dismiss();
                                            keyEditext.setText("");
                                            AlertDialog.Builder builder;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                builder = new AlertDialog.Builder(PurchaseActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                                            } else {
                                                builder = new AlertDialog.Builder(PurchaseActivity.this);
                                            }
                                            builder.setTitle("Success")
                                                    .setMessage("Your package has been activated successfully!")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete
                                                            Intent intent = new Intent(getApplicationContext(), BuyVideosList.class);
                                                            intent.putExtra("path", path);
                                                            intent.putExtra("title", "course");
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    })

                                                    .setIcon(android.R.drawable.ic_dialog_info)
                                                    .show();

                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Invalid key", Toast.LENGTH_SHORT).show();
                                            keyEditext.setText("");

                                        }
                                    } else {
                                        keyEditext.setText("");
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Invalid key", Toast.LENGTH_SHORT).show();
                                    }

                                } else {

                                    // here check if it is the master key or not
                                    if (keyEditext.getText().toString().trim().equals(getMaster_key()) && pack1place.equals(getMaster_package())) {
                                        // here it is sucess
                                        try {
                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("master_activation");
                                            databaseReference = databaseReference.child(phoneEdittext.getText().toString().trim());
                                            KeyActivationDetails keyActivationDetails = new KeyActivationDetails(keyEditext.getText().toString().trim(), phoneEdittext.getText().toString().trim(), StorageClass.getDate(), StorageClass.getImei(PurchaseActivity.this), pack1place);
                                            databaseReference.setValue(keyActivationDetails);
                                        } catch (Exception e) {
                                        }


                                        setActivatedKey(true);
                                        postExcecutePurchase1(path);
                                        keyEditext.setText("");
                                        AlertDialog.Builder builder;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            builder = new AlertDialog.Builder(PurchaseActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                                        } else {
                                            builder = new AlertDialog.Builder(PurchaseActivity.this);
                                        }
                                        builder.setTitle("Success")
                                                .setMessage("Congrats! Your package has been activated successfully!")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // continue with delete
                                                        Intent intent = new Intent(getApplicationContext(), BuyVideosList.class);
                                                        intent.putExtra("path", path);
                                                        intent.putExtra("title", "course");
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })

                                                .setIcon(android.R.drawable.ic_dialog_info)
                                                .show();


                                    } else {

                                        Toast.makeText(getApplicationContext(), "Inavalid key...", Toast.LENGTH_SHORT).show();

                                    }
                                    progressDialog.dismiss();
                                    keyEditext.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    } else {
                        keyEditext.setText("");
                        Toast.makeText(getApplicationContext(), "Entered Key is Invalid...Enter again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Phone Number is Empty...", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void postExecutePurchase(String path) {
        // first set the values to the database
        // as no means key has been activated and can't be used further

        if (isActivatedKey()) {
            String char3place = "" + keyEditext.getText().toString().trim().charAt(2) + "";

            FirebaseDatabase.getInstance().getReference().child("keys").child("package").child(char3place).child(keyEditext.getText().toString().trim()).setValue("no");
            FirebaseDatabase.getInstance().getReference().child("activated_keys").push().setValue(keyEditext.getText().toString().trim());

        }


        File fileID = new File(path + "/data/id.dll");
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
        if (!secretKeyFile.exists()) {
            try {
                secretKeyFile.createNewFile();
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(secretKeyFile, true);
                    BufferedWriter out = new BufferedWriter(fileWriter);
                    Random random = new Random(10);
                    String key[] = new String[10];
                    for (int i = 0; i < 5; i++) {
                        int randomGenerated = random.nextInt();
                        String temp = "";
                        temp = "" + randomGenerated;
                        key[2 * i] = "" + temp.charAt(0);

                    }
                    for (int i = 0; i < 5; i++) {
                        key[2 * i + 1] = "" + getImei().substring(getImei().length() - 5, getImei().length()).charAt(i);
                    }
                    String finalKeyWritten = "";
                    for (int i = 0; i < 10; i++) {
                        finalKeyWritten = finalKeyWritten + key[i];

                    }
                    out.write(finalKeyWritten);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            secretKeyFile.delete();
        }

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

    public String getImei() {
        final TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public void postExcecutePurchase1(String path) {
        // it is the method for the master key

        File fileID = new File(path + "/data/id.dll");
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
        if (!secretKeyFile.exists()) {
            try {
                secretKeyFile.createNewFile();
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(secretKeyFile, true);
                    BufferedWriter out = new BufferedWriter(fileWriter);
                    Random random = new Random(10);
                    String key[] = new String[10];
                    for (int i = 0; i < 5; i++) {
                        int randomGenerated = random.nextInt();
                        String temp = "";
                        temp = "" + randomGenerated;
                        key[2 * i] = "" + temp.charAt(0);

                    }
                    for (int i = 0; i < 5; i++) {
                        key[2 * i + 1] = "" + getImei().substring(getImei().length() - 5, getImei().length()).charAt(i);
                    }
                    String finalKeyWritten = "";
                    for (int i = 0; i < 10; i++) {
                        finalKeyWritten = finalKeyWritten + key[i];

                    }
                    out.write(finalKeyWritten);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            secretKeyFile.delete();
        }

    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
