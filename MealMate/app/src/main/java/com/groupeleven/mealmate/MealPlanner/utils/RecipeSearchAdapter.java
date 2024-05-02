package com.groupeleven.mealmate.MealPlanner.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupeleven.mealmate.MealPlanner.models.Recipe;
import com.groupeleven.mealmate.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeSearchAdapter extends BaseAdapter {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeSearchAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recipe_card_item, parent, false);
        }

        // Get the current recipe
        Recipe recipe = recipeList.get(position);

        // Bind data to the views in the item layout
        ImageView imageView = convertView.findViewById(R.id.recipeImageView);
        TextView titleTextView = convertView.findViewById(R.id.recipeName);
        TextView servingTextView = convertView.findViewById(R.id.servingSize);

        Picasso.get().load(recipe.getImageUrl()).into(imageView);

        titleTextView.setText(recipe.getRecipeName());
        servingTextView.setText(String.valueOf(recipe.getServingSize()));

        return convertView;
    }
}
