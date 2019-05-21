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
import android.support.v4.app.FragmentTransaction;
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
        setSupportActionBar(toolbar);//toolbar acting as actionbar

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view); // the menu list and each item
        navigationView.setNavigationItemSelectedListener(this);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle); //adding the actonbardrawerToggle in action bar
        toggle.syncState();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home); //by default home fragment is loaded
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_home: //if home is pressed open home fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                Toast.makeText(getBaseContext(), "Displaying Home Page", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_sub_italian1:{ //if pizza is pressed open pizza fragment
                ItalianPizzaFragment fragment = new ItalianPizzaFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getBaseContext(), "Displaying Italian Pizza Recipes", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_sub_italian2: { //if pasta is pressed open pasta fragment
                ItalianPastaFragment fragment = new ItalianPastaFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container ,fragment); //replace layout with fragment
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getBaseContext(), "Displaying Italian Pasta Recipes", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_sub_italian3: { //if others is pressed open Italianothers fragment
                ItalianOthersFragment fragment = new ItalianOthersFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getBaseContext(), "Displaying Italian Others Recipes", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_sub_french1: { //if soup is pressed open soup fragment
                FrenchSoupFragment fragment = new FrenchSoupFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getBaseContext(), "Displaying French Soup Recipes", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_sub_french2: { //if crossiant is pressed open crossiant fragment
                FrenchCrossiantFragment fragment = new FrenchCrossiantFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getBaseContext(), "Displaying French Croissant Recipes", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_sub_french3: { //if others is pressed open Frenchothers fragment
                FrenchOthersFragment fragment = new FrenchOthersFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getBaseContext(), "Displaying French Others Recipes", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_sub_american1: { //if burgers is pressed open burgers
                AmericanBurgersFragment fragment = new AmericanBurgersFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getBaseContext(), "Displaying American Burgers Recipes", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.nav_sub_american2: { //if hotdogs is pressed open hotdogs fragment
                AmericanHotDogsFragment fragment = new AmericanHotDogsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getBaseContext(), "Displaying American Hot Dogs Recipes", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.nav_sub_american3: { // if others is pressed open Americanothers fragment
                AmericanOthersFragment fragment = new AmericanOthersFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getBaseContext(), "Displaying American Others Recipes", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.nav_aboutus: {//if about us is pressed open about us fragment
                AboutUSFragment fragment = new AboutUSFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
        drawer.closeDrawer(GravityCompat.START); //since its on left side of screen
        return true;//item is selected
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); //close drawer if drawer is open
        } else { // if not open perform on back
            super.onBackPressed();
        }
    }

    private boolean InternetConnectivity(Context context) { //check for internet connectivity
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        //if there is network and is connected or still connecting to a network
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder message = new AlertDialog.Builder(c);
        message.setCancelable(false); //cannot cancel
        message.setTitle("No Internet Connection"); //title of message to be displayed
        message.setMessage("Please ensure your device has internet connection"); //message displayed

        message.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent myIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(myIntent);//internet settings are opened once procceed is clicked
            }

        });
        message.setNegativeButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                //activity is destroyed if exit is clicked
            }
        });

        return message; //return message
    }


}

