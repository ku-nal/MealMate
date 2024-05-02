package com.groupeleven.mealmate.MealPlanner.utils;

import com.groupeleven.mealmate.R;

import com.squareup.picasso.Picasso;

import java.util.List;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.groupeleven.mealmate.MealPlanner.models.Recipe;
import com.groupeleven.mealmate.MealPlanner.models.Meal;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MealViewHolder> {
    private List<Meal> meals;

    private AlertDialog alertDialog;

    private Context context;

    public MealPlanAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        private TextView mealNameTextView;
        private LinearLayout recipesLayout;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealNameTextView = itemView.findViewById(R.id.mealNameTextView);
            recipesLayout = itemView.findViewById(R.id.recipesLayout);
        }

        public void bind(Meal meal) {
            mealNameTextView.setText(meal.getMealName());
            recipesLayout.removeAllViews(); // Clear previous data if any

            for (Recipe recipe : meal.getRecipes()) {
                View recipeView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.recipe_item, recipesLayout, false);
                TextView recipeNameTextView = recipeView.findViewById(R.id.recipeNameTextView);
                TextView servingSizeTextView = recipeView.findViewById(R.id.servingSizeTextView);
                ImageView recipeImageView = recipeView.findViewById(R.id.recipeImageView);
                ImageButton editRecipeButton = recipeView.findViewById(R.id.editRecipeButton);

                recipeNameTextView.setText(recipe.getRecipeName());
                servingSizeTextView.setText("Serving Size: " + recipe.getServingSize());

                // Load the recipe image from the URL using Picasso
                Picasso.get().load(recipe.getImageUrl()).into(recipeImageView);
                editRecipeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Inflate the custom dialog layout
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.popup_dialog, null);

                        // Create the AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setView(dialogView);

                        // Find views within the dialog
                        EditText servingSizeEditText = dialogView.findViewById(R.id.servingSizeEditText);
                        Button updateButton = dialogView.findViewById(R.id.updateButton);
                        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
                        Button closeButton = dialogView.findViewById(R.id.closeButton);

                        // Set up button click actions
                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Handle the update action
                                // Retrieve the serving size from the EditText: servingSizeEditText.getText().toString()
                            }
                        });
                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Handle the delete action
                            }
                        });
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                            }
                        });

                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

                recipesLayout.addView(recipeView);
            }
        }
    }
}