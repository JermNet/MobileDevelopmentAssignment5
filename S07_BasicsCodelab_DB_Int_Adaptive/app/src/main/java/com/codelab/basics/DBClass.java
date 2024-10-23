package com.codelab.basics;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;

public class DBClass extends SQLiteOpenHelper implements DB_Interface {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "DB_Name.db";

    // If you change the database schema, you must increment the database version.
    private static final String TABLE_NAME = "sample_table";
    private static final String TEXT_TYPE = " TEXT";
    private static final String NUM_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String _ID = "_ID";
    private static final String _COL_1 = "dex_col";
    private static final String _COL_2 = "name_col";
    private static final String _COL_3 = "type1_col";
    private static final String _COL_4 = "type2_col";
    private static final String _COL_5 = "total_col";
    private static final String _COL_6 = "hp_col";
    private static final String _COL_7 = "attack_col";
    private static final String _COL_8 = "defense_col";
    private static final String _COL_9 = "specialAttack_col";
    private static final String _COL_10 = "specialDefense_col";
    private static final String _COL_11 = "speed_col";
    private static final String _COL_12 = "generation_col";
    private static final String _COL_13 = "legendary_col";
    private static final String _COL_14 = "access_col";

    // Create the table in Java
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, dex_col INTEGER," +
                    "name_col VARCHAR(256), type1_col VARCHAR(256), type2_col VARCHAR(256), total_col INTEGER," +
                    "hp_col INTEGER, attack_col INTEGER, defense_col INTEGER, specialAttack_col INTEGER," +
                    "specialDefense_col INTEGER, speed_col INTEGER, generation_col INTEGER, legendary_col INTEGER," +
                    "access_col INTEGER)";
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private Context context;

    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Create the table in SQL and log
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("DBClass", "DB onCreate() " + SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
        Log.d("DBClass", "DB onCreate()");

        // Old code with embedded SQL
//        db.execSQL("CREATE TABLE sample_table (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, str_col VARCHAR(256), num_col INTEGER)");
//
//        db.execSQL(
//                "INSERT INTO sample_table(str_col,num_col) VALUES('Ford', 100)");
//        db.execSQL(
//                "INSERT INTO sample_table(str_col,num_col) VALUES('Toyota', 200)");
//        db.execSQL(
//                "INSERT INTO sample_table(str_col,num_col) VALUES('Honda', 300)");
//        db.execSQL(
//                "INSERT INTO sample_table(str_col,num_col) VALUES('GM', 400)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d("DBClass", "DB onUpgrade() to version " + DATABASE_VERSION);
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    /////////// Implement Interface ///////////////////////////
    @Override
    public int count() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.v("DBClass", "getCount=" + cnt);
        return cnt;
    }

    @Override
    public int save(DataModel dataModel) {
        // Logs and puts the dataModel (Pokemon) into the DB
        Log.v("DBClass", "add=>  " + dataModel.toString());

        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(_COL_1, dataModel.getDexNumber());
        values.put(_COL_2, dataModel.getPokemonName());
        values.put(_COL_3, dataModel.getType1());
        values.put(_COL_4, dataModel.getType2());
        values.put(_COL_5, dataModel.getTotal());
        values.put(_COL_6, dataModel.getHP());
        values.put(_COL_7, dataModel.getAttack());
        values.put(_COL_8, dataModel.getDefense());
        values.put(_COL_9, dataModel.getSpecialAttack());
        values.put(_COL_10, dataModel.getSpecialDefense());
        values.put(_COL_11, dataModel.getSpeed());
        values.put(_COL_12, dataModel.getGeneration());
        values.put(_COL_13, dataModel.getLegendary());
        values.put(_COL_14, dataModel.getAccess());

        // Insert
        db.insert(TABLE_NAME, // Table
                null, // null column hack
                values); // key/value -> keys = column names/ values = column values

        // Close
        db.close();
        return 0;
    }

    // Since the only data that is actively being changed is the access count, this update method just updates that
    @Override
    public void update(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_COL_14, dataModel.getAccess()+1);

        db.update(TABLE_NAME, values, "name_col = ?", new String[] { dataModel.getPokemonName() } );
        db.close();
    }

    // Now gets data from the CSV file and adds it to the DB, now in a try catch since we're working with more outside factors
    private void addDefaultRows(){
        // Call count once
        int doCount = this.count();
        if (doCount > 1) {
            Log.v("DBClass", "Already rows in DB");

        } else {
            Log.v("DBClass", "No rows in DB... Adding from CSV");
            BufferedReader bufferedReader = null;
            int loop = 0;
            try {
                InputStream inputStream = context.getAssets().open("pokemon.csv");
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                // This is to skip the first line, which contains the names for the cols in the CSV
                bufferedReader.readLine();

                while ((line = bufferedReader.readLine()) != null) {
                    String[] pokeData = line.split(",");
                    int id = loop;
                    loop++;

                    int dexNumber = Integer.parseInt(pokeData[0]);
                    String name = pokeData[1];
                    String type1 = pokeData[2];

                    // Instead of having an empty value for a second type if the Pokemon doesn't have it, "None" is used.
                    // No big reason for it, I just prefer it this way
                    String type2 = !pokeData[3].isEmpty() ? pokeData[3] : "None";
                    int total = Integer.parseInt(pokeData[4]);
                    int hp = Integer.parseInt(pokeData[5]);
                    int attack = Integer.parseInt(pokeData[6]);
                    int defense = Integer.parseInt(pokeData[7]);
                    int specialAttack = Integer.parseInt(pokeData[8]);
                    int specialDefense = Integer.parseInt(pokeData[9]);
                    int speed = Integer.parseInt(pokeData[10]);
                    int generation = Integer.parseInt(pokeData[11]);

                    // Legendary check because I can't use boolean because Google is dumb (no cursor.getBoolean method)
                    int legendary;
                    if (pokeData[12].equals("True")) {
                        legendary = 1;
                    } else {
                        legendary = 0;
                    }
                    int access = 0;

                    DataModel dataModel = new DataModel(id, dexNumber, name, type1, type2, total, hp, attack,
                            defense, specialAttack, specialDefense, speed, generation, legendary, access);
                    this.save(dataModel);
                }

            } catch (IOException e){
                Log.e("DBClass", "Error reading CSV", e);
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e("DBClass", "Error closing reader", e);
                    }
                }
            }
        }

    }

    @Override
    public List<DataModel> findAll() {
        List<DataModel> temp = new ArrayList<DataModel>();

        // If no rows, add
        addDefaultRows();

        // Build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // Get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Go over each row, build and add it to list
        DataModel item;
        if (cursor.moveToFirst()) {
            do {
                // This code puts a dataModel object into the PlaceHolder for the fragment if you had more columns in the DB,
                // you'd format  them in the non-details list here
                item = new DataModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6),
                        cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10),
                        cursor.getInt(11), cursor.getInt(12), cursor.getInt(13), cursor.getInt(14));
                temp.add(item);
            } while (cursor.moveToNext());
        }
        Log.v("DBClass", "findAll=> " + temp.toString());

        // Return all
        return temp;
    }

    // These are never used, but kept since they inherit from the interface, and that doesn't need to be changed to add anything here
    @Override
    public String getNameById(Long id) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    // Never used but kept here to look at
    /*
    private void dump() {
    }  // oops, never got around to this...but findall is dump-ish
     */
}
