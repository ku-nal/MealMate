package com.groupeleven.mealmate.MealPlanner.models;

import java.util.List;

public class MealPlan {
    private String date;
    private List<Meal> meals;

    public MealPlan(String date, List<Meal> meals) {
        this.date = date;
        this.meals = meals;
    }

    public String getDate() {
        return date;
    }

    public List<Meal> getMeals() {
        return meals;
    }
}