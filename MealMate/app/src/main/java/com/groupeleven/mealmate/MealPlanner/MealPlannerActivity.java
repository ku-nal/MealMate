package com.groupeleven.mealmate.MealPlanner;

import com.groupeleven.mealmate.R;

import android.os.Bundle;
import android.view.View;
import java.time.LocalDate;
import java.util.List;
import android.content.Intent;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groupeleven.mealmate.MealPlanner.utils.CalendarAdapter;
import com.groupeleven.mealmate.MealPlanner.utils.CalendarUtils;
import static com.groupeleven.mealmate.MealPlanner.utils.CalendarUtils.daysInWeekArray;
import static com.groupeleven.mealmate.MealPlanner.utils.CalendarUtils.monthYearFromDate;

import com.groupeleven.mealmate.MealPlanner.models.Recipe;
import com.groupeleven.mealmate.MealPlanner.models.Meal;
import com.groupeleven.mealmate.MealPlanner.models.MealPlan;
import com.groupeleven.mealmate.MealPlanner.utils.MealPlanAdapter;

public class MealPlannerActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView mealListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_planner);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        loadMealPlan();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
//      setMealAdpater();
    }

    public void loadMealPlan() {
        // Create sample data
        Recipe recipe1 = new Recipe("Oatmeal", 1, "https://www.allrecipes.com/thmb/YpIrLAXU7ebXHRipczxmKbhPRzA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/265505-maple-and-brown-sugar-oatmeal-DDMFS-beauty_3x4-3228-e432bfe7d9fd4a3880979970814af34f.jpg");
        Recipe recipe2 = new Recipe("Fruit and Nut Oatmeal", 1, "https://www.eatingwell.com/thmb/-UULlbERQCfJRQTnb5bwjoo9-UQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/old-fashioned-oatmeal-hero-05-060861b81cb641cea272e068aba093fd.jpg");
        Recipe recipe3 = new Recipe("Chicken Salad", 2, "https://images.squarespace-cdn.com/content/v1/584f00ea2e69cf6c982e323f/748a75be-5332-4006-a994-142fbd5b98bf/chinese%2Bchicken%2Bsalad%2Band%2Bdressing-2.jpg");
        Recipe recipe4 = new Recipe("Vegetarian Wrap", 2, "https://www.acouplecooks.com/wp-content/uploads/2023/02/Veggie-Wrap-002.jpg");

        List<Recipe> recipes1 = new ArrayList<>();
        recipes1.add(recipe1);
        recipes1.add(recipe2);

        List<Recipe> recipes2 = new ArrayList<>();
        recipes2.add(recipe3);
        recipes2.add(recipe4);

        Meal meal1 = new Meal("Breakfast", recipes1);
        Meal meal2 = new Meal("Lunch", recipes2);

        List<Meal> meals = new ArrayList<>();
        meals.add(meal1);
        meals.add(meal2);

//        MealPlan mealPlan1 = new MealPlan("2023-10-11", meals);

        RecyclerView mealPlanRecyclerView = findViewById(R.id.mealPlanRecyclerView);
        mealPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MealPlanAdapter adapter = new MealPlanAdapter(this, meals);
        mealPlanRecyclerView.setAdapter(adapter);

    }

    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void newMealAction(View view) {
        Intent intent = new Intent(MealPlannerActivity.this, NewMealActivity.class);
        startActivity(intent);
    }
}