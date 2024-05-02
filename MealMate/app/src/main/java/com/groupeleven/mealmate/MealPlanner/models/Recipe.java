package com.groupeleven.mealmate.MealPlanner.models;


public class Recipe {
    private String recipeName;
    private int servingSize;

    private String imageUrl;

    public Recipe(String recipeName, int servingSize, String imageUrl) {
        this.recipeName = recipeName;
        this.servingSize = servingSize;
        this.imageUrl = imageUrl;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getServingSize() {
        return servingSize;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}