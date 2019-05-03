package com.example.cdac_integrated_sign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class WelcomeActivity extends AppCompatActivity {

    private String response;

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
                my_location = new LatLng(28.6139, 77.2090);

                Intent intent2 = new Intent(this, HospitalsMapsActivity.class);
                intent2.putExtra("my_latitude", my_location.latitude);
                intent2.putExtra("my_longitude", my_location.longitude);
                intent2.putExtra("response", response);
                startActivity(intent2);

                return true;
            case R.id.main_menu_cached:
                my_location = new LatLng(28.6139, 77.2090);
                HospitalsMapsList hospitalsMapsList = new HospitalsMapsList(this,my_location);

                hospitalsMapsList.getData(new HospitalsMapsList.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        response = result;
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                });
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
