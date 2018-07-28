package com.edutech.app.subActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.edutech.app.Home;
import com.edutech.app.LoginRequestSplash;
import com.edutech.app.R;
import com.edutech.app.others.EncryptDecrypt;
import com.edutech.app.others.StorageClass;
import com.edutech.app.others.UserDetails;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    EditText phone_et, name_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Phone Sign-Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        phone_et = (EditText) findViewById(R.id.phone_number_editext);
        name_et = (EditText) findViewById(R.id.name_edittext);

        findViewById(R.id.phone_verify_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(PhoneAuth.this);
                progressDialog.setMessage("Automatic Verifying phone Number: ");
                progressDialog.setCanceledOnTouchOutside(true);

                if ((!TextUtils.isEmpty(phone_et.getText().toString().trim())) && (!TextUtils.isEmpty(name_et.getText().toString().trim()))) {
                    // now handle the phone number verification
                    progressDialog.show();
                    PhoneAuthProvider.getInstance()
                            .verifyPhoneNumber(phone_et.getText().toString().trim(),
                                    60,
                                    TimeUnit.SECONDS,
                                    PhoneAuth.this,
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                            Toast.makeText(getApplicationContext(), "OTP received...", Toast.LENGTH_SHORT).show();

                                            signInWithPhoneAuthCredential(phoneAuthCredential);
                                            progressDialog.dismiss();
                                        }

                                        @Override
                                        public void onVerificationFailed(FirebaseException e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Unable to verify the number...try again"+e, Toast.LENGTH_SHORT).show();

                                        }
                                    });


                } else {

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "One or more fields are empty...Enter a valid phone number or sign with google", Toast.LENGTH_SHORT).show();
                    phone_et.setText("");
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(getApplicationContext(), "Successfully signed in ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            saveData();

                            Intent intent = new Intent(PhoneAuth.this, Home.class);
                            startActivity(intent);
                            // ...
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to sign in with the otp", Toast.LENGTH_SHORT).show();
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(), "Failure in registration ", Toast.LENGTH_SHORT).show();


                            }
                        }
                    }
                });


    }


    public void saveData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("user_details");


        UserDetails userDetails = new UserDetails("" + FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                "" + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),
                "" + name_et.getText().toString().trim(),
                StorageClass.getImei(PhoneAuth.this), StorageClass.getDate(), "" + FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            databaseReference = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.setValue(userDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Successfully signed in...", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
