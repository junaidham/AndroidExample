package com.android.spinnertest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.spinnertest.Utils.Utils;
import com.android.spinnertest.config.Config;
import com.android.spinnertest.model.Country;
import com.android.spinnertest.model.ZoneData;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junaid Ahmad on 2/7/2017.
 */

public class MainActivity extends AppCompatActivity {

    Spinner mCountry, mState;
    TextView country, state;
    ProgressDialog dialog;
    Country countryData;
    ZoneData zoneData;

    ArrayAdapter<String> apModeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountry = (Spinner) findViewById(R.id.country);
        mState = (Spinner) findViewById(R.id.state);
        country = (TextView) findViewById(R.id.countryTv);
        state = (TextView) findViewById(R.id.stateTv);


        mCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country.setText("Country Name : "+countryData.getData().get(position).getName()+"\n"+"County Id :"+countryData.getData().get(position).getCountryId());
                getZoneData(countryData.getData().get(position).getCountryId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state.setText("Zone Name : "+zoneData.getData().getZone().get(position).getName()+"\n"+"Zone Id :"+zoneData.getData().getZone().get(position).getZoneId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.isConnected(MainActivity.this)) {
            getCountryData();
        } else {
            Toast.makeText(getApplicationContext(), "Please check internet connection and try again!", Toast.LENGTH_LONG).show();
        }
    }

    public void getCountryData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Config.DATA_URL_COUNTRY;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        removeLoading();
                        if (!TextUtils.isEmpty(response)) {
                            Gson gson = new Gson();
                            countryData = gson.fromJson(response, Country.class);
                            if(countryData.getSuccess()){
                                mCountry.setAdapter(new CustomSpinnerAdapter(MainActivity.this, countryData));
                            }else{
                                Toast.makeText(MainActivity.this,"Error in getting country list",Toast.LENGTH_LONG).show();
                            }

                        }

                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        removeLoading();
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Oc-Merchant-Id", "123");

                return params;
            }
        };

        showLoading();
        queue.add(postRequest);

    }

    public void getZoneData(String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Config.DATA_URL_COUNTRY+"&id="+id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        removeLoading();
                        if (!TextUtils.isEmpty(response)) {
                            Gson gson = new Gson();
                            zoneData = gson.fromJson(response, ZoneData.class);
                            if(countryData.getSuccess()){
                                mState.setAdapter(new CustomSpinnerZoneAdapter(MainActivity.this, zoneData));
                            }else{
                                Toast.makeText(MainActivity.this,"Error in getting country list",Toast.LENGTH_LONG).show();
                            }

                        }

                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        removeLoading();
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Oc-Merchant-Id", "123");

                return params;
            }
        };

        showLoading();
        queue.add(postRequest);

    }

    private void showLoading() {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void removeLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
