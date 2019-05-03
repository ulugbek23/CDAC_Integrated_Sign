package com.example.cdac_integrated_sign;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

public class HospitalsMapsList {
    Context context;
    String URL="https://cdacnoida.000webhostapp.com/doctor_care/hospitalsmap.php";
    LatLng myLocation = new LatLng(41.5345,60.6249);
    String responseHospitals;

    public HospitalsMapsList(Context context, LatLng myLocation) {
        this.context = context;
        this.myLocation = myLocation;
        //getData();
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }

    public void getData(final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("0")){//if nothing come
                    Toast.makeText(context, "Empty list", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "come on"+response, Toast.LENGTH_LONG).show();
                    callback.onSuccess(response);

                    //responseHospitals = response;
                    /*try {
                        JSONArray jsonArray = new JSONArray(response); // set global variable
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            names.add(jsonObject.getString("name"));
                            latitudes.add(jsonObject.getDouble("latitude"));
                            longitudes.add(jsonObject.getDouble("longitude"));
                        }
                        Toast.makeText(context, "come 2", Toast.LENGTH_SHORT).show();
                        //ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, strings);
                        //listView.setAdapter(arrayAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error+" Please try again.", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("my_latitude",  myLocation.latitude+"");
                params.put("my_longitude", myLocation.longitude+"");
                return params;
            }
        }; //manashunaqa konstruksiya bor javada, methodni argumentlari tugaganidan keyin block biriktirish mumkin

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public LatLng getMyLocation(){
        //here get last known location and return as LatLng
        LatLng latLng = new LatLng(28.6139, 77.2090);
        return latLng;
    }

}
