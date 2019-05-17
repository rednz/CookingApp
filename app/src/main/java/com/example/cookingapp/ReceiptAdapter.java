package com.example.cookingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.RecipeView> {


    private Context ctxt;
    private List<receipt> ListofRecipes; //creating recipes in a list

    public ReceiptAdapter(Context ctxt, List<receipt> ListofRecipes) { //constructor
        this.ctxt = ctxt;
        this.ListofRecipes = ListofRecipes;
    }

    @Override
    public RecipeView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctxt);
        View view = inflater.inflate(R.layout.generic_layout, null); //default layout of each recipe in different fragment
        return new RecipeView(view);
    }

    @Override
    public void onBindViewHolder(RecipeView holder, int position) {
        receipt receipt = ListofRecipes.get(position); //creating object receipt of type receipt (class)
        //.get is used to get recipe position

        //loading the image
        Glide.with(ctxt)
                .load(receipt.getImage()) //from where to laod image (image location i.e getImage which is iniated in receipt
                .into(holder.imageView); //load image to image view

        holder.textViewTitle.setText(receipt.getTitle()); //setting title of recipe
        holder.textViewDescription.setText(receipt.getdescription()); //setting recipe description

    }

    @Override
    public int getItemCount() {
        return ListofRecipes.size(); //counting size of list to determine how much to repeat
    }

    class RecipeView extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDescription;
        ImageView imageView;

        public RecipeView(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);//associating image according to its xml layout
            textViewDescription = itemView.findViewById(R.id.textViewDescription);//associating text according to its xml layout
            imageView = itemView.findViewById(R.id.imageView); //associating image according to its xml layout

        }
    }
}