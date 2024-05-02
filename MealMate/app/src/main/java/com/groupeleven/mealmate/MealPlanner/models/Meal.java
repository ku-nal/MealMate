package com.groupeleven.mealmate.MealPlanner.models;

import java.util.List;

public class Meal {
    private String mealName;
    private List<Recipe> recipes;

    public Meal(String mealName, List<Recipe> recipes) {
        this.mealName = mealName;
        this.recipes = recipes;
    }

    public String getMealName() {
        return mealName;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}