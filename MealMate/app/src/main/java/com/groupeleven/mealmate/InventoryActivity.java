package com.groupeleven.mealmate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    private CollectionReference collectionRef;

    private List<IngredientsData> ingredientsDataList;
    private InventoryAdapter ingredientsAdapter;


    private List<CategoryData> category;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DBHelper dbHelper;

    private SQLiteDatabase sqlDBWrite;

    private SQLiteDatabase sqlDBRead;

    static int quantity = 0;

    Button startRecommendButton;

    private HashMap<String,List<IngredientsData>> categoryIngredientMap = new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        dbHelper = new DBHelper(this);
        sqlDBWrite = dbHelper.getWritableDatabase();

        setContentView(R.layout.inventory_mgmt);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        startRecommendButton = findViewById(R.id.start_recommend_button);
        LinearLayout buttonsLayout = findViewById(R.id.buttons_layout);
        buttonsLayout.setGravity(Gravity.CENTER_HORIZONTAL);



        populateCategories();
        // Add IngredientsData objects to the list here

        Window appWindow = this.getWindow();
        appWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        appWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        appWindow.setStatusBarColor(ContextCompat.getColor(this,R.color.primary_light_color));
    }

    private void populateCategories() {
        startRecommendButton.setVisibility(View.GONE);
        if (categoryAdapter != null) {
            recyclerView.setAdapter(categoryAdapter);
        } else {
            collectionRef = db.collection("Categories");

            collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        category = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Access data for each document here
                            String documentId = document.getId();
                            Map<String, Object> data = document.getData();
                            category.add(new CategoryData(data.get("Name").toString(),
                                    data.get("Url").toString()));
                        }
                        categoryAdapter = new CategoryAdapter(InventoryActivity.this, category);
                        recyclerView.setAdapter(categoryAdapter);

                    } else {
                        // Handle the error if the data retrieval fails
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Handle the exception
                            Log.e("Firebase Exception", exception.toString());
                        }
                    }
                }
            });

        }
    }

    public void onCategoryClick(View view){
        CardView cardView = (CardView) view.getParent().getParent();
        TextView txtQty = cardView.findViewById(R.id.category_name);
        String clickedCategory = txtQty.getText().toString();
        populateIngredientsOnCategories(clickedCategory);
    }

    public void populateIngredientsOnCategories(String category) {

        if (categoryIngredientMap.containsKey(category)) {
            ingredientsAdapter = new InventoryAdapter(InventoryActivity.this,
                    categoryIngredientMap.get(category));
            recyclerView.setAdapter(ingredientsAdapter);
        } else {
            collectionRef = db.collection("Ingredients");

            Query query = collectionRef.whereEqualTo("category", category);

            collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ingredientsDataList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Access data for each document here
                            String documentId = document.getId(); // Get the document ID
                            Map<String, Object> data = document.getData(); // Get the document data
                            String url = data.get("surl").toString();
                            String name = data.get("Name").toString();
                            String unit = data.get("unit").toString();
                            String ctgry = data.get("category").toString();

                            if (ctgry.toLowerCase().equals(category.toLowerCase())) {
                                ingredientsDataList.add(new IngredientsData(url, name, 0, 0, unit, ctgry));
                            }
                            // Do something with the data
                        }
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    ingredientsDataList = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        // Access data for each document here
//                        String documentId = document.getId(); // Get the document ID
//                        Map<String, Object> data = document.getData(); // Get the document data
//                        String url = data.get("surl").toString();
//                        String name = data.get("Name").toString();
//                        String unit = data.get("unit").toString();
//                        String category = data.get("category").toString();
//                        ingredientsDataList.add(new IngredientsData(url, name,0,0,unit,category));
//                        // Do something with the data
//                    }
                        categoryIngredientMap.put(category, ingredientsDataList);
                        ingredientsAdapter = new InventoryAdapter(InventoryActivity.this, ingredientsDataList);
                        recyclerView.setAdapter(ingredientsAdapter);
                    } else {
                        // Handle the error if the data retrieval fails
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Handle the exception
                            Log.e("Firebase Exception", exception.toString());
                        }
                    }
                }
            });
        }
    }

    public void onAddButtonClick(View view) {
        //As cardview is parent of parent of button (button --> layout --> cardview)
        CardView cardView = (CardView) view.getParent().getParent();

        //finding textview to change the quantity
        TextView txtQty = cardView.findViewById(R.id.ingt_qty_num);
        TextView ingtName = cardView.findViewById(R.id.ingt_name);

        //finding the relative position of the clicked view
        int pos = recyclerView.getChildAdapterPosition((View) cardView);
        IngredientsData ingredient = ingredientsDataList.get(pos);
        IngredientsData modifiedIngredient = categoryIngredientMap.get(ingredient.getIngredientCategory()).get(pos);
        int updatedQty = modifiedIngredient.getIngredientQty() + 1;
        txtQty.setText(updatedQty + "");
        modifiedIngredient.setIngredientQty(updatedQty);
    }

    // Click handler for the "Subtract" icon
    public void onSubtractButtonClick(View view) {

        CardView cardView = (CardView) view.getParent().getParent();

        //finding textview to change the quantity
        TextView txtQty = cardView.findViewById(R.id.ingt_qty_num);

        //finding the relative position of the clicked view
        int pos = recyclerView.getChildAdapterPosition((View) cardView);
        IngredientsData ingredient = ingredientsDataList.get(pos);
        int updatedQty = ingredient.getIngredientQty() - 1 >=0 ?
                ingredient.getIngredientQty() - 1: 0;
        txtQty.setText(updatedQty+"");
        ingredient.setIngredientQty(updatedQty);
    }

    public void onBackButtonClick(View view){
        populateCategories();
    }

    @Override
    public void onBackPressed() {
        if(onIngredientPage()){
            populateCategories();
        }
        else{
            super.onBackPressed();
        }
    }

    private boolean onIngredientPage(){
        return recyclerView.getAdapter() instanceof InventoryAdapter;
    }


    public void storeDataToSql(View view){
        ContentValues value = new ContentValues();
        int previousQty;

        for(String key : categoryIngredientMap.keySet()){
            for(IngredientsData data : categoryIngredientMap.get(key)){
                if(data.getIngredientQty()!=0) {
//                    value.put("id",i++);


                    previousQty = getExistIngredientQty(data.getIngredientName());

                    if(previousQty>0){
                        updateIngredientQty(previousQty,data);
                    }
                    else{
                        value.put("name", data.getIngredientName());
                        value.put("quantity", data.getIngredientQty());
                        value.put("category", data.getIngredientCategory());
                        value.put("unit", data.getUnit());
                        sqlDBWrite.insertWithOnConflict("user_ingredients", null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        value.clear();
                    }

                }
            }
        }
    }

    private void updateIngredientQty(int previousQty, IngredientsData data){

        String whereClause = "name = ?";
        String[] checkAttrs = {data.getIngredientName()};
        ContentValues setValues = new ContentValues();
        setValues.put("quantity",previousQty+data.getIngredientQty());


        int success = sqlDBWrite.update("user_ingredients", setValues,whereClause, checkAttrs);

        Log.d("Update Error", String.valueOf(success));
    }

    public int getExistIngredientQty(String checkIngredient){
        sqlDBRead = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        String[] selectAttrs = {"quantity"};

        String whereClause = "name = ?";

        String[] matchValue = {checkIngredient};

        cursor = sqlDBRead.query("user_ingredients", selectAttrs, whereClause,matchValue,
                null,null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex("quantity");
                if (index >= 0) {
                    int previousQty = cursor.getInt(index);
                    cursor.close();
                    return previousQty;
                }
            }
        }
        return 0;
    }

    public void showSelectedIngredients(){

    }
}
