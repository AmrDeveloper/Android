package com.worldnews.amrdeveloper.worldnews.Fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.worldnews.amrdeveloper.worldnews.Loaders.NewsAsyncLoader;
import com.worldnews.amrdeveloper.worldnews.Model.News;
import com.worldnews.amrdeveloper.worldnews.R;

import java.util.List;

/**
 * Created by AmrDeveloper on 1/29/2018.
 */

public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {


    /**
     * ListView Object to show Data with Custom Adapter
     */
    private ListView newsListView;
    /**
     * TextView Object to Show Error Message To User
     */
    private TextView errorMessage;
    /**
     * ProgressBar to show Loading Cycle while data is loading
     */
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
        //Inflate View
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

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        //Get Country Current value from Edit Preferences
        String country = sharedPrefs.getString(
                getString(R.string.news_country_key),
                getString(R.string.news_country_default)
        );

        //Get Order By value from List Preferences
        String orderBy = sharedPrefs.getString(
                getString(R.string.news_order_key),
                getString(R.string.news_order_default)
        );

        Uri baseUri = Uri.parse(Api.API_LINK);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        //Append Attribute to The Link
        uriBuilder.appendQueryParameter(Api.QUERY, country);
        uriBuilder.appendQueryParameter(Api.ORDER_BY, orderBy);
        uriBuilder.appendQueryParameter(Api.SECTION, Api.SECTION_NEWS);
        uriBuilder.appendQueryParameter(Api.SHOW_TAGS, Api.CONTRIBUTOR);
        uriBuilder.appendQueryParameter(Api.SHOW_FIELDS, Api.API_FIELDS_INPUT);
        uriBuilder.appendQueryParameter(Api.PAGE_SIZE, Api.PAGE_SIZE_NUM);
        uriBuilder.appendQueryParameter(Api.API_KEY, Api.MY_API_KEY);

        //Start Loader
        Loader<List<News>> dataLoader = new NewsAsyncLoader(getActivity(), uriBuilder.toString());

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
