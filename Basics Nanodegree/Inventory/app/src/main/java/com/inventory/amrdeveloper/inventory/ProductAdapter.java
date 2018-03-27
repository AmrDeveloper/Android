package com.inventory.amrdeveloper.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inventory.amrdeveloper.inventory.DataBase.ProductContract;

/**
 * Created by AmrDeveloper on 2/12/2018.
 */

public class ProductAdapter extends CursorAdapter {

    public ProductAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public ProductAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public ProductAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //inflate The View
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        //Views
        ImageView productImage = view.findViewById(R.id.productImage);
        TextView productName = view.findViewById(R.id.productName);
        TextView productPrice = view.findViewById(R.id.productPrice);
        final TextView productQuantity = view.findViewById(R.id.productQuantity);
        ImageButton productSaleOne = view.findViewById(R.id.productSaleOne);
        RelativeLayout itemLayout = view.findViewById(R.id.itemLayout);

        //Get Column Index
        int nameColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME);
        int imageColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_IMAGE);
        int priceColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_QUANTITY);

        //Get Values
        String name = cursor.getString(nameColumnIndex);
        String price = cursor.getString(priceColumnIndex);
        String quantity = cursor.getString(quantityColumnIndex);

        byte[] image = cursor.getBlob(imageColumnIndex);

        if (image.length != 0) {
            Bitmap imageMap = ImageConverter.getBitmapFromBytes(image);
            productImage.setImageBitmap(imageMap);
        } else {
            productImage.setImageResource(R.drawable.sale_product);
        }

        int idColumnIndex = cursor.getColumnIndexOrThrow(ProductContract.ProductEntry._ID);

        final int rowId = cursor.getInt(idColumnIndex);

        //Set Strings on Views
        productName.setText(name);
        productPrice.setText(price);
        productQuantity.setText(quantity);

        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Detail activity
                Intent intent = new Intent(context, DetailActivity.class);

                // Form the content URI that represents clicked medicine.
                Uri currentInventoryUri = ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, rowId);

                // Set the URI on the data field of the intent
                intent.setData(currentInventoryUri);

                context.startActivity(intent);
            }
        });

        productSaleOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantity = Integer.parseInt(productQuantity.getText().toString());

                Uri currentInventoryUri = ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, rowId);

                if (quantity >= 0) {
                    //Quantity sell one
                    quantity = quantity - 1;
                    String quantityString = Integer.toString(quantity);
                    ContentValues values = new ContentValues();
                    values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, quantityString);
                    int rowsAffected = context.getContentResolver().update(currentInventoryUri, values, null, null);
                    if (rowsAffected != 0) {
                        /* update text view if database update is successful */
                        productQuantity.setText(quantityString);
                    } else {
                        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
