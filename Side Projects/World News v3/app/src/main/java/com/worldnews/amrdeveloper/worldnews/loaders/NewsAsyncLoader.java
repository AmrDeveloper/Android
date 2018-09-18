package com.worldnews.amrdeveloper.worldnews.loaders;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.worldnews.amrdeveloper.worldnews.data.NewsContract;
import com.worldnews.amrdeveloper.worldnews.model.News;
import com.worldnews.amrdeveloper.worldnews.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmrDeveloper on 1/22/2018.
 */

public class NewsAsyncLoader extends AsyncTaskLoader<List<News>> {

    //Get Current Api Url
    private String apiUrl;
    private String section;

    public NewsAsyncLoader(Context context, String apiUrl, String section) {
        super(context);
        this.apiUrl = apiUrl;
        this.section = section;
    }

    @Override
    public List<News> loadInBackground() {
        //Load And Parser Data From Api Url
        List<News> newsList = QueryUtils.readDataFromApi(apiUrl);

        if(!newsList.isEmpty()){
            //Delete old news First
            deleteOldNews();

            //Put new news into database
            insertBulkNews(newsList);
        }

        return newsList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * @param newsList : insert all news into database
     */
    private void insertBulkNews(List<News> newsList) {
        List<ContentValues> newsValues = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            newsValues.add(createNewsContentValues(newsList.get(i)));
        }

        getContext().getContentResolver().bulkInsert(
                NewsContract.NewsEntry.CONTENT_URI,
                newsValues.toArray(new ContentValues[newsList.size()]));
    }

    /**
     *  Delete all news in this section
     */
    private void deleteOldNews() {
        Log.v("DELETE","Delete Method Run");
        String selection = NewsContract.NewsEntry.COLUMN_NEWS_SECTION + "=?";
        String[] selectionArgs = new String[]{section};

        getContext().getContentResolver().delete(
                NewsContract.NewsEntry.CONTENT_URI,
                selection,
                selectionArgs
        );
    }

    /**
     * @param news : Get information from News Model object
     * @return : all news object into ContentValues object
     */
    private ContentValues createNewsContentValues(News news) {
        String newsTitle = news.getTitle();
        String newsSection = news.getPillar();
        String newsDate = news.getDate();
        String newsAuthor = news.getNewsAuthor();
        String newsDescription = news.getShortDescription();

        ContentValues values = new ContentValues();
        values.put(NewsContract.NewsEntry.COLUMN_NEWS_TITLE, newsTitle);
        values.put(NewsContract.NewsEntry.COLUMN_NEWS_SECTION, newsSection);
        values.put(NewsContract.NewsEntry.COLUMN_NEWS_DATE, newsDate);
        values.put(NewsContract.NewsEntry.COLUMN_NEWS_AUTHOR, newsAuthor);
        values.put(NewsContract.NewsEntry.COLUMN_NEWS_DESC, newsDescription);
        return values;
    }
}
