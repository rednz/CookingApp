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


public class FrenchOthersFragment extends Fragment {

    private FloatingActionButton btn_add_french_other;

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = "http://10.68.125.158/CookingApp/MyApi/OthersFrench.php";

    //a list to store all the products
    List<receipt> receiptList;

    //the recyclerview
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_french_others, container, false);

        btn_add_french_other = view.findViewById(R.id.btn_add_french_others);
        btn_add_french_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fr_other();
            }
        });

        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //initializing the productlist
        receiptList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

        return view;
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                receiptList.add(new receipt(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("description"),
                                        product.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
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
                    }
                }){

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);


    }

    private void add_fr_other() {

        final String food_type = "6";

        String URL_ADD = "http://10.68.125.158/CookingApp/get_type.php";
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
