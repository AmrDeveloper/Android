package com.worldnews.amrdeveloper.worldnews.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.worldnews.amrdeveloper.worldnews.Model.News;
import com.worldnews.amrdeveloper.worldnews.Query.QueryUtils;

import java.util.List;

/**
 * Created by AmrDeveloper on 1/22/2018.
 */

public class NewsAsyncLoader extends AsyncTaskLoader<List<News>> {

    //Get Current Api Url
    private String apiUrl;

    public NewsAsyncLoader(Context context , String apiUrl ) {
        super(context);
        this.apiUrl = apiUrl;
    }

    @Override
    public List<News> loadInBackground() {
        //Load And Parser Data From Api Url
        List<News> listOfNews = QueryUtils.readDataFromApi(apiUrl);
        return listOfNews;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
