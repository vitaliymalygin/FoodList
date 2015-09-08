package com.example.vitaliy.foodlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by vitaliy on 21.06.15.
 */
public class FoodCategory extends Activity {

    private String categoryName;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    DbHelper dbAdapter;
    Cursor catCursor;
    private ArrayList<String> categories;
    private static FoodCategory instance;

    public FoodCategory() {
        instance = this;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodcategory);

        //SET LISTVIEW AND ADAPTER

        listView=(ListView)findViewById(R.id.listViewCat);

        categories=new ArrayList<String>();
        int listId=android.R.layout.simple_list_item_1;
        arrayAdapter=new ArrayAdapter<String>(this,listId,categories);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View v,
                                    int position, long id) {

                // Choose this category
                categoryName = (String) listView.getItemAtPosition(position);
                Intent myIntent = new Intent(v.getContext(), FoodType.class);
                myIntent.putExtra("categoryName", categoryName);
                startActivityForResult(myIntent, 0);
                catCursor.close();
                dbAdapter.close();
                finish();
            }
        });
        dbAdapter = new DbHelper(this);
        try {
            dbAdapter.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        populateList();

    }
    private void populateList() {
        categories.clear();
        catCursor = dbAdapter.getAllFoodCategoriesCursor();
        updateArray();
    }
    private void updateArray() {
        // Refresh the list of food categories
        if (catCursor.moveToFirst())
            do {
                String name = catCursor
                        .getString(catCursor
                                .getColumnIndex(DbHelper.CAT_CATEGORY));
                categories.add(0, name);
            } while (catCursor.moveToNext());

        Collections.reverse(categories);
        arrayAdapter.notifyDataSetChanged();
        catCursor.close();
    }


    public static Context getContext() {
        return instance;
    }

}
