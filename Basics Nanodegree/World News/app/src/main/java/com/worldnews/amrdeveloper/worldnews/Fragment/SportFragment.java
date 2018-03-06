package com.worldnews.amrdeveloper.worldnews.Fragment;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.worldnews.amrdeveloper.worldnews.Adapter.NewsListAdapter;
import com.worldnews.amrdeveloper.worldnews.Constants.ApiUrls;
import com.worldnews.amrdeveloper.worldnews.Loaders.NewsAsyncLoader;
import com.worldnews.amrdeveloper.worldnews.Model.News;
import com.worldnews.amrdeveloper.worldnews.R;

import java.util.List;

/**
 * Created by AmrDeveloper on 1/29/2018.
 */

public class SportFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    private ListView newsListView;
    private TextView errorMessage;
    private ProgressBar loadingBar;

    /**
     * Adapter For List of news
     */
    private NewsListAdapter newsAdapter;

    //Loader Final Id
    private final int LOADER_ID = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.news_listview, container, false);

        errorMessage = rootView.findViewById(R.id.errorMessage);
        loadingBar = rootView.findViewById(R.id.loadingBar);

        newsAdapter = new NewsListAdapter(getActivity());

        newsListView = rootView.findViewById(R.id.newsListView);
        //Set Empty View to show error message when no data
        newsListView.setEmptyView(errorMessage);
        newsListView.setAdapter(newsAdapter);
        //on every news click
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News current = newsAdapter.getItem(i);
                //Create News Uri From String url
                Uri newsLink = Uri.parse(current.getNewsUrl());
                //Open This Uri in Browser Using Intent
                Intent intent = new Intent(Intent.ACTION_VIEW, newsLink);
                startActivity(intent);
            }
        });

        //Call Loader
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);

        return rootView;
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        //Show Loading Bar and hide ListView and Error TextView
        loadingBar.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
        newsListView.setVisibility(View.INVISIBLE);

        //Start Loader
        Loader<List<News>> dataLoader = new NewsAsyncLoader(getActivity(), ApiUrls.SPORT);
        return dataLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        //Hide Loading Bar and show ListView
        loadingBar.setVisibility(View.INVISIBLE);
        newsListView.setVisibility(View.VISIBLE);

        //update The List View by new data
        newsAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        //Clear The Data From ListView
        newsAdapter.clear();
    }
}
