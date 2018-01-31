package com.worldnews.amrdeveloper.worldnews.Fragment;

/**
 * Created by AmrDeveloper on 1/29/2018.
 */

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.worldnews.amrdeveloper.worldnews.Adapter.NewsListAdapter;
import com.worldnews.amrdeveloper.worldnews.Loaders.NewsAsyncLoader;
import com.worldnews.amrdeveloper.worldnews.Model.News;
import com.worldnews.amrdeveloper.worldnews.R;

import java.util.List;

/**
 * Created by AmrDeveloper on 1/29/2018.
 */

public class SportFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    private ListView newsListView;
    private NewsListAdapter newsAdapter;

    private final int LOADER_ID = 1;
    private final String API_LINK = "https://content.guardianapis.com/search?section=sport&show-fields=thumbnail,trailText&page-size=20&api-key=246cb6ff-865d-4763-9284-168fdf56cf13";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.news_listview, container, false);

        newsListView = (ListView) rootView.findViewById(R.id.newsListView);

        newsAdapter = new NewsListAdapter(getActivity());

        newsListView.setAdapter(newsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News current = newsAdapter.getItem(i);
                //Open Current News Url In Browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getNewsUrl()));
                startActivity(intent);
            }
        });

        //Class Loader
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);

        return rootView;
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Loader<List<News>> dataLoader = new NewsAsyncLoader(getActivity(), API_LINK);
        return dataLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        //update The List View by new data
        newsAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        //Clear The Data From ListView
        newsAdapter.clear();
    }
}
