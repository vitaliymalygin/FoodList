package com.example.vitaliy.foodlist;

/**
 * Created by vitaliy on 23.06.15.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.sql.SQLException;

public class FoodItem extends Activity {
    String id;
    DbHelper dbAdapter;

    final String LOG_TAG = "myLogs";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_item);
        Log.d(LOG_TAG, "getExtra");
        Intent intent=getIntent();
        id = intent.getStringExtra("idOfFoodItem");
        try {
            dbAdapter.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor c= dbAdapter.getFoodItem(id);
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);

                } while (c.moveToNext());
            }
            c.close();
        } else
            Log.d(LOG_TAG, "Cursor is null");


    }


}
