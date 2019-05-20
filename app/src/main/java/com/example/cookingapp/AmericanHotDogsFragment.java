package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AmericanHotDogsFragment extends Fragment {

    private FloatingActionButton btn_add_hotdog;

    private static final String URL_PRODUCTS = "http://192.168.1.68:81/CookingApp/MyApi/HotDogs.php";

    List<receipt> receiptList;

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_american_hotdogs, container, false);

        btn_add_hotdog = view.findViewById(R.id.btn_add_hotdogs);
        btn_add_hotdog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_hotdog();
            }
        });

        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        receiptList = new ArrayList<>();

        getrecipes();
        return view;
    }

    private void getrecipes() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject product = array.getJSONObject(i);
                                receiptList.add(new receipt(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("description"),
                                        product.getString("image")
                                ));
                            }
                            ReceiptAdapter adapter = new ReceiptAdapter(getActivity(), receiptList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();

                    }
                }) {

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);


    }

    private void add_hotdog() {

        final String food_type = "8";

        String URL_ADD = "http://192.168.1.68:81/CookingApp/get_type.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String failed = jsonObject.getString("message");

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Fill in Details to Add Recipe For American Hot Dogs", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
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
