package com.inventory1.amrdeveloper.inventoryv1;

import android.provider.BaseColumns;

/**
 * Created by AmrDeveloper on 3/20/2018.
 */

public class ProductContract {


    public static final class ProductEntry implements BaseColumns {

        public static final String TABLE_NAME = "product";

        /**
         * Product Name
         * Type is TEXT
         */
        public static final String COLUMN_NAME = "name";
        /**
         * Product Price
         * Type is REAL
         */
        public static final String COLUMN_PRICE = "price";
        /**
         * Quantity
         * Type is INTEGER
         */
        public static final String COLUMN_QUANTITY = "quantity";
        /**
         * quantity Name
         * Type is TEXT
         */
        public static final String COLUMN_SUPPLIER = "supplier_name";
        /**
         * supplier Phone
         * Type is Text
         */
        public static final String COLUMN_PHONE = "supplier_phone";
    }
}