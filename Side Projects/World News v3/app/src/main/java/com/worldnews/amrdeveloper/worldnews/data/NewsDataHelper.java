package com.worldnews.amrdeveloper.worldnews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AmrDeveloper on 5/1/2018.
 */

public class NewsDataHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = NewsDataHelper.class.getSimpleName();

    public final static String DATABASE_NAME = "news.db";
    public final static int DATABASE_VERSION = 1;

    public NewsDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //SQLite Creation Query
        String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " ("+
                NewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NewsContract.NewsEntry.COLUMN_NEWS_TITLE + " TEXT NOT NULL, " +
                NewsContract.NewsEntry.COLUMN_NEWS_SECTION + " TEXT , " +
                NewsContract.NewsEntry.COLUMN_NEWS_DATE + " TEXT , " +
                NewsContract.NewsEntry.COLUMN_NEWS_AUTHOR + " TEXT , " +
                NewsContract.NewsEntry.COLUMN_NEWS_DESC + " TEXT , " +
                " UNIQUE (" + NewsContract.NewsEntry.COLUMN_NEWS_TITLE + ") ON CONFLICT REPLACE);";

        //execute Sqlite
        database.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
