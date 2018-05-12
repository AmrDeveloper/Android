package com.worldnews.amrdeveloper.worldnews.data;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;

import com.worldnews.amrdeveloper.worldnews.adapter.NewsCursorAdapter;

/**
 * Created by AmrDeveloper on 5/7/2018.
 */

public class NewsLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private NewsCursorAdapter newsCursorAdapter;

    public NewsLoaderManager(Context context, NewsCursorAdapter newsCursorAdapter) {
        this.context = context;
        this.newsCursorAdapter = newsCursorAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
            String[] projection = {
                    NewsContract.NewsEntry._ID,
                    NewsContract.NewsEntry.COLUMN_NEWS_TITLE,
                    NewsContract.NewsEntry.COLUMN_NEWS_SECTION,
                    NewsContract.NewsEntry.COLUMN_NEWS_DATE,
                    NewsContract.NewsEntry.COLUMN_NEWS_AUTHOR,
                    NewsContract.NewsEntry.COLUMN_NEWS_DESC
            };
            return new CursorLoader(
                    context,
                    NewsContract.NewsEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null
            );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        newsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        newsCursorAdapter.swapCursor(null);
    }
}
