package com.example.cookingapp;

public class receipt {
    private int id;
    private String title;
    private String description;
    private String image;

    public receipt(int id, String title, String description, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getdescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

}
