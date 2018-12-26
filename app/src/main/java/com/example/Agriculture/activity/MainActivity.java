package com.example.Agriculture.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Agriculture.R;
import com.example.Agriculture.model.State;
import com.example.Agriculture.model.User;
import com.example.Agriculture.sql.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arr = new ArrayList<String>();
    ArrayList<String> arrCrop = new ArrayList<String>();

    Spinner state_spinner, cropSpinner;

    String[] StateId, StateName, index/*, cropId, crop, cropVariety, cropStateId*/;
    String cropId, crop, cropVariety, cropStateId;
    String state_spinner_code = null, crop_state_code = null;
    String url = "https://myfarminfo.com/yfirest.svc/All/States";
    String cropUrl = "https://myfarminfo.com/yfirest.svc/All/Crops";

    private User user;
    private State state;
    private DatabaseHelper databaseHelper;
    private Button btnCropDetails, btnCropData, btnStateDetails;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgBack = findViewById(R.id.backBtn);
        imgBack.setVisibility(View.GONE);
        btnCropDetails = findViewById(R.id.btn_crop);
        btnCropData = findViewById(R.id.btn_crop_data);
        btnStateDetails = findViewById(R.id.btn_state);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        user = new User();
        state = new State();
        state_spinner = findViewById(R.id.birth_country_spinner);
        cropSpinner = findViewById(R.id.crop_spinner);
        state_spinner.getBackground().setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
        cropSpinner.getBackground().setColorFilter(getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
        getStateName();
        getCropDetail();
        btnCropDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllDataListActivity.class);
                startActivity(intent);
            }
        });

        btnCropData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
                startActivity(intent);
            }
        });

        btnStateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StateListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getCropDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        System.out.println("state cropurl " + cropUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, cropUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    String finalResponse = response.replaceAll("\\\\", "").toString();
                    String finalResponse1 = finalResponse.replaceAll("^\"|\"$", "");
                    System.out.println("state Crop response Exception:" + finalResponse1);
                    System.out.println("state Crop Length Exception:" + finalResponse1.length());

                    JSONArray jsonArray = new JSONArray(finalResponse1);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                        cropId = jsonArray1.getString(0);
                        crop = jsonArray1.getString(1);
                        cropVariety = jsonArray1.getString(2);
                        cropStateId = jsonArray1.getString(3);

                        user.setCropId(cropId);
                        user.setCrop(crop);
                        user.setCropVariety(cropVariety);
                        user.setCropStateId(cropStateId);

                        arrCrop.add(crop);
                        if (databaseHelper.checkUser(user)) {
                            databaseHelper.addUser(null);
                        } else {
                            databaseHelper.addUser(user);
                        }
                    }

                    setCropName(arrCrop);

                } catch (Exception e) {
                    progressDialog.dismiss();
                    System.out.println("Error@#$" + e);
                    Toast.makeText(MainActivity.this, "Error. " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error. " + error, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getStateName() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        System.out.println("state url " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    String finalResponse = response.replaceAll("\\\\", "").toString();
                    String finalResponse1 = finalResponse.replaceAll("^\"|\"$", "");
                    System.out.println("state response Exception:" + finalResponse1);
                    System.out.println("state Length Exception:" + finalResponse1.length());

                    JSONArray jsonArray = new JSONArray(finalResponse1);

                    StateId = new String[jsonArray.length()];
                    StateName = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        StateId[i] = jsonObject.getString("StateID").substring(0, jsonObject.getString("StateID").indexOf("."));
                        StateName[i] = jsonObject.getString("StateName");

                        state.setStateId(StateId[i]);
                        state.setStateName(StateName[i]);
                        arr.add(StateName[i]);


                        if (databaseHelper.checkUserState(state)) {
                            databaseHelper.addState(null);
                        } else {
                            databaseHelper.addState(state);
                        }
                    }
                    setStateName(arr);
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Error. + e", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setStateName(final ArrayList<String> arr) {
        ArrayAdapter spinner_value = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, arr);
        spinner_value.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(spinner_value);

        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state_spinner_code = StateId[i];
                Toast.makeText(MainActivity.this, StateName[i], Toast.LENGTH_SHORT).show();

                System.out.println("selected Data:" + state_spinner_code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setCropName(final ArrayList<String> arrCrop) {
        ArrayAdapter spinner_value = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, arrCrop);
        spinner_value.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(spinner_value);

        cropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                crop_state_code = cropStateId;
                Toast.makeText(MainActivity.this, (CharSequence) adapterView.getSelectedItem(), Toast.LENGTH_SHORT).show();
                System.out.println("selected Data:" + cropStateId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
