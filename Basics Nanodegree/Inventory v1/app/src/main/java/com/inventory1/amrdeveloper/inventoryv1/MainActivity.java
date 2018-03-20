package com.inventory1.amrdeveloper.inventoryv1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        //Insert Dummy Data Into DataBase
        insertData();

        //Get Data Query In Cursor
        Cursor cursor = queryData();
        //Get The Length of Cursor
        int dbLength = cursor.getCount();
        //Show This Length in Log
        Log.v("DataBase Length", String.valueOf(dbLength));
    }

    //Insert Data To DataBase
    private void insertData() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        // Insert into database.
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME, "iphone");
        values.put(ProductContract.ProductEntry.COLUMN_PRICE, 1600);
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, 10);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER, "AmrDeveloper");
        values.put(ProductContract.ProductEntry.COLUMN_PHONE, "01212494046");

        //Show This Values In Log
        Log.v("Insert",String.valueOf(values));

        //Insert
        long newRowInsertId = sqLiteDatabase.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);

        if (newRowInsertId == -1) {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
    }

    //Get Data From DataBase as Cursor
    private Cursor queryData() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        //Projection
        String[] projection = {
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_SUPPLIER,
                ProductContract.ProductEntry.COLUMN_PHONE
        };
        //Get Data In Cursor Object
        Cursor cursor =
                sqLiteDatabase.query(
                        ProductContract.ProductEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Get Index For Every Attribute
        int productNameIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME);
        int productPriceIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRICE);
        int productQuantityIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_QUANTITY);
        int productSupplierIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_SUPPLIER);
        int productSupplierPhoneIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PHONE);

        //Loop to get All Products in database
        while (cursor.moveToNext()) {

            //Get Attribute
            String name = cursor.getString(productNameIndex);
            double price = cursor.getDouble(productPriceIndex);
            int quantity = cursor.getInt(productQuantityIndex);
            String suppplierName = cursor.getString(productSupplierIndex);
            String supplierPhone = cursor.getString(productSupplierPhoneIndex);

            //Print Information In Log
            Log.v("Product", "Name : " + name);
            Log.v("Product", "Price : " + price);
            Log.v("Product", "quantity : " + quantity);
            Log.v("Product", "Supplier Name : " + suppplierName);
            Log.v("Product", "Supplier Phone : " + supplierPhone);
        }
        //Close Cursor
        cursor.close();
        //Return This Cursor
        return cursor;
    }
}
