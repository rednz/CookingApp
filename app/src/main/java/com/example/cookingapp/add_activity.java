package com.example.cookingapp;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class add_activity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private static final String type  = "type_name";
    private static final String JSON_ARRAY ="result";
    private static final String typearray = "type_name";


    private JSONArray result;
    private Button btn_choose ;

    private Spinner spinner;
    private ArrayList<String> arrayList;
    private TextView food_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btn_choose = findViewById(R.id.btn_choosedesign);

        spinner = findViewById(R.id.spinnertype);
        food_type = findViewById(R.id.hiddentype);
      //  findViewById(R.id.hiddentype).setVisibility(View.GONE);

        arrayList = new ArrayList<>();
        getdata();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item
                food_type.setText(getlocation(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                food_type.setText("");
            }
        });

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void getdata() {
        StringRequest stringRequest = new StringRequest("http://10.68.127.144/CookingApp/gettype.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(JSON_ARRAY);
                            details(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void details(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList.add(json.getString(typearray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<>(add_activity.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
    }

    //Method to get location name which user selects
    private String getlocation(int position) { // method is used to post name of marker to database accordingly
        String loc = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);
            //Fetching location from that object
            loc = json.getString(type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Return location
        return loc;
    }
}
