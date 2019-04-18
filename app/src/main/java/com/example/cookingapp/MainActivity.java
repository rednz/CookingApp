package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()) {

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

                break;

            case R.id.nav_sub_italian1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItalianPizzaFragment()).commit();


                break;

            case R.id.nav_sub_italian2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItalianPastaFragment()).commit();

                break;

            case R.id.nav_sub_italian3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItalianOthersFragment()).commit();

                break;

            case R.id.nav_sub_french1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrenchSoupFragment()).commit();

                break;

            case R.id.nav_sub_french2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrenchCrossiantFragment()).commit();

                break;

            case R.id.nav_sub_french3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FrenchOthersFragment()).commit();

                break;

            case R.id.nav_sub_american1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AmericanBurgersFragment()).commit();

                break;

            case R.id.nav_sub_american2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AmericanHotDogsFragment()).commit();

                break;

            case R.id.nav_sub_american3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AmericanOthersFragment()).commit();

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


}