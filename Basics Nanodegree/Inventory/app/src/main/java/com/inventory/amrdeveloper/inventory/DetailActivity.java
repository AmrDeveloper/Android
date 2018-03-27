package com.inventory.amrdeveloper.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inventory.amrdeveloper.inventory.DataBase.ProductContract;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText productName;
    private EditText productPrice;
    private EditText productQuantity;
    private EditText productSupplier;
    private EditText supplierPhone;

    private ImageView productImage;

    private Bitmap bitMapImage;

    //Current Product Uri
    private Uri currentUri;
    private static final int PRODUCT_LOADER = 1;
    private static final int GALLERY_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        this.currentUri = intent.getData();

        //Change Toolbar title
        if (currentUri == null) {
            setTitle("Order Product");
        } else {
            setTitle("Save Product");
            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        }

        this.productName = findViewById(R.id.productName);
        this.productPrice = findViewById(R.id.productPrice);
        this.productQuantity = findViewById(R.id.productQuantity);
        this.productSupplier = findViewById(R.id.productSupplier);
        this.supplierPhone = findViewById(R.id.supplierPhone);
        this.productImage = findViewById(R.id.productImage);
    }


    //Get The Image Using Intent Request
    public void getImageFromPhone(View view) {
        try{
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            //Set The Type of file as Image
            intent.setType("image/*");
            //Start This Intent request
            startActivityForResult(intent, GALLERY_REQUEST);
        } catch (Exception exp) {
            Log.i("Error", exp.toString());
        }
    }

    //Insert Product In DataBase
    private void saveNewProduct() {
        String name = productName.getText().toString();
        double price = Double.parseDouble(productPrice.getText().toString());
        int quantity = Integer.parseInt(productQuantity.getText().toString());
        String supplier = productSupplier.getText().toString();
        byte[] image = ImageConverter.getBytesFromBitmap(bitMapImage);

        if(name.equals("")){
            return;
        }

        if (image == null) {
            image = new byte[]{};
        }
        if (name.equals("")) {
            return;
        }
        if (supplier.equals("")) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME, name);
        values.put(ProductContract.ProductEntry.COLUMN_PRICE, price);
        values.put(ProductContract.ProductEntry.COLUMN_IMAGE, image);
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, quantity);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER, supplier);

        if (currentUri == null) {
            Uri uri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
            if (uri == null) {
                Toast.makeText(this, "NUll uri", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Update Current Product
    private void updateCurrentProduct() {
        if (this.currentUri != null) {

            String name = productName.getText().toString();
            double price = Double.parseDouble(productPrice.getText().toString());
            int quantity = Integer.parseInt(productQuantity.getText().toString());
            String supplier = productSupplier.getText().toString();
            String phone = supplierPhone.getText().toString();

            if (name.equals("")) {
                return;
            }

            if (supplier.equals("")) {
                return;
            }

            ContentValues values = new ContentValues();
            values.put(ProductContract.ProductEntry.COLUMN_NAME, name);
            values.put(ProductContract.ProductEntry.COLUMN_PRICE, price);
            values.put(ProductContract.ProductEntry.COLUMN_IMAGE, 0);
            values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, quantity);
            values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER, supplier);

            int updatedId = getContentResolver().update(currentUri, values, null, null);
        }
    }

    //Delete Current Product
    private void deleteCurrentProduct() {
        String selection = ProductContract.ProductEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(ContentUris.parseId(currentUri))};
        getContentResolver().delete(ProductContract.ProductEntry.CONTENT_URI, selection, selectionArgs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate The Menu
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.orderProduct:
                if (currentUri == null) {
                    saveNewProduct();
                } else {
                    updateCurrentProduct();
                }
                finish();
                return true;

            case R.id.deleteProduct:
                //Delete This Product
                deleteCurrentProduct();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_IMAGE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_SUPPLIER
        };

        Loader<Cursor> cursorLoader =
                new CursorLoader(
                        getApplicationContext(),
                        currentUri,
                        projection,
                        null,
                        null,
                        null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            //Update Uri
            //Get Column Index
            int nameColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME);
            int priceColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRICE);
            int imageColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_IMAGE);
            int quantityColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_SUPPLIER);

            //Get Values
            String name = cursor.getString(nameColumnIndex);
            String price = cursor.getString(priceColumnIndex);
            String quantity = cursor.getString(quantityColumnIndex);
            String supplier = cursor.getString(supplierColumnIndex);
            byte[] imageBytes = cursor.getBlob(imageColumnIndex);

            if (imageBytes.length != 0) {
                //Convert Byte Array to Bitmap
                Bitmap bitmap = ImageConverter.getBitmapFromBytes(imageBytes);
                //Update Ui
                this.productImage.setImageBitmap(bitmap);
            }

            //Update UI
            this.productName.setText(name);
            this.productPrice.setText(price);
            this.productQuantity.setText(quantity);
            this.productSupplier.setText(supplier);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Make Ui Views With Default Value
        this.productName.setText("");
        this.productPrice.setText("");
        this.productQuantity.setText("");
        this.productSupplier.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check if this request is My Intent Request
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            //Get The Image File in Uri
            Uri uri = data.getData();
            //Update Ui
            productImage.setImageURI(uri);
            try {
                bitMapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException ex) {
                Log.v("Order", "Can't Convert Uri to Bitmap");
            }
        }
    }
}
