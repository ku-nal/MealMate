package com.groupeleven.mealmate;

// A skeleton data class to populate the card items in the Recycler View
public class IngredientsData {
    private String ingredientImg;
    private String ingredientName;
    private int ingredientQty;
    private int noOfCook;

    private String ingredientCategory;
    private String unit;

    public IngredientsData(String ingredientImg, String ingredientName, int ingredientQty, int noOfCook, String unit, String ingredientCategory) {
        this.ingredientImg = ingredientImg;
        this.ingredientName = ingredientName;
        this.ingredientQty = ingredientQty;
        this.noOfCook = noOfCook;
        this.unit = unit;
        this.ingredientCategory = ingredientCategory;
    }

    public String getIngredientImg() {
        return ingredientImg;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getIngredientQty() {
        return ingredientQty;
    }

    public int getNoOfCook() {
        return noOfCook;
    }

    public String getIngredientCategory() {
        return ingredientCategory;
    }

    public String getUnit() {
        return unit;
    }
    public void setIngredientQty(int qty){
        ingredientQty = qty;
    }
}
