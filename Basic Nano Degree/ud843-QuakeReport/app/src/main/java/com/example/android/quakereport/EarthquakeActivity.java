/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>>{

    private static int threadCounter = 1;
    //State TextView
    private TextView state;
    //ProgressBar for loading
    private ProgressBar loadingBar;
    //Adapter Object
    private QuakeReportAdapter adapter;
    //Class Name
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    //ِApi Request Link
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //State TextView
        state = (TextView)findViewById(R.id.state);

        //ProgressBar
        loadingBar = (ProgressBar)findViewById(R.id.loadingBar);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        //set empty view
        earthquakeListView.setEmptyView(findViewById(R.id.state));

        //Take context and the list of Earth Quakes
        adapter = new QuakeReportAdapter(this,new ArrayList<EarthQuake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        //TODO : Check network Connection
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            //InitLoader and Start
            Log.v("Loaders","run");
            getLoaderManager().initLoader(0,null,this);
        }
        else{
            //Make Loader Gond
            loadingBar.setVisibility(View.GONE);
            //Show New State
            state.setText(R.string.no_internet_connection);
        }

    }

    //Run Loaders
    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        Loader<List<EarthQuake>> loader = new EarthQuakeLoader(this,USGS_REQUEST_URL);
        //Make Loading Bar Run
        loadingBar.setVisibility(View.VISIBLE);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuake>> loader, List<EarthQuake> earthQuakes) {
        // TODO: Update the UI with the result
        if(earthQuakes.size() == 0 || earthQuakes == null){
           return;
        }
        //put all data in adapter
        adapter.addAll(earthQuakes);
        //Make Progress Bar Gone
        loadingBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<List<EarthQuake>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.
        adapter.addAll(new ArrayList<EarthQuake>());
        //Make Progress Bar Gone
        loadingBar.setVisibility(View.GONE);
    }





}
