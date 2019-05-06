package com.example.cdac_integrated_sign;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class WelcomeActivity extends AppCompatActivity {

    static  String response;
    LatLng myPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LatLng my_location;
        int id = item.getItemId();
        switch (id){
            case R.id.main_menu_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.main_menu_signout:
                Intent intent1 = new Intent(this, ProfileActivity.class);
                startActivity(intent1);
                return true;
            case R.id.main_menu_hospitalsmap:
                Intent hospitalmapactivity = new Intent(this, HospitalsMapsActivity.class);
                hospitalmapactivity.putExtra("response", response);
                hospitalmapactivity.putExtra("my_latitude", myPosition.latitude);
                hospitalmapactivity.putExtra("my_longitude", myPosition.longitude);
                startActivity(hospitalmapactivity);

                return true;
            case R.id.main_menu_cached:
                data_cached();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void data_cached() {
        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            myPosition = new LatLng(latitude, longitude);
        } else myPosition = new LatLng(0, 0);

        Toast.makeText(this, myPosition.latitude+" "+myPosition.longitude,Toast.LENGTH_LONG).show();
        HospitalsMapsList hospitalsMapsList = new HospitalsMapsList(this, myPosition);
        hospitalsMapsList.getData();//get value to response

        Toast.makeText(this, "Data cached", Toast.LENGTH_LONG).show();
    }
}
