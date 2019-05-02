package com.example.cookingapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class add_activity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;

    private Button btn_choose ;

    private Button btn_submiter;
    private EditText name;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btn_choose = findViewById(R.id.btn_choosedesign);
        name = findViewById(R.id.title);
        description = findViewById(R.id.recipedetails);

        btn_submiter = findViewById(R.id.btn_submit);

        btn_submiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
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
        intent.setType("image/*"); //get image
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void Submit() {
        final String name_recipe = this.name.getText().toString().trim();
        final String name_details = this.description.getText().toString().trim();


        String URL_LOC = "http://10.68.117.144:81/CookingApp/insert_recipe.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(add_activity.this, "Recipe uploaded", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(add_activity.this, MainActivity.class));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(add_activity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(add_activity.this, "Please try again....", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("food_type", getIntent().getStringExtra("food_type").trim());
                params.put("name", name_recipe);
                params.put("details", name_details );

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    }
