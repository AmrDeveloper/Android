package com.inventory.amrdeveloper.inventory.Activity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.inventory.amrdeveloper.inventory.Adapter.ProductAdapter;
import com.inventory.amrdeveloper.inventory.DataBase.ProductContract;
import com.inventory.amrdeveloper.inventory.R;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    //ListView To Save Products
    private ProductAdapter mCursorAdapter;
    private static final int PRODUCT_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView productList = findViewById(R.id.productList);
        mCursorAdapter = new ProductAdapter(this,null);
        productList.setAdapter(mCursorAdapter);
        productList.setEmptyView(findViewById(R.id.emptyTextView));
        //Loading Data From DataBase
        getLoaderManager().initLoader(PRODUCT_LOADER,null,this);
    }

    //Insert Product In DataBase
    private void insertDummyProduct(){
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME, "iphone");
        values.put(ProductContract.ProductEntry.COLUMN_PRICE, 16000);
        values.put(ProductContract.ProductEntry.COLUMN_IMAGE,new byte[]{});
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, 5);
        values.put(ProductContract.ProductEntry.COLUMN_PHONE, "01212909090");
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER, "Apple Inc");
        getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
    }

    //Go From This Main Activity To Editor Activity Using Intent
    public void floatButtonGoAddActivity(View view){
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        startActivity(intent);
    }

    //Delete All Products Using ContentResolver
    private void deleteAllProduct(){
        getContentResolver().delete(ProductContract.ProductEntry.CONTENT_URI,null,null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insertDummy:
                insertDummyProduct();
                return true;
            case R.id.deleteProducts:
                deleteAllProduct();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_IMAGE,
                ProductContract.ProductEntry.COLUMN_PHONE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_SUPPLIER
        };
        Loader<Cursor> cursorLoader =
        new CursorLoader(
                getApplicationContext(),
                ProductContract.ProductEntry.CONTENT_URI
                ,projection
                ,null
                ,null
                ,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
