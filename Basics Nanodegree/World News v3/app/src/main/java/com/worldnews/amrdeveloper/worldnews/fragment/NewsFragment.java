package com.worldnews.amrdeveloper.worldnews.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.worldnews.amrdeveloper.worldnews.activities.WebViewerActivity;
import com.worldnews.amrdeveloper.worldnews.adapter.NewsCursorAdapter;
import com.worldnews.amrdeveloper.worldnews.adapter.NewsListAdapter;
import com.worldnews.amrdeveloper.worldnews.data.NewsLoaderManager;
import com.worldnews.amrdeveloper.worldnews.loaders.NewsAsyncLoader;
import com.worldnews.amrdeveloper.worldnews.model.News;
import com.worldnews.amrdeveloper.worldnews.R;

import java.util.List;

/**
 * Created by AmrDeveloper on 1/29/2018.
 */

public class NewsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>>,
        SwipeRefreshLayout.OnRefreshListener {


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

    private NetworkInfo networkInfo;

    private SwipeRefreshLayout swipeRefreshLayout;

    private LoaderManager.LoaderCallbacks<List<News>> loaderCallbacksObject = this;


    //Loader Final Id
    public final static int NEWS_LOADER_ID = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //Inflate View
        View rootView = inflater.inflate(R.layout.news_listview, container, false);

        errorMessage = rootView.findViewById(R.id.errorMessage);
        loadingBar = rootView.findViewById(R.id.loadingBar);

        newsAdapter = new NewsListAdapter(getActivity());

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, loaderCallbacksObject);
                                    }
                                }
        );

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
                //Open This Uri in Browser Using Intent
                Intent intent = new Intent(getActivity(), WebViewerActivity.class);
                intent.putExtra("newsUrl", current.getNewsUrl());
                startActivity(intent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter.
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Update empty state with no connection error message
            errorMessage.setText(R.string.no_connection);
            //Get data from database
            NewsCursorAdapter newsCursorAdapter = new NewsCursorAdapter(getContext(), null);
            newsListView.setAdapter(newsCursorAdapter);
            NewsLoaderManager dataLoader = new NewsLoaderManager(getContext(),Api.SECTION_NEWS_DATA, newsCursorAdapter);
            getActivity().getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null,dataLoader);
        }
        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        //Show Loading Bar and hide ListView and Error TextView
        loadingBar.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);

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
        ///Hide the indicator after the data is appeared
        loadingBar.setVisibility(View.GONE);

        // Check if connection is still available, otherwise show appropriate message
        if (networkInfo != null && networkInfo.isConnected()) {
            // If there is a valid list of news stories, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                newsAdapter.clear();
                newsAdapter.addAll(data);
            } else {
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(getString(R.string.no_data));
            }

            //Here
        } else {
            errorMessage.setText(R.string.no_connection);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        //Clear The Data From ListView
        newsAdapter.clear();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, loaderCallbacksObject);
    }
}
