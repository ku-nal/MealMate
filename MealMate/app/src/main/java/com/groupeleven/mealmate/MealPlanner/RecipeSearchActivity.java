package com.groupeleven.mealmate.MealPlanner;

import com.groupeleven.mealmate.MealPlanner.models.Recipe;
import com.groupeleven.mealmate.MealPlanner.utils.RecipeSearchAdapter;
import com.groupeleven.mealmate.R;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchActivity extends AppCompatActivity {

    private ListView recipeSearchResultsListView;
    private ArrayAdapter<String> recipeSearchResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        recipeSearchResultsListView = findViewById(R.id.recipeSearchList);

        ListView listView = findViewById(R.id.recipeSearchList);

        List<Recipe> recipeList = new ArrayList<>();

        Recipe recipe1 = new Recipe("Oatmeal", 1, "https://www.allrecipes.com/thmb/YpIrLAXU7ebXHRipczxmKbhPRzA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/265505-maple-and-brown-sugar-oatmeal-DDMFS-beauty_3x4-3228-e432bfe7d9fd4a3880979970814af34f.jpg");
        Recipe recipe2 = new Recipe("Fruit and Nut Oatmeal", 1, "https://www.eatingwell.com/thmb/-UULlbERQCfJRQTnb5bwjoo9-UQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/old-fashioned-oatmeal-hero-05-060861b81cb641cea272e068aba093fd.jpg");
        Recipe recipe3 = new Recipe("Chicken Salad", 2, "https://images.squarespace-cdn.com/content/v1/584f00ea2e69cf6c982e323f/748a75be-5332-4006-a994-142fbd5b98bf/chinese%2Bchicken%2Bsalad%2Band%2Bdressing-2.jpg");
        Recipe recipe4 = new Recipe("Vegetarian Wrap", 2, "https://www.acouplecooks.com/wp-content/uploads/2023/02/Veggie-Wrap-002.jpg");

        recipeList.add(recipe1);
        recipeList.add(recipe2);
        recipeList.add(recipe3);
        recipeList.add(recipe4);

        RecipeSearchAdapter adapter = new RecipeSearchAdapter(this, recipeList);

        listView.setAdapter(adapter);


        Button cancelButton = findViewById(R.id.recipe_search_cancel_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recipeSearchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedRecipe = recipeSearchResultsAdapter.getItem(position);
            }
        });
    }
}