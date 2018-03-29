package com.inventory.amrdeveloper.inventory.Activity;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inventory.amrdeveloper.inventory.DataBase.ProductContract;
import com.inventory.amrdeveloper.inventory.R;
import com.inventory.amrdeveloper.inventory.Utils.ImageConverter;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //This Activity Views
    private EditText productName;
    private EditText productPrice;
    private EditText productQuantity;
    private EditText productSupplier;
    private EditText supplierPhone;
    private ImageView productImage;
    private Button callSupplier;
    private Button productPlus;
    private Button productMin;

    private Bitmap bitMapImage;

    //Current Product Uri
    private Uri currentUri;
    private static final int PRODUCT_LOADER = 1;
    private static final int GALLERY_REQUEST = 100;

    //Boolean Var To Check if this product changed or not
    private boolean mProductHasChanged = false;
    private final View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        this.currentUri = intent.getData();

        //Change Toolbar title
        if (currentUri == null) {
            setTitle(getString(R.string.order_product));
        } else {
            setTitle(getString(R.string.save_product));
            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        }
        initializationViews();
        viewsSetTouchListener();
    }

    //initialization The Views
    private void initializationViews(){
        this.productName = findViewById(R.id.productName);
        this.productPrice = findViewById(R.id.productPrice);
        this.productQuantity = findViewById(R.id.productQuantity);
        this.productSupplier = findViewById(R.id.productSupplier);
        this.supplierPhone = findViewById(R.id.supplierPhone);
        this.productImage = findViewById(R.id.productImage);
        this.callSupplier = findViewById(R.id.callSupplier);
        this.productPlus = findViewById(R.id.productPlus);
        this.productMin = findViewById(R.id.productMin);
    }

    //Set on Touch Listener
    private void viewsSetTouchListener(){
        this.productName.setOnTouchListener(mTouchListener);
        this.productPrice.setOnTouchListener(mTouchListener);
        this.productQuantity.setOnTouchListener(mTouchListener);
        this.productSupplier.setOnTouchListener(mTouchListener);
        this.supplierPhone.setOnTouchListener(mTouchListener);
        this.callSupplier.setOnTouchListener(mTouchListener);
        this.productPlus.setOnTouchListener(mTouchListener);
        this.productMin.setOnTouchListener(mTouchListener);
    }

    //Get The Image Using Intent Request
    public void getImageFromPhone(View view) {
        try {
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

    //Call Supplier Using Intent
    public void callSupplierButton(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + supplierPhone.getText().toString().trim()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //Product Quantity plus one
    public void plusOneQuantity(View view) {
        //Get The Current Product Quantity From Edit Text
        String quantityStr = productQuantity.getText().toString().trim();
        //Convert Quantity To Number
        int currentQuantity = Integer.parseInt(quantityStr);
        //Convert The New Quantity after add one to it to String
        String newQuantity = String.valueOf(currentQuantity + 1);
        //Update The EditText UI
        productQuantity.setText(newQuantity);
    }

    //Product Quantity minus one
    public void minOneQuantity(View view) {
        //Get The Current Product Quantity From Edit Text
        String quantityStr = productQuantity.getText().toString().trim();
        //Convert Quantity To Number
        int currentQuantity = Integer.parseInt(quantityStr);
        //Assert That Quantity Can't be negative
        if (currentQuantity > 0) {
            //Update The Current Quantity and convert it to String
            String newQuantnty = String.valueOf(currentQuantity - 1);
            //Update The EditText UI
            productQuantity.setText(newQuantnty);
        } else {
            Toast.makeText(this, "You Can't Order Negative Product", Toast.LENGTH_SHORT).show();
        }
    }

    //Check for All Edit Vies if Information Is Valid
    private boolean checkViewsValid() {
        String name = productName.getText().toString();
        String price = productPrice.getText().toString();
        String supplierName = productSupplier.getText().toString();
        String Phone = supplierPhone.getText().toString();

        //Check if Any String is Empty
        if (TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(price)
                || TextUtils.isEmpty(supplierName)
                || TextUtils.isEmpty(Phone)) {
            return false;
        }

        return true;
    }

    //Insert Product In DataBase
    private void saveNewProduct() {
        boolean isValid = checkViewsValid();
        if (!isValid) {
            Toast.makeText(this, "Sorry Invalid Information", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = productName.getText().toString();

        int quantity = Integer.parseInt(productQuantity.getText().toString());

        String priceString = productPrice.getText().toString();

        Double price = Double.parseDouble(priceString);

        String supplier = productSupplier.getText().toString();

        String phone = supplierPhone.getText().toString();

        byte[] image = ImageConverter.getBytesFromBitmap(bitMapImage);
        if (image == null)
            image = new byte[]{};

        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME, name);
        values.put(ProductContract.ProductEntry.COLUMN_PRICE, price);
        values.put(ProductContract.ProductEntry.COLUMN_IMAGE, image);
        values.put(ProductContract.ProductEntry.COLUMN_PHONE, phone);
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, quantity);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER, supplier);

        //Check if Product Insert State
        Uri uri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
        if (uri == null) {
            Toast.makeText(this, "Save Product Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Save Product Done", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //Update Current Product
    private void updateCurrentProduct() {
        if (this.currentUri != null) {
            boolean isValid = checkViewsValid();
            if (!isValid) {
                Toast.makeText(this, "Sorry Invalid Information", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = productName.getText().toString();
            double price = Double.parseDouble(productPrice.getText().toString());
            int quantity = Integer.parseInt(productQuantity.getText().toString());
            String supplier = productSupplier.getText().toString();
            String phone = supplierPhone.getText().toString();
            byte[] bitmapArr = ImageConverter.getBytesFromBitmap(bitMapImage);
            if (bitmapArr == null)
                bitmapArr = new byte[]{};

            ContentValues values = new ContentValues();
            values.put(ProductContract.ProductEntry.COLUMN_NAME, name);
            values.put(ProductContract.ProductEntry.COLUMN_PRICE, price);
            values.put(ProductContract.ProductEntry.COLUMN_IMAGE, bitmapArr);
            values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, quantity);
            values.put(ProductContract.ProductEntry.COLUMN_PHONE, phone);
            values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER, supplier);

            int updatedId = getContentResolver().update(currentUri, values, null, null);
            if (updatedId == -1) {
                Toast.makeText(this, "Sorry Invalid Update", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update Product is Done", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //Delete Current Product
    private void deleteCurrentProduct() {
        String selection = ProductContract.ProductEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(ContentUris.parseId(currentUri))};
        getContentResolver().delete(ProductContract.ProductEntry.CONTENT_URI, selection, selectionArgs);
        finish();
    }

    //Show Dialog To Delete or cancel this product
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteCurrentProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener listener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.cancel, listener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Set Settings For Save Activity Mode
    private void saveActivitySettings(Menu menu) {
        //Order Menu Name change
        menu.findItem(R.id.orderProduct).setTitle(getString(R.string.save_product));
        //InVisible Delete Menu Item
        menu.findItem(R.id.deleteProduct).setVisible(false);
        //Gone The Button
        this.callSupplier.setVisibility(View.GONE);

    }

    //Set Settings For Update Activity Mode
    private void updateActivitySettings(Menu menu) {
        //Change Order Product To Update Product
        menu.findItem(R.id.orderProduct).setTitle(getString(R.string.update_product));
        //visible The Button
        this.callSupplier.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate The Menu
        getMenuInflater().inflate(R.menu.order, menu);
        //Change Order menu name
        if (currentUri == null) {
            saveActivitySettings(menu);
        } else {
            updateActivitySettings(menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.orderProduct:
                if (currentUri == null)
                    saveNewProduct();
                else
                    updateCurrentProduct();
                return true;

            case R.id.deleteProduct:
                //Delete This Product
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(DetailActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(DetailActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
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
                ProductContract.ProductEntry.COLUMN_PHONE,
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
            //Get Column Index
            int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRICE);
            int imageColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_IMAGE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_SUPPLIER);
            int phoneColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PHONE);

            //Get Values
            String name = cursor.getString(nameColumnIndex);
            String price = cursor.getString(priceColumnIndex);
            String quantity = cursor.getString(quantityColumnIndex);
            String supplier = cursor.getString(supplierColumnIndex);
            String phone = cursor.getString(phoneColumnIndex);
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
            this.supplierPhone.setText(phone);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Make Ui Views With Default Value
        this.productName.setText("");
        this.productPrice.setText("");
        this.productQuantity.setText("");
        this.productSupplier.setText("");
        this.supplierPhone.setText("");
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
