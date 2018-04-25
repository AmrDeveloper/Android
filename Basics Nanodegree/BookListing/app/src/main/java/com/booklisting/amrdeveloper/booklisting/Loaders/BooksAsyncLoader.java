package com.booklisting.amrdeveloper.booklisting.Loaders;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.booklisting.amrdeveloper.booklisting.Model.Book;
import com.booklisting.amrdeveloper.booklisting.Utils.QueryUtils;

import java.util.List;

/**
 * Created by AmrDeveloper on 4/22/2018.
 */

public class BooksAsyncLoader extends AsyncTaskLoader<List<Book>> {

    private Bundle args;
    private List<Book> booksInventory;

    public BooksAsyncLoader(Context context, Bundle args) {
        super(context);
        this.args = args;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (args == null) {
            return;
        }

        if (booksInventory != null) {
            deliverResult(booksInventory);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Book> loadInBackground() {
         /* Extract the search query from the args using our constant */
        String searchQueryUrlString = args.getString("QUERY");

        /* If the user didn't enter anything, there's nothing to search for */
        if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
            return null;
        }
        return QueryUtils.getDataFromInternet(searchQueryUrlString);
    }

    @Override
    public void deliverResult(List<Book> data) {
        booksInventory = data;
        super.deliverResult(data);
    }
}
