package com.example.vitaliy.foodlist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vitaliy.foodlist.Dish;
import com.example.vitaliy.foodlist.FoodCategory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class DbHelper extends SQLiteOpenHelper{

    private static String DB_NAME = "USDA.DB";
    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "usda.db";
    private static final String DATABASE_TABLE = "food";
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/com.example.vitaliy.foodlist/databaces/";
    //constant names
    public static final String DISH_ID = "id";
    public static final String DISH_NAME = "name";
    public static final String CAT_ID = "id";
    public static final String CAT_CATEGORY = "category";
    public static final String DISH_WATER = "water", DISH_ENERGY = "energy", DISH_ASH = "ash", DISH_FIBER = "fiber",
            DISH_SUGAR = "sugar", DISH_CALCIUM = "calcium", DISH_IRON = "iron", DISH_MAGNESIUM = "magnesium",
            DISH_PHOSSPHORUS = "phosphorus", DISH_POTASSIUM = "potassium", DISH_SODIUM = "sodium",
            DISH_ZINK = "zink", DISH_COPPER = "copper", DISH_MANGANESE = "manganese", DISH_SELENIIM = "selenium",
            DISH_VIT_C = "vit_c", DISH_THAMIN = "thamin", DISH_RIBOFLAVIN = "riboflavin", DISH_NIACIN = "niacin",
            DISH_PANTO_ACID = "panto_acid", DISH_VIT_B6 = "vit_b6", DISH_FOLATE_TOT = "folate_Tot",
            DISH_FOLIC_ACID = "folic_acid", DISH_FOOD_FOLATE = "food_folate", DISH_FOLATE_DFE = "folate_DFE",
            DISH_CHLOLINE_TOT = "choline_tot", DISH_VIT_B12 = "vit_b12", DISH_VIT_A_IU = "vit_a_iu",
            DISH_VIT_A_RAE = "vit_a_RAE", DISH_RETINOL = "retinol", DISH_ALPHA_CAROT = "alpha_carot",
            DISH_BETA_CAROT = "beta_carot", DISH_BETA_CRYPT = "beta_crypt", DISH_LYCOPENE = "lycopene",
            DISH_LUTZEA = "lutzea", DISH_VIT_E = "vit_e", DISH_VIT_D = "vit_d", DISH_VIT_D_IU = "vit_d_iu",
            DISH_VIT_K = "vit_k", DISH_FA_SAT = "fa_sat", DISH_FA_MONO = "fa_mono", DISH__FA_POLY = "fa_poly",
            DISH_CHLOLESTEL = "cholestel", DISH_GMAWT_1 = "gmWt_1", DISH_GMAWT_DESC_1 = "gmWt_desc_1",
            DISH_GMWT_2 = "gm_Wt_2", DISH_GMAWT_DESC_2 = "gmWt_Desc2", DISH_REDISE_PCT = "refise_pct",
            DISH_PROTEIN = "protein", DISH_LIPID = "lipid", DISH_CARBO = "carbo";

    String name = "USDA_DB";
    int version = 3;

    private Context context = FoodCategory.getContext();


        /**
         * Конструктор
         * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
         * @param context
         */
        public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }




    public Cursor getAllFoodCategoriesCursor() {
        return db.query(DATABASE_TABLE,
                new String[]{CAT_ID, CAT_CATEGORY}, null, null, null, null,
                null);
    }

    public Cursor getFoodItem(String id) {
        return db.query(DATABASE_TABLE, new String[]{"name", "water", "energy"}, "id = ?", new String[]{id}, null, null, null);


    }


    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String DB_PATH = "/data/data/" + context.getPackageName()
                    + "/databases/";
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from local assets-folder to the just created empty
     * database in the system folder, from where it can be accessed and handled.
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String DB_PATH = "/data/data/" + context.getPackageName()
                + "/databases/";
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        // Open the database
        String DB_PATH = "/data/data/" + context.getPackageName()
                + "/databases/";
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS);

    }

    @Override
    public synchronized void close() {

        if (db != null)
            db.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}