package com.edutech.app.subActivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.edutech.app.R;
import com.edutech.app.activities.OurShops;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ShopsActivityDetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,OnMapReadyCallback, ViewPagerEx.OnPageChangeListener {

    SliderLayout sliderLayout;
    GoogleMap map;
    public String lat,lon;
    public String shop_name;
    public String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    TextView shop_dtails,call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String lat = getIntent().getStringExtra("lat");
        String lon = getIntent().getStringExtra("lon");
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String key = getIntent().getStringExtra("key");
        final String phone = getIntent().getStringExtra("phone");
        setContentView(R.layout.activity_shops_details);
        setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setLat(lat);
        setLon(lon);
        setShop_name(name);
        setAddress(address);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sliderLayout = (SliderLayout) findViewById(R.id.slider_shops);

        shop_dtails = (TextView)findViewById(R.id.textviewdetails_shops);
        call = (TextView)findViewById(R.id.call_textviewshops);
        shop_dtails.setText(""+name+"\n\n "+getAddress());
        call.setText("Call: "+phone);

        findViewById(R.id.callphone_here).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here call the number
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        });
        if (key != null) {
            FirebaseDatabase.getInstance().getReference().child("our_shops").child(key).child("images").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> urls = new ArrayList<>();
                    List<String> titles = new ArrayList<>();
                    titles.clear();
                    urls.clear();
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        urls.add(ds.getValue().toString().trim());
                        titles.add("" + (i + 1));
                        i++;
                    }
                    if (urls.size() > 0) {

                        startSliderFromInternet(urls.toArray(new String[urls.size()]), titles.toArray(new String[titles.size()]));

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(ShopsActivityDetails.this, "Check your internet connection: ", Toast.LENGTH_SHORT).show();

                }
            });
        }


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
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
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
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        try {
            LatLng sydney = new LatLng(Double.parseDouble(getLat()), Double.parseDouble(getLon()));
            map.addMarker(new MarkerOptions().position(sydney).title("" + getShop_name()));
            map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            map.animateCamera(CameraUpdateFactory.zoomTo(16));
        }catch (Exception e){

            Toast.makeText(ShopsActivityDetails.this,"Unable to load map...",Toast.LENGTH_SHORT).show();
        }

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
        //Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


}
