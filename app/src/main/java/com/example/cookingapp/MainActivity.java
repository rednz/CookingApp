package com.example.cookingapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.arch.lifecycle.ReportFragment;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ChangeFragment(View view) {
        Fragment fragment;

        if(view == findViewById(R.id.button1)) {
            fragment = new FrenchFragment1();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place1, fragment);
            ft.commit();
        }

        if(view == findViewById(R.id.button2)) {
            fragment = new ItalianFragment2();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place1, fragment);
            ft.commit();
        }
    }
}
