package com.worldnews.amrdeveloper.worldnews;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.worldnews.amrdeveloper.worldnews.Adapter.NewsAdapter;
import com.worldnews.amrdeveloper.worldnews.Loaders.NewsAsyncLoader;
import com.worldnews.amrdeveloper.worldnews.Model.News;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private ListView newsListView;
    private NewsAdapter newsAdapter;

    private final int LOADER_ID = 1;
    private final String API_LINK = "https://content.guardianapis.com/search?show-fields=thumbnail&page-size=20&api-key=246cb6ff-865d-4763-9284-168fdf56cf13";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListView = (ListView)findViewById(R.id.newsListView);
        newsAdapter = new NewsAdapter(this);

        newsListView.setAdapter(newsAdapter);


        //Class Loader
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID,null,this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Loader<List<News>> dataLoader = new NewsAsyncLoader(this,API_LINK);
        return dataLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        //update The List View by new data
        newsAdapter.addAll(newsList);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        //Clear The Data From ListView
        newsAdapter.clear();
    }

}
