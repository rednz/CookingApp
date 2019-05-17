package com.example.cookingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!InternetConnectivity(MainActivity.this)) buildDialog(MainActivity.this).show();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying Home Page", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sub_italian1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItalianPizzaFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying Italian Pizza Recipes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sub_italian2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItalianPastaFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying Italian Pasta Recipes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sub_italian3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItalianOthersFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying Italian Others Recipes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sub_french1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrenchSoupFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying French Soup Recipes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sub_french2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrenchCrossiantFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying French Croissant Recipes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sub_french3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrenchOthersFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying French Others Recipes", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sub_american1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AmericanBurgersFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying American Burgers Recipes", Toast.LENGTH_SHORT).show();
                break;


            case R.id.nav_sub_american2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AmericanHotDogsFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying American Hot Dogs Recipes", Toast.LENGTH_SHORT).show();
                break;


            case R.id.nav_sub_american3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AmericanOthersFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying American Others Recipes", Toast.LENGTH_SHORT).show();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;//item is selected
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); //since its on left side of screen
        } else {
            super.onBackPressed();
        }
    }

    private boolean InternetConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder message = new AlertDialog.Builder(c);
        message.setCancelable(false);
        message.setTitle("No Internet Connection");
        message.setMessage("Please ensure your device has internet connection");

        message.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent myIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(myIntent);
            }

        });
        message.setNegativeButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return message;
    }


}

