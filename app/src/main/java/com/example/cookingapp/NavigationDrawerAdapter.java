package com.example.cookingapp;

import android.content.Context;
import android.widget.ArrayAdapter;

public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private int menuSelectedItem; //Selected item position

    public NavigationDrawerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    public int getSelectedItem() {
        return menuSelectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        menuSelectedItem = selectedItem;
    }
}

