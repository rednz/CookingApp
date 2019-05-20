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


public class AmericanBurgersFragment extends Fragment {

    private FloatingActionButton btn_add_burger;

    //this is the JSON Data URL
    private static final String URL_PRODUCTS = "http://192.168.1.68:81/CookingApp/MyApi/Burgers.php";//make sure you are using the correct ip else it will not work


    //a list to store all the recipes
    List<receipt> receiptList;

    //Creating the recycler view
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_american_burgers, container, false);

        btn_add_burger = view.findViewById(R.id.btn_add_burgers);
        btn_add_burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_burger(); //call add method
            }
        });

        recyclerView = view.findViewById(R.id.recylcerView); //setting reclerview based on it's id which is specified in layout of fragment
        recyclerView.setHasFixedSize(true); //recyclerView's size is not affected by the adapter contents.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //set layout according to fragment

        //initializing the array list
        receiptList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        getrecipes();

        return view;
    }

    private void getrecipes() { //display recipes from database

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

                                //adding the recipe and it's details to array list
                                receiptList.add(new receipt(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("description"),
                                        product.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ReceiptAdapter adapter = new ReceiptAdapter(getActivity(), receiptList);
                            recyclerView.setAdapter(adapter); //A list adapter is an object that adapts a collection objects for display in a ListView.
                            // adapter is used to display recipes from the database according to their type in a list view
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();// When Cannot connect
                    }
                }) {

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);


    }

    private void add_burger() {// launching add_activity on click
        //passing the food type  to know which type of food user is adding.

        final String food_type = "7"; // type_id of food

        String URL_ADD = "http://192.168.1.68:81/CookingApp/get_type.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success"); //query succeed
                            String failed = jsonObject.getString("message");// query failed

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Fill in Details to Add Recipe For American Burgers", Toast.LENGTH_SHORT).show();

                                Intent add = new Intent(getActivity(), add_activity.class); //moving to the add activity
                                add.putExtra("food_type", food_type); //passing the type_id of the food
                                startActivity(add); //starting add_activity
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

                        Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show(); //Connection error
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("food_type", food_type); //is used to pass food_type value to php script


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(stringRequest); //adding request to queue
    }
}
