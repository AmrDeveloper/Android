package com.inventory.amrdeveloper.inventory.DataBase;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AmrDeveloper on 2/11/2018.
 */

public final class ProductContract {

    ProductContract(){

    }

    public static final String PATH_PRODUCT = "product";
    public static final String CONTENT_AUTHORITY = "com.amrdeveloper.product";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class ProductEntry implements BaseColumns{

        public static final String TABLE_NAME = "product";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCT);

        /**Unique ID number for the pet (only for use in the database table).
         * Type: INTEGER
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        //public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_ID = ProductEntry._ID;
        /** Product Name
         * Type is TEXT
         */
        public static final String COLUMN_NAME = "name";
        /** Product Price
         * Type is REAL
         */
        public static final String COLUMN_PRICE = "price";
        /** Product Image
         * Type is BLOB
         */
        public static final String COLUMN_IMAGE = "image";
        /** Product quantity
         * Type is INTEGER
         */
        public static final String COLUMN_QUANTITY = "quantity";
        /** Product supplier
         * Type is TEXT
         */
        public static final String COLUMN_SUPPLIER = "supplier";

        /** Product supplier Phone
         * Type is TEXT
         */
        public static final String COLUMN_PHONE = "phone";
    }

}
