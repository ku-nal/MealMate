package com.groupeleven.mealmate.MealPlanner;

import com.groupeleven.mealmate.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewMealActivity extends AppCompatActivity {

    private Spinner mealTypesSpinner;
    private EditText searchRecipeEditText;
    private EditText servingSizeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);

        mealTypesSpinner = findViewById(R.id.spinnerMealTypes);
        servingSizeEditText = findViewById(R.id.etServingSize);

        // Define an array of temporary meal types
        String[] mealTypes = {"Select Meal Type", "Breakfast", "Lunch", "Dinner", "Snack", "Create New"};

        ArrayAdapter<String> mealTypesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mealTypes
        );

        mealTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTypesSpinner.setAdapter(mealTypesAdapter);

        mealTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedMealType = mealTypesSpinner.getSelectedItem().toString();
                if ("Create New".equals(selectedMealType)) {
                    // Show a field to create a new meal type
                } else {
                    // Hide the "Create New" field if it was previously shown
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        Button searchRecipeButton = findViewById(R.id.btnSearchRecipe);
        searchRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewMealActivity.this, RecipeSearchActivity.class);
                startActivity(intent);
            }
        });

        Button saveMealPlanButton = findViewById(R.id.btnSaveMealPlan);
        saveMealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateMealPlan()) {
                    // Save the meal plan
                    finish(); // Close the activity
                }
            }
        });

        Button cancelButton = findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Close the activity without saving
            }
        });
    }

    // Function to validate the meal plan
    private boolean validateMealPlan() {
        String selectedMealType = mealTypesSpinner.getSelectedItem().toString();
        String selectedRecipe = searchRecipeEditText.getText().toString();
        String servingSize = servingSizeEditText.getText().toString();

        if ("Create New".equals(selectedMealType)) {
            // Handle the case where a new meal type is not specified
            return false;
        } else if (selectedRecipe.isEmpty()) {
            // Handle the case where a recipe is not specified
            return false;
        } else if (servingSize.isEmpty()) {
            // Handle the case where serving size is not specified
            return false;
        }

        return true;
    }
}