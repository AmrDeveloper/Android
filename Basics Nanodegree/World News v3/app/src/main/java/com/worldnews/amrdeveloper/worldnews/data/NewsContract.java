package com.worldnews.amrdeveloper.worldnews.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AmrDeveloper on 5/1/2018.
 */

public final class NewsContract {


    private NewsContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.worldnews.amrdeveloper.worldnews";
    public static final Uri BASE_CONTNENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NEWS = "news";


    public static final class NewsEntry implements BaseColumns {

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTNENT_URI, PATH_NEWS);

        public static final String TABLE_NAME = "news";

        public static final String _ID = BaseColumns._ID;

        public final static String COLUMN_NEWS_TITLE = "title";
        public final static String COLUMN_NEWS_SECTION = "section";
        public final static String COLUMN_NEWS_DATE = "date";
        public final static String COLUMN_NEWS_AUTHOR = "author";
        public final static String COLUMN_NEWS_DESC = "desc";


    }


}
