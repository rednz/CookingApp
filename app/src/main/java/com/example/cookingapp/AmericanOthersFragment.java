package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class AmericanOthersFragment extends Fragment {

    private FloatingActionButton btn_add_american_other;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_american_others, container, false);

        btn_add_american_other= view.findViewById(R.id.btn_add_american_others);
        btn_add_american_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_am_others();
            }
        });

        return view;
    }

    private void add_am_others() {

        final String food_type = "9";

        String URL_ADD = "http://10.68.113.231/CookingApp/get_type.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String failed = jsonObject.getString("message");

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Fill in Details to Add Recipe", Toast.LENGTH_SHORT).show();

                                Intent add = new Intent(getActivity(), add_activity.class);
                                add.putExtra("food_type", food_type);
                                startActivity(add);
                            } else {
                                Toast.makeText(getActivity(), failed, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Please Try Again later ......" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "Please Try Again later........" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("food_type", food_type);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(stringRequest);
    }
}
