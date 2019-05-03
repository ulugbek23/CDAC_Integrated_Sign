package com.example.cdac_integrated_sign;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HospitalsMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String response;
    LatLng myLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals_maps);

        Intent intent = getIntent();
        response = intent.getStringExtra("response");
        Double my_latitude = intent.getDoubleExtra("my_latitude",0);
        Double my_longitude = intent.getDoubleExtra("my_longitude",0);
        myLocation = new LatLng(my_latitude,my_longitude);

        Toast.makeText(this, "come 1"+response, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,  my_latitude+" "+my_longitude, Toast.LENGTH_SHORT).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Marker marker=null;
        try {
            JSONArray jsonArray = new JSONArray(response); // set global variable
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                LatLng hospital = new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
                marker = mMap.addMarker(new MarkerOptions().position(hospital).title(jsonObject.getString("name")));
            }
            //Toast.makeText(getApplicationContext(), "come 2", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(hospital));
        if(marker!=null) mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 8));
    }

}