package com.edutech.app.subActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.edutech.app.Home;
import com.edutech.app.R;
import com.edutech.app.others.StorageClass;
import com.edutech.app.others.UserDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignEmail extends AppCompatActivity {
    EditText name, email, password;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_email);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.setCanceledOnTouchOutside(false);


        findViewById(R.id.signup_button_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (TextUtils.isEmpty(name.getText().toString().trim()) || TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(password.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "One or more fields are empty...", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    Toast.makeText(getApplicationContext(), "Successfully signed up ", Toast.LENGTH_SHORT).show();

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference = databaseReference.child("user_details");
                                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                        UserDetails userDetails = new UserDetails(email.getText().toString().trim(), "", name.getText().toString().trim(), StorageClass.getImei(SignEmail.this)
                                                , StorageClass.getDate(), "");
                                        databaseReference = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        databaseReference.setValue(userDetails);

                                    }
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(getApplicationContext(), Home.class);

                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Unable to register...please try again", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }
        });
    }

    public void init() {
        name = (EditText) findViewById(R.id.name_edittext);
        email = (EditText) findViewById(R.id.email_edittext);
        password = (EditText) findViewById(R.id.password);

    }
}
