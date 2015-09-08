package com.example.vitaliy.foodlist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by vitaliy on 21.06.15.
 */
public class FoodType extends Activity {

    private ArrayList<Dish> dishes;
    private ListView listView;
    private ShowFoodAdapter showFoodAdapter;
    DbHelper dbAdapter;
    Cursor foodListCursor;

    Dish dishToReturn;
    String category;
    int weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_type);
        Intent intent=getIntent();

        listView = (ListView) findViewById(R.id.listViewFoodType);
        dishes = new ArrayList<Dish>();
        int resId = android.R.layout.simple_list_item_1;
        showFoodAdapter = new ShowFoodAdapter(this, resId, dishes);
        listView.setAdapter(showFoodAdapter);
        category = intent.getStringExtra("categoryName");


        try {
            dbAdapter.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // updateArray();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View v,
                                    final int position, long id) {


                Intent myIntent = new Intent(v.getContext(), FoodItem.class);

                Dish dishItemToTake = (Dish) listView
                        .getItemAtPosition(position);

               // dishItemToTake.setCategory("category");
               // Long BoxID = (long) 0;
               // dishItemToTake.setBoxId(BoxID);


                myIntent.putExtra("idOfFoodItem",dishItemToTake.getId() );
                startActivityForResult(myIntent, 0);

                foodListCursor.close();
                dbAdapter.close();

                finish();
            }

        });

    }

   /** private void updateArray() {

        dishes.clear();
        foodListCursor = dbAdapter.getCurrentFoodTypesCursor(category);

        if (foodListCursor.moveToFirst())
            do {
                long _id = foodListCursor.getLong(foodListCursor
                        .getColumnIndex(dbAdapter.DISH_ID));
                String name = foodListCursor
                        .getString(foodListCursor
                                .getColumnIndex(dbAdapter.DISH_NAME));
                String category = foodListCursor
                        .getString(foodListCursor
                                .getColumnIndex(dbAdapter.CAT_CATEGORY));
                String carbo = foodListCursor
                        .getDouble(foodListCursor
                                .getColumnIndex(dbAdapter.DISH_CARBO));
                String fat = foodListCursor
                        .getDouble(foodListCursor
                                .getColumnIndex(dbAdapter.DISH_LIPID));
                double prot = foodListCursor
                        .getDouble(foodListCursor
                                .getColumnIndex(dbAdapter.DISH_PROTEIN));
                double ccal = foodListCursor
                        .getDouble(foodListCursor
                                .getColumnIndex(dbAdapter.DISH_ENERGY));
                /**int glycIndex = foodListCursor
                        .getInt(foodListCursor
                                .getColumnIndex(dev.diacompanion.DiaDBAdapter.FOOD_GLYCINDEX));
                int profit = foodListCursor
                        .getInt(foodListCursor
                                .getColumnIndex(dev.diacompanion.DiaDBAdapter.FOOD_PROFIT));

                Dish newItem = new Dish();
                newItem.setId(_id);
                newItem.setName(name);
                newItem.setCategory(category);
                newItem.setCarbo(carbo);
                newItem.setFat(fat);
                newItem.setProt(prot);
                newItem.setCcal(ccal);
                newItem.setGlycIndex(glycIndex);
                newItem.setProfit(profit);

                dishes.add(0, newItem);
            } while (foodListCursor.moveToNext());

        Collections.reverse(dishes);
        showFoodAdapter.notifyDataSetChanged();
        foodListCursor.close();
    }**/


    @Override
    protected void onResume() {
        super.onResume();
        //updateArray();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbAdapter != null) {
            dbAdapter.close();
        }
    }
}