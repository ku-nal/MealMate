package com.groupeleven.mealmate;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InventoryAdapter ingredientsAdapter;

    private CollectionReference collectionRef;

    private List<IngredientsData> ingredientsDataList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DBHelper dbHelper;

    private SQLiteDatabase sqlDB;

    static int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        dbHelper = new DBHelper(this);
        sqlDB = dbHelper.getWritableDatabase();

        setContentView(R.layout.activity_main);

        Window appWindow = this.getWindow();
        appWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        appWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        appWindow.setStatusBarColor(ContextCompat.getColor(this,R.color.primary_light_color));
    }

    public void onUpdatedButtonClick(View view){
        ContentValues value = new ContentValues();

        for(IngredientsData data : ingredientsDataList){
            if(data.getIngredientQty()!=0) {
                value.put("name", data.getIngredientName());
                value.put("quantity", data.getIngredientQty());
                value.put("category", data.getIngredientCategory());
                value.put("unit", data.getUnit());
            }
        }
        sqlDB.insert("user_ingredients",null,value);
    }
}



