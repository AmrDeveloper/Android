package com.inventory.amrdeveloper.inventory.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AmrDeveloper on 2/11/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = SQLiteHelper.class.getSimpleName();

    public final static String DATABASE_NAME = "product.db";
    public final static int DATABASE_VERSION = 1;


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a String that contains the SQL statement to create the Products table
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME + " (" +
                ProductContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductContract.ProductEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ProductContract.ProductEntry.COLUMN_PRICE + " REAL NOT NULL, " +
                ProductContract.ProductEntry.COLUMN_IMAGE + " BLOB NOT NULL," +
                //ProductContract.ProductEntry.COLUMN_PHONE + " TEXT NOT NULL," +
                ProductContract.ProductEntry.COLUMN_QUANTITY + " INTEGER  NOT NULL, " +
                ProductContract.ProductEntry.COLUMN_SUPPLIER + " TEXT  NOT NULL);";

        //Create Database
        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //ReCreate Sqlite DataBase if version is different
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
    }


}
