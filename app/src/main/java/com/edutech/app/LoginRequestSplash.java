package com.edutech.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.edutech.app.others.StorageClass;
import com.edutech.app.others.UserDetails;
import com.edutech.app.subActivities.PhoneAuth;
import com.edutech.app.subActivities.SignEmail;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginRequestSplash extends AppCompatActivity {

    private static final int RC_SIGN_IN = 333;
    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        findViewById(R.id.phone_dialgo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginRequestSplash.this, PhoneAuth.class));
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Home.class));
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.defaul))
                .requestEmail()
                .build();


        findViewById(R.id.google_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // now sign in using google
                signInButton = (SignInButton) findViewById(R.id.google_button);
                mAuth = FirebaseAuth.getInstance();

               /* mGoogleApiClient = new GoogleApiClient.Builder(LoginRequestSplash.this)
                        .enableAutoManage(LoginRequestSplash.this, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                Toast.makeText(getApplicationContext(), "Failed connection", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();

                  */

                signIn();
            }
        });


        findViewById(R.id.skip_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginRequestSplash.this, Home.class));
            }
        });


    }


    private void signIn() {

        Intent intent = new Intent(LoginRequestSplash.this,SignEmail.class);
        startActivity(intent);
        // Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
       // startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Error at 101" + e, Toast.LENGTH_SHORT).show();
                // Google Sign In failed, update UI appropriately
                // ...
            }
        } else {
            Toast.makeText(getApplicationContext(), "Unable to login at 106", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveData();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);

                            //updateUI(user);
                            //Toast.makeText(getApplicationContext(), "Success at 124", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                          //  Toast.makeText(getApplicationContext(), "Failed at 127", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void saveData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("user_details");


        UserDetails userDetails = new UserDetails("" + FirebaseAuth.getInstance().getCurrentUser().getEmail(), ""+FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), "" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), StorageClass.getImei(LoginRequestSplash.this), StorageClass.getDate(), "" + FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            databaseReference = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.setValue(userDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Successfully signed in...", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
