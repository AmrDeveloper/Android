package com.inventory.amrdeveloper.inventory.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by AmrDeveloper on 2/11/2018.
 */

public class SQLiteProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */

    public static final String LOG_TAG = SQLiteProvider.class.getSimpleName();

    //two type of matcher
    private static final int PRODUCT = 100;
    private static final int PRODUCT_ID = 101;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //Static Block
    static {
        uriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCT, PRODUCT);
        uriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCT + "/#", PRODUCT_ID);
    }


    //SQLite Helper Object
    private SQLiteHelper mDataBaseHelper;

    @Override
    public boolean onCreate() {
        //If is first time the helper will create database
        //else it will use it
        //Like SingleTon Design pattern :D
        mDataBaseHelper = new SQLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //Get DataBase object
        SQLiteDatabase sqLiteDatabase = mDataBaseHelper.getReadableDatabase();
        //Cursor Object to return it
        Cursor cursor;
        //Using Uri Matcher
        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                cursor = sqLiteDatabase.query(
                        ProductContract.ProductEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder);

                break;

            case PRODUCT_ID:
                selection = ProductContract.ProductEntry.COLUMN_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(ProductContract.ProductEntry.TABLE_NAME
                        , projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new IllegalArgumentException("cannot query unknown uri " + uri);
        }
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case PRODUCT:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertProduct(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();

        //Make Sure All Strings not empty and not equals null
        String name = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_NAME);
        Double price = contentValues.getAsDouble(ProductContract.ProductEntry.COLUMN_PRICE);
        String supplier = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER);
        Integer quantity = contentValues.getAsInteger(ProductContract.ProductEntry.COLUMN_QUANTITY);
        byte[] image = contentValues.getAsByteArray(ProductContract.ProductEntry.COLUMN_IMAGE);

        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("product requires a name");
        }

        if (price == null) {
            throw new IllegalArgumentException("product requires a price");
        }

        if (supplier == null) {
            throw new IllegalArgumentException("product requires a supplier");
        }


        if (quantity == null) {
            throw new IllegalArgumentException("product requires a quantity");
        }

        //Insert And Notify Change
        long productId = database.insert(ProductContract.ProductEntry.TABLE_NAME, null, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, productId);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCT_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Cant Update");
        }
    }

    //Update Product Method to Check every information is valid to put it on database
    private int updateProduct(@NonNull Uri uri,
                              @Nullable ContentValues contentValues,
                              @Nullable String selection,
                              @Nullable String[] selectionArgs) {

        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_NAME)) {
            String name = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_PRICE)) {
            Double price = contentValues.getAsDouble(ProductContract.ProductEntry.COLUMN_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Product requires a price");
            }
        }
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_IMAGE)) {
            byte[] image = contentValues.getAsByteArray(ProductContract.ProductEntry.COLUMN_IMAGE);
            if (image == null) {
                throw new IllegalArgumentException("Product requires an image");
            }
        }
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_QUANTITY)) {
            Integer quantity = contentValues.getAsInteger(ProductContract.ProductEntry.COLUMN_QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Product requires valid quantity");
            }
        }
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_SUPPLIER)) {
            String supplier = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER);
            if (supplier == null) {
                throw new IllegalArgumentException("Product requires a supplier");
            }
        }
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_PHONE)) {
            String phone = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER);
            if (phone == null) {
                throw new IllegalArgumentException("Phone Number is required");
            }
        }
        if (contentValues.size() == 0) {
            return 0;
        }


        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        int rowUpdated = database.update(ProductContract.ProductEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        if (rowUpdated != 0) {
            // Notify all listeners that the data has changed for the pet content URI
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Returns the number of database rows affected by the update statement
        return rowUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int indexDeleted;
        //DataBase Writer Object
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        //Find Uri type using Uri Matcher
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                indexDeleted = database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                indexDeleted = database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (indexDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return indexDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                return ProductContract.ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return ProductContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
